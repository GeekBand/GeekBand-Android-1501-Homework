package com.geekband.luminous.homework.Activity;

import com.geekband.luminous.homework.R;
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
