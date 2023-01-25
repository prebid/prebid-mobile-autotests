package adapters.factory;

import adapters.PrebidAdapter;
import adapters.android.*;
import appium.common.InAppBiddingTestEnvironment;
import appium.pages.inAppBidding.InAppBiddingAdPageImpl;
import utils.PrebidConstants;

public class PrebidAdapterFactoryAndroid implements PrebidAdapterFactory {
    @Override
    public PrebidAdapter createPrebidAdapter(String prebidAd, InAppBiddingTestEnvironment testEnvironment, InAppBiddingAdPageImpl adPage) throws NoSuchFieldException {
        switch (PrebidAdapter.getAdapterFromAd(prebidAd)) {
            case PrebidConstants.IN_APP:
                return new InAppAdapterAndroid(testEnvironment, adPage);
            case PrebidConstants.GAM:
                return new GamAdapterAndroid(testEnvironment, adPage);
            case PrebidConstants.ORIGINAL:
                return new OriginalAdapterAndroid(testEnvironment, adPage);
            case PrebidConstants.ADMOB:
                return new AdMobAdapterAndroid(testEnvironment, adPage);
            case PrebidConstants.MAX:
                return new MaxAdapterAndroid(testEnvironment, adPage);
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
            case PrebidConstants.ORIGINAL:
                return new OriginalAdapterAndroid(testEnvironment);
            case PrebidConstants.ADMOB:
                return new AdMobAdapterAndroid(testEnvironment);
            case PrebidConstants.MAX:
                return new MaxAdapterAndroid(testEnvironment);
            default:
                throw new NoSuchFieldException(PrebidConstants.ADAPTER_NOT_EXIST_MESSAGE);
        }
    }
}
