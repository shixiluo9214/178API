package com.framework.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileUtils {

	public static final String TYPE_USERADDRESS = "TYPE_USERADDRESS";
	public static final String TYPE_USERPIC = "TYPE_USERPIC";
	public static final String TYPE_GROUPBUYITEM = "TYPE_GROUPBUYITEM";
	public static final String TYPE_COMMADDRESS = "TYPE_COMMADDRESS";
	public static final String TYPE_EXPERIENCE = "TYPE_EXPERIENCE";

	public static String saveFile(String name, MultipartFile file,HttpServletRequest request, String type) throws IllegalStateException, IOException {
		String path = null;
		if (!file.isEmpty()) {
			String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
			path = getPath(type) + name + "_" +System.currentTimeMillis() + fileType;
			String apath =  request.getSession().getServletContext().getRealPath("/") + path;
			File destFile = new File(apath);
			file.transferTo(destFile);
		}
		return path;
	}
	
	private static String getPath(String type) {
		String separator = java.io.File.separator.indexOf("/") != -1 ? "/": "\\";
		String path = separator+"upload"+ separator ;
		if(StringUtils.equals(TYPE_USERADDRESS, type)){
			path += "userAddressFolder";
		}else if (StringUtils.equals(TYPE_USERPIC, type)){
			path += "userPicFolder";			
		}else if (StringUtils.equals(TYPE_GROUPBUYITEM, type)){
			path += "groupbuyItemFolder";			
		}else if (StringUtils.equals(TYPE_COMMADDRESS, type)){
			path += "commAddressFolder";		
		}else if (StringUtils.equals(TYPE_EXPERIENCE, type)){
			path += "experienceFolder";			
		}	
		path += separator;
		return path;
	}
	
	public static String readFile(String fileName)
            throws Exception {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
            String line = null;
            while ((line = br.readLine()) != null) {
                if (line.charAt(0) == '-') {
                    continue;
                } else {
                    sb.append(line);
                    sb.append('\r');
                }
            }
        } finally {
            if (br != null) {
                br.close();
            }
        }
        return sb.toString();
    }

}
