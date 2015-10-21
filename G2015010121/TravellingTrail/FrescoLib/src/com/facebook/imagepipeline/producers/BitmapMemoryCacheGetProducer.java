/*
 * Copyright (c) 2015-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */

package com.facebook.imagepipeline.producers;

import com.facebook.imagepipeline.cache.BitmapMemoryCacheKey;
import com.facebook.imagepipeline.cache.MemoryCache;
import com.facebook.imagepipeline.cache.CacheKeyFactory;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.request.ImageRequest;

import com.facebook.common.internal.VisibleForTesting;

/**
 * Memory cache producer for the bitmap memory cache.
 */
public class BitmapMemoryCacheGetProducer
    extends MemoryCacheProducer<BitmapMemoryCacheKey, CloseableImage> {
  @VisibleForTesting static final String PRODUCER_NAME = "BitmapMemoryCacheGetProducer";

  public BitmapMemoryCacheGetProducer(
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
    return false;
  }

  @Override
  protected boolean shouldCacheResult(
      CloseableReference<CloseableImage> result,
      BitmapMemoryCacheKey cacheKey,
      boolean isLast) {
    return false;
  }

  @Override
  protected String getProducerName() {
    return PRODUCER_NAME;
  }
}
