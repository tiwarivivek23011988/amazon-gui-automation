package com.assignment.amazon.drivermanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class EdgeDriverManager implements AbstractDriverFunction<WebDriver,EdgeOptions>{

	@Override
	public synchronized WebDriver getDriver() {
		// TODO Auto-generated method stub
		return new EdgeDriver(getCapabilities());
	}

	@Override
	public EdgeOptions getCapabilities() {
		// TODO Auto-generated method stub
		return DriverOptionsFactory.getInternetExplorerOptions();
	}

}
