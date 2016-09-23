package com.ccc.comm.bean;

public class HistorySummaryBean{

	private static final long serialVersionUID = 1L;
	
	private String operation;
	private String eventType;
	private int quantity;
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
}
