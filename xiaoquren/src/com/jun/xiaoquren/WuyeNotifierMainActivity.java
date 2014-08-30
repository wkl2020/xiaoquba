package com.jun.xiaoquren;

import java.util.ArrayList;
import java.util.Locale;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.jun.xiaoquren.adapter.XiaoquListViewAdapter;
import com.jun.xiaoquren.model.Xiaoqu;
import com.jun.xiaoquren.util.MyAbstractActivity;

public class WuyeNotifierMainActivity extends MyAbstractActivity implements OnClickListener{
	public static final String ACTIVITY_NAME = "WuyeNotifierMainActivity";

    @Override
	public String getActivityName() {
		return ACTIVITY_NAME;
	} 

	// Declare Variables
	ListView notifierListView;
	XiaoquListViewAdapter listViewAdapter;
	EditText searchTextbox;
	String[] xiaoquIds;
	String[] xiaoquNames;
	String[] xiaoquAddress;
	ArrayList<Xiaoqu> xiaoquList = new ArrayList<Xiaoqu>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wuye_notifier_main);

		// Generate sample data
		xiaoquIds = new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };

		xiaoquNames = new String[] { "asdf", "音asdf雅苑", "上asdf海康城",
				"上海国际豪都asdf花园", "汤臣asdf一品", "asdf汤臣二品", "汤asdf臣三品", "汤asdf臣四品",
				"汤臣asdf五品", "汤臣asdf六品" };

		xiaoquAddress = new String[] { "南京西路asdf1899号", "南asdf京西路1899号",
				"南京西路asdf1899号", "南京西路189asdf9号", "南京西路1asdf899号", "南京asdf西路1899号",
				"南京西asdf路1899号", "南asdasdff京西路1899号", "南京西路asdf1899号", "南京asdf西路1899号" };

		// Locate the ListView in listview_main.xml
		notifierListView = (ListView) findViewById(R.id.listview);

		for (int i = 0; i < xiaoquIds.length; i++) 
		{
			Xiaoqu wp = new Xiaoqu(xiaoquIds[i], xiaoquNames[i], xiaoquAddress[i]);
			// Binds all strings into an array
			xiaoquList.add(wp);
		}

		// Pass results to ListViewAdapter Class
		listViewAdapter = new XiaoquListViewAdapter(this, xiaoquList);
		
		// Binds the Adapter to the ListView
		notifierListView.setAdapter(listViewAdapter);
		
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
		
//		Button btnBack = (Button) findViewById(R.id.btn_back);
//		btnBack.setFocusable(true);
//		btnBack.requestFocus();
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