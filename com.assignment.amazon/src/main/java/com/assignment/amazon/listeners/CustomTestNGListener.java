/**
 * @author Vivek Tiwari
 * 
 */
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
import org.testng.ITestContext;
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
import com.assignment.amazon.utilities.ExtentManagerUtility;
import com.assignment.amazon.utilities.FileSearchUtility;
import com.aventstack.extentreports.ExtentTest;

/**
 * {@summary}
 * 
 * The CustomTestNGListener Class
 * 
 * The listener interface for receiving testNG events.
 * The class that is interested in processing testNG
 * event implements testNG provided ITestListener interface. 
 * When the testNG event occurs, CustomTestNGListener object's 
 * appropriate method is invoked.
 *
 * @see CustomTestNGListener
 */
public class CustomTestNGListener implements ITestListener {
	
	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(CustomTestNGListener.class);
   
    /**
     * Creates the instance of extent reports
    

    /**
     * On test start.
     *
     * @param result - the result
     */
    @Override
    public void onTestStart(ITestResult result) {
    	logger.debug("*******Test Case started!*******");
    	logger.debug("*******In onTestStart function*******");
    	
    }

    /**
     * On test success.
     *
     * @param result - the result
     */
    @Override
    public void onTestSuccess(ITestResult result) {
    	logger.debug("*******In onTestSuccess function*******");
    }
    
    /**
     * On test failure.
     *
     * @param result - the result
     */
    @Override
    public void onTestFailure(ITestResult result) {
       logger.debug("*******In onTestFailure function*******");
    }

    /**
     * On test skipped.
     *
     * @param result - the result
     */
    @Override
    public void onTestSkipped(ITestResult result) {
      logger.debug("*******In onTestSkipped function*******");
    }
    
    /**
     * Extent report pre-processing function.
     */
    public synchronized static void extentReportPreProcessing() {
        logger.debug("*******In extentReportPreProcessing function*******");
    	
        ExtentManagerUtility.setExtentTest(ExtentManagerUtility.getExtentReports().createTest("Test Coverage Report"));

        try {
           
                /**
                 * Create a DocumentBuilderFactory instance
                 */
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                factory.setValidating(false); // Disable validation
                factory.setNamespaceAware(true);

                /**
                 * Create a DocumentBuilder instance
                 */
                DocumentBuilder builder = factory.newDocumentBuilder();

                /**
                 *  Set a custom EntityResolver to skip DTD validation
                 */
                builder.setEntityResolver(new EntityResolver() {
                    @Override
                    public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
                        // Ignore the DTD
                        return new InputSource(new StringReader(""));
                    }
                });

                /**
                 *  Parse the jacoco.xml file into a Document
                 */
                try (FileInputStream inputStream = new FileInputStream(FileSearchUtility.searchFile("temp", "jacoco.xml")))
                {
	                Document doc = builder.parse(inputStream);
	
	
	                /**
	                 * Now you have the Document object, you can work with it as needed.
	                 * 
	                 * For example, you can print the root element.
	                 */
	                logger.info("Root element: " + doc.getDocumentElement().getNodeName());
	
	            
		            /**
		             * Extract and log coverage information
		             */
		            extractAndLogCoverageInfo(doc, ExtentManagerUtility.getExtentTest());
		            
		            /**
		             * Close the input stream
		             */
		            inputStream.close();
                } catch (Exception e) {
                    ExceptionHandler.throwsException(e);
                }

        } catch (Exception e) {
        	ExceptionHandler.throwsException(e);
        }
    }
    
    @Override
    public void onFinish(ITestContext context) {
        logger.debug("******* Test Suite Finished *******");
    }
    
    /**
     * Extract and log coverage info.
     *
     * @param doc - the document
     * @param test - the test
     */
    private synchronized static void extractAndLogCoverageInfo(Document doc, ExtentTest test) {
        try {
        CustomTestNGListener.logger.debug("*******In extractAndLogCoverageInfo function*******");
    	NodeList packageList = doc.getElementsByTagName("package");
        for (int i = 0; i < packageList.getLength(); i++) {
            Node packageNode = packageList.item(i);
            if (packageNode.getNodeType() == Node.ELEMENT_NODE) {
                Element packageElement = (Element) packageNode;
                String packageName = packageElement.getAttribute("name");
                CustomTestNGListener.logger.info("Package Name: " + packageName);

                ExtentTest packageTest = test.createNode("<span style='color:white;'>Package: " + packageName + "</span>");

                NodeList classList = packageElement.getElementsByTagName("class");
                for (int j = 0; j < classList.getLength(); j++) {
                    Node classNode = classList.item(j);
                    if (classNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element classElement = (Element) classNode;
                        String className = classElement.getAttribute("name");
                        CustomTestNGListener.logger.info("Class Name: " + className);

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
        ExtentManagerUtility.flush();
        } catch(Exception e) {
        	ExceptionHandler.throwsException(e);
        	throw e;
        }
    }
 }
