package delegates.factory;

import appium.pages.inAppBidding.InAppBiddingAdPageImpl;
import appium.pages.inAppBidding.InAppBiddingHomePageImpl;
import delegates.*;
import utils.PrebidConstants;

public class DelegatesCheckFactoryImpl implements DelegatesCheckFactory {
    @Override
    public DelegatesCheck provideDelegatesCheck(String adapter, InAppBiddingHomePageImpl homePage, InAppBiddingAdPageImpl adPage) throws NoSuchFieldException {
        switch (adapter) {
            case PrebidConstants.IN_APP:
                return new InAppDelegatesCheck(homePage, adPage);
            case PrebidConstants.GAM:
                return new GamDelegatesCheck(homePage, adPage);
            case PrebidConstants.MOPUB:
                return new MoPubDelegatesCheck(homePage, adPage);
            case PrebidConstants.ADMOB:
                return new AdMobDelegatesCheck(homePage, adPage);
            default:
                throw new NoSuchFieldException("Adapter does not exist");
        }
    }

    @Override
    public DelegatesCheck provideDelegatesCheck(String adapter, InAppBiddingHomePageImpl homePage) throws NoSuchFieldException {
        switch (adapter) {
            case PrebidConstants.IN_APP:
                return new InAppDelegatesCheck(homePage);
            case PrebidConstants.GAM:
                return new GamDelegatesCheck(homePage);
            case PrebidConstants.MOPUB:
                return new MoPubDelegatesCheck(homePage);
            case PrebidConstants.ADMOB:
                return new AdMobDelegatesCheck(homePage);
            default:
                throw new NoSuchFieldException("Adapter does not exist");
        }
    }
}
