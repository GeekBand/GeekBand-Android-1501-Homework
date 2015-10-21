package com.snail.travellingTrail.newTravel.controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DownloadManager.Request;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;

import org.apache.http.client.methods.HttpHead;
import org.apache.http.entity.StringEntity;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import com.google.gson.Gson;
import com.snail.travellingTrail.R;
import com.snail.travellingTrail.common.RequestAddress;
import com.snail.travellingTrail.common.TravellingTrailApplication;
import com.snail.travellingTrail.common.utils.ToastHelper;
import com.snail.travellingTrail.common.wizard.model.AbstractWizardModel;
import com.snail.travellingTrail.common.wizard.model.ModelCallbacks;
import com.snail.travellingTrail.common.wizard.model.Page;
import com.snail.travellingTrail.common.wizard.model.ReviewItem;
import com.snail.travellingTrail.common.wizard.ui.PageFragmentCallbacks;
import com.snail.travellingTrail.common.wizard.ui.ReviewFragment;
import com.snail.travellingTrail.common.wizard.ui.StepPagerStrip;
import com.snail.travellingTrail.newTravel.model.CreateNewTravelModel;

public class CreateNewTravelActivity extends FragmentActivity implements
		PageFragmentCallbacks, ReviewFragment.Callbacks, ModelCallbacks, 
		OnClickListener
{
	private ViewPager mPager;
	private MyPagerAdapter mPagerAdapter;

	private boolean mEditingAfterReview;

	private AbstractWizardModel mWizardModel = new CreateNewTravelModel(this);

	private boolean mConsumePageSelectedEvent;

	private Button mNextButton;
	private Button mPrevButton;

	private List<Page> mCurrentPageSequence;
	private StepPagerStrip mStepPagerStrip;

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_new_travel);

		if (savedInstanceState != null)
		{
			mWizardModel.load(savedInstanceState.getBundle("model"));
		}

		mWizardModel.registerListener(this);

		mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setAdapter(mPagerAdapter);
		mStepPagerStrip = (StepPagerStrip) findViewById(R.id.strip);
		mStepPagerStrip
				.setOnPageSelectedListener(new StepPagerStrip.OnPageSelectedListener()
				{
					@Override
					public void onPageStripSelected(int position)
					{
						position = Math.min(mPagerAdapter.getCount() - 1,
								position);
						if (mPager.getCurrentItem() != position)
						{
							mPager.setCurrentItem(position);
						}
					}
				});

		mNextButton = (Button) findViewById(R.id.next_button);
		mPrevButton = (Button) findViewById(R.id.prev_button);

		mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener()
		{
			@Override
			public void onPageSelected(int position)
			{
				mStepPagerStrip.setCurrentPage(position);

				if (mConsumePageSelectedEvent)
				{
					mConsumePageSelectedEvent = false;
					return;
				}

				mEditingAfterReview = false;
				updateBottomBar();
			}
		});

		mNextButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				if (mPager.getCurrentItem() == mCurrentPageSequence.size())
				{
					DialogFragment dg = new DialogFragment()
					{
						@Override
						public Dialog onCreateDialog(Bundle savedInstanceState)
						{
							return new AlertDialog.Builder(getActivity())
									.setMessage(R.string.submit_confirm_message)
									.setPositiveButton(
											R.string.submit_confirm_button,
											CreateNewTravelActivity.this)
									.setNegativeButton(android.R.string.cancel,
											CreateNewTravelActivity.this).create();
						}
					};
					dg.show(getSupportFragmentManager(), "提交");
				} else
				{
					if (mEditingAfterReview)
					{
						mPager.setCurrentItem(mPagerAdapter.getCount() - 1);
					} else
					{
						mPager.setCurrentItem(mPager.getCurrentItem() + 1);
					}
				}
			}
		});

		mPrevButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				mPager.setCurrentItem(mPager.getCurrentItem() - 1);
			}
		});

		onPageTreeChanged();
		updateBottomBar();
	}

	@Override
	public void onPageTreeChanged()
	{
		mCurrentPageSequence = mWizardModel.getCurrentPageSequence();
		recalculateCutOffPage();
		mStepPagerStrip.setPageCount(mCurrentPageSequence.size() + 1); // + 1 =
																		// review
																		// step
		mPagerAdapter.notifyDataSetChanged();
		updateBottomBar();
	}

	private void updateBottomBar()
	{
		int position = mPager.getCurrentItem();
		if (position == mCurrentPageSequence.size())
		{
			mNextButton.setText(R.string.finish);
			mNextButton.setBackgroundResource(R.drawable.finish_background);
		} else
		{
			mNextButton.setText(mEditingAfterReview ? R.string.review
					: R.string.next);
			mNextButton
					.setBackgroundResource(R.drawable.selectable_item_background);
			TypedValue v = new TypedValue();
			getTheme().resolveAttribute(android.R.attr.textAppearanceMedium, v,
					true);
			mNextButton.setTextAppearance(this, v.resourceId);
			mNextButton.setEnabled(position != mPagerAdapter.getCutOffPage());
		}

		mPrevButton
				.setVisibility(position <= 0 ? View.INVISIBLE : View.VISIBLE);
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		mWizardModel.unregisterListener(this);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		outState.putBundle("model", mWizardModel.save());
	}

	@Override
	public AbstractWizardModel onGetModel()
	{
		return mWizardModel;
	}

	@Override
	public void onEditScreenAfterReview(String key)
	{
		for (int i = mCurrentPageSequence.size() - 1; i >= 0; i--)
		{
			if (mCurrentPageSequence.get(i).getKey().equals(key))
			{
				mConsumePageSelectedEvent = true;
				mEditingAfterReview = true;
				mPager.setCurrentItem(i);
				updateBottomBar();
				break;
			}
		}
	}

	@Override
	public void onPageDataChanged(Page page)
	{
		if (page.isRequired())
		{
			if (recalculateCutOffPage())
			{
				mPagerAdapter.notifyDataSetChanged();
				updateBottomBar();
			}
		}
	}

	@Override
	public Page onGetPage(String key)
	{
		return mWizardModel.findByKey(key);
	}

	private boolean recalculateCutOffPage()
	{
		int cutOffPage = mCurrentPageSequence.size() + 1;
		for (int i = 0; i < mCurrentPageSequence.size(); i++)
		{
			Page page = mCurrentPageSequence.get(i);
			if (page.isRequired() && !page.isCompleted())
			{
				cutOffPage = i;
				break;
			}
		}

		if (mPagerAdapter.getCutOffPage() != cutOffPage)
		{
			mPagerAdapter.setCutOffPage(cutOffPage);
			return true;
		}

		return false;
	}

	public class MyPagerAdapter extends FragmentStatePagerAdapter
	{
		private int mCutOffPage;
		private Fragment mPrimaryItem;
		private ReviewFragment reviewFragment; 

		public MyPagerAdapter(FragmentManager fm)
		{
			super(fm);
		}

		@Override
		public Fragment getItem(int i)
		{
			if (i >= mCurrentPageSequence.size())
			{
				reviewFragment = new ReviewFragment();
				return reviewFragment;
			}

			return mCurrentPageSequence.get(i).createFragment();
		}
		
		public ReviewFragment getReviewFragment()
		{
			return reviewFragment;
		}

		@Override
		public int getItemPosition(Object object)
		{
			if (object == mPrimaryItem)
			{
				return POSITION_UNCHANGED;
			}

			return POSITION_NONE;
		}

		@Override
		public void setPrimaryItem(ViewGroup container, int position,
				Object object)
		{
			super.setPrimaryItem(container, position, object);
			mPrimaryItem = (Fragment) object;
		}

		@Override
		public int getCount()
		{
			if (mCurrentPageSequence == null)
			{
				return 0;
			}
			return Math.min(mCutOffPage + 1, mCurrentPageSequence.size() + 1);
		}

		public void setCutOffPage(int cutOffPage)
		{
			if (cutOffPage < 0)
			{
				cutOffPage = Integer.MAX_VALUE;
			}
			mCutOffPage = cutOffPage;
		}

		public int getCutOffPage()
		{
			return mCutOffPage;
		}
	}

	@Override
	public void onClick(DialogInterface dialog, int which)
	{
		switch (which)
		{
		case Dialog.BUTTON_POSITIVE:
			ToastHelper.showToast(CreateNewTravelActivity.this, "创建旅程中...", Toast.LENGTH_SHORT);
			createTravel();
			break;

		default:
			break;
		}
	}
	
	private void createTravel()
	{
		FinalHttp finalHttp = new FinalHttp();
		ReviewFragment reviewFragment = mPagerAdapter.getReviewFragment();
		List<ReviewItem> reviewItems = reviewFragment.getReviewItems();
		HashMap<String, String> hashMap = new HashMap<String, String>();
		for (ReviewItem reviewItem : reviewItems)
		{
			hashMap.put(reviewItem.getPageKey(), reviewItem.getDisplayValue());
		}
		hashMap.put("Trvl_Us_Id", String.valueOf(TravellingTrailApplication.loginUser.getUs_Info_Us_Id()));
		Gson gson = new Gson();
		StringEntity jsonEntity;
		try
		{
			String jsonString = gson.toJson(hashMap);
			jsonString = new String(jsonString.getBytes(), "8859_1");
			jsonEntity = new StringEntity(jsonString);
			Log.v("createTravel---->jsonString", jsonString);
			finalHttp.post(RequestAddress.CREATE_NEW_TRAVEL, jsonEntity, "application/json",
					new AjaxCallBack<String>()
					{

						@Override
						public void onSuccess(String t)
						{
							ToastHelper.showToast(CreateNewTravelActivity.this, "创建成功！", Toast.LENGTH_SHORT);
							finish();
						}

						@Override
						public void onFailure(Throwable t, int errorNo,
								String strMsg)
						{
							ToastHelper.showToast(
									CreateNewTravelActivity.this,
									"创建失败！错误代码：" + errorNo +  "；错误信息：" + strMsg,
									Toast.LENGTH_SHORT);
						}
						
					}
			);
		} catch (UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
