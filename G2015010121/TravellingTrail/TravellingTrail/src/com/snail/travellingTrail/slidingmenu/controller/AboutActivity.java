package com.snail.travellingTrail.slidingmenu.controller;

import net.tsz.afinal.FinalHttp;
import android.content.Context;
import android.os.Bundle;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.snail.travellingTrail.R;

public class AboutActivity extends SherlockActivity{
	ActionBar actionBar;
	MenuItem menuItem;
	Context context = this;
	
	FinalHttp mFinalHttp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_about);
		findId();
		super.onCreate(savedInstanceState);
	}



	private void findId() {
		
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		actionBar = getSupportActionBar();
		actionBar.setIcon(R.drawable.mistake);
		actionBar.setTitle("ABOUT");
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(false);
		actionBar.setDisplayShowHomeEnabled(true);
		
		return super.onCreateOptionsMenu(menu);
	}



	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
