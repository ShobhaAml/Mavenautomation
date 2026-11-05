package com.mavenautomation.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import org.openqa.selenium.By;

/**
 * Reads admin.properties. Locator keys end in {@code .id} or {@code .xpath}
 * so the caller can see which strategy a key uses.
 */
public final class AdminConfig {

    private static final String RESOURCE = "admin.properties";

    private final Properties properties = new Properties();

    public AdminConfig() {
        try (InputStream input = AdminConfig.class.getClassLoader().getResourceAsStream(RESOURCE)) {
            if (input == null) {
                throw new IllegalStateException("Missing classpath resource " + RESOURCE);
            }
            try (Reader reader = new InputStreamReader(input, StandardCharsets.UTF_8)) {
                properties.load(reader);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Could not read " + RESOURCE, e);
        }
    }

    public String get(String key) {
        String value = properties.getProperty(key);
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Missing property: " + key);
        }
        return value.trim();
    }

    public String username() {
        return envOrProperty("ADMIN_USERNAME", "admin.username");
    }

    public String password() {
        return envOrProperty("ADMIN_PASSWORD", "admin.password");
    }

    public boolean headless() {
        return Boolean.parseBoolean(properties.getProperty("browser.headless", "true"));
    }

    public By locator(String key) {
        String value = get(key);
        if (key.endsWith(".id")) {
            return By.id(value);
        }
        if (key.endsWith(".xpath")) {
            return By.xpath(value);
        }
        throw new IllegalArgumentException("Locator key must end with .id or .xpath: " + key);
    }

    private String envOrProperty(String envName, String propertyKey) {
        String fromEnv = System.getenv(envName);
        if (fromEnv != null && !fromEnv.isBlank()) {
            return fromEnv;
        }
        return get(propertyKey);
    }
}
