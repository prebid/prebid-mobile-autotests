package delegates.android;

import appium.pages.inAppBidding.InAppBiddingAdPageImpl;
import appium.pages.inAppBidding.InAppBiddingHomePageImpl;
import delegates.DelegatesCheck;

import static appium.common.InAppBiddingTestEnvironment.InAppBiddingDelegates.*;

public class MoPubDelegatesCheckAndroid implements DelegatesCheck {
    private final InAppBiddingHomePageImpl homePage;
    private InAppBiddingAdPageImpl adPage;

    public MoPubDelegatesCheckAndroid(InAppBiddingHomePageImpl homePage, InAppBiddingAdPageImpl adPage) {
        this.homePage = homePage;
        this.adPage = adPage;
    }

    public MoPubDelegatesCheckAndroid(InAppBiddingHomePageImpl homePage) {
        this.homePage = homePage;
    }

    @Override
    public void checkBannerDelegates() {
        homePage.isDelegateEnabled(ON_BANNER_LOADED);
        adPage.clickBanner();
        homePage.clickCloseButtonClickThroughBrowser();
        homePage.isDelegateEnabled(ON_AD_CLICKED);
    }

    @Override
    public void checkDisplayInterstitialDelegates() {
        homePage.isDelegateEnabled(ON_BANNER_LOADED);
        homePage.isDelegateEnabled(ON_AD_DISPLAYED);
        homePage.isDelegateEnabled(ON_INTERSTITIAL_DISMISSED);
    }

    @Override
    public void checkVideoInterstitialDelegates()  {
        homePage.isDelegateEnabled(ON_BANNER_LOADED);
        homePage.isDelegateEnabled(ON_AD_DISPLAYED);
        homePage.isDelegateEnabled(ON_AD_CLICKED);
        homePage.isDelegateEnabled(ON_INTERSTITIAL_DISMISSED);

    }

    @Override
    public void checkVideoRewardedDelegates() {
        homePage.isDelegateEnabled(ON_REWARDED_STARTED);
        homePage.isDelegateEnabled(ON_REWARDED_COMPLETED);
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
        adPage.clickHereToVisitOurSite();
        Thread.sleep(1000);
        homePage.clickBack();
        homePage.isDelegateEnabled(NATIVE_AD_DID_LOAD);
        homePage.isDelegateEnabled(ON_AD_CLICKED);
        homePage.isDelegateEnabled(NATIVE_AD_DID_LOG_IMPRESSION);
    }


}
