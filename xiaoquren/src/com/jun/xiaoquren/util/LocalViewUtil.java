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
	
	
	
	// Parking Stall Info Search Main Page
	public static LinkedHashMap<String, String> MainParkingInfoMap = new LinkedHashMap<String, String>();
	
	public static final String Info_Search_Supply = "供需";
	public static final String Info_Search_Supply_Value_First = "出租";
	public static final String Info_Search_Supply_Value_Second = "寻租";
	public static List<String> SupplyInfoList = new ArrayList<String>();
	
	public static final String Info_Search_Identity = "发布人";
	public static final String Info_Search_Identity_Value_Zero = "不限";
	public static final String Info_Search_Identity_Value_First = "个人";
	public static final String Info_Search_Identity_Value_Second = "经纪人";
	public static List<String> IdentityInfoList = new ArrayList<String>();
	
	public static final String Info_Search_Area = "面积";
	public static final String Info_Search_Area_Value_Zero = "不限";
	public static final String Info_Search_Area_Value_First = "大于10平米";
	public static final String Info_Search_Area_Value_Second = "大于20平米";
	public static final String Info_Search_Area_Value_Third = "大于50平米";
	public static final String Info_Search_Area_Value_Fourth = "大于100平米";
	public static List<String> AreaInfoList = new ArrayList<String>();
	
	public static final String Info_Search_Price = "月租金";
	public static final String Info_Search_Price_Value_Zero = "不限";
	public static final String Info_Search_Price_Value_First = "大于1000元/月";
	public static final String Info_Search_Price_Value_Second = "大于5000元/月";
	public static List<String> PriceInfoList = new ArrayList<String>();
	
	public static final String Info_Search_Unit = "月租金单位";
	public static final String Info_Search_Unit_Value_Zero = "元/月";
	public static final String Info_Search_Unit_Value_First = "元/天";
	public static List<String> UnitInfoList = new ArrayList<String>();
	
	// Parking Stall Add Page
	public static LinkedHashMap<String, String> AddParkingInfoMap = new LinkedHashMap<String, String>();
	
	
	
	static {
		MainParkingInfoMap.put(Info_Search_Supply, Info_Search_Supply_Value_First);
		MainParkingInfoMap.put(Info_Search_Identity, Info_Search_Identity_Value_Zero);
		MainParkingInfoMap.put(Info_Search_Area, Info_Search_Area_Value_Zero);
		MainParkingInfoMap.put(Info_Search_Price, Info_Search_Price_Value_Zero);
		
		AddParkingInfoMap.put(Info_Search_Supply, Info_Search_Supply_Value_First);
		AddParkingInfoMap.put(Info_Search_Identity, Info_Search_Identity_Value_First);
		AddParkingInfoMap.put(Info_Search_Unit, Info_Search_Unit_Value_Zero);
		
		SupplyInfoList.add(Info_Search_Supply_Value_First);
		SupplyInfoList.add(Info_Search_Supply_Value_Second);
		
		IdentityInfoList.add(Info_Search_Identity_Value_Zero);
		IdentityInfoList.add(Info_Search_Identity_Value_First);
		IdentityInfoList.add(Info_Search_Identity_Value_Second);
		
		AreaInfoList.add(Info_Search_Area_Value_Zero);
		AreaInfoList.add(Info_Search_Area_Value_First);
		AreaInfoList.add(Info_Search_Area_Value_Second);
		AreaInfoList.add(Info_Search_Area_Value_Third);
		AreaInfoList.add(Info_Search_Area_Value_Fourth);
		
		PriceInfoList.add(Info_Search_Price_Value_Zero);
		PriceInfoList.add(Info_Search_Price_Value_First);
		PriceInfoList.add(Info_Search_Price_Value_Second);
		
		UnitInfoList.add(Info_Search_Unit_Value_Zero);
		UnitInfoList.add(Info_Search_Unit_Value_First);
	}
	
	
	
}

