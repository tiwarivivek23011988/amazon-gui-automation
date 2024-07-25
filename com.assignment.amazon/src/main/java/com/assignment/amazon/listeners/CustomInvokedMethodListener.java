package com.assignment.amazon.listeners;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

import com.assignment.amazon.utilities.DriverManager;

public class CustomInvokedMethodListener implements IInvokedMethodListener {

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod()) {
            //DriverManager.browserCounter();
        }
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        // After invocation logic
    	if(method.isTestMethod()) {
    		//DriverManager.resetCounter();
    	}
    }
}
