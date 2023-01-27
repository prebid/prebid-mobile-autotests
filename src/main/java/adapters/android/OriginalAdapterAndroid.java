package adapters.android;

import adapters.PrebidAdapter;
import appium.common.InAppBiddingTestEnvironment;
import appium.pages.inAppBidding.InAppBiddingAdPageImpl;

import java.util.concurrent.TimeoutException;

import static appium.common.InAppBiddingTestEnvironment.InAppBiddingDelegates.*;

public class OriginalAdapterAndroid extends PrebidAdapter {

    public OriginalAdapterAndroid(InAppBiddingTestEnvironment testEnvironment, InAppBiddingAdPageImpl adPage) {
        super(testEnvironment, adPage);
    }

    public OriginalAdapterAndroid(InAppBiddingTestEnvironment testEnvironment) {
        super(testEnvironment);
    }

    @Override
    public void checkBannerDelegates() throws InterruptedException {
        checkLoadDelegate();
        testEnvironment.homePage.isDelegateEnabled(ON_AD_DISPLAYED);
        checkClickDelegate();
    }

    @Override
    public void checkDisplayInterstitialDelegates() throws InterruptedException {
        checkLoadDelegate();
    }

    @Override
    public void checkVideoInterstitialDelegates() {
        checkLoadDelegate();
    }

    @Override
    public void checkVideoRewardedDelegates() {
        checkLoadDelegate();
    }

    @Override
    public void checkVideoOutstreamDelegates() {
        checkLoadDelegate();
    }

    @Override
    public void checkNativeAdsDelegates(String prebidAd) throws InterruptedException {
        checkLoadDelegate();
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

    @Override
    public void checkEvents() throws InterruptedException, TimeoutException {
        testEnvironment.waitForEvent(InAppBiddingTestEnvironment.InAppBiddingEvents.CACHE_EVENT, 1, 10);
        testEnvironment.waitForEvent(InAppBiddingTestEnvironment.InAppBiddingEvents.GAM_GAMPAD, 1, 10);
    }
}
