package delegates.factory;

import appium.pages.inAppBidding.InAppBiddingAdPageImpl;
import appium.pages.inAppBidding.InAppBiddingHomePageImpl;
import delegates.DelegatesCheck;
import delegates.android.AdMobDelegatesCheckAndroid;
import delegates.android.GamDelegatesCheckAndroid;
import delegates.android.InAppDelegatesCheckAndroid;
import delegates.android.MoPubDelegatesCheckAndroid;
import delegates.ios.AdMobDelegatesCheckIOS;
import delegates.ios.GamDelegatesCheckIOS;
import delegates.ios.InAppDelegatesCheckIOS;
import delegates.ios.MoPubDelegatesCheckIOS;
import utils.PrebidConstants;

public class DelegatesCheckFactoryIOS implements DelegatesCheckFactory {
    @Override
    public DelegatesCheck provideDelegatesCheck(String adapter, InAppBiddingHomePageImpl homePage, InAppBiddingAdPageImpl adPage) throws NoSuchFieldException {
        switch (adapter) {
            case PrebidConstants.IN_APP:
                return new InAppDelegatesCheckIOS(homePage, adPage);
            case PrebidConstants.GAM:
                return new GamDelegatesCheckIOS(homePage, adPage);
            case PrebidConstants.MOPUB:
                return new MoPubDelegatesCheckIOS(homePage, adPage);
            case PrebidConstants.ADMOB:
                return new AdMobDelegatesCheckIOS(homePage, adPage);
            default:
                throw new NoSuchFieldException(PrebidConstants.ADAPTER_NOT_EXIST_MESSAGE);
        }
    }

    @Override
    public DelegatesCheck provideDelegatesCheck(String adapter, InAppBiddingHomePageImpl homePage) throws NoSuchFieldException {
        switch (adapter) {
            case PrebidConstants.IN_APP:
                return new InAppDelegatesCheckIOS(homePage);
            case PrebidConstants.GAM:
                return new GamDelegatesCheckIOS(homePage);
            case PrebidConstants.MOPUB:
                return new MoPubDelegatesCheckIOS(homePage);
            case PrebidConstants.ADMOB:
                return new AdMobDelegatesCheckIOS(homePage);
            default:
                throw new NoSuchFieldException(PrebidConstants.ADAPTER_NOT_EXIST_MESSAGE);
        }
    }
}
