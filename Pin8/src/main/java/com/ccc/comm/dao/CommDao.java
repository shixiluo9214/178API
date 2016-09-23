package com.ccc.comm.dao;

import java.util.List;

import com.ccc.comm.bean.ConfigItemBean;
import com.ccc.comm.bean.ConfigBean;
import com.ccc.comm.bean.HistoryBean;
import com.ccc.comm.bean.MessageBean;
import com.ccc.comm.bean.QuestionBean;
import com.ccc.comm.bean.SurveyBean;
import com.ccc.comm.bean.SurveyDefBean;
import com.ccc.comm.bean.SysLogBean;
import com.ccc.comm.bean.ValuateBean;
import com.ccc.groupbuy.bean.PictureBean;

public interface CommDao {

	List<ConfigItemBean> getConfig(ConfigBean configBean);

	int answerQuestion(QuestionBean qBean);

	int askQuestion(QuestionBean qBean);

	List<QuestionBean> getQuestion(QuestionBean qBean);

	List<SurveyDefBean> getSurveyDef(SurveyDefBean sdBean);

	int saveSurvey(SurveyBean survey);

	int saveSurveyDef(SurveyDefBean surveyDefBean);

	int getQuestionTotal(QuestionBean qBean);

	int saveMessage(MessageBean message);

	List<MessageBean> getMessages(MessageBean message);

	int getCountOfMessage(MessageBean message);

	int saveValuate(ValuateBean valuate);

	int readMessage(MessageBean message);

	int saveLog(SysLogBean log);

	int saveHistory(HistoryBean history);

	List<HistoryBean> getHistory(HistoryBean history);

	int addPic(PictureBean pictureBean);

	List<ValuateBean> getValuations(ValuateBean valuate);

	int updateLog(SysLogBean log);

}
