package com.ccc.comm.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.framework.bean.BaseBean;

@JsonIgnoreProperties({"id","configId","configName","configType","createdDate","modifiedDate"})  
public class ConfigItemBean extends BaseBean {


	private static final long serialVersionUID = 1L;
	
	private long configId;
	private String name;
	private String configName;
	private String configType;
	
	public long getConfigId() {
		return configId;
	}
	public void setConfigId(long configId) {
		this.configId = configId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getConfigName() {
		return configName;
	}
	public void setConfigName(String configName) {
		this.configName = configName;
	}
	public String getConfigType() {
		return configType;
	}
	public void setConfigType(String configType) {
		this.configType = configType;
	}
	
	
}
