package com.jun.xiaoquren;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.jun.xiaoquren.util.LocalUtil;
import com.jun.xiaoquren.util.MyAbstractActivity;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

public class MainActivity extends MyAbstractActivity {
	public static final String ACTIVITY_NAME = "MainActivity";

    @Override
	public String getActivityName() {
		return ACTIVITY_NAME;
	}
	 
    @Override
    public void onCreate(Bundle arg0) {
    	super.onCreate(arg0);    	
    	setContentView(R.layout.main_activity);
    	
    	// Create Database
    	DBUtil.initDBConnection(MainActivity.this);
    	
    	String xiaoquName = LocalUtil.getCurrentXiaoQuName(this);
    	if (xiaoquName == null || xiaoquName.isEmpty()) {
    		xiaoquName = "请选择小区...";
    	}
    	
    	TextView currentXiaoquName = (TextView)findViewById(R.id.current_xiaoqu_name);
    	currentXiaoquName.setText(xiaoquName);
    	
    	currentXiaoquName.setOnClickListener(new XiaoquNameOnClickListener(this, MainActivity.this));
 
    }
    
    public void refreshCurrentXiaoQuName() {
    	String xiaoquName = LocalUtil.getCurrentXiaoQuName(this);
    	if (xiaoquName == null || xiaoquName.isEmpty()) {
    		xiaoquName = "新凯家园";
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
		intent.setClass(MainActivity.this, PersonalSettingActivity.class);
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
