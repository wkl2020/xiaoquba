package com.jun.xiaoquren.util;

import java.util.HashMap;
import java.util.Map;

import com.jun.xiaoquren.AppMainActivity;
import com.jun.xiaoquren.AppMainFragment;
import com.jun.xiaoquren.AppSettingFragment;
import com.jun.xiaoquren.dao.ConstantTableDao;
import com.jun.xiaoquren.dao.model.ConstantTable;
import com.lidroid.xutils.util.LogUtils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class LocalUtil {
	
	public static final String CLASSNAME = "LocalUtil";
	
	public static Map<String, Activity> activeActivities = new HashMap<String, Activity>();
	
	public static void createNewActivity(String activityName, Activity activity) {
		Activity previousActivity = activeActivities.get(activityName);
		if (previousActivity != null) {
			previousActivity.finish();
			previousActivity = null;
		}
		
		activeActivities.put(activityName, activity);
	}
	
	public static boolean isActiveActivityExists(String activityName) {
		return activeActivities.get(activityName) != null;
	}
	
	public static Activity getActiveActivity(String activityName) {
		return activeActivities.get(activityName);
	}
	
	public static void finishActiveActivity(String activityName) {
		activeActivities.remove(activityName);
	}
	
	public static Map<String, Activity> getAllActivities() {
		return activeActivities;
	}
	
	public static void finishAllOtherActivities() {
		Map<String, Activity> remainingActivities = LocalUtil.getAllActivities();
		for (String key: remainingActivities.keySet()) {
			if (!key.equals(AppMainActivity.CLASSNAME)) {
				MyAbstractActivity activity = (MyAbstractActivity)remainingActivities.get(key);
				activity.finish();
				activity = null;
			}
		}
	}
	
	public static AppMainFragment appMainFragment = null;
	
	public static void setAppMainFragment(AppMainFragment fragment) {
		appMainFragment = fragment;
	}
	
	public static AppMainFragment getAppMainFragment() {
		return appMainFragment;
	}
	
	public static boolean isAppMainFragmentExists() {
		return appMainFragment != null;
	}
	
	public static AppSettingFragment appSettingFragment = null;
	
	public static void setAppSettingFragment(AppSettingFragment fragment) {
		appSettingFragment = fragment;
	}
	
	public static AppSettingFragment getAppSettingFragment() {
		return appSettingFragment;
	}
	
	public static boolean isAppSettingFragmentExists() {
		return appSettingFragment != null;
	}
	
	
	
	
	/**
	 * Set project constants cache content as below
	 */
	public static void syncConstantsAndSharedPreferences() {
		// TODO
		// ....
	}
	
	private final static String USERROLE = "userrole";
	private final static String NICKNAME = "nickname";
	private final static String JSESSIONID = "jsessionid";
	private final static String USERNAME = "username";
	private final static String PASSWORD = "password";
	private final static String CURRENT_XIAOQU_NAME = "currentxiaoquname";
	private final static String CURRENT_XIAOQU_ID = "currentxiaoquid";
	private final static String CURRENT_CITY_NAME = "currentcityname";
	private final static String CURRENT_CITY_ID = "currentcityid";
	
	/**
     * 使用SharedPreferences保存用户登录信息
     * @param context
     * @param username
     * @param password
     */
    public static void saveLoginInfo(Context context,String username,String password, 
    		String jsessionId, String nickname, String userrole){
    	
        SharedPreferences sharedPre=context.getSharedPreferences("config", context.MODE_PRIVATE);
        Editor editor=sharedPre.edit();
        
        editor.putString(USERNAME, username);
        editor.putString(PASSWORD, password);
        editor.putString(NICKNAME, nickname);
        editor.putString(USERROLE, userrole);
        editor.putString(JSESSIONID, jsessionId);
        
        // Save to database
        ConstantTable usrCon = ConstantTableDao.findByName(ConstantTableDao.ConstantsUsername);
        ConstantTable pwdCon = ConstantTableDao.findByName(ConstantTableDao.ConstantsPassword);        
        usrCon.setFieldvalue(username);
        pwdCon.setFieldvalue(password);        
        ConstantTableDao.updateByName(usrCon);
        ConstantTableDao.updateByName(pwdCon);
        
        //提交
        editor.commit();
    }
    
    public static void saveNicknameAndUsrrole(Context context, String nickname, String userrole) {
    	SharedPreferences sharedPre=context.getSharedPreferences("config", context.MODE_PRIVATE);
        Editor editor=sharedPre.edit();

        editor.putString(NICKNAME, nickname);
        editor.putString(USERROLE, userrole);
        editor.commit();
    }
    
    public static void saveCurrentCity(Context context,String cityName,String cityId){
        //获取SharedPreferences对象
        SharedPreferences sharedPre=context.getSharedPreferences("config", context.MODE_PRIVATE);
        //获取Editor对象
        Editor editor=sharedPre.edit();
        //设置参数
        editor.putString(CURRENT_CITY_NAME, cityName);
        editor.putString(CURRENT_CITY_ID, cityId);
        editor.putBoolean(IsFirstTimeLogin, false);
        LogUtils.i("Start to init IsFirstTimeLogin to false");
        
        //提交
        editor.commit();
    }
    
    public static String getCurrentCityName(Context context) {
    	SharedPreferences sharedPre=context.getSharedPreferences("config", context.MODE_PRIVATE);
        return sharedPre.getString(CURRENT_CITY_NAME, "请选择城市...");
    }
    
    public static String getCurrentCityId(Context context) {
    	SharedPreferences sharedPre=context.getSharedPreferences("config", context.MODE_PRIVATE);
        return sharedPre.getString(CURRENT_CITY_ID, "");
    }

    public static void saveCurrentXiaoQu(Context context,String xiaoquName,String xiaoquId){
        //获取SharedPreferences对象
        SharedPreferences sharedPre=context.getSharedPreferences("config", context.MODE_PRIVATE);
        //获取Editor对象
        Editor editor=sharedPre.edit();
        //设置参数
        editor.putString(CURRENT_XIAOQU_NAME, xiaoquName);
        editor.putString(CURRENT_XIAOQU_ID, xiaoquId);
        editor.putBoolean(IsFirstTimeLogin, false);
        LogUtils.i("Start to init IsFirstTimeLogin to false");
        
        // Save to database
        ConstantTable xiaoquIdCon = ConstantTableDao.findByName(ConstantTableDao.ConstantsCurrentXiaoquId);
        ConstantTable xiaoquNameCon = ConstantTableDao.findByName(ConstantTableDao.ConstantsCurrentXiaoquName);        
        xiaoquIdCon.setFieldvalue(xiaoquId);
        xiaoquNameCon.setFieldvalue(xiaoquName);        
        ConstantTableDao.updateByName(xiaoquIdCon);
        ConstantTableDao.updateByName(xiaoquNameCon);
        
        //提交
        editor.commit();
    }
    
    public static String getCurrentXiaoQuName(Context context) {
    	SharedPreferences sharedPre=context.getSharedPreferences("config", context.MODE_PRIVATE);
        return sharedPre.getString(CURRENT_XIAOQU_NAME, "请选择小区...");
    }
    
    public static String getCurrentXiaoQuId(Context context) {
    	SharedPreferences sharedPre=context.getSharedPreferences("config", context.MODE_PRIVATE);
    	return sharedPre.getString(CURRENT_XIAOQU_ID, "");
    }
    
    public static String getUsername(Context context) {
    	SharedPreferences sharedPre=context.getSharedPreferences("config", context.MODE_PRIVATE);
    	return sharedPre.getString(USERNAME, "");
    }
    
    public static String getPassword(Context context) {
    	SharedPreferences sharedPre=context.getSharedPreferences("config", context.MODE_PRIVATE);
    	return sharedPre.getString(PASSWORD, "");
    }
    
    public static String getNickname(Context context) {
    	SharedPreferences sharedPre=context.getSharedPreferences("config", context.MODE_PRIVATE);
    	return sharedPre.getString(NICKNAME, "游客");
    }
    
    public static String getJSessionId(Context context) {
    	SharedPreferences sharedPre=context.getSharedPreferences("config", context.MODE_PRIVATE);
    	return sharedPre.getString(JSESSIONID, "");
    }
    
    public static boolean isUserSessionValid(Context context) {
    	String sessionId = getJSessionId(context);
    	
    	// TODO
    	
    	return sessionId != null && !sessionId.isEmpty();
    }
    
    public static void userLogout(Context context) {
    	SharedPreferences sharedPre=context.getSharedPreferences("config", context.MODE_PRIVATE);
        Editor editor=sharedPre.edit();
        
        editor.remove(USERNAME);
        editor.remove(PASSWORD);
        editor.remove(NICKNAME);
        editor.remove(USERROLE);
        editor.remove(JSESSIONID);
        
        //提交
        editor.commit();
    }
    
    public static String getUserrole(Context context) {
    	SharedPreferences sharedPre=context.getSharedPreferences("config", context.MODE_PRIVATE);
    	return sharedPre.getString(USERROLE, "");
    }
    
    
    /**
     * 使用SharedPreferences保存第一次登陆的信息
     */    
    private final static String IsFirstTimeLogin = "isfirsttimelogin";
    
    public static boolean isFirstTimeLogin(Context context) {
    	SharedPreferences sharedPre=context.getSharedPreferences("config", context.MODE_WORLD_READABLE);
    	LogUtils.i("Get IsFirstTimeLogin value: " + sharedPre.getBoolean(IsFirstTimeLogin, true));
    	return sharedPre.getBoolean(IsFirstTimeLogin, true);
    }
}
