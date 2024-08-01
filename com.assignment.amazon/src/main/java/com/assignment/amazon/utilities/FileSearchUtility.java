/**
 * @author Vivek Tiwari
 * 
 */
package com.assignment.amazon.utilities;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.assignment.amazon.exceptions.ExceptionHandler;

/**
 * {@summary}
 * 
 * The FileSearchUtility Class
 * 
 * This is a utility class which is used to search a file
 * in a specific directory.
 * 
 * @see FileSearchUtility
 * 
 */
public class FileSearchUtility {

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(FileSearchUtility.class);
	
	/**
     * Searches for a file in the given directory and its sub-directories.
     * 
     * @param directoryPath - the path of the directory to search in
     * @param fileName - the name of the file to search for
     * @return - the path of the file if found, null otherwise
     */
    public static String searchFile(String directoryPath, String fileName) {
    	logger.debug("*******In searchFile function*******");
    	try {
        File directory = new File(directoryPath);

        /**
         * Check if the provided path is a directory
         */
        if (!directory.isDirectory()) {
            ExceptionHandler.throwsException(new IllegalArgumentException("The provided path is not a directory: " + directoryPath));
        }

        /**
         * Call the recursive search method
         */
        return searchFileRecursive(directory, fileName);
    	} catch(Exception e) {
    		ExceptionHandler.throwsException(e);
    		throw e;
    	}
    }

    /**
     * Recursive method to search for a file in the given directory and its sub-directories.
     * 
     * @param directory - the directory to search in
     * @param fileName - the name of the file to search for
     * @return the path of the file if found, null otherwise
     */
    private static String searchFileRecursive(File directory, String fileName) {
    	try {
	    	logger.debug("*******In searchFileRecursive function*******");
	        /**
	         *  Get all files and sub-directories in the current directory
	         */
	        File[] files = directory.listFiles();
	
	        if (files != null) {
	            for (File file : files) {
	                /**
	                 *  If the current file matches the search file name, return the file path
	                 */
	                if (file.isFile() && file.getName().equals(fileName)) {
	                    return file.getAbsolutePath();
	                }
	                /**
	                 *  If the current file is a directory, recursively search within it
	                 */
	                if (file.isDirectory()) {
	                    String result = searchFileRecursive(file, fileName);
	                    if (result != null) {
	                        return result;
	                    }
	                }
	            }
	        }
    	} catch(Exception e) {
    		ExceptionHandler.throwsException(e);
    		throw e;
    	}
        /**
         *  If the file is not found in the current directory and its sub-directories, return null
         */
        return null;
    }
}
