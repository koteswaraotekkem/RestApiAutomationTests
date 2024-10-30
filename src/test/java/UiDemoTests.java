/*
 * Copyright (c) 2024.
 * KoteswaraRao Tekkem
 */

import com.codeborne.selenide.WebDriverRunner;
import com.ui.base.UiTestBase;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

public class UiDemoTests extends UiTestBase {

    @Test
    public void loginTest(){
        System.out.println(".........");
        WebDriverRunner.getWebDriver().findElement(By.id("aa")).click();
    }
}
