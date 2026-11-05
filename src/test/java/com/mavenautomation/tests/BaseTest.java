package com.mavenautomation.tests;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.mavenautomation.config.AdminConfig;
import com.mavenautomation.driver.Browser;
import com.mavenautomation.pages.LoginPage;
import com.mavenautomation.pages.ShotPage;
import com.mavenautomation.pages.SupereventoPage;

public abstract class BaseTest {

    protected WebDriver driver;
    protected AdminConfig config;
    protected LoginPage loginPage;
    protected SupereventoPage supereventoPage;
    protected ShotPage shotPage;

    @BeforeMethod
    public void openBrowser() {
        config = new AdminConfig();
        driver = Browser.chrome(config);
        loginPage = new LoginPage(driver, config);
        supereventoPage = new SupereventoPage(driver, config);
        shotPage = new ShotPage(driver, config);
    }

    @AfterMethod(alwaysRun = true)
    public void closeBrowser(ITestResult result) {
        if (driver != null && !result.isSuccess()) {
            saveScreenshot(result.getName());
        }
        if (driver != null) {
            driver.quit();
        }
    }

    private void saveScreenshot(String name) {
        try {
            Path directory = Path.of("target", "screenshots");
            Files.createDirectories(directory);
            byte[] png = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Files.write(directory.resolve(name + ".png"), png);
        } catch (IOException ignored) {
            // A missing screenshot should not hide the test failure.
        }
    }
}
