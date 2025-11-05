package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.Keys;

public class IssueDetailsPage extends BasePage{
    private final By editButton = By.cssSelector("button[data-test='edit-issue-button']");
    private final By summaryField = By.cssSelector("textarea[data-test='summary']");
    private final By saveButton = By.cssSelector("button[data-test='save-button']");
    private final By moreActionsButton = By.cssSelector("button[aria-label='Показать больше'][aria-haspopup='true']");
    private final By deleteOption = By.xpath("//span[contains(text(),'Удалить')]");
    private final By confirmDeleteButton = By.cssSelector("button[data-test='confirm-ok-button']");
    private final By displayedSummary = By.cssSelector("h1[data-test='ticket-summary']");

    public IssueDetailsPage(WebDriver driver) {
        super(driver);
    }

    public void clickEdit() {
        clickElement(editButton);
    }

    public void updateSummary(String newSummary) {
        waitForElementToBeVisible(summaryField);
        driver.findElement(summaryField).sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        driver.findElement(summaryField).sendKeys(newSummary);
    }

    public void clickSave() {
        clickElement(saveButton);
    }

    public String getDisplayedSummary() {
        return getElementText(displayedSummary);
    }

    public void deleteIssue() {
        clickElement(moreActionsButton);
        clickElement(deleteOption);
        clickElement(confirmDeleteButton);
    }
}