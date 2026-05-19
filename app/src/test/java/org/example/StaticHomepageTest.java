package org.example;

import org.testng.annotations.*;

public class StaticHomepageTest extends BasePage {
    @Test
    public void testStaticHomepage() {
        driver.get(homePageUrl);
        // TODO 
    }
}
