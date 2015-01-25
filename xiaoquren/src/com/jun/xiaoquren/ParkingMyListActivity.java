package com.jun.xiaoquren;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.jun.xiaoquren.http.JsonTools;
import com.jun.xiaoquren.http.LocalHttpUtil;
import com.jun.xiaoquren.server.model.ParkingStallInfo;
import com.jun.xiaoquren.util.LocalUtil;
import com.jun.xiaoquren.util.LocalViewUtil;
import com.jun.xiaoquren.util.MyAbstractActivity;
import com.jun.xiaoquren.view.adapter.ParkingMyListViewAdapter;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

public class ParkingMyListActivity extends MyAbstractActivity {

	public static final String CLASSNAME = "ParkingMyListActivity";

    @Override
	public String getActivityName() {
		return CLASSNAME;
	} 

	// Declare Variables
	ListView infoListView;
	ParkingMyListViewAdapter listViewAdapter;
	EditText searchTextbox;
	List<ParkingStallInfo> infoList = new ArrayList<ParkingStallInfo>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.parking_my_list);

		infoListView = (ListView) findViewById(R.id.listview);
		
		String parkingStallInfosJsonstr = (String)getIntent().getSerializableExtra("parkingStallInfosJsonstr");
		infoList = JsonTools.getParkingStallInfoList(parkingStallInfosJsonstr);

		// Pass results to ListViewAdapter Class
		listViewAdapter = new ParkingMyListViewAdapter(this, infoList);
		
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

		refreshList();
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
//			commentJson.put("owner", LocalUtil.getUsername(ParkingMyListActivity.this));
			
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
