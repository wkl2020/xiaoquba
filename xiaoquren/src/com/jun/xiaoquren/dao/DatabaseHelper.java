package com.jun.xiaoquren.dao;

import com.jun.xiaoquren.util.LocalLog;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Maintains the table structure
 * @author Administrator
 *
 */
public class DatabaseHelper extends SQLiteOpenHelper {
	
	public static final String CLASSNAME = "DatabaseHelper";
	
	private static final String DB_NAME = "mydata.db"; //数据库名称
	private static final int version = 1; //数据库版本
	
//	private final String DATABASE_PATH = android.os.Environment
//			.getExternalStorageDirectory().getAbsolutePath() + "/path"; // define the sqlite.db path
	 
	public DatabaseHelper(Context context) {		
		super(context, DB_NAME, null, version);
	    // TODO Auto-generated constructor stub
	}

	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int version, DatabaseErrorHandler errorHandler) {
		super(context, name, factory, version, errorHandler);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void close() {
		super.close();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		LocalLog.info(CLASSNAME, "onCreate", "Start Create Database and tables");
		
		// 1. Create table wkltable
		String sql = "create table wkltable(id int, username varchar(20), password varchar(60));";         
        db.execSQL(sql);
        
        //2. Create table constant_table 
        sql = "create table constant_table (id int, fieldname varchar(50), fieldvalue varchar(50));";
        db.execSQL(sql);
        
        //3. Create table xiaoqu_list 
        sql = "create table xiaoqu_list (id int, name varchar(50), address varchar(500), ismine varchar(10));";
        db.execSQL(sql);
        
        //4. Create table document  
        sql = "create table document (id int, xiaoquid int, type varchar(50), title varchar(50), content varchar(4000), author varchar(50), owner varchar(50), createtime varchar(50), publishtime varchar(50), expiretime varchar(50));";
        db.execSQL(sql);
        
        //5. Create table document_comment  
        sql = "create table document_comment (id int, document int, author varchar(50), nickname varchar(50), comment varchar(4000), createtime varchar(50));";
        db.execSQL(sql);
        
        LocalLog.info(CLASSNAME, "onCreate", "End Create Database and tables");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// 版本更新时调用

	}

}
