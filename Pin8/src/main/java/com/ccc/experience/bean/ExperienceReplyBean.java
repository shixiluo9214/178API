package com.ccc.experience.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.framework.bean.BaseBean;

@JsonIgnoreProperties({"modifiedDate"})  
public class ExperienceReplyBean extends BaseBean  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long userId;
	private long experId;
	private long parentId;
	private String content;	
	private List<ExperienceReplyBean> replys;
	
	public long getParentId() {
		return parentId;
	}
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getExperId() {
		return experId;
	}
	public void setExperId(long experId) {
		this.experId = experId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<ExperienceReplyBean> getReplys() {
		return replys;
	}
	public void setReplys(List<ExperienceReplyBean> replys) {
		this.replys = replys;
	}
	public void addReply(ExperienceReplyBean reply) {
		if(this.replys == null)
		{
			this.replys = new ArrayList<ExperienceReplyBean>();
		}
		this.replys.add(reply);
	}
}
