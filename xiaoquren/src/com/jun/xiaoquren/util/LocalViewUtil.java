package com.jun.xiaoquren.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

public class LocalViewUtil {

	public LocalViewUtil() {
		// TODO Auto-generated constructor stub
	}

	public static void setListViewHeightBasedOnChildren(ListView listView) {

		// 获取ListView对应的Adapter
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) { // listAdapter.getCount()返回数据项的数目
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0); // 计算子项View 的宽高
			totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();

		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));

		// listView.getDividerHeight()获取子项间分隔符占用的高度
		// params.height最后得到整个ListView完整显示需要的高度\
		listView.setLayoutParams(params);
	}
	
	
	
	// Parking Stall Info Search
	public static LinkedHashMap<String, String> MainParkingInfoMap = new LinkedHashMap<String, String>();
	
	public static final String Info_Search_First = "供需";
	public static final String Info_Search_First_Value_First = "出租";
	public static final String Info_Search_First_Value_Second = "寻租";
	public static List<String> FirstInfoList = new ArrayList<String>();
	
	public static final String Info_Search_Second = "发布人";
	public static final String Info_Search_Second_Value_Zero = "不限";
	public static final String Info_Search_Second_Value_First = "个人";
	public static final String Info_Search_Second_Value_Second = "经纪人";
	public static List<String> SecondInfoList = new ArrayList<String>();
	
	public static final String Info_Search_Third = "面积";
	public static final String Info_Search_Third_Value_Zero = "不限";
	public static final String Info_Search_Third_Value_First = "大于10平米";
	public static final String Info_Search_Third_Value_Second = "大于20平米";
	public static final String Info_Search_Third_Value_Third = "大于50平米";
	public static final String Info_Search_Third_Value_Fourth = "大于100平米";
	public static List<String> ThirdInfoList = new ArrayList<String>();
	
	public static final String Info_Search_Fourth = "月租金";
	public static final String Info_Search_Fourth_Value_Zero = "不限";
	public static final String Info_Search_Fourth_Value_First = "大于1000元/月";
	public static final String Info_Search_Fourth_Value_Second = "大于5000元/月";
	public static List<String> FourthInfoList = new ArrayList<String>();
	
	
	
	static {
		MainParkingInfoMap.put(Info_Search_First, Info_Search_First_Value_First);
		MainParkingInfoMap.put(Info_Search_Second, Info_Search_Second_Value_Zero);
		MainParkingInfoMap.put(Info_Search_Third, Info_Search_Third_Value_Zero);
		MainParkingInfoMap.put(Info_Search_Fourth, Info_Search_Fourth_Value_Zero);
		
		FirstInfoList.add(Info_Search_First_Value_First);
		FirstInfoList.add(Info_Search_First_Value_Second);
		
		SecondInfoList.add(Info_Search_Second_Value_Zero);
		SecondInfoList.add(Info_Search_Second_Value_First);
		SecondInfoList.add(Info_Search_Second_Value_Second);
		
		ThirdInfoList.add(Info_Search_Third_Value_Zero);
		ThirdInfoList.add(Info_Search_Third_Value_First);
		ThirdInfoList.add(Info_Search_Third_Value_Second);
		ThirdInfoList.add(Info_Search_Third_Value_Third);
		ThirdInfoList.add(Info_Search_Third_Value_Fourth);
		
		FourthInfoList.add(Info_Search_Fourth_Value_Zero);
		FourthInfoList.add(Info_Search_Fourth_Value_First);
		FourthInfoList.add(Info_Search_Fourth_Value_Second);
	}
	
	
	
}

