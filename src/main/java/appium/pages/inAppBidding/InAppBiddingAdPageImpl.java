package appium.pages.inAppBidding;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;

public interface InAppBiddingAdPageImpl {
    // Actions
    void clickShowButton();

    void clickExamplesTab();

    void clickUtilitiesTab();

    void clickReloadButton();

    void clickStopRefreshButton();

    void clickRetryButton();

    void performScrollToDelegates();

    void clickBanner();

    void clickSkipButton();

    void isAdDisplayed();

    void waitAndReturnToApp() throws InterruptedException;

    void clickCloseInterstitial();

    void clickCloseRandom() throws InterruptedException;

    void clickInterstitialAd();

    void clickVideoAd();

    void isEndCardDisplayed();

    boolean isShowButtonEnabled();

    void clickLearnMore();

    void performScrollDown();

    void performScrollUp();

    void checkAdDidLoadCounterValue(Integer expectedCountValue) throws InterruptedException;

    void closeWebViewCreative();

    void adShouldExpand1Part();

    void lockToLandscape();

    void lockToPortrait();

    void releaseLock();

    void clickExpandStateChange();

    void clickExpandSizeChange();

    void clickTapToCloseExpand();

    void clickTapToCheckLogs();

    void clickTapToUnload();

    void checkLogs(String logText);

    boolean isLogsContainsText(String logText);

    void checkLogsTestsStatuses(String logText, boolean expectedResult);

    void performScrollUpAndDown();

    boolean checkExpand2PartOrientationChanged();

    Dimension getAdSize(By locator);

    void clickExpand2creative();

    void learnMoreShouldNotBeDisplayed();

    void clickHereToClose();

    void clickOpenIAB();

    void clickPlayVideo();

    void clickExpandAgain();

    void adShouldExpand2Part();

    void clickResizeCreative();

    void verifyResizeAd();

    void clickOpenURL();

    void clickToMap();

    void clickToApp();

    void clickSMS();

    void clickStorePicture();

    boolean isListOfSmsAppOpened();

    boolean isVideoAdExpanded();

    void clickPlayVideoResizeAd();

    void createCalendarEvent();

    void clickCloseMRAIDResizeButton();

    void mapAppShouldOpen();

    void smsAppShouldOpen();

    void confirmSavingPicture();

    void confirmCreatingCalendarEvent();

    Dimension getResizeWithErrorsAdSize();

    boolean isCloseButtonInvisible();

    boolean isCloseButtonDisplayed();

    void resizeDown();

    void resizeUp();

    void resizeRight();

    void resizeLeft();

    void setToggleOffscreenFalse();

    void setToggleOffscreenTrue();

    void isResizeWithErrorsAdLoaded();

    void scrollToFeedAd() throws InterruptedException;

    void clickOnVideoFeed();

    void waitWhenWatchAgainDisplayed();

    void clickWatchAgain();

    void clickBtnNativeDeeplinkFallback();

    void clickBtnNativeDeeplinkOk();

    void clickBtnNativeLinkRoot();

    void clickBtnNativeLinkUrl();

    void clickHereToVisitOurSite();

    void clickTvBody();

    void clickButtonCallToAction();

}
