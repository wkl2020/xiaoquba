package com.jun.xiaoquren.dao.model;

import java.io.Serializable;

public class LocalXiaoqu implements Serializable {

	private static final long serialVersionUID = -3682150426632758908L;
	
	private Integer id = null;
	private String name;
	private String address;

	public LocalXiaoqu() {
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
