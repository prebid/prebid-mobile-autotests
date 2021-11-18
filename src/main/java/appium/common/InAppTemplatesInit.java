package appium.common;

import org.json.JSONObject;
import utils.FileUtils;

import java.util.HashMap;

public class InAppTemplatesInit implements InAppAdNamesImpl {

    //REQUEST TEMPLATES
    private final static String TEMPLATE_BANNER_320x50_LIVERAMP = "appium/custom_requests/ortb/%s/liveRampATS.json";
    private final static String TEMPLATE_BANNER_320x50_CCPA_TRUE = "appium/custom_requests/ortb/%s/AUCTION_CustomParamCCPA_TRUE.json";
    private final static String TEMPLATE_BANNER_320x50_CCPA_FALSE = "appium/custom_requests/ortb/%s/AUCTION_CustomParamCCPA_FALSE.json";
    private final static String TEMPLATE_BANNER_320x50_GDPR0_CONS_NULL = "appium/custom_requests/ortb/%s/AUCTION_TCFv1_TRUE_GDPR_0_CONS_NULL.json";
    private final static String TEMPLATE_BANNER_320x50_GDPR1_CONS_TRUE = "appium/custom_requests/ortb/%s/AUCTION_TCFv1_TRUE_GDPR_1_CONS_TRUE.json";
    private final static String TEMPLATE_BANNER_320x50_ATTS_1 = "appium/inAppBidding_requests/%s/inApp_auction_320x50_atts_1.json";
    private final static String TEMPLATE_BANNER_320x50_ATTS_2 = "appium/inAppBidding_requests/%s/inApp_auction_320x50_atts_2.json";
    private final static String TEMPLATE_BANNER_320x50_ATTS_3 = "appium/inAppBidding_requests/%s/inApp_auction_320x50_atts_3.json";
    private final static String TEMPLATE_BANNER_320x50_SKADN = "appium/inAppBidding_requests/%s/inApp_auction_320x50_skadn.json";

    private final static String TEMPLATE_BANNER_320x50 = "appium/inAppBidding_requests/%s/inApp_auction_320x50.json";

    private final static String TEMPLATE_BANNER_300x250 = "appium/inAppBidding_requests/%s/inApp_auction_300x250.json";
    private final static String TEMPLATE_BANNER_728x90 = "appium/inAppBidding_requests/%s/inApp_auction_728x90.json";
    private final static String TEMPLATE_BANNER_MULTISIZE = "appium/inAppBidding_requests/%s/inApp_auction_ms.json";

    private final static String TEMPLATE_INTERSTITIAL_320x480 = "appium/inAppBidding_requests/%s/inApp_auction_320x480.json";

    private final static String TEMPLATE_MRAID_FULLSCREEN = "appium/inAppBidding_requests/%s/inApp_mraid_fullscreen_auc.json";
    private final static String TEMPLATE_MRAID_VIDEO_INTERSTITIAL = "appium/inAppBidding_requests/%s/inApp_mraid_video_int_auc.json";

    private final static String TEMPLATE_VIDEO_INTERSTITIAL_320x480 = "appium/inAppBidding_requests/%s/inApp_video_int_auc_320x480.json";
    private final static String TEMPLATE_VIDEO_OUTSTREAM_300x250 = "appium/inAppBidding_requests/%s/inApp_video_outstream_auc_300x250.json";
    private final static String TEMPLATE_VIDEO_FEED_300x250 = "appium/inAppBidding_requests/%s/inApp_video_feed_auc_300x250.json";
    private final static String TEMPLATE_VIDEO_REWARDED_320x480 = "appium/inAppBidding_requests/%s/inApp_video_rew_auc_320x480.json";

    //NATIVE TEMPLATES
    private final static String REQUEST_TEMPLATE_NATIVE = "appium/inAppBidding_requests/%s/inApp_native_req.json";

    //RESPONSES
    private final static String RESPONSE_TEMPLATE_NATIVE = "appium/inAppBidding_responses/%s/inApp_native_res.json";

    public static JSONObject getAuctionRequestTemplate(String prebidAd, String platformName) {
        final String filePath = getFilePathAuctionRequest(prebidAd, platformName);
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

        put(BANNER_320x50_MOPUB, TEMPLATE_BANNER_320x50);
        put(BANNER_320x50_MOPUB_RANDOM, TEMPLATE_BANNER_320x50);
        put(BANNER_320x50_MOPUB_VANILLA, TEMPLATE_BANNER_320x50);
        put(BANNER_320x50_NO_BID_MOPUB, TEMPLATE_BANNER_320x50);

        put(BANNER_320x50_GAM, TEMPLATE_BANNER_320x50);
        put(BANNER_320x50_GAM_AD, TEMPLATE_BANNER_320x50);
        put(BANNER_320x50_GAM_RANDOM, TEMPLATE_BANNER_320x50);
        put(BANNER_320x50_GAM_VANILLA, TEMPLATE_BANNER_320x50);
        put(BANNER_320x50_NO_BID_GAM_AD, TEMPLATE_BANNER_320x50);

        put(BANNER_300x250_IN_APP, TEMPLATE_BANNER_300x250);
        put(BANNER_300x250_GAM, TEMPLATE_BANNER_300x250);
        put(BANNER_300x250_MOPUB, TEMPLATE_BANNER_300x250);

        put(BANNER_728x90_IN_APP, TEMPLATE_BANNER_728x90);
        put(BANNER_728x90_GAM, TEMPLATE_BANNER_728x90);
        put(BANNER_728x90_MOPUB, TEMPLATE_BANNER_728x90);

        put(BANNER_MULTISIZE_IN_APP, TEMPLATE_BANNER_MULTISIZE);
        put(BANNER_MULTISIZE_GAM, TEMPLATE_BANNER_MULTISIZE);
        put(BANNER_MULTISIZE_MOPUB, TEMPLATE_BANNER_MULTISIZE);

        put(BANNER_SKADNETWORK, TEMPLATE_BANNER_320x50_SKADN);
        put(LIVE_RAMP_ATS, TEMPLATE_BANNER_320x50_LIVERAMP);
        put(ATTS_1, TEMPLATE_BANNER_320x50_ATTS_1);
        put(ATTS_2, TEMPLATE_BANNER_320x50_ATTS_2);
        put(ATTS_3, TEMPLATE_BANNER_320x50_ATTS_3);
        put(CUSTOM_USPRIVACY_CCPA_TRUE, TEMPLATE_BANNER_320x50_CCPA_TRUE);
        put(CUSTOM_USPRIVACY_CCPA_FALSE, TEMPLATE_BANNER_320x50_CCPA_FALSE);
        put(CUSTOM_TCF_GDPR0_NO_CONSENT, TEMPLATE_BANNER_320x50_GDPR0_CONS_NULL);
        put(CUSTOM_TCF_GDPR1_CONSENT, TEMPLATE_BANNER_320x50_GDPR1_CONS_TRUE);
        put(CUSTOM_TCF_NO_GDPR_NO_CONSENT, TEMPLATE_BANNER_320x50_CCPA_FALSE);

        put(INTERSTITIAL_320x480_IN_APP, TEMPLATE_INTERSTITIAL_320x480);
        put(INTERSTITIAL_320x480_GAM, TEMPLATE_INTERSTITIAL_320x480);
        put(INTERSTITIAL_320x480_MOPUB, TEMPLATE_INTERSTITIAL_320x480);

        put(INTERSTITIAL_320x480_GAM_RANDOM, TEMPLATE_INTERSTITIAL_320x480);
        put(INTERSTITIAL_320x480_MOPUB_RANDOM, TEMPLATE_INTERSTITIAL_320x480);

        put(INTERSTITIAL_320x480_NO_BID_GAM, TEMPLATE_INTERSTITIAL_320x480);
        put(INTERSTITIAL_320x480_NO_BID_MOPUB, TEMPLATE_INTERSTITIAL_320x480);

        put(INTERSTITIAL_320x480_MULTISIZE_GAM, TEMPLATE_BANNER_MULTISIZE);

        put(VIDEO_INTERSTITIAL_320x480_IN_APP, TEMPLATE_VIDEO_INTERSTITIAL_320x480);
        put(VIDEO_INTERSTITIAL_320x480_MOPUB, TEMPLATE_VIDEO_INTERSTITIAL_320x480);
        put(VIDEO_INTERSTITIAL_320x480_ENDCARD, TEMPLATE_VIDEO_INTERSTITIAL_320x480);

        put(VIDEO_320x480_GAM, TEMPLATE_VIDEO_INTERSTITIAL_320x480);
        put(VIDEO_320x480_GAM_RANDOM, TEMPLATE_VIDEO_INTERSTITIAL_320x480);
        put(VIDEO_320x480_NO_BID_GAM_AD, TEMPLATE_VIDEO_INTERSTITIAL_320x480);

        put(VIDEO_320x480_MOPUB_RANDOM, TEMPLATE_VIDEO_INTERSTITIAL_320x480);
        put(VIDEO_320x480_NO_BID_MOPUB, TEMPLATE_VIDEO_INTERSTITIAL_320x480);

        put(VIDEO_REWARDED_320x480_IN_APP, TEMPLATE_VIDEO_REWARDED_320x480);

        put(VIDEO_REWARDED_320x480_MOPUB, TEMPLATE_VIDEO_REWARDED_320x480);
        put(VIDEO_REWARDED_320x480_NO_BID_MOPUB, TEMPLATE_VIDEO_REWARDED_320x480);
        put(VIDEO_REWARDED_320x480_MOPUB_RANDOM, TEMPLATE_VIDEO_REWARDED_320x480);

        put(VIDEO_REWARDED_320x480_GAM_METADATA, TEMPLATE_VIDEO_REWARDED_320x480);
        put(VIDEO_REWARDED_320x480_NO_BID_GAM, TEMPLATE_VIDEO_REWARDED_320x480);
        put(VIDEO_REWARDED_320x480_GAM_RANDOM, TEMPLATE_VIDEO_REWARDED_320x480);

        put(VIDEO_OUTSTREAM_IN_APP, TEMPLATE_VIDEO_OUTSTREAM_300x250);
        put(VIDEO_OUTSTREAM_FEED_IN_APP, TEMPLATE_VIDEO_FEED_300x250);
        put(VIDEO_OUTSTREAM_ENDCARD, TEMPLATE_VIDEO_OUTSTREAM_300x250);

        put(VIDEO_OUTSTREAM_GAM, TEMPLATE_VIDEO_OUTSTREAM_300x250);
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

        put(MRAID_VIDEO_INTERSTITIAL_MOPUB, TEMPLATE_MRAID_VIDEO_INTERSTITIAL);
        put(MRAID_EXPAND_1_MOPUB, TEMPLATE_BANNER_320x50);
        put(MRAID_RESIZE_MOPUB, TEMPLATE_BANNER_320x50);

        //NATIVE CASES
        put(NATIVE_AD_IN_APP, REQUEST_TEMPLATE_NATIVE);
        put(NATIVE_STYLES_IN_APP_MAP, REQUEST_TEMPLATE_NATIVE);
        put(NATIVE_STYLES_IN_APP_KEYS, REQUEST_TEMPLATE_NATIVE);
        put(NATIVE_STYLES_NO_ASSETS_PPM, REQUEST_TEMPLATE_NATIVE);
        put(NATIVE_STYLES_NO_CREATIVE_PPM, REQUEST_TEMPLATE_NATIVE);

        put(NATIVE_STYLES_MO_PUB, REQUEST_TEMPLATE_NATIVE);
        put(NATIVE_STYLES_NO_ASSETS_MO_PUB, REQUEST_TEMPLATE_NATIVE);

        put(NATIVE_STYLES_GAM_FLUID, REQUEST_TEMPLATE_NATIVE);
        put(NATIVE_STYLES_GAM_MRECT, REQUEST_TEMPLATE_NATIVE);
        put(NATIVE_STYLES_NO_ASSETS_GAM_MRECT, REQUEST_TEMPLATE_NATIVE);
    }};

    private final static HashMap<String, String> responseTemplates = new HashMap<>() {{
        //NATIVE CASES
        put(NATIVE_AD_IN_APP, RESPONSE_TEMPLATE_NATIVE);
        put(NATIVE_AD_FEED_IN_APP, RESPONSE_TEMPLATE_NATIVE);
        put(NATIVE_AD_LINKS_IN_APP, RESPONSE_TEMPLATE_NATIVE);
        put(NATIVE_STYLES_IN_APP_MAP, RESPONSE_TEMPLATE_NATIVE);
        put(NATIVE_STYLES_IN_APP_KEYS, RESPONSE_TEMPLATE_NATIVE);
        put(NATIVE_STYLES_NO_ASSETS_PPM, RESPONSE_TEMPLATE_NATIVE);
        put(NATIVE_STYLES_NO_CREATIVE_PPM, RESPONSE_TEMPLATE_NATIVE);

        put(NATIVE_STYLES_MO_PUB, RESPONSE_TEMPLATE_NATIVE);
        put(NATIVE_STYLES_NO_ASSETS_MO_PUB, RESPONSE_TEMPLATE_NATIVE);

        put(NATIVE_STYLES_GAM_FLUID, RESPONSE_TEMPLATE_NATIVE);
        put(NATIVE_STYLES_GAM_MRECT, RESPONSE_TEMPLATE_NATIVE);
        put(NATIVE_STYLES_NO_ASSETS_GAM_MRECT, RESPONSE_TEMPLATE_NATIVE);

    }};
}
