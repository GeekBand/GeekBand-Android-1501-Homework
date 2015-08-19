package com.geekband.luminous.homework.Activity;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.geekband.luminous.homework.R;
import com.geekband.luminous.homework.adapter.MyGridAdapter;
import com.geekband.luminous.homework.model.MyData;
import com.geekband.luminous.homework.widget.MyListView;
import com.geekband.luminous.homework.widget.MyListViewX;

/**
 * An activity to test listView
 * Created by hisashieki on 15/8/15.
 */
public class MyListViewActivity extends BaseActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    @Override
    public void initView() {
        MyListView lvMain = (MyListView) findViewById(R.id.lv_main);
        lvMain.setAdapter(new MyGridAdapter(context, MyData.picIds));
        lvMain.setOnItemClickListener(this);
        lvMain.setOnItemLongClickListener(this);
    }

    @Override
    public int getMainContentViewId() {
        return R.layout.activity_my_listview;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.e(TAG, "onItemClick " + position);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(MyListViewActivity.this, "longClick"+position, Toast.LENGTH_SHORT).show();
        return true;
    }
}
