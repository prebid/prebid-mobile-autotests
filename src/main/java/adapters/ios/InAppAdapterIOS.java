package adapters.ios;

import adapters.PrebidAdapter;
import appium.common.InAppBiddingTestEnvironment;
import appium.pages.inAppBidding.InAppBiddingAdPageImpl;

import static appium.common.InAppBiddingTestEnvironment.InAppBiddingDelegates.*;

public class InAppAdapterIOS extends PrebidAdapter {
    public InAppAdapterIOS(InAppBiddingTestEnvironment testEnvironment, InAppBiddingAdPageImpl adPage) {
        super(testEnvironment,adPage);
    }

    public InAppAdapterIOS(InAppBiddingTestEnvironment testEnvironment) {
        super(testEnvironment);
    }

    @Override
    public void checkBannerDelegates() throws InterruptedException {
        testEnvironment.homePage.openInBrowser();
        adPage.waitAndReturnToApp();
        testEnvironment.homePage.isDelegateEnabled(AD_VIEW_RECEIVED);
        testEnvironment.homePage.isDelegateEnabled(AD_VIEW_PRESENT);
        testEnvironment.homePage.isDelegateEnabled(AD_VIEW_DID_DISMISS);
        testEnvironment.homePage.isDelegateEnabled(AD_VIEW_WILL_LEAVE);
    }

    @Override
    public void checkDisplayInterstitialDelegates() {
        testEnvironment.homePage.isDelegateEnabled(INTERSTITIAL_DID_RECEIVED);
        testEnvironment.homePage.isDelegateEnabled(INTERSTITIAL_WILL_PRESENT_AD);
        testEnvironment.homePage.isDelegateEnabled(INTERSTITIAL_DID_DISMISS_AD);
        testEnvironment.homePage.isDelegateEnabled(INTERSTITIAL_WILL_LEAVE_APP);
        testEnvironment.homePage.isDelegateEnabled(INTERSTITIAL_DID_CLICK);
    }

    @Override
    public void checkVideoInterstitialDelegates() {
        testEnvironment.homePage.isDelegateEnabled(INTERSTITIAL_DID_RECEIVED);
        testEnvironment.homePage.isDelegateEnabled(INTERSTITIAL_WILL_PRESENT_AD);
        testEnvironment.homePage.isDelegateEnabled(INTERSTITIAL_DID_DISMISS_AD);
        testEnvironment.homePage.isDelegateEnabled(INTERSTITIAL_WILL_LEAVE_APP);
        testEnvironment.homePage.isDelegateEnabled(INTERSTITIAL_DID_CLICK);
    }

    @Override
    public void checkVideoRewardedDelegates()  {
        testEnvironment.homePage.isDelegateEnabled(REWARDED_AD_DID_RECEIVE);
        testEnvironment.homePage.isDelegateEnabled(REWARDED_AD_WILL_PRESENT);
        testEnvironment.homePage.isDelegateEnabled(REWARDED_AD_DID_DISMISS);
        testEnvironment.homePage.isDelegateEnabled(REWARDED_AD_WILL_LEAVE_APP);
        testEnvironment.homePage.isDelegateEnabled(REWARDED_AD_DID_CLICK);
        testEnvironment.homePage.isDelegateEnabled(REWARDED_AD_USER_DID_EARN_REWARD);
    }

    @Override
    public void checkVideoOutstreamDelegates() {
        testEnvironment.homePage.isDelegateEnabled(AD_VIEW_RECEIVED);
        testEnvironment.homePage.isDelegateEnabled(AD_VIEW_PRESENT);
        testEnvironment.homePage.isDelegateEnabled(AD_VIEW_WILL_LEAVE);
        testEnvironment.homePage.isDelegateEnabled(AD_VIEW_DID_DISMISS);
    }

    @Override
    public void checkNativeAdsDelegates(String prebidAd) throws InterruptedException {
        if (prebidAd.contains("Links")) {
            adPage.clickBtnNativeLinkRoot();
        } else {
            adPage.clickHereToVisitOurSite();
        }
        adPage.waitAndReturnToApp();
        testEnvironment.homePage.isDelegateEnabled(GET_NATIVE_AD);
        testEnvironment.homePage.isDelegateEnabled(NATIVE_AD_DID_LOG_IMPRESSION);
        testEnvironment.homePage.isDelegateEnabled(NATIVE_AD_DID_CLICK);
    }

    @Override
    public void checkEvents() {

    }
}
