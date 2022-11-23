package ru.netology.delivery;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static java.time.Duration.ofSeconds;

public class CardDelivery {
    @BeforeEach
    void setup() {
        open("http://localhost:9999/");
    }

    @Test
    void shouldSendFormWithValidData1() {
        $x("//*[@placeholder=\"Город\"]").setValue("Барнаул");
        String testData = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//*[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.DELETE + testData);
        $x("//*[@name=\"name\"]").setValue("Иванов Иван");
        $x("//*[@name=\"phone\"]").setValue("+79111234567");
        $("[data-test-id=\"agreement\"]").click();
        $(".button").click();
        $("[data-test-id=notification] .notification__content").shouldBe(Condition.visible, ofSeconds(15)).shouldHave(exactText("Встреча успешно забронирована на " + testData));
    }

    @Test
    void shouldSendFormWithValidData2() {
        $x("//*[@placeholder=\"Город\"]").setValue("Барнаул");
        String testData = LocalDate.now().plusDays(4).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//*[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.DELETE + testData);
        $x("//*[@name=\"name\"]").setValue("Иванов Иван");
        $x("//*[@name=\"phone\"]").setValue("+79111234567");
        $("[data-test-id=\"agreement\"]").click();
        $(".button").click();
        $("[data-test-id=notification] .notification__content").shouldBe(Condition.visible, ofSeconds(15)).shouldHave(exactText("Встреча успешно забронирована на " + testData));
    }

    @Test
    void shouldSendFormWithValidData3() {
        $x("//*[@placeholder=\"Город\"]").setValue("Барнаул");
        String testData = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//*[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.DELETE + testData);
        $x("//*[@name=\"name\"]").setValue("Иванова-Петрова Анна-Ирина");
        $x("//*[@name=\"phone\"]").setValue("+79111234567");
        $("[data-test-id=\"agreement\"]").click();
        $(".button").click();
        $("[data-test-id=notification] .notification__content").shouldBe(Condition.visible, ofSeconds(15)).shouldHave(exactText("Встреча успешно забронирована на " + testData));
    }

    @Test
    void shouldNotSendFormWithWrongCity() {
        $x("//*[@placeholder=\"Город\"]").setValue("Бийск");
        String testData = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//*[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.DELETE + testData);
        $x("//*[@name=\"name\"]").setValue("Иванов Иван");
        $x("//*[@name=\"phone\"]").setValue("+79111234567");
        $("[data-test-id=\"agreement\"]").click();
        $(".button").click();
        String text = $x("//*[text()=\"Доставка в выбранный город недоступна\"]").getText();
        Assertions.assertEquals("Доставка в выбранный город недоступна", text.trim());
    }

    @Test
    void shouldNotSendFormWithWrongDate() {
        $x("//*[@placeholder=\"Город\"]").setValue("Барнаул");
        String testData = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//*[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.DELETE + testData);
        $x("//*[@name=\"name\"]").setValue("Иванов Иван");
        $x("//*[@name=\"phone\"]").setValue("+79111234567");
        $("[data-test-id=\"agreement\"]").click();
        $(".button").click();
        String text = $x("//*[text()=\"Заказ на выбранную дату невозможен\"]").getText();
        Assertions.assertEquals("Заказ на выбранную дату невозможен", text.trim());
    }

    @Test
    void shouldNotSendFormWithWrongName() {
        $x("//*[@placeholder=\"Город\"]").setValue("Барнаул");
        String testData = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//*[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.DELETE + testData);
        $x("//*[@name=\"name\"]").setValue("Bdfyjd Bdfy");
        $x("//*[@name=\"phone\"]").setValue("+79111234567");
        $("[data-test-id=\"agreement\"]").click();
        $(".button").click();
        String text = $x("//*[text()=\"Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.\"]").getText();
        Assertions.assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    void shouldNotSendFormWithWrongPhone() {
        $x("//*[@placeholder=\"Город\"]").setValue("Барнаул");
        String testData = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//*[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.DELETE + testData);
        $x("//*[@name=\"name\"]").setValue("Иванов Иван");
        $x("//*[@name=\"phone\"]").setValue("8911555555555555");
        $("[data-test-id=\"agreement\"]").click();
        $(".button").click();
        String text = $x("//*[text()=\"Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.\"]").getText();
        Assertions.assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    void shouldNotSendFormWithoutCheckbox() {
        $x("//*[@placeholder=\"Город\"]").setValue("Барнаул");
        String testData = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//*[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.DELETE + testData);
        $x("//*[@name=\"name\"]").setValue("Иванов Иван");
        $x("//*[@name=\"phone\"]").setValue("8911555555555555");
        $(".button").click();
        String text = $("[data-test-id=\"agreement\"]").getText();
        Assertions.assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных", text.trim());
    }

    @Test
    void shouldNotSendFormWithoutCity() {
        String testData = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//*[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.DELETE + testData);
        $x("//*[@name=\"name\"]").setValue("Иванов Иван");
        $x("//*[@name=\"phone\"]").setValue("8911555555555555");
        $("[data-test-id=\"agreement\"]").click();
        $(".button").click();
        String text = $("[data-test-id=\"city\"].input_invalid .input__sub").getText();
        Assertions.assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    void shouldNotSendFormWithoutDate() {
        $x("//*[@placeholder=\"Город\"]").setValue("Барнаул");
        String testData = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern(""));
        $x("//*[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.DELETE + testData);
        $x("//*[@name=\"name\"]").setValue("Иванов Иван");
        $x("//*[@name=\"phone\"]").setValue("+79111234567");
        $("[data-test-id=\"agreement\"]").click();
        $(".button").click();
        String text = $x("//*[text()=\"Неверно введена дата\"]").getText();
        Assertions.assertEquals("Неверно введена дата", text.trim());
    }

    @Test
    void shouldNotSendFormWithoutName() {
        $x("//*[@placeholder=\"Город\"]").setValue("Барнаул");
        String testData = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//*[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.DELETE + testData);
        $x("//*[@name=\"phone\"]").setValue("+79111234567");
        $("[data-test-id=\"agreement\"]").click();
        $(".button").click();
        String text = $("[data-test-id=\"name\"].input_invalid .input__sub").getText();
        Assertions.assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    void shouldNotSendFormWithoutPhone() {
        $x("//*[@placeholder=\"Город\"]").setValue("Барнаул");
        String testData = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//*[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.DELETE + testData);
        $x("//*[@name=\"name\"]").setValue("Иванов Иван");
        $("[data-test-id=\"agreement\"]").click();
        $(".button").click();
        String text = $("[data-test-id=\"phone\"].input_invalid .input__sub").getText();
        Assertions.assertEquals("Поле обязательно для заполнения", text.trim());
    }
}