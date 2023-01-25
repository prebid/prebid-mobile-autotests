package adapters.ios;

import adapters.PrebidAdapter;
import appium.common.InAppBiddingTestEnvironment;
import appium.pages.inAppBidding.InAppBiddingAdPageImpl;

import java.util.concurrent.TimeoutException;

import static appium.common.InAppBiddingTestEnvironment.InAppBiddingDelegates.*;

public class OriginalAdapterIOS extends PrebidAdapter {
    public OriginalAdapterIOS(InAppBiddingTestEnvironment testEnvironment, InAppBiddingAdPageImpl adPage) {
        super(testEnvironment, adPage);
    }

    public OriginalAdapterIOS(InAppBiddingTestEnvironment testEnvironment) {
        super(testEnvironment);
    }

    @Override
    public void checkBannerDelegates() throws InterruptedException {
        testEnvironment.homePage.isDelegateEnabled(BANNER_VIEW_DID_RECEIVE_AD);
        testEnvironment.homePage.isDelegateEnabled(BANNER_VIEW_DID_RECORD_IMPRESSION);
        testEnvironment.homePage.isDelegateEnabled(BANNER_VIEW_DID_CLICK_AD);
    }

    @Override
    public void checkDisplayInterstitialDelegates() throws InterruptedException {
        testEnvironment.homePage.isDelegateEnabled(AD_DID_RECORD_IMPRESSION);
        testEnvironment.homePage.isDelegateEnabled(AD_DID_RECORD_CLICK);
        testEnvironment.homePage.isDelegateEnabled(AD_WILL_PRESENT_FULL_SCREEN_CONTENT);
        testEnvironment.homePage.isDelegateEnabled(AD_WILL_DISMISS_FULL_SCREEN_CONTENT);
        testEnvironment.homePage.isDelegateEnabled(AD_DID_DISMISS_FULL_SCREEN_CONTENT);
    }

    @Override
    public void checkVideoInterstitialDelegates() throws InterruptedException {
        checkDisplayInterstitialDelegates();
    }

    @Override
    public void checkVideoRewardedDelegates() throws InterruptedException {
        checkDisplayInterstitialDelegates();
    }

    @Override
    public void checkVideoOutstreamDelegates() throws InterruptedException {
        checkBannerDelegates();
    }

    @Override
    public void checkNativeAdsDelegates(String prebidAd) throws InterruptedException {
        testEnvironment.homePage.isDelegateEnabled(NATIVE_AD_LOADED);
        testEnvironment.homePage.isDelegateEnabled(NATIVE_AD_DID_LOG_IMPRESSION);
        testEnvironment.homePage.isDelegateEnabled(NATIVE_AD_DID_CLICK);
    }

    @Override
    public void checkClickDelegate() {
        throw new UnsupportedOperationException("Not used in tests");
    }

    @Override
    public void checkLoadDelegate() {
        throw new UnsupportedOperationException("Not used in tests");
    }

    @Override
    public void checkLoadFailDelegate() {
        throw new UnsupportedOperationException("Not used in tests");
    }

    @Override
    public void checkEvents() throws InterruptedException, TimeoutException {
        testEnvironment.waitForEvent(InAppBiddingTestEnvironment.InAppBiddingEvents.CACHE_EVENT, 1, 10);
        testEnvironment.waitForEvent(InAppBiddingTestEnvironment.InAppBiddingEvents.GAM_GAMPAD, 1, 10);
    }
}
