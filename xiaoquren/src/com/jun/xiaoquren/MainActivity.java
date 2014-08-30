package com.jun.xiaoquren;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jun.xiaoquren.util.LocalUtil;
import com.jun.xiaoquren.util.MyAbstractActivity;

public class MainActivity extends MyAbstractActivity {
	public static final String ACTIVITY_NAME = "MainActivity";

    @Override
	public String getActivityName() {
		return ACTIVITY_NAME;
	}
	 
    @Override
    public void onCreate(Bundle arg0) {
    	super.onCreate(arg0);    	
    	setContentView(R.layout.main_activity);
    	
    	String xiaoquName = LocalUtil.getCurrentXiaoQuName(this);
    	if (xiaoquName == null || xiaoquName.isEmpty()) {
    		xiaoquName = "新凯家园";
    	}
    	
    	TextView currentXiaoquName = (TextView)findViewById(R.id.current_xiaoqu_name);
    	currentXiaoquName.setText(xiaoquName);
    	
    	currentXiaoquName.setOnClickListener(new XiaoquNameOnClickListener(this, MainActivity.this));
 
    }
    
    public void refreshCurrentXiaoQuName() {
    	String xiaoquName = LocalUtil.getCurrentXiaoQuName(this);
    	if (xiaoquName == null || xiaoquName.isEmpty()) {
    		xiaoquName = "新凯家园";
    	}
    	
    	TextView currentXiaoquName = (TextView)findViewById(R.id.current_xiaoqu_name);
    	currentXiaoquName.setText(xiaoquName);
    }
    
    public void personalsettings(View v) {  
        Intent intent = new Intent();
		intent.setClass(MainActivity.this, PersonalSettingActivity.class);
		startActivity(intent);
    }
    
    public void wuyenotifieronclick(View v) { 
        Intent intent = new Intent();
		intent.setClass(MainActivity.this, PersonalSettingActivity.class);
		startActivity(intent);    	
    }

}

class XiaoquNameOnClickListener implements View.OnClickListener {  
	
	MainActivity parentView = null;
	Context context = null;

    public XiaoquNameOnClickListener(MainActivity parentView, Context context) { 
    	this.parentView = parentView;
    	this.context = context;
    }
    
  @Override  
  public void onClick(View v) {
	  
	  Intent intent = new Intent();
	  intent.setClass(parentView, XiaoquSearchActivity.class);
	  parentView.startActivity(intent); 
  } 
}
