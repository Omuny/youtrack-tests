package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import pages.DashboardPage;
import pages.NewIssuePage;

public class NegCreateIssueTest extends BaseTest {
    private String originalWindow;
    private DashboardPage dashboardPage;
    private NewIssuePage newIssuePage;
    private WebDriver driver;

    @Test(groups = "requiresLogin", priority = 2)
    public void testNegativeCreateIssue() {
        driver = getDriver();
        originalWindow = driver.getWindowHandle();
        dashboardPage = new DashboardPage(driver);
        newIssuePage = new NewIssuePage(driver);

        // Создание задачи. Оставляем заголовок пустым
        dashboardPage.clickCreateNewIssue();
        newIssuePage.enterDescription("Test Description");
        Assert.assertFalse(newIssuePage.isCreateButtonEnabled(), "Create button should be disabled with empty summary");

        // Закроем вкладку, очистка не требуется
        driver.close();
        driver.switchTo().window(originalWindow);

        System.out.println("Negative create issue test passed");
    }
}