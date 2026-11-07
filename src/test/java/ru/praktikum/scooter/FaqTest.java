package ru.praktikum.scooter;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.praktikum.scooter.pages.MainPage;

import java.util.Locale;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/** Параметризованные проверки всех восьми ответов FAQ. */
public class FaqTest extends BaseTest {

    static Stream<Arguments> faqData() {
        return Stream.of(
                arguments(0, "Сутки — 400 рублей"),
                arguments(1, "один заказ — один самокат"),
                arguments(2, "8 мая"),
                arguments(3, "Только начиная с завтрашнего дня"),
                arguments(4, "Пока что нет"),
                arguments(5, "полной зарядкой"),
                arguments(6, "Пока самокат не привезли"),
                arguments(7, "Да, обязательно")
        );
    }

    @ParameterizedTest(name = "FAQ: вопрос {0}")
    @MethodSource("faqData")
    public void clickingQuestionShowsCorrectAnswer(int questionIndex, String expectedText) {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.acceptCookiesIfPresent();
        mainPage.expandFaqQuestion(questionIndex);

        String actualText = mainPage.getFaqAnswer(questionIndex);
        assertTrue(actualText.toLowerCase(Locale.ROOT)
                        .contains(expectedText.toLowerCase(Locale.ROOT)),
                "У вопроса " + questionIndex + " не тот ответ. Получено: " + actualText);
    }
}
