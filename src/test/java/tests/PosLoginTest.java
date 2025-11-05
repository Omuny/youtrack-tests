package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import pages.LoginPage;
import pages.DashboardPage;

public class PosLoginTest extends BaseTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private DashboardPage dashboardPage;

    @Test(priority = 1)
    public void testPositiveLogin() {
        driver = getDriver();
        loginPage = new LoginPage(driver);
        dashboardPage = new DashboardPage(driver);

        loginPage.login(LOGIN, PASSWORD);
        Assert.assertTrue(dashboardPage.isDashboardLoaded(), "Dashboard not loaded after positive login");

        System.out.println("Positive login test passed");
    }
}