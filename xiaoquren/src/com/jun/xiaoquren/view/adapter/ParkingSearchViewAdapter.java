package com.jun.xiaoquren.view.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.jun.xiaoquren.R;
import com.jun.xiaoquren.SearchPageActivity;
import com.jun.xiaoquren.util.LocalViewUtil;
import com.jun.xiaoquren.view.model.ParkingSearchViewHolder;

public class ParkingSearchViewAdapter extends BaseAdapter {
	
	public static final String CLASSNAME = "ParkingSearchViewAdapter";

	// Declare Variables
	Activity mContext;
	LayoutInflater inflater;
	private List<String> keyList = null;
	

	public ParkingSearchViewAdapter(Activity context, List<String> list) {
		this.mContext = context;
		list = list == null ? new ArrayList<String>() : list;		
		this.keyList = list;
		inflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return keyList.size();
	}

	@Override
	public String getItem(int position) {
		return keyList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup parent) {
		final ParkingSearchViewHolder holder;
		if (view == null) {
			holder = new ParkingSearchViewHolder();
			view = inflater.inflate(R.layout.parking_search_item, null);
			// Locate the TextViews in listview_item.xml
			holder.item_key = (TextView) view.findViewById(R.id.parking_info_search_item_key);
			holder.item_value = (TextView) view.findViewById(R.id.parking_info_search_item_value);
			view.setTag(holder);
		} else {
			holder = (ParkingSearchViewHolder) view.getTag();
		}
		
		// Set the results into TextViews
		holder.item_key.setText(keyList.get(position));
		holder.item_value.setText(LocalViewUtil.MainParkingInfoMap.get(keyList.get(position)));
		
		// search item click event
		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				String key = String.valueOf(keyList.get(position));
//				String value = LocalViewUtil.MainParkingInfoMap.get(keyList.get(position));		
//				Toast.makeText(mContext, value, Toast.LENGTH_LONG).show();
				
				Intent intent = new Intent(mContext, SearchPageActivity.class);
				intent.putExtra("listName", key);
				mContext.startActivity(intent);
			}
		});

		return view;
	}

}
