/**
 * @author Vivek Tiwari
 * 
 */

package com.assignment.amazon.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import com.assignment.amazon.exceptions.ExceptionHandler;


/**
 * {@summary}
 * 
 * The CustomRetryAnalyser Class
 * 
 * The listener interface for receiving test retry events.
 * The class that is interested in processing a customInvokedMethod
 * event implements IRetryAnalyzer. When
 * the customInvokedMethod event occurs, CustomRetryAnalyser 
 * object's appropriate method is invoked.
 *
 * @see CustomRetryAnalyser
 */

public class CustomRetryAnalyser implements IRetryAnalyzer{
	
	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(CustomRetryAnalyser.class);

    /** The retry count. */
    private int retryCount = 0;
    
    /** The Constant maxRetryCount. */
    private static final int maxRetryCount = 3;

    /**
     * Retry - T retry execution for failed tests.
     *
     * @param result - the result
     * @return true, if successful
     */
    @Override
    public boolean retry(ITestResult result) {
    	logger.debug("*******In retry function*******");
    	try {
	        if (result.getStatus() == ITestResult.FAILURE && retryCount < maxRetryCount) {
	            retryCount++;
	            logger.info("Retrying test " + result.getMethod().getMethodName() + " for the " + retryCount + " time.");
	            return true; 
	        }
	        return false;
    	} catch(Exception e) {
    		ExceptionHandler.throwsException(e);
    		throw e;
    	}
    }
}