package appium.inAppBidding;

import OMSDK.OMSDKAssert;
import OMSDK.OMSDKEventHandler;
import OMSDK.OMSDKSessionDescriptor;
import appium.common.InAppBiddingTestEnvironment.InAppBiddingEvents;
import appium.pages.inAppBidding.InAppBiddingAdPageImpl;
import org.openqa.selenium.ScreenOrientation;
import org.testng.annotations.Test;
import utils.RequestValidator;

import java.util.concurrent.TimeoutException;

import static OMSDK.OMSDKAssert.assertTrue;
import static appium.common.InAppBiddingTestEnvironment.InAppBiddingDelegates.*;
import static appium.common.InAppTemplatesInit.*;
import static org.testng.Assert.assertEquals;

public class InAppBiddingInterstitialTests extends InAppBaseTest {


    // =============================
    // INTERSTITIAL TESTS
    // =============================

    @Test(groups = {"requests"}, dataProvider = "interstitialAds", dataProviderClass = InAppDataProviders.class)
    public void testAuctionRequest(String prebidAd) throws TimeoutException, InterruptedException {
        initValidTemplatesJson(prebidAd);

        env.homePage.goToAd(prebidAd);

        env.waitForEvent(InAppBiddingEvents.AUCTION, 1, 15);
        env.validateEventRequest(InAppBiddingEvents.AUCTION, validAuctionRequest);

        env.homePage.clickBack();
    }

    @Test(groups = {"serverBased"}, dataProvider = "interstitialAds", dataProviderClass = InAppDataProviders.class)
    public void testVersionParametersInRequest(String prebidAd) throws InterruptedException, TimeoutException {
        env.homePage.goToAd(prebidAd);

        env.waitForEvent(InAppBiddingEvents.AUCTION, 1, 60);

        env.homePage.clickBack();

        RequestValidator.checkVersionParametersFromRequest(env.bmp.getHar(), ver, version, omidpv, displaymanagerver);
    }

    @Test(groups = {"serverBased"}, dataProvider = "interstitialAds", dataProviderClass = InAppDataProviders.class)
    public void testAuctionRequestServerBased(String prebidAd) throws TimeoutException, InterruptedException {
        initValidTemplatesJson(prebidAd);

        InAppBiddingAdPageImpl page = env.homePage.goToAd(prebidAd);

        page.isShowButtonEnabled();
        env.waitForEvent(InAppBiddingEvents.AUCTION, 1, 60);
        env.validateEventRequest(InAppBiddingEvents.AUCTION, validAuctionRequest);
        page.clickShowButton();

        env.bmp.waitForEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_START, 1, 30);

        checkGamOrMoPubEvents(prebidAd);

        page.clickCloseInterstitial();

        env.homePage.clickBack();

        env.bmp.waitForEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_FINISH, 1, 30);
        // CHECK OM EVENTS
        OMSDKEventHandler eventHandler = new OMSDKEventHandler(env.bmp.getHar());
        assertTrue(eventHandler.checkSessionsCount(1));
        OMSDKSessionDescriptor session = eventHandler.getFirstSession();
        session.checkOMBaseEvents(platformName);
        session.checkNoObstructions();

    }

    @Test(groups = {"requests"}, dataProvider = "randomAdInterstitial", dataProviderClass = InAppDataProviders.class)
    public void testInterstitialRandom(String prebidAd) throws TimeoutException, InterruptedException {
        initValidTemplatesJson(prebidAd);
        InAppBiddingAdPageImpl interstitialPage = env.homePage.goToAd(prebidAd);

        env.waitForEvent(InAppBiddingEvents.AUCTION, 1, 10);
        env.validateEventRequest(InAppBiddingEvents.AUCTION, validAuctionRequest);

        interstitialPage.clickShowButton();

        interstitialPage.clickCloseRandom();

        env.homePage.clickBack();
    }

    @Test(groups = {"serverBased"}, dataProvider = "noBidsInterstitial", dataProviderClass = InAppDataProviders.class)
    public void testInterstitialNoBidsAd(String prebidAd) throws TimeoutException, InterruptedException {
        initValidTemplatesJson(prebidAd);
        env.homePage.goToAd(prebidAd);

        env.waitForEvent(InAppBiddingEvents.AUCTION, 1, 10);
        env.validateEventRequest(InAppBiddingEvents.AUCTION, validAuctionRequest);

        env.waitForEvent(InAppBiddingEvents.WIN_PREBID, 0, 10);
        if (prebidAd.equalsIgnoreCase(INTERSTITIAL_320x480_NO_BID_GAM)) {
            if (isPlatformIOS) {
                env.waitForEvent(InAppBiddingEvents.GAM_GAMPAD, 1, 10);
            } else {
                //TODO find correct event for Android
                //env.waitForEvent(InAppBiddingEvents.GAM_G_DOUBLECLICK, 1, 10);
            }
        } else if (prebidAd.equalsIgnoreCase(INTERSTITIAL_320x480_NO_BID_MOPUB)) {
            env.waitForEvent(InAppBiddingEvents.MOPUB_AD, 1, 10);
        } else if (prebidAd.equalsIgnoreCase(INTERSTITIAL_320x480_NO_BID_ADMOB)){
            if (isPlatformIOS) {
                env.waitForEvent(InAppBiddingEvents.ADMOB_MADS_GMA, 1, 10);
            }
            env.waitForEvent(InAppBiddingEvents.ADMOB_PAGEAD_INTERACTION, 1, 10);
        }

        env.homePage.clickBack();
    }

    // =============================
    // DELEGATES TEST
    // =============================

    @Test(groups = {"ios"}, dataProvider = "interstitialAds", dataProviderClass = InAppDataProviders.class)
    public void testInterstitialiOSDelegates(String prebidAd) throws InterruptedException {
        initValidTemplatesJson(prebidAd);

        InAppBiddingAdPageImpl interstitialPage = env.homePage.goToAd(prebidAd);

        if (prebidAd.contains("AdMob")){
            env.homePage.isDelegateEnabled(INTERSTITIAL_DID_RECEIVE_BUTTON);
        }
        interstitialPage.clickShowButton();

        interstitialPage.clickInterstitialAd();

        env.homePage.openInBrowser();

        interstitialPage.waitAndReturnToApp();

        if (prebidAd.contains("MoPub")) {
            interstitialPage.clickCloseInterstitial();
            env.homePage.isDelegateEnabled(INTERSTITIAL_DID_LOAD);
            env.homePage.isDelegateEnabled(INTERSTITIAL_WILL_APPEAR);
            env.homePage.isDelegateEnabled(INTERSTITIAL_DID_APPEAR);
            env.homePage.isDelegateEnabled(INTERSTITIAL_DID_DISAPPEAR);
            env.homePage.isDelegateEnabled(INTERSTITIAL_WILL_DISAPPEAR);
            env.homePage.isDelegateEnabled(INTERSTITIAL_DID_RECEIVED_TAP);
        } else if(prebidAd.contains("AdMob")){
            env.homePage.isDelegateEnabled(INTERSTITIAL_DID_PRESENT_FULLSCREEN);
            env.homePage.isDelegateEnabled(INTERSTITIAL_WILL_DISMISS_FULLSCREEN);
            env.homePage.isDelegateEnabled(INTERSTITIAL_DID_DISMISS_FULLSCREEN);
            env.homePage.isDelegateEnabled(INTERSTITIAL_DID_RECORD_IMPRESSION);
        } else {
            env.homePage.isDelegateEnabled(INTERSTITIAL_DID_RECEIVED);
            env.homePage.isDelegateEnabled(INTERSTITIAL_WILL_PRESENT);
            env.homePage.isDelegateEnabled(INTERSTITIAL_DID_DISMISS);
            env.homePage.isDelegateEnabled(INTERSTITIAL_WILL_LEAVE_APP);
            env.homePage.isDelegateEnabled(INTERSTITIAL_DID_CLICK);
        }

        env.homePage.clickBack();
    }

    @Test(groups = {"android"}, dataProvider = "interstitialAds", dataProviderClass = InAppDataProviders.class)
    public void testInterstitialAndroidDelegates(String prebidAd) throws InterruptedException {
        initValidTemplatesJson(prebidAd);

        InAppBiddingAdPageImpl interstitialPage = env.homePage.goToAd(prebidAd);

        interstitialPage.clickShowButton();
        Thread.sleep(3000);
        interstitialPage.clickInterstitialAd();

        interstitialPage.closeWebViewCreative();

        interstitialPage.clickCloseInterstitial();
        if (prebidAd.contains("MoPub")) {
            env.homePage.isDelegateEnabled(ON_BANNER_LOADED);
            env.homePage.isDelegateEnabled(ON_AD_DISPLAYED);
            env.homePage.isDelegateEnabled(ON_INTERSTITIAL_DISMISSED);
        } else if (prebidAd.contains("AdMob")){
            env.homePage.isDelegateEnabled(ON_AD_LOADED);
            env.homePage.isDelegateEnabled(ON_AD_CLICKED);
            env.homePage.isDelegateEnabled(ON_AD_IMPRESSION);
            env.homePage.isDelegateEnabled(ON_AD_SHOWED);
            env.homePage.isDelegateEnabled(ON_INTERSTITIAL_DISMISSED);
        } else {
            env.homePage.isDelegateEnabled(ON_AD_LOADED);
            env.homePage.isDelegateEnabled(ON_AD_DISPLAYED);
            env.homePage.isDelegateEnabled(ON_AD_CLOSED);
        }

        env.homePage.clickBack();
    }

    // =============================
    // OMSDK TESTS
    // =============================

    @Test(groups = {"requests"})
    public void testOMEvents() throws TimeoutException, InterruptedException {
        initValidTemplatesJson(INTERSTITIAL_320x480_ADMOB);

        InAppBiddingAdPageImpl page = env.homePage.goToAd(INTERSTITIAL_320x480_ADMOB);

        env.waitForEvent(InAppBiddingEvents.AUCTION, 1, 5);

        page.clickShowButton();

        env.waitForOMEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_START, 1, 10);

        page.isEndCardDisplayed();

        page.clickCloseInterstitial();

        env.homePage.clickBack();

        env.waitForOMEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_FINISH, 1, 10);

        initEventHandler();
        OMSDKAssert.assertTrue(eventHandler.checkSessions());
        OMSDKSessionDescriptor session = eventHandler.getFirstSession();
        session.checkOMBaseEvents(platformName);
        session.checkNoObstructions();

    }

    @Test(groups = {"requests"})
    public void testBackgroundedSession() throws InterruptedException, TimeoutException {
        initValidTemplatesJson(INTERSTITIAL_320x480_ADMOB);

        InAppBiddingAdPageImpl page = env.homePage.goToAd(INTERSTITIAL_320x480_ADMOB);

        page.clickShowButton();

        env.waitForOMEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_START, 1, 5);

        page.isEndCardDisplayed();

        env.homePage.runAppInBackground(3);

        page.isEndCardDisplayed();

        page.clickCloseInterstitial();

        Thread.sleep(3000);

        env.homePage.clickBack();
        env.waitForOMEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_FINISH, 1, 10);

        // CHECK OM EVENTS
        initEventHandler();
        OMSDKAssert.assertTrue(eventHandler.checkSessions());
        OMSDKSessionDescriptor session = eventHandler.getFirstSession();
        String[] reasons;
        if (isPlatformIOS) {
            // On iOS I see different reasons in different cases. It could be 'backgrounded' or 'clipped'
            reasons = new String[]{OMSDKSessionDescriptor.EVENT_VALUE.BACKGROUNDED, OMSDKSessionDescriptor.EVENT_VALUE.CLIPPED};
        } else {
            reasons = new String[]{OMSDKSessionDescriptor.EVENT_VALUE.BACKGROUNDED, OMSDKSessionDescriptor.EVENT_VALUE.NOT_FOUND};
        }
        session.checkOMBaseEvents(platformName);
        session.checkHideAndRestoreViewabilityWithReasons(reasons);
        session.checkNoObstructions();
    }

    @Test(groups = {"smoke"})
    public void testRotation() throws InterruptedException {
        initValidTemplatesJson(INTERSTITIAL_320x480_IN_APP);

        InAppBiddingAdPageImpl page = env.homePage.goToAd(INTERSTITIAL_320x480_IN_APP);

        page.clickShowButton();

        env.homePage.rotateLandscape();
        assertEquals(ScreenOrientation.LANDSCAPE, env.homePage.getOrientation());

        env.homePage.rotatePortrait();
        assertEquals(ScreenOrientation.PORTRAIT, env.homePage.getOrientation());

        page.clickCloseInterstitial();

        env.homePage.clickBack();
    }

}