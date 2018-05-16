package com.ccc.admin.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ccc.admin.dao.AdminDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.stereotype.Repository;

import com.ccc.user.bean.AddressBean;
import com.ccc.user.bean.FriendBean;
import com.ccc.user.bean.UserBean;
import com.ccc.user.bean.UserSecretBean;
import com.ccc.user.dao.UserDao;
import com.framework.bean.RequestBean;
import com.framework.dao.BaseDao;
import com.framework.dao.DaoUtils;

@Repository
public class AdminDaoImpl extends BaseDao implements AdminDao {
		
	public int insertUser(UserBean user) {
		String sql = "INSERT INTO t_user(password,salt,locked,phone,nick_name,token,mac_address,os_version,token_created_date)"
        		+ " VALUES(:password,:salt,:locked,:phone,:nickName,:token,:macAddress,:osVersion,:tokenCreatedDate)";
		return DaoUtils.insert(namedJdbcTemplate, user, sql);		
		
	}	

	//validate whether phone is duplicated
	@Override
	public int validateUser(UserBean user) {
		if(StringUtils.isEmpty(user.getPhone()))
		{
			return 0;
		}
        String sql = "SELECT count(1) FROM t_user where phone =:phone  and id != :userId ";
        Map<String, Object> paramMap = new HashMap<String, Object>();        
    	paramMap.put("phone", user.getPhone());  
    	paramMap.put("userId", Long.valueOf(user.getId())); 
    	
        return  namedJdbcTemplate.queryForInt(sql, paramMap);
	}

	@Override
	public int updateUser(UserBean user) {
		String sql = "update t_user set ";
		String sql1 = new String();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        if(StringUtils.isNotEmpty(user.getWechatId())){
        	sql1 += " ,wechat_id = :wechatId";
            paramMap.put("wechatId", user.getWechatId());
        }
        if(StringUtils.isNotEmpty(user.getPhone()))
        {
        	sql1 += " ,phone = :phone";
            paramMap.put("phone", user.getPhone());
        }
        if(StringUtils.isNotEmpty(user.getRealName()))
        {
        	sql1 += " ,real_name = :realName";
            paramMap.put("realName", user.getRealName());
        }
        if(StringUtils.isNotEmpty(user.getNickName()))
        {
        	sql1 += " ,nick_name = :nickName";
            paramMap.put("nickName", user.getNickName());
        }
        if(StringUtils.isNotEmpty(user.getPicLink()))
        {
        	sql1 += " ,pic_link = :picLink";
            paramMap.put("picLink", user.getPicLink());
        }
        if(StringUtils.isNotEmpty(user.getPassword()))
        {
        	sql1 += " ,password = :password, salt = :salt ";
            paramMap.put("password",user.getPassword());
            paramMap.put("salt",user.getSalt());
        }
        if(user.getStatus()>-1)
        {
        	sql1 += " ,status = :status";
            paramMap.put("status", Integer.valueOf(user.getStatus()));
        }
        
        sql += sql1.substring(2); //remove first " ,"        
        sql += " where id=:id ";
        paramMap.put("id", Long.valueOf(user.getId()));
        
        return namedJdbcTemplate.update(sql, paramMap);
	}
	
	@Override
	public int saveToken(UserBean user) {
		String sql = "update t_user set token=:token,token_created_date=:tokenCreatedDate where id=:id";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", Long.valueOf(user.getId()));
        paramMap.put("token",user.getToken());
        paramMap.put("tokenCreatedDate", user.getTokenCreatedDate());
        return namedJdbcTemplate.update(sql, paramMap);
	}

	@Override
	public UserBean getUser(UserBean user) {

        String sql = "SELECT u.id,u.password,u.salt,u.status,u.locked,u.phone,u.wechat_id,u.pic_link,u.real_name,u.nick_name,u.token,u.mac_address,u.score,u.credit,u.os_version,u.token_created_date,u.created_date,u.modified_date,IFNULL(a.comm_id,0) comm_id,a.address,c.name comm_name"
        		+ " FROM t_user u left join t_address a on u.id=a.user_id and a.default_flag=1 and a.status<20 left join t_community c on a.comm_id =c.id where ";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        if(user.getId()>0){
        	sql += " u.id = :id";
        	paramMap.put("id", Long.valueOf(user.getId()));
        }else if(StringUtils.isNotEmpty(user.getPhone())){
        	sql += " u.phone =:phone";
        	paramMap.put("phone", user.getPhone());        	
        }              
		user = namedJdbcTemplate.queryForObject(sql, paramMap, new BeanPropertyRowMapper<UserBean>(UserBean.class));
        return user;
	}
	
	@Override
	public List<UserBean> getPendingConfirmUserList() {

        String sql = "SELECT u.id,u.status,u.locked,u.phone,u.wechat_id,u.real_name,u.nick_name,u.created_date,u.modified_date,"
        		+ " a.id address_id,c.id comm_id,c.name comm_name, a.address, a.pic_link "
        		+ " FROM t_user u,t_community c, t_address a"
        		+ " where u.id=a.user_id and a.comm_id=c.id and u.status=1 and a.status=1"
        		+ " order by a.modified_date desc";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        return namedJdbcTemplate.query(sql, paramMap, new BeanPropertyRowMapper<UserBean>(UserBean.class));
	}

	@Override
	public int updateFriend(FriendBean friend) {
        String sql = " INSERT INTO t_friend (user_id, friend_id, status) VALUES (:userId, :friendId, :status) "
        		+ " ON DUPLICATE KEY UPDATE status=:status ";
        return DaoUtils.insert(namedJdbcTemplate, friend, sql);
	}

	@Override
	public int updateFriendApply(FriendBean friend) {
		String sql = " update t_friend_apply set status=:status, accepted_date=sysdate() where id=:id";
		return namedJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(friend));
	}

	@Override
	public int insertFriendApply(FriendBean friend) {
		String sql = " INSERT INTO t_friend_apply (user_id, friend_id, status, applied_date) VALUES (:userId, :friendId, :status, sysdate()) ";
		return DaoUtils.insert(namedJdbcTemplate, friend, sql);
	}


	@Override
	public int disableFriendApplys(FriendBean friend) {
		String sql = "update t_friend_apply set status=20 where status<= 20 and id != :id and ((user_id =:userId and friend_id=:friendId) or (user_id =:friendId and friend_id=:userId))";
		return namedJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(friend));
	}

	@Override
	public List<FriendBean> getFriendList(long userId) {
        String sql = "select f.id,f.user_id, f.friend_id,10 status, u.nick_name, f.created_date,u.pic_link"
        		+ " from t_friend f, t_user u"
        		+ " where f.user_id=:id and f.friend_id=u.id and f.status < 20";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", Long.valueOf(userId));
        return namedJdbcTemplate.query(sql, paramMap, new BeanPropertyRowMapper<FriendBean>(FriendBean.class));
	}

	@Override
	public List<FriendBean> getFriendApplyList(long userId) {
		String sql = "select f.id,f.user_id, f.friend_id,f.status, u.nick_name, f.created_date,u.pic_link" +
				" from t_friend_apply f, t_user u" +
				" where f.user_id=:id and f.friend_id=u.id and f.status < 20" +
				" union" +
				" select f.id,f.user_id, f.friend_id,f.status, u.nick_name, f.created_date,u.pic_link" +
				" from t_friend_apply f, t_user u" +
				" where f.friend_id=:id and f.user_id=u.id and f.status < 20";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", Long.valueOf(userId));
		return namedJdbcTemplate.query(sql, paramMap, new BeanPropertyRowMapper<FriendBean>(FriendBean.class));
	}


	@Override
	public int addFriendinGB(long gbId) {
        String sql = " INSERT INTO t_friend(user_id, friend_id)"
        		+ " select gb.created_by user_id, gbp.user_id friend_id from t_groupbuy gb, t_gb_purchase gbp "
        		+ " where gb.id=:id and gb.id=gbp.gb_id and gbp.status<20 and gb.created_by<>gbp.user_id "
        		+ " and not exists ( select 1 from t_friend where user_id= gb.created_by and friend_id=gbp.user_id)"
        		+ " union "
        		+ " select gbp.user_id user_id, gb.created_by friend_id from t_groupbuy gb, t_gb_purchase gbp"
        		+ " where gb.id=:id and gb.id=gbp.gb_id and gbp.status<20 and gb.created_by<>gbp.user_id "
        		+ " and not exists ( select 1 from t_friend where user_id= gbp.user_id and friend_id=gb.created_by)";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", Long.valueOf(gbId));
        return namedJdbcTemplate.update(sql, paramMap);
	}

	@Override
	public List<AddressBean> getAddresses(long userId) {
        String sql = "SELECT a.id,a.user_id,a.comm_id,a.address,a.pic_link,a.status,a.default_flag,a.created_date,a.modified_date,a.type,c.name commName,c.country,c.province,c.city,c.district"
        		+ " FROM t_address a left join t_community c on a.comm_id=c.id where a.status<20  and a.user_id=:userId";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userId", Long.valueOf(userId));
        return namedJdbcTemplate.query(sql, paramMap, new BeanPropertyRowMapper<AddressBean>(AddressBean.class));
	}

	
	@Override
	public int insertAddress(AddressBean address) {
		
        String sql = " INSERT INTO t_address (user_id,comm_id,address,default_flag,type) VALUES  (:userId,:commId,:address,:defaultFlag,:type)"
				+ "	 ON DUPLICATE KEY UPDATE address =:address, default_flag=:defaultFlag and type=:type ";
        return DaoUtils.insert(namedJdbcTemplate, address, sql);
		
	}
	
	@Override
	public int updateAddress(AddressBean address) {
		String sql = "update t_address set ";
		String sql1 = new String();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        if(address.getCommId()>0){
        	sql1 += " ,comm_id = :commId";
            paramMap.put("commId", address.getCommId());
        }
        if(StringUtils.isNotEmpty(address.getAddress()))
        {
        	sql1 += " ,address = :address";
            paramMap.put("address", address.getAddress());
        }
        if(address.getStatus()>-1)
        {
        	sql1 += " ,status = :status";
            paramMap.put("status", Integer.valueOf(address.getStatus()));
        }
        if(StringUtils.isNotEmpty(address.getPicLink()))
        {
        	sql1 += " ,pic_link = :picLink";
            paramMap.put("picLink", address.getPicLink());
        }
        if(address.getDefaultFlag()>0)
        {
        	sql1 += " ,default_flag = :defaultFlag";
            paramMap.put("defaultFlag",Integer.valueOf(address.getDefaultFlag()));
        }
        
        sql += sql1.substring(2); //remove first " ,"        
        sql += " where id=:id ";
        paramMap.put("id", Long.valueOf(address.getId()));
        
        return namedJdbcTemplate.update(sql, paramMap);
	}

	@Override
	public int removeAddressDefaultFlag(AddressBean address) {
        String sql = " update `t_address` a inner join `t_address` b on a.`user_id` = b.`user_id`"
        		+ " set a.`default_flag` = 0 where a.default_flag=1 and b.`id` = :id";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", Long.valueOf(address.getId()));
        return namedJdbcTemplate.update(sql, paramMap);
	}
	
	@Override
	public long resetDefaultAddress(long id) {
        String sql = " update t_address set default_flag= 1 where id = "
        		+ " (select id from"
        		+ " (select b.id"
        		+ " from t_address a, t_address b"
        		+ " where a.id=:id and a.user_id=b.user_id and b.status<20 and a.id<>b.id"
        		+ " order by b.modified_date desc limit 0,1) x )";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", Long.valueOf(id));
        return namedJdbcTemplate.update(sql, paramMap);
	}

	@Override
	public List<UserSecretBean> getSecrets(long userId) {
        String sql = "select id, user_id, secret_type, hide, created_date from t_user_secret where user_id=:userId";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userId", Long.valueOf(userId));
        return namedJdbcTemplate.query(sql, paramMap, new BeanPropertyRowMapper<UserSecretBean>(UserSecretBean.class));
	}

	@Override
	public int updateSecret(UserSecretBean secret) {
        String sql = " INSERT INTO t_user_secret (user_id, secret_type, hide) VALUES (:userId, :secretType, :hide) "
        		+ " ON DUPLICATE KEY UPDATE hide=:hide ";
        return DaoUtils.insert(namedJdbcTemplate, secret, sql);
	}

	@Override
	public List<UserBean> getUserByPhones(RequestBean request) {
		List<UserBean> users = new ArrayList<UserBean>();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userId", Long.valueOf(request.getUserId()));
        String sql = "select u.id,u.nick_name,u.phone,u.status,IFNULL(f.id,0) friend_id"
        		+ " from t_user u left join t_friend f on u.id=f.friend_id and f.user_id= :userId and f.status in (0,1)"
        		+ " where u.phone in (";
        int i=0;
        StringBuffer sqls;
        while(i<request.getPhones().length){
        	sqls = new StringBuffer();
        	sqls.append(sql);
        	int j;
        	for(j=0;i+j<request.getPhones().length && j<50;j++){
        		if(j>0){
        			sqls.append(",");
        		}
        		sqls.append("'");
        		sqls.append(request.getPhones()[i+j]);
        		sqls.append("'");
        	}
    		sqls.append(")");
            users.addAll(namedJdbcTemplate.query(sqls.toString(), paramMap, new BeanPropertyRowMapper<UserBean>(UserBean.class)));
            
            i+=j;
        }
        return users;
	}

	@Override
	public int getUserCount4PendingComm(long commId) {
	        String sql = " select count(distinct a.user_id) from t_community c, t_address a where c.status=-1 and c.id=a.comm_id and c.id=:commId";
	        Map<String, Object> paramMap = new HashMap<String, Object>();
	        paramMap.put("commId", Long.valueOf(commId));
	        return namedJdbcTemplate.queryForInt(sql, paramMap);
	}

	@Override
	public boolean checkAddressExisting(AddressBean address) {
		boolean result = false; 
        String sql = " select count(1)"
        		+ " from t_address a left join t_community c on a.comm_id=c.id"
        		+ " where a.user_id=:userId and (CONCAT(c.name,' ',a.address)=:address or a.address=:address)";
        Map<String, Object> paramMap = new HashMap<String, Object>();        
    	paramMap.put("address", address.getAddress());  
    	paramMap.put("userId", Long.valueOf(address.getUserId())); 
    	
        if(namedJdbcTemplate.queryForInt(sql, paramMap)>0){
        	result = true;
        }
        return result;
	}

}
