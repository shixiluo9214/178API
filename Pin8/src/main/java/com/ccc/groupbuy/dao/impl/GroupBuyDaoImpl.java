package com.ccc.groupbuy.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.ccc.comm.bean.HistorySummaryBean;
import com.ccc.groupbuy.bean.GroupBuyBean;
import com.ccc.groupbuy.bean.GroupBuyItemBean;
import com.ccc.groupbuy.bean.GroupBuyItemPurchaseBean;
import com.ccc.groupbuy.bean.GroupBuyPurchaseBean;
import com.ccc.groupbuy.bean.GroupBuySearchBean;
import com.ccc.groupbuy.bean.PayinfoBean;
import com.ccc.groupbuy.bean.PictureBean;
import com.ccc.groupbuy.dao.GroupBuyDao;
import com.framework.bean.BeanConstant;
import com.framework.dao.BaseDao;
import com.framework.dao.DaoUtils;

@Repository
public class GroupBuyDaoImpl extends BaseDao implements GroupBuyDao {	

	private static final Logger logger = Logger.getLogger(GroupBuyDaoImpl.class);	
	
	@Override
	public int create(GroupBuyBean groupBuy) {
		
		String sql = "INSERT INTO t_groupbuy (title, catalog, due_date, participate_scope, visible_scope, contact_type, invitation_code,freight_cal,deliver_info,status, member_limit, created_date, modified_date, created_by, description, type, can_add_item)"
				+ " VALUES (:title, :catalog, :dueDate,:participateScope, :visibleScope, :contactType, :invitationCode, :freightCal,:deliverInfo, :status, :memberLimit, :createdDate,:modifiedDate, :createdBy, :description,:type,:canAddItem)";
		return DaoUtils.insert(namedJdbcTemplate, groupBuy, sql);
	}

	@Override
	public int update(GroupBuyBean groupBuy) {
		String sql = "update t_groupbuy set ";
		String sql1 = new String();
		int update = 0;
        Map<String, Object> paramMap = new HashMap<String, Object>();
        if(groupBuy.getDueDate() != null){
        	sql1 += " ,due_date = :dueDate";
            paramMap.put("dueDate", groupBuy.getDueDate());
            update = 1;
        }
        if(groupBuy.getMemberLimit()!=0)
        {
        	sql1 += " ,member_limit = :memberLimit";
            paramMap.put("memberLimit", groupBuy.getMemberLimit());
            update = 1;
        }        
        if(groupBuy.getDeliverInfo() != null){
        	sql1 += " ,deliver_info=:deliverInfo";
            paramMap.put("deliverInfo", groupBuy.getDeliverInfo());
            update = 1;
        }   
        if(groupBuy.getDeliverStatus() != 0){
        	sql1 += " ,deliver_status=:deliverStatus";
            paramMap.put("deliverStatus", groupBuy.getDeliverStatus());
            update = 1;
        }
        if(1==update){
            sql += sql1.substring(2); //remove first " ,"        
            sql += " where id=:id ";
            paramMap.put("id", Long.valueOf(groupBuy.getId()));
        	return namedJdbcTemplate.update(sql, paramMap);
        }else{
        	return 0;
        }
	}
	
	@Override
	public int setStatus(GroupBuyBean groupBuy) {
		String sql = "update t_groupbuy set status=:status where id=:id";
        return namedJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(groupBuy));
	}	

	@Override
	public int addItem(GroupBuyItemBean groupBuyItemBean) {
		String sql = "INSERT INTO t_gb_item(gb_id,name,list_price,unit,quantity_limit,detail)"
				+ "	VALUES(:gbId,:name,:listPrice,:unit,:quantityLimit,:detail)";
		return DaoUtils.insert(namedJdbcTemplate, groupBuyItemBean, sql);
	}

	@Override
	public int adjustItem(GroupBuyItemBean groupBuyItemBean) {
		StringBuffer sql =  new StringBuffer();
		sql.append("update t_gb_item set ");
		int flagFrist = 1;
        Map<String, Object> paramMap = new HashMap<String, Object>();
        if(groupBuyItemBean.getNetPrice()>-1){
        	if(0==flagFrist){
        		sql.append(",");
        	}
    		flagFrist = 0;
        	sql.append("net_price=:netPrice ");
            paramMap.put("netPrice", Float.valueOf(groupBuyItemBean.getNetPrice()));
        }
        if(groupBuyItemBean.getNetQuantity()>-1){
        	if(0==flagFrist){
        		sql.append(",");
        	}
    		flagFrist = 0;
        	sql.append("net_quantity=:netQuantity ");
            paramMap.put("netQuantity", Float.valueOf(groupBuyItemBean.getNetQuantity()));
        }
        if(groupBuyItemBean.getFreight()>-1){
        	if(0==flagFrist){
        		sql.append(",");
        	}
    		flagFrist = 0;
        	sql.append("freight=:freight ");
            paramMap.put("freight", Float.valueOf(groupBuyItemBean.getFreight()));
        }
		sql.append("where id=:id");
        paramMap.put("id", Long.valueOf(groupBuyItemBean.getId()));
        
        return namedJdbcTemplate.update(sql.toString(), paramMap);
    }

	@Override
	public int addPic(PictureBean pictureBean) {
		String sql = "INSERT INTO t_event_pic(event_id,event_type,pic_link)"
				+ "	VALUES(:eventId,:eventType,:picLink)";
		return DaoUtils.insert(namedJdbcTemplate, pictureBean, sql);
	}

	@Override
	public int insertParticipate(GroupBuyPurchaseBean groupBuyPurchase) {
        
		String sql = " INSERT INTO t_gb_purchase(gb_id,user_id,status) VALUES(:gbId,:userId,:status)";
		return DaoUtils.insert(namedJdbcTemplate, groupBuyPurchase, sql);	  
	}

	@Override
	public int participateItem(GroupBuyItemPurchaseBean groupBuyItemPurchaseBean) {
		String sql = "INSERT INTO t_gbi_purchase(gbp_id,gbi_id,quantity)"
				+ "VALUES(:gbpId,:gbiId,:quantity) ON DUPLICATE KEY UPDATE quantity=:quantity";
		return DaoUtils.insert(namedJdbcTemplate, groupBuyItemPurchaseBean, sql);
		
	}

	@Override
	public int adjustItemPurchase(GroupBuyItemPurchaseBean groupBuyItemPurchaseBean) {

		StringBuffer sql =  new StringBuffer();
		sql.append("update t_gbi_purchase set ");
		int flagFrist = 1;
        Map<String, Object> paramMap = new HashMap<String, Object>();
        if(groupBuyItemPurchaseBean.getNetQuantity()>-1){
        	if(0==flagFrist){
        		sql.append(",");
        	}
    		flagFrist = 0;
        	sql.append("net_quantity=:netQuantity ");
            paramMap.put("netQuantity", Float.valueOf(groupBuyItemPurchaseBean.getNetQuantity()));
        }
        if(groupBuyItemPurchaseBean.getFreight()>-1){
        	if(0==flagFrist){
        		sql.append(",");
        	}
    		flagFrist = 0;
        	sql.append("freight=:freight ");
            paramMap.put("freight", Float.valueOf(groupBuyItemPurchaseBean.getFreight()));
        }
		sql.append("where id=:id");
        paramMap.put("id", Long.valueOf(groupBuyItemPurchaseBean.getId()));
        
        return namedJdbcTemplate.update(sql.toString(), paramMap);
	}

	@Override
	public List<GroupBuyBean> getList(GroupBuySearchBean search) {
		StringBuilder sql = new StringBuilder();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		sql.append("select gb.id,gb.title,gb.catalog,gb.due_date,gb.participate_scope, gb.visible_scope, gb.contact_type, gb.invitation_code,gb.status, gb.deliver_status,gb.deliver_info,gb.member_limit,gb.total_member,gb.created_by,gb.created_Date,gb.modified_date,gb.description,gb.type,gb.can_add_item,o.nick_name,p.pic_link");
		sql.append(" from t_groupbuy gb join t_user o on gb.created_by=o.id left join t_event_pic p on p.event_id=gb.id and p.event_type='EVENT_GROUPBUY' "
				+ " where gb.status<20 ");
		if(!StringUtils.isEmpty(search.getCatalog())){
			sql.append(" and gb.catalog=:catalog ");
	        paramMap.put("catalog", search.getCatalog());
		}
		
		if(search.getFilterType().equals(BeanConstant.GB_SEARCH_TYPE_INCOMM)){
			sql.append("and gb.status=0 and gb.type=0 and exists( select 1 from t_address cu1, t_address cu2 where cu1.comm_id=cu2.comm_id and cu1.user_id=o.id and cu2.user_id=:userId) ");
	    }else if(search.getFilterType().equals(BeanConstant.GB_SEARCH_TYPE_CREATED)){
			sql.append("and gb.created_by=:userId");
		}else if(search.getFilterType().equals(BeanConstant.GB_SEARCH_TYPE_PARTICIPATED)){
			sql.append("and gb.created_by!=:userId and exists( select 1 from t_gb_purchase gbp where gbp.status<20 and gbp.gb_id=gb.id and gbp.user_id=:userId) ");
		}
		
		if(search.getFromDate()!= null)
		{
			sql.append(" and gb.modified_date >= :fromDate ");
			paramMap.put("fromDate", search.getFromDate());
		}

		if(search.getToDate()!= null)
		{
			sql.append(" and gb.modified_date <= :toDate ");
			paramMap.put("toDate", search.getToDate());
		}
		
        paramMap.put("userId", Long.valueOf(search.getUserId()));        
		sql.append(" order by gb.modified_date desc, gb.status");
		
		logger.debug("sql:"+sql.toString());
        return namedJdbcTemplate.query(sql.toString(), paramMap, new BeanPropertyRowMapper<GroupBuyBean>(GroupBuyBean.class));
	}	

	@Override
	public List<GroupBuyPurchaseBean> getGBPList(GroupBuySearchBean search) {
		 if(search.getFilterType().equals(BeanConstant.GB_SEARCH_TYPE_CREATED)){
				return null;
		}
				
		StringBuilder sql = new StringBuilder();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		sql.append("select gb.id gb_id,o.id user_id,gbp.pay_status,o.nick_name,o.pic_link");
		sql.append(" from t_groupbuy gb join t_gb_purchase gbp on gb.id=gbp.gb_id and gbp.status<20 join t_user o on gbp.user_id=o.id");
		sql.append(" where gb.status<20 ");
		if(!StringUtils.isEmpty(search.getCatalog())){
			sql.append(" and gb.catalog=:catalog ");
	        paramMap.put("catalog", search.getCatalog());
		}
		
		if(search.getFilterType().equals(BeanConstant.GB_SEARCH_TYPE_INCOMM)){
			sql.append(" and gb.status=0 and exists( select 1 from t_address cu1, t_address cu2 where cu1.comm_id=cu2.comm_id and cu1.user_id=o.id and cu2.user_id=:userId) ");
	    }else if(search.getFilterType().equals(BeanConstant.GB_SEARCH_TYPE_PARTICIPATED)){
			sql.append(" and gb.created_by!=:userId and  gbp.user_id=:userId ");
		}

		if(search.getFromDate()!= null)
		{
			sql.append(" and gb.modified_date >= :fromDate ");
			paramMap.put("fromDate", search.getFromDate());
		}

		if(search.getToDate()!= null)
		{
			sql.append(" and gb.modified_date <= :toDate ");
			paramMap.put("toDate", search.getToDate());
		}

        paramMap.put("userId", Long.valueOf(search.getUserId()));        
		sql.append(" order by gb.modified_date desc, gb.status,gb.id,gbp.created_date");
		
		logger.debug("sql:"+sql.toString());
        return namedJdbcTemplate.query(sql.toString(), paramMap, new BeanPropertyRowMapper<GroupBuyPurchaseBean>(GroupBuyPurchaseBean.class));
	}

	@Override
	public GroupBuyBean getGroupBuy(GroupBuySearchBean search) {
		String sql = " SELECT gb.id, gb.title, gb.catalog, gb.due_date,gb.participate_scope, gb.visible_scope, gb.contact_type, gb.invitation_code, gb.status, gb.deliver_status,gb.deliver_date,gb.deliver_info,gb.freight_cal, gb.member_limit,gb.total_member,gb.list_amount,"
				+ " gb.net_amount,gb.freight,gb.total, gb.created_date, gb.modified_date, gb.created_by,gb.created_Date, gb.description,gb.type,gb.can_add_item,o.nick_name,o.pic_link owner_pic"
				+ " FROM t_groupbuy gb join t_user o on gb.created_by=o.id "
				+ " where gb.id  = :gbId ";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("gbId", Long.valueOf(search.getGbId()));
        if(!StringUtils.isEmpty(search.getInvitationCode())){
        	sql +=" and gb.invitation_code=:invitationCode ";
            paramMap.put("invitationCode", search.getInvitationCode());
        }
        return namedJdbcTemplate.queryForObject(sql, paramMap, new BeanPropertyRowMapper<GroupBuyBean>(GroupBuyBean.class));        
	}	

	@Override
	public GroupBuyBean getGroupBuy(long gbId) {
		GroupBuySearchBean search = new GroupBuySearchBean();
		search.setGbId(gbId);
		return getGroupBuy(search);
	}

	@Override
	public List<GroupBuyItemBean> getItems(long gbId,long userId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("gbId", Long.valueOf(gbId));
        String sql;
        if(userId != 0 ){
    		sql = "SELECT gbi.id,gbi.gb_id,gbi.name,gbi.list_price,gbi.net_price,gbi.unit,gbi.quantity_limit,gbi.total_quantity,IFNULL(gbip.net_quantity,0) net_quantity,IFNULL(gbip.freight,0) freight,gbi.detail,IFNULL(gbip.quantity,0) quantity"
    				+ " FROM t_gb_item gbi left join t_gb_purchase gbp on gbi.gb_id=gbp.gb_id and gbp.user_id=:userId and gbp.status<20 "
    				+ " left join t_gbi_purchase gbip on gbp.id=gbip.gbp_id and gbi.id=gbip.gbi_id"
    				+ " where gbi.gb_id=:gbId ";
            paramMap.put("userId", Long.valueOf(userId));
        }else{
        	sql = "SELECT gbi.id,gbi.gb_id,gbi.name,gbi.list_price,gbi.net_price,gbi.unit,gbi.quantity_limit,gbi.total_quantity,gbi.net_quantity,gbi.freight,gbi.detail"
        			+ " FROM t_gb_item gbi where gbi.gb_id=:gbId";
        }
        List<GroupBuyItemBean> items = namedJdbcTemplate.query(sql, paramMap, new BeanPropertyRowMapper<GroupBuyItemBean>(GroupBuyItemBean.class));        
        return items;
	}

	@Override
	public List<PictureBean> getPics(long gbiId) {
		String sql = "SELECT id,event_id,event_type,pic_link"
    			+ " FROM t_event_pic where event_id=:gbiId and event_type=:eventType";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("gbiId", Long.valueOf(gbiId));
        paramMap.put("eventType", BeanConstant.EVENT_TYPE_GROUPBUYITEM);
        return namedJdbcTemplate.query(sql, paramMap, new BeanPropertyRowMapper<PictureBean>(PictureBean.class));
	}


	@Override
	public int addPic4GB(long gbId) {
		String sql=" INSERT INTO t_event_pic(event_id,event_type,pic_link) "
				+ "  select gbi.gb_id,'EVENT_GROUPBUY',p.pic_link from t_gb_item gbi, t_event_pic p"
				+ "  where gbi.gb_id=:gbId and gbi.id=p.event_id and p.event_type='EVENT_GROUPBUY_ITEM'"
				+ " and not exists (select 1 from t_event_pic p where p.event_id=gbi.gb_id and p.event_type='EVENT_GROUPBUY')"
				+ " order by gbi.id asc, p.id asc limit 1";
		Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("gbId", Long.valueOf(gbId));
        return namedJdbcTemplate.update(sql, paramMap);        
	}


	@Override
	public int calculateAmount4Purchase(long gbpId) {

		Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("gbpId", Long.valueOf(gbpId));
        
		String sql = "update t_groupbuy gb set "
				+ " total_member = (select count(1) from t_gb_purchase gbp where gbp.status=0 and gbp.gb_id= gb.id ),"
				+ " list_amount = (select IFNULL(sum(gbi.list_price*gbip.quantity),0) from t_gb_item gbi, t_gbi_purchase gbip, t_gb_purchase gbp where gbip.gbp_id=gbp.id and gbp.status=0 and gbi.gb_id=gb.id and gbip.gbi_id=gbi.id and gbi.list_price<>-1 and gbip.quantity<>-1)"
				+ " where gb.id=(select gbp1.gb_id from t_gb_purchase gbp1 where gbp1.id=:gbpId)";		
		namedJdbcTemplate.update(sql, paramMap);
		
		sql = "update t_gb_item gbi set "
				+ " total_quantity = (select IFNULL(sum(gbip.quantity),0) from t_gbi_purchase gbip, t_gb_purchase gbp where gbip.gbp_id=gbp.id and gbp.status<20 and gbip.gbi_id=gbi.id and gbip.quantity<>-1)"
				+ " where gbi.id in (select gbip.gbi_id from t_gbi_purchase gbip where gbip.gbp_id=:gbpId)";	
		namedJdbcTemplate.update(sql, paramMap);
		
		sql = "update t_gb_purchase gbp set "
				+ " list_amount = (select IFNULL(sum(gbi.list_price*gbip.quantity),0) from t_gb_item gbi, t_gbi_purchase gbip where gbip.gbp_id=gbp.id and gbip.gbi_id=gbi.id and gbi.list_price<>-1 and gbip.quantity<>-1)"
				+ " where gbp.id=:gbpId and gbp.status<20";
        return namedJdbcTemplate.update(sql, paramMap);
	}

	@Override
	public int calculateAmount4GB(long gbId) {

		Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("gbId", Long.valueOf(gbId));
        
		String sql = "update t_groupbuy gb set "
				+ " net_amount = (select IFNULL(sum(gbi.net_price*gbi.net_quantity),0) from t_gb_item gbi where gbi.gb_id=gb.id and gbi.net_price<>-1 and gbi.net_quantity<>-1),"
				+ " freight = (select IFNULL(sum(gbi.freight),0) from t_gb_item gbi where gbi.gb_id=gb.id and gbi.freight<>-1),"
				+ " total = IFNULL((gb.net_amount+gb.freight),0)"
				+ " where gb.id=:gbId";		
		namedJdbcTemplate.update(sql, paramMap);
		
		sql = "update t_gb_purchase gbp set "
				+ " net_amount = (select IFNULL(sum(gbi.net_price*gbip.net_quantity),0) from t_gb_item gbi, t_gbi_purchase gbip where gbip.gbp_id=gbp.id and gbip.gbi_id=gbi.id and gbi.net_price<>-1 and gbip.net_quantity<>-1),"
				+ " freight = (select IFNULL(sum(gbip.freight),0) from t_gbi_purchase gbip where gbip.gbp_id=gbp.id and gbip.freight<>-1),"
				+ " total = IFNULL((gbp.net_amount+gbp.freight),0)"
				+ " where gbp.gb_id=:gbId and gbp.status<20";
        return namedJdbcTemplate.update(sql, paramMap);
	}


	@Override
	public List<GroupBuyItemPurchaseBean> getItemPurchases(long gbiId) {
		String sql = "select gbip.id,gbip.gbp_id,gbip.gbi_id,gbip.quantity,gbip.net_quantity,gbip.freight,u.id user_id,u.nick_name,gbi.name,gbi.list_price,gbi.net_price,gbi.unit"
				+ " from t_gb_item gbi,t_gb_purchase gbp,t_gbi_purchase gbip, t_user u"
				+ " where gbi.id=:gbiId and gbi.id=gbip.gbi_id and gbip.gbp_id=gbp.id and gbp.user_id=u.id and gbp.status<20";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("gbiId", Long.valueOf(gbiId));
        return namedJdbcTemplate.query(sql, paramMap, new BeanPropertyRowMapper<GroupBuyItemPurchaseBean>(GroupBuyItemPurchaseBean.class));
	}


	@Override
	public List<GroupBuyPurchaseBean> getPurchases(long gbId,long userId) {
		String sql = "select gbp.id,gbp.gb_id,u.id user_id,gbp.status,gbp.pay_status,gbp.collect_status,gbp.receive_status,gbp.list_amount,gbp.net_amount,gbp.freight,gbp.total,u.nick_name,u.pic_link,u.credit"
				+ " from t_gb_purchase gbp, t_user u"
				+ " where gbp.gb_id=:gbid and gbp.user_id=u.id and gbp.status<20";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("gbid", Long.valueOf(gbId));
        if(userId!=0){
            paramMap.put("userId", Long.valueOf(userId));
            sql += " and u.id = :userId ";
        }
        return namedJdbcTemplate.query(sql, paramMap, new BeanPropertyRowMapper<GroupBuyPurchaseBean>(GroupBuyPurchaseBean.class));
	}



	@Override
	public int pay(GroupBuyPurchaseBean groupBuyPurchase) {
		String sql = "update t_gb_purchase set pay_status=1 where id=:id";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", Long.valueOf(groupBuyPurchase.getId()));
        return namedJdbcTemplate.update(sql, paramMap);
	}


	@Override
	public int receive(GroupBuyPurchaseBean groupBuyPurchase) {
		String sql = "update t_gb_purchase set receive_status=1 where id=:id";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", Long.valueOf(groupBuyPurchase.getId()));
        return namedJdbcTemplate.update(sql, paramMap);
	}


	@Override
	public int collect(GroupBuyPurchaseBean groupBuyPurchase) {
		String sql = "update t_gb_purchase set collect_status=1,pay_status=1 where id=:id";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", Long.valueOf(groupBuyPurchase.getId()));
        return namedJdbcTemplate.update(sql, paramMap);
	}


	@Override
	public int terminateParticipation(long gbpId) {
		String sql = "update t_gb_purchase set status=20 where id=:id";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", Long.valueOf(gbpId));
        return namedJdbcTemplate.update(sql, paramMap);
	}


	@Override
	public int createPayinfo(PayinfoBean payinfoBean) {
		String sql = "INSERT INTO t_payinfo(event_id,event_type,payment_type,account_id,account_name)"
				+ " VALUES(:eventId,:eventType,:paymentType,:accountId,:accountName)";
		return DaoUtils.insertWithoutId(namedJdbcTemplate, payinfoBean, sql);
	}

	@Override
	public int removePayinfo(long gbId) {
		String sql="update t_payinfo set status=1 where event_type='EVENT_GROUPBUY' and event_id=:gbId";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("gbId", Long.valueOf(gbId));
        return namedJdbcTemplate.update(sql, paramMap);
	}

	@Override
	public List<PayinfoBean> getPayinfos(long gbId) {
		String sql = "SELECT id,event_id,event_type,payment_type,account_id,account_name"
    			+ " FROM t_payinfo where event_id=:gbId and event_type=:eventType and status=0";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("gbId", Long.valueOf(gbId));
        paramMap.put("eventType", BeanConstant.EVENT_TYPE_GROUPBUY);
        return namedJdbcTemplate.query(sql, paramMap, new BeanPropertyRowMapper<PayinfoBean>(PayinfoBean.class));
	}

	@Override
	public int updateSatus4Owner(GroupBuyBean groupBuy) {
		String sql = "update t_gb_purchase gbp set pay_status=1, collect_status=1, receive_status=1 "
				+ " where gbp.gb_id=:id and gbp.user_id = (select gb.created_by from t_groupbuy gb where gbp.gb_id=gb.id)";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", Long.valueOf(groupBuy.getId()));
        return namedJdbcTemplate.update(sql, paramMap);
	}


	@Override
	public GroupBuyPurchaseBean getPurchaseById(long id) {
		String sql = "select gbp.id,gbp.gb_id,u.id user_id,gbp.status,gbp.pay_status,gbp.collect_status,gbp.receive_status,gbp.list_amount,gbp.net_amount,gbp.freight,gbp.total,u.nick_name"
				+ " from t_gb_purchase gbp, t_user u"
				+ " where gbp.id=:id and gbp.user_id=u.id";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", Long.valueOf(id));
        return namedJdbcTemplate.queryForObject(sql, paramMap, new BeanPropertyRowMapper<GroupBuyPurchaseBean>(GroupBuyPurchaseBean.class));
	}
	
	@Override
	public List<HistorySummaryBean> getGBSummary(long userId) {
		String sql = "select event_type, operation, quantity from "
				+ " (select 'EVENT_GROUPBUY' event_type, 'GB_Create' operation, count(1) quantity"
				+ " from t_groupbuy gb where gb.created_by =:userId and gb.status=10"
				+ " union"
				+ " select 'EVENT_GROUPBUY' eventType, 'GB_Participate' operation, count(1) quantity"
				+ " from t_groupbuy gb, t_gb_purchase gbp "
				+ " where gb.id =gbp.gb_id and gbp.user_id=:userId and gb.created_by!=gbp.user_id and gb.status=10 and gbp.status=0) as tmp"
				+ " where quantity>0";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userId", Long.valueOf(userId));
        return namedJdbcTemplate.query(sql, paramMap, new BeanPropertyRowMapper<HistorySummaryBean>(HistorySummaryBean.class));
    }

	@Override
	public int disuseParticipation(long gbpId) {
		String sql = "update t_gb_purchase set status=10 where id=:id";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", Long.valueOf(gbpId));
        return namedJdbcTemplate.update(sql, paramMap);
	}

	@Override
	public List<GroupBuyBean> getList4Job(String type) {
		String sql = null;
		if(BeanConstant.ACTION_GB_JOB_TERMINATE_NO_PARTICIPATE.equals(type)){
			sql = "select gb.id,gb.created_by"
					+ " from t_groupbuy gb"
					+ " where gb.status=0 and not exists (select gbp.id from t_gb_purchase gbp where gbp.status=0 and gb.created_by<>gbp.user_id and gb.id=gbp.gb_id)"
					+ " and gb.due_date < DATE_FORMAT(DATE_SUB(now(),INTERVAL 3 DAY), '%Y-%m-%d')"
					+ " order by gb.modified_date desc";
		}else if(BeanConstant.ACTION_GB_JOB_TERMINATE_NO_PURCHASE.equals(type)){
			sql = "select gb.id,gb.created_by"
					+ " from t_groupbuy gb"
					+ " where gb.status=0 and exists (select gbp.id from t_gb_purchase gbp where gbp.status=0 and gb.created_by<>gbp.user_id and gb.id=gbp.gb_id)"
					+ " and gb.due_date < DATE_FORMAT(DATE_SUB(now(),INTERVAL 7 DAY), '%Y-%m-%d')"
					+ " order by gb.modified_date desc";
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		logger.debug("sql:"+sql);
		if(sql != null){
			return namedJdbcTemplate.query(sql, paramMap, new BeanPropertyRowMapper<GroupBuyBean>(GroupBuyBean.class));
		}else{
			return null;
		}
			
	}

	@Override
	public int notifyDeliver(GroupBuyBean groupBuy) {
		String sql = "update t_groupbuy set status=:status,deliver_date=sysdate() where id=:id";
        return namedJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(groupBuy));
	}

}
