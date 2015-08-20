package com.geekband.luminous.homework.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Adapter;
import android.widget.AdapterView;

import java.util.LinkedList;
import java.util.logging.Handler;

/**
 * custom twice
 * Created by luminous on 15/8/15.
 */
public class MyHorizontalListView extends AdapterView {
    public static final String TAG = "MHV";
    /** 滚动的状态 */
    public static final int TOUCH_STATE_SCROLLING = 2;
    /** 没有点击的状态 */
    private static final int TOUCH_STATE_RESTING = 0;
    /** 点击的状态 */
    private static final int TOUCH_STATE_CLICK = 1;
    /** 用于判定是否为scroll的threshold */
    static int TOUCH_THRESHOLD = 10;
    Adapter mAdapter;
    /** 列表的最左边 */
    int mListLeft;
    /** 手指点下去的X */
    int mTouchDownX;
    /** 手指点下去的Y */
    int mTouchDownY;
    /** 测量时的List的右边缘,超出屏幕则不再添加View */
    int rightEdge;
    /** 测量时得List得左边缘,超出屏幕则不在添加View */
    int leftEdge;
    /** 最后一个加入的View的position */
    int myLastAddPosition = -1;
    /** layout中的第一个View的position */
    int myFirstViewPosition;
    LinkedList<View> cacheViews = new LinkedList<>();
    /** 按键状态 */
    private int mTouchState = TOUCH_STATE_RESTING;
    private Runnable longClickHandler;
    public MyHorizontalListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setClickable(true);
        TOUCH_THRESHOLD = ViewConfiguration.get(context).getScaledPagingTouchSlop();
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

    //TODO:reconstruct the method
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
            //Log.w(TAG, "onLayout after remove left views count:" + getChildCount());
        }
        //remove right
        while (getChildCount() > 0 && getChildAt(getChildCount() - 1).getLeft() > getWidth()) {
            View lastView = getChildAt(getChildCount() - 1);
            cacheViews.addLast(lastView);
            rightEdge -= lastView.getMeasuredWidth();
            removeViewInLayout(lastView);
            myLastAddPosition--;
            //Log.w(TAG, "onLayout after remove right views count:" + getChildCount());
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

    /**
     * 如果在这里不返回true的话,所有的事件都会被Item的listener拦截,导致无法移动List
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchDownX = (int) event.getX();
                mTouchDownY = (int) event.getY();
                mTouchState = TOUCH_STATE_CLICK;
                startLongClickCheck(mTouchDownX,mTouchDownY);
                break;
            case MotionEvent.ACTION_MOVE:
                if (mTouchState == TOUCH_STATE_CLICK) {
                    if (Math.abs(event.getX() - mTouchDownX) > TOUCH_THRESHOLD || Math.abs(event.getY() - mTouchDownY) > TOUCH_THRESHOLD) {
                        mTouchDownX = (int) event.getX();
                        mTouchDownY = (int) event.getY();
                        scrollingList(event);
                    }
                } else if (mTouchState == TOUCH_STATE_SCROLLING) {
                    scrollingList(event);
                }
                break;
            case MotionEvent.ACTION_UP:
                if(mTouchState==TOUCH_STATE_CLICK){
                    clickChild((int) event.getX(), (int) event.getY());
                }
                endTouch();
                break;
            default:
                endTouch();
                break;
        }
        return super.onTouchEvent(event);

    }

    private void endTouch() {
        removeCallbacks(longClickHandler);
        mTouchState = TOUCH_STATE_RESTING;
    }

    private void scrollingList(MotionEvent event) {
        mListLeft += (int) (event.getX() - mTouchDownX);
        rightEdge += (int) (event.getX() - mTouchDownX);
        leftEdge += (int) (event.getX() - mTouchDownX);
//                if (mListLeft > 0) {
//                    rightEdge -= mListLeft;
//                    mListLeft = 0;
//                }
        mTouchDownX = (int) event.getX();
        mTouchDownY = (int) event.getY();
        mTouchState = TOUCH_STATE_SCROLLING;
        requestLayout();
    }

    private void clickChild(int x, int y) {
        Rect rect = new Rect();
        for (int i = 0; i < getChildCount(); i++) {
            View v = getChildAt(i);
            v.getHitRect(rect);
            if(rect.contains(x,y)){
                performItemClick(v,myFirstViewPosition+i,mAdapter.getItemId(myFirstViewPosition+i));
            }
        }
    }
    public void startLongClickCheck(final int x, final int y){
        if(longClickHandler==null){
            longClickHandler = new Runnable() {
                @Override
                public void run() {
                    longClickChild(x,y);
                }
            };
        }
        postDelayed(longClickHandler,ViewConfiguration.get(getContext()).getLongPressTimeout());
    }
    private void longClickChild(int x, int y){
        Rect rect = new Rect();
        for (int i = 0; i < getChildCount(); i++) {
            View v = getChildAt(i);
            v.getHitRect(rect);
            if(rect.contains(x,y)){
                OnItemLongClickListener listener = getOnItemLongClickListener();
                if(listener!=null){
                    listener.onItemLongClick(this,v,myFirstViewPosition + i, mAdapter.getItemId(myFirstViewPosition + i));
                    mTouchState = TOUCH_STATE_RESTING;
                }
            }
        }
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
