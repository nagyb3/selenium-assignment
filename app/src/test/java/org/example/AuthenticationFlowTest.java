package org.example;

import org.example.utils.GlobalStore;
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

    @Test
    void reigsterNewUser() {
        RandomEmailAndPasswordGenerator generator = new RandomEmailAndPasswordGenerator();
        String newEmail = generator.generateRandomEmail();
        String newPassword = generator.generateRandomPassword();
        driver.get(baseUrl);
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
        // because of this I will store these randomly generated credentials in a global store to be used in other test files.
        String registeredEmail = driver.findElement(By.xpath("//td[text()='Email']/following-sibling::td")).getText();
        String registeredPassword = driver.findElement(By.xpath("//td[text()='Password']/following-sibling::td")).getText();
        GlobalStore.actualEmail = registeredEmail;
        GlobalStore.actualPassword = registeredPassword;
    }

    @Test(dependsOnMethods = {"reigsterNewUser"})
    void loginTest() {
        driver.get(baseUrl);

        WebElement emailInput = driver.findElement(By.xpath("//input[@name='email']"));
        emailInput.sendKeys(GlobalStore.actualEmail);

        WebElement passwordInput = driver.findElement(By.xpath("//input[@name='password']"));
        passwordInput.sendKeys(GlobalStore.actualPassword);

        driver.findElement(By.xpath("//input[@value='Login']")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement logoutButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Logout")));

        assertTrue(logoutButton.isDisplayed(), "Logout button should be displayed after successful login");
    }


    @Test(dependsOnMethods = {"loginTest"})
    public void testVerifyCookie() {
        driver.get(baseUrl);
        Cookie cookie = driver.manage().getCookieNamed("email");

        assertNotNull(cookie, "Cookie 'email' not found!");

        String rawCookieValue = cookie.getValue();

        String sanitizedValue = rawCookieValue.replace("\"", "");

        assertEquals(sanitizedValue, GlobalStore.actualEmail);
    }

    @Test(dependsOnMethods = {"testVerifyCookie"})
    void logoutTest() {
        driver.get(baseUrl);
        driver.findElement(By.linkText("Logout")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement loggedOutText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(), 'logged out')]")));
        assertTrue(loggedOutText.isDisplayed(), "Logged out text should be displayed after successful logout");
    }
}
