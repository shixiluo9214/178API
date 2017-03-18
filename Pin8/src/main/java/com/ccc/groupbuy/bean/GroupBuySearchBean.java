package com.ccc.groupbuy.bean;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class GroupBuySearchBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long gbId;
	private String catalog;
	private long userId;
	private String filterType; //ViewedByMe,CreatedByMe,ParticipateByMe,CanParticipateByMe
	private int status;
	private Date fromDate;
	private Date toDate;
	private String invitationCode;
	
	public String getInvitationCode() {
		return invitationCode;
	}
	public void setInvitationCode(String invitationCode) {
		this.invitationCode = invitationCode;
	}
	public long getGbId() {
		return gbId;
	}
	public void setGbId(long gbId) {
		this.gbId = gbId;
	}
	public String getCatalog() {
		return catalog;
	}
	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getFilterType() {
		return filterType;
	}
	public void setFilterType(String filterType) {
		this.filterType = filterType;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date startDate) {
		this.fromDate = startDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date endDate) {
		this.toDate = endDate;
	}	
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		if(userId>0){
			sb.append("userid:").append(userId).append(",");
		}
		if(!StringUtils.isEmpty(filterType)){
			sb.append("filterType:").append(filterType).append(",");
		}
		return sb.toString();
	}
}
