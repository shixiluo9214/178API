package com.ccc.comm.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.framework.bean.BaseBean;

@JsonIgnoreProperties({})  
public class QuestionBean extends BaseBean{

	private static final long serialVersionUID = 1L;
	
	private String eventType;
	private long eventId;
	private long askerId;
	private long answererId;
	private String question;
	private String answer;
	private String askerNickName;
	private String answererNickName;
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public long getEventId() {
		return eventId;
	}
	public void setEventId(long eventId) {
		this.eventId = eventId;
	}
	public long getAskerId() {
		return askerId;
	}
	public void setAskerId(long askerId) {
		this.askerId = askerId;
	}
	public long getAnswererId() {
		return answererId;
	}
	public void setAnswererId(long answererId) {
		this.answererId = answererId;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getAskerNickName() {
		return askerNickName;
	}
	public void setAskerNickName(String askerNickName) {
		this.askerNickName = askerNickName;
	}
	public String getAnswererNickName() {
		return answererNickName;
	}
	public void setAnswererNickName(String answererNickName) {
		this.answererNickName = answererNickName;
	}
		
}
