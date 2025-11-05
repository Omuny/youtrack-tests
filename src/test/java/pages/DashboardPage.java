package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Set;

public class DashboardPage extends BasePage{
    private final By createButton = By.cssSelector("span[aria-label='Создать']"); // Кнопка "Создать" на sidebar
    private final By newIssueMenuItem = By.xpath("//a[contains(@href, 'newIssue') or contains(text(),'Новая задача')]"); // В меню после клика "Создать"
    private final By issuesLink = By.xpath("//a[contains(@href, 'issues')]");
    private Set<String> originalWindows;

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    public void navigateToIssues() {
        clickElement(issuesLink);
    }

    public void clickCreateNewIssue() {
        originalWindows = driver.getWindowHandles();
        clickElement(createButton);
        clickElement(newIssueMenuItem);
        switchToNewWindow(originalWindows);
    }

    public boolean isDashboardLoaded() {
        return isElementDisplayed(issuesLink);
    }
}