package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class BaseTest {
    protected WebDriver driver;
    protected static final String BASE_URL = "http://193.233.193.42:9091";
    protected static final String PROJECT_ID = "QA"; // Для префикса задач, например, QA-57
    protected static String LOGIN;
    protected static String PASSWORD;

    static {
        try {
            Properties props = new Properties();
            FileInputStream fis = new FileInputStream("local.properties");
            props.load(fis);
            LOGIN = props.getProperty("login");
            PASSWORD = props.getProperty("password");
            if (LOGIN == null || PASSWORD == null) {
                throw new RuntimeException("Login or password not found in local.properties");
            }
            fis.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load local.properties: " + e.getMessage());
        }
    }

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless"); // Раскомментировать, если не нужно видеть браузер
        options.addArguments("--window-size=1920,1080");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(BASE_URL);
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            takeScreenshot(result.getName());
        }
        if (driver != null) {
            driver.quit();
        }
    }

    private void takeScreenshot(String testName) {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshot, new File("target/screenshots/" + testName + ".png"));
            System.out.println("Screenshot saved for failed test: " + testName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}