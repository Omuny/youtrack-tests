package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class IssuesPage extends BasePage{
    private final By searchField = By.cssSelector("input[data-test='ring-select__focus'][placeholder='Поиск по тексту или добавление фильтра']");
    private final String issueLocator = "//a[contains(@data-test,'ring-link ticket-id')][contains(text(),'%s')]";

    public IssuesPage(WebDriver driver) {
        super(driver);
    }

    public void searchIssue(String issueId) {
        sendKeysToElement(searchField, issueId + "\n");
    }

    public boolean isIssuePresent(String issueId) {
        By issueLocatorBy = By.xpath(String.format(issueLocator, issueId));
        try {
            waitForElementToBeVisible(issueLocatorBy);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}