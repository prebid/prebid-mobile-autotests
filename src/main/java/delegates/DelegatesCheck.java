package delegates;

public interface DelegatesCheck {
    void checkAndroidBannerDelegates();

    void checkAndroidDisplayInterstitialDelegates() throws InterruptedException;

    void checkAndroidVideoInterstitialDelegates();

    void checkAndroidVideoRewardedDelegates();

    void checkAndroidVideoOutstreamDelegates();

    void checkAndroidNativeAdsDelegates(String prebidAd) throws InterruptedException;

    void checkIosBannerDelegates() throws InterruptedException;

    void checkIosDisplayInterstitialDelegates();

    void checkIosVideoInterstitialDelegates();

    void checkIosVideoRewardedDelegates();

    void checkIosVideoOutstreamDelegates();

    void checkIosNativeAdsDelegates(String prebidAd) throws InterruptedException;
}
