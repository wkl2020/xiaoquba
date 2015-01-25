package com.jun.xiaoquren;

import java.io.UnsupportedEncodingException;
import java.util.Locale;

import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.jun.xiaoquren.http.LocalHttpUtil;
import com.jun.xiaoquren.util.LocalUtil;
import com.jun.xiaoquren.util.LocalViewUtil;
import com.jun.xiaoquren.util.MyAbstractActivity;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

public class ParkingAddActivity extends MyAbstractActivity implements OnClickListener{
	
	public static final String CLASSNAME = "ParkingAddActivity";
	
	TextView saveBtn;
	TextView publish_supply_type;
	TextView publish_identity;
	TextView publish_title_content;
	TextView publish_address_content;
	TextView publish_desc_content;
	TextView publish_area_content;
	TextView publish_price_content;
	TextView publish_unit_content;
	TextView publish_nickname_content;
	TextView publish_phone_content;
	
    @Override
	public String getActivityName() {
		return CLASSNAME;
	} 
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parking_add);
        
        saveBtn = (TextView)findViewById(R.id.parking_save_button);
        publish_supply_type = (TextView)findViewById(R.id.publish_supply_type);
        publish_identity = (TextView)findViewById(R.id.publish_identity);
        publish_title_content = (TextView)findViewById(R.id.publish_title_content);
        publish_address_content = (TextView)findViewById(R.id.publish_address_content);
        publish_desc_content = (TextView)findViewById(R.id.publish_desc_content);
        publish_area_content = (TextView)findViewById(R.id.publish_area_content);
        publish_price_content = (TextView)findViewById(R.id.publish_price_content);
        publish_unit_content = (TextView)findViewById(R.id.publish_unit_content);
        publish_nickname_content = (TextView)findViewById(R.id.publish_nickname_content);
        publish_phone_content = (TextView)findViewById(R.id.publish_phone_content);
        
        saveBtn.setOnClickListener(new SaveParkingBtnOnClickListener(ParkingAddActivity.this));
        
        refreshPage();
    }

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.btn_back:
			finish();
			break;
		}
	}

    public void page_back(View v) {
		this.finish();
    }
    
    public void supplyTypeOnClicked(View v) {
    	Intent intent = new Intent(ParkingAddActivity.this, SearchPageActivity.class);
		intent.putExtra("listName", LocalViewUtil.Info_Search_Supply);
		intent.putExtra("parentPageName", ParkingAddActivity.CLASSNAME);				
		this.startActivity(intent);
    }
    
    public void identityOnClicked(View v) {
    	Intent intent = new Intent(ParkingAddActivity.this, SearchPageActivity.class);
		intent.putExtra("listName", LocalViewUtil.Info_Search_Identity);
		intent.putExtra("parentPageName", ParkingAddActivity.CLASSNAME);				
		this.startActivity(intent);    	
    }
    
    public void unitOnClicked(View v) {
    	Intent intent = new Intent(ParkingAddActivity.this, SearchPageActivity.class);
		intent.putExtra("listName", LocalViewUtil.Info_Search_Unit);
		intent.putExtra("parentPageName", ParkingAddActivity.CLASSNAME);				
		this.startActivity(intent);    	
    }

	public void refreshPage() {
		
		for (String key : LocalViewUtil.AddParkingInfoMap.keySet()) {
			
			if (LocalViewUtil.Info_Search_Supply.equals(key)) {
				publish_supply_type.setText(LocalViewUtil.AddParkingInfoMap.get(key));
				
			} else if (LocalViewUtil.Info_Search_Identity.equals(key)) {
				publish_identity.setText(LocalViewUtil.AddParkingInfoMap.get(key));
				
			} else if (LocalViewUtil.Info_Search_Unit.equals(key)) {
				publish_unit_content.setText(LocalViewUtil.AddParkingInfoMap.get(key));
				
			}
		}
		
	}

}

class SaveParkingBtnOnClickListener implements View.OnClickListener {  

	ParkingAddActivity parentView = null;

	public SaveParkingBtnOnClickListener(ParkingAddActivity parentView) { 
		this.parentView = parentView;
	}

	@Override  
	public void onClick(View v) {
		
		String publish_supply_type = parentView.publish_supply_type.getText().toString().toLowerCase(Locale.getDefault());
		String publish_identity = parentView.publish_identity.getText().toString().toLowerCase(Locale.getDefault());
		String publish_title_content = parentView.publish_title_content.getText().toString().toLowerCase(Locale.getDefault());
		String publish_address_content = parentView.publish_address_content.getText().toString().toLowerCase(Locale.getDefault());
		String publish_desc_content = parentView.publish_desc_content.getText().toString().toLowerCase(Locale.getDefault());
		String publish_area_content = parentView.publish_area_content.getText().toString().toLowerCase(Locale.getDefault());
		String publish_price_content = parentView.publish_price_content.getText().toString().toLowerCase(Locale.getDefault());
		String publish_unit_content = parentView.publish_unit_content.getText().toString().toLowerCase(Locale.getDefault());
		String publish_nickname_content = parentView.publish_nickname_content.getText().toString().toLowerCase(Locale.getDefault());
		String publish_phone_content = parentView.publish_phone_content.getText().toString().toLowerCase(Locale.getDefault());
		
		if (publish_supply_type == null || publish_supply_type.trim().isEmpty()) {
			parentView.publish_supply_type.setError("内容不能为空");
			parentView.publish_supply_type.requestFocus();
			
		} else if (publish_identity == null || publish_identity.trim().isEmpty()) {
			parentView.publish_identity.setError("内容不能为空");
			parentView.publish_identity.requestFocus();
			
		} else if (publish_title_content == null || publish_title_content.trim().isEmpty()) {
			parentView.publish_title_content.setError("内容不能为空");
			parentView.publish_title_content.requestFocus();
			
		} else if (publish_address_content == null || publish_address_content.trim().isEmpty()) {
			parentView.publish_address_content.setError("内容不能为空");
			parentView.publish_address_content.requestFocus();
			
		} else if (publish_desc_content == null || publish_desc_content.trim().isEmpty()) {
			parentView.publish_desc_content.setError("内容不能为空");
			parentView.publish_desc_content.requestFocus();
			
		} else if (publish_area_content == null || publish_area_content.trim().isEmpty()) {
			parentView.publish_area_content.setError("内容不能为空");
			parentView.publish_area_content.requestFocus();
			
		} else if (publish_price_content == null || publish_price_content.trim().isEmpty()) {
			parentView.publish_price_content.setError("内容不能为空");
			parentView.publish_price_content.requestFocus();
			
		} else if (publish_unit_content == null || publish_unit_content.trim().isEmpty()) {
			parentView.publish_unit_content.setError("内容不能为空");
			parentView.publish_unit_content.requestFocus();
			
		} else if (publish_nickname_content == null || publish_nickname_content.trim().isEmpty()) {
			parentView.publish_nickname_content.setError("内容不能为空");
			parentView.publish_nickname_content.requestFocus();
			
		} else if (publish_phone_content == null || publish_phone_content.trim().isEmpty()) {
			parentView.publish_phone_content.setError("内容不能为空");
			parentView.publish_phone_content.requestFocus();
		} else {	
			
			RequestParams params = new RequestParams();
			try {
				
				JSONObject commentJson = new JSONObject();
				commentJson.put("supplyDemandType", publish_supply_type);
				commentJson.put("yourIdentity", publish_identity);
				commentJson.put("title", publish_title_content);
				commentJson.put("address", publish_address_content);
				commentJson.put("content", publish_desc_content);
				commentJson.put("areaMeasure", publish_area_content);
				commentJson.put("price", publish_price_content);
				commentJson.put("priceUnit", publish_unit_content);
				commentJson.put("nickname", publish_nickname_content);
				commentJson.put("phone", publish_phone_content);
				commentJson.put("xiaoquId", LocalUtil.getCurrentXiaoQuId(parentView));
				commentJson.put("owner", LocalUtil.getUsername(parentView));
				
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
	        		LocalHttpUtil.AddParkingStallInfoUrl,
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
	                    public void onSuccess(ResponseInfo<String> responseInfo) {
	                    	LogUtils.i("onSuccess upload response:" + responseInfo.result);	 
	                		
	                    	if (responseInfo.result.contains("success")) {               		
		                		Toast.makeText(parentView, "保存成功", Toast.LENGTH_LONG).show();
	                    		if (LocalUtil.isActiveActivityExists(ParkingMainActivity.CLASSNAME)) {
	                    			ParkingMainActivity page = (ParkingMainActivity)LocalUtil.getActiveActivity(ParkingMainActivity.CLASSNAME);
                    				page.refreshList();
                    			}
	                    		
	                    		parentView.finish();
	                    		
	                    	} else {
		                		Toast.makeText(parentView, "保存失败", Toast.LENGTH_LONG).show();
	                    	}
	                    }

	                    @Override
	                    public void onFailure(HttpException error, String msg) {
	                    	LogUtils.i("onFailure " + msg);
	                    }
	                });
				
		}

	}
}
