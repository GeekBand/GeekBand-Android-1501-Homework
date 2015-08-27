package com.homework.hubertliu.horizontallistview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);

        mLayoutManager.setOrientation(LinearLayout.HORIZONTAL);

        mRecyclerView.setLayoutManager(mLayoutManager);

        String[] data = new String[20];
        for (int i = 0; i < data.length; i++) {
            data[i] = "Item: " + i;
        }

        RecyclerView.Adapter mAdapter = new MyAdapter(data);
        mRecyclerView.setAdapter(mAdapter);
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        private String[] data;
        public MyAdapter(String[] dataset) {
            super();
            data = dataset;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

            View view = View.inflate(getApplication(),
                    android.R.layout.simple_list_item_1, null);

            ViewHolder holder = new ViewHolder(view);
            holder.mTextView = (TextView) view.findViewById(android.R.id.text1);
            holder.mTextView.setTextColor(Color.RED);
            holder.mTextView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplication(), ((TextView) v).getText(),
                            Toast.LENGTH_SHORT).show();
                }
            });

            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            viewHolder.mTextView.setText(data[i]);
        }

        @Override
        public int getItemCount() {
            return data.length;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView mTextView;
            public ViewHolder(View itemView) {
                super(itemView);
            }
        }
    }
}