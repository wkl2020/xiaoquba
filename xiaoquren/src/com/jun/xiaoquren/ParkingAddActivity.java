package com.jun.xiaoquren;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.jun.xiaoquren.util.LocalViewUtil;
import com.jun.xiaoquren.util.MyAbstractActivity;

public class ParkingAddActivity extends MyAbstractActivity implements OnClickListener{
	
	public static final String CLASSNAME = "ParkingAddActivity";
	
	TextView saveBtn;
	TextView publish_supply_type;
	TextView publish_identity;
	TextView publish_unit_content;
	
    @Override
	public String getActivityName() {
		return CLASSNAME;
	} 
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parking_add);
        
        saveBtn = (TextView)findViewById(R.id.parking_save_button);
        publish_supply_type = (TextView)findViewById(R.id.publish_supply_type);
        publish_identity = (TextView)findViewById(R.id.publish_identity);
        publish_unit_content = (TextView)findViewById(R.id.publish_unit_content);
        
        saveBtn.setOnClickListener(new SaveParkingBtnOnClickListener(ParkingAddActivity.this));
        
        refreshPage();
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
    
    public void supplyTypeOnClicked(View v) {
    	Intent intent = new Intent(ParkingAddActivity.this, SearchPageActivity.class);
		intent.putExtra("listName", LocalViewUtil.Info_Search_Supply);
		intent.putExtra("parentPageName", ParkingAddActivity.CLASSNAME);				
		this.startActivity(intent);
    }

	public void refreshPage() {
		
		for (String key : LocalViewUtil.AddParkingInfoMap.keySet()) {
			
			if (LocalViewUtil.Info_Search_Supply.equals(key)) {
				publish_supply_type.setText(LocalViewUtil.AddParkingInfoMap.get(key));
				
			} else if (LocalViewUtil.Info_Search_Identity.equals(key)) {
				publish_identity.setText(LocalViewUtil.AddParkingInfoMap.get(key));
				
			} else if (LocalViewUtil.Info_Search_Unit.equals(key)) {
				publish_unit_content.setText(LocalViewUtil.AddParkingInfoMap.get(key));
				
			}
		}
		
	}

}

class SaveParkingBtnOnClickListener implements View.OnClickListener {  

	ParkingAddActivity parentView = null;

	public SaveParkingBtnOnClickListener(ParkingAddActivity parentView) { 
		this.parentView = parentView;
	}

	@Override  
	public void onClick(View v) {
		
		Toast.makeText(parentView, "SaveParkingBtnOnClick", Toast.LENGTH_LONG).show();
	

	}
}
