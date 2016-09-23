package com.ccc.experience.controller;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.ccc.experience.bean.ExperienceBean;
import com.ccc.experience.bean.ExperienceReplyBean;
import com.ccc.experience.service.ExperienceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.framework.bean.RequestBean;
import com.framework.bean.ResponseBean;
import com.framework.utils.ErrorUtils;
import com.framework.utils.FileUtils;

@Controller
@RequestMapping("/experience") 
public class ExperienceController {
	
	private static final Logger logger = Logger.getLogger(ExperienceController.class);	
	@Autowired
	private ExperienceService experienceServiceImpl;
	private ObjectMapper objectMapper = null; 

	@RequestMapping(value = "/create")
	public @ResponseBody ResponseBean create(RequestBean bean,HttpServletRequest request)  {
		
		logger.info("Experience Create "+ bean);
		
		objectMapper = new ObjectMapper();
		ResponseBean response = new ResponseBean();	
		ExperienceBean experience;
		String name;
		String fileName;
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext()); 	
		try {			
			experience = objectMapper.readValue(bean.getJsonContent(), ExperienceBean.class);
	        if(multipartResolver.isMultipart(request)){  
	            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;                  
	            Iterator<String> iter = multiRequest.getFileNames();  
	            while(iter.hasNext()){  
	            	name = iter.next();
	                List<MultipartFile> files = multiRequest.getFiles(name);
	                for(int i=0;i<files.size();i++){
	                	fileName = files.get(i).getName()+"_"+i;
	                	experience.addPic(FileUtils.saveFile(fileName, files.get(i),request,FileUtils.TYPE_EXPERIENCE));
	                }	                
	            }	              
	        } 
	        experienceServiceImpl.create(experience);
	        response.setBean(experience);
			
		} catch (Exception e) {
			e.printStackTrace();
			ErrorUtils.setError(response, e);
		}
        return response; 
	}
	
	@RequestMapping(value = "/getList", produces = "application/json;charset=UTF-8")
	public @ResponseBody ResponseBean getList(@RequestBody RequestBean bean)
	{
		logger.info("Experience getList "+ bean);
		ResponseBean response = new ResponseBean();
		try {
			List<ExperienceBean> experiences =	experienceServiceImpl.getList(bean);
			response.setBean(experiences);
		} catch (Exception e) {
			ErrorUtils.setError(response, e);
		}
		return response;
	}
	
	@RequestMapping(value = "/reply", produces = "application/json;charset=UTF-8")
	public @ResponseBody ResponseBean reply(@RequestBody ExperienceReplyBean experienceReply)
	{
		logger.info("Experience reply "+ experienceReply);
		ResponseBean response = new ResponseBean();
		try {
			experienceServiceImpl.reply(experienceReply);
		} catch (Exception e) {
			ErrorUtils.setError(response, e);
		}
		return response;
	}	
	

	
	@RequestMapping(value = "/get", produces = "application/json;charset=UTF-8")
	public @ResponseBody ResponseBean get(@RequestBody RequestBean bean)
	{
		logger.info("Experience get "+ bean);
		ResponseBean response = new ResponseBean();
		try {
			ExperienceBean experience = experienceServiceImpl.get(bean.getId());
			response.setBean(experience);
		} catch (Exception e) {
			ErrorUtils.setError(response, e);
		}
		return response;
	}	
	
}
