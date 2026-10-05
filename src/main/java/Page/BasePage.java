package Page;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    private static final Logger log = LogManager.getLogger(BasePage.class);

    public BasePage(WebDriver driver) {
        this.driver = driver;
        log.debug("BasePage initialized with WebDriver");
    }

    public WebElement findElement(By locator) {

        log.debug("Finding element: {}", locator);

        return findElement(locator, Duration.ofSeconds(20));
    }

    public WebElement findElement(By locator, Duration timeout) {

        log.debug("Waiting for element to be visible: {} | Timeout: {} seconds",
                locator,
                timeout.getSeconds());

        wait = new WebDriverWait(driver, timeout);

        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

        log.debug("Element is visible: {}", locator);

        return driver.findElement(locator);
    }
}