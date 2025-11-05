package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class IssuesPage {
    private final WebDriver driver;
    private final By searchField = By.cssSelector("input[data-test='ring-select__focus'][placeholder='Поиск по тексту или добавление фильтра']");
    private final String issueLocator = "//a[contains(@data-test,'ring-link ticket-id')][contains(text(),'%s')]";

    public IssuesPage(WebDriver driver) {
        this.driver = driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchField));
    }

    public void searchIssue(String issueId) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchField));
        driver.findElement(searchField).clear();
        driver.findElement(searchField).sendKeys(issueId + "\n");
    }

    public boolean isIssuePresent(String issueId) {
        return driver.findElements(By.xpath(String.format(issueLocator, issueId))).size() > 0;
    }
}