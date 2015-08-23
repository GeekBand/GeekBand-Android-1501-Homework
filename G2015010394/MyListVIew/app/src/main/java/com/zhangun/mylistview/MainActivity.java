package com.zhangun.mylistview;

import android.app.Activity;
import android.os.Bundle;

import com.zhangun.mylistview.adapter.Myadapter;
import com.zhangun.mylistview.view.HListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends Activity {
    private HListView listView ;
    private Myadapter mAdapter ;

    private List<Map<String, Object>> list ;
    private Map<String,Object> map ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView() {
        listView = (HListView) findViewById(R.id.listView);

        list = this.getData();

        mAdapter = new Myadapter(MainActivity.this, list);
        listView.setAdapter(mAdapter);
    }

    private List<Map<String, Object>> getData(){

        int [] pic = {R.drawable.b1,R.drawable.b2,R.drawable.b3,R.drawable.b4,R.drawable.b5,
                R.drawable.b6,R.drawable.b7,R.drawable.b8,R.drawable.b9,R.drawable.b10};

        list = new ArrayList<Map<String,Object>>();

        for(int i = 0;i<pic.length;i++){
            map =new HashMap<String, Object>();
            map.put("index", "第"+(i+1)+"张");
            map.put("img", pic[i]);
            list.add(map);
        }
        return list;

    }
}

