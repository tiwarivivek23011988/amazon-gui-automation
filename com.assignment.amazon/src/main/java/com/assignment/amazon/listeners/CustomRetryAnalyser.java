package com.assignment.amazon.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class CustomRetryAnalyser implements IRetryAnalyzer{

    private int retryCount = 0;
    private static final int maxRetryCount = 3;

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < maxRetryCount) {
            retryCount++;
            System.out.println("Retrying test " + result.getMethod().getMethodName() + " for the " + retryCount + " time.");
            return true; 
        }
        return false;
    }
}