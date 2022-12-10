package ru.netology.test;


import org.junit.jupiter.api.Test;
import ru.netology.data.DataGenerator;

import ru.netology.pages.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataGenerator.*;

public class TransferTest {

    @Test
    void transferMoneyFromFirstCard() {
        var loginPage = open("http://localhost:9999/", LoginPage.class);
        var authInfo = DataGenerator.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataGenerator.getVerificationCode();
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardInfo = DataGenerator.getFirstCardInfo();
        var secondCardInfo = getSecondCardInfo();
        var currentFirstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
        var currentSecondCardBalance = dashboardPage.getCardBalance(secondCardInfo);
        var amount = generateValidAmount(currentFirstCardBalance);
        var expectedFirstCardBalance = currentFirstCardBalance - amount;
        var expectedSecondCardBalance = currentSecondCardBalance + amount;
        var transferPage = dashboardPage.selectCardToTransfer(secondCardInfo);
        dashboardPage = transferPage.shouldMakeValidTransfer(String.valueOf(amount), firstCardInfo);
        var actualFirstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
        var actualSecondCardBalance = dashboardPage.getCardBalance(secondCardInfo);
        assertEquals(expectedFirstCardBalance, actualFirstCardBalance);
        assertEquals(expectedSecondCardBalance, actualSecondCardBalance);
    }

    @Test
    void transferMoneyFromSecondCard() {
        var loginPage = open("http://localhost:9999/", LoginPage.class);
        var authInfo = DataGenerator.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataGenerator.getVerificationCode();
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardInfo = DataGenerator.getFirstCardInfo();
        var secondCardInfo = getSecondCardInfo();
        var currentFirstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
        var currentSecondCardBalance = dashboardPage.getCardBalance(secondCardInfo);
        var amount = generateValidAmount(currentSecondCardBalance);
        var expectedSecondCardBalance = currentSecondCardBalance - amount;
        var expectedFirstCardBalance = currentFirstCardBalance + amount;
        var transferPage = dashboardPage.selectCardToTransfer(firstCardInfo);
        dashboardPage = transferPage.shouldMakeValidTransfer(String.valueOf(amount), secondCardInfo);
        var actualFirstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
        var actualSecondCardBalance = dashboardPage.getCardBalance(secondCardInfo);
        assertEquals(expectedFirstCardBalance, actualFirstCardBalance);
        assertEquals(expectedSecondCardBalance, actualSecondCardBalance);
    }

    @Test
    void transferMoneyIfAmountMoreThanBalance() {
        var loginPage = open("http://localhost:9999/", LoginPage.class);
        var authInfo = getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = getVerificationCode();
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardInfo = getFirstCardInfo();
        var secondCardInfo = getSecondCardInfo();
        var currentFirstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
        var currentSecondCardBalance = dashboardPage.getCardBalance(secondCardInfo);
        var amount = generateInvalidAmount(currentSecondCardBalance);
        var expectedSecondCardBalance = currentSecondCardBalance - amount;
        var expectedFirstCardBalance = currentFirstCardBalance + amount;
        var transferPage = dashboardPage.selectCardToTransfer(firstCardInfo);
        transferPage.makeTransfer(String.valueOf(amount), secondCardInfo);
        transferPage.shouldFindErrorMessage("Недостаточно средств на карте");
        var actualFirstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
        var actualBalanceSecondCard = dashboardPage.getCardBalance(secondCardInfo);
        assertEquals(currentFirstCardBalance, actualFirstCardBalance);
        assertEquals(currentSecondCardBalance, actualBalanceSecondCard);
    }

}


