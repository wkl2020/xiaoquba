package com.jun.xiaoquren;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.jun.xiaoquren.http.JsonTools;
import com.jun.xiaoquren.http.LocalHttpUtil;
import com.jun.xiaoquren.server.model.ParkingStallInfo;
import com.jun.xiaoquren.util.LocalUtil;
import com.jun.xiaoquren.util.LocalViewUtil;
import com.jun.xiaoquren.util.MyAbstractActivity;
import com.jun.xiaoquren.view.adapter.ParkingMainViewAdapter;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

public class ParkingMainActivity extends MyAbstractActivity {

	public static final String CLASSNAME = "ParkingMainActivity";

    @Override
	public String getActivityName() {
		return CLASSNAME;
	} 

	// Declare Variables
	ListView infoListView;
	ParkingMainViewAdapter listViewAdapter;
	EditText searchTextbox;
	List<ParkingStallInfo> infoList = new ArrayList<ParkingStallInfo>();
	
	private PopupWindow popupwindow; 

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.parking_main);

		infoListView = (ListView) findViewById(R.id.listview);
		
		String parkingStallInfosJsonstr = (String)getIntent().getSerializableExtra("parkingStallInfosJsonstr");
		infoList = JsonTools.getParkingStallInfoList(parkingStallInfosJsonstr);

		// Pass results to ListViewAdapter Class
		listViewAdapter = new ParkingMainViewAdapter(this, infoList);
		
		// Binds the Adapter to the ListView
		infoListView.setAdapter(listViewAdapter);
		
		// Locate the EditText in listview_main.xml
		searchTextbox = (EditText) findViewById(R.id.search);

		// Capture Text in EditText
		searchTextbox.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable arg0) {
				String text = searchTextbox.getText().toString().toLowerCase(Locale.getDefault());
				listViewAdapter.filter(text);
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
			}
		});
		
		initmPopupWindowView();
		refreshList();
	}
	
	public void onFilterInfoPopClick(View v) {
		
		popupwindow.dismiss();
	    Intent intent = new Intent();
		intent.setClass(ParkingMainActivity.this, ParkingSearchActivity.class);
		startActivity(intent);
	}
	
	public void onPublishInfoPopClick(View v) {
		
		if (LocalUtil.isUserStatusValid(ParkingMainActivity.this)) {
			popupwindow.dismiss();
			Intent intent = new Intent();
			intent.setClass(ParkingMainActivity.this, ParkingAddActivity.class);
			startActivity(intent);
		}
	}
	
	public void onMyInfoPopClick(View v) {
		
		if (LocalUtil.isUserStatusValid(ParkingMainActivity.this)) {
			popupwindow.dismiss();
			Intent intent = new Intent();
			intent.putExtra("parkingStallInfosJsonstr", "[]");			
			intent.setClass(ParkingMainActivity.this, ParkingMyListActivity.class);
			startActivity(intent);			
		}
	}
    
    public void onPopupButtonClicked(View v) {
		if (popupwindow != null && popupwindow.isShowing()) {  
            popupwindow.dismiss();  
            return;  
        } else {
            popupwindow.showAsDropDown(v, 5, 5);  
        }  
    }
	
	 public void initmPopupWindowView() {  		  
        View customView = getLayoutInflater().inflate(R.layout.parking_main_pop, null, false);  
        popupwindow = new PopupWindow(customView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);  
        
        popupwindow.setAnimationStyle(R.style.AnimationFade);
        popupwindow.setOutsideTouchable(false);
        popupwindow.setBackgroundDrawable(new BitmapDrawable());
        popupwindow.setFocusable(true);//如果不加这个，Grid不会响应ItemClick
        popupwindow.setTouchable(true);
        
        popupwindow.setTouchInterceptor(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					
					System.out.println("onTouch: Y: " + event.getY() + ":X: " + event.getX());                   
                    if (event.getY()<0){  //这里处理，当点击gridview以外区域的时候，菜单关闭
                        if (popupwindow.isShowing())
                        	popupwindow.dismiss();
                    }
                    return false;
				}
        });
    }  

	// Not using options menu in this page
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

    public void page_back(View v) {
		this.finish();
    }
    
    public void refreshList() {
    	
    	String currentXiaoquId = LocalUtil.getCurrentXiaoQuId(this);
    	System.out.println("Start to connect xiaoqu ParkingStallInfos with xiaoqu id: " + currentXiaoquId);
    	
    	RequestParams params = new RequestParams();
		try {
			
			JSONObject commentJson = new JSONObject();
			commentJson.put("xiaoquId", currentXiaoquId);
			commentJson.put("supplyDemandType", LocalViewUtil.MainParkingInfoMap.get(LocalViewUtil.Info_Search_Supply));			
			if (!LocalViewUtil.MainParkingInfoMap.get(LocalViewUtil.Info_Search_Identity)
					.equals(LocalViewUtil.Info_Search_Identity_Value_Zero)) {
				commentJson.put("yourIdentity", LocalViewUtil.MainParkingInfoMap.get(LocalViewUtil.Info_Search_Identity));
			}			
			
			commentJson.put("rows", 10);
			commentJson.put("page", 1);
			commentJson.put("sidx", "create_date");
			commentJson.put("sord", "desc");
			
			LogUtils.i("CommentJSON: " + commentJson.toString());
			
			params.setContentType("application/json;charset=UTF-8");
			params.setBodyEntity(new StringEntity(commentJson.toString(),"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			
			LogUtils.i("Error occured UnsupportedEncodingException: " + e.getMessage());
			e.printStackTrace();
		} catch (JSONException e) {
			
			LogUtils.i("Error occured JSONException: " + e.getMessage());
			e.printStackTrace();
		}
		
		HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST,
        		LocalHttpUtil.ParkingInfoSearchUrl,
                params,
                new RequestCallBack<String>() {

                    @Override
                    public void onStart() {
                    	LogUtils.i("onStart conn...");
                    }

                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                    	LogUtils.i("onLoading " + current + "/" + total);
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> response) {
                    	LogUtils.i("onSuccess response:" + response.result);
                    	System.out.println("Success to connect xiaoqu ParkingStallInfos: " + response.result.toString());
          				String parkingStallInfosJsonstr = response.result.toString(); 
          				infoList = JsonTools.getParkingStallInfoList(parkingStallInfosJsonstr);  				
          				listViewAdapter.resetListData(infoList);
          		    	infoListView.setAdapter(listViewAdapter);
          		    	listViewAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                    	LogUtils.i("onFailure " + msg);
                    }
                });
    }

}
