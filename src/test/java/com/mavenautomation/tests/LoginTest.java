package com.mavenautomation.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Logs in on testadmin and checks that the editor opens.
 */
public class LoginTest extends BaseTest {

    @Test(description = "Login testadmin and open the editor")
    public void loginTestadmin() {
        loginPage.signIn();
        Assert.assertTrue(loginPage.isEditorOpen(),
                "testadmin login should open the editor at " + config.get("admin.editor.url"));
    }
}
