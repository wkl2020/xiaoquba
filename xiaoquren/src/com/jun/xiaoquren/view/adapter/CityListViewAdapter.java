package com.jun.xiaoquren.view.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jun.xiaoquren.CitySearchActivity;
import com.jun.xiaoquren.MainActivity;
import com.jun.xiaoquren.R;
import com.jun.xiaoquren.XiaoquSearchActivity;
import com.jun.xiaoquren.dao.model.City;
import com.jun.xiaoquren.http.LocalHttpUtil;
import com.jun.xiaoquren.util.LocalUtil;
import com.jun.xiaoquren.view.model.CityViewHolder;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

public class CityListViewAdapter extends BaseAdapter {

	// Declare Variables
	Activity mContext;
	LayoutInflater inflater;
	private List<City> cityList = null;			// Current shows city list, equals with the copycityArraylist when search inputs is empty
	private ArrayList<City> copycityArraylist;  // All city list

	public CityListViewAdapter(Activity context, List<City> cityList) {
		this.mContext = context;
		this.cityList = cityList;
		inflater = LayoutInflater.from(mContext);
		this.copycityArraylist = new ArrayList<City>();
		this.copycityArraylist.addAll(cityList);
	}

	@Override
	public int getCount() {
		return cityList.size();
	}

	@Override
	public City getItem(int position) {
		return cityList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup parent) {
		final CityViewHolder holder;
		if (view == null) {
			holder = new CityViewHolder();
			view = inflater.inflate(R.layout.city_item, null);
			// Locate the TextViews in listview_item.xml
			holder.id = (TextView) view.findViewById(R.id.id);
			holder.name = (TextView) view.findViewById(R.id.name);
			view.setTag(holder);
		} else {
			holder = (CityViewHolder) view.getTag();
		}
		
		// Set the results into TextViews
		holder.id.setText(cityList.get(position).getStringId());
		holder.name.setText(cityList.get(position).getName());
		
		// city item click event
		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				String selId = String.valueOf(cityList.get(position).getId());
				String selName = cityList.get(position).getName();				
				LocalUtil.saveCurrentCity(mContext, selName, selId);
				
				// Refresh xiaoqu list
				LocalHttpUtil.getDefaultHttpUtils().send(HttpRequest.HttpMethod.GET, LocalHttpUtil.GetAllXiaoquListUrl, new RequestCallBack<String>() {

		          @Override
		          public void onStart() {
		          	LogUtils.i("Start to connect xiaoqu index...");
		          }

		          @Override
		          public void onLoading(long total, long current, boolean isUploading) {
		          	LogUtils.i("On loading to connect xiaoqu index: " + current + "/" + total);
		          }

					@Override
					public void onFailure(HttpException error, String msg) {
						LogUtils.i("Error to connect xiaoqu index: " + msg);
					}

					@Override
					public void onSuccess(ResponseInfo<String> response) {
						LogUtils.i("Success to connect xiaoqu index: " + response.result.toString());
						String xiaoquListJsonstr = response.result.toString(); 
						
						if (LocalUtil.getActiveActivity(XiaoquSearchActivity.ACTIVITY_NAME) != null) {
							XiaoquSearchActivity xiaoquActivity = (XiaoquSearchActivity)LocalUtil.getActiveActivity(XiaoquSearchActivity.ACTIVITY_NAME);
							xiaoquActivity.finish();
						}
							
						Intent intent = new Intent();
					    intent.setClass(mContext, XiaoquSearchActivity.class);
					    intent.putExtra("xiaoquListJsonstr", xiaoquListJsonstr);
					    mContext.startActivity(intent);						
					    mContext.finish();
					}
				});
				
			}
		});

		return view;
	}

	// Filter Class
	public void filter(String charText) {
		charText = charText.toLowerCase(Locale.getDefault());
		cityList.clear();
		if (charText.length() == 0) {
			cityList.addAll(copycityArraylist);
		} else {
			for (City wp : copycityArraylist) {
				if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
					cityList.add(wp);
				}
			}
		}
		notifyDataSetChanged();
	}

}
