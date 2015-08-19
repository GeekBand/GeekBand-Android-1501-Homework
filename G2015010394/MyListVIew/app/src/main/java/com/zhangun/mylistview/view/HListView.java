package com.zhangun.mylistview.view;

/**
 * Created by Administrator on 2015/8/19.
 */
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;


public class HListView extends ViewGroup{

    private ListAdapter listAdapter;
    private int index = 0;

    public HListView(Context context) {
        super(context);
    }

    public HListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public HListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }



    public ListAdapter getAdapter() {
        return listAdapter;
    }


    public void setAdapter(ListAdapter adapter) {
        this.listAdapter = adapter;
    }


    private int leftSrcollDistance = -500;

    public boolean onTouchEvent(MotionEvent event) {
        int eventAction = event.getAction();
        switch (eventAction) {
            case MotionEvent.ACTION_DOWN:
                requestLayout();
                break;

        }

        return true;
    }


    private View measureChild(View view) {
        LayoutParams params = view.getLayoutParams();
        if(params==null) {
            params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(params);
        }

        view.measure(MeasureSpec.makeMeasureSpec(getWidth(), MeasureSpec.AT_MOST), MeasureSpec.makeMeasureSpec(getHeight(),
                MeasureSpec.AT_MOST));
        return view;

    }

    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {


        if(listAdapter==null) {
            return;
        }




        View firtVisiableView = getChildAt(0);
        if(firtVisiableView!=null&&leftSrcollDistance+firtVisiableView.getRight()<=0) {
            removeView(firtVisiableView);
        }


        View rightChildView = getChildAt(getChildCount()-1);

        int rightEdge = rightChildView!=null? rightChildView.getRight():0;
        while(rightEdge+leftSrcollDistance<getWidth()&&index<listAdapter.getCount()) {
            View child = listAdapter.getView(index, null, null);
            child = measureChild(child);
            addView(child);
            rightEdge += child.getMeasuredWidth();
            index++;
        }

        Log.e("HListView", "getChildCount=="+getChildCount());


        int childLeft = 0;
        for(int i=0;i<getChildCount();i++) {
            View child = getChildAt(i);
            int childWidth = child.getMeasuredWidth();
            child.layout(childLeft, 0, childWidth+childLeft, child.getMeasuredHeight());

            childLeft +=  childWidth+child.getPaddingRight();
        }

    }
}

