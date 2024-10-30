package com.ui.base;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import lombok.extern.java.Log;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import atu.testrecorder.ATUTestRecorder;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.Selenide.open;

/**
 * Author : KoteswaraRao Tekkem
 */
@Log
public class UiTestBase {
    String url = EnvVars.base_ui_url;
    private ATUTestRecorder recorder;
    private String videoRecordsPath = "testRecords";
    private boolean firstStopBeforeClassRecordingCall = true;
    private String beforeClassFilename;
    private ITestResult result;

    @BeforeClass(alwaysRun = true)
    public void reInitDriver() {
        System.out.println(url);
        log.info("quit opened instance and open new instance");
        Selenide.closeWebDriver();
        open(url);
        WebDriverRunner.getWebDriver().manage().timeouts().pageLoadTimeout(90, TimeUnit.SECONDS);
        WebDriverRunner.getWebDriver().manage().window().maximize();
    }

    @BeforeClass(alwaysRun = true)
    public void initDrivers() {
        //setBrowser(EnvVars.BROWSER);
        WebDriverRunner.closeWebDriver();
        log.info("Initialising WebDriver");
        WebDriverRunner.clearBrowserCache();
        beforeClassFilename = getTestClassName() + ".beforeClass";
        recordTestAsVideo(beforeClassFilename);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTestBaseMethod(Method method, ITestResult result) throws Exception {
        String filename = method.getDeclaringClass().getName() + "." + method.getName();
        stopBeforeClassRecording(beforeClassFilename, true);
        recordTestAsVideo(filename);
        log.info("===========================================================================================\n");
        log.info("STARTING TEST CASE '" + method.getDeclaringClass().getName() + "." + method.getName() + "'");
        log.info("===========================================================================================\n\n");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDownTestBaseMethod(Method method, ITestResult result) {
        this.result = result;
        if (result.isSuccess() || result.getStatus() == ITestResult.FAILURE) {
            String filename = method.getDeclaringClass().getName() + "." + method.getName();
            log.info("Post Condition TEAR DOWN for method: " + filename);
            stopTestRecording(filename, result);
            log.info("===========================================================================================\n\n");
        }
    }

    @AfterClass(alwaysRun = true)
    public void testBaseAfterClass() {
        if (result != null) {
            if (ITestResult.FAILURE == result.getStatus()) {
                stopBeforeClassRecording(beforeClassFilename, true);
            } else {
                stopBeforeClassRecording(beforeClassFilename, false);
            }
            log.info("**** In TestBase AfterClass method END****");
        }

    }

    private void recordTestAsVideo(String fileName) {
        // -------- enable video recording --------------
        try {
            File file = new File(videoRecordsPath);
            if (!file.exists()) {
                if (file.mkdir()) {
                    log.info("Create dir for video reports.");
                } else {
                    log.info("Can't create dir for video reports.");
                }
            }
        } catch (Exception e) {
            log.info(e.toString());
        }
        stopTestRecording(null, null);
        try {
            deleteFileIfExists(videoRecordsPath, fileName);
            recorder = new ATUTestRecorder(videoRecordsPath, fileName, false);
            log.info("Start recording for class: " + fileName);
            recorder.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stopTestRecording(String filename, ITestResult result) {
        try {
            if (filename != null) {
                log.info("Stop recording for method: " + filename);
            }

            if (null != recorder) {
                recorder.stop();
            }

            if (result != null) {
                if (result.getStatus() == ITestResult.SUCCESS || result.getStatus() == ITestResult.CREATED) {
                    log.info("Test " + filename + " is SUCCESS, Hence Deleting the recording");
                    deleteFileIfExists(videoRecordsPath, filename);
                } else if (result.getStatus() == ITestResult.FAILURE) {
                    if (null != filename) {
                        log.info("Test '" + filename + "' has failed.");
                    }
                }
            }
        } catch (Exception e) {
            log.info("Exception occurred in stopTestRecording." + e.getMessage());
            e.printStackTrace();
        }

        recorder = null;
    }

    public static void deleteFileIfExists(String filePath, String fileName) {
        File[] listOfFiles = new File(filePath).listFiles();
        for (File downloadedFile : listOfFiles) {
            if (downloadedFile.isFile()) {
                if (downloadedFile.getName().contains(fileName)) {
                    downloadedFile.delete();
                }
            }
        }
    }

    private void stopBeforeClassRecording(String filename, boolean deleteRecording) {
        if (firstStopBeforeClassRecordingCall) {
            // This code will be executed only once for each test class
            try {
                if (filename != null) {
                    log.info("Stop recording for class: " + filename);
                }

                if (null != recorder) {
                    try {
                        recorder.stop();
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }

                if (deleteRecording) {
                    log.info("Deleting recording for class: " + filename);
                    deleteFileIfExists(videoRecordsPath, filename);
                }
            } catch (Exception e) {
                log.info("Exception occurred in stopTestRecording." + e.getMessage());
                e.printStackTrace();
            }

            recorder = null;
        }
        firstStopBeforeClassRecordingCall = false;
    }

    private String getTestClassName() {
        return this.getClass().getSimpleName();
    }

    protected void setBrowser(String browser) {
        if (browser != null || browser != "") {
            switch (browser.toLowerCase()) {

                case "chrome":
                    Configuration.browser = "chrome";
                    Configuration.browserCapabilities = new DesiredCapabilities();
                    Configuration.holdBrowserOpen = true;
                    ChromeOptions options = new ChromeOptions();
                    Map prefs = new HashMap();
                    prefs.put("profile.default_content_setting_values.notifications", 1);
                    prefs.put("credentials_enable_service", false);
                    prefs.put("profile.password_manager_enabled", false);
                    prefs.put("download.prompt_for_download", false);
                    prefs.put("profile.default_content_settings.popups", 0);
                    prefs.put("profile.content_settings.exceptions.automatic_downloads.*.setting", 1);
                    prefs.put("download.default_directory", "EnvVars.pathInLocal");
                    options.setExperimentalOption("prefs", prefs);
                    options.addArguments("--test-type");
                    options.addArguments("--disable-extensions");
                    Configuration.browserCapabilities.setCapability(ChromeOptions.CAPABILITY, options);

                    break;

                case "chromedriverzapproxyprovider":
                   /* WebDriverManager.chromedriver().browserVersion("80.0.3987.106").setup();
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--no-sandbox");
                    chromeOptions.addArguments("--verbose");
                    chromeOptions.addArguments("--start-maximized");
                    String PROXY = "localhost:8090";
                    org.openqa.selenium.Proxy proxy = new org.openqa.selenium.Proxy();
                    proxy.setHttpProxy(PROXY)
                            .setFtpProxy(PROXY)
                            .setSslProxy(PROXY);
                    chromeOptions.setCapability(CapabilityType.PROXY, proxy);
                    WebDriverRunner.setWebDriver(new ChromeDriver(chromeOptions));*/
                    break;

                case "firefox":
                    Configuration.browser = "firefox";
                    break;

                case "edge":

                    break;

                case "ie11":

                    break;
            }
        }
    }
}
