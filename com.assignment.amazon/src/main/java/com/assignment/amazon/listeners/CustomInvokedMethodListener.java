/**
 * @author Vivek Tiwari
 * 
 */

package com.assignment.amazon.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

/**
 * {@summary}
 * 
 * The CustomInvokedMethodListener Class
 * 
 * The listener interface for receiving InvokedMethod events.
 * The class that is interested in processing a customInvokedMethod
 * event implements @IInvokedMethodListener. When
 * the customInvokedMethod event occurs, CustomInvokedMethodListener 
 * object's appropriate method is invoked.
 *
 * @see CustomInvokedMethodListener
 */
public class CustomInvokedMethodListener implements IInvokedMethodListener {

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(CustomInvokedMethodListener.class);

    /**
     * Before invocation.
     *
     * @param method - the method
     * @param testResult - the test result
     */
    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod()) {
            logger.info("<= In beforeInvocation funtion =>");
        }
    }

    /**
     * After invocation.
     *
     * @param method - the method
     * @param testResult - the test result
     */
    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
    	if(method.isTestMethod()) {
    		logger.info("<= In afterInvocation funtion =>");
    	}
    }
}
