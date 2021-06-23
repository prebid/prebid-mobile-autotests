package appium.inAppBidding;

import OMSDK.OMSDKEventHandler;
import OMSDK.OMSDKSessionDescriptor;
import appium.common.InAppBiddingTestEnvironment.InAppBiddingEvents;
import appium.pages.inAppBidding.InAppBiddingAdPageImpl;
import org.openqa.selenium.Dimension;
import org.testng.annotations.Test;
import utils.RequestValidator;

import java.util.concurrent.TimeoutException;

import static OMSDK.OMSDKAssert.assertTrue;
import static appium.common.InAppBiddingTestEnvironment.InAppBiddingDelegates.*;
import static appium.common.InAppTemplatesInit.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

public class InAppBiddingMraidTests extends InAppBaseTest {


    // =============================
    //GENERAL TESTS
    // =============================

    @Test(groups = {"smoke"}, dataProvider = "adNameMraid", dataProviderClass = InAppDataProviders.class)
    public void testAuctionRequest(String adName) throws TimeoutException, InterruptedException {
        initValidTemplatesJson(adName);

        env.homePage.goToAd(adName);

        env.waitForEvent(InAppBiddingEvents.AUCTION, 1, 60);
        env.validateEventRequest(InAppBiddingEvents.AUCTION, validAuctionRequest);

        env.homePage.clickBack();
    }

    @Test(groups = {"serverBased"}, dataProvider = "mraidResize", dataProviderClass = InAppDataProviders.class)
    public void testVersionParametersAuctionRequest(String adName) throws TimeoutException, InterruptedException {
        initValidTemplatesJson(adName);

        env.homePage.goToAd(adName);

        env.waitForEvent(InAppBiddingEvents.AUCTION, 1, 60);

        env.homePage.clickBack();

        RequestValidator.checkVersionParametersFromRequest(env.bmp.getHar(), ver, version, omidpv, displaymanagerver);
    }

    // =============================
    // EXPAND 1 TESTS
    // =============================

   @Test(groups = {"android"}, dataProvider = "mraidExpand1", dataProviderClass = InAppDataProviders.class)
    public void testDelegatesExpand1Android(String adName) throws InterruptedException {
        InAppBiddingAdPageImpl page = env.homePage.goToAd(adName);

        page.clickBanner();

        page.adShouldExpand1Part();

        page.clickCloseInterstitial();

        if (adName.equalsIgnoreCase(MRAID_EXPAND_1_MOPUB)) {
            env.homePage.isDelegateEnabled(ON_BANNER_LOADED);
            env.homePage.isDelegateEnabled(ON_AD_COLLAPSED);
        } else {
            env.homePage.isDelegateEnabled(ON_AD_LOADED);
            env.homePage.isDelegateEnabled(ON_AD_DISPLAYED);
            env.homePage.isDelegateEnabled(ON_AD_CLOSED);
        }

        env.homePage.isDelegateEnabled(ON_AD_CLICKED);

        env.homePage.clickBack();
    }

   @Test(groups = {"ios"}, dataProvider = "mraidExpand1", dataProviderClass = InAppDataProviders.class)
    public void testDelegatesExpand1iOS(String adName) throws InterruptedException {
        InAppBiddingAdPageImpl page = env.homePage.goToAd(adName);

        page.clickBanner();

        page.adShouldExpand1Part();

        page.clickCloseInterstitial();

        if (adName.equalsIgnoreCase(MRAID_EXPAND_1_MOPUB)) {
            env.homePage.isDelegateEnabled(AD_VIEW_DID_LOAD);
            env.homePage.isDelegateEnabled(AD_CONTROLLER_FOR_PRESENTING_MODAL_VIEW);
        } else {
            env.homePage.isDelegateEnabled(AD_VIEW_RECEIVED);
        }
        env.homePage.clickBack();
    }

   @Test(groups = {"smoke"}, dataProvider = "mraidExpand1", dataProviderClass = InAppDataProviders.class)
    public void testExpand1Locks(String adName) throws InterruptedException {
        InAppBiddingAdPageImpl page = env.homePage.goToAd(adName);

        page.isAdDisplayed();

        page.clickBanner();

        page.adShouldExpand1Part();

        page.lockToLandscape();

        page.lockToPortrait();
        if (platformName.equalsIgnoreCase("Android")) {
            page.releaseLock();
        }
        page.clickCloseInterstitial();

        page.isAdDisplayed();

        env.homePage.clickBack();
    }

   @Test(groups = {"requests"}, dataProvider = "mraidExpand1", dataProviderClass = InAppDataProviders.class)
    public void testOMSessionWithOpenClickthroughExpand1(String adName) throws InterruptedException, TimeoutException {
        InAppBiddingAdPageImpl page = env.homePage.goToAd(adName);

        page.isAdDisplayed();

        env.waitForOMEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_START, 1, 30);

        page.clickBanner();

        page.adShouldExpand1Part();

        page.clickCloseInterstitial();

        page.isAdDisplayed();

        env.homePage.clickBack();

        env.waitForOMEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_FINISH, 1, 30);
        // CHECK OM EVENTS
        initEventHandler();
        assertTrue(eventHandler.checkSessionsCount(1));
        OMSDKSessionDescriptor session = eventHandler.getFirstSession();
        session.checkOMBaseEvents(platformName);
        session.checkNoObstructions();
    }

    // =============================
    // EXPAND 2 TESTS
    // =============================

   @Test(groups = {"smoke"})
    public void testDelegatesExpand2() throws InterruptedException {
        InAppBiddingAdPageImpl page = env.homePage.goToAd(MRAID_EXPAND_2_IN_APP);

        page.isAdDisplayed();

        page.clickExpand2creative();

        page.adShouldExpand2Part();

        page.clickHereToClose();

        if (isPlatformIOS) {
            env.homePage.isDelegateEnabled(AD_VIEW_RECEIVED);
        } else {
            env.homePage.isDelegateEnabled(ON_AD_LOADED);
            env.homePage.isDelegateEnabled(ON_AD_DISPLAYED);
            env.homePage.isDelegateEnabled(ON_AD_CLICKED);
            env.homePage.isDelegateEnabled(ON_AD_CLOSED);
        }
        env.homePage.clickBack();
    }

   @Test(groups = {"requests"})
    public void testExpand2OMEventsWithOpenClickThrough() throws InterruptedException, TimeoutException {
        InAppBiddingAdPageImpl page = env.homePage.goToAd(MRAID_EXPAND_2_IN_APP);

        page.isAdDisplayed();

        env.waitForOMEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_START, 1, 30);

        page.clickExpand2creative();

        page.adShouldExpand2Part();

        page.clickHereToClose();

        page.isAdDisplayed();

        env.homePage.clickBack();

        env.waitForOMEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_FINISH, 1, 30);
        // CHECK OM EVENTS
        initEventHandler();
        assertTrue(eventHandler.checkSessionsCount(1));
        OMSDKSessionDescriptor session = eventHandler.getFirstSession();
        if (isPlatformIOS) {
            String[] reasons = {OMSDKSessionDescriptor.EVENT_VALUE.OBSTRUCTED, OMSDKSessionDescriptor.EVENT_VALUE.CLIPPED};
            session.checkOMBaseEvents(platformName);
            session.checkHideAndRestoreViewabilityWithReasons(reasons);
        } else {
            session.checkOMBaseEvents(platformName);
        }
    }

   @Test(groups = {"smoke"})
    public void testExpand2ExpandAgain() throws InterruptedException {
        InAppBiddingAdPageImpl page = env.homePage.goToAd(MRAID_EXPAND_2_IN_APP);

        page.isAdDisplayed();

        page.clickExpand2creative();

        page.adShouldExpand2Part();

        page.clickExpandAgain();

        page.adShouldExpand2Part();

        page.clickHereToClose();

        page.isAdDisplayed();

        env.homePage.clickBack();
    }

   @Test(groups = {"smoke"})
    public void testExpand2OpenIAB() throws InterruptedException {
        InAppBiddingAdPageImpl page = env.homePage.goToAd(MRAID_EXPAND_2_IN_APP);

        page.isAdDisplayed();

        page.clickExpand2creative();

        page.adShouldExpand2Part();

        page.clickOpenIAB();

        page.closeWebViewCreative();

        page.clickHereToClose();

        page.isAdDisplayed();

        env.homePage.clickBack();
    }

   @Test(groups = {"smoke"})
    public void testExpand2PlayVideo() throws InterruptedException {
        InAppBiddingAdPageImpl page = env.homePage.goToAd(MRAID_EXPAND_2_IN_APP);

        page.isAdDisplayed();

        page.clickExpand2creative();

        page.adShouldExpand2Part();

        page.clickPlayVideo();

        page.learnMoreShouldNotBeDisplayed();

        if (isPlatformIOS) {
            page.clickCloseInterstitial();
        } else {
            env.homePage.clickBack();
        }

        page.clickHereToClose();

        env.homePage.clickBack();
    }

    // =============================
    // RESIZE TESTS
    // =============================

   @Test(groups = {"requests-Android"}, dataProvider = "mraidResize", dataProviderClass = InAppDataProviders.class)
    public void testMraidResizeDelegatesAndOMEventsAndroid(String adName) throws InterruptedException, TimeoutException {
        InAppBiddingAdPageImpl page = env.homePage.goToAd(adName);

        page.isAdDisplayed();

        env.waitForOMEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_START, 1, 30);

        page.clickResizeCreative();

        page.clickOpenURL();

        page.closeWebViewCreative();

        page.clickCloseMRAIDResizeButton();

        if (adName.equalsIgnoreCase(MRAID_RESIZE_MOPUB)) {
            env.homePage.isDelegateEnabled(ON_BANNER_LOADED);
            env.homePage.isDelegateEnabled(ON_AD_COLLAPSED);
        } else {
            env.homePage.isDelegateEnabled(ON_AD_LOADED);
            env.homePage.isDelegateEnabled(ON_AD_DISPLAYED);
            env.homePage.isDelegateEnabled(ON_AD_CLOSED);
        }

        env.homePage.isDelegateEnabled(ON_AD_CLICKED);

        env.homePage.clickBack();

        env.waitForOMEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_FINISH, 1, 30);
        // CHECK OM EVENTS
        initEventHandler();
        assertTrue(eventHandler.checkSessionsCount(1));
        OMSDKSessionDescriptor session = eventHandler.getFirstSession();
        String[] reasons = {OMSDKSessionDescriptor.EVENT_VALUE.NOT_FOUND};
        session.checkOMBaseEvents(platformName);
        session.checkHideAndRestoreViewabilityWithReasons(reasons);
        session.checkNoObstructions();
    }


   @Test(groups = {"requests-iOS"}, dataProvider = "mraidResize", dataProviderClass = InAppDataProviders.class)
    public void testMraidResizeDelegatesAndOMEventsIos(String adName) throws InterruptedException, TimeoutException {
        InAppBiddingAdPageImpl page = env.homePage.goToAd(adName);

        page.isAdDisplayed();

        env.waitForOMEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_START, 1, 30);

        page.clickResizeCreative();

        page.clickOpenURL();

        page.closeWebViewCreative();

        page.clickCloseMRAIDResizeButton();

        if (adName.equalsIgnoreCase(MRAID_RESIZE_MOPUB)) {
            env.homePage.isDelegateEnabled(AD_VIEW_DID_LOAD);
            env.homePage.isDelegateEnabled(AD_PRESENT_MODAL_VIEW);
            env.homePage.isDelegateEnabled(AD_DISMISS_MODAL_VIEW);
            env.homePage.isDelegateEnabled(AD_CONTROLLER_FOR_PRESENTING_MODAL_VIEW);
        } else {
            env.homePage.isDelegateEnabled(AD_VIEW_RECEIVED);
            env.homePage.isDelegateEnabled(AD_VIEW_PRESENT);
            env.homePage.isDelegateEnabled(AD_VIEW_DID_DISMISS);
        }

        env.homePage.clickBack();

        env.waitForOMEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_FINISH, 1, 30);
        // CHECK OM EVENTS
        initEventHandler();
        assertTrue(eventHandler.checkSessionsCount(1));
        OMSDKSessionDescriptor session = eventHandler.getFirstSession();
        String[] reasons = {OMSDKSessionDescriptor.EVENT_VALUE.OBSTRUCTED, OMSDKSessionDescriptor.EVENT_VALUE.CLIPPED};
        session.checkOMBaseEvents(platformName);
        session.checkGeometryChangeReasons(reasons);
    }

   //@Test(groups = {"serverBased"}, dataProvider = "mraidResize", dataProviderClass = InAppDataProviders.class)
    public void testMraidResizeAppolloBased(String adName) throws InterruptedException, TimeoutException {
        initValidTemplatesJson(adName);


        InAppBiddingAdPageImpl page = env.homePage.goToAd(adName);

        page.isAdDisplayed();

        env.waitForEvent(InAppBiddingEvents.AUCTION, 1, 60);
        env.validateEventRequest(InAppBiddingEvents.AUCTION, validAuctionRequest);

        checkGamOrMoPubEvents(adName);

        env.bmp.waitForEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_START, 1, 30);

        page.clickResizeCreative();

        page.clickCloseMRAIDResizeButton();

        env.homePage.clickBack();

        env.bmp.waitForEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_FINISH, 1, 30);
        // CHECK OM EVENTS
        OMSDKEventHandler eventHandler = new OMSDKEventHandler(env.bmp.getHar());
        assertTrue(eventHandler.checkSessionsCount(1));
        OMSDKSessionDescriptor session = eventHandler.getFirstSession();
        String[] reasons = {OMSDKSessionDescriptor.EVENT_VALUE.OBSTRUCTED, OMSDKSessionDescriptor.EVENT_VALUE.CLIPPED, OMSDKSessionDescriptor.EVENT_VALUE.NOT_FOUND};
        session.checkOMBaseEvents(platformName);
        session.checkGeometryChangeReasons(reasons);
    }

   @Test(groups = {"smoke"}, dataProvider = "mraidResize", dataProviderClass = InAppDataProviders.class)
    public void testMraidResizeOpenMap(String adName) throws InterruptedException {
        InAppBiddingAdPageImpl page = env.homePage.goToAd(adName);

        page.isAdDisplayed();

        page.clickResizeCreative();

        page.clickToMap();

        page.closeWebViewCreative();

        page.clickCloseMRAIDResizeButton();

        env.homePage.clickBack();
    }

   @Test(groups = {"smoke"}, dataProvider = "mraidResize", dataProviderClass = InAppDataProviders.class)
    public void testMraidResizePlayVideo(String adName) throws InterruptedException {
        InAppBiddingAdPageImpl page = env.homePage.goToAd(adName);

        page.isAdDisplayed();

        page.clickResizeCreative();

        page.clickPlayVideoResizeAd();

        page.isVideoAdExpanded();

        if (isPlatformIOS) {
            page.clickCloseInterstitial();
        } else {
            env.homePage.clickBack();
        }

        page.clickCloseMRAIDResizeButton();

        env.homePage.clickBack();
    }

   @Test(groups = {"smoke"}, dataProvider = "mraidResize", dataProviderClass = InAppDataProviders.class)
    public void testMraidResizeOpenApp(String adName) throws InterruptedException {
        InAppBiddingAdPageImpl page = env.homePage.goToAd(adName);

        page.isAdDisplayed();

        page.clickResizeCreative();

        page.clickToApp();

        if (platformName.equalsIgnoreCase("Android")) {
            page.closeWebViewCreative();
        }

        page.clickCloseMRAIDResizeButton();

        env.homePage.clickBack();
    }

   @Test(groups = {"smoke"}, dataProvider = "mraidResize", dataProviderClass = InAppDataProviders.class)
    public void testMraidResizeSMS(String adName) throws InterruptedException {
        InAppBiddingAdPageImpl page = env.homePage.goToAd(adName);

        page.isAdDisplayed();

        page.clickResizeCreative();

        page.clickSMS();

        page.smsAppShouldOpen();

        if (platformName.equalsIgnoreCase("Android")) {
            env.homePage.clickBack();
        }

        page.clickCloseMRAIDResizeButton();

        env.homePage.clickBack();
    }

   @Test(groups = {"smoke"}, dataProvider = "mraidResize", dataProviderClass = InAppDataProviders.class)
    public void testMraidResizeStorePicture(String adName) throws InterruptedException {
        InAppBiddingAdPageImpl page = env.homePage.goToAd(adName);

        page.isAdDisplayed();

        page.clickResizeCreative();

        page.clickStorePicture();

        page.confirmSavingPicture();

        page.clickCloseMRAIDResizeButton();

        env.homePage.clickBack();
    }

   @Test(groups = {"smoke"}, dataProvider = "mraidResize", dataProviderClass = InAppDataProviders.class)
    public void testResizeCreateCalendarEvent(String adName) throws InterruptedException {
        InAppBiddingAdPageImpl page = env.homePage.goToAd(adName);

        page.isAdDisplayed();

        page.clickResizeCreative();

        page.createCalendarEvent();

        if (isPlatformIOS) {
            page.confirmCreatingCalendarEvent();
        } else {
            env.homePage.sleep(5);
            env.homePage.clickBack();
        }

        page.clickCloseMRAIDResizeButton();

        env.homePage.clickBack();
    }

    // =============================
    // MRAID RESIZE WITH ERRORS TESTS
    // =============================

   @Test(groups = {"requests"})
    public void testMraidResizeWithErrorsWithOffsetTrue() throws InterruptedException, TimeoutException {
        InAppBiddingAdPageImpl page = env.homePage.goToAd(MRAID_RESIZE_WITH_ERRORS_IN_APP);

        page.isResizeWithErrorsAdLoaded();

        env.waitForOMEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_START, 1, 30);

        Dimension defaultAdSize = page.getResizeWithErrorsAdSize();

        page.resizeDown();

        page.isCloseButtonInvisible();//, "Close button shouldn't be displayed.");

        assertEquals(defaultAdSize, page.getResizeWithErrorsAdSize(), "Ad should not resize");

        page.resizeUp();

        page.isCloseButtonInvisible();//, "Close button shouldn't be displayed.");

        assertEquals(defaultAdSize, page.getResizeWithErrorsAdSize(), "Ad should not resize");

        page.resizeLeft();

        page.isCloseButtonInvisible();//, "Close button shouldn't be displayed.");

        assertEquals(defaultAdSize, page.getResizeWithErrorsAdSize(), "Ad should not resize");

        page.resizeRight();

        page.isCloseButtonInvisible();//, "Close button shouldn't be displayed.");

        assertEquals(defaultAdSize, page.getResizeWithErrorsAdSize(), "Ad should not resize");

        env.homePage.clickBack();

        env.waitForOMEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_FINISH, 1, 30);
        // CHECK OM EVENTS
        initEventHandler();
        assertTrue(eventHandler.checkSessionsCount(1));
        OMSDKSessionDescriptor session = eventHandler.getFirstSession();
        session.checkOMBaseEvents(platformName);
    }

   @Test(groups = {"requests"})
    public void testMraidResizeWithErrorsWithOffsetFalls() throws InterruptedException, TimeoutException {
        InAppBiddingAdPageImpl page = env.homePage.goToAd(MRAID_RESIZE_WITH_ERRORS_IN_APP);

        page.isResizeWithErrorsAdLoaded();

        env.waitForOMEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_START, 1, 30);

        Dimension defaultAdSize = page.getResizeWithErrorsAdSize();

        page.setToggleOffscreenFalse();

        page.resizeLeft();

        page.isCloseButtonDisplayed();//, "Close button should be displayed.";

        assertNotEquals(defaultAdSize, page.getResizeWithErrorsAdSize(), "Ad should resize");

        page.resizeRight();

        page.isCloseButtonDisplayed();//, "Close button should be displayed.");

        assertNotEquals(defaultAdSize, page.getResizeWithErrorsAdSize(), "Ad should resize");

        page.resizeUp();

        page.isCloseButtonDisplayed();//, "Close button should displayed.");

        assertNotEquals(defaultAdSize, page.getResizeWithErrorsAdSize(), "Ad should resize");

        page.resizeDown();

        page.isCloseButtonDisplayed();//, "Close button should be displayed.");

        assertNotEquals(defaultAdSize, page.getResizeWithErrorsAdSize(), "Ad should resize");

        env.homePage.clickBack();

        env.waitForOMEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_FINISH, 1, 30);
        // CHECK OM EVENTS
        initEventHandler();
        assertTrue(eventHandler.checkSessionsCount(1));
        OMSDKSessionDescriptor session = eventHandler.getFirstSession();
        session.checkOMBaseEvents(platformName);
    }


    // =============================
    // OM TESTS
    // =============================

   @Test(groups = {"requests"}, dataProvider = "adNameReload", dataProviderClass = InAppDataProviders.class)
    public void testOMEventsSingleSession(String adName) throws InterruptedException, TimeoutException {
        // RUN TEST SCENARIO
        InAppBiddingAdPageImpl page = env.homePage.goToAd(adName);

        page.isAdDisplayed();

        env.waitForOMEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_START, 1, 30);

        env.homePage.clickBack();

        env.waitForOMEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_FINISH, 1, 30);

        // CHECK OM EVENTS
        initEventHandler();
        assertTrue(eventHandler.checkSessionsCount(1));
        OMSDKSessionDescriptor session = eventHandler.getFirstSession();
        session.checkOMBaseEvents(platformName);
        session.checkNoObstructions();
    }

   @Test(groups = {"requests"}, dataProvider = "videoInterstitialAd", dataProviderClass = InAppDataProviders.class)
    public void testOMEventsSingleSessionVideoInterstitial(String adName) throws InterruptedException, TimeoutException {
        // RUN TEST SCENARIO
        InAppBiddingAdPageImpl page = env.homePage.goToAd(adName);

        page.clickShowButton();

        env.waitForOMEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_START, 1, 30);

        page.clickCloseInterstitial();

        env.homePage.clickBack();

        env.waitForOMEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_FINISH, 1, 30);
        // CHECK OM EVENTS
        initEventHandler();
        assertTrue(eventHandler.checkSessionsCount(1));
        OMSDKSessionDescriptor session = eventHandler.getFirstSession();
        session.checkOMBaseEvents(platformName);
        session.checkNoObstructions();
    }

   @Test(groups = {"requests"}, dataProvider = "adNameReload", dataProviderClass = InAppDataProviders.class)
    public void testOMEventsAfterReload(String adName) throws InterruptedException, TimeoutException {
        // RUN TEST SCENARIO
        InAppBiddingAdPageImpl page = env.homePage.goToAd(adName);

        page.isAdDisplayed();

        env.waitForOMEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_START, 1, 10);

        page.clickReloadButton();

        page.isAdDisplayed();

        env.waitForOMEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_START, 2, 10);

        env.homePage.clickBack();

        env.waitForOMEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_FINISH, 2, 10);
        initEventHandler();
        // CHECK OM EVENTS
        assertTrue(eventHandler.checkSessionsCount(2));
        for (OMSDKSessionDescriptor session : eventHandler.getSessions()) {
            session.checkOMBaseEvents(platformName);
            session.checkNoObstructions();
        }
    }

   @Test(groups = {"requests"}, dataProvider = "videoInterstitialAd", dataProviderClass = InAppDataProviders.class)
    public void testOMEventsAfterReloadVideoInterstitial(String adName) throws InterruptedException, TimeoutException {
        // RUN TEST SCENARIO
        InAppBiddingAdPageImpl page = env.homePage.goToAd(adName);

        page.clickShowButton();

        env.waitForOMEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_START, 1, 10);

        page.clickCloseRandom();

        if (platformName.equalsIgnoreCase("Android")) {
            if (adName.equalsIgnoreCase(MRAID_VIDEO_INTERSTITIAL_MOPUB)) {
                page.clickRetryButton();
            } else {
                page.clickReloadButton();
            }
            page.clickShowButton();
            env.waitForOMEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_START, 2, 30);
            page.clickCloseInterstitial();
            env.homePage.clickBack();
            env.waitForOMEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_FINISH, 2, 30);
            initEventHandler();
            // CHECK OM EVENTS
            assertTrue(eventHandler.checkSessionsCount(2));
            for (OMSDKSessionDescriptor session : eventHandler.getSessions()) {
                session.checkOMBaseEvents(platformName);
                session.checkNoObstructions();
            }
            //TODO there should be same behaiviour for iOS , but no reload button for now
        } else {
            env.homePage.clickBack();
            env.waitForOMEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_FINISH, 1, 30);
            initEventHandler();
            // CHECK OM EVENTS
            OMSDKSessionDescriptor session = eventHandler.getFirstSession();
            session.checkOMBaseEvents(platformName);
            session.checkNoObstructions();
        }
    }

   @Test(groups = {"requests"}, dataProvider = "adNameReload", dataProviderClass = InAppDataProviders.class)
    public void testOMEventsSessionWithBackground(String adName) throws InterruptedException, TimeoutException {
        InAppBiddingAdPageImpl page = env.homePage.goToAd(adName);

        page.isAdDisplayed();

        env.waitForOMEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_START, 1, 10);

        env.homePage.runAppInBackground(5);

        Thread.sleep(3000);

        env.homePage.clickBack();

        env.waitForOMEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_FINISH, 1, 10);
        // CHECK OM EVENTS
        initEventHandler();
        OMSDKSessionDescriptor session = eventHandler.getFirstSession();
        // On iOS I see different reasons in different cases. It could be 'backgrounded' or 'clipped' ,or 'notFound' for android
        String[] reasons = {OMSDKSessionDescriptor.EVENT_VALUE.BACKGROUNDED, OMSDKSessionDescriptor.EVENT_VALUE.CLIPPED, OMSDKSessionDescriptor.EVENT_VALUE.NOT_FOUND};
        session.checkOMBaseEvents(platformName);
        session.checkHideAndRestoreViewabilityWithReasons(reasons);
        session.checkNoObstructions();
    }

   @Test(groups = {"requests"}, dataProvider = "videoInterstitialAd", dataProviderClass = InAppDataProviders.class)
    public void testOMEventsSessionWithBackgroundVideoInterstitial(String adName) throws InterruptedException, TimeoutException {
        InAppBiddingAdPageImpl page = env.homePage.goToAd(adName);

        page.clickShowButton();

        env.waitForOMEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_START, 1, 10);
        //sleep needed to render creative and getting correct percentageInView
        Thread.sleep(5000);

        env.homePage.runAppInBackground(5);
        Thread.sleep(3000);

        page.clickCloseInterstitial();
        Thread.sleep(3000);

        env.homePage.clickBack();

        env.waitForOMEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_FINISH, 1, 30);
        // CHECK OM EVENTS
        initEventHandler();
        OMSDKSessionDescriptor session = eventHandler.getFirstSession();
        // On iOS I see different reasons in different cases. It could be 'backgrounded' or 'clipped' ,or 'notFound' for android
        String[] reasons = {OMSDKSessionDescriptor.EVENT_VALUE.BACKGROUNDED, OMSDKSessionDescriptor.EVENT_VALUE.CLIPPED, OMSDKSessionDescriptor.EVENT_VALUE.NOT_FOUND};
        session.checkOMBaseEvents(platformName);
        session.checkHideAndRestoreViewabilityWithReasons(reasons);
        session.checkNoObstructions();
    }

    // =============================
    //MRAID 3.0 TESTS
    // =============================

   @Test(groups = {"smoke"})
    public void testMraid3LoadAndEvents() throws InterruptedException, TimeoutException {
        InAppBiddingAdPageImpl page = env.homePage.goToAd(MRAID_LOAD_AND_EVENTS);

        env.waitForEvent(InAppBiddingEvents.AUCTION, 1, 60);

        page.isAdDisplayed();

        page.clickExpandStateChange();

        page.checkLogs("stepchange 2");

        page.clickCloseInterstitial();

        page.isAdDisplayed();

        page.clickExpandSizeChange();

        page.checkLogs("stepchange 4");

        page.clickTapToCloseExpand();

        page.clickTapToCheckLogs();

        page.checkLogs("stepchange 6");

        page.clickTapToUnload();

        env.homePage.clickBack();
    }

   @Test(groups = {"requests"})
    public void testMRAID3ViewabilityComplianceAd() throws TimeoutException, InterruptedException {
        InAppBiddingAdPageImpl page;
        if (isPlatformIOS) {
            page = env.homePage.goToAd(MRAID_VIEWABILITY_COMPLIANCE);
        } else {
            page = env.homePage.goToAd("MRAID 3.0: Viewability Compliance");
        }

        env.waitForEvent(InAppBiddingEvents.AUCTION, 1, 60);

        page.isAdDisplayed();

        page.performScrollUpAndDown();

        env.homePage.clickBack();
    }

    //TODO make same for android
   @Test(groups = {"ios"})
    public void testMRAID3ResizeNegative() throws InterruptedException {

        InAppBiddingAdPageImpl page = env.homePage.goToAd(MRAID_RESIZE_NEGATIVE);

        Thread.sleep(5000);

        page.checkLogsTestsStatuses("FAILED", false);

        page.checkLogsTestsStatuses("PASSED", true);

        page.clickCloseInterstitial();

        env.homePage.clickBack();
    }


}