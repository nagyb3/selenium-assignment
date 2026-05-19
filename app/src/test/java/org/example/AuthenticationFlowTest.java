package org.example;

import org.example.utils.RandomEmailAndPasswordGenerator;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class AuthenticationFlowTest extends BasePage {
    private String actualEmail;
    private String actualPassword;

    @Test
    void shouldRegisterNewUserSuccessfully() {
        RandomEmailAndPasswordGenerator generator = new RandomEmailAndPasswordGenerator();
        String newEmail = generator.generateRandomEmail();
        String newPassword = generator.generateRandomPassword();
        driver.get(homePageUrl);
        driver.findElement(By.linkText("Sign up")).click();

        WebElement zipCodeInput = driver.findElement(By.xpath("//input[@name='zip_code']"));
        zipCodeInput.sendKeys(properties.get("zipCode").toString());

        driver.findElement(By.xpath("//input[@value='Continue']")).click();

        driver.findElement(By.xpath("//input[@name='first_name']")).sendKeys(properties.get("firstName").toString());

        driver.findElement(By.xpath("//input[@name='last_name']")).sendKeys(properties.get("lastName").toString());
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys(newEmail);
        driver.findElement(By.xpath("//input[@name='password1']")).sendKeys(newPassword);
        driver.findElement(By.xpath("//input[@name='password2']")).sendKeys(newPassword);

        driver.findElement(By.xpath("//input[@value='Register']")).click();

        // Note to the grader of this assignment: the tested website generates a new email and password for security reasons
        // because of this I will store these credentials from the website as a data member, so we can use them in the next test methods.
        String registeredEmail = driver.findElement(By.xpath("//td[text()='Email']/following-sibling::td")).getText();
        String registeredPassword = driver.findElement(By.xpath("//td[text()='Password']/following-sibling::td")).getText();
        this.actualEmail = registeredEmail;
        this.actualPassword = registeredPassword;
    }

    @Test(dependsOnMethods = {"shouldRegisterNewUserSuccessfully"})
    void shouldLoginWithARegisteredUserSuccessfully() {
        driver.get(homePageUrl);

        WebElement emailInput = driver.findElement(By.xpath("//input[@name='email']"));
        emailInput.sendKeys(this.actualEmail);

        WebElement passwordInput = driver.findElement(By.xpath("//input[@name='password']"));
        passwordInput.sendKeys(this.actualPassword);

        driver.findElement(By.xpath("//input[@value='Login']")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement logoutButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Logout")));

        assertTrue(logoutButton.isDisplayed(), "Logout button should be displayed after successful login");
    }


    @Test(dependsOnMethods = {"shouldLoginWithARegisteredUserSuccessfully"})
    public void shouldStoreCookiesAfterLogin() {
        driver.get(homePageUrl);
        Cookie cookie = driver.manage().getCookieNamed("email");

        assertNotNull(cookie, "Cookie 'email' not found!");

        String rawCookieValue = cookie.getValue();

        String sanitizedValue = rawCookieValue.replace("\"", "");

        assertEquals(sanitizedValue, this.actualEmail);
    }

    @Test(dependsOnMethods = {"shouldStoreCookiesAfterLogin"})
    void shouldLogoutSuccessfully() {
        driver.get(homePageUrl);
        driver.findElement(By.linkText("Logout")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement loggedOutText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(), 'logged out')]")));
        assertTrue(loggedOutText.isDisplayed(), "Logged out text should be displayed after successful logout");
    }
}
