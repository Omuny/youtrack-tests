package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class NewIssuePage {
    private final WebDriver driver;
    private final By summaryInput = By.cssSelector("textarea[data-test='summary']");
    private final By descriptionTextarea = By.cssSelector("div[data-test='wysiwyg-editor-content']");
    private final By createButton = By.cssSelector("button[data-test='submit-button']");

    public NewIssuePage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterSummary(String summary) {
        driver.findElement(summaryInput).sendKeys(summary);
    }

    public void enterDescription(String description) {
        // click для фокуса, после sendKeys
        driver.findElement(descriptionTextarea).click();
        driver.findElement(descriptionTextarea).sendKeys(description);
    }

    public void clickCreate() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(createButton)).click();
    }

    public boolean isCreateButtonEnabled() {
        return driver.findElement(createButton).isEnabled();
    }

    public String getCreatedIssueId() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/issue/"));
        String url = driver.getCurrentUrl();
        // Разбиваем URL на сегменты по "/"
        String[] parts = url.split("/");
        // IssueId — предпоследний сегмент
        if (parts.length >= 2) {
            return parts[parts.length - 2];
        } else {
            throw new RuntimeException("Invalid issue URL format: " + url);
        }
    }
}