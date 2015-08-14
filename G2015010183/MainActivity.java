package com.example.lucaschen.horizontallistview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    LinearLayoutManager manager;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(manager);
        adapter = new RecyclerViewAdapter(new String[]{
                "This is test-text No.0, implemented by android.support.v7.widget.RecyclerView.",
                "This is test-text No.1, implemented by android.support.v7.widget.RecyclerView.",
                "This is test-text No.2, implemented by android.support.v7.widget.RecyclerView.",
                "This is test-text No.3, implemented by android.support.v7.widget.RecyclerView.",
                "This is test-text No.4, implemented by android.support.v7.widget.RecyclerView.",
                "This is test-text No.5, implemented by android.support.v7.widget.RecyclerView.",
                "This is test-text No.6, implemented by android.support.v7.widget.RecyclerView.",
                "This is test-text No.7, implemented by android.support.v7.widget.RecyclerView.",
                "This is test-text No.8, implemented by android.support.v7.widget.RecyclerView.",
                "This is test-text No.9, implemented by android.support.v7.widget.RecyclerView.",
                "This is test-text No.10, implemented by android.support.v7.widget.RecyclerView.",
        });
        recyclerView.setAdapter(adapter);
    }
}
