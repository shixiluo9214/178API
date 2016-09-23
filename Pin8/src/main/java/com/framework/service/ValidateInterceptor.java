package com.framework.service;


import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ccc.comm.bean.HistoryBean;
import com.ccc.comm.bean.SysLogBean;
import com.ccc.comm.service.CommService;
import com.ccc.groupbuy.service.GroupBuyService;
import com.ccc.user.service.UserService;
import com.framework.bean.BeanConstant;

@Service 
@Aspect  
public class ValidateInterceptor {

	@Autowired
	private UserService userServiceImpl;
	@Autowired
	private CommService commServiceImpl;
	@Autowired
	private GroupBuyService groupBuyServiceImpl;
	
	private static final Logger logger = Logger.getLogger(ValidateInterceptor.class);  
	
	@Pointcut("execution(* com.ccc.*.controller.*Controller.*(..))")  
    private void anyMethod(){}//定义一个切入点  
    
	  
	@Around("anyMethod()")  
    public Object log(ProceedingJoinPoint point) throws Throwable  {  
    	HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
    	ThreadLocal<Long> time=new ThreadLocal<Long>();
    	time.set(System.currentTimeMillis());

    	//Validate token
		//boolean validUser = userServiceImpl.validateToken(token);
    	
    	//System log
    	SysLogBean log = new SysLogBean();
        Object object = null;  
        try {  
            object = point.proceed();  
        } catch (Exception e) {  
        	log.setException(e.toString());
        	log.setStatus(1);
        	logger.error(e);
            throw e;  
        } finally{
        
	        Signature signature = point.getSignature();  
	    	if(!StringUtils.isEmpty(request.getHeader("UserId"))){
	    		log.setUserId(Long.valueOf(request.getHeader("UserId")));
	    	}
			log.setToken(request.getHeader("Token"));
			log.setOperation(signature.getName());
			log.setModule(signature.toShortString().split("Controller")[0]);
			if(point.getArgs().length>0){
				log.setInput(point.getArgs()[0].toString());
			}
			if(object != null)
			{
				log.setOutput(object.toString());
			}
			log.setDuration(System.currentTimeMillis()-time.get());
			log.setIp(getIpAddress(request));
			
			commServiceImpl.saveLog(log);
        }

        //History
    	HistoryBean history = new HistoryBean(log);
		if(point.getArgs().length>0){
	    	history.setInputObject(point.getArgs()[0]);
		}
    	history.setOutputObject(object);
        if(StringUtils.equals(log.getModule(), BeanConstant.MODULE_GROUPBUY)){   
        	groupBuyServiceImpl.saveHistory(history);
        }
        return object;
    }  
	
	public String getIpAddress(HttpServletRequest request)
    {
    	String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
    	 ip = request.getHeader("http_client_ip");  
    	}  
    	if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
    	 ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
    	}  
    	if (ip != null && ip.indexOf(",") != -1) {  
		 ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();  
		} 
        if("0:0:0:0:0:0:0:1".equals(ip))
        {
        	ip="127.0.0.1";
        }
        return ip;
    }
}