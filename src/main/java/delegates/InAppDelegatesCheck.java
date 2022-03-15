package delegates;

import appium.pages.inAppBidding.InAppBiddingAdPageImpl;
import appium.pages.inAppBidding.InAppBiddingHomePageImpl;

import static appium.common.InAppBiddingTestEnvironment.InAppBiddingDelegates.*;

public class InAppDelegatesCheck implements DelegatesCheck {
    private InAppBiddingHomePageImpl homePage;
    private InAppBiddingAdPageImpl adPage;


    public InAppDelegatesCheck(InAppBiddingHomePageImpl homePage, InAppBiddingAdPageImpl adPage) {
        this.homePage = homePage;
        this.adPage = adPage;
    }

    public InAppDelegatesCheck(InAppBiddingHomePageImpl homePage) {
        this.homePage = homePage;
    }

    @Override
    public void checkAndroidBannerDelegates()  {
        homePage.isDelegateEnabled(ON_AD_LOADED);
        homePage.isDelegateEnabled(ON_AD_DISPLAYED);
        adPage.clickBanner();
        homePage.clickCloseButtonClickThroughBrowser();
        homePage.isDelegateEnabled(ON_AD_CLICKED);
        homePage.isDelegateEnabled(ON_AD_CLOSED);
    }

    @Override
    public void checkAndroidDisplayInterstitialDelegates() {
        homePage.isDelegateEnabled(ON_AD_LOADED);
        homePage.isDelegateEnabled(ON_AD_DISPLAYED);
        homePage.isDelegateEnabled(ON_AD_CLOSED);
    }

    @Override
    public void checkAndroidVideoInterstitialDelegates() {
        homePage.isDelegateEnabled(ON_AD_LOADED);
        homePage.isDelegateEnabled(ON_AD_DISPLAYED);
        homePage.isDelegateEnabled(ON_AD_CLICKED);
        homePage.isDelegateEnabled(ON_AD_CLOSED);
    }

    @Override
    public void checkAndroidVideoRewardedDelegates() {
        homePage.isDelegateEnabled(ON_AD_CLOSED);
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
        if (prebidAd.contains("Links")) {
            adPage.clickBtnNativeLinkRoot();
        } else {
            adPage.clickHereToVisitOurSite();
        }
        Thread.sleep(1000);
        homePage.clickBack();
        homePage.isDelegateEnabled(ON_NATIVE_GET_NATIVE_AD_SUCCESS);
        homePage.isDelegateEnabled(ON_AD_CLICKED);
        homePage.isDelegateEnabled(ON_AD_IMPRESSION);
    }

    @Override
    public void checkIosBannerDelegates() throws InterruptedException {
        homePage.openInBrowser();
        adPage.waitAndReturnToApp();
        homePage.isDelegateEnabled(AD_VIEW_RECEIVED);
        homePage.isDelegateEnabled(AD_VIEW_PRESENT);
        homePage.isDelegateEnabled(AD_VIEW_DID_DISMISS);
        homePage.isDelegateEnabled(AD_VIEW_WILL_LEAVE);
    }

    @Override
    public void checkIosDisplayInterstitialDelegates() {
        homePage.isDelegateEnabled(INTERSTITIAL_DID_RECEIVED);
        homePage.isDelegateEnabled(INTERSTITIAL_WILL_PRESENT);
        homePage.isDelegateEnabled(INTERSTITIAL_DID_DISMISS);
        homePage.isDelegateEnabled(INTERSTITIAL_WILL_LEAVE_APP);
        homePage.isDelegateEnabled(INTERSTITIAL_DID_CLICK);
    }

    @Override
    public void checkIosVideoInterstitialDelegates() {
        homePage.isDelegateEnabled(INTERSTITIAL_DID_RECEIVED);
        homePage.isDelegateEnabled(INTERSTITIAL_WILL_PRESENT);
        homePage.isDelegateEnabled(INTERSTITIAL_DID_DISMISS);
        homePage.isDelegateEnabled(INTERSTITIAL_WILL_LEAVE_APP);
        homePage.isDelegateEnabled(INTERSTITIAL_DID_CLICK);
    }

    @Override
    public void checkIosVideoRewardedDelegates()  {
        homePage.isDelegateEnabled(REWARDED_AD_DID_RECEIVE);
        homePage.isDelegateEnabled(REWARDED_AD_WILL_PRESENT);
        homePage.isDelegateEnabled(REWARDED_AD_DID_DISMISS);
        homePage.isDelegateEnabled(REWARDED_AD_WILL_LEAVE_APP);
        homePage.isDelegateEnabled(REWARDED_AD_DID_CLICK);
        homePage.isDelegateEnabled(REWARDED_AD_USER_DID_EARN_REWARD);
    }

    @Override
    public void checkIosVideoOutstreamDelegates() {
        homePage.isDelegateEnabled(AD_VIEW_RECEIVED);
        homePage.isDelegateEnabled(AD_VIEW_PRESENT);
        homePage.isDelegateEnabled(AD_VIEW_WILL_LEAVE);
        homePage.isDelegateEnabled(AD_VIEW_DID_DISMISS);
    }

    @Override
    public void checkIosNativeAdsDelegates(String prebidAd) throws InterruptedException {
        if (prebidAd.contains("Links")) {
            adPage.clickBtnNativeLinkRoot();
        } else {
            adPage.clickHereToVisitOurSite();
        }
        adPage.waitAndReturnToApp();
        homePage.isDelegateEnabled(GET_NATIVE_AD);
        homePage.isDelegateEnabled(NATIVE_AD_DID_LOG_IMPRESSION);
        homePage.isDelegateEnabled(NATIVE_AD_DID_CLICK);
    }
}
