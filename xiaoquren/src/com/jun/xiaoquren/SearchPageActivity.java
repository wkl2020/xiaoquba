package com.jun.xiaoquren;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.jun.xiaoquren.util.LocalViewUtil;
import com.jun.xiaoquren.util.MyAbstractActivity;
import com.jun.xiaoquren.view.adapter.SearchPageViewAdapter;

public class SearchPageActivity  extends MyAbstractActivity {

	public static final String CLASSNAME = "SearchPageActivity";

    @Override
	public String getActivityName() {
		return CLASSNAME;
	} 

	// Declare Variables
	ListView searchListView;
	SearchPageViewAdapter listViewAdapter;
	List<String> searchKeyList = new ArrayList<String>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_page);
		searchListView = (ListView) findViewById(R.id.listview);		
		searchKeyList.add(LocalViewUtil.Info_Search_First);
		searchKeyList.add(LocalViewUtil.Info_Search_Second);
		searchKeyList.add(LocalViewUtil.Info_Search_Third);
		searchKeyList.add(LocalViewUtil.Info_Search_Fourth);
		
		String listName = (String)getIntent().getSerializableExtra("listName");
		listViewAdapter = new SearchPageViewAdapter(this, listName);
		
		// Binds the Adapter to the ListView
		searchListView.setAdapter(listViewAdapter);
	}

	// Not using options menu in this page
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

    public void page_back(View v) {
		this.finish();
    }
}