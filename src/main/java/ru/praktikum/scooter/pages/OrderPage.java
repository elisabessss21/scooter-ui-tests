
package ru.praktikum.scooter.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/** Page Object первого шага заказа — «Для кого самокат». */
public class OrderPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Поле «Имя».
    private final By nameInput =
            By.xpath("//input[@placeholder='* Имя']");

    // Поле «Фамилия».
    private final By surnameInput =
            By.xpath("//input[@placeholder='* Фамилия']");

    // Поле «Адрес: куда привезти заказ».
    private final By addressInput = By.xpath(
            "//input[@placeholder='* Адрес: куда привезти заказ']");

    // Поле «Станция метро».
    private final By metroInput =
            By.xpath("//input[@placeholder='* Станция метро']");

    // Станция метро из выпадающего списка.
    private By metroOption(String stationName) {
        return By.xpath(
                "//*[contains(@class,'select-search__option') "
                        + "and normalize-space(.)='" + stationName + "']"
        );
    }

    // Поле «Телефон: на него позвонит курьер».
    private final By phoneInput = By.xpath(
            "//input[@placeholder='* Телефон: на него позвонит курьер']");

    // Кнопка «Далее» для перехода ко второму шагу заказа.
    private final By nextButton =
            By.xpath("//button[normalize-space()='Далее']");

    public OrderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver, Duration.ofSeconds(15)
        );
    }

    public void fillCustomerInfo(
            String name,
            String surname,
            String address,
            String metro,
            String phone
    ) {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(nameInput)
        ).sendKeys(name);

        driver.findElement(surnameInput).sendKeys(surname);
        driver.findElement(addressInput).sendKeys(address);
        driver.findElement(metroInput).sendKeys(metro);

        // Выбираем станцию из списка подсказок.
        wait.until(
                ExpectedConditions.elementToBeClickable(
                        metroOption(metro)
                )
        ).click();

        driver.findElement(phoneInput).sendKeys(phone);
    }

    public void clickNext() {
        wait.until(
                ExpectedConditions.elementToBeClickable(nextButton)
        ).click();
    }
}
