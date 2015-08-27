package com.geekband.luminous.homework.Activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by hisashieki on 15/8/14.
 */
public abstract class BaseActivity extends Activity {
    protected static String TAG;
    protected Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        TAG = this.getClass().getName();
        if (getMainContentViewId() != 0) {
            setContentView(getMainContentViewId());
        }
        initView();

    }

    public abstract void initView();

    public abstract int getMainContentViewId();
}
