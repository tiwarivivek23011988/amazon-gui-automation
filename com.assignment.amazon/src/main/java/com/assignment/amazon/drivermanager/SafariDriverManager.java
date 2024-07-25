package com.assignment.amazon.drivermanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

public class SafariDriverManager implements AbstractDriverFunction<WebDriver,SafariOptions>{

	@Override
	public synchronized WebDriver getDriver() {
		// TODO Auto-generated method stub
		return new SafariDriver(getCapabilities());
	}

	@Override
	public SafariOptions getCapabilities() {
		// TODO Auto-generated method stub
		return DriverOptionsFactory.getSafariOptions();
	}

}
