package com.ccc.experience.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.ccc.groupbuy.bean.PictureBean;
import com.framework.bean.BaseBean;

public class ExperienceBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String title;
	private String catalog;
	private int status;	
	private String keyWords;
	private long createdBy;
	private long targetGroupdefId;
	private String sourceType;//Create,Forward, Others
	private String scopeType; //CommunityUser,VerifiedUser,RegisteredUser
	private String scopeId; //Community ID if scopeType is CommunityUser
	private String content;
	
	private int replyCount;
	private int reviewCount;

	private List<PictureBean> pics;
	private List<ExperienceReplyBean> replys;	

	private String nickName;
	
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public List<ExperienceReplyBean> getReplys() {
		return replys;
	}

	public void setReplys(List<ExperienceReplyBean> replys) {
		this.replys = replys;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}

	public long getTargetGroupdefId() {
		return targetGroupdefId;
	}

	public void setTargetGroupdefId(long targetGroupdefId) {
		this.targetGroupdefId = targetGroupdefId;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getScopeType() {
		return scopeType;
	}

	public void setScopeType(String scopeType) {
		this.scopeType = scopeType;
	}

	public String getScopeId() {
		return scopeId;
	}

	public void setScopeId(String scopeId) {
		this.scopeId = scopeId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getReplyCount() {
		return replyCount;
	}

	public void setReplyCount(int replyCount) {
		this.replyCount = replyCount;
	}

	public int getReviewCount() {
		return reviewCount;
	}

	public void setReviewCount(int reviewCount) {
		this.reviewCount = reviewCount;
	}

	public List<PictureBean> getPics() {
		return pics;
	}

	public void setPics(List<PictureBean> pics) {
		this.pics = pics;
	}
	
	public void addPic(PictureBean pic) {
		if(this.pics == null)
		{
			this.pics = new ArrayList<PictureBean>();
		}
		this.pics.add(pic);
	}
	public void addPic(String picFile){
		PictureBean pic = new PictureBean();
		pic.setPicLink(picFile);
		this.addPic(pic);
	}
	
	public void addReply(ExperienceReplyBean reply) {
		if(this.replys == null)
		{
			this.replys = new ArrayList<ExperienceReplyBean>();
		}
		this.replys.add(reply);
	}
}
