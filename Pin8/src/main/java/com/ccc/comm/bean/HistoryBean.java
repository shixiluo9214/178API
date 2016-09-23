package com.ccc.comm.bean;

import com.framework.bean.BaseBean;

public class HistoryBean extends BaseBean{

	private static final long serialVersionUID = 1L;
	
	private long syslogId;
	private long userId;
	private String module;
	private String operation;
	private String message;
	private String eventType;
	private long eventId;

	private Object inputObject;
	private Object outputObject;
	
	private String nickName;
	private int quantity;
	
	public HistoryBean(SysLogBean log){
		this.syslogId = log.getId();
		this.userId = log.getUserId();
		this.module = log.getModule();
		this.operation = log.getOperation();		
	}
	public HistoryBean() {
		super();
	}
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Object getInputObject() {
		return inputObject;
	}

	public void setInputObject(Object inputObject) {
		this.inputObject = inputObject;
	}

	public Object getOutputObject() {
		return outputObject;
	}

	public void setOutputObject(Object outputObject) {
		this.outputObject = outputObject;
	}

	public long getSyslogId() {
		return syslogId;
	}
	public void setSyslogId(long syslogId) {
		this.syslogId = syslogId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public long getEventId() {
		return eventId;
	}
	public void setEventId(long eventId) {
		this.eventId = eventId;
	}
}
