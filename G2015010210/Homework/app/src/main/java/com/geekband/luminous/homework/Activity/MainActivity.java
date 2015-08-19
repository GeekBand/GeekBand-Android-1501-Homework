package com.geekband.luminous.homework.Activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.geekband.luminous.homework.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    ListView lvMain;
    List<String> homeWorks = new ArrayList<>();

    @Override
    public void initView() {
        lvMain = (ListView) findViewById(R.id.lv_main);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, homeWorks);
        lvMain.setAdapter(adapter);
        lvMain.setOnItemClickListener(this);
        initList();
    }
    public void initList(){
        homeWorks.add("Horizontal ListView");
        homeWorks.add("MyListView");

    }
    @Override
    public int getMainContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        switch (position){
            case 0:
                intent.setClass(context,HorizontalListActivity.class);
                startActivity(intent);
                break;
            case 1:
                intent.setClass(context,MyListViewActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
