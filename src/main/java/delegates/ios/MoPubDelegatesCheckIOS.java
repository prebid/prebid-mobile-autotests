package delegates.ios;

import appium.pages.inAppBidding.InAppBiddingAdPageImpl;
import appium.pages.inAppBidding.InAppBiddingHomePageImpl;
import delegates.DelegatesCheck;

import static appium.common.InAppBiddingTestEnvironment.InAppBiddingDelegates.*;

public class MoPubDelegatesCheckIOS implements DelegatesCheck {
    private final InAppBiddingHomePageImpl homePage;
    private InAppBiddingAdPageImpl adPage;

    public MoPubDelegatesCheckIOS(InAppBiddingHomePageImpl homePage, InAppBiddingAdPageImpl adPage) {
        this.homePage = homePage;
        this.adPage = adPage;
    }

    public MoPubDelegatesCheckIOS(InAppBiddingHomePageImpl homePage) {
        this.homePage = homePage;
    }

    @Override
    public void checkBannerDelegates() {
        homePage.clickCloseButtonClickThroughBrowser();
        homePage.isDelegateEnabled(AD_VIEW_DID_LOAD);
        homePage.isDelegateEnabled(AD_PRESENT_MODAL_VIEW);
        homePage.isDelegateEnabled(AD_DISMISS_MODAL_VIEW);
        // TO-DO -- FIX IT
//            bannerPage.isDelegateEnabled(AD_WILL_LEAVE_APP);
        homePage.isDelegateEnabled(AD_CONTROLLER_FOR_PRESENTING_MODAL_VIEW);
    }

    @Override
    public void checkDisplayInterstitialDelegates() {
        adPage.clickCloseInterstitial();
        homePage.isDelegateEnabled(INTERSTITIAL_DID_LOAD);
        homePage.isDelegateEnabled(INTERSTITIAL_WILL_APPEAR);
        homePage.isDelegateEnabled(INTERSTITIAL_DID_APPEAR);
        homePage.isDelegateEnabled(INTERSTITIAL_DID_DISAPPEAR);
        homePage.isDelegateEnabled(INTERSTITIAL_WILL_DISAPPEAR);
        homePage.isDelegateEnabled(INTERSTITIAL_DID_RECEIVED_TAP);
    }

    @Override
    public void checkVideoInterstitialDelegates() {
        adPage.clickCloseInterstitial();
        homePage.isDelegateEnabled(INTERSTITIAL_DID_LOAD);
        homePage.isDelegateEnabled(INTERSTITIAL_WILL_APPEAR);
        homePage.isDelegateEnabled(INTERSTITIAL_DID_APPEAR);
        homePage.isDelegateEnabled(INTERSTITIAL_DID_DISAPPEAR);
        homePage.isDelegateEnabled(INTERSTITIAL_WILL_DISAPPEAR);
        homePage.isDelegateEnabled(INTERSTITIAL_DID_RECEIVED_TAP);
    }

    @Override
    public void checkVideoRewardedDelegates() {
        homePage.isDelegateEnabled(REWARDED_VIDEO_AD_DID_LOAD);
        homePage.isDelegateEnabled(REWARDED_VIDEO_AD_WILL_PRESENT);
        homePage.isDelegateEnabled(REWARDED_VIDEO_AD_DID_PRESENT);
        homePage.isDelegateEnabled(REWARDED_VIDEO_AD_WILL_DISMISS);
        homePage.isDelegateEnabled(REWARDED_VIDEO_AD_DID_DISMISS);
        homePage.isDelegateEnabled(REWARDED_VIDEO_AD_DID_TAP);
        homePage.isDelegateEnabled(REWARDED_VIDEO_AD_SHOULD_REWARD);
    }

    @Override
    public void checkVideoOutstreamDelegates() {
        homePage.isDelegateEnabled(AD_VIEW_RECEIVED);
        homePage.isDelegateEnabled(AD_VIEW_PRESENT);
        homePage.isDelegateEnabled(AD_VIEW_WILL_LEAVE);
        homePage.isDelegateEnabled(AD_VIEW_DID_DISMISS);
    }

    @Override
    public void checkNativeAdsDelegates(String prebidAd)  {
        homePage.isDelegateEnabled(GET_NATIVE_AD);
        homePage.isDelegateEnabled(NATIVE_AD_PRIMARY_WIN);
        homePage.isDelegateEnabled(NATIVE_AD_DID_TRACK_IMPRESSION);
    }
}
