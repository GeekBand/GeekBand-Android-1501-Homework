package com.example.third;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener{
	private Button btn1,btn2,btn3,btn4,btn5,btn6,btn7;
	private TextView tv1,tv2,tv3,tv4;
	private SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
//		if (savedInstanceState!=null) {
//		String score =	(String) savedInstanceState.getString("score");
//		String score2 =	(String) savedInstanceState.getString("score2");
//		tv2.setText(score);
//		tv4.setText(score2);
//		}
		sp = getSharedPreferences("my", 0);
		String score=sp.getString("score", "0");
		String score2=sp.getString("score2", "0");
		tv2.setText(score);
		tv4.setText(score2);
		 
		  
	}

	private void init() {
		btn1=(Button) findViewById(R.id.button1); 
		btn2=(Button) findViewById(R.id.button2); 
		btn3=(Button) findViewById(R.id.button3); 
		btn4=(Button) findViewById(R.id.button4); 
		btn5=(Button) findViewById(R.id.button5); 
		btn6=(Button) findViewById(R.id.button6);
		
		btn7=(Button) findViewById(R.id.reset);
		
		tv2=(TextView) findViewById(R.id.textView2); 
		tv4=(TextView) findViewById(R.id.textView4); 
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
		btn4.setOnClickListener(this);
		btn5.setOnClickListener(this);
		btn6.setOnClickListener(this);
		btn7.setOnClickListener(this);
		
		
	}

	@Override
	public void onClick(View v) {
	String num	=tv2.getText().toString();
	int score = Integer.parseInt(num);
	String num_team2	=tv4.getText().toString();
	int score2 = Integer.parseInt(num_team2);
		switch (v.getId()) {
			case R.id.button1:
				tv2.setText(String.valueOf(score+3));
			break;	
			case R.id.button2:
				tv2.setText(String.valueOf(score+2));
				break;
			case R.id.button3:
				
				break;
			case R.id.button4:
				tv4.setText(String.valueOf(score2+3));
				break;
			case R.id.button5:
				tv4.setText(String.valueOf(score2+2));
				break;
			case R.id.button6:
				
				break;
case R.id.reset:
				tv2.setText("0");
				tv4.setText("0");
				Editor editor=sp.edit();
				editor.clear();
				break;

		default:
			break;
		}
		
	}
	@Override
	protected void onPause() {
		
		super.onPause();
		 Editor editor=sp.edit();
		 editor.putString("score", tv2.getText().toString());
		 editor.putString("score2", tv4.getText().toString());
		 editor.commit();
		
	}
//	@Override
//	protected void onSaveInstanceState(Bundle outState) {
//		// TODO Auto-generated method stub
//		super.onSaveInstanceState(outState);
//		outState.putString("score", tv2.getText().toString());
//		outState.putString("score2", tv4.getText().toString());
//		Log.e("tag", "onSaveInstanceState");
//	}
	
}
