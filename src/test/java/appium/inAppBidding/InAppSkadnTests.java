package appium.inAppBidding;

import appium.common.InAppBiddingTestEnvironment;
import org.testng.annotations.Test;
import utils.RequestValidator;

import java.util.concurrent.TimeoutException;
import java.util.function.Predicate;

public class InAppSkadnTests extends InAppBaseTest {

    @Test(groups = {"requests-simulator"}, dataProvider = "SkadNetworkAds", dataProviderClass = InAppDataProviders.class)
    public void testAuctionRequestSKADNIsPresent(String prebidAd) throws TimeoutException, InterruptedException {
        initValidTemplatesJson(prebidAd);

        env.homePage.goToAd(prebidAd);

        env.waitForEvent(InAppBiddingTestEnvironment.InAppBiddingEvents.AUCTION, 1, 30);
        env.validateEventRequest(InAppBiddingTestEnvironment.InAppBiddingEvents.AUCTION, validAuctionRequest);
        checkPrebidWinEvent(prebidAd);
        env.homePage.clickBack();
    }
    @Test(groups = {"requests-simulator"}, dataProvider = "NoSkadNetworkAds", dataProviderClass = InAppDataProviders.class)
    public void testAuctionRequestSKADNIsNotPresent(String prebidAd) throws TimeoutException, InterruptedException {
        initValidTemplatesJson(prebidAd);

        env.homePage.goToAd(prebidAd);

        env.waitForEvent(InAppBiddingTestEnvironment.InAppBiddingEvents.AUCTION, 1, 30);
        env.validateEventRequest(InAppBiddingTestEnvironment.InAppBiddingEvents.AUCTION, validAuctionRequest);
        checkPrebidWinEvent(prebidAd);
        env.homePage.clickBack();
    }
    private void checkPrebidWinEvent(String prebidAd) throws InterruptedException, TimeoutException {
        if (prebidAd.contains("Banner")) {
            env.waitForEvent(InAppBiddingTestEnvironment.InAppBiddingEvents.WIN_PREBID, 1, 30);
        }
    }
}
