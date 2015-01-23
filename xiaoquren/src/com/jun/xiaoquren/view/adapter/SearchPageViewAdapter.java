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

import com.jun.xiaoquren.ParkingAddActivity;
import com.jun.xiaoquren.ParkingMainActivity;
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
	String parentPageName = "";
	

	public SearchPageViewAdapter(Activity context, String listName, String parentPageName) {
		this.mContext = context;
		inflater = LayoutInflater.from(mContext);
		this.listName = listName;
		this.parentPageName = parentPageName;
		
		if (ParkingMainActivity.CLASSNAME.equals(parentPageName)) {
			
			if (LocalViewUtil.Info_Search_Supply.equals(listName)) {
				this.list = LocalViewUtil.SupplyInfoList;
				
			} else if (LocalViewUtil.Info_Search_Identity.equals(listName)) {
				this.list = LocalViewUtil.IdentityInfoList;
				
			} else if (LocalViewUtil.Info_Search_Area.equals(listName)) {
				this.list = LocalViewUtil.AreaInfoList;
				
			} else if (LocalViewUtil.Info_Search_Price.equals(listName)) {
				this.list = LocalViewUtil.PriceInfoList;
				
			} else {
				this.list = new ArrayList<String>();
			}
		} else if (ParkingAddActivity.CLASSNAME.equals(parentPageName)) {
			
			if (LocalViewUtil.Info_Search_Supply.equals(listName)) {
				this.list = LocalViewUtil.SupplyInfoList;
				
			} else if (LocalViewUtil.Info_Search_Identity.equals(listName)) {
				this.list = LocalViewUtil.IdentityInfoList;
				
			} else if (LocalViewUtil.Info_Search_Unit.equals(listName)) {
				this.list = LocalViewUtil.UnitInfoList;
				
			} else {
				this.list = new ArrayList<String>();
			}
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
				
				if (ParkingMainActivity.CLASSNAME.equals(parentPageName)) {
					
					LocalViewUtil.MainParkingInfoMap.put(listName, item);
					if (LocalUtil.isActiveActivityExists(ParkingSearchActivity.CLASSNAME)) {
						ParkingSearchActivity page = (ParkingSearchActivity)LocalUtil.getActiveActivity(ParkingSearchActivity.CLASSNAME);
						page.refreshPage();
						mContext.finish();
	    			}
					
				} else if (ParkingAddActivity.CLASSNAME.equals(parentPageName)) {
					
					LocalViewUtil.AddParkingInfoMap.put(listName, item);
					if (LocalUtil.isActiveActivityExists(ParkingAddActivity.CLASSNAME)) {
						ParkingAddActivity page = (ParkingAddActivity)LocalUtil.getActiveActivity(ParkingAddActivity.CLASSNAME);
						page.refreshPage();
						mContext.finish();
	    			}
					
				}
			}
		});

		return view;
	}

}

