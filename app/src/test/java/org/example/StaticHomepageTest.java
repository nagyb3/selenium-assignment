package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;

public class StaticHomepageTest extends BasePage {
    @Test
    public void homepageHeaderShouldDisplayItsContentSuccessfully() {
        driver.get(homePageUrl);

        WebElement logoLink = driver.findElement(By.xpath("//a[contains(@href, './main.py')]"));
        WebElement logoImg = logoLink.findElement(By.tagName("img"));
        Assert.assertTrue(logoImg.getAttribute("src").endsWith("/images/logo.jpg"), "Logo image source is incorrect.");

        WebElement cartLink = driver.findElement(By.xpath("//a[contains(@href, './shopping_cart.py')]"));
        Assert.assertTrue(cartLink.getText().contains("Shopping Cart"), "Shopping Cart link text is missing.");
        WebElement cartImg = cartLink.findElement(By.tagName("img"));
        Assert.assertTrue(cartImg.getAttribute("src").endsWith("/images/shopping_cart.gif"), "Shopping cart icon source is incorrect.");
    }

    @Test
    public void homepageShouldDisplaySearchComponentSuccessfully() {
        driver.get(homePageUrl);
        WebElement keywordInput = driver.findElement(By.name("keyword"));
        WebElement searchButton = driver.findElement(By.xpath("//input[@value='Search']"));

        Assert.assertTrue(keywordInput.isDisplayed(), "Search keyword input field is not visible.");
        Assert.assertEquals(keywordInput.getAttribute("size"), "50", "Keyword input field size is incorrect.");
        Assert.assertTrue(searchButton.isDisplayed(), "Search submit button is not visible.");
    }

    @Test
    public void homepageShouldDisplayLoginComponentSuccessfully() {
        driver.get(homePageUrl);

        WebElement emailInput = driver.findElement(By.name("email"));
        WebElement passwordInput = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.xpath("//input[@value='Login']"));
        WebElement signUpLink = driver.findElement(By.xpath("//a[contains(@href, './register.py')]"));
        String pageSource = driver.getPageSource();
        Assert.assertTrue(pageSource.contains("Email"), "Email text label missing.");
        Assert.assertTrue(pageSource.contains("Password"), "Password text label missing.");

        Assert.assertTrue(emailInput.isDisplayed(), "Email input field is missing.");
        Assert.assertEquals(passwordInput.getAttribute("type"), "password", "Password field type must be secure.");
        Assert.assertTrue(loginButton.isDisplayed(), "Login button is missing.");
        Assert.assertEquals(signUpLink.getText(), "Sign up", "Sign up link text matches incorrectly.");
    }

    @Test
    public void homepageFooterShouldDisplayItsContentSuccessfully() {
        driver.get(homePageUrl);

        WebElement qaTutorLink = driver.findElement(By.linkText("QATutor.com"));
        WebElement testPortalLink = driver.findElement(By.linkText("Test Portal"));
        WebElement pythonSourceLink = driver.findElement(By.linkText("Python Source File"));
        WebElement contactLink = driver.findElement(By.linkText("Contact"));

        Assert.assertEquals(qaTutorLink.getAttribute("href"), "https://www.qatutor.com/");
        Assert.assertTrue(testPortalLink.getAttribute("href").endsWith("/test_portal.html"));
        Assert.assertTrue(pythonSourceLink.getAttribute("href").endsWith("/source_files/main_bright_normal.html"));
        Assert.assertEquals(contactLink.getAttribute("href"), "mailto:roman@qatutor.com");
    }
}
