package appium.common;

import org.json.JSONObject;
import utils.FileUtils;

import java.util.HashMap;

public class InAppTemplatesInit implements InAppAdNamesImpl {

    //REQUEST TEMPLATES
    private final static String TEMPLATE_BANNER_320x50_LIVERAMP = "appium/custom_requests/ortb/%s/liveRampATS.json";
    private final static String TEMPLATE_BANNER_320x50_CUSTOM_ORTB = "appium/custom_requests/ortb/%s/Custom_ORTB.json";
    private final static String TEMPLATE_BANNER_320x50_CCPA_TRUE = "appium/custom_requests/ortb/%s/AUCTION_CustomParamCCPA_TRUE.json";
    private final static String TEMPLATE_BANNER_320x50_CCPA_FALSE = "appium/custom_requests/ortb/%s/AUCTION_CustomParamCCPA_FALSE.json";
    private final static String TEMPLATE_BANNER_320x50_GDPR0_CONS_NULL = "appium/custom_requests/ortb/%s/AUCTION_TCFv1_TRUE_GDPR_0_CONS_NULL.json";
    private final static String TEMPLATE_BANNER_320x50_GDPR1_CONS_TRUE = "appium/custom_requests/ortb/%s/AUCTION_TCFv1_TRUE_GDPR_1_CONS_TRUE.json";
    private final static String TEMPLATE_BANNER_320x50_ATTS_1 = "appium/inAppBidding_requests/%s/inApp_auction_320x50_atts_1.json";
    private final static String TEMPLATE_BANNER_320x50_ATTS_2 = "appium/inAppBidding_requests/%s/inApp_auction_320x50_atts_2.json";
    private final static String TEMPLATE_BANNER_320x50_ATTS_3 = "appium/inAppBidding_requests/%s/inApp_auction_320x50_atts_3.json";
    private final static String TEMPLATE_BANNER_320x50_SKADN = "appium/inAppBidding_requests/%s/inApp_auction_320x50_skadn.json";

    private final static String TEMPLATE_BANNER_320x50 = "appium/inAppBidding_requests/%s/inApp_auction_320x50.json";
    private final static String TEMPLATE_BANNER_320x50_CACHE = "appium/inAppBidding_requests/%s/caching/inApp_auction_320x50.json";
    private final static String TEMPLATE_BANNER_320x50_ORIGINAL = "appium/inAppBidding_requests/%s/original_api/inApp_auction_320x50_original.json";
    private final static String TEMPLATE_BANNER_320x50_ADDITIONAL_PARAMS = "appium/inAppBidding_requests/%s/additional_params/inApp_auction_320x50.json";
    private final static String TEMPLATE_BANNER_320x50_REAL_DEVICE = "appium/inAppBidding_requests/%s/inApp_auction_320x50_real_device.json";

    private final static String TEMPLATE_BANNER_300x250 = "appium/inAppBidding_requests/%s/inApp_auction_300x250.json";
    private final static String TEMPLATE_BANNER_300x250_ORIGINAL = "appium/inAppBidding_requests/%s/original_api/inApp_auction_300x250.json";
    private final static String TEMPLATE_BANNER_728x90 = "appium/inAppBidding_requests/%s/inApp_auction_728x90.json";
    private final static String TEMPLATE_BANNER_728x90_ORIGINAL = "appium/inAppBidding_requests/%s/original_api/inApp_auction_728x90.json";
    private final static String TEMPLATE_BANNER_MULTISIZE = "appium/inAppBidding_requests/%s/inApp_auction_ms.json";
    private final static String TEMPLATE_BANNER_MULTISIZE_ORIGINAL = "appium/inAppBidding_requests/%s/original_api/inApp_auction_ms.json";

    private final static String TEMPLATE_INTERSTITIAL_320x480 = "appium/inAppBidding_requests/%s/inApp_auction_320x480.json";
    private final static String TEMPLATE_INTERSTITIAL_320x480_ORIGINAL = "appium/inAppBidding_requests/%s/original_api/inApp_auction_320x480_original.json";
    private final static String TEMPLATE_INTERSTITIAL_320x480_REAL_DEVICE = "appium/inAppBidding_requests/%s/inApp_auction_320x480_real_device.json";
    private final static String TEMPLATE_INTERSTITIAL_MULTIFORMAT = "appium/inAppBidding_requests/%s/inApp_auction_mf.json";
    private final static String TEMPLATE_INTERSTITIAL_MULTIFORMAT_REAL_DEVICE = "appium/inAppBidding_requests/%s/inApp_auction_mf_real_device.json";

    private final static String TEMPLATE_MRAID_FULLSCREEN = "appium/inAppBidding_requests/%s/inApp_mraid_fullscreen_auc.json";
    private final static String TEMPLATE_MRAID_VIDEO_INTERSTITIAL = "appium/inAppBidding_requests/%s/inApp_mraid_video_int_auc.json";

    private final static String TEMPLATE_VIDEO_INTERSTITIAL_320x480 = "appium/inAppBidding_requests/%s/inApp_video_int_auc_320x480.json";
    private final static String TEMPLATE_VIDEO_INTERSTITIAL_320x480_ORIGINAL = "appium/inAppBidding_requests/%s/original_api/inApp_video_int_auc_320x480_original.json";
    private final static String TEMPLATE_VIDEO_INTERSTITIAL_320x480_CACHE = "appium/inAppBidding_requests/%s/caching/inApp_video_int_auc_320x480.json";
    private final static String TEMPLATE_VIDEO_INTERSTITIAL_320x480_ADDITIONAL_PARAMS = "appium/inAppBidding_requests/%s/additional_params/inApp_video_int_auc_320x480.json";
    private final static String TEMPLATE_VIDEO_INTERSTITIAL_320x480_REAL_DEVICE = "appium/inAppBidding_requests/%s/inApp_video_int_auc_320x480_real_device.json";
    private final static String TEMPLATE_VIDEO_OUTSTREAM_300x250 = "appium/inAppBidding_requests/%s/inApp_video_outstream_auc_300x250.json";
    private final static String TEMPLATE_VIDEO_OUTSTREAM_300x250_ORIGINAL = "appium/inAppBidding_requests/%s/original_api/inApp_video_outstream_auc_300x250.json";
    private final static String TEMPLATE_VIDEO_INSTREAM_300x250_ORIGINAL = "appium/inAppBidding_requests/%s/original_api/inApp_video_instream_auc_300x250_original.json";
    private final static String TEMPLATE_VIDEO_FEED_300x250 = "appium/inAppBidding_requests/%s/inApp_video_feed_auc_300x250.json";
    private final static String TEMPLATE_VIDEO_REWARDED_320x480 = "appium/inAppBidding_requests/%s/inApp_video_rew_auc_320x480.json";
    private final static String TEMPLATE_VIDEO_REWARDED_320x480_ORIGINAL = "appium/inAppBidding_requests/%s/original_api/inApp_video_rew_auc_320x480.json";
    private final static String TEMPLATE_VIDEO_REWARDED_320x480_REAL_DEVICE = "appium/inAppBidding_requests/%s/inApp_video_rew_auc_320x480_real_device.json";

    //NATIVE TEMPLATES
    private final static String REQUEST_TEMPLATE_NATIVE = "appium/inAppBidding_requests/%s/inApp_native_req.json";
    private final static String REQUEST_TEMPLATE_NATIVE_ORIGINAL = "appium/inAppBidding_requests/%s/original_api/inApp_native_req.json";
    private final static String REQUEST_TEMPLATE_NATIVE_REAL_DEVICE = "appium/inAppBidding_requests/%s/inApp_native_req_real_device.json";

    //RESPONSES
    private final static String RESPONSE_TEMPLATE_NATIVE = "appium/inAppBidding_responses/%s/inApp_native_res.json";
    private final static String RESPONSE_TEMPLATE_VIDEO_INTERSTITIAL_AD_CONFIGURATION = "appium/inAppBidding_responses/inApp_video_interstitial_320x480_with_ad_configuration.json";
    private final static String RESPONSE_TEMPLATE_VIDEO_REWARDED_AD_CONFIGURATION = "appium/inAppBidding_responses/inApp_video_rewarded_320x480_with_ad_configuration.json";

    public static JSONObject getAuctionRequestTemplate(String prebidAd, String platformName) {

        final String filePath = getFilePathAuctionRequest(prebidAd, platformName);
        System.out.println("TEMPLATE PATH IS ==> " + filePath + "\n");

        final String testJSON = FileUtils.getJsonStringFromResourceFile(filePath);

        return new JSONObject(testJSON);
    }
    public static JSONObject getAuctionRequestWithCacheTemplate(String prebidAd, String platformName) {

        final String filePath = getFilePathAuctionRequestWithCache(prebidAd, platformName);
        System.out.println("TEMPLATE PATH IS ==> " + filePath + "\n");

        final String testJSON = FileUtils.getJsonStringFromResourceFile(filePath);

        return new JSONObject(testJSON);
    }
    public static JSONObject getAuctionRequestWithAdditionalParams(String prebidAd, String platformName) {

        final String filePath = getFilePathAuctionRequestWithAdditionalParams(prebidAd, platformName);
        System.out.println("TEMPLATE PATH IS ==> " + filePath + "\n");

        final String testJSON = FileUtils.getJsonStringFromResourceFile(filePath);

        return new JSONObject(testJSON);
    }
    public static JSONObject getRealDeviceAuctionRequestTemplate(String prebidAd, String platformName) {

        final String filePath = getFilePathRealDeviceAuctionRequest(prebidAd, platformName);
        System.out.println("TEMPLATE PATH IS ==> " + filePath + "\n");

        final String testJSON = FileUtils.getJsonStringFromResourceFile(filePath);

        return new JSONObject(testJSON);
    }
    public static JSONObject getAuctionResponseTemplate(String prebidAd, String platformName) {
        final String filePath = getFilePathAuctionResponse(prebidAd, platformName);
        System.out.println("RESPONSE TEMPLATE PATH IS ==> " + filePath + "\n");
        final String testJSON = FileUtils.getJsonStringFromResourceFile(filePath);

        return new JSONObject(testJSON);
    }

    private static String getFilePathAuctionRequest(String prebidAd, String platformName) {
        String path = requestTemplates.get(prebidAd);
        return String.format(path, platformName);
    }
    private static String getFilePathAuctionRequestWithCache(String prebidAd, String platformName) {
        String path = requestWithCacheTemplates.get(prebidAd);
        return String.format(path, platformName);
    }
    private static String getFilePathAuctionRequestWithAdditionalParams(String prebidAd, String platformName) {
        String path = requestWithAdditionalParamsTemplates.get(prebidAd);
        return String.format(path, platformName);
    }
    private static String getFilePathRealDeviceAuctionRequest(String prebidAd, String platformName) {
        String path = realDeviceRequestTemplates.get(prebidAd);
        return String.format(path, platformName);
    }
    private static String getFilePathAuctionResponse(String prebidAd, String platformName) {
        String path = responseTemplates.get(prebidAd);
        return String.format(path, platformName);
    }


    private final static HashMap<String, String> requestTemplates = new HashMap<String, String>() {{
        put(BANNER_320x50_IN_APP, TEMPLATE_BANNER_320x50);
        put(BANNER_320x50_IN_APP_SCROLLABLE, TEMPLATE_BANNER_320x50);
        put(BANNER_320x50_IN_APP_VAST, TEMPLATE_BANNER_320x50);
        put(BANNER_320x50_IN_APP_DEEPLINK, TEMPLATE_BANNER_320x50);
        put(BANNER_320x50_NO_BID_IN_APP, TEMPLATE_BANNER_320x50);

        put(BANNER_320x50_ADMOB, TEMPLATE_BANNER_320x50);
        put(BANNER_320x50_NO_BID_ADMOB, TEMPLATE_BANNER_320x50);
        put(BANNER_320x50_ADMOB_RANDOM, TEMPLATE_BANNER_320x50);

        put(BANNER_320x50_MAX, TEMPLATE_BANNER_320x50);
        put(BANNER_320x50_NO_BID_MAX, TEMPLATE_BANNER_320x50);
        put(BANNER_320x50_MAX_RANDOM, TEMPLATE_BANNER_320x50);

        put(BANNER_320x50_GAM, TEMPLATE_BANNER_320x50);
        put(BANNER_320x50_GAM_ORIGINAL, TEMPLATE_BANNER_320x50_ORIGINAL);
        put(BANNER_320x50_GAM_AD, TEMPLATE_BANNER_320x50);
        put(BANNER_320x50_GAM_RANDOM, TEMPLATE_BANNER_320x50);
        put(BANNER_320x50_GAM_VANILLA, TEMPLATE_BANNER_320x50);
        put(BANNER_320x50_NO_BID_GAM_AD, TEMPLATE_BANNER_320x50);

        put(BANNER_300x250_IN_APP, TEMPLATE_BANNER_300x250);
        put(BANNER_300x250_GAM, TEMPLATE_BANNER_300x250);
        put(BANNER_300x250_GAM_ORIGINAL, TEMPLATE_BANNER_300x250_ORIGINAL);
        put(BANNER_300x250_ADMOB, TEMPLATE_BANNER_300x250);
        put(BANNER_300x250_MAX, TEMPLATE_BANNER_300x250);
        put(BANNER_ADAPTIVE_ADMOB, TEMPLATE_BANNER_MULTISIZE);
        put(BANNER_ADAPTIVE_MAX, TEMPLATE_BANNER_MULTISIZE);

        put(BANNER_728x90_IN_APP, TEMPLATE_BANNER_728x90);
        put(BANNER_728x90_GAM, TEMPLATE_BANNER_728x90);
        put(BANNER_728x90_GAM_ORIGINAL, TEMPLATE_BANNER_728x90_ORIGINAL);

        put(BANNER_MULTISIZE_IN_APP, TEMPLATE_BANNER_MULTISIZE);
        put(BANNER_MULTISIZE_GAM, TEMPLATE_BANNER_MULTISIZE);
        put(BANNER_MULTISIZE_GAM_ORIGINAL, TEMPLATE_BANNER_MULTISIZE_ORIGINAL);

        put(BANNER_SKADNETWORK, TEMPLATE_BANNER_320x50_SKADN);
        put(LIVE_RAMP_ATS, TEMPLATE_BANNER_320x50_LIVERAMP);
        put(ATTS_1, TEMPLATE_BANNER_320x50_ATTS_1);
        put(ATTS_2, TEMPLATE_BANNER_320x50_ATTS_2);
        put(ATTS_3, TEMPLATE_BANNER_320x50_ATTS_3);
        put(CUSTOM_USPRIVACY_CCPA_TRUE, TEMPLATE_BANNER_320x50_CCPA_TRUE);
        put(CUSTOM_OPENRTB, TEMPLATE_BANNER_320x50_CUSTOM_ORTB);
        put(CUSTOM_USPRIVACY_CCPA_FALSE, TEMPLATE_BANNER_320x50_CCPA_FALSE);
        put(CUSTOM_TCF_GDPR0_NO_CONSENT, TEMPLATE_BANNER_320x50_GDPR0_CONS_NULL);
        put(CUSTOM_TCF_GDPR1_CONSENT, TEMPLATE_BANNER_320x50_GDPR1_CONS_TRUE);
        put(CUSTOM_TCF_NO_GDPR_NO_CONSENT, TEMPLATE_BANNER_320x50_CCPA_FALSE);

        put(INTERSTITIAL_320x480_IN_APP, TEMPLATE_INTERSTITIAL_320x480);
        put(INTERSTITIAL_320x480_GAM, TEMPLATE_INTERSTITIAL_320x480);
        put(INTERSTITIAL_320x480_GAM_ORIGINAL, TEMPLATE_INTERSTITIAL_320x480_ORIGINAL);
        put(INTERSTITIAL_320x480_ADMOB, TEMPLATE_INTERSTITIAL_320x480);
        put(INTERSTITIAL_320x480_MAX, TEMPLATE_INTERSTITIAL_320x480);

        put(INTERSTITIAL_320x480_GAM_RANDOM, TEMPLATE_INTERSTITIAL_320x480);
        put(INTERSTITIAL_320x480_ADMOB_RANDOM, TEMPLATE_INTERSTITIAL_320x480);
        put(INTERSTITIAL_320x480_MAX_RANDOM, TEMPLATE_INTERSTITIAL_320x480);

        put(INTERSTITIAL_320x480_NO_BID_GAM, TEMPLATE_INTERSTITIAL_320x480);
        put(INTERSTITIAL_320x480_NO_BID_ADMOB, TEMPLATE_INTERSTITIAL_320x480);
        put(INTERSTITIAL_320x480_NO_BID_MAX, TEMPLATE_INTERSTITIAL_320x480);

        put(INTERSTITIAL_320x480_MULTISIZE_GAM, TEMPLATE_BANNER_MULTISIZE);

        put(INTERSTITIAL_MULTI_FORMAT_IN_APP, TEMPLATE_INTERSTITIAL_MULTIFORMAT);
        put(INTERSTITIAL_MULTI_FORMAT_GAM, TEMPLATE_INTERSTITIAL_MULTIFORMAT);
        put(INTERSTITIAL_MULTI_FORMAT_ADMOB, TEMPLATE_INTERSTITIAL_MULTIFORMAT);
        put(INTERSTITIAL_MULTI_FORMAT_MAX, TEMPLATE_INTERSTITIAL_MULTIFORMAT);


        put(VIDEO_INTERSTITIAL_320x480_IN_APP, TEMPLATE_VIDEO_INTERSTITIAL_320x480);
        put(VIDEO_INTERSTITIAL_320x480_IN_APP_WITH_AD_CONFIGURATION, TEMPLATE_VIDEO_INTERSTITIAL_320x480);

        put(VIDEO_INTERSTITIAL_320x480_ADMOB, TEMPLATE_VIDEO_INTERSTITIAL_320x480);
        put(VIDEO_INTERSTITIAL_320x480_ADMOB_WITH_AD_CONFIGURATION, TEMPLATE_VIDEO_INTERSTITIAL_320x480);
        put(VIDEO_INTERSTITIAL_320x480_ENDCARD, TEMPLATE_VIDEO_INTERSTITIAL_320x480);

        put(VIDEO_INTERSTITIAL_320x480_GAM, TEMPLATE_VIDEO_INTERSTITIAL_320x480);
        put(VIDEO_INTERSTITIAL_320x480_GAM_ORIGINAL, TEMPLATE_VIDEO_INTERSTITIAL_320x480_ORIGINAL);
        put(VIDEO_INTERSTITIAL_320x480_GAM_WITH_AD_CONFIGURATION, TEMPLATE_VIDEO_INTERSTITIAL_320x480);
        put(VIDEO_INTERSTITIAL_320x480_GAM_RANDOM, TEMPLATE_VIDEO_INTERSTITIAL_320x480);
        put(VIDEO_INTERSTITIAL_320x480_NO_BID_GAM_AD, TEMPLATE_VIDEO_INTERSTITIAL_320x480);


        put(VIDEO_INTERSTITIAL_320x480_ADMOB_RANDOM, TEMPLATE_VIDEO_INTERSTITIAL_320x480);
        put(VIDEO_INTERSTITIAL_320x480_NO_BID_ADMOB, TEMPLATE_VIDEO_INTERSTITIAL_320x480);

        put(VIDEO_INTERSTITIAL_320x480_MAX, TEMPLATE_VIDEO_INTERSTITIAL_320x480);
        put(VIDEO_INTERSTITIAL_320x480_MAX_WITH_AD_CONFIGURATION, TEMPLATE_VIDEO_INTERSTITIAL_320x480);
        put(VIDEO_INTERSTITIAL_320x480_MAX_RANDOM, TEMPLATE_VIDEO_INTERSTITIAL_320x480);
        put(VIDEO_INTERSTITIAL_320x480_NO_BID_MAX, TEMPLATE_VIDEO_INTERSTITIAL_320x480);


        put(VIDEO_REWARDED_320x480_IN_APP, TEMPLATE_VIDEO_REWARDED_320x480);
        put(VIDEO_REWARDED_320x480_IN_APP_WITH_AD_CONFIGURATION, TEMPLATE_VIDEO_REWARDED_320x480);


        put(VIDEO_REWARDED_320x480_ADMOB, TEMPLATE_VIDEO_REWARDED_320x480);
        put(VIDEO_REWARDED_320x480_ADMOB_WITH_AD_CONFIGURATION, TEMPLATE_VIDEO_REWARDED_320x480);
        put(VIDEO_REWARDED_320x480_NO_BID_ADMOB, TEMPLATE_VIDEO_REWARDED_320x480);
        put(VIDEO_REWARDED_320x480_ADMOB_RANDOM, TEMPLATE_VIDEO_REWARDED_320x480);

        put(VIDEO_REWARDED_320x480_MAX, TEMPLATE_VIDEO_REWARDED_320x480);
        put(VIDEO_REWARDED_320x480_MAX_WITH_AD_CONFIGURATION, TEMPLATE_VIDEO_REWARDED_320x480);
        put(VIDEO_REWARDED_320x480_NO_BID_MAX, TEMPLATE_VIDEO_REWARDED_320x480);
        put(VIDEO_REWARDED_320x480_MAX_RANDOM, TEMPLATE_VIDEO_REWARDED_320x480);

        put(VIDEO_REWARDED_320x480_GAM_METADATA, TEMPLATE_VIDEO_REWARDED_320x480);
        put(VIDEO_REWARDED_320x480_GAM_ORIGINAL, TEMPLATE_VIDEO_REWARDED_320x480_ORIGINAL);
        put(VIDEO_REWARDED_320x480_GAM_WITH_AD_CONFIGURATION, TEMPLATE_VIDEO_REWARDED_320x480);
        put(VIDEO_REWARDED_320x480_NO_BID_GAM, TEMPLATE_VIDEO_REWARDED_320x480);
        put(VIDEO_REWARDED_320x480_GAM_RANDOM, TEMPLATE_VIDEO_REWARDED_320x480);

        put(VIDEO_OUTSTREAM_IN_APP, TEMPLATE_VIDEO_OUTSTREAM_300x250);
        put(VIDEO_OUTSTREAM_FEED_IN_APP, TEMPLATE_VIDEO_FEED_300x250);
        put(VIDEO_OUTSTREAM_ENDCARD, TEMPLATE_VIDEO_OUTSTREAM_300x250);

        put(VIDEO_OUTSTREAM_GAM, TEMPLATE_VIDEO_OUTSTREAM_300x250);
        put(VIDEO_OUTSTREAM_GAM_ORIGINAL, TEMPLATE_VIDEO_OUTSTREAM_300x250_ORIGINAL);
        put(VIDEO_INSTREAM_GAM_ORIGINAL, TEMPLATE_VIDEO_INSTREAM_300x250_ORIGINAL);
        put(VIDEO_OUTSTREAM_FEED_GAM, TEMPLATE_VIDEO_FEED_300x250);
        put(VIDEO_OUTSTREAM_NO_BID_GAM_AD, TEMPLATE_VIDEO_OUTSTREAM_300x250);
        put(VIDEO_OUTSTREAM_GAM_RANDOM, TEMPLATE_VIDEO_OUTSTREAM_300x250);

        put(MRAID_EXPAND_1_IN_APP, TEMPLATE_BANNER_320x50);
        put(MRAID_EXPAND_2_IN_APP, TEMPLATE_BANNER_320x50);
        put(MRAID_RESIZE_IN_APP, TEMPLATE_BANNER_320x50);
        put(MRAID_RESIZE_WITH_ERRORS_IN_APP, TEMPLATE_BANNER_320x50);
        put(MRAID_VIDEO_INTERSTITIAL_IN_APP, TEMPLATE_MRAID_VIDEO_INTERSTITIAL);
        put(MRAID_FULLSCREEN_IN_APP, TEMPLATE_MRAID_FULLSCREEN);

        put(MRAID_VIDEO_INTERSTITIAL_GAM, TEMPLATE_MRAID_VIDEO_INTERSTITIAL);
        put(MRAID_EXPAND_1_GAM, TEMPLATE_BANNER_320x50);
        put(MRAID_RESIZE_GAM, TEMPLATE_BANNER_320x50);

        //NATIVE CASES

        put(NATIVE_AD_ADMOB, REQUEST_TEMPLATE_NATIVE);
        put(NATIVE_AD_ADMOB_NO_BIDS, REQUEST_TEMPLATE_NATIVE);
        put(NATIVE_AD_ADMOB_GAD_NO_BIDS, REQUEST_TEMPLATE_NATIVE);

        put(NATIVE_AD_MAX, REQUEST_TEMPLATE_NATIVE);
        put(NATIVE_AD_MAX_NO_BIDS, REQUEST_TEMPLATE_NATIVE);

        put(NATIVE_AD_IN_APP, REQUEST_TEMPLATE_NATIVE);
        put(NATIVE_AD_FEED_IN_APP, REQUEST_TEMPLATE_NATIVE);
        put(NATIVE_AD_LINKS_IN_APP, REQUEST_TEMPLATE_NATIVE);

        put(NATIVE_AD_GAM_CUSTOM_TEMPLATE_GAD, REQUEST_TEMPLATE_NATIVE);
        put(NATIVE_AD_GAM_ORIGINAL, REQUEST_TEMPLATE_NATIVE_ORIGINAL);
        put(NATIVE_AD_GAM_UNIFIED_GAD, REQUEST_TEMPLATE_NATIVE);
        put(NATIVE_AD_GAM_UNIFIED, REQUEST_TEMPLATE_NATIVE);
        put(NATIVE_AD_GAM_CUSTOM_TEMPLATE, REQUEST_TEMPLATE_NATIVE);
        put(NATIVE_AD_FEED_GAM, REQUEST_TEMPLATE_NATIVE);
        put(NATIVE_AD_GAM_UNIFIED_GAD_NO_BIDS, REQUEST_TEMPLATE_NATIVE);
        put(NATIVE_AD_GAM_CUSTOM_TEMPLATE_GAD_NO_BIDS, REQUEST_TEMPLATE_NATIVE);

    }};
    private final static HashMap<String, String> requestWithCacheTemplates = new HashMap<String, String>() {{
        put(BANNER_320x50_IN_APP, TEMPLATE_BANNER_320x50_CACHE);
        put(BANNER_320x50_ADMOB, TEMPLATE_BANNER_320x50_CACHE);
        put(BANNER_320x50_MAX, TEMPLATE_BANNER_320x50_CACHE);
        put(BANNER_320x50_GAM, TEMPLATE_BANNER_320x50_CACHE);

        put(VIDEO_INTERSTITIAL_320x480_IN_APP, TEMPLATE_VIDEO_INTERSTITIAL_320x480_CACHE);
        put(VIDEO_INTERSTITIAL_320x480_ADMOB, TEMPLATE_VIDEO_INTERSTITIAL_320x480_CACHE);
        put(VIDEO_INTERSTITIAL_320x480_GAM, TEMPLATE_VIDEO_INTERSTITIAL_320x480_CACHE);
        put(VIDEO_INTERSTITIAL_320x480_MAX, TEMPLATE_VIDEO_INTERSTITIAL_320x480_CACHE);
    }};
    private final static HashMap<String, String> requestWithAdditionalParamsTemplates = new HashMap<String, String>() {{
        put(BANNER_320x50_IN_APP, TEMPLATE_BANNER_320x50_ADDITIONAL_PARAMS);
        put(BANNER_320x50_ADMOB, TEMPLATE_BANNER_320x50_ADDITIONAL_PARAMS);
        put(BANNER_320x50_MAX, TEMPLATE_BANNER_320x50_ADDITIONAL_PARAMS);
        put(BANNER_320x50_GAM, TEMPLATE_BANNER_320x50_ADDITIONAL_PARAMS);

        put(VIDEO_INTERSTITIAL_320x480_IN_APP, TEMPLATE_VIDEO_INTERSTITIAL_320x480_ADDITIONAL_PARAMS);
        put(VIDEO_INTERSTITIAL_320x480_ADMOB, TEMPLATE_VIDEO_INTERSTITIAL_320x480_ADDITIONAL_PARAMS);
        put(VIDEO_INTERSTITIAL_320x480_GAM, TEMPLATE_VIDEO_INTERSTITIAL_320x480_ADDITIONAL_PARAMS);
        put(VIDEO_INTERSTITIAL_320x480_MAX, TEMPLATE_VIDEO_INTERSTITIAL_320x480_ADDITIONAL_PARAMS);
    }};
    private final static HashMap<String, String> realDeviceRequestTemplates = new HashMap<String, String>() {{
        put(BANNER_320x50_IN_APP, TEMPLATE_BANNER_320x50_REAL_DEVICE);
        put(BANNER_320x50_GAM, TEMPLATE_BANNER_320x50_REAL_DEVICE);
        put(BANNER_320x50_ADMOB, TEMPLATE_BANNER_320x50_REAL_DEVICE);
        put(BANNER_320x50_MAX, TEMPLATE_BANNER_320x50_REAL_DEVICE);

        put(INTERSTITIAL_320x480_IN_APP,TEMPLATE_INTERSTITIAL_320x480_REAL_DEVICE);
        put(INTERSTITIAL_320x480_ADMOB,TEMPLATE_INTERSTITIAL_320x480_REAL_DEVICE);
        put(INTERSTITIAL_320x480_GAM,TEMPLATE_INTERSTITIAL_320x480_REAL_DEVICE);
        put(INTERSTITIAL_320x480_MAX,TEMPLATE_INTERSTITIAL_320x480_REAL_DEVICE);

        put(INTERSTITIAL_MULTI_FORMAT_IN_APP,TEMPLATE_INTERSTITIAL_MULTIFORMAT_REAL_DEVICE);
        put(INTERSTITIAL_MULTI_FORMAT_MAX,TEMPLATE_INTERSTITIAL_MULTIFORMAT_REAL_DEVICE);
        put(INTERSTITIAL_MULTI_FORMAT_GAM,TEMPLATE_INTERSTITIAL_MULTIFORMAT_REAL_DEVICE);
        put(INTERSTITIAL_MULTI_FORMAT_ADMOB,TEMPLATE_INTERSTITIAL_MULTIFORMAT_REAL_DEVICE);

        put(VIDEO_INTERSTITIAL_320x480_IN_APP,TEMPLATE_VIDEO_INTERSTITIAL_320x480_REAL_DEVICE);
        put(VIDEO_INTERSTITIAL_320x480_ADMOB,TEMPLATE_VIDEO_INTERSTITIAL_320x480_REAL_DEVICE);
        put(VIDEO_INTERSTITIAL_320x480_GAM,TEMPLATE_VIDEO_INTERSTITIAL_320x480_REAL_DEVICE);
        put(VIDEO_INTERSTITIAL_320x480_MAX,TEMPLATE_VIDEO_INTERSTITIAL_320x480_REAL_DEVICE);

        put(VIDEO_REWARDED_320x480_IN_APP,TEMPLATE_VIDEO_REWARDED_320x480_REAL_DEVICE);
        put(VIDEO_REWARDED_320x480_ADMOB,TEMPLATE_VIDEO_REWARDED_320x480_REAL_DEVICE);
        put(VIDEO_REWARDED_320x480_GAM_METADATA,TEMPLATE_VIDEO_REWARDED_320x480_REAL_DEVICE);
        put(VIDEO_REWARDED_320x480_MAX,TEMPLATE_VIDEO_REWARDED_320x480_REAL_DEVICE);

        put(NATIVE_AD_GAM_CUSTOM_TEMPLATE, REQUEST_TEMPLATE_NATIVE_REAL_DEVICE);
        put(NATIVE_AD_GAM_UNIFIED, REQUEST_TEMPLATE_NATIVE_REAL_DEVICE);
        put(NATIVE_AD_ADMOB, REQUEST_TEMPLATE_NATIVE_REAL_DEVICE);
        put(NATIVE_AD_IN_APP, REQUEST_TEMPLATE_NATIVE_REAL_DEVICE);
        put(NATIVE_AD_MAX, REQUEST_TEMPLATE_NATIVE_REAL_DEVICE);

    }};
    private final static HashMap<String, String> responseTemplates = new HashMap<>() {{
        //NATIVE CASES

        put(NATIVE_AD_ADMOB, RESPONSE_TEMPLATE_NATIVE);
        put(NATIVE_AD_ADMOB_NO_BIDS, RESPONSE_TEMPLATE_NATIVE);
        put(NATIVE_AD_ADMOB_GAD_NO_BIDS, RESPONSE_TEMPLATE_NATIVE);

        put(NATIVE_AD_MAX, RESPONSE_TEMPLATE_NATIVE);
        put(NATIVE_AD_MAX_NO_BIDS, RESPONSE_TEMPLATE_NATIVE);

        put(NATIVE_AD_IN_APP, RESPONSE_TEMPLATE_NATIVE);
        put(NATIVE_AD_FEED_IN_APP, RESPONSE_TEMPLATE_NATIVE);
        put(NATIVE_AD_LINKS_IN_APP, RESPONSE_TEMPLATE_NATIVE);

        put(NATIVE_AD_GAM_CUSTOM_TEMPLATE_GAD, RESPONSE_TEMPLATE_NATIVE);
        put(NATIVE_AD_GAM_ORIGINAL, RESPONSE_TEMPLATE_NATIVE);
        put(NATIVE_AD_GAM_UNIFIED_GAD, RESPONSE_TEMPLATE_NATIVE);
        put(NATIVE_AD_GAM_UNIFIED, RESPONSE_TEMPLATE_NATIVE);
        put(NATIVE_AD_GAM_CUSTOM_TEMPLATE, RESPONSE_TEMPLATE_NATIVE);
        put(NATIVE_AD_FEED_GAM, RESPONSE_TEMPLATE_NATIVE);
        put(NATIVE_AD_GAM_UNIFIED_GAD_NO_BIDS, RESPONSE_TEMPLATE_NATIVE);
        put(NATIVE_AD_GAM_CUSTOM_TEMPLATE_GAD_NO_BIDS, RESPONSE_TEMPLATE_NATIVE);

        put(VIDEO_INTERSTITIAL_320x480_IN_APP_WITH_AD_CONFIGURATION, RESPONSE_TEMPLATE_VIDEO_INTERSTITIAL_AD_CONFIGURATION);
        put(VIDEO_INTERSTITIAL_320x480_GAM_WITH_AD_CONFIGURATION, RESPONSE_TEMPLATE_VIDEO_INTERSTITIAL_AD_CONFIGURATION);
        put(VIDEO_INTERSTITIAL_320x480_ADMOB_WITH_AD_CONFIGURATION, RESPONSE_TEMPLATE_VIDEO_INTERSTITIAL_AD_CONFIGURATION);
        put(VIDEO_INTERSTITIAL_320x480_MAX_WITH_AD_CONFIGURATION, RESPONSE_TEMPLATE_VIDEO_INTERSTITIAL_AD_CONFIGURATION);

        put(VIDEO_REWARDED_320x480_IN_APP_WITH_AD_CONFIGURATION, RESPONSE_TEMPLATE_VIDEO_REWARDED_AD_CONFIGURATION);
        put(VIDEO_REWARDED_320x480_GAM_WITH_AD_CONFIGURATION, RESPONSE_TEMPLATE_VIDEO_REWARDED_AD_CONFIGURATION);
        put(VIDEO_REWARDED_320x480_ADMOB_WITH_AD_CONFIGURATION, RESPONSE_TEMPLATE_VIDEO_REWARDED_AD_CONFIGURATION);
        put(VIDEO_REWARDED_320x480_MAX_WITH_AD_CONFIGURATION, RESPONSE_TEMPLATE_VIDEO_REWARDED_AD_CONFIGURATION);
    }};
}
