package ru.netology.pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import ru.netology.data.DataGenerator;

import static com.codeborne.selenide.Selenide.page;

public class LoginPage {
    @FindBy(css = "[data-test-id=login] input")
    private SelenideElement loginField;

    @FindBy(css = "[data-test-id=password] input")
    private SelenideElement passwordField;

    @FindBy(css = "[data-test-id=action-login]")
    private SelenideElement loginButton;

    public VerificationPage validLogin(DataGenerator.AuthInfo info) {
        loginField.setValue(info.getLogin());
        passwordField.setValue(info.getPassword());
        loginButton.click();
        return page(VerificationPage.class);
    }
}

