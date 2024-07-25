package com.assignment.amazon.utilities;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ParallelCounter {
	
	private static final Logger logger = LogManager.getLogger(ParallelCounter.class);

    private static final AtomicInteger counter = new AtomicInteger(0);
    public static final AtomicInteger scenarioCounter = new AtomicInteger(0);
    
    public static synchronized int incrementCounter() {
    	logger.info("<= In incrementCounter function of ParallelCounter class =>");
        return counter.incrementAndGet();
    }
    
    public static synchronized int decrementCounter() {
    	logger.info("<= In decrementCounter function of ParallelCounter class =>");
        return counter.decrementAndGet();
    }

    public static synchronized int getCounter() {
    	logger.info("<= In getCounter function of ParallelCounter class =>");
        return counter.get();
    }

	public static synchronized void resetCounter() {
    	logger.info("<= In resetCounter function of ParallelCounter class =>");
		counter.set(0);
	}
}
