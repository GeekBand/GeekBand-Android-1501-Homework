package com.geekband.luminous.homework.widget;

import android.content.Context;
import android.nfc.Tag;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;

/**
 * custom twice
 * Created by hisashieki on 15/8/15.
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
    /** 最后一个加入的View的position */
    int myLastAddPosition=-1;
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
        while (rightEdge < getWidth() && myLastAddPosition+1 < mAdapter.getCount()) {
            View v = mAdapter.getView(myLastAddPosition+1, null, this);
            v.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.EXACTLY | getHeight());
            rightEdge += v.getMeasuredWidth();
            LayoutParams myLayoutParams = v.getLayoutParams();
            if (myLayoutParams == null) {
                myLayoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
            }
            addViewInLayout(v, -1, myLayoutParams);
            myLastAddPosition++;
        }
        int mLeft = 0;
        Log.e(TAG, "onLayout last added position:" + myLastAddPosition);
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            int cRight = childView.getMeasuredWidth();
            childView.layout(mLeft+mListLeft, 0, mLeft + cRight+mListLeft, getHeight());
            mLeft += cRight;
            Log.e(TAG, "onLayout mLeft" + mLeft);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchDownX = (int) event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                mListLeft += (int)(event.getX() - mTouchDownX);
                rightEdge += (int)(event.getX() - mTouchDownX);
                mTouchDownX = (int) event.getX();
                requestLayout();
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);

    }
}
