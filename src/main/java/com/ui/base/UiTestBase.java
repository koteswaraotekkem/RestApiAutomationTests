package com.ui.base;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import lombok.extern.java.Log;
import org.testng.annotations.BeforeClass;

import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.Selenide.open;

@Log
public class UiTestBase {
    String url = EnvVars.base_ui_url;

    @BeforeClass(alwaysRun = true)
    public void reInitDriver() {
        System.out.println(url);
        log.info("quit opened instance and open new instance");
        Selenide.closeWebDriver();
        open(url);
        WebDriverRunner.getWebDriver().manage().timeouts().pageLoadTimeout(90, TimeUnit.SECONDS);
        WebDriverRunner.getWebDriver().manage().window().maximize();
    }
}
