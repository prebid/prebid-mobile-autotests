package appium.pages.inAppBidding.ios;

import appium.pages.inAppBidding.InAppBiddingAdPageImpl;
import appium.pages.ios.IOSBasePage;
import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;

import static org.testng.Assert.assertEquals;

public class InAppBiddingAdPageIOS extends IOSBasePage implements InAppBiddingAdPageImpl {

    public InAppBiddingAdPageIOS(IOSDriver driver) {
        super(driver);
    }

    private static class Locators {

        static final By examples = MobileBy.AccessibilityId("Examples");
        static final By utilities = MobileBy.AccessibilityId("Utilities");
        static final By adView = MobileBy.AccessibilityId("PBMAdView");
        static final By webView = MobileBy.AccessibilityId("PBMWebView");
        static final By scrollView = MobileBy.className("XCUIElementTypeScrollView");
        static final By videoView = MobileBy.AccessibilityId("PBMVideoView");
        static final By mraidVideoContainer = MobileBy.xpath("//XCUIElementTypeApplication[@name=\"PrebidMobileDemoRendering\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther");
        static final By showButton = MobileBy.xpath("//XCUIElementTypeButton[@name='Show']");
        static final By reloadButton = MobileBy.xpath("//XCUIElementTypeStaticText[@name='[Reload]']");
        static final By retryButton = MobileBy.xpath("//XCUIElementTypeStaticText[@name='[Retry]']");
        static final By closeButton = MobileBy.AccessibilityId("PBM Close");
        static final By closeButtonVideo = MobileBy.AccessibilityId("Close ad");
        static final By closeButtonInterstitial = MobileBy.AccessibilityId("Close Advertisement");
        static final By learnMore = MobileBy.AccessibilityId("Learn More");
        static final By closeWebViewButton = MobileBy.AccessibilityId("PBMCloseButtonClickThroughBrowser");
        static final By adDidLoadCounter = MobileBy.xpath("(//XCUIElementTypeStaticText[@name=' - '])[1]/following-sibling::*[1]");

        //TODO divide locators to separate page class
        static By expand1PartCreative = MobileBy.AccessibilityId("Expand!");
        static final By expand1PartAdText = MobileBy.AccessibilityId("IAB MRAID2 Expandable Compliance Ad.");
        static By lockToPortrait = MobileBy.AccessibilityId("Lock to Portrait");
        static final By lockToLandscape = MobileBy.AccessibilityId("Lock to Landscape");
        static final By releaseLock = MobileBy.AccessibilityId("Release Lock");

        static By rotateToPortraitWebviewId = MobileBy.id("forceland");
        static String rotateToPortraitText = "Rotate To Portrait";

        static final By expandStateChangeCheck = MobileBy.AccessibilityId("Tap For Expand/stateChange Check");
        static final By expandSizeChangeCheck = MobileBy.AccessibilityId("Tap For Expand/sizeChange Check");
        static final By expandToCheckLogs = MobileBy.AccessibilityId("Tap To Check Logs");
        static final By tapToCloseExpand = MobileBy.AccessibilityId("Tap To Close Expand");
        static final By tapToUnload = MobileBy.AccessibilityId("Tap To Unload");

        //MRAID EXPAND 2 LOCATORS
        static final By expand2partCreative = MobileBy.AccessibilityId("Two Part Expand");
        static final By expand2partAdText = MobileBy.AccessibilityId("IAB MRAID2 Two Part Compliance Ad.");
        static final By expandAgain = MobileBy.AccessibilityId("Expand Again");
        static final By playVideo = MobileBy.AccessibilityId("PlayVideo");
        static final By openIAB = MobileBy.AccessibilityId("Open IAB.net");
        static final By clickHereToClose = MobileBy.AccessibilityId("PBM Close");

        //MRAID RESIZE LOCATORS
        static final By resizeButton = MobileBy.AccessibilityId("Click to Resize");
        static final By openUrlButton = MobileBy.AccessibilityId("Open URL");
        static final By clickToMapButton = MobileBy.AccessibilityId("Click to Map");
        static final By clickToAppButton = MobileBy.AccessibilityId("Click to App");
        static final By playVideoButton = MobileBy.AccessibilityId("Play Video");
        static final By smsButton = MobileBy.AccessibilityId("SMS");
        static final By storePictureButton = MobileBy.AccessibilityId("Store Picture");
        static final By createCalendarEventButton = MobileBy.AccessibilityId("Create Calendar Event");
        static final By clickToCallButton = MobileBy.AccessibilityId("Click to Call");
        static final By xButtonResizeAd = MobileBy.AccessibilityId("X");
        static final By mapLocation = MobileBy.AccessibilityId("Your location");
        static By smsMessage = By.name("New Message");
        static By smsException = By.id("com.apple.MobileSMS.CKRecipientAvailabilityTimeout");

        //MRAID RESIZE WITH ERRORS LOCATORS
        static final By testProperties = MobileBy.AccessibilityId("Test properties:");
        static final By testOffscreen = MobileBy.AccessibilityId("Test offScreen:");
        static By propertiesLabel = MobileBy.AccessibilityId("Test properties:");
        static By offscreenLabel = MobileBy.AccessibilityId("Test offScreen:");

        static By clear = MobileBy.AccessibilityId("clear");
        static final By toggleOffScreenTrue = MobileBy.AccessibilityId("TRUE");
        static final By toggleOffScreenFalse = MobileBy.AccessibilityId("FALSE");

        static By badTiming = MobileBy.AccessibilityId("bad timing");
        static By badValues = MobileBy.AccessibilityId("bad values");
        static By tooSmall = MobileBy.AccessibilityId("small");
        static By tooBig = MobileBy.AccessibilityId("big");

        static final By resizeLeft = MobileBy.AccessibilityId("←");
        static final By resizeRight = MobileBy.AccessibilityId("→");
        static final By resizeUp = MobileBy.AccessibilityId("↑");
        static final By resizeDown = MobileBy.AccessibilityId("↓");
        static final By closeButtonOffscreen = MobileBy.AccessibilityId("X");

        //VIDEO FEED LOCATORS
        static final By watchAgain = MobileBy.AccessibilityId("Watch Again");
        static final By feedTable = MobileBy.className("XCUIElementTypeTable");
        static final By video300x250Feed = MobileBy.xpath("//XCUIElementTypeCell[position()=4]");

        //NATIVE LOCATORS
        static final By clickHereToVisitOurSite = MobileBy.xpath("//XCUIElementTypeButton[@name='Click here to visit our site!']");
        static final By btnNativeLinkUrl = MobileBy.xpath("//XCUIElementTypeButton[@name='Link URL']");
        static final By btnNativeLinkRoot = MobileBy.xpath("//XCUIElementTypeButton[@name='Link Root']");
        static final By btnNativeDeeplinkOk = MobileBy.xpath("//XCUIElementTypeButton[@name='Deeplink OK']");
        static final By btnNativeDeeplinkFallback = MobileBy.xpath("//XCUIElementTypeButton[@name='Deeplink Fallback']");

    }

    //Actions

    @Override
    public void checkAdDidLoadCounterValue(Integer expectedCountValue) throws InterruptedException {
        Thread.sleep(1000);
        assertEquals(getAdDidLoadCounterValue(), expectedCountValue.toString());
    }

    private String getAdDidLoadCounterValue() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.adDidLoadCounter)).getText();
    }

    @Override
    public void waitAndReturnToApp() throws InterruptedException {

        // This method should use a separate class for the browser page
        Thread.sleep(1000);
        TouchAction action = new TouchAction(driver);
        action.longPress(PointOption.point(0, 0)).perform().release();
        Thread.sleep(1000);
    }

    @Override
    public void clickCloseInterstitial() {
        wait.until(ExpectedConditions.elementToBeClickable(Locators.closeButton))
                .click();
    }

    @Override
    public void clickCloseRandom() throws InterruptedException {
        //Sleep needed for load DOM xml of locators on the screen
        Thread.sleep(2000);
        if (driver.findElements(Locators.closeButtonVideo).size() != 0) {
            System.out.println("press mopub close video");
            wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.closeButtonVideo)).click();
        } else if (driver.findElements(Locators.closeButton).size() != 0) {
            System.out.println("press inApp close video");
            wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.closeButton)).click();
        } else if (driver.findElements(Locators.closeButtonInterstitial).size()!=0){
            System.out.println("press by coordinates");
            wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.closeButtonInterstitial)).click();
            if (!isShowButtonEnabled()) {
                new TouchAction(driver)
                        .press(PointOption.point(382, 68))
                        .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
                        .perform();
            }
        }
    }

    @Override
    public void performScrollToDelegates() {

    }

    @Override
    public void clickVideoAd() {
        wait.until(ExpectedConditions.elementToBeClickable(Locators.videoView))
                .click();
    }

    @Override
    public void clickLearnMore() {
        wait.until(ExpectedConditions.elementToBeClickable(Locators.learnMore))
                .click();
    }

    @Override
    public void clickInterstitialAd() {
        (new WebDriverWait(driver, 20)).until(ExpectedConditions.elementToBeClickable(Locators.webView))
                .click();
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
    public void isAdDisplayed() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.adView))
                .isDisplayed();
    }

    @Override
    public boolean isShowButtonEnabled() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.showButton))
                .isEnabled();
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
        wait.until(ExpectedConditions.elementToBeClickable(Locators.webView))
                .click();
    }

    @Override
    public void closeWebViewCreative() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.closeWebViewButton))
                .click();
    }

    @Override
    public void performScrollUpAndDown() {
        performScrollBannerUp();
        performScrollBannerDown();
    }

    @Override
    public void performScrollDown() {
        performScrollBannerDown();
    }

    @Override
    public void performScrollUp() {
        performScrollBannerUp();
    }


    @Override
    public void adShouldExpand1Part() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.expand1PartAdText));
    }

    @Override
    public void lockToPortrait() {
        TouchAction action = new TouchAction(driver);
        action
                .tap(PointOption.point(330, 340))
                .perform();
        action
                .press(PointOption.point(330, 340))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
                .perform();
    }

    @Override
    public void lockToLandscape() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.lockToLandscape))
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
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.expand2partCreative)).click();
    }

    @Override
    public void adShouldExpand2Part() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.expand2partAdText));
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.expandAgain));
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.playVideo));
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.openIAB));
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.clickHereToClose));
    }

    @Override
    public void clickExpandAgain() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.expandAgain))
                .click();
    }

    @Override
    public void clickPlayVideo() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.playVideo))
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
        int portraitWidth = getAdSize(Locators.expand2partCreative).width;
        int portraitHeight = getAdSize(Locators.expand2partCreative).height;
        rotateLandscape();
        int landscapeWidth = getAdSize(Locators.expand2partCreative).width + 1;
        int landscapeHeight = getAdSize(Locators.expand2partCreative).height - 1;
        return (portraitHeight == landscapeHeight) && (portraitWidth == landscapeWidth);
    }

    //TODO migrate to separate class Mraid Resize
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
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.playVideoButton));
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
        return true;
    }

    @Override
    public boolean isVideoAdExpanded() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.videoView));
            return true;
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }

    @Override
    public void clickPlayVideoResizeAd() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.playVideoButton))
                .click();
    }

    @Override
    public void clickCloseMRAIDResizeButton() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.xButtonResizeAd))
                .click();
    }

    @Override
    public void mapAppShouldOpen() {
        wait.until(ExpectedConditions.presenceOfElementLocated(Locators.mapLocation));
    }

    @Override
    public void smsAppShouldOpen() {
        //TODO add method to check that sms app displayed
        try {
            waitAndReturnToApp();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void confirmSavingPicture() {
        try {
            (new WebDriverWait(driver, 3)).until(ExpectedConditions.presenceOfElementLocated(
                    By.id(String.format("“%s” Would Like to Add to your Photos", appName))));
            JavascriptExecutor js = driver;
            HashMap<String, String> tapObject = new HashMap<String, String>();
            tapObject.put("action", "accept");
            tapObject.put("label", "OK");
            js.executeScript("mobile:alert", tapObject);
        } catch (org.openqa.selenium.TimeoutException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void confirmCreatingCalendarEvent() {
        try {
            (new WebDriverWait(driver, 3)).until(ExpectedConditions.presenceOfElementLocated(
                    By.id(String.format("“%s” Would Like to Access Your Calendar", appName))));
            JavascriptExecutor js = driver;
            HashMap<String, String> tapObject = new HashMap<String, String>();
            tapObject.put("action", "accept");
            tapObject.put("label", "OK");
            js.executeScript("mobile:alert", tapObject);
        } catch (org.openqa.selenium.TimeoutException e) {
            e.printStackTrace();
        }
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("Cancel")))
                .click();
    }

    @Override
    public void createCalendarEvent() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.createCalendarEventButton))
                .click();
    }


    //TODO MRAID RESIZE WITH ERRORS
    @Override
    public void isResizeWithErrorsAdLoaded() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.testProperties));
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.testOffscreen));
    }

    @Override
    public void setToggleOffscreenTrue() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.toggleOffScreenFalse))
                .click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.toggleOffScreenTrue))
                .isDisplayed();
    }

    @Override
    public void setToggleOffscreenFalse() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.toggleOffScreenTrue))
                .click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.toggleOffScreenFalse))
                .isDisplayed();
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
        return getSize(Locators.webView);
    }

    //VIDEO FEED METHODS
    @Override
    public void scrollToFeedAd() {
        Point location = wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.feedTable))
                .getLocation();
        Dimension dim = wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.feedTable))
                .getSize();

        int startX = (int) (location.getX() + dim.getWidth() * 0.5);
        int finishX = (int) (location.getX() + dim.getWidth() * 0.5);
        int startY = (int) (location.getY() + dim.getHeight() * 0.8);
        int finishY = (int) (location.getY() + dim.getHeight() * 0.2);
        TouchAction touchAction = new TouchAction(driver);
        touchAction
                .longPress(PointOption.point(startX, startY))
                .moveTo(PointOption.point(finishX, finishY))
                .perform();
    }

    @Override
    public void clickOnVideoFeed() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.video300x250Feed))
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
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.webView));
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

    private void performScrollBannerUp() {
        Point locationScrollableBanner = driver.findElement(Locators.scrollView)
                .getLocation();
        int heightScrollableBanner = driver.findElement(Locators.scrollView)
                .getSize()
                .getHeight();
        int widthScrollableBanner = driver.findElement(Locators.scrollView)
                .getSize()
                .getWidth();

        int startX = (locationScrollableBanner.x + widthScrollableBanner / 2);
        int startY = (locationScrollableBanner.y + heightScrollableBanner / 2);
        int finishY = (locationScrollableBanner.y);

        TouchAction touchAction = new TouchAction(driver);

        for (int i = 1; i <= 4; i++) {
            touchAction.longPress(PointOption.point(startX, startY))
                    .moveTo(PointOption.point(startX, finishY))
                    .perform();
        }

    }

    private void performScrollBannerDown() {

        Point locationScrollableBanner = driver.findElement(Locators.scrollView)
                .getLocation();
        int heightScrollableBanner = driver.findElement(Locators.scrollView)
                .getSize()
                .getHeight();
        int widthScrollableBanner = driver.findElement(Locators.scrollView)
                .getSize()
                .getWidth();

        int startX = (locationScrollableBanner.x + widthScrollableBanner / 2);
        int startY = (locationScrollableBanner.y + 10);
        int finishY = (locationScrollableBanner.y + heightScrollableBanner);

        TouchAction touchAction = new TouchAction(driver);

        for (int i = 1; i <= 4; i++) {
            touchAction.longPress(PointOption.point(startX, startY))
                    .moveTo(PointOption.point(startX, finishY))
                    .perform();
        }
    }

    private void clickOnElement(By elementLocator) {
        driver.findElement(elementLocator).click();
    }

    protected Dimension getSize(By elementLocator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(elementLocator)).getSize();
    }
}
