package appium.pages.inAppBidding.android;

import appium.common.InAppBiddingTestEnvironment;
import appium.pages.android.AndroidBasePage;
import appium.pages.inAppBidding.InAppBiddingAdPageImpl;
import appium.pages.inAppBidding.InAppBiddingHomePageImpl;
import appium.pages.inAppBidding.InAppBiddingHomePageNavigator;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.testng.Assert.assertTrue;

public class InAppBiddingHomePageAndroid extends AndroidBasePage implements InAppBiddingHomePageImpl {

    public static class PrebidLocators {
        static final By searchEditText = MobileBy.id("android:id/search_src_text");
        static final By listItem = MobileBy.id("org.prebid.mobile.renderingtestapp:id/listitem_demo");
        static final By openBrowser = MobileBy.AccessibilityId("openInExternalBrowser");
        static final By customConfigButtonChecked = MobileBy.xpath("//android.widget.ToggleButton[@checked='true']");
        static final By customConfigButtonUnchecked = MobileBy.xpath("//android.widget.ToggleButton[@checked='false']");
        static final By closeButtonClickThroughBrowser = MobileBy.AccessibilityId("close");
        static final By autoRefreshDelay = MobileBy.id("org.prebid.mobile.renderingtestapp:id/etRefreshDelay");
        static final By loadAd = MobileBy.id("org.prebid.mobile.renderingtestapp:id/btnLoad");
        static final By examples = MobileBy.AccessibilityId("Examples");
        static final By utilities = MobileBy.AccessibilityId("Utilities");
        static final By GDPRSwitcher = MobileBy.id("org.prebid.mobile.renderingtestapp:id/switchEnableGdpr");

        static final By closeButton = MobileBy.id("org.prebid.mobile.renderingtestapp:id/iv_close_interstitial");
        static final By closeWebViewButton = MobileBy.xpath("//android.widget.Button[@content-desc='close']");
    }

    protected final InAppBiddingHomePageNavigator pageNavigator;

    public InAppBiddingHomePageAndroid(AndroidDriver driver, String locatorType) {
        super(driver, locatorType);
        pageNavigator = new InAppBiddingHomePageNavigator(wait);
    }


    // Pages

    @Override
    public void goToUtilities() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(PrebidLocators.utilities)).click();
    }



    @Override
    public void turnOnGDPRSwitcher() {
        if (isGDPROff()) {
            wait.until(ExpectedConditions.elementToBeClickable(PrebidLocators.GDPRSwitcher)).click();
        }
    }

    @Override
    public void turnOffGDPRSwitcher() {
        wait.until(ExpectedConditions.elementToBeClickable(PrebidLocators.GDPRSwitcher)).click();
    }

    public boolean isGDPROff() {
        return !wait.until(ExpectedConditions.elementToBeClickable(PrebidLocators.GDPRSwitcher)).isSelected();
    }


    @Override
    public void clickRequestTrackingAuthorization() {
        System.out.println("ios method");
    }

    @Override
    public void clickAppNotToTrack() {
        System.out.println("ios method");
    }

    @Override
    public void clickAllow() {
        System.out.println("ios method");
    }

    @Override
    public InAppBiddingAdPageImpl goToAd(String prebidAd) throws InterruptedException {
        navigateToAd(prebidAd);
        return new InAppBiddingAdPageAndroid(driver, locatorType);
    }

    // Actions

    @Override
    public boolean isSearchFieldDisplayed() {
        try {
            return !(driver.findElements(PrebidLocators.searchEditText).size() == 0);
        } catch (WebDriverException exception) {
            return false;
        }
    }

    @Override
    public void relaunchApp() throws InterruptedException {
        System.out.println("relaunch app");
        driver.launchApp();
    }

    @Override
    public void resetApp() {
        System.out.println("reset app");
        driver.resetApp();
    }

    @Override
    public void goToAdExamples() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(PrebidLocators.examples)).click();
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
    public void isDelegateEnabled(InAppBiddingTestEnvironment.InAppBiddingDelegates delegate) {
        String delegateName = InAppBiddingTestEnvironment.getDelegate(delegate, platformName);
        String idDelegateLocator = String.format("org.prebid.mobile.renderingtestapp:id/%s", delegateName);
        By locator = MobileBy.id(idDelegateLocator);
        wait.until(ExpectedConditions.elementToBeClickable(locator)).isEnabled();
    }


    @Override
    public void isDelegateDisabled(InAppBiddingTestEnvironment.InAppBiddingDelegates delegate) {
        String delegateName = InAppBiddingTestEnvironment.getDelegate(delegate, platformName);
        String idDelegateLocator = String.format("org.prebid.mobile.renderingtestapp:id/%s", delegateName);
        By locator = MobileBy.id(idDelegateLocator);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    @Override
    public ScreenOrientation getOrientation() {
        return super.getOrientation();
    }

    @Override
    public void runAppInBackground(Integer seconds) throws InterruptedException {
        appWaitInBackground(seconds);
        goToPrebidActivity();
    }

    @Override
    public void sleep(Integer seconds) throws InterruptedException {
        Thread.sleep((seconds).longValue());
    }

    @Override
    public void clickBack() {
        driver.navigate().back();
    }

    @Override
    public void openInBrowser() {
        wait.until(ExpectedConditions.elementToBeClickable(PrebidLocators.openBrowser))
                .click();
    }

    @Override
    public void clickCloseButtonClickThroughBrowser() {
        wait.until(ExpectedConditions.elementToBeClickable(PrebidLocators.closeButtonClickThroughBrowser))
                .click();
    }

    @Override
    public void turnOnCustomConfig() {
        if (driver.findElements(PrebidLocators.customConfigButtonUnchecked).size() != 0) {
            wait.until(ExpectedConditions.elementToBeClickable(PrebidLocators.customConfigButtonUnchecked))
                    .click();
        }

    }

    @Override
    public void turnOffCustomConfig() {
        if (driver.findElements(PrebidLocators.customConfigButtonChecked).size() != 0) {
            wait.until(ExpectedConditions.elementToBeClickable(PrebidLocators.customConfigButtonChecked))
                    .click();
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

    // PRIVATE

    /**
     * Types the title into the search field and clicks on the result
     *
     * @param name String to type into the search field
     * @return true if navigate successful; else false
     */
    private void navigateToAd(String name) throws InterruptedException {
        Thread.sleep(1000);
        pageNavigator.goToAd(name, PrebidLocators.searchEditText, PrebidLocators.listItem);
    }

    public void appWaitInBackground(Integer seconds) throws InterruptedException {
        clickHome();
        Thread.sleep(seconds * 1000);
    }
}
