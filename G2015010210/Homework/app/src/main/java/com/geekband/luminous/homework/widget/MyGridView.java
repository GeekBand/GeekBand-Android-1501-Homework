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

        System.out.println("");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);

        super.onMeasure(widthMeasureSpec, expandSpec);

        System.out.println();
    }
}
