package com.jun.xiaoquren;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.jun.xiaoquren.util.MyAbstractActivity;

public class ParkingAddActivity extends MyAbstractActivity implements OnClickListener{
	
	public static final String CLASSNAME = "ParkingAddActivity";
	
	TextView saveBtn;
	
    @Override
	public String getActivityName() {
		return CLASSNAME;
	} 
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parking_add);
        
        saveBtn = (TextView)findViewById(R.id.parking_save_button);
        saveBtn.setOnClickListener(new SaveParkingBtnOnClickListener(ParkingAddActivity.this));
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
