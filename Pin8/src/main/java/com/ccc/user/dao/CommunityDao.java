package com.ccc.user.dao;


import java.util.List;

import com.ccc.user.bean.CommunityBean;
import com.ccc.user.bean.UserBean;
import com.framework.bean.RequestBean;

public interface CommunityDao {

	CommunityBean getComms(long commId);

	int addComm(CommunityBean comm);

	List<CommunityBean> getPendingComms();

	int rejectComm(RequestBean comm);

	int confirmComm(RequestBean comm);

	List<CommunityBean> getCommList(CommunityBean comm);

	List<UserBean> getUserByComm(long commId);


}
