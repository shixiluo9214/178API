package com.ccc.comm.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.framework.bean.BaseBean;

@JsonIgnoreProperties({"score"})
public class ValuateBean extends BaseBean{

	private static final long serialVersionUID = 1L;
	
	private long valuator;
	private long valuatee;
	private String eventType;
	private long eventId;
	private String valuateType; //GroupBuyOwner/GroupBuyParticipant/
	private String detail;
	private int scale;	
	private int score;
	
	private String valuatorNickName;
	private String valuateeNickName;
	
	private String valuatorPicLink;
	private String valuateePicLink;

	public String getValuatorPicLink() {
		return valuatorPicLink;
	}

	public void setValuatorPicLink(String valuatorPicLink) {
		this.valuatorPicLink = valuatorPicLink;
	}

	public String getValuateePicLink() {
		return valuateePicLink;
	}

	public void setValuateePicLink(String valuateePicLink) {
		this.valuateePicLink = valuateePicLink;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getValuatorNickName() {
		return valuatorNickName;
	}

	public void setValuatorNickName(String valuatorNickName) {
		this.valuatorNickName = valuatorNickName;
	}

	public String getValuateeNickName() {
		return valuateeNickName;
	}

	public void setValuateeNickName(String valuateeNickName) {
		this.valuateeNickName = valuateeNickName;
	}

	public long getValuator() {
		return valuator;
	}

	public void setValuator(long valuator) {
		this.valuator = valuator;
	}

	public long getValuatee() {
		return valuatee;
	}

	public void setValuatee(long valuatee) {
		this.valuatee = valuatee;
	}

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

	public String getValuateType() {
		return valuateType;
	}

	public void setValuateType(String valuateType) {
		this.valuateType = valuateType;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

}
