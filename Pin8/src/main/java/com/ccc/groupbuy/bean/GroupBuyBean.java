package com.ccc.groupbuy.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.framework.bean.BaseBean;


public class GroupBuyBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String title;
	private String catalog;
	private String description;
	private Date dueDate;
	private int memberLimit = 0;
	private String freightCal; //byQty 按照商品数量, byPeople 参与者均摊, byOthers 其它规则

	private String participateScope; //RegUser, ValidUser
	private String visibleScope; //Private, InComm
	private String contactType; //WeChat, Phone
	
	private String invitationCode;
	
	private int status;
	private int deliverStatus;
	private Date deliverDate;
	//0.单主即将下单；1.单主已下单；2.商家已发货；3.国际快递途中（海淘）；4.国内快递途中；5.货品已抵达单主家里（请尽快通知参与者取货付款）
	private String deliverInfo;
	private int totalMember;
	private long createdBy;
	private String nickName;
	private String ownerPic;
	private String picLink;
	private float listAmount;
	private float netAmount;
	private float freight;
	private float total;	
	
	private int type; //0 普通拼单；1 闪拼
	private int canAddItem; //0 can't add item by Participant, 1 can 

	private List<GroupBuyItemBean> items;
	private List<GroupBuyPurchaseBean> purchases;
	private List<PayinfoBean> payinfos;	
	
	private String message;
	private int countOfQuestion;
	private int countOfMessage;	
	
	public Date getDeliverDate() {
		return deliverDate;
	}

	public void setDeliverDate(Date deliverDate) {
		this.deliverDate = deliverDate;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getCanAddItem() {
		return canAddItem;
	}

	public void setCanAddItem(int canAddItem) {
		this.canAddItem = canAddItem;
	}

	public String getOwnerPic() {
		return ownerPic;
	}

	public void setOwnerPic(String ownerPic) {
		this.ownerPic = ownerPic;
	}

	public String getParticipateScope() {
		return participateScope;
	}

	public void setParticipateScope(String participateScope) {
		this.participateScope = participateScope;
	}

	public String getVisibleScope() {
		return visibleScope;
	}

	public void setVisibleScope(String visibleScope) {
		this.visibleScope = visibleScope;
	}

	public String getContactType() {
		return contactType;
	}

	public void setContactType(String contactType) {
		this.contactType = contactType;
	}

	public String getInvitationCode() {
		return invitationCode;
	}

	public void setInvitationCode(String invitationCode) {
		this.invitationCode = invitationCode;
	}

	public String getDeliverInfo() {
		return deliverInfo;
	}

	public void setDeliverInfo(String deliverInfo) {
		this.deliverInfo = deliverInfo;
	}

	public List<PayinfoBean> getPayinfos() {
		return payinfos;
	}

	public void setPayinfos(List<PayinfoBean> payinfos) {
		this.payinfos = payinfos;
	}

	public int getCountOfMessage() {
		return countOfMessage;
	}

	public void setCountOfMessage(int countOfMessage) {
		this.countOfMessage = countOfMessage;
	}

	public int getCountOfQuestion() {
		return countOfQuestion;
	}

	public void setCountOfQuestion(int countOfQuestion) {
		this.countOfQuestion = countOfQuestion;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getDeliverStatus() {
		return deliverStatus;
	}

	public void setDeliverStatus(int deliverStatus) {
		this.deliverStatus = deliverStatus;
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

	public String getFreightCal() {
		return freightCal;
	}

	public void setFreightCal(String freightCal) {
		this.freightCal = freightCal;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	@DateTimeFormat(pattern="yyyy-MM-dd")  
	@JsonFormat(pattern="yyyy-MM-dd")
	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<GroupBuyItemBean> getItems() {
		return items;
	}
	
	public int getMemberLimit() {
		return memberLimit;
	}

	public void setMemberLimit(int memberLimit) {
		this.memberLimit = memberLimit;
	}

	public void setItems(List<GroupBuyItemBean> items) {
		this.items = items;
	}
	
	public void addItem(GroupBuyItemBean item)
	{
		if(items == null)
		{
			items = new ArrayList<GroupBuyItemBean>();			
		}
		items.add(item);
	}
	

	public int getTotalMember() {
		return totalMember;
	}

	public void setTotalMember(int totalMember) {
		this.totalMember = totalMember;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPicLink() {
		return picLink;
	}

	public void setPicLink(String picLink) {
		this.picLink = picLink;
	}

	public List<GroupBuyPurchaseBean> getPurchases() {
		if(purchases == null){
			purchases = new ArrayList<GroupBuyPurchaseBean>();
		}
		return purchases;
	}

	public void setPurchases(List<GroupBuyPurchaseBean> purchases) {
		this.purchases = purchases;
	}
	
}
