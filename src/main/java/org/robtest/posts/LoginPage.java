package org.robtest.posts;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BaseView {
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//*[@id=\"login\"]/div[1]/label/input")
    private WebElement userNameField;

    @FindBy(xpath = "//*[@id=\"login\"]/div[2]/label/input")
    private WebElement passwordField;

    @FindBy(xpath = "//*[@id=\"login\"]/div[3]/button/span")
    private WebElement loginButton;

    @Step("Login")
    public void setUserName(String userName) {
        userNameField.sendKeys(userName);
    }

    public void setPasswordField(String password) {
        passwordField.sendKeys(password);
    }

    public void clickLoginButton() {
        loginButton.click();
    }

    public void login(String login, String password) {
        setUserName(login);
        setPasswordField(password);
        clickLoginButton();
    }

    @Step("LoggedIn check")
    public void checkLoggedIn() {

        String loginName = "//*[@id=\"app\"]/main/nav/ul/li[3]/a";
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(loginName)));
        String text = driver.findElement(By.xpath(loginName)).getText();
        Assertions.assertEquals("Hello, RobTest", text);
    }

    @Step("Check invalid credentials")
    public void checkInvalidCredentials() {
        String invalidCredentials = "//*[@id=\"app\"]/main/div/div/div[2]/p[1]";
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(invalidCredentials)));
        String text = driver.findElement(By.xpath(invalidCredentials)).getText();
        Assertions.assertEquals("Invalid credentials.", text);
    }

//        List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'" + text + "')]"));
//        Assert.assertTrue("Text not found!", list.size() > 0);
//        String bodyText = driver.findElement(By.tagName("body")).getText();
//        Assert.assertTrue("Text not found!", bodyText.contains(text));
//        driver.getPageSource().contains("Hello, RobTest");
}