/**
 * @author Vivek Tiwari
 * 
 */
package com.assignment.amazon.utilities;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
 * @see ParallelCounter
 * 
 */
public class ParallelCounter {
	
	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(ParallelCounter.class);

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
    	logger.info("<= In incrementCounter function of ParallelCounter class =>");
        return counter.incrementAndGet();
    }
    
    /**
     * Decrement counter.
     *
     * @return integer value
     */
    public static synchronized int decrementCounter() {
    	logger.info("<= In decrementCounter function of ParallelCounter class =>");
        return counter.decrementAndGet();
    }

    /**
     * Gets the counter.
     *
     * @return the current counter value
     */
    public static synchronized int getCounter() {
    	logger.info("<= In getCounter function of ParallelCounter class =>");
        return counter.get();
    }

	/**
	 * Reset counter value
	 */
	public static synchronized void resetCounter() {
    	logger.info("<= In resetCounter function of ParallelCounter class =>");
		counter.set(0);
	}
}
