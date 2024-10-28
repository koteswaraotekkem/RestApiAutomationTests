/*
 * Copyright (c) 2024.
 * KoteswaraRao Tekkem
 */

package com.ui.base;

import lombok.extern.java.Log;

/**
 * @author Koteswararao Tekkem
 */

@Log
public class EnvVars {
    public static String base_ui_url;
    public static String HEROKU_URL;
    public static String username;
    public static String password;

    static {
        base_ui_url = getExtProperty("url");
        HEROKU_URL = getExtProperty("HEROKU_URL");
        username = getExtProperty("username");
        password = getExtProperty("password");

    }

    /**
     * Get environment variables
     *
     * @param propName - Name of property to get. Property name should be same at
     *                 CI/CD jobs and build.xml files.
     * @return - Property value.
     */
    private static String getExtProperty(String propName) {
        String prop = System.getenv(propName);
        System.out.println("URL From Jenkins::" + propName);

        if (prop == null || prop.equals("${" + propName + "}") || prop.isEmpty()) {
            System.out.println("Param '" + propName + "' is not defined at CI/CD job and = '" + prop
                    + "'. Getting value from config.properties file.");
            prop = DataUtils.getPropertyValue(propName);
            if (prop == null) {
                log.info("Param '" + propName + "' is missed (at CI/CD, "
                        + "config.properties) and will be set to null.");
            }
        }
        return prop;
    }
}
