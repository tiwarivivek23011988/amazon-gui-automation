/**
 * @author Vivek Tiwari
 * 
 */

package com.assignment.amazon.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.assignment.amazon.exceptions.ExceptionHandler;


/**
 * {@summary}
 * 
 * The ProcessKillerUtility Class
 * 
 * This class is used to kill browser related processes which remains
 * dangling in the background, even after browser driver session gets
 * over/terminated.
 * 
 * @see ProcessManagementUtility
 * 
 */

public class ProcessManagementUtility {

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(ProcessManagementUtility.class);
	
	/**
	 * Kills dangling browser processes.
	 *
	 * @param browserName - The Browser Name
	 * 
	 */
	
	public static void killDanglingBrowserProcesses(String browserName) {
		try {
			
			String osName = RandomUtilities.getOsName();
			String command=null, subCommand=null;
			if(osName.toLowerCase().contains("windows")) {
				if(browserName.equalsIgnoreCase("chrome")) {
					subCommand="chrome.exe";
				} else if(browserName.equalsIgnoreCase("firefox")) {
					subCommand="firefox.exe";
				} else if(browserName.equalsIgnoreCase("edge")) {
					subCommand="msedge.exe";
				}
				command="taskkill /F /IM "+subCommand;
				
			} else {
				command="pkill -f "+browserName;
			}
            Runtime.getRuntime().exec(command);
        
		} catch (IOException e) {
			
            ExceptionHandler.throwsException(e);
            
        }
	}

	/**
	 * Gets the driver process name.
	 *
	 * @param browserName - the browser name
	 * @return the driver process name if exists
	 */
	public static String getDriverProcessName(String browserName) {
		String os = System.getProperty("os.name").toLowerCase();
        ProcessBuilder processBuilder;
        Process process = null;
        BufferedReader reader = null;

        try {
            if (os.contains("win")) {
                /** Windows command to get process list */
                processBuilder = new ProcessBuilder("cmd.exe", "/c", "tasklist");
            } else {
                /** Unix-like systems command to get process list */
                processBuilder = new ProcessBuilder("ps", "aux");
            }
            
            process = processBuilder.start();
            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(browserName)) {
                    /** Check if the process name is part of the line */
                    return browserName;
                }
            }
        } catch (IOException e) {
            /** Handle exception or log error */
            ExceptionHandler.throwsException(e);
        } finally {
            /** Close the BufferedReader and process to free resources */
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    ExceptionHandler.throwsException(e);
                }
            }
            if (process != null) {
                process.destroy();
            }
        }
        return null;
    }
	   
	/**
	 * Gets the driver PID.
	 *
	 * @param processName - the process name
	 * @return the driver Process ID
	 */
	public static List<Long> getDriverPID(String processName) {
		
		String os = System.getProperty("os.name").toLowerCase();
		
        logger.info("Process Name Is => " + processName);
        
        List<Long> processIdList = new ArrayList<>();
        
        try {
            if (os.contains("win")) {
                /** Windows command to get process list */
                ProcessBuilder processBuilder = new ProcessBuilder(
                    "cmd.exe", "/c", 
                    "tasklist", 
                    "/FI", "SESSIONNAME eq Console",
                    "/FO", "csv","|",
                    "findstr","/i",
                    processName
                );
                
                Process process = processBuilder.start();
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
           
                String line;
                
                while ((line = reader.readLine()) != null) {
                    logger.debug("Read line Is => " + line.trim());
                    if (line.contains(processName)) {
                        /** The PID is typically the second item in the output line */
                        String[] tokens = line.trim().split("\",\"");
                        if (tokens.length > 1) {
                            /** Note: CSV fields might be quoted, so handle quotes appropriately */
                            processIdList.add(Long.parseLong(tokens[1].replaceAll("[^\\d]", "")));
               
                        }
                    }
                }
            } else {
                /** Unix-like systems command to get process list */
                ProcessBuilder processBuilder = new ProcessBuilder("pgrep", "-f", processName+"driver");
                Process process = processBuilder.start();
                
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                if ((line = reader.readLine()) != null) {
                	logger.debug("Read line Is => " + line.trim());
                    processIdList.add(Long.parseLong(line.trim()));
                }
            }
            return processIdList;
        } catch (IOException e) {
            logger.error("Error occurred while trying to find process ID: " + e.getMessage());
        } catch (NumberFormatException e) {
            logger.error("Error occurred while parsing process ID: " + e.getMessage());
        }

        return null;
   
    }
	
	
	 /**
 	 * Gets the browser process PID.
 	 *
 	 * @param driver - the browser driver
 	 * @param browserName - the browser name
 	 * @return the browser process PID
 	 */
 	public static List<Long> getBrowserProcessPID(WebDriver driver, String browserName) {
	        try {
	        	if(browserName.equalsIgnoreCase("edge")) {
	        		browserName="ms"+browserName;
	        	} else if(browserName.equalsIgnoreCase("firefox")) {
	        		browserName="geckodriver";
	        	}
	        	return getDriverPID(getDriverProcessName(browserName));
	        } catch (Exception e) {
	           ExceptionHandler.throwsException(e);
	        }
	        return null;
	    }
	 
	 	/**
	 	 * Kills browser process.
	 	 *
	 	 * @param pid - the process id of the browser driver
	 	 * 
	 	 */
	 	public static void killProcess(long pid) {
	        try {
	            String osName = System.getProperty("os.name").toLowerCase();
	            
	            ProcessBuilder processBuilder;

	            if (osName.contains("win")) {
	                processBuilder = new ProcessBuilder("taskkill", "/PID", String.valueOf(pid), "/F");
	            } else {
	                processBuilder = new ProcessBuilder("kill", "-9", String.valueOf(pid));
	            }

	            Process process = processBuilder.start();
	            process.waitFor();

	            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
	                String line;
	                while ((line = reader.readLine()) != null) {
	                    logger.info(line);
	                }
	            }
	            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
	                String line;
	                while ((line = reader.readLine()) != null) {
	                    logger.error(line);
	                }
	            }
	        } catch (Exception e) {
	            ExceptionHandler.throwsException(e);
	        }
	    }
	 	
	 	/**
	 	 * Gets the child process IDs.
	 	 *
	 	 * @param parentProcessIds - the parent process ID's of the browser driver
	 	 * @return the child process ID's of the browser driver
	 	 * 
	 	 */
	 	public static List<Long> getChildProcessIds(List<Long> parentProcessIds) {
	 		
	 		String osName = System.getProperty("os.name").toLowerCase();
	 		
	 		List<Long> childProcessIds = new ArrayList<>();
	 		
	 		ProcessBuilder processBuilder;
	 		try {
		 		for(Long pid: parentProcessIds) {
		 			
		 			if(osName.contains("win")) {
		 			
		 				processBuilder = new ProcessBuilder("wmic", "process", "where", "ParentProcessId=" + pid, "get", "ProcessId");
		 			
		 			} else {
		 				
		 				processBuilder = new ProcessBuilder("ps", "--ppid", String.valueOf(pid), "-o", "pid=");
		 			}
		 			
			        try {
			      
			            Process process = processBuilder.start();
		
			            /** Read the output from the command */
			            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
			                
			            	String line;
			            	 boolean isFirstLine = true; /** Flag to skip header line */
			                while ((line = reader.readLine()) != null) {
			                	 line = line.trim(); /** Trim any extra whitespace */
			                     if (isFirstLine) {
			                        isFirstLine = false; /** Skip the header line */
			                        continue;
			                    }
			                	logger.info("Child Process Details Inside getChildProcessIds Is => "+line);
			                	if (!line.trim().isEmpty()) {
			                		childProcessIds.add(Long.parseLong(line));
			                    }
			                }
			            }
		
			            /** Wait for the process to complete */
			            int exitCode = process.waitFor();
			            
			            logger.info("Process exited with code: " + exitCode);
		
			        } catch (Exception e) {
			           continue;
			        }
		 		}
	 		} catch(Exception e) {
	 			 ExceptionHandler.throwsException(e);
	 		}
	 		return childProcessIds;
	 	}
}
