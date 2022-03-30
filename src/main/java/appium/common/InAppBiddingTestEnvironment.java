package appium.common;

import appium.pages.inAppBidding.InAppBiddingHomePageImpl;
import appium.pages.inAppBidding.android.InAppBiddingHomePageAndroid;
import appium.pages.inAppBidding.ios.InAppBiddingHomePageIOS;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import net.lightbody.bmp.proxy.CaptureType;
import org.json.JSONObject;
import org.openqa.selenium.Platform;
import org.testng.ITestContext;
import utils.RequestValidator;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import static appium.common.InAppBiddingTestEnvironment.InAppBiddingDelegates.*;

/**
 * Test Environment class for handling default & system properties and preparing appium capabilities
 * Override Default properties Key-Values with System properties  Key-Values.
 * e.g. -DdeviceName="iPhone 8 Plus" in command line will override deviceName defined in .properties file
 */
public class InAppBiddingTestEnvironment extends TestEnvironment {

    // Private Properties

    private static final String PROPERTIES_FILE_PATH_IOS = "src/test/resources/appium/config/iOSConfig.properties";
    private static final String PROPERTIES_FILE_PATH_ANDROID = "src/test/resources/appium/config/AndroidInAppBiddingConfig.properties";
    private static final String PROPERTIES_FILE_PATH_ANDROID_PREBID_SERVER = "src/test/resources/appium/config/AndroidConfig.properties";

    private Set<TrafficInspectorKind> trafficInspectors;

    private static final Map<InAppBiddingEvents, String> inAppBidding_Events;

    static {
        inAppBidding_Events = new HashMap<>();
        inAppBidding_Events.put(InAppBiddingEvents.AUCTION, "/openrtb2/auction");
        inAppBidding_Events.put(InAppBiddingEvents.IMPRESSION, "/events/tracker/impression");
        inAppBidding_Events.put(InAppBiddingEvents.MRC50, "/events/tracker/mrc50");
        inAppBidding_Events.put(InAppBiddingEvents.MRC100, "/events/tracker/mrc100");
        inAppBidding_Events.put(InAppBiddingEvents.CLICK_ROOT_URL, "/events/click/root/url");
        inAppBidding_Events.put(InAppBiddingEvents.CLICK_DEEPLINK, "/events/click/data/deeplink");
        inAppBidding_Events.put(InAppBiddingEvents.CLICK_DATA_URL, "/events/click/data/url");
        inAppBidding_Events.put(InAppBiddingEvents.CLICK_DATA_FALLBACK, "/events/click/data/fallback");
        inAppBidding_Events.put(InAppBiddingEvents.GAM_GAMPAD, "/gampad/ads");
        inAppBidding_Events.put(InAppBiddingEvents.GAM_G_DOUBLECLICK, "g.doubleclick.net");
        inAppBidding_Events.put(InAppBiddingEvents.MOPUB_AD, "/m/ad");
        inAppBidding_Events.put(InAppBiddingEvents.ADMOB_PAGEAD_INTERACTION, "/pagead/interaction");
        inAppBidding_Events.put(InAppBiddingEvents.ADMOB_MADS_GMA, "mads/gma");
        inAppBidding_Events.put(InAppBiddingEvents.WIN_PREBID, "/win/prebid");

        inAppBidding_Events.put(InAppBiddingEvents.VIDEO_REQUEST, "ads/video/vast/");
        inAppBidding_Events.put(InAppBiddingEvents.VIDEO_REWARDED_REQUEST, "/sdks/");
        inAppBidding_Events.put(InAppBiddingEvents.VIDEO_IMPRESSION, "/v/1.0/ri?");
        inAppBidding_Events.put(InAppBiddingEvents.VIDEO_CLICK, "/v/1.0/rc?");
        inAppBidding_Events.put(InAppBiddingEvents.VIDEO_CREATIVEVIEW, "/rv?t=creativeView");
        inAppBidding_Events.put(InAppBiddingEvents.VIDEO_START, "/rv?t=start");
        inAppBidding_Events.put(InAppBiddingEvents.VIDEO_FIRSTQUARTILE, "/rv?t=firstQuartile");
        inAppBidding_Events.put(InAppBiddingEvents.VIDEO_MIDPOINT, "/rv?t=midpoint");
        inAppBidding_Events.put(InAppBiddingEvents.VIDEO_THIRDQUARTILE, "/rv?t=thirdQuartile");
        inAppBidding_Events.put(InAppBiddingEvents.VIDEO_COMPLETE, "/rv?t=complete");
        inAppBidding_Events.put(InAppBiddingEvents.VIDEO_PAUSE, "/rv?t=pause");
        inAppBidding_Events.put(InAppBiddingEvents.VIDEO_RESUME, "/rv?t=resume");
        inAppBidding_Events.put(InAppBiddingEvents.VIDEO_CLOSE, "/rv?t=close");
    }

    private static final Map<InAppBiddingDelegates, String> inAppBidding_Delegates_iOS;

    static {
        inAppBidding_Delegates_iOS = new HashMap<>();
        inAppBidding_Delegates_iOS.put(InAppBiddingDelegates.AD_VIEW_RECEIVED, "adViewDidReceiveAd called");
        inAppBidding_Delegates_iOS.put(InAppBiddingDelegates.AD_VIEW_DID_FAIL_LOAD, "adViewDidFailToLoadAd called");
        inAppBidding_Delegates_iOS.put(InAppBiddingDelegates.AD_VIEW_PRESENT, "adViewWillPresentScreen called");
        inAppBidding_Delegates_iOS.put(InAppBiddingDelegates.AD_VIEW_DID_DISMISS, "adViewDidDismissScreen called");
        inAppBidding_Delegates_iOS.put(InAppBiddingDelegates.AD_VIEW_WILL_LEAVE, "adViewWillLeaveApplication called");

        inAppBidding_Delegates_iOS.put(InAppBiddingDelegates.AD_VIEW_DID_LOAD, "adViewDidLoadAd called");
        inAppBidding_Delegates_iOS.put(AD_VIEW_DID_RECORD_IMPRESSION, "didRecordImpression called");
        inAppBidding_Delegates_iOS.put(InAppBiddingDelegates.AD_VIEW_DID_FAIL, "adViewDidFail called");
        inAppBidding_Delegates_iOS.put(InAppBiddingDelegates.AD_PRESENT_MODAL_VIEW, "willPresentModalView called");
        inAppBidding_Delegates_iOS.put(InAppBiddingDelegates.AD_WILL_PRESENT_SCREEN, "willPresentScreen called");
        inAppBidding_Delegates_iOS.put(InAppBiddingDelegates.AD_WILL_DISMISS_SCREEN, "willDismissScreen called");
        inAppBidding_Delegates_iOS.put(InAppBiddingDelegates.AD_DID_DISMISS_SCREEN, "didDismissScreen called");
        inAppBidding_Delegates_iOS.put(InAppBiddingDelegates.AD_DISMISS_MODAL_VIEW, "didDismissModalView called");
        inAppBidding_Delegates_iOS.put(InAppBiddingDelegates.AD_WILL_LEAVE_APP, "willLeaveApplication called");
        inAppBidding_Delegates_iOS.put(InAppBiddingDelegates.AD_CONTROLLER_FOR_PRESENTING_MODAL_VIEW, "viewControllerForPresentingModalView called");

        inAppBidding_Delegates_iOS.put(InAppBiddingDelegates.INTERSTITIAL_DID_LOAD, "interstitialDidLoadAd called");
        inAppBidding_Delegates_iOS.put(InAppBiddingDelegates.INTERSTITIAL_DID_FAIL, "interstitialDidFail called");
        inAppBidding_Delegates_iOS.put(InAppBiddingDelegates.INTERSTITIAL_WILL_APPEAR, "interstitialWillAppear called");
        inAppBidding_Delegates_iOS.put(InAppBiddingDelegates.INTERSTITIAL_DID_APPEAR, "interstitialDidAppear called");
        inAppBidding_Delegates_iOS.put(InAppBiddingDelegates.INTERSTITIAL_WILL_DISAPPEAR, "interstitialWillDisappear called");
        inAppBidding_Delegates_iOS.put(InAppBiddingDelegates.INTERSTITIAL_DID_DISAPPEAR, "interstitialDidDisappear called");
        inAppBidding_Delegates_iOS.put(InAppBiddingDelegates.INTERSTITIAL_DID_EXPIRE, "interstitialDidExpire called");
        inAppBidding_Delegates_iOS.put(InAppBiddingDelegates.INTERSTITIAL_DID_RECEIVED_TAP, "interstitialDidReceiveTapEvent called");

        inAppBidding_Delegates_iOS.put(INTERSTITIAL_DID_FAIL_TO_PRESENT_FULLSCREEN, "adDidFailToPresentFullScreenContentWithError called");
        inAppBidding_Delegates_iOS.put(INTERSTITIAL_DID_RECEIVE_BUTTON, "adDidReceiveButton called");
        inAppBidding_Delegates_iOS.put(INTERSTITIAL_DID_FAIL_TO_RECEIVE_BUTTON, "adDidFailToReceiveButton called");
        inAppBidding_Delegates_iOS.put(INTERSTITIAL_DID_PRESENT_FULLSCREEN, "adDidPresentFullScreenContent called");
        inAppBidding_Delegates_iOS.put(INTERSTITIAL_WILL_DISMISS_FULLSCREEN, "adWillDismissFullScreenContent called");
        inAppBidding_Delegates_iOS.put(INTERSTITIAL_DID_DISMISS_FULLSCREEN, "adDidDismissFullScreenContent called");
        inAppBidding_Delegates_iOS.put(INTERSTITIAL_DID_RECORD_IMPRESSION, "adDidRecordImpression called");


        inAppBidding_Delegates_iOS.put(InAppBiddingDelegates.INTERSTITIAL_DID_RECEIVED, "interstitialDidReceiveAd called");
        inAppBidding_Delegates_iOS.put(InAppBiddingDelegates.INTERSTITIAL_DID_FAIL_TO_RECEIVE, "interstitialDidFailToReceiveAd called");
        inAppBidding_Delegates_iOS.put(InAppBiddingDelegates.INTERSTITIAL_WILL_PRESENT_AD, "interstitialWillPresentAd called");
        inAppBidding_Delegates_iOS.put(InAppBiddingDelegates.INTERSTITIAL_WILL_PRESENT, "interstitialWillPresent called");
        inAppBidding_Delegates_iOS.put(InAppBiddingDelegates.INTERSTITIAL_DID_PRESENT, "interstitialDidPresent called");
        inAppBidding_Delegates_iOS.put(InAppBiddingDelegates.INTERSTITIAL_DID_DISMISS_AD, "interstitialDidDismissAd called");
        inAppBidding_Delegates_iOS.put(InAppBiddingDelegates.INTERSTITIAL_DID_DISMISS, "interstitialDidDismiss called");
        inAppBidding_Delegates_iOS.put(InAppBiddingDelegates.INTERSTITIAL_WILL_DISMISS, "interstitialWillDismiss called");
        inAppBidding_Delegates_iOS.put(InAppBiddingDelegates.INTERSTITIAL_WILL_LEAVE_APP, "interstitialWillLeaveApplication called");
        inAppBidding_Delegates_iOS.put(InAppBiddingDelegates.INTERSTITIAL_DID_CLICK, "interstitialDidClickAd called");

        inAppBidding_Delegates_iOS.put(InAppBiddingDelegates.REWARDED_AD_DID_RECEIVE, "rewardedAdDidReceiveAd called");
        inAppBidding_Delegates_iOS.put(InAppBiddingDelegates.REWARDED_AD_DID_FAIL_TO_RECEIVE, "rewardedAdDidFailToReceiveAd called");
        inAppBidding_Delegates_iOS.put(InAppBiddingDelegates.REWARDED_AD_WILL_PRESENT, "rewardedAdWillPresentAd called");
        inAppBidding_Delegates_iOS.put(InAppBiddingDelegates.REWARDED_AD_DID_DISMISS, "rewardedAdDidDismissAd called");
        inAppBidding_Delegates_iOS.put(InAppBiddingDelegates.REWARDED_AD_WILL_LEAVE_APP, "rewardedAdWillLeaveApplication called");
        inAppBidding_Delegates_iOS.put(InAppBiddingDelegates.REWARDED_AD_DID_CLICK, "rewardedAdDidClickAd called");
        inAppBidding_Delegates_iOS.put(InAppBiddingDelegates.REWARDED_AD_USER_DID_EARN_REWARD, "rewardedAdUserDidEarnReward called");

        inAppBidding_Delegates_iOS.put(InAppBiddingDelegates.REWARDED_VIDEO_AD_DID_LOAD, "rewardedVideoAdDidLoad called");
        inAppBidding_Delegates_iOS.put(InAppBiddingDelegates.REWARDED_VIDEO_AD_DID_FAIL, "rewardedVideoAdDidFailToLoad called");
        inAppBidding_Delegates_iOS.put(InAppBiddingDelegates.REWARDED_VIDEO_AD_WILL_PRESENT, "rewardedAdWillPresent called");
        inAppBidding_Delegates_iOS.put(InAppBiddingDelegates.REWARDED_VIDEO_AD_DID_PRESENT, "rewardedAdDidPresent called");
        inAppBidding_Delegates_iOS.put(InAppBiddingDelegates.REWARDED_VIDEO_AD_WILL_DISMISS, "rewardedAdWillDismiss called");
        inAppBidding_Delegates_iOS.put(InAppBiddingDelegates.REWARDED_VIDEO_AD_DID_DISMISS, "rewardedAdDidDismiss called");
        inAppBidding_Delegates_iOS.put(InAppBiddingDelegates.REWARDED_VIDEO_AD_DID_EXPIRE, "rewardedVideoAdDidExpire called");
        inAppBidding_Delegates_iOS.put(InAppBiddingDelegates.REWARDED_VIDEO_AD_DID_TAP, "rewardedVideoAdDidReceiveTapEvent called");
        inAppBidding_Delegates_iOS.put(InAppBiddingDelegates.REWARDED_VIDEO_AD_SHOULD_REWARD, "rewardedVideoAdShouldReward called");


        inAppBidding_Delegates_iOS.put(FETCH_DEMAND, "fetchDemand success");
        inAppBidding_Delegates_iOS.put(GET_NATIVE_AD, "getNativeAd success");
        inAppBidding_Delegates_iOS.put(NATIVE_AD_DID_CLICK, "adWasClicked called");
        inAppBidding_Delegates_iOS.put(NATIVE_AD_DID_LOG_IMPRESSION, "adDidLogImpression called");
        inAppBidding_Delegates_iOS.put(CUSTOM_NATIVE_AD_REQUEST_SUCCESS, "custom ad request successful");
        inAppBidding_Delegates_iOS.put(CUSTOM_NATIVE_AD_PRIMARY_WIN, "onPrimaryAdWin called (custom)");
        inAppBidding_Delegates_iOS.put(NATIVE_AD_PRIMARY_WIN, "onPrimaryAdWin called");
        inAppBidding_Delegates_iOS.put(NATIVE_AD_DID_TRACK_IMPRESSION, "nativeAdDidTrackImpression called");
        inAppBidding_Delegates_iOS.put(NATIVE_AD_WILL_LEAVE_APPLICATION, "nativeAdWillLeaveApplication called");
        inAppBidding_Delegates_iOS.put(NATIVE_AD_DID_DISMISS_MODAL, "nativeAdDidDismissModal called");
        inAppBidding_Delegates_iOS.put(UNIFIED_NATIVE_AD_PRIMARY_WIN, "onPrimaryAdWin called (unified)");
        inAppBidding_Delegates_iOS.put(UNIFIED_CUSTOM_AD_REQUEST_SUCCESS, "unified ad request successful");
        inAppBidding_Delegates_iOS.put(NATIVE_AD_DID_LOAD, "onNativeAdLoaded called");
        inAppBidding_Delegates_iOS.put(LOADER_DID_RECEIVE_BUTTON, "adLoaderDidReceiveAdButton called");
        inAppBidding_Delegates_iOS.put(LOADER_DID_FINISH_LOADING_BUTTON, "adLoaderDidFinishLoadingButton called");

    }

    private static final Map<InAppBiddingDelegates, String> inAppBidding_Delegates_Android;

    static {
        inAppBidding_Delegates_Android = new HashMap<>();
        inAppBidding_Delegates_Android.put(InAppBiddingDelegates.ON_AD_LOADED, "btnAdLoaded");
        inAppBidding_Delegates_Android.put(InAppBiddingDelegates.ON_AD_FAILED, "btnAdFailed");
        inAppBidding_Delegates_Android.put(InAppBiddingDelegates.ON_AD_DISPLAYED, "btnAdDisplayed");
        inAppBidding_Delegates_Android.put(ON_AD_IMPRESSION, "btnAdImpression");
        inAppBidding_Delegates_Android.put(ON_AD_OPENED, "btnAdOpened");
        inAppBidding_Delegates_Android.put(InAppBiddingDelegates.ON_AD_CLICKED, "btnAdClicked");
        inAppBidding_Delegates_Android.put(InAppBiddingDelegates.ON_AD_SHOWED, "btnAdShowed");
        inAppBidding_Delegates_Android.put(InAppBiddingDelegates.ON_AD_CLOSED, "btnAdClosed");

        inAppBidding_Delegates_Android.put(ON_NATIVE_FETCH_DEMAND_SUCCESS, "btnFetchDemandResultSuccess");
        inAppBidding_Delegates_Android.put(ON_NATIVE_GET_NATIVE_AD_SUCCESS, "btnGetNativeAdResultSuccess");
        inAppBidding_Delegates_Android.put(NATIVE_AD_DID_LOAD, "btnNativeAdLoaded");
        inAppBidding_Delegates_Android.put(NATIVE_AD_DID_LOG_IMPRESSION, "btnAdEventImpression");
        inAppBidding_Delegates_Android.put(CUSTOM_NATIVE_AD_REQUEST_SUCCESS, "btnCustomAdRequestSuccess");
        inAppBidding_Delegates_Android.put(UNIFIED_CUSTOM_AD_REQUEST_SUCCESS, "btnUnifiedRequestSuccess");
        inAppBidding_Delegates_Android.put(CUSTOM_NATIVE_AD_PRIMARY_WIN, "btnPrimaryAdWinCustom");
        inAppBidding_Delegates_Android.put(UNIFIED_NATIVE_AD_PRIMARY_WIN, "btnPrimaryAdWinUnified");

        inAppBidding_Delegates_Android.put(InAppBiddingDelegates.ON_BANNER_LOADED, "btnAdDidLoad");
        inAppBidding_Delegates_Android.put(InAppBiddingDelegates.ON_BANNER_EXPANDED, "btnAdExpanded");
        inAppBidding_Delegates_Android.put(InAppBiddingDelegates.ON_AD_COLLAPSED, "btnAdCollapsed");
        inAppBidding_Delegates_Android.put(InAppBiddingDelegates.ON_INTERSTITIAL_DISMISSED, "btnAdDismissed");

        inAppBidding_Delegates_Android.put(InAppBiddingDelegates.ON_REWARDED_STARTED, "btnAdVideoStarted");
        inAppBidding_Delegates_Android.put(InAppBiddingDelegates.ON_REWARDED_PLAYBACK_ERROR, "btnAdVideoPlaybackError");
        inAppBidding_Delegates_Android.put(InAppBiddingDelegates.ON_REWARDED_COMPLETED, "btnAdCompleted");

        inAppBidding_Delegates_Android.put(INTERSTITIAL_DID_RECEIVED, "btnAdLoaded");
        inAppBidding_Delegates_Android.put(INTERSTITIAL_WILL_PRESENT_AD, "btnAdDisplayed");
        inAppBidding_Delegates_Android.put(INTERSTITIAL_DID_DISMISS_AD, "btnAdClosed");
        inAppBidding_Delegates_Android.put(INTERSTITIAL_DID_LOAD, "btnAdDidLoad");
        inAppBidding_Delegates_Android.put(INTERSTITIAL_WILL_APPEAR, "btnInterstitialShown");
        inAppBidding_Delegates_Android.put(INTERSTITIAL_DID_DISAPPEAR, "btnAdDismissed");
        inAppBidding_Delegates_Android.put(INTERSTITIAL_DID_CLICK, "btnAdClicked");
        inAppBidding_Delegates_Android.put(INTERSTITIAL_DID_FAIL_TO_RECEIVE, "btnAdFailed");

    }

    public enum InAppBiddingDelegates {
        AD_VIEW_RECEIVED,
        AD_VIEW_DID_FAIL_LOAD,
        AD_VIEW_PRESENT,
        AD_VIEW_DID_DISMISS,
        AD_VIEW_WILL_LEAVE,

        AD_VIEW_DID_LOAD,
        AD_VIEW_DID_RECORD_IMPRESSION,
        AD_VIEW_DID_FAIL,
        AD_PRESENT_MODAL_VIEW,
        AD_WILL_PRESENT_SCREEN,
        AD_WILL_DISMISS_SCREEN,
        AD_DID_DISMISS_SCREEN,
        AD_DISMISS_MODAL_VIEW,
        AD_WILL_LEAVE_APP,
        AD_CONTROLLER_FOR_PRESENTING_MODAL_VIEW,

        INTERSTITIAL_DID_RECEIVED,
        INTERSTITIAL_DID_FAIL_TO_RECEIVE,
        INTERSTITIAL_WILL_PRESENT_AD,
        INTERSTITIAL_DID_DISMISS_AD,
        INTERSTITIAL_WILL_LEAVE_APP,
        INTERSTITIAL_DID_CLICK,

        INTERSTITIAL_DID_RECEIVE_BUTTON,
        INTERSTITIAL_DID_FAIL_TO_RECEIVE_BUTTON,
        INTERSTITIAL_DID_FAIL_TO_PRESENT_FULLSCREEN,
        INTERSTITIAL_DID_PRESENT_FULLSCREEN,
        INTERSTITIAL_DID_PRESENT,
        INTERSTITIAL_DID_DISMISS,
        INTERSTITIAL_WILL_DISMISS,
        INTERSTITIAL_WILL_PRESENT,
        INTERSTITIAL_DID_DISMISS_FULLSCREEN,
        INTERSTITIAL_WILL_DISMISS_FULLSCREEN,
        INTERSTITIAL_DID_RECORD_IMPRESSION,

        INTERSTITIAL_DID_LOAD,
        INTERSTITIAL_WILL_APPEAR,
        INTERSTITIAL_DID_FAIL,
        INTERSTITIAL_DID_APPEAR,
        INTERSTITIAL_WILL_DISAPPEAR,
        INTERSTITIAL_DID_DISAPPEAR,
        INTERSTITIAL_DID_EXPIRE,
        INTERSTITIAL_DID_RECEIVED_TAP,

        REWARDED_AD_DID_RECEIVE,
        REWARDED_AD_DID_FAIL_TO_RECEIVE,
        REWARDED_AD_WILL_PRESENT,
        REWARDED_AD_DID_DISMISS,
        REWARDED_AD_WILL_LEAVE_APP,
        REWARDED_AD_DID_CLICK,
        REWARDED_AD_USER_DID_EARN_REWARD,

        REWARDED_VIDEO_AD_DID_LOAD,
        REWARDED_VIDEO_AD_DID_FAIL,
        REWARDED_VIDEO_AD_WILL_PRESENT,
        REWARDED_VIDEO_AD_DID_PRESENT,
        REWARDED_VIDEO_AD_WILL_DISMISS,
        REWARDED_VIDEO_AD_DID_DISMISS,
        REWARDED_VIDEO_AD_DID_EXPIRE,
        REWARDED_VIDEO_AD_DID_TAP,
        REWARDED_VIDEO_AD_SHOULD_REWARD,

        ON_AD_LOADED,
        ON_AD_FAILED,
        ON_AD_DISPLAYED,
        ON_AD_CLICKED,
        ON_AD_SHOWED,
        ON_AD_CLOSED,

        ON_AD_IMPRESSION,
        ON_AD_OPENED,

        ON_NATIVE_FETCH_DEMAND_SUCCESS,
        ON_NATIVE_GET_NATIVE_AD_SUCCESS,

        ON_BANNER_LOADED,
        ON_BANNER_EXPANDED,
        ON_AD_COLLAPSED,
        ON_INTERSTITIAL_DISMISSED,
        ON_REWARDED_STARTED,
        ON_REWARDED_PLAYBACK_ERROR,
        ON_REWARDED_COMPLETED,

        FETCH_DEMAND,
        GET_NATIVE_AD,
        NATIVE_AD_DID_CLICK,
        NATIVE_AD_DID_LOG_IMPRESSION,
        CUSTOM_NATIVE_AD_REQUEST_SUCCESS,
        CUSTOM_NATIVE_AD_PRIMARY_WIN,
        NATIVE_AD_PRIMARY_WIN,
        UNIFIED_NATIVE_AD_PRIMARY_WIN,
        UNIFIED_CUSTOM_AD_REQUEST_SUCCESS,
        NATIVE_AD_DID_LOAD,
        NATIVE_AD_DID_TRACK_IMPRESSION,
        NATIVE_AD_WILL_LEAVE_APPLICATION,
        NATIVE_AD_DID_DISMISS_MODAL,
        LOADER_DID_RECEIVE_BUTTON,
        LOADER_DID_FINISH_LOADING_BUTTON,
        ;

    }

    private IOSDriver driver;
    private AndroidDriver androidDriver;

    // Public Properties

    public enum InAppBiddingEvents {
        AUCTION,
        IMPRESSION,
        MRC50,
        MRC100,
        CLICK_ROOT_URL,
        CLICK_DATA_FALLBACK,
        CLICK_DATA_URL,
        CLICK_DEEPLINK,
        WIN_PREBID,
        GAM_GAMPAD,
        GAM_G_DOUBLECLICK,
        MOPUB_AD,
        MOPUB_IMP,
        ADMOB_PAGEAD_INTERACTION,
        ADMOB_MADS_GMA,
        VIDEO_REQUEST,
        VIDEO_REWARDED_REQUEST,
        VIDEO_IMPRESSION,
        VIDEO_CLICK,
        VIDEO_CREATIVEVIEW,
        VIDEO_START,
        VIDEO_FIRSTQUARTILE,
        VIDEO_MIDPOINT,
        VIDEO_THIRDQUARTILE,
        VIDEO_COMPLETE,
        VIDEO_PAUSE,
        VIDEO_RESUME,
        VIDEO_CLOSE,
    }

    public InAppBiddingHomePageImpl homePage;

    public static String getConfigFileForContext(ITestContext itc) {
        final Map<String, String> testParams = itc.getCurrentXmlTest().getAllParameters();
        final String platform = testParams.getOrDefault("prebidTestPlatform", "UNKNOWN");

        switch (platform) {
            case "iOS":
                return PROPERTIES_FILE_PATH_IOS;
            case "Android":
                return PROPERTIES_FILE_PATH_ANDROID;
            case "Android-Server":
                return PROPERTIES_FILE_PATH_ANDROID_PREBID_SERVER;
            default:
                return "There is NO config for such prebidTestPlatform";
        }
    }

    // Public Methods

    /**
     * Constructor with possibility to use BMP
     *
     * @param name test name which is later used as name in SauceLabs, e.g. iOS_SDK_UI
     * @throws IOException when propertiesFilePath can't be loaded
     */
    public InAppBiddingTestEnvironment(String name, ITestContext itc, Set<TrafficInspectorKind> trafficInspectors) throws IOException {
        super(name, InAppBiddingTestEnvironment.getConfigFileForContext(itc), trafficInspectors);

        final Platform platform = capabilities.getPlatform();
        if (platform == Platform.IOS) {
            driver = new IOSDriver(new URL(urlAdress), capabilities);
            installBmpCertificate();
            homePage = new InAppBiddingHomePageIOS(driver);
        } else if (platform == Platform.ANDROID) {
            androidDriver = new AndroidDriver(new URL(urlAdress), capabilities);
            homePage = new InAppBiddingHomePageAndroid(androidDriver, "xpath");
        }

        this.trafficInspectors = trafficInspectors;
        bmp.newHar();
        bmp.setHarCaptureTypes(CaptureType.getAllContentCaptureTypes());


        homePage.rotatePortrait();
    }

    private void installBmpCertificate() throws IOException {
        Map<String, Object> args = new HashMap<>();

        byte[] byteContent = Files.readAllBytes(new File(
                "src/test/resources/appium/bmp/iosBMPCertificate.pem").toPath());
        args.put("content", Base64.getEncoder().encodeToString(byteContent));
        driver.executeScript("mobile: installCertificate", args);
    }


    /**
     * Constructor with possibility to use BMP and CustomLineArguments
     *
     * @throws IOException when propertiesFilePath can't be loaded
     */
    public InAppBiddingTestEnvironment(String name, ITestContext itc, Set<TrafficInspectorKind> trafficInspectors, String commandLineArguments) throws IOException {
        super(name, InAppBiddingTestEnvironment.getConfigFileForContext(itc), trafficInspectors);
        final Platform platform = capabilities.getPlatform();
        capabilities.setCapability("noReset", false);
        if (platform == Platform.IOS) {
            capabilities.setCapability("processArguments", commandLineArguments);
            driver = new IOSDriver(new URL(urlAdress), capabilities);
            installBmpCertificate();
            homePage = new InAppBiddingHomePageIOS(driver);
        } else if (platform == Platform.ANDROID) {
            capabilities.setCapability("optionalIntentArguments", commandLineArguments);
            androidDriver = new AndroidDriver(new URL(urlAdress), capabilities);
            homePage = new InAppBiddingHomePageAndroid(androidDriver, "xpath");
        }

        this.trafficInspectors = trafficInspectors;
        bmp.newHar();
        bmp.setHarCaptureTypes(CaptureType.getAllContentCaptureTypes());


        homePage.rotatePortrait();
    }

    // TestEnvironment

    @Override
    public void teardown() throws IOException {
        super.teardown();

        if (driver != null) {
            driver.quit();
        } else if (androidDriver != null) {
            androidDriver.quit();
        }
    }

    // Methods
    public void waitForEvent(InAppBiddingEvents event, int expectedOccurrences, int timeout) throws InterruptedException, TimeoutException {
        waitForEventBmp(event, expectedOccurrences, timeout);

    }

    public void waitForEvent(InAppBiddingEvents event, int expectedOccurrences, int timeout, int delay) throws InterruptedException, TimeoutException {
         waitForEventBmp(event, expectedOccurrences, timeout, delay);
    }



    public String getEvent(InAppBiddingEvents event) {
        return inAppBidding_Events.get(event);
    }

    public static String getDelegate(InAppBiddingDelegates delegate, String platformName) {
        String res = "";
        if (platformName.contains("ios")) {
            res = inAppBidding_Delegates_iOS.get(delegate);
        } else {
            res = inAppBidding_Delegates_Android.get(delegate);
        }
        return res;
    }

    public void validateEventRequest(InAppBiddingEvents event, JSONObject jsonValidTemplate) {
        String inAppBiddingEvent_value = getEvent(event);
        String inAppBiddingEvent_name = event.toString().toLowerCase();

        switch (inAppBiddingEvent_name) {
            case "auction":

                if (trafficInspectors.contains(TrafficInspectorKind.MOB_PROXY)) {
                    RequestValidator.validateInAppBiddingRequest(bmp.getHar(), inAppBiddingEvent_value, jsonValidTemplate);
                } else {
                    try {
                        RequestValidator.validateInAppBiddingRequest(bmp.getHar(), inAppBiddingEvent_value, jsonValidTemplate);
                    } catch (NullPointerException | IndexOutOfBoundsException exception) {
                        RequestValidator.validateAuctionRequest(logValidator.getLogs(inAppBiddingEvent_value), jsonValidTemplate);
                    }
                }
                break;
            case "cache":
                System.out.println("This is a CACHE event");
                break;
        }
    }

    public void validateEventResponse(InAppBiddingEvents event, JSONObject jsonValidTemplate) {
        String inAppBiddingEvent_value = getEvent(event);
        String inAppBiddingEvent_name = event.toString().toLowerCase();

        switch (inAppBiddingEvent_name) {
            case "auction":

                if (trafficInspectors.contains(TrafficInspectorKind.MOB_PROXY)) {
                    RequestValidator.validateInAppBiddingResponse(bmp.getHar(), inAppBiddingEvent_value, jsonValidTemplate);
                } else {
                    try {
                        RequestValidator.validateInAppBiddingResponse(bmp.getHar(), inAppBiddingEvent_value, jsonValidTemplate);
                    } catch (NullPointerException | IndexOutOfBoundsException exception) {
                        RequestValidator.validateAuctionResponse(logValidator.getLogs(inAppBiddingEvent_value), jsonValidTemplate);
                    }
                }
                break;
            case "cache":
                System.out.println("This is a CACHE event");
                break;
        }
    }

    // Private Methods
    private void waitForEventBmp(InAppBiddingEvents event, int expectedOccurrences, int timeout) throws InterruptedException, TimeoutException {
        bmp.waitForEvent(getEvent(event), expectedOccurrences, timeout);
    }

    private void waitForEventBmp(InAppBiddingEvents event, int expectedOccurrences, int timeout, int delay) throws InterruptedException, TimeoutException {
        bmp.waitForEvent(getEvent(event), expectedOccurrences, timeout, delay);
    }

}