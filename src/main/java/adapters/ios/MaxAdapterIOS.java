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
        testEnvironment.homePage.isDelegateEnabled(InAppBiddingTestEnvironment.InAppBiddingDelegates.DID_COLLAPSED_AD);
        testEnvironment.homePage.isDelegateEnabled(InAppBiddingTestEnvironment.InAppBiddingDelegates.DID_EXPAND_AD);
        testEnvironment.homePage.isDelegateEnabled(InAppBiddingTestEnvironment.InAppBiddingDelegates.DID_CLICK_AD);

    }

    @Override
    public void checkDisplayInterstitialDelegates() {
        testEnvironment.homePage.isDelegateEnabled(InAppBiddingTestEnvironment.InAppBiddingDelegates.DID_LOAD_AD);
        testEnvironment.homePage.isDelegateEnabled(InAppBiddingTestEnvironment.InAppBiddingDelegates.DID_HIDE_AD);
        testEnvironment.homePage.isDelegateEnabled(InAppBiddingTestEnvironment.InAppBiddingDelegates.DID_DISPLAY_AD);
        testEnvironment.homePage.isDelegateEnabled(InAppBiddingTestEnvironment.InAppBiddingDelegates.DID_CLICK_AD);
    }

    @Override
    public void checkVideoInterstitialDelegates() {
        checkDisplayInterstitialDelegates();
    }

    @Override
    public void checkVideoRewardedDelegates() {
        checkDisplayInterstitialDelegates();
    }

    @Override
    public void checkVideoOutstreamDelegates() {

    }

    @Override
    public void checkNativeAdsDelegates(String prebidAd) throws InterruptedException {
        adPage.clickTvBody();
        adPage.waitAndReturnToApp();
        testEnvironment.homePage.isDelegateEnabled(InAppBiddingTestEnvironment.InAppBiddingDelegates.DID_LOAD_NATIVE_AD);
        testEnvironment.homePage.isDelegateEnabled(InAppBiddingTestEnvironment.InAppBiddingDelegates.DID_CLICK_NATIVE_AD);
    }

    @Override
    public void checkEvents() throws InterruptedException, TimeoutException {

    }
}
