package appium.inAppBidding;

import OMSDK.OMSDKEventHandler;
import adapters.PrebidAdapter;
import adapters.factory.PrebidAdapterFactory;
import adapters.factory.PrebidAdapterFactoryAndroid;
import adapters.factory.PrebidAdapterFactoryIOS;
import appium.common.InAppBiddingTestEnvironment;
import appium.common.TestEnvironment;
import appium.pages.inAppBidding.InAppBiddingAdPageImpl;
import org.json.JSONObject;
import org.testng.ITestContext;
import org.testng.annotations.*;

import java.io.IOException;
import java.lang.reflect.Method;

import static appium.common.InAppTemplatesInit.*;

public class InAppBaseTest {
    protected static InAppBiddingTestEnvironment env;
    protected static JSONObject validAuctionRequest;
    protected static JSONObject validAuctionResponse;
    protected static String platformName;
    protected static String displaymanagerver;
    protected static String ver;
    protected static String version;
    protected static String omidpv;
    protected static boolean isPlatformIOS;
    protected static PrebidAdapterFactory prebidAdapterFactory;
    protected PrebidAdapter prebidAdapter;
    protected JSONObject auctionRequestCCPA_TRUE;
    protected JSONObject auctionRequestCCPA_FALSE;
    protected JSONObject auctionRequestJson;
    protected OMSDKEventHandler eventHandler;

    /**
     * For skadn tests we must launch another application
     */
    @BeforeTest(groups = {"smoke", "android", "ios", "exec", "requests", "requests-realDevice", "requests-skadn", "requests-simulator"})
    @Parameters({"prebidTestPlatform"})
    public void setupBMP(ITestContext itc, String prebidTestPlatform) throws IOException {
        System.out.println(itc.getName());
        setup(itc, prebidTestPlatform);
    }

    @AfterTest(groups = {"smoke", "android", "ios", "exec", "requests", "requests-realDevice", "requests-skadn", "requests-simulator"})
    public void teardown() throws IOException {
        displaymanagerver = null;
        ver = null;
        version = null;
        omidpv = null;
        env.teardown();
    }

    @BeforeMethod(groups = {"smoke", "android", "ios", "exec", "requests", "requests-skadn", "requests-realDevice", "requests-simulator"})
    public void setupMethodBMP(ITestContext itc, Method method) throws InterruptedException {

        if (!env.homePage.isSearchFieldDisplayed()) {
            env.homePage.relaunchApp();
            env.homePage.turnOffGDPRSwitcher();
        }

        env.homePage.turnOffCustomConfig();
        env.bmp.newHar();
    }


    @AfterMethod(groups = {"smoke", "android", "ios", "exec", "requests", "requests-skadn", "requests-realDevice", "requests-simulator"})
    public void teardownMethod() {
        eventHandler = null;
        validAuctionRequest = null;
        validAuctionResponse = null;
        auctionRequestCCPA_TRUE = null;
        auctionRequestCCPA_FALSE = null;
        auctionRequestJson = null;
    }

    @AfterMethod(groups = {"USPrivacy", "TCFv1", "CustomOpenRTB", "LiveRampATS", "WithAdditionalParams", "Gpp"})
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
        String appPath = isPlatformIOS ? TestEnvironment.IOS_ORIGINAL_APP_PATH : TestEnvironment.ANDROID_ORIGINAL_APP_PATH;
        env = new InAppBiddingTestEnvironment(testName, itc, TestEnvironment.INSPECTORS_MOB_PROXY, commandLineArguments, appPath);
        platformName = env.getProperty("platformName");
        System.out.println("commandLineArguments ==> " + commandLineArguments);
        itc.setAttribute("pathToManifest", env.getProperty("pathToManifest"));
        itc.setAttribute("authToken", env.capabilities.getCapability("authToken").toString());
    }

    public void initValidTemplatesJson(String prebidAd) {
        initValidTemplatesJson(prebidAd, false);
    }

    public void initValidTemplatesJsonWithCache(String prebidAd) {

        validAuctionRequest = getAuctionRequestWithCacheTemplate(prebidAd, platformName);

        System.out.println(prebidAd);
        if (prebidAd.startsWith("Native") || prebidAd.startsWith("Banner Native") || prebidAd.contains("Ad Configuration")) {
            validAuctionResponse = getAuctionResponseTemplate(prebidAd, platformName);
        }
    }

    public void initValidTemplatesJsonWithAdditionalParams(String prebidAd) {

        validAuctionRequest = getAuctionRequestWithAdditionalParams(prebidAd, platformName);

        System.out.println(prebidAd);
        if (prebidAd.startsWith("Native") || prebidAd.startsWith("Banner Native") || prebidAd.contains("Ad Configuration") || isOriginalAd(prebidAd)) {
            validAuctionResponse = getAuctionResponseTemplate(prebidAd, platformName);
        }
    }

    public void initValidTemplatesJson(String prebidAd, boolean isRealDevice) {
        if (isRealDevice) {
            validAuctionRequest = getRealDeviceAuctionRequestTemplate(prebidAd, platformName);
        } else {
            validAuctionRequest = getAuctionRequestTemplate(prebidAd, platformName);
        }
        System.out.println(prebidAd);
        if (prebidAd.startsWith("Native") || prebidAd.startsWith("Banner Native") || isOriginalAd(prebidAd)) {
            validAuctionResponse = getAuctionResponseTemplate(prebidAd, platformName);
        }
    }

    public void initEventHandler() {
        eventHandler = new OMSDKEventHandler(env.bmp.getHar());
    }

    public void initPrebidAdapter(String prebidAd, InAppBiddingTestEnvironment env, InAppBiddingAdPageImpl adPage) {
        try {
            prebidAdapter = prebidAdapterFactory.createPrebidAdapter(prebidAd, env, adPage);
        } catch (NoSuchFieldException noSuchFieldException) {
            noSuchFieldException.printStackTrace();
        }
    }

    public void initPrebidAdapter(String prebidAd, InAppBiddingTestEnvironment env) {
        try {
            prebidAdapter = prebidAdapterFactory.createPrebidAdapter(prebidAd, env);
        } catch (NoSuchFieldException noSuchFieldException) {
            noSuchFieldException.printStackTrace();
        }
    }

    protected boolean isOriginalAd(String prebidAd) {
        return prebidAd.contains("Original");
    }

    private void setup(ITestContext itc, String prebidTestPlatform) throws IOException {
        final String testName = String.format("%s", this.getClass().getSimpleName());
        if (prebidTestPlatform.equals("iOS")) {
            initIosEnv(itc, testName);
        } else {
            initAndroidEnv(itc, testName);
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
        if (isPlatformIOS) {
            prebidAdapterFactory = new PrebidAdapterFactoryIOS();
        } else {
            prebidAdapterFactory = new PrebidAdapterFactoryAndroid();
        }
    }

    private void initIosEnv(ITestContext itc, String testName) throws IOException {
        String appPath = testName.equals(InAppSkadnTests.class.getSimpleName()) ? TestEnvironment.IOS_SKADN_APP_PATH : TestEnvironment.IOS_ORIGINAL_APP_PATH;
        env = new InAppBiddingTestEnvironment(testName, itc, TestEnvironment.INSPECTORS_MOB_PROXY, appPath);
    }

    private void initAndroidEnv(ITestContext itc, String testName) throws IOException {
        env = new InAppBiddingTestEnvironment(testName, itc, TestEnvironment.INSPECTORS_MOB_PROXY, TestEnvironment.ANDROID_ORIGINAL_APP_PATH);
    }


}
