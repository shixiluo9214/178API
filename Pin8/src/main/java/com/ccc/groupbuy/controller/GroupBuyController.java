package com.ccc.groupbuy.controller;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.ccc.groupbuy.bean.GroupBuyBean;
import com.ccc.groupbuy.bean.GroupBuyItemBean;
import com.ccc.groupbuy.bean.GroupBuyPurchaseBean;
import com.ccc.groupbuy.bean.GroupBuySearchBean;
import com.ccc.groupbuy.service.GroupBuyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.framework.bean.BeanConstant;
import com.framework.bean.RequestBean;
import com.framework.bean.ResponseBean;
import com.framework.utils.ErrorUtils;
import com.framework.utils.FileUtils;

@Controller
@RequestMapping("/groupbuy") 
public class GroupBuyController {
	
	private static final Logger logger = Logger.getLogger(GroupBuyController.class);	
	@Autowired
	private GroupBuyService groupBuyServiceImpl;
	private ObjectMapper objectMapper = null; 
	
	@RequestMapping(value = "/create", produces = "application/json;charset=UTF-8")
	public @ResponseBody ResponseBean create(@RequestBody GroupBuyBean groupBuy)
	{
		logger.info("GroupBuy Create "+ groupBuy);
		ResponseBean response = new ResponseBean();
		try {
			groupBuyServiceImpl.create(groupBuy);
			response.setBean(groupBuy);
		} catch (Exception e) {
			ErrorUtils.setError(response, e);
		}
		return response;
	}	
	
	@RequestMapping(value = "/update", produces = "application/json;charset=UTF-8")
	public @ResponseBody ResponseBean update(@RequestBody GroupBuyBean groupBuy)
	{
		logger.info("GroupBuy update "+ groupBuy);
		ResponseBean response = new ResponseBean();
		try {
			groupBuyServiceImpl.update(groupBuy);
		} catch (Exception e) {
			ErrorUtils.setError(response, e);
		}
		return response;
	}
	
	@RequestMapping(value = "/purchase", produces = "application/json;charset=UTF-8")
	public @ResponseBody ResponseBean purchase(@RequestBody GroupBuyBean groupBuy) throws Exception
	{
		logger.info("GroupBuy purchase "+ groupBuy);
		ResponseBean response = new ResponseBean();
		try {
			groupBuyServiceImpl.purchase(groupBuy);
		} catch (Exception e) {
			ErrorUtils.setError(response, e);
		}
		return response;
	}
	
	@RequestMapping(value = "/deliver", produces = "application/json;charset=UTF-8")
	public @ResponseBody ResponseBean deliver(@RequestBody GroupBuyBean groupBuy) throws Exception
	{
		logger.info("GroupBuy deliver "+ groupBuy);
		ResponseBean response = new ResponseBean();	
		try {			
			groupBuyServiceImpl.deliver(groupBuy);
		} catch (Exception e) {
			ErrorUtils.setError(response, e);
		}
		return response;
	}	
	
	@RequestMapping(value = "/updateDeliverInfo", produces = "application/json;charset=UTF-8")
	public @ResponseBody ResponseBean updateDeliverInfo(@RequestBody GroupBuyBean groupBuy) throws Exception
	{
		logger.info("GroupBuy updateDeliverInfo "+ groupBuy);
		ResponseBean response = new ResponseBean();	
		try {			
			groupBuyServiceImpl.updateDeliverInfo(groupBuy);
		} catch (Exception e) {
			ErrorUtils.setError(response, e);
		}
		return response;
	}	

	@RequestMapping(value = "/notifyDeliver", produces = "application/json;charset=UTF-8")
	public @ResponseBody ResponseBean notifyDeliver(@RequestBody GroupBuyBean groupBuy)
	{
		logger.info("GroupBuy notifyDeliver "+ groupBuy);
		ResponseBean response = new ResponseBean();
		try {
			groupBuyServiceImpl.notifyDeliver(groupBuy);
		} catch (Exception e) {
			ErrorUtils.setError(response, e);
		}
		return response;
	}
	
	@RequestMapping(value = "/terminate", produces = "application/json;charset=UTF-8")
	public @ResponseBody ResponseBean terminate(@RequestBody RequestBean request) throws Exception
	{
		request.setEventId(request.getId());
		request.setEventType(BeanConstant.EVENT_TYPE_GROUPBUY);
		request.setAction(BeanConstant.ACTION_GB_TERMINATE);
		logger.info("GroupBuy terminate "+ request);
		ResponseBean response = new ResponseBean();	
		try {			
			groupBuyServiceImpl.terminate(request);
		} catch (Exception e) {
			ErrorUtils.setError(response, e);
		}
		return response;
	}
	
	@RequestMapping(value = "/terminateParticipation", produces = "application/json;charset=UTF-8")
	public @ResponseBody ResponseBean terminateParticipation(@RequestBody RequestBean request) throws Exception
	{
		request.setEventId(request.getId());
		request.setEventType(BeanConstant.EVENT_TYPE_GROUPBUY);
		request.setAction(BeanConstant.ACTION_GBP_TERMINATE);
		logger.info("GroupBuy terminateParticipation "+ request);
		ResponseBean response = new ResponseBean();	
		try {			
			groupBuyServiceImpl.terminateParticipation(request);
		} catch (Exception e) {
			ErrorUtils.setError(response, e);
		}
		return response;
	}
	
	@RequestMapping(value = "/removeParticipation", produces = "application/json;charset=UTF-8")
	public @ResponseBody ResponseBean removeParticipation(@RequestBody RequestBean request) throws Exception
	{
		request.setEventId(request.getId());
		request.setEventType(BeanConstant.EVENT_TYPE_GROUPBUY);
		request.setAction(BeanConstant.ACTION_GBP_REMOVE);
		logger.info("GroupBuy removeParticipation "+ request);
		ResponseBean response = new ResponseBean();	
		try {			
			groupBuyServiceImpl.removeParticipation(request);
		} catch (Exception e) {
			ErrorUtils.setError(response, e);
		}
		return response;
	}
		
	@RequestMapping(value = "/disuseParticipation", produces = "application/json;charset=UTF-8")
	public @ResponseBody ResponseBean disuseParticipation(@RequestBody RequestBean request) throws Exception
	{
		request.setEventId(request.getId());
		request.setEventType(BeanConstant.EVENT_TYPE_GROUPBUY);
		request.setAction(BeanConstant.ACTION_GBP_DISUSE);
		logger.info("GroupBuy disuseParticipation "+ request);
		ResponseBean response = new ResponseBean();	
		try {			
			groupBuyServiceImpl.disuseParticipation(request);
		} catch (Exception e) {
			ErrorUtils.setError(response, e);
		}
		return response;
	}
	
	@RequestMapping(value = "/complete", produces = "application/json;charset=UTF-8")
	public @ResponseBody ResponseBean complete(@RequestBody GroupBuyBean groupBuy) throws Exception
	{
		logger.info("GroupBuy complete "+ groupBuy);
		ResponseBean response = new ResponseBean();	
		try {			
			groupBuyServiceImpl.complete(groupBuy);
		} catch (Exception e) {
			ErrorUtils.setError(response, e);
		}
		return response;
	}
	
	@RequestMapping("/addItem"  )  
    public @ResponseBody ResponseBean addItem(RequestBean bean,HttpServletRequest request)  {		

		logger.info("GroupBuy addItem "+ bean);
		logger.info("getJsonContent:\n" + bean.getJsonContent());
		
		objectMapper = new ObjectMapper();
		ResponseBean response = new ResponseBean();	
		GroupBuyItemBean item;
		String name;
		String fileName;
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext()); 	
		try {			
			GroupBuyItemBean[] items = objectMapper.readValue(bean.getJsonContent(), GroupBuyItemBean[].class);
	        if(multipartResolver.isMultipart(request)){  
	            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;  
	            Iterator<String> iter = multiRequest.getFileNames();  
	            while(iter.hasNext()){  
	            	name = iter.next();
	            	item = items[Integer.valueOf(name)-1];
	                List<MultipartFile> files = multiRequest.getFiles(name);
	                for(int i=0;i<files.size();i++){
	                	fileName = item.getGbId() + "_"+files.get(i).getName()+"_"+i;
	            		item.addPic(FileUtils.saveFile(fileName, files.get(i),request,FileUtils.TYPE_GROUPBUYITEM));
	                }	                
	            }	              
	        } 
			groupBuyServiceImpl.additems(items);
			
		} catch (Exception e) {
			e.printStackTrace();
			ErrorUtils.setError(response, e);
		}
        return response;  
    }  
	
	

	@RequestMapping(value = "/participate", produces = "application/json;charset=UTF-8")
	public @ResponseBody ResponseBean participate(@RequestBody GroupBuyPurchaseBean groupBuyPurchase)
	{
		logger.info("GroupBuy participate "+ groupBuyPurchase);
		ResponseBean response = new ResponseBean();
		try {
			groupBuyServiceImpl.participateWithValid(groupBuyPurchase);
			response.setBean(groupBuyPurchase);
		} catch (Exception e) {
			ErrorUtils.setError(response, e);
		}
		return response;
	}	
	
	@RequestMapping(value = "/getList", produces = "application/json;charset=UTF-8")
	public @ResponseBody ResponseBean getList(@RequestBody GroupBuySearchBean search)
	{
		logger.info("GroupBuy getList "+ search);
		ResponseBean response = new ResponseBean();
		try {
			List<GroupBuyBean> groupBuys =	groupBuyServiceImpl.getList(search);
			response.setBean(groupBuys);
		} catch (Exception e) {
			ErrorUtils.setError(response, e);
		}
		return response;
	}

//	@RequestMapping(value = "/", produces = "application/json;charset=UTF-8")
//	public @ResponseBody ResponseBean get(@RequestParam(value="invitationCode") String invitationCode)
//	{
//		logger.info("GroupBuy get "+ invitationCode);
//		GroupBuySearchBean search = new GroupBuySearchBean();
//		search.setInvitationCode(invitationCode);
//		return this.get(search);
//	}
	
	@RequestMapping(value = "/get", produces = "application/json;charset=UTF-8")
	public @ResponseBody ResponseBean get(@RequestBody GroupBuySearchBean search)
	{
		logger.info("GroupBuy get "+ search);
		ResponseBean response = new ResponseBean();
		try {
			if(search.getGbId()==0 && StringUtils.isNotEmpty(search.getInvitationCode())){
				String tmp = search.getInvitationCode();
				search.setInvitationCode(tmp.substring(tmp.length()-8));
				search.setGbId(Long.parseLong(tmp.substring(0, tmp.length()-8)));
			}
			GroupBuyBean groupBuy =	groupBuyServiceImpl.getGroupBuy(search);
			response.setBean(groupBuy);
		} catch (Exception e) {
			ErrorUtils.setError(response, e);
		}
		return response;
	}
	
	@RequestMapping(value = "/adjustItem", produces = "application/json;charset=UTF-8")
	public @ResponseBody ResponseBean adjustItem(@RequestBody GroupBuyItemBean[] items)
	{
		logger.info("GroupBuy adjustItem "+ items);
		ResponseBean response = new ResponseBean();
		try {
			groupBuyServiceImpl.adjustItem(items);
		} catch (Exception e) {
			ErrorUtils.setError(response, e);
		}
		return response;
	}
	
	@RequestMapping(value = "/pay", produces = "application/json;charset=UTF-8")
	public @ResponseBody ResponseBean pay(@RequestBody GroupBuyPurchaseBean groupBuyPurchase)
	{
		logger.info("GroupBuy pay "+ groupBuyPurchase);
		ResponseBean response = new ResponseBean();
		try {
			groupBuyServiceImpl.pay(groupBuyPurchase);
		} catch (Exception e) {
			ErrorUtils.setError(response, e);
		}
		return response;
	}	

	@RequestMapping(value = "/receive", produces = "application/json;charset=UTF-8")
	public @ResponseBody ResponseBean receive(@RequestBody GroupBuyPurchaseBean groupBuyPurchase)
	{
		logger.info("GroupBuy receive "+ groupBuyPurchase);
		ResponseBean response = new ResponseBean();
		try {
			groupBuyServiceImpl.receive(groupBuyPurchase);
		} catch (Exception e) {
			ErrorUtils.setError(response, e);
		}
		return response;
	}	

	@RequestMapping(value = "/collect", produces = "application/json;charset=UTF-8")
	public @ResponseBody ResponseBean collect(@RequestBody GroupBuyPurchaseBean groupBuyPurchase)
	{
		logger.info("GroupBuy collect "+ groupBuyPurchase);
		ResponseBean response = new ResponseBean();
		try {
			groupBuyServiceImpl.collect(groupBuyPurchase);
		} catch (Exception e) {
			ErrorUtils.setError(response, e);
		}
		return response;
	}
	
	@RequestMapping(value = "/remindPay", produces = "application/json;charset=UTF-8")
	public @ResponseBody ResponseBean remindPay(@RequestBody GroupBuyPurchaseBean groupBuyPurchase)
	{
		logger.info("GroupBuy remindPay "+ groupBuyPurchase);
		ResponseBean response = new ResponseBean();
		try {
			groupBuyServiceImpl.remindPay(groupBuyPurchase);
		} catch (Exception e) {
			ErrorUtils.setError(response, e);
		}
		return response;
	}
	
	@RequestMapping(value = "/remindReceive", produces = "application/json;charset=UTF-8")
	public @ResponseBody ResponseBean remindReceive(@RequestBody GroupBuyPurchaseBean groupBuyPurchase)
	{
		logger.info("GroupBuy remindReceive "+ groupBuyPurchase);
		ResponseBean response = new ResponseBean();
		try {
			groupBuyServiceImpl.remindReceive(groupBuyPurchase);
		} catch (Exception e) {
			ErrorUtils.setError(response, e);
		}
		return response;
	}
	
}
