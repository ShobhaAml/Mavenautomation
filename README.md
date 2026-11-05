# Mavenautomation

Selenium and TestNG coverage for the testadmin editor.

1. Log in on testadmin.
2. Locators (id and xpath) live in `src/test/resources/admin.properties`.
3. `src/test/resources/CreateShot.xml` runs the login test and the new-shot test.

Creating a shot fills the superevento start date and end date, saves the draft, and publishes the shot from the Emisión tab.

```bash
mvn test
```

Account and browser flags are in `admin.properties`. `ADMIN_USERNAME` and `ADMIN_PASSWORD` override the file when they are set.
