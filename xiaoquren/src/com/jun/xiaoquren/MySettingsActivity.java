//package com.jun.xiaoquren;
//
//import android.app.AlertDialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.TextView;
//
//import com.jun.xiaoquren.util.LocalUtil;
//import com.jun.xiaoquren.util.MyAbstractActivity;
//
//public class MySettingsActivity extends MyAbstractActivity implements OnClickListener{
//	
//	public static final String CLASSNAME = "MySettingsActivity";
//	
//	TextView loginOrRegisterBtn;
//	Button logoutBtn;
//	
//    @Override
//	public String getActivityName() {
//		return CLASSNAME;
//	} 
//	
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.my_settings);
//        
//        loginOrRegisterBtn = (TextView)findViewById(R.id.login_or_register_btn);
//        logoutBtn = (Button)findViewById(R.id.logout_btn);
//        refreshLoginInfo();
//    }
//
//	@Override
//	public void onClick(View v) {
//		switch(v.getId()) {
//		case R.id.btn_back:
//			finish();
//			break;
//		}
//	}
//
//    public void page_back(View v) {
//		this.finish();
//    }
//    
//    public void loginOrRegisterClicked(View v) {
//    	
//    	if (LocalUtil.isUserSessionValid(MySettingsActivity.this)) {
//    		// go to personal information page
//            Intent intent = new Intent();
//    		intent.setClass(MySettingsActivity.this, PersonalSettingActivity.class);
//    		startActivity(intent);
//    		
//    	} else {
//	    	Intent intent = new Intent();
//			intent.setClass(MySettingsActivity.this, AppLoginActivity.class);
//			startActivity(intent);
//    	}
//    }
//    
//    public void refreshLoginInfo() {
//    	if (LocalUtil.isUserSessionValid(MySettingsActivity.this)) {
//    		loginOrRegisterBtn.setText("个人信息");
//    		logoutBtn.setVisibility(View.VISIBLE);
//    	} else {
//    		loginOrRegisterBtn.setText("登录/注册");
//    		logoutBtn.setVisibility(View.INVISIBLE);
//    	}
//    }
//    
//    public void exit_settings(View v) {
//    	new AlertDialog.Builder(this).setTitle("确认")  
//	    	.setMessage("确定退出登录吗？")  
//	    	.setPositiveButton("是", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                	LocalUtil.userLogout(MySettingsActivity.this);
//                	refreshLoginInfo();
////                	MySettingsActivity.this.finish();
//                }
//	    	})  
//	    	.setNegativeButton("否", new DialogInterface.OnClickListener() {
//	           @Override
//	           public void onClick(DialogInterface dialog, int which) {
//	        	   dialog.cancel();
//	           }
//	        })  
//	    	.show(); 
//    }
//}
