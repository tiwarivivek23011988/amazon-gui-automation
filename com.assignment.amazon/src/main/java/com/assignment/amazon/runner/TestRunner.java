package com.assignment.amazon.runner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.DataProvider;

import com.assignment.amazon.utilities.ParallelCounter;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features="src/test/resources/com/assignment/amazon/features",
glue={"com.assignment.amazon.stepdefinitions","com.assignment.amazon.configuration",
		"com.assignment.amazon.listeners"},monochrome = true, dryRun=false,
plugin = {"pretty", "json:target/cucumber-report.json","com.assignment.amazon.listeners.CustomCucumberListener"})
public class TestRunner extends AbstractTestNGCucumberTests {
	private static final Logger logger = LogManager.getLogger(TestRunner.class);
	@Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
		int numOfScenarios=super.scenarios().length;
		System.out.println("Number of Scenarios Are:" +numOfScenarios);
		ParallelCounter.scenarioCounter.set(numOfScenarios);
        return super.scenarios();
    }
	
}
