package com.jun.xiaoquren;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.jun.xiaoquren.http.JsonTools;
import com.jun.xiaoquren.server.model.ParkingStallInfo;
import com.jun.xiaoquren.util.MyAbstractActivity;

public class ParkingViewActivity extends MyAbstractActivity implements OnClickListener{
	
	public static final String CLASSNAME = "ParkingViewActivity";
	
	TextView editBtn;
	TextView publish_supply_type;
	TextView publish_identity;
	TextView publish_title_content;
	TextView publish_address_content;
	TextView publish_desc_content;
	TextView publish_area_content;
	TextView publish_price_content;
	TextView publish_create_date_content;
	TextView publish_nickname_content;
	TextView publish_phone_content;
	
    @Override
	public String getActivityName() {
		return CLASSNAME;
	} 
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parking_view);
        
        editBtn = (TextView)findViewById(R.id.parking_edit_button);
        publish_supply_type = (TextView)findViewById(R.id.publish_supply_type);
        publish_identity = (TextView)findViewById(R.id.publish_identity);
        publish_title_content = (TextView)findViewById(R.id.publish_title_content);
        publish_address_content = (TextView)findViewById(R.id.publish_address_content);
        publish_desc_content = (TextView)findViewById(R.id.publish_desc_content);
        publish_area_content = (TextView)findViewById(R.id.publish_area_content);
        publish_price_content = (TextView)findViewById(R.id.publish_price_content);
        publish_create_date_content = (TextView)findViewById(R.id.publish_create_date_content);
        publish_nickname_content = (TextView)findViewById(R.id.publish_nickname_content);
        publish_phone_content = (TextView)findViewById(R.id.publish_phone_content);
        
        String parkingJsonstr = (String)getIntent().getSerializableExtra("parkingJsonstr");
        ParkingStallInfo parking = JsonTools.getParkingStallInfoFromJsonStr(parkingJsonstr);
        
        publish_supply_type.setText(parking.getSupplyDemandType());
        publish_identity.setText(parking.getYourIdentity());
        publish_title_content.setText(parking.getTitle());
        publish_address_content.setText(parking.getAddress());
        publish_desc_content.setText(parking.getContent());
        publish_area_content.setText(parking.getAreaMeasure() + "平方米");
        publish_price_content.setText(parking.getPrice() + parking.getPriceUnit());
        publish_nickname_content.setText(parking.getNickname());
        publish_phone_content.setText(parking.getPhone());
        publish_create_date_content.setText(parking.getCreateTime());
        
        editBtn.setVisibility(View.INVISIBLE);
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
