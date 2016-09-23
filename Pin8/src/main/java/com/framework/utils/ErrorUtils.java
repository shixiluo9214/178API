package com.framework.utils;


import com.framework.bean.ResponseBean;
import com.mysql.jdbc.StringUtils;

public class ErrorUtils {
	
	public static void setError(ResponseBean response, String errorMessage){
		String message = Cfg.getErrorMessage(errorMessage);
		
		response.setStatus(1);
		if(StringUtils.isNullOrEmpty(message)){
			response.setErrorMessage(errorMessage);
		}else {
			response.setErrorCode(errorMessage);
			response.setErrorMessage(message);
		}
	}
	
	public static void setError(ResponseBean response, Exception e){
		e.printStackTrace();
		setError(response,e.getMessage());
	}

}
