package adapters.android;

import adapters.PrebidAdapter;
import appium.common.InAppBiddingTestEnvironment;
import appium.pages.inAppBidding.InAppBiddingAdPageImpl;
import appium.pages.inAppBidding.InAppBiddingHomePageImpl;
import adapters.delegates.DelegatesInspector;

import java.util.concurrent.TimeoutException;

import static appium.common.InAppBiddingTestEnvironment.InAppBiddingDelegates.*;

public class MoPubAdapterAndroid extends PrebidAdapter {
    public MoPubAdapterAndroid(InAppBiddingTestEnvironment testEnvironment, InAppBiddingAdPageImpl adPage) {
        super(testEnvironment,adPage);
    }

    public MoPubAdapterAndroid(InAppBiddingTestEnvironment testEnvironment) {
        super(testEnvironment);
    }

    @Override
    public void checkBannerDelegates() {
        testEnvironment.homePage.isDelegateEnabled(ON_BANNER_LOADED);
        adPage.clickBanner();
        testEnvironment.homePage.clickCloseButtonClickThroughBrowser();
        testEnvironment.homePage.isDelegateEnabled(ON_AD_CLICKED);
    }

    @Override
    public void checkDisplayInterstitialDelegates() {
        testEnvironment.homePage.isDelegateEnabled(ON_BANNER_LOADED);
        testEnvironment.homePage.isDelegateEnabled(ON_AD_DISPLAYED);
        testEnvironment.homePage.isDelegateEnabled(ON_INTERSTITIAL_DISMISSED);
    }

    @Override
    public void checkVideoInterstitialDelegates()  {
        testEnvironment.homePage.isDelegateEnabled(ON_BANNER_LOADED);
        testEnvironment.homePage.isDelegateEnabled(ON_AD_DISPLAYED);
        testEnvironment.homePage.isDelegateEnabled(ON_AD_CLICKED);
        testEnvironment.homePage.isDelegateEnabled(ON_INTERSTITIAL_DISMISSED);

    }

    @Override
    public void checkVideoRewardedDelegates() {
        testEnvironment.homePage.isDelegateEnabled(ON_REWARDED_STARTED);
        testEnvironment.homePage.isDelegateEnabled(ON_REWARDED_COMPLETED);
        testEnvironment.homePage.isDelegateEnabled(ON_AD_CLICKED);
    }

    @Override
    public void checkVideoOutstreamDelegates() {
        testEnvironment.homePage.isDelegateEnabled(ON_AD_LOADED);
        testEnvironment.homePage.isDelegateEnabled(ON_AD_DISPLAYED);
        testEnvironment.homePage.isDelegateEnabled(ON_AD_CLICKED);
    }

    @Override
    public void checkNativeAdsDelegates(String prebidAd) throws InterruptedException {
        adPage.clickHereToVisitOurSite();
        Thread.sleep(1000);
        testEnvironment.homePage.clickBack();
        testEnvironment.homePage.isDelegateEnabled(NATIVE_AD_DID_LOAD);
        testEnvironment.homePage.isDelegateEnabled(ON_AD_CLICKED);
        testEnvironment.homePage.isDelegateEnabled(NATIVE_AD_DID_LOG_IMPRESSION);
    }


    @Override
    public void checkEvents() throws InterruptedException, TimeoutException {
        testEnvironment.waitForEvent(InAppBiddingTestEnvironment.InAppBiddingEvents.MOPUB_AD, 1, 60);
    }
}
