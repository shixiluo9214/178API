package com.ccc.user.bean;

import java.io.Serializable;

import com.framework.bean.BaseBean;

public class AddressBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long userId;
	private long commId;
	private String address;
	private int status=-1; //0- initial status, 1-pending authenticate, 2-authenticated, 20-deleted
	private int defaultFlag;
	private String picLink;
	
	private String commName;	
	private String country;
	private String province;
	private String city;
	private String district;
	
	private int type; //0 user address,	1 group buy address
	
	private String message;
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getCommName() {
		return commName;
	}
	public void setCommName(String commName) {
		this.commName = commName;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getCommId() {
		return commId;
	}
	public void setCommId(long commId) {
		this.commId = commId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPicLink() {
		return picLink;
	}
	public void setPicLink(String picLink) {
		this.picLink = picLink;
	}
	public int getDefaultFlag() {
		return defaultFlag;
	}
	public void setDefaultFlag(int defaultFlag) {
		this.defaultFlag = defaultFlag;
	}	
}
