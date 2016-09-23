package com.ccc.user.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ccc.user.bean.CommunityBean;
import com.ccc.user.service.CommunityService;
import com.framework.bean.RequestBean;
import com.framework.bean.ResponseBean;
import com.framework.utils.ErrorUtils;
import com.framework.utils.FileUtils;

@Controller
@RequestMapping("/community") 
public class CommunityController {
	
	private static final Logger logger = Logger.getLogger(CommunityController.class);
	@Autowired
	private CommunityService communityServiceImpl;
	
	@RequestMapping(value = "/getList", produces = "application/json;charset=UTF-8")
	public @ResponseBody ResponseBean getList(@RequestBody CommunityBean comm)
	{
		logger.info("Comm getList "+ comm);
		ResponseBean response = new ResponseBean();
		response.setBean(communityServiceImpl.getCommList(comm));
		return response;
	}
	
	@RequestMapping(value = "/add", produces = "application/json;charset=UTF-8")
	public @ResponseBody ResponseBean addComm(@RequestBody CommunityBean comm)
	{
		logger.info("Comm addComm"+ comm);
		ResponseBean response = new ResponseBean();
		try {
			comm.setStatus(-1); //the status is pending when it is added
			comm = communityServiceImpl.addComm(comm);
			response.setBean(comm);
		} catch (Exception e) {
			ErrorUtils.setError(response, e);
		}		
		return response;
	}
	
//	@RequestMapping(value = "/applyComm")
//	public @ResponseBody ResponseBean applyComm(@RequestParam("commFile") CommonsMultipartFile commFile,HttpServletRequest request,
//			CommunityBean comm)
//	{		
//		logger.info("Comm applyComm  "+ comm);
//		String path = null;
//		ResponseBean response = new ResponseBean();
//		try {
//			
//			path = FileUtils.saveFile(""+comm.getAppliedBy(), commFile,request,FileUtils.TYPE_COMMADDRESS);
//			
//			comm.setPicLink(path);
//			comm.setStatus(-1);
//			communityServiceImpl.addComm(comm);
//		} catch (Exception e) {
//			e.printStackTrace();
//			ErrorUtils.setError(response, e);
//		}		
//		return response;
//	}
	

	@RequestMapping(value = "/getPendingComms", produces = "application/json;charset=UTF-8")
	public @ResponseBody ResponseBean getPendingComms()
	{
		logger.info("Comm getPendingComms ");
		ResponseBean response = new ResponseBean();
		try {
			response.setBean(communityServiceImpl.getPendingComms());
		} catch (Exception e) {
			ErrorUtils.setError(response, e);
		}
		return response;
	}	
	

	@RequestMapping(value = "/confirmComm", produces = "application/json;charset=UTF-8")
	public @ResponseBody ResponseBean confirmComm(@RequestBody RequestBean comm) throws Exception
	{
		logger.info("Comm confirmComm "+ comm);
		ResponseBean response = new ResponseBean();
		try {
			communityServiceImpl.confirmComm(comm);
		} catch (Exception e) {
			ErrorUtils.setError(response, e);
		}
		return response;
	}
	
	@RequestMapping(value = "/rejectComm", produces = "application/json;charset=UTF-8")
	public @ResponseBody ResponseBean rejectComm(@RequestBody RequestBean comm) throws Exception
	{
		logger.info("Comm rejectComm "+ comm);
		ResponseBean response = new ResponseBean();
		try {
			communityServiceImpl.rejectComm(comm);
		} catch (Exception e) {
			ErrorUtils.setError(response, e);
		}
		return response;
	}
	
	
}