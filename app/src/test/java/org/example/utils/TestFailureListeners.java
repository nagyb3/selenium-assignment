package org.example.utils;

import org.example.BasePage;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestFailureListeners implements ITestListener {
    @Override
    public void onTestFailure(ITestResult result) {
        Object currentClass = result.getInstance();

        WebDriver driver = ((BasePage) currentClass).driver;

        if (driver != null) {
            try {
                File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

                Path targetDir = Paths.get("build", "screenshots");
                Files.createDirectories(targetDir);

                String fileName = result.getName() + "_" + System.currentTimeMillis() + ".png";
                Path targetPath = targetDir.resolve(fileName);

                Files.move(screenshotFile.toPath(), targetPath);
                System.out.println(targetPath.toAbsolutePath());
            } catch (Exception e) {
                System.out.println("Failed to create screenshot: " + e.getMessage());
            }
        }
    }
}