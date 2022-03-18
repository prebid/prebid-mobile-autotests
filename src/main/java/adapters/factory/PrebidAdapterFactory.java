package adapters.factory;

import adapters.PrebidAdapter;
import appium.common.InAppBiddingTestEnvironment;
import appium.pages.inAppBidding.InAppBiddingAdPageImpl;
import appium.pages.inAppBidding.InAppBiddingHomePageImpl;
import adapters.delegates.DelegatesInspector;

public interface PrebidAdapterFactory {
    PrebidAdapter createPrebidAdapter(String adapter, InAppBiddingTestEnvironment testEnvironment, InAppBiddingAdPageImpl adPage) throws NoSuchFieldException;
    PrebidAdapter createPrebidAdapter(String adapter, InAppBiddingTestEnvironment testEnvironment) throws NoSuchFieldException;
}
