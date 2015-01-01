package com.jun.xiaoquren.dao;

import java.util.ArrayList;
import java.util.List;

import com.jun.xiaoquren.dao.model.LocalXiaoqu;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class XiaoquListDao {
	
	public static final String CLASSNAME = "XiaoquListDao";

	public XiaoquListDao() {
		// TODO Auto-generated constructor stub
	}
	
	public static LocalXiaoqu cursorToBean(Cursor cursor) {
		int id = cursor.getInt(cursor.getColumnIndex("id"));  
        String name = cursor.getString(cursor.getColumnIndex("name"));
        String address = cursor.getString(cursor.getColumnIndex("address"));
        String ismine = cursor.getString(cursor.getColumnIndex("ismine"));
        
        LocalXiaoqu xaoqu = new LocalXiaoqu();
		xaoqu.setId(id);
		xaoqu.setName(name);
		xaoqu.setAddress(address);
		return xaoqu;
	}
	
	public static ContentValues beanToContentValues(LocalXiaoqu xaoqu) {
		ContentValues values = new ContentValues();
		values.put("id", xaoqu.getId());
		values.put("name", xaoqu.getName());
		values.put("address", xaoqu.getAddress());
		return values;
	}
	
	public static List<LocalXiaoqu> findAll() {
		List<LocalXiaoqu> xaoquList = new ArrayList<LocalXiaoqu>();
		SQLiteDatabase db = DBUtil.getReadableDatabase(); 
		Cursor cursor = db.query(DBUtil.XiaoquListTable, null, null, null, null, null, null);
		if (cursor != null) {
			while (cursor.moveToNext()) {
				xaoquList.add(cursorToBean(cursor));
			}
		}
		
		return xaoquList;
	}
	
	public static LocalXiaoqu findByName(String name) {
		LocalXiaoqu xaoqu = null;
		SQLiteDatabase db = DBUtil.getReadableDatabase(); 
		Cursor cursor = db.query(DBUtil.XiaoquListTable, null, "name=?", new String[]{name}, null, null, null);
		if (cursor != null && cursor.moveToNext()) {
			xaoqu = cursorToBean(cursor);
		}
		return xaoqu;
	}
	
	public static LocalXiaoqu findById(int id) {
		LocalXiaoqu xaoqu = null;
		SQLiteDatabase db = DBUtil.getReadableDatabase(); 
		Cursor cursor = db.query(DBUtil.XiaoquListTable, null, "id=?", new String[]{String.valueOf(id)}, null, null, null);
		if (cursor != null && cursor.moveToNext()) {
			xaoqu = cursorToBean(cursor);
		}
		return xaoqu;
	}
	
	public static void add(LocalXiaoqu xaoqu) {
		SQLiteDatabase db = DBUtil.getWritableDatabase();
		db.insert(DBUtil.XiaoquListTable, null, beanToContentValues(xaoqu));
	}
	
	public static void updateByName(LocalXiaoqu xaoqu) {
		SQLiteDatabase db = DBUtil.getWritableDatabase();		
		db.update(DBUtil.XiaoquListTable, beanToContentValues(xaoqu), "name=?", new String[]{xaoqu.getName()}); 
	}
	
	public static void updateById(LocalXiaoqu xaoqu) {
		SQLiteDatabase db = DBUtil.getWritableDatabase();		
		db.update(DBUtil.XiaoquListTable, beanToContentValues(xaoqu), "id=?", new String[]{String.valueOf(xaoqu.getId())}); 
	}
	
	public static void deleteByName(String name) {
		SQLiteDatabase db = DBUtil.getWritableDatabase();
		db.delete(DBUtil.XiaoquListTable, "name=?", new String[]{name});
	}
	
	public static void deleteAll() {
		SQLiteDatabase db = DBUtil.getWritableDatabase();
		db.delete(DBUtil.XiaoquListTable, null, null);
	}

}
