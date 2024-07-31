/**
 * @author Vivek Tiwari
 * 
 */

package com.assignment.amazon.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * {@summary}
 * 
 * The TextProcessingUtility Class
 * 
 * This class is used to filter web-element product names
 * by creating relevant match with provided text. This class
 * will also be used to process any kind of texts as needed
 * 
 * @see - TextProcessingUtility
 * 
 */
public class TextProcessingUtility {

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(TextProcessingUtility.class);
	
	/**
	 * Return extracted product text.
	 *
	 * @param subProductNameFromWebElement - the sub product name from web element
	 * 
	 * @return the processed product string
	 * 
	 */
	public static String returnExtractedSubProductText(String subProductNameFromWebElement) {
		
		logger.debug("*****In returnExtractedSubProductText function*****");
		
		String[] strArray = subProductNameFromWebElement.split("—");
		
		StringBuilder sb = new StringBuilder();
		
		for(String strTemp:strArray) {
			if(strTemp.contains("(") && strTemp.contains(",")) {
				sb.append(strTemp.substring(strTemp.indexOf('('), strTemp.indexOf(','))).append(')');
			} else {
				sb.append(strTemp);
			}
		}
		
		return sb.toString();
		
	}

}
