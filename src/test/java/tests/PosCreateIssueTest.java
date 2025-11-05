package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pages.DashboardPage;
import pages.IssueDetailsPage;
import pages.IssuesPage;
import pages.NewIssuePage;

public class PosCreateIssueTest extends BaseTest {
    private WebDriver driver;
    private String originalWindow;
    private DashboardPage dashboardPage;
    private NewIssuePage newIssuePage;
    private IssuesPage issuesPage;
    private IssueDetailsPage issueDetailsPage;
    private String issueId;

    @DataProvider(name = "issueData")
    public Object[][] issueData() {
        return new Object[][] {
                {"Test Summary 1", "Test Description 1"},
                {"Test Summary 2", "Test Description 2"}
        };
    }

    @Test(dataProvider = "issueData", groups = "requiresLogin", priority = 3)
    public void testPositiveCreateIssue(String summary, String description) {
        driver = getDriver();
        originalWindow = driver.getWindowHandle();
        dashboardPage = new DashboardPage(driver);
        newIssuePage = new NewIssuePage(driver);
        issuesPage = new IssuesPage(driver);
        issueDetailsPage = new IssueDetailsPage(driver);

        // Создание задачи
        dashboardPage.clickCreateNewIssue();
        newIssuePage.enterSummary(summary);
        newIssuePage.enterDescription(description);
        newIssuePage.clickCreate();

        issueId = newIssuePage.getCreatedIssueId();
        Assert.assertNotNull(issueId, "Issue ID not extracted after creation");
        System.out.println("Created issue: " + issueId);

        // Переключимся на базовое окно для проверки и очистки
        driver.close();
        driver.switchTo().window(originalWindow);
        dashboardPage.navigateToIssues();
        Assert.assertTrue(issuesPage.isIssuePresent(issueId), "Issue not found after creation");

        // Очистка
        issuesPage.searchIssue(issueId);
        issueDetailsPage.deleteIssue();

        // Проверка удаления
        driver.navigate().to(BASE_URL + "/issues");
        issuesPage = new IssuesPage(driver);
        Assert.assertFalse(issuesPage.isIssuePresent(issueId), "Issue not deleted");

        System.out.println("Positive create issue test passed for: " + summary);
    }
}