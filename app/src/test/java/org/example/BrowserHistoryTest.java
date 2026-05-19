package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class BrowserHistoryTest extends BasePage {
    @Test
    public void shouldHandleNavigatingBackInBrowserHistorySuccessfully() {
        driver.get(homePageUrl);

        String firstItemXpath = "//table[@align='center']//table[1]//a";
        WebElement firstItemImage = driver.findElement(By.xpath(firstItemXpath));
        firstItemImage.click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        assertTrue(
            wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(homePageUrl))),
            "The URL should have changed after clicking the first item image."
        );

        driver.navigate().back();

        assertTrue(
            wait.until(ExpectedConditions.urlToBe(homePageUrl)),
            "The URL should be the same as the base URL after navigating back."
        );
    }
}
