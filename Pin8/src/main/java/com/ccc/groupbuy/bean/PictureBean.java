package com.ccc.groupbuy.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.framework.bean.BaseBean;

@JsonIgnoreProperties({"eventId","eventType","createdDate","modifiedDate","defaultFlag"})  
public class PictureBean extends BaseBean {	

	private static final long serialVersionUID = 1L;
	private long eventId;
	private String eventType;
	private String picLink;
	private int defaultFlag;
	
	public int getDefaultFlag() {
		return defaultFlag;
	}
	public void setDefaultFlag(int defaultFlag) {
		this.defaultFlag = defaultFlag;
	}
	public long getEventId() {
		return eventId;
	}
	public void setEventId(long eventId) {
		this.eventId = eventId;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public String getPicLink() {
		return picLink;
	}
	public void setPicLink(String picLink) {
		this.picLink = picLink;
	}
	
}
