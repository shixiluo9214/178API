package com.ccc.experience.service;

import java.util.List;

import com.ccc.experience.bean.ExperienceBean;
import com.ccc.experience.bean.ExperienceReplyBean;
import com.framework.bean.RequestBean;

public interface ExperienceService {

	ExperienceBean get(long id);

	int reply(ExperienceReplyBean experienceReply);

	List<ExperienceBean> getList(RequestBean bean);

	int create(ExperienceBean experience);
}
