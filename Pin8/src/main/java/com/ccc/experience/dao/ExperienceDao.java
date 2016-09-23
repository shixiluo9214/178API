package com.ccc.experience.dao;

import java.util.List;

import com.ccc.comm.bean.HistoryBean;
import com.ccc.experience.bean.ExperienceBean;
import com.ccc.experience.bean.ExperienceReplyBean;
import com.ccc.groupbuy.bean.PictureBean;
import com.framework.bean.RequestBean;

public interface ExperienceDao {

	ExperienceBean get(long id);

	List<PictureBean> getPics(long id);

	String getContent(long id);

	List<ExperienceReplyBean> getReplys(long id);

	int create(ExperienceBean experience);

	int addContent(ExperienceBean experience);

	List<ExperienceBean> getList(RequestBean bean);

	int reply(ExperienceReplyBean experienceReply);

	int addReviewCount(long experId);

	int addReplyCount(long experId);
}
