package com.geekband.luminous.homework;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

import com.geekband.luminous.homework.adapter.MyGridAdapter;
import com.geekband.luminous.homework.model.MyData;

public class HorizontalListActivity extends BaseActivity {
    GridView gvMain,gv2;
    ListView lv1;
    @Override
    public void initView() {
        gv2 = (GridView) findViewById(R.id.gv_2);
        gv2.setAdapter(new MyGridAdapter(context, MyData.picIds));
    }

    @Override
    public int getMainContentViewId() {
        return R.layout.activity_horizontal_list;
    }

}
