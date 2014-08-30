package com.jun.xiaoquren;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.jun.xiaoquren.util.MyAbstractActivity;

public class WuyeNotifierDetailActivity extends MyAbstractActivity implements OnClickListener{
	public static final String ACTIVITY_NAME = "WuyeNotifierDetailActivity";

    @Override
	public String getActivityName() {
		return ACTIVITY_NAME;
	} 
    
	// Declare Variables
	TextView id_text;
	TextView name_text;
	TextView address_text;
	String id;
	String name;
	String address;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wuye_notifier_detail);
		// Retrieve data from MainActivity on item click event
		Intent i = getIntent();
		// Get the results of rank
		id = i.getStringExtra("id");
		// Get the results of country
		name = i.getStringExtra("name");
		// Get the results of population
		address = i.getStringExtra("address");

		// Locate the TextViews in singleitemview.xml
		id_text = (TextView) findViewById(R.id.xiaoquid);
		name_text = (TextView) findViewById(R.id.name);
		address_text = (TextView) findViewById(R.id.address);

		// Load the results into the TextViews
		id_text.setText(id);
		name_text.setText(name);
		address_text.setText(address);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}