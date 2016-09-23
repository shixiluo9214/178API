package com.framework.utils;

import java.util.Properties;

import org.springframework.core.io.support.PropertiesLoaderUtils;

public final class Cfg {

    private Cfg() {
    }

    private static Properties sysProps = null;
    private static Properties errorProps = null;
    private static Properties messageProps = null;
    public static boolean debug = false;

    static {
        try {
        	sysProps = PropertiesLoaderUtils.loadAllProperties("system.properties");
        	if(sysProps.getProperty("developer.debug").equals("true"))
        	{
        		debug = true;
        	}
        	errorProps = PropertiesLoaderUtils.loadAllProperties("error.properties");
        	messageProps = PropertiesLoaderUtils.loadAllProperties("message.properties");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getSysProperty(String key) {
        return sysProps.getProperty(key);
    }

    public static String getSysProperty(String key, String defaultValue) {
        return sysProps.getProperty(key, defaultValue);
    }
    
    public static String getErrorMessage(String errorCode){
    	return errorProps.getProperty(errorCode);
    }
    
    public static String getMessage(String messageType){
    	return messageProps.getProperty(messageType);
    }

	public static String getMessageTopic(String messageType) {
    	return messageProps.getProperty("Topic."+messageType);
	}
}
