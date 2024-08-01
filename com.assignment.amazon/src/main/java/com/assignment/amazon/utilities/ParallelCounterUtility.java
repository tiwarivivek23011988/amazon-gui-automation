/**
 * @author Vivek Tiwari
 * 
 */
package com.assignment.amazon.utilities;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.assignment.amazon.exceptions.ExceptionHandler;

/**
 * {@summary}
 * 
 * The ParallelCounter Class
 * 
 * This class is a utility class which helps maintain browser
 * rotations for cucumber scenarios execution at runtime.
 * 
 * This class uses thread local implementation of counter
 * and scenario counter.
 * 
 * @see ParallelCounterUtility
 * 
 */
public class ParallelCounterUtility {
	
	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(ParallelCounterUtility.class);

    /** The Constant counter. */
    private static final AtomicInteger counter = new AtomicInteger(0);
    
    /** The Constant scenarioCounter. */
    public static final AtomicInteger scenarioCounter = new AtomicInteger(0);
    
    /**
     * Increment counter.
     *
     * @return integer value
     */
    public static synchronized int incrementCounter() {
    	logger.debug("*******In incrementCounter function of ParallelCounter class*******");
    	try {
    		return counter.incrementAndGet();
    	} catch(Exception e) {
    		ExceptionHandler.throwsException(e);
    		throw e;
    	}
    }
    
    /**
     * Decrement counter.
     *
     * @return integer value
     */
    public static synchronized int decrementCounter() {
    	logger.debug("*******In decrementCounter function of ParallelCounter class*******");
    	try {
    		return counter.decrementAndGet();
    	}catch(Exception e) {
    		ExceptionHandler.throwsException(e);
    		throw e;
    	}
    }

    /**
     * Gets the counter.
     *
     * @return the current counter value
     */
    public static synchronized int getCounter() {
    	logger.debug("*******In getCounter function of ParallelCounter class*******");
    	try {
    		return counter.get();
    	} catch(Exception e) {
    		ExceptionHandler.throwsException(e);
    		throw e;
    	}
    }

	/**
	 * Reset counter value
	 */
	public static synchronized void resetCounter() {
    	logger.debug("*******In resetCounter function of ParallelCounter class*******");
    	try {
    		counter.set(0);
    	} catch(Exception e) {
    		ExceptionHandler.throwsException(e);
    		throw e;
    	}
	}
}
