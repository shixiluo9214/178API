package com.ccc.comm.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.stereotype.Repository;

import com.ccc.comm.bean.ConfigItemBean;
import com.ccc.comm.bean.ConfigBean;
import com.ccc.comm.bean.HistoryBean;
import com.ccc.comm.bean.MessageBean;
import com.ccc.comm.bean.QuestionBean;
import com.ccc.comm.bean.SurveyBean;
import com.ccc.comm.bean.SurveyDefBean;
import com.ccc.comm.bean.SysLogBean;
import com.ccc.comm.bean.ValuateBean;
import com.ccc.comm.controller.CommController;
import com.ccc.comm.dao.CommDao;
import com.ccc.groupbuy.bean.PictureBean;
import com.framework.bean.BeanConstant;
import com.framework.dao.BaseDao;
import com.framework.dao.DaoUtils;

@Repository
public class CommDaoImpl extends BaseDao implements CommDao {	

	private static final Logger logger = Logger.getLogger(CommController.class);	

	public List<ConfigItemBean> getConfig(ConfigBean config) {
		StringBuilder sql = new StringBuilder();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		sql.append("select c.id config_id,c.name config_name,c.config_type,ci.name "
				+ " from t_config c,t_config_item ci where c.id=ci.config_id ");
		
		if(StringUtils.isNotEmpty(config.getConfigType())){
			sql.append(" and c.config_type=:configType ");
	        paramMap.put("configType", config.getConfigType());
		}
		
		if(config.getModifiedDate() != null){
			sql.append(" and c.modified_date>=:modifiedDate ");
	        paramMap.put("modifiedDate", config.getModifiedDate());			
		}      
		sql.append(" order by c.modified_date desc, c.id");
		
		logger.debug("sql:"+sql.toString());
        return namedJdbcTemplate.query(sql.toString(), paramMap, new BeanPropertyRowMapper<ConfigItemBean>(ConfigItemBean.class));
	}

	@Override
	public int askQuestion(QuestionBean qBean) {
		String sql = "INSERT INTO t_question (event_type, event_id, asker_id, question) values(:eventType,:eventId,:askerId,:question)";
		return DaoUtils.insert(namedJdbcTemplate, qBean, sql);
	}
	
	@Override
	public int answerQuestion(QuestionBean qBean) {
		String sql = "UPDATE t_question SET answerer_id = :answererId,answer = :answer WHERE id = :Id";
		Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("answererId", Long.valueOf(qBean.getAnswererId()));
        paramMap.put("answer", qBean.getAnswer());
        paramMap.put("Id", Long.valueOf(qBean.getId()));
        return namedJdbcTemplate.update(sql, paramMap);
	}

	@Override
	public List<QuestionBean> getQuestion(QuestionBean qBean) {
		String sql = "SELECT q.id,q.event_type,q.event_id,q.asker_id,q.answerer_id,q.question,q.answer,q.created_date,q.modified_date,o.nick_name asker_nick_name,a.nick_name answerer_nick_name"
				+ " FROM t_question q join t_user o on q.asker_id=o.id left join t_user a on q.answerer_id=a.id"
				+ " where q.event_id=:eventId and q.event_type=:eventType order by created_date desc";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("eventId", Long.valueOf(qBean.getEventId()));
        paramMap.put("eventType", qBean.getEventType());
        return namedJdbcTemplate.query(sql, paramMap, new BeanPropertyRowMapper<QuestionBean>(QuestionBean.class));
	}

	@Override
	public int getQuestionTotal(QuestionBean qBean) {
		String sql = "SELECT count(1) FROM t_question q where q.event_id=:eventId and q.event_type=:eventType";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("eventId", Long.valueOf(qBean.getEventId()));
        paramMap.put("eventType", qBean.getEventType());
        return namedJdbcTemplate.queryForInt(sql, paramMap);
	}
	
	@Override
	public List<SurveyDefBean> getSurveyDef(SurveyDefBean sdBean) {
		String sql = "select id, event_type, action, number, question, question_type, created_date, modified_date from t_survey_def"
				+ " where event_type=:eventType and action=:action";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("eventType", sdBean.getEventType());
        paramMap.put("action", sdBean.getAction());
        return namedJdbcTemplate.query(sql, paramMap, new BeanPropertyRowMapper<SurveyDefBean>(SurveyDefBean.class));
	}

	@Override
	public int saveSurvey(SurveyBean survey) {
        
		String sql = " INSERT INTO t_survey (event_type, event_id, user_id, action) VALUES (:eventType,:eventId,:userId, :action)";
		return DaoUtils.insert(namedJdbcTemplate, survey, sql);	
	}

	@Override
	public int saveSurveyDef(SurveyDefBean surveyDefBean) {
		String sql = "INSERT INTO t_survey_def (survey_id, number, question, answer)"
				+ " VALUES (:surveyId, :number, :question, :answer)";
		return DaoUtils.insertWithoutId(namedJdbcTemplate, surveyDefBean, sql);
	}

	@Override
	public int saveMessage(MessageBean message) {
		int result = 0;
		String sql = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if(message.getReceiver()==0 && message.getSender()==0  && BeanConstant.EVENT_TYPE_GROUPBUY.equals(message.getEventType()) && (message.getEventId()!=0 || message.getSubEventId()!=0)){
			sql = "INSERT INTO t_message (sender, receiver, topic,message_type, message, event_type, event_id) "
					+ " select gb.created_by, gbp.user_id,concat_ws(':',gb.title,:topic), :messageType,:message,:eventType,gb.id"
					+ " from t_groupbuy gb, t_gb_purchase gbp"
					+ " where gb.id=gbp.gb_id and gb.created_by<>gbp.user_id";
			if(message.getEventId()!=0){
				sql +=" and gbp.status<20 and gb.id=:eventId";
			}else if(message.getSubEventId()!=0){
				sql +=" and gbp.id=:subEventId";
			}
			
			String ids = DaoUtils.insertwithMulId(namedJdbcTemplate, message, sql);
			
			if(StringUtils.isNotEmpty(ids)){
				sql = "update t_message set ancestor_id = id where id in ("+ids+")";
				result = namedJdbcTemplate.update(sql,paramMap);				
			}
		}else if(BeanConstant.EVENT_TYPE_GROUPBUY.equals(message.getEventType()) && 
				BeanConstant.ACTION_GB_JOB_REMIND_PURCHASE.equals(message.getMessageType())){
			sql = "INSERT INTO t_message (sender, receiver, topic,message_type, message, event_type, event_id) "
					+ " select :sender,gb.created_by,concat_ws(':',gb.title,:topic), :messageType,:message,:eventType,gb.id"
					+ " from t_groupbuy gb "
					+ " where gb.status=0 and exists (select gbp.id from t_gb_purchase gbp where gbp.status=0 and gb.created_by<>gbp.user_id and gb.id=gbp.gb_id) "
					+ " and gb.due_date < DATE_FORMAT(now(), '%Y-%m-%d') "
					+ " order by gb.modified_date desc";
			
			String ids = DaoUtils.insertwithMulId(namedJdbcTemplate, message, sql);
			
			if(StringUtils.isNotEmpty(ids)){
				sql = "update t_message set ancestor_id = id where id in ("+ids+")";
				result = namedJdbcTemplate.update(sql,paramMap);	
			}
		}else if(BeanConstant.EVENT_TYPE_GROUPBUY.equals(message.getEventType()) && 
				BeanConstant.ACTION_GB_JOB_REMIND_EDIT.equals(message.getMessageType())){
			sql = "INSERT INTO t_message (sender, receiver, topic,message_type, message, event_type, event_id) "
					+ " select :sender,gb.created_by,concat_ws(':',gb.title,:topic), :messageType,:message,:eventType,gb.id"
					+ " from t_groupbuy gb "
					+ " where gb.status=0 and not exists (select gbp.id from t_gb_purchase gbp where gbp.status=0 and gb.created_by<>gbp.user_id and gb.id=gbp.gb_id) "
					+ " and gb.due_date < DATE_FORMAT(now(), '%Y-%m-%d') "
					+ " order by gb.modified_date desc";
			
			String ids = DaoUtils.insertwithMulId(namedJdbcTemplate, message, sql);
			
			if(StringUtils.isNotEmpty(ids)){
				sql = "update t_message set ancestor_id = id where id in ("+ids+")";
				result = namedJdbcTemplate.update(sql,paramMap);				
			}
		}else{
			sql = "INSERT INTO t_message (sender, receiver,topic, message_type, message, event_type, event_id,ancestor_id) "
					+ " values(:sender, :receiver,:topic, :messageType, :message, :eventType, :eventId,:ancestorId)";
			result = DaoUtils.insert(namedJdbcTemplate, message, sql);
			if(message.getAncestorId()==0){
				sql = "update t_message set ancestor_id = id where id =:id";
				paramMap.put("id", Long.valueOf(message.getId()));
				namedJdbcTemplate.update(sql,paramMap);		
			}
		}
		return result;
	}

	@Override
	public int readMessage(MessageBean message) {
		int result = 0;
		String sql = "UPDATE t_message SET received = 1,received_date=sysdate() WHERE ";
		String sql_temp = "";
		Map<String, Object> paramMap = new HashMap<String, Object>();

        if(message.getId()>0){
        	sql_temp += " and id = :id";
        	paramMap.put("id", Long.valueOf(message.getId()));
        }

        if(message.getReceiver()>0){
        	sql_temp += " and receiver=:receiver";
        	paramMap.put("receiver", Long.valueOf(message.getReceiver()));
        }
        if(message.getSender()>0){
        	sql_temp += " and sender=:sender";
        	paramMap.put("sender", Long.valueOf(message.getSender()));
        }
        if(message.getAncestorId()>0){
        	sql_temp += " and ancestor_id=:ancestorId";
        	paramMap.put("ancestorId", Long.valueOf(message.getAncestorId()));
        }
        if(!StringUtils.isEmpty(message.getEventType()) && message.getEventId()>0){
            paramMap.put("eventType", message.getEventType());
            paramMap.put("eventId", Long.valueOf(message.getEventId()));
            sql_temp +=" and event_type=:eventType and event_id=:eventId";
        }
        if(!StringUtils.isEmpty(sql_temp)){
        	sql += sql_temp.substring(4);
        	result = namedJdbcTemplate.update(sql, paramMap);
        }
        return result;
	}

	@Override
	public List<MessageBean> getMessages(MessageBean message) {
		String sql = "select m.id, m.sender, m.receiver, m.received,m.topic, m.message_type, m.message, m.event_type, m.event_id, m.ancestor_id,m.received_date, m.created_date,u1.nick_name senderNickName,u2.nick_name receiverNickName,u1.pic_link senderPic,u2.pic_link receiverPic"
				+ " from t_message m, t_user u1, t_user u2 where m.sender=u1.id and m.receiver=u2.id ";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        if(message.getReceiver()>0){
        	sql += " and receiver=:receiver";
        	paramMap.put("receiver", Long.valueOf(message.getReceiver()));
        }
        if(message.getSender()>0){
        	sql += " and sender=:sender";
        	paramMap.put("sender", Long.valueOf(message.getSender()));
        }
        if(message.getAncestorId()>0){
        	sql += " and ancestor_id=:ancestorId";
        	paramMap.put("ancestorId", Long.valueOf(message.getAncestorId()));
        }        
        
        if(!StringUtils.isEmpty(message.getEventType()) && message.getEventId()>0){
            paramMap.put("eventType", message.getEventType());
            paramMap.put("eventId", Long.valueOf(message.getEventId()));
            sql +=" and event_type=:eventType and event_id=:eventId";
        }        
        sql +=" order by created_date desc";
        
        return namedJdbcTemplate.query(sql, paramMap, new BeanPropertyRowMapper<MessageBean>(MessageBean.class));
	}

	@Override
	public int getCountOfMessage(MessageBean message) {
		String sql = "select count(1) from t_message where receiver=:receiver ";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("receiver", Long.valueOf(message.getReceiver()));
        if(!StringUtils.isEmpty(message.getEventType()) && message.getEventId()>0){
            paramMap.put("eventType", message.getEventType());
            paramMap.put("eventId", Long.valueOf(message.getEventId()));
            sql +=" and event_type=:eventType and event_id=:eventId";
        }
        
        return namedJdbcTemplate.queryForInt(sql, paramMap);
	}

	@Override
	public int saveValuate(ValuateBean valuate) {
		String sql = " INSERT INTO t_valuate (valuator, valuatee, event_type, event_id, valuate_type, detail, scale, score) "
				+ " VALUES (:valuator, :valuatee, :eventType, :eventId, :valuateType, :detail, :scale,:score)";
		int result = DaoUtils.insert(namedJdbcTemplate, valuate, sql);	
		
		sql = "update t_user set score = score+:score, credit_amount=credit_amount+:scale, credit_count=credit_count+1, "
				+ "credit=(credit_amount)/(credit_count) where id=:userId";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("score", Integer.valueOf(valuate.getScore()));
        paramMap.put("scale", Integer.valueOf(valuate.getScale()));
        paramMap.put("userId", Long.valueOf(valuate.getValuatee()));
		namedJdbcTemplate.update(sql, paramMap);
		
		return result;
	}

	@Override
	public List<ValuateBean> getValuations(ValuateBean valuate) {
		StringBuilder sql = new StringBuilder();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		sql.append("select v.id,v.valuator, v.valuatee, v.event_type, v.event_id, v.valuate_type, v.detail, v.scale, v.created_date,uor.nick_name valuatorNickName, uee.nick_name valuateeNickName,uor.pic_link valuatorPicLink, uee.pic_link valuateePicLink"
				+ " from t_valuate v, t_user uor, t_user uee"
				+ " where v.valuator=uor.id and v.valuatee=uee.id ");
		
		if(valuate.getValuator()>0){
			sql.append(" and v.valuator=:valuator ");
	        paramMap.put("valuator", Long.valueOf(valuate.getValuator()));
		}
		if(valuate.getValuatee()>0){
			sql.append(" and v.valuatee=:valuatee ");
	        paramMap.put("valuatee", Long.valueOf(valuate.getValuatee()));
		}		
		if(!StringUtils.isEmpty(valuate.getEventType())){
			sql.append(" and v.event_type=:eventType ");
	        paramMap.put("eventType", valuate.getEventType());
		}
		if(valuate.getEventId()>0){
			sql.append(" and v.event_id=:eventId ");
	        paramMap.put("eventId", Long.valueOf(valuate.getEventId()));
		}	
		if(!StringUtils.isEmpty(valuate.getValuateType())){
			sql.append(" and v.valuate_type=:valuateType ");
	        paramMap.put("valuateType", valuate.getValuateType());
		}
		sql.append(" order by v.created_date desc, v.id");
		
		logger.debug("sql:"+sql.toString());
        return namedJdbcTemplate.query(sql.toString(), paramMap, new BeanPropertyRowMapper<ValuateBean>(ValuateBean.class));
	}

	@Override
	public int saveLog(SysLogBean log) {
		String sql = "INSERT INTO t_syslog (user_id, ip, token, module, operation, input, output, exception, duration, status, created_date) "
				+ " VALUES (:userId, :ip, :token, :module, :operation, :input, :output, :exception, :duration, :status, :createdDate)";
		return DaoUtils.insert(namedJdbcTemplate, log, sql);
	}
	@Override
	public int updateLog(SysLogBean log) {
		String sql = "update t_syslog set exception=:exception, duration=:duration, status=:status where id=:id ";
        return namedJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(log));
	}

	@Override
	public int saveHistory(HistoryBean history) {
		String sql = "INSERT INTO t_history (syslog_id, user_id, module, operation, message, event_type, event_id)"
				+ " VALUES (:syslogId, :userId, :module, :operation, :message, :eventType, :eventId)";
		return DaoUtils.insert(namedJdbcTemplate, history, sql);
	}

	@Override
	public List<HistoryBean> getHistory(HistoryBean history) {
		String sql = "SELECT h.id,h.user_id,h.module,h.operation,h.message,h.event_type,h.event_id,h.created_date,u.nick_name "
				+ " FROM t_history h left join t_user u on h.user_id=u.id "
				+ " where event_type=:eventType and event_id=:eventId order by created_date desc";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("eventType", history.getEventType());
        paramMap.put("eventId", Long.valueOf(history.getEventId()));
        
        return namedJdbcTemplate.query(sql, paramMap, new BeanPropertyRowMapper<HistoryBean>(HistoryBean.class));
	}

	@Override
	public int addPic(PictureBean pictureBean) {
		String sql = "INSERT INTO t_event_pic(event_id,event_type,pic_link,default_flag)"
				+ "	VALUES(:eventId,:eventType,:picLink,:defaultFlag)";
		return DaoUtils.insert(namedJdbcTemplate, pictureBean, sql);
	}


}
