package com.ccc.user.bean;

import com.framework.bean.BaseBean;

public class UserSecretBean extends BaseBean{

	private static final long serialVersionUID = 1L;
	public static final String SECRET_TYPE_PHONE="PHONE";
	public static final String SECRET_TYPE_WECHAT="WECHAT";
	public static final String SECRET_TYPE_REALNAME="REALNAME";
	public static final String SECRET_TYPE_ADDRESS="ADDRESS";
	
	
	private long userId;
	private int hide=0; //0- show, 1-hide
	private String secretType; //PICTURE, PHONE, WECHAT,REALNAME,ADDRESS
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public int getHide() {
		return hide;
	}
	public void setHide(int hide) {
		this.hide = hide;
	}
	public String getSecretType() {
		return secretType;
	}
	public void setSecretType(String secretType) {
		this.secretType = secretType;
	}
}
