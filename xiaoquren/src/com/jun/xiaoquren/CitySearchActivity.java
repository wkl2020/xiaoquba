package com.jun.xiaoquren;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.jun.xiaoquren.dao.model.City;
import com.jun.xiaoquren.util.LocalUtil;
import com.jun.xiaoquren.util.MyAbstractActivity;
import com.jun.xiaoquren.view.adapter.CityListViewAdapter;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class CitySearchActivity extends MyAbstractActivity implements OnClickListener {
	
	public static final String CLASSNAME = "CitySearchActivity";

    @Override
	public String getActivityName() {
		return CLASSNAME;
	} 

	// Declare Variables
	ListView cityListView;
	CityListViewAdapter listViewAdapter;
	EditText searchTextbox;
	List<City> cityList = new ArrayList<City>();
	
	public void setCityList(List<City> list) {
		this.cityList = list;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.city_search_main);

//		String cityListJsonstr = (String)getIntent().getSerializableExtra("cityListJsonstr");
//		cityList = JsonTools.getCityList(cityListJsonstr);
		City city1 = new City();
		city1.setName("上海");
		cityList.add(city1);
		
		City city2 = new City();
		city2.setName("北京");
		cityList.add(city2);
		
		// Locate the ListView in listview_main.xml
		cityListView = (ListView) findViewById(R.id.listview);

		// Pass results to ListViewAdapter Class
		listViewAdapter = new CityListViewAdapter(this, cityList);
		
		// Binds the Adapter to the ListView
		cityListView.setAdapter(listViewAdapter);
		
		// Locate the EditText in listview_main.xml
		searchTextbox = (EditText) findViewById(R.id.search);

		// Capture Text in EditText
		searchTextbox.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable arg0) {
				String text = searchTextbox.getText().toString().toLowerCase(Locale.getDefault());
				listViewAdapter.filter(text);
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
		
		if (LocalUtil.isFirstTimeLogin(this)) {
			Button backBtn = (Button) findViewById(R.id.btn_back);
			backBtn.setVisibility(View.INVISIBLE);
		}
	}

	// Not using options menu in this page
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.btn_back:
			finish();
			break;
		}
	}

    public void page_back(View v) {
		this.finish();
    }
}
