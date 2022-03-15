package delegates;

import appium.pages.inAppBidding.InAppBiddingAdPageImpl;
import appium.pages.inAppBidding.InAppBiddingHomePageImpl;

import static appium.common.InAppBiddingTestEnvironment.InAppBiddingDelegates.*;

public class MoPubDelegatesCheck implements DelegatesCheck {
    private InAppBiddingHomePageImpl homePage;
    private InAppBiddingAdPageImpl adPage;

    public MoPubDelegatesCheck(InAppBiddingHomePageImpl homePage, InAppBiddingAdPageImpl adPage) {
        this.homePage = homePage;
        this.adPage = adPage;
    }

    public MoPubDelegatesCheck(InAppBiddingHomePageImpl homePage) {
        this.homePage = homePage;
    }

    @Override
    public void checkAndroidBannerDelegates() {
        homePage.isDelegateEnabled(ON_BANNER_LOADED);
        adPage.clickBanner();
        homePage.clickCloseButtonClickThroughBrowser();
        homePage.isDelegateEnabled(ON_AD_CLICKED);
    }

    @Override
    public void checkAndroidDisplayInterstitialDelegates() {
        homePage.isDelegateEnabled(ON_BANNER_LOADED);
        homePage.isDelegateEnabled(ON_AD_DISPLAYED);
        homePage.isDelegateEnabled(ON_INTERSTITIAL_DISMISSED);
    }

    @Override
    public void checkAndroidVideoInterstitialDelegates()  {
        homePage.isDelegateEnabled(ON_BANNER_LOADED);
        homePage.isDelegateEnabled(ON_AD_DISPLAYED);
        homePage.isDelegateEnabled(ON_AD_CLICKED);
        homePage.isDelegateEnabled(ON_INTERSTITIAL_DISMISSED);

    }

    @Override
    public void checkAndroidVideoRewardedDelegates() {
        homePage.isDelegateEnabled(ON_REWARDED_STARTED);
        homePage.isDelegateEnabled(ON_REWARDED_COMPLETED);
        homePage.isDelegateEnabled(ON_AD_CLICKED);
    }

    @Override
    public void checkAndroidVideoOutstreamDelegates() {
        homePage.isDelegateEnabled(ON_AD_LOADED);
        homePage.isDelegateEnabled(ON_AD_DISPLAYED);
        homePage.isDelegateEnabled(ON_AD_CLICKED);
    }

    @Override
    public void checkAndroidNativeAdsDelegates(String prebidAd) throws InterruptedException {
        adPage.clickHereToVisitOurSite();
        Thread.sleep(1000);
        homePage.clickBack();
        homePage.isDelegateEnabled(NATIVE_AD_DID_LOAD);
        homePage.isDelegateEnabled(ON_AD_CLICKED);
        homePage.isDelegateEnabled(NATIVE_AD_DID_LOG_IMPRESSION);
    }

    @Override
    public void checkIosBannerDelegates() {
        homePage.clickCloseButtonClickThroughBrowser();
        homePage.isDelegateEnabled(AD_VIEW_DID_LOAD);
        homePage.isDelegateEnabled(AD_PRESENT_MODAL_VIEW);
        homePage.isDelegateEnabled(AD_DISMISS_MODAL_VIEW);
        // TO-DO -- FIX IT
//            bannerPage.isDelegateEnabled(AD_WILL_LEAVE_APP);
        homePage.isDelegateEnabled(AD_CONTROLLER_FOR_PRESENTING_MODAL_VIEW);
    }

    @Override
    public void checkIosDisplayInterstitialDelegates() {
        adPage.clickCloseInterstitial();
        homePage.isDelegateEnabled(INTERSTITIAL_DID_LOAD);
        homePage.isDelegateEnabled(INTERSTITIAL_WILL_APPEAR);
        homePage.isDelegateEnabled(INTERSTITIAL_DID_APPEAR);
        homePage.isDelegateEnabled(INTERSTITIAL_DID_DISAPPEAR);
        homePage.isDelegateEnabled(INTERSTITIAL_WILL_DISAPPEAR);
        homePage.isDelegateEnabled(INTERSTITIAL_DID_RECEIVED_TAP);
    }

    @Override
    public void checkIosVideoInterstitialDelegates() {
        adPage.clickCloseInterstitial();
        homePage.isDelegateEnabled(INTERSTITIAL_DID_LOAD);
        homePage.isDelegateEnabled(INTERSTITIAL_WILL_APPEAR);
        homePage.isDelegateEnabled(INTERSTITIAL_DID_APPEAR);
        homePage.isDelegateEnabled(INTERSTITIAL_DID_DISAPPEAR);
        homePage.isDelegateEnabled(INTERSTITIAL_WILL_DISAPPEAR);
        homePage.isDelegateEnabled(INTERSTITIAL_DID_RECEIVED_TAP);
    }

    @Override
    public void checkIosVideoRewardedDelegates() {
        homePage.isDelegateEnabled(REWARDED_VIDEO_AD_DID_LOAD);
        homePage.isDelegateEnabled(REWARDED_VIDEO_AD_WILL_PRESENT);
        homePage.isDelegateEnabled(REWARDED_VIDEO_AD_DID_PRESENT);
        homePage.isDelegateEnabled(REWARDED_VIDEO_AD_WILL_DISMISS);
        homePage.isDelegateEnabled(REWARDED_VIDEO_AD_DID_DISMISS);
        homePage.isDelegateEnabled(REWARDED_VIDEO_AD_DID_TAP);
        homePage.isDelegateEnabled(REWARDED_VIDEO_AD_SHOULD_REWARD);
    }

    @Override
    public void checkIosVideoOutstreamDelegates() {
        homePage.isDelegateEnabled(AD_VIEW_RECEIVED);
        homePage.isDelegateEnabled(AD_VIEW_PRESENT);
        homePage.isDelegateEnabled(AD_VIEW_WILL_LEAVE);
        homePage.isDelegateEnabled(AD_VIEW_DID_DISMISS);
    }

    @Override
    public void checkIosNativeAdsDelegates(String prebidAd)  {
        homePage.isDelegateEnabled(GET_NATIVE_AD);
        homePage.isDelegateEnabled(NATIVE_AD_PRIMARY_WIN);
        homePage.isDelegateEnabled(NATIVE_AD_DID_TRACK_IMPRESSION);
    }
}
