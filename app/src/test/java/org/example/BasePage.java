package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.testng.annotations.*;

public class BasePage {
    public WebDriver driver;
    public String baseUrl;
    public Properties properties;

    @BeforeClass
    public void setUp() throws IOException {
        properties = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream stream = loader.getResourceAsStream("config.properties");
        properties.load(stream);
        baseUrl = properties.get("baseUrl").toString();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--window-size=1920,1080");
        this.driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);
        this.driver.manage().window().maximize();
    }

    @AfterClass
    public void close() {
        if (this.driver != null) {
            this.driver.quit();
        }
    }
}
