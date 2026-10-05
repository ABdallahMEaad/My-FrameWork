package DriverFactory;

import Utils.ConfigHandler;
import Utils.ExcelFileManger;
import Utils.JSONFileManger;
import Utils.TakeScreenShot;
import Utils.TestLogCapture;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.ByteArrayInputStream;
import java.util.Arrays;

public class Base {

    private static final Logger log = LogManager.getLogger(Base.class);

    public WebDriver driver;

    public ConfigHandler configHandler;

    // Optional helpers: create them inside your test when you need them, e.g.
    // excelFileManger = new ExcelFileManger("src/main/resources/data.xlsx", "Sheet1");
    public JSONFileManger jsonFileManger;
    public ExcelFileManger excelFileManger;

    @BeforeMethod(alwaysRun = true)
    public void initializeDriver() {

        // Start capturing the log of this test (attached to Allure only if it fails)
        TestLogCapture.start();

        log.info("Starting test setup");

        configHandler = new ConfigHandler("src/main/resources/config.properties");

        String browserName = configHandler.getValue("browserName");
        String url = configHandler.getValue("url");

        log.info("Selected browser: {}", browserName);
        log.info("Application URL: {}", url);

        driver = ChooseDriver.getWebdriver(browserName);

        log.info("WebDriver initialized successfully");

        driver.manage().window().maximize();

        driver.get(url);

        log.info("Application opened successfully");
        log.info("Test setup completed successfully");
    }

    /**
     * One single teardown on purpose: the screenshot and the log must be taken
     * BEFORE the browser is closed, so the order has to be guaranteed.
     */
    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {

        try {

            if (result.getStatus() == ITestResult.FAILURE) {
                attachFailureEvidence(result);
            }

        } finally {

            log.info("Starting test teardown");

            try {
                if (configHandler != null) {
                    ChooseDriver.quitDriver(configHandler.getValue("browserName"));
                }
            } finally {
                TestLogCapture.stop();
            }
        }
    }

    private void attachFailureEvidence(ITestResult result) {

        String testName = result.getName();

        if (result.getParameters() != null && result.getParameters().length > 0) {
            testName += " " + Arrays.toString(result.getParameters());
        }

        // Written to the log so the reason (and stack trace) appears inside the attached log
        log.error("TEST FAILED: {}", testName, result.getThrowable());

        if (driver != null) {

            byte[] screenshot = TakeScreenShot.takesScreenshotAsBytes(driver);

            if (screenshot != null) {
                Allure.addAttachment(
                        "Failure screenshot - " + testName,
                        "image/png",
                        new ByteArrayInputStream(screenshot),
                        "png"
                );
            }
        }

        Allure.addAttachment(
                "Test log - " + testName,
                "text/plain",
                TestLogCapture.getLog(),
                ".log"
        );
    }
}
