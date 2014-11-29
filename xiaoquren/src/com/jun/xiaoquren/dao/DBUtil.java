package com.jun.xiaoquren.dao;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.jun.xiaoquren.dao.model.ConstantTable;
import com.jun.xiaoquren.dao.model.Document;
import com.jun.xiaoquren.dao.model.XiaoquList;

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
    	
    	// ConstantTable
    	List<ConstantTable> constants = ConstantTableDao.findAll();
    	if (constants.size() == 0) {
    		ConstantTable newconstant = new ConstantTable();
    		newconstant.setId(1);
    		newconstant.setFieldname("username");
    		newconstant.setFieldvalue("wklusername");
    		ConstantTableDao.add(newconstant);
    		
    		newconstant = new ConstantTable();
    		newconstant.setId(2);
    		newconstant.setFieldname("password");
    		newconstant.setFieldvalue("wklpassword");
    		ConstantTableDao.add(newconstant);
    		
    		newconstant = new ConstantTable();
    		newconstant.setId(3);
    		newconstant.setFieldname("nickname");
    		newconstant.setFieldvalue("wklnickname");
    		ConstantTableDao.add(newconstant);
    		
    		newconstant = new ConstantTable();
    		newconstant.setId(4);
    		newconstant.setFieldname("token");
    		newconstant.setFieldvalue("wkltoken");
    		ConstantTableDao.add(newconstant);
    		
    		newconstant = new ConstantTable();
    		newconstant.setId(5);
    		newconstant.setFieldname("currentxiaoquname");
    		newconstant.setFieldvalue("wklcurrentxiaoquname");
    		ConstantTableDao.add(newconstant);
    		
    		newconstant = new ConstantTable();
    		newconstant.setId(6);
    		newconstant.setFieldname("currentxiaoquaddr");
    		newconstant.setFieldvalue("wklcurrentxiaoquaddr");
    		ConstantTableDao.add(newconstant);
    		
    		newconstant = new ConstantTable();
    		newconstant.setId(7);
    		newconstant.setFieldname("currentxiaoqu");
    		newconstant.setFieldvalue("wklcurrentxiaoqu");
    		ConstantTableDao.add(newconstant);
    	}
    	constants = ConstantTableDao.findAll();
    	for (ConstantTable con : constants) {
    		System.out.println("query---->ConstantTable---->" + con.getId() + " : " + con.getFieldname() + " : " + con.getFieldvalue());
    	}
    	
    	// XiaoquList
    	List<XiaoquList> xiaoqulists = XiaoquListDao.findAll();
    	if (xiaoqulists.size() == 0) {
    		XiaoquList xiaoqu = new XiaoquList();
    		xiaoqu.setId(1);
    		xiaoqu.setName("新凯家园");
    		xiaoqu.setAddress("南京西路1899号");
    		XiaoquListDao.add(xiaoqu);
    		
    		xiaoqu = new XiaoquList();
    		xiaoqu.setId(2);
    		xiaoqu.setName("音都雅苑");
    		xiaoqu.setAddress("南京西路1899号");
    		XiaoquListDao.add(xiaoqu);
    		
    		xiaoqu = new XiaoquList();
    		xiaoqu.setId(3);
    		xiaoqu.setName("xiaoqu3");
    		xiaoqu.setAddress("address3");
    		XiaoquListDao.add(xiaoqu);
    		
    		xiaoqu = new XiaoquList();
    		xiaoqu.setId(4);
    		xiaoqu.setName("xiaoqu4");
    		xiaoqu.setAddress("address4");
    		XiaoquListDao.add(xiaoqu);
    		
    		xiaoqu = new XiaoquList();
    		xiaoqu.setId(5);
    		xiaoqu.setName("xiaoqu5");
    		xiaoqu.setAddress("address5");
    		XiaoquListDao.add(xiaoqu);
    		
    		xiaoqu = new XiaoquList();
    		xiaoqu.setId(6);
    		xiaoqu.setName("xiaoqu6");
    		xiaoqu.setAddress("address6");
    		XiaoquListDao.add(xiaoqu);
    	}
    	xiaoqulists = XiaoquListDao.findAll();
    	for (XiaoquList con : xiaoqulists) {
    		System.out.println("query---->XiaoquList---->" + con.getId() + " : " + con.getName() + " : " + con.getAddress());
    	}
    	
    	// Document
    	List<Document> documents = DocumentDao.findAll();
    	if (documents.size() == 0) {
    		Document document = new Document();
    		document.setId(1);
    		document.setXiaoquid(1);
    		document.setType("type1");
    		document.setTitle("title1");
    		document.setAuthor("author1");
    		document.setOwner("owner1");
    		document.setContent("content1");
    		document.setCreatetime("2014-11-11 11:11");
    		document.setPublishtime("2014-11-11 11:11");
    		document.setExpiretime("2014-11-11 11:11");
    		DocumentDao.add(document);
    	}
    	documents = DocumentDao.findAll();
    	for (Document doc : documents) {
    		System.out.println("query---->Document---->" + doc.getId() + " : " + doc.getXiaoquid() + " : " + doc.getType() + " : " + doc.getTitle() + " : " + doc.getContent() + " : " + doc.getOwner() + " : " + doc.getCreatetime() + " : " + doc.getPublishtime() + " : " + doc.getExpiretime());
    	}
	}
	
	public static SQLiteDatabase getReadableDatabase() {
		return database.getReadableDatabase();
	}
	
	public static  SQLiteDatabase getWritableDatabase() {
		return database.getWritableDatabase();
	}

}
