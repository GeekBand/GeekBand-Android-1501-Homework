package com.example.mo.no3counter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView textViewA;
    private TextView textViewB;
    private TextView scoreA;
    private TextView scoreB;
    private Button buttonA3;
    private Button buttonA2;
    private Button buttonA0;
    private Button buttonB3;
    private Button buttonB2;
    private Button buttonB0;
    private Button buttonReset;
    private View viewLine;
    private int scoreTeamA = 0;
    private int scoreTeamB = 0;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
        editor.putInt("A",scoreTeamA);
        editor.putInt("B",scoreTeamB);
        editor.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
        scoreTeamA = pref.getInt("A",0);
        scoreTeamB = pref.getInt("B",0);


        textViewA = (TextView) findViewById(R.id.textView_a);
        textViewB = (TextView) findViewById(R.id.textView_b);
        scoreA = (TextView) findViewById(R.id.score_a);
        scoreB = (TextView) findViewById(R.id.score_b);
        buttonA3 = (Button) findViewById(R.id.button_a3);
        buttonA2 = (Button) findViewById(R.id.button_a2);
        buttonA0 = (Button) findViewById(R.id.button_a0);
        buttonB3 = (Button) findViewById(R.id.button_b3);
        buttonB2 = (Button) findViewById(R.id.button_b2);
        buttonB0 = (Button) findViewById(R.id.button_b0);
        buttonReset = (Button) findViewById(R.id.button_reset);

        scoreA.setText(Integer.toString(scoreTeamA));
        scoreB.setText(Integer.toString(scoreTeamB));

        buttonA3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreTeamA = scoreTeamA+3;
                scoreA.setText(Integer.toString(scoreTeamA));
            }
        });

        buttonA2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreTeamA = scoreTeamA+2;
                scoreA.setText(Integer.toString(scoreTeamA));
            }
        });

        buttonA0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Free Throw", Toast.LENGTH_SHORT).show();
            }
        });

        buttonB3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreTeamB = scoreTeamB + 3;
                scoreB.setText(Integer.toString(scoreTeamB));
            }
        });

        buttonB2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreTeamB = scoreTeamB + 2;
                scoreB.setText(Integer.toString(scoreTeamB));
            }
        });

        buttonB0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Free Throw", Toast.LENGTH_SHORT).show();
            }
        });

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreTeamA = 0;
                scoreTeamB = 0;
                scoreB.setText(Integer.toString(scoreTeamB));
                scoreA.setText(Integer.toString(scoreTeamA));
            }
        });


    }
}
