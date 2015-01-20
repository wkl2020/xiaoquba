package com.jun.xiaoquren.view.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jun.xiaoquren.ParkingSearchActivity;
import com.jun.xiaoquren.R;
import com.jun.xiaoquren.util.LocalUtil;
import com.jun.xiaoquren.util.LocalViewUtil;
import com.jun.xiaoquren.view.model.SearchPageViewHolder;

public class SearchPageViewAdapter extends BaseAdapter {
	
	public static final String CLASSNAME = "SearchPageViewAdapter";

	// Declare Variables
	Activity mContext;
	LayoutInflater inflater;
	private List<String> list = null;
	String listName = "";
	

	public SearchPageViewAdapter(Activity context, String listName) {
		this.mContext = context;
		inflater = LayoutInflater.from(mContext);
		this.listName = listName;
		
		if (LocalViewUtil.Info_Search_First.equals(listName)) {
			this.list = LocalViewUtil.FirstInfoList;
			
		} else if (LocalViewUtil.Info_Search_Second.equals(listName)) {
			this.list = LocalViewUtil.SecondInfoList;
			
		} else if (LocalViewUtil.Info_Search_Third.equals(listName)) {
			this.list = LocalViewUtil.ThirdInfoList;
			
		} else if (LocalViewUtil.Info_Search_Fourth.equals(listName)) {
			this.list = LocalViewUtil.FourthInfoList;
			
		} else {
			this.list = new ArrayList<String>();
		}
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public String getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup parent) {
		final SearchPageViewHolder holder;
		if (view == null) {
			holder = new SearchPageViewHolder();
			view = inflater.inflate(R.layout.search_page_item, null);
			// Locate the TextViews in listview_item.xml
			holder.item_key = (TextView) view.findViewById(R.id.search_page_item_key);
			holder.item_value = (TextView) view.findViewById(R.id.search_page_item_value);
			view.setTag(holder);
		} else {
			holder = (SearchPageViewHolder) view.getTag();
		}
		
		// Set the results into TextViews
		holder.item_key.setText(list.get(position));
		
		// search item click event
		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				String item = String.valueOf(list.get(position));				
				LocalViewUtil.MainParkingInfoMap.put(listName, item);
				
				if (LocalUtil.isActiveActivityExists(ParkingSearchActivity.CLASSNAME)) {
					ParkingSearchActivity page = (ParkingSearchActivity)LocalUtil.getActiveActivity(ParkingSearchActivity.CLASSNAME);
					page.refreshPage();
					mContext.finish();
    			}
			}
		});

		return view;
	}

}

