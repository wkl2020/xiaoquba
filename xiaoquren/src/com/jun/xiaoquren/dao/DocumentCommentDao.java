//package com.jun.xiaoquren.dao;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import android.content.ContentValues;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//
//import com.jun.xiaoquren.dao.model.DocumentComment;
//
//public class DocumentCommentDao {
//	
//	public static final String CLASSNAME = "DocumentCommentDao";
//
//	public DocumentCommentDao() {
//		// TODO Auto-generated constructor stub
//	}
//	
//	public static DocumentComment cursorToBean(Cursor cursor) {
//		int id = cursor.getInt(cursor.getColumnIndex("id"));  
//		int document = cursor.getInt(cursor.getColumnIndex("document"));
//        String author = cursor.getString(cursor.getColumnIndex("author"));
//        String nickname = cursor.getString(cursor.getColumnIndex("nickname"));
//        String comment = cursor.getString(cursor.getColumnIndex("comment"));
//        String createtime = cursor.getString(cursor.getColumnIndex("createtime"));
//        
//		DocumentComment dcomment = new DocumentComment();
//		dcomment.setId(id);
//		dcomment.setDocument(document);
//		dcomment.setAuthor(author);
//		dcomment.setNickname(nickname);
//		dcomment.setComment(comment);
//		dcomment.setCreatetime(createtime);
//		
//		return dcomment;
//	}
//	
//	public static ContentValues beanToContentValues(DocumentComment dcomment) {
//		ContentValues values = new ContentValues();
//		values.put("id", dcomment.getId());
//		values.put("document", dcomment.getDocument());
//		values.put("author", dcomment.getAuthor());
//		values.put("nickname", dcomment.getNickname());
//		values.put("comment", dcomment.getComment());
//		values.put("createtime", dcomment.getCreatetime());
//		
//		return values;
//	}
//	
//	public static List<DocumentComment> findAll() {
//		List<DocumentComment> dcomments = new ArrayList<DocumentComment>();
//		SQLiteDatabase db = DBUtil.getReadableDatabase(); 
//		Cursor cursor = db.query(DBUtil.DocumentCommentTable, null, null, null, null, null, null);
//		if (cursor != null) {
//			while (cursor.moveToNext()) {
//				dcomments.add(cursorToBean(cursor));
//			}
//		}
//		
//		return dcomments;
//	}
//
//	public static List<DocumentComment> findByDocumentId(int documentId) {
//		List<DocumentComment> dcomments = new ArrayList<DocumentComment>();
//		SQLiteDatabase db = DBUtil.getReadableDatabase(); 
//		Cursor cursor = db.query(DBUtil.DocumentCommentTable, null, "id=?", new String[]{String.valueOf(documentId)}, null, null, "createtime desc");
//		if (cursor != null) {
//			while (cursor.moveToNext()) {
//				dcomments.add(cursorToBean(cursor));
//			}
//		}
//		
//		return dcomments;
//	}
//	
//	public static void add(DocumentComment dcomment) {
//		SQLiteDatabase db = DBUtil.getWritableDatabase();
//		db.insert(DBUtil.DocumentCommentTable, null, beanToContentValues(dcomment));
//	}
//	
//	public static void updateById(DocumentComment dcomment) {
//		SQLiteDatabase db = DBUtil.getWritableDatabase();		
//		db.update(DBUtil.DocumentCommentTable, beanToContentValues(dcomment), "id=?", new String[]{String.valueOf(dcomment.getId())}); 
//	}
//	
//	public static void deleteByCommentId(int commentId) {
//		SQLiteDatabase db = DBUtil.getWritableDatabase();
//		db.delete(DBUtil.DocumentCommentTable, "id=?", new String[]{String.valueOf(commentId)});
//	}
//	
//	public static void deleteByDocumentId(int documentId) {
//		SQLiteDatabase db = DBUtil.getWritableDatabase();
//		db.delete(DBUtil.DocumentCommentTable, "document=?", new String[]{String.valueOf(documentId)});
//	}
//	
//	public static void deleteAll() {
//		SQLiteDatabase db = DBUtil.getWritableDatabase();
//		db.delete(DBUtil.DocumentCommentTable, null, null);
//	}
//}
