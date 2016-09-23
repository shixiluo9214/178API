package com.ccc.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccc.comm.bean.MessageBean;
import com.ccc.comm.service.CommService;
import com.ccc.user.bean.CommunityBean;
import com.ccc.user.bean.UserBean;
import com.ccc.user.dao.CommunityDao;
import com.ccc.user.service.CommunityService;
import com.framework.bean.BeanConstant;
import com.framework.bean.RequestBean;
import com.framework.utils.ErrorConstant;

@Service
public class CommunityServiceImpl implements  CommunityService {
	@Autowired
	private CommService commServiceImpl;

	@Autowired
	private CommunityDao CommunityDaoImpl;
	
	public CommunityBean getComm(long commId) {		
		return CommunityDaoImpl.getComms(commId);
	}

	public CommunityBean addComm(CommunityBean comm) throws Exception {
		List<CommunityBean> commList = CommunityDaoImpl.getCommList(comm);
		if(commList.size()>1){
			throw new Exception(ErrorConstant.COMM_DUPLICATED);
		}else if(commList.size()==1){
			return commList.get(0);
		}else{
			CommunityDaoImpl.addComm(comm);
			return comm;
		}
	}

	@Override
	public List<CommunityBean> getPendingComms() {
		return CommunityDaoImpl.getPendingComms();
	}

	@Override
	public int confirmComm(RequestBean comm) {
		int result = CommunityDaoImpl.confirmComm(comm);

		CommunityBean commBean = CommunityDaoImpl.getComms(comm.getId());
		MessageBean message = new MessageBean();
		message.setReceiver(commBean.getAppliedBy());
		message.setEventId(comm.getId());
		message.setEventType(BeanConstant.EVENT_TYPE_COMM);
		message.setSender(comm.getUserId());
		message.setMessageType(BeanConstant.ACTION_COMM_CONFIRM);
		commServiceImpl.sendMessage(message);
		
		return result;
	}	

	@Override
	public int rejectComm(RequestBean comm) {
		int result = CommunityDaoImpl.rejectComm(comm);

		List<UserBean> users=CommunityDaoImpl.getUserByComm(comm.getId());
		MessageBean message = null;
		for(int i=0;i<users.size();i++){
			message = new MessageBean();
			message.setReceiver(users.get(i).getId());
			message.setSender(comm.getUserId());
			message.setEventId(comm.getId());
			message.setEventType(BeanConstant.EVENT_TYPE_COMM);
			message.setMessage(comm.getMessage());
			message.setMessageType(BeanConstant.ACTION_COMM_REJECT);
			commServiceImpl.sendMessage(message);
		}
		return result;
	}

	@Override
	public List<CommunityBean> getCommList(CommunityBean comm) {
		return CommunityDaoImpl.getCommList(comm);
	}

}
