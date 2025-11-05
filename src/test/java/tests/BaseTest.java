package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import org.apache.commons.io.FileUtils;

import helper.LocalReader;
import pages.DashboardPage;
import pages.LoginPage;

public class BaseTest {
    protected static ThreadLocal<WebDriver> threadDriver = new ThreadLocal<>();
    protected static String BASE_URL;
    protected static String LOGIN;
    protected static String PASSWORD;

    static {
        // Инициализация статических полей через LocalReader
        BASE_URL = LocalReader.getBaseUrl();
        LOGIN = LocalReader.getLogin();
        PASSWORD = LocalReader.getPassword();
    }

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless"); // Раскомментировать, если не нужно видеть браузер
        options.addArguments("--window-size=1920,1080");
        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(BASE_URL);
        threadDriver.set(driver); // Установка для этого потока
    }

    @BeforeMethod(onlyForGroups = "requiresLogin", dependsOnMethods = "setUp")
    public void loginIfNeeded() {
        // Для тестов, требующих авторизации (кроме login тестов)
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.login(LOGIN, PASSWORD);
        DashboardPage dashboardPage = new DashboardPage(getDriver());
        Assert.assertTrue(dashboardPage.isDashboardLoaded(), "Dashboard not loaded after login");
    }

    protected WebDriver getDriver() {
        return threadDriver.get();
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        WebDriver driver = getDriver();
        if (ITestResult.FAILURE == result.getStatus()) {
            takeScreenshot(result.getName());
        }
        if (driver != null) {
            driver.quit();
            threadDriver.remove(); // Очистка driver ядра
        }
    }

    private void takeScreenshot(String testName) {
        WebDriver driver = getDriver();
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshot, new File("target/screenshots/" + testName + ".png"));
            System.out.println("Screenshot saved for failed test: " + testName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}