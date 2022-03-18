package adapters.android;

import adapters.PrebidAdapter;
import appium.common.InAppBiddingTestEnvironment;
import appium.pages.inAppBidding.InAppBiddingAdPageImpl;
import appium.pages.inAppBidding.InAppBiddingHomePageImpl;
import adapters.delegates.DelegatesInspector;

import java.util.concurrent.TimeoutException;

import static appium.common.InAppBiddingTestEnvironment.InAppBiddingDelegates.*;
import static appium.common.InAppBiddingTestEnvironment.InAppBiddingDelegates.ON_AD_CLOSED;

public class GamAdapterAndroid extends PrebidAdapter {
    public GamAdapterAndroid(InAppBiddingTestEnvironment testEnvironment, InAppBiddingAdPageImpl adPage) {
        super(testEnvironment,adPage);
    }

    public GamAdapterAndroid(InAppBiddingTestEnvironment testEnvironment) {
        super(testEnvironment);
    }

    @Override
    public void checkBannerDelegates()  {
        testEnvironment.homePage.isDelegateEnabled(ON_AD_LOADED);
        testEnvironment.homePage.isDelegateEnabled(ON_AD_DISPLAYED);
        adPage.clickBanner();
        testEnvironment.homePage.clickCloseButtonClickThroughBrowser();
        testEnvironment.homePage.isDelegateEnabled(ON_AD_CLICKED);
        testEnvironment.homePage.isDelegateEnabled(ON_AD_CLOSED);
    }

    @Override
    public void checkDisplayInterstitialDelegates() {
        testEnvironment.homePage.isDelegateEnabled(ON_AD_LOADED);
        testEnvironment.homePage.isDelegateEnabled(ON_AD_DISPLAYED);
        testEnvironment.homePage.isDelegateEnabled(ON_AD_CLOSED);
    }

    @Override
    public void checkVideoInterstitialDelegates()  {
        testEnvironment.homePage.isDelegateEnabled(ON_AD_LOADED);
        testEnvironment.homePage.isDelegateEnabled(ON_AD_DISPLAYED);
        testEnvironment.homePage.isDelegateEnabled(ON_AD_CLICKED);
        testEnvironment.homePage.isDelegateEnabled(ON_AD_CLOSED);
    }

    @Override
    public void checkVideoRewardedDelegates() {
        testEnvironment.homePage.isDelegateEnabled(ON_AD_CLOSED);
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
        if (prebidAd.contains("Custom")) {
            testEnvironment.homePage.isDelegateEnabled(CUSTOM_NATIVE_AD_REQUEST_SUCCESS);
        } else if (prebidAd.contains("Unified")) {
            testEnvironment.homePage.isDelegateEnabled(UNIFIED_CUSTOM_AD_REQUEST_SUCCESS);
        }
        testEnvironment.homePage.isDelegateEnabled(ON_AD_IMPRESSION);
        if (prebidAd.contains("GADUnified")){
            testEnvironment.homePage.isDelegateEnabled(UNIFIED_NATIVE_AD_PRIMARY_WIN);
            adPage.clickButtonCallToAction();
        } else {
            if (prebidAd.contains("GAD")) {
                testEnvironment.homePage.isDelegateEnabled(CUSTOM_NATIVE_AD_PRIMARY_WIN);
            }
            adPage.clickHereToVisitOurSite();
        }
        Thread.sleep(1000);
        testEnvironment.homePage.clickBack();
        testEnvironment.homePage.isDelegateEnabled(ON_AD_CLICKED);
    }

    @Override
    public void checkEvents() throws InterruptedException, TimeoutException {
        testEnvironment.waitForEvent(InAppBiddingTestEnvironment.InAppBiddingEvents.GAM_G_DOUBLECLICK, 1, 10);
    }
}
