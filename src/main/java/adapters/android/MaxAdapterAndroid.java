package adapters.android;

import adapters.PrebidAdapter;
import appium.common.InAppBiddingTestEnvironment;
import appium.pages.inAppBidding.InAppBiddingAdPageImpl;

import java.util.concurrent.TimeoutException;

import static appium.common.InAppBiddingTestEnvironment.InAppBiddingDelegates.*;

public class MaxAdapterAndroid extends PrebidAdapter {
    public MaxAdapterAndroid(InAppBiddingTestEnvironment testEnvironment, InAppBiddingAdPageImpl adPage) {
        super(testEnvironment, adPage);
    }

    public MaxAdapterAndroid(InAppBiddingTestEnvironment testEnvironment) {
        super(testEnvironment);
    }

    @Override
    public void checkClickDelegate() {
        testEnvironment.homePage.isDelegateEnabled(ON_AD_CLICKED);
    }

    @Override
    public void checkBannerDelegates() {
        checkLoadDelegate();;
        testEnvironment.homePage.isDelegateEnabled(ON_BANNER_EXPANDED);
        testEnvironment.homePage.isDelegateEnabled(ON_AD_COLLAPSED);
        checkClickDelegate();
    }

    @Override
    public void checkDisplayInterstitialDelegates() {
        checkLoadDelegate();
        testEnvironment.homePage.isDelegateEnabled(ON_AD_DISPLAYED);
        testEnvironment.homePage.isDelegateEnabled(DID_HIDE_AD);
        checkClickDelegate();
    }

    @Override
    public void checkVideoInterstitialDelegates() {
        checkDisplayInterstitialDelegates();
    }

    @Override
    public void checkVideoRewardedDelegates() {
        checkDisplayInterstitialDelegates();
        testEnvironment.homePage.isDelegateEnabled(ON_REWARDED_VIDEO_STARTED);
        testEnvironment.homePage.isDelegateEnabled(ON_REWARDED_VIDEO_COMPLETED);
        testEnvironment.homePage.isDelegateEnabled(REWARDED_AD_USER_DID_EARN_REWARD);
    }

    @Override
    public void checkVideoOutstreamDelegates() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void checkNativeAdsDelegates(String prebidAd) throws InterruptedException {
        adPage.clickButtonCallToAction();
        testEnvironment.homePage.clickBack();
        testEnvironment.homePage.isDelegateEnabled(NATIVE_AD_DID_LOAD);
        testEnvironment.homePage.isDelegateEnabled(NATIVE_AD_DID_CLICK);
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
    public void checkEvents() throws InterruptedException, TimeoutException {
        testEnvironment.waitForEvent(InAppBiddingTestEnvironment.InAppBiddingEvents.MAX_LOAD, 1, 60);
        try {
            testEnvironment.waitForEvent(InAppBiddingTestEnvironment.InAppBiddingEvents.MAX_CIMP, 1, 60);
        } catch (Exception e) {
            testEnvironment.waitForEvent(InAppBiddingTestEnvironment.InAppBiddingEvents.MAX_MIMP, 1, 60);
        }
    }
}
