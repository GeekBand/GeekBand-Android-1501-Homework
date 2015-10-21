
package com.snail.travellingTrail.newTravel.controller;


import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

import com.snail.travellingTrail.R;
import com.snail.travellingTrail.common.views.WheelView;
import com.snail.travellingTrail.common.views.WheelView.OnWheelViewListener;
import com.snail.travellingTrail.common.wizard.ui.PageFragmentCallbacks;
import com.snail.travellingTrail.newTravel.model.ChooseStartDatePage;
import com.snail.travellingTrail.newTravel.model.StartDate;
import com.snail.travellingTrail.slidingmenu.view.ScrollerNumberPicker;
import com.snail.travellingTrail.slidingmenu.view.ScrollerNumberPicker.OnSelectListener;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ChooseStartDateFragment extends Fragment {//implements OnSelectListener  {
    private static final String ARG_KEY = "ChooseStartDatePage";
    
    private static final int YEAR = 0;
    private static final int MONTH = 1;
    private static final int DAY = 3;

    private PageFragmentCallbacks mCallbacks;
    private String mKey;
    private ChooseStartDatePage mPage;
    private WheelView mYearView, mMonthView, mDayView;
    String year, month, day;
    
	ArrayList<String> yearList, monthList, dayList;

    public static ChooseStartDateFragment create(String key) {
        Bundle args = new Bundle();
        args.putString(ARG_KEY, key);

        ChooseStartDateFragment fragment = new ChooseStartDateFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ChooseStartDateFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mKey = args.getString(ARG_KEY);
        mPage = (ChooseStartDatePage) mCallbacks.onGetPage(mKey);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_new_travel_choose_date, container, false);
        ((TextView) rootView.findViewById(android.R.id.title)).setText(mPage.getTitle());

        mYearView = ((WheelView) rootView.findViewById(R.id.frag_choose_start_date_wv_year));
        mMonthView = ((WheelView) rootView.findViewById(R.id.frag_choose_start_date_wv_month));
        mDayView = ((WheelView) rootView.findViewById(R.id.frag_choose_start_date_wv_day));

	    setPickerData();
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (!(activity instanceof PageFragmentCallbacks)) {
            throw new ClassCastException("Activity must implement PageFragmentCallbacks");
        }

        mCallbacks = (PageFragmentCallbacks) activity;

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }
   

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
//        mYearView.setOnSelectListener(this);
//        mMonthView.setOnSelectListener(this);
//        mDayView.setOnSelectListener(this);


        
//		mPage.getData().putString(ChooseStartDatePage.START_DATE_DATA_KEY,
//				mYearView.getSeletedItem() + "-" + mMonthView.getSeletedItem() 
//				+ "-" + mDayView.getSeletedItem());
//	    mPage.notifyDataChanged();
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);

    }
//
//	@Override
//	public void endSelect(int viewId, int id, String text)
//	{
//		switch (viewId) {
//		case R.id.frag_choose_start_date_np_year:
//			if(isLeapYear(Integer.valueOf(text))){
//				if(StartDate.Month.equals("2")){
//					mDayView.setData(getData(DAY, 1));
//					mDayView.setDefault(6);
//				}else{
//					setDefultDay(StartDate.Month);
//				}
//			}else{
//				if(StartDate.Month.equals("2")){
//					mDayView.setData(getData(DAY, 0));
//					mDayView.setDefault(6);
//				}else{
//					setDefultDay(StartDate.Month);
//				}
//			}
//			StartDate.Year = text;
//			break;
//		case R.id.frag_choose_start_date_np_month:
//			if(isLeapYear(Integer.valueOf(StartDate.Year))){
//				if(text.equals("2")){
//					mDayView.setData(getData(DAY, 1));
//					mDayView.setDefault(6);
//				}else{
//					setDefultDay(text);
//				}
//			}else{ 
//				if(text.equals("2")){
//					mDayView.setData(getData(DAY, 0));
//					mDayView.setDefault(6);
//				}else{
//					setDefultDay(text);
//				}
//			}
//			StartDate.Month = text;
//			break;
//		case R.id.frag_choose_start_date_np_day:
//			StartDate.Day = mDayView.getSelectedText();
//			break;
//		default:
//			break;
//		}
//		StartDate.Year = mYearView.getSelectedText();
//		StartDate.Month = mMonthView.getSelectedText();
//		StartDate.Day = mDayView.getSelectedText();
//		Message msg = new Message();
//		handler.sendMessage(msg);
//	}
//	
//	
//	@Override
//	public void selecting(int viewId, int id, String text)
//	{
//		// TODO Auto-generated method stub
//		
//	}
//	
//	
	private void setPickerData(){
		
//		mYearView.setData(getData(YEAR, 0));
//		mMonthView.setData(getData(MONTH, 0));
//		mDayView.setData(getData(DAY, 3));
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("y");
		year = simpleDateFormat.format(new java.util.Date());
		simpleDateFormat = new SimpleDateFormat("M");
		month = simpleDateFormat.format(new java.util.Date());
		simpleDateFormat = new SimpleDateFormat("d");
		day = simpleDateFormat.format(new java.util.Date());
		
		yearList = new ArrayList<String>();
		monthList = new ArrayList<String>();
		dayList = new ArrayList<String>();
		
		setListener();
		
		setYears(month);
		setMonths(month, day);
		setDays(year, month);
		
		Message msg = new Message();
		handler.sendMessage(msg);
		
//		mYearView.setItems(yearList);
//		mMonthView.setItems(monthList);
//		mDayView.setItems(dayList);
		
		
//		mYearView.setDefault(1);
//		mMonthView.setDefault(Integer.parseInt(month)-1);
//		mDayView.setDefault(Integer.parseInt(day)-1);
	}
	
	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg)
		{
			mPage.getData().putString(ChooseStartDatePage.START_DATE_DATA_KEY,
					mYearView.getSeletedItem() + "-" + mMonthView.getSeletedItem() 
					+ "-" + mDayView.getSeletedItem());
			mPage.notifyDataChanged();
		}
	};
	

	private void setListener()
	{
		mYearView.setOnWheelViewListener(new OnWheelViewListener(){

			@Override
			public void onSelected(int selectedIndex, String item)
			{
				super.onSelected(selectedIndex, item);
				if (mMonthView.getSeletedItem().equals("2"))
				{
					setDays(item, "2");
				}
				Message msg = new Message();
				handler.sendMessage(msg);
			}
			
		});
		
		mMonthView.setOnWheelViewListener(new OnWheelViewListener(){

			@Override
			public void onSelected(int selectedIndex, String item)
			{
				super.onSelected(selectedIndex, item);
				setDays(mYearView.getSeletedItem(), item);
				Message msg = new Message();
				handler.sendMessage(msg);
			}
			
		});
		
		mDayView.setOnWheelViewListener(new OnWheelViewListener(){

			@Override
			public void onSelected(int selectedIndex, String item)
			{
				super.onSelected(selectedIndex, item);
				Message msg = new Message();
				handler.sendMessage(msg);
			}
			
		});
	}

	private void setYears(String month)
	{
		yearList.removeAll(yearList);
		if (month.equals("12") && (Integer.valueOf(day) > 20 && Integer.valueOf(day) <= 31))
		//若当前日期在12月21日到31日之间，则年份可为今年或明年
		{
			yearList.add(year);
			yearList.add(String.valueOf((Integer.valueOf(year) + 1)));
			mYearView.setSeletion(0);
		}
		else if (month.equals("1") && (Integer.valueOf(day) >= 1 && Integer.valueOf(day) <= 10)) {
			//若当前日期在1月1日到10日之间，则年份可为今年或去年
			yearList.add(String.valueOf((Integer.valueOf(year) - 1)));
			yearList.add(year);
			mYearView.setSeletion(1);
		}
		else {
			yearList.add(year);
		}
		mYearView.setItems(yearList);
	}
	
	
	private void setMonths(String month, String day)
	{
		for (int i = 1; i <= 12; i++)
		{
			monthList.add(String.valueOf(i));
		}
		mMonthView.setItems(monthList);
		mMonthView.setSeletion(monthList.indexOf(month));
	}
	
	private void setDays(String year, String month)
	{
		int monthInt = Integer.valueOf(month);
		int yearInt = Integer.valueOf(year);
		if (monthInt == 2)
		{
			if (isLeapYear(yearInt))
			{
				setDays29();
			}
			else {
				setDays28();
			}
		}
		else if (monthInt == 4 || monthInt == 6 || monthInt == 9 || monthInt == 11)
		{
			setDays30();
		}
		else {
			setDays31();
		}
		mDayView.setItems(dayList);
		mDayView.setSeletion( (dayList.indexOf(day) == -1)? 0 : dayList.indexOf(day));
	}

	private void setDays31()
	{
		dayList.removeAll(dayList);
		for (int i = 1; i <= 31; i++)
		{
			dayList.add(String.valueOf(i));
		}
	}
	
	private void setDays30()
	{
		dayList.removeAll(dayList);
		for (int i = 1; i <= 30; i++)
		{
			dayList.add(String.valueOf(i));
		}
	}
	
	private void setDays29()
	{
		dayList.removeAll(dayList);
		for (int i = 1; i <= 29; i++)
		{
			dayList.add(String.valueOf(i));
		}
	}
	
	private void setDays28()
	{
		dayList.removeAll(dayList);
		for (int i = 1; i <= 28; i++)
		{
			dayList.add(String.valueOf(i));
		}
	}
	
	/**
	 * 判断是否闰年
	 * @param year
	 * @return
	 */
	public boolean isLeapYear(int year){
		return (year%4==0&&year%100!=0)||year%400==0;
	}
//	
//	
//	private void setDefultDay(String text){
//		if(text.equals("4")||text.equals("6")||text.equals("9")||text.equals("11")){
//			mDayView.setData(getData(DAY, 2));
//		}else{
//			mDayView.setData(getData(DAY, 3));
//		}
//		mDayView.setDefault(0);
//	}
//	
//	
//	private ArrayList<String> getData(int data,int day){
//		SimpleDateFormat simpleDateFormat;
//		
//		ArrayList<String> datas = new ArrayList<String>();
//		switch (data) {
//		case YEAR:
//			simpleDateFormat = new SimpleDateFormat("yyyy");  
//			String year = simpleDateFormat.format(new java.util.Date());
//			for (int i = 0; i < 2; i++) {
//				datas.add(String.valueOf(Integer.parseInt(year) -1 + i));
//			}
//			break;
//		case MONTH:
//			for (int i = 0; i < 12; i++) {
//				datas.add(String.valueOf(1+i));
//			}
//			break;
//		case DAY:
//			switch (day) {
//			case 0:
//				for (int i = 0; i < 28; i++) {
//					datas.add(String.valueOf(1+i));
//				}
//				break;
//			case 1:
//				for (int i = 0; i < 29; i++) {
//					datas.add(String.valueOf(1+i));
//				}
//				break;
//			case 2:
//				for (int i = 0; i < 30; i++) {
//					datas.add(String.valueOf(1+i));
//				}
//				break;
//			case 3:
//				for (int i = 0; i < 31; i++) {
//					datas.add(String.valueOf(1+i));
//				}
//				break;
//
//			default:
//				break;
//			}
//			break;
//
//		default:
//			break;
//		}
//		return datas;
//	}
}
