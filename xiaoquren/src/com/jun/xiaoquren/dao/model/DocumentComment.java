package com.jun.xiaoquren.dao.model;

import java.io.Serializable;

public class DocumentComment implements Serializable  {

	private static final long serialVersionUID = -7047730276548094748L;
	
	private Integer id = null;
	private Integer document;
	private String author;
	private String nickname;
	private String comment;
	private String createtime;

	public DocumentComment() {
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public String getStringId() {
		return String.valueOf(id);
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDocument() {
		return document;
	}

	public void setDocument(Integer document) {
		this.document = document;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	
}
