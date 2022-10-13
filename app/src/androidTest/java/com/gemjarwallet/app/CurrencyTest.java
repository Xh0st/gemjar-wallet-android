package com.gemjarwallet.app;

import static com.gemjarwallet.app.assertions.Should.shouldSee;
import static com.gemjarwallet.app.steps.Steps.createNewWallet;
import static com.gemjarwallet.app.steps.Steps.gotoWalletPage;
import static com.gemjarwallet.app.steps.Steps.selectCurrency;

import org.junit.Test;

public class CurrencyTest extends BaseE2ETest
{

    @Test
    public void should_switch_currency()
    {
        createNewWallet();

        selectCurrency("CNY");
        gotoWalletPage();
        shouldSee("¥");

        selectCurrency("KRW");
        gotoWalletPage();
        shouldSee("₩");

        selectCurrency("IDR");
        gotoWalletPage();
        shouldSee("Rp");
    }

}
