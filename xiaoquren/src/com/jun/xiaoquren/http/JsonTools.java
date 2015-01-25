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
import com.jun.xiaoquren.dao.model.DocumentComment;
import com.jun.xiaoquren.dao.model.LocalXiaoqu;
import com.jun.xiaoquren.server.model.ParkingStallInfo;
import com.jun.xiaoquren.server.model.UserEntity;
import com.lidroid.xutils.util.LogUtils;

public class JsonTools {
	
	public static final String CLASSNAME = "JsonTools";
	
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
	public static DocumentComment getCommentFromJsonStr(String jsonStr) {
		DocumentComment document = new DocumentComment();
		
		try {
			document = getCommentFromJsonObject(new JSONObject(jsonStr));
		} catch (JSONException e) {
			LogUtils.e("Error occured at getCityFromJsonStr: " + e.getMessage());
			e.printStackTrace();
		}
		
		return document;
	}
	
	public static DocumentComment getCommentFromJsonObject(JSONObject obj) {
		DocumentComment comment = new DocumentComment();
		
		try {	
			comment.setId(obj.getInt("id"));
			comment.setNickname(obj.getString("nickname"));
			comment.setContent(obj.getString("content"));
			comment.setCreateDate(getDateShowStyle(obj.getLong(("createDate"))));
			
		} catch (JSONException e) {
			LogUtils.e("Error occured at getCityFromJsonObject: " + e.getMessage());
			e.printStackTrace();
		}
		
		return comment;
	}
	
	public static List<DocumentComment> getCommentList(String jsonStr) {
		
		List<DocumentComment> commentList = new ArrayList<DocumentComment>();
		try {
			JSONArray  array = new JSONArray (jsonStr);
			
			for (int i = 0; i < array.length(); i++) {
                JSONObject item = array.getJSONObject(i);                
                commentList.add(getCommentFromJsonObject(item));
            }
			
		} catch (JSONException e) {
			LogUtils.e("Error occured at getCityList: " + e.getMessage());
			e.printStackTrace();
		}
		
		return commentList;
	}
	
	
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
	
	
	// 6. UserEntity
	public static UserEntity getUserEntityFromJsonStr(String jsonStr) {
		UserEntity user = new UserEntity();
		
		try {
			user = getUserEntityFromJsonObject(new JSONObject(jsonStr));
		} catch (JSONException e) {
			LogUtils.e("Error occured at getCityFromJsonStr: " + e.getMessage());
			e.printStackTrace();
		}
		
		return user;
	}
	
	public static UserEntity getUserEntityFromJsonObject(JSONObject obj) {
		UserEntity user = new UserEntity();
		
		try {	
			user.setId(obj.getInt("id"));
			user.setNickname(obj.getString("nickname"));
			user.setAddress(obj.getString("address"));
			user.setCompanyName(obj.getString("companyName"));
			user.setEmail(obj.getString("email"));
			user.setFirstname(obj.getString("firstname"));
			user.setLastname(obj.getString("lastname"));
			user.setPhone(obj.getString("phone"));
			user.setRole(obj.getString("role"));
			user.setToken(obj.getString("token"));
			user.setUsername(obj.getString("username"));
			user.setXiaoquId(Long.valueOf(obj.getString("xiaoquId")));
			user.setEnable(Boolean.valueOf(obj.getString("enable")));
			user.setNickname(obj.getString("nickname"));
			
		} catch (JSONException e) {
			LogUtils.e("Error occured at getCityFromJsonObject: " + e.getMessage());
			e.printStackTrace();
		}
		
		return user;
	}
	
	
	// 8. SparkingStallInfo
	public static ParkingStallInfo getParkingStallInfoFromJsonStr(String jsonStr) {
		ParkingStallInfo parkingStallInfo = new ParkingStallInfo();
		
		try {
			parkingStallInfo = getParkingStallInfoFromJsonObject(new JSONObject(jsonStr));
		} catch (JSONException e) {
			LogUtils.e("Error occured at getParkingStallInfoFromJsonStr: " + e.getMessage());
			e.printStackTrace();
		}
		
		return parkingStallInfo;
	}
	
	public static ParkingStallInfo getParkingStallInfoFromJsonObject(JSONObject obj) {
		ParkingStallInfo parkingStallInfo = new ParkingStallInfo();
		
		try {	
			parkingStallInfo.setId(obj.getInt("id"));
			parkingStallInfo.setSupplyDemandType(obj.getString("supplyDemandType"));
			parkingStallInfo.setTitle(obj.getString("title"));
			parkingStallInfo.setContent(obj.getString("content"));
			parkingStallInfo.setOwner(obj.getString("owner"));
			parkingStallInfo.setAddress(obj.getString("address"));
			parkingStallInfo.setAreaMeasure(obj.getDouble("areaMeasure"));
			parkingStallInfo.setNickname(obj.getString("nickname"));
			parkingStallInfo.setPhone(obj.getString("phone"));
			parkingStallInfo.setPrice(obj.getDouble("price"));
			parkingStallInfo.setPriceUnit(obj.getString("priceUnit"));
			parkingStallInfo.setYourIdentity(obj.getString("yourIdentity"));
			parkingStallInfo.setCreateTime(getDateDiffStr(obj.getLong("createDate")));			
			
		} catch (JSONException e) {
			LogUtils.e("Error occured at getParkingStallInfoFromJsonObject: " + e.getMessage());
			e.printStackTrace();
		}
		
		return parkingStallInfo;
	}
	
	public static List<ParkingStallInfo> getParkingStallInfoList(String jsonStr) {
		
		List<ParkingStallInfo> parkingStallInfoList = new ArrayList<ParkingStallInfo>();
		try {
			JSONArray  array = new JSONArray (jsonStr);
			
			for (int i = 0; i < array.length(); i++) {
                JSONObject item = array.getJSONObject(i);                
                parkingStallInfoList.add(getParkingStallInfoFromJsonObject(item));
            }
			
		} catch (JSONException e) {
			LogUtils.e("Error occured at getLocalXiaoquList: " + e.getMessage());
			e.printStackTrace();
		}
		
		return parkingStallInfoList;
	}
	
	
	
	
	// Format date to string format
	public static String getDateShowStyle(long longdate) {
		Date date = new Date(longdate);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(date);
	}
	public static String getDateStringWithMin(long longdate) {
		Date date = new Date(longdate);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return formatter.format(date);
	}
	
	// Get the date diff string with now
	public static String getDateDiffStr(long longdate) {
		String result = getDateStringWithMin(longdate);
		long diff = new Date().getTime() - longdate;
		
		// 毫秒
		if (diff < 1000) {
			result = "1秒前";
		} else {
			diff = diff/1000;
			// 分
			if (diff < 60) {
				result = diff+"秒前";
			} else {
				diff = diff/60;
				// 时
				if (diff < 60) {
					result = diff+"分前";
				} else {
					diff = diff/60;
					// 天
					if (diff < 24) {
						result = diff+"小时前";
					} else {
						diff = diff/24;
						// 月
						if (diff < 30) {
							result = diff+"天前";
						} else {
							diff = diff/30;				
							// 年
							if (diff < 12) {
								result = diff+"月前";
							}
						}
					}
				}
			}
		}
		
		return result;
	}
}
