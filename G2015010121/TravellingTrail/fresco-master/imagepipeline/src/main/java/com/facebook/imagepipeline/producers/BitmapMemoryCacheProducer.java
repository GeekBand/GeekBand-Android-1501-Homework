/*
 * Copyright (c) 2015-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */

package com.facebook.imagepipeline.producers;

import com.facebook.common.internal.VisibleForTesting;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.cache.BitmapMemoryCacheKey;
import com.facebook.imagepipeline.cache.MemoryCache;
import com.facebook.imagepipeline.cache.CacheKeyFactory;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.imagepipeline.request.ImageRequest;

/**
 * Memory cache producer for the bitmap memory cache.
 */
public class BitmapMemoryCacheProducer
    extends MemoryCacheProducer<BitmapMemoryCacheKey, CloseableImage> {
  @VisibleForTesting static final String PRODUCER_NAME = "BitmapMemoryCacheProducer";

  public BitmapMemoryCacheProducer(
      MemoryCache<BitmapMemoryCacheKey, CloseableImage, Void> memoryCache,
      CacheKeyFactory cacheKeyFactory,
      Producer<CloseableReference<CloseableImage>> nextProducer) {
    super(memoryCache, cacheKeyFactory, nextProducer);
  }

  @Override
  protected BitmapMemoryCacheKey getCacheKey(ImageRequest imageRequest) {
    return mCacheKeyFactory.getBitmapCacheKey(imageRequest);
  }

  @Override
  protected boolean shouldStartNextProducer(CloseableReference<CloseableImage> cachedResultFound) {
    return !cachedResultFound.get().getQualityInfo().isOfFullQuality();
  }

  @Override
  protected boolean shouldCacheReturnedValues() {
    return true;
  }

  @Override
  protected boolean shouldCacheResult(
      CloseableReference<CloseableImage> result,
      BitmapMemoryCacheKey cacheKey,
      boolean isLast) {
    if (result.get().isStateful()) {
      return false;
    }
    return isLast || shouldCacheIntermediateResult(result, cacheKey);
  }

  private boolean shouldCacheIntermediateResult(
      CloseableReference<CloseableImage> newResult,
      BitmapMemoryCacheKey cacheKey) {
    CloseableReference<CloseableImage> currentCachedResult = mMemoryCache.get(cacheKey, null);
    if (currentCachedResult == null) {
      return true;
    }
    try {
      QualityInfo currentQualityInfo = currentCachedResult.get().getQualityInfo();
      return !currentQualityInfo.isOfFullQuality() &&
          newResult.get().getQualityInfo().getQuality() > currentQualityInfo.getQuality();
    } finally {
      CloseableReference.closeSafely(currentCachedResult);
    }
  }

  @Override
  protected String getProducerName() {
    return PRODUCER_NAME;
  }
}
