package com.jun.xiaoquren.dao;

import java.util.ArrayList;
import java.util.List;

import com.jun.xiaoquren.dao.model.Xiaoqu;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class XiaoquListDao {

	public XiaoquListDao() {
		// TODO Auto-generated constructor stub
	}
	
	public static Xiaoqu cursorToBean(Cursor cursor) {
		int id = cursor.getInt(cursor.getColumnIndex("id"));  
        String name = cursor.getString(cursor.getColumnIndex("name"));
        String address = cursor.getString(cursor.getColumnIndex("address"));
        String ismine = cursor.getString(cursor.getColumnIndex("ismine"));
        
        Xiaoqu xaoqu = new Xiaoqu();
		xaoqu.setId(id);
		xaoqu.setName(name);
		xaoqu.setAddress(address);
		return xaoqu;
	}
	
	public static ContentValues beanToContentValues(Xiaoqu xaoqu) {
		ContentValues values = new ContentValues();
		values.put("id", xaoqu.getId());
		values.put("name", xaoqu.getName());
		values.put("address", xaoqu.getAddress());
		return values;
	}
	
	public static List<Xiaoqu> findAll() {
		List<Xiaoqu> xaoquList = new ArrayList<Xiaoqu>();
		SQLiteDatabase db = DBUtil.getReadableDatabase(); 
		Cursor cursor = db.query(DBUtil.XiaoquListTable, null, null, null, null, null, null);
		if (cursor != null) {
			while (cursor.moveToNext()) {
				xaoquList.add(cursorToBean(cursor));
			}
		}
		
		return xaoquList;
	}
	
	public static Xiaoqu findByName(String name) {
		Xiaoqu xaoqu = null;
		SQLiteDatabase db = DBUtil.getReadableDatabase(); 
		Cursor cursor = db.query(DBUtil.XiaoquListTable, null, "name=?", new String[]{name}, null, null, null);
		if (cursor != null && cursor.moveToNext()) {
			xaoqu = cursorToBean(cursor);
		}
		return xaoqu;
	}
	
	public static Xiaoqu findById(int id) {
		Xiaoqu xaoqu = null;
		SQLiteDatabase db = DBUtil.getReadableDatabase(); 
		Cursor cursor = db.query(DBUtil.XiaoquListTable, null, "id=?", new String[]{String.valueOf(id)}, null, null, null);
		if (cursor != null && cursor.moveToNext()) {
			xaoqu = cursorToBean(cursor);
		}
		return xaoqu;
	}
	
	public static void add(Xiaoqu xaoqu) {
		SQLiteDatabase db = DBUtil.getWritableDatabase();
		db.insert(DBUtil.XiaoquListTable, null, beanToContentValues(xaoqu));
	}
	
	public static void updateByName(Xiaoqu xaoqu) {
		SQLiteDatabase db = DBUtil.getWritableDatabase();		
		db.update(DBUtil.XiaoquListTable, beanToContentValues(xaoqu), "name=?", new String[]{xaoqu.getName()}); 
	}
	
	public static void updateById(Xiaoqu xaoqu) {
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