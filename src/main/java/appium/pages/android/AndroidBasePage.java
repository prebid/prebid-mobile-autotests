package appium.pages.android;

import com.sun.tools.javac.util.Pair;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static utils.CommandUtils.executeCommand;

public class AndroidBasePage {
    private static final String COMMAND_DENSITY_DPI = "adb shell wm density";

    private static final int DENSITY_MEDIUM = 160;
    private static final float DENSITY_DEFAULT_SCALE = 1.0f / DENSITY_MEDIUM;

    protected static AndroidDriver driver;
    protected static WebDriverWait wait;
    protected static String locatorType = " ";
    protected final String platformName;

    public AndroidBasePage(AndroidDriver driver, String locatorType) {
        AndroidBasePage.driver = driver;
        wait = new WebDriverWait(driver, 35);
        AndroidBasePage.locatorType = locatorType;
        platformName = driver.getPlatformName();
    }

    public void rotateLandscape() {
        driver.rotate(ScreenOrientation.LANDSCAPE);
    }

    public void rotatePortrait() {
        driver.rotate(ScreenOrientation.PORTRAIT);
    }

    public ScreenOrientation getOrientation() {
        return driver.getOrientation();
    }

    protected Dimension getSize(By elementLocator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(elementLocator)).getSize();
    }

    public void clickBack() {
        driver.navigate().back();
    }

    public void openRecentAppsList() {
        driver.pressKey(new KeyEvent(AndroidKey.APP_SWITCH));
    }

    public void clickHome() {
        driver.pressKey(new KeyEvent(AndroidKey.HOME));
    }

    public void goToPrebidActivity() {
        openRecentAppsList();
        System.out.println(locatorType);
        if(!locatorType.equals("text")){
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.FrameLayout[@content-desc=\"Prebid Rendering Kotlin Demo\"]"))).click();
        }else {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@content-desc=\"Prebid Rendering Kotlin Demo\"]"))).click();
        }
    }

    protected void elementShouldDisplayAfter(By element, int delay) {
        try {
            (new WebDriverWait(driver, delay-1)).until(
                ExpectedConditions.elementToBeClickable(element));
            throw new WebDriverException(String.format("%s should not appear before %d seconds",
                                                       element.toString(), delay));
        }
        catch (TimeoutException e) {
            (new WebDriverWait(driver, 3)).until(
                ExpectedConditions.visibilityOfElementLocated(element));
        }
    }

    protected Pair<Integer, Integer> getCreativeWidthHeightPairDpi(WebElement element) {
        String result = executeCommand(COMMAND_DENSITY_DPI);
        Integer densityDpi = getDensityDpi(result);
        float density = densityDpi * DENSITY_DEFAULT_SCALE;

        Dimension elementSize = element.getSize();

        float dipHeight = elementSize.height / density;
        float dipWidth = elementSize.width / density;
        return new Pair<>(Math.round(dipWidth), Math.round(dipHeight));
    }

    private Integer getDensityDpi(String result) {
        int densityDpi = 0;
        try {
            densityDpi = Integer.parseInt(result.replaceAll("\\D+", ""));
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        return densityDpi;
    }

    public void runAppInBackground(Integer sec) throws InterruptedException {
        appWaitInBackground(sec);
        goToPrebidActivity();
    }

    public void appWaitInBackground(Integer sec) throws InterruptedException {
        clickHome();
        Thread.sleep(sec * 1000);
    }
}
