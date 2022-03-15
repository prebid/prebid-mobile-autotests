package delegates;

import appium.pages.inAppBidding.InAppBiddingAdPageImpl;
import appium.pages.inAppBidding.InAppBiddingHomePageImpl;

import static appium.common.InAppBiddingTestEnvironment.InAppBiddingDelegates.*;
import static appium.common.InAppBiddingTestEnvironment.InAppBiddingDelegates.ON_AD_CLOSED;

public class AdMobDelegatesCheck implements DelegatesCheck {

    private InAppBiddingHomePageImpl homePage;
    private InAppBiddingAdPageImpl adPage;

    public AdMobDelegatesCheck(InAppBiddingHomePageImpl homePage, InAppBiddingAdPageImpl adPage) {
        this.homePage = homePage;
        this.adPage = adPage;
    }

    public AdMobDelegatesCheck(InAppBiddingHomePageImpl homePage) {
        this.homePage = homePage;
    }

    @Override
    public void checkAndroidBannerDelegates()  {
        homePage.isDelegateEnabled(ON_AD_LOADED);
        homePage.isDelegateEnabled(ON_AD_IMPRESSION);
        adPage.clickBanner();
        homePage.clickCloseButtonClickThroughBrowser();
        homePage.isDelegateEnabled(ON_AD_CLICKED);
        homePage.isDelegateEnabled(ON_AD_OPENED);
        homePage.isDelegateEnabled(ON_AD_CLOSED);
    }

    @Override
    public void checkAndroidDisplayInterstitialDelegates() {
        homePage.isDelegateEnabled(ON_AD_LOADED);
        homePage.isDelegateEnabled(ON_AD_CLICKED);
        homePage.isDelegateEnabled(ON_AD_IMPRESSION);
        homePage.isDelegateEnabled(ON_AD_SHOWED);
        homePage.isDelegateEnabled(ON_INTERSTITIAL_DISMISSED);
    }

    @Override
    public void checkAndroidVideoInterstitialDelegates()  {
        homePage.isDelegateEnabled(ON_AD_LOADED);
        homePage.isDelegateEnabled(ON_AD_CLICKED);
        homePage.isDelegateEnabled(ON_AD_IMPRESSION);
        homePage.isDelegateEnabled(ON_AD_SHOWED);
        homePage.isDelegateEnabled(ON_INTERSTITIAL_DISMISSED);
    }

    @Override
    public void checkAndroidVideoRewardedDelegates() {
        homePage.isDelegateEnabled(ON_AD_LOADED);
        homePage.isDelegateEnabled(ON_AD_CLICKED);
        homePage.isDelegateEnabled(ON_AD_IMPRESSION);
        homePage.isDelegateEnabled(ON_AD_SHOWED);
        homePage.isDelegateEnabled(ON_INTERSTITIAL_DISMISSED);
        homePage.isDelegateEnabled(ON_AD_CLICKED);
    }

    @Override
    public void checkAndroidVideoOutstreamDelegates() {
        homePage.isDelegateEnabled(ON_AD_LOADED);
        homePage.isDelegateEnabled(ON_AD_DISPLAYED);
        homePage.isDelegateEnabled(ON_AD_CLICKED);
    }

    @Override
    public void checkAndroidNativeAdsDelegates() throws InterruptedException {

    }

    @Override
    public void checkIosBannerDelegates() throws InterruptedException {
       homePage.openInBrowser();
       adPage.waitAndReturnToApp();
       homePage.isDelegateEnabled(AD_VIEW_DID_LOAD);
       homePage.isDelegateEnabled(AD_VIEW_DID_RECORD_IMPRESSION);
       homePage.isDelegateEnabled(AD_WILL_PRESENT_SCREEN);
       homePage.isDelegateEnabled(AD_WILL_DISMISS_SCREEN);
       homePage.isDelegateEnabled(AD_DID_DISMISS_SCREEN);
    }

    @Override
    public void checkIosDisplayInterstitialDelegates() {
        homePage.isDelegateEnabled(INTERSTITIAL_DID_PRESENT_FULLSCREEN);
        homePage.isDelegateEnabled(INTERSTITIAL_WILL_DISMISS_FULLSCREEN);
        homePage.isDelegateEnabled(INTERSTITIAL_DID_DISMISS_FULLSCREEN);
        homePage.isDelegateEnabled(INTERSTITIAL_DID_RECORD_IMPRESSION);
    }

    @Override
    public void checkIosVideoInterstitialDelegates() {
        homePage.isDelegateEnabled(INTERSTITIAL_DID_RECEIVE_BUTTON);
        homePage.isDelegateEnabled(INTERSTITIAL_DID_PRESENT_FULLSCREEN);
        homePage.isDelegateEnabled(INTERSTITIAL_WILL_DISMISS_FULLSCREEN);
        homePage.isDelegateEnabled(INTERSTITIAL_DID_DISMISS_FULLSCREEN);
        homePage.isDelegateEnabled(INTERSTITIAL_DID_RECORD_IMPRESSION);
    }

    @Override
    public void checkIosVideoRewardedDelegates() {
        homePage.isDelegateEnabled(INTERSTITIAL_DID_RECEIVE_BUTTON);
        homePage.isDelegateEnabled(INTERSTITIAL_DID_PRESENT_FULLSCREEN);
        homePage.isDelegateEnabled(INTERSTITIAL_WILL_DISMISS_FULLSCREEN);
        homePage.isDelegateEnabled(INTERSTITIAL_DID_DISMISS_FULLSCREEN);
        homePage.isDelegateEnabled(INTERSTITIAL_DID_RECORD_IMPRESSION);
    }

    @Override
    public void checkIosVideoOutstreamDelegates() {
        homePage.isDelegateEnabled(AD_VIEW_RECEIVED);
        homePage.isDelegateEnabled(AD_VIEW_PRESENT);
        homePage.isDelegateEnabled(AD_VIEW_WILL_LEAVE);
        homePage.isDelegateEnabled(AD_VIEW_DID_DISMISS);
    }

    @Override
    public void checkIosNativeAdsDelegates() throws InterruptedException {

    }
}
