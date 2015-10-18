/*
 * Copyright (c) 2015-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */

package com.facebook.imagepipeline.producers;

import com.facebook.common.internal.ImmutableMap;
import com.facebook.common.internal.VisibleForTesting;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.cache.MemoryCache;
import com.facebook.imagepipeline.cache.CacheKeyFactory;
import com.facebook.imagepipeline.request.ImageRequest;

/**
 * Memory cache producer.
 *
 * <p>This producer looks in the specified memory cache for the requested image. If the image is
 * found, then it is passed to the consumer. If the image is not found, then the request is passed
 * to the next producer in the sequence. Any results that the producer returns are passed to the
 * consumer, and the last result is also put into the memory cache.
 */
public abstract class MemoryCacheProducer<K, T> implements Producer<CloseableReference<T>> {
  @VisibleForTesting static final String CACHED_VALUE_FOUND = "cached_value_found";

  protected final MemoryCache<K, T, Void> mMemoryCache;
  protected final CacheKeyFactory mCacheKeyFactory;
  private final Producer<CloseableReference<T>> mNextProducer;

  protected MemoryCacheProducer(
      MemoryCache<K, T, Void> memoryCache,
      CacheKeyFactory cacheKeyFactory,
      Producer<CloseableReference<T>> nextProducer) {
    mMemoryCache = memoryCache;
    mCacheKeyFactory = cacheKeyFactory;
    mNextProducer = nextProducer;
  }

  @Override
  public void produceResults(
      final Consumer<CloseableReference<T>> consumer,
      final ProducerContext producerContext) {

    final ProducerListener listener = producerContext.getListener();
    final String requestId = producerContext.getId();
    listener.onProducerStart(requestId, getProducerName());

    final K cacheKey = getCacheKey(producerContext.getImageRequest());
    CloseableReference<T> cachedReference = mMemoryCache.get(cacheKey, null);
    if (cachedReference != null) {
      boolean shouldStartNextProducer = shouldStartNextProducer(cachedReference);
      if (!shouldStartNextProducer) {
        listener.onProducerFinishWithSuccess(
            requestId,
            getProducerName(),
            listener.requiresExtraMap(requestId) ?
                ImmutableMap.of(CACHED_VALUE_FOUND, "true") :
                null);
      }
      consumer.onNewResult(cachedReference, !shouldStartNextProducer);
      cachedReference.close();
      if (!shouldStartNextProducer) {
        return;
      }
    }

    Consumer<CloseableReference<T>> consumerOfNextProducer;
    if (!shouldCacheReturnedValues()) {
      consumerOfNextProducer = consumer;
    } else {
      consumerOfNextProducer = new BaseConsumer<CloseableReference<T>>() {
        @Override
        public void onNewResultImpl(
            CloseableReference<T> newResult, boolean isLast) {
          CloseableReference<T> cachedResult = null;
          if (newResult != null && shouldCacheResult(newResult, cacheKey, isLast)) {
            cachedResult = mMemoryCache.cache(cacheKey, newResult);
          }

          if (cachedResult != null) {
            consumer.onNewResult(cachedResult, isLast);
            cachedResult.close();
          } else {
            consumer.onNewResult(newResult, isLast);
          }
        }

        @Override
        public void onFailureImpl(Throwable t) {
          consumer.onFailure(t);
        }

        @Override
        protected void onCancellationImpl() {
          consumer.onCancellation();
        }
      };
    }

    listener.onProducerFinishWithSuccess(
        requestId,
        getProducerName(),
        listener.requiresExtraMap(requestId) ? ImmutableMap.of(CACHED_VALUE_FOUND, "false") : null);
    mNextProducer.produceResults(consumerOfNextProducer, producerContext);
  }

  protected abstract K getCacheKey(ImageRequest imageRequest);

  protected abstract boolean shouldStartNextProducer(CloseableReference<T> cachedResultFound);

  protected abstract boolean shouldCacheReturnedValues();

  protected abstract boolean shouldCacheResult(
      CloseableReference<T> result,
      K cacheKey,
      boolean isLast);

  protected abstract String getProducerName();
}
