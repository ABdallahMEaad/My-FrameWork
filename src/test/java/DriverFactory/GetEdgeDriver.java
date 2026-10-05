package DriverFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class GetEdgeDriver {

    private static final Logger log = LogManager.getLogger(GetEdgeDriver.class);

    public static WebDriver driver = null;

    public static WebDriver getEdgeDriver() {

        log.info("Initializing Edge WebDriver");

        if (driver == null) {

            EdgeOptions edgeOptions = new EdgeOptions();
            edgeOptions.addArguments("--inprivate");

            log.debug("Edge InPrivate mode enabled");

            driver = new EdgeDriver(edgeOptions);

            log.info("Edge WebDriver initialized successfully");

        } else {
            log.debug("Existing Edge WebDriver instance returned");
        }

        return driver;
    }

    public static void closeDriver() {

        log.info("Closing Edge WebDriver");

        if (driver != null) {

            driver.quit();
            driver = null;

            log.info("Edge WebDriver closed successfully");

        } else {
            log.debug("Edge WebDriver is already closed");
        }
    }
}