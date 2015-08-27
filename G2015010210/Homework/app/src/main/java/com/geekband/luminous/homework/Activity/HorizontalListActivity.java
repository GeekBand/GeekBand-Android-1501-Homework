package com.geekband.luminous.homework.Activity;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.geekband.luminous.homework.R;
import com.geekband.luminous.homework.adapter.MyGridAdapter;
import com.geekband.luminous.homework.model.MyData;
import com.geekband.luminous.homework.widget.MyHorizontalListView;

public class HorizontalListActivity extends BaseActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    MyHorizontalListView lvMain;

    @Override
    public void initView() {
        lvMain = (MyHorizontalListView) findViewById(R.id.lv_main);
        lvMain.setAdapter(new MyGridAdapter(context, MyData.picIds));
        lvMain.setOnItemClickListener(this);
        lvMain.setOnItemLongClickListener(this);
    }

    @Override
    public int getMainContentViewId() {
        return R.layout.activity_horizontal_list;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.e(TAG, "onItemClick " + position);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Log.w(TAG, "onItemLongClick "+position);
        return true;
    }
}
