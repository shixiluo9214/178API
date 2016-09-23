package com.ccc.user.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.stereotype.Repository;

import com.ccc.user.bean.CommunityBean;
import com.ccc.user.bean.UserBean;
import com.ccc.user.dao.CommunityDao;
import com.framework.bean.RequestBean;
import com.framework.dao.BaseDao;
import com.framework.dao.DaoUtils;

@Repository
public class CommunityDaoImpl extends BaseDao implements CommunityDao {
	
	@Override
	public CommunityBean getComms(long commId) {
		String sql = "SELECT id,name,status,country,province,city,district,address,applied_by,pic_link,created_date,modified_date "
				+ " FROM t_community where id = :id ";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", commId);
		CommunityBean comm =  namedJdbcTemplate.queryForObject(sql, paramMap, new BeanPropertyRowMapper<CommunityBean>(CommunityBean.class));
		
		return comm;
	}

	@Override
	public int addComm(CommunityBean comm) {

		String sql = "INSERT INTO t_community(name,status,country,province,city,district,address,applied_by,pic_link,created_date,modified_date)"
		 		+ " VALUES (:name,:status,:country,:province,:city,:district,:address,:appliedBy,:picLink,:createdDate,:modifiedDate)";

		return DaoUtils.insert(namedJdbcTemplate, comm, sql);
	}

	@Override
	public List<CommunityBean> getPendingComms() {

        String sql = "SELECT id,name,status,country,province,city,district,address,applied_by,pic_link,created_date,modified_date"
        		+ " FROM t_community"
        		+ " where status=-1"
        		+ " order by modified_date desc";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        return namedJdbcTemplate.query(sql, paramMap, new BeanPropertyRowMapper<CommunityBean>(CommunityBean.class));
	}

	@Override
	public int rejectComm(RequestBean comm) {
        String sql = " update t_community set status=-2, confirmed_by=:userId, confirmed_date=sysdate() where id=:id";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", Long.valueOf(comm.getId()));
        paramMap.put("userId", Long.valueOf(comm.getUserId()));
        return namedJdbcTemplate.update(sql, paramMap);
	}

	@Override
	public int confirmComm(RequestBean comm) {
        String sql = " update t_community set status=0, confirmed_by=:userId, confirmed_date=sysdate() where id=:id";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", Long.valueOf(comm.getId()));
        paramMap.put("userId", Long.valueOf(comm.getUserId()));
        return namedJdbcTemplate.update(sql, paramMap);
	}
	
	@Override
	public List<CommunityBean> getCommList(CommunityBean comm) {
		String sql = "SELECT id,name,status,country,province,city,district,address,applied_by,pic_link,created_date,modified_date "
				+ " FROM t_community where status=:status and country=:country and province =:province and city=:city ";
		if(StringUtils.isNotEmpty(comm.getDistrict()))
		{
			sql += " and district=:district ";
		}
		if(StringUtils.isNotEmpty(comm.getName()))
		{
			sql += " and name=:name ";
		}
		return namedJdbcTemplate.query(sql, new BeanPropertySqlParameterSource(comm), new BeanPropertyRowMapper<CommunityBean>(CommunityBean.class));
	}

	@Override
	public List<UserBean> getUserByComm(long commId) {
        String sql = "SELECT a.user_id id fROM  t_address a where a.comm_id=:commId";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("commId", Long.valueOf(commId));
        return namedJdbcTemplate.query(sql, paramMap, new BeanPropertyRowMapper<UserBean>(UserBean.class));
	}

}
