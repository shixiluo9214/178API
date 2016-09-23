package com.ccc.groupbuy.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.framework.bean.BaseBean;

@JsonIgnoreProperties({"eventId","eventType","createdDate","modifiedDate"})  
public class PayinfoBean extends BaseBean{	

	private static final long serialVersionUID = 1L;
	
	private long eventId;
	private String eventType;
	private String paymentType; //支付宝、微信
	private String accountId;
	private String accountName;
	
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
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
		
}
