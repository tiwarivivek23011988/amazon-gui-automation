/**
 * @author Vivek Tiwari
 * 
 */

package com.assignment.amazon.runner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.DataProvider;

import com.assignment.amazon.exceptions.ExceptionHandler;
import com.assignment.amazon.utilities.ParallelCounterUtility;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

/**
 * {@summary}
 * 
 * The TestRunner Class
 * 
 * This is a Cucumber-TestNG test runner. This class extends testNG
 * provided class @AbstractTestNGCucumberTests
 * 
 * @see TestRunner
 * 
 */

@CucumberOptions(features="src/test/resources/com/assignment/amazon/features",
glue={"com.assignment.amazon.stepdefinitions","com.assignment.amazon.configuration",
		"com.assignment.amazon.listeners"},monochrome = true, dryRun=false,
plugin = {"pretty", "json:target/cucumber-report.json","com.assignment.amazon.listeners.CustomCucumberListener"})
public class TestRunner extends AbstractTestNGCucumberTests {
	
	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(TestRunner.class);
	
	/**
	 * This function helps run cucumber scenarios in parallel
	 * It overrides scenarios function
	 * 
	 * @return the object[][]
	 * 
	 */
	@Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
		logger.debug("*******In scenarios function*******");
		try {
			int numOfScenarios=super.scenarios().length;
			logger.info("Number of Scenarios Are:" +numOfScenarios);
			ParallelCounterUtility.scenarioCounter.set(numOfScenarios);
	        return super.scenarios();
		} catch(Exception e) {
			ExceptionHandler.throwsException(e);
			throw e;
		}
    }
	
}
