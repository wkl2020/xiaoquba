package com.jun.xiaoquren.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jun.xiaoquren.dao.model.ConstantTable;

public class ConstantTableDao {
	
	public static final String ConstantsUsername = "constatns_username"; 
	public static final String ConstantsPassword = "constatns_password";
	public static final String ConstantsNickname = "constatns_nickname";
	public static final String ConstantsToken = "constatns_token";
	public static final String ConstantsCurrentXiaoquName = "constatns_currentxiaoquname";
	public static final String ConstantsCurrentXiaoquAddr = "constatns_currentxiaoquaddr";
	public static final String ConstantsCurrentXiaoquId = "constatns_currentxiaoquid";
	
	public ConstantTableDao() {
		// TODO Auto-generated constructor stub
	}
	
	public static ConstantTable cursorToBean(Cursor cursor) {
		int id = cursor.getInt(cursor.getColumnIndex("id"));  
        String fieldname = cursor.getString(cursor.getColumnIndex("fieldname"));
        String fieldvalue = cursor.getString(cursor.getColumnIndex("fieldvalue"));
        
		ConstantTable constant = new ConstantTable();
		constant.setId(id);
		constant.setFieldname(fieldname);
		constant.setFieldvalue(fieldvalue);
		return constant;
	}
	
	public static ContentValues beanToContentValues(ConstantTable constant) {
		ContentValues values = new ContentValues();
		values.put("id", constant.getId());
		values.put("fieldname", constant.getFieldname());
		values.put("fieldvalue", constant.getFieldvalue());
		return values;
	}
	
	public static List<ConstantTable> findAll() {
		List<ConstantTable> constants = new ArrayList<ConstantTable>();
		SQLiteDatabase db = DBUtil.getReadableDatabase(); 
		Cursor cursor = db.query(DBUtil.ConstantTable, null, null, null, null, null, null);
		if (cursor != null) {
			while (cursor.moveToNext()) {
				constants.add(cursorToBean(cursor));
			}
		}
		
		return constants;
	}
	
	public static ConstantTable findByName(String fieldname) {
		ConstantTable constant = null;
		SQLiteDatabase db = DBUtil.getReadableDatabase(); 
		Cursor cursor = db.query(DBUtil.ConstantTable, null, "fieldname=?", new String[]{fieldname}, null, null, null);
		if (cursor != null && cursor.moveToNext()) {
			constant = cursorToBean(cursor);
		}
		return constant;
	}
	
	public static void add(ConstantTable constant) {
		SQLiteDatabase db = DBUtil.getWritableDatabase();
		db.insert(DBUtil.ConstantTable, null, beanToContentValues(constant));
	}
	
	public static void updateByName(ConstantTable constant) {
		SQLiteDatabase db = DBUtil.getWritableDatabase();		
		db.update(DBUtil.ConstantTable, beanToContentValues(constant), "fieldname=?", new String[]{constant.getFieldname()}); 
	}
	
	public static void deleteByName(String fieldname) {
		SQLiteDatabase db = DBUtil.getWritableDatabase();
		db.delete(DBUtil.ConstantTable, "fieldname=?", new String[]{fieldname});
	}
	
	public static void deleteAll() {
		SQLiteDatabase db = DBUtil.getWritableDatabase();
		db.delete(DBUtil.ConstantTable, null, null);
	}

}
