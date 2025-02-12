package adapters.factory;

import adapters.PrebidAdapter;
import adapters.ios.*;
import appium.common.InAppBiddingTestEnvironment;
import appium.pages.inAppBidding.InAppBiddingAdPageImpl;
import utils.PrebidConstants;

public class PrebidAdapterFactoryIOS implements PrebidAdapterFactory {
    @Override
    public PrebidAdapter createPrebidAdapter(String prebidAd, InAppBiddingTestEnvironment testEnvironment, InAppBiddingAdPageImpl adPage) throws NoSuchFieldException {
        switch (PrebidAdapter.getAdapterFromAd(prebidAd)) {
            case PrebidConstants.IN_APP:
                return new InAppAdapterIOS(testEnvironment, adPage);
            case PrebidConstants.GAM:
                return new GamAdapterIOS(testEnvironment, adPage);
            case PrebidConstants.ORIGINAL:
                return new OriginalAdapterIOS(testEnvironment, adPage);
            case PrebidConstants.ADMOB:
                return new AdMobAdapterIOS(testEnvironment, adPage);
            case PrebidConstants.MAX:
                return new MaxAdapterIOS(testEnvironment, adPage);
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
            case PrebidConstants.ORIGINAL:
                return new OriginalAdapterIOS(testEnvironment);
            case PrebidConstants.ADMOB:
                return new AdMobAdapterIOS(testEnvironment);
            case PrebidConstants.MAX:
                return new MaxAdapterIOS(testEnvironment);
            default:
                throw new NoSuchFieldException(PrebidConstants.ADAPTER_NOT_EXIST_MESSAGE);
        }
    }
}
