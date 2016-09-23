package com.ccc.user.service.impl;
 

import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.ccc.comm.bean.MessageBean;
import com.ccc.comm.service.CommService;
import com.ccc.groupbuy.bean.GroupBuyBean;
import com.ccc.groupbuy.service.GroupBuyService;
import com.ccc.user.bean.AddressBean;
import com.ccc.user.bean.FriendBean;
import com.ccc.user.bean.UserBean;
import com.ccc.user.bean.UserSecretBean;
import com.ccc.user.dao.UserDao;
import com.ccc.user.service.CommunityService;
import com.ccc.user.service.UserService;
import com.framework.bean.BeanConstant;
import com.framework.bean.RequestBean;
import com.framework.utils.Cfg;
import com.framework.utils.ErrorConstant;
import com.framework.utils.security.EncryptionUtil;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private CommService commServiceImpl;
	@Autowired
	private GroupBuyService groupBuyServiceImpl;
	@Autowired
	private UserDao userDaoImpl;
	@Autowired
	private CommunityService communityServiceImpl;
	
	public int addUser(UserBean user) throws Exception {
		
		int count = userDaoImpl.validateUser(user);
		if(count > 0)
		{
			throw new Exception(ErrorConstant.USER_DUPLICATED);
		}
		user.setPassword(EncryptionUtil.decryptByRSAwithSalt(user.getPassword()));
		user.setSalt(EncryptionUtil.generateSalt());
		user.setPassword(EncryptionUtil.hashPassword(user.getPassword(), user.getSalt()));
		
		user.setTokenCreatedDate(new Date());
		user.setToken(EncryptionUtil.encryptByAES(""+user.getId()+","+user.getMacAddress()+","+user.getTokenCreatedDate()));

		count = userDaoImpl.insertUser(user);
		
		user.setPassword(null);
		user.setSalt(null);
		
		return count;
	}
	

	@Override
	public int updateUser(UserBean user)  throws Exception  {
		int count;
		if(StringUtils.isNotEmpty(user.getPhone()))
		{
			count = userDaoImpl.validateUser(user);
			if(count > 0)
			{
				throw new Exception(ErrorConstant.USER_DUPLICATED);
			}
		}
		
		if(StringUtils.isNotEmpty(user.getPassword()))
		{
			user.setPassword(EncryptionUtil.decryptByRSAwithSalt(user.getPassword()));
			user.setSalt(EncryptionUtil.generateSalt());
			user.setPassword(EncryptionUtil.hashPassword(user.getPassword(), user.getSalt()));
		}
		
		count = userDaoImpl.updateUser(user);
		
		user.setPassword(null);
		user.setSalt(null);
		
		return count;
	}

	public UserBean getUser(UserBean user) {
		user = userDaoImpl.getUser(user);
		user.setPassword(null);
		user.setSalt(null);
		user.setToken(null);
		user.setOsVersion(null);
		user.setMacAddress(null);
		user.setTokenCreatedDate(null);
		
		user.setHistoryList(groupBuyServiceImpl.getGBSummary(user.getId()));
		
		//if(userDaoImpl.)
		List<UserSecretBean> secrets = userDaoImpl.getSecrets(user.getId());
		for(int i=0;i<secrets.size();i++){
			if(1==secrets.get(i).getHide()){
				if(StringUtils.equals(UserSecretBean.SECRET_TYPE_PHONE, secrets.get(i).getSecretType())){
					user.setPhone(null);
				}else if(StringUtils.equals(UserSecretBean.SECRET_TYPE_REALNAME, secrets.get(i).getSecretType())){
					user.setRealName(null);
				}else if(StringUtils.equals(UserSecretBean.SECRET_TYPE_WECHAT, secrets.get(i).getSecretType())){
					user.setWechatId(null);
				}else if(StringUtils.equals(UserSecretBean.SECRET_TYPE_ADDRESS, secrets.get(i).getSecretType())){
					user.setAddress(null);
				}
			}
		}
		
		return user;
	}

	@Override
	public UserBean loginUser(UserBean user) throws Exception {
		
		try {
			String inputPassword = user.getPassword();
			user = userDaoImpl.getUser(user);
			 
			if(!StringUtils.equalsIgnoreCase(Cfg.getSysProperty("developer.debug","false"),"true")){
				inputPassword = EncryptionUtil.decryptByRSAwithSalt(inputPassword);
				inputPassword = EncryptionUtil.hashPassword(inputPassword, user.getSalt());
				if(!StringUtils.equals(inputPassword, user.getPassword()))
				{
					throw new Exception(ErrorConstant.USER_INVALID);
				}
			}
			
			user.setTokenCreatedDate(new Date());
			user.setToken(EncryptionUtil.encryptByAES(""+user.getId()+","+user.getMacAddress()+","+user.getTokenCreatedDate()));

			user.setPassword(null);
			user.setSalt(null);
			userDaoImpl.saveToken(user);
			
			user.setSecretList(userDaoImpl.getSecrets(user.getId()));
			
		} catch (EmptyResultDataAccessException e) {
			throw new Exception(ErrorConstant.USER_INVALID,e);
		} catch (GeneralSecurityException e) {
			throw new Exception(ErrorConstant.USER_INVALID,e);
		}
		
		return user;
	}

	@Override
	public boolean validateToken(String token) {
		boolean validUser = false;
		try {
			if(!StringUtils.equalsIgnoreCase(Cfg.getSysProperty("developer.debug","false"),"true")){
				String tokenText = EncryptionUtil.decryptByAES(token);
				String[] tokenTexts = tokenText.split(",");
				if(tokenTexts.length==3){
					UserBean user = new UserBean();
					user.setId(Long.valueOf(tokenTexts[0]));
					user = userDaoImpl.getUser(user);
					if(token.equals(user.getToken())){
						validUser = true;
					}
				}
			}else{
				validUser = true;
			}
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
			
		return validUser;
	}

	@Override
	public int validateUser(UserBean user) {
		return userDaoImpl.validateUser(user);
	}


	@Override
	public List<UserBean> getPendingConfirmUserList() {
		return userDaoImpl.getPendingConfirmUserList();
	}


	@Override
	public List<FriendBean> getFriendList(long userId) {
		return userDaoImpl.getFriendList(userId);
	}

	@Override
	public int deleteFriend(FriendBean friend) {
		friend.setStatus(1); //1- delete
		return userDaoImpl.updateFriend(friend);
	}

	@Override
	public int addFriendinGB(long gbId)
	{
		return userDaoImpl.addFriendinGB(gbId);
	}

	@Override
	public int addFriend(FriendBean friend) {
		friend.setStatus(0); //0 - active
		return userDaoImpl.updateFriend(friend);
	}


	@Override
	public List<AddressBean> getAddresses(long userId) {
		return userDaoImpl.getAddresses(userId);
	}


	@Override
	public int addAddress(AddressBean address) {
		if(userDaoImpl.getAddresses(address.getUserId()).size()==0){
			address.setDefaultFlag(1);
		}
		int result =  userDaoImpl.insertAddress(address);
		

		//Auto confirm community if the number of user in this community is more than 5 
		if(address.getCommId()>0 && userDaoImpl.getUserCount4PendingComm(address.getCommId())>=5)
		{
			RequestBean comm = new RequestBean();
			comm.setId(address.getCommId());
			comm.setUserId(BeanConstant.USER_ROOT_ID);
			communityServiceImpl.confirmComm(comm);
		}
		
		return result;
	}

	@Override
	public int updateAddress(AddressBean address) {
		if(address.getDefaultFlag()==1 && address.getStatus()<20){
			userDaoImpl.removeAddressDefaultFlag(address);
		}
		int result = userDaoImpl.updateAddress(address);

		//If the default address is removed, the other address should be set to default.
		if(address.getDefaultFlag()==1 && address.getStatus()==20){
			userDaoImpl.resetDefaultAddress(address.getId());
		}		
		
		return result;
	}
	

	@Override
	public int confirmAddress(AddressBean address) {

		address.setStatus(2);
		int result = userDaoImpl.updateAddress(address);
		
		UserBean user = new UserBean();
		user.setId(address.getUserId());
		user.setStatus(2);
		userDaoImpl.updateUser(user);		

		MessageBean message = new MessageBean();
		message.setReceiver(address.getUserId());
		message.setSender(BeanConstant.USER_ROOT_ID);
		message.setMessageType(BeanConstant.ACTION_USER_CONFIRM_ADDRESS);
		commServiceImpl.sendMessage(message);
		
		return result;
	}

	@Override
	public int rejectAddress(AddressBean address) {

		address.setStatus(0);
		int result = userDaoImpl.updateAddress(address);
		
		UserBean user = new UserBean();
		user.setId(address.getUserId());
		user.setStatus(0);
		userDaoImpl.updateUser(user);

		MessageBean message = new MessageBean();
		message.setReceiver(address.getUserId());
		message.setSender(BeanConstant.USER_ROOT_ID);
		message.setTopic(BeanConstant.ACTION_USER_REJECT_ADDRESS);
		message.setMessage(address.getMessage());
		commServiceImpl.sendMessage(message);
		
		return result;
	}


	@Override
	public int updateSecret(UserBean user) {
		List<UserSecretBean> secrets = user.getSecretList();
		for(int i=0;i<secrets.size();i++)
		{
			secrets.get(i).setUserId(user.getId());
			userDaoImpl.updateSecret(secrets.get(i));
		}
		return 0;
	}

	@Override
	public List<UserBean> getUserByPhones(RequestBean request) {
		return userDaoImpl.getUserByPhones(request);
	}


	@Override
	public void saveGBAddress(GroupBuyBean groupBuy) {
		AddressBean address = new AddressBean();
		address.setUserId(groupBuy.getCreatedBy());
		address.setAddress(groupBuy.getDeliverInfo());
		address.setType(1);//0 user address,1 group buy address
		
		if(!userDaoImpl.checkAddressExisting(address)){
			userDaoImpl.insertAddress(address);
		}		
	}
}
