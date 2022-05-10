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
        checkLoadDelegate();
        testEnvironment.homePage.isDelegateEnabled(ON_AD_DISPLAYED);
        checkClickDelegate();
        testEnvironment.homePage.isDelegateEnabled(ON_AD_CLOSED);
    }

    @Override
    public void checkDisplayInterstitialDelegates() {
        checkLoadDelegate();
        testEnvironment.homePage.isDelegateEnabled(ON_AD_DISPLAYED);
        testEnvironment.homePage.isDelegateEnabled(ON_AD_CLOSED);
    }

    @Override
    public void checkVideoInterstitialDelegates()  {
        checkLoadDelegate();
        testEnvironment.homePage.isDelegateEnabled(ON_AD_DISPLAYED);
        checkClickDelegate();
        testEnvironment.homePage.isDelegateEnabled(ON_AD_CLOSED);
    }

    @Override
    public void checkVideoRewardedDelegates() {
        testEnvironment.homePage.isDelegateEnabled(ON_AD_CLOSED);
        checkClickDelegate();
    }

    @Override
    public void checkVideoOutstreamDelegates() {
        checkLoadDelegate();
        testEnvironment.homePage.isDelegateEnabled(ON_AD_DISPLAYED);
        checkClickDelegate();
    }

    @Override
    public void checkNativeAdsDelegates(String prebidAd) throws InterruptedException {
        testEnvironment.homePage.isDelegateEnabled(ON_NATIVE_FETCH_DEMAND_SUCCESS);
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

    @Override
    public void checkLoadDelegate() {
        testEnvironment.homePage.isDelegateEnabled(ON_AD_LOADED);
    }

    @Override
    public void checkLoadFailDelegate() {
        testEnvironment.homePage.isDelegateEnabled(ON_AD_FAILED);
    }

    @Override
    public void checkClickDelegate() {
        testEnvironment.homePage.isDelegateEnabled(ON_AD_CLICKED);

    }
}
