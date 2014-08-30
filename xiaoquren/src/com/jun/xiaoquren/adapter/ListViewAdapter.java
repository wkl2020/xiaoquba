package com.jun.xiaoquren.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.jun.xiaoquren.MainActivity;
import com.jun.xiaoquren.R;
import com.jun.xiaoquren.XiaoQuItemActivity;
import com.jun.xiaoquren.R.id;
import com.jun.xiaoquren.R.layout;
import com.jun.xiaoquren.model.WorldPopulation;
import com.jun.xiaoquren.util.LocalUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class ListViewAdapter extends BaseAdapter {

	// Declare Variables
	Activity mContext;
	LayoutInflater inflater;
	private List<WorldPopulation> worldpopulationlist = null;
	private ArrayList<WorldPopulation> arraylist;

	public ListViewAdapter(Activity context, List<WorldPopulation> worldpopulationlist) {
		this.mContext = context;
		this.worldpopulationlist = worldpopulationlist;
		inflater = LayoutInflater.from(mContext);
		this.arraylist = new ArrayList<WorldPopulation>();
		this.arraylist.addAll(worldpopulationlist);
	}

	public class ViewHolder {
		TextView rank;
		TextView country;
		TextView population;
	}

	@Override
	public int getCount() {
		return worldpopulationlist.size();
	}

	@Override
	public WorldPopulation getItem(int position) {
		return worldpopulationlist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup parent) {
		final ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.listview_item, null);
			// Locate the TextViews in listview_item.xml
			holder.rank = (TextView) view.findViewById(R.id.rank);
			holder.country = (TextView) view.findViewById(R.id.country);
			holder.population = (TextView) view.findViewById(R.id.population);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		// Set the results into TextViews
//		holder.rank.setText(worldpopulationlist.get(position).getRank());
		holder.country.setText(worldpopulationlist.get(position).getCountry());
		holder.population.setText(worldpopulationlist.get(position).getPopulation());
		
		// Listen for ListView Item Click
		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				String selRank = (worldpopulationlist.get(position).getRank());
				String selCountry = worldpopulationlist.get(position).getCountry();
				String selPosition = worldpopulationlist.get(position).getPopulation();
				
				LocalUtil.saveCurrentXiaoQu(mContext, selCountry, selRank);

				MainActivity mainActivity = (MainActivity)LocalUtil.getActiveActivity(MainActivity.ACTIVITY_NAME);
				mainActivity.refreshCurrentXiaoQuName();
				
				mContext.finish();
				
//				Intent intent = new Intent(mContext, MainActivity.class);
//				mContext.startActivity(intent);
				
//				// Send single item click data to SingleItemView Class
//				Intent intent = new Intent(mContext, XiaoQuItemActivity.class);
//				// Pass all data rank
//				intent.putExtra("rank",(worldpopulationlist.get(position).getRank()));
//				// Pass all data country
//				intent.putExtra("country",(worldpopulationlist.get(position).getCountry()));
//				// Pass all data population
//				intent.putExtra("population",(worldpopulationlist.get(position).getPopulation()));
//				// Pass all data flag
//				// Start SingleItemView Class
//				mContext.startActivity(intent);
			}
		});

		return view;
	}

	// Filter Class
	public void filter(String charText) {
		charText = charText.toLowerCase(Locale.getDefault());
		worldpopulationlist.clear();
		if (charText.length() == 0) {
			worldpopulationlist.addAll(arraylist);
		} 
		else 
		{
			for (WorldPopulation wp : arraylist) 
			{
				if (wp.getCountry().toLowerCase(Locale.getDefault()).contains(charText)) 
				{
					worldpopulationlist.add(wp);
				}
			}
		}
		notifyDataSetChanged();
	}

}
