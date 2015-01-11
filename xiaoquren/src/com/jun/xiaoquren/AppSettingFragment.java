package com.jun.xiaoquren;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jun.xiaoquren.util.LocalUtil;

public class AppSettingFragment extends Fragment {
	
	TextView loginOrRegisterBtn;
	Button logoutBtn;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
        View root = inflater.inflate(R.layout.my_settings, container, false);
        
        loginOrRegisterBtn = (TextView)root.findViewById(R.id.login_or_register_btn);
        logoutBtn = (Button)root.findViewById(R.id.logout_btn);
        logoutBtn.setOnClickListener(new LogoutOnClickListener(AppSettingFragment.this));
        
        refreshLoginInfo();    	
        LocalUtil.setAppSettingFragment(this);
        
        return root;
    }
    
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
//        LocalUtil.setSettingMainFragment(null);
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    }
    
    public void refreshLoginInfo() {
    	if (LocalUtil.isUserSessionValid(this.getActivity())) {
    		loginOrRegisterBtn.setText("个人信息");
    		logoutBtn.setVisibility(View.VISIBLE);
    	} else {
    		loginOrRegisterBtn.setText("登录/注册");
    		logoutBtn.setVisibility(View.INVISIBLE);
    	}
    }
}

class LogoutOnClickListener implements View.OnClickListener {  
	
	AppSettingFragment parentFragment = null;

    public LogoutOnClickListener(AppSettingFragment parentFragment) { 
    	this.parentFragment = parentFragment;
    }
    
  @Override  
  public void onClick(View v) {
	  
	  new AlertDialog.Builder(parentFragment.getActivity()).setTitle("确认")  
  	.setMessage("确定退出登录吗？")  
  	.setPositiveButton("是", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
          	LocalUtil.userLogout(parentFragment.getActivity());
          	parentFragment.refreshLoginInfo();
          }
  	})  
  	.setNegativeButton("否", new DialogInterface.OnClickListener() {
         @Override
         public void onClick(DialogInterface dialog, int which) {
      	   dialog.cancel();
         }
      })  
  	.show(); 
  } 
}
