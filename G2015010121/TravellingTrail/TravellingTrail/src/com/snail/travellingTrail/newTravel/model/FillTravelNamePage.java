
package com.snail.travellingTrail.newTravel.model;


import android.support.v4.app.Fragment;
import android.text.TextUtils;

import java.util.ArrayList;

import com.snail.travellingTrail.common.wizard.model.ModelCallbacks;
import com.snail.travellingTrail.common.wizard.model.Page;
import com.snail.travellingTrail.common.wizard.model.ReviewItem;
import com.snail.travellingTrail.newTravel.controller.FillTravelNameFragment;


public class FillTravelNamePage extends Page {
    public static final String NAME_DATA_KEY = "Trvl_Name";

    public FillTravelNamePage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
        return FillTravelNameFragment.create(getKey());
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {
    	setParentKey(NAME_DATA_KEY);
        dest.add(new ReviewItem("旅程名称", mData.getString(NAME_DATA_KEY), getKey(), -1));
    }

    @Override
    public boolean isCompleted() {
        return !TextUtils.isEmpty(mData.getString(NAME_DATA_KEY));
    }
}
