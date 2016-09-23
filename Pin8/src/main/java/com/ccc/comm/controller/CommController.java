package com.ccc.comm.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ccc.comm.bean.ConfigBean;
import com.ccc.comm.bean.HistoryBean;
import com.ccc.comm.bean.MessageBean;
import com.ccc.comm.bean.QuestionBean;
import com.ccc.comm.bean.SurveyDefBean;
import com.ccc.comm.bean.ValuateBean;
import com.ccc.comm.service.CommService;
import com.framework.bean.ResponseBean;
import com.framework.utils.ErrorUtils;

@Controller
@RequestMapping("/comm") 
public class CommController {
	
	private static final Logger logger = Logger.getLogger(CommController.class);	
	@Autowired
	private CommService commServiceImpl;

	@RequestMapping(value = "/getConfig", produces = "application/json;charset=UTF-8")
	public @ResponseBody ResponseBean getConfig(@RequestBody ConfigBean configBean)
	{
		logger.info("Comm get config "+ configBean);
		ResponseBean response = new ResponseBean();
		try {
			response.setBean(commServiceImpl.getConfig(configBean));
		} catch (Exception e) {
			ErrorUtils.setError(response, e);
		}
		return response;
	}
	
	@RequestMapping(value = "/askQuestion", produces = "application/json;charset=UTF-8")
	public @ResponseBody ResponseBean askQuestion(@RequestBody QuestionBean qBean)
	{
		logger.info("Comm askQuestion "+ qBean);
		ResponseBean response = new ResponseBean();
		try {
			response.setBean(commServiceImpl.askQuestion(qBean));
		} catch (Exception e) {
			ErrorUtils.setError(response, e);
		}
		return response;
	}
	
	@RequestMapping(value = "/answerQuestion", produces = "application/json;charset=UTF-8")
	public @ResponseBody ResponseBean answerQuestion(@RequestBody QuestionBean qBean)
	{
		logger.info("Comm answerQuestion "+ qBean);
		ResponseBean response = new ResponseBean();
		try {
			response.setBean(commServiceImpl.answerQuestion(qBean));
		} catch (Exception e) {
			ErrorUtils.setError(response, e);
		}
		return response;
	}
	
	@RequestMapping(value = "/getQuestion", produces = "application/json;charset=UTF-8")
	public @ResponseBody ResponseBean getQuestion(@RequestBody QuestionBean qBean)
	{
		logger.info("Comm getQuestion "+ qBean);
		ResponseBean response = new ResponseBean();
		try {
			response.setBean(commServiceImpl.getQuestion(qBean));
		} catch (Exception e) {
			ErrorUtils.setError(response, e);
		}
		return response;
	}
	

	@RequestMapping(value = "/getSurveyDef", produces = "application/json;charset=UTF-8")
	public @ResponseBody ResponseBean getSurveyDef(@RequestBody SurveyDefBean sdBean)
	{
		logger.info("Comm getSurvey "+ sdBean);
		ResponseBean response = new ResponseBean();
		try {
			response.setBean(commServiceImpl.getSurveyDef(sdBean));
		} catch (Exception e) {
			ErrorUtils.setError(response, e);
		}
		return response;
	}

	@RequestMapping(value = "/getMessages", produces = "application/json;charset=UTF-8")
	public @ResponseBody ResponseBean getMessages(@RequestBody MessageBean message)
	{
		logger.info("Comm getMessages "+ message);
		ResponseBean response = new ResponseBean();
		try {
			response.setBean(commServiceImpl.getMessages(message));
		} catch (Exception e) {
			ErrorUtils.setError(response, e);
		}
		return response;
	}
	
	@RequestMapping(value = "/readMessage", produces = "application/json;charset=UTF-8")
	public @ResponseBody ResponseBean readMessage(@RequestBody MessageBean message)
	{
		logger.info("Comm readMessage "+ message);
		ResponseBean response = new ResponseBean();
		try {
			commServiceImpl.readMessage(message);
		} catch (Exception e) {
			ErrorUtils.setError(response, e);
		}
		return response;
	}
	
	@RequestMapping(value = "/valuate", produces = "application/json;charset=UTF-8")
	public @ResponseBody ResponseBean valuate(@RequestBody ValuateBean valuate)
	{
		logger.info("Comm valuate "+ valuate);
		ResponseBean response = new ResponseBean();
		try {
			commServiceImpl.valuate(valuate);
		} catch (Exception e) {
			ErrorUtils.setError(response, e);
		}
		return response;
	}
	
	@RequestMapping(value = "/getValuations", produces = "application/json;charset=UTF-8")
	public @ResponseBody ResponseBean getValuations(@RequestBody ValuateBean valuate)
	{
		logger.info("Comm getValuations "+ valuate);
		ResponseBean response = new ResponseBean();
		try {
			response.setBean(commServiceImpl.getValuations(valuate));
		} catch (Exception e) {
			ErrorUtils.setError(response, e);
		}
		return response;
	}
	
	@RequestMapping(value = "/sendMessage", produces = "application/json;charset=UTF-8")
	public @ResponseBody ResponseBean sendMessage(@RequestBody MessageBean message)
	{
		logger.info("Comm sendMessage "+ message);
		ResponseBean response = new ResponseBean();
		try {
			commServiceImpl.sendMessage(message);
		} catch (Exception e) {
			ErrorUtils.setError(response, e);
		}
		return response;
	}

	@RequestMapping(value = "/getHistory", produces = "application/json;charset=UTF-8")
	public @ResponseBody ResponseBean getHistory(@RequestBody HistoryBean history)
	{
		logger.info("Comm getHistory "+ history);
		ResponseBean response = new ResponseBean();
		try {
			response.setBean(commServiceImpl.getHistory(history));
		} catch (Exception e) {
			ErrorUtils.setError(response, e);
		}
		return response;
	}
}
