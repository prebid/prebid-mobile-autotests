package delegates.factory;

import appium.pages.inAppBidding.InAppBiddingAdPageImpl;
import appium.pages.inAppBidding.InAppBiddingHomePageImpl;
import delegates.DelegatesCheck;

public interface DelegatesCheckFactory {
    DelegatesCheck provideDelegatesCheck(String adapter, InAppBiddingHomePageImpl homePage, InAppBiddingAdPageImpl adPage) throws NoSuchFieldException;
    DelegatesCheck provideDelegatesCheck(String adapter, InAppBiddingHomePageImpl homePage) throws NoSuchFieldException;
}
