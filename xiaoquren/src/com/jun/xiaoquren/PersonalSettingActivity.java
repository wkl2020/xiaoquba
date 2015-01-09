package com.jun.xiaoquren;

import java.io.UnsupportedEncodingException;
import java.util.Locale;

import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jun.xiaoquren.http.LocalHttpUtil;
import com.jun.xiaoquren.util.LocalUtil;
import com.jun.xiaoquren.util.MyAbstractActivity;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

public class PersonalSettingActivity extends MyAbstractActivity implements OnClickListener{
	
	public static final String CLASSNAME = "PersonalSettingActivity";

    @Override
	public String getActivityName() {
		return CLASSNAME;
	} 
    
    EditText nickname;
    TextView phone;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_settings);
        
        nickname = (EditText) findViewById(R.id.nickname);
        phone = (TextView) findViewById(R.id.phone);
        
        nickname.setText(LocalUtil.getNickname(PersonalSettingActivity.this));
        phone.setText(LocalUtil.getUsername(PersonalSettingActivity.this));
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
    
    public void onSubmitActionClicked(View v) {
    	String nicknameContent = nickname.getText().toString().toLowerCase(Locale.getDefault());
		if (nicknameContent == null || nicknameContent.trim().isEmpty()) {
			nickname.setError("内容不能为空");
			nickname.requestFocus();
		} else {	
			
			RequestParams params = new RequestParams();
			try {
				JSONObject commentJson = new JSONObject();	
				commentJson.put("username", phone.getText());
				commentJson.put("nickname", nicknameContent);	
				
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
	        		LocalHttpUtil.UpdateUserUrl,
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
	                    		try {
									JSONObject results = new JSONObject(responseInfo.result);
									String nickname = results.getString("nickname");
									String role = results.getString("role");
									LocalUtil.saveNicknameAndUsrrole(PersonalSettingActivity.this, nickname, role);
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}	                    		
	                    		Toast.makeText(PersonalSettingActivity.this, "更新成功!", Toast.LENGTH_LONG).show();
	                    		
	                    	} else {
	                    		Toast.makeText(PersonalSettingActivity.this, "更新失败!", Toast.LENGTH_LONG).show();
	                    	}
	                    }

	                    @Override
	                    public void onFailure(HttpException error, String msg) {
	                    	LogUtils.i("onFailure " + msg);
	                    	Toast.makeText(PersonalSettingActivity.this, "更新失败!", Toast.LENGTH_LONG).show();
	                    }
	                });
				
		}
    }
}
