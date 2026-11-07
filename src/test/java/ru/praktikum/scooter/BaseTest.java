package ru.praktikum.scooter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/** Общая настройка и закрытие браузера для каждого теста. */
public abstract class BaseTest {
    protected WebDriver driver;

    @BeforeEach
    public void setUp() {
        String browser = System.getProperty("browser", "chrome");
        if ("firefox".equalsIgnoreCase(browser)) {
            driver = new FirefoxDriver();
        } else if ("chrome".equalsIgnoreCase(browser)) {
            driver = new ChromeDriver();
        } else {
            throw new IllegalArgumentException("Неподдерживаемый браузер: " + browser);
        }
        driver.manage().window().maximize();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
