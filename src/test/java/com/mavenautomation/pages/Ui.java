package com.mavenautomation.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.mavenautomation.config.AdminConfig;

/**
 * Small wait and typing helpers. Pages name the action; this class only
 * finds the element from a locator key.
 */
public final class Ui {

    private final WebDriver driver;
    private final AdminConfig config;
    private final WebDriverWait wait;

    public Ui(WebDriver driver, AdminConfig config) {
        this.driver = driver;
        this.config = config;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(40));
    }

    public WebDriverWait driverWait() {
        return wait;
    }

    public WebElement visible(String locatorKey) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(config.locator(locatorKey)));
    }

    public WebElement clickable(String locatorKey) {
        return wait.until(ExpectedConditions.elementToBeClickable(config.locator(locatorKey)));
    }

    public void click(String locatorKey) {
        WebElement element = clickable(locatorKey);
        scrollIntoView(element);
        element.click();
    }

    public void clickDisplayed(String locatorKey) {
        List<WebElement> elements = driver.findElements(config.locator(locatorKey));
        for (WebElement element : elements) {
            if (element.isDisplayed() && element.isEnabled()) {
                scrollIntoView(element);
                element.click();
                return;
            }
        }
        click(locatorKey);
    }

    public void replaceText(WebElement element, String value) {
        scrollIntoView(element);
        element.click();
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        element.sendKeys(Keys.DELETE);
        if (value != null && !value.isEmpty()) {
            element.sendKeys(value);
        }
    }

    public void replaceText(String locatorKey, String value) {
        replaceText(visible(locatorKey), value);
    }

    public boolean isPresent(String locatorKey) {
        return !driver.findElements(config.locator(locatorKey)).isEmpty();
    }

    public String bodyText() {
        return driver.findElement(org.openqa.selenium.By.tagName("body")).getText();
    }

    private void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center', inline:'nearest'});", element);
    }
}
