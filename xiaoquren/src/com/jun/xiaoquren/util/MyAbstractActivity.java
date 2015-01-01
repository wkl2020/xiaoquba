package com.jun.xiaoquren.util;


import java.util.Map;

import com.jun.xiaoquren.MainActivity;

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
		if (getActivityName().equals(MainActivity.CLASSNAME)) {
			Map<String, Activity> remainingActivities = LocalUtil.getAllActivities();
			for (String key: remainingActivities.keySet()) {
				MyAbstractActivity activity = (MyAbstractActivity)remainingActivities.get(key);
				if (activity != null && !activity.getActivityName().equals(MainActivity.CLASSNAME)) {
					activity.finish();
					activity = null;
				}
			}
		}
        super.finish();
    }
}
