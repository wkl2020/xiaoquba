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

import com.jun.xiaoquren.adapter.WuyeNotifierListViewAdapter;
import com.jun.xiaoquren.model.WuyeNotification;
import com.jun.xiaoquren.util.MyAbstractActivity;

public class WuyeNotifierMainActivity extends MyAbstractActivity implements OnClickListener{
	public static final String ACTIVITY_NAME = "WuyeNotifierMainActivity";

    @Override
	public String getActivityName() {
		return ACTIVITY_NAME;
	} 

	// Declare Variables
	ListView notifierListView;
	WuyeNotifierListViewAdapter listViewAdapter;
	EditText searchTextbox;
	String[] notificationIds;
	String[] notificationNames;
	String[] notificationAddress;
	ArrayList<WuyeNotification> notificationList = new ArrayList<WuyeNotification>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wuye_notifier_main);

		// Generate sample data
		notificationIds = new String[] { "1", "2", "3", "4"};

		notificationNames = new String[] { "2014年度物业管理费缴纳通知", "电能标调换预告", "家庭活动赛通知",
				"关于上海市黄浦区汤臣一品业主委员会换届改选小组成员名单的公示"};

		notificationAddress = new String[] { "1回复", "2回复", "3回复", "4回复"};

		// Locate the ListView in listview_main.xml
		notifierListView = (ListView) findViewById(R.id.listview);

		for (int i = 0; i < notificationIds.length; i++) 
		{
			WuyeNotification wp = new WuyeNotification(notificationIds[i], notificationNames[i], notificationAddress[i]);
			// Binds all strings into an array
			notificationList.add(wp);
		}

		// Pass results to ListViewAdapter Class
		listViewAdapter = new WuyeNotifierListViewAdapter(this, notificationList);
		
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