package delegates.factory;

import appium.pages.inAppBidding.InAppBiddingAdPageImpl;
import appium.pages.inAppBidding.InAppBiddingHomePageImpl;
import delegates.*;
import delegates.android.AdMobDelegatesCheckAndroid;
import delegates.android.GamDelegatesCheckAndroid;
import delegates.android.InAppDelegatesCheckAndroid;
import delegates.android.MoPubDelegatesCheckAndroid;
import utils.PrebidConstants;

public class DelegatesCheckFactoryAndroid implements DelegatesCheckFactory {
    @Override
    public DelegatesCheck provideDelegatesCheck(String adapter, InAppBiddingHomePageImpl homePage, InAppBiddingAdPageImpl adPage) throws NoSuchFieldException {
        switch (adapter) {
            case PrebidConstants.IN_APP:
                return new InAppDelegatesCheckAndroid(homePage, adPage);
            case PrebidConstants.GAM:
                return new GamDelegatesCheckAndroid(homePage, adPage);
            case PrebidConstants.MOPUB:
                return new MoPubDelegatesCheckAndroid(homePage, adPage);
            case PrebidConstants.ADMOB:
                return new AdMobDelegatesCheckAndroid(homePage, adPage);
            default:
                throw new NoSuchFieldException(PrebidConstants.ADAPTER_NOT_EXIST_MESSAGE);
        }
    }

    @Override
    public DelegatesCheck provideDelegatesCheck(String adapter, InAppBiddingHomePageImpl homePage) throws NoSuchFieldException {
        switch (adapter) {
            case PrebidConstants.IN_APP:
                return new InAppDelegatesCheckAndroid(homePage);
            case PrebidConstants.GAM:
                return new GamDelegatesCheckAndroid(homePage);
            case PrebidConstants.MOPUB:
                return new MoPubDelegatesCheckAndroid(homePage);
            case PrebidConstants.ADMOB:
                return new AdMobDelegatesCheckAndroid(homePage);
            default:
                throw new NoSuchFieldException(PrebidConstants.ADAPTER_NOT_EXIST_MESSAGE);
        }
    }
}
