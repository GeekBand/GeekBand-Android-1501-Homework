package com.geekband.luminous.homework.widget;

import android.content.Context;
import android.util.AttributeSet;
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

    int mTouchStartY;
    int mListTopStart;
    /** The top of this ListView */
    int mListTop;
    /** adapter positions of the first currently visible views */
    int mFirstItemPosition;
    /** adapter positions of the last currently visible views */
    int mLastItemPosition;

    /** The adapter with all the data */
    private Adapter mAdapter;
    /** the cache of Views */
    private LinkedList<View> recycledView = new LinkedList<>();
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
            fillListDown(mListTop);
        } else {
            //int offset = mListTop + mListTopOffset - getChildTop(getChildAt(0));
            //removeNonVisibleViews(offset);
            //fillList(offset);
        }

        positionItems();
    }

    private void fillListDown(int offset) {
        int bottomEdge = offset;
        while (bottomEdge < getHeight() && mLastItemPosition+1 < mAdapter.getCount()) {
            View newBottomChild = mAdapter.getView(mLastItemPosition+1, null, this);
            addAndMeasureChild(newBottomChild);
            bottomEdge += newBottomChild.getMeasuredHeight();
            mLastItemPosition++;
        }
    }

    private void fillListUp(int offset) {

    }

    private void fillList(int offset) {

    }

    private void removeNonVisibleViews(int offset) {

    }

    private void addAndMeasureChild(View child) {
        LayoutParams params = child.getLayoutParams();
        if (params == null) {
            params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        }
        addViewInLayout(child, -1, params, true);
        int itemWidth = getWidth();
        System.out.println(itemWidth);
        child.measure(MeasureSpec.EXACTLY | itemWidth, MeasureSpec.UNSPECIFIED);
    }

    private void positionItems() {
        int top = 0 + mListTop;
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
