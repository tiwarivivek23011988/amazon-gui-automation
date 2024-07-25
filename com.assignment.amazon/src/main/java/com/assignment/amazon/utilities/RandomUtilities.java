package com.assignment.amazon.utilities;

import java.net.InetAddress;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.assignment.amazon.exceptions.ExceptionHandler;

public class RandomUtilities {
	
	private static final Logger logger = LogManager.getLogger(RandomUtilities.class);

	public static String getHostName() {
	    try {
	    	logger.info("<= In getHostName function =>");
	        InetAddress inetAddress = InetAddress.getLocalHost();
	        return inetAddress.getHostName();
	    } catch (Exception e) {
	        ExceptionHandler.throwsException(e);
	        return "Unknown Host";
	    }
	}
	
	public static String getUserName() {
		logger.info("<= In getUserName function =>");
		return System.getProperty("user.name");
	}
	
	public static String getOsName() {
		logger.info("<= In getOsName function =>");
		return System.getProperty("os.name");
	}
	
	public static String getOsVersion() {
		logger.info("<= In getOsVersion function =>");
		return System.getProperty("os.version");
	}
	
	public static String getOsArchitecture() {
		logger.info("<= In getOsArchitecture function =>");
		return System.getProperty("os.arch");
	}
}
