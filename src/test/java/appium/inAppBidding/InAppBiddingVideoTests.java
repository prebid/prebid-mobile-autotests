package appium.inAppBidding;

import OMSDK.OMSDKAssert;
import OMSDK.OMSDKSessionDescriptor;
import adapters.PrebidAdapter;
import appium.common.InAppBiddingTestEnvironment.InAppBiddingEvents;
import appium.pages.inAppBidding.InAppBiddingAdPageImpl;
import org.testng.annotations.Test;
import utils.RequestValidator;

import java.util.concurrent.TimeoutException;

import static OMSDK.OMSDKAssert.assertTrue;
import static appium.common.InAppAdNamesImpl.INTERSTITIAL_320x480_ADMOB;
import static appium.common.InAppBiddingTestEnvironment.InAppBiddingDelegates.*;
import static appium.common.InAppTemplatesInit.VIDEO_OUTSTREAM_ENDCARD;

public class InAppBiddingVideoTests extends InAppBaseTest {

    ////////////////////////////
    //AUCTION REQUEST TESTS
    ////////////////////////////

//    @Test(groups = {"smoke"}, dataProvider = "adNameVideo", dataProviderClass = InAppDataProviders.class)
    public void testAuctionRequestVideo(String adName) throws TimeoutException, InterruptedException {

        initValidTemplatesJson(adName);
        InAppBiddingAdPageImpl page = env.homePage.goToAd(adName);

        if (adName.contains("Feed")) {
            System.out.println("PERFORM SCROLL TO FEED");
            page.scrollToFeedAd();
        }

        env.waitForEvent(InAppBiddingEvents.AUCTION, 1, 35);

        env.validateEventRequest(InAppBiddingEvents.AUCTION, validAuctionRequest);

        env.homePage.clickBack();

    }


    @Test(groups = {"requests"}, dataProvider = "randomAdVideo", dataProviderClass = InAppDataProviders.class)
    public void testAuctionRequestVideoRandom(String adName) throws TimeoutException, InterruptedException {
        initValidTemplatesJson(adName);

        InAppBiddingAdPageImpl videoPage = env.homePage.goToAd(adName);

        env.waitForEvent(InAppBiddingEvents.AUCTION, 1, 10);
        env.validateEventRequest(InAppBiddingEvents.AUCTION, validAuctionRequest);

        videoPage.clickShowButton();

        videoPage.clickCloseRandom();

        env.homePage.clickBack();

    }


    @Test(groups = {"requests"}, dataProvider = "noBidsVideo", dataProviderClass = InAppDataProviders.class)
    public void testAuctionRequestVideoNoBidsAd(String adName) throws TimeoutException, InterruptedException, NoSuchFieldException {
        initValidTemplatesJson(adName);

        env.homePage.goToAd(adName);

        env.waitForEvent(InAppBiddingEvents.AUCTION, 1, 30);
        env.validateEventRequest(InAppBiddingEvents.AUCTION, validAuctionRequest);

        PrebidAdapter prebidAdapter = prebidAdapterFactory.createPrebidAdapter(adName, env);
        try {
            env.waitForEvent(InAppBiddingEvents.WIN_PREBID, 0, 10);
        } catch (TimeoutException e) {
            prebidAdapter.checkEvents();
        }


        env.homePage.clickBack();
        RequestValidator.checkVersionParametersFromRequest(env.bmp.getHar(), ver, version, omidpv, displaymanagerver);
    }

    ////////////////////////////
    //VIDEO INTERSTITIAL TESTS
    ////////////////////////////


    //VIDEO DELEGATES TEST
    @Test(groups = {"ios"}, dataProvider = "videoInterstitialAdName", dataProviderClass = InAppDataProviders.class)
    public void testVideoInterstitialIOSDelegates(String adName) throws InterruptedException, NoSuchFieldException {
        initValidTemplatesJson(adName);

        InAppBiddingAdPageImpl videoPage = env.homePage.goToAd(adName);

        videoPage.isShowButtonEnabled();
        videoPage.clickShowButton();

        videoPage.clickLearnMore();
        Thread.sleep(5000);
        env.homePage.openInBrowser();

        videoPage.waitAndReturnToApp();

        Thread.sleep(3000);
        PrebidAdapter prebidAdapter = prebidAdapterFactory.createPrebidAdapter(adName, env,videoPage);
        prebidAdapter.checkVideoInterstitialDelegates();
        env.homePage.clickBack();
    }

    @Test(groups = {"requests"}, dataProvider = "videoInterstitialAdName", dataProviderClass = InAppDataProviders.class)
    public void testVideoInterstitialOMEventsSingleSession(String adName) throws TimeoutException, InterruptedException {
        InAppBiddingAdPageImpl page = env.homePage.goToAd(adName);

        page.isShowButtonEnabled();

        page.clickShowButton();

        env.bmp.waitForEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_START, 1, 30);

        page.clickCloseInterstitial();

        env.homePage.clickBack();

        env.bmp.waitForEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_FINISH, 1, 30);
        // CHECK OM EVENTS
        initEventHandler();
        assertTrue(eventHandler.checkSessionsCount(1));
        OMSDKSessionDescriptor session = eventHandler.getFirstSession();
        session.checkOMBaseEvents(platformName);
        session.checkPauseAndResumeAreEqual();
        session.checkVideoStartEvent(platformName);
        session.checkNonAutoPlaySkippableAndStandalonePosition();
        session.checkPlayerStateIsNormal();
    }

    @Test(groups = {"requests"}, dataProvider = "videoInterstitialAdName", dataProviderClass = InAppDataProviders.class)
    public void testVideoInterstitialOMEventsBackgrounded(String adName) throws TimeoutException, InterruptedException {
        InAppBiddingAdPageImpl page = env.homePage.goToAd(adName);

        page.clickShowButton();

        env.bmp.waitForEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_START, 1, 30);

        env.waitForEvent(InAppBiddingEvents.VIDEO_FIRSTQUARTILE, 1, 30);

        env.homePage.runAppInBackground(5);

        page.clickCloseInterstitial();

        env.homePage.clickBack();

        env.bmp.waitForEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_FINISH, 1, 30);
        // CHECK OM EVENTS
        initEventHandler();
        OMSDKAssert.assertTrue(eventHandler.checkSessions());
        String[] reasons;
        if (isPlatformIOS) {
            reasons = new String[]{OMSDKSessionDescriptor.EVENT_VALUE.BACKGROUNDED, OMSDKSessionDescriptor.EVENT_VALUE.CLIPPED};
        } else {
            reasons = new String[]{OMSDKSessionDescriptor.EVENT_VALUE.NOT_FOUND, OMSDKSessionDescriptor.EVENT_VALUE.BACKGROUNDED,};
        }
        OMSDKSessionDescriptor session = eventHandler.getFirstSession();
        session.checkMediaTypeIsVideo();
        session.checkOMBaseEvents(platformName);
        session.checkHideAndRestoreViewabilityWithReasons(reasons);
        session.checkPauseAndResumeAreEqual();
        session.checkVideoStartEvent(platformName);
        session.checkNonAutoPlaySkippableAndStandalonePosition();
        session.checkPlayerStateIsNormal();
    }

    @Test(groups = {"requests"}, dataProvider = "videoInterstitialAdName", dataProviderClass = InAppDataProviders.class)
    public void testVideoInterstitialOMEventsLearnMore(String adName) throws TimeoutException, InterruptedException {
        InAppBiddingAdPageImpl page = env.homePage.goToAd(adName);

        page.clickShowButton();

        env.bmp.waitForEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_START, 1, 30);

        page.clickLearnMore();

        page.closeWebViewCreative();

        page.clickCloseInterstitial();

        if (isPlatformIOS) {
            if (adName.contains("AdMob")) {
                env.homePage.isDelegateEnabled(INTERSTITIAL_DID_RECORD_IMPRESSION);
            } else {
                env.homePage.isDelegateEnabled(INTERSTITIAL_DID_CLICK);
            }
        } else {
            env.homePage.isDelegateEnabled(ON_AD_CLICKED);
            if (adName.contains("AdMob") || adName.contains("MoPub")) {
                env.homePage.isDelegateEnabled(ON_INTERSTITIAL_DISMISSED);
            } else {
                env.homePage.isDelegateEnabled(ON_AD_CLOSED);
            }
        }

        env.homePage.clickBack();

        env.bmp.waitForEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_FINISH, 1, 30);
        initEventHandler();
        OMSDKAssert.assertTrue(eventHandler.checkSessions());
        String[] reasons = {OMSDKSessionDescriptor.EVENT_VALUE.OBSTRUCTED, OMSDKSessionDescriptor.EVENT_VALUE.NOT_FOUND};
        OMSDKSessionDescriptor session = eventHandler.getFirstSession();
        session.checkOMBaseEvents(platformName);
        session.checkMediaTypeIsVideo();
        session.checkHideAndRestoreViewabilityWithReasons(reasons);
        session.checkPauseAndResumeAreEqual();
        session.checkVideoStartEvent(platformName);
        session.checkNonAutoPlaySkippableAndStandalonePosition();
        session.checkPlayerStateIsNormal();
    }

    @Test(groups = {"smoke"}, dataProvider = "videoInterstitialAdName", dataProviderClass = InAppDataProviders.class)
    public void testVideoInterstitialVideoEventsAndAutoclose(String adName) throws TimeoutException, InterruptedException {
        InAppBiddingAdPageImpl page = env.homePage.goToAd(adName);

        page.isShowButtonEnabled();

        page.clickShowButton();

        env.waitForEvent(InAppBiddingEvents.VIDEO_CREATIVEVIEW, 1, 30);
        env.waitForEvent(InAppBiddingEvents.VIDEO_START, 1, 30);
        env.waitForEvent(InAppBiddingEvents.VIDEO_FIRSTQUARTILE, 1, 30);
        env.waitForEvent(InAppBiddingEvents.VIDEO_MIDPOINT, 1, 30);
        env.waitForEvent(InAppBiddingEvents.VIDEO_THIRDQUARTILE, 1, 30);
        env.waitForEvent(InAppBiddingEvents.VIDEO_COMPLETE, 1, 30);

        env.homePage.clickBack();
    }

    @Test(groups = {"smoke"}, dataProvider = "videoInterstitialAdName", dataProviderClass = InAppDataProviders.class)
    public void testVideoInterstitialVideoClickPauseResumeClose(String adName) throws TimeoutException, InterruptedException {
        InAppBiddingAdPageImpl page = env.homePage.goToAd(adName);

        page.clickShowButton();

        page.clickLearnMore();

        env.waitForEvent(InAppBiddingEvents.VIDEO_CLICK, 2, 30);

        page.closeWebViewCreative();

        env.waitForEvent(InAppBiddingEvents.VIDEO_PAUSE, 1, 30);
        env.waitForEvent(InAppBiddingEvents.VIDEO_RESUME, 1, 30);

        env.homePage.clickBack();
    }

    @Test(groups = {"requests"}, dataProvider = "videoInterstitialEndCardAdName", dataProviderClass = InAppDataProviders.class)
    public void testVideoInterstitialOMEventsEndCardClicked(String adName) throws TimeoutException, InterruptedException {
        InAppBiddingAdPageImpl page = env.homePage.goToAd(adName);

        page.isShowButtonEnabled();

        page.clickShowButton();

        env.bmp.waitForEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_START, 1, 30);

        page.clickInterstitialAd();

        page.closeWebViewCreative();

        if (isPlatformIOS) {
            if (adName.contains("MoPub")) {
                env.homePage.isDelegateEnabled(INTERSTITIAL_DID_LOAD);
                env.homePage.isDelegateEnabled(INTERSTITIAL_WILL_APPEAR);
                env.homePage.isDelegateEnabled(INTERSTITIAL_DID_APPEAR);
                env.homePage.isDelegateEnabled(INTERSTITIAL_DID_DISAPPEAR);
                env.homePage.isDelegateEnabled(INTERSTITIAL_WILL_DISAPPEAR);
                env.homePage.isDelegateEnabled(INTERSTITIAL_DID_RECEIVED_TAP);
            }
            env.homePage.isDelegateEnabled(INTERSTITIAL_DID_CLICK);
        } else {
            page.isEndCardDisplayed();
            page.clickCloseInterstitial();
            env.homePage.isDelegateEnabled(ON_AD_CLICKED);
        }

        env.homePage.clickBack();

        env.bmp.waitForEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_FINISH, 1, 30);
        initEventHandler();
        OMSDKAssert.assertTrue(eventHandler.checkSessions());
        String[] reasons = {OMSDKSessionDescriptor.EVENT_VALUE.OBSTRUCTED, OMSDKSessionDescriptor.EVENT_VALUE.NOT_FOUND};
        OMSDKSessionDescriptor session = eventHandler.getFirstSession();
        session.checkOMBaseEvents(platformName);
        session.checkMediaTypeIsVideo();
        session.checkGeometryChangeReasons(reasons);
        session.checkVideoPlaybackEvents();
        session.checkOMEndCardUserInteractionsAndClick();
        session.checkNonAutoPlaySkippableAndStandalonePosition();
        session.checkPlayerStateIsNormal();
    }

    @Test(groups = {"android"}, dataProvider = "videoInterstitialAdName", dataProviderClass = InAppDataProviders.class)
    public void testVideoInterstitialAndroidDelegates(String adName) throws InterruptedException, NoSuchFieldException {
        InAppBiddingAdPageImpl videoPage = env.homePage.goToAd(adName);

        videoPage.isShowButtonEnabled();
        Thread.sleep(3000);

        videoPage.clickShowButton();
        Thread.sleep(5000);

        videoPage.clickLearnMore();
        Thread.sleep(10000);

        env.homePage.clickBack();
        Thread.sleep(5000);

        videoPage.clickCloseInterstitial();
        PrebidAdapter prebidAdapter = prebidAdapterFactory.createPrebidAdapter(adName, env);
        prebidAdapter.checkVideoInterstitialDelegates();
        env.homePage.clickBack();
    }


    ////////////////////////////
    //REWARDED TESTS
    ////////////////////////////


    //VIDEO DELEGATES TEST
//    @Test(groups = {"ios"}, dataProvider = "videoRewardedAdName", dataProviderClass = InAppDataProviders.class)
    public void testVideoRewardedIOSDelegates(String prebidAd) throws InterruptedException, NoSuchFieldException {
        InAppBiddingAdPageImpl videoPage = env.homePage.goToAd(prebidAd);

        videoPage.isShowButtonEnabled();
        Thread.sleep(3000);
        videoPage.clickShowButton();
        Thread.sleep(5000);

        videoPage.clickInterstitialAd();
        Thread.sleep(3000);

        env.homePage.openInBrowser();

        videoPage.waitAndReturnToApp();
        Thread.sleep(3000);
        PrebidAdapter prebidAdapter = prebidAdapterFactory.createPrebidAdapter(prebidAd, env,videoPage);
        prebidAdapter.checkVideoRewardedDelegates();
        env.homePage.clickBack();

    }

    @Test(groups = {"requests"}, dataProvider = "videoRewardedAdName", dataProviderClass = InAppDataProviders.class)
    public void testVideoRewardedOMEventsSingleSession(String adName) throws TimeoutException, InterruptedException, NoSuchFieldException {
        InAppBiddingAdPageImpl page = env.homePage.goToAd(adName);

        page.clickShowButton();

        env.bmp.waitForEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_START, 1, 60);

        page.clickCloseInterstitial();
        PrebidAdapter prebidAdapter = prebidAdapterFactory.createPrebidAdapter(adName, env);
        prebidAdapter.checkEvents();
        env.homePage.clickBack();

        env.bmp.waitForEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_FINISH, 1, 60);
        // CHECK OM EVENTS
        initEventHandler();
        System.out.println("Total sessions: " + eventHandler.getSessions().length);
        assertTrue(eventHandler.checkSessionsCount(1));
        OMSDKSessionDescriptor session = eventHandler.getFirstSession();
        session.checkOMBaseEvents(platformName);
        session.checkMediaTypeIsVideo();
        session.checkVideoPlaybackEvents();
        session.checkNonAutoPlaySkippableAndStandalonePosition();
        session.checkPlayerStateIsNormal();

    }


    @Test(groups = {"requests"}, dataProvider = "videoRewardedAdName", dataProviderClass = InAppDataProviders.class)
    public void testVideoRewardedOMEventsBackgrounded(String adName) throws TimeoutException, InterruptedException {
        InAppBiddingAdPageImpl page = env.homePage.goToAd(adName);

        page.clickShowButton();

        env.bmp.waitForEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_START, 1, 60);

        env.homePage.runAppInBackground(5);

        env.bmp.waitForEvent(OMSDKSessionDescriptor.EVENT_TYPE.FIRST_QUARTILE, 3, 60);

        page.isEndCardDisplayed();

        page.clickCloseInterstitial();

        env.homePage.clickBack();

        env.bmp.waitForEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_FINISH, 1, 60);
        // CHECK OM EVENTS
        initEventHandler();
        OMSDKAssert.assertTrue(eventHandler.checkSessions());
        String[] reasons;

        if (isPlatformIOS) {
            reasons = new String[]{OMSDKSessionDescriptor.EVENT_VALUE.BACKGROUNDED, OMSDKSessionDescriptor.EVENT_VALUE.CLIPPED};
        } else {
            reasons = new String[]{OMSDKSessionDescriptor.EVENT_VALUE.BACKGROUNDED, OMSDKSessionDescriptor.EVENT_VALUE.NOT_FOUND};
        }

        OMSDKSessionDescriptor session = eventHandler.getFirstSession();
        session.checkOMBaseEvents(platformName);
        session.checkMediaTypeIsVideo();
        session.checkPauseAndResumeAreEqual();
        session.checkVideoStartEvent(platformName);
        session.checkNonAutoPlaySkippableAndStandalonePosition();
        session.checkPlayerStateIsNormal();
        session.checkHideAndRestoreViewabilityWithReasons(reasons);
    }

//    @Test(groups = {"requests"}, dataProvider = "videoRewardedAdName", dataProviderClass = InAppDataProviders.class)
    public void testVideoRewardedOMEventsClickEndCard(String adName) throws TimeoutException, InterruptedException {
        InAppBiddingAdPageImpl page = env.homePage.goToAd(adName);

        page.clickShowButton();

        env.bmp.waitForEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_START, 1, 60);

        env.bmp.waitForEvent(OMSDKSessionDescriptor.EVENT_TYPE.THIRD_QUARTILE, 3, 60);

        page.clickInterstitialAd();

        page.closeWebViewCreative();

        if (platformName.equalsIgnoreCase("Android")) {
            page.clickCloseInterstitial();
        }

        env.homePage.clickBack();

        env.bmp.waitForEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_FINISH, 1, 60);
        // CHECK OM EVENTS
        initEventHandler();
        OMSDKAssert.assertTrue(eventHandler.checkSessions());
        String[] reasons = {OMSDKSessionDescriptor.EVENT_VALUE.OBSTRUCTED, OMSDKSessionDescriptor.EVENT_VALUE.NOT_FOUND};
        OMSDKSessionDescriptor session = eventHandler.getFirstSession();
        session.checkOMBaseEvents(platformName);
        session.checkMediaTypeIsVideo();
        session.checkGeometryChangeReasons(reasons);
        session.checkVideoPlaybackEvents();
        session.checkOMEndCardUserInteractionsAndClick();
        session.checkNonAutoPlaySkippableAndStandalonePosition();
        session.checkPlayerStateIsNormal();
    }

//    @Test(groups = {"smoke"}, dataProvider = "videoRewardedAdName", dataProviderClass = InAppDataProviders.class)
    public void testVideoRewardedPauseResumeClose(String adName) throws TimeoutException, InterruptedException {
        InAppBiddingAdPageImpl page = env.homePage.goToAd(adName);

        page.clickShowButton();

        Thread.sleep(2 * 1000);

        env.homePage.runAppInBackground(5);

        env.waitForEvent(InAppBiddingEvents.VIDEO_PAUSE, 2, 30);

        env.waitForEvent(InAppBiddingEvents.VIDEO_RESUME, 2, 30);

        page.clickCloseInterstitial();

        // TODO: 13.05.2021 Verify event count with iOS. on Android there is close event when video is closed and when end card is closed (thats why 4 events)
//        env.waitForEvent(InAppBiddingEvents.VIDEO_CLOSE, 4, 30);

        env.homePage.clickBack();
    }

    @Test(groups = {"smoke"}, dataProvider = "videoRewardedAdName", dataProviderClass = InAppDataProviders.class)
    public void testVideoRewardedVideoEvents(String adName) throws TimeoutException, InterruptedException {
        InAppBiddingAdPageImpl page = env.homePage.goToAd(adName);

        page.clickShowButton();

        env.waitForEvent(InAppBiddingEvents.VIDEO_CREATIVEVIEW, 2, 30);
        env.waitForEvent(InAppBiddingEvents.VIDEO_START, 2, 30);
        env.waitForEvent(InAppBiddingEvents.VIDEO_FIRSTQUARTILE, 2, 30);
        env.waitForEvent(InAppBiddingEvents.VIDEO_MIDPOINT, 2, 30);
        env.waitForEvent(InAppBiddingEvents.VIDEO_THIRDQUARTILE, 2, 30);
        env.waitForEvent(InAppBiddingEvents.VIDEO_COMPLETE, 2, 30);

        page.clickCloseInterstitial();

        env.homePage.clickBack();
    }

    @Test(groups = {"android"}, dataProvider = "videoRewardedAdName", dataProviderClass = InAppDataProviders.class)
    public void testVideoRewardedAndroidDelegates(String prebidAd) throws InterruptedException, TimeoutException, NoSuchFieldException {
        InAppBiddingAdPageImpl videoPage = env.homePage.goToAd(prebidAd);

        env.homePage.sleep(3);

        videoPage.clickShowButton();

        videoPage.clickInterstitialAd();

        env.homePage.sleep(3);

        videoPage.closeWebViewCreative();

        env.homePage.sleep(3);

        videoPage.clickCloseInterstitial();

        env.homePage.sleep(3);

        PrebidAdapter prebidAdapter = prebidAdapterFactory.createPrebidAdapter(prebidAd, env,videoPage);
        prebidAdapter.checkVideoRewardedDelegates();

        env.homePage.clickBack();
    }

    //
//    //////////////////////////
//    //    OUTSTREAM 300x250 TESTS
//    //////////////////////////
//
//
//    //VIDEO DELEGATES TEST
    @Test(groups = {"ios"}, dataProvider = "videoOutstreamAdName", dataProviderClass = InAppDataProviders.class)
    public void testVideoOutstreamIOSDelegates(String adName) throws InterruptedException, NoSuchFieldException {
        InAppBiddingAdPageImpl videoPage = env.homePage.goToAd(adName);

        videoPage.clickVideoAd();
        Thread.sleep(5000);

        env.homePage.openInBrowser();

        videoPage.waitAndReturnToApp();
        Thread.sleep(3000);
        PrebidAdapter prebidAdapter = prebidAdapterFactory.createPrebidAdapter(adName, env);
        prebidAdapter.checkVideoOutstreamDelegates();

        env.homePage.clickBack();
    }

    @Test(groups = {"requests"}, dataProvider = "videoOutstreamAdName", dataProviderClass = InAppDataProviders.class)
    public void testVideoOutstreamOMEventsSingleSessionMinimize(String adName) throws InterruptedException, TimeoutException {
        InAppBiddingAdPageImpl videoPage = env.homePage.goToAd(adName);

        env.bmp.waitForEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_START, 1, 60);

        env.bmp.waitForEvent(OMSDKSessionDescriptor.EVENT_TYPE.COMPLETE, 2, 60);

        videoPage.waitWhenWatchAgainDisplayed();

        env.homePage.clickBack();

        env.bmp.waitForEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_FINISH, 1, 60);
        // CHECK OM EVENTS
        initEventHandler();
        assertTrue(eventHandler.checkSessionsCount(1));
        OMSDKSessionDescriptor session = eventHandler.getFirstSession();
        session.checkOMBaseEvents(platformName);
        session.checkMediaTypeIsVideo();
        session.checkOutstreamPlaybackEvents(platformName);
        session.checkNonAutoPlaySkippableAndStandalonePosition();
        session.checkPlayerStateIsNormal();
    }

    @Test(groups = {"requests"}, dataProvider = "videoOutstreamAdName", dataProviderClass = InAppDataProviders.class)
    public void testVideoOutstreamOMEventsSingleSessionFullscreen(String adName) throws InterruptedException, TimeoutException {
        InAppBiddingAdPageImpl videoPage = env.homePage.goToAd(adName);

        env.bmp.waitForEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_START, 1, 60);

        videoPage.waitWhenWatchAgainDisplayed();

        env.homePage.clickBack();

        env.bmp.waitForEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_FINISH, 1, 60);
        // CHECK OM EVENTS
        initEventHandler();
        assertTrue(eventHandler.checkSessionsCount(1));
        OMSDKSessionDescriptor session = eventHandler.getFirstSession();
        session.checkOMBaseEvents(platformName);
        session.checkMediaTypeIsVideo();
        session.checkOutstreamPlaybackEvents(platformName);
        session.checkNonAutoPlaySkippableAndStandalonePosition();
    }

    @Test(groups = {"requests"}, dataProviderClass = InAppDataProviders.class)
    public void testVideoOutstreamOMEventsSingleSessionEndCard() throws InterruptedException, TimeoutException {
        InAppBiddingAdPageImpl videoPage = env.homePage.goToAd(VIDEO_OUTSTREAM_ENDCARD);

        env.bmp.waitForEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_START, 1, 30);
        env.waitForEvent(InAppBiddingEvents.VIDEO_COMPLETE, 1, 30);

        videoPage.isEndCardDisplayed();
        if (!isPlatformIOS) {
            env.homePage.clickBack();
        }

        env.homePage.clickBack();

        env.bmp.waitForEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_FINISH, 1, 30);
        // CHECK OM EVENTS
        initEventHandler();
        OMSDKAssert.assertTrue(eventHandler.checkSessions());
        String[] reasons = {OMSDKSessionDescriptor.EVENT_VALUE.OBSTRUCTED, OMSDKSessionDescriptor.EVENT_VALUE.CLIPPED, OMSDKSessionDescriptor.EVENT_VALUE.NOT_FOUND,};
        OMSDKSessionDescriptor session = eventHandler.getFirstSession();
        session.checkOMBaseEvents(platformName);
        session.checkMediaTypeIsVideo();
        session.checkNonAutoPlaySkippableAndStandalonePosition();
        session.checkOutstreamPlaybackEvents(platformName);
        session.checkPauseAndResumeAreEqual();
        session.checkGeometryChangeReasons(reasons);
        session.checkPlayerStateIsNormal();
    }

    @Test(groups = {"requests"}, dataProvider = "videoOutstreamAdName", dataProviderClass = InAppDataProviders.class)
    public void testVideoOutstreamOMEventsBackgrounded(String adName) throws InterruptedException, TimeoutException {
        InAppBiddingAdPageImpl videoPage = env.homePage.goToAd(adName);

        env.bmp.waitForEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_START, 1, 30);

        env.homePage.runAppInBackground(5);

        videoPage.waitWhenWatchAgainDisplayed();

        env.homePage.clickBack();

        env.bmp.waitForEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_FINISH, 1, 30);
        // CHECK OM EVENTS
        initEventHandler();
        OMSDKAssert.assertTrue(eventHandler.checkSessions());
        String[] reasons;

        if (isPlatformIOS) {
            reasons = new String[]{OMSDKSessionDescriptor.EVENT_VALUE.BACKGROUNDED, OMSDKSessionDescriptor.EVENT_VALUE.CLIPPED};
        } else {
            reasons = new String[]{OMSDKSessionDescriptor.EVENT_VALUE.NOT_FOUND, OMSDKSessionDescriptor.EVENT_VALUE.BACKGROUNDED,};
        }

        OMSDKSessionDescriptor session = eventHandler.getFirstSession();
        session.checkOMBaseEvents(platformName);
        session.checkMediaTypeIsVideo();
        session.checkHideAndRestoreViewabilityWithReasons(reasons);
        session.checkPauseAndResumeAreEqual();
        session.checkVideoStartEvent(platformName);
        session.checkNonAutoPlaySkippableAndStandalonePosition();
        session.checkPlayerStateIsNormal();
    }

    @Test(groups = {"requests"}, dataProviderClass = InAppDataProviders.class)
    public void testVideoOutstreamOMEventsBackgroundedEndCard() throws InterruptedException, TimeoutException {
        InAppBiddingAdPageImpl videoPage = env.homePage.goToAd(VIDEO_OUTSTREAM_ENDCARD);

        env.bmp.waitForEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_START, 1, 30);

        env.waitForEvent(InAppBiddingEvents.VIDEO_FIRSTQUARTILE, 1, 30);

        env.homePage.runAppInBackground(5);
        videoPage.isEndCardDisplayed();

        env.homePage.clickBack();

        env.bmp.waitForEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_FINISH, 1, 30);
        // CHECK OM EVENTS
        initEventHandler();
        OMSDKAssert.assertTrue(eventHandler.checkSessions());
        String[] reasons;

        if (isPlatformIOS) {
            reasons = new String[]{OMSDKSessionDescriptor.EVENT_VALUE.BACKGROUNDED, OMSDKSessionDescriptor.EVENT_VALUE.CLIPPED};
        } else {
            reasons = new String[]{OMSDKSessionDescriptor.EVENT_VALUE.BACKGROUNDED, OMSDKSessionDescriptor.EVENT_VALUE.NOT_FOUND};
        }

        OMSDKSessionDescriptor session = eventHandler.getFirstSession();
        session.checkOMBaseEvents(platformName);
        session.checkMediaTypeIsVideo();
        session.checkHideAndRestoreViewabilityWithReasons(reasons);
        session.checkPauseAndResumeAreEqual();
        session.checkVideoStartEvent(platformName);
        session.checkNonAutoPlaySkippableAndStandalonePosition();
        session.checkPlayerStateIsNormal();
    }

    @Test(groups = {"smoke"}, dataProvider = "videoOutstreamAdName", dataProviderClass = InAppDataProviders.class)
    public void testVideoOutstreamVideoEventsPauseResume(String adName) throws InterruptedException, TimeoutException {
        InAppBiddingAdPageImpl videoPage = env.homePage.goToAd(adName);

        env.waitForEvent(InAppBiddingEvents.VIDEO_CREATIVEVIEW, 1, 30);
        env.waitForEvent(InAppBiddingEvents.VIDEO_START, 1, 30);

        videoPage.clickVideoAd();
        env.waitForEvent(InAppBiddingEvents.VIDEO_PAUSE, 1, 30);
        videoPage.closeWebViewCreative();
        env.waitForEvent(InAppBiddingEvents.VIDEO_RESUME, 1, 30);

        env.waitForEvent(InAppBiddingEvents.VIDEO_FIRSTQUARTILE, 1, 30);

        env.waitForEvent(InAppBiddingEvents.VIDEO_MIDPOINT, 1, 30);
        env.waitForEvent(InAppBiddingEvents.VIDEO_THIRDQUARTILE, 1, 30);
        env.waitForEvent(InAppBiddingEvents.VIDEO_COMPLETE, 1, 30);

        videoPage.waitWhenWatchAgainDisplayed();
        env.homePage.clickBack();
    }

    @Test(groups = {"smoke"})
    public void testVideoOutstreamVideoEventsPauseResumeEndCard() throws InterruptedException, TimeoutException {
        InAppBiddingAdPageImpl videoPage = env.homePage.goToAd(VIDEO_OUTSTREAM_ENDCARD);

        videoPage.clickVideoAd();
        videoPage.closeWebViewCreative();

        env.waitForEvent(InAppBiddingEvents.VIDEO_CREATIVEVIEW, 1, 30);
        env.waitForEvent(InAppBiddingEvents.VIDEO_START, 1, 30);
        env.waitForEvent(InAppBiddingEvents.VIDEO_FIRSTQUARTILE, 1, 30);

        env.waitForEvent(InAppBiddingEvents.VIDEO_MIDPOINT, 1, 30);
        env.waitForEvent(InAppBiddingEvents.VIDEO_THIRDQUARTILE, 1, 30);
        env.waitForEvent(InAppBiddingEvents.VIDEO_COMPLETE, 1, 30);

        videoPage.isEndCardDisplayed();

        env.homePage.clickBack();
    }


    @Test(groups = {"android"}, dataProvider = "videoOutstreamAdName", dataProviderClass = InAppDataProviders.class)
    public void testVideoOutstreamAndroidDelegates(String adName) throws InterruptedException, NoSuchFieldException {
        InAppBiddingAdPageImpl videoPage = env.homePage.goToAd(adName);
        Thread.sleep(5000);

        videoPage.clickVideoAd();
        Thread.sleep(5000);

        videoPage.closeWebViewCreative();
        PrebidAdapter prebidAdapter = prebidAdapterFactory.createPrebidAdapter(adName, env);
        prebidAdapter.checkVideoOutstreamDelegates();

        env.homePage.clickBack();
    }
}