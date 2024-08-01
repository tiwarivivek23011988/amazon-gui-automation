/**
 * @author Vivek Tiwari
 *  
 */
package com.assignment.amazon.utilities;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.assignment.amazon.exceptions.ExceptionHandler;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * {@summary}
 * 
 * The JsonParser Class
 * 
 * This is a utility class used to parse any json data or
 * configuration file.
 * 
 * @see JsonParserUtility
 */
public class JsonParserUtility {
	
	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(JsonParserUtility.class);
	
	/**
	 * Parses the json data/configuration file.
	 *
	 * @param filePath - the file path
	 * @return the hash map
	 */
	public HashMap<String, ?> parseJson(String filePath) {
		HashMap<String,?> map = null;
		try {
			logger.debug("*******In parseJson function*******");
			Path path = Paths.get(filePath);
			String jsonStr = Files.readString(path);
			ObjectMapper obj = new ObjectMapper();
			map = obj.readValue(jsonStr, new TypeReference<HashMap<String, ?>>(){});
		} catch(Exception e) {
			ExceptionHandler.throwsException(e);
		}
		return map;
	}
}
