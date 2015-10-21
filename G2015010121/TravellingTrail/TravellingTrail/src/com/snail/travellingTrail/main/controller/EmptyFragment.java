package com.snail.travellingTrail.main.controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.snail.travellingTrail.R;

public class EmptyFragment extends Fragment 
{
	View frameView;
	ImageView defoultIv;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		frameView = inflater.inflate(R.layout.fragment_empty, null);

		return frameView;
	}

}
