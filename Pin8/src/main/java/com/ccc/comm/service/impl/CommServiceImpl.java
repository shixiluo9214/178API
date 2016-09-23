package com.ccc.comm.service.impl;
 

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccc.comm.bean.ConfigBean;
import com.ccc.comm.bean.ConfigItemBean;
import com.ccc.comm.bean.HistoryBean;
import com.ccc.comm.bean.MessageBean;
import com.ccc.comm.bean.QuestionBean;
import com.ccc.comm.bean.SurveyBean;
import com.ccc.comm.bean.SurveyDefBean;
import com.ccc.comm.bean.SysLogBean;
import com.ccc.comm.bean.ValuateBean;
import com.ccc.comm.dao.CommDao;
import com.ccc.comm.service.CommService;
import com.framework.utils.Cfg;

@Service
public class CommServiceImpl implements CommService {
	@Autowired
	private CommDao commDaoImpl;

	@Override
	public List<ConfigBean> getConfig(ConfigBean configBean) {
		List<ConfigItemBean> items = commDaoImpl.getConfig(configBean);
		List<ConfigBean> configs = new ArrayList<ConfigBean>();
		String configName = null;
		ConfigBean config = null;
		for(int i=0;i<items.size();i++){
			if(!StringUtils.equals(items.get(i).getConfigName(), configName)){
				configName = items.get(i).getConfigName();
				
				config = new ConfigBean();
				config.setName(configName);
				config.setConfigType(items.get(i).getConfigType());
				config.setId(items.get(i).getConfigId());	
				configs.add(config);
			}
			config.addItem(items.get(i));
		}
		return configs;
	}

	@Override
	public List<QuestionBean> answerQuestion(QuestionBean qBean) {
		commDaoImpl.answerQuestion(qBean);
		return commDaoImpl.getQuestion(qBean);
	}

	@Override
	public List<QuestionBean> askQuestion(QuestionBean qBean) {
		commDaoImpl.askQuestion(qBean);
		return commDaoImpl.getQuestion(qBean);
	}

	@Override
	public List<QuestionBean> getQuestion(QuestionBean qBean) {
		return commDaoImpl.getQuestion(qBean);
	}

	@Override
	public List<SurveyDefBean> getSurveyDef(SurveyDefBean sdBean) {
		return commDaoImpl.getSurveyDef(sdBean);
	}

	@Override
	public int saveSurvey(SurveyBean survey) {		
		int result = commDaoImpl.saveSurvey(survey);
		for(int i=0;i<survey.getSurveys().size();i++){
			survey.getSurveys().get(i).setSurveyId(survey.getId());
			commDaoImpl.saveSurveyDef(survey.getSurveys().get(i));
		}
		
		return result;
	}

	@Override
	public int getCountOfQuestion(QuestionBean qBean) {
		return commDaoImpl.getQuestionTotal(qBean);
	}

	@Override
	public int sendMessage(MessageBean message) {
		int result = 0;
		if(StringUtils.isEmpty(message.getMessage())){
			message.setMessage(Cfg.getMessage(message.getMessageType()));
		}
		if(StringUtils.isEmpty(message.getTopic())){
			message.setTopic(Cfg.getMessageTopic(message.getMessageType()));
		}
			
		if(StringUtils.isNotEmpty(message.getReceivers()))
		{
			String[] receives = message.getReceivers().split(",");
			for(int i=0;i<receives.length;i++)
			{
				message.setReceiver(Integer.parseInt(receives[i]));
				result += commDaoImpl.saveMessage(message);
			}
		}else{
			result = commDaoImpl.saveMessage(message);
		}
		return result;
	}

	@Override
	public List<MessageBean> getMessages(MessageBean message) {
		return commDaoImpl.getMessages(message);
	}

	@Override
	public int getCountOfMessage(MessageBean message) {
		return commDaoImpl.getCountOfMessage(message);
	}

	@Override
	public int valuate(ValuateBean valuate) {	
		if(valuate.getScale()<=1){
			valuate.setScore(-10);			
		}else if(valuate.getScale()>=3){
			valuate.setScore(10);
		}			
		return commDaoImpl.saveValuate(valuate);
	}
	
	@Override
	public List<ValuateBean> getValuations(ValuateBean valuate) {
		return commDaoImpl.getValuations(valuate);
	}

	@Override
	public int readMessage(MessageBean message) {
		return commDaoImpl.readMessage(message);
	}

	@Override
	public int saveLog(SysLogBean log) {
		return commDaoImpl.saveLog(log);
	}
	
	@Override
	public int updateLog(SysLogBean log) {
		return commDaoImpl.updateLog(log);
	}

	@Override
	public int saveHistory(HistoryBean history) {
		return commDaoImpl.saveHistory(history);
	}

	@Override
	public List<HistoryBean> getHistory(HistoryBean history) {
		return commDaoImpl.getHistory(history);
	}


}
