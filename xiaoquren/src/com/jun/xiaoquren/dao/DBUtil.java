package com.jun.xiaoquren.dao;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.jun.xiaoquren.dao.model.ConstantTable;

/**
 * Add the transaction for each DB operate
 * @author Administrator
 *
 */
public class DBUtil {
	
	public static String ConstantTable = "constant_table";
	public static String XiaoquListTable = "xiaoqu_list";
	public static String DocumentTable = "document";
	public static String DocumentCommentTable = "document_comment";
	
	private static DatabaseHelper database = null;

	public static DatabaseHelper getDatabase() {
		return database;
	}

	public static void setDatabase(DatabaseHelper database) {
		DBUtil.database = database;
	}

	public DBUtil() {
		// TODO Auto-generated constructor stub
	}
	
	public static void initDBConnection(Context context) {
		database = new DatabaseHelper(context);//这段代码放到Activity类中才用this
    	database.getReadableDatabase();
    	
    	// Add test data
    	List<ConstantTable> constants = ConstantTableDao.findAll();
    	if (constants.size() == 0) {
    		ConstantTable newconstant = new ConstantTable();
    		newconstant.setId(1);
    		newconstant.setFieldname("username");
    		newconstant.setFieldvalue("wklusername");
    		ConstantTableDao.add(newconstant);
    		
    		newconstant = new ConstantTable();
    		newconstant.setId(1);
    		newconstant.setFieldname("password");
    		newconstant.setFieldvalue("wklpassword");
    		ConstantTableDao.add(newconstant);
    		
    		newconstant = new ConstantTable();
    		newconstant.setId(1);
    		newconstant.setFieldname("nickname");
    		newconstant.setFieldvalue("wklnickname");
    		ConstantTableDao.add(newconstant);
    		
    		newconstant = new ConstantTable();
    		newconstant.setId(1);
    		newconstant.setFieldname("token");
    		newconstant.setFieldvalue("wkltoken");
    		ConstantTableDao.add(newconstant);
    		
    		newconstant = new ConstantTable();
    		newconstant.setId(1);
    		newconstant.setFieldname("currentxiaoquname");
    		newconstant.setFieldvalue("wklcurrentxiaoquname");
    		ConstantTableDao.add(newconstant);
    		
    		newconstant = new ConstantTable();
    		newconstant.setId(1);
    		newconstant.setFieldname("currentxiaoquaddr");
    		newconstant.setFieldvalue("wklcurrentxiaoquaddr");
    		ConstantTableDao.add(newconstant);
    		
    		newconstant = new ConstantTable();
    		newconstant.setId(1);
    		newconstant.setFieldname("currentxiaoqu");
    		newconstant.setFieldvalue("wklcurrentxiaoqu");
    		ConstantTableDao.add(newconstant);
    	}
    	constants = ConstantTableDao.findAll();
    	for (ConstantTable con : constants) {
    		System.out.println("query---->ConstantTable---->" + con.getId() + " : " + con.getFieldname() + " : " + con.getFieldvalue());
    	}
	}
	
	public static SQLiteDatabase getReadableDatabase() {
		return database.getReadableDatabase();
	}
	
	public static  SQLiteDatabase getWritableDatabase() {
		return database.getWritableDatabase();
	}

}
