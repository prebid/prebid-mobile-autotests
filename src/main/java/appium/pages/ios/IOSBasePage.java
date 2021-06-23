package appium.pages.ios;

import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class IOSBasePage {
    protected final IOSDriver driver;
    protected static WebDriverWait wait;
    protected String appName;
    protected final String platformName;
    
    public IOSBasePage(IOSDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 20);
        platformName = driver.getPlatformName();
    }

    protected static class IOSBaseLocators {
        static final By openBrowser = MobileBy.AccessibilityId("PBM_openbrowser");
    }
    
    public void rotateLandscape() {
        driver.rotate(ScreenOrientation.LANDSCAPE);
    }

    public void rotatePortrait() {
        driver.rotate(ScreenOrientation.PORTRAIT);
    }

    public void openInBrowser() {
        wait.until(ExpectedConditions.elementToBeClickable(IOSBaseLocators.openBrowser))
                .click();
    }

}
