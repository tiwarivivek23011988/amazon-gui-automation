package com.assignment.amazon.drivermanager;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.assignment.amazon.utilities.DriverManager;

public class RemoteDriverManager implements AbstractDriverFunction<WebDriver,MutableCapabilities>{
	
	@Override
	public synchronized WebDriver getDriver() {
		// TODO Auto-generated method stub
		try {
			return new RemoteWebDriver(new URL((String) DriverManager.hashMap.get("gridUrl")),getCapabilities());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public MutableCapabilities getCapabilities() {
		// TODO Auto-generated method stub
		return DriverOptionsFactory.getOptions(DriverManager.browserName.get());
	}

}
