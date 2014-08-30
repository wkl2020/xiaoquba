package com.jun.xiaoquren.adapter;

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

import com.jun.xiaoquren.R;
import com.jun.xiaoquren.WuyeNotifierDetailActivity;
import com.jun.xiaoquren.model.Xiaoqu;

public class WuyeNotifierListViewAdapter extends BaseAdapter {

	// Declare Variables
	Activity mContext;
	LayoutInflater inflater;
	private List<Xiaoqu> xiaoquList = null;
	private ArrayList<Xiaoqu> copyXiaoquArraylist;

	public WuyeNotifierListViewAdapter(Activity context, List<Xiaoqu> xiaoquList) {
		this.mContext = context;
		this.xiaoquList = xiaoquList;
		inflater = LayoutInflater.from(mContext);
		this.copyXiaoquArraylist = new ArrayList<Xiaoqu>();
		this.copyXiaoquArraylist.addAll(xiaoquList);
	}

	public class ViewHolder {
		TextView id;
		TextView name;
		TextView address;
	}

	@Override
	public int getCount() {
		return xiaoquList.size();
	}

	@Override
	public Xiaoqu getItem(int position) {
		return xiaoquList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup parent) {
		final ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.wuye_notifier_item, null);
			// Locate the TextViews in listview_item.xml
			holder.id = (TextView) view.findViewById(R.id.xiaoquid);
			holder.name = (TextView) view.findViewById(R.id.name);
			holder.address = (TextView) view.findViewById(R.id.address);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		// Set the results into TextViews
//		holder.rank.setText(worldpopulationlist.get(position).getRank());
		holder.name.setText(xiaoquList.get(position).getName());
		holder.address.setText(xiaoquList.get(position).getAddress());
		
		// Listen for ListView Item Click
		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				// Send single item click data to WuyeNotifierDetailActivity Class
				Intent intent = new Intent(mContext, WuyeNotifierDetailActivity.class);
				intent.putExtra("id",(xiaoquList.get(position).getId()));
				intent.putExtra("name",(xiaoquList.get(position).getName()));
				intent.putExtra("address",(xiaoquList.get(position).getAddress()));
				mContext.startActivity(intent);
			}
		});

		return view;
	}

	// Filter Class
	public void filter(String charText) {
		charText = charText.toLowerCase(Locale.getDefault());
		xiaoquList.clear();
		if (charText.length() == 0) {
			xiaoquList.addAll(copyXiaoquArraylist);
		} 
		else 
		{
			for (Xiaoqu wp : copyXiaoquArraylist) 
			{
				if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText)) 
				{
					xiaoquList.add(wp);
				}
			}
		}
		notifyDataSetChanged();
	}

}