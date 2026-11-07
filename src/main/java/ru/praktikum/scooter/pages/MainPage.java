package ru.praktikum.scooter.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/** Page Object главной страницы «Яндекс.Самоката». */
public class MainPage {

    public static final String URL = "https://qa-scooter.education-services.ru/";

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Кнопка «Заказать» в верхнем меню главной страницы.
    private final By topOrderButton = By.xpath(
            "//div[contains(@class,'Header_Nav')]//button[normalize-space()='Заказать']");

    // Кнопка «Заказать» в нижней части главной страницы.
    private final By bottomOrderButton = By.xpath(
            "//div[contains(@class,'Home_FinishButton')]//button[normalize-space()='Заказать']");

    // Кнопка принятия cookies.
    private final By cookieButton = By.xpath(
            "//button[normalize-space()='да все привыкли']");

    // Вопрос 1: «Сколько это стоит? И как оплатить?».
    private final By questionPrice = By.id("accordion__heading-0");
    // Ответ на вопрос 1 о стоимости.
    private final By answerPrice = By.id("accordion__panel-0");

    // Вопрос 2: «Хочу сразу несколько самокатов! Так можно?».
    private final By questionMultiple = By.id("accordion__heading-1");
    // Ответ на вопрос 2 о количестве самокатов.
    private final By answerMultiple = By.id("accordion__panel-1");

    // Вопрос 3: «Как рассчитывается время аренды?».
    private final By questionTime = By.id("accordion__heading-2");
    // Ответ на вопрос 3 о времени аренды.
    private final By answerTime = By.id("accordion__panel-2");

    // Вопрос 4: «Можно ли заказать самокат прямо на сегодня?».
    private final By questionToday = By.id("accordion__heading-3");
    // Ответ на вопрос 4 о дате заказа.
    private final By answerToday = By.id("accordion__panel-3");

    // Вопрос 5: «Можно ли продлить заказ или вернуть самокат раньше?».
    private final By questionExtend = By.id("accordion__heading-4");
    // Ответ на вопрос 5 о продлении аренды.
    private final By answerExtend = By.id("accordion__panel-4");

    // Вопрос 6: «Вы привозите зарядку вместе с самокатом?».
    private final By questionCharger = By.id("accordion__heading-5");
    // Ответ на вопрос 6 о зарядке.
    private final By answerCharger = By.id("accordion__panel-5");

    // Вопрос 7: «Можно ли отменить заказ?».
    private final By questionCancel = By.id("accordion__heading-6");
    // Ответ на вопрос 7 об отмене.
    private final By answerCancel = By.id("accordion__panel-6");

    // Вопрос 8: «Я живу за МКАДом, привезёте?».
    private final By questionOutsideMkad = By.id("accordion__heading-7");
    // Ответ на вопрос 8 о доставке за МКАД.
    private final By answerOutsideMkad = By.id("accordion__panel-7");

    // Локаторы сведены в массивы, чтобы проверять все вопросы одним параметризованным тестом.
    private final By[] questions = {
            questionPrice, questionMultiple, questionTime, questionToday,
            questionExtend, questionCharger, questionCancel, questionOutsideMkad
    };

    private final By[] answers = {
            answerPrice, answerMultiple, answerTime, answerToday,
            answerExtend, answerCharger, answerCancel, answerOutsideMkad
    };

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void open() {
        driver.get(URL);
    }

    public void acceptCookiesIfPresent() {
        try {
            // Баннер может появиться с задержкой после загрузки страницы.
            new WebDriverWait(driver, Duration.ofSeconds(3))
                    .until(ExpectedConditions.elementToBeClickable(cookieButton))
                    .click();
        } catch (TimeoutException ignored) {
            // Баннера нет: например, пользователь уже принял cookies.
        }
    }

    public void clickOrderButton(boolean useTopButton) {
        By button = useTopButton ? topOrderButton : bottomOrderButton;
        clickAfterScroll(button);
    }

    public void expandFaqQuestion(int index) {
        clickAfterScroll(questions[index]);
    }

    public String getFaqAnswer(int index) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(answers[index]))
                .getText();
    }

    private void clickAfterScroll(By locator) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        // Как в шпаргалке: прокрутка через JavascriptExecutor, клик — через Selenium.
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", element);
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }
}
