package com.jun.xiaoquren.util;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public abstract class MyAbstractFragmentActivity extends FragmentActivity {
	
	public abstract String getActivityName();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocalUtil.createNewActivity(getActivityName(), this);
    }
	
	@Override
    public void finish() {
		LocalUtil.finishActiveActivity(getActivityName());
        super.finish();
    }

}
