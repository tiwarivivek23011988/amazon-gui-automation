package com.assignment.amazon.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

public class CustomInvokedMethodListener implements IInvokedMethodListener {

	private static final Logger logger = LogManager.getLogger(CustomInvokedMethodListener.class);

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod()) {
            logger.info("<= In beforeInvocation funtion =>");
        }
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
    	if(method.isTestMethod()) {
    		logger.info("<= In afterInvocation funtion =>");
    	}
    }
}
