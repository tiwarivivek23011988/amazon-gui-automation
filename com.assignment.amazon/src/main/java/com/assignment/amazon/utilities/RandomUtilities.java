package com.assignment.amazon.utilities;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class RandomUtilities {

	public static String getHostName() {
	    try {
	        InetAddress inetAddress = InetAddress.getLocalHost();
	        return inetAddress.getHostName();
	    } catch (UnknownHostException e) {
	        e.printStackTrace();
	        return "Unknown Host";
	    }
	}
	
	public static String getUserName() {
		return System.getProperty("user.name");
	}
	
	public static String getOsName() {
		return System.getProperty("os.name");
	}
	
	public static String getOsVersion() {
		return System.getProperty("os.version");
	}
	
	public static String getOsArchitecture() {
		return System.getProperty("os.arch");
	}
}
