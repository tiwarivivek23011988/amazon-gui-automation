Consent Notice: This automation framework implementation is a sole proprietary of <Mr. Vivek Tiwari>
(Who is the Creator and Maintainer of this automation framework). Hence, prior written consent must 
be taken before using this framework for personal or organizational needs.

*****Automation Framework related specifics*****

<Pre-Requisites>

	1. Prior knowledge or understanding in Java. Preferably Java 8+.
	2. Hands on experience in creating/maintaining automation frameworks.
	3. Knowledge on dependencies and plug-ins as mentioned in pom.xml

<Maven>
	
	<Command to use/tweak>
	
	mvn clean test -Dcucumber.filter.tags="@Smoke or @Sanity or @Regression or @ProductScenario"
	
	<General Information>
	
	1. mvn - Maven command keyword. Maven is a build life cycle management tool
	2. cucumber.filter.tags is a cucumber provided flag to dynamically pass cucumber tags to tests
	3. clean - This maven goal is used to clean project artifacts and remove target folder 
	   generated from previous run.
	4. test - This phase is used to perform test execution and generate extent reports.
	
<Points To Note>

	1. There are numerous features/functionalities which are yet to be added to this framework. 
	   I will keep on adding it and the same will be updated to my personal git repository.
	2. At the time of implementation of this framework, it runs on latest selenium grid/web-driver
	   and corresponding driver binaries and on latest supported browsers as listed below:
	   2.1 Chrome
	   2.2 Firefox
	   2.3 Edge
	   2.4 RemoteWebDriver
	   2.5 Safari - Yet to be tested on Safari browser due to platform unavailability
	   2.6 Internet Explorer - This browser has whole lot of compatibility issues with
	   	   latest versions of relevant tools and technologies being used in this framework.
	   	   And since the framework supports edge browser, Internet Explorer is kept aside
	   	   for time being.
	3. Local and Remote execution is controlled by runType json data key. As per need, we can
	   parameterize it via maven build or can be categered from within json data file as well.
	   Currently, it is reading mentioned key and other keys from json file. We will parametize
	   these from maven build in future releases.
	4. Remote and Local executions, both target same .cache directory (where all driver binaries are stored), 
	   therefore it is recommended that both executions be run on separate machines or follow steps as below,
	   before switching runTypes:
	   (This is tools specific limitation and is not framework specific. Since both executions are referring 
	   to 2 difference browser driver api's/library. It has been kept this way intentionally for time being)
	   1. Clear "temp" directory where temporary binary related files are strored
	   2. Delete all files present in .cache directory of your machine
	   3. If for some reason, the driver binaries do not get deleted in step 2 then, kill related tasks
	   	  processes and try step 2 again. Deletion should be successful.
	   4. You will need to restart your hub and node again.
	5. Currently our feature tests execute in collaboration with cucumber, testNG and number of available browsers.
	   Please refer code to understand its way of working.
	6. This framework is under development, hence lots of functionalities are yet to be implemented. Please
	   refer my git repository for latest changes/updates.
	7. Currently, local executions uses webdrivermanager and remote executions use selenium-manager.
	   It will be updated to point to a single binary library in future releases.
	   
<Current To-Do List>

	1. Test framework optimization and updation. 
	2. Scale tests using dockers and kubernetes.
	2. Integration with CI/CD tool like jenkins.
	3. Emailable report publishing via Jenkins on build status event change.
	4. Integration with cloud platforms like AWS.
	5. Add Performance aspects to this framework.
	6. Add security aspects to the framework. Utilization of tools like zaproxy or some code vulnerability scanner
	   (This will be different from code quality tools being used) tool.
	7. Integration with test management tools like Jira. Primarily with automation tasks or tests written.
	8. Add API testing capability to this framework.
	9. Integration or updation with GitHub-Copilot (Usage of AI/ML in test automation)
	10. Local and Remote browser binary pointing to be changed to a single binary source.
	