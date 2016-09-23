package com.ccc.groupbuy.bean;

import java.util.ArrayList;
import java.util.List;

import com.framework.bean.BaseBean;

public class GroupBuyPurchaseBean extends BaseBean{

	private static final long serialVersionUID = 1L;
	
	private long gbId;
	private long userId;
	private int status; // 0-normal, 10-disuse, 20-temination
	private int payStatus;
	private int collectStatus;
	private int receiveStatus;
	private float listAmount;
	private float netAmount;
	private float freight;
	private float total;	
	
	private String nickName;
	private String picLink;

	private List<GroupBuyItemPurchaseBean> items;

	public void addItem(GroupBuyItemPurchaseBean item){
		if(items ==null){
			items = new ArrayList<GroupBuyItemPurchaseBean>();			
		}
		items.add(item);
	}

	public String getPicLink() {
		return picLink;
	}

	public void setPicLink(String picLink) {
		this.picLink = picLink;
	}

	public int getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(int payStatus) {
		this.payStatus = payStatus;
	}

	public int getCollectStatus() {
		return collectStatus;
	}

	public void setCollectStatus(int collectStatus) {
		this.collectStatus = collectStatus;
	}

	public int getReceiveStatus() {
		return receiveStatus;
	}

	public void setReceiveStatus(int receiveStatus) {
		this.receiveStatus = receiveStatus;
	}

	public long getGbId() {
		return gbId;
	}

	public void setGbId(long gbId) {
		this.gbId = gbId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public float getListAmount() {
		return listAmount;
	}

	public void setListAmount(float listAmount) {
		this.listAmount = listAmount;
	}

	public float getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(float netAmount) {
		this.netAmount = netAmount;
	}

	public float getFreight() {
		return freight;
	}

	public void setFreight(float freight) {
		this.freight = freight;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public List<GroupBuyItemPurchaseBean> getItems() {
		return items;
	}

	public void setItems(List<GroupBuyItemPurchaseBean> items) {
		this.items = items;
	}	
}
