package adapters.factory;

import adapters.PrebidAdapter;
import appium.common.InAppBiddingTestEnvironment;
import appium.pages.inAppBidding.InAppBiddingAdPageImpl;
import appium.pages.inAppBidding.InAppBiddingHomePageImpl;
import adapters.delegates.*;
import adapters.android.AdMobAdapterAndroid;
import adapters.android.GamAdapterAndroid;
import adapters.android.InAppAdapterAndroid;
import adapters.android.MoPubAdapterAndroid;
import utils.PrebidConstants;

public class PrebidAdapterFactoryAndroid implements PrebidAdapterFactory {
    @Override
    public PrebidAdapter createPrebidAdapter(String prebidAd, InAppBiddingTestEnvironment testEnvironment, InAppBiddingAdPageImpl adPage) throws NoSuchFieldException {
        switch (PrebidAdapter.getAdapterFromAd(prebidAd)) {
            case PrebidConstants.IN_APP:
                return new InAppAdapterAndroid(testEnvironment, adPage);
            case PrebidConstants.GAM:
                return new GamAdapterAndroid(testEnvironment, adPage);
            case PrebidConstants.MOPUB:
                return new MoPubAdapterAndroid(testEnvironment, adPage);
            case PrebidConstants.ADMOB:
                return new AdMobAdapterAndroid(testEnvironment, adPage);
            default:
                throw new NoSuchFieldException(PrebidConstants.ADAPTER_NOT_EXIST_MESSAGE);
        }
    }

    @Override
    public PrebidAdapter createPrebidAdapter(String prebidAd, InAppBiddingTestEnvironment testEnvironment) throws NoSuchFieldException {
        switch (PrebidAdapter.getAdapterFromAd(prebidAd)) {
            case PrebidConstants.IN_APP:
                return new InAppAdapterAndroid(testEnvironment);
            case PrebidConstants.GAM:
                return new GamAdapterAndroid(testEnvironment);
            case PrebidConstants.MOPUB:
                return new MoPubAdapterAndroid(testEnvironment);
            case PrebidConstants.ADMOB:
                return new AdMobAdapterAndroid(testEnvironment);
            default:
                throw new NoSuchFieldException(PrebidConstants.ADAPTER_NOT_EXIST_MESSAGE);
        }
    }
}
