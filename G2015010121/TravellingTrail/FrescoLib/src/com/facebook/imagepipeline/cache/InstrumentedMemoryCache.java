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

import com.facebook.common.internal.Predicate;
import com.facebook.common.references.CloseableReference;


public class InstrumentedMemoryCache<K, V, S> implements MemoryCache<K, V, S> {
  private final MemoryCache<K, V, S> mDelegate;
  private final MemoryCacheTracker mTracker;

  public InstrumentedMemoryCache(
      MemoryCache<K, V, S> delegate,
      MemoryCacheTracker tracker) {
    mDelegate = delegate;
    mTracker = tracker;
  }

  @Override
  public CloseableReference<V> get(K key, @Nullable S lookupStrategy) {
    CloseableReference<V> result = mDelegate.get(key, lookupStrategy);
    if (result == null) {
      mTracker.onCacheMiss();
    } else {
      mTracker.onCacheHit();
    }
    return result;
  }

  @Override
  public CloseableReference<V> cache(
      K key, CloseableReference<V> value) {
    mTracker.onCachePut();
    return mDelegate.cache(key, value);
  }

  @Override
  public long removeAll(Predicate<K> match) {
    return mDelegate.removeAll(match);
  }
}
