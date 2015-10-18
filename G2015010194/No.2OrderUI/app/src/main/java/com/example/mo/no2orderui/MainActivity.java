package com.example.mo.no2orderui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private TextView textQuantity;
    private TextView textPrice;
    private TextView textMoney;
    private TextView textNumber;
    private Button buttonJia;
    private Button buttonJian;
    private Button buttonOrder;
    private int number= 0 ;
    private int money=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonJian = (Button) findViewById(R.id.button_jian);
        buttonJia = (Button) findViewById(R.id.button_jia);
        buttonOrder = (Button) findViewById(R.id.button_order);
        textQuantity = (TextView) findViewById(R.id.quantity);
        textPrice = (TextView) findViewById(R.id.price);
        textMoney = (TextView) findViewById(R.id.money);
        textNumber = (TextView) findViewById(R.id.number);

        textNumber.setText(Integer.toString(number));
        textMoney.setText(Integer.toString(money));

        buttonJian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (number == 0) {
                    Toast.makeText(MainActivity.this, "不能再少了", Toast.LENGTH_SHORT).show();
                } else {
                    number--;
                    money = number * 10;
                    textNumber.setText(Integer.toString(number));
                    textMoney.setText("$" + Integer.toString(money));
                }
            }
        });

        buttonJia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number++;
                money = number * 10;
                textNumber.setText(Integer.toString(number));
                textMoney.setText("$" + Integer.toString(money));
            }
        });

        buttonOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("订单确认");
                dialog.setMessage("请核对:订购数量"+number+","+"总价:$"+money);
                dialog.setCancelable(false);
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.show();
            }
        });

    }
}
