package com.snail.travellingTrail.newTravel.model;


import android.support.v4.app.Fragment;
import android.text.TextUtils;

import java.util.ArrayList;

import com.snail.travellingTrail.common.wizard.model.ModelCallbacks;
import com.snail.travellingTrail.common.wizard.model.Page;
import com.snail.travellingTrail.common.wizard.model.ReviewItem;
import com.snail.travellingTrail.newTravel.controller.FillTravelIntroductionFragment;



public class FillTravelIntroductionPage extends Page {
    public static final String INTRONDUCTION_DATA_KEY = "Trvl_Introduce";

    public FillTravelIntroductionPage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
        return FillTravelIntroductionFragment.create(getKey());
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {
    	setParentKey(INTRONDUCTION_DATA_KEY);
        dest.add(new ReviewItem("旅程介绍", mData.getString(INTRONDUCTION_DATA_KEY), getKey(), -1));
    }

    @Override
    public boolean isCompleted() {
        return !TextUtils.isEmpty(mData.getString(INTRONDUCTION_DATA_KEY));
    }
}
