package com.ccc.groupbuy.bean;

import com.framework.bean.BaseBean;

public class GroupBuyItemPurchaseBean extends BaseBean {

	private static final long serialVersionUID = 1L;
	
	private long gbpId;
	private long gbiId;
	private float quantity;
	private float netQuantity = -1;
	private float freight = -1;	

	private long userId;
	private String nickName;

	private String name;
	private float listPrice;
	private float netPrice;
	private String unit;
	
	public long getGbpId() {
		return gbpId;
	}
	public void setGbpId(long gbpId) {
		this.gbpId = gbpId;
	}
	public long getGbiId() {
		return gbiId;
	}
	public void setGbiId(long gbiId) {
		this.gbiId = gbiId;
	}
	public float getQuantity() {
		return quantity;
	}
	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}	
	public float getNetQuantity() {
		return netQuantity;
	}
	public void setNetQuantity(float netQuantity) {
		this.netQuantity = netQuantity;
	}
	public float getFreight() {
		return freight;
	}
	public void setFreight(float freight) {
		this.freight = freight;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getListPrice() {
		return listPrice;
	}
	public void setListPrice(float listPrice) {
		this.listPrice = listPrice;
	}
	public float getNetPrice() {
		return netPrice;
	}
	public void setNetPrice(float netPrice) {
		this.netPrice = netPrice;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
}
