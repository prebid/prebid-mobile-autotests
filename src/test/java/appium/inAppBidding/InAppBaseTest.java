package appium.inAppBidding;

import OMSDK.OMSDKEventHandler;
import appium.common.InAppBiddingTestEnvironment;
import appium.common.TestEnvironment;
import org.json.JSONObject;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import static appium.common.InAppTemplatesInit.getAuctionRequestTemplate;
import static appium.common.InAppTemplatesInit.getAuctionResponseTemplate;

public class InAppBaseTest {
    public static InAppBiddingTestEnvironment env;
    public static JSONObject validAuctionRequest;
    public static JSONObject validAuctionResponse;
    public static String platformName;
    public static String displaymanagerver;
    public static String ver;
    public static String version;
    public static String omidpv;
    public static boolean isPlatformIOS;
    public JSONObject auctionRequestCCPA_TRUE;
    public JSONObject auctionRequestCCPA_FALSE;
    public JSONObject auctionRequestJson;
    public OMSDKEventHandler eventHandler;


    @BeforeTest(groups = {"smoke", "android", "ios", "exec", "requests"})
    public void setupMock(ITestContext itc) throws IOException {
        System.out.println(itc.getName());
        setup(itc, TestEnvironment.INSPECTORS_MOCK_SERVER);
    }

    @BeforeTest(groups = {"serverBased", "serverBased-ios"})
    public void setupBMP(ITestContext itc) throws IOException {
        System.out.println(itc.getName());
        setup(itc, TestEnvironment.INSPECTORS_MOB_PROXY);
    }

    @AfterTest(groups = {"smoke", "android", "ios", "exec", "serverBased", "requests", "serverBased-ios"})
    public void teardown() throws IOException {
        displaymanagerver = null;
        ver = null;
        version = null;
        omidpv = null;
        env.teardown();
    }


    @BeforeMethod(groups = {"smoke", "android", "ios", "exec", "requests"})
    public void setupMethodMock(ITestContext itc) throws InterruptedException {
        if (!env.homePage.isSearchFieldDisplayed()) {
            env.homePage.relaunchApp();
            env.homePage.turnOnMockServerSwitcher();
            env.homePage.turnOffGDPRSwitcher();
        }

        env.homePage.turnOffCustomConfig();
        env.logValidator.clearLogs();
    }

    @BeforeMethod(groups = {"serverBased"})
    public void setupMethodBMP(ITestContext itc) throws InterruptedException {
        if (!env.homePage.isSearchFieldDisplayed()) {
            env.homePage.relaunchApp();
            env.homePage.turnOffGDPRSwitcher();
        }
        env.homePage.turnOffCustomConfig();
        env.bmp.newHar();
    }

    @AfterMethod(groups = {"smoke", "android", "ios", "exec", "serverBased", "requests"})
    public void teardownMethod() {
        eventHandler = null;
        validAuctionRequest = null;
        validAuctionResponse = null;
        auctionRequestCCPA_TRUE = null;
        auctionRequestCCPA_FALSE = null;
        auctionRequestJson = null;
    }

    @AfterMethod(groups = {"USPrivacy", "TCFv1", "CustomOpenRTB", "LiveRampATS"})
    public void teardownMethodCustom() throws IOException {
        eventHandler = null;
        validAuctionRequest = null;
        validAuctionResponse = null;
        auctionRequestCCPA_TRUE = null;
        auctionRequestCCPA_FALSE = null;
        auctionRequestJson = null;
        env.teardown();
    }


    public void setupEnvWithCommandLineArguments(Method method, ITestContext itc, String commandLineArguments) throws IOException {
        final String testName = String.format("%s_%s", this.getClass().getSimpleName(), method.getName());
        env = new InAppBiddingTestEnvironment(testName, itc, TestEnvironment.INSPECTORS_MOCK_SERVER, commandLineArguments);
        platformName = env.getProperty("platformName");
        System.out.println("commandLineArguments ==> " + commandLineArguments);
        itc.setAttribute("pathToManifest", env.getProperty("pathToManifest"));
        itc.setAttribute("authToken", env.capabilities.getCapability("authToken").toString());
        env.homePage.turnOnMockServerSwitcher();
        env.logValidator.clearLogs();
    }

    public void initValidTemplatesJson(String prebidAd) {
        validAuctionRequest = getAuctionRequestTemplate(prebidAd, platformName);

        System.out.println(prebidAd);
        if (prebidAd.startsWith("Native") || prebidAd.startsWith("Banner Native")) {
            validAuctionResponse = getAuctionResponseTemplate(prebidAd, platformName);
        }
    }

    public void initEventHandler() {
        System.out.println("Log validator: "+env.logValidator.getLogs());
        eventHandler = new OMSDKEventHandler(env.logValidator);
    }

    private void setup(ITestContext itc, Set<TestEnvironment.TrafficInspectorKind> trafficInsprctors) throws IOException {
        final String testName = String.format("%s", this.getClass().getSimpleName());
        env = new InAppBiddingTestEnvironment(testName, itc, trafficInsprctors);
        if (trafficInsprctors.contains(TestEnvironment.TrafficInspectorKind.MOCK_SERVER)) {
            env.homePage.turnOnMockServerSwitcher();
        }
        env.homePage.turnOffGDPRSwitcher();
        itc.setAttribute("pathToManifest", env.getProperty("pathToManifest"));
        itc.setAttribute("authToken", env.capabilities.getCapability("authToken").toString());
        platformName = env.getProperty("platformName");
        ver = env.getProperty("ver");
        version = env.getProperty("version");
        displaymanagerver = version;
        omidpv = env.getProperty("omidpv");
        isPlatformIOS = platformName.equalsIgnoreCase("iOS");
    }

    protected void checkGamOrMoPubEvents(String prebidAd) throws TimeoutException, InterruptedException {
        if (isPlatformIOS) {
            if (prebidAd.contains("GAM")) {
                env.waitForEvent(InAppBiddingTestEnvironment.InAppBiddingEvents.GAM_GAMPAD, 1, 60);
            }
        }
        if (prebidAd.contains("MoPub")) {
            env.waitForEvent(InAppBiddingTestEnvironment.InAppBiddingEvents.MOPUB_AD, 1, 60);
            env.waitForEvent(InAppBiddingTestEnvironment.InAppBiddingEvents.MOPUB_IMP, 1, 60);
        }
    }

}
