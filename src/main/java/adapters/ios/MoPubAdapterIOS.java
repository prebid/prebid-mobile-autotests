package adapters.ios;

import adapters.PrebidAdapter;
import appium.common.InAppBiddingTestEnvironment;
import appium.pages.inAppBidding.InAppBiddingAdPageImpl;

import java.util.concurrent.TimeoutException;

import static appium.common.InAppBiddingTestEnvironment.InAppBiddingDelegates.*;

public class MoPubAdapterIOS extends PrebidAdapter {
    public MoPubAdapterIOS(InAppBiddingTestEnvironment testEnvironment, InAppBiddingAdPageImpl adPage) {
        super(testEnvironment,adPage);
    }

    public MoPubAdapterIOS(InAppBiddingTestEnvironment testEnvironment) {
        super(testEnvironment);
    }

    @Override
    public void checkBannerDelegates() {
        testEnvironment.homePage.clickCloseButtonClickThroughBrowser();
        testEnvironment.homePage.isDelegateEnabled(AD_VIEW_DID_LOAD);
        testEnvironment.homePage.isDelegateEnabled(AD_PRESENT_MODAL_VIEW);
        testEnvironment.homePage.isDelegateEnabled(AD_DISMISS_MODAL_VIEW);
        // TO-DO -- FIX IT
//            bannerPage.isDelegateEnabled(AD_WILL_LEAVE_APP);
        testEnvironment.homePage.isDelegateEnabled(AD_CONTROLLER_FOR_PRESENTING_MODAL_VIEW);
    }

    @Override
    public void checkDisplayInterstitialDelegates() {
//        adPage.clickCloseInterstitial();
        testEnvironment.homePage.isDelegateEnabled(INTERSTITIAL_DID_LOAD);
        testEnvironment.homePage.isDelegateEnabled(INTERSTITIAL_WILL_PRESENT);
        testEnvironment.homePage.isDelegateEnabled(INTERSTITIAL_DID_PRESENT);
        testEnvironment.homePage.isDelegateEnabled(INTERSTITIAL_DID_DISMISS);
        testEnvironment.homePage.isDelegateEnabled(INTERSTITIAL_WILL_DISMISS);
        testEnvironment.homePage.isDelegateEnabled(INTERSTITIAL_DID_RECEIVED_TAP);
    }

    @Override
    public void checkVideoInterstitialDelegates() {
        adPage.clickCloseInterstitial();
        testEnvironment.homePage.isDelegateEnabled(INTERSTITIAL_DID_LOAD);
        testEnvironment.homePage.isDelegateEnabled(INTERSTITIAL_WILL_APPEAR);
        testEnvironment.homePage.isDelegateEnabled(INTERSTITIAL_DID_APPEAR);
        testEnvironment.homePage.isDelegateEnabled(INTERSTITIAL_DID_DISAPPEAR);
        testEnvironment.homePage.isDelegateEnabled(INTERSTITIAL_WILL_DISAPPEAR);
        testEnvironment.homePage.isDelegateEnabled(INTERSTITIAL_DID_RECEIVED_TAP);
    }

    @Override
    public void checkVideoRewardedDelegates() {
        testEnvironment.homePage.isDelegateEnabled(REWARDED_VIDEO_AD_DID_LOAD);
        testEnvironment.homePage.isDelegateEnabled(REWARDED_VIDEO_AD_WILL_PRESENT);
        testEnvironment.homePage.isDelegateEnabled(REWARDED_VIDEO_AD_DID_PRESENT);
        testEnvironment.homePage.isDelegateEnabled(REWARDED_VIDEO_AD_WILL_DISMISS);
        testEnvironment.homePage.isDelegateEnabled(REWARDED_VIDEO_AD_DID_DISMISS);
        testEnvironment.homePage.isDelegateEnabled(REWARDED_VIDEO_AD_DID_TAP);
        testEnvironment.homePage.isDelegateEnabled(REWARDED_VIDEO_AD_SHOULD_REWARD);
    }

    @Override
    public void checkVideoOutstreamDelegates() {
        testEnvironment.homePage.isDelegateEnabled(AD_VIEW_RECEIVED);
        testEnvironment.homePage.isDelegateEnabled(AD_VIEW_PRESENT);
        testEnvironment.homePage.isDelegateEnabled(AD_VIEW_WILL_LEAVE);
        testEnvironment.homePage.isDelegateEnabled(AD_VIEW_DID_DISMISS);
    }

    @Override
    public void checkNativeAdsDelegates(String prebidAd)  {
        testEnvironment.homePage.isDelegateEnabled(GET_NATIVE_AD);
        testEnvironment.homePage.isDelegateEnabled(NATIVE_AD_PRIMARY_WIN);
        testEnvironment.homePage.isDelegateEnabled(NATIVE_AD_DID_TRACK_IMPRESSION);
    }

    @Override
    public void checkEvents() throws InterruptedException, TimeoutException {
        testEnvironment.waitForEvent(InAppBiddingTestEnvironment.InAppBiddingEvents.MOPUB_AD, 1, 60);

    }
}
