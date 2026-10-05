package DriverFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class ChooseDriver {

    private static final Logger log = LogManager.getLogger(ChooseDriver.class);

    public static WebDriver getWebdriver(String browserName) {

        log.info("Selecting browser: {}", browserName);

        WebDriver driver;

        switch (browserName.toLowerCase().trim()) {

            case "chrome":
                log.info("Starting Chrome WebDriver");
                driver = GetChrome.getChromeDriver();
                break;

            case "firefox":
                log.info("Starting Firefox WebDriver");
                driver = GetFireFox.FirefoxDriver();
                break;

            case "edge":
                log.info("Starting Edge WebDriver");
                driver = GetEdgeDriver.getEdgeDriver();
                break;

            default:
                log.error("Invalid browser name: {}", browserName);
                throw new IllegalArgumentException("Invalid Browser: " + browserName);
        }

        log.info("{} WebDriver selected successfully", browserName);

        return driver;
    }

    public static void quitDriver(String browserName) {

        log.info("Closing browser: {}", browserName);

        switch (browserName.toLowerCase().trim()) {

            case "chrome":
                GetChrome.closeDriver();
                break;

            case "firefox":
                GetFireFox.closeDriver();
                break;

            case "edge":
                GetEdgeDriver.closeDriver();
                break;

            default:
                log.error("Invalid browser name while closing driver: {}", browserName);
                throw new IllegalArgumentException("Invalid Browser: " + browserName);
        }

        log.info("{} WebDriver closed successfully", browserName);
    }
}