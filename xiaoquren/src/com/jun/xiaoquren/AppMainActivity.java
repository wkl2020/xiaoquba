package com.jun.xiaoquren;

import java.io.UnsupportedEncodingException;

import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings.Secure;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.jun.xiaoquren.http.LocalHttpUtil;
import com.jun.xiaoquren.mqtt.PushService;
import com.jun.xiaoquren.util.LocalUtil;
import com.jun.xiaoquren.util.LocalViewUtil;
import com.jun.xiaoquren.util.MyAbstractFragmentActivity;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

public class AppMainActivity extends MyAbstractFragmentActivity {
	
	public static final String CLASSNAME = "AppMainActivity";
	private String mDeviceID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_main_activity);
        
        // 解决Android4.0后主线程不能访问网络异常解决办法
        // 由于对于网络状况的不可预见性，很有可能在网络访问的时候造成阻塞，那么这样一来我们的主线程UI线程 就会出现假死的现象，产生很不好的用户体验。所以，默认的情况下如果直接在主线程中访问就报出了这个异常，名字是NetworkOnMainThreadException
    	// 因为 mqtt 3.1 版本最后确定已经是一零年的事情了。当时 android 正好是 2.3 版本，当时编写的 wmqtt java 支持包是基于 android 2.3 的方法，到 android 4.0 时，可能有些网络协议的方法规定了一些其他必填的内容，导致了在 4.0 支持包下运行出错。所以必须修改最低 SDK 版本以支持旧的网络协议特性。
    	if (android.os.Build.VERSION.SDK_INT > 9) {
    		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    		StrictMode.setThreadPolicy(policy);
    	}
    	
    	mDeviceID = Secure.getString(getContentResolver(), Secure.ANDROID_ID);
    	LogUtils.i("PushService mDeviceID: " + mDeviceID);

    	Editor editor = getSharedPreferences(PushService.TAG, MODE_PRIVATE).edit();
    	editor.putString(PushService.PREF_DEVICE_ID, mDeviceID);
    	editor.commit();
    	startPushServiceListening(this);
        
        LocalUtil.finishAllOtherActivities();
    	System.out.println("XXXXXXXXXXXX1: AppMainActivity: onCreate: " + LocalUtil.getAllActivities().size());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

	@Override
	public String getActivityName() {
		return CLASSNAME;
	}
	
	@Override
    public void finish() {
//    	DBUtil.closeDBConnection(this);
        super.finish();
        LocalUtil.finishAllOtherActivities();
    	System.out.println("XXXXXXXXXXXX2: AppMainActivity: finish: " + LocalUtil.getAllActivities().size());
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    	LocalUtil.finishAllOtherActivities();
    	System.out.println("XXXXXXXXXXXX3: AppMainActivity: OnResume: " + LocalUtil.getAllActivities().size());
    }
    
    public void startPushServiceListening(final FragmentActivity activity) {
		new Thread() {
			@Override
			public void run() {
				PushService.actionStart(activity.getApplicationContext());
			}
		}.start();
    }
    
    public void stopPushServiceListening() {
		PushService.actionStop(this.getApplicationContext());
    }
    
    public void wuyenotifieronclick(final View v) {
    	String currentXiaoquId = LocalUtil.getCurrentXiaoQuId(this);
    	LogUtils.i("Start to connect xiaoqu documents with xiaoqu id: " + currentXiaoquId);
    	LocalHttpUtil.getDefaultHttpUtils().send(HttpRequest.HttpMethod.GET, LocalHttpUtil.XiaoquDocumentsUrl+currentXiaoquId, new RequestCallBack<String>() {

            @Override
            public void onStart() {
            	LogUtils.i("Start to connect xiaoqu documents... ");
            }

            @Override
            public void onLoading(long total, long current, boolean isUploading) {
            	LogUtils.i("On loading to connect xiaoqu documents: " + current + "/" + total);
            }

  			@Override
  			public void onFailure(HttpException error, String msg) {
  				LogUtils.i("Error to connect xiaoqu documents: " + msg);
  			}

  			@Override
  			public void onSuccess(ResponseInfo<String> response) {
  				LogUtils.i("Success to connect xiaoqu documents: " + response.result.toString());
  				String xiaoquDocumentsJsonstr = response.result.toString(); 
  				
  				Intent intent = new Intent();
  				intent.setClass(v.getContext(), WuyeNotifierMainActivity.class);
  			    intent.putExtra("xiaoquDocumentsJsonstr", xiaoquDocumentsJsonstr);
  			    startActivity(intent);    	
  			}
  		});
    }
    
    public void phonenumbersonclick(View v) { 
        Intent intent = new Intent();
		intent.setClass(this, PersonalSettingActivity.class);
		startActivity(intent);
    }
    
    public void parkingonclick(final View v) { 

    	if (!LocalUtil.getCurrentXiaoQuId(this).isEmpty()) {
    		String parkingStallInfosJsonstr = "[]"; 
			
			Intent intent = new Intent();
			intent.setClass(v.getContext(), ParkingMainActivity.class);
		    intent.putExtra("parkingStallInfosJsonstr", parkingStallInfosJsonstr);
		    startActivity(intent);
    	}
//    	System.out.println("Start to connect xiaoqu ParkingStallInfos with xiaoqu id: " + currentXiaoquId);
//    	
//    	RequestParams params = new RequestParams();
//		try {
//			
//			JSONObject commentJson = new JSONObject();
//			commentJson.put("xiaoquId", currentXiaoquId);
//			commentJson.put("supplyDemandType", LocalViewUtil.Info_Search_Supply_Value_First);
//			commentJson.put("yourIdentity", LocalViewUtil.Info_Search_Identity_Value_First);
//			commentJson.put("rows", 10);
//			commentJson.put("page", 1);
//			commentJson.put("sidx", "create_date");
//			commentJson.put("sord", "desc");
//			
//			LogUtils.i("CommentJSON: " + commentJson.toString());
//			
//			params.setContentType("application/json;charset=UTF-8");
//			params.setBodyEntity(new StringEntity(commentJson.toString(),"UTF-8"));
//		} catch (UnsupportedEncodingException e) {
//			
//			LogUtils.i("Error occured UnsupportedEncodingException: " + e.getMessage());
//			e.printStackTrace();
//		} catch (JSONException e) {
//			
//			LogUtils.i("Error occured JSONException: " + e.getMessage());
//			e.printStackTrace();
//		}
//		
//		HttpUtils http = new HttpUtils();
//        http.send(HttpRequest.HttpMethod.POST,
//        		LocalHttpUtil.ParkingInfoSearchUrl,
//                params,
//                new RequestCallBack<String>() {
//
//                    @Override
//                    public void onStart() {
//                    	LogUtils.i("onStart conn...");
//                    }
//
//                    @Override
//                    public void onLoading(long total, long current, boolean isUploading) {
//                    	LogUtils.i("onLoading " + current + "/" + total);
//                    }
//
//                    @Override
//                    public void onSuccess(ResponseInfo<String> response) {
//                    	LogUtils.i("onSuccess response:" + response.result);
//                    	System.out.println("Success to connect xiaoqu ParkingStallInfos: " + response.result.toString());
//          				String parkingStallInfosJsonstr = response.result.toString(); 
//          				
//          				Intent intent = new Intent();
//          				intent.setClass(v.getContext(), ParkingMainActivity.class);
//          			    intent.putExtra("parkingStallInfosJsonstr", parkingStallInfosJsonstr);
//          			    startActivity(intent);
//                    }
//
//                    @Override
//                    public void onFailure(HttpException error, String msg) {
//                    	LogUtils.i("onFailure " + msg);
//                    }
//                });
    }
    
    public void shopptingsonclick(View v) {
//        Intent intent = new Intent();
//		intent.setClass(this, PersonalSettingActivity.class);
//		startActivity(intent);
    }
    
    public void waimaionclick(View v) {
    	
    }
    
    public void pingcheonclick(final View v) {

    	String currentXiaoquId = LocalUtil.getCurrentXiaoQuId(this);
    	LogUtils.i("Start to connect xiaoqu ParkingStallInfos with xiaoqu id: " + currentXiaoquId);
    	LocalHttpUtil.getDefaultHttpUtils().send(HttpRequest.HttpMethod.GET, LocalHttpUtil.XiaoquParkingStallInfosUrl+currentXiaoquId, new RequestCallBack<String>() {

            @Override
            public void onStart() {
            	LogUtils.i("Start to connect xiaoqu ParkingStallInfos... ");
            }

            @Override
            public void onLoading(long total, long current, boolean isUploading) {
            	LogUtils.i("On loading to connect xiaoqu ParkingStallInfos: " + current + "/" + total);
            }

  			@Override
  			public void onFailure(HttpException error, String msg) {
  				LogUtils.i("Error to connect xiaoqu ParkingStallInfos: " + msg);
  			}

  			@Override
  			public void onSuccess(ResponseInfo<String> response) {
  				LogUtils.i("Success to connect xiaoqu ParkingStallInfos: " + response.result.toString());
  				String parkingStallInfosJsonstr = response.result.toString(); 
  				
  				Intent intent = new Intent();
  				intent.setClass(v.getContext(), ParkingMainActivity.class);
  			    intent.putExtra("parkingStallInfosJsonstr", parkingStallInfosJsonstr);
  			    startActivity(intent);    	
  			}
  		});
    }
    
    

    /**
     * My Settings Page
     * @param v
     */
    public void loginOrRegisterClicked(View v) {
    	
    	if (LocalUtil.isUserSessionValid(AppMainActivity.this)) {
    		// go to personal information page
            Intent intent = new Intent();
    		intent.setClass(AppMainActivity.this, PersonalSettingActivity.class);
    		startActivity(intent);
    		
    	} else {
	    	Intent intent = new Intent();
			intent.setClass(AppMainActivity.this, AppLoginActivity.class);
			startActivity(intent);
    	}
    }

}
