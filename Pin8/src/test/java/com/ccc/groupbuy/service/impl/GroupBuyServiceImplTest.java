package com.ccc.groupbuy.service.impl;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ccc.groupbuy.bean.GroupBuySearchBean;
import com.ccc.groupbuy.controller.GroupBuyController;
import com.ccc.groupbuy.service.GroupBuyService;
import com.ccc.user.bean.UserBean;
import com.ccc.user.controller.UserController;

public class GroupBuyServiceImplTest {
	private static final Logger logger = Logger.getLogger(GroupBuyServiceImplTest.class);	
	@Test
	 public void inteceptorTest(){  
	        try {
//				ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");  
			//	MyInterceptor interceptor = (MyInterceptor)ctx.getBean("myInterceptor");
//				GroupBuyServiceImpl service = (GroupBuyServiceImpl)ctx.getBean("groupBuyServiceImpl");  
//				GroupBuySearchBean bean = new GroupBuySearchBean();
//				bean.setGbId(1l);
//				service.test("bb");
//				
//				UserController userController = (UserController)ctx.getBean("userController");  
//				UserBean user = new UserBean();
//				userController.login(user);				
//				logger.info("Group Buy Service Get :"+user);
	        	
	        	String message="单主确认收到UserNickName的付款.";
				logger.info("message before replace :"+message);
	        	String newMessage = message.replaceAll("UserNickName", "Stony");
				logger.info("message after replace:"+newMessage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }  
	 
}
