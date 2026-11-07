package ru.praktikum.scooter.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/** Page Object второго шага заказа — «Про аренду», включая модальные окна. */
public class RentPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Поле выбора даты доставки.
    private final By dateInput = By.xpath(
            "//input[@placeholder='* Когда привезти самокат']");

    // Стрелка перехода к следующему месяцу в календаре.
    private final By nextMonthButton = By.cssSelector(
            ".react-datepicker__navigation--next");

    // 15-е число открытого месяца (не захватываем дни соседних месяцев).
    private final By fifteenthDay = By.cssSelector(
            ".react-datepicker__day--015:not(.react-datepicker__day--outside-month)");

    // Выпадающий список «Срок аренды».
    private final By rentalPeriodDropdown = By.className("Dropdown-control");

    // Конкретный срок аренды в выпадающем списке.
    private By rentalPeriodOption(String period) {
        return By.xpath("//*[contains(@class,'Dropdown-option') "
                + "and normalize-space(.)='" + period + "']");
    }

    // Чекбокс цвета самоката, который можно выбрать по подписи.
    private By colorCheckbox(String color) {
        return By.xpath("//label[normalize-space(.)='" + color + "']");
    }

    // Поле «Комментарий для курьера».
    private final By commentInput = By.xpath(
            "//input[@placeholder='Комментарий для курьера']");

    // Кнопка «Заказать» ВНУТРИ формы аренды, не в верхнем меню сайта.
    private final By submitOrderButton = By.xpath(
            "//div[contains(@class,'Order_Buttons')]//button[normalize-space()='Заказать']");

    // Кнопка «Да» в модальном окне подтверждения заказа.
    private final By confirmOrderButton = By.xpath(
            "//div[contains(@class,'Order_Modal')]//button[normalize-space()='Да']");

    // Заголовок окна об успешном создании заказа.
    private final By successHeader = By.xpath(
            "//div[contains(@class,'Order_ModalHeader') "
                    + "and contains(normalize-space(.),'Заказ оформлен')]");

    public RentPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void selectDeliveryDate() {
        // 15-е число следующего месяца всегда позже сегодняшней даты.
        // Если приложение ограничивает период доставки, этот выбор нужно уточнить.
        wait.until(ExpectedConditions.elementToBeClickable(dateInput)).click();
        wait.until(ExpectedConditions.elementToBeClickable(nextMonthButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(fifteenthDay)).click();
    }

    public void selectRentalPeriod(String period) {
        wait.until(ExpectedConditions.elementToBeClickable(rentalPeriodDropdown)).click();
        wait.until(ExpectedConditions.elementToBeClickable(rentalPeriodOption(period))).click();
    }

    public void selectColor(String color) {
        wait.until(ExpectedConditions.elementToBeClickable(colorCheckbox(color))).click();
    }

    public void enterComment(String comment) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(commentInput))
                .sendKeys(comment);
    }

    public void submitAndConfirmOrder() {
        wait.until(ExpectedConditions.elementToBeClickable(submitOrderButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(confirmOrderButton)).click();
    }

    public String getSuccessMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successHeader))
                .getText();
    }
}
