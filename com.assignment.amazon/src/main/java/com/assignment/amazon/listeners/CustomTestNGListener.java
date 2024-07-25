package com.assignment.amazon.listeners;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.assignment.amazon.exceptions.ExceptionHandler;
import com.assignment.amazon.utilities.FileSearchUtility;
import com.assignment.amazon.utilities.RandomUtilities;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class CustomTestNGListener implements ITestListener {
	private static final Logger logger = LogManager.getLogger(CustomTestNGListener.class);
    public static ExtentReports extent;
    private static ExtentSparkReporter sparkReporter;
    static volatile ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    
    public static synchronized ExtentReports createInstance() {
    	logger.info("<= In createInstance function =>");
    	try {
    	sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "/test-output/ExtentReport.html");
        sparkReporter.config().setDocumentTitle("Automation Report");
        sparkReporter.config().setReportName("Regression Testing");
        sparkReporter.config().setTheme(Theme.DARK);
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Host Name", RandomUtilities.getHostName());
        extent.setSystemInfo("Environment", RandomUtilities.getOsName());
        extent.setSystemInfo("User Name", RandomUtilities.getUserName());
        extent.setSystemInfo("OS Version", RandomUtilities.getOsVersion());
        extent.setSystemInfo("OS Architecture", RandomUtilities.getOsArchitecture());
    	} catch(Exception e) {
    		ExceptionHandler.throwsException(e);
    	}
        return extent;
    }
    
    public synchronized static ExtentReports getInstance() {
    	logger.info("<= In getInstance function =>");
        return extent;
    }

    @Override
    public synchronized void onTestStart(ITestResult result) {
    	logger.info("Test Case started!");
    	logger.info("<= In onTestStart function =>");
    }

    @Override
    public synchronized void onTestSuccess(ITestResult result) {
    	logger.info("<= In onTestSuccess function =>");
       extentTest.get().log(Status.PASS, "Test Passed");
    }
    
    @Override
    public synchronized void onTestFailure(ITestResult result) {
       logger.info("<= In onTestFailure function =>");
       extentTest.get().log(Status.FAIL, "Test Failed");
       extentTest.get().log(Status.FAIL, result.getThrowable());
    }

    @Override
    public synchronized void onTestSkipped(ITestResult result) {
      logger.info("<= In onTestSkipped function =>");
      extentTest.get().log(Status.SKIP, "Test Skipped");
    }
    
    public static synchronized void extentReportPreProcessing() {
        logger.info("<= In extentReportPreProcessing function =>");
    	
    	ExtentTest test = extent.createTest("Test Coverage Report");

        try {
           
                // Create a DocumentBuilderFactory instance
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                factory.setValidating(false); // Disable validation
                factory.setNamespaceAware(true);

                // Create a DocumentBuilder instance
                DocumentBuilder builder = factory.newDocumentBuilder();

                // Set a custom EntityResolver to skip DTD validation
                builder.setEntityResolver(new EntityResolver() {
                    @Override
                    public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
                        // Ignore the DTD
                        return new InputSource(new StringReader(""));
                    }
                });

                // Parse the jacoco.xml file into a Document
                FileInputStream inputStream = new FileInputStream(FileSearchUtility.searchFile("temp", "jacoco.xml"));
                Document doc = builder.parse(inputStream);


                // Now you have the Document object, you can work with it as needed
                // For example, you can print the root element
                System.out.println("Root element: " + doc.getDocumentElement().getNodeName());

            
            // Extract and log coverage information
            extractAndLogCoverageInfo(doc, test);
            
            // Close the input stream
            inputStream.close();

        } catch (Exception e) {
        	ExceptionHandler.throwsException(e);
        }
        
        extent.flush();
    }
    
    private synchronized static void extractAndLogCoverageInfo(Document doc, ExtentTest test) {
        try {
        CustomTestNGListener.logger.info("<= In extractAndLogCoverageInfo function =>");
    	NodeList packageList = doc.getElementsByTagName("package");
        for (int i = 0; i < packageList.getLength(); i++) {
            Node packageNode = packageList.item(i);
            if (packageNode.getNodeType() == Node.ELEMENT_NODE) {
                Element packageElement = (Element) packageNode;
                String packageName = packageElement.getAttribute("name");
                System.out.println("Package Name: " + packageName);

                ExtentTest packageTest = test.createNode("<span style='color:white;'>Package: " + packageName + "</span>");

                NodeList classList = packageElement.getElementsByTagName("class");
                for (int j = 0; j < classList.getLength(); j++) {
                    Node classNode = classList.item(j);
                    if (classNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element classElement = (Element) classNode;
                        String className = classElement.getAttribute("name");
                        System.out.println("Class Name: " + className);

                        Map<String, String> coverageDetails = new HashMap<>();
                        NodeList counterList = classElement.getElementsByTagName("counter");
                        for (int k = 0; k < counterList.getLength(); k++) {
                            Node counterNode = counterList.item(k);
                            if (counterNode.getNodeType() == Node.ELEMENT_NODE) {
                                Element counterElement = (Element) counterNode;
                                String counterType = counterElement.getAttribute("type");
                                int missed = Integer.parseInt(counterElement.getAttribute("missed"));
                                int covered = Integer.parseInt(counterElement.getAttribute("covered"));
                                double coveragePercentage = (double) covered / (missed + covered) * 100;
                                String coverageInfo = String.format("%.2f%% (Covered: %d, Missed: %d)", coveragePercentage, covered, missed);
                                coverageDetails.put(counterType, coverageInfo);
                            }
                        }

                        String formattedCoverage = String.format(
                                "<span style='color:white;'>Class: %s</span><br>" +
                                "INSTRUCTION Coverage: %s<br>" +
                                "BRANCH Coverage: %s<br>" +
                                "LINE Coverage: %s<br>" +
                                "COMPLEXITY Coverage: %s<br>" +
                                "METHOD Coverage: %s<br>" +
                                "CLASS Coverage: %s",
                                className,
                                coverageDetails.getOrDefault("INSTRUCTION", "N/A"),
                                coverageDetails.getOrDefault("BRANCH", "N/A"),
                                coverageDetails.getOrDefault("LINE", "N/A"),
                                coverageDetails.getOrDefault("COMPLEXITY", "N/A"),
                                coverageDetails.getOrDefault("METHOD", "N/A"),
                                coverageDetails.getOrDefault("CLASS", "N/A")
                        );

                        packageTest.info(formattedCoverage);
                    }
                }
            }
        }
        } catch(Exception e) {
        	ExceptionHandler.throwsException(e);
        }
    }
 }
