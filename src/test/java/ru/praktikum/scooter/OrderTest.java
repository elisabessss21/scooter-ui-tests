package ru.praktikum.scooter;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.praktikum.scooter.pages.MainPage;
import ru.praktikum.scooter.pages.OrderPage;
import ru.praktikum.scooter.pages.RentPage;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/** Два набора данных x две кнопки входа в заказ = четыре запуска. */
public class OrderTest extends BaseTest {

    static Stream<Arguments> orderData() {
        return Stream.of(
                // Верхняя кнопка, первый набор данных.
                arguments(true, "Анна", "Иванова", "Москва, улица Лесная, 10",
                        "Черкизовская", "+79991234567", "сутки",
                        "чёрный жемчуг", "Позвоните за час"),
                // Нижняя кнопка, первый набор данных.
                arguments(false, "Анна", "Иванова", "Москва, улица Лесная, 10",
                        "Черкизовская", "+79991234567", "сутки",
                        "чёрный жемчуг", "Позвоните за час"),
                // Верхняя кнопка, второй набор данных.
                arguments(true, "Мария", "Петрова", "Москва, улица Тверская, 15",
                        "Сокольники", "+79997654321", "двое суток",
                        "серая безысходность", "Оставьте у входа"),
                // Нижняя кнопка, второй набор данных.
                arguments(false, "Мария", "Петрова", "Москва, улица Тверская, 15",
                        "Сокольники", "+79997654321", "двое суток",
                        "серая безысходность", "Оставьте у входа")
        );
    }

    @ParameterizedTest(name = "Заказ: верхняя кнопка = {0}, клиент = {1} {2}")
    @MethodSource("orderData")
    public void successfulOrder(boolean useTopButton, String name, String surname,
                                String address, String metro, String phone,
                                String rentalPeriod, String color, String comment) {
        MainPage mainPage = new MainPage(driver);
        OrderPage orderPage = new OrderPage(driver);
        RentPage rentPage = new RentPage(driver);

        mainPage.open();
        mainPage.acceptCookiesIfPresent();
        mainPage.clickOrderButton(useTopButton);

        orderPage.fillCustomerInfo(name, surname, address, metro, phone);
        orderPage.clickNext();

        rentPage.selectDeliveryDate();
        rentPage.selectRentalPeriod(rentalPeriod);
        rentPage.selectColor(color);
        rentPage.enterComment(comment);
        rentPage.submitAndConfirmOrder();

        String actualMessage = rentPage.getSuccessMessage();
        assertTrue(actualMessage.contains("Заказ оформлен"),
                "Не показано сообщение об успешном оформлении: " + actualMessage);
    }
}
