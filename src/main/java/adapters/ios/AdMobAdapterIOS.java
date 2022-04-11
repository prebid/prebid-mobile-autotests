package adapters.ios;

import adapters.PrebidAdapter;
import appium.common.InAppBiddingTestEnvironment;
import appium.pages.inAppBidding.InAppBiddingAdPageImpl;
import appium.pages.inAppBidding.InAppBiddingHomePageImpl;
import adapters.delegates.DelegatesInspector;

import java.util.concurrent.TimeoutException;

import static appium.common.InAppBiddingTestEnvironment.InAppBiddingDelegates.*;

public class AdMobAdapterIOS extends PrebidAdapter {

    public AdMobAdapterIOS(InAppBiddingTestEnvironment testEnvironment, InAppBiddingAdPageImpl adPage) {
        super(testEnvironment,adPage);
    }

    public AdMobAdapterIOS(InAppBiddingTestEnvironment testEnvironment) {
        super(testEnvironment);
    }

    @Override
    public void checkBannerDelegates() throws InterruptedException {
        testEnvironment.homePage.openInBrowser();
        adPage.waitAndReturnToApp();
        testEnvironment.homePage.isDelegateEnabled(AD_VIEW_DID_LOAD);
        testEnvironment.homePage.isDelegateEnabled(AD_VIEW_DID_RECORD_IMPRESSION);
        testEnvironment.homePage.isDelegateEnabled(AD_WILL_PRESENT_SCREEN);
        testEnvironment.homePage.isDelegateEnabled(AD_WILL_DISMISS_SCREEN);
        testEnvironment.homePage.isDelegateEnabled(AD_DID_DISMISS_SCREEN);
    }

    @Override
    public void checkDisplayInterstitialDelegates() {
        testEnvironment.homePage.isDelegateEnabled(INTERSTITIAL_DID_PRESENT_FULLSCREEN);
        testEnvironment.homePage.isDelegateEnabled(INTERSTITIAL_WILL_DISMISS_FULLSCREEN);
        testEnvironment.homePage.isDelegateEnabled(INTERSTITIAL_DID_DISMISS_FULLSCREEN);
        testEnvironment.homePage.isDelegateEnabled(INTERSTITIAL_DID_RECORD_IMPRESSION);
    }

    @Override
    public void checkVideoInterstitialDelegates() {
        testEnvironment.homePage.isDelegateEnabled(INTERSTITIAL_DID_RECEIVE_BUTTON);
        testEnvironment.homePage.isDelegateEnabled(INTERSTITIAL_DID_PRESENT_FULLSCREEN);
        testEnvironment.homePage.isDelegateEnabled(INTERSTITIAL_WILL_DISMISS_FULLSCREEN);
        testEnvironment.homePage.isDelegateEnabled(INTERSTITIAL_DID_DISMISS_FULLSCREEN);
        testEnvironment.homePage.isDelegateEnabled(INTERSTITIAL_DID_RECORD_IMPRESSION);
    }

    @Override
    public void checkVideoRewardedDelegates() {
        testEnvironment.homePage.isDelegateEnabled(INTERSTITIAL_DID_RECEIVE_BUTTON);
        testEnvironment.homePage.isDelegateEnabled(INTERSTITIAL_DID_PRESENT_FULLSCREEN);
        testEnvironment.homePage.isDelegateEnabled(INTERSTITIAL_WILL_DISMISS_FULLSCREEN);
        testEnvironment.homePage.isDelegateEnabled(INTERSTITIAL_DID_DISMISS_FULLSCREEN);
        testEnvironment.homePage.isDelegateEnabled(INTERSTITIAL_DID_RECORD_IMPRESSION);
    }

    @Override
    public void checkVideoOutstreamDelegates() {
        testEnvironment.homePage.isDelegateEnabled(AD_VIEW_RECEIVED);
        testEnvironment.homePage.isDelegateEnabled(AD_VIEW_PRESENT);
        testEnvironment.homePage.isDelegateEnabled(AD_VIEW_WILL_LEAVE);
        testEnvironment.homePage.isDelegateEnabled(AD_VIEW_DID_DISMISS);
    }

    @Override
    public void checkNativeAdsDelegates(String prebidAd) {
        testEnvironment.homePage.isDelegateEnabled(LOADER_DID_RECEIVE_BUTTON);
        testEnvironment.homePage.isDelegateEnabled(LOADER_DID_FINISH_LOADING_BUTTON);
    }

    @Override
    public void checkEvents() throws InterruptedException, TimeoutException {
        testEnvironment.waitForEvent(InAppBiddingTestEnvironment.InAppBiddingEvents.ADMOB_MADS_GMA, 1, 60);
        testEnvironment.waitForEvent(InAppBiddingTestEnvironment.InAppBiddingEvents.ADMOB_PAGEAD_INTERACTION, 1, 10);
    }
}
