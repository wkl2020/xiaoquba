package com.jun.xiaoquren;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
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
import com.jun.xiaoquren.util.MyAbstractActivity;

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
//    		DefaultHttpClient client = new DefaultHttpClient();//client.getCookieStore().getCookies()  session_id  
//            HttpParams httpParams = client.getParams();  
//            httpParams.setParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE, false); 
              
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
            
              
//            HttpHost request = new HttpHost(login_url);  
            HttpPost request = new HttpPost("http://192.168.1.118:8080/xiaoqubaserver/main/j_spring_security_check");
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
	    	
	    	for(Cookie cookie : client.getCookieStore().getCookies()) {  
            	System.out.println("CookieStore: " + cookie.getName() + " : " + cookie.getValue());
            	
                if("JSESSIONID".equals(cookie.getName())){  
                    String JSESSIONID = cookie.getValue();  
                    System.out.println("JSESSIONID: " + JSESSIONID);
                    break;  
                }  
            }
              
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            	System.out.println(response.getStatusLine().getStatusCode()); 
            	System.out.println("error response" + response.getStatusLine().toString());  
                return;  
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
    		
    		
//===========================DefaultHttpClient=========================================    		
//    		try {
//        		DefaultHttpClient client = LocalHttpUtil.getHttpClient();
//        		HttpPost httpPost = new HttpPost(LocalHttpUtil.LoginUrl);
//        		
//    			String postDataJsonStr = "{\"j_username\":\"xjj\",\"j_password\":\"xjj\"}";
//    			StringEntity inputEntity = new StringEntity(postDataJsonStr);
//    			inputEntity.setContentType("application/x-www-form-urlencoded");  //application/x-www-form-urlencoded
////    			inputEntity.setContentType("application/json");  //application/x-www-form-urlencoded
//    			httpPost.setEntity(inputEntity);
//    			
////    			HttpParams param = new BasicHttpParams();   
//    			
//    			System.out.println("inputEntity: " + postDataJsonStr);
//    			
//    			HttpResponse response = client.execute(httpPost);
//    			
//    			CookieStore cookieStore = client.getCookieStore(); 
//    			List<Cookie> cookies = cookieStore.getCookies();  
//                for(int i=0;i<cookies.size();i++){  
//                	System.out.println("CookieStore: " + cookies.get(i).getName() + " : " + cookies.get(i).getValue());
//                	
//                    if("JSESSIONID".equals(cookies.get(i).getName())){  
//                        String JSESSIONID = cookies.get(i).getValue();  
//                        System.out.println("JSESSIONID: " + JSESSIONID);
//                        break;  
//                    }  
//                }
//
//    			System.out.println("responseStr: " + response.toString());
//    			for(Header h : response.getAllHeaders()) {
//    				System.out.println("Header: " + h.getName() + " : " + h.getValue());
//    				
//    				if (h.getName().equals("Set-Cookie")) {
//    					String cookies2 = h.getValue();
//    					cookies2 = cookies2.substring(cookies2.indexOf("JSESSIONID"), cookies2.indexOf(";"));
//    					
//    					System.out.println("Cookies2: " + cookies2);
//    				}
//    			}
//    			
//    			
//    			String resultDataJsonStr = EntityUtils.toString(response.getEntity(), "utf-8");
//    			
//    			System.out.println("JSON: " + resultDataJsonStr);
//    		} catch (Exception e) {
//    			System.out.println(e.getMessage());
//    			e.printStackTrace();     
//    		}
 
//===========================HttpUtils=========================================
//			RequestParams params = new RequestParams();
//			try {				
//				JSONObject commentJson = new JSONObject();
//				commentJson.put("j_username", username);
//				commentJson.put("j_password", password);
//				
//				LogUtils.i("CommentJSON: " + commentJson.toString());
//				
//				params.setContentType("application/json;charset=UTF-8");
//				params.setBodyEntity(new StringEntity(commentJson.toString(),"UTF-8"));
//			} catch (UnsupportedEncodingException e) {
//				
//				LogUtils.i("Error occured UnsupportedEncodingException: " + e.getMessage());
//				e.printStackTrace();
//			} catch (JSONException e) {
//				
//				LogUtils.i("Error occured JSONException: " + e.getMessage());
//				e.printStackTrace();
//			}
//			
//			HttpUtils http = new HttpUtils();
//	        http.send(HttpRequest.HttpMethod.POST,
//	        		LocalHttpUtil.LoginUserUrl,
//	                params,
//	                new RequestCallBack<String>() {
//
//	                    @Override
//	                    public void onStart() {
//	                    	LogUtils.i("onStart conn...");
//	                    }
//
//	                    @Override
//	                    public void onLoading(long total, long current, boolean isUploading) {
//	                    	LogUtils.i("onLoading " + current + "/" + total);
//	                    }
//
//	                    @Override
//	                    public void onSuccess(ResponseInfo<String> responseInfo) {
//	                    	LogUtils.i("onSuccess login user response:" + responseInfo.result);
//	                    	
//	                    	
//	                    	
//	                    	if (responseInfo.result.contains("success")) {
//	                    		if (LocalUtil.isActiveActivityExists(AppLoginActivity.CLASSNAME)) {
//	                    			AppLoginActivity currentPage = (AppLoginActivity)LocalUtil.getActiveActivity(AppLoginActivity.CLASSNAME);
//	                    			currentPage.finish();
//                    			}
//	                    		
//	                    		// How to show Logged page?
////	                    		if (LocalUtil.isActiveActivityExists(AppRegisterActivity.CLASSNAME)) {
////	                    			WuyeNotifierCommentAddActivity currentPage = (WuyeNotifierCommentAddActivity)LocalUtil.getActiveActivity(WuyeNotifierCommentAddActivity.CLASSNAME);
////	                    			currentPage.finish();
////	                    		}
//	                    	}
//	                    }
//
//	                    @Override
//	                    public void onFailure(HttpException error, String msg) {
//	                    	LogUtils.i("onFailure " + msg);
//	                    	
//	                    	
//	                    }
//	                });
    	}
    }
}
