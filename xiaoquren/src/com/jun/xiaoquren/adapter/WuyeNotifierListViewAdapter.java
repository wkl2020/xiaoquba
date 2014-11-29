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
import com.jun.xiaoquren.model.WuyeNotification;

public class WuyeNotifierListViewAdapter extends BaseAdapter {

	// Declare Variables
	Activity mContext;
	LayoutInflater inflater;
	private List<WuyeNotification> notificationList = null;
	private ArrayList<WuyeNotification> copyNotificationArraylist;

	public WuyeNotifierListViewAdapter(Activity context, List<WuyeNotification> notificationList) {
		this.mContext = context;
		this.notificationList = notificationList;
		inflater = LayoutInflater.from(mContext);
		this.copyNotificationArraylist = new ArrayList<WuyeNotification>();
		this.copyNotificationArraylist.addAll(notificationList);
	}

	public class ViewHolder {
		TextView id;
		TextView name;
		TextView address;
	}

	@Override
	public int getCount() {
		return notificationList.size();
	}

	@Override
	public WuyeNotification getItem(int position) {
		return notificationList.get(position);
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
		holder.id.setText(notificationList.get(position).getId());
		holder.name.setText(notificationList.get(position).getName());
		holder.address.setText(notificationList.get(position).getAddress());
		
		// Listen for ListView Item Click
		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				// Send single item click data to WuyeNotifierDetailActivity Class
				Intent intent = new Intent(mContext, WuyeNotifierDetailActivity.class);
				intent.putExtra("id",(notificationList.get(position).getId()));
				intent.putExtra("name",(notificationList.get(position).getName()));
				intent.putExtra("address",(notificationList.get(position).getAddress()));
				mContext.startActivity(intent);
			}
		});

		return view;
	}

	// Filter Class
	public void filter(String charText) {
		charText = charText.toLowerCase(Locale.getDefault());
		notificationList.clear();
		if (charText.length() == 0) {
			notificationList.addAll(copyNotificationArraylist);
		} 
		else 
		{
			for (WuyeNotification wp : copyNotificationArraylist) 
			{
				if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText)) 
				{
					notificationList.add(wp);
				}
			}
		}
		notifyDataSetChanged();
	}

}