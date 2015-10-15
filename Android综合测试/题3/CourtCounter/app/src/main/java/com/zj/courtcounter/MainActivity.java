package com.zj.courtcounter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btna3,btnb3,btna2,btnb2,btna1,btnb1,btnrest;
    private TextView aCounter,bCounter;
    private static final String TAG = "zhang";
    private String mString1,mString2;
    private String value="default";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState!=null){
            value=savedInstanceState.getString("value");
        }

        aCounter= (TextView) findViewById(R.id.aCounter);
        bCounter= (TextView) findViewById(R.id.bCounter);
        btna1= (Button) findViewById(R.id.a1Points);
        btnb1= (Button) findViewById(R.id.b1Points);
        btna2= (Button) findViewById(R.id.a2Points);
        btnb2= (Button) findViewById(R.id.b2Points);
        btna3= (Button) findViewById(R.id.a3Points);
        btnb3= (Button) findViewById(R.id.b3Points);
        btnrest= (Button) findViewById(R.id.resetButton);
        btnrest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aCounter.setText("0");
                bCounter.setText("0");
            }
        });

        btna1.setOnClickListener(this);
        btnb1.setOnClickListener(this);
        btna2.setOnClickListener(this);
        btnb2.setOnClickListener(this);
        btna3.setOnClickListener(this);
        btnb3.setOnClickListener(this);
        Log.d(TAG, "onCreate() returned: " + "++");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() returned: " + "++");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() returned: " + "++");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart() returned: " + "++");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() returned: " + "++");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() returned: " + "++");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() returned: " + "++");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("value",value);
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
        String numString1=aCounter.getText().toString();
        String numString2=bCounter.getText().toString();
        if (v == btna1){
            numString1=String.valueOf((Integer.parseInt(numString1) + 1));
            aCounter.setText(numString1);
        }else if (v==btnb1){
            numString2=String.valueOf((Integer.parseInt(numString2) + 1));
            bCounter.setText(numString2);
        }else if (v==btna2){
            numString1=String.valueOf((Integer.parseInt(numString1) + 2));
            aCounter.setText(numString1);
        }else if (v==btnb2){
            numString2=String.valueOf((Integer.parseInt(numString2) + 2));
            bCounter.setText(numString2);
        }else if (v==btna3){
            numString1=String.valueOf((Integer.parseInt(numString1) + 3));
            aCounter.setText(numString1);
        }else if (v==btnb3){
            numString2=String.valueOf((Integer.parseInt(numString2) + 3));
            bCounter.setText(numString2);
        }
    }

}
