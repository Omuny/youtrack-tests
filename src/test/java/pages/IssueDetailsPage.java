package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.Keys;
import java.time.Duration;

public class IssueDetailsPage {
    private final WebDriver driver;
    private final By editButton = By.cssSelector("button[data-test='edit-issue-button']");
    private final By summaryField = By.cssSelector("textarea[data-test='summary']");
    private final By saveButton = By.cssSelector("button[data-test='save-button']");
    private final By moreActionsButton = By.cssSelector("button[aria-label='Показать больше'][aria-haspopup='true']");
    private final By deleteOption = By.xpath("//span[contains(text(),'Удалить')]");
    private final By confirmDeleteButton = By.cssSelector("button[data-test='confirm-ok-button']");
    private final By displayedSummary = By.cssSelector("h1[data-test='ticket-summary']");

    public IssueDetailsPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickEdit() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(editButton)).click();
    }

    public void updateSummary(String newSummary) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(summaryField));
        driver.findElement(summaryField).sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        driver.findElement(summaryField).sendKeys(newSummary);
    }

    public void clickSave() {
        driver.findElement(saveButton).click();
    }

    public String getDisplayedSummary() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(displayedSummary));
        return driver.findElement(displayedSummary).getText();
    }

    public void deleteIssue() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(moreActionsButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(deleteOption)).click();
        wait.until(ExpectedConditions.elementToBeClickable(confirmDeleteButton)).click();
    }
}