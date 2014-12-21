package com.jun.xiaoquren.http;

import com.lidroid.xutils.HttpUtils;

public class LocalHttpUtil {
	
	public static final String ServerURL = "http://192.168.1.118:8080";
	
	// Document
	public static final String XiaoquDocumentsUrl = ServerURL + "/xiaoqubaserver/main/xiaoqudocuments/"; 
	
	// Document Comment
	public static final String DocumentCommentsUrl = ServerURL + "/xiaoqubaserver/main/documentComments/";
	public static final String AddDocumentCommentUrl = ServerURL + "/xiaoqubaserver/main/documentComment";
	
	
	// Xiaoqu
	public static final String GetAllXiaoquListUrl = ServerURL + "/xiaoqubaserver/main/xiaoqu/index"; 
	

	public LocalHttpUtil() {
		// TODO Auto-generated constructor stub
	}
	
	public static HttpUtils getDefaultHttpUtils() {
		HttpUtils http = new HttpUtils();
		http.configCurrentHttpCacheExpiry(10 * 1000);
		return http;
	}

}
