package adapters.android;

import adapters.PrebidAdapter;
import appium.common.InAppBiddingTestEnvironment;
import appium.pages.inAppBidding.InAppBiddingAdPageImpl;
import appium.pages.inAppBidding.InAppBiddingHomePageImpl;
import adapters.delegates.DelegatesInspector;

import java.util.concurrent.TimeoutException;

import static appium.common.InAppBiddingTestEnvironment.InAppBiddingDelegates.*;
import static appium.common.InAppBiddingTestEnvironment.InAppBiddingDelegates.ON_AD_CLOSED;

public class AdMobAdapterAndroid extends PrebidAdapter {

    public AdMobAdapterAndroid(InAppBiddingTestEnvironment testEnvironment, InAppBiddingAdPageImpl adPage) {
        super(testEnvironment,adPage);
    }

    public AdMobAdapterAndroid(InAppBiddingTestEnvironment testEnvironment) {
        super(testEnvironment);
    }

    @Override
    public void checkBannerDelegates()  {
        checkLoadDelegate();
        testEnvironment.homePage.isDelegateEnabled(ON_AD_IMPRESSION);
        checkClickDelegate();
        testEnvironment.homePage.isDelegateEnabled(ON_AD_OPENED);
        testEnvironment.homePage.isDelegateEnabled(ON_AD_CLOSED);
    }

    @Override
    public void checkDisplayInterstitialDelegates() {
        checkLoadDelegate();
        checkClickDelegate();
        testEnvironment.homePage.isDelegateEnabled(ON_AD_IMPRESSION);
        testEnvironment.homePage.isDelegateEnabled(ON_AD_SHOWED);
        testEnvironment.homePage.isDelegateEnabled(ON_INTERSTITIAL_DISMISSED);
    }

    @Override
    public void checkVideoInterstitialDelegates()  {
        checkDisplayInterstitialDelegates();
    }

    @Override
    public void checkVideoRewardedDelegates() {
        checkLoadDelegate();
        checkClickDelegate();
        testEnvironment.homePage.isDelegateEnabled(ON_AD_IMPRESSION);
        testEnvironment.homePage.isDelegateEnabled(ON_AD_SHOWED);
        testEnvironment.homePage.isDelegateEnabled(ON_INTERSTITIAL_DISMISSED);
        testEnvironment.homePage.isDelegateEnabled(ON_AD_CLICKED);
    }

    @Override
    public void checkVideoOutstreamDelegates() {
        checkLoadDelegate();
        testEnvironment.homePage.isDelegateEnabled(ON_AD_DISPLAYED);
        checkClickDelegate();
    }

    @Override
    public void checkNativeAdsDelegates(String prebidAd) throws InterruptedException {
        adPage.clickTvBody();
        Thread.sleep(1000);
        testEnvironment.homePage.clickBack();
        checkLoadDelegate();
        checkClickDelegate();
        testEnvironment.homePage.isDelegateEnabled(ON_AD_SHOWED);
        testEnvironment.homePage.isDelegateEnabled(ON_AD_OPENED);
    }

    @Override
    public void checkEvents() throws InterruptedException, TimeoutException {
        testEnvironment.waitForEvent(InAppBiddingTestEnvironment.InAppBiddingEvents.ADMOB_PAGEAD_INTERACTION, 1, 10);
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
