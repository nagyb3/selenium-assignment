package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MultiplePageTest extends BasePage {
    public String[] testPaths = {
            "/cgi-bin/show_book.py?book_id=1",
            "/cgi-bin/show_book.py?book_id=2",
            "/cgi-bin/show_book.py?book_id=3",
            "/cgi-bin/show_book.py?book_id=4"
    };

    public String[] exectedItemNames = {
            "Great Expectations",
            "White Fang",
            "The Adventures of Tom Sawyer",
            "War and Peace"
    };

    @Test
    void shouldDisplayMultipleBookItemsDataSuccessfully() {
        for (int i = 0; i < testPaths.length; i++) {
            driver.get(baseUrl + testPaths[i]);
            String expectedTitle = exectedItemNames[i];

            WebElement bookTitle = driver.findElement(By.xpath("//p[contains(text(), '" + expectedTitle + "')]"));

            Assert.assertTrue(bookTitle.isDisplayed(), "Book title '" + expectedTitle + "' should be displayed");
        }
    }
}