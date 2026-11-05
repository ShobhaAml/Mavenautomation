package com.mavenautomation.pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.mavenautomation.config.AdminConfig;

/**
 * Signs in on the testadmin login form, then opens the Content Forge editor.
 * The username field is {@code #username} ({@code name="_username"}).
 * The account name "admin" is typed into that field. It is not an element id.
 */
public class LoginPage {

    private final WebDriver driver;
    private final AdminConfig config;
    private final Ui ui;

    public LoginPage(WebDriver driver, AdminConfig config) {
        this.driver = driver;
        this.config = config;
        this.ui = new Ui(driver, config);
    }

    public void signIn() {
        driver.get(config.get("admin.login.url"));
        WebElement username = loginField(
                "login.username.id",
                By.id("username"),
                By.name("_username"));
        ui.replaceText(username, config.username());
        WebElement password = loginField(
                "login.password.id",
                By.id("password"),
                By.name("_password"));
        ui.replaceText(password, config.password());
        loginField(
                "login.submit.id",
                By.id("_submit"),
                By.name("_submit"),
                By.cssSelector("#user-login input[type='submit']")).click();
        waitUntilLoginFormCloses();
        openEditor();
    }

    public boolean isEditorOpen() {
        String url = driver.getCurrentUrl();
        return url.startsWith(editorRoot())
                && !url.contains("/login")
                && ui.isPresent("editor.app.id");
    }

    private void waitUntilLoginFormCloses() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(25)).until(d -> {
                String error = loginErrorText();
                if (!error.isBlank()) {
                    throw new AssertionError("testadmin login failed. " + error);
                }
                List<WebElement> username = d.findElements(By.id("username"));
                if (username.isEmpty()) {
                    username = d.findElements(By.name("_username"));
                }
                return username.isEmpty() || !username.get(0).isDisplayed();
            });
        } catch (TimeoutException e) {
            throw new AssertionError(
                    "testadmin login failed. The login form stayed on " + driver.getCurrentUrl(), e);
        }
    }

    private void openEditor() {
        driver.get(config.get("admin.editor.url"));
        new WebDriverWait(driver, Duration.ofSeconds(40)).until(ExpectedConditions.or(
                ExpectedConditions.urlContains("/login"),
                ExpectedConditions.presenceOfElementLocated(config.locator("editor.app.id"))));
        if (driver.getCurrentUrl().contains("/login")) {
            throw new AssertionError(
                    "testadmin session did not open the editor. " + loginErrorText());
        }
    }

    private String loginErrorText() {
        List<WebElement> errors = driver.findElements(config.locator("login.error.xpath"));
        if (errors.isEmpty() || !errors.get(0).isDisplayed()) {
            return "";
        }
        return errors.get(0).getText().trim();
    }

    private String editorRoot() {
        String url = config.get("admin.editor.url");
        return url.endsWith("/") ? url.substring(0, url.length() - 1) : url;
    }

    /**
     * Uses the configured locator when that element is on the page.
     * Otherwise uses the real testadmin ids. A locator of {@code #admin}
     * is skipped because that id does not exist on the login form.
     */
    private WebElement loginField(String configuredKey, By... fallbacks) {
        List<By> locators = new ArrayList<>();
        By configured = config.locator(configuredKey);
        if (!isAdminId(configured)) {
            locators.add(configured);
        }
        for (By fallback : fallbacks) {
            if (!locators.contains(fallback)) {
                locators.add(fallback);
            }
        }
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(20)).until(d -> firstDisplayed(d, locators));
        } catch (TimeoutException e) {
            throw new AssertionError(
                    "Login field was not found. Tried " + locators + " at " + driver.getCurrentUrl()
                            + ". The username field id is \"username\", not \"admin\".",
                    e);
        }
    }

    private WebElement firstDisplayed(WebDriver page, List<By> locators) {
        for (By locator : locators) {
            for (WebElement element : page.findElements(locator)) {
                if (element.isDisplayed()) {
                    return element;
                }
            }
        }
        return null;
    }

    private boolean isAdminId(By locator) {
        return "By.id: admin".equals(String.valueOf(locator));
    }
}
