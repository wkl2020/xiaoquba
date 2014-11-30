package com.jun.xiaoquren.view.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jun.xiaoquren.MainActivity;
import com.jun.xiaoquren.R;
import com.jun.xiaoquren.dao.model.Xiaoqu;
import com.jun.xiaoquren.util.LocalUtil;
import com.jun.xiaoquren.view.model.XiaoquViewHolder;

public class XiaoquListViewAdapter extends BaseAdapter {

	// Declare Variables
	Activity mContext;
	LayoutInflater inflater;
	private List<Xiaoqu> xiaoquList = null;			// Current shows xiaoqu list, equals with the copyXiaoquArraylist when search inputs is empty
	private ArrayList<Xiaoqu> copyXiaoquArraylist;  // All xiaoqu list

	public XiaoquListViewAdapter(Activity context, List<Xiaoqu> xiaoquList) {
		this.mContext = context;
		this.xiaoquList = xiaoquList;
		inflater = LayoutInflater.from(mContext);
		this.copyXiaoquArraylist = new ArrayList<Xiaoqu>();
		this.copyXiaoquArraylist.addAll(xiaoquList);
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
		final XiaoquViewHolder holder;
		if (view == null) {
			holder = new XiaoquViewHolder();
			view = inflater.inflate(R.layout.xiaoqu_item, null);
			// Locate the TextViews in listview_item.xml
			holder.id = (TextView) view.findViewById(R.id.xiaoquid);
			holder.name = (TextView) view.findViewById(R.id.name);
			holder.address = (TextView) view.findViewById(R.id.address);
			view.setTag(holder);
		} else {
			holder = (XiaoquViewHolder) view.getTag();
		}
		
		// Set the results into TextViews
		holder.id.setText(xiaoquList.get(position).getStringId());
		holder.name.setText(xiaoquList.get(position).getName());
		holder.address.setText(xiaoquList.get(position).getAddress());
		
		// Xiaoqu item click event
		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				String selId = String.valueOf(xiaoquList.get(position).getId());
				String selName = xiaoquList.get(position).getName();				
				LocalUtil.saveCurrentXiaoQu(mContext, selName, selId);

				MainActivity mainActivity = (MainActivity)LocalUtil.getActiveActivity(MainActivity.ACTIVITY_NAME);
				mainActivity.refreshCurrentXiaoQuName();
				
				mContext.finish();
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
		} else {
			for (Xiaoqu wp : copyXiaoquArraylist) {
				if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
					xiaoquList.add(wp);
				}
			}
		}
		notifyDataSetChanged();
	}

}