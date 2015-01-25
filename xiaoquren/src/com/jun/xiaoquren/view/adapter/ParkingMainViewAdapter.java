package com.jun.xiaoquren.view.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jun.xiaoquren.ParkingViewActivity;
import com.jun.xiaoquren.R;
import com.jun.xiaoquren.http.LocalHttpUtil;
import com.jun.xiaoquren.server.model.ParkingStallInfo;
import com.jun.xiaoquren.view.model.ParkingMainViewHolder;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

public class ParkingMainViewAdapter extends BaseAdapter {
	
	public static final String CLASSNAME = "ParkingStallInfoListViewAdapter";

	// Declare Variables
	Activity mContext;
	LayoutInflater inflater;
	private List<ParkingStallInfo> parkingStallInfoList = null;			// Current show ParkingStallInfos, equals the copyParkingStallInfoArraylist when search inputs empty
	private ArrayList<ParkingStallInfo> copyParkingStallInfoArraylist;  // Clone all the ParkingStallInfos for search function

	public ParkingMainViewAdapter(Activity context, List<ParkingStallInfo> parkingStallInfoList) {
		this.mContext = context;
		parkingStallInfoList = parkingStallInfoList == null ? new ArrayList<ParkingStallInfo>() : parkingStallInfoList; 
		
		this.parkingStallInfoList = parkingStallInfoList;
		inflater = LayoutInflater.from(mContext);
		this.copyParkingStallInfoArraylist = new ArrayList<ParkingStallInfo>();
		this.copyParkingStallInfoArraylist.addAll(parkingStallInfoList);
	}

	@Override
	public int getCount() {
		return parkingStallInfoList.size();
	}

	@Override
	public ParkingStallInfo getItem(int position) {
		return parkingStallInfoList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public void resetListData(List<ParkingStallInfo> parkingStallInfoList) {
		parkingStallInfoList = parkingStallInfoList == null ? new ArrayList<ParkingStallInfo>() : parkingStallInfoList; 		
		this.parkingStallInfoList = parkingStallInfoList;
		this.copyParkingStallInfoArraylist = new ArrayList<ParkingStallInfo>();
		this.copyParkingStallInfoArraylist.addAll(parkingStallInfoList);
	}

	/*
	 * View for each ParkingStallInfo
	 */
	public View getView(final int position, View view, ViewGroup parent) {
		final ParkingMainViewHolder parkingStallInfoHolder;
		if (view == null) {
			parkingStallInfoHolder = new ParkingMainViewHolder();
			view = inflater.inflate(R.layout.parking_main_item, null);
			// Locate the TextViews in listview_item.xml
			parkingStallInfoHolder.id = (TextView) view.findViewById(R.id.item_id);
			parkingStallInfoHolder.title = (TextView) view.findViewById(R.id.item_title);
			parkingStallInfoHolder.price = (TextView) view.findViewById(R.id.item_price);
			parkingStallInfoHolder.priceUnit = (TextView) view.findViewById(R.id.item_priceUnit);
			parkingStallInfoHolder.createTime = (TextView) view.findViewById(R.id.item_createTime);
			parkingStallInfoHolder.areaMeasure = (TextView) view.findViewById(R.id.item_areaMeasure);
			view.setTag(parkingStallInfoHolder);
		} else {
			parkingStallInfoHolder = (ParkingMainViewHolder) view.getTag();
		}
		
		// Set the results into TextViews
		parkingStallInfoHolder.id.setText(parkingStallInfoList.get(position).getStringId());
		parkingStallInfoHolder.title.setText(parkingStallInfoList.get(position).getTitle());
		parkingStallInfoHolder.price.setText(""+parkingStallInfoList.get(position).getPrice());
		parkingStallInfoHolder.priceUnit.setText(parkingStallInfoList.get(position).getPriceUnit());
		parkingStallInfoHolder.createTime.setText(parkingStallInfoList.get(position).getCreateTime());
		parkingStallInfoHolder.areaMeasure.setText(""+parkingStallInfoList.get(position).getAreaMeasure());
		
		// ParkingStallInfo item click event
		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				ParkingStallInfo currentParkingStallInfo = parkingStallInfoList.get(position);				
				LocalHttpUtil.getDefaultHttpUtils().send(HttpRequest.HttpMethod.GET, LocalHttpUtil.GetParkingStallInfoByIdUrl+currentParkingStallInfo.getId(), new RequestCallBack<String>() {

			          @Override
			          public void onStart() {
			          	LogUtils.i("Start to connect comment ParkingStallInfoComments...");
			          }

			          @Override
			          public void onLoading(long total, long current, boolean isUploading) {
			          	LogUtils.i("On loading to connect comment ParkingStallInfoComments: " + current + "/" + total);
			          }

						@Override
						public void onFailure(HttpException error, String msg) {
							LogUtils.i("Error to connect comment ParkingStallInfoComments: " + msg);
						}

						@Override
						public void onSuccess(ResponseInfo<String> response) {
							LogUtils.i("Success to connect comment ParkingStallInfoComments: " + response.result.toString());
							String infoJsonstr = response.result.toString();
							System.out.println("infoJsonstr: " + infoJsonstr);
							
							Intent intent = new Intent(mContext, ParkingViewActivity.class);
							intent.putExtra("parkingJsonstr", infoJsonstr);
							mContext.startActivity(intent);
						}
					});
			}
		});

		return view;
	}

	/*
	 * ParkingStallInfo search action
	 */
	public void filter(String charText) {
		charText = charText.toLowerCase(Locale.getDefault());
		parkingStallInfoList.clear();
		if (charText.length() == 0) {
			// Shows all ParkingStallInfos when inputs empty
			parkingStallInfoList.addAll(copyParkingStallInfoArraylist);
		} else {
			// Shows the matches ParkingStallInfos
			for (ParkingStallInfo wp : copyParkingStallInfoArraylist) {
				if (wp.getTitle().toLowerCase(Locale.getDefault()).contains(charText)) {
					parkingStallInfoList.add(wp);
				}
			}
		}
		notifyDataSetChanged();
	}
}
