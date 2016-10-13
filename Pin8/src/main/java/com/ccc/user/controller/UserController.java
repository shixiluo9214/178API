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

import com.ccc.user.bean.AddressBean;
import com.ccc.user.bean.FriendBean;
import com.ccc.user.bean.UserBean;
import com.ccc.user.service.UserService;
import com.framework.bean.RequestBean;
import com.framework.bean.ResponseBean;
import com.framework.utils.ErrorConstant;
import com.framework.utils.ErrorUtils;
import com.framework.utils.FileUtils;

@Controller
@RequestMapping("/user") 
public class UserController {
	
	private static final Logger logger = Logger.getLogger(UserController.class);	
	@Autowired
	private UserService userServiceImpl;
	@Autowired
	private HttpServletRequest request;

	
	@RequestMapping(value = "/add", produces = "application/json;charset=UTF-8")
	public @ResponseBody ResponseBean addUser(@RequestBody UserBean user) throws Exception
	{
		logger.info("User add "+ user);
		ResponseBean response = new ResponseBean();
		try {
			userServiceImpl.addUser(user);
			response.setBean(user);
		} catch (Exception e) {
			ErrorUtils.setError(response, e);
		}
		return response;
	}

	@RequestMapping(value = "/validate", produces = "application/json;charset=UTF-8")
	public @ResponseBody ResponseBean validateUser(@RequestBody UserBean user) throws Exception
	{
		logger.info("User validate "+ user);
		ResponseBean response = new ResponseBean();
		if(userServiceImpl.validateUser(user) > 0){
			ErrorUtils.setError(response, ErrorConstant.USER_DUPLICATED);
		}
		return response;
	}
	
	@RequestMapping(value = "/update", produces = "application/json;charset=UTF-8")
	public @ResponseBody ResponseBean updateUser(@RequestBody UserBean user) throws Exception
	{
		logger.info("User update "+ user);
		ResponseBean response = new ResponseBean();
		try {
			userServiceImpl.updateUser(user);
			//response.setBean(user);
		} catch (Exception e) {
			ErrorUtils.setError(response, e);
		}
		return response;
	}
	
	@RequestMapping(value = "/login", produces = "application/json;charset=UTF-8")
	public @ResponseBody ResponseBean login(@RequestBody UserBean user)
	{
		logger.info("User login "+ user);
		ResponseBean response = new ResponseBean();
		try {
			response.setBean(userServiceImpl.loginUser(user));
		} catch (Exception e) {
			ErrorUtils.setError(response, e);
		}
		return response;
	}	

	@RequestMapping(value = "/getUser", produces = "application/json;charset=UTF-8")
	public @ResponseBody ResponseBean getUser(@RequestBody UserBean user)
	{
		logger.info("User getUser "+ user);
		ResponseBean response = new ResponseBean();
		try {
			response.setBean(userServiceImpl.getUser(user));
		} catch (Exception e) {
			ErrorUtils.setError(response, e);
		}
		return response;
	}

	@RequestMapping(value = "/getFriendList", produces = "application/json;charset=UTF-8")
	public @ResponseBody ResponseBean getFriendList(@RequestBody RequestBean user)
	{
		logger.info("User getFriendList "+user);
		ResponseBean response = new ResponseBean();
		try {
			response.setBean(userServiceImpl.getFriendList(user.getId()));
		} catch (Exception e) {
			ErrorUtils.setError(response, e);
		}
		return response;
	}
	
	@RequestMapping(value = "/deleteFriend", produces = "application/json;charset=UTF-8")
	public @ResponseBody ResponseBean deleteFriend(@RequestBody FriendBean friend)
	{
		logger.info("User deleteFriend "+friend);
		ResponseBean response = new ResponseBean();
		try {
			userServiceImpl.deleteFriend(friend);
		} catch (Exception e) {
			ErrorUtils.setError(response, e);
		}
		return response;
	}

	@RequestMapping(value = "/applyFriend", produces = "application/json;charset=UTF-8")
	public @ResponseBody ResponseBean applyFriend(@RequestBody FriendBean friend)
	{
		logger.info("User applyFriend "+friend);
		ResponseBean response = new ResponseBean();
		try {
			userServiceImpl.applyFriend(friend);
			response.setBean(friend);
		} catch (Exception e) {
			ErrorUtils.setError(response, e);
		}
		return response;
	}
	@RequestMapping(value = "/confirmFriend", produces = "application/json;charset=UTF-8")
	public @ResponseBody ResponseBean confirmFriend(@RequestBody FriendBean friend)
	{
		logger.info("User confirmFriend "+friend);
		ResponseBean response = new ResponseBean();
		try {
			userServiceImpl.confirmFriend(friend);
			response.setBean(friend);
		} catch (Exception e) {
			ErrorUtils.setError(response, e);
		}
		return response;
	}	

	
	@RequestMapping(value = "/addAddress", produces = "application/json;charset=UTF-8")
	public @ResponseBody ResponseBean addAddress(@RequestBody AddressBean address)
	{
		logger.info("User addAddress "+address);
		ResponseBean response = new ResponseBean();
		try {
			userServiceImpl.addAddress(address);
			response.setBean(address);
		} catch (Exception e) {
			ErrorUtils.setError(response, e);
		}
		return response;
	}
	
	@RequestMapping(value = "/deleteAddress", produces = "application/json;charset=UTF-8")
	public @ResponseBody ResponseBean deleteAddress(@RequestBody AddressBean address)
	{
		logger.info("User deleteAddress "+address);
		ResponseBean response = new ResponseBean();
		try {
			address.setStatus(20);
			userServiceImpl.updateAddress(address);
		} catch (Exception e) {
			ErrorUtils.setError(response, e);
		}
		return response;
	}
	
	@RequestMapping(value = "/setDefaultAddress", produces = "application/json;charset=UTF-8")
	public @ResponseBody ResponseBean setDefaultAddress(@RequestBody AddressBean address)
	{
		logger.info("User setDefaultAddress "+address);
		ResponseBean response = new ResponseBean();
		try {
			address.setDefaultFlag(1);
			userServiceImpl.updateAddress(address);
		} catch (Exception e) {
			ErrorUtils.setError(response, e);
		}
		return response;
	}
	
	@RequestMapping(value = "/updateAddress", produces = "application/json;charset=UTF-8")
	public @ResponseBody ResponseBean updateAddress(@RequestBody AddressBean address)
	{
		logger.info("User updateAddress "+address);
		ResponseBean response = new ResponseBean();
		try {
			userServiceImpl.updateAddress(address);
		} catch (Exception e) {
			ErrorUtils.setError(response, e);
		}
		return response;
	}
	
	@RequestMapping(value = "/getAddresses", produces = "application/json;charset=UTF-8")
	public @ResponseBody ResponseBean getAddresses(@RequestBody AddressBean address)
	{
		logger.info("User getAddresses "+address);
		ResponseBean response = new ResponseBean();
		try {
			response.setBean(userServiceImpl.getAddresses(address.getUserId()));
		} catch (Exception e) {
			ErrorUtils.setError(response, e);
		}
		return response;
	}	
		
	@RequestMapping(value = "/authenticateAddress")
	public @ResponseBody ResponseBean authenticateAddress(@RequestParam("file") CommonsMultipartFile addressFile,HttpServletRequest request,
			AddressBean address)
	{		
		logger.info("User authenticateAddress  "+ address);
		String path = null;
		ResponseBean response = new ResponseBean();
		try {
			path = FileUtils.saveFile(Long.toString(address.getUserId()), addressFile,request,FileUtils.TYPE_USERADDRESS);
			address.setPicLink(path);
			address.setStatus(1);
			userServiceImpl.updateAddress(address);
			
			UserBean user = new UserBean();
			user.setId(address.getUserId());
			user.setStatus(1);
			userServiceImpl.updateUser(user);

		} catch (Exception e) {
			e.printStackTrace();
			ErrorUtils.setError(response, e);
		}		
		return response;
	}


	@RequestMapping(value = "/getPendingConfirmUserList", produces = "application/json;charset=UTF-8")
	public @ResponseBody ResponseBean getPendingConfirmUserList()
	{
		logger.info("User getPendingConfirmUserList ");
		ResponseBean response = new ResponseBean();
		try {
			response.setBean(userServiceImpl.getPendingConfirmUserList());
		} catch (Exception e) {
			ErrorUtils.setError(response, e);
		}
		return response;
	}	
	
	@RequestMapping(value = "/confirmAddress", produces = "application/json;charset=UTF-8")
	public @ResponseBody ResponseBean confirmAddress(@RequestBody AddressBean address) throws Exception
	{
		logger.info("User confirmAddress "+ address);
		ResponseBean response = new ResponseBean();
		try {
			userServiceImpl.confirmAddress(address);
		} catch (Exception e) {
			ErrorUtils.setError(response, e);
		}
		return response;
	}
	
	@RequestMapping(value = "/rejectAddress", produces = "application/json;charset=UTF-8")
	public @ResponseBody ResponseBean rejectAddress(@RequestBody AddressBean address) throws Exception
	{
		logger.info("User rejectAddress "+ address);
		ResponseBean response = new ResponseBean();
		try {
			userServiceImpl.rejectAddress(address);
		} catch (Exception e) {
			ErrorUtils.setError(response, e);
		}
		return response;
	}	
	@RequestMapping(value = "/updateSecret", produces = "application/json;charset=UTF-8")
	public @ResponseBody ResponseBean updateSecret(@RequestBody  UserBean user) throws Exception
	{
		logger.info("User updateSecret "+ user);
		ResponseBean response = new ResponseBean();
		try {
			userServiceImpl.updateSecret(user);
		} catch (Exception e) {
			ErrorUtils.setError(response, e);
		}
		return response;
	}
		
	@RequestMapping(value = "/uploadPic")
	public @ResponseBody ResponseBean uploadPic(@RequestParam("file") CommonsMultipartFile picFile,HttpServletRequest request,
			UserBean user)
	{		
		logger.info("User uploadPic  "+ user);
		String path = null;
		ResponseBean response = new ResponseBean();
		try {
			path = FileUtils.saveFile(Long.toString(user.getId()), picFile,request,FileUtils.TYPE_USERPIC);
			user.setPicLink(path);
			userServiceImpl.updateUser(user);
		} catch (Exception e) {
			e.printStackTrace();
			ErrorUtils.setError(response, e);
		}		
		return response;
	}	
	
	@RequestMapping(value = "/getUserByPhones", produces = "application/json;charset=UTF-8")
	public @ResponseBody ResponseBean getUserByPhones(@RequestBody RequestBean request)
	{
		logger.info("User getUserByPhones "+request);
		ResponseBean response = new ResponseBean();
		try {
			response.setBean(userServiceImpl.getUserByPhones(request));
		} catch (Exception e) {
			ErrorUtils.setError(response, e);
		}
		return response;
	}
}
