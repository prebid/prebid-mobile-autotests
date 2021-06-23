package appium.pages.inAppBidding;

import appium.common.InAppBiddingTestEnvironment;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;

public interface InAppBiddingHomePageImpl {

    // Pages
    InAppBiddingAdPageImpl goToAd(String prebidAd) throws InterruptedException;

    // Actions

    void goToAdExamples();

    void sleep(Integer seconds) throws InterruptedException;

    boolean isSearchFieldDisplayed();

    void relaunchApp() throws InterruptedException;

    void goToUtilities();

    void clickRequestTrackingAuthorization();

    void clickAppNotToTrack() throws InterruptedException;

    void clickAllow() throws InterruptedException;

    void turnOnMockServerSwitcher();

    void turnOffMockServerSwitcher();

    void resetApp();

    void turnOffCustomConfig();

    void rotatePortrait();

    void rotateLandscape();

    ScreenOrientation getOrientation();

    void turnOnCustomConfig();

    void setAutoRefreshDelay(Integer refreshDelay);

    void clickLoadAdConfig();

    boolean isDelegateEnabledAfterDelayTime(By sdkEvent, int delay);

    void isDelegateEnabled(InAppBiddingTestEnvironment.InAppBiddingDelegates delegate);

    void isDelegateDisabled(InAppBiddingTestEnvironment.InAppBiddingDelegates delegate);

    void runAppInBackground(Integer seconds) throws InterruptedException;

    void clickBack();

    void openInBrowser();

    void clickCloseButtonClickThroughBrowser();
}
