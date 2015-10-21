/*
 * Copyright (c) 2015-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */

package com.facebook.imagepipeline.request;

import javax.annotation.Nullable;

import android.net.Uri;

import com.facebook.common.internal.Preconditions;
import com.facebook.common.util.UriUtil;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.common.ResizeOptions;

import static com.facebook.imagepipeline.request.ImageRequest.ImageType;
import static com.facebook.imagepipeline.request.ImageRequest.RequestLevel;

/**
 * Builder class for {@link ImageRequest}s.
 */
public class ImageRequestBuilder {

  private Uri mSourceUri = null;
  private RequestLevel mLowestPermittedRequestLevel = RequestLevel.FULL_FETCH;
  private boolean mAutoRotateEnabled = false;
  private @Nullable ResizeOptions mResizeOptions = null;
  private ImageDecodeOptions mImageDecodeOptions = ImageDecodeOptions.defaults();
  private ImageType mImageType = ImageType.DEFAULT;
  private boolean mProgressiveRenderingEnabled = false;
  private boolean mLocalThumbnailPreviewsEnabled = false;
  private @Nullable Postprocessor mPostprocessor = null;

  /**
   * Creates a new request builder instance. The setting will be done according to the source type.
   * @param uri the uri to fetch
   * @return a new request builder instance
   */
  public static ImageRequestBuilder newBuilderWithSource(Uri uri) {
    return new ImageRequestBuilder().setSource(uri);
  }

  private ImageRequestBuilder() {
  }

  /**
   * Sets the source uri (both network and local uris are supported).
   * Note: this will enable disk caching for network sources, and disable it for local sources.
   * @param uri the uri to fetch the image from
   * @return the updated builder instance
   */
  public ImageRequestBuilder setSource(Uri uri) {
    Preconditions.checkNotNull(uri);

    mSourceUri = uri;
    return this;
  }

  /** Gets the source Uri. */
  public Uri getSourceUri() {
    return mSourceUri;
  }

  /**
   * Sets the lowest level that is permitted to request the image from.
   * @param requestLevel the lowest request level that is allowed
   * @return the updated builder instance
   */
  public ImageRequestBuilder setLowestPermittedRequestLevel(RequestLevel requestLevel) {
    mLowestPermittedRequestLevel = requestLevel;
    return this;
  }

  /** Gets the lowest permitted request level. */
  public RequestLevel getLowestPermittedRequestLevel() {
    return mLowestPermittedRequestLevel;
  }

  /**
   * Enables or disables auto-rotate for the image in case image has orientation.
   * @return the updated builder instance
   * @param enabled
   */
  public ImageRequestBuilder setAutoRotateEnabled(boolean enabled) {
    mAutoRotateEnabled = enabled;
    return this;
  }

  /** Returns whether auto-rotate is enabled. */
  public boolean isAutoRotateEnabled() {
    return mAutoRotateEnabled;
  }

  /**
   * Sets resize options in case resize should be performed.
   * @param resizeOptions resize options
   * @return the modified builder instance
   */
  public ImageRequestBuilder setResizeOptions(ResizeOptions resizeOptions) {
    mResizeOptions = resizeOptions;
    return this;
  }

  /** Gets the resize options if set, null otherwise. */
  public @Nullable ResizeOptions getResizeOptions() {
    return mResizeOptions;
  }

  public ImageRequestBuilder setImageDecodeOptions(ImageDecodeOptions imageDecodeOptions) {
    mImageDecodeOptions = imageDecodeOptions;
    return this;
  }

  public ImageDecodeOptions getImageDecodeOptions() {
    return mImageDecodeOptions;
  }

  /**
   * Sets the image type. Pipeline might use different caches and eviction policies for each
   * image type.
   * @param imageType the image type to set
   * @return the modified builder instance
   */
  public ImageRequestBuilder setImageType(ImageType imageType) {
    mImageType = imageType;
    return this;
  }

  /** Gets the image type (profile image or default). */
  public ImageType getImageType() {
    return mImageType;
  }

  /**
   * Enables or disables progressive rendering.
   * @param enabled
   * @return the modified builder instance
   */
  public ImageRequestBuilder setProgressiveRenderingEnabled(boolean enabled) {
    mProgressiveRenderingEnabled = enabled;
    return this;
  }

  /** Returns whether progressive loading is enabled. */
  public boolean isProgressiveRenderingEnabled() {
    return mProgressiveRenderingEnabled;
  }

  /**
   * Enables or disables the use of local thumbnails as previews.
   * @param enabled
   * @return the modified builder instance
   */
  public ImageRequestBuilder setLocalThumbnailPreviewsEnabled(boolean enabled) {
    mLocalThumbnailPreviewsEnabled = enabled;
    return this;
  }

  /** Returns whether the use of local thumbnails for previews is enabled */
  public boolean isLocalThumbnailPreviewsEnabled() {
    return mLocalThumbnailPreviewsEnabled;
  }

  /**
   * Sets the postprocessor.
   * @param postprocessor postprocessor to postprocess the output bitmap with.
   */
  public ImageRequestBuilder setPostprocessor(Postprocessor postprocessor) {
    mPostprocessor = postprocessor;
    return this;
  }

  /** Gets postprocessor if set, null otherwise. */
  public @Nullable Postprocessor getPostprocessor() {
    return mPostprocessor;
  }

  /**
   * Builds the Request.
   * @return a valid image request
   */
  public ImageRequest build() {
    validate();
    return new ImageRequest(this);
  }

  /** An exception class for builder methods. */
  public static class BuilderException extends RuntimeException {
    public BuilderException(String message) {
      super("Invalid request builder: " + message);
    }
  }

  /** Performs validation. */
  protected void validate() {
    // make sure that the source uri is set correctly.
    if (mSourceUri == null) {
      throw new BuilderException("Source must be set!");
    }

    // For local resource we require caller to specify statically generated resource id as a path.
    if (UriUtil.isLocalResourceUri(mSourceUri)) {
      if (!mSourceUri.isAbsolute()) {
        throw new BuilderException("Resource URI path must be absolute.");
      }
      if (mSourceUri.getPath().isEmpty()) {
        throw new BuilderException("Resource URI must not be empty");
      }
      try {
        Integer.parseInt(mSourceUri.getPath().substring(1));
      } catch (NumberFormatException ignored) {
        throw new BuilderException("Resource URI path must be a resource id.");
      }
    }

    // For local asset we require caller to specify absolute path of an asset, which will be
    // resolved by AssetManager relative to configured asset folder of an app.
    if (UriUtil.isLocalAssetUri(mSourceUri) && !mSourceUri.isAbsolute()) {
      throw new BuilderException("Asset URI path must be absolute.");
    }
  }
}
