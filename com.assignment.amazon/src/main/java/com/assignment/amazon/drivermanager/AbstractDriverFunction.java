/**
 * @author Vivek Tiwari
 * 
 */

package com.assignment.amazon.drivermanager;

/**
 * {@summary}
 * 
 * The AbstractDriverFunction Interface
 * 
 * This interface is  used as an abstraction
 * to create different browser managers
 *
 * @see AbstractDriverFunction
 *
 * @param <Y> the generic type
 * @param <T> the generic type
 * 
 */
public interface AbstractDriverFunction<Y,T> {
	 
 	/**
 	 * Gets the driver.
 	 *
 	 * @return the driver
 	 * 
 	 */
 	Y getDriver();
	 
 	/**
 	 * Gets the capabilities.
 	 *
 	 * @return the capabilities
 	 * 
 	 */
 	T getCapabilities();
}
