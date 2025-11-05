package tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.IssueDetailsPage;
import pages.IssuesPage;
import pages.LoginPage;
import pages.NewIssuePage;

import java.util.Set;

public class YouTrackTests extends BaseTest {

    private String originalWindow;

    @BeforeMethod(onlyForGroups = "full")
    public void loginIfNeeded() {
        // Для тестов, требующих авторизации (кроме login тестов)
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.login(LOGIN, PASSWORD);
        DashboardPage dashboardPage = new DashboardPage(getDriver());
        Assert.assertTrue(dashboardPage.isDashboardLoaded(), "Dashboard not loaded after login");
    }

    @Test(groups = "login", priority = 1)
    public void testPositiveLogin() {
        // Без логина в @Before, так как тест авторизации
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.login(LOGIN, PASSWORD);
        DashboardPage dashboardPage = new DashboardPage(getDriver());
        Assert.assertTrue(dashboardPage.isDashboardLoaded(), "Dashboard not loaded after positive login");
        System.out.println("Positive login test passed");
    }

    @Test(groups = "login", priority = 0)
    public void testNegativeLogin() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.login(LOGIN, "WrongPassword");
        Assert.assertTrue(loginPage.isErrorDisplayed(), "Error message not displayed for negative login");
        System.out.println("Negative login test passed");
    }

    @DataProvider(name = "issueData")
    public Object[][] issueData() {
        return new Object[][] {
                {"Test Summary 1", "Test Description 1"},
                {"Test Summary 2", "Test Description 2"}
        };
    }

    @Test(dataProvider = "issueData", groups = "full", priority = 3)
    public void testPositiveCreateIssue(String summary, String description) {
        DashboardPage dashboardPage = new DashboardPage(getDriver());
        originalWindow = getDriver().getWindowHandle();
        Set<String> originalWindows = getDriver().getWindowHandles();

        dashboardPage.clickCreateNewIssue();
        NewIssuePage newIssuePage = new NewIssuePage(getDriver());
        newIssuePage.enterSummary(summary);
        newIssuePage.enterDescription(description);
        newIssuePage.clickCreate();

        String issueId = newIssuePage.getCreatedIssueId();
        Assert.assertNotNull(issueId, "Issue ID not extracted after creation");
        System.out.println("Created issue: " + issueId);

        // Переключитесь обратно для проверки и очистки
        getDriver().close(); // Закрыть новую вкладку
        getDriver().switchTo().window(originalWindow);
        dashboardPage.navigateToIssues();
        IssuesPage issuesPage = new IssuesPage(getDriver());
        Assert.assertTrue(issuesPage.isIssuePresent(issueId), "Issue not found after creation");

        // Очистка
        issuesPage.searchIssue(issueId);
        IssueDetailsPage issueDetailsPage = new IssueDetailsPage(getDriver());
        issueDetailsPage.deleteIssue();

        System.out.println("Positive create issue test passed for: " + summary);
    }

    @Test(groups = "full", priority = 2)
    public void testNegativeCreateIssue() {
        DashboardPage dashboardPage = new DashboardPage(getDriver());
        originalWindow = getDriver().getWindowHandle();

        dashboardPage.clickCreateNewIssue();
        NewIssuePage newIssuePage = new NewIssuePage(getDriver());
        // Оставляем заголовок пустым
        newIssuePage.enterDescription("Test Description");
        Assert.assertFalse(newIssuePage.isCreateButtonEnabled(), "Create button should be disabled with empty summary");

        // Закроем вкладку, очистка не требуется
        getDriver().close();
        getDriver().switchTo().window(originalWindow);
        System.out.println("Negative create issue test passed");
    }

    @Test(groups = "full", priority = 4)
    public void testPositiveEditIssue() {
        // Сначала создаем тестовую задачу
        DashboardPage dashboardPage = new DashboardPage(getDriver());
        originalWindow = getDriver().getWindowHandle();

        dashboardPage.clickCreateNewIssue();
        NewIssuePage newIssuePage = new NewIssuePage(getDriver());
        newIssuePage.enterSummary("Original Summary");
        newIssuePage.enterDescription("Original Description");
        newIssuePage.clickCreate();

        String issueId = newIssuePage.getCreatedIssueId();

        getDriver().close();
        getDriver().switchTo().window(originalWindow);

        // Тестируем редактирование
        dashboardPage.navigateToIssues();
        IssuesPage issuesPage = new IssuesPage(getDriver());
        issuesPage.searchIssue(issueId);

        IssueDetailsPage issueDetailsPage = new IssueDetailsPage(getDriver());
        issueDetailsPage.clickEdit();
        issueDetailsPage.updateSummary("Updated Summary");
        issueDetailsPage.clickSave();

        Assert.assertEquals(issueDetailsPage.getDisplayedSummary(), "Updated Summary", "Summary not updated");

        // Очистка
        issueDetailsPage.deleteIssue();

        // Проверка удаления
        getDriver().navigate().to(BASE_URL + "/issues");
        issuesPage = new IssuesPage(getDriver());
        Assert.assertFalse(issuesPage.isIssuePresent(issueId), "Issue not deleted");

        System.out.println("Positive edit issue test passed");
    }

    @Test(groups = "full", priority = 5)
    public void testPositiveDeleteIssue() {
        // Сначала создаем тестовую задачу
        DashboardPage dashboardPage = new DashboardPage(getDriver());
        originalWindow = getDriver().getWindowHandle();

        dashboardPage.clickCreateNewIssue();
        NewIssuePage newIssuePage = new NewIssuePage(getDriver());
        newIssuePage.enterSummary("To Delete Summary");
        newIssuePage.enterDescription("To Delete Description");
        newIssuePage.clickCreate();

        String issueId = newIssuePage.getCreatedIssueId();

        getDriver().close();
        getDriver().switchTo().window(originalWindow);

        // Тестируем удаление
        dashboardPage.navigateToIssues();
        IssuesPage issuesPage = new IssuesPage(getDriver());
        issuesPage.searchIssue(issueId);

        IssueDetailsPage issueDetailsPage = new IssueDetailsPage(getDriver());
        issueDetailsPage.deleteIssue();

        // Проверка удаления
        getDriver().navigate().to(BASE_URL + "/issues");
        issuesPage = new IssuesPage(getDriver());
        Assert.assertFalse(issuesPage.isIssuePresent(issueId), "Issue not deleted");

        System.out.println("Positive delete issue test passed");
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod() {
        // В целом не нужен
    }
}