package adapters.ios;

import adapters.PrebidAdapter;
import appium.common.InAppBiddingTestEnvironment;
import appium.pages.inAppBidding.InAppBiddingAdPageImpl;

import java.util.concurrent.TimeoutException;

import static appium.common.InAppBiddingTestEnvironment.InAppBiddingDelegates.AD_VIEW_DID_FAIL;
import static appium.common.InAppBiddingTestEnvironment.InAppBiddingDelegates.DID_LOAD_AD;

public class MaxAdapterIOS extends PrebidAdapter {
    public MaxAdapterIOS(InAppBiddingTestEnvironment testEnvironment, InAppBiddingAdPageImpl adPage) {
        super(testEnvironment, adPage);
    }

    public MaxAdapterIOS(InAppBiddingTestEnvironment testEnvironment) {
        super(testEnvironment);
    }

    @Override
    public void checkBannerDelegates() {
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
        throw new UnsupportedOperationException("Not yet implemented");
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
        testEnvironment.waitForEvent(InAppBiddingTestEnvironment.InAppBiddingEvents.MAX_LOAD, 1, 60);
        try {
            testEnvironment.waitForEvent(InAppBiddingTestEnvironment.InAppBiddingEvents.MAX_CIMP, 1, 60);
        } catch (Exception e) {
            testEnvironment.waitForEvent(InAppBiddingTestEnvironment.InAppBiddingEvents.MAX_MIMP, 1, 60);
        }
    }

    @Override
    public void checkLoadDelegates() {
        testEnvironment.homePage.isDelegateEnabled(DID_LOAD_AD);
    }

    @Override
    public void checkLoadFailDelegates() {
        testEnvironment.homePage.isDelegateEnabled(AD_VIEW_DID_FAIL);
    }
}
