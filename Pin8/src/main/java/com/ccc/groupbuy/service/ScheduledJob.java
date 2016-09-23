package com.ccc.groupbuy.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ccc.comm.bean.SysLogBean;
import com.ccc.comm.service.CommService;
import com.ccc.groupbuy.controller.GroupBuyController;
import com.framework.bean.BeanConstant;

@Component
public class ScheduledJob {

	private static final Logger logger = Logger.getLogger(ScheduledJob.class);	

	@Autowired
	private GroupBuyService groupBuyServiceImpl;
	@Autowired
	private CommService commServiceImpl;
	
	@Scheduled(cron="${job.groupbuy}")
	public void groupBuyscheduledJob(){
		logger.info("GroupBuyJob reminderOwner Triggered");
		ThreadLocal<Long> time=new ThreadLocal<Long>();
    	time.set(System.currentTimeMillis());

		SysLogBean log = new SysLogBean();
		log.setUserId(BeanConstant.USER_ROOT_ID);
		log.setIp("localhost");
		log.setModule(BeanConstant.MODULE_GROUPBUY);
		log.setOperation(BeanConstant.ACTION_GB_JOB);
		log.setStatus(0);
		commServiceImpl.saveLog(log);		
		
		groupBuyServiceImpl.scheduledJob(log);
		

		log.setDuration(System.currentTimeMillis()-time.get());
		commServiceImpl.updateLog(log);
		
	}

}
