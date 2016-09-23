package com.ccc.user.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ccc.comm.bean.HistorySummaryBean;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.framework.bean.BaseBean;

@JsonIgnoreProperties({"creditAmount","creditCount"})
public class UserBean extends BaseBean{

	private static final long serialVersionUID = 1L;
	
	private long id;
	private String phone;
	private String password;
	private String realName;
	private String nickName;
	private String wechatId;

	private int status = -1;
	private int locked = 0;
	private String salt;
	private String token;
	private String macAddress;
	private String osVersion;
	private Date tokenCreatedDate;
	
	private long addressId;
	private long commId;
	private String commName;

	private String picLink;
	private String address;
	
	private long friendId;
	
	private long score;
	private float credit;
	private long creditAmount;
	private int creditCount;
	
	private List<HistorySummaryBean> historyList;
	private List<UserSecretBean> secretList;
	
	public static UserBean getExample()
	{
		UserBean user = new UserBean();
		user.setPhone("123456778");
		user.setPassword("password");
		user.setRealName("CCC user real name");	
		user.setCreatedDate(new Date());
		user.setModifiedDate(new Date());		
		
		return user;
	}
	public void addItem(HistorySummaryBean history)
	{
		if(historyList == null)
		{
			historyList = new ArrayList<HistorySummaryBean>();			
		}
		historyList.add(history);
	}
	public void addSecret(UserSecretBean secret)
	{
		if(secretList == null)
		{
			secretList = new ArrayList<UserSecretBean>();			
		}
		secretList.add(secret);
	}	
	
	public long getScore() {
		return score;
	}
	public void setScore(long score) {
		this.score = score;
	}
	public float getCredit() {
		return credit;
	}
	public void setCredit(float credit) {
		this.credit = credit;
	}	
	public long getCreditAmount() {
		return creditAmount;
	}
	public void setCreditAmount(long creditAmount) {
		this.creditAmount = creditAmount;
	}
	public int getCreditCount() {
		return creditCount;
	}
	public void setCreditCount(int creditCount) {
		this.creditCount = creditCount;
	}
	public long getFriendId() {
		return friendId;
	}
	public void setFriendId(long friendId) {
		this.friendId = friendId;
	}
	public List<UserSecretBean> getSecretList() {
		return secretList;
	}
	public void setSecretList(List<UserSecretBean> secretList) {
		this.secretList = secretList;
	}
	public long getAddressId() {
		return addressId;
	}
	public void setAddressId(long addressId) {
		this.addressId = addressId;
	}
	public List<HistorySummaryBean> getHistoryList() {
		return historyList;
	}
	public void setHistoryList(List<HistorySummaryBean> historyList) {
		this.historyList = historyList;
	}
	public String getWechatId() {
		return wechatId;
	}

	public void setWechatId(String wechatId) {
		this.wechatId = wechatId;
	}


	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public String getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	public Date getTokenCreatedDate() {
		return tokenCreatedDate;
	}

	public void setTokenCreatedDate(Date tokenCreatedDate) {
		this.tokenCreatedDate = tokenCreatedDate;
	}

	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getLocked() {
		return locked;
	}
	public void setLocked(int locked) {
		this.locked = locked;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getCommId() {
		return commId;
	}

	public void setCommId(long commId) {
		this.commId = commId;
	}

	public String getCommName() {
		return commName;
	}

	public void setCommName(String commName) {
		this.commName = commName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getPicLink() {
		return picLink;
	}

	public void setPicLink(String picLink) {
		this.picLink = picLink;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
}
