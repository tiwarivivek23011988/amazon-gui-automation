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

public class JsonParser {
	
	private static final Logger logger = LogManager.getLogger(JsonParser.class);
	
	public HashMap<String, ?> parseJson(String filePath) {
		HashMap<String,?> map = null;
		try {
			logger.info("<= In parseJson function =>");
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
