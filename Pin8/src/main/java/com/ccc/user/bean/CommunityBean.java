package com.ccc.user.bean;

import java.io.Serializable;

import com.framework.bean.BaseBean;

public class CommunityBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private String address;
	private int status; //-2 rejected, -1 pending, 0 active
	private String country="中国";
	private String province;
	private String city;
	private String district;	
	private int appliedBy;
	private String picLink;
	
	private int size; 	
	
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getPicLink() {
		return picLink;
	}
	public void setPicLink(String picLink) {
		this.picLink = picLink;
	}
	
	public int getAppliedBy() {
		return appliedBy;
	}
	public void setAppliedBy(int appliedBy) {
		this.appliedBy = appliedBy;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
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
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
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
	public void setStatus(int status) {
		this.status = status;
	}
	public int getStatus() {
		return status;
	}
	
}
