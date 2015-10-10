package com.example.forth;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity {
	private ListView lv;
	private EditText et;
	private Button update;
	private ArrayAdapter<Object>  adapter;
	private List<Object> lists;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		SQLiteOpenHelper dbHelper=new MySqlite(this, "zooOpenHelper.db", null, 1);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		init();
	}

	private void init() {
		lv= (ListView) findViewById(R.id.lv);
		et= (EditText) findViewById(R.id.et);
		update= (Button) findViewById(R.id.update);
		lists = new ArrayList<Object>();
		lists.add("Ash");
		lists.add("Ted");
		lists.add("Jimo");
		lists.add("Jack");
		adapter = new ArrayAdapter<Object>(this, android.R.layout.simple_list_item_1, lists);
		lv.setAdapter(adapter);
		
		
		
		update.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			 String ett=et.getText().toString();
				if (!et.equals("")) {
					lists.add(ett);
					adapter.notifyDataSetChanged();
				}
			
			}
		});
	}
	
	
}
