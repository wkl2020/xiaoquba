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
	TextView content_text;
	TextView ower_name_text;
	TextView create_time_text;
	String id;
	String name;
	String address;
	String content;
	String ower_name;
	String create_time;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wuye_notifier_detail);
		// Retrieve data from MainActivity on item click event
		Intent i = getIntent();
		id = i.getStringExtra("id");
		name = i.getStringExtra("name");
		address = i.getStringExtra("address");
		
		content = "即日起开始收缴2014年年度物业管理费。需缴纳的业主，请前往物业管理处缴纳，如需上门收费的业主，请拨打物业管理处电话：51234566管理与上门会出具正规的统一机打税控发票上门收取。以防不法分子冒充物业管理处人员上门诈骗。";
		ower_name = "紫竹园物业管理处";
		create_time = "2014年3月11日";

		// Locate the TextViews in singleitemview.xml
		id_text = (TextView) findViewById(R.id.xiaoquid);
		name_text = (TextView) findViewById(R.id.name);
		address_text = (TextView) findViewById(R.id.address);
		content_text = (TextView) findViewById(R.id.content);
		ower_name_text = (TextView) findViewById(R.id.ower_name);
		create_time_text = (TextView) findViewById(R.id.create_time);

		// Load the results into the TextViews
		id_text.setText(id);
		name_text.setText(name);
		address_text.setText(address);
		content_text.setText(content);
		ower_name_text.setText(ower_name);
		create_time_text.setText(create_time);
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