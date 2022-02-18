package appium.inAppBidding;

import org.testng.annotations.DataProvider;

import static appium.common.InAppTemplatesInit.*;

public class InAppDataProviders {

    private static final String[][] TCFv1Cases = {
        {CUSTOM_TCF_NO_GDPR_NO_CONSENT},
        {CUSTOM_TCF_GDPR0_NO_CONSENT},
        {CUSTOM_TCF_GDPR1_CONSENT}
    };

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
            {BANNER_320x50_MOPUB},
            {BANNER_320x50_ADMOB},
            {BANNER_728x90_IN_APP},
            {BANNER_728x90_GAM},
            {BANNER_728x90_MOPUB},
            {BANNER_MULTISIZE_IN_APP},
            {BANNER_MULTISIZE_GAM},
            {BANNER_MULTISIZE_MOPUB},
            {BANNER_300x250_IN_APP},
            {BANNER_300x250_GAM},
            {BANNER_300x250_ADMOB},
            {BANNER_ADAPTIVE_ADMOB},
            {BANNER_300x250_MOPUB}
    };
    private static final String[][] BannerRefreshAd = {
            {BANNER_320x50_IN_APP},
            {BANNER_320x50_ADMOB},
    };

    /* AD_NAME_TYPE*/
    @DataProvider(name = "adName")
    public static Object[][] provideBannerFormatAndSettings() {
        return BannerAd;
    }

    @DataProvider(name = "refreshAds")
    public static Object[][] provideBannerRefreshAds() {
        return BannerRefreshAd;
    }

    private static final String[][] BannerNoBids = {
            {BANNER_320x50_NO_BID_GAM_AD},
            {BANNER_320x50_NO_BID_MOPUB},
            {BANNER_320x50_NO_BID_IN_APP},
            {BANNER_320x50_NO_BID_ADMOB},
    };

    @DataProvider(name = "noBids")
    public static Object[][] provideBannerNoBids() {
        return BannerNoBids;
    }

    private static final String[][] BannerRandom = {
            {BANNER_320x50_GAM_RANDOM},
            {BANNER_320x50_MOPUB_RANDOM},
            {BANNER_320x50_ADMOB_RANDOM},
    };

    @DataProvider(name = "randomAd")
    public static Object[][] provideBannerRandom() {
        return BannerRandom;
    }

    @DataProvider(name = "bannerAds")
    public static Object[][] provideBanner() {
        return BannerAd;
    }

    private static final String[][] ServerBasedBanner = {
            {BANNER_320x50_IN_APP},
            {BANNER_320x50_GAM},
            {BANNER_320x50_MOPUB},
            {BANNER_320x50_ADMOB},
    };

    @DataProvider(name = "serverBasedBanner")
    public static Object[][] provideServerBasedBanner() {
        return ServerBasedBanner;
    }

    private static final String[][] InterstitialAds = {
            {INTERSTITIAL_320x480_IN_APP},
            {INTERSTITIAL_320x480_MOPUB},
            {INTERSTITIAL_320x480_ADMOB},
            {INTERSTITIAL_320x480_GAM}
    };

    @DataProvider(name = "interstitialAds")
    public static Object[][] provideInterstitial() {
        return InterstitialAds;
    }

    private static final String[][] InterstitialRandom = {
            {INTERSTITIAL_320x480_GAM_RANDOM},
            {INTERSTITIAL_320x480_ADMOB_RANDOM},
            {INTERSTITIAL_320x480_MOPUB_RANDOM},
    };

    @DataProvider(name = "randomAdInterstitial")
    public static Object[][] provideInterstitialRandom() {
        return InterstitialRandom;
    }

    private static final String[][] InterstitialNoBids = {
            {INTERSTITIAL_320x480_NO_BID_MOPUB},
            {INTERSTITIAL_320x480_NO_BID_ADMOB},
            {INTERSTITIAL_320x480_NO_BID_GAM},
    };

    @DataProvider(name = "noBidsInterstitial")
    public static Object[][] provideInterstitialNoBids() {
        return InterstitialNoBids;
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
            {MRAID_EXPAND_1_MOPUB},
            {MRAID_RESIZE_MOPUB},
            {MRAID_VIDEO_INTERSTITIAL_MOPUB}
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
            {MRAID_EXPAND_1_MOPUB},
            {MRAID_RESIZE_MOPUB}
    };

    @DataProvider(name = "adNameReload")
    public static Object[][] provideMraidReloadAd() {
        return MraidReloads;
    }

    private static final String[][] MraidExpand1 = {
            {MRAID_EXPAND_1_IN_APP},
            {MRAID_EXPAND_1_GAM},
            {MRAID_EXPAND_1_MOPUB}
    };

    @DataProvider(name = "mraidExpand1")
    public static Object[][] provideMraidExpand1Ad() {
        return MraidExpand1;
    }

    private static final String[][] MraidVideoInterstitial = {
            {MRAID_VIDEO_INTERSTITIAL_IN_APP},
            {MRAID_VIDEO_INTERSTITIAL_GAM},
            {MRAID_VIDEO_INTERSTITIAL_MOPUB}
    };

    @DataProvider(name = "videoInterstitialAd")
    public static Object[][] provideMraidVideoInterstitialAd() {
        return MraidVideoInterstitial;
    }

    private static final String[][] MraidResize = {
            {MRAID_RESIZE_IN_APP},
            {MRAID_RESIZE_GAM},
            {MRAID_RESIZE_MOPUB}
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
            {VIDEO_INTERSTITIAL_320x480_MOPUB},
            {VIDEO_INTERSTITIAL_320x480_ADMOB},
            {VIDEO_320x480_GAM},
            {VIDEO_REWARDED_320x480_IN_APP},
            {VIDEO_REWARDED_320x480_GAM_METADATA},
            {VIDEO_REWARDED_320x480_MOPUB},
            {VIDEO_REWARDED_320x480_ADMOB},
    };

    /* AD_NAME_TYPE*/
    @DataProvider(name = "adNameVideo")
    public static Object[][] provideVideoAd() {
        return VideoAds;
    }

    private static final String[][] VideoServerBasedAds = {
            {VIDEO_OUTSTREAM_ENDCARD},
            {VIDEO_REWARDED_320x480_IN_APP},
            {VIDEO_INTERSTITIAL_320x480_ENDCARD},
            {VIDEO_INTERSTITIAL_320x480_MOPUB},
            {VIDEO_INTERSTITIAL_320x480_ADMOB},
            {VIDEO_REWARDED_320x480_MOPUB},
            {VIDEO_REWARDED_320x480_ADMOB},
            {VIDEO_320x480_GAM},
            {VIDEO_REWARDED_320x480_GAM_METADATA},
    };

    /* AD_NAME_TYPE*/
    @DataProvider(name = "adNameVideoPrebid")
    public static Object[][] provideVideoServerBasedAds() {
        return VideoServerBasedAds;
    }

    private static final String[][] VideoRandom = {
            {VIDEO_320x480_MOPUB_RANDOM},
            {VIDEO_320x480_GAM_RANDOM},
            {VIDEO_INTERSTITIAL_320x480_ADMOB_RANDOM},
    };

    /* AD_NAME_TYPE*/
    @DataProvider(name = "randomAdVideo")
    public static Object[][] provideVideoRandom() {
        return VideoRandom;
    }

    private static final String[][] VideoNoBids = {
            {VIDEO_320x480_NO_BID_MOPUB},
            {VIDEO_INTERSTITIAL_320x480_NO_BID_ADMOB},
            {VIDEO_320x480_NO_BID_GAM_AD},
            {VIDEO_REWARDED_320x480_NO_BID_GAM},
            {VIDEO_REWARDED_320x480_NO_BID_MOPUB},
            {VIDEO_REWARDED_320x480_NO_BID_ADMOB},
    };

    /* AD_NAME_TYPE*/
    @DataProvider(name = "noBidsVideo")
    public static Object[][] provideVideoNoBids() {
        return VideoNoBids;
    }

    private static final String[][] VideoInterstitial = {
            {VIDEO_INTERSTITIAL_320x480_IN_APP},
            {VIDEO_INTERSTITIAL_320x480_ADMOB},
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
            {VIDEO_REWARDED_320x480_MOPUB},
            {VIDEO_REWARDED_320x480_ADMOB},
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


    private static final String[][] NativeMockedRequestAdsIos = {
            {NATIVE_AD_ADMOB},

            {NATIVE_AD_IN_APP},
            {NATIVE_AD_LINKS_IN_APP},
            {NATIVE_AD_FEED_IN_APP},

            {NATIVE_AD_MOPUB_PBM},
            {NATIVE_AD_MO_PUB_PBM_NIB},
            {NATIVE_AD_FEED_MOPUB_PBM},

            {NATIVE_AD_FEED_GAM},
            {NATIVE_AD_GAM_CUSTOM_TEMPLATE},
            {NATIVE_AD_GAM_UNIFIED},

    };
    private static final String[][] NativeMockedAdsIos = {
            {NATIVE_AD_ADMOB},
            {NATIVE_AD_IN_APP},
            {NATIVE_AD_LINKS_IN_APP},

            {NATIVE_AD_GAM_CUSTOM_TEMPLATE_GAD},
            {NATIVE_AD_GAM_CUSTOM_TEMPLATE},
            {NATIVE_AD_GAM_UNIFIED_GAD},
            {NATIVE_AD_GAM_UNIFIED},
            {NATIVE_AD_MOPUB_MP},
            {NATIVE_AD_MOPUB_PBM},

    };
    private static final String[][] NativeMockedRequestAdsAndroid = {
            {NATIVE_AD_ADMOB},

            {NATIVE_AD_IN_APP},
            {NATIVE_AD_FEED_IN_APP},
            {NATIVE_AD_LINKS_IN_APP},

            {NATIVE_AD_MOPUB},


            {NATIVE_AD_FEED_GAM},
            {NATIVE_AD_GAM_CUSTOM_TEMPLATE},
            {NATIVE_AD_GAM_UNIFIED},

    };
    private static final String[][] NativeMockedAdsAndroid = {
            {NATIVE_AD_ADMOB},

            {NATIVE_AD_IN_APP},
            {NATIVE_AD_LINKS_IN_APP},

            {NATIVE_AD_MOPUB},

            {NATIVE_AD_GAM_CUSTOM_TEMPLATE},
            {NATIVE_AD_GAM_CUSTOM_TEMPLATE_GAD},
            {NATIVE_AD_GAM_UNIFIED_GAD},
            {NATIVE_AD_GAM_UNIFIED},

    };

    private static final String[][] NativeMockedNoBidsAndroidAds = {
            {NATIVE_AD_GAM_UNIFIED_GAD_NO_BIDS},
            {NATIVE_AD_GAM_CUSTOM_TEMPLATE_GAD_NO_BIDS},
            {NATIVE_AD_FEED_MOPUB_MP_NO_BIDS},
            {NATIVE_AD_ADMOB_NO_BIDS},

    };
    private static final String[][] NativeMockedNoBidsIosAds = {
            {NATIVE_AD_GAM_UNIFIED_GAD_NO_BIDS},
            {NATIVE_AD_GAM_CUSTOM_TEMPLATE_GAD_NO_BIDS},
            {NATIVE_AD_FEED_MOPUB_MP_NO_BIDS},
            {NATIVE_AD_MOPUB_MP_NO_BIDS},
            {NATIVE_AD_ADMOB_GAD_NO_BIDS},

    };

    /* AD_NAME_TYPE*/
    @DataProvider(name = "nativeMockedRequestAdsIos")
    public static Object[][] provideNativeMockedRequestAdsIos() {
        return NativeMockedRequestAdsIos;
    }

    @DataProvider(name = "nativeMockedAdsIos")
    public static Object[][] provideNativeMockedAdsIos() {
        return NativeMockedAdsIos;
    }

    @DataProvider(name = "nativeMockedRequestAdsAndroid")
    public static Object[][] provideNativeMockedRequestAdsAndroid() {
        return NativeMockedRequestAdsAndroid;
    }

    @DataProvider(name = "nativeMockedAdsAndroid")
    public static Object[][] provideNativeMockedAdsAndroid() {
        return NativeMockedAdsAndroid;
    }

    @DataProvider(name = "nativeMockedNoBidsAdsIos")
    public static Object[][] provideNativeMockedNoBidsAdsIos() {
        return NativeMockedNoBidsIosAds;
    }

    @DataProvider(name = "nativeMockedNoBidsAdsAndroid")
    public static Object[][] provideNativeMockedNoBidsAdsAndroid() {
        return NativeMockedNoBidsAndroidAds;
    }
}
