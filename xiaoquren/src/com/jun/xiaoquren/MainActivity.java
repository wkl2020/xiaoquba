package com.jun.xiaoquren;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings.Secure;
import android.view.View;
import android.widget.TextView;
import com.jun.xiaoquren.dao.ConstantTableDao;
import com.jun.xiaoquren.dao.DBUtil;
import com.jun.xiaoquren.dao.DocumentDao;
import com.jun.xiaoquren.dao.XiaoquListDao;
import com.jun.xiaoquren.dao.model.ConstantTable;
import com.jun.xiaoquren.dao.model.Document;
import com.jun.xiaoquren.dao.model.LocalXiaoqu;
import com.jun.xiaoquren.http.LocalHttpUtil;
import com.jun.xiaoquren.http.XiaoquHttp;
import com.jun.xiaoquren.mqtt.PushService;
import com.jun.xiaoquren.util.LocalUtil;
import com.jun.xiaoquren.util.MyAbstractActivity;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

public class MainActivity extends MyAbstractActivity {
	
	public static final String CLASSNAME = "MainActivity";
	private String mDeviceID;

    @Override
	public String getActivityName() {
		return CLASSNAME;
	}
	 
    @Override
    public void onCreate(Bundle arg0) {
    	super.onCreate(arg0);    	
    	setContentView(R.layout.main_activity);
    	
    	String xiaoquName = LocalUtil.getCurrentXiaoQuName(this);    	
    	TextView currentXiaoquName = (TextView)findViewById(R.id.current_xiaoqu_name);
    	currentXiaoquName.setText(xiaoquName);
    	
    	currentXiaoquName.setOnClickListener(new XiaoquNameOnClickListener(this, MainActivity.this));
    	
    	// 解决Android4.0后主线程不能访问网络异常解决办法
    	// 由于对于网络状况的不可预见性，很有可能在网络访问的时候造成阻塞，那么这样一来我们的主线程UI线程 就会出现假死的现象，产生很不好的用户体验。所以，默认的情况下如果直接在主线程中访问就报出了这个异常，名字是NetworkOnMainThreadException
    	// 因为 mqtt 3.1 版本最后确定已经是一零年的事情了。当时 android 正好是 2.3 版本，当时编写的 wmqtt java 支持包是基于 android 2.3 的方法，到 android 4.0 时，可能有些网络协议的方法规定了一些其他必填的内容，导致了在 4.0 支持包下运行出错。所以必须修改最低 SDK 版本以支持旧的网络协议特性。
    	if (android.os.Build.VERSION.SDK_INT > 9) {
    		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    		StrictMode.setThreadPolicy(policy);
    	}
    	
    	mDeviceID = Secure.getString(this.getContentResolver(), Secure.ANDROID_ID);
    	LogUtils.i("PushService mDeviceID: " + mDeviceID);

    	Editor editor = getSharedPreferences(PushService.TAG, MODE_PRIVATE).edit();
    	editor.putString(PushService.PREF_DEVICE_ID, mDeviceID);
    	editor.commit();
    	startPushServiceListening();
    }
    
    @Override
    public void finish() {
    	DBUtil.closeDBConnection(MainActivity.this);
        super.finish();
    }
    
    public void startPushServiceListening() {
		new Thread() {
			@Override
			public void run() {
				PushService.actionStart(getApplicationContext());
			}
		}.start();
    }
    
    public void stopPushServiceListening() {
		PushService.actionStop(getApplicationContext());
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    }
    
    public void refreshCurrentXiaoQuName() {
    	String xiaoquName = LocalUtil.getCurrentXiaoQuName(this);
    	if (xiaoquName == null || xiaoquName.isEmpty()) {
    		xiaoquName = "请选择小区...";
    	}
    	
    	TextView currentXiaoquName = (TextView)findViewById(R.id.current_xiaoqu_name);
    	currentXiaoquName.setText(xiaoquName);
    }
    
    public void personalsettings(View v) {  
        Intent intent = new Intent();
		intent.setClass(MainActivity.this, PersonalSettingActivity.class);
		startActivity(intent);
    }
    
    public void wuyenotifieronclick(View v) {
    	String currentXiaoquId = LocalUtil.getCurrentXiaoQuId(MainActivity.this);
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
  				intent.setClass(MainActivity.this, WuyeNotifierMainActivity.class);
  			    intent.putExtra("xiaoquDocumentsJsonstr", xiaoquDocumentsJsonstr);
  			    startActivity(intent);    	
  			}
  		});
    }
    
    public void phonenumbersonclick(View v) { 
        Intent intent = new Intent();
		intent.setClass(MainActivity.this, PersonalSettingActivity.class);
		startActivity(intent);
    }
    
    public void bbsonclick(View v) { 
        Intent intent = new Intent();
		intent.setClass(MainActivity.this, MySettingsActivity.class);
		startActivity(intent);
    }
    
    public void shopptingsonclick(View v) {
        Intent intent = new Intent();
		intent.setClass(MainActivity.this, PersonalSettingActivity.class);
		startActivity(intent);
    }
    
    public void waimaionclick(View v) {  
//        Intent intent = new Intent();
//		intent.setClass(MainActivity.this, PersonalSettingActivity.class);
//		startActivity(intent);
		
		XiaoquHttp.testGetAllList();
    }
    
    public void pingcheonclick(View v) {  
//        Intent intent = new Intent();
//		intent.setClass(MainActivity.this, PersonalSettingActivity.class);
//		startActivity(intent);
    	
    	// insert record
//    	ContentValues values = new ContentValues();
//        values.put("id", 1);//注意值的类型要匹配
//        values.put("username", "wkl");        
//        values.put("password", "12345");        
//        SQLiteDatabase db = DBUtil.getWritableDatabase();//这里是获得可写的数据库
//        db.insert("wkltable", null, values);
//        
//        // find record
//        SQLiteDatabase db2 = DBUtil.getWritableDatabase();
//        //查询的语法，参数1为表名；参数2为表中的列名；参数3为要查询的列名；参数时为对应列的值；该函数返回的是一个游标
//        Cursor cursor = db2.query("wkltable", new String[]{"id", "username"}, "id=?", new String[]{"1"},  null, null, null);
//        //遍历每一个记录
//        String username = "testusername: ";
//        while(cursor.moveToNext()) {
//            username += ": " + cursor.getString(cursor.getColumnIndex("username"));//返回列名为name的值
//            System.out.println("query---->" + username);
//        }
    	
    	System.out.println("query------------------------------------------1");
    	List<ConstantTable> constants = ConstantTableDao.findAll();
    	for (ConstantTable con : constants) {
    		System.out.println("query---->ConstantTable---->" + con.getId() + " : " + con.getFieldname() + " : " + con.getFieldvalue());
    	}
    	
    	System.out.println("query------------------------------------------2");
    	List<LocalXiaoqu> xiaoqulists = XiaoquListDao.findAll();
    	for (LocalXiaoqu con : xiaoqulists) {
    		System.out.println("query---->XiaoquList---->" + con.getId() + " : " + con.getName() + " : " + con.getAddress());
    	}
    	
    	System.out.println("query------------------------------------------3");
    	List<Document> documents = DocumentDao.findAll();
    	for (Document doc : documents) {
    		System.out.println("query---->Document---->" + doc.getId() + " : " + doc.getXiaoquid() + " : " + doc.getType() + " : " + doc.getTitle() + " : " + doc.getContent() + " : " + doc.getOwner() + " : " + doc.getCreatetime() + " : " + doc.getPublishtime() + " : " + doc.getExpiretime());
    	}
        		
//		TextView dbtest_label = (TextView)findViewById(R.id.dbtest_label);
//		dbtest_label.setText(username);
    }

}

class XiaoquNameOnClickListener implements View.OnClickListener {  
	
	MainActivity parentView = null;
	Context context = null;

    public XiaoquNameOnClickListener(MainActivity parentView, Context context) { 
    	this.parentView = parentView;
    	this.context = context;
    }
    
  @Override  
  public void onClick(View v) {
	  
	  LocalHttpUtil.getDefaultHttpUtils().send(HttpRequest.HttpMethod.GET, LocalHttpUtil.GetAllXiaoquListUrl, new RequestCallBack<String>() {

          @Override
          public void onStart() {
          	LogUtils.i("Start to connect xiaoqu index...");
          }

          @Override
          public void onLoading(long total, long current, boolean isUploading) {
          	LogUtils.i("On loading to connect xiaoqu index: " + current + "/" + total);
          }

			@Override
			public void onFailure(HttpException error, String msg) {
				LogUtils.i("Error to connect xiaoqu index: " + msg);
			}

			@Override
			public void onSuccess(ResponseInfo<String> response) {
				LogUtils.i("Success to connect xiaoqu index: " + response.result.toString());
				String xiaoquListJsonstr = response.result.toString(); 
				
				Intent intent = new Intent();
			    intent.setClass(parentView, XiaoquSearchActivity.class);
			    intent.putExtra("xiaoquListJsonstr", xiaoquListJsonstr);
			    parentView.startActivity(intent); 
			}
		});
  } 
}
