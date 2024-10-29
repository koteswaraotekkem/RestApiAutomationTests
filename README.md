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

 