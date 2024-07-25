package com.assignment.amazon.utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonParser {

	public HashMap<String, ?> parseJson(String filePath) {
		
		Path path = Paths.get(filePath);
		HashMap<String,?> map = null;
		try {
			String jsonStr = Files.readString(path);
			ObjectMapper obj = new ObjectMapper();
			map = obj.readValue(jsonStr, new TypeReference<HashMap<String, ?>>(){});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
}
