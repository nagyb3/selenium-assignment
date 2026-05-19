package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Parameters;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.testng.annotations.*;

public class BasePage {
    public WebDriver driver;
    public String baseUrl;
    public String homePageUrl;
    public Properties properties;
    public String remoteWebDriverUrl;

    @BeforeClass
    @Parameters({"browser"})
    public void setUp(@Optional("chrome") String browser) throws IOException {
        properties = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream stream = loader.getResourceAsStream("config.properties");
        properties.load(stream);
        baseUrl = properties.get("baseUrl").toString();
        homePageUrl = properties.get("homePageUrl").toString();
        remoteWebDriverUrl = properties.getProperty("remoteWebDriverUrl");

        switch (browser.toLowerCase()) {
            case "chrome":
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--headless=new");
                options.addArguments("--window-size=1920,1080");
                this.driver = new RemoteWebDriver(new URL(remoteWebDriverUrl), options);
                break;
            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--headless");
                firefoxOptions.addArguments("--width=1920");
                firefoxOptions.addArguments("--height=1080");
                this.driver = new RemoteWebDriver(new URL(remoteWebDriverUrl), firefoxOptions);
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
        this.driver.manage().window().maximize();
    }

    @AfterClass
    public void close() {
        if (this.driver != null) {
            this.driver.quit();
        }
    }
}
