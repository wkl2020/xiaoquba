package com.jun.xiaoquren;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.jun.xiaoquren.http.LocalHttpUtil;
import com.jun.xiaoquren.util.LocalUtil;
import com.jun.xiaoquren.util.MyAbstractActivity;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.http.client.HttpRequest;

public class WuyeNotifierCommentAddActivity extends MyAbstractActivity implements OnClickListener {
	public static final String ACTIVITY_NAME = "WuyeNotifierCommentAddActivity";

    @Override
	public String getActivityName() {
		return ACTIVITY_NAME;
	}
    
	EditText commentContent;
	
	private String documentId = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wuye_notifier_comment_add);
		
		// Locate the EditText in listview_main.xml
		commentContent = (EditText) findViewById(R.id.comment_content);

		// Capture Text in EditText
		commentContent.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable arg0) {
				String text = commentContent.getText().toString().toLowerCase(Locale.getDefault());
				// Validate
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
		
		if (LocalUtil.isFirstTimeLogin(this)) {
			Button backBtn = (Button) findViewById(R.id.btn_back);
			backBtn.setVisibility(View.INVISIBLE);
		}
		
		documentId = (String)getIntent().getStringExtra("documentId");
	}

	// Not using options menu in this page
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.btn_back:
			finish();
			break;
		}
	}
	
	public void onCommitComment(View v) {
		String content = commentContent.getText().toString().toLowerCase(Locale.getDefault());
		if (content == null || content.trim().isEmpty()) {
			commentContent.setError("内容不能为空");
			commentContent.requestFocus();
		} else if (content.trim().length() > 100) {
			commentContent.setError("内容限50字");
			commentContent.requestFocus();
		} else {	
			List<NameValuePair> pairs = new ArrayList<NameValuePair>();
			NameValuePair contentPair = new BasicNameValuePair("content", content);
			NameValuePair documentIdPair = new BasicNameValuePair("documentId",documentId);
			NameValuePair nicknamePair = new BasicNameValuePair("nickname", "我就测试者");
			
			pairs.add(contentPair);
			pairs.add(documentIdPair);
			pairs.add(nicknamePair);	
			
//			RequestParams params = new RequestParams();
//	        params.addQueryStringParameter("content", commentJson.toString());
			
			RequestParams params = new RequestParams();
//			params.addQueryStringParameter(pairs);
//			params.addBodyParameter(pairs);
			try {				
				String nickname = "我就测试者";
				JSONObject commentJson = new JSONObject();
				commentJson.put("content", content);		
				commentJson.put("documentId", documentId);
				commentJson.put("nickname", nickname);	
				
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
//			params.addHeader("contentType", "text/plain;charset=UTF-8");
//			params.addHeader("dataType", "text");
//			params.addHeader("contentType", "application/json;charset=UTF-8");
//			params.addHeader("dataType", "json");
	        
	        HttpUtils http = new HttpUtils();
	        http.send(HttpRequest.HttpMethod.POST,
	        		LocalHttpUtil.AddDocumentCommentUrl,
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
	                    }

	                    @Override
	                    public void onFailure(HttpException error, String msg) {
	                    	LogUtils.i("onFailure " + msg);
	                    }
	                });
			


				
				
		}
    }

    public void page_back(View v) {
		this.finish();
    }

}
