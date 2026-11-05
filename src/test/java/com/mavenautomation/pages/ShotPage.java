package com.mavenautomation.pages;

import java.time.Duration;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.mavenautomation.config.AdminConfig;

/**
 * Writes a shot on the Emisión tab and publishes that shot.
 * The parent superevento stays unpublished.
 */
public class ShotPage {

    private final WebDriver driver;
    private final Ui ui;

    public ShotPage(WebDriver driver, AdminConfig config) {
        this.driver = driver;
        this.ui = new Ui(driver, config);
    }

    public void writeContent(String content) {
        WebElement editor = contentField();
        editor.click();
        editor.sendKeys(content);
    }

    public void publishNow() {
        ui.clickDisplayed("shot.publish.xpath");
    }

    public void assertShotCreated(String content) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        try {
            wait.until(d -> {
                String page = ui.bodyText();
                if (page.contains("Nada que guardar")) {
                    throw new AssertionError("The shot form was empty, so nothing was published");
                }
                return page.contains(content) || page.contains("Publicado correctamente");
            });
        } catch (TimeoutException e) {
            throw new AssertionError("The shot was not created. Last page text did not include the shot.", e);
        }
    }

    private WebElement contentField() {
        try {
            return ui.visible("shot.content.xpath");
        } catch (TimeoutException missing) {
            ui.click("shot.placeholder.xpath");
            return ui.visible("shot.content.xpath");
        }
    }
}
