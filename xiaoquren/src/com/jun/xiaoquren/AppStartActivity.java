package com.jun.xiaoquren;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.jun.xiaoquren.dao.DBUtil;
import com.jun.xiaoquren.util.LocalUtil;
import com.jun.xiaoquren.util.MyAbstractActivity;
import com.lidroid.xutils.util.LogUtils;

public class AppStartActivity extends MyAbstractActivity {

	@Override
	public void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 竖屏锁定
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 取消标题
        setContentView(R.layout.app_start);
    	
    	// Create Database
    	DBUtil.initDBConnection(AppStartActivity.this);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha);
        // 监听动画过程
        animation.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            	LogUtils.i("Xiaoqu info: start to redirect to");
                redirectTo();
            }
        });
        
        ImageView imageView = (ImageView)findViewById(R.id.img_start);
        imageView.setAnimation(animation);
    }
	
	private void redirectTo() {
        Intent intent = new Intent();
        
        LogUtils.i("Xiaoqu info: isFirstTimeLogin: " + LocalUtil.isFirstTimeLogin(this));
        if (LocalUtil.isFirstTimeLogin(this)) {
        	
        	LogUtils.i("Xiaoqu info: AppWelcomeActivity.class");
            intent.setClass(this, AppWelcomeActivity.class);
        } else {
        	
        	LogUtils.i("Xiaoqu info: MainActivity.class");
            intent.setClass(this, MainActivity.class);
        }
        
        LogUtils.i("Xiaoqu info: start you trval");
        startActivity(intent);
        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
        finish();
    }

	@Override
	public String getActivityName() {
		return "AppStart";
	}

}
