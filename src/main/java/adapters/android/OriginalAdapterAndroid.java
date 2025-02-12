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
    }

    @Override
    public void checkDisplayInterstitialDelegates() throws InterruptedException {
    }

    @Override
    public void checkVideoInterstitialDelegates() {
    }

    @Override
    public void checkVideoRewardedDelegates() {
    }

    @Override
    public void checkVideoOutstreamDelegates() {
    }

    @Override
    public void checkNativeAdsDelegates(String prebidAd) throws InterruptedException {
    }

    @Override
    public void checkLoadDelegate() {
    }

    @Override
    public void checkLoadFailDelegate() {
    }

    @Override
    public void checkClickDelegate() {
    }

    @Override
    public void checkAdRequests() throws InterruptedException, TimeoutException {
        testEnvironment.waitForEvent(InAppBiddingTestEnvironment.InAppBiddingEvents.CACHE_EVENT, 1, 10);
        testEnvironment.waitForEvent(InAppBiddingTestEnvironment.InAppBiddingEvents.GAM_G_DOUBLECLICK, 1, 10);
    }
}
