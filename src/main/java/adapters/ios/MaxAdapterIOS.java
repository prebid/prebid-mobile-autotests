package adapters.ios;

import adapters.PrebidAdapter;
import appium.common.InAppBiddingTestEnvironment;
import appium.pages.inAppBidding.InAppBiddingAdPageImpl;

import java.util.concurrent.TimeoutException;

public class MaxAdapterIOS extends PrebidAdapter {
    public MaxAdapterIOS(InAppBiddingTestEnvironment testEnvironment, InAppBiddingAdPageImpl adPage) {
        super(testEnvironment, adPage);
    }

    public MaxAdapterIOS(InAppBiddingTestEnvironment testEnvironment) {
        super(testEnvironment);
    }

    @Override
    public void checkBannerDelegates() throws InterruptedException {
        testEnvironment.homePage.openInBrowser();
        adPage.waitAndReturnToApp();
        testEnvironment.homePage.isDelegateEnabled(InAppBiddingTestEnvironment.InAppBiddingDelegates.DID_LOAD_AD);
        testEnvironment.homePage.isDelegateEnabled(InAppBiddingTestEnvironment.InAppBiddingDelegates.DID_HIDE_AD);
//        testEnvironment.homePage.isDelegateEnabled(InAppBiddingTestEnvironment.InAppBiddingDelegates.DID_COLLAPSED_AD);
//        testEnvironment.homePage.isDelegateEnabled(InAppBiddingTestEnvironment.InAppBiddingDelegates.DID_EXPAND_AD);
        testEnvironment.homePage.isDelegateEnabled(InAppBiddingTestEnvironment.InAppBiddingDelegates.DID_CLICK_AD);

    }

    @Override
    public void checkDisplayInterstitialDelegates() throws InterruptedException {

    }

    @Override
    public void checkVideoInterstitialDelegates() {

    }

    @Override
    public void checkVideoRewardedDelegates() {

    }

    @Override
    public void checkVideoOutstreamDelegates() {

    }

    @Override
    public void checkNativeAdsDelegates(String prebidAd) throws InterruptedException {

    }

    @Override
    public void checkEvents() throws InterruptedException, TimeoutException {

    }
}
