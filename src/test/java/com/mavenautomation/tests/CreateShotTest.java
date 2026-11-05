package com.mavenautomation.tests;

import java.time.LocalDate;
import java.time.ZoneId;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Creates a shot inside a new superevento.
 * The superevento needs a start date and an end date. Publishing it is not required.
 */
public class CreateShotTest extends BaseTest {

    private static final ZoneId SITE_ZONE = ZoneId.of("Europe/Madrid");

    @Test(description = "Create a shot after filling the superevento start and end dates")
    public void createNewShot() {
        loginPage.signIn();

        String title = "Superevento shot " + System.currentTimeMillis();
        String shotText = "Shot automatizado " + System.currentTimeMillis();
        LocalDate startDate = LocalDate.now(SITE_ZONE);
        LocalDate endDate = startDate.plusDays(7);

        supereventoPage.openNew();
        supereventoPage.fillTitle(title);
        supereventoPage.fillStartDate(startDate);
        supereventoPage.fillEndDate(endDate);
        supereventoPage.waitUntilDraftSaved();

        supereventoPage.openEmisionTab();
        shotPage.writeContent(shotText);
        shotPage.publishNow();
        shotPage.assertShotCreated(shotText);

        Assert.assertTrue(driver.getCurrentUrl().contains("/emision/"),
                "The shot is created from the Emisión tab");
        Assert.assertFalse(driver.getCurrentUrl().contains("/publicar"),
                "The superevento does not need to be published");
    }
}
