package com.assignment.amazon.drivermanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeDriverManager implements AbstractDriverFunction<WebDriver,ChromeOptions>{

	@Override
	public synchronized WebDriver getDriver() {
		return new ChromeDriver(getCapabilities());
	}

	@Override
	public ChromeOptions getCapabilities() {
		// TODO Auto-generated method stub
		return DriverOptionsFactory.getChromeOptions();
	}

}
