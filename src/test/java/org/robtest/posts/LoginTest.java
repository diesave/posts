package org.robtest.posts;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.robtest.AdditionalLogger;
import org.robtest.JUnitExtention;

import java.io.ByteArrayInputStream;

@Story("Работа с корзиной")
public class LoginTest {
    WebDriver driver;
    LoginPage loginPage;

    @RegisterExtension
    TestWatcher testWatcher = new JUnitExtention();

    @BeforeAll
    static void registerDriver() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void initDriver() {
        driver = new EventFiringDecorator(new AdditionalLogger()).decorate(new ChromeDriver());
        loginPage = new LoginPage(driver);
        driver.get("https://test-stand.gb.ru/login");
    }

    @Test
    @Feature("Login with correct password")
    public void loginWithCorrectPasswordTest(){
          loginPage.login("RobTest", "f82ad91aea");
          loginPage.checkLoggedIn();
    }

    @Test
    @Feature("Login with incorrect password")
    public void loginWithIncorrectPasswordTest(){
        loginPage.login("RobTest", "wrong password");
        loginPage.checkInvalidCredentials();
    }

    @Test
    @Feature("Login with empty name and password")
    public void loginWithEmptyPasswordTest(){
        loginPage.login("", "");
        loginPage.checkInvalidCredentials();
    }


    @AfterEach
    void tearDown() {
        LogEntries logs = driver.manage().logs().get(LogType.BROWSER);
        for (LogEntry log : logs) {
            Allure.addAttachment("Browser stacktrace:\n", log.getMessage());
        }
        ((JUnitExtention) testWatcher)
                .setScreenShot(new ByteArrayInputStream(((TakesScreenshot) driver)
                        .getScreenshotAs(OutputType.BYTES)));
        driver.quit();
    }
}