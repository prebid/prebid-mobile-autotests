package delegates.ios;

import appium.pages.inAppBidding.InAppBiddingAdPageImpl;
import appium.pages.inAppBidding.InAppBiddingHomePageImpl;
import delegates.DelegatesCheck;

import static appium.common.InAppBiddingTestEnvironment.InAppBiddingDelegates.*;

public class InAppDelegatesCheckIOS implements DelegatesCheck {
    private final InAppBiddingHomePageImpl homePage;
    private InAppBiddingAdPageImpl adPage;


    public InAppDelegatesCheckIOS(InAppBiddingHomePageImpl homePage, InAppBiddingAdPageImpl adPage) {
        this.homePage = homePage;
        this.adPage = adPage;
    }

    public InAppDelegatesCheckIOS(InAppBiddingHomePageImpl homePage) {
        this.homePage = homePage;
    }

    @Override
    public void checkBannerDelegates() throws InterruptedException {
        homePage.openInBrowser();
        adPage.waitAndReturnToApp();
        homePage.isDelegateEnabled(AD_VIEW_RECEIVED);
        homePage.isDelegateEnabled(AD_VIEW_PRESENT);
        homePage.isDelegateEnabled(AD_VIEW_DID_DISMISS);
        homePage.isDelegateEnabled(AD_VIEW_WILL_LEAVE);
    }

    @Override
    public void checkDisplayInterstitialDelegates() {
        homePage.isDelegateEnabled(INTERSTITIAL_DID_RECEIVED);
        homePage.isDelegateEnabled(INTERSTITIAL_WILL_PRESENT);
        homePage.isDelegateEnabled(INTERSTITIAL_DID_DISMISS);
        homePage.isDelegateEnabled(INTERSTITIAL_WILL_LEAVE_APP);
        homePage.isDelegateEnabled(INTERSTITIAL_DID_CLICK);
    }

    @Override
    public void checkVideoInterstitialDelegates() {
        homePage.isDelegateEnabled(INTERSTITIAL_DID_RECEIVED);
        homePage.isDelegateEnabled(INTERSTITIAL_WILL_PRESENT);
        homePage.isDelegateEnabled(INTERSTITIAL_DID_DISMISS);
        homePage.isDelegateEnabled(INTERSTITIAL_WILL_LEAVE_APP);
        homePage.isDelegateEnabled(INTERSTITIAL_DID_CLICK);
    }

    @Override
    public void checkVideoRewardedDelegates()  {
        homePage.isDelegateEnabled(REWARDED_AD_DID_RECEIVE);
        homePage.isDelegateEnabled(REWARDED_AD_WILL_PRESENT);
        homePage.isDelegateEnabled(REWARDED_AD_DID_DISMISS);
        homePage.isDelegateEnabled(REWARDED_AD_WILL_LEAVE_APP);
        homePage.isDelegateEnabled(REWARDED_AD_DID_CLICK);
        homePage.isDelegateEnabled(REWARDED_AD_USER_DID_EARN_REWARD);
    }

    @Override
    public void checkVideoOutstreamDelegates() {
        homePage.isDelegateEnabled(AD_VIEW_RECEIVED);
        homePage.isDelegateEnabled(AD_VIEW_PRESENT);
        homePage.isDelegateEnabled(AD_VIEW_WILL_LEAVE);
        homePage.isDelegateEnabled(AD_VIEW_DID_DISMISS);
    }

    @Override
    public void checkNativeAdsDelegates(String prebidAd) throws InterruptedException {
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
