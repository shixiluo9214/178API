package com.ccc.admin.controller;

import javax.servlet.http.HttpServletRequest;

import com.ccc.admin.bean.Report;
import com.ccc.admin.service.AdminService;
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
@RequestMapping("/admin")
public class AdminController {
	
	private static final Logger logger = Logger.getLogger(AdminController.class);
	@Autowired
	private AdminService adminServiceImpl;
	@Autowired
	private HttpServletRequest request;

	
	@RequestMapping(value = "/getReport", produces = "application/json;charset=UTF-8")
	public @ResponseBody ResponseBean getReport(@RequestBody Report report) throws Exception
	{
		logger.info("Admin getReport "+ report);
		ResponseBean response = new ResponseBean();
		try {
//			adminServiceImpl.addUser(user);
//			response.setBean(user);
		} catch (Exception e) {
			ErrorUtils.setError(response, e);
		}
		return response;
	}

}
