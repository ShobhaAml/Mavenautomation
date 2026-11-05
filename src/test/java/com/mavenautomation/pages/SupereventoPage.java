package com.mavenautomation.pages;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.mavenautomation.config.AdminConfig;

/**
 * Creates a superevento draft on the Datos tab.
 * Start date and end date are filled. The superevento is not published.
 */
public class SupereventoPage {

    private static final DateTimeFormatter DISPLAY_DATE = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter DIGITS = DateTimeFormatter.ofPattern("ddMMyyyy");

    private final WebDriver driver;
    private final AdminConfig config;
    private final Ui ui;

    public SupereventoPage(WebDriver driver, AdminConfig config) {
        this.driver = driver;
        this.config = config;
        this.ui = new Ui(driver, config);
    }

    public void openNew() {
        driver.get(config.get("superevento.create.url"));
        ui.driverWait().until(ExpectedConditions.urlContains("/escribir/"));
        ui.visible("superevento.title.xpath");
    }

    public void fillTitle(String title) {
        ui.replaceText("superevento.title.xpath", title);
        ui.visible("superevento.title.xpath").sendKeys(Keys.TAB);
    }

    public void fillStartDate(LocalDate date) {
        fillDate("superevento.startDate.xpath", date);
    }

    public void fillEndDate(LocalDate date) {
        fillDate("superevento.endDate.xpath", date);
    }

    public void waitUntilDraftSaved() {
        try {
            ui.visible("superevento.savedStatus.xpath");
        } catch (TimeoutException e) {
            throw new AssertionError(
                    "Superevento draft was not saved after filling the start and end dates", e);
        }
    }

    public void openEmisionTab() {
        ui.click("tab.emision.xpath");
        ui.driverWait().until(ExpectedConditions.urlContains("/emision/"));
        new WebDriverWait(driver, Duration.ofSeconds(40)).until(d ->
                ui.isPresent("shot.editor.xpath")
                        || ui.isPresent("shot.placeholder.xpath")
                        || ui.isPresent("superevento.closedMessage.xpath"));
        if (ui.isPresent("superevento.closedMessage.xpath")
                && driver.findElement(config.locator("superevento.closedMessage.xpath")).isDisplayed()) {
            throw new AssertionError(
                    "Emisión does not allow a new shot because follow-up is already closed");
        }
    }

    /**
     * Types a date as dd/mm/aaaa. If the field reports an invalid format,
     * types the same day as digits so the mask can insert the slashes.
     */
    private void fillDate(String locatorKey, LocalDate date) {
        typeDate(locatorKey, date.format(DISPLAY_DATE));
        if (fieldError(locatorKey) != null) {
            typeDate(locatorKey, date.format(DIGITS));
        }
        String error = fieldError(locatorKey);
        if (error != null) {
            throw new AssertionError("Date " + date.format(DISPLAY_DATE) + " was rejected: " + error);
        }
    }

    private void typeDate(String locatorKey, String value) {
        WebElement input = ui.visible(locatorKey);
        ui.replaceText(input, value);
        input.sendKeys(Keys.ENTER);
        input.sendKeys(Keys.TAB);
    }

    private String fieldError(String locatorKey) {
        WebElement input = driver.findElement(config.locator(locatorKey));
        WebElement control = input.findElement(By.xpath(
                "./ancestor::*[contains(@class,'MuiFormControl')][1]"));
        List<WebElement> errors = control.findElements(By.xpath(
                ".//p[contains(@class,'Mui-error')]"));
        for (WebElement error : errors) {
            if (error.isDisplayed() && !error.getText().isBlank()) {
                return error.getText().trim();
            }
        }
        return null;
    }
}
