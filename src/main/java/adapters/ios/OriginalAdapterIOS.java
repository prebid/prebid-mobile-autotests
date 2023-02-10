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
    }

    @Override
    public void checkDisplayInterstitialDelegates() throws InterruptedException {
    }

    @Override
    public void checkVideoInterstitialDelegates() throws InterruptedException {
    }

    @Override
    public void checkVideoRewardedDelegates() throws InterruptedException {
    }

    @Override
    public void checkVideoOutstreamDelegates() throws InterruptedException {
    }

    @Override
    public void checkNativeAdsDelegates(String prebidAd) throws InterruptedException {
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
    public void checkAdRequests() throws InterruptedException, TimeoutException {
        testEnvironment.waitForEvent(InAppBiddingTestEnvironment.InAppBiddingEvents.CACHE_EVENT, 1, 10);
        testEnvironment.waitForEvent(InAppBiddingTestEnvironment.InAppBiddingEvents.GAM_GAMPAD, 1, 10);
    }
}
