package DriverFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class GetChrome {

    private static final Logger log = LogManager.getLogger(GetChrome.class);

    private static WebDriver driver = null;

    public static WebDriver getChromeDriver() {

        log.info("Initializing Chrome WebDriver");

        if (driver == null) {

            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--incognito");

            log.debug("Chrome Incognito mode enabled");

            driver = new ChromeDriver(chromeOptions);

            log.info("Chrome WebDriver initialized successfully");

        } else {
            log.debug("Existing Chrome WebDriver instance returned");
        }

        return driver;
    }

    public static void closeDriver() {

        log.info("Closing Chrome WebDriver");

        if (driver != null) {

            driver.quit();
            driver = null;

            log.info("Chrome WebDriver closed successfully");

        } else {
            log.debug("Chrome WebDriver is already closed");
        }
    }
}