package com.jun.xiaoquren.http;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

public class XiaoquHttp {
	
	public static final String url = "http://192.168.1.118:8080/xiaoqubaserver/main/xiaoqu/index"; 

	public XiaoquHttp() {
		// TODO Auto-generated constructor stub
	}
	
	
	public static void testGetAllList() {
		
		LogUtils.i("http1------------------------------------------1");
		
		HttpUtils http = new HttpUtils();
		http.configCurrentHttpCacheExpiry(10 * 1000);
		http.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {

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
			}
			
		});
	}

}
