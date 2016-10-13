package com.ccc.user.bean;

import com.framework.bean.BaseBean;

public class FriendBean extends BaseBean{

	private static final long serialVersionUID = 1L;
	
	private long userId;
	private long friendId;
	private int status=0; //0-apply, 1- friend, 20-deleted
	private String nickName;
	private String picLink;
		
	public String getPicLink() {
		return picLink;
	}
	public void setPicLink(String picLink) {
		this.picLink = picLink;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getFriendId() {
		return friendId;
	}
	public void setFriendId(long friendId) {
		this.friendId = friendId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
}
