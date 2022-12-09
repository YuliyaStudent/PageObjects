package ru.netology.pages;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataGenerator;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private SelenideElement codeField = $("[data-test-id=code] input");
    private SelenideElement veryfyButton = $("[data-test-id=action-verify]");

    public VerificationPage(){
        codeField.shouldBe(visible);
    }

    public DashboardPage validVerify(DataGenerator.VerificationCode verificationCode){
        codeField.setValue(verificationCode.getCode());
        veryfyButton.click();
        return new DashboardPage();
    }

}
