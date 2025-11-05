package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage{
    private final By usernameField = By.name("username");
    private final By passwordField = By.name("password");
    private final By submitButton = By.cssSelector("button[type='submit']");
    private final By errorMessage = By.xpath("//div[contains(text(), 'Некорректное имя пользователя или пароль.')]");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void login(String username, String password) {
        sendKeysToElement(usernameField, username);
        sendKeysToElement(passwordField, password);
        clickElement(submitButton);
    }

    public boolean isErrorDisplayed() {
        return isElementDisplayed(errorMessage);
    }
}