/**
 * @author Vivek Tiwari
 * 
 */
package com.assignment.amazon.listeners;


import java.io.File;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.assignment.amazon.drivermanager.CustomWebDriverManager;
import com.assignment.amazon.exceptions.ExceptionHandler;
import com.assignment.amazon.utilities.ExtentManagerUtility;
import com.assignment.amazon.utilities.WebDriverUtilities;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.PickleStepTestStep;
import io.cucumber.plugin.event.TestCaseFinished;
import io.cucumber.plugin.event.TestCaseStarted;
import io.cucumber.plugin.event.TestStepFinished;
import io.cucumber.plugin.event.TestStepStarted;

/**
 * {@summary}
 * 
 * The CustomCucumberListener class
 * 
 * The listener interface for receiving cucumber events.
 * The class that is interested in processing a customCucumber
 * event implements cucumber provided ConcurrentEventListener interface. 
 * When the customCucumber event occurs, CustomCucumberListener object's 
 * appropriate method is invoked.
 *
 * @see CustomCucumberListener
 */
public class CustomCucumberListener implements ConcurrentEventListener {

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(CustomCucumberListener.class);
    
	@After(order=1)
	private void takeScreenshot(Scenario scenario) {
		if(scenario.isFailed()) {
			TakesScreenshot scrShot = (TakesScreenshot) CustomWebDriverManager.getDriver();
		       File srcFile = scrShot.getScreenshotAs(OutputType.FILE);
		       String fileName = "target/" + scenario.getName().toString() + UUID.randomUUID().toString() + ".png";
		       try {
		           FileUtils.copyFile(srcFile, new File(fileName));
		           ExtentManagerUtility.getExtentTest().log(Status.FAIL, "Test Failed: ", 
		                   MediaEntityBuilder.createScreenCaptureFromPath(fileName).build());
		           ExtentManagerUtility.flush();
		       } catch (Exception e) {
		           logger.error("Failed to capture screenshot", e);
		       }
		}
	}
    /**
     * Sets the event publisher.
     *
     * @param publisher - the new event publisher
     */
    @Override
    public synchronized void setEventPublisher(EventPublisher publisher) {
    	logger.debug("*******In setEventPublisher function*******");
    	try {
	        publisher.registerHandlerFor(TestCaseStarted.class, this::handleTestCaseStarted);
	        publisher.registerHandlerFor(TestStepStarted.class, this::handleTestStepStarted);
	        publisher.registerHandlerFor(TestStepFinished.class, this::handleTestStepFinished);
	        publisher.registerHandlerFor(TestCaseFinished.class, this::handleTestCaseFinished);
    		} catch(Exception e) {
    			ExceptionHandler.throwsException(e);
    			throw e;
    		}
    	}
    
    /**
     * Handle test case started event.
     *
     * @param event - the event
     */
    private synchronized void handleTestCaseStarted(TestCaseStarted event) {
	
			try {
	            logger.debug("******* In handleTestCaseStarted Publisher Event *******");
	            WebDriverUtilities.browserCounter();
	            
	            // Start logging in ExtentReports
	            ExtentTest test = ExtentManagerUtility.getExtentReports().createTest(event.getTestCase().getName());
	            ExtentManagerUtility.setExtentTest(test);
	            ExtentManagerUtility.getExtentTest().log(Status.INFO, "Browser Name: " + WebDriverUtilities.browserName.get().toUpperCase());
	        } catch (Exception e) {
	            ExceptionHandler.throwsException(e);
	            throw e;
	        }
    }

    /**
     * Handle test step started event.
     *
     * @param event - the event
     */
    private synchronized void handleTestStepStarted(TestStepStarted event) {
    	try {
    	logger.debug("*******In handleTestStepStarted Publisher Event*******");
    	if (event.getTestStep() instanceof PickleStepTestStep) {
            PickleStepTestStep testStep = (PickleStepTestStep) event.getTestStep();
            String stepText = testStep.getStep().getKeyword() + testStep.getStep().getText();
            ExtentManagerUtility.getExtentTest().log(Status.INFO, "Starting Step: " + stepText);
        }
    	} catch(Exception e) {
    		ExceptionHandler.throwsException(e);
    		throw e;
    	}
    }

    /**
     * Handle test step finished event.
     *
     * @param event - the event
     */
    private synchronized void handleTestStepFinished(TestStepFinished event) {
    	try {
	    	logger.debug("*******In handleTestStepFinished Publisher Event*******");
	    	if (event.getTestStep() instanceof PickleStepTestStep) {
	            PickleStepTestStep testStep = (PickleStepTestStep) event.getTestStep();
	            String stepText = testStep.getStep().getKeyword() + testStep.getStep().getText();
	            if (event.getResult().getStatus().is(io.cucumber.plugin.event.Status.PASSED)) {
	            	ExtentManagerUtility.getExtentTest().log(Status.PASS, "Step Passed: " + stepText);
	            } else if (event.getResult().getStatus().is(io.cucumber.plugin.event.Status.FAILED)) {
	            	ExtentManagerUtility.getExtentTest().log(Status.FAIL, "Step Failed: " + stepText);
	            } else {
	            	ExtentManagerUtility.getExtentTest().log(Status.SKIP, "Step Skipped: " + stepText);
	            }
	    	}
    	} catch(Exception e) {
    		ExceptionHandler.throwsException(e);
    	}
    }

    /**
     * Handle test case finished event.
     *
     * @param event - the event
     */
    private synchronized void handleTestCaseFinished(TestCaseFinished event) {
    	try {
    	logger.debug("*******In handleTestCaseFinished Publisher Event*******");
    	ExtentTest test = ExtentManagerUtility.getExtentTest();
        if (test != null) {
            String status = event.getResult().getStatus().toString();
        	switch (status) {
                case "PASSED":
                    test.log(Status.PASS, "Scenario Passed");
                    break;
                case "FAILED":
                    Throwable throwable = event.getResult().getError();
                    if (throwable != null) {
                        test.log(Status.FAIL, throwable);
                    } else {
                    	 test.log(Status.FAIL, "Scenario Failed");
                    }
                    break;
                case "SKIPPED":
                    test.log(Status.SKIP, "Scenario skipped");
                    break;
         }
        }
        logger.info("Web-Driver specific browser process removed successfully.!");
        ExtentManagerUtility.flush();
    	} catch(Exception e) {
    		ExceptionHandler.throwsException(e);
    		throw e;
    	}
    }
}