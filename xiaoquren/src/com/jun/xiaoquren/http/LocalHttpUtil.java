package com.jun.xiaoquren.http;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import com.lidroid.xutils.HttpUtils;

public class LocalHttpUtil {
	
	// HttpUtils
	
	public static final String CLASSNAME = "LocalHttpUtil";
	
	public static final String ServerURL = "http://192.168.1.118:8080";
	
	// Document
	public static final String XiaoquDocumentsUrl = ServerURL + "/xiaoqubaserver/main/xiaoqudocuments/"; 
	
	// Document Comment
	public static final String DocumentCommentsUrl = ServerURL + "/xiaoqubaserver/main/documentComments/";
	public static final String AddDocumentCommentUrl = ServerURL + "/xiaoqubaserver/main/documentComment";
	
	
	// Xiaoqu
	public static final String GetAllXiaoquListUrl = ServerURL + "/xiaoqubaserver/main/xiaoqu/index"; 
	
	
	// UserEntity
	public static final String AddUserEntityUrl = ServerURL + "/xiaoqubaserver/main/user";
	public static final String LoginUserUrl = ServerURL + "/xiaoqubaserver/main/j_spring_security_check";

	public LocalHttpUtil() {
		// TODO Auto-generated constructor stub
	}
	
	public static HttpUtils getDefaultHttpUtils() {
		HttpUtils http = new HttpUtils();
		http.configCurrentHttpCacheExpiry(10 * 1000);
		return http;
	}
	
	
	// Http Client
	public static final long Default_Thread_Timeout = 60 * 20 * 1000;	
	public static final int Default_Http_Conn_Timeout = 20 * 1000;
	public static final int Default_Http_ReadWrite_Timeout = 20 * 1000;
	
	public static String LoginUrl = "http://192.168.1.118:8080/xiaoqubaserver/main/j_spring_security_check";
	
	public static DefaultHttpClient getHttpClient() { 
		DefaultHttpClient client = new DefaultHttpClient(); //client.getCookieStore().getCookies()  session_id  
        HttpParams httpParams = client.getParams();  
        httpParams.setParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE, false); 
        HttpConnectionParams.setConnectionTimeout(httpParams, 20 * 1000);  
        HttpConnectionParams.setSoTimeout(httpParams, 20 * 1000);
        HttpConnectionParams.setSocketBufferSize(httpParams, 8192); 
        String userAgent = "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2) Gecko/20100115 Firefox/3.6";  
        HttpProtocolParams.setUserAgent(httpParams, userAgent); 
        
        return client;  
    }  
}
