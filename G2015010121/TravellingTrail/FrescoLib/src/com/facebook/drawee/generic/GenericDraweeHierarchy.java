/*
 * Copyright (c) 2015-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */

package com.facebook.drawee.generic;

import javax.annotation.Nullable;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;

import com.facebook.common.internal.Preconditions;
import com.facebook.drawee.drawable.FadeDrawable;
import com.facebook.drawee.drawable.ForwardingDrawable;
import com.facebook.drawee.drawable.MatrixDrawable;
import com.facebook.drawee.drawable.RoundedBitmapDrawable;
import com.facebook.drawee.drawable.RoundedColorDrawable;
import com.facebook.drawee.drawable.RoundedCornersDrawable;
import com.facebook.drawee.drawable.ScaleTypeDrawable;
import com.facebook.drawee.drawable.SettableDrawable;
import com.facebook.drawee.drawable.VisibilityAwareDrawable;
import com.facebook.drawee.drawable.VisibilityCallback;
import com.facebook.drawee.interfaces.SettableDraweeHierarchy;

import static com.facebook.drawee.drawable.ScalingUtils.ScaleType;

/**
 * A SettableDraweeHierarchy that displays placeholder image until the actual image is set.
 * If provided, failure image will be used in case of failure (placeholder otherwise).
 * If provided, retry image will be used in case of failure when retrying is enabled.
 * If provided, progressbar will be displayed until fully loaded.
 * Each image can be displayed with a different scale type (or no scaling at all).
 * Fading between the layers is supported.
 *
 * <p>
 * Example hierarchy with placeholder, retry, failure and one actual image:
 *  <pre>
 *     o FadeDrawable (top level drawable)
 *     |
 *     +--o ScaleTypeDrawable
 *     |  |
 *     |  +--o Drawable (placeholder image)
 *     |
 *     +--o ScaleTypeDrawable
 *     |  |
 *     |  +--o SettableDrawable
 *     |     |
 *     |     +--o Drawable (actual image)
 *     |
 *     +--o ScaleTypeDrawable
 *     |  |
 *     |  +--o Drawable (retry image)
 *     |
 *     +--o ScaleTypeDrawable
 *        |
 *        +--o Drawable (failure image)
 *  </pre>
 *
 * <p>
 * Note:
 * - ScaleType and Matrix transformations will be added only if specified. If both are unspecified,
 * then the branch for that image will be attached directly.
 * - It is not permitted to set both ScaleType transformation and Matrix transformation for the
 * same image.
 * - A Matrix transformation is only supported for actual image.
 * - All branches (placeholder, failure, retry, actual image, progressBar) are optional.
 * If some branch is not specified it won't be created. The exception is placeholder branch,
 * which will, if not specified, be created with a transparent drawable.
 * - If overlays and/or backgrounds are specified, they are added to the same fade drawable, and
 * are always displayed.
 * - Instance of some drawable should be used by only one DH. If more than one DH is being built
 * with the same builder, different drawable instances must be specified for each DH.
 */
public class GenericDraweeHierarchy implements SettableDraweeHierarchy {

  private static class RootFadeDrawable extends FadeDrawable implements VisibilityAwareDrawable {
    @Nullable
    private VisibilityCallback mVisibilityCallback;

    public RootFadeDrawable(Drawable[] layers) {
      super(layers);
    }

    @Override
    public int getIntrinsicWidth() {
      return -1;
    }

    @Override
    public int getIntrinsicHeight() {
      return -1;
    }

    @Override
    public void setVisibilityCallback(@Nullable VisibilityCallback visibilityCallback) {
      mVisibilityCallback = visibilityCallback;
    }

    @Override
    public boolean setVisible(boolean visible, boolean restart) {
      if (mVisibilityCallback != null) {
        mVisibilityCallback.onVisibilityChange(visible);
      }
      return super.setVisible(visible, restart);
    }

    @Override
    public void draw(Canvas canvas) {
      if (mVisibilityCallback != null) {
        mVisibilityCallback.onDraw();
      }
      super.draw(canvas);
    }
  }

  private Drawable mEmptyPlaceholderDrawable;
  private final Drawable mEmptyActualImageDrawable = new ColorDrawable(Color.TRANSPARENT);
  private final Drawable mEmptyControllerOverlayDrawable = new ColorDrawable(Color.TRANSPARENT);

  private final Resources mResources;

  private final Drawable mTopLevelDrawable;
  private final FadeDrawable mFadeDrawable;
  private final SettableDrawable mActualImageSettableDrawable;

  private final int mPlaceholderImageIndex;
  private final int mProgressBarImageIndex;
  private final int mActualImageIndex;
  private final int mRetryImageIndex;
  private final int mFailureImageIndex;
  private final int mControllerOverlayIndex;

  private RoundingParams mRoundingParams;

  GenericDraweeHierarchy(GenericDraweeHierarchyBuilder builder) {
    mResources = builder.getResources();
    mRoundingParams = builder.getRoundingParams();

    int numLayers = 0;

    // backgrounds
    int numBackgrounds = (builder.getBackgrounds() != null) ? builder.getBackgrounds().size() : 0;
    int backgroundsIndex = numLayers;
    numLayers += numBackgrounds;

    // placeholder image branch
    Drawable placeholderImageBranch = builder.getPlaceholderImage();
    if (placeholderImageBranch == null) {
      placeholderImageBranch = getEmptyPlaceholderDrawable();
    }
    placeholderImageBranch = maybeApplyRounding(
        mRoundingParams,
        mResources,
        placeholderImageBranch);
    placeholderImageBranch = maybeWrapWithScaleType(
        placeholderImageBranch,
        builder.getPlaceholderImageScaleType());
    mPlaceholderImageIndex = numLayers++;

    // actual image branch
    Drawable actualImageBranch = null;
    mActualImageSettableDrawable = new SettableDrawable(mEmptyActualImageDrawable);
    actualImageBranch = mActualImageSettableDrawable;
    actualImageBranch = maybeWrapWithScaleType(
        actualImageBranch,
        builder.getActualImageScaleType(),
        builder.getActualImageFocusPoint());
    actualImageBranch = maybeWrapWithMatrix(
        actualImageBranch,
        builder.getActualImageMatrix());
    actualImageBranch.setColorFilter(builder.getActualImageColorFilter());
    mActualImageIndex = numLayers++;

    // progressBar image branch
    Drawable progressBarImageBranch = builder.getProgressBarImage();
    if (progressBarImageBranch != null) {
      progressBarImageBranch = maybeWrapWithScaleType(
          progressBarImageBranch,
          builder.getProgressBarImageScaleType());
      mProgressBarImageIndex = numLayers++;
    } else {
      mProgressBarImageIndex = -1;
    }

    // retry image branch
    Drawable retryImageBranch = builder.getRetryImage();
    if (retryImageBranch != null) {
      retryImageBranch = maybeWrapWithScaleType(
          retryImageBranch,
          builder.getRetryImageScaleType());
      mRetryImageIndex = numLayers++;
    } else {
      mRetryImageIndex = -1;
    }

    // failure image branch
    Drawable failureImageBranch = builder.getFailureImage();
    if (failureImageBranch != null) {
      failureImageBranch = maybeWrapWithScaleType(
          failureImageBranch,
          builder.getFailureImageScaleType());
      mFailureImageIndex = numLayers++;
    } else {
      mFailureImageIndex = -1;
    }

    // overlays
    int numOverlays = (builder.getOverlays() != null) ? builder.getOverlays().size() : 0;
    int overlaysIndex = numLayers;
    numLayers += numOverlays;
    numLayers += (builder.getPressedStateOverlay() != null) ? 1 : 0;

    // controller overlay
    mControllerOverlayIndex = numLayers++;

    // array of layers
    Drawable[] layers = new Drawable[numLayers];
    if (numBackgrounds > 0) {
      int index = 0;
      for (Drawable background : builder.getBackgrounds()) {
        layers[backgroundsIndex + index++] = background;
      }
    }
    if (mPlaceholderImageIndex >= 0) {
      layers[mPlaceholderImageIndex] = placeholderImageBranch;
    }
    if (mActualImageIndex >= 0) {
      layers[mActualImageIndex] = actualImageBranch;
    }
    if (mProgressBarImageIndex >= 0) {
      layers[mProgressBarImageIndex] = progressBarImageBranch;
    }
    if (mRetryImageIndex >= 0) {
      layers[mRetryImageIndex] = retryImageBranch;
    }
    if (mFailureImageIndex >= 0) {
      layers[mFailureImageIndex] = failureImageBranch;
    }
    if (numOverlays > 0) {
      int index = 0;
      for (Drawable overlay : builder.getOverlays()) {
        layers[overlaysIndex + index++] = overlay;
      }
      if (builder.getPressedStateOverlay() != null) {
        layers[overlaysIndex + index++] = builder.getPressedStateOverlay();
      }
    }
    if (mControllerOverlayIndex >= 0) {
      layers[mControllerOverlayIndex] = mEmptyControllerOverlayDrawable;
    }

    Drawable root;

    // fade drawable composed of branches
    mFadeDrawable = new RootFadeDrawable(layers);
    mFadeDrawable.setTransitionDuration(builder.getFadeDuration());
    root = mFadeDrawable;

    // rounded corners drawable (optional)
    root = maybeWrapWithRoundedCorners(mRoundingParams, root);

    // top-level drawable
    mTopLevelDrawable = root;
    mTopLevelDrawable.mutate();

    resetFade();
  }

  private static Drawable maybeWrapWithScaleType(
      Drawable drawable,
      @Nullable ScaleType scaleType) {
    return maybeWrapWithScaleType(drawable, scaleType, null);
  }

  private static Drawable maybeWrapWithScaleType(
      Drawable drawable,
      @Nullable ScaleType scaleType,
      @Nullable PointF focusPoint) {
    Preconditions.checkNotNull(drawable);
    if (scaleType == null) {
      return drawable;
    }
    ScaleTypeDrawable scaleTypeDrawable = new ScaleTypeDrawable(drawable, scaleType);
    if (focusPoint != null) {
      scaleTypeDrawable.setFocusPoint(focusPoint);
    }
    return scaleTypeDrawable;
  }

  private static Drawable maybeWrapWithMatrix(
      Drawable drawable,
      @Nullable Matrix matrix) {
    Preconditions.checkNotNull(drawable);
    if (matrix == null) {
      return drawable;
    }
    return new MatrixDrawable(drawable, matrix);
  }

  private static Drawable maybeWrapWithRoundedCorners(
      @Nullable RoundingParams roundingParams,
      Drawable drawable) {
    if (roundingParams != null &&
        roundingParams.getRoundingMethod() == RoundingParams.RoundingMethod.OVERLAY_COLOR) {
      RoundedCornersDrawable roundedCornersDrawable = new RoundedCornersDrawable(drawable);
      roundedCornersDrawable.setCircle(roundingParams.getRoundAsCircle());
      roundedCornersDrawable.setRadii(roundingParams.getCornersRadii());
      roundedCornersDrawable.setOverlayColor(roundingParams.getOverlayColor());
      roundedCornersDrawable.setBorder(
          roundingParams.getBorderColor(),
          roundingParams.getBorderWidth());
      return roundedCornersDrawable;
    } else {
      return drawable;
    }
  }

  private static Drawable maybeApplyRounding(
      @Nullable RoundingParams roundingParams,
      Resources resources,
      Drawable drawable) {
    if (roundingParams != null &&
        roundingParams.getRoundingMethod() == RoundingParams.RoundingMethod.BITMAP_ONLY) {
      if (drawable instanceof BitmapDrawable) {
        RoundedBitmapDrawable roundedBitmapDrawable =
            RoundedBitmapDrawable.fromBitmapDrawable(resources, (BitmapDrawable) drawable);
        roundedBitmapDrawable.setCircle(roundingParams.getRoundAsCircle());
        roundedBitmapDrawable.setCornerRadii(roundingParams.getCornersRadii());
        roundedBitmapDrawable.setBorder(
            roundingParams.getBorderColor(),
            roundingParams.getBorderWidth());
        return roundedBitmapDrawable;
      }
      if (drawable instanceof ColorDrawable &&
          Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
        RoundedColorDrawable roundedColorDrawable =
            RoundedColorDrawable.fromColorDrawable((ColorDrawable) drawable);
        roundedColorDrawable.setCircle(roundingParams.getRoundAsCircle());
        roundedColorDrawable.setRadii(roundingParams.getCornersRadii());
        roundedColorDrawable.setBorder(
            roundingParams.getBorderColor(),
            roundingParams.getBorderWidth());
        return roundedColorDrawable;
      }
    }
    return drawable;
  }

  private void resetActualImages() {
    if (mActualImageSettableDrawable != null) {
      mActualImageSettableDrawable.setDrawable(mEmptyActualImageDrawable);
    }
  }

  private void resetFade() {
    if (mFadeDrawable != null) {
      mFadeDrawable.beginBatchMode();
      // turn on all layers (backgrounds, branches, overlays)
      mFadeDrawable.fadeInAllLayers();
      // turn off branches (leaving backgrounds and overlays on)
      fadeOutBranches();
      // turn on placeholder
      fadeInLayer(mPlaceholderImageIndex);
      mFadeDrawable.finishTransitionImmediately();
      mFadeDrawable.endBatchMode();
    }
  }

  private void fadeOutBranches() {
    fadeOutLayer(mPlaceholderImageIndex);
    fadeOutLayer(mActualImageIndex);
    fadeOutLayer(mProgressBarImageIndex);
    fadeOutLayer(mRetryImageIndex);
    fadeOutLayer(mFailureImageIndex);
  }

  private void fadeInLayer(int index) {
    if (index >= 0) {
      mFadeDrawable.fadeInLayer(index);
    }
  }

  private void fadeOutLayer(int index) {
    if (index >= 0) {
      mFadeDrawable.fadeOutLayer(index);
    }
  }

  private void setProgress(int progress) {
    // display indefinite progressbar when not fully loaded, hide otherwise
    if (progress == 100) {
      fadeOutLayer(mProgressBarImageIndex);
    } else {
      fadeInLayer(mProgressBarImageIndex);
    }
  }

  // SettableDraweeHierarchy interface

  @Override
  public Drawable getTopLevelDrawable() {
    return mTopLevelDrawable;
  }

  @Override
  public void reset() {
    resetActualImages();
    resetFade();
  }

  @Override
  public void setImage(Drawable drawable, boolean immediate, int progress) {
    drawable = maybeApplyRounding(mRoundingParams, mResources, drawable);
    drawable.mutate();
    mActualImageSettableDrawable.setDrawable(drawable);
    mFadeDrawable.beginBatchMode();
    fadeOutBranches();
    fadeInLayer(mActualImageIndex);
    setProgress(progress);
    if (immediate) {
      mFadeDrawable.finishTransitionImmediately();
    }
    mFadeDrawable.endBatchMode();
  }

  @Override
  public void setProgress(int progress, boolean immediate) {
    mFadeDrawable.beginBatchMode();
    setProgress(progress);
    if (immediate) {
      mFadeDrawable.finishTransitionImmediately();
    }
    mFadeDrawable.endBatchMode();
  }

  @Override
  public void setFailure(Throwable throwable) {
    mFadeDrawable.beginBatchMode();
    fadeOutBranches();
    if (mFailureImageIndex >= 0) {
      fadeInLayer(mFailureImageIndex);
    } else {
      fadeInLayer(mPlaceholderImageIndex);
    }
    mFadeDrawable.endBatchMode();
  }

  @Override
  public void setRetry(Throwable throwable) {
    mFadeDrawable.beginBatchMode();
    fadeOutBranches();
    if (mRetryImageIndex >= 0) {
      fadeInLayer(mRetryImageIndex);
    } else {
      fadeInLayer(mPlaceholderImageIndex);
    }
    mFadeDrawable.endBatchMode();
  }

  @Override
  public void setControllerOverlay(@Nullable Drawable drawable) {
    if (drawable == null) {
      drawable = mEmptyControllerOverlayDrawable;
    }
    mFadeDrawable.setDrawable(mControllerOverlayIndex, drawable);
  }

  // Helper methods for accessing layers

  /**
   * Returns the parent drawable at the specified index.
   * <p>
   * If MatrixDrawable or ScaleTypeDrawable is found at that index, it will be returned as a parent.
   * Otherwise, the FadeDrawable will be returned.
   */
  private Drawable findLayerParent(int index) {
    Drawable parent = mFadeDrawable;
    Drawable child = mFadeDrawable.getDrawable(index);
    if (child instanceof MatrixDrawable) {
      parent = child;
      child = parent.getCurrent();
    }
    if (child instanceof ScaleTypeDrawable) {
      parent = child;
      child = parent.getCurrent();
    }
    return parent;
  }

  /**
   * Returns the ScaleTypeDrawable at the specified index, or null if not found.
   */
  private @Nullable ScaleTypeDrawable findLayerScaleTypeDrawable(int index) {
    Drawable drawable = mFadeDrawable.getDrawable(index);
    if (drawable instanceof MatrixDrawable) {
      drawable = drawable.getCurrent();
    }
    if (drawable instanceof ScaleTypeDrawable) {
      return (ScaleTypeDrawable) drawable;
    } else {
      return null;
    }
  }

  /**
   * Sets a child drawable at the specified index.
   *
   * <p> Note: This uses {@code findLayerParent} to find the parent drawable. Given drawable is
   * then set as its child.
   */
  private void setLayerChildDrawable(int index, Drawable drawable) {
    Drawable parent = findLayerParent(index);
    if (parent == mFadeDrawable) {
      mFadeDrawable.setDrawable(index, drawable);
    } else {
      ((ForwardingDrawable) parent).setCurrent(drawable);
    }
  }

  private Drawable getEmptyPlaceholderDrawable() {
    if (mEmptyPlaceholderDrawable == null) {
      mEmptyPlaceholderDrawable = new ColorDrawable(Color.TRANSPARENT);
    }
    return mEmptyPlaceholderDrawable;
  }

  // Mutability

  /** Sets the actual image focus point. */
  public void setActualImageFocusPoint(PointF focusPoint) {
    Preconditions.checkNotNull(focusPoint);
    ScaleTypeDrawable scaleTypeDrawable = findLayerScaleTypeDrawable(mActualImageIndex);
    if (scaleTypeDrawable == null) {
      throw new UnsupportedOperationException("ScaleTypeDrawable not found!");
    }
    scaleTypeDrawable.setFocusPoint(focusPoint);
  }

  /** Sets the actual image scale type. */
  public void setActualImageScaleType(ScaleType scaleType) {
    Preconditions.checkNotNull(scaleType);
    ScaleTypeDrawable scaleTypeDrawable = findLayerScaleTypeDrawable(mActualImageIndex);
    if (scaleTypeDrawable == null) {
      throw new UnsupportedOperationException("ScaleTypeDrawable not found!");
    }
    scaleTypeDrawable.setScaleType(scaleType);
  }

  /** Sets the color filter to be applied on the actual image. */
  public void setActualImageColorFilter(ColorFilter colorfilter) {
    mFadeDrawable.getDrawable(mActualImageIndex).setColorFilter(colorfilter);
  }

  /**
   * Gets the post-scaling bounds of the actual image.
   *
   * <p> Note: the returned bounds are not cropped.
   * @param outBounds rect to fill with bounds
   */
  public void getActualImageBounds(RectF outBounds) {
    mActualImageSettableDrawable.getTransformedBounds(outBounds);
  }

  /**
   * Sets a new placeholder drawable.
   *
   * <p>The placeholder scale type will not be changed.
   */
  public void setPlaceholderImage(Drawable drawable) {
    if (drawable == null) {
      drawable = getEmptyPlaceholderDrawable();
    }
    drawable = maybeApplyRounding(mRoundingParams, mResources, drawable);
    setLayerChildDrawable(mPlaceholderImageIndex, drawable);
  }

  /**
   * Sets a new placeholder drawable using the supplied resource ID.
   *
   * <p>The placeholder scale type will not be changed.
   * @param resourceId an identifier of an Android drawable or color resource.
   */
  public void setPlaceholderImage(int resourceId) {
    setPlaceholderImage(mResources.getDrawable(resourceId));
  }

  /**
   * Sets the rounding params.
   */
  public void setRoundingParams(RoundingParams roundingParams) {
    Preconditions.checkState(
        mRoundingParams != null && roundingParams != null &&
            roundingParams.getRoundingMethod() == mRoundingParams.getRoundingMethod(),
        "Rounding method cannot be changed and it has to be set during construction time.");
    mRoundingParams = roundingParams;
    if (roundingParams.getRoundingMethod() == RoundingParams.RoundingMethod.OVERLAY_COLOR) {
      RoundedCornersDrawable roundedCornersDrawable = (RoundedCornersDrawable) mTopLevelDrawable;
      roundedCornersDrawable.setCircle(roundingParams.getRoundAsCircle());
      roundedCornersDrawable.setRadii(roundingParams.getCornersRadii());
      roundedCornersDrawable.setOverlayColor(roundingParams.getOverlayColor());
      roundedCornersDrawable.setBorder(
          roundingParams.getBorderColor(),
          roundingParams.getBorderWidth());
    }
  }

  /**
   * Gets the rounding params.
   * @return rounding params
   */
  public RoundingParams getRoundingParams() {
    return mRoundingParams;
  }
}
