/**
 * @author Vivek Tiwari
 * 
 */
package com.assignment.amazon.listeners;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.assignment.amazon.drivermanager.CustomWebDriverManager;
import com.assignment.amazon.exceptions.ExceptionHandler;
import com.assignment.amazon.utilities.WebDriverUtilities;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

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

    /**
     * Sets the event publisher.
     *
     * @param publisher - the new event publisher
     */
    @Override
    public synchronized void setEventPublisher(EventPublisher publisher) {
    	logger.info("<= In setEventPublisher function =>");
        publisher.registerHandlerFor(TestCaseStarted.class, this::handleTestCaseStarted);
        publisher.registerHandlerFor(TestStepStarted.class, this::handleTestStepStarted);
        publisher.registerHandlerFor(TestStepFinished.class, this::handleTestStepFinished);
        publisher.registerHandlerFor(TestCaseFinished.class, this::handleTestCaseFinished);
    }
    
    /**
     * Handle test case started event.
     *
     * @param event - the event
     */
    private synchronized void handleTestCaseStarted(TestCaseStarted event) {
		try {
		logger.info("<= In handleTestCaseStarted Publisher Event =>");
    	WebDriverUtilities.browserCounter();
    	
    	if(WebDriverUtilities.getCounter()==WebDriverUtilities.browserNames.size()) {
    		WebDriverUtilities.resetCounter();
    	}
    	
    	if (CustomTestNGListener.extent == null) {
    		 CustomTestNGListener.createInstance();
         }
         ExtentTest test = CustomTestNGListener.extent.createTest(event.getTestCase().getName());
         CustomTestNGListener.extentTest.set(test);
         CustomTestNGListener.extentTest.get().log(Status.INFO, "Browser Name: " + WebDriverUtilities.browserName.get().toUpperCase());
		} catch(Exception e) {
			ExceptionHandler.throwsException(e);
		}
    }

    /**
     * Handle test step started event.
     *
     * @param event - the event
     */
    private synchronized void handleTestStepStarted(TestStepStarted event) {
    	try {
    	logger.info("<= In handleTestStepStarted Publisher Event =>");
    	if (event.getTestStep() instanceof PickleStepTestStep) {
            PickleStepTestStep testStep = (PickleStepTestStep) event.getTestStep();
            String stepText = testStep.getStep().getKeyword() + testStep.getStep().getText();
            CustomTestNGListener.extentTest.get().log(Status.INFO, "Starting step: " + stepText);
        }
    	} catch(Exception e) {
    		ExceptionHandler.throwsException(e);
    	}
    }

    /**
     * Handle test step finished event.
     *
     * @param event - the event
     */
    private synchronized void handleTestStepFinished(TestStepFinished event) {
    	try {
    	logger.info("<= In handleTestStepFinished Publisher Event =>");
    	if (event.getTestStep() instanceof PickleStepTestStep) {
            PickleStepTestStep testStep = (PickleStepTestStep) event.getTestStep();
            String stepText = testStep.getStep().getKeyword() + testStep.getStep().getText();
            if (event.getResult().getStatus().is(io.cucumber.plugin.event.Status.PASSED)) {
            	CustomTestNGListener.extentTest.get().log(Status.PASS, "Step passed: " + stepText);
            } else if (event.getResult().getStatus().is(io.cucumber.plugin.event.Status.FAILED)) {
            	System.out.println("<= In handleTestStepFinished Publisher Event =>");
                if(CustomWebDriverManager.getDriver() != null) {
                	TakesScreenshot scrShot =((TakesScreenshot)CustomWebDriverManager.getDriver());
                	String SrcFile=scrShot.getScreenshotAs(OutputType.BASE64);
                	CustomTestNGListener.extentTest.get().log(Status.FAIL, "Step failed: " + stepText, 
                			MediaEntityBuilder.createScreenCaptureFromPath(SrcFile).build());
                }
            } else {
            	CustomTestNGListener.extentTest.get().log(Status.SKIP, "Step skipped: " + stepText);
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
    	logger.info("<= In handleTestCaseFinished Publisher Event =>");
    	if (event.getResult().getStatus().is(io.cucumber.plugin.event.Status.PASSED)) {
    		CustomTestNGListener.extentTest.get().log(Status.PASS, "Scenario passed");
        } else if (event.getResult().getStatus().is(io.cucumber.plugin.event.Status.FAILED)) {
        	CustomTestNGListener.extentTest.get().log(Status.FAIL, "Scenario failed");
        } else {
        	CustomTestNGListener.extentTest.get().log(Status.SKIP, "Scenario skipped");
        }
    	} catch(Exception e) {
    		ExceptionHandler.throwsException(e);
    	}
    }
}