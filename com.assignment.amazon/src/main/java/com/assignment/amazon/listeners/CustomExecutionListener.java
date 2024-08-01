/**
 * @author Vivek Tiwari
 * 
 */

package com.assignment.amazon.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IExecutionListener;

import com.assignment.amazon.configuration.Setup;
import com.assignment.amazon.exceptions.ExceptionHandler;

/**
 * {@summary}
 * 
 * The CustomExecutionListener class
 * 
 * The listener interface for receiving execution events.
 * The class that is interested in processing a customExecution
 * event implements TestNG provided IExecutionListener. When
 * the customExecution event occurs, CustomExecutionListener 
 * object's appropriate method is invoked.
 *
 * @see CustomExecutionListener
 */
public class CustomExecutionListener implements IExecutionListener{
	  
	/** The Constant logger. */
  	private static final Logger logger = LogManager.getLogger(CustomExecutionListener.class);

	/**
  	 * On execution start.
  	 */
  	@Override
	  public void onExecutionStart() {
		logger.debug("*******In onExecutionStart function*******");
		try {
			CustomTestNGListener.createInstance();
		} catch(Exception e) {
			ExceptionHandler.throwsException(e);
			throw e;
		}
	  }

	/**
  	 * On execution finish.
  	 */
  	@Override
	  public void onExecutionFinish() {
		logger.debug("*******onExecutionFinish function*******");
		try {
			CustomTestNGListener.extentReportPreProcessing();
			Setup.killDanglingBrowserProcessReferences();
		} catch(Exception e) {
			ExceptionHandler.throwsException(e);
			throw e;
		}
	  }
}
