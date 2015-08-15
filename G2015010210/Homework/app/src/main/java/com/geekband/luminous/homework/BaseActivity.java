package com.geekband.luminous.homework;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;

/**
 *
 * Created by hisashieki on 15/8/14.
 */
public abstract class BaseActivity extends Activity {
    protected Context context;
    protected static String TAG;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        TAG = this.getClass().getName();
        if(getMainContentViewId()!=0){
            setContentView(getMainContentViewId());
        }
        initView();

    }
    public abstract void initView();
    public abstract int getMainContentViewId();
}
