package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import pages.LoginPage;

public class NegLoginTest extends BaseTest {
    private LoginPage loginPage;

    @Test(priority = 0)
    public void testNegativeLogin() {
        loginPage = new LoginPage(getDriver());

        loginPage.login(LOGIN, "WrongPassword");
        Assert.assertTrue(loginPage.isErrorDisplayed(), "Error message not displayed for negative login");

        System.out.println("Negative login test passed");
    }
}