package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class NewIssuePage extends BasePage{
    private final By summaryInput = By.cssSelector("textarea[data-test='summary']");
    private final By descriptionTextarea = By.cssSelector("div[data-test='wysiwyg-editor-content']");
    private final By createButton = By.cssSelector("button[data-test='submit-button']");

    public NewIssuePage(WebDriver driver) {
        super(driver);
    }

    public void enterSummary(String summary) {
        sendKeysToElement(summaryInput, summary);
    }

    public void enterDescription(String description) {
        clickElement(descriptionTextarea);
        sendKeysToElement(descriptionTextarea, description);
    }

    public void clickCreate() {
        clickElement(createButton);
    }

    public boolean isCreateButtonEnabled() {
        return driver.findElement(createButton).isEnabled();
    }

    public String getCreatedIssueId() {
        wait.until(driver -> driver.getCurrentUrl().contains("/issue/"));
        String url = driver.getCurrentUrl();
        String[] parts = url.split("/");
        if (parts.length >= 2) {
            return parts[parts.length - 2];
        } else {
            throw new RuntimeException("Invalid issue URL format: " + url);
        }
    }
}