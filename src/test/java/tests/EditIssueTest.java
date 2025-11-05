package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import pages.DashboardPage;
import pages.IssueDetailsPage;
import pages.IssuesPage;
import pages.NewIssuePage;

public class EditIssueTest extends BaseTest {
    private WebDriver driver;
    private String originalWindow;
    private DashboardPage dashboardPage;
    private NewIssuePage newIssuePage;
    private IssuesPage issuesPage;
    private IssueDetailsPage issueDetailsPage;
    private String issueId;

    @Test(groups = "requiresLogin", priority = 4)
    public void testPositiveEditIssue() {
        driver = getDriver();
        originalWindow = driver.getWindowHandle();
        dashboardPage = new DashboardPage(driver);
        newIssuePage = new NewIssuePage(driver);
        issuesPage = new IssuesPage(driver);
        issueDetailsPage = new IssueDetailsPage(driver);

        // Сначала создаем тестовую задачу
        dashboardPage.clickCreateNewIssue();
        newIssuePage.enterSummary("Original Summary");
        newIssuePage.enterDescription("Original Description");
        newIssuePage.clickCreate();

        issueId = newIssuePage.getCreatedIssueId();
        Assert.assertNotNull(issueId, "Issue ID not extracted after creation");

        // Закроем второе и переключимся на базовое окно
        driver.close();
        driver.switchTo().window(originalWindow);

        // Тестируем редактирование
        dashboardPage.navigateToIssues();
        issuesPage.searchIssue(issueId);
        issueDetailsPage.clickEdit();
        issueDetailsPage.updateSummary("Updated Summary");
        issueDetailsPage.clickSave();
        Assert.assertEquals(issueDetailsPage.getDisplayedSummary(), "Updated Summary", "Summary not updated");

        // Очистка
        issueDetailsPage.deleteIssue();

        // Проверка удаления
        driver.navigate().to(BASE_URL + "/issues");
        issuesPage = new IssuesPage(driver);
        Assert.assertFalse(issuesPage.isIssuePresent(issueId), "Issue not deleted");

        System.out.println("Positive edit issue test passed");
    }
}