package com.framework.dao;

import java.util.Map;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.framework.bean.BaseBean;


public class DaoUtils {
	
	public static int insert(NamedParameterJdbcTemplate namedJdbcTemplate,BaseBean bean, String sql){
		
		KeyHolder keyHolder = new GeneratedKeyHolder(); 
	    int result = namedJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(bean),keyHolder);
	    if(keyHolder.getKeyList().size()==1){
	    	bean.setId(((Number)(keyHolder.getKeyList().get(0).get("GENERATED_KEY"))).longValue());
	    }
	    
	    return result;

	}
	
	public static int insertWithoutId(NamedParameterJdbcTemplate namedJdbcTemplate,BaseBean bean, String sql){
		
		return namedJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(bean));

	}

	public static int insertWithoutId(NamedParameterJdbcTemplate namedJdbcTemplate, Map<String, Object> paramMap, String sql){
		return namedJdbcTemplate.update(sql, paramMap);
	}

	
	public static String insertwithMulId(NamedParameterJdbcTemplate namedJdbcTemplate,BaseBean bean, String sql){
		
		String ids = new String();
		KeyHolder keyHolder = new GeneratedKeyHolder(); 
	    namedJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(bean),keyHolder);
	    for(int i=0;i<keyHolder.getKeyList().size();i++){
	    	ids += ","+((Long)(keyHolder.getKeyList().get(i).get("GENERATED_KEY"))).longValue();
	    }
	    if(ids.length()>2){
	    	ids = ids.substring(1);
	    }
	    return ids;
	}
}
