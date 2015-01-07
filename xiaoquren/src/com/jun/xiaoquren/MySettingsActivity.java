package com.jun.xiaoquren;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.jun.xiaoquren.util.MyAbstractActivity;

public class MySettingsActivity extends MyAbstractActivity implements OnClickListener{
	
	public static final String CLASSNAME = "MySettingsActivity";

    @Override
	public String getActivityName() {
		return CLASSNAME;
	} 
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_settings);
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
    
    public void loginOrRegisterClicked(View v) {
    	Intent intent = new Intent();
		intent.setClass(MySettingsActivity.this, AppLoginActivity.class);
		startActivity(intent);
    }
}
