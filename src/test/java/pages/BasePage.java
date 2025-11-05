package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(10);

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
    }

    protected void waitForElementToBeVisible(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void waitForElementToBeClickable(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void clickElement(By locator) {
        waitForElementToBeClickable(locator);
        driver.findElement(locator).click();
    }

    protected void sendKeysToElement(By locator, String text) {
        waitForElementToBeVisible(locator);
        driver.findElement(locator).clear();
        driver.findElement(locator).sendKeys(text);
    }

    protected boolean isElementDisplayed(By locator) {
        try {
            waitForElementToBeVisible(locator);
            return driver.findElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected String getElementText(By locator) {
        waitForElementToBeVisible(locator);
        return driver.findElement(locator).getText();
    }

    protected void waitForNumberOfWindowsToBe(int numberOfWindows) {
        wait.until(ExpectedConditions.numberOfWindowsToBe(numberOfWindows));
    }

    protected void switchToNewWindow(java.util.Set<String> originalWindows) {

        waitForNumberOfWindowsToBe(originalWindows.size() + 1);
        java.util.Set<String> allWindows = driver.getWindowHandles();
        for (String windowHandle : allWindows) {
            if (!originalWindows.contains(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
    }
}