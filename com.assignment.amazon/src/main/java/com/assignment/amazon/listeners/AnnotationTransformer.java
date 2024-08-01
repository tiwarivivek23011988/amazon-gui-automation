/**
 * @author Vivek Tiwari
 * 
 */

package com.assignment.amazon.listeners;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IAnnotationTransformer;
import org.testng.IRetryAnalyzer;
import org.testng.annotations.ITestAnnotation;

import com.assignment.amazon.exceptions.ExceptionHandler;

/**
 * {@summary}
 * 
 * The AnnotationTransformer Class
 * 
 * This is a custom implementation of TestNG provided listener IAnotationTransformer
 * 
 * @see AnnotationTransformer
 * 
 */
public class AnnotationTransformer implements IAnnotationTransformer {
	
	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(AnnotationTransformer.class);
    
    /**
     * Transform - Overridden method.
     *
     * @param annotation - the annotation
     * @param testClass - the test class
     * @param testConstructor - the test constructor
     * @param testMethod - the test method
     */
    @SuppressWarnings("rawtypes")
	@Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
    	logger.debug("*******In transform function of AnnotationTransformer class*******");
        Class<? extends IRetryAnalyzer> retry = annotation.getRetryAnalyzerClass();
        try {
	        if (retry == null) {
	            annotation.setRetryAnalyzer((Class<? extends IRetryAnalyzer>) CustomRetryAnalyser.class);
	        }
        } catch(Exception e) {
        	ExceptionHandler.throwsException(e);
        	throw e;
        }
    }
}
