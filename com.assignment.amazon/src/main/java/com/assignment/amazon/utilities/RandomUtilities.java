/**
 * @author Vivek Tiwari
 * 
 */

package com.assignment.amazon.utilities;

import java.net.InetAddress;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.assignment.amazon.exceptions.ExceptionHandler;

/**
 * {@summary}
 * 
 * The RandomUtilities Class
 * 
 * This is a utility class which handles random needful
 * implementations like getting host name, getting operating system name,
 * getting operating system version, getting current user name etc.
 * 
 * @see RandomUtilities
 */
public class RandomUtilities {
	
	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(RandomUtilities.class);

	/**
	 * Gets the host name.
	 *
	 * @return the host name
	 */
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
	
	/**
	 * Gets the user name.
	 *
	 * @return the user name
	 */
	public static String getUserName() {
		logger.info("<= In getUserName function =>");
		return System.getProperty("user.name");
	}
	
	/**
	 * Gets the operating system name.
	 *
	 * @return the operating system name
	 */
	public static String getOsName() {
		logger.info("<= In getOsName function =>");
		return System.getProperty("os.name");
	}
	
	/**
	 * Gets the operating system version.
	 *
	 * @return the operating system version
	 */
	public static String getOsVersion() {
		logger.info("<= In getOsVersion function =>");
		return System.getProperty("os.version");
	}
	
	/**
	 * Gets the operating system architecture.
	 *
	 * @return the operating system architecture
	 */
	public static String getOsArchitecture() {
		logger.info("<= In getOsArchitecture function =>");
		return System.getProperty("os.arch");
	}
}
