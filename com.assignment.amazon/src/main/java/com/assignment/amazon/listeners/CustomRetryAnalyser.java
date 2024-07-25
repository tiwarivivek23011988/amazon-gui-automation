package com.assignment.amazon.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class CustomRetryAnalyser implements IRetryAnalyzer{
	
	private static final Logger logger = LogManager.getLogger(CustomRetryAnalyser.class);

    private int retryCount = 0;
    private static final int maxRetryCount = 3;

    @Override
    public boolean retry(ITestResult result) {
    	logger.info("<= In retry function =>");
        if (retryCount < maxRetryCount) {
            retryCount++;
            logger.info("Retrying test " + result.getMethod().getMethodName() + " for the " + retryCount + " time.");
            return true; 
        }
        return false;
    }
}