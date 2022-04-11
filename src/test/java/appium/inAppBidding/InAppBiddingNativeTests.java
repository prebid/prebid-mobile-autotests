package appium.inAppBidding;

import OMSDK.OMSDKSessionDescriptor;
import adapters.PrebidAdapter;
import appium.common.InAppBiddingTestEnvironment.InAppBiddingDelegates;
import appium.common.InAppBiddingTestEnvironment.InAppBiddingEvents;
import appium.pages.inAppBidding.InAppBiddingAdPageImpl;
import org.testng.annotations.Test;

import java.util.concurrent.TimeoutException;

import static appium.common.InAppAdNamesImpl.NATIVE_AD_LINKS_IN_APP;
import static appium.common.InAppBiddingTestEnvironment.InAppBiddingDelegates.FETCH_DEMAND;
import static appium.common.InAppBiddingTestEnvironment.InAppBiddingDelegates.ON_NATIVE_FETCH_DEMAND_SUCCESS;
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

        env.bmp.waitForEvent(OMSDKSessionDescriptor.EVENT_TYPE.SESSION_START, 1, 60);

        //env.waitForOMEvent(OMSDKSessionDescriptor.EVENT_TYPE.IMPRESSION, 1, 60);

        env.homePage.clickBack();

    }

    @Test(groups = {"requests-iOS"}, dataProvider = "nativeRequestAdsIos", dataProviderClass = InAppDataProviders.class)
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

    @Test(groups = {"requests-iOS"}, dataProvider = "nativeNoBidsAdsIos", dataProviderClass = InAppDataProviders.class)
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

    @Test(groups = {"requests-Android"}, dataProvider = "nativeNoBidsAdsAndroid", dataProviderClass = InAppDataProviders.class)
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

    @Test(groups = {"requests-Android"}, dataProvider = "nativeRequestAdsAndroid", dataProviderClass = InAppDataProviders.class)
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

    @Test(groups = {"ios"}, dataProvider = "nativeAdsIos", dataProviderClass = InAppDataProviders.class)
    public void testNativeAdsiOSDelegates(String prebidAd) throws InterruptedException, NoSuchFieldException {
        initValidTemplatesJson(prebidAd);

        InAppBiddingAdPageImpl nativePage = env.homePage.goToAd(prebidAd);
        if (!prebidAd.contains("AdMob")) {
            env.homePage.isDelegateEnabled(FETCH_DEMAND);
        }
        PrebidAdapter prebidAdapter = prebidAdapterFactory.createPrebidAdapter(prebidAd, env,nativePage);
        prebidAdapter.checkNativeAdsDelegates(prebidAd);
        env.homePage.clickBack();

    }

    @Test(groups = {"android"}, dataProvider = "nativeAdsAndroid", dataProviderClass = InAppDataProviders.class)
    public void testNativeAdsAndroidDelegates(String prebidAd) throws InterruptedException, NoSuchFieldException {
        initValidTemplatesJson(prebidAd);

        InAppBiddingAdPageImpl nativePage = env.homePage.goToAd(prebidAd);
        if (!prebidAd.contains("AdMob")) {
            env.homePage.isDelegateEnabled(ON_NATIVE_FETCH_DEMAND_SUCCESS);
        }
        PrebidAdapter prebidAdapter = prebidAdapterFactory.createPrebidAdapter(prebidAd, env,nativePage);
        prebidAdapter.checkNativeAdsDelegates(prebidAd);
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


}