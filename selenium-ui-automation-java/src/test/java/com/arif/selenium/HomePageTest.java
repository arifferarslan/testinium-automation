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
@Epic("Homepage Tests")
@Feature("Testinium Homepage UI")
public class HomePageTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @Step("Setup ChromeDriver and open browser")
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
    @Story("Homepage title check")
    @Description("Check that the homepage loads with the correct title")
    public void testHomePageLoads() {
        navigateToHomePage();
        String expectedTitle = "Testinium: End-to-End Software Testing and QA Solutions";
        String actualTitle = driver.getTitle();
        assertEquals(expectedTitle, actualTitle, "Homepage title should match expected");
    }

    @Test
    @Story("Homepage URL validation")
    @Description("Verify the homepage URL contains 'testinium.com'")
    public void testHomePageURL() {
        navigateToHomePage();
        String currentURL = driver.getCurrentUrl();
        assertTrue(currentURL.contains("testinium.com"), "URL should contain 'testinium.com'");
    }

    @Test
    @Story("Homepage logo visibility")
    @Description("Verify that the homepage logo is visible")
    public void testLogoIsDisplayed() {
        navigateToHomePage();
        WebElement logo = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("img.logo")));
        assertTrue(logo.isDisplayed(), "Logo should be visible on homepage");
    }

    @Test
    @Story("Navigation menu interaction")
    @Description("Open Services dropdown and verify Consultancy link navigation")
    public void testClickServicesMenu() {
        navigateToHomePage();

        WebElement servicesMenu = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.nav-dropdown-title")));
        servicesMenu.click();

        WebElement consultancyLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Consultancy")));
        assertTrue(consultancyLink.isDisplayed(), "Consultancy link should be visible in dropdown");

        consultancyLink.click();

        wait.until(ExpectedConditions.urlContains("consultancy"));
        assertTrue(driver.getCurrentUrl().contains("consultancy"), "URL should contain 'consultancy' after navigation");
    }

    @Step("Navigate to the homepage")
    private void navigateToHomePage() {
        driver.get("https://testinium.com/");
    }
}
