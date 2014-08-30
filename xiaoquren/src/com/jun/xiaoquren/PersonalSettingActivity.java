package com.jun.xiaoquren;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.jun.xiaoquren.util.MyAbstractActivity;

public class PersonalSettingActivity extends MyAbstractActivity implements OnClickListener{
	public static final String ACTIVITY_NAME = "PersonalSettingActivity";

    @Override
	public String getActivityName() {
		return ACTIVITY_NAME;
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

    public void chat_back(View v) {
		this.finish();
    }
}
