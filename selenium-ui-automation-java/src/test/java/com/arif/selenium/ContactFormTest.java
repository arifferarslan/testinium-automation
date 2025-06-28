package com.arif.selenium.tests;

import io.qameta.allure.*;
import io.qameta.allure.junit5.AllureJunit5;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(AllureJunit5.class)
@Epic("Contact Form Tests")
@Feature("Contact form validation")
public class ContactFormTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @Step("Setup Chrome Driver and open browser")
    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @Step("Close browser")
    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @Story("Valid Email Submission")
    @Description("Form submission with valid email shows success message")
    public void testValidEmailShowsSuccess() {
        stepNavigateToPage();
        stepAcceptCookiesIfPresent();
        stepSwitchToIframe();
        stepFillEmailAndMessage();
        stepSubmitForm();
        stepVerifySuccessMessage();
    }

    @Step("Navigate to contact us page")
    private void stepNavigateToPage() {
        driver.get("https://testinium.com/#contact-us");
    }

    @Step("Accept cookies if the popup appears")
    private void stepAcceptCookiesIfPresent() {
        try {
            WebElement acceptBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Accept All')]")));
            acceptBtn.click();
        } catch (TimeoutException | NoSuchElementException ignored) {}
    }

    @Step("Switch to form iframe")
    private void stepSwitchToIframe() {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("hs-form-iframe-0")));
    }

    @Step("Fill email and message fields")
    private void stepFillEmailAndMessage() {
        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email-8a32b90b-d213-4cff-861a-557a32f50b76")));
        emailInput.clear();
        emailInput.sendKeys("test12314@gmail.com");

        WebElement messageInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("message-8a32b90b-d213-4cff-861a-557a32f50b76")));
        messageInput.sendKeys("This is a test message from Selenium.");
    }

    @Step("Submit the form")
    private void stepSubmitForm() {
        WebElement submitButton = driver.findElement(By.cssSelector("input[type='submit']"));
        submitButton.click();
    }

    @Step("Verify success message or success text presence")
    private void stepVerifySuccessMessage() {
        boolean success = wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".submitted-message")),
                ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), "thank you"),
                ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), "success")
        ));
        assertTrue(success, "Başarı mesajı DOM'da görünmeli veya sayfa içeriğinde 'thank you' ya da 'success' ifadesi bulunmalı.");
    }
}
