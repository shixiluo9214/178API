package com.ccc.user.service;

import java.util.List;

import com.ccc.user.bean.CommunityBean;
import com.framework.bean.RequestBean;

public interface CommunityService {

	public CommunityBean getComm(long commId);

	public CommunityBean addComm(CommunityBean comm) throws Exception;

	public List<CommunityBean> getPendingComms();

	public int confirmComm(RequestBean comm);

	public int rejectComm(RequestBean comm);

	public List<CommunityBean> getCommList(CommunityBean comm);


}
