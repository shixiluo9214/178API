package com.ccc.groupbuy.service.impl;
 

import java.security.GeneralSecurityException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccc.comm.bean.HistoryBean;
import com.ccc.comm.bean.HistorySummaryBean;
import com.ccc.comm.bean.MessageBean;
import com.ccc.comm.bean.QuestionBean;
import com.ccc.comm.bean.SurveyBean;
import com.ccc.comm.bean.SysLogBean;
import com.ccc.comm.service.CommService;
import com.ccc.groupbuy.bean.GroupBuyBean;
import com.ccc.groupbuy.bean.GroupBuyItemBean;
import com.ccc.groupbuy.bean.GroupBuyItemPurchaseBean;
import com.ccc.groupbuy.bean.GroupBuyPurchaseBean;
import com.ccc.groupbuy.bean.GroupBuySearchBean;
import com.ccc.groupbuy.dao.GroupBuyDao;
import com.ccc.groupbuy.service.GroupBuyService;
import com.ccc.user.service.UserService;
import com.framework.bean.BeanConstant;
import com.framework.bean.RequestBean;
import com.framework.bean.ResponseBean;
import com.framework.utils.Cfg;
import com.framework.utils.security.EncryptionUtil;

@Service
public class GroupBuyServiceImpl implements GroupBuyService {
	@Autowired
	private GroupBuyDao groupBuyDaoImpl;
	@Autowired
	private CommService commServiceImpl;
	@Autowired
	private UserService userServiceImpl;

	@Override
	public int create(GroupBuyBean groupBuy) throws GeneralSecurityException {
		groupBuy.setInvitationCode(EncryptionUtil.generateInvitationCode());
		userServiceImpl.saveGBAddress(groupBuy);
		return groupBuyDaoImpl.create(groupBuy);
	}

	@Override
	public int purchase(GroupBuyBean groupBuy) {
		groupBuy.setStatus(1);
		int result = groupBuyDaoImpl.setStatus(groupBuy);		

		MessageBean message = new MessageBean();
		message.setEventId(groupBuy.getId());
		message.setEventType(BeanConstant.EVENT_TYPE_GROUPBUY);
		message.setMessageType(BeanConstant.ACTION_GB_PURCHASE);
		commServiceImpl.sendMessage(message);
		
		return result;
	}

	@Override
	public int deliver(GroupBuyBean groupBuy) {
		groupBuy.setStatus(2);
		int result = groupBuyDaoImpl.setStatus(groupBuy);
		
		groupBuyDaoImpl.updateSatus4Owner(groupBuy);

		MessageBean message = new MessageBean();
		message.setEventId(groupBuy.getId());
		message.setEventType(BeanConstant.EVENT_TYPE_GROUPBUY);
		message.setMessageType(BeanConstant.ACTION_GB_DELIVER);
		message.setMessage(groupBuy.getMessage());
		commServiceImpl.sendMessage(message);
		
		return result;		
		
	}
	
	@Override
	public int updateDeliverInfo(GroupBuyBean groupBuy) {
		int result = groupBuyDaoImpl.update(groupBuy);

		MessageBean message = new MessageBean();
		message.setEventId(groupBuy.getId());
		message.setEventType(BeanConstant.EVENT_TYPE_GROUPBUY);
		message.setMessageType(BeanConstant.ACTION_GB_UPDATE_DELIVERINFO);
		message.setMessage(groupBuy.getMessage());
		commServiceImpl.sendMessage(message);
		
		return result;		
	}

	@Override
	public int notifyDeliver(GroupBuyBean groupBuy) {
		groupBuy.setStatus(3);
		int result = groupBuyDaoImpl.notifyDeliver(groupBuy);		

		MessageBean message = new MessageBean();
		message.setEventId(groupBuy.getId());
		message.setEventType(BeanConstant.EVENT_TYPE_GROUPBUY);
		message.setMessageType(BeanConstant.ACTION_GB_NOTIFY_DELIVER);
		message.setMessage(groupBuy.getMessage());		
		commServiceImpl.sendMessage(message);
		
		return result;
	}

	@Override
	public int terminate(RequestBean request) {
		GroupBuyBean groupBuy = new GroupBuyBean();
		groupBuy.setId(request.getId());
		groupBuy.setStatus(20);
		int result = groupBuyDaoImpl.setStatus(groupBuy);

		SurveyBean survey = new SurveyBean(request);
		commServiceImpl.saveSurvey(survey);		

		MessageBean message = new MessageBean(request);
		commServiceImpl.sendMessage(message);
				
		return result;
		
	}

	@Override
	public int terminateParticipation(RequestBean request) {
		int result = groupBuyDaoImpl.terminateParticipation(request.getId());
		groupBuyDaoImpl.calculateAmount4Purchase(request.getId());

		SurveyBean survey = new SurveyBean(request);
		commServiceImpl.saveSurvey(survey);
		
		return result;
	}

	@Override
	public int removeParticipation(RequestBean request) {
		int result = groupBuyDaoImpl.terminateParticipation(request.getId());
		groupBuyDaoImpl.calculateAmount4Purchase(request.getId());		

		MessageBean message = new MessageBean();
		message.setEventType(request.getEventType());
		message.setSubEventId(request.getId());
		message.setMessageType(request.getAction());
		message.setMessage(request.getMessage());
		commServiceImpl.sendMessage(message);
		
		return result;
	}

	
	@Override
	public int complete(GroupBuyBean groupBuy) {
		groupBuy.setStatus(10);
		int result = groupBuyDaoImpl.setStatus(groupBuy);
	//	userServiceImpl.addFriendinGB(groupBuy.getId());

		MessageBean message = new MessageBean();
		message.setEventId(groupBuy.getId());
		message.setEventType(BeanConstant.EVENT_TYPE_GROUPBUY);
		message.setMessageType(BeanConstant.ACTION_GB_COMPLETE);
		commServiceImpl.sendMessage(message);
		
		return result;
	}
		
	@Override
	public int additems(GroupBuyItemBean[] items) throws Exception {
		int result =0;
		int addPic = 0;
		GroupBuyPurchaseBean groupBuyPurchase = new GroupBuyPurchaseBean();
		GroupBuyItemPurchaseBean itemPurchase;
		for(int i=0;i<items.length;i++){
			result += groupBuyDaoImpl.addItem(items[i]);
			if(items[i].getPics() != null){
				for(int j=0;j<items[i].getPics().size();j++){
					items[i].getPics().get(j).setEventId(items[i].getId());
					items[i].getPics().get(j).setEventType(BeanConstant.EVENT_TYPE_GROUPBUYITEM);
					groupBuyDaoImpl.addPic(items[i].getPics().get(j));
				}
				if(addPic == 0){
					groupBuyDaoImpl.addPic4GB(items[i].getGbId());
					addPic = 1;
				}
			}
			if(items[i].getQuantity()>0){

				groupBuyPurchase.setGbId(items[i].getGbId());
				groupBuyPurchase.setUserId(items[i].getUserId());
				
				itemPurchase = new GroupBuyItemPurchaseBean();
				itemPurchase.setGbiId(items[i].getId());
				itemPurchase.setQuantity(items[i].getQuantity());
				groupBuyPurchase.addItem(itemPurchase);
			}
		}
		
		if(groupBuyPurchase.getItems()!= null && groupBuyPurchase.getItems().size()>0){
			//add new item
			List<GroupBuyPurchaseBean> purchases = groupBuyDaoImpl.getPurchases(groupBuyPurchase.getGbId(), groupBuyPurchase.getUserId());
			if(purchases.size()>0){
				groupBuyPurchase.setId(purchases.get(0).getId());
			}
			participate(groupBuyPurchase);
		}
		return result;
	}

	@Override
	public int participateWithValid(GroupBuyPurchaseBean groupBuyPurchase) throws Exception {
		
		validateParticipate(groupBuyPurchase);
		return participate(groupBuyPurchase);
	}
	
	private int participate(GroupBuyPurchaseBean groupBuyPurchase){

		int result = 0;
		if(0==groupBuyPurchase.getId()){
			result = groupBuyDaoImpl.insertParticipate(groupBuyPurchase);
		}
		
		for(int i=0;i<groupBuyPurchase.getItems().size();i++){
			groupBuyPurchase.getItems().get(i).setGbpId(groupBuyPurchase.getId());
			groupBuyDaoImpl.participateItem(groupBuyPurchase.getItems().get(i));
		}
		
		groupBuyDaoImpl.calculateAmount4Purchase(groupBuyPurchase.getId());
		return result;
	}
	
	private void validateParticipate(GroupBuyPurchaseBean groupBuyPurchase) throws Exception {
		int updated = 0;
		String message = "";
		GroupBuyBean groupBuy = groupBuyDaoImpl.getGroupBuy(groupBuyPurchase.getGbId());
		if(groupBuy.getStatus() != 0){			
			if(groupBuy.getStatus() == 1){
				message +="单主已经开始采购，不能参加了。下次抓紧哦。";
			}else if(groupBuy.getStatus() == 10){
				message +="单主已经取消拼单，请下次再参与吧。";
			}else{
				message +="单主已经修改了拼单状态，不能参加了。下次抓紧哦。";
			}
			
		}else{
			List<GroupBuyItemBean> items = groupBuyDaoImpl.getItems(groupBuyPurchase.getGbId(),groupBuyPurchase.getUserId());
			
			for(int i=0;i<groupBuyPurchase.getItems().size();i++)
			{
				for(int j=0;j<items.size();j++){
					if(updated == 0 && items.get(j).getQuantity()>0){
						updated = 1;
					}
					if(groupBuyPurchase.getItems().get(i).getGbiId()==items.get(j).getId()){
						if(items.get(j).getQuantityLimit()>0 && items.get(j).getTotalQuantity()-items.get(j).getQuantity()+groupBuyPurchase.getItems().get(i).getQuantity()
								> items.get(j).getQuantityLimit()){
							message+="你所拼单的商品<"+items.get(j).getName()+">已超过数量上限！\n";
						}
						break;
					}
				}			
			}
			if(groupBuy.getMemberLimit()>0 && updated==0 && groupBuy.getTotalMember()+1>groupBuy.getMemberLimit()){
				message+="您所参与的拼单已超过人数限定。";
			}
		}
		if(StringUtils.isNotEmpty(message)){
			throw new Exception(message);
		}
		
	}

	@Override
	public List<GroupBuyBean> getList(GroupBuySearchBean search) {
		List<GroupBuyBean> gbList = groupBuyDaoImpl.getList(search);
		List<GroupBuyPurchaseBean> purchases = groupBuyDaoImpl.getGBPList(search);
		int found = 0;
		if(purchases != null && gbList != null ){
			for(int i=0,size=gbList.size();i<size;i++){
				found = 0;
				for(int j=0,s=purchases.size();j<s;j++){
					if(gbList.get(i).getId()==purchases.get(j).getGbId()){
						gbList.get(i).getPurchases().add(purchases.get(j));
						found = 1;
					}else if(found ==1){
						break;
					}
				}
			}
		}
		return gbList;
	}	

	@Override
	public List<HistorySummaryBean> getGBSummary(long userId) {
		return groupBuyDaoImpl.getGBSummary(userId);
	}

	@Override
	public GroupBuyBean getGroupBuy(GroupBuySearchBean search) {
		GroupBuyBean groupBuy = groupBuyDaoImpl.getGroupBuy(search);
		groupBuy.setPayinfos(groupBuyDaoImpl.getPayinfos(search.getGbId()));
		if(search.getUserId()==groupBuy.getCreatedBy()){
			groupBuy.setItems(groupBuyDaoImpl.getItems(search.getGbId(),0l));
		}else{
			groupBuy.setItems(groupBuyDaoImpl.getItems(search.getGbId(),search.getUserId()));
		}
		for(int i=0;i<groupBuy.getItems().size();i++){
			groupBuy.getItems().get(i).setPics(groupBuyDaoImpl.getPics(groupBuy.getItems().get(i).getId()));
			if(search.getUserId()==groupBuy.getCreatedBy()){
				groupBuy.getItems().get(i).setItemPurchases(groupBuyDaoImpl.getItemPurchases(groupBuy.getItems().get(i).getId()));
			}
		}
		if(search.getUserId()==groupBuy.getCreatedBy()){
			groupBuy.setPurchases(groupBuyDaoImpl.getPurchases(groupBuy.getId(),0l));
		}else{
			groupBuy.setPurchases(groupBuyDaoImpl.getPurchases(groupBuy.getId(),search.getUserId()));
		}
		QuestionBean qBean = new QuestionBean();
		qBean.setEventId(groupBuy.getId());
		qBean.setEventType(BeanConstant.EVENT_TYPE_GROUPBUY);
		groupBuy.setCountOfQuestion(commServiceImpl.getCountOfQuestion(qBean));
		
		MessageBean message = new MessageBean();
		message.setEventId(search.getGbId());
		message.setEventType(BeanConstant.EVENT_TYPE_GROUPBUY);
		message.setReceiver(search.getUserId());
		groupBuy.setCountOfMessage(commServiceImpl.getCountOfMessage(message));
		
		return groupBuy;
	}

	@Override
	public int adjustItem(GroupBuyItemBean[] items) {
		for(int i=0;i<items.length;i++)
		{
			if(!(items[i].getNetQuantity()<0 && items[i].getNetPrice()<0 && items[i].getFreight()<0)){
				groupBuyDaoImpl.adjustItem(items[i]);
			}
			if(null !=items[i].getItemPurchases()){
				for(int j=0;j<items[i].getItemPurchases().size();j++)
				{
					groupBuyDaoImpl.adjustItemPurchase(items[i].getItemPurchases().get(j));
				}
			}
		}
		groupBuyDaoImpl.calculateAmount4GB(items[0].getGbId());
		return items.length;
	}

	@Override
	public int pay(GroupBuyPurchaseBean groupBuyPurchase) {
		return groupBuyDaoImpl.pay(groupBuyPurchase);
	}

	@Override
	public int receive(GroupBuyPurchaseBean groupBuyPurchase) {
		return groupBuyDaoImpl.receive(groupBuyPurchase);
	}

	@Override
	public int collect(GroupBuyPurchaseBean groupBuyPurchase) {
		return groupBuyDaoImpl.collect(groupBuyPurchase);
	}
	@Override
	public int remindPay(GroupBuyPurchaseBean groupBuyPurchase) {
		MessageBean message = new MessageBean();
		message.setEventType(BeanConstant.EVENT_TYPE_GROUPBUY);
		message.setSubEventId(groupBuyPurchase.getId());
		message.setMessageType(BeanConstant.ACTION_GB_REMIND_PAY);
		
		return commServiceImpl.sendMessage(message);
	}

	@Override
	public int remindReceive(GroupBuyPurchaseBean groupBuyPurchase) {
		MessageBean message = new MessageBean();
		message.setEventType(BeanConstant.EVENT_TYPE_GROUPBUY);
		message.setSubEventId(groupBuyPurchase.getId());
		message.setMessageType(BeanConstant.ACTION_GB_REMIND_RECEIVE);
		
		return commServiceImpl.sendMessage(message);
	}

	@Override
	public int saveHistory(HistoryBean history) {
		String message = Cfg.getMessage(history.getModule()+"_"+history.getOperation());
		if(StringUtils.isEmpty(message)){
			return 0;
		}
		ResponseBean response = null;
		GroupBuyBean groupBuy = null;
		RequestBean request = null;
		GroupBuyPurchaseBean groupBuyPurchase = null;
		history.setMessage(message);			
		history.setEventType(BeanConstant.EVENT_TYPE_GROUPBUY);
		if(StringUtils.equals("create",history.getOperation()))
		{
			response = (ResponseBean)(history.getOutputObject());
			groupBuy = (GroupBuyBean)(response.getBean());
			history.setEventId(groupBuy.getId());
		}else if(StringUtils.equals("purchase",history.getOperation()) || StringUtils.equals("deliver",history.getOperation()) 
				|| StringUtils.equals("complete",history.getOperation()) || StringUtils.equals("updateDeliverInfo",history.getOperation())){
			groupBuy = (GroupBuyBean)(history.getInputObject());
			history.setEventId(groupBuy.getId());
		}else if(StringUtils.equals("terminate",history.getOperation())){
			request = (RequestBean)(history.getInputObject());
			history.setEventId(request.getId());
		}else if(StringUtils.equals("terminateParticipation",history.getOperation())){
			request = (RequestBean)(history.getInputObject());
			groupBuyPurchase = groupBuyDaoImpl.getPurchaseById(request.getId());
			history.setEventId(groupBuyPurchase.getGbId());
		}else if(StringUtils.equals("participate",history.getOperation())){
			groupBuyPurchase = (GroupBuyPurchaseBean)(history.getInputObject());
			history.setEventId(groupBuyPurchase.getGbId());
		}else if(StringUtils.equals("pay",history.getOperation()) || StringUtils.equals("receive",history.getOperation()) 
				|| StringUtils.equals("collect",history.getOperation())){
			groupBuyPurchase = (GroupBuyPurchaseBean)(history.getInputObject());
			groupBuyPurchase = groupBuyDaoImpl.getPurchaseById(groupBuyPurchase.getId());
			history.setEventId(groupBuyPurchase.getGbId());
			if(StringUtils.equals("collect",history.getOperation()))
			{
				history.setMessage(history.getMessage().replaceAll("UserNickName", groupBuyPurchase.getNickName()));
			}
		}else if(StringUtils.equals("adjustItem",history.getOperation())){
			GroupBuyItemBean[] items = (GroupBuyItemBean[])(history.getInputObject());
			history.setEventId(items[0].getGbId());		     
		}
		
		return commServiceImpl.saveHistory(history);

	}

	@Override
	public int update(GroupBuyBean groupBuy) {
		int result = groupBuyDaoImpl.update(groupBuy);
		
		if(groupBuy.getPayinfos()!=null){
			groupBuyDaoImpl.removePayinfo(groupBuy.getId());
			for(int i=0;i<groupBuy.getPayinfos().size();i++){
				groupBuy.getPayinfos().get(i).setEventId(groupBuy.getId());
				groupBuy.getPayinfos().get(i).setEventType(BeanConstant.EVENT_TYPE_GROUPBUY);
				groupBuyDaoImpl.createPayinfo(groupBuy.getPayinfos().get(i));
			}
		}
		
		return result;
	}

	@Override
	public int disuseParticipation(RequestBean request) {
		return groupBuyDaoImpl.disuseParticipation(request.getId());
	}

	@Override
	public void scheduledJob(SysLogBean log) {
		//Reminder owner for purchase after due date if there are participate
		MessageBean message = new MessageBean();
		message.setEventType(BeanConstant.EVENT_TYPE_GROUPBUY);
		message.setMessageType(BeanConstant.ACTION_GB_JOB_REMIND_PURCHASE);		
		message.setSender(BeanConstant.USER_ROOT_ID);
		commServiceImpl.sendMessage(message);		

		//Reminder owner for edit due date after due date if there is no participate
		message = new MessageBean();
		message.setEventType(BeanConstant.EVENT_TYPE_GROUPBUY);
		message.setMessageType(BeanConstant.ACTION_GB_JOB_REMIND_EDIT);		
		message.setSender(BeanConstant.USER_ROOT_ID);
		commServiceImpl.sendMessage(message);
		

		//Terminate group buy if  it is not been purchased 7 days after due date
		List<GroupBuyBean> groupBuyList = groupBuyDaoImpl.getList4Job(BeanConstant.ACTION_GB_JOB_TERMINATE_NO_PURCHASE);
    	HistoryBean history = new HistoryBean(log);
    	history.setOperation(BeanConstant.ACTION_GB_JOB_TERMINATE_NO_PURCHASE);
		message = new MessageBean();
		message.setEventType(BeanConstant.EVENT_TYPE_GROUPBUY);
		message.setSender(BeanConstant.USER_ROOT_ID);
		message.setMessageType(BeanConstant.ACTION_GB_JOB_TERMINATE_NO_PURCHASE);		

    	for(int i=0;i<groupBuyList.size();i++){
			groupBuyList.get(i).setStatus(20);
			groupBuyDaoImpl.setStatus(groupBuyList.get(i));
			
			history.setEventId(groupBuyList.get(i).getId());
			this.saveHistory(history);
			
			message.setEventId(groupBuyList.get(i).getId());
			message.setReceiver(groupBuyList.get(i).getCreatedBy());
			commServiceImpl.sendMessage(message);				
		}
			

		//Terminate group buy when 3 day after due date if there is no participate
    	groupBuyList = groupBuyDaoImpl.getList4Job(BeanConstant.ACTION_GB_JOB_TERMINATE_NO_PARTICIPATE);
    	history = new HistoryBean(log);
    	history.setOperation(BeanConstant.ACTION_GB_JOB_TERMINATE_NO_PARTICIPATE);
		message = new MessageBean();
		message.setEventType(BeanConstant.EVENT_TYPE_GROUPBUY);
		message.setSender(BeanConstant.USER_ROOT_ID);
		message.setMessageType(BeanConstant.ACTION_GB_JOB_TERMINATE_NO_PARTICIPATE);		

    	for(int i=0;i<groupBuyList.size();i++){
			groupBuyList.get(i).setStatus(20);
			groupBuyDaoImpl.setStatus(groupBuyList.get(i));
			
			history.setEventId(groupBuyList.get(i).getId());
			this.saveHistory(history);
			
			message.setEventId(groupBuyList.get(i).getId());
			message.setReceiver(groupBuyList.get(i).getCreatedBy());
			commServiceImpl.sendMessage(message);				
		}
		
	}

}
