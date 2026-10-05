package TestCases;

import DriverFactory.Base;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Example test. Copy it as a starting point for your real tests, then delete it.
 */
@Epic("My Framework")
@Feature("Sample")
public class SampleTest extends Base {

    private static final Logger log = LogManager.getLogger(SampleTest.class);

    @Story("Open application")
    @Description("Verify that the application URL from config.properties opens.")
    @Test(groups = "smoke")
    public void applicationOpensSuccessfully() {

        String title = driver.getTitle();

        log.info("Page title: {}", title);

        Assert.assertFalse(title.isEmpty(), "Page title should not be empty.");
    }

    @Story("Failure demo")
    @Description("Enable this test to check that the log + screenshot appear in Allure on failure.")
    @Test(enabled = false, groups = "demo")
    public void demoFailureToSeeLogInAllure() {

        log.info("This test fails on purpose");

        Assert.fail("Intentional failure to verify the Allure log attachment.");
    }
}
