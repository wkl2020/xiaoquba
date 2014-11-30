package com.jun.xiaoquren.util;

import java.util.HashMap;
import java.util.Map;

import com.jun.xiaoquren.dao.ConstantTableDao;
import com.jun.xiaoquren.dao.model.ConstantTable;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class LocalUtil {
	
	public static Map<String, Activity> activeActivities = new HashMap<String, Activity>();
	
	private final static String USERNAME = "username";
	private final static String PASSWORD = "password";
	private final static String CURRENT_XIAOQU_NAME = "currentxiaoquname";
	private final static String CURRENT_XIAOQU_ID = "currentxiaoquid";
	
	public static void createNewActivity(String activityName, Activity activity) {
		activeActivities.put(activityName, activity);
	}
	
	public static Activity getActiveActivity(String activityName) {
		return activeActivities.get(activityName);
	}
	
	public static void finishActiveActivity(String activityName) {
		activeActivities.remove(activityName);
	}
	
	
	public static void syncConstantsAndSharedPreferences() {
		// TODO
		// ....
	}
	
	/**
     * 使用SharedPreferences保存用户登录信息
     * @param context
     * @param username
     * @param password
     */
    public static void saveLoginInfo(Context context,String username,String password){
        //获取SharedPreferences对象
        SharedPreferences sharedPre=context.getSharedPreferences("config", context.MODE_PRIVATE);
        //获取Editor对象
        Editor editor=sharedPre.edit();
        //设置参数
        editor.putString(USERNAME, username);
        editor.putString(PASSWORD, password);
        
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

    public static void saveCurrentXiaoQu(Context context,String xiaoquName,String xiaoquId){
        //获取SharedPreferences对象
        SharedPreferences sharedPre=context.getSharedPreferences("config", context.MODE_PRIVATE);
        //获取Editor对象
        Editor editor=sharedPre.edit();
        //设置参数
        editor.putString(CURRENT_XIAOQU_NAME, xiaoquName);
        editor.putString(CURRENT_XIAOQU_ID, xiaoquId);
        
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
        return sharedPre.getString(CURRENT_XIAOQU_NAME, "");
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
}
