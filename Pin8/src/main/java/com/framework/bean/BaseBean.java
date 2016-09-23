package com.framework.bean;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties({"modifiedDate"})
public class BaseBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private long id;
	private Date createdDate;
	private Date modifiedDate;

	public Date getModifiedDate() {
		return modifiedDate;
	}
		
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	public String toString() {
	        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	 }
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}	 

}
