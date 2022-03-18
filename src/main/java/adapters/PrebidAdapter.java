package adapters;

import adapters.delegates.DelegatesInspector;
import adapters.events.EventsInspector;
import appium.common.InAppBiddingTestEnvironment;
import appium.pages.inAppBidding.InAppBiddingAdPageImpl;
import appium.pages.inAppBidding.InAppBiddingHomePageImpl;

public abstract class PrebidAdapter implements DelegatesInspector, EventsInspector {
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
