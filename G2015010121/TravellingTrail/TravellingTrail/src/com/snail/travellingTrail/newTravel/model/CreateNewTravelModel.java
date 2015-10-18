package com.snail.travellingTrail.newTravel.model;

import android.content.Context;

import com.snail.travellingTrail.common.wizard.model.AbstractWizardModel;
import com.snail.travellingTrail.common.wizard.model.PageList;

public class CreateNewTravelModel extends AbstractWizardModel
{
	public CreateNewTravelModel(Context context)
	{
		super(context);
	}

	@Override
	protected PageList onNewRootPageList()
	{
		return new PageList(
				new FillTravelNamePage(this, "旅程名称").setRequired(true),
				new FillTravelIntroductionPage(this, "旅程介绍/期待").setRequired(true),
				new FillStartCityPage(this, "出发地/城市").setRequired(true),
				new ChooseStartDatePage(this, "旅程开始日期").setRequired(true)
		);
	}
}
