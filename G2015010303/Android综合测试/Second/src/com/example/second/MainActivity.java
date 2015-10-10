package com.example.second;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity implements OnClickListener{
	private Button btn1,btn2,btn3;
	private TextView tv1,tv2,tv3,tv4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        
    }

   
	private void findView() {
		btn1 = (Button) findViewById(R.id.button1);
		btn2 = (Button) findViewById(R.id.button2);
		btn3 = (Button) findViewById(R.id.order);
		tv2= (TextView) findViewById(R.id.textView2);
		tv4= (TextView) findViewById(R.id.textView4);
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
		
	}
	

	@Override
	public void onClick(View v) {
		String quantity	=tv2.getText().toString();
		switch (v.getId()) {
		case R.id.button1:
		    int num =Integer.parseInt(quantity);
		    if (num>0) {
				tv2.setText(String.valueOf(--num));
				tv4.setText("$"+String.valueOf(num*5));
			}
			break;
		case R.id.button2:
		    int plus =Integer.parseInt(quantity);
		    
				tv2.setText(String.valueOf(++plus));
				tv4.setText("$"+String.valueOf(plus*5));
			break;
		case R.id.order:
			new AlertDialog.Builder(this).setTitle("系统提示").setMessage("您选择了"+tv2.getText().toString()+"件共"+tv4.getText().toString()+"确定吗？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			}).show();
			
			break;

		default:
			break;
		}
		
	}
}
