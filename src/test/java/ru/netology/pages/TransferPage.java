package ru.netology.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataGenerator;


import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement transferButton = $("[data-test-id=action-transfer]");
    private SelenideElement amountInput = $("[data-test-id=amount] input");
    private SelenideElement fromInput = $("[data-test-id=from] input");
    private SelenideElement transferHead = $(byText("Пополнение карты"));
    private SelenideElement errorMessage = $("[data-test-id=error-message]");

    public TransferPage() {
        transferHead.shouldBe(visible);
    }

    public DashboardPage shouldMakeValidTransfer(String amountToTransfer, DataGenerator.CardInfo cardInfo) {
        makeTransfer(amountToTransfer, cardInfo);
        return new DashboardPage();
    }

    public void makeTransfer(String amountToTransfer, DataGenerator.CardInfo cardInfo) {
        amountInput.setValue(amountToTransfer);
        fromInput.setValue(cardInfo.getCardNumber());
        transferButton.click();
    }

    public void shouldFindErrorMessage(String expectedText) {
        errorMessage.shouldBe(Condition.exactText(expectedText), Duration.ofSeconds(15)).shouldBe(visible);
    }
}