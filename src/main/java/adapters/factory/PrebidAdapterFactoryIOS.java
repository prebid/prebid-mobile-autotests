package adapters.factory;

import adapters.PrebidAdapter;
import appium.common.InAppBiddingTestEnvironment;
import appium.pages.inAppBidding.InAppBiddingAdPageImpl;
import appium.pages.inAppBidding.InAppBiddingHomePageImpl;
import adapters.delegates.DelegatesInspector;
import adapters.ios.AdMobAdapterIOS;
import adapters.ios.GamAdapterIOS;
import adapters.ios.InAppAdapterIOS;
import adapters.ios.MoPubAdapterIOS;
import utils.PrebidConstants;

public class PrebidAdapterFactoryIOS implements PrebidAdapterFactory {
    @Override
    public PrebidAdapter createPrebidAdapter(String prebidAd, InAppBiddingTestEnvironment testEnvironment, InAppBiddingAdPageImpl adPage) throws NoSuchFieldException {
        switch (PrebidAdapter.getAdapterFromAd(prebidAd)) {
            case PrebidConstants.IN_APP:
                return new InAppAdapterIOS(testEnvironment, adPage);
            case PrebidConstants.GAM:
                return new GamAdapterIOS(testEnvironment, adPage);
            case PrebidConstants.MOPUB:
                return new MoPubAdapterIOS(testEnvironment, adPage);
            case PrebidConstants.ADMOB:
                return new AdMobAdapterIOS(testEnvironment, adPage);
            default:
                throw new NoSuchFieldException(PrebidConstants.ADAPTER_NOT_EXIST_MESSAGE);
        }
    }

    @Override
    public PrebidAdapter createPrebidAdapter(String prebidAd, InAppBiddingTestEnvironment testEnvironment) throws NoSuchFieldException {
        switch (PrebidAdapter.getAdapterFromAd(prebidAd)) {
            case PrebidConstants.IN_APP:
                return new InAppAdapterIOS(testEnvironment);
            case PrebidConstants.GAM:
                return new GamAdapterIOS(testEnvironment);
            case PrebidConstants.MOPUB:
                return new MoPubAdapterIOS(testEnvironment);
            case PrebidConstants.ADMOB:
                return new AdMobAdapterIOS(testEnvironment);
            default:
                throw new NoSuchFieldException(PrebidConstants.ADAPTER_NOT_EXIST_MESSAGE);
        }
    }
}
