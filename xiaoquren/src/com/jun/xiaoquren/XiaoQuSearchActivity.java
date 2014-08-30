package com.jun.xiaoquren;

import java.util.ArrayList;
import java.util.Locale;

import com.jun.xiaoquren.adapter.ListViewAdapter;
import com.jun.xiaoquren.model.WorldPopulation;
import com.jun.xiaoquren.util.MyAbstractActivity;

import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.widget.EditText;
import android.widget.ListView;

public class XiaoQuSearchActivity extends MyAbstractActivity {
	public static final String ACTIVITY_NAME = "XiaoQuSearchActivity";

    @Override
	public String getActivityName() {
		return ACTIVITY_NAME;
	} 

	// Declare Variables
	ListView list;
	ListViewAdapter adapter;
	EditText editsearch;
	String[] rank;
	String[] country;
	String[] population;
	ArrayList<WorldPopulation> arraylist = new ArrayList<WorldPopulation>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_main);

		// Generate sample data
		rank = new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };

		country = new String[] { "新开家园", "音都雅苑", "上海康城",
				"上海国际豪都花园", "汤臣一品", "汤臣二品", "汤臣三品", "汤臣四品",
				"汤臣五品", "汤臣六品" };

		population = new String[] { "南京西路1899号", "南京西路1899号",
				"南京西路1899号", "南京西路1899号", "南京西路1899号", "南京西路1899号",
				"南京西路1899号", "南京西路1899号", "南京西路1899号", "南京西路1899号" };

		// Locate the ListView in listview_main.xml
		list = (ListView) findViewById(R.id.listview);

		for (int i = 0; i < rank.length; i++) 
		{
			WorldPopulation wp = new WorldPopulation(rank[i], country[i],
					population[i]);
			// Binds all strings into an array
			arraylist.add(wp);
		}

		// Pass results to ListViewAdapter Class
		adapter = new ListViewAdapter(this, arraylist);
		
		// Binds the Adapter to the ListView
		list.setAdapter(adapter);
		
		// Locate the EditText in listview_main.xml
		editsearch = (EditText) findViewById(R.id.search);

		// Capture Text in EditText
		editsearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				String text = editsearch.getText().toString().toLowerCase(Locale.getDefault());
				adapter.filter(text);
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
			}
		});
	}

	// Not using options menu in this tutorial
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}