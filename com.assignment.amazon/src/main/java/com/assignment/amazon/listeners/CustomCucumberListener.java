package com.assignment.amazon.listeners;


import com.assignment.amazon.utilities.DriverManager;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.PickleStepTestStep;
import io.cucumber.plugin.event.TestCaseFinished;
import io.cucumber.plugin.event.TestCaseStarted;
import io.cucumber.plugin.event.TestStepFinished;
import io.cucumber.plugin.event.TestStepStarted;


public class CustomCucumberListener implements ConcurrentEventListener {

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestCaseStarted.class, this::handleTestCaseStarted);
        publisher.registerHandlerFor(TestStepStarted.class, this::handleTestStepStarted);
        publisher.registerHandlerFor(TestStepFinished.class, this::handleTestStepFinished);
        publisher.registerHandlerFor(TestCaseFinished.class, this::handleTestCaseFinished);
    }
    
    private synchronized void handleTestCaseStarted(TestCaseStarted event) {
		
    	DriverManager.browserCounter();
    	
    	if(DriverManager.getCounter()==DriverManager.getScenarioCounter()) {
    		DriverManager.resetCounter();
    	}
    	
    	if (CustomTestNGListener.extent == null) {
    		 CustomTestNGListener.createInstance();
         }
         ExtentTest test = CustomTestNGListener.extent.createTest(event.getTestCase().getName());
         CustomTestNGListener.extentTest.set(test);
         CustomTestNGListener.extentTest.get().log(Status.INFO, "Browser Name: " + DriverManager.browserName.get().toUpperCase());
    }

    private void handleTestStepStarted(TestStepStarted event) {
    	if (event.getTestStep() instanceof PickleStepTestStep) {
            PickleStepTestStep testStep = (PickleStepTestStep) event.getTestStep();
            String stepText = testStep.getStep().getKeyword() + testStep.getStep().getText();
            CustomTestNGListener.extentTest.get().log(Status.INFO, "Starting step: " + stepText);
        }
    }

    private void handleTestStepFinished(TestStepFinished event) {
    	if (event.getTestStep() instanceof PickleStepTestStep) {
            PickleStepTestStep testStep = (PickleStepTestStep) event.getTestStep();
            String stepText = testStep.getStep().getKeyword() + testStep.getStep().getText();
            if (event.getResult().getStatus().is(io.cucumber.plugin.event.Status.PASSED)) {
            	CustomTestNGListener.extentTest.get().log(Status.PASS, "Step passed: " + stepText);
            } else if (event.getResult().getStatus().is(io.cucumber.plugin.event.Status.FAILED)) {
            	CustomTestNGListener.extentTest.get().log(Status.FAIL, "Step failed: " + stepText);
            } else {
            	CustomTestNGListener.extentTest.get().log(Status.SKIP, "Step skipped: " + stepText);
            }
        }
    }

    private void handleTestCaseFinished(TestCaseFinished event) {
    	if (event.getResult().getStatus().is(io.cucumber.plugin.event.Status.PASSED)) {
    		CustomTestNGListener.extentTest.get().log(Status.PASS, "Scenario passed");
        } else if (event.getResult().getStatus().is(io.cucumber.plugin.event.Status.FAILED)) {
        	CustomTestNGListener.extentTest.get().log(Status.FAIL, "Scenario failed");
        } else {
        	CustomTestNGListener.extentTest.get().log(Status.SKIP, "Scenario skipped");
        }
    }
}