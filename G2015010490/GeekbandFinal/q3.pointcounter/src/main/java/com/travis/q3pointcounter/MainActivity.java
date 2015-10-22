package com.travis.q3pointcounter;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String SAVE_TEAM_A = "SavingScoresForTeamA";
    private static final String SAVE_TEAM_B = "SavingScoresForTeamB";

    private SharedPreferences mSharedPreferences;
    private int scoreTeamA;
    private int scoreTeamB;

    private TextView scoreTextTeamA;
    private TextView scoreTextTeamB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button add3PointTeamA = (Button) findViewById(R.id.plus3_teamA);
        add3PointTeamA.setOnClickListener(this);

        Button add2PointTeamA = (Button) findViewById(R.id.plus2_teamA);
        add2PointTeamA.setOnClickListener(this);

        Button add3PointTeamB = (Button) findViewById(R.id.plus3_teamB);
        add3PointTeamB.setOnClickListener(this);

        Button add2PointTeamB = (Button) findViewById(R.id.plus2_teamB);
        add2PointTeamB.setOnClickListener(this);

        Button freeThrowTeamA = (Button) findViewById(R.id.free_throw_teamA);
        freeThrowTeamA.setOnClickListener(this);

        Button freeThrowTeamB = (Button) findViewById(R.id.free_throw_teamB);
        freeThrowTeamB.setOnClickListener(this);

        Button resetButton = (Button) findViewById(R.id.reset_button);
        resetButton.setOnClickListener(this);

        scoreTextTeamA = (TextView) findViewById(R.id.teamA_score);
        scoreTextTeamB = (TextView) findViewById(R.id.teamB_score);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        scoreTeamA = mSharedPreferences.getInt(SAVE_TEAM_A, 0);
        scoreTeamB = mSharedPreferences.getInt(SAVE_TEAM_B, 0);

        scoreTextTeamA.setText(String.valueOf(scoreTeamA));
        scoreTextTeamB.setText(String.valueOf(scoreTeamB));
    }

    @Override
    protected void onPause() {

        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(SAVE_TEAM_A, scoreTeamA);
        editor.putInt(SAVE_TEAM_B, scoreTeamB);
        editor.apply();

        super.onPause();
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case (R.id.plus3_teamA):
                scoreTeamA += 3;
                break;
            case (R.id.plus3_teamB):
                scoreTeamB += 3;
                break;
            case (R.id.plus2_teamA):
                scoreTeamA += 2;
                break;
            case (R.id.plus2_teamB):
                scoreTeamB += 2;
                break;
            case (R.id.reset_button):
                scoreTeamA = 0;
                scoreTeamB = 0;
                break;
            case (R.id.free_throw_teamA):
            case (R.id.free_throw_teamB):
            default:
                return;
        }

        scoreTextTeamA.setText(String.valueOf(scoreTeamA));
        scoreTextTeamB.setText(String.valueOf(scoreTeamB));
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
}
