package com.framework.bean;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ResponseBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int status = 0;
	private String errorCode;
	private String errorMessage;
	
	private Object bean;	
	
	 public String toString() {
	        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	 }



	public int getStatus() {
		return status;
	}



	public void setStatus(int status) {
		this.status = status;
	}



	public String getErrorCode() {
		return errorCode;
	}



	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}



	public String getErrorMessage() {
		return errorMessage;
	}



	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}



	public Object getBean() {
		return bean;
	}



	public void setBean(Object bean) {
		this.bean = bean;
	}
	 

}
