package com.ccc.groupbuy.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.framework.bean.BaseBean;

@JsonIgnoreProperties({"createdDate","modifiedDate"})  
public class GroupBuyItemBean extends BaseBean  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String name;
	private float listPrice;
	private String unit;
	private float quantityLimit = -1;
	private float quantity;	
	private String detail;		

	private float netPrice = -1;
	private float totalQuantity;
	private float netQuantity = -1;
	private float freight = -1;

	private long gbId;	
	private long userId;

	private List<PictureBean> pics;
	private List<GroupBuyItemPurchaseBean> itemPurchases;

	public void addPic(PictureBean pic){
		if(pics == null){
			pics = new ArrayList<PictureBean>();
		}
		pics.add(pic);
	}
	
	public void addPic(String picFile){
		if(pics == null){
			pics = new ArrayList<PictureBean>();
		}
		PictureBean pic = new PictureBean();
		pic.setPicLink(picFile);
		pics.add(pic);
	}
	
	public void addItemPurchase(GroupBuyItemPurchaseBean item){
		if(itemPurchases ==null){
			itemPurchases = new ArrayList<GroupBuyItemPurchaseBean>();			
		}
		itemPurchases.add(item);
	}
	
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public List<GroupBuyItemPurchaseBean> getItemPurchases() {
		return itemPurchases;
	}

	public void setItemPurchases(List<GroupBuyItemPurchaseBean> itemPurchases) {
		this.itemPurchases = itemPurchases;
	}

	public long getGbId() {
		return gbId;
	}

	public void setGbId(long gbId) {
		this.gbId = gbId;
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

	public float getQuantityLimit() {
		return quantityLimit;
	}

	public void setQuantityLimit(float quantityLimit) {
		this.quantityLimit = quantityLimit;
	}

	public float getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(float totalQuantity) {
		this.totalQuantity = totalQuantity;
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

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public float getQuantity() {
		return quantity;
	}

	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}

	public List<PictureBean> getPics() {
		return pics;
	}

	public void setPics(List<PictureBean> pics) {
		this.pics = pics;
	}
	
}
