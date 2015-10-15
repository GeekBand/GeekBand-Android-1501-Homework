package com.zj.omgandroid;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button update;
    private ListView show;
    private MyShowAdapter madapter;
    private List mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        madapter=new MyShowAdapter(this);
        show.setAdapter(madapter);
    }

    private void init() {
        update=(Button)findViewById(R.id.update_button);
        show= (ListView) findViewById(R.id.lv_show);
        update.setOnClickListener(this);
   }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        madapter.mList.add("1234");
        madapter.notifyDataSetChanged();
    }

    public class MyShowAdapter extends BaseAdapter {
        private ArrayList<String> mList;
        private LayoutInflater inflater;
        private Context mContext;
        public MyShowAdapter(Context mContext){
            super();
            this.mContext=  mContext;
            inflater=LayoutInflater.from(mContext);
            mList=new ArrayList();
//            for (int i=0;i<5;i++){
//                mList.add("1234");
//            }
        }
        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView==null){
                convertView=inflater.inflate(R.layout.list_item,null);
            }
            EditText edit= (EditText)findViewById(R.id.et_show);
            TextView text=(TextView)convertView.findViewById(R.id.textView1);
            text.setText("***第"+(position+1)+"个***"+edit.getText()+"￥");
            return convertView;
        }
    }
}
