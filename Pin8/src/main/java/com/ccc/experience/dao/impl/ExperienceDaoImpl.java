package com.ccc.experience.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.ccc.experience.bean.ExperienceBean;
import com.ccc.experience.bean.ExperienceReplyBean;
import com.ccc.experience.dao.ExperienceDao;
import com.ccc.groupbuy.bean.PictureBean;
import com.framework.bean.BeanConstant;
import com.framework.bean.RequestBean;
import com.framework.dao.BaseDao;
import com.framework.dao.DaoUtils;

@Repository
public class ExperienceDaoImpl extends BaseDao implements ExperienceDao {	

	private static final Logger logger = Logger.getLogger(ExperienceDaoImpl.class);	
	
	@Override
	public int create(ExperienceBean experience) {
		
		String sql = "INSERT INTO t_experience (title, catalog, status, key_words, target_groupdef_id, source_type, scope_type, scope_id, created_by, reply_count, review_count)"
				+ " VALUES (:title, :catalog, :status, :keyWords, :targetGroupdefId, :sourceType, :scopeType, :scopeId, :createdBy, :replyCount, :reviewCount)";
		return DaoUtils.insert(namedJdbcTemplate, experience, sql);
	}

	@Override
	public ExperienceBean get(long id) {
		String sql = " select id, title, catalog, status, key_words, target_groupdef_id, source_type, scope_type, scope_id, created_by, reply_count, review_count, created_date, modified_date "
				+ " from t_experience "
				+ " where id = :id";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", Long.valueOf(id));
        return namedJdbcTemplate.queryForObject(sql, paramMap, new BeanPropertyRowMapper<ExperienceBean>(ExperienceBean.class));        
	}


	@Override
	public List<PictureBean> getPics(long id) {
		String sql = "SELECT id,event_id,event_type,pic_link"
    			+ " FROM t_event_pic where event_id=:id and event_type=:eventType";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", Long.valueOf(id));
        paramMap.put("eventType", BeanConstant.EVENT_TYPE_EXPERIENCE);
        return namedJdbcTemplate.query(sql, paramMap, new BeanPropertyRowMapper<PictureBean>(PictureBean.class));
	}


	@Override
	public String getContent(long id) {
		String sql = "SELECT content from t_exper_content where exper_id = :id ";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", Long.valueOf(id));
        return namedJdbcTemplate.queryForObject(sql, paramMap, new BeanPropertyRowMapper<ExperienceBean>(ExperienceBean.class)).getContent();
	}


	@Override
	public List<ExperienceReplyBean> getReplys(long id) {
		String sql = "select id, user_id, exper_id, parent_id, content, created_date from t_exper_reply "
				+ " where exper_id=:id and FIND_IN_SET(id, queryChildrenReplyInfo(0)) order by parent_id, created_date desc";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", Long.valueOf(id));
        return namedJdbcTemplate.query(sql, paramMap, new BeanPropertyRowMapper<ExperienceReplyBean>(ExperienceReplyBean.class));
	}


	@Override
	public int addContent(ExperienceBean experience) {
		String sql = "INSERT INTO t_exper_content (exper_id, content) VALUES (:experId, :content);";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("experId", Long.valueOf(experience.getId()));
        paramMap.put("content", experience.getContent());
		return DaoUtils.insertWithoutId(namedJdbcTemplate, paramMap, sql);
	}


	@Override
	public List<ExperienceBean> getList(RequestBean bean) {
		StringBuilder sql = new StringBuilder();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		sql.append("select e.id, e.title, e.catalog, e.status, e.key_words, e.target_groupdef_id, e.source_type, e.scope_type, e.scope_id, e.created_by, "
				+ " e.reply_count, e.review_count, e.created_date, e.modified_date,uo.nick_name"
				+ " from t_experience e, t_user uo"
				+ " where e.created_by=uo.id and e.status = 0 and "
				+ " ( e.scope_type='RegisteredUser' "
				+ " or (e.scope_type='VerifiedUser' and exists ( select 1 from t_user u where u.id = :userId and u.status=2))"
				+ " or (e.scope_type='CommunityUser' and exists ( select 1 from t_community_user cu,t_user u where u.id = :userId and cu.user_id=u.id and cu.comm_id=e.scope_id))"
				+ " ) ");
        paramMap.put("userId", Long.valueOf(bean.getUserId()));   		
		return namedJdbcTemplate.query(sql.toString(), paramMap, new BeanPropertyRowMapper<ExperienceBean>(ExperienceBean.class));
		
	}

	@Override
	public int reply(ExperienceReplyBean experienceReply) {		
		String sql = "INSERT INTO t_exper_reply (user_id, exper_id, parent_id, content) VALUES (:userId, :experId, :parentId, :content)";
		return DaoUtils.insert(namedJdbcTemplate, experienceReply, sql);
	}

	@Override
	public int addReviewCount(long experId) {
		String sql = "update t_experience set review_count=review_count+1 where id=:experId";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("experId", Long.valueOf(experId));
        return namedJdbcTemplate.update(sql, paramMap);
	}

	@Override
	public int addReplyCount(long experId) {
		String sql = "update t_experience set reply_count=reply_count+1 where id=:experId";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("experId", Long.valueOf(experId));
        return namedJdbcTemplate.update(sql, paramMap);
	}

}
