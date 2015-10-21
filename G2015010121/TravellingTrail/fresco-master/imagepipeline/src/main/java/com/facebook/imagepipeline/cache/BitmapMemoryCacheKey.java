/*
 * Copyright (c) 2015-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */

package com.facebook.imagepipeline.cache;

import javax.annotation.Nullable;

import java.util.Locale;

import android.net.Uri;

import com.facebook.common.internal.Objects;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.util.HashCodeUtil;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.common.ResizeOptions;

/**
 * Cache key for BitmapMemoryCache
 */
public class BitmapMemoryCacheKey {
  private final String mSourceString;
  private final @Nullable ResizeOptions mResizeOptions;
  private final boolean mAutoRotated;
  private final ImageDecodeOptions mImageDecodeOptions;
  private final int mHash;

  public BitmapMemoryCacheKey(
      String sourceString,
      @Nullable ResizeOptions resizeOptions,
      boolean autoRotated,
      ImageDecodeOptions imageDecodeOptions) {
    mSourceString = Preconditions.checkNotNull(sourceString);
    mResizeOptions = resizeOptions;
    mAutoRotated = autoRotated;
    mImageDecodeOptions = imageDecodeOptions;
    mHash = HashCodeUtil.hashCode(
        sourceString.hashCode(),
        (resizeOptions != null) ? resizeOptions.hashCode() : 0,
        autoRotated ? Boolean.TRUE.hashCode() : Boolean.FALSE.hashCode(),
        mImageDecodeOptions);
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof BitmapMemoryCacheKey)) {
      return false;
    }

    BitmapMemoryCacheKey otherKey = (BitmapMemoryCacheKey) o;
    return mHash == otherKey.mHash &&
        mSourceString.equals(otherKey.mSourceString) &&
        Objects.equal(this.mResizeOptions, otherKey.mResizeOptions) &&
        mAutoRotated == otherKey.mAutoRotated &&
        Objects.equal(mImageDecodeOptions, otherKey.mImageDecodeOptions);
  }

  @Override
  public int hashCode() {
    return mHash;
  }

  public Uri sourceUri() {
    return Uri.parse(mSourceString);
  }

  public String getSourceUriString() {
    return mSourceString;
  }

  @Override
  public String toString() {
    return String.format(
        (Locale) null,
        "%s_%s_%s_%d_%d",
        mSourceString,
        mResizeOptions,
        Boolean.toString(mAutoRotated),
        mImageDecodeOptions,
        mHash);
  }
}
