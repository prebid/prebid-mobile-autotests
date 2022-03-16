package delegates.android;

import appium.pages.inAppBidding.InAppBiddingAdPageImpl;
import appium.pages.inAppBidding.InAppBiddingHomePageImpl;
import delegates.DelegatesCheck;

import static appium.common.InAppBiddingTestEnvironment.InAppBiddingDelegates.*;
import static appium.common.InAppBiddingTestEnvironment.InAppBiddingDelegates.ON_AD_CLOSED;

public class GamDelegatesCheckAndroid implements DelegatesCheck {
    private final InAppBiddingHomePageImpl homePage;
    private InAppBiddingAdPageImpl adPage;

    public GamDelegatesCheckAndroid(InAppBiddingHomePageImpl homePage, InAppBiddingAdPageImpl adPage) {
        this.homePage = homePage;
        this.adPage = adPage;
    }

    public GamDelegatesCheckAndroid(InAppBiddingHomePageImpl homePage) {
        this.homePage = homePage;
    }

    @Override
    public void checkBannerDelegates()  {
        homePage.isDelegateEnabled(ON_AD_LOADED);
        homePage.isDelegateEnabled(ON_AD_DISPLAYED);
        adPage.clickBanner();
        homePage.clickCloseButtonClickThroughBrowser();
        homePage.isDelegateEnabled(ON_AD_CLICKED);
        homePage.isDelegateEnabled(ON_AD_CLOSED);
    }

    @Override
    public void checkDisplayInterstitialDelegates() {
        homePage.isDelegateEnabled(ON_AD_LOADED);
        homePage.isDelegateEnabled(ON_AD_DISPLAYED);
        homePage.isDelegateEnabled(ON_AD_CLOSED);
    }

    @Override
    public void checkVideoInterstitialDelegates()  {
        homePage.isDelegateEnabled(ON_AD_LOADED);
        homePage.isDelegateEnabled(ON_AD_DISPLAYED);
        homePage.isDelegateEnabled(ON_AD_CLICKED);
        homePage.isDelegateEnabled(ON_AD_CLOSED);
    }

    @Override
    public void checkVideoRewardedDelegates() {
        homePage.isDelegateEnabled(ON_AD_CLOSED);
        homePage.isDelegateEnabled(ON_AD_CLICKED);
    }

    @Override
    public void checkVideoOutstreamDelegates() {
        homePage.isDelegateEnabled(ON_AD_LOADED);
        homePage.isDelegateEnabled(ON_AD_DISPLAYED);
        homePage.isDelegateEnabled(ON_AD_CLICKED);
    }

    @Override
    public void checkNativeAdsDelegates(String prebidAd) throws InterruptedException {
        if (prebidAd.contains("Custom")) {
            homePage.isDelegateEnabled(CUSTOM_NATIVE_AD_REQUEST_SUCCESS);
        } else if (prebidAd.contains("Unified")) {
            homePage.isDelegateEnabled(UNIFIED_CUSTOM_AD_REQUEST_SUCCESS);
        }
        homePage.isDelegateEnabled(ON_AD_IMPRESSION);
        if (prebidAd.contains("GADUnified")){
            homePage.isDelegateEnabled(UNIFIED_NATIVE_AD_PRIMARY_WIN);
            adPage.clickButtonCallToAction();
        } else {
            if (prebidAd.contains("GAD")) {
                homePage.isDelegateEnabled(CUSTOM_NATIVE_AD_PRIMARY_WIN);
            }
            adPage.clickHereToVisitOurSite();
        }
        Thread.sleep(1000);
        homePage.clickBack();
        homePage.isDelegateEnabled(ON_AD_CLICKED);
    }

}
