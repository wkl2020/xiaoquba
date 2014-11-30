package com.jun.xiaoquren.dao.model;

import java.io.Serializable;

public class ConstantTable implements Serializable  {

	private static final long serialVersionUID = -432046111192397802L;
	
	private Integer id = null;	
	private String fieldname;
	private String fieldvalue;
	
	public ConstantTable() {
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

	public String getFieldname() {
		return fieldname;
	}

	public void setFieldname(String fieldname) {
		this.fieldname = fieldname;
	}

	public String getFieldvalue() {
		return fieldvalue;
	}

	public void setFieldvalue(String fieldvalue) {
		this.fieldvalue = fieldvalue;
	}
}
