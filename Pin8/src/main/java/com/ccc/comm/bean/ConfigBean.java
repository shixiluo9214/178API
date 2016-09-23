package com.ccc.comm.bean;

import java.util.ArrayList;
import java.util.List;

import com.framework.bean.BaseBean;

public class ConfigBean extends BaseBean{

	private static final long serialVersionUID = 1L;
	
	private String name;
	private String configType;

	private List<ConfigItemBean> items;
	
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getConfigType() {
		return configType;
	}


	public void setConfigType(String configType) {
		this.configType = configType;
	}


	public List<ConfigItemBean> getItems() {
		return items;
	}


	public void setItems(List<ConfigItemBean> items) {
		this.items = items;
	}


	public void addItem(ConfigItemBean item)
	{
		if(items == null)
		{
			items = new ArrayList<ConfigItemBean>();			
		}
		items.add(item);
	}	
	
}
