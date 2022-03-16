package delegates.ios;

import appium.pages.inAppBidding.InAppBiddingAdPageImpl;
import appium.pages.inAppBidding.InAppBiddingHomePageImpl;
import delegates.DelegatesCheck;

import static appium.common.InAppBiddingTestEnvironment.InAppBiddingDelegates.*;

public class AdMobDelegatesCheckIOS implements DelegatesCheck {

    private final InAppBiddingHomePageImpl homePage;
    private InAppBiddingAdPageImpl adPage;

    public AdMobDelegatesCheckIOS(InAppBiddingHomePageImpl homePage, InAppBiddingAdPageImpl adPage) {
        this.homePage = homePage;
        this.adPage = adPage;
    }

    public AdMobDelegatesCheckIOS(InAppBiddingHomePageImpl homePage) {
        this.homePage = homePage;
    }

    @Override
    public void checkBannerDelegates() throws InterruptedException {
        homePage.openInBrowser();
        adPage.waitAndReturnToApp();
        homePage.isDelegateEnabled(AD_VIEW_DID_LOAD);
        homePage.isDelegateEnabled(AD_VIEW_DID_RECORD_IMPRESSION);
        homePage.isDelegateEnabled(AD_WILL_PRESENT_SCREEN);
        homePage.isDelegateEnabled(AD_WILL_DISMISS_SCREEN);
        homePage.isDelegateEnabled(AD_DID_DISMISS_SCREEN);
    }

    @Override
    public void checkDisplayInterstitialDelegates() {
        homePage.isDelegateEnabled(INTERSTITIAL_DID_PRESENT_FULLSCREEN);
        homePage.isDelegateEnabled(INTERSTITIAL_WILL_DISMISS_FULLSCREEN);
        homePage.isDelegateEnabled(INTERSTITIAL_DID_DISMISS_FULLSCREEN);
        homePage.isDelegateEnabled(INTERSTITIAL_DID_RECORD_IMPRESSION);
    }

    @Override
    public void checkVideoInterstitialDelegates() {
        homePage.isDelegateEnabled(INTERSTITIAL_DID_RECEIVE_BUTTON);
        homePage.isDelegateEnabled(INTERSTITIAL_DID_PRESENT_FULLSCREEN);
        homePage.isDelegateEnabled(INTERSTITIAL_WILL_DISMISS_FULLSCREEN);
        homePage.isDelegateEnabled(INTERSTITIAL_DID_DISMISS_FULLSCREEN);
        homePage.isDelegateEnabled(INTERSTITIAL_DID_RECORD_IMPRESSION);
    }

    @Override
    public void checkVideoRewardedDelegates() {
        homePage.isDelegateEnabled(INTERSTITIAL_DID_RECEIVE_BUTTON);
        homePage.isDelegateEnabled(INTERSTITIAL_DID_PRESENT_FULLSCREEN);
        homePage.isDelegateEnabled(INTERSTITIAL_WILL_DISMISS_FULLSCREEN);
        homePage.isDelegateEnabled(INTERSTITIAL_DID_DISMISS_FULLSCREEN);
        homePage.isDelegateEnabled(INTERSTITIAL_DID_RECORD_IMPRESSION);
    }

    @Override
    public void checkVideoOutstreamDelegates() {
        homePage.isDelegateEnabled(AD_VIEW_RECEIVED);
        homePage.isDelegateEnabled(AD_VIEW_PRESENT);
        homePage.isDelegateEnabled(AD_VIEW_WILL_LEAVE);
        homePage.isDelegateEnabled(AD_VIEW_DID_DISMISS);
    }

    @Override
    public void checkNativeAdsDelegates(String prebidAd) {
        homePage.isDelegateEnabled(LOADER_DID_RECEIVE_BUTTON);
        homePage.isDelegateEnabled(LOADER_DID_FINISH_LOADING_BUTTON);
    }
}
