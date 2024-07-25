package com.assignment.amazon.listeners;

import org.testng.IExecutionListener;

public class CustomExecutionListener implements IExecutionListener{

	  @Override
	  public void onExecutionStart() {
	    CustomTestNGListener.createInstance();
	  }

	  @Override
	  public void onExecutionFinish() {
		CustomTestNGListener.extentReportPreProcessing();
	  }
}
