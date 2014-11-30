package com.jun.xiaoquren.dao.model;

import java.io.Serializable;

public class Document implements Serializable  {

	private static final long serialVersionUID = 7423383626345692958L;
	
	private Integer id = null;
	private Integer xiaoquid = null;
	private String type;
	private String title;
	private String content;	
	private String author;
	private String owner;  // empty now
	private String createtime;
	private String publishtime;
	private String expiretime;

	public Document() {
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

	public Integer getXiaoquid() {
		return xiaoquid;
	}

	public void setXiaoquid(Integer xiaoquid) {
		this.xiaoquid = xiaoquid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getPublishtime() {
		return publishtime;
	}

	public void setPublishtime(String publishtime) {
		this.publishtime = publishtime;
	}

	public String getExpiretime() {
		return expiretime;
	}

	public void setExpiretime(String expiretime) {
		this.expiretime = expiretime;
	}
}
