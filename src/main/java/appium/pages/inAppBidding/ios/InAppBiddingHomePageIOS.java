package appium.pages.inAppBidding.ios;

import appium.common.InAppBiddingTestEnvironment;
import appium.pages.inAppBidding.InAppBiddingAdPageImpl;
import appium.pages.inAppBidding.InAppBiddingHomePageImpl;
import appium.pages.inAppBidding.InAppBiddingHomePageNavigator;
import appium.pages.ios.IOSBasePage;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.testng.Assert.assertTrue;

public class InAppBiddingHomePageIOS extends IOSBasePage implements InAppBiddingHomePageImpl {

    protected final InAppBiddingHomePageNavigator pageNavigator;

    public static class PrebidLocators {
        public static final By backButton = MobileBy.AccessibilityId("Back");
        static final By openBrowser = MobileBy.AccessibilityId("PBM openbrowser");
        static final By closeButtonClickThroughBrowser = MobileBy.AccessibilityId("PBMCloseButtonClickThroughBrowser");
        static final By customConfigButton = MobileBy.AccessibilityId("\u2699");
        static final By autoRefreshDelay = MobileBy.AccessibilityId("refreshInterval_field");
        static final By loadAd = MobileBy.AccessibilityId("load_ad");
        static final By examples = MobileBy.AccessibilityId("Examples");
        static final By utilities = MobileBy.AccessibilityId("Utilities");
        static final By appNotToTrackButton = MobileBy.AccessibilityId("Ask App not to Track");
        static final By requestTrackingAuthorization = MobileBy.AccessibilityId("Request Tracking Authorization");
        static final By allowButton = MobileBy.AccessibilityId("Allow");
        static final By alertOk = MobileBy.AccessibilityId("OK");
        static final By searchField = MobileBy.xpath("//XCUIElementTypeSearchField");
        static final By mockServerSwitcher = MobileBy.AccessibilityId("useMockServerSwitch");
    }


    public InAppBiddingHomePageIOS(IOSDriver driver) {
        super(driver);
        pageNavigator = new InAppBiddingHomePageNavigator(wait);
    }

    // Pages

    @Override
    public InAppBiddingAdPageImpl goToAd(String prebidAd) {
        navigateToAd(prebidAd, MobileBy.AccessibilityId(prebidAd));
        return new InAppBiddingAdPageIOS(super.driver);
    }

    @Override
    public void turnOnMockServerSwitcher(){
        if(isMockServerOff()){
            wait.until(ExpectedConditions.elementToBeClickable(PrebidLocators.mockServerSwitcher)).click();
        }
    }

    @Override
    public void turnOffMockServerSwitcher(){
        if(wait.until(ExpectedConditions.elementToBeClickable(PrebidLocators.mockServerSwitcher)).isSelected()){
            wait.until(ExpectedConditions.elementToBeClickable(PrebidLocators.mockServerSwitcher)).click();
        }
    }

    private boolean isMockServerOff(){
        return !wait.until(ExpectedConditions.elementToBeClickable(PrebidLocators.mockServerSwitcher)).isSelected();
    }

    // Actions
    @Override
    public boolean isSearchFieldDisplayed(){
        try {
            return !(driver.findElements(PrebidLocators.searchField).size() == 0);
        }catch (NoSuchElementException exception){
         return false;
        }
    }

    @Override
    public void relaunchApp(){
            System.out.println("relaunch app");
            driver.launchApp();
    }

    @Override
    public void resetApp(){
        System.out.println("reset app");
        driver.removeApp("org.prebid.mobile.renderingtestapp");
    }

    @Override
    public void clickRequestTrackingAuthorization() {
        wait.until(ExpectedConditions.elementToBeClickable(PrebidLocators.requestTrackingAuthorization)).click();
    }

    @Override
    public void clickAppNotToTrack() throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(PrebidLocators.appNotToTrackButton)).click();
    }

    @Override
    public void clickAllow() throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(PrebidLocators.allowButton)).click();
    }


    @Override
    public void sleep(Integer seconds) throws InterruptedException {
        Thread.sleep((seconds).longValue());
    }

    @Override
    public void goToAdExamples(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(PrebidLocators.examples)).click();
    }

    @Override
    public void goToUtilities(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(PrebidLocators.utilities)).click();
    }



    @Override
    public void isDelegateEnabled(InAppBiddingTestEnvironment.InAppBiddingDelegates delegate) {
        final String delegateName = InAppBiddingTestEnvironment.getDelegate(delegate, platformName);
        final String xpathDelegateLocator = String.format("//XCUIElementTypeButton[@name='%s']", delegateName);

        By locator = MobileBy.xpath(xpathDelegateLocator);
        wait.until(ExpectedConditions.elementToBeClickable(locator)).isEnabled();
    }

    @Override
    public void isDelegateDisabled(InAppBiddingTestEnvironment.InAppBiddingDelegates delegate) {
        final String delegateName = InAppBiddingTestEnvironment.getDelegate(delegate, platformName);
        final String xpathDelegateLocator = String.format("//XCUIElementTypeButton[@name='%s']", delegateName);

        By locator = MobileBy.xpath(xpathDelegateLocator);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        assertTrue(!element.isDisplayed());
    }

    @Override
    public void rotatePortrait() {
        super.rotatePortrait();
    }

    @Override
    public void rotateLandscape() {
        super.rotateLandscape();
    }

    @Override
    public void runAppInBackground(Integer seconds) throws InterruptedException {
        Thread.sleep(2000);
        driver.runAppInBackground(Duration.ofSeconds(seconds));
    }

    @Override
    public ScreenOrientation getOrientation() {
        return driver.getOrientation();
    }

    @Override
    public void clickBack() {
        wait.until(ExpectedConditions.elementToBeClickable(PrebidLocators.backButton))
                .click();
    }

    public void navigateBack(){
        driver.navigate().back();
    }

    @Override
    public void clickCloseButtonClickThroughBrowser() {
        wait.until(ExpectedConditions.elementToBeClickable(PrebidLocators.closeButtonClickThroughBrowser))
                .click();
    }

    @Override
    public void openInBrowser() {
        wait.until(ExpectedConditions.elementToBeClickable(PrebidLocators.openBrowser))
                .click();
    }

    @Override
    public void turnOnCustomConfig() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(PrebidLocators.customConfigButton))
                .click();
    }

    @Override
    public void turnOffCustomConfig() {
        WebElement customConfigButton = wait.until(ExpectedConditions.visibilityOfElementLocated(PrebidLocators.customConfigButton));
        if(customConfigButton.isSelected()){
            customConfigButton.click();
        }
    }

    @Override
    public void setAutoRefreshDelay(Integer refreshDelay) {
        WebElement refreshField = wait.until(ExpectedConditions.visibilityOfElementLocated(PrebidLocators.autoRefreshDelay));
        refreshField.clear();
        refreshField.sendKeys(refreshDelay.toString());
        clickLoadAdConfig();
    }

    @Override
    public void clickLoadAdConfig() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(PrebidLocators.loadAd))
                .click();
    }

    private void navigateToAd(String name, By adLocator) {
        pageNavigator.goToAd(name, PrebidLocators.searchField, adLocator);
    }

    @Override
    public boolean isDelegateEnabledAfterDelayTime(By sdkEvent, int delay) {
        return isElementEnabled(sdkEvent, delay + 1);
    }

    public void sdkEventShouldDisplayForDelayTime(By sdkEvent, int delay) {
        int actualDelayTime = getTimeWhenElementIsInvisible(sdkEvent);
        assertTrue(actualDelayTime >= delay, String.format("%s expected in %d seconds, but appeared in %d",
                sdkEvent.toString(), delay, actualDelayTime));
    }


    protected void elementShouldDisplayAfter(By buttonLocator, int delay) {
        try {
            (new WebDriverWait(driver, delay - 1)).until(
                    ExpectedConditions.elementToBeClickable(buttonLocator));
            throw new WebDriverException(String.format("%s should not appear before %d seconds",
                    buttonLocator.toString(), delay));
        } catch (TimeoutException e) {
            (new WebDriverWait(driver, 15)).until(
                    ExpectedConditions.visibilityOfElementLocated(buttonLocator));
        }
    }

    private int getTimeWhenElementIsInvisible(By buttonLocator) {
        int counter = 1;
        int timeOutInSeconds = 1;
        for (int i = 1; i <= 30; i++) {
            if (isElementEnabled(buttonLocator, timeOutInSeconds)) {
                break;
            }
            counter++;
        }
        return counter;
    }

    private boolean isElementEnabled(By buttonLocator, int timeOutInSeconds) {
        return (new WebDriverWait(driver, timeOutInSeconds))
                .until(ExpectedConditions.visibilityOfElementLocated(buttonLocator))
                .isEnabled();
    }
}
