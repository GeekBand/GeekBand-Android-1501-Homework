/*
 * Copyright (c) 2015-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */

package com.facebook.drawee.drawable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;

import com.facebook.testing.robolectric.v2.WithTestDefaultsRunner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(WithTestDefaultsRunner.class)
public class RoundedBitmapDrawableTest {
  private Resources mResources;
  private Bitmap mBitmap;
  private DisplayMetrics mDisplayMetrics;

  RoundedBitmapDrawable mRoundedBitmapDrawable;
  private final Drawable.Callback mCallback = mock(Drawable.Callback.class);

  @Before
  public void setUp() {
    mResources = mock(Resources.class);
    mBitmap = mock(Bitmap.class);
    mDisplayMetrics = mock(DisplayMetrics.class);
    when(mResources.getDisplayMetrics()).thenReturn(mDisplayMetrics);
    mRoundedBitmapDrawable = new RoundedBitmapDrawable(mResources, mBitmap);
    mRoundedBitmapDrawable.setCallback(mCallback);
  }

  @Test
  public void testDefaults() {
    assertArrayEquals(new float[]{0, 0, 0, 0, 0, 0, 0, 0}, mRoundedBitmapDrawable.mCornerRadii, 0);
    AndroidGraphicsTestUtils.assertEquals(new Matrix(), mRoundedBitmapDrawable.mInverseTransform);
  }

  @Test
  public void testSetCircle() {
    mRoundedBitmapDrawable.setCircle(true);
    verify(mCallback).invalidateDrawable(mRoundedBitmapDrawable);
    assertTrue(mRoundedBitmapDrawable.mIsCircle);
  }

  @Test
  public void testSetRadii() {
    mRoundedBitmapDrawable.setCornerRadii(new float[]{1, 2, 3, 4, 5, 6, 7, 8});
    verify(mCallback).invalidateDrawable(mRoundedBitmapDrawable);
    assertArrayEquals(new float[]{1, 2, 3, 4, 5, 6, 7, 8}, mRoundedBitmapDrawable.mCornerRadii, 0);
  }

  @Test
  public void testSetRadius() {
    mRoundedBitmapDrawable.setCornerRadius(9);
    verify(mCallback).invalidateDrawable(mRoundedBitmapDrawable);
    assertArrayEquals(new float[]{9, 9, 9, 9, 9, 9, 9, 9}, mRoundedBitmapDrawable.mCornerRadii, 0);
  }

  @Test
  public void testSetBorder() {
    int color = 0x12345678;
    float width = 5;
    mRoundedBitmapDrawable.setBorder(color, width);
    verify(mCallback).invalidateDrawable(mRoundedBitmapDrawable);
    assertEquals(color, mRoundedBitmapDrawable.mBorderColor);
    assertEquals(width, mRoundedBitmapDrawable.mBorderWidth, 0);
  }
}
