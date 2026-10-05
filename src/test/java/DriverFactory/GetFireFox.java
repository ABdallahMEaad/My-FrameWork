package DriverFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class GetFireFox {

    private static final Logger log = LogManager.getLogger(GetFireFox.class);

    public static WebDriver driver = null;

    public static WebDriver FirefoxDriver() {

        log.info("Initializing Firefox WebDriver");

        if (driver == null) {

            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.addArguments("--private");

            log.debug("Firefox private browsing mode enabled");

            driver = new FirefoxDriver(firefoxOptions);

            log.info("Firefox WebDriver initialized successfully");
        } else {
            log.debug("Existing Firefox WebDriver instance returned");
        }

        return driver;
    }

    public static void closeDriver() {

        log.info("Closing Firefox WebDriver");

        if (driver != null) {

            driver.quit();
            driver = null;

            log.info("Firefox WebDriver closed successfully");

        } else {
            log.debug("Firefox WebDriver is already closed");
        }
    }
}