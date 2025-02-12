package appium.inAppBidding;

import OMSDK.OMSDKAssert;
import OMSDK.OMSDKSessionDescriptor;
import appium.common.InAppAdNames;
import appium.common.InAppBiddingTestEnvironment.InAppBiddingEvents;
import appium.pages.inAppBidding.InAppBiddingAdPageImpl;
import org.openqa.selenium.ScreenOrientation;
import org.testng.annotations.Test;
import utils.RequestTemplate;
import utils.RequestValidator;

import java.util.concurrent.TimeoutException;

import static appium.common.InAppBiddingTestEnvironment.InAppBiddingDelegates.INTERSTITIAL_DID_RECEIVE_BUTTON;
import static org.testng.Assert.assertEquals;

public class InAppBiddingInterstitialTests extends InAppBaseTest {


    // =============================
    // INTERSTITIAL TESTS
    // =============================

    @Test(groups = {"requests-simulator"}, dataProvider = "interstitialAds", dataProviderClass = InAppDataProviders.class)
    public void testAuctionRequestInterstitial(String prebidAd) throws TimeoutException, InterruptedException {
        testAuctionRequest(prebidAd);
    }

    @Test(groups = {"requests-simulator"}, dataProvider = "interstitialMultiFormat", dataProviderClass = InAppDataProviders.class)
    public void testAuctionRequestInterstitialMultiFormat(String prebidAd) throws TimeoutException, InterruptedException {
        testAuctionRequest(prebidAd);
    }

    @Test(groups = {"requests-realDevice"}, dataProvider = "interstitialAds", dataProviderClass = InAppDataProviders.class)
    public void testAuctionRequestInterstitialRealDevice(String prebidAd) throws TimeoutException, InterruptedException {
        testAuctionRequestRealDevice(prebidAd);
    }

    @Test(groups = {"requests-realDevice"}, dataProvider = "interstitialMultiFormat", dataProviderClass = InAppDataProviders.class)
    public void testAuctionRequestInterstitialMultiFormatRealDevice(String prebidAd) throws TimeoutException, InterruptedException {
        testAuctionRequestRealDevice(prebidAd);
    }

    @Test(groups = {"requests-simulator"}, dataProvider = "randomAdInterstitial", dataProviderClass = InAppDataProviders.class)
    public void testInterstitialRandom(String prebidAd) throws TimeoutException, InterruptedException {
        initValidTemplatesJson(prebidAd);
        InAppBiddingAdPageImpl interstitialPage = env.homePage.goToAd(prebidAd);

        env.waitForEvent(InAppBiddingEvents.AUCTION, 1, 10);
        env.validateEventRequest(InAppBiddingEvents.AUCTION, validAuctionRequest);

        interstitialPage.clickShowButton();

        interstitialPage.clickCloseRandom();

        env.homePage.clickBack();
    }

    @Test(groups = {"requests-simulator"}, dataProvider = "noBidsInterstitial", dataProviderClass = InAppDataProviders.class)
    public void testInterstitialNoBidsAd(String prebidAd) throws TimeoutException, InterruptedException {
        initValidTemplatesJson(prebidAd);
        env.homePage.goToAd(prebidAd);

        env.waitForEvent(InAppBiddingEvents.AUCTION, 1, 10);
        env.validateEventRequest(InAppBiddingEvents.AUCTION, validAuctionRequest);

        env.waitForEvent(InAppBiddingEvents.WIN_PREBID, 0, 10);
        initPrebidAdapter(prebidAd, env);
        prebidAdapter.checkAdRequests();

        env.homePage.clickBack();
    }

    // =============================
    // DELEGATES TEST
    // =============================

    @Test(groups = {"ios"}, dataProvider = "interstitialAds", dataProviderClass = InAppDataProviders.class)
    public void testInterstitialiOSDelegates(String prebidAd) throws InterruptedException {
        initValidTemplatesJson(prebidAd);

        InAppBiddingAdPageImpl interstitialPage = env.homePage.goToAd(prebidAd);

        interstitialPage.clickShowButton();

        interstitialPage.clickInterstitialAd();

        env.homePage.openInBrowser();

        interstitialPage.waitAndReturnToApp();

        initPrebidAdapter(prebidAd, env);
        prebidAdapter.checkDisplayInterstitialDelegates();

        env.homePage.clickBack();
    }

    @Test(groups = {"ios"}, dataProvider = "interstitialMultiFormat", dataProviderClass = InAppDataProviders.class)
    public void testInterstitialMultiFormatiOSDelegates(String prebidAd) throws InterruptedException {
        initValidTemplatesJson(prebidAd);

        InAppBiddingAdPageImpl multiformatPage = env.homePage.goToAd(prebidAd);

        if (prebidAd.contains("AdMob")) {
            env.homePage.isDelegateEnabled(INTERSTITIAL_DID_RECEIVE_BUTTON);
        }
        multiformatPage.isShowButtonEnabled();
        multiformatPage.clickShowButton();
        try {
            multiformatPage.clickLearnMore();
        } catch (Exception exception) {
            multiformatPage.clickInterstitialAd();
        }
        Thread.sleep(3000);
        env.homePage.openInBrowser();

        multiformatPage.waitAndReturnToApp();

        initPrebidAdapter(prebidAd, env);
        prebidAdapter.checkDisplayInterstitialDelegates();

        env.homePage.clickBack();
    }

    @Test(groups = {"android"}, dataProvider = "interstitialMultiFormat", dataProviderClass = InAppDataProviders.class)
    public void testInterstitialMultiFormatAndroidDelegates(String prebidAd) throws InterruptedException {
        initValidTemplatesJson(prebidAd);

        InAppBiddingAdPageImpl multiformatPage = env.homePage.goToAd(prebidAd);

        multiformatPage.isShowButtonEnabled();
        multiformatPage.clickShowButton();
        try {
            multiformatPage.clickLearnMore();
        } catch (Exception exception) {
            multiformatPage.clickInterstitialAd();
        }
        Thread.sleep(3000);
        env.homePage.openInBrowser();

        multiformatPage.waitAndReturnToApp();

        initPrebidAdapter(prebidAd, env);
        prebidAdapter.checkDisplayInterstitialDelegates();

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
        initPrebidAdapter(prebidAd, env);
        ;
        prebidAdapter.checkDisplayInterstitialDelegates();

        env.homePage.clickBack();
    }

    // =============================
    // OMSDK TESTS
    // =============================

    @Test(groups = {"requests"}, dataProvider = "interstitialAds", dataProviderClass = InAppDataProviders.class)
    public void testOMEvents(String prebidAd) throws TimeoutException, InterruptedException {
        initValidTemplatesJson(prebidAd);

        InAppBiddingAdPageImpl page = env.homePage.goToAd(prebidAd);

        env.waitForEvent(InAppBiddingEvents.AUCTION, 1, 5);
        page.clickShowButton();

        env.bmp.waitForEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_START, 1, 30);

        page.isEndCardDisplayed();

        page.clickCloseInterstitial();

        initPrebidAdapter(prebidAd, env);
        prebidAdapter.checkAdRequests();

        env.homePage.clickBack();

        env.bmp.waitForEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_FINISH, 1, 10);

        initEventHandler();
        OMSDKAssert.assertTrue(eventHandler.checkSessions());
        OMSDKSessionDescriptor session = eventHandler.getFirstSession();
        session.checkOMBaseEvents(platformName);
        session.checkNoObstructions();
    }

    @Test(groups = {"requests-simulator"}, dataProvider = "interstitialOriginalAds", dataProviderClass = InAppDataProviders.class)
    public void testOriginalInterstitialSession(String prebidAd) throws TimeoutException, InterruptedException {
        initValidTemplatesJson(prebidAd);
        env.homePage.goToAd(prebidAd);

        env.waitForEvent(InAppBiddingEvents.AUCTION, 1, 5);
        env.validateEventRequest(InAppBiddingEvents.AUCTION, validAuctionRequest);
        setValidAuctionResponseForMultiformat(prebidAd);
        env.validateEventResponse(InAppBiddingEvents.AUCTION, validAuctionResponse);

        initPrebidAdapter(InAppAdNames.INTERSTITIAL_320x480_GAM_ORIGINAL, env);
        // Unstable check
        // prebidAdapter.checkAdRequests();

        env.homePage.clickBack();

    }

    @Test(groups = {"requests"}, dataProvider = "interstitialMultiFormat", dataProviderClass = InAppDataProviders.class)
    public void testInterstitialMultiFormatOMEvents(String prebidAd) throws TimeoutException, InterruptedException {
        initValidTemplatesJson(prebidAd);

        InAppBiddingAdPageImpl page = env.homePage.goToAd(prebidAd);

        env.waitForEvent(InAppBiddingEvents.AUCTION, 1, 5);
        page.clickShowButton();

        env.bmp.waitForEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_START, 1, 10);

//        page.isEndCardDisplayed();

        page.clickCloseInterstitial();

        initPrebidAdapter(prebidAd, env);
        prebidAdapter.checkAdRequests();

        env.homePage.clickBack();

        env.bmp.waitForEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_FINISH, 1, 10);

        initEventHandler();
        OMSDKAssert.assertTrue(eventHandler.checkSessions());
        OMSDKSessionDescriptor session = eventHandler.getFirstSession();
        session.checkOMBaseEvents(platformName);
        session.checkNoObstructions();

    }

    @Test(groups = {"requests"})
    public void testBackgroundedSession() throws InterruptedException, TimeoutException {
        initValidTemplatesJson(InAppAdNames.INTERSTITIAL_320x480_IN_APP);

        InAppBiddingAdPageImpl page = env.homePage.goToAd(InAppAdNames.INTERSTITIAL_320x480_IN_APP);

        page.clickShowButton();

        env.bmp.waitForEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_START, 1, 5);

        page.isEndCardDisplayed();

        env.homePage.runAppInBackground(3);

        page.isEndCardDisplayed();

        page.clickCloseInterstitial();

        Thread.sleep(3000);

        env.homePage.clickBack();
        env.bmp.waitForEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_FINISH, 1, 10);

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

    @Test(groups = {"requests"}, dataProvider = "interstitialMultiFormat", dataProviderClass = InAppDataProviders.class)
    public void testInterstitialMultiFormatBackgroundedSession(String prebidAd) throws InterruptedException, TimeoutException {
        initValidTemplatesJson(prebidAd);

        InAppBiddingAdPageImpl page = env.homePage.goToAd(prebidAd);

        page.clickShowButton();

        env.bmp.waitForEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_START, 1, 5);
        try {
            env.waitForEvent(InAppBiddingEvents.VIDEO_FIRSTQUARTILE, 1, 30);
            env.homePage.runAppInBackground(5);
        } catch (Exception exception) {
            page.isEndCardDisplayed();
            env.homePage.runAppInBackground(3);
            page.isEndCardDisplayed();
        }

        page.clickCloseInterstitial();

        env.homePage.clickBack();
        env.bmp.waitForEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_FINISH, 1, 10);

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
        try {
            session.checkMediaTypeIsVideo();
            session.checkVideoStartEvent(platformName);
            session.checkNonAutoPlaySkippableAndStandalonePosition();
            session.checkPlayerStateIsNormal();
        } catch (AssertionError assertionError) {
            session.checkNoObstructions();
        }
    }

    @Test(groups = {"smoke"})
    public void testRotation() throws InterruptedException {
        initValidTemplatesJson(InAppAdNames.INTERSTITIAL_320x480_IN_APP);

        InAppBiddingAdPageImpl page = env.homePage.goToAd(InAppAdNames.INTERSTITIAL_320x480_IN_APP);

        page.clickShowButton();

        env.homePage.rotateLandscape();
        assertEquals(ScreenOrientation.LANDSCAPE, env.homePage.getOrientation());

        env.homePage.rotatePortrait();
        assertEquals(ScreenOrientation.PORTRAIT, env.homePage.getOrientation());

        page.clickCloseInterstitial();

        env.homePage.clickBack();
    }

    private void testAuctionRequest(String prebidAd) throws InterruptedException, TimeoutException {
        initValidTemplatesJson(prebidAd);

        env.homePage.goToAd(prebidAd);

        env.waitForEvent(InAppBiddingEvents.AUCTION, 1, 15);
        env.validateEventRequest(InAppBiddingEvents.AUCTION, validAuctionRequest);

        env.homePage.clickBack();
        RequestValidator.checkVersionParametersFromRequest(env.bmp.getHar(), ver, version, omidpv, displaymanagerver);

    }

    private void testAuctionRequestRealDevice(String prebidAd) throws InterruptedException, TimeoutException {
        initValidTemplatesJson(prebidAd, RequestTemplate.REQUEST_REAL_DEVICE);

        env.homePage.goToAd(prebidAd);

        env.waitForEvent(InAppBiddingEvents.AUCTION, 1, 15);
        env.validateEventRequest(InAppBiddingEvents.AUCTION, validAuctionRequest);

        env.homePage.clickBack();
        RequestValidator.checkVersionParametersFromRequest(env.bmp.getHar(), ver, version, omidpv, displaymanagerver);
    }
}