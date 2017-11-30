package com.mellow.application.jpaservice.config;

import com.mellow.application.jpaservice.service.exception.DatabaseException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigHelper {

    private final String fileName;
    private Properties properties;
    private InputStream inputStream;

    public ConfigHelper(String fileName) {
        this.properties = new Properties();
        this.fileName = fileName;
    }

    String getMySqlUserValue() {
        try {
            return getValueFromInputStream("user");
        } catch (IOException e) {
            throw new DatabaseException("Failed to read user.");
        }
    }

    String getMySqlPasswordValue() {
        try {
            return getValueFromInputStream("password");
        } catch (IOException e) {
            throw new DatabaseException("Failed to read password.");
        }
    }

    String getMySqlUrlValue() {
        try {
            return getValueFromInputStream("mysqlUrl");
        } catch (IOException e) {
            throw new DatabaseException("Failed to read mysql jdbc url.");
        }
    }

    public String getJwtAccessSecretValue() {
        try {
            return getValueFromInputStream("access_secret");
        } catch (IOException e) {
            throw new DatabaseException("Failed to read access secret.");
        }
    }

    public String getJwtRefreshSecretValue() {
        try {
            return getValueFromInputStream("refresh_secret");
        } catch (IOException e) {
            throw new DatabaseException("Failed to read refresh secret.");
        }
    }

    private String getValueFromInputStream(String property) throws IOException {
        try {
            this.inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
            properties.load(inputStream);
            return properties.getProperty(property);
        } catch (IOException e) {
            throw new DatabaseException(String.format("Failed to read from file %s", fileName));
        } finally {
            inputStream.close();
        }
    }
}
