package adapters;

import adapters.delegates.DelegatesInspector;
import adapters.adrequests.AdRequestsInspector;
import appium.common.InAppBiddingTestEnvironment;
import appium.pages.inAppBidding.InAppBiddingAdPageImpl;

public abstract class PrebidAdapter implements DelegatesInspector, AdRequestsInspector {
    protected final InAppBiddingTestEnvironment testEnvironment;
    protected InAppBiddingAdPageImpl adPage;

    public PrebidAdapter(InAppBiddingTestEnvironment testEnvironment, InAppBiddingAdPageImpl adPage) {
        this.testEnvironment = testEnvironment;
        this.adPage = adPage;
    }

    public PrebidAdapter(InAppBiddingTestEnvironment testEnvironment) {
        this.testEnvironment = testEnvironment;
    }

    public static String getAdapterFromAd(String prebidAd) {
        return prebidAd.substring(prebidAd.indexOf("(") + 1, prebidAd.indexOf(")"));
    }

}
