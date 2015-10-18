package com.example.mo.no4listview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private Button buttonUpdate;
    private TextView textView;
    private ImageView imageView;
    private EditText input;
    private ListView listView;
    private List<String> name = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name.add("Jack");
        name.add("Ash");
        name.add("Jimbo");
        name.add("Simon");
        name.add("Darryl");
        name.add("Micheal");
        name.add("Backham");

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,name);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        buttonUpdate = (Button) findViewById(R.id.button_update);
        textView = (TextView) findViewById(R.id.textView);
        imageView = (ImageView)findViewById(R.id.image);
        input = (EditText) findViewById(R.id.input);
        input.setSelection(input.length());

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = input.getText().toString();
                name.add(temp);
                adapter.notifyDataSetChanged();
                textView.setText(temp+" is learning Android development!");
                input.setText("");
            }
        });



    }
}
