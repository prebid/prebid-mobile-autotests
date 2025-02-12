package appium.inAppBidding;

import org.testng.annotations.DataProvider;

import static appium.common.InAppAdNames.*;


public class InAppDataProviders {

    private static final String[][] TCFv1Cases = {
            {CUSTOM_TCF_NO_GDPR_NO_CONSENT},
            {CUSTOM_TCF_GDPR0_NO_CONSENT},
            {CUSTOM_TCF_GDPR1_CONSENT}
    };

    // Without geo data
    @DataProvider(name = "adNamesWithAdditionalParams")
    public static Object[][] provideWithoutGeoRequests() {
        String[][] geoRequestsArray = new String[BannerAdWithCache.length + VideoInterstitial.length][];
        System.arraycopy(BannerAdWithCache, 0, geoRequestsArray, 0, BannerAdWithCache.length - 2);
        System.arraycopy(VideoInterstitial, 0, geoRequestsArray, BannerAdWithCache.length, VideoInterstitial.length - 2);
        return geoRequestsArray;
    }

    //CUSTOM REQUESTS DATA
    @DataProvider(name = "TCFv1")
    public static Object[][] provideTCFv1Cases() {
        return TCFv1Cases;
    }

    //////////////
    //BANNER DATA
    //////////////

    private static final String[][] BannerAd = {
            {BANNER_320x50_IN_APP},
            {BANNER_320x50_GAM},
            {BANNER_320x50_ADMOB},
//            {BANNER_320x50_MAX},
            {BANNER_728x90_IN_APP},
            {BANNER_728x90_GAM},
            {BANNER_MULTISIZE_IN_APP},
            {BANNER_MULTISIZE_GAM},
            {BANNER_300x250_IN_APP},
            {BANNER_300x250_GAM},
            {BANNER_300x250_ADMOB},
//            {BANNER_300x250_MAX},
            {BANNER_ADAPTIVE_ADMOB},
//            {BANNER_ADAPTIVE_MAX}
    };

    private static final String[][] BannerAdOriginal = {
            {BANNER_320x50_GAM_ORIGINAL},
            {BANNER_728x90_GAM_ORIGINAL},
            {BANNER_300x250_GAM_ORIGINAL},
            {BANNER_MULTISIZE_GAM_ORIGINAL},
            {BANNER_300x250_GAM_ORIGINAL_MULTIFORMAT},
    };

    private static final String[][] BannerAdWithCache = {
            {BANNER_320x50_IN_APP},
            {BANNER_320x50_GAM},
            {BANNER_320x50_ADMOB},
//            {BANNER_320x50_MAX},
    };
    private static final String[][] BannerAdRealDevice = {
            {BANNER_320x50_IN_APP},
            {BANNER_320x50_GAM},
            {BANNER_320x50_ADMOB},
//            {BANNER_320x50_MAX},
    };
    private static final String[][] BannerRefreshAd = {
            {BANNER_320x50_IN_APP},
    };
    private static final String[][] BannerCustomOmAds = {
            {BANNER_320x50_IN_APP},
            {BANNER_320x50_ADMOB},
//            {BANNER_320x50_MAX},
    };

    /* AD_NAME_TYPE*/
    @DataProvider(name = "adName")
    public static Object[][] provideBannerFormatAndSettings() {
        return BannerAd;
    }

    @DataProvider(name = "adNameWithCache")
    public static Object[][] provideBannerAdsWithCache() {
        return BannerAdWithCache;
    }

    @DataProvider(name = "adNameReal")
    public static Object[][] provideBannerFoRealDevices() {
        return BannerAdRealDevice;
    }

    @DataProvider(name = "customOmAdName")
    public static Object[][] provideBannerWithCustomOm() {
        return BannerCustomOmAds;
    }

    @DataProvider(name = "refreshAds")
    public static Object[][] provideBannerRefreshAds() {
        return BannerRefreshAd;
    }

    private static final String[][] BannerNoBids = {
            {BANNER_320x50_NO_BID_GAM_AD},
            {BANNER_320x50_NO_BID_IN_APP},
            {BANNER_320x50_NO_BID_ADMOB},
//            {BANNER_320x50_NO_BID_MAX},
    };

    @DataProvider(name = "noBids")
    public static Object[][] provideBannerNoBids() {
        return BannerNoBids;
    }

    private static final String[][] BannerRandom = {
            {BANNER_320x50_GAM_RANDOM},
            {BANNER_320x50_ADMOB_RANDOM},
//            {BANNER_320x50_MAX_RANDOM},
    };

    @DataProvider(name = "randomAd")
    public static Object[][] provideBannerRandom() {
        return BannerRandom;
    }

    @DataProvider(name = "bannerAds")
    public static Object[][] provideBanner() {
        return BannerAd;
    }

    @DataProvider(name = "bannerOriginalAds")
    public static Object[][] provideOriginalBanner() {
        return BannerAdOriginal;
    }


    private static final String[][] InterstitialAds = {
            {INTERSTITIAL_320x480_IN_APP},
            {INTERSTITIAL_320x480_ADMOB},
//            {INTERSTITIAL_320x480_MAX},
            {INTERSTITIAL_320x480_GAM},
    };

    @DataProvider(name = "interstitialAds")
    public static Object[][] provideInterstitial() {
        return InterstitialAds;
    }


    @DataProvider(name = "interstitialOriginalAds")
    public static Object[][] provideInterstitialOriginalAds() {
        return new String[][]{
                {INTERSTITIAL_320x480_GAM_ORIGINAL},
                {INTERSTITIAL_320x480_MULTIFORMAT_ORIGINAL},
        };
    }

    private static final String[][] InterstitialRandom = {
            {INTERSTITIAL_320x480_GAM_RANDOM},
            {INTERSTITIAL_320x480_ADMOB_RANDOM},
//            {INTERSTITIAL_320x480_MAX_RANDOM},
    };

    @DataProvider(name = "randomAdInterstitial")
    public static Object[][] provideInterstitialRandom() {
        return InterstitialRandom;
    }

    private static final String[][] InterstitialNoBids = {
            {INTERSTITIAL_320x480_NO_BID_ADMOB},
            {INTERSTITIAL_320x480_NO_BID_GAM},
//            {INTERSTITIAL_320x480_NO_BID_MAX},
    };

    @DataProvider(name = "noBidsInterstitial")
    public static Object[][] provideInterstitialNoBids() {
        return InterstitialNoBids;
    }

    private static final String[][] InterstitialMultiFormatAds = {
            {INTERSTITIAL_MULTI_FORMAT_IN_APP},
            {INTERSTITIAL_MULTI_FORMAT_GAM},
            {INTERSTITIAL_MULTI_FORMAT_GAM},
//            {INTERSTITIAL_MULTI_FORMAT_MAX},
            {INTERSTITIAL_MULTI_FORMAT_ADMOB}
    };

    @DataProvider(name = "interstitialMultiFormat")
    public static Object[][] provideInterstitialMultiFormat() {
        return InterstitialMultiFormatAds;
    }
    //MRAID DATA

    private static final String[][] MraidAds = {
            {MRAID_EXPAND_1_IN_APP},
            {MRAID_RESIZE_IN_APP},
            {MRAID_VIDEO_INTERSTITIAL_IN_APP},
            {MRAID_EXPAND_2_IN_APP},
            {MRAID_FULLSCREEN_IN_APP},
            {MRAID_EXPAND_1_GAM},
            {MRAID_RESIZE_GAM},
            {MRAID_VIDEO_INTERSTITIAL_GAM},
    };

    /* AD_NAME_TYPE*/
    @DataProvider(name = "adNameMraid")
    public static Object[][] provideMraidAd() {
        return MraidAds;
    }

    private static final String[][] MraidReloads = {
            {MRAID_FULLSCREEN_IN_APP},
            {MRAID_EXPAND_1_IN_APP},
            {MRAID_RESIZE_IN_APP},
            {MRAID_EXPAND_2_IN_APP},
            {MRAID_EXPAND_1_GAM},
            {MRAID_RESIZE_GAM},

    };

    @DataProvider(name = "adNameReload")
    public static Object[][] provideMraidReloadAd() {
        return MraidReloads;
    }

    private static final String[][] MraidExpand1 = {
            {MRAID_EXPAND_1_IN_APP},
            {MRAID_EXPAND_1_GAM},
    };

    @DataProvider(name = "mraidExpand1")
    public static Object[][] provideMraidExpand1Ad() {
        return MraidExpand1;
    }

    private static final String[][] MraidVideoInterstitial = {
            {MRAID_VIDEO_INTERSTITIAL_IN_APP},
            {MRAID_VIDEO_INTERSTITIAL_GAM},
    };

    @DataProvider(name = "videoInterstitialAd")
    public static Object[][] provideMraidVideoInterstitialAd() {
        return MraidVideoInterstitial;
    }

    private static final String[][] MraidResize = {
            {MRAID_RESIZE_IN_APP},
            {MRAID_RESIZE_GAM},
    };

    @DataProvider(name = "mraidResize")
    public static Object[][] provideMraidResizeAd() {
        return MraidResize;
    }

    private static final String[][] VideoAds = {
            {VIDEO_OUTSTREAM_IN_APP},
            {VIDEO_OUTSTREAM_ENDCARD},
            {VIDEO_OUTSTREAM_FEED_IN_APP},
            {VIDEO_OUTSTREAM_FEED_GAM},
            {VIDEO_OUTSTREAM_GAM},

            {VIDEO_INTERSTITIAL_320x480_IN_APP},
            {VIDEO_INTERSTITIAL_320x480_ENDCARD},
            {VIDEO_INTERSTITIAL_320x480_ADMOB},
            {VIDEO_INTERSTITIAL_320x480_GAM},
//            {VIDEO_INTERSTITIAL_320x480_MAX},
            {VIDEO_REWARDED_320x480_IN_APP},
            {VIDEO_REWARDED_320x480_GAM_METADATA},
            {VIDEO_REWARDED_320x480_ADMOB},
//            {VIDEO_REWARDED_320x480_MAX},
    };

    private static final String[][] VideoAdsOriginal = {
            {VIDEO_OUTSTREAM_GAM_ORIGINAL},
//            {VIDEO_INSTREAM_GAM_ORIGINAL},
            {VIDEO_INTERSTITIAL_320x480_GAM_ORIGINAL},
            {VIDEO_REWARDED_320x480_GAM_ORIGINAL},
    };

    private static final String[][] VideoAdsRealDevice = {
            {VIDEO_INTERSTITIAL_320x480_IN_APP},
            {VIDEO_INTERSTITIAL_320x480_ADMOB},
            {VIDEO_INTERSTITIAL_320x480_GAM},
//            {VIDEO_INTERSTITIAL_320x480_MAX},
            {VIDEO_REWARDED_320x480_IN_APP},
            {VIDEO_REWARDED_320x480_GAM_METADATA},
            {VIDEO_REWARDED_320x480_ADMOB},
//            {VIDEO_REWARDED_320x480_MAX},
    };

    private static final String[][] VideoAdsWithAdConfiguration = {
            {VIDEO_INTERSTITIAL_320x480_IN_APP_WITH_AD_CONFIGURATION},
            {VIDEO_INTERSTITIAL_320x480_ADMOB_WITH_AD_CONFIGURATION},
            {VIDEO_INTERSTITIAL_320x480_GAM_WITH_AD_CONFIGURATION},
//////            {VIDEO_INTERSTITIAL_320x480_MAX_WITH_AD_CONFIGURATION},
            {VIDEO_REWARDED_320x480_IN_APP_WITH_AD_CONFIGURATION},
            {VIDEO_REWARDED_320x480_GAM_WITH_AD_CONFIGURATION},
            {VIDEO_REWARDED_320x480_ADMOB_WITH_AD_CONFIGURATION},
//            {VIDEO_REWARDED_320x480_MAX_WITH_AD_CONFIGURATION},
    };

    /* AD_NAME_TYPE*/
    @DataProvider(name = "adNameVideo")
    public static Object[][] provideVideoAd() {
        return VideoAds;
    }

    @DataProvider(name = "videoOriginalAds")
    public static Object[][] provideOriginalVideoAd() {
        return VideoAdsOriginal;
    }

    @DataProvider(name = "adNameVideoWithAdConfiguration")
    public static Object[][] provideVideoWithAdConfiguration() {
        return VideoAdsWithAdConfiguration;
    }

    @DataProvider(name = "adNameVideoReal")
    public static Object[][] provideVideoAdRealDevice() {
        return VideoAdsRealDevice;
    }

    private static final String[][] VideoRandom = {
            {VIDEO_INTERSTITIAL_320x480_GAM_RANDOM},
            {VIDEO_INTERSTITIAL_320x480_ADMOB_RANDOM},
//            {VIDEO_INTERSTITIAL_320x480_MAX_RANDOM},
//            {VIDEO_REWARDED_320x480_MAX_RANDOM},
            {VIDEO_REWARDED_320x480_ADMOB_RANDOM},
            {VIDEO_REWARDED_320x480_GAM_RANDOM},
    };

    /* AD_NAME_TYPE*/
    @DataProvider(name = "randomAdVideo")
    public static Object[][] provideVideoRandom() {
        return VideoRandom;
    }

    private static final String[][] VideoNoBids = {
            {VIDEO_INTERSTITIAL_320x480_NO_BID_ADMOB},
//            {VIDEO_INTERSTITIAL_320x480_NO_BID_MAX},
            {VIDEO_INTERSTITIAL_320x480_NO_BID_GAM_AD},
            {VIDEO_REWARDED_320x480_NO_BID_GAM},
            {VIDEO_REWARDED_320x480_NO_BID_ADMOB},
//            {VIDEO_REWARDED_320x480_NO_BID_MAX},
    };

    /* AD_NAME_TYPE*/
    @DataProvider(name = "noBidsVideo")
    public static Object[][] provideVideoNoBids() {
        return VideoNoBids;
    }

    private static final String[][] VideoInterstitial = {
            {VIDEO_INTERSTITIAL_320x480_IN_APP},
            {VIDEO_INTERSTITIAL_320x480_ADMOB},
//            {VIDEO_INTERSTITIAL_320x480_MAX},
            {VIDEO_INTERSTITIAL_320x480_GAM},
    };

    /* AD_NAME_TYPE*/
    @DataProvider(name = "videoInterstitialAdName")
    public static Object[][] provideVideo() {
        return VideoInterstitial;
    }

    private static final String[][] VideoInterstitialEndCard = {
            {VIDEO_INTERSTITIAL_320x480_ENDCARD}
    };

    /* AD_NAME_TYPE*/
    @DataProvider(name = "videoInterstitialEndCardAdName")
    public static Object[][] provideVideoEndCard() {
        return VideoInterstitialEndCard;
    }

    private static final String[][] VideoRewarded = {
            {VIDEO_REWARDED_320x480_IN_APP},
            {VIDEO_REWARDED_320x480_GAM_METADATA},
            {VIDEO_REWARDED_320x480_ADMOB},
//            {VIDEO_REWARDED_320x480_MAX},
    };

    /* AD_NAME_TYPE*/
    @DataProvider(name = "videoRewardedAdName")
    public static Object[][] provideVideoRewarded() {
        return VideoRewarded;
    }

    private static final String[][] VideoOutstream = {
            {VIDEO_OUTSTREAM_IN_APP},
            {VIDEO_OUTSTREAM_GAM},
    };

    /* AD_NAME_TYPE*/
    @DataProvider(name = "videoOutstreamAdName")
    public static Object[][] provideVideoOutstream() {
        return VideoOutstream;
    }

    private static final String[][] VideoFeed = {
            {VIDEO_OUTSTREAM_FEED_IN_APP},
            {VIDEO_OUTSTREAM_FEED_GAM},
    };

    /* AD_NAME_TYPE*/
    @DataProvider(name = "videoOutstreamFeedAdName")
    public static Object[][] provideVideoOutstreamFeed() {
        return VideoFeed;
    }


    private static final String[][] NativeAds = {
            {NATIVE_AD_ADMOB},
//            {NATIVE_AD_MAX},
            {NATIVE_AD_IN_APP},
            {NATIVE_AD_LINKS_IN_APP},

            {NATIVE_AD_GAM_CUSTOM_TEMPLATE},
            {NATIVE_AD_GAM_CUSTOM_TEMPLATE_GAD},
            {NATIVE_AD_GAM_UNIFIED_GAD},
            {NATIVE_AD_GAM_UNIFIED},

    };


    private static final String[][] NativeRequestAds = {
            {NATIVE_AD_ADMOB},
//            {NATIVE_AD_MAX},
            {NATIVE_AD_IN_APP},
            {NATIVE_AD_LINKS_IN_APP},
            {NATIVE_AD_FEED_IN_APP},

            {NATIVE_AD_FEED_GAM},
            {NATIVE_AD_GAM_CUSTOM_TEMPLATE},
            {NATIVE_AD_GAM_ORIGINAL},
            {NATIVE_AD_GAM_UNIFIED},

    };
    private static final String[][] NativeRequestAdsRealDevice = {
            {NATIVE_AD_ADMOB},
//            {NATIVE_AD_MAX},
            {NATIVE_AD_IN_APP},
            {NATIVE_AD_GAM_CUSTOM_TEMPLATE},
            {NATIVE_AD_GAM_UNIFIED},

    };
    private static final String[][] NativeNoBidsAds = {
            {NATIVE_AD_GAM_UNIFIED_GAD_NO_BIDS},
            {NATIVE_AD_GAM_CUSTOM_TEMPLATE_GAD_NO_BIDS},
            {NATIVE_AD_ADMOB_NO_BIDS},
//            {NATIVE_AD_MAX_NO_BIDS},

    };

    /* AD_NAME_TYPE*/
    @DataProvider(name = "nativeRequestAds")
    public static Object[][] provideNativeRequestAdsIos() {
        return NativeRequestAds;
    }

    @DataProvider(name = "nativeRequestAdsReal")
    public static Object[][] provideNativeRequestAdsRealDevice() {
        return NativeRequestAdsRealDevice;
    }

    @DataProvider(name = "nativeAds")
    public static Object[][] provideNativeAdsAndroid() {
        return NativeAds;
    }

    @DataProvider(name = "nativeNoBidsAds")
    public static Object[][] provideNativeNoBidsAdsAndroid() {
        return NativeNoBidsAds;
    }

    private static final String[][] SkadNetworkAds = {
            {BANNER_SKADNETWORK},
            {INTERSTITIAL_320x480_IN_APP_SKADN},
            {VIDEO_INTERSTITIAL_320x480_IN_APP_SKADN},
            {VIDEO_REWARDED_320x480_IN_APP_SKADN},
            {VIDEO_OUTSTREAM_IN_APP_SKADN},
    };

    @DataProvider(name = "SkadNetworkAds")
    public static Object[][] provideSkadNetworkAds() {
        return SkadNetworkAds;
    }

    private static final String[][] NoSkadNetworkAds = {
            // Rendering
            {NATIVE_AD_IN_APP},
            {BANNER_320x50_IN_APP},
            {INTERSTITIAL_320x480_IN_APP},
            {VIDEO_OUTSTREAM_IN_APP},
            {VIDEO_INTERSTITIAL_320x480_IN_APP},
            {VIDEO_REWARDED_320x480_IN_APP},
            // Original
            {BANNER_320x50_GAM_ORIGINAL_NO_SKADN},
            {BANNER_320x50_GAM_ORIGINAL},
            {INTERSTITIAL_320x480_GAM_ORIGINAL},
            {VIDEO_INTERSTITIAL_320x480_GAM_ORIGINAL},
            {VIDEO_REWARDED_320x480_GAM_ORIGINAL},
            {VIDEO_OUTSTREAM_GAM_ORIGINAL},
            {NATIVE_AD_GAM_ORIGINAL},
    };

    @DataProvider(name = "NoSkadNetworkAds")
    public static Object[][] provideNoSkadNetworkAds() {
        return NoSkadNetworkAds;
    }

    private static final String[][] FirstPartyDataAds = {
            {BANNER_320x50_IN_APP},
            {BANNER_320x50_ADMOB},
            {BANNER_320x50_GAM_ORIGINAL},
            {BANNER_320x50_GAM},
            {NATIVE_AD_ADMOB},
            {VIDEO_INTERSTITIAL_320x480_ADMOB},
            {VIDEO_INTERSTITIAL_320x480_GAM},
            {VIDEO_INTERSTITIAL_320x480_IN_APP},
    };

    @DataProvider(name = "FirstPartyDataAds")
    public static Object[][] provideFirstPartyDataAds() {
        return FirstPartyDataAds;
    }


}
