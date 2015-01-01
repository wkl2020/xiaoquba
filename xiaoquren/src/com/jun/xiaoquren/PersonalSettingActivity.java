package com.jun.xiaoquren;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.jun.xiaoquren.util.MyAbstractActivity;

public class PersonalSettingActivity extends MyAbstractActivity implements OnClickListener{
	
	public static final String CLASSNAME = "PersonalSettingActivity";

    @Override
	public String getActivityName() {
		return CLASSNAME;
	} 
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_settings);
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
