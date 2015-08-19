package com.geekband.luminous.homework.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;

import java.util.LinkedList;

/**
 * A customize listView
 * Created by hisashieki on 15/8/14.
 */
public class MyListViewX extends AdapterView {
    public static final String TAG = "MyListViewX";
    int mTouchStartY;
    int mListTopStart;
    /** The top of this ListView */
    int mListTop;
    /** The offset from the top of the currently first visible item to the top of the first item */
    int mListTopOffset;

    /** adapter positions of the first currently visible views */
    int mFirstItemPosition;
    /** adapter positions of the last currently visible views */
    int mLastItemPosition;

    /** The adapter with all the data */
    private Adapter mAdapter;
    /** the cache of Views */
    private LinkedList<View> cacheViews = new LinkedList<>();

    public MyListViewX(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public Adapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void setAdapter(Adapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public View getSelectedView() {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public void setSelection(int position) {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (mAdapter == null) {
            return;
        }
        if (getChildCount() == 0) {
            mLastItemPosition = -1;
            fillListDown(0, 0);
        } else {
            int offset = mListTop;
            removeNonVisibleViews(offset);
            fillList(offset);
        }

        positionItems();
    }

    private void fillListDown(int bottomEdge, int offset) {
        Log.e(TAG, "fillListDown times+1");
        while (bottomEdge < getHeight() && mLastItemPosition + 1 < mAdapter.getCount()) {
            View newBottomChild = mAdapter.getView(mLastItemPosition + 1, null, this);
            addAndMeasureChild(newBottomChild);
            bottomEdge += newBottomChild.getMeasuredHeight();
            mLastItemPosition++;
            Log.d(TAG, "fillListDown " + mLastItemPosition + "bottomEdge: " + bottomEdge);
        }
    }

    private void fillListUp(int topEdge, int offset) {

    }

    private void fillList(int offset) {
        final int bottomEdge = getChildAt(getChildCount() - 1).getBottom();
        fillListDown(bottomEdge, offset);

        final int topEdge = getChildAt(0).getTop();
        fillListUp(topEdge, offset);
    }

    private void addAndMeasureChild(View child) {
        LayoutParams params = child.getLayoutParams();
        if (params == null) {
            params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        }
        addViewInLayout(child, -1, params, true);
        int itemWidth = getWidth();
        child.measure(MeasureSpec.EXACTLY | itemWidth, MeasureSpec.EXACTLY | itemWidth);
    }

    /**
     * @param offset Offset of the visible area
     */
    private void removeNonVisibleViews(int offset) {
        int childCount = getChildCount();
        if (mLastItemPosition != mAdapter.getCount() - 1 && childCount > 1) {
            View firstChild = getChildAt(0);
            if (firstChild.getBottom() < 0) {
                removeViewInLayout(firstChild);
                cacheViews.addLast(firstChild);
            }
        }

    }


    private void positionItems() {
        int top = +mListTopOffset + mListTop;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            int width = child.getMeasuredWidth();
            int height = child.getMeasuredHeight();
            int left = (getWidth() - width) / 2;
            child.layout(left, top, left + width, top + height);
            top += height;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (getChildCount() == 0) {
            return false;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchStartY = (int) event.getY();
                mListTopStart = getChildAt(0).getTop();
                break;
            case MotionEvent.ACTION_MOVE:
                int scrolledDistance = (int) event.getY() - mTouchStartY;
                mListTop = mListTopStart + scrolledDistance;
                requestLayout();
                break;
            default:
                break;
        }
        return true;
    }

}
