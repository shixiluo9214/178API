package com.ccc.comm.bean;

import java.util.List;

import com.framework.bean.BaseBean;
import com.framework.bean.RequestBean;

public class SurveyBean extends BaseBean{

	private static final long serialVersionUID = 1L;
	
	private String eventType;
	private long eventId;
	private long userId;
	private String action;	

	List<SurveyDefBean> surveys;

	public SurveyBean(RequestBean request){
		super();
		this.action=request.getAction();
		this.eventId = request.getEventId();
		this.eventType = request.getEventType();
		this.userId = request.getUserId();
		this.surveys = request.getSurveys();
	}
	
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public long getEventId() {
		return eventId;
	}
	public void setEventId(long eventId) {
		this.eventId = eventId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public List<SurveyDefBean> getSurveys() {
		return surveys;
	}
	public void setSurveys(List<SurveyDefBean> surveys) {
		this.surveys = surveys;
	}
	
}
