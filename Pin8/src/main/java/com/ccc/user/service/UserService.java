package com.ccc.user.service;
 
import java.util.List;

import com.ccc.groupbuy.bean.GroupBuyBean;
import com.ccc.user.bean.AddressBean;
import com.ccc.user.bean.FriendBean;
import com.ccc.user.bean.UserBean;
import com.framework.bean.RequestBean;

public interface UserService {
	
	public int addUser(UserBean userInfo) throws Exception;

	public UserBean getUser(UserBean user);

	public UserBean loginUser(UserBean user) throws Exception;

	public int validateUser(UserBean user);

	public boolean validateToken(String token);

	public int updateUser(UserBean user) throws Exception;

	public List<FriendBean> getFriendList(long userId);

	public int deleteFriend(FriendBean friend);

	public int addFriendinGB(long gbId);

	public int confirmFriend(FriendBean friend);

	public List<AddressBean> getAddresses(long userId);

	public int addAddress(AddressBean address);

	public int updateAddress(AddressBean address);

	public List<UserBean> getPendingConfirmUserList();

	public int rejectAddress(AddressBean address);

	public int confirmAddress(AddressBean address);

	public int updateSecret(UserBean user);

	public List<UserBean> getUserByPhones(RequestBean request);

	public void saveGBAddress(GroupBuyBean groupBuy);

	public int applyFriend(FriendBean friend);
}
