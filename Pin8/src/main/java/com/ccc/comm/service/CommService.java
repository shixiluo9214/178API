package com.ccc.comm.service;

import java.util.List;

import com.ccc.comm.bean.ConfigBean;
import com.ccc.comm.bean.HistoryBean;
import com.ccc.comm.bean.MessageBean;
import com.ccc.comm.bean.QuestionBean;
import com.ccc.comm.bean.SurveyBean;
import com.ccc.comm.bean.SurveyDefBean;
import com.ccc.comm.bean.SysLogBean;
import com.ccc.comm.bean.ValuateBean;

public interface CommService {

	List<ConfigBean> getConfig(ConfigBean configBean);

	List<QuestionBean> answerQuestion(QuestionBean qBean);

	List<QuestionBean> askQuestion(QuestionBean qBean);

	List<QuestionBean> getQuestion(QuestionBean qBean);

	List<SurveyDefBean> getSurveyDef(SurveyDefBean sdBean);

	int saveSurvey(SurveyBean survey);

	int sendMessage(MessageBean message);
	
	List<MessageBean> getMessages(MessageBean message);
	
	int getCountOfQuestion(QuestionBean qBean);

	int getCountOfMessage(MessageBean message);

	int valuate(ValuateBean valuate);

	int readMessage(MessageBean message);

	int saveLog(SysLogBean log);

	int saveHistory(HistoryBean history);

	List<HistoryBean> getHistory(HistoryBean history);

	List<ValuateBean> getValuations(ValuateBean valuate);

	int updateLog(SysLogBean log);	

}
