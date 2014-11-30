package com.jun.xiaoquren.dao;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.jun.xiaoquren.dao.model.ConstantTable;
import com.jun.xiaoquren.dao.model.Document;
import com.jun.xiaoquren.dao.model.Xiaoqu;

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
    		newconstant.setFieldname(ConstantTableDao.ConstantsUsername);
    		newconstant.setFieldvalue("wklusername");
    		ConstantTableDao.add(newconstant);
    		
    		newconstant = new ConstantTable();
    		newconstant.setId(2);
    		newconstant.setFieldname(ConstantTableDao.ConstantsPassword);
    		newconstant.setFieldvalue("wklpassword");
    		ConstantTableDao.add(newconstant);
    		
    		newconstant = new ConstantTable();
    		newconstant.setId(3);
    		newconstant.setFieldname(ConstantTableDao.ConstantsNickname);
    		newconstant.setFieldvalue("wklnickname");
    		ConstantTableDao.add(newconstant);
    		
    		newconstant = new ConstantTable();
    		newconstant.setId(4);
    		newconstant.setFieldname(ConstantTableDao.ConstantsToken);
    		newconstant.setFieldvalue("wkltoken");
    		ConstantTableDao.add(newconstant);
    		
    		newconstant = new ConstantTable();
    		newconstant.setId(5);
    		newconstant.setFieldname(ConstantTableDao.ConstantsCurrentXiaoquName);
    		newconstant.setFieldvalue("wklcurrentxiaoquname");
    		ConstantTableDao.add(newconstant);
    		
    		newconstant = new ConstantTable();
    		newconstant.setId(6);
    		newconstant.setFieldname(ConstantTableDao.ConstantsCurrentXiaoquAddr);
    		newconstant.setFieldvalue("wklcurrentxiaoquaddr");
    		ConstantTableDao.add(newconstant);
    		
    		newconstant = new ConstantTable();
    		newconstant.setId(7);
    		newconstant.setFieldname(ConstantTableDao.ConstantsCurrentXiaoquId);
    		newconstant.setFieldvalue("wklcurrentxiaoquid");
    		ConstantTableDao.add(newconstant);
    	}
    	constants = ConstantTableDao.findAll();
    	for (ConstantTable con : constants) {
    		System.out.println("query---->ConstantTable---->" + con.getId() + " : " + con.getFieldname() + " : " + con.getFieldvalue());
    	}
    	
    	// XiaoquList
    	List<Xiaoqu> xiaoqulists = XiaoquListDao.findAll();
    	if (xiaoqulists.size() == 0) {
    		Xiaoqu xiaoqu = new Xiaoqu();
    		xiaoqu.setId(1);
    		xiaoqu.setName("新凯家园");
    		xiaoqu.setAddress("南京西路1899号");
    		XiaoquListDao.add(xiaoqu);
    		
    		xiaoqu = new Xiaoqu();
    		xiaoqu.setId(2);
    		xiaoqu.setName("音都雅苑");
    		xiaoqu.setAddress("南京西路1899号");
    		XiaoquListDao.add(xiaoqu);
    		
    		xiaoqu = new Xiaoqu();
    		xiaoqu.setId(3);
    		xiaoqu.setName("xiaoqu3");
    		xiaoqu.setAddress("address3");
    		XiaoquListDao.add(xiaoqu);
    		
    		xiaoqu = new Xiaoqu();
    		xiaoqu.setId(4);
    		xiaoqu.setName("xiaoqu4");
    		xiaoqu.setAddress("address4");
    		XiaoquListDao.add(xiaoqu);
    		
    		xiaoqu = new Xiaoqu();
    		xiaoqu.setId(5);
    		xiaoqu.setName("xiaoqu5");
    		xiaoqu.setAddress("address5");
    		XiaoquListDao.add(xiaoqu);
    		
    		xiaoqu = new Xiaoqu();
    		xiaoqu.setId(6);
    		xiaoqu.setName("xiaoqu6");
    		xiaoqu.setAddress("address6");
    		XiaoquListDao.add(xiaoqu);
    	}
    	xiaoqulists = XiaoquListDao.findAll();
    	for (Xiaoqu con : xiaoqulists) {
    		System.out.println("query---->XiaoquList---->" + con.getId() + " : " + con.getName() + " : " + con.getAddress());
    	}
    	
    	// Document
    	List<Document> documents = DocumentDao.findAll();
    	if (documents.size() == 0) {
    		Document document = new Document();
    		document.setId(1);
    		document.setXiaoquid(1);
    		document.setType("type1");
    		document.setTitle("2014年度物业管理费缴纳通知");
    		document.setAuthor("author1");
    		document.setOwner("紫竹园物业管理处1");
    		document.setContent("1即日起开始收缴2014年年度物业管理费。需缴纳的业主，请前往物业管理处缴纳，如需上门收费的业主，请拨打物业管理处电话：51234566管理与上门会出具正规的统一机打税控发票上门收取。以防不法分子冒充物业管理处人员上门诈骗。");
    		document.setCreatetime("2014-11-11 11:11");
    		document.setPublishtime("2014-11-11 11:11");
    		document.setExpiretime("2014-11-11 11:11");
    		DocumentDao.add(document);
    		
    		document = new Document();
    		document.setId(2);
    		document.setXiaoquid(1);
    		document.setType("type1");
    		document.setTitle("电能标调换预告");
    		document.setAuthor("author1");
    		document.setOwner("紫竹园物业管理处2");
    		document.setContent("2即日起开始收缴2014年年度物业管理费。需缴纳的业主，请前往物业管理处缴纳，如需上门收费的业主，请拨打物业管理处电话：51234566管理与上门会出具正规的统一机打税控发票上门收取。以防不法分子冒充物业管理处人员上门诈骗。");
    		document.setCreatetime("2014-11-11 11:11");
    		document.setPublishtime("2014-11-11 11:11");
    		document.setExpiretime("2014-11-11 11:11");
    		DocumentDao.add(document);
    		
    		document = new Document();
    		document.setId(3);
    		document.setXiaoquid(1);
    		document.setType("type1");
    		document.setTitle("关于上海市黄浦区汤臣一品业主委员会换届改选小组成员名单的公示");
    		document.setAuthor("author1");
    		document.setOwner("紫竹园物业管理处3");
    		document.setContent("3即日起开始收缴2014年年度物业管理费。需缴纳的业主，请前往物业管理处缴纳，如需上门收费的业主，请拨打物业管理处电话：51234566管理与上门会出具正规的统一机打税控发票上门收取。以防不法分子冒充物业管理处人员上门诈骗。");
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
