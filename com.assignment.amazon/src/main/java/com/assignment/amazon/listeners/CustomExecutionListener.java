package com.assignment.amazon.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IExecutionListener;

public class CustomExecutionListener implements IExecutionListener{
	  
	  private static final Logger logger = LogManager.getLogger(CustomExecutionListener.class);

	  @Override
	  public void onExecutionStart() {
		logger.info("<= In onExecutionStart function =>");
	    CustomTestNGListener.createInstance();
	  }

	  @Override
	  public void onExecutionFinish() {
		logger.info("<= onExecutionFinish function =>");
		CustomTestNGListener.extentReportPreProcessing();
	  }
}
