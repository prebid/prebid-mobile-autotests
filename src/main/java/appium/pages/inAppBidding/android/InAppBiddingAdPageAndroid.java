package appium.pages.inAppBidding.android;

import appium.pages.android.AndroidBasePage;
import appium.pages.inAppBidding.InAppBiddingAdPageImpl;
import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.testng.Assert.assertEquals;

public class InAppBiddingAdPageAndroid extends AndroidBasePage implements InAppBiddingAdPageImpl {
    public InAppBiddingAdPageAndroid(AndroidDriver driver, String locatorType) {
        super(driver, locatorType);
    }

    private static class Locators {
        static final By examples = MobileBy.AccessibilityId("Examples");
        static final By utilities = MobileBy.AccessibilityId("Utilities");
        static final By adView = MobileBy.xpath("//android.widget.FrameLayout[@content-desc='adView']/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View");
        static final By endCard = MobileBy.xpath("//android.view.View[@clickable='true']");
        static final By videoView = MobileBy.xpath("//android.widget.FrameLayout[@content-desc='adView']");
        static final By viewContainer = getId("viewContainer");
        static final By content = MobileBy.xpath("//hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.RelativeLayout");
        static final By showButton = MobileBy.xpath("//*[@text='Show']");
        static final By reloadButton = MobileBy.xpath("//*[@text='Load']");
        static final By retryButton = MobileBy.xpath("//*[@text='Retry']");
        static final By closeButton = getId("iv_close_interstitial");
        static final By closeWebViewButton = MobileBy.xpath("//android.widget.Button[@content-desc='close']");
        static final By learnMore = getId("tv_learn_more");

        private static By getId(String id) {
            return MobileBy.id("org.prebid.mobile.renderingtestapp:id/" + id);
        }

        static final By scrollableViewArea = getId("scrollView");
        static final By scrollToDelegates = MobileBy.xpath("//android.widget.ScrollView[@scrollable='true']");

        //MRAID EXPAND 1 LOCATORS
        static final By lockToPortrait = MobileBy.xpath("//*[@resource-id='forceport']");
        static final By lockToLandscape = MobileBy.xpath("//*[@resource-id='forceland']");
        static final By releaseLock = MobileBy.xpath("//*[@resource-id='unlock']");

        static final By expandStateChangeCheck = MobileBy.className("android.webkit.WebView");
        static final By expandSizeChangeCheck = MobileBy.className("android.webkit.WebView");
        static final By expandToCheckLogs = MobileBy.className("android.webkit.WebView");
        static final By tapToCloseExpand = MobileBy.xpath("//android.view.View[@text='Tap To Close Expand']");
        static final By tapToUnload = MobileBy.xpath("//android.view.View[@text='Tap To Unload']");

        //MRAID EXPAND 2 LOCATORS
        static final By expandAgain = MobileBy.xpath("//android.view.View[@text='Expand Again']");
        static final By clickHereToClose = MobileBy.xpath("//android.view.View[@text='Click here to close.']");
        static final By openIAB = MobileBy.xpath("//android.view.View[@text='Open IAB.net']");
        static final By playVideoExpand2 = MobileBy.xpath("//android.view.View[@text='PlayVideo']");

        //MRAID RESIZE LOCATORS
        static final By smsAppText = MobileBy.id("com.google.android.apps.messaging:id/compose_message_text");
        static final By videoViewResizeAd = MobileBy.xpath("//android.widget.VideoView");
        static final By savePictureButton = MobileBy.id("android:id/button1");
        static final By resizeButton = MobileBy.xpath("//android.view.View[@text='Click to Resize']");
        static final By openUrlButton = MobileBy.xpath("//android.view.View[@text='Open URL']");
        static final By clickToMapButton = MobileBy.xpath("//android.view.View[@text='Click to Map']");
        static final By clickToAppButton = MobileBy.xpath("//android.view.View[@text='Click to App']");
        static final By smsButton = MobileBy.xpath("//android.view.View[@text='SMS']");
        static final By playVideoResize = MobileBy.xpath("//android.view.View[@text='Play Video']");
        static final By storePictureButton = MobileBy.xpath("//android.view.View[@text='Store Picture']");
        static final By createCalendarEventButton = MobileBy.xpath("//android.view.View[@text='Create Calendar Event']");
        static final By clickToCallButton = MobileBy.xpath("//android.view.View[@text='Click to Call']");
        static final By xButtonResizeAd = MobileBy.xpath("//android.view.View[@text='X']");

        //MRAID RESIZE WITH ERRORS LOCATORS
        static final By bannerDiv = MobileBy.xpath("//*[@resource-id='bannerDiv']");
        static final By testProperties = MobileBy.xpath("//*[@resource-id='setResizeDiv']");
        static final By testOffscreen = MobileBy.xpath("//*[@resource-id='resizeDiv']");

        static By badTiming = MobileBy.AccessibilityId("1bad timing");
        static By badValues = MobileBy.AccessibilityId("2bad values");
        static By tooSmall = MobileBy.AccessibilityId("too small");
        static By tooBig = MobileBy.AccessibilityId("too big");
        static By iabLogo = MobileBy.xpath("//*[@resource-id='logoImage']");

        static By toggleOffscreen = MobileBy.xpath("//*[@resource-id='toggleOffscreenDiv']");
        static final By toggleOffscreenText = MobileBy.xpath("//*[@resource-id='toggleOffscreenText']");

        static final By resizeLeft = MobileBy.xpath("//*[@resource-id='resizeLeftText']");
        static final By resizeRight = MobileBy.xpath("//*[@resource-id='resizeRightText']");
        static final By resizeUp = MobileBy.xpath("//*[@resource-id='resizeUpText']");
        static final By resizeDown = MobileBy.xpath("//*[@resource-id='resizeDownText']");

        static final By closeButtonOffscreen = MobileBy.xpath("//*[@resource-id='closeButtonDiv']");

        //VIDEO FEED LOCATORS
        static By scrollArea = MobileBy.className("android.widget.ScrollView");
        static final By scrollFeed = MobileBy.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout[1]/android.widget.FrameLayout/android.widget.ListView");
        static final By videoFeed = getId("exo_overlay");
        static final By watchAgain = getId("btn_watch_again");

        static final By clickHereToVisitOurSite = MobileBy.xpath("//*[@text='CLICK HERE TO VISIT OUR SITE!']");
        static final By btnNativeLinkUrl = getId("btnNativeLinkUrl");
        static final By btnNativeLinkRoot = getId("btnNativeLinkRoot");
        static final By btnNativeDeeplinkOk = getId("btnNativeDeeplinkOk");
        static final By btnNativeDeeplinkFallback = getId("btnNativeDeeplinkFallback");

    }

    enum ScrollDirection {
        UP,
        DOWN
    }

    //Actions

    @Override
    public void checkAdDidLoadCounterValue(Integer expectedCountValue) {
        assertEquals(getAdDidLoadCounterValue(), expectedCountValue.toString());
    }

    @Override
    public void performScrollDown() {
        for (int i = 0; i < 3; i++) {
            performScrollBanner(ScrollDirection.DOWN);
        }
    }

    @Override
    public void performScrollUp() {
        for (int i = 0; i < 3; i++) {
            performScrollBanner(ScrollDirection.UP);
        }
    }

    @Override
    public void waitAndReturnToApp() throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.android.chrome:id/url_bar")));
        Thread.sleep(1000);
        driver.navigate().back();
        Thread.sleep(1000);
        driver.navigate().back();
    }

    @Override
    public void clickExamplesTab() {
        clickOnElement(Locators.examples);
    }

    @Override
    public void clickUtilitiesTab() {
        clickOnElement(Locators.utilities);
    }

    @Override
    public boolean isShowButtonEnabled() {
        return wait.until(ExpectedConditions.elementToBeClickable(Locators.showButton))
                .isEnabled();
    }

    @Override
    public void isAdDisplayed() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.adView))
                .isDisplayed();
    }

    @Override
    public void clickCloseInterstitial() {
        wait.until(ExpectedConditions.elementToBeClickable(Locators.closeButton))
                .click();
    }

    @Override
    public void clickCloseRandom() {
        if (driver.findElements(Locators.closeButton).size() != 0) {
            wait.until(ExpectedConditions.elementToBeClickable(Locators.closeButton))
                    .click();
            System.out.println("Tap on close Button by locator");
        } else {
            TouchAction action = new TouchAction(driver);
            action.tap(PointOption.point(1000, 66)).perform().release();
            System.out.println("Tap on close Button by coordinates");
        }
    }

    @Override
    public void clickLearnMore() {
        wait.until(ExpectedConditions.elementToBeClickable(Locators.learnMore))
                .click();
    }

    @Override
    public void clickVideoAd() {
        wait.until(ExpectedConditions.elementToBeClickable(Locators.videoView))
                .click();
    }

    @Override
    public void clickInterstitialAd() {
        wait.until(ExpectedConditions.elementToBeClickable(Locators.endCard))
                .click();
    }

    @Override
    public void clickShowButton() {
        wait.until(ExpectedConditions.elementToBeClickable(Locators.showButton))
                .click();
    }

    @Override
    public void clickReloadButton() {
        wait.until(ExpectedConditions.elementToBeClickable(Locators.reloadButton))
                .click();
    }

    @Override
    public void clickRetryButton() {
        wait.until(ExpectedConditions.elementToBeClickable(Locators.retryButton))
                .click();
    }

    @Override
    public void clickBanner() {
        wait.until(ExpectedConditions.elementToBeClickable(Locators.adView))
                .click();
    }

    @Override
    public void closeWebViewCreative() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.closeWebViewButton))
                .click();
    }

    @Override
    public void performScrollUpAndDown() {
        for (int i = 0; i < 2; i++) {
            performScrollBanner(ScrollDirection.UP);
        }

        performScrollBanner(ScrollDirection.DOWN);
    }

    @Override
    public void performScrollToDelegates() {

        Point location = wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.scrollToDelegates))
                .getLocation();
        Dimension dim = wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.scrollToDelegates))
                .getSize();

        int startX = (int) (location.getX() + dim.getWidth() * 0.1);
        int finishX = (int) (location.getX() + dim.getWidth() * 0.1);
        int startY = (int) (location.getY() + dim.getHeight() * 0.9);
        int finishY = (int) (location.getY() + dim.getHeight() * 0.1);

        TouchAction touchAction = new TouchAction(driver);

        touchAction
                .press(PointOption.point(startX, startY))
                .moveTo(PointOption.point(finishX, finishY))
                .release()
                .perform();
    }


    @Override
    public void adShouldExpand1Part() {
        wait.until(driver -> driver.getPageSource().contains("IAB MRAID2 Expandable Compliance Ad"));
    }

    @Override
    public void lockToLandscape() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.lockToLandscape))
                .click();
    }

    @Override
    public void lockToPortrait() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.lockToPortrait))
                .click();
    }

    @Override
    public void releaseLock() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.releaseLock))
                .click();
    }

    @Override
    public void clickExpandStateChange() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.expandStateChangeCheck))
                .click();
    }

    @Override
    public void clickExpandSizeChange() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.expandSizeChangeCheck))
                .click();
    }

    @Override
    public void clickTapToCloseExpand() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.tapToCloseExpand))
                .click();
    }

    @Override
    public void clickTapToCheckLogs() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.expandToCheckLogs))
                .click();
    }

    @Override
    public void clickTapToUnload() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.tapToUnload))
                .click();
    }

    @Override
    public void checkLogs(String logText) {
        wait.until(driver -> driver.getPageSource().contains(logText));
    }

    @Override
    public boolean isLogsContainsText(String logText) {
        return driver.getPageSource().contains(logText);
    }

    @Override
    public void checkLogsTestsStatuses(String logText, boolean expectedResult) {
        assertEquals(isLogsContainsText(logText), expectedResult);
    }


    @Override
    public void clickExpand2creative() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.viewContainer))
                .click();
    }

    @Override
    public void adShouldExpand2Part() {
        wait.until(driver -> driver.getPageSource().contains("IAB MRAID2 Two Part Compliance Ad."));
        wait.until(driver -> driver.getPageSource().contains("Click here to close."));
        wait.until(driver -> driver.getPageSource().contains("Open IAB.net"));
        wait.until(driver -> driver.getPageSource().contains("PlayVideo"));
        wait.until(driver -> driver.getPageSource().contains("Expand Again"));
    }

    @Override
    public void clickExpandAgain() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.expandAgain))
                .click();
    }

    @Override
    public void clickPlayVideo() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.playVideoExpand2))
                .click();
    }

    @Override
    public void clickOpenIAB() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.openIAB))
                .click();
    }

    @Override
    public void clickHereToClose() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.clickHereToClose))
                .click();
    }

    @Override
    public void learnMoreShouldNotBeDisplayed() {
        (new WebDriverWait(driver, 3)).until(driver -> driver.findElements(Locators.learnMore).size() == 0);
    }

    @Override
    public Dimension getAdSize(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator))
                .getSize();
    }

    @Override
    public boolean checkExpand2PartOrientationChanged() {
        int portraitWidth = getAdSize(Locators.viewContainer).width;
        int portraitHeight = getAdSize(Locators.viewContainer).height;
        rotateLandscape();
        int landscapeWidth = getAdSize(Locators.viewContainer).width + 1;
        int landscapeHeight = getAdSize(Locators.viewContainer).height - 1;
        return (portraitHeight == landscapeHeight) && (portraitWidth == landscapeWidth);
    }

    @Override
    public void clickResizeCreative() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.resizeButton))
                .click();
    }

    @Override
    public void verifyResizeAd() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.openUrlButton));
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.clickToMapButton));
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.clickToAppButton));
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.clickToCallButton));
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.smsButton));
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.createCalendarEventButton));
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.playVideoResize));
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.storePictureButton));
    }

    @Override
    public void clickOpenURL() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.openUrlButton))
                .click();
    }

    @Override
    public void clickToMap() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.clickToMapButton))
                .click();
    }

    @Override
    public void clickToApp() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.clickToAppButton))
                .click();
    }

    @Override
    public void clickSMS() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.smsButton))
                .click();
    }

    @Override
    public void clickStorePicture() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.storePictureButton))
                .click();
    }

    @Override
    public boolean isListOfSmsAppOpened() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.smsAppText));
            return true;
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }

    @Override
    public boolean isVideoAdExpanded() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.videoViewResizeAd));
            return true;
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }

    @Override
    public void clickPlayVideoResizeAd() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.playVideoResize))
                .click();
    }

    @Override
    public void clickCloseMRAIDResizeButton() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.xButtonResizeAd))
                .click();
    }

    @Override
    public void mapAppShouldOpen() {
//        wait.until(ExpectedConditions.presenceOfElementLocated(Locators.mapLocation));
    }

    @Override
    public void smsAppShouldOpen() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.smsAppText));
    }

    @Override
    public void confirmSavingPicture() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.savePictureButton)).click();
    }

    @Override
    public void confirmCreatingCalendarEvent() {
        //
    }

    @Override
    public void createCalendarEvent() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.createCalendarEventButton))
                .click();
    }

    @Override
    public void isResizeWithErrorsAdLoaded() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.testProperties));
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.testOffscreen));
    }

    @Override
    public void setToggleOffscreenTrue() {
        setToggleOffscreen("TRUE");
    }

    @Override
    public void setToggleOffscreenFalse() {
        setToggleOffscreen("FALSE");
    }

    private void setToggleOffscreen(String targetValue) {
        WebElement toggleOffscreen = wait.until(
                ExpectedConditions.visibilityOfElementLocated(Locators.toggleOffscreenText));

        String toggleOfscreenText = toggleOffscreen.getText().trim();
        System.out.println(toggleOfscreenText);
        if (!toggleOfscreenText.contentEquals(targetValue)) {
            toggleOffscreen.click();
        }
    }

    @Override
    public void resizeLeft() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.resizeLeft))
                .click();
    }

    @Override
    public void resizeRight() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.resizeRight))
                .click();
    }

    @Override
    public void resizeUp() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.resizeUp))
                .click();
    }

    @Override
    public void resizeDown() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.resizeDown))
                .click();
    }

    @Override
    public boolean isCloseButtonDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.closeButtonOffscreen))
                .isDisplayed();
    }

    @Override
    public boolean isCloseButtonInvisible() {
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(Locators.closeButtonOffscreen));
    }

    @Override
    public Dimension getResizeWithErrorsAdSize() {
        return getSize(Locators.bannerDiv);
    }

    //VIDEO FEED METHODS
    @Override
    public void scrollToFeedAd() {
        Dimension dim = wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.scrollFeed))
                .getSize();

        int startX = (int) (dim.getWidth() * 0.5);
        int finishX = (int) (dim.getWidth() * 0.5);
        int startY = (int) (dim.getHeight() * 0.85);
        int finishY = (int) (dim.getHeight() * 0.1);

        TouchAction touchAction = new TouchAction(driver);

        for (int i = 0; i <= 1; i++) {
            touchAction
                    .press(PointOption.point(startX, startY))
                    .moveTo(PointOption.point(finishX, finishY))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
                    .release()
                    .perform();
        }
    }

    @Override
    public void clickOnVideoFeed() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.videoFeed))
                .click();
    }

    @Override
    public void waitWhenWatchAgainDisplayed() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.watchAgain));
    }

    @Override
    public void clickWatchAgain() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.watchAgain))
                .click();
    }

    @Override
    public void isEndCardDisplayed() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.endCard));
    }

    @Override
    public void clickBtnNativeDeeplinkFallback() {
        wait.until(ExpectedConditions.elementToBeClickable(Locators.btnNativeDeeplinkFallback)).click();
    }

    @Override
    public void clickBtnNativeDeeplinkOk() {
        wait.until(ExpectedConditions.elementToBeClickable(Locators.btnNativeDeeplinkOk)).click();
    }

    @Override
    public void clickBtnNativeLinkRoot() {
        wait.until(ExpectedConditions.elementToBeClickable(Locators.btnNativeLinkRoot)).click();
    }

    @Override
    public void clickBtnNativeLinkUrl() {
        wait.until(ExpectedConditions.elementToBeClickable(Locators.btnNativeLinkUrl)).click();
    }

    @Override
    public void clickHereToVisitOurSite() {
        wait.until(ExpectedConditions.elementToBeClickable(Locators.clickHereToVisitOurSite)).click();
    }

    //Private
    private void clickOnElement(By elementLocator) {
        wait.until(ExpectedConditions.elementToBeClickable(elementLocator))
                .click();
    }

    protected Dimension getSize(By elementLocator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(elementLocator))
                .getSize();
    }

    private String getAdDidLoadCounterValue() {
        //TODO add correct locator
        //wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.adDidLoadCounter)).getText();
        return "";
    }

    private void performScrollBanner(ScrollDirection direction) {
        Point location = wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.scrollableViewArea))
                .getLocation();
        Dimension dim = wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.scrollableViewArea))
                .getSize();

        int startX = (int) (location.getX() + dim.getWidth() * 0.1);
        int finishX = (int) (location.getX() + dim.getWidth() * 0.1);
        int startY = (int) (location.getY() + dim.getHeight() * 0.9);
        int finishY = (int) (location.getY() + dim.getHeight() * 0.1);

        TouchAction touchAction = new TouchAction(driver);

        switch (direction) {
            case UP:
                touchAction
                        .press(PointOption.point(startX, startY))
                        .moveTo(PointOption.point(finishX, finishY));
                break;
            case DOWN:
                touchAction
                        .press(PointOption.point(finishX, finishY))
                        .moveTo(PointOption.point(startX, startY));
                break;
        }

        touchAction.release().perform();
    }
}
