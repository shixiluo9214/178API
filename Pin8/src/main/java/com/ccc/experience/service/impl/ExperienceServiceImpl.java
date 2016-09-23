package com.ccc.experience.service.impl;
 

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccc.comm.dao.CommDao;
import com.ccc.comm.service.CommService;
import com.ccc.experience.bean.ExperienceBean;
import com.ccc.experience.bean.ExperienceReplyBean;
import com.ccc.experience.dao.ExperienceDao;
import com.ccc.experience.service.ExperienceService;
import com.framework.bean.BeanConstant;
import com.framework.bean.RequestBean;

@Service
public class ExperienceServiceImpl implements ExperienceService {
	@Autowired
	private ExperienceDao experienceDaoImpl;
	@Autowired
	private CommDao commDaoImpl;
	@Autowired
	private CommService commServiceImpl;
	@Override
	public ExperienceBean get(long id) {
		ExperienceBean experience = experienceDaoImpl.get(id);
		experience.setPics(experienceDaoImpl.getPics(id));
		experience.setContent(experienceDaoImpl.getContent(id));
		List<ExperienceReplyBean> replys = experienceDaoImpl.getReplys(id);
		
		for(int i=replys.size()-1;i>-1;i--){
			if(replys.get(i).getParentId()==0){
				experience.addReply(replys.get(i));
			}else{
				for(int j=i-1;j>-1;j--){
					if(replys.get(i).getParentId()==replys.get(j).getId())
					{
						replys.get(j).addReply(replys.get(i));
					}					
				}
			}
		}
		
		experienceDaoImpl.addReviewCount(experience.getId());
		
		return experience;
	}
	
	
	
	@Override
	public int reply(ExperienceReplyBean experienceReply) {

		experienceDaoImpl.addReplyCount(experienceReply.getExperId());
		
		return experienceDaoImpl.reply(experienceReply);
	}
	@Override
	public List<ExperienceBean> getList(RequestBean bean) {
		return experienceDaoImpl.getList(bean);
	}
	@Override
	public int create(ExperienceBean experience) {

		int result = experienceDaoImpl.create(experience);
		int addPic = 0;
		
		experienceDaoImpl.addContent(experience);
		
		//pic
		for(int i=0;i<experience.getPics().size();i++){
			experience.getPics().get(i).setEventId(experience.getId());
			experience.getPics().get(i).setEventType(BeanConstant.EVENT_TYPE_EXPERIENCE);

			if(addPic == 0){
				experience.getPics().get(i).setDefaultFlag(1);
				addPic = 1;
			}
			commDaoImpl.addPic(experience.getPics().get(i));
		}
		
		return result;
	}

}
