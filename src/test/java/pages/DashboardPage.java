package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.Set;

public class DashboardPage {
    private final WebDriver driver;
    private final By createButton = By.cssSelector("span[aria-label='Создать']"); // Кнопка "Создать" на sidebar
    private final By newIssueMenuItem = By.xpath("//a[contains(@href, 'newIssue') or contains(text(),'Новая задача')]"); // В меню после клика "Создать"
    private final By issuesLink = By.xpath("//a[contains(@href, 'issues')]");
    private Set<String> originalWindows;

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
    }

    public void navigateToIssues() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(issuesLink)).click();
    }

    public void clickCreateNewIssue() {
        originalWindows = driver.getWindowHandles();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(createButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(newIssueMenuItem)).click();

        // Ждём открытия новой вкладки и переключаемся на неё, если необходимо
        wait.until(ExpectedConditions.numberOfWindowsToBe(originalWindows.size() + 1));
        Set<String> allWindows = driver.getWindowHandles();
        for (String windowHandle : allWindows) {
            if (!originalWindows.contains(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
    }

    public boolean isDashboardLoaded() {
        return driver.findElement(issuesLink).isDisplayed();
    }
}