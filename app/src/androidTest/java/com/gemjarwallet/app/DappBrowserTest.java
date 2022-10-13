package com.gemjarwallet.app;

import static androidx.test.espresso.Espresso.pressBack;
import static com.gemjarwallet.app.assertions.Should.shouldSee;
import static com.gemjarwallet.app.steps.Steps.createNewWallet;
import static com.gemjarwallet.app.steps.Steps.navigateToBrowser;
import static com.gemjarwallet.app.steps.Steps.selectTestNet;
import static com.gemjarwallet.app.steps.Steps.visit;

import com.gemjarwallet.app.util.Helper;
import com.gemjarwallet.app.util.SnapshotUtil;

import org.junit.Test;

public class DappBrowserTest extends BaseE2ETest
{

    @Test
    public void should_switch_network()
    {
        String urlString = "https://opensea.io";

        createNewWallet();
        visit(urlString);
        shouldSee("Ethereum");
        Helper.wait(5);
        SnapshotUtil.take("1");
        selectTestNet("Görli");
        navigateToBrowser();
        Helper.wait(3);
        pressBack();
        shouldSee("Görli");
    }
}
