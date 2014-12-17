package com.jun.xiaoquren;

import com.jun.xiaoquren.util.MyAbstractActivity;
import com.jun.xiaoquren.widget.ScrollLayout;
import com.jun.xiaoquren.widget.ScrollLayout.OnViewChangeListener;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class AppWelcomeActivity extends MyAbstractActivity implements OnViewChangeListener, OnClickListener {
	
	private LinearLayout pointLayout;  
    private ScrollLayout scrollLayout;  
    private Button mBtnStart;  
    private int count;  
    private ImageView[] imgs;  
    private int currentItem; 
	
    @Override
    public void onCreate(Bundle arg0) {
    	super.onCreate(arg0);    	
    	setContentView(R.layout.app_welcome);
    	
        pointLayout = (LinearLayout) findViewById(R.id.pointLayout);  
        scrollLayout = (ScrollLayout) findViewById(R.id.scrollLayout);  
        mBtnStart = (Button) findViewById(R.id.startBtn);  
        count = scrollLayout.getChildCount();  
        imgs = new ImageView[count];  
        for (int i = 0; i < count; i++) {  
            imgs[i] = (ImageView) pointLayout.getChildAt(i);  
            imgs[i].setEnabled(true);  
            imgs[i].setTag(i);  
        }  
        currentItem = 0;  
        imgs[currentItem].setEnabled(false);  
        scrollLayout.setOnViewChangeLintener(this);  
        mBtnStart.setOnClickListener(this);  
    } 
	
	@Override  
    public void onViewChange(int postion) {  
        if (postion < 0 || postion > count - 1 || currentItem == postion) {  
            return;  
        }  
        imgs[currentItem].setEnabled(true);  
        imgs[postion].setEnabled(false);  
        currentItem = postion;  
    }  
  
    public void onClick(View v) {  
    	startActivity(new Intent(this, CitySearchActivity.class));
    	this.finish();
    }

	@Override
	public String getActivityName() {
		return "FirstWelcomeActivity";
	}  
}
