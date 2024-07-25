package com.assignment.amazon.drivermanager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class EdgeDriverManager implements AbstractDriverFunction<WebDriver,EdgeOptions>{

	private static final Logger logger = LogManager.getLogger(EdgeDriverManager.class);

	@Override
	public synchronized WebDriver getDriver() {
		logger.info("<= In getDriver function of EdgeDriverManager class =>");
		return new EdgeDriver(getCapabilities());
	}

	@Override
	public EdgeOptions getCapabilities() {
		logger.info("<= In getCapabilities function of EdgeDriverManager class =>");
		return DriverOptionsFactory.getInternetExplorerOptions();
	}

}
