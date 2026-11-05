package com.mavenautomation.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.mavenautomation.config.AdminConfig;

/**
 * Signs in on the testadmin login form, then opens the Content Forge editor.
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
        ui.visible("login.username.id");
        ui.replaceText("login.username.id", config.username());
        ui.replaceText("login.password.id", config.password());
        ui.click("login.submit.id");
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
                List<WebElement> username = d.findElements(config.locator("login.username.id"));
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
}
