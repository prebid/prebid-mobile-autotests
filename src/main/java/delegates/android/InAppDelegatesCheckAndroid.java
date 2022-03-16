package delegates.android;

import appium.pages.inAppBidding.InAppBiddingAdPageImpl;
import appium.pages.inAppBidding.InAppBiddingHomePageImpl;
import delegates.DelegatesCheck;

import static appium.common.InAppBiddingTestEnvironment.InAppBiddingDelegates.*;

public class InAppDelegatesCheckAndroid implements DelegatesCheck {
    private final InAppBiddingHomePageImpl homePage;
    private InAppBiddingAdPageImpl adPage;


    public InAppDelegatesCheckAndroid(InAppBiddingHomePageImpl homePage, InAppBiddingAdPageImpl adPage) {
        this.homePage = homePage;
        this.adPage = adPage;
    }

    public InAppDelegatesCheckAndroid(InAppBiddingHomePageImpl homePage) {
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
    public void checkVideoInterstitialDelegates() {
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

}
