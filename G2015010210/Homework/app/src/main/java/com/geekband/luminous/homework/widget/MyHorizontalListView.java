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
 * custom twice
 * Created by luminous on 15/8/15.
 */
public class MyHorizontalListView extends AdapterView {
    public static final String TAG = "MHV";
    Adapter mAdapter;
    /** 列表的最左边 */
    int mListLeft;
    /** 手指点下去的X */
    int mTouchDownX;
    /** 测量时的List的右边缘,超出屏幕则不再添加View */
    int rightEdge;
    /** 测量时得List得左边缘,超出屏幕则不在添加View */
    int leftEdge;
    /** 最后一个加入的View的position */
    int myLastAddPosition = -1;
    /** layout中的第一个View的position */
    int myFirstViewPosition;
    LinkedList<View> cacheViews = new LinkedList<>();

    public MyHorizontalListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setClickable(true);
    }

    @Override
    public Adapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void setAdapter(Adapter adapter) {
        this.mAdapter = adapter;
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
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (mAdapter == null || mAdapter.getCount() <= 0) {
            return;
        }
        //新添加的View没有被layout,所以left,right的值都是错误的,不能在add之后调用remove
        //remove left
        while (getChildCount() > 0 && getChildAt(0).getRight() < 0) {
            View firstView = getChildAt(0);
            cacheViews.addLast(firstView);
            mListLeft += firstView.getWidth();
            leftEdge += firstView.getMeasuredWidth();
            removeViewInLayout(firstView);
            myFirstViewPosition++;
            Log.w(TAG, "onLayout after remove left views count:" + getChildCount());
        }
        //remove right
        while (getChildCount() > 0 && getChildAt(getChildCount() - 1).getLeft() > getWidth()) {
            Log.e(TAG, "onLayout left:" + getChildAt(getChildCount() - 1).getLeft());

            View lastView = getChildAt(getChildCount() - 1);
            cacheViews.addLast(lastView);
            rightEdge -= lastView.getMeasuredWidth();
            removeViewInLayout(lastView);
            myLastAddPosition--;
            Log.w(TAG, "onLayout after remove right views count:" + getChildCount());
        }
        //add in right
        while (rightEdge < getWidth() && myLastAddPosition + 1 < mAdapter.getCount()) {
            View v = mAdapter.getView(myLastAddPosition + 1, getCachedView(), this);
            v.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.EXACTLY | getHeight());
            rightEdge += v.getMeasuredWidth();
            LayoutParams myLayoutParams = v.getLayoutParams();
            if (myLayoutParams == null) {
                myLayoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
            }
            addViewInLayout(v, -1, myLayoutParams);
            myLastAddPosition++;
        }
        //add in left
        while (leftEdge >= 0 && myFirstViewPosition > 0) {
            View v = mAdapter.getView(myFirstViewPosition - 1, getCachedView(), this);
            //TODO:当View是回收来的时候,View是否需要measure
            v.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.EXACTLY | getHeight());
            leftEdge -= v.getMeasuredWidth();
            mListLeft -= v.getMeasuredWidth();
            LayoutParams myLayoutParams = v.getLayoutParams();
            if (myLayoutParams == null) {
                myLayoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
            }
            addViewInLayout(v, 0, myLayoutParams);
            myFirstViewPosition--;
            //Log.d(TAG, "onLayout add to Left");
        }

        //Layout
        int mLeft = 0;
        //Log.e(TAG, "onLayout last added position:" + myLastAddPosition);
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            int cRight = childView.getMeasuredWidth();
            childView.layout(mLeft + mListLeft, 0, mLeft + cRight + mListLeft, getHeight());
            mLeft += cRight;
            //Log.e(TAG, "onLayout mLeft" + mLeft);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchDownX = (int) event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                mListLeft += (int) (event.getX() - mTouchDownX);
                rightEdge += (int) (event.getX() - mTouchDownX);
                leftEdge += (int) (event.getX() - mTouchDownX);
//                if (mListLeft > 0) {
//                    rightEdge -= mListLeft;
//                    mListLeft = 0;
//                }
                mTouchDownX = (int) event.getX();
                requestLayout();
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);

    }

    /**
     * 获得缓存的View,作为ConvertView
     *
     * @return
     */
    private View getCachedView() {
        if (cacheViews.size() > 0) {
            return cacheViews.removeFirst();
        }
        return null;
    }
}
