package com.jun.xiaoquren.util;

import android.app.Activity;
import android.os.Bundle;

public abstract class MyAbstractActivity extends Activity {
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
