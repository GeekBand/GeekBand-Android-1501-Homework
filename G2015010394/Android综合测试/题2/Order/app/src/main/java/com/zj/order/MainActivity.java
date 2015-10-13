package com.zj.order;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button bt0,bt1,bt2;
    private TextView tv0,tv3;
    int num=0;
    private TextView show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt0= (Button) findViewById(R.id.subtractButton);
        bt1= (Button) findViewById(R.id.addButton);
        bt2= (Button) findViewById(R.id.bt);
        tv0= (TextView) findViewById(R.id.tv0);
        tv3= (TextView) findViewById(R.id.tv3);
        show= (TextView) findViewById(R.id.show);

        bt0.setTag("+");
        bt1.setTag("-");

        setViewListener();
    }

    private void setViewListener()
    {
        bt0.setOnClickListener(new OnButtonClickListener());
        bt1.setOnClickListener(new OnButtonClickListener());
        tv0.addTextChangedListener(new OnTextChangeListener());
    }

    public void custom(View source){
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("你的选择是：")
                .setMessage("物品数量是："+tv0.getText()+"\n价格是："+tv3.getText());
                 setPositiveButton(builder);
                 setNegativeButton(builder)
                .create()
                .show();

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



    private class OnButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String numString = tv0.getText().toString();
            if (numString == null || numString.equals(""))
            {
                num = 0;
                tv0.setText("$0");
            } else
            {
                if (v.getTag().equals("-"))
                    if (++num < 0)
                    {
                        num--;
                        Toast.makeText(MainActivity.this, "已经是最小",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        tv0.setText(String.valueOf(num));
                        int i1=Integer.parseInt(String.valueOf(num))*8;
                        tv3.setText("$" + i1);
                    }
                else if (v.getTag().equals("+"))
                {
                    if (--num < 0)
                    {
                        num++;
                        Toast.makeText(MainActivity.this, "已经是最小",
                                Toast.LENGTH_SHORT).show();
                    } else
                    {
                        tv0.setText(String.valueOf(num));
                        int i1=Integer.parseInt(String.valueOf(num))*8;
                        tv3.setText("$"+i1);

                    }
                }
            }

        }
    }

    private class OnTextChangeListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String numString = s.toString();
            if(numString == null || numString.equals(""))
            {
                num = 0;
                int i1=Integer.parseInt(String.valueOf(num))*8;
                tv3.setText("$" + i1);
            }
            else {
                int numInt = Integer.parseInt(numString);
                if (numInt < 0)
                {
                    Toast.makeText(MainActivity.this, "已经是最小",
                            Toast.LENGTH_SHORT).show();
                } else
                {
                    num = numInt;
                    int i1=Integer.parseInt(String.valueOf(num))*8;
                    tv3.setText("$" + i1);

                }
            }

        }
    }

    private AlertDialog.Builder setPositiveButton(
            AlertDialog.Builder builder)
    {

        return builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
        {
            @Override
             public void onClick(DialogInterface dialog, int which)
            {
                show.setText("恭喜你，下单成功！");
            }
        });
    }
    private AlertDialog.Builder setNegativeButton(
            AlertDialog.Builder builder)
    {
        return builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                show.setText("你取消了订单！");
            }
        });
    }
}
