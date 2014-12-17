package com.jun.xiaoquren.http;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jun.xiaoquren.dao.model.City;
import com.jun.xiaoquren.dao.model.Document;
import com.jun.xiaoquren.dao.model.LocalXiaoqu;
import com.lidroid.xutils.util.LogUtils;




public class JsonTools {
	
	// 1. Xiaoqu
	public static LocalXiaoqu getLocalXiaoquFromJsonStr(String jsonStr) {
		LocalXiaoqu xiaoqu = new LocalXiaoqu();
		
		try {
			xiaoqu = getLocalXiaoquFromJsonObject(new JSONObject(jsonStr));
		} catch (JSONException e) {
			LogUtils.e("Error occured at getLocalXiaoquFromJsonStr: " + e.getMessage());
			e.printStackTrace();
		}
		
		return xiaoqu;
	}
	
	public static LocalXiaoqu getLocalXiaoquFromJsonObject(JSONObject obj) {
		LocalXiaoqu xiaoqu = new LocalXiaoqu();
		
		try {	
			xiaoqu.setId(obj.getInt("id"));
			xiaoqu.setName(obj.getString("name"));
			xiaoqu.setAddress(obj.getString("address"));			
		} catch (JSONException e) {
			LogUtils.e("Error occured at getLocalXiaoquFromJsonObject: " + e.getMessage());
			e.printStackTrace();
		}
		
		return xiaoqu;
	}
	
	public static List<LocalXiaoqu> getLocalXiaoquList(String jsonStr) {
		
		List<LocalXiaoqu> xiaoquList = new ArrayList<LocalXiaoqu>();
		try {
			JSONArray  array = new JSONArray (jsonStr);
			
			for (int i = 0; i < array.length(); i++) {
                JSONObject item = array.getJSONObject(i);                
                xiaoquList.add(getLocalXiaoquFromJsonObject(item));
            }
			
		} catch (JSONException e) {
			LogUtils.e("Error occured at getLocalXiaoquList: " + e.getMessage());
			e.printStackTrace();
		}
		
		return xiaoquList;
	}
	
	
	// 2. Document
	public static Document getDocumentFromJsonStr(String jsonStr) {
		Document document = new Document();
		
		try {
			document = getDocumentFromJsonObject(new JSONObject(jsonStr));
		} catch (JSONException e) {
			LogUtils.e("Error occured at getDocumentFromJsonStr: " + e.getMessage());
			e.printStackTrace();
		}
		
		return document;
	}
	
	public static Document getDocumentFromJsonObject(JSONObject obj) {
		Document document = new Document();
		
		try {	
			document.setId(obj.getInt("id"));
			document.setTitle(obj.getString("title"));
			document.setContent(obj.getString("content"));
			document.setOwner(obj.getString("owner"));
			document.setCreatetime(getDateShowStyle(obj.getLong(("createDate"))));
		} catch (JSONException e) {
			LogUtils.e("Error occured at getDocumentFromJsonObject: " + e.getMessage());
			e.printStackTrace();
		}
		
		return document;
	}
	
	public static List<Document> getDocumentList(String jsonStr) {
		
		List<Document> documentList = new ArrayList<Document>();
		try {
			JSONArray  array = new JSONArray (jsonStr);
			
			for (int i = 0; i < array.length(); i++) {
                JSONObject item = array.getJSONObject(i);                
                documentList.add(getDocumentFromJsonObject(item));
            }
			
		} catch (JSONException e) {
			LogUtils.e("Error occured at getLocalXiaoquList: " + e.getMessage());
			e.printStackTrace();
		}
		
		return documentList;
	}
	
	
	// 3. Document Comment	
	
	
	// 4. Document Eva...
	
	
	
	// 5. City Name
	public static City getCityFromJsonStr(String jsonStr) {
		City city = new City();
		
		try {
			city = getCityFromJsonObject(new JSONObject(jsonStr));
		} catch (JSONException e) {
			LogUtils.e("Error occured at getCityFromJsonStr: " + e.getMessage());
			e.printStackTrace();
		}
		
		return city;
	}
	
	public static City getCityFromJsonObject(JSONObject obj) {
		City city = new City();
		
		try {	
			city.setId(obj.getInt("id"));
			city.setName(obj.getString("name"));			
		} catch (JSONException e) {
			LogUtils.e("Error occured at getCityFromJsonObject: " + e.getMessage());
			e.printStackTrace();
		}
		
		return city;
	}
	
	public static List<City> getCityList(String jsonStr) {
		
		List<City> cityList = new ArrayList<City>();
		try {
			JSONArray  array = new JSONArray (jsonStr);
			
			for (int i = 0; i < array.length(); i++) {
                JSONObject item = array.getJSONObject(i);                
                cityList.add(getCityFromJsonObject(item));
            }
			
		} catch (JSONException e) {
			LogUtils.e("Error occured at getCityList: " + e.getMessage());
			e.printStackTrace();
		}
		
		return cityList;
	}
	
	
	
	
	
	public static String getDateShowStyle(long longdate) {
		Date date = new Date(longdate);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(date);
	}
}
