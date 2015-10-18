package com.snail.travellingTrail.newTravel.model;


import android.support.v4.app.Fragment;
import android.text.TextUtils;

import java.util.ArrayList;

import com.snail.travellingTrail.common.wizard.model.ModelCallbacks;
import com.snail.travellingTrail.common.wizard.model.Page;
import com.snail.travellingTrail.common.wizard.model.ReviewItem;
import com.snail.travellingTrail.newTravel.controller.ChooseStartDateFragment;


public class ChooseStartDatePage extends Page {
    public static final String START_DATE_DATA_KEY = "Trvl_Time_Start";

    public ChooseStartDatePage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
        return ChooseStartDateFragment.create(getKey());
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {
    	setParentKey(START_DATE_DATA_KEY);
        dest.add(new ReviewItem("开始日期", mData.getString(START_DATE_DATA_KEY), getKey(), -1));
    }

    @Override
    public boolean isCompleted() {
        return !TextUtils.isEmpty(mData.getString(START_DATE_DATA_KEY));
    }
}
