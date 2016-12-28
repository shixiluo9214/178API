package com.ccc.user.dao;


import java.util.Collection;
import java.util.List;

import com.ccc.user.bean.AddressBean;
import com.ccc.user.bean.FriendBean;
import com.ccc.user.bean.UserBean;
import com.ccc.user.bean.UserSecretBean;
import com.framework.bean.RequestBean;

public interface UserDao {

	int insertUser(UserBean user);

	int validateUser(UserBean user);

	UserBean getUser(UserBean user);

	int saveToken(UserBean user);

	List<UserBean> getPendingConfirmUserList();
	
	int updateUser(UserBean user);

	List<FriendBean> getFriendList(long userId);

	int updateFriend(FriendBean friend);

	int addFriendinGB(long gbId);

	List<AddressBean> getAddresses(long userId);

	int insertAddress(AddressBean address);

	int updateAddress(AddressBean address);

	int removeAddressDefaultFlag(AddressBean address);

	List<UserSecretBean> getSecrets(long userId);

	int updateSecret(UserSecretBean secret);

	List<UserBean> getUserByPhones(RequestBean request);

	int getUserCount4PendingComm(long commId);

	long resetDefaultAddress(long id);

	boolean checkAddressExisting(AddressBean address);

	int updateFriendApply(FriendBean friend);

	int insertFriendApply(FriendBean friend);

	List<FriendBean> getFriendApplyList(long userId);
}
