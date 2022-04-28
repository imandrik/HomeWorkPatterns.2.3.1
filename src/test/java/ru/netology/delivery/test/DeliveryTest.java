package ru.netology.delivery.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.delivery.data.DataGenerator;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

class DeliveryTest {

    @BeforeEach
    void setup() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $("[data-test-id=\"city\"] input").val(DataGenerator.generateCity());
        $("[data-test-id=\"date\"] input").doubleClick().sendKeys(firstMeetingDate);
        $("[data-test-id=\"name\"] input").val(DataGenerator.generateName());
        $("[data-test-id=\"phone\"] input").val(DataGenerator.generatePhone());
        $("[data-test-id=\"agreement\"]").click();
        $("[class=\"button__text\"]").click();
        $("[data-test-id=\"success-notification\"]").shouldHave(text("Встреча успешно запланирована на " + firstMeetingDate));
        $("[data-test-id=\"date\"] input").doubleClick().sendKeys(secondMeetingDate);
        $("[class=\"button__text\"]").click();
        $("[data-test-id=\"replan-notification\"]").shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"));
        $(byText("Перепланировать")).click();
        $("[data-test-id='success-notification']").shouldHave(text("Встреча успешно запланирована на " + secondMeetingDate));
    }
}
