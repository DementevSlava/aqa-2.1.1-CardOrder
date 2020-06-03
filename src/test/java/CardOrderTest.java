import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;


public class CardOrderTest {

    SelenideElement form = $("form");
    SelenideElement name = form.$("[data-test-id=name] input");
    SelenideElement phone = form.$("[data-test-id=phone] input");
    SelenideElement agreement = form.$("[data-test-id=agreement]");
    SelenideElement button = form.$("[type=button]");

    @BeforeEach
    void openHost() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSubmitRequest() {
        name.setValue("Петр Иванов");
        phone.setValue("+79101234567");
        agreement.click();
        button.click();
        $("[data-test-id=order-success]").shouldHave(Condition.exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void invalidNameTest(){
        name.setValue("Petr Ivanov");
        phone.setValue("+791198765432");
        agreement.click();
        button.click();
        $("[data-test-id=name] span.input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void invalidPhoneTest(){
        name.setValue("Петр Иванов");
        phone.setValue("+7910123456");
        agreement.click();
        button.click();
        $("[data-test-id=phone] span.input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void NoNameTest(){

        phone.setValue("+7910123456");
        agreement.click();
        button.click();
        $("[data-test-id=name] span.input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void NoPhoneTest(){
        name.setValue("Петр Иванов");

        agreement.click();
        button.click();
        $("[data-test-id=phone] span.input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }
}
