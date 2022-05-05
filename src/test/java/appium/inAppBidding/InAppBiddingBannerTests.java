package appium.inAppBidding;

import OMSDK.OMSDKSessionDescriptor;
import appium.common.InAppBiddingTestEnvironment.InAppBiddingEvents;
import appium.pages.inAppBidding.InAppBiddingAdPageImpl;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.RequestValidator;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static OMSDK.OMSDKAssert.assertTrue;
import static appium.common.InAppTemplatesInit.*;

public class InAppBiddingBannerTests extends InAppBaseTest {

    //BANNER TESTS
    @Test(groups = {"requests-simulator"}, dataProvider = "adName", dataProviderClass = InAppDataProviders.class)
    public void testAuctionRequest(String prebidAd) throws TimeoutException, InterruptedException {
        initValidTemplatesJson(prebidAd);

        env.homePage.goToAd(prebidAd);

        env.waitForEvent(InAppBiddingEvents.AUCTION, 1, 30);
        env.validateEventRequest(InAppBiddingEvents.AUCTION, validAuctionRequest);
        env.waitForEvent(InAppBiddingEvents.WIN_PREBID, 1, 30);

        env.homePage.clickBack();

        RequestValidator.checkVersionParametersFromRequest(env.bmp.getHar(), ver, version, omidpv, displaymanagerver);
    }

    @Test(groups = {"requests-realDevice"}, dataProvider = "adNameReal", dataProviderClass = InAppDataProviders.class)
    public void testAuctionRequestRealDevice(String prebidAd) throws TimeoutException, InterruptedException {
        initValidTemplatesJsonRealDevice(prebidAd);

        env.homePage.goToAd(prebidAd);

        env.waitForEvent(InAppBiddingEvents.AUCTION, 1, 30);
        env.validateEventRequest(InAppBiddingEvents.AUCTION, validAuctionRequest);
        env.waitForEvent(InAppBiddingEvents.WIN_PREBID, 1, 30);

        env.homePage.clickBack();

        RequestValidator.checkVersionParametersFromRequest(env.bmp.getHar(), ver, version, omidpv, displaymanagerver);
    }

    @Test(groups = {"requests-simulator"}, dataProvider = "noBids", dataProviderClass = InAppDataProviders.class)
    public void testAuctionRequestNoBidsAd(String prebidAd) throws TimeoutException, InterruptedException {
        String noBidAd;
        initValidTemplatesJson(prebidAd);

        if (prebidAd.equalsIgnoreCase(BANNER_320x50_NO_BID_IN_APP) && !(isPlatformIOS)) {
            noBidAd = "Banner 320x50 [noBids] (In-App)";
        } else {
            noBidAd = prebidAd;
        }

        env.homePage.goToAd(noBidAd);

        env.waitForEvent(InAppBiddingEvents.AUCTION, 1, 10);
        env.validateEventRequest(InAppBiddingEvents.AUCTION, validAuctionRequest);

        System.out.println(InAppBiddingEvents.GAM_G_DOUBLECLICK);
        initPrebidAdapter(prebidAd,env);
        prebidAdapter.checkEvents();

        env.homePage.clickBack();
    }

    @Test(groups = {"ios"})
    public void testAuctionRequestSKAdNetwork() throws TimeoutException, InterruptedException {

        initValidTemplatesJson(BANNER_SKADNETWORK);

        env.homePage.goToAd(BANNER_SKADNETWORK);

        env.waitForEvent(InAppBiddingEvents.AUCTION, 1, 10);
        env.validateEventRequest(InAppBiddingEvents.AUCTION, validAuctionRequest);

        env.homePage.clickBack();
    }

    @Test(groups = {"requests-simulator"}, dataProvider = "randomAd", dataProviderClass = InAppDataProviders.class)
    public void testAuctionRequestRandomAd(String prebidAd) throws TimeoutException, InterruptedException {
        initValidTemplatesJson(prebidAd);
        env.homePage.goToAd(prebidAd);

        env.waitForEvent(InAppBiddingEvents.AUCTION, 1, 10);
        env.validateEventRequest(InAppBiddingEvents.AUCTION, validAuctionRequest);

        env.homePage.clickBack();
    }

    //BANNER CUSTOM TESTS
    @Test(groups = {"requests"}, dataProvider = "adName", dataProviderClass = InAppDataProviders.class)
    public void testAuctionRequestReload(String prebidAd) throws InterruptedException, TimeoutException {
        initValidTemplatesJson(prebidAd);

        InAppBiddingAdPageImpl bannerPage = env.homePage.goToAd(prebidAd);

        env.waitForEvent(InAppBiddingEvents.AUCTION, 1, 15);
        env.waitForEvent(InAppBiddingEvents.WIN_PREBID, 1, 30);

        env.homePage.sleep(3);

        bannerPage.clickReloadButton();

        env.homePage.sleep(3);

        env.waitForEvent(InAppBiddingEvents.AUCTION, 2, 15);
        env.waitForEvent(InAppBiddingEvents.WIN_PREBID, 2, 30);

        env.homePage.clickBack();

    }

    //BANNER DELEGATES TEST
    @Test(groups = {"ios"}, dataProvider = "bannerAds", dataProviderClass = InAppDataProviders.class)
    public void testBanneriOSDelegates(String prebidAd) throws InterruptedException {
        initValidTemplatesJson(prebidAd);

        InAppBiddingAdPageImpl bannerPage = env.homePage.goToAd(prebidAd);

        bannerPage.clickBanner();
        env.homePage.openInBrowser();
        bannerPage.waitAndReturnToApp();
        initPrebidAdapter(prebidAd,env,bannerPage);
        prebidAdapter.checkBannerDelegates();
        env.homePage.clickBack();

    }

    //BANNER DELEGATES TEST
    @Test(groups = {"android"}, dataProvider = "bannerAds", dataProviderClass = InAppDataProviders.class)
    public void testBannerAndroidDelegates(String prebidAd) throws InterruptedException {
        initValidTemplatesJson(prebidAd);
        InAppBiddingAdPageImpl bannerPage = env.homePage.goToAd(prebidAd);
        bannerPage.clickBanner();
        env.homePage.clickCloseButtonClickThroughBrowser();
        initPrebidAdapter(prebidAd,env,bannerPage);
        prebidAdapter.checkBannerDelegates();
        env.homePage.clickBack();
    }


    //OM Events Tests

    /**
     * Test case to checks all basic events for Banner Ads
     * - sessionStart
     * - sessionFinish
     * - impression
     * - viewability
     * - no obstructions
     *
     * @throws IOException
     */
    @Test(groups = {"requests"}, dataProvider = "bannerAds", dataProviderClass = InAppDataProviders.class)
    public void testOMEventsSingleSession(String bannerAds) throws InterruptedException, TimeoutException {
        // RUN TEST SCENARIO
        InAppBiddingAdPageImpl bannerPage = env.homePage.goToAd(bannerAds);

        env.bmp.waitForEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_START, 1, 30);

        bannerPage.isAdDisplayed();

        if (isPlatformIOS && (bannerAds.contains("728x90") || bannerAds.contains("Multisize"))) {
            env.homePage.rotateLandscape();
            env.homePage.rotatePortrait();
        }
        initPrebidAdapter(bannerAds,env);
        prebidAdapter.checkEvents();
        env.homePage.clickBack();

        env.bmp.waitForEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_FINISH, 1, 30);
        // CHECK OM EVENTS
        initEventHandler();
        assertTrue(eventHandler.checkSessionsCount(1));
        OMSDKSessionDescriptor session = eventHandler.getFirstSession();
        session.checkOMBaseEvents(platformName);
        session.checkNoObstructions();

    }

    @Test(groups = {"requests"}, dataProvider = "customOmAdName", dataProviderClass = InAppDataProviders.class)
    public void testOMEventsAfterReload(String bannerAds) throws InterruptedException, TimeoutException {
        // RUN TEST SCENARIO
        InAppBiddingAdPageImpl bannerPage = env.homePage.goToAd(bannerAds);

        env.bmp.waitForEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_START, 1, 10);

        bannerPage.clickReloadButton();

        env.bmp.waitForEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_START, 2, 10);

        env.homePage.clickBack();

        env.bmp.waitForEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_FINISH, 2, 50);
        // CHECK OM EVENTS
        initEventHandler();
        assertTrue(eventHandler.checkSessionsCount(2));
        for (OMSDKSessionDescriptor session : eventHandler.getSessions()) {
            session.checkOMBaseEvents(platformName);
            session.checkNoObstructions();
        }
    }

    /**
     * Test case to checks all basic events for Banner Ads in case with tapping on the ad and calling the clickthrough
     * - sessionStart
     * - sessionFinish
     * - impression
     * - restore viewability
     *
     * @throws IOException
     */
    @Test(groups = {"requests"}, dataProvider = "customOmAdName", dataProviderClass = InAppDataProviders.class)
    public void testOMEventsSessionWithOpenClickthrough(String bannerAds) throws InterruptedException, TimeoutException {
        // RUN TEST SCENARIO
        InAppBiddingAdPageImpl bannerPage = env.homePage.goToAd(bannerAds);

        env.bmp.waitForEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_START, 1, 30);
        bannerPage.clickBanner();

        env.homePage.openInBrowser();

        bannerPage.waitAndReturnToApp();

        if (platformName.equalsIgnoreCase("android")) {
            env.homePage.clickBack();
        }

        env.homePage.clickBack();

        env.bmp.waitForEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_FINISH, 1, 30);
        // CHECK OM EVENTS
        initEventHandler();
        assertTrue(eventHandler.checkSessionsCount(1));
        OMSDKSessionDescriptor session = eventHandler.getFirstSession();
        // It could be 'obstructed' when webBrowser opened or 'clipped', or 'notFound' for android
        String[] reasons = {OMSDKSessionDescriptor.EVENT_VALUE.OBSTRUCTED, OMSDKSessionDescriptor.EVENT_VALUE.CLIPPED, OMSDKSessionDescriptor.EVENT_VALUE.NOT_FOUND};
        session.checkOMBaseEvents(platformName);
        session.checkHideAndRestoreViewabilityWithReasons(reasons);
    }

    /**
     * Test case to checks all basic events for Banner Ads in case with moving app to background during the sessison
     * - sessionStart
     * - sessionFinish
     * - impression
     * - backgrounded
     * - restore viewability
     *
     * @throws IOException
     */
    @Test(groups = {"requests"},dataProvider = "customOmAdName",dataProviderClass = InAppDataProviders.class)
    public void testOMEventsSessionWithBackground(String prebidAd) throws InterruptedException, TimeoutException {
        InAppBiddingAdPageImpl bannerPage = env.homePage.goToAd(prebidAd);

        bannerPage.isAdDisplayed();

        env.bmp.waitForEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_START, 1, 10);

        env.homePage.runAppInBackground(5);

        bannerPage.isAdDisplayed();
        initPrebidAdapter(prebidAd,env);
        prebidAdapter.checkLoadDelegate();

        env.homePage.clickBack();

        env.bmp.waitForEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_FINISH, 1, 10);

        // CHECK OM EVENTS
        initEventHandler();
        assertTrue(eventHandler.checkSessionsCount(1));
        OMSDKSessionDescriptor session = eventHandler.getFirstSession();
        // On iOS I see different reasons in different cases. It could be 'backgrounded' or 'clipped' ,or 'notFound' for android
        String[] reasons = {OMSDKSessionDescriptor.EVENT_VALUE.BACKGROUNDED, OMSDKSessionDescriptor.EVENT_VALUE.CLIPPED, OMSDKSessionDescriptor.EVENT_VALUE.NOT_FOUND};
        session.checkOMBaseEvents(platformName);
        session.checkHideAndRestoreViewabilityWithReasons(reasons);
    }

    @Test(groups = {"requests"})
    public void testOMEventsSessionWithScroll() throws InterruptedException, TimeoutException {
        String scrollableBanner;

        if (platformName.equalsIgnoreCase("iOS")) {
            scrollableBanner = BANNER_320x50_IN_APP_SCROLLABLE;
        } else {
            scrollableBanner = "Scrollable";
        }

        InAppBiddingAdPageImpl bannerPage = env.homePage.goToAd(scrollableBanner);

        initPrebidAdapter(scrollableBanner,env);
        prebidAdapter.checkLoadDelegate();

        env.bmp.waitForEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_START, 1, 30);

        bannerPage.performScrollUpAndDown();

        env.homePage.clickBack();

        env.bmp.waitForEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_FINISH, 1, 30);

        initEventHandler();
        assertTrue(eventHandler.checkSessionsCount(1));
        String[] reasons = {OMSDKSessionDescriptor.EVENT_VALUE.CLIPPED, OMSDKSessionDescriptor.EVENT_VALUE.OBSTRUCTED};
        OMSDKSessionDescriptor session = eventHandler.getFirstSession();
        session.checkGeometryChangeReasons(reasons);
        session.checkOMBaseEvents(platformName);
    }

    @Test(groups = {"smoke"}, enabled = false)
    public void testAdLoadsAfterFailInFirstAttempt() throws TimeoutException, InterruptedException {
        int autoRefreshDelay = 15;

        env.bmp.setResponseError();

        env.homePage.turnOnCustomConfig();

        env.homePage.goToAd(BANNER_320x50_ADMOB);

        env.homePage.setAutoRefreshDelay(autoRefreshDelay);

        initPrebidAdapter(BANNER_320x50_ADMOB,env);
        prebidAdapter.checkLoadDelegate();

        env.waitForEvent(InAppBiddingEvents.AUCTION, 1, 5);
        env.waitForEvent(InAppBiddingEvents.WIN_PREBID, 0, 5);

        env.bmp.cancelResponseError();
        //env.restartBMP(true);
        env.bmp.getLogs();

        env.waitForEvent(InAppBiddingEvents.AUCTION, 2, 5, autoRefreshDelay);

        prebidAdapter.checkLoadFailDelegate();

        env.waitForEvent(InAppBiddingEvents.WIN_PREBID, 1, 5, autoRefreshDelay);

        env.homePage.clickBack();
    }

    @Test(groups = {"smoke"}, enabled = false)
    public void testRefreshWithFailsInTheMiddle() throws TimeoutException, InterruptedException {
        int autoRefreshDelay = 15;

        env.homePage.turnOnCustomConfig();

        env.homePage.goToAd(BANNER_320x50_ADMOB);

        env.homePage.setAutoRefreshDelay(autoRefreshDelay);

        initPrebidAdapter(BANNER_320x50_ADMOB,env);

        prebidAdapter.checkLoadDelegate();

        env.waitForEvent(InAppBiddingEvents.AUCTION, 1, 10);

        env.waitForEvent(InAppBiddingEvents.WIN_PREBID, 1, 10);

        env.bmp.setResponseError();

        env.waitForEvent(InAppBiddingEvents.AUCTION, 2, autoRefreshDelay);

        prebidAdapter.checkLoadFailDelegate();

        env.waitForEvent(InAppBiddingEvents.WIN_PREBID, 1, autoRefreshDelay);

        env.bmp.cancelResponseError();
        env.bmp.getLogs();

        env.waitForEvent(InAppBiddingEvents.AUCTION, 3, autoRefreshDelay);

        prebidAdapter.checkLoadDelegate();

        env.waitForEvent(InAppBiddingEvents.WIN_PREBID, 2, autoRefreshDelay);

        env.homePage.clickBack();
    }

    @Test(groups = {"smoke"}, priority = 1,dataProvider = "refreshAds",dataProviderClass = InAppDataProviders.class)
    public void testClientRefreshMaxAlwaysFails(String prebidAd) throws TimeoutException, InterruptedException, IOException {
        int setUpEventsCount = 4;

        int autoRefreshDelay = 15;

        env.homePage.turnOnCustomConfig();

        env.bmp.setResponseError();

        env.homePage.goToAd(prebidAd);

        env.homePage.setAutoRefreshDelay(autoRefreshDelay);

        env.waitForEvent(InAppBiddingEvents.AUCTION, setUpEventsCount, autoRefreshDelay * setUpEventsCount);

        env.waitForEvent(InAppBiddingEvents.WIN_PREBID, 0, 10);

        env.homePage.clickBack();

        env.bmp.cancelResponseError();
    }

    @Test(groups = {"requests"})
    public void testRefreshClientSide() throws TimeoutException, InterruptedException {
        int expectedEventCount = 4;

        int autoRefreshDelay = 15;

        env.homePage.turnOnCustomConfig();

        env.homePage.goToAd(BANNER_320x50_IN_APP);

        env.homePage.setAutoRefreshDelay(autoRefreshDelay);

        initPrebidAdapter(BANNER_320x50_IN_APP,env);
        prebidAdapter.checkLoadDelegate();

        env.bmp.waitForEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_START, 1, 10);

        env.waitForEvent(InAppBiddingEvents.AUCTION, expectedEventCount, autoRefreshDelay * expectedEventCount);
        env.waitForEvent(InAppBiddingEvents.WIN_PREBID, expectedEventCount, autoRefreshDelay * expectedEventCount);

        env.homePage.clickBack();

        env.bmp.waitForEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_FINISH, expectedEventCount, 30);
        // CHECK OM EVENTS
        initEventHandler();
        assertTrue(eventHandler.checkSessionsCount(expectedEventCount));
        for (OMSDKSessionDescriptor session : eventHandler.getSessions()) {
            session.checkOMBaseEvents(platformName);
            session.checkNoObstructions();
        }

    }

    @Test(groups = {"smoke"},dataProvider = "refreshAds",dataProviderClass = InAppDataProviders.class)
    public void testDefaultRefresh(String prebidAd) throws TimeoutException, InterruptedException {
        env.homePage.goToAd(prebidAd);

        env.waitForEvent(InAppBiddingEvents.AUCTION, 1, 30);

        initPrebidAdapter(prebidAd,env);
        prebidAdapter.checkLoadDelegate();
        //wait for reload
        env.waitForEvent(InAppBiddingEvents.AUCTION, 2, 30, 60);

        env.homePage.clickBack();
    }

    @Test(groups = {"requests"})
    public void testWithIncorrectVastFile() throws TimeoutException, InterruptedException {
        env.homePage.goToAd(BANNER_320x50_IN_APP_VAST);

        env.waitForEvent(InAppBiddingEvents.AUCTION, 1, 10);

        initPrebidAdapter(BANNER_320x50_IN_APP_VAST,env);
        prebidAdapter.checkLoadFailDelegate();

        env.waitForEvent(InAppBiddingEvents.WIN_PREBID, 0, 10);

        env.homePage.clickBack();
    }

    @Test(groups = {"smoke"},dataProvider = "refreshAds",dataProviderClass = InAppDataProviders.class)
    public void testAdRequestLimitation(String prebidAd) throws TimeoutException, InterruptedException {
        int autoRefreshDelay = 15;

        env.homePage.turnOnCustomConfig();

        InAppBiddingAdPageImpl bannerPage = env.homePage.goToAd(prebidAd);

        env.homePage.setAutoRefreshDelay(autoRefreshDelay);

        initPrebidAdapter(prebidAd,env);
        prebidAdapter.checkLoadDelegate();

        env.waitForEvent(InAppBiddingEvents.AUCTION, 1, 5);

        bannerPage.clickBanner();

        env.homePage.sleep(20);

        //Check that there is NO additional request during app in webView
        env.waitForEvent(InAppBiddingEvents.AUCTION, 1, autoRefreshDelay);

        bannerPage.closeWebViewCreative();

        env.waitForEvent(InAppBiddingEvents.AUCTION, 2, 30, autoRefreshDelay);

        env.homePage.clickBack();
    }

    @Test(groups = {"requests"})
    public void testSlowConnection() throws TimeoutException, InterruptedException {
        // regular 2G network (250/50 KB/s, 300ms latency -  diff because of real network latency)

        env.bmp.setLatency(300);

        env.homePage.goToAd(BANNER_320x50_ADMOB);

        env.waitForEvent(InAppBiddingEvents.AUCTION, 1, 35);

        initPrebidAdapter(BANNER_320x50_ADMOB,env);
        prebidAdapter.checkLoadDelegate();

        env.waitForEvent(InAppBiddingEvents.WIN_PREBID, 1, 35);

        env.homePage.clickBack();

        env.bmp.cancelLatency();
    }

    @Test(groups = {"requests"}, priority = 1)
    public void testNoConnection() throws TimeoutException, InterruptedException {
        env.bmp.setLatency(30 * 1000);

        env.homePage.goToAd(BANNER_320x50_IN_APP);

        env.waitForEvent(InAppBiddingEvents.AUCTION, 1, 35);

        initPrebidAdapter(BANNER_320x50_IN_APP,env);
        prebidAdapter.checkLoadFailDelegate();

        env.waitForEvent(InAppBiddingEvents.WIN_PREBID, 0, 10);

        env.homePage.clickBack();

        env.bmp.cancelLatency();
    }

    @Test(groups = {"atts"}, priority = 2)
    public void testAttsNotAllowed() throws InterruptedException, TimeoutException {
        initValidTemplatesJson(ATTS_2);

        env.homePage.goToUtilities();

        env.homePage.clickRequestTrackingAuthorization();

        Thread.sleep(3000);

        env.homePage.clickAppNotToTrack();

        env.homePage.goToAdExamples();

        env.homePage.goToAd(BANNER_320x50_IN_APP);

        //Validate default request with ATTS 2;
        env.waitForEvent(InAppBiddingEvents.AUCTION, 1, 60);
        env.validateEventRequest(InAppBiddingEvents.AUCTION, validAuctionRequest);

        env.homePage.clickBack();

        env.homePage.resetApp();
    }

    @Test(groups = {"atts"}, priority = 3)
    public void testAttsAllowed() throws InterruptedException, TimeoutException {
        initValidTemplatesJson(ATTS_3);

        env.homePage.goToUtilities();

        env.homePage.clickRequestTrackingAuthorization();

        Thread.sleep(3000);

        env.homePage.clickAllow();

        env.homePage.goToAdExamples();

        env.homePage.goToAd(BANNER_320x50_IN_APP);

        //Validate default request with ATTS 3;
        env.waitForEvent(InAppBiddingEvents.AUCTION, 1, 60);
        env.validateEventRequest(InAppBiddingEvents.AUCTION, validAuctionRequest);

        env.homePage.clickBack();

        env.homePage.resetApp();
    }

    @Test(groups = {"smoke"},dataProvider = "refreshAds",dataProviderClass = InAppDataProviders.class)
    public void testCustomRefresh(String prebidAd) throws TimeoutException, InterruptedException {

        int autoRefreshDelay = 15;

        env.homePage.turnOnCustomConfig();

        InAppBiddingAdPageImpl bannerPage = env.homePage.goToAd(prebidAd);

        env.homePage.setAutoRefreshDelay(autoRefreshDelay);

        env.waitForEvent(InAppBiddingEvents.AUCTION, 1, 15);
        //Check that refresh works properly
        env.waitForEvent(InAppBiddingEvents.AUCTION, 2, 15);
        //Check that refresh works properly
        env.waitForEvent(InAppBiddingEvents.AUCTION, 3, 15);

        env.homePage.clickBack();
    }

    @Test(groups = {"smoke"})
    public void testNoRefreshIfBannerViewDisabledScrollable() throws TimeoutException, InterruptedException {

        int autoRefreshDelay = 15;

        env.homePage.turnOnCustomConfig();

        String scrollableBanner;

        if (platformName.equalsIgnoreCase("iOS")) {
            scrollableBanner = BANNER_320x50_IN_APP_SCROLLABLE;
        } else {
            scrollableBanner = "Scrollable";
        }

        InAppBiddingAdPageImpl bannerPage = env.homePage.goToAd(scrollableBanner);

        env.homePage.setAutoRefreshDelay(autoRefreshDelay);

        env.waitForEvent(InAppBiddingEvents.AUCTION, 1, 15);
        //Check that refresh works properly

        bannerPage.performScrollUp();
        //Need to wait for check that no new Requests added
        Thread.sleep(30000);
        //Check that there was NO refresh during BannerView WAS not in view
        env.waitForEvent(InAppBiddingEvents.AUCTION, 1, 15);

        bannerPage.performScrollDown();

        //Check that there was refresh after BannerView WAS in view
        env.waitForEvent(InAppBiddingEvents.AUCTION, 2, 15);

        env.homePage.clickBack();
    }

    @Test(groups = {"smoke"},dataProvider = "refreshAds",dataProviderClass = InAppDataProviders.class)
    public void testNoRefreshIfBannerViewDisabledTabOverlay(String prebidAd) throws TimeoutException, InterruptedException {

        int autoRefreshDelay = 15;

        env.homePage.turnOnCustomConfig();

        InAppBiddingAdPageImpl bannerPage = env.homePage.goToAd(prebidAd);

        env.homePage.setAutoRefreshDelay(autoRefreshDelay);

        env.waitForEvent(InAppBiddingEvents.AUCTION, 1, 15);
        //Check that refresh works properly

        bannerPage.clickUtilitiesTab();
        //Need to wait for check that no new Requests added
        Thread.sleep(30000);
        //Check that there was NO refresh during BannerView WAS overlayed
        env.waitForEvent(InAppBiddingEvents.AUCTION, 1, 15);
        if (isPlatformIOS) {
            bannerPage.clickExamplesTab();
        } else {
            env.homePage.clickBack();
            env.homePage.setAutoRefreshDelay(autoRefreshDelay);
        }
        //Check that there was refresh after
        env.waitForEvent(InAppBiddingEvents.AUCTION, 2, 15);
        env.homePage.clickBack();
    }

    @Test(groups = {"smoke"},dataProvider = "refreshAds",dataProviderClass = InAppDataProviders.class)
    public void testNoRefreshIfBannerViewDisabledBackgrounded(String prebidAd) throws TimeoutException, InterruptedException {

        int autoRefreshDelay = 15;

        env.homePage.turnOnCustomConfig();

        env.homePage.goToAd(prebidAd);

        env.homePage.setAutoRefreshDelay(autoRefreshDelay);

        env.waitForEvent(InAppBiddingEvents.AUCTION, 1, 15);

        //Check that refresh works properly
        env.waitForEvent(InAppBiddingEvents.AUCTION, 2, 15);

        //App goes to background for 30 seconds, there shouldn't be new requests
        env.homePage.runAppInBackground(30);

        env.waitForEvent(InAppBiddingEvents.AUCTION, 3, 15);
        //Check that refresh works properly after return from background
        env.waitForEvent(InAppBiddingEvents.AUCTION, 4, 15);

        env.homePage.clickBack();
    }

}