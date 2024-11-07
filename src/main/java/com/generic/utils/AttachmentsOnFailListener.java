package com.generic.utils;

import com.codeborne.selenide.Screenshots;
import com.codeborne.selenide.WebDriverRunner;
import com.google.common.io.Files;
import io.qameta.allure.Attachment;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import static com.codeborne.selenide.Selenide.screenshot;

public class AttachmentsOnFailListener implements ITestListener {
    @Override
    public void onTestStart(ITestResult iTestResult) {

    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {

    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        try {
            attachScreenshot(iTestResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        attachBrowserURL();
        attachPageSource();

        if (WebDriverRunner.driver().hasWebDriverStarted()) {
            if (!WebDriverRunner.getWebDriver().getClass().getSimpleName().toLowerCase().contains("internetexplorer".toLowerCase()) && !WebDriverRunner.isEdge())
                attachBrowserLogs();
        }
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    @Override
    public void onStart(ITestContext iTestContext) {

    }

    @Override
    public void onFinish(ITestContext iTestContext) {

    }


    @Attachment(value = "Browser screenshot after test failed", type = "image/png")
    public byte[] attachScreenshot(ITestResult iTestResult) throws IOException {
        File screenshot = null;
        byte[] fileBytes = new byte[0];
        if (WebDriverRunner.driver().hasWebDriverStarted()) {
            try {
                screenshot = Screenshots.takeScreenShotAsFile();
                screenshot("Chrome" + "_" + iTestResult.getName() + "_" + System.currentTimeMillis());
                System.out.println("See screenshot: " + Screenshots.getLastScreenshot().getAbsolutePath());
                if (screenshot != null)
                    fileBytes = Files.toByteArray(screenshot);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return fileBytes;
        }
        return fileBytes;
    }

    @Attachment(value = "Browser logs", type = "text/plain")
    public String attachBrowserLogs() {
        String text = "";
        try {
            if (WebDriverRunner.driver().hasWebDriverStarted()) {
                LogEntries logs = WebDriverRunner.getWebDriver().manage().logs().get("browser");
                for (LogEntry entry : logs.getAll()) {
                    text += (new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage()) + "\n";
                }
                return text;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        try {
            LogEntries logs = WebDriverRunner.getWebDriver().manage().logs().get("browser");
            for (LogEntry entry : logs.getAll()) {
                text += (new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage()) + "\n";
            }
        } catch (Exception e) {
        }
        return null;
    }

    @Attachment(value = "Browser logs", type = "text/plain")
    public String attachBrowserLogsDup() throws IOException {
        String text = "";
        try {
            if (WebDriverRunner.driver().hasWebDriverStarted()) {
                LogEntries logs = WebDriverRunner.getWebDriver().manage().logs().get("browser");
                for (LogEntry entry : logs.getAll()) {
                    text += (new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage()) + "\n";
                }
                return text;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Attachment(value = "Browser URL", type = "text/plain")
    public String attachBrowserURL() {
        String url = "";
        try {
            if (WebDriverRunner.driver().hasWebDriverStarted()) {
                url = WebDriverRunner.url();
                return url;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Attachment(value = "Page source html", type = "text/html")
    public String attachPageSource() {
        String url = "";
        try {
            if (WebDriverRunner.driver().hasWebDriverStarted()) {
                url = WebDriverRunner.getWebDriver().getPageSource();
                return url;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}

