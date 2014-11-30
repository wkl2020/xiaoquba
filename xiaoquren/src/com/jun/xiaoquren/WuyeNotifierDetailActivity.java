package com.jun.xiaoquren;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.jun.xiaoquren.dao.model.Document;
import com.jun.xiaoquren.util.MyAbstractActivity;

public class WuyeNotifierDetailActivity extends MyAbstractActivity implements OnClickListener{
	public static final String ACTIVITY_NAME = "WuyeNotifierDetailActivity";

    @Override
	public String getActivityName() {
		return ACTIVITY_NAME;
	} 
    
	// Declare Variables
	TextView id_text;
	TextView title_text;
	TextView subtitle_text;
	TextView content_text;
	TextView ower_name_text;
	TextView create_time_text;	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wuye_notifier_detail);
		// Retrieve data from MainActivity on item click event
		Document document = (Document)getIntent().getSerializableExtra("document");

		// Locate the TextViews in singleitemview.xml
		id_text = (TextView) findViewById(R.id.document_detail_id);
		title_text = (TextView) findViewById(R.id.document_detail_title);
		subtitle_text = (TextView) findViewById(R.id.document_detail_subtitle);
		content_text = (TextView) findViewById(R.id.document_detail_content);
		ower_name_text = (TextView) findViewById(R.id.document_detail_ower_name);
		create_time_text = (TextView) findViewById(R.id.document_detail_create_time);

		// Load the results into the TextViews
		id_text.setText(document.getStringId());
		title_text.setText(document.getTitle());
		subtitle_text.setText(document.getCreatetime());
		content_text.setText(document.getContent());
		ower_name_text.setText(document.getOwner());
		create_time_text.setText(document.getCreatetime());
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