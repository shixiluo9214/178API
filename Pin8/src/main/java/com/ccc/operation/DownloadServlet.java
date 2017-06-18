package com.ccc.operation;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.ccc.comm.controller.CommController;
import com.framework.utils.Cfg;

/**
 * This servlet is used to provide the download service for android apk
 * 
 * @author bobao
 *
 */
public class DownloadServlet extends HttpServlet {

	private static final Logger logger = Logger.getLogger(CommController.class);
	private static final int BYTES_DOWNLOAD = 1024;

	private static final String DEFAULT_DOANLOWD_PATH = "upload/download/pin8-android.apk";
	private static final String FILENAME_DISPLAY_IN_BROWSER = "pin8-android.apk";

	public void doGet(HttpServletRequest request, HttpServletResponse response) {

		String rootPath = getServletConfig().getServletContext().getRealPath("/");
		
		/**
		 * the path could be configured in the system.properties file.
		 */
		String androidAppPath = Cfg.getSysProperty(Cfg.KEY_DOWNLOAD_ANDROID_PATH);
		if(StringUtils.isEmpty(androidAppPath)){
			androidAppPath = DEFAULT_DOANLOWD_PATH;
		}
		File downloadFile = new File(rootPath + File.separator + androidAppPath);
		System.out.println("path:" + downloadFile.getAbsolutePath());
		System.out.println("exists:" + downloadFile.exists());

		// response.setContentType("text/plain");
		OutputStream os = null;
		try {
			response.setContentType("application/octet-stream");
			response.setContentLength((int) downloadFile.length());
			response.setHeader("Content-Disposition", 
					String.format("attachment; filename=\"%s\"", FILENAME_DISPLAY_IN_BROWSER));

			ServletContext ctx = getServletContext();
			InputStream is = ctx.getResourceAsStream("/" + androidAppPath);

			int read = 0;
			byte[] bytes = new byte[BYTES_DOWNLOAD];
			os = response.getOutputStream();

			while ((read = is.read(bytes)) != -1) {
				os.write(bytes, 0, read);
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("Failed to provide the download service due to:\n");
			logger.error(e);
			// out.println("Sorry! System error! Please have a rety later!");
		} finally {
			if (os != null) {
				try {
					os.flush();
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		}

	}

	// public void doGet(HttpServletRequest request, HttpServletResponse
	// response) throws IOException {
	//
	//
	// response.setHeader( "Content-Disposition",
	// String.format("attachment; filename=\"%s\"", file.getName()));
	//
	// OutputStream out = response.getOutputStream();
	// try (FileInputStream in = new FileInputStream(file)) {
	// byte[] buffer = new byte[4096];
	// int length;
	// while ((length = in.read(buffer)) > 0) {
	// out.write(buffer, 0, length);
	// }
	// }
	// out.flush();
	// }
}