package adapters.android;

import adapters.PrebidAdapter;
import appium.common.InAppBiddingTestEnvironment;
import appium.pages.inAppBidding.InAppBiddingAdPageImpl;
import appium.pages.inAppBidding.InAppBiddingHomePageImpl;
import adapters.delegates.DelegatesInspector;

import static appium.common.InAppBiddingTestEnvironment.InAppBiddingDelegates.*;

public class InAppAdapterAndroid extends PrebidAdapter {

    public InAppAdapterAndroid(InAppBiddingTestEnvironment testEnvironment, InAppBiddingAdPageImpl adPage) {
        super(testEnvironment,adPage);
    }

    public InAppAdapterAndroid(InAppBiddingTestEnvironment testEnvironment) {
        super(testEnvironment);
    }

    @Override
    public void checkBannerDelegates()  {
        testEnvironment.homePage.isDelegateEnabled(ON_AD_LOADED);
        testEnvironment.homePage.isDelegateEnabled(ON_AD_DISPLAYED);
        adPage.clickBanner();
        testEnvironment.homePage.clickCloseButtonClickThroughBrowser();
        testEnvironment.homePage.isDelegateEnabled(ON_AD_CLICKED);
        testEnvironment.homePage.isDelegateEnabled(ON_AD_CLOSED);
    }

    @Override
    public void checkDisplayInterstitialDelegates() {
        testEnvironment.homePage.isDelegateEnabled(ON_AD_LOADED);
        testEnvironment.homePage.isDelegateEnabled(ON_AD_DISPLAYED);
        testEnvironment.homePage.isDelegateEnabled(ON_AD_CLOSED);
    }

    @Override
    public void checkVideoInterstitialDelegates() {
        testEnvironment.homePage.isDelegateEnabled(ON_AD_LOADED);
        testEnvironment.homePage.isDelegateEnabled(ON_AD_DISPLAYED);
        testEnvironment.homePage.isDelegateEnabled(ON_AD_CLICKED);
        testEnvironment.homePage.isDelegateEnabled(ON_AD_CLOSED);
    }

    @Override
    public void checkVideoRewardedDelegates() {
        testEnvironment.homePage.isDelegateEnabled(ON_AD_CLOSED);
        testEnvironment.homePage.isDelegateEnabled(ON_AD_CLICKED);
    }

    @Override
    public void checkVideoOutstreamDelegates() {
        testEnvironment.homePage.isDelegateEnabled(ON_AD_LOADED);
        testEnvironment.homePage.isDelegateEnabled(ON_AD_DISPLAYED);
        testEnvironment.homePage.isDelegateEnabled(ON_AD_CLICKED);
    }

    @Override
    public void checkNativeAdsDelegates(String prebidAd) throws InterruptedException {
        if (prebidAd.contains("Links")) {
            adPage.clickBtnNativeLinkRoot();
        } else {
            adPage.clickHereToVisitOurSite();
        }
        Thread.sleep(1000);
        testEnvironment.homePage.clickBack();
        testEnvironment.homePage.isDelegateEnabled(ON_NATIVE_GET_NATIVE_AD_SUCCESS);
        testEnvironment.homePage.isDelegateEnabled(ON_AD_CLICKED);
        testEnvironment.homePage.isDelegateEnabled(ON_AD_IMPRESSION);
    }

    @Override
    public void checkEvents() {

    }
}
