package delegates.android;

import appium.pages.inAppBidding.InAppBiddingAdPageImpl;
import appium.pages.inAppBidding.InAppBiddingHomePageImpl;
import delegates.DelegatesCheck;

import static appium.common.InAppBiddingTestEnvironment.InAppBiddingDelegates.*;
import static appium.common.InAppBiddingTestEnvironment.InAppBiddingDelegates.ON_AD_CLOSED;

public class AdMobDelegatesCheckAndroid implements DelegatesCheck {

    private final InAppBiddingHomePageImpl homePage;
    private InAppBiddingAdPageImpl adPage;

    public AdMobDelegatesCheckAndroid(InAppBiddingHomePageImpl homePage, InAppBiddingAdPageImpl adPage) {
        this.homePage = homePage;
        this.adPage = adPage;
    }

    public AdMobDelegatesCheckAndroid(InAppBiddingHomePageImpl homePage) {
        this.homePage = homePage;
    }

    @Override
    public void checkBannerDelegates()  {
        homePage.isDelegateEnabled(ON_AD_LOADED);
        homePage.isDelegateEnabled(ON_AD_IMPRESSION);
        adPage.clickBanner();
        homePage.clickCloseButtonClickThroughBrowser();
        homePage.isDelegateEnabled(ON_AD_CLICKED);
        homePage.isDelegateEnabled(ON_AD_OPENED);
        homePage.isDelegateEnabled(ON_AD_CLOSED);
    }

    @Override
    public void checkDisplayInterstitialDelegates() {
        homePage.isDelegateEnabled(ON_AD_LOADED);
        homePage.isDelegateEnabled(ON_AD_CLICKED);
        homePage.isDelegateEnabled(ON_AD_IMPRESSION);
        homePage.isDelegateEnabled(ON_AD_SHOWED);
        homePage.isDelegateEnabled(ON_INTERSTITIAL_DISMISSED);
    }

    @Override
    public void checkVideoInterstitialDelegates()  {
        homePage.isDelegateEnabled(ON_AD_LOADED);
        homePage.isDelegateEnabled(ON_AD_CLICKED);
        homePage.isDelegateEnabled(ON_AD_IMPRESSION);
        homePage.isDelegateEnabled(ON_AD_SHOWED);
        homePage.isDelegateEnabled(ON_INTERSTITIAL_DISMISSED);
    }

    @Override
    public void checkVideoRewardedDelegates() {
        homePage.isDelegateEnabled(ON_AD_LOADED);
        homePage.isDelegateEnabled(ON_AD_CLICKED);
        homePage.isDelegateEnabled(ON_AD_IMPRESSION);
        homePage.isDelegateEnabled(ON_AD_SHOWED);
        homePage.isDelegateEnabled(ON_INTERSTITIAL_DISMISSED);
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
        adPage.clickTvBody();
        Thread.sleep(1000);
        homePage.clickBack();
        homePage.isDelegateEnabled(ON_AD_LOADED);
        homePage.isDelegateEnabled(ON_AD_CLICKED);
        homePage.isDelegateEnabled(ON_AD_SHOWED);
        homePage.isDelegateEnabled(ON_AD_OPENED);
    }

}
