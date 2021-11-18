package appium.pages.inAppBidding;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Locale;

public class InAppBiddingHomePageNavigator {

    protected final WebDriverWait wait;

    public InAppBiddingHomePageNavigator(WebDriverWait wait) {
        this.wait = wait;
    }

    // PRIVATE

    public void goToAd(String text, By searchBarElement, By listElement) {
        WebElement searchBar = wait.until(ExpectedConditions.visibilityOfElementLocated(searchBarElement));
        searchBar.clear();
        searchBar.click();
        searchBar.sendKeys(text.toLowerCase());
        wait.until(ExpectedConditions.visibilityOfElementLocated(listElement))
                .click();
    }
}
