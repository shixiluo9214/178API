package com.framework.bean;

public class BeanConstant {
	//Event: GroupBuy, Activity
	public static final String EVENT_TYPE_GROUPBUY = "EVENT_GROUPBUY";
	public static final String EVENT_TYPE_ACTIVITY = "EVENT_ACTIVITY";
	public static final String EVENT_TYPE_GROUPBUYITEM = "EVENT_GROUPBUY_ITEM";
	public static final String EVENT_TYPE_EXPERIENCE = "EVENT_EXPERIENCE";
	public static final String EVENT_TYPE_COMM = "EVENT_TYPE_COMM";	
	
	public static final String SCOPE_TYPE_USER = "USER";
	public static final String SCOPE_TYPE_GROUP = "GROUP";
	public static final String SCOPE_TYPE_COMM = "COMM";

	//ViewedByMe,CreatedByMe,ParticipateByMe,CanParticipateByMe,InSameComm
	public static final String GB_SEARCH_TYPE_INCOMM = "InSameComm";
	public static final String GB_SEARCH_TYPE_CREATED = "CreatedByMe";
	public static final String GB_SEARCH_TYPE_PARTICIPATED = "ParticipateByMe";
	
	//Action
	public static final String ACTION_GB_CREATE="GB_Create";
	public static final String ACTION_GB_PARTICIPATE="GB_Participate";
	public static final String ACTION_GB_TERMINATE="GB_Terminate";
	public static final String ACTION_GB_UPDATE_DELIVERINFO="GB_UpdateDeliverInfo";
	public static final String ACTION_GBP_TERMINATE="GBP_Terminate";
	public static final String ACTION_GBP_REMOVE="GBP_Remove";
	public static final String ACTION_GBP_DISUSE="GBP_Disuse";
	public static final String ACTION_GB_PURCHASE = "GB_Purchase";
	public static final String ACTION_GB_DELIVER = "GB_Deliver";
	public static final String ACTION_GB_NOTIFY_DELIVER = "GB_NotifyDeliver";
	public static final String ACTION_GB_COMPLETE = "GB_Complete";
	public static final String ACTION_GB_REMIND_PAY = "GB_RemindPay";
	public static final String ACTION_GB_REMIND_RECEIVE = "GB_RemindReceive";	
	public static final String ACTION_GB_JOB = "GB_Job";	
	public static final String ACTION_GB_JOB_REMIND_PURCHASE = "GB_Job_RemindPurchase";	
	public static final String ACTION_GB_JOB_REMIND_EDIT = "GB_Job_RemindEDIT";	
	public static final String ACTION_GB_JOB_TERMINATE_NO_PARTICIPATE = "GB_Job_TerminateNoPerticipate";	
	public static final String ACTION_GB_JOB_TERMINATE_NO_PURCHASE = "GB_Job_TerminateNoPurchase";	

	public static final String ACTION_USER_CONFIRM_ADDRESS = "User_ConfirmAddress";
	public static final String ACTION_USER_REJECT_ADDRESS = "User_RejectAddress";
	
	public static final String ACTION_COMM_CONFIRM = "Comm_Confirm";
	public static final String ACTION_COMM_REJECT = "Comm_Reject";
	

	public static final String MODULE_GROUPBUY = "GroupBuy";
	
	public static final long USER_ROOT_ID = 1;
	
	
	

}
