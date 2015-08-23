package com.geekband.luminous.homework;

import android.widget.ListView;

import com.geekband.luminous.homework.adapter.MyGridAdapter;
import com.geekband.luminous.homework.model.MyData;
import com.geekband.luminous.homework.widget.MyListViewX;

/**
 * An activity to test listView
 * Created by hisashieki on 15/8/15.
 */
public class MyListViewActivity extends BaseActivity {
    @Override
    public void initView() {
        MyListViewX lvMain = (MyListViewX) findViewById(R.id.lv_main);
        lvMain.setAdapter(new MyGridAdapter(context, MyData.picIds));
    }

    @Override
    public int getMainContentViewId() {
        return R.layout.activity_my_listview;
    }
}
