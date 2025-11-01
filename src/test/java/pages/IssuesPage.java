package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class IssuesPage {
    private final WebDriver driver;
    private final By searchField = By.cssSelector("input[data-test='ring-select__focus'][placeholder='Поиск по тексту или добавление фильтра']");

    public IssuesPage(WebDriver driver) {
        this.driver = driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchField));
    }

    public void searchIssue(String issueId) {
        driver.findElement(searchField).clear();
        driver.findElement(searchField).sendKeys(issueId + "\n");
    }

    public boolean isIssuePresent(String issueId) {
        By issueLocator = By.xpath("//a[contains(@data-test,'ring-link ticket-id')][contains(text(),'" + issueId + "')]");
        return driver.findElements(issueLocator).size() > 0;
    }

    public void openIssue(String issueId) {
        By issueLocator = By.xpath("//a[contains(@data-test,'ring-link ticket-id')][contains(text(),'" + issueId + "')]");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(issueLocator)).click();
    }
}