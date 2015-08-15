package com.geekband.luminous.homework;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

import com.geekband.luminous.homework.adapter.MyGridAdapter;
import com.geekband.luminous.homework.model.MyData;
import com.geekband.luminous.homework.widget.MyHorizontalListView;

public class HorizontalListActivity extends BaseActivity {
    MyHorizontalListView lvMain;
    @Override
    public void initView() {
        lvMain = (MyHorizontalListView) findViewById(R.id.lv_main);
        lvMain.setAdapter(new MyGridAdapter(context, MyData.picIds));
    }

    @Override
    public int getMainContentViewId() {
        return R.layout.activity_horizontal_list;
    }

}
