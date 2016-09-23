package com.framework.utils;

import java.util.UUID;

import org.apache.log4j.Logger;



public class FrameWorkTools {

    private Logger logger;

    public FrameWorkTools(Logger logger) {
        this.logger = logger;
    }

    public String debug(Exception e) {
        String uuid = debug(e.getMessage());
        e.printStackTrace();
        return uuid;
    }

    public String debug(String log) {
        String uuid = getUUID();
        logger.debug("Log id:" + uuid);
        logger.debug(log);
        return uuid;
    }

    public String info(Exception e) {
        String uuid = info(e.getMessage());
        e.printStackTrace();
        return uuid;
    }

    public String info(String log) {
        String uuid = getUUID();
        logger.info("Log id:" + uuid);
        logger.info(log);
        return uuid;
    }

    public String error(Exception e) {
        String uuid = error(e.getMessage());
        e.printStackTrace();
        return uuid;
    }

    public String error(String log) {
        String uuid = getUUID();
        logger.error("Log id:" + uuid);
        logger.error(log);
        return uuid;
    }

    public String getUUID() {
        return UUID.randomUUID().toString();
    }

    public boolean isNull(String str) {
        if (str == null || str.length() < 1) {
            return true;
        }
        return false;
    }

    public boolean isNotNull(String str) {
        if (str == null || str.length() < 1) {
            return false;
        }
        return true;
    }

}
