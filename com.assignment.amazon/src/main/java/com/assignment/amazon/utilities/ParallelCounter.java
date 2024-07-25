package com.assignment.amazon.utilities;

import java.util.concurrent.atomic.AtomicInteger;

public class ParallelCounter {
    private static final AtomicInteger counter = new AtomicInteger(0);
    public static final AtomicInteger scenarioCounter = new AtomicInteger(0);
    
    public static synchronized int incrementCounter() {
        return counter.incrementAndGet();
    }
    
    public static synchronized int decrementCounter() {
        return counter.decrementAndGet();
    }

    public static synchronized int getCounter() {
        return counter.get();
    }

	public static synchronized void resetCounter() {
		// TODO Auto-generated method stub
		counter.set(0);
		
	}
}
