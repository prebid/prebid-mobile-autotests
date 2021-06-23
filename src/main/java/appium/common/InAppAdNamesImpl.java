package appium.common;

public interface InAppAdNamesImpl {
    //IN APP BIDDING
    String BANNER_320x50_IN_APP = "Banner 320x50 (In-App)";
    String BANNER_320x50_NO_BID_IN_APP = "Banner 320x50 (In-App) [noBids]";
    String BANNER_320x50_IN_APP_ATS = "Banner 320x50 (In-App) ATS";
    String BANNER_320x50_IN_APP_VAST = "Banner 320x50 (In-App) [Incorrect VAST]";
    String BANNER_320x50_IN_APP_DEEPLINK = "Banner 320x50 (In-App) [DeepLink+]";
    String BANNER_320x50_IN_APP_SCROLLABLE = "Banner 320x50 (In-App) [Scrollable]";
    String BANNER_300x250_IN_APP = "Banner 300x250 (In-App)";
    String BANNER_728x90_IN_APP = "Banner 728x90 (In-App)";
    String BANNER_MULTISIZE_IN_APP = "Banner Multisize (In-App)";
    String BANNER_SKADNETWORK = "Banner 320x50 (In-App) [SKAdN]";

    String BANNER_320x50_GAM = "Banner 320x50 (GAM) [OK, AppEvent]";
    String BANNER_320x50_GAM_VANILLA = "Banner 320x50 (GAM) [Vanilla Prebid Order]";
    String BANNER_320x50_GAM_AD = "Banner 320x50 (GAM) [OK, GAM Ad]";
    String BANNER_320x50_NO_BID_GAM_AD = "Banner 320x50 (GAM) [noBids, GAM Ad]";
    String BANNER_320x50_GAM_RANDOM = "Banner 320x50 (GAM) [OK, Random]";
    String BANNER_300x250_GAM = "Banner 300x250 (GAM)";
    String BANNER_728x90_GAM = "Banner 728x90 (GAM)";
    String BANNER_MULTISIZE_GAM = "Banner Multisize (GAM)";

    String BANNER_320x50_MOPUB = "Banner 320x50 (MoPub) [OK, OXB Adapter]";
    String BANNER_320x50_MOPUB_VANILLA = "Banner 320x50 (MoPub) [Vanilla Prebid Order]";
    String BANNER_320x50_NO_BID_MOPUB = "Banner 320x50 (MoPub) [noBids, MoPub Ad]";
    String BANNER_320x50_MOPUB_RANDOM = "Banner 320x50 (MoPub) [OK, Random]";
    String BANNER_300x250_MOPUB = "Banner 300x250 (MoPub)";
    String BANNER_728x90_MOPUB = "Banner 728x90 (MoPub)";
    String BANNER_MULTISIZE_MOPUB = "Banner Multisize (MoPub)";

    String INTERSTITIAL_320x480_IN_APP = "Display Interstitial 320x480 (In-App)";

    String INTERSTITIAL_320x480_MOPUB = "Display Interstitial 320x480 (MoPub) [OK, OXB Adapter]";
    String INTERSTITIAL_320x480_MOPUB_RANDOM = "Display Interstitial 320x480 (MoPub) [OK, Random]";
    String INTERSTITIAL_320x480_NO_BID_MOPUB = "Display Interstitial 320x480 (MoPub) [noBids, MoPub Ad]";

    String INTERSTITIAL_320x480_GAM = "Display Interstitial 320x480 (GAM) [OK, AppEvent]";
    String INTERSTITIAL_320x480_MULTISIZE_GAM = "Display Interstitial Multisize (GAM) [OK, AppEvent]";
    String INTERSTITIAL_320x480_NO_BID_GAM = "Display Interstitial 320x480 (GAM) [noBids, GAM Ad]";
    String INTERSTITIAL_320x480_GAM_RANDOM = "Display Interstitial 320x480 (GAM) [OK, Random]";

    String MRAID_EXPAND_1_IN_APP = "MRAID 2.0: Expand - 1 Part (In-App)";
    String MRAID_EXPAND_2_IN_APP = "MRAID 2.0: Expand - 2 Part (In-App)";
    String MRAID_RESIZE_IN_APP = "MRAID 2.0: Resize (In-App)";
    String MRAID_VIDEO_INTERSTITIAL_IN_APP = "MRAID 2.0: Video Interstitial (In-App)";

    String MRAID_EXPAND_1_GAM = "MRAID 2.0: Expand - 1 Part (GAM)";
    String MRAID_VIDEO_INTERSTITIAL_GAM = "MRAID 2.0: Video Interstitial (GAM)";
    String MRAID_RESIZE_GAM = "MRAID 2.0: Resize (GAM)";

    String MRAID_EXPAND_1_MOPUB = "MRAID 2.0: Expand - 1 Part (MoPub)";
    String MRAID_VIDEO_INTERSTITIAL_MOPUB = "MRAID 2.0: Video Interstitial (MoPub)";
    String MRAID_RESIZE_MOPUB = "MRAID 2.0: Resize (MoPub)";

    String MRAID_FULLSCREEN_IN_APP = "MRAID 2.0: Fullscreen (In-App)";
    String MRAID_RESIZE_WITH_ERRORS_IN_APP = "MRAID 2.0: Resize with Errors (In-App)";

    String MRAID_LOAD_AND_EVENTS = "MRAID 3.0: Load And Events (In-App)";
    String MRAID_VIEWABILITY_COMPLIANCE = "MRAID 3.0: Viewability Compliance (In-App)";
    String MRAID_RESIZE_NEGATIVE = "MRAID 3.0: Resize Negative Test (In-App)";

    String VIDEO_INTERSTITIAL_320x480_IN_APP = "Video Interstitial 320x480 (In-App)";
    String VIDEO_INTERSTITIAL_320x480_ENDCARD = "Video Interstitial 320x480 with End Card";
    String VIDEO_INTERSTITIAL_320x480_MOPUB = "Video Interstitial 320x480 (MoPub) [OK, OXB Adapter]";
    String VIDEO_320x480_MOPUB_RANDOM = "Video Interstitial 320x480 (MoPub) [OK, Random]";
    String VIDEO_320x480_NO_BID_MOPUB = "Video Interstitial 320x480 (MoPub) [noBids, MoPub Ad]";
    String VIDEO_320x480_GAM = "Video Interstitial 320x480 (GAM) [OK, AppEvent]";
    String VIDEO_320x480_GAM_RANDOM = "Video Interstitial 320x480 (GAM) [OK, Random]";
    String VIDEO_320x480_NO_BID_GAM_AD = "Video Interstitial 320x480 (GAM) [noBids, GAM Ad]";

    String VIDEO_OUTSTREAM_IN_APP = "Video Outstream (In-App)";
    String VIDEO_OUTSTREAM_ENDCARD = "Video Outstream with End Card (In-App)";
    String VIDEO_OUTSTREAM_FEED_IN_APP = "Video Outstream Feed (In-App)";
    String VIDEO_OUTSTREAM_FEED_GAM = "Video Outstream Feed (GAM)";
    String VIDEO_OUTSTREAM_GAM = "Video Outstream (GAM) [OK, AppEvent]";
    String VIDEO_OUTSTREAM_GAM_RANDOM = "Video Outstream (GAM) [OK, Random]";
    String VIDEO_OUTSTREAM_NO_BID_GAM_AD = "Video Outstream (GAM) [noBids, GAM Ad]";

    String VIDEO_REWARDED_320x480_IN_APP = "Video Rewarded 320x480 (In-App)";
    String VIDEO_REWARDED_320x480_GAM_METADATA = "Video Rewarded 320x480 (GAM) [OK, Metadata]";
    String VIDEO_REWARDED_320x480_NO_BID_GAM = "Video Rewarded 320x480 (GAM) [noBids, GAM Ad]";
    String VIDEO_REWARDED_320x480_GAM_RANDOM = "Video Rewarded 320x480 (GAM) [OK, Random]";
    String VIDEO_REWARDED_320x480_MOPUB = "Video Rewarded 320x480 (MoPub) [OK, OXB Adapter]";
    String VIDEO_REWARDED_320x480_MOPUB_RANDOM = "Video Rewarded 320x480 (MoPub) [OK, Random]";
    String VIDEO_REWARDED_320x480_NO_BID_MOPUB = "Video Rewarded 320x480 (MoPub) [noBids, MoPub Ad]";

    //CUSTOM AUCTION REQUEST TESTS
    String CUSTOM_USPRIVACY_CCPA_TRUE = "CUSTOM_USPRIVACY_CCPA_TRUE";
    String CUSTOM_USPRIVACY_CCPA_FALSE = "CUSTOM_USPRIVACY_CCPA_FALSE";
    String CUSTOM_TCF_GDPR1_CONSENT = "CUSTOM_TCF_GDPR1_CONSENT";
    String CUSTOM_TCF_GDPR0_NO_CONSENT = "CUSTOM_TCF_GDPR0_NO_CONSENT";
    String CUSTOM_TCF_NO_GDPR_NO_CONSENT = "CUSTOM_TCF_NO_GDPR_NO_CONSENT";
    String CUSTOM_OPENRTB = "CUSTOM_OPENRTB";
    String LIVE_RAMP_ATS = "LIVE_RAMP_ATS";
    String ATTS_1 = "ATTS_1";
    String ATTS_2 = "ATTS_2";
    String ATTS_3 = "ATTS_3";

    //NATIVE ADS NAME
    String NATIVE_STYLES_IN_APP_MAP = "Banner Native Styles (In-App) [MAP]";
    String NATIVE_STYLES_IN_APP_KEYS = "Banner Native Styles (In-App) [KEYS]";
    String NATIVE_STYLES_NO_ASSETS_PPM = "Banner Native Styles No Assets (In-App)";
    String NATIVE_STYLES_NO_CREATIVE_PPM = "Banner Native Styles No Creative (In-App)";

    String NATIVE_AD_IN_APP = "Native Ad (In-App)";
    String NATIVE_AD_FEED_IN_APP = "Native Ad Feed (In-App)";
    String NATIVE_AD_LINKS_IN_APP = "Native Ad Links (In-App)";

    String NATIVE_STYLES_GAM_MRECT = "Banner Native Styles (GAM) [MRect]";
    String NATIVE_STYLES_NO_ASSETS_GAM_MRECT = "Banner Native Styles No Assets (GAM) [MRect]";
    String NATIVE_STYLES_GAM_FLUID = "Banner Native Styles (GAM) [Fluid]";

    String NATIVE_STYLES_MO_PUB = "Banner Native Styles (MoPub)";
    String NATIVE_STYLES_NO_ASSETS_MO_PUB = "Banner Native Styles No Assets (MoPub)";
}
