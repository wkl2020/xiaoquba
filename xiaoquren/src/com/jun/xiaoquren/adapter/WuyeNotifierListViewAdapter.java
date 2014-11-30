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
import com.jun.xiaoquren.dao.model.Document;

public class WuyeNotifierListViewAdapter extends BaseAdapter {

	// Declare Variables
	Activity mContext;
	LayoutInflater inflater;
	private List<Document> documentList = null;
	private ArrayList<Document> copyDocumentArraylist;

	public WuyeNotifierListViewAdapter(Activity context, List<Document> documentList) {
		this.mContext = context;
		this.documentList = documentList;
		inflater = LayoutInflater.from(mContext);
		this.copyDocumentArraylist = new ArrayList<Document>();
		this.copyDocumentArraylist.addAll(documentList);
	}

	public class ViewHolder {
		TextView id;
		TextView name;
		TextView address;
	}

	@Override
	public int getCount() {
		return documentList.size();
	}

	@Override
	public Document getItem(int position) {
		return documentList.get(position);
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
		holder.id.setText(String.valueOf(documentList.get(position).getId()));
		holder.name.setText(documentList.get(position).getTitle());
		holder.address.setText(documentList.get(position).getCreatetime());
		
		// Listen for ListView Item Click
		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				// Send single item click data to WuyeNotifierDetailActivity Class
				Intent intent = new Intent(mContext, WuyeNotifierDetailActivity.class);
				intent.putExtra("id",String.valueOf(documentList.get(position).getId()));
				intent.putExtra("name",(documentList.get(position).getTitle()));
				intent.putExtra("address",(documentList.get(position).getCreatetime()));
				mContext.startActivity(intent);
			}
		});

		return view;
	}

	// Filter Class
	public void filter(String charText) {
		charText = charText.toLowerCase(Locale.getDefault());
		documentList.clear();
		if (charText.length() == 0) {
			documentList.addAll(copyDocumentArraylist);
		} 
		else 
		{
			for (Document wp : copyDocumentArraylist) 
			{
				if (wp.getTitle().toLowerCase(Locale.getDefault()).contains(charText)) 
				{
					documentList.add(wp);
				}
			}
		}
		notifyDataSetChanged();
	}

}