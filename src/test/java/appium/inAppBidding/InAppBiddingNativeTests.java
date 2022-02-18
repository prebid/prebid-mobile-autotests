package appium.inAppBidding;

import OMSDK.OMSDKSessionDescriptor;
import appium.common.InAppBiddingTestEnvironment.InAppBiddingDelegates;
import appium.common.InAppBiddingTestEnvironment.InAppBiddingEvents;
import appium.pages.inAppBidding.InAppBiddingAdPageImpl;
import org.testng.annotations.Test;

import java.util.concurrent.TimeoutException;

import static appium.common.InAppAdNamesImpl.NATIVE_AD_LINKS_IN_APP;
import static appium.common.InAppBiddingTestEnvironment.InAppBiddingDelegates.*;
import static appium.common.InAppTemplatesInit.NATIVE_AD_FEED_MOPUB_PBM;
//import static appium.common.InAppTemplatesInit.NATIVE_AD_LINKS_IN_APP;

public class InAppBiddingNativeTests extends InAppBaseTest {

    //NATIVE TESTS

//    @Test(groups = {"serverBased"}, dataProvider = "nativeAds", dataProviderClass = InAppDataProviders.class)
    public void testAuctionNativeAds(String prebidAd) throws TimeoutException, InterruptedException {
        initValidTemplatesJson(prebidAd);

        env.homePage.goToAd(prebidAd);

        env.waitForEvent(InAppBiddingEvents.AUCTION, 1, 60);

        env.validateEventRequest(InAppBiddingEvents.AUCTION, validAuctionRequest);
        env.validateEventResponse(InAppBiddingEvents.AUCTION, validAuctionResponse);

        env.waitForOMEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_START, 1, 60);

        //env.waitForOMEvent(OMSDKSessionDescriptor.EVENT_TYPE.IMPRESSION, 1, 60);

        env.homePage.clickBack();

    }

    @Test(groups = {"requests-iOS"}, dataProvider = "nativeMockedRequestAdsIos", dataProviderClass = InAppDataProviders.class)
    public void testAuctionNativeAdsEventsIos(String prebidAd) throws TimeoutException, InterruptedException {
        initValidTemplatesJson(prebidAd);

        InAppBiddingAdPageImpl page = env.homePage.goToAd(prebidAd);

        if (prebidAd.contains("Feed")) {
            System.out.println("PERFORM SCROLL TO FEED");
            page.scrollToFeedAd();
        }
        env.waitForEvent(InAppBiddingEvents.AUCTION, 1, 60);

        env.validateEventRequest(InAppBiddingEvents.AUCTION, validAuctionRequest);

        if (!isPlatformIOS) {
            env.validateEventResponse(InAppBiddingEvents.AUCTION, validAuctionResponse);
        }

        env.waitForEvent(InAppBiddingEvents.IMPRESSION, 1, 60);

        env.waitForEvent(InAppBiddingEvents.MRC50, 1, 60);

        env.waitForEvent(InAppBiddingEvents.MRC100, 1, 60);

        env.homePage.clickBack();
    }

    @Test(groups = {"requests-iOS"}, dataProvider = "nativeMockedNoBidsAdsIos", dataProviderClass = InAppDataProviders.class)
    public void testAuctionNativeNoBidsAdsIos(String prebidAd) throws TimeoutException, InterruptedException {
        initValidTemplatesJson(prebidAd);

        InAppBiddingAdPageImpl page = env.homePage.goToAd(prebidAd);

        if (prebidAd.contains("Feed")) {
            System.out.println("PERFORM SCROLL TO FEED");
            page.scrollToFeedAd();
        }
        env.waitForEvent(InAppBiddingEvents.AUCTION, 1, 60);

        env.homePage.clickBack();
    }

    @Test(groups = {"requests-Android"}, dataProvider = "nativeMockedNoBidsAdsAndroid", dataProviderClass = InAppDataProviders.class)
    public void testAuctionNativeNoBidsAdsAndroid(String prebidAd) throws TimeoutException, InterruptedException {
        initValidTemplatesJson(prebidAd);

        InAppBiddingAdPageImpl page = env.homePage.goToAd(prebidAd);

        /*if (prebidAd.contains("Feed")) {
            System.out.println("PERFORM SCROLL TO FEED");
            page.scrollToFeedAd();
        }*/
        env.waitForEvent(InAppBiddingEvents.AUCTION, 1, 60);

        env.homePage.clickBack();
    }

    @Test(groups = {"requests-Android"}, dataProvider = "nativeMockedRequestAdsAndroid", dataProviderClass = InAppDataProviders.class)
    public void testAuctionNativeAdsEventsAndroid(String prebidAd) throws TimeoutException, InterruptedException {
        initValidTemplatesJson(prebidAd);

        InAppBiddingAdPageImpl page = env.homePage.goToAd(prebidAd);

        if (prebidAd.contains("Feed")) {
            System.out.println("PERFORM SCROLL TO FEED");
            page.scrollToFeedAd();
        }

        env.waitForEvent(InAppBiddingEvents.AUCTION, 1, 60);

        env.validateEventRequest(InAppBiddingEvents.AUCTION, validAuctionRequest);

        if (!isPlatformIOS) {
            env.validateEventResponse(InAppBiddingEvents.AUCTION, validAuctionResponse);
        }

        env.waitForEvent(InAppBiddingEvents.IMPRESSION, 1, 60);

        env.waitForEvent(InAppBiddingEvents.MRC50, 1, 60);

        env.waitForEvent(InAppBiddingEvents.MRC100, 1, 60);

        env.homePage.clickBack();
    }

    @Test(groups = {"ios"}, dataProvider = "nativeMockedAdsIos", dataProviderClass = InAppDataProviders.class)
    public void testNativeAdsiOSDelegates(String prebidAd) throws InterruptedException {
        initValidTemplatesJson(prebidAd);

        InAppBiddingAdPageImpl nativePage = env.homePage.goToAd(prebidAd);
        if (!prebidAd.contains("AdMob")) {
            env.homePage.isDelegateEnabled(FETCH_DEMAND);
        }
        checkNativeAdsIosDelegates(prebidAd, nativePage);
        env.homePage.clickBack();

    }

    @Test(groups = {"android"}, dataProvider = "nativeMockedAdsAndroid", dataProviderClass = InAppDataProviders.class)
    public void testNativeAdsAndroidDelegates(String prebidAd) throws InterruptedException {
        initValidTemplatesJson(prebidAd);

        InAppBiddingAdPageImpl nativePage = env.homePage.goToAd(prebidAd);
        if (!prebidAd.contains("AdMob")) {
            env.homePage.isDelegateEnabled(ON_NATIVE_FETCH_DEMAND_SUCCESS);
        }
        checkNativeAdsAndroidDelegates(prebidAd, nativePage);
        env.homePage.clickBack();

    }

    //    @Test(groups = {"requests"}, dataProvider = "nativeMockedAds", dataProviderClass = InAppDataProviders.class)
    // OM is temporarily disabled for native ads
    public void testNativeAdsOMEventsAndClick(String prebidAd) throws TimeoutException, InterruptedException {
        initValidTemplatesJson(prebidAd);

        InAppBiddingAdPageImpl page = env.homePage.goToAd(prebidAd);

        env.waitForEvent(InAppBiddingEvents.AUCTION, 1, 60);

//        env.waitForOMEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_START, 1, 60);

        page.clickHereToVisitOurSite();

        page.closeWebViewCreative();

        env.waitForEvent(InAppBiddingEvents.CLICK_ROOT_URL, 1, 60);

        checkDelegates();

        env.homePage.clickBack();

//        env.waitForOMEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_FINISH, 1, 60);

//        initEventHandler();
//        assertTrue(eventHandler.checkSessionsCount(1));
//        OMSDKSessionDescriptor session = eventHandler.getFirstSession();
//        session.checkNativeOMBaseEvents(platformName);
    }

    //    @Test(groups = {"requests"}, dataProvider = "nativeMockedAds", dataProviderClass = InAppDataProviders.class)
    // OM is temporarily disabled for native ads
    public void testNativeAdsOMEventsBackgrounded(String prebidAd) throws TimeoutException, InterruptedException {
        initValidTemplatesJson(prebidAd);

        InAppBiddingAdPageImpl page = env.homePage.goToAd(prebidAd);

        env.waitForEvent(InAppBiddingEvents.AUCTION, 1, 60);

//        env.waitForOMEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_START, 1, 60);

        env.homePage.runAppInBackground(5);

        env.homePage.clickBack();

//        env.waitForOMEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_FINISH, 1, 60);

        // CHECK OM EVENTS
//        initEventHandler();
//        assertTrue(eventHandler.checkSessionsCount(1));
//        OMSDKSessionDescriptor session = eventHandler.getFirstSession();
//        // On iOS I see different reasons in different cases. It could be 'backgrounded' or 'clipped' ,or 'notFound' for android
//        String[] reasons = {OMSDKSessionDescriptor.EVENT_VALUE.BACKGROUNDED, OMSDKSessionDescriptor.EVENT_VALUE.CLIPPED, OMSDKSessionDescriptor.EVENT_VALUE.NOT_FOUND};
//        session.checkNativeOMBaseEvents(platformName);
//        session.checkHideAndRestoreViewabilityWithReasons(reasons);
    }

    //    @Test(groups = {"requests"})
    // OM is temporarily disabled for native ads
    public void testNativeAdFeedEventsAndOM() throws TimeoutException, InterruptedException {
        InAppBiddingAdPageImpl page = env.homePage.goToAd(NATIVE_AD_FEED_MOPUB_PBM);

        if (isPlatformIOS) {
            page.scrollToFeedAd();
        }

        env.waitForEvent(InAppBiddingEvents.AUCTION, 1, 60);

        env.waitForEvent(InAppBiddingEvents.IMPRESSION, 1, 60);

        env.waitForEvent(InAppBiddingEvents.MRC50, 1, 60);

        env.waitForEvent(InAppBiddingEvents.MRC100, 1, 60);

//        env.waitForOMEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_START, 1, 60);

        env.homePage.clickBack();

//        env.waitForOMEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_FINISH, 1, 60);

//        initEventHandler();
//        assertTrue(eventHandler.checkSessionsCount(1));
//        OMSDKSessionDescriptor session = eventHandler.getFirstSession();
//        session.checkNativeOMBaseEvents(platformName);
//        session.checkNoObstructions();
    }

    //    @Test(groups = {"requests"})
    public void testNativeAdLinks() throws TimeoutException, InterruptedException {
        InAppBiddingAdPageImpl page = env.homePage.goToAd(NATIVE_AD_LINKS_IN_APP);

        env.waitForEvent(InAppBiddingEvents.AUCTION, 1, 60);

        page.clickBtnNativeLinkRoot();

//        env.waitForEvent(InAppBiddingEvents.CLICK_ROOT_URL, 1, 60);

//        page.closeWebViewCreative();
        env.homePage.clickBack();
        page.clickBtnNativeDeeplinkOk();

        env.waitForEvent(InAppBiddingEvents.CLICK_DEEPLINK, 1, 60);

//        page.closeWebViewCreative();
        env.homePage.clickBack();

        page.clickBtnNativeLinkUrl();

        env.waitForEvent(InAppBiddingEvents.CLICK_DATA_URL, 1, 60);

//        page.closeWebViewCreative();
        env.homePage.clickBack();

        page.clickBtnNativeDeeplinkFallback();

        env.waitForEvent(InAppBiddingEvents.CLICK_DATA_FALLBACK, 1, 60);

//        page.closeWebViewCreative();
        env.homePage.clickBack();

        env.homePage.clickBack();
    }

    private void checkDelegates() {
        if (isPlatformIOS) {
            env.homePage.isDelegateEnabled(InAppBiddingDelegates.FETCH_DEMAND);
            env.homePage.isDelegateEnabled(InAppBiddingDelegates.GET_NATIVE_AD);
            env.homePage.isDelegateEnabled(InAppBiddingDelegates.NATIVE_AD_DID_CLICK);
        } else {
            env.homePage.isDelegateEnabled(InAppBiddingDelegates.ON_NATIVE_FETCH_DEMAND_SUCCESS);
            env.homePage.isDelegateEnabled(InAppBiddingDelegates.ON_NATIVE_GET_NATIVE_AD_SUCCESS);
            env.homePage.isDelegateEnabled(InAppBiddingDelegates.ON_AD_CLICKED);
        }
    }

    private void checkNativeAdsIosDelegates(String prebidAd, InAppBiddingAdPageImpl nativePage) throws InterruptedException {
        switch (getAdapter(prebidAd)) {
            case "In-App": {
                if (prebidAd.contains("Links")) {
                    nativePage.clickBtnNativeLinkRoot();
                } else {
                    nativePage.clickHereToVisitOurSite();
                }
                nativePage.waitAndReturnToApp();
                env.homePage.isDelegateEnabled(GET_NATIVE_AD);
                env.homePage.isDelegateEnabled(NATIVE_AD_DID_LOG_IMPRESSION);
                env.homePage.isDelegateEnabled(NATIVE_AD_DID_CLICK);
                break;
            }
            case "GAM": {
                if (prebidAd.contains("Custom")) {
                    env.homePage.isDelegateEnabled(CUSTOM_NATIVE_AD_REQUEST_SUCCESS);
                    if (prebidAd.contains("GAD")) {
                        env.homePage.isDelegateEnabled(CUSTOM_NATIVE_AD_PRIMARY_WIN);
                    }
                } else if (prebidAd.contains("Unified")){
                    env.homePage.isDelegateEnabled(UNIFIED_CUSTOM_AD_REQUEST_SUCCESS);
                    if (prebidAd.contains("GAD")) {
                        env.homePage.isDelegateEnabled(UNIFIED_NATIVE_AD_PRIMARY_WIN);
                    }
                }
                if (!prebidAd.contains("GAD")) {
                    env.homePage.isDelegateEnabled(NATIVE_AD_DID_LOAD);
                }
                break;
            }
            case "MoPub": {
                env.homePage.isDelegateEnabled(GET_NATIVE_AD);
                env.homePage.isDelegateEnabled(NATIVE_AD_PRIMARY_WIN);
                env.homePage.isDelegateEnabled(NATIVE_AD_DID_TRACK_IMPRESSION);
                break;
            }
            case "AdMob": {
                env.homePage.isDelegateEnabled(LOADER_DID_RECEIVE_BUTTON);
                env.homePage.isDelegateEnabled(LOADER_DID_FINISH_LOADING_BUTTON);
                break;
            }
        }
    }

    private String getAdapter(String prebidAd) {
        return prebidAd.substring(prebidAd.indexOf("(")+1, prebidAd.indexOf(")"));
    }

    private void checkNativeAdsAndroidDelegates(String prebidAd, InAppBiddingAdPageImpl nativePage) throws InterruptedException {
        switch (getAdapter(prebidAd)) {
            case "GAM": {
                if (prebidAd.contains("Custom")) {
                    env.homePage.isDelegateEnabled(CUSTOM_NATIVE_AD_REQUEST_SUCCESS);
                } else if (prebidAd.contains("Unified")) {
                    env.homePage.isDelegateEnabled(UNIFIED_CUSTOM_AD_REQUEST_SUCCESS);
                }
                env.homePage.isDelegateEnabled(ON_AD_IMPRESSION);
                if (prebidAd.contains("GADUnified")){
                    env.homePage.isDelegateEnabled(UNIFIED_NATIVE_AD_PRIMARY_WIN);
                    nativePage.clickButtonCallToAction();
                } else {
                    if (prebidAd.contains("GAD")) {
                        env.homePage.isDelegateEnabled(CUSTOM_NATIVE_AD_PRIMARY_WIN);
                    }
                    nativePage.clickHereToVisitOurSite();
                }
                Thread.sleep(1000);
                env.homePage.clickBack();
                env.homePage.isDelegateEnabled(ON_AD_CLICKED);
                break;
            }
            case "AdMob":{
                nativePage.clickTvBody();
                Thread.sleep(1000);
                env.homePage.clickBack();
                env.homePage.isDelegateEnabled(ON_AD_LOADED);
                env.homePage.isDelegateEnabled(ON_AD_CLICKED);
                env.homePage.isDelegateEnabled(ON_AD_SHOWED);
                env.homePage.isDelegateEnabled(ON_AD_OPENED);
                break;
            }
            case "In-App": {
                if (prebidAd.contains("Links")) {
                    nativePage.clickBtnNativeLinkRoot();
                } else {
                    nativePage.clickHereToVisitOurSite();
                }
                Thread.sleep(1000);
                env.homePage.clickBack();
                env.homePage.isDelegateEnabled(ON_NATIVE_GET_NATIVE_AD_SUCCESS);
                env.homePage.isDelegateEnabled(ON_AD_CLICKED);
                env.homePage.isDelegateEnabled(ON_AD_IMPRESSION);
                break;
            }
            case "MoPub":{
                nativePage.clickHereToVisitOurSite();
                Thread.sleep(1000);
                env.homePage.clickBack();
                env.homePage.isDelegateEnabled(NATIVE_AD_DID_LOAD);
                env.homePage.isDelegateEnabled(ON_AD_CLICKED);
                env.homePage.isDelegateEnabled(NATIVE_AD_DID_LOG_IMPRESSION);
                break;
            }

        }
    }

}