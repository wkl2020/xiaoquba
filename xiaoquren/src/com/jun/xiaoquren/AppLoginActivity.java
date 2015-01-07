package com.jun.xiaoquren;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.client.entity.UrlEncodedFormEntity;   
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.jun.xiaoquren.http.LocalHttpUtil;
import com.jun.xiaoquren.util.LocalUtil;
import com.jun.xiaoquren.util.MyAbstractActivity;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

public class AppLoginActivity extends MyAbstractActivity implements OnClickListener{
	
	public static final String CLASSNAME = "AppLoginActivity";

    @Override
	public String getActivityName() {
		return CLASSNAME;
	} 
    
    EditText usernameContent;
    EditText passwordContent;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_login);
        
        usernameContent = (EditText) findViewById(R.id.username);
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
    
    public void onRegisterClicked(View v) {
    	Intent intent = new Intent();
		intent.setClass(AppLoginActivity.this, AppRegisterActivity.class);
		startActivity(intent);
    }
    
    public void onLoginActionClicked(View v) {
    	String username = usernameContent.getText().toString().toLowerCase(Locale.getDefault());
		String password = passwordContent.getText().toString().toLowerCase(Locale.getDefault());
		boolean isValid = true;
		
		if (username == null || username.trim().isEmpty()) {
			usernameContent.setError("用户名为手机号，用户名不能为空");
			usernameContent.requestFocus();
			isValid = false;
		}
		
		if (password == null || password.isEmpty()) {
			passwordContent.setError("密码不能为空");
			passwordContent.requestFocus();
			isValid = false;
		}
    	
    	if (isValid) {
    		DefaultHttpClient client = LocalHttpUtil.getHttpClient(); 
              
            List<NameValuePair> params = new ArrayList<NameValuePair>(1);  
            params.add(new BasicNameValuePair("j_username", username));  
            params.add(new BasicNameValuePair("j_password", password));  
            HttpEntity formEntity = null;  
            try {  
                formEntity = new UrlEncodedFormEntity(params, HTTP.UTF_8);  
            } catch (UnsupportedEncodingException e) {  
            	System.out.println(e.getMessage());
                e.printStackTrace();
            }  

            HttpPost request = new HttpPost(LocalHttpUtil.LoginUrl);
            request.setEntity(formEntity);  
            
            HttpResponse response = null;  
            try {  
                response = client.execute(request);//重定向 RedirectStrategy execute while (!done)   
            } catch (ClientProtocolException e) {    
            	System.out.println(e.getMessage());
                e.printStackTrace();
            } catch (IOException e) {    
            	System.out.println(e.getMessage());
                e.printStackTrace(); 
            }  
              
            if (response == null) {  
                return;  
            }  
            
            System.out.println("ResponseStatusCode: " + response.getStatusLine().getStatusCode());
            for(Header h : response.getAllHeaders()) {
				System.out.println("ResponseHeader: " + h.getName() + " : " + h.getValue());
			}
            String jSessionId = LocalHttpUtil.getJSessionId(client.getCookieStore().getCookies());
            for(Cookie cookie : client.getCookieStore().getCookies()) {  
            	System.out.println("CookieStore: " + cookie.getName() + " : " + cookie.getValue());
            }
            
            String result = null;  
            try {  
            	result = EntityUtils.toString(response.getEntity(),"UTF-8");  
            } catch (Exception e) {  
            	System.out.println(e.getMessage());
            	e.printStackTrace();
            	return;  
            } 
            System.out.println("result: " + result.toString());
            
            if (result.contains(LocalHttpUtil.indexPageTag)) { // If login success and goto index page
            	System.out.println("Login Success!");
            	LocalUtil.saveLoginInfo(AppLoginActivity.this, username, password, jSessionId, "", "");
            	
            	LogUtils.i("Start to get user by username: " + username);
            	LocalHttpUtil.getDefaultHttpUtils().send(HttpRequest.HttpMethod.GET, LocalHttpUtil.GetUserByUsername+username, new RequestCallBack<String>() {

                    @Override
                    public void onStart() {
                    	LogUtils.i("Start to get user... ");
                    }

                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                    	LogUtils.i("On loading to get user: " + current + "/" + total);
                    }

          			@Override
          			public void onFailure(HttpException error, String msg) {
          				LogUtils.i("Error to get user: " + msg);
          			}

          			@Override
          			public void onSuccess(ResponseInfo<String> response) {
          				LogUtils.i("Success to get user");
//          				String xiaoquDocumentsJsonstr = response.result.toString(); 
          				
//          				Intent intent = new Intent();
//          				intent.setClass(MainActivity.this, WuyeNotifierMainActivity.class);
//          			    intent.putExtra("xiaoquDocumentsJsonstr", xiaoquDocumentsJsonstr);
//          			    startActivity(intent);    	
          			}
          		});
            	
            	
            } else if (result.contains(LocalHttpUtil.deniedPageTag)) { // If login failed and goto denied page
            	System.out.println("Login Failure!");
            	usernameContent.setError("用户名或密码错误");
            	usernameContent.requestFocus();
            }
            
            
            
            
//            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//            	// If login success and return index page
//            	
//            	
//            } else if (response.getStatusLine().getStatusCode() == 302) {
//            	// else If login success with header location to xiaoqubaserver/main/index
//            	
//            	
//            } else {
//            	// else If login failure with header location to xiaoqubaserver/main/denied page
//            	
//            	System.out.println(response.getStatusLine().getStatusCode()); 
//            	System.out.println("error response" + response.getStatusLine().toString());  
//                return;  
//            }
    	}
    }
}
