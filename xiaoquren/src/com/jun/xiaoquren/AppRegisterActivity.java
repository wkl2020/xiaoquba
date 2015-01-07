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

import com.jun.xiaoquren.http.LocalHttpUtil;
import com.jun.xiaoquren.util.LocalUtil;
import com.jun.xiaoquren.util.MyAbstractActivity;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.util.LogUtils;

public class AppRegisterActivity extends MyAbstractActivity implements OnClickListener{
	
	public static final String CLASSNAME = "AppRegisterActivity";

    @Override
	public String getActivityName() {
		return CLASSNAME;
	} 
    
    EditText phonenumContent;
    EditText passwordContent;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_register);
        
        phonenumContent = (EditText) findViewById(R.id.phonenum);
        passwordContent = (EditText) findViewById(R.id.password);
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
    
    public void onRegisterActionClicked(View v) {

		String phonenum = phonenumContent.getText().toString().toLowerCase(Locale.getDefault());
		String password = passwordContent.getText().toString().toLowerCase(Locale.getDefault());
		boolean isValid = true;
		
		if (phonenum == null || phonenum.trim().isEmpty()) {
			phonenumContent.setError("手机号不能为空");
			phonenumContent.requestFocus();
			isValid = false;
		} else if (phonenum.trim().length() < 11 || phonenum.trim().length() > 11) {
			phonenumContent.setError("手机号长度匹配");
			phonenumContent.requestFocus();
			isValid = false;
		}
		
		if (password == null || password.isEmpty()) {
			passwordContent.setError("密码不能为空");
			passwordContent.requestFocus();
			isValid = false;
		} else if (password.length() < 6) {
			passwordContent.setError("密码长度不能小于6");
			passwordContent.requestFocus();
			isValid = false;
		}
		
		if (isValid) {
			RequestParams params = new RequestParams();
			try {				
				String nickname = "我是新注册用户";
				JSONObject commentJson = new JSONObject();
				commentJson.put("username", phonenum);
				commentJson.put("phone", phonenum);
				commentJson.put("password", password);		
				commentJson.put("confirmPassword", password);
				commentJson.put("nickname", nickname);
				commentJson.put("xiaoquId", LocalUtil.getCurrentXiaoQuId(AppRegisterActivity.this));	
				commentJson.put("role", "NORMAL");
				
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
	        		LocalHttpUtil.AddUserEntityUrl,
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
	                    	LogUtils.i("onSuccess add user response:" + responseInfo.result);
	                    	if (responseInfo.result.contains("success")) {
	                    		if (LocalUtil.isActiveActivityExists(AppRegisterActivity.CLASSNAME)) {
	                    			AppRegisterActivity currentPage = (AppRegisterActivity)LocalUtil.getActiveActivity(AppRegisterActivity.CLASSNAME);
	                    			currentPage.finish();
                    			}
	                    		
	                    		// How to show Logged page?
//	                    		if (LocalUtil.isActiveActivityExists(AppRegisterActivity.CLASSNAME)) {
//	                    			WuyeNotifierCommentAddActivity currentPage = (WuyeNotifierCommentAddActivity)LocalUtil.getActiveActivity(WuyeNotifierCommentAddActivity.CLASSNAME);
//	                    			currentPage.finish();
//	                    		}
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
