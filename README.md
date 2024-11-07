# RestApiAutomationTests
Use Gson to Serialize and DeSearliaze

Step 1: Install Allure CLI
                 Download Allure: Visit Allure's GitHub page to download the latest .zip file for Allure CLI.
                 Extract Allure: Extract the downloaded .zip file to a location, for example, C:\allure.
                 Add Allure to System Path:
                 Open System Properties > Environment Variables.
                 In the System Variables section, find and select the Path variable, then click Edit.
                 Add the path to the extracted bin folder, e.g., C:\allure\bin.

Step 2: Run Tests and Generate Allure Results
mvn clean test

Step 3: Run bellow command from pom file location
        allure serve allure-results

Video Test Recoder
---------------------
1. run this command with your local ATUTestRecorder path
   mvn install:install-file -Dfile=C:\Users\ktekkem\Desktop\Automation\src\main\lib\ATUTestRecorder-2.1.jar -DpomFile=C:\Users\ktekkem\Desktop\Automation\src\main\lib\ATUTestRecorder-2.1.pom
2. The Above command should add a maven dependency to your pom.xml. If not added please add by your self
   <dependency>
   <groupId>ATUTestRecorder</groupId>
   <artifactId>ATUTestRecorder</artifactId>
   <version>2.1</version>
   </dependency>

Things to set up in your computer
-----------------------------------
1. Install vlc media player to see test recorded videos
2. In your intelli enable annotation processing (lombok to support)
    
 