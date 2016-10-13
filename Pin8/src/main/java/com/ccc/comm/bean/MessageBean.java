package com.ccc.comm.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.framework.bean.BaseBean;
import com.framework.bean.RequestBean;

public class MessageBean extends BaseBean{

	private static final long serialVersionUID = 1L;
	
	private long sender;
	private long receiver;
	private int received;
	private String receivers;
	private String topic;
	private String messageType;  //requestPay/updateDeliver/
	private String message;
	private String eventType;
	private long eventId;
	private long ancestorId;
	private Date receivedDate;
	

	private long subEventId;
	private String senderNickName;
	private String reciverNickName;

	private String senderPic;
	private String receiverPic;

	public String getSenderPic() {
		return senderPic;
	}

	public void setSenderPic(String senderPic) {
		this.senderPic = senderPic;
	}

	public String getReceiverPic() {
		return receiverPic;
	}

	public void setReceiverPic(String receiverPic) {
		this.receiverPic = receiverPic;
	}

	public String getReciverNickName() {
		return reciverNickName;
	}
	public void setReciverNickName(String reciverNickName) {
		this.reciverNickName = reciverNickName;
	}
	public long getAncestorId() {
		return ancestorId;
	}
	public void setAncestorId(long ancestorId) {
		this.ancestorId = ancestorId;
	}
	public String getReceivers() {
		return receivers;
	}
	public void setReceivers(String receivers) {
		this.receivers = receivers;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public MessageBean(RequestBean request) {
		super();
		this.messageType=request.getAction();
		this.message=request.getMessage();
		this.eventId = request.getEventId();
		this.eventType = request.getEventType();
		this.sender = request.getUserId();
	}
	public MessageBean() {
		super();
	}
	
	public String getSenderNickName() {
		return senderNickName;
	}
	public void setSenderNickName(String senderNickName) {
		this.senderNickName = senderNickName;
	}
	public long getSubEventId() {
		return subEventId;
	}
	public void setSubEventId(long subEventId) {
		this.subEventId = subEventId;
	}
	public long getReceiver() {
		return receiver;
	}
	public void setReceiver(long receiver) {
		this.receiver = receiver;
	}
	public long getSender() {
		return sender;
	}
	public void setSender(long sender) {
		this.sender = sender;
	}
	public int getReceived() {
		return received;
	}
	public void setReceived(int received) {
		this.received = received;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")  
	public Date getReceivedDate() {
		return receivedDate;
	}
	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}
	public long getEventId() {
		return eventId;
	}
	public void setEventId(long eventId) {
		this.eventId = eventId;
	}
	
}
