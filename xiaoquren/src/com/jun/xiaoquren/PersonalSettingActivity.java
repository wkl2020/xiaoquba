package com.jun.xiaoquren;

import com.jun.xiaoquren.util.MyAbstractActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class PersonalSettingActivity extends MyAbstractActivity implements OnClickListener{
	public static final String ACTIVITY_NAME = "PersonalSettingActivity";

    @Override
	public String getActivityName() {
		return ACTIVITY_NAME;
	} 
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_tab_settings);
    }


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.btn_back:
			finish();
			break;
		}
	}
	

    public void chat_back(View v) {
		this.finish();
    }
    
//    public void welcome_login(View v) {  
//      	Intent intent = new Intent();
//		intent.setClass(Welcome.this,Login.class);
//		startActivity(intent);
//		//this.finish();
//      }  
//    public void welcome_register(View v) {  
//      	Intent intent = new Intent();
//		intent.setClass(Welcome.this,MainWeixin.class);
//		startActivity(intent);
//		//this.finish();
//      }  
   
}
