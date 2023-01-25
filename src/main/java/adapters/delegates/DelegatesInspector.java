package adapters.delegates;

public interface DelegatesInspector extends LoadDelegatesInspector, ClickDelegatesInspector {
    void checkBannerDelegates() throws InterruptedException;

    void checkDisplayInterstitialDelegates() throws InterruptedException;

    void checkVideoInterstitialDelegates() throws InterruptedException;

    void checkVideoRewardedDelegates() throws InterruptedException;

    void checkVideoOutstreamDelegates() throws InterruptedException;

    void checkNativeAdsDelegates(String prebidAd) throws InterruptedException;

}
