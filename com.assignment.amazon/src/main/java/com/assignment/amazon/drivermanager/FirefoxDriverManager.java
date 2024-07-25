package com.assignment.amazon.drivermanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class FirefoxDriverManager implements AbstractDriverFunction<WebDriver,FirefoxOptions> {

	@Override
	public synchronized WebDriver getDriver() {
		// TODO Auto-generated method stub
		return new FirefoxDriver(getCapabilities());
	}

	@Override
	public FirefoxOptions getCapabilities() {
		// TODO Auto-generated method stub
		return DriverOptionsFactory.getFirefoxOptions();
	}

}
