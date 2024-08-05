package com.assignment.amazon.utilities;

import com.assignment.amazon.exceptions.ExceptionHandler;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManagerUtility {
	
	private static ExtentReports extentReports;
    private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    public static synchronized ExtentReports getExtentReports() {
    	try {
	        if (extentReports == null) {
	        	ExtentSparkReporter sparkReporter = new ExtentSparkReporter("target/ExtentReport.html");
		        sparkReporter.config().setDocumentTitle("Automation Report");
		        sparkReporter.config().setReportName("Regression Testing");
		        sparkReporter.config().setTheme(Theme.DARK);
		        extentReports = new ExtentReports();
		        extentReports.attachReporter(sparkReporter);
		        extentReports.setSystemInfo("Host Name", RandomUtilities.getHostName());
		        extentReports.setSystemInfo("Environment", RandomUtilities.getOsName());
		        extentReports.setSystemInfo("User Name", RandomUtilities.getUserName());
		        extentReports.setSystemInfo("OS Version", RandomUtilities.getOsVersion());
		        extentReports.setSystemInfo("OS Architecture", RandomUtilities.getOsArchitecture());
		 
	        }
    	} catch(Exception e) {
    		ExceptionHandler.throwsException(e);
    		throw e;
    	}
        return extentReports;
    }

    public synchronized static void setExtentTest(ExtentTest test) {
        extentTest.set(test);
    }

    public synchronized static ExtentTest getExtentTest() {
        return extentTest.get();
    }
    
    public static synchronized void flush() {
        if (extentReports != null) {
            extentReports.flush();
        }
    }
}
