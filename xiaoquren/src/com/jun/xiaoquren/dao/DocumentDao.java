package com.jun.xiaoquren.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jun.xiaoquren.dao.model.Document;

public class DocumentDao {
	
	public static final String CLASSNAME = "DocumentDao";

	public DocumentDao() {
		// TODO Auto-generated constructor stub
	}
	
	public static Document cursorToBean(Cursor cursor) {
		int id = cursor.getInt(cursor.getColumnIndex("id"));  
		int xiaoquid = cursor.getInt(cursor.getColumnIndex("xiaoquid"));
        String type = cursor.getString(cursor.getColumnIndex("type"));
        String title = cursor.getString(cursor.getColumnIndex("title"));
        String content = cursor.getString(cursor.getColumnIndex("content"));
        String author = cursor.getString(cursor.getColumnIndex("author"));
        String owner = cursor.getString(cursor.getColumnIndex("owner"));
        String createtime = cursor.getString(cursor.getColumnIndex("createtime"));
        String publishtime = cursor.getString(cursor.getColumnIndex("publishtime"));
        String expiretime = cursor.getString(cursor.getColumnIndex("expiretime"));
        
		Document document = new Document();
		document.setId(id);
		document.setXiaoquid(xiaoquid);
		document.setType(type);
		document.setTitle(title);
		document.setContent(content);
		document.setAuthor(author);
		document.setOwner(owner);
		document.setCreatetime(createtime);
		document.setPublishtime(publishtime);
		document.setExpiretime(expiretime);
		return document;
	}
	
	public static ContentValues beanToContentValues(Document document) {
		ContentValues values = new ContentValues();
		values.put("id", document.getId());
		values.put("xiaoquid", document.getXiaoquid());
		values.put("type", document.getType());
		values.put("title", document.getTitle());
		values.put("content", document.getContent());
		values.put("author", document.getAuthor());
		values.put("owner", document.getOwner());
		values.put("createtime", document.getCreatetime());
		values.put("publishtime", document.getPublishtime());
		values.put("expiretime", document.getExpiretime());
		return values;
	}
	
	public static List<Document> findAll() {
		List<Document> documents = new ArrayList<Document>();
		SQLiteDatabase db = DBUtil.getReadableDatabase(); 
		Cursor cursor = db.query(DBUtil.DocumentTable, null, null, null, null, null, null);
		if (cursor != null) {
			while (cursor.moveToNext()) {
				documents.add(cursorToBean(cursor));
			}
		}
		
		return documents;
	}

	public static Document findByDocumentId(int documentId) {
		Document document = null;
		SQLiteDatabase db = DBUtil.getReadableDatabase(); 
		Cursor cursor = db.query(DBUtil.DocumentTable, null, "id=?", new String[]{String.valueOf(documentId)}, null, null, null);
		if (cursor != null && cursor.moveToNext()) {
			document = cursorToBean(cursor);
		}
		
		return document;
	}
	
	public static List<Document> findByXiaoquId(int xiaoquid) {
		List<Document> documents = new ArrayList<Document>();
		SQLiteDatabase db = DBUtil.getReadableDatabase(); 
		Cursor cursor = db.query(DBUtil.DocumentTable, null, "xiaoquid=?", new String[]{String.valueOf(xiaoquid)}, null, null, "createtime desc");
		if (cursor != null) {
			while (cursor.moveToNext()) {
				documents.add(cursorToBean(cursor));
			}
		}
		
		return documents;
	}
	
	public static void add(Document document) {
		SQLiteDatabase db = DBUtil.getWritableDatabase();
		db.insert(DBUtil.DocumentTable, null, beanToContentValues(document));
	}
	
	public static void updateByDocumentId(Document document) {
		SQLiteDatabase db = DBUtil.getWritableDatabase();		
		db.update(DBUtil.DocumentTable, beanToContentValues(document), "id=?", new String[]{String.valueOf(document.getId())}); 
	}
	
	public static void deleteByDocumentId(int documentId) {
		SQLiteDatabase db = DBUtil.getWritableDatabase();
		db.delete(DBUtil.DocumentTable, "id=?", new String[]{String.valueOf(documentId)});
	}
	
	public static void deleteAll() {
		SQLiteDatabase db = DBUtil.getWritableDatabase();
		db.delete(DBUtil.DocumentTable, null, null);
	}
}
