package com.geekband.luminous.homework.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * a new GridView
 * Created by hisashieki on 15/8/14.
 */
public class MyGridView extends GridView {

    public MyGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        System.out.println(Integer.MAX_VALUE >> 2);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
