package org.example;

import org.testng.annotations.*;

import static org.testng.Assert.assertEquals;


public class PageTitleTest extends BasePage {
    @Test
    public void shouldDisplayThePageTitleCorrectly() {
        driver.get(baseUrl);
        String title = driver.getTitle();
        assertEquals(title, "ShareLane: Learn to Test");
    }
}
