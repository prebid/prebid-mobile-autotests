package appium.inAppBidding;

import appium.common.InAppBiddingTestEnvironment;
import appium.common.InAppBiddingTestEnvironment.InAppBiddingEvents;
import appium.common.InAppTemplatesInit;
import appium.pages.inAppBidding.InAppBiddingAdPageImpl;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeoutException;

import static appium.common.InAppTemplatesInit.*;

public class InAppBiddingCustomRequestTests extends InAppBaseTest {

    //CUSTOM REQUESTS TESTS
   @Test(groups = {"USPrivacy"})
    public void testUSPrivacy(Method method, ITestContext itc) throws TimeoutException, InterruptedException, IOException {
        isPlatformIOS = itc.getSuite().getXmlSuite().getParameter("prebidTestPlatform").equalsIgnoreCase("ios");
        setupEnvWithCommandLineArguments(method, itc, processArguments("USPrivacy"));
        initValidCCPAJsons();
        env.homePage.goToAd(BANNER_320x50_IN_APP);
        env.waitForEvent(InAppBiddingEvents.AUCTION, 1, 60);

        env.validateEventRequest(InAppBiddingEvents.AUCTION, auctionRequestCCPA_FALSE);

        env.logValidator.clearLogs();

        env.waitForEvent(InAppBiddingEvents.AUCTION, 1, 90);
        env.validateEventRequest(InAppBiddingEvents.AUCTION, auctionRequestCCPA_TRUE);
    }

   @Test(groups = {"TCFv1"}, dataProvider = "TCFv1", dataProviderClass = InAppDataProviders.class)
    public void testTCFv1(Method method, ITestContext itc, String testCase) throws TimeoutException, InterruptedException, IOException {
        isPlatformIOS = itc.getSuite().getXmlSuite().getParameter("prebidTestPlatform").equalsIgnoreCase("ios");
        setupEnvWithCommandLineArguments(method, itc, processArguments(testCase));
        initValidRequestJsons(testCase);
        env.homePage.goToAd(BANNER_320x50_IN_APP);
        env.waitForEvent(InAppBiddingTestEnvironment.InAppBiddingEvents.AUCTION, 1, 50);

        env.validateEventRequest(InAppBiddingEvents.AUCTION, auctionRequestJson);
    }

   @Test(groups = {"LiveRampATS"})
    public void testLiveRampATS(Method method, ITestContext itc) throws TimeoutException, InterruptedException, IOException {
        isPlatformIOS = itc.getSuite().getXmlSuite().getParameter("prebidTestPlatform").equalsIgnoreCase("ios");
        setupEnvWithCommandLineArguments(method, itc, processArguments(LIVE_RAMP_ATS));
        initValidRequestJsons(LIVE_RAMP_ATS);
        InAppBiddingAdPageImpl page;
        if (isPlatformIOS) {
            page = env.homePage.goToAd(BANNER_320x50_IN_APP_ATS);
        } else {
            page = env.homePage.goToAd(BANNER_320x50_IN_APP);
        }
        env.waitForEvent(InAppBiddingTestEnvironment.InAppBiddingEvents.AUCTION, 1, 50);

        env.validateEventRequest(InAppBiddingEvents.AUCTION, auctionRequestJson);
    }

   @Test(groups = {"CustomOpenRTB"}, enabled = false)
    public void testCustomOpenRtbUser(Method method, ITestContext itc) throws TimeoutException, InterruptedException, IOException {
        isPlatformIOS = itc.getSuite().getXmlSuite().getParameter("prebidTestPlatform").equalsIgnoreCase("ios");
        setupEnvWithCommandLineArguments(method, itc, processArguments(CUSTOM_OPENRTB));
        initValidRequestJsons(CUSTOM_OPENRTB);
        Thread.sleep(5000);
        InAppBiddingAdPageImpl bannerPage = env.homePage.goToAd(BANNER_320x50_IN_APP);
        env.waitForEvent(InAppBiddingTestEnvironment.InAppBiddingEvents.AUCTION, 1, 10);
        env.validateEventRequest(InAppBiddingEvents.AUCTION, auctionRequestJson);
    }

    // PRIVATE METHODS

    private void initValidCCPAJsons() {
        auctionRequestCCPA_TRUE = InAppTemplatesInit.getAuctionRequestTemplate(CUSTOM_USPRIVACY_CCPA_TRUE, platformName);
        auctionRequestCCPA_FALSE = InAppTemplatesInit.getAuctionRequestTemplate(CUSTOM_USPRIVACY_CCPA_FALSE, platformName);
    }

    private void initValidRequestJsons(String testCase) {
        auctionRequestJson = InAppTemplatesInit.getAuctionRequestTemplate(testCase, platformName);
    }

    private String processArguments(String testCase) {
        switch (testCase) {
            case "USPrivacy":
                if (isPlatformIOS) {
                    return USPrivacyArgumentsIOS();
                } else {
                    return USPrivacyArgumentsAndroid();
                }
            case CUSTOM_TCF_NO_GDPR_NO_CONSENT:
                if (isPlatformIOS) {
                    return NoGDPRnoConsent_iOS();
                } else {
                    return NoGDPRnoConsent_Android;
                }
            case CUSTOM_TCF_GDPR0_NO_CONSENT:
                if (isPlatformIOS) {
                    return With0GDPRnoConsent_iOS();
                } else {
                    return With0GDPRnoConsent_Android;
                }
            case CUSTOM_TCF_GDPR1_CONSENT:
                if (isPlatformIOS) {
                    return With1GDPRandConsent_iOS();
                } else {
                    return With1GDPRandConsent_Android;
                }
            case CUSTOM_OPENRTB:
                if (isPlatformIOS) {
                    return getComandLineCustomOpenRTBParams_iOS();
                } else {
                    return comandLineCustomOpenRTBParams_Android;
                }
            case LIVE_RAMP_ATS:
                if (isPlatformIOS) {
                    return LiveRampATS_iOS();
                } else {
                    return LiveRampATS_Android;
                }
            default:
                return "THERE IS NO SUCH TEST CASES";
        }
    }

    private String USPrivacyArgumentsIOS() {
        String comandLineCustomOpenRTBParams = "[{\"openRtb\":" +
                "{\"age\":20,\"url\":\"https://url.com\",\"crr\":\"carrier\",\"dma\":\"ukraine\"," +
                "\"eth\":\"WHITE\",\"inc\":10000,\"ip\":\"127.0.0.1\",\"mar\":\"SINGLE\",\"xid\":\"007\"," +
                "\"gen\":\"FEMALE\",\"buyerid\":\"buyerid_test\",\"customdata\":\"customdata_test\", " +
                "\"keywords\":\"keyword1,keyword2\",\"geo\":{ \"lat\":1.5, \"lon\":2.5 }, \"ext\":{ \"key1\":\"string\", " +
                "\"key2\":1, \"object\":{ \"inner\":\"value\" } }}}]";

        String EXTRA_OPEN_RTB = "EXTRA_OPEN_RTB";
        String IABConsent_Settings = "IABConsent_Settings";

        String IABLaunchOptions = "{\"launchOptions\":{\"IABUSPrivacy_String\":null}," +
                "\"updateInterval\":45,\"updatedOptions\":[{\"IABUSPrivacy_String\":\"1YNN\"},{\"IABUSPrivacy_String\":null}]}";

        List<String> processArgs = new ArrayList<>(Arrays.asList(
                EXTRA_OPEN_RTB,
                comandLineCustomOpenRTBParams,
                IABConsent_Settings,
                IABLaunchOptions
        ));

        final net.minidev.json.JSONObject argsValue = new net.minidev.json.JSONObject();
        argsValue.put("args", processArgs);

        return argsValue.toString();
    }

    private String USPrivacyArgumentsAndroid() {
        return "--es EXTRA_CONSENT_V1 \"{\"launchOptions\":{\"IABUSPrivacy_String\":null},\"updateInterval\":15,\"updatedOptions\":[{\"IABUSPrivacy_String\":null}, {\"IABUSPrivacy_String\":'1YNN'}]}\"";
    }

    //Android commandLineArgument parts
    final String NoGDPRnoConsent_Android = "--es EXTRA_CONSENT_V1 \"{\"launchOptions\":{\"IABConsent_CMPPresent\":false,\"IABConsent_SubjectToGDPR\":null,\"IABConsent_ConsentString\":null}}\"";
    final String With1GDPRandConsent_Android = "--es EXTRA_CONSENT_V1 \"{\"launchOptions\":{\"IABConsent_CMPPresent\":true,\"IABConsent_SubjectToGDPR\":'1',\"IABConsent_ConsentString\":'BOEFEAyOEFEAyAHABDENAI4AAAB9vABAASA'}}\"";
    final String With0GDPRnoConsent_Android = "--es EXTRA_CONSENT_V1 \"{\"launchOptions\":{\"IABConsent_CMPPresent\":true,\"IABConsent_SubjectToGDPR\":'0',\"IABConsent_ConsentString\":null}}\"";
    final String comandLineCustomOpenRTBParams_Android = "--es EXTRA_OPEN_RTB \"{\"crr\":\"carrier\",\"keywords\":\"keyword\",\"ip\":\"127.0.0.1\",\"buyerid\":\"buyerid\",\"url\":\"https://url.com\",\"geo\":{\"lon\":2,\"lat\":1},\"gen\":\"MALE\",\"xid\":\"007\",\"eth\":\"WHITE\",\"dma\":\"area\",\"customdata\":\"data\",\"age\":23,\"inc\":10000,\"mar\":\"SINGLE\"}\"";
    final String LiveRampATS_Android = "--es EXTRA_EIDS \"[{\\\"source\\\": \\\"liveramp.com\\\",\\\"uids\\\": [{\\\"id\\\": \\\"XY1000bIVBVah9ium-sZ3ykhPiXQbEcUpn4GjCtxrrw2BRDGM\\\"}] }]\"";

    //iOS commandLineArgument parts
    private String NoGDPRnoConsent_iOS() {

        String IABLaunchOptions = "{\"launchOptions\": {\"IABTCF_CmpSdkID\": \"0\",\"IABTCF_gdprApplies\": null,\"IABTCF_TCString\": null}}";

        List<String> processArgs = new ArrayList<>(Arrays.asList(
                IABConsent_Settings,
                IABLaunchOptions
        ));

        final net.minidev.json.JSONObject argsValue = new net.minidev.json.JSONObject();
        argsValue.put("args", processArgs);

        return argsValue.toString();
    }

    private String With0GDPRnoConsent_iOS() {

        String IABLaunchOptions = "{\"launchOptions\": {\"IABTCF_CmpSdkID\": \"1\",\"IABTCF_gdprApplies\": \"0\",\"IABTCF_TCString\": null}}";

        List<String> processArgs = new ArrayList<>(Arrays.asList(
                IABConsent_Settings,
                IABLaunchOptions
        ));

        final net.minidev.json.JSONObject argsValue = new net.minidev.json.JSONObject();
        argsValue.put("args", processArgs);

        return argsValue.toString();
    }

    private String With1GDPRandConsent_iOS() {

        String IABLaunchOptions = "{\"launchOptions\": {\"IABTCF_CmpSdkID\": \"1\",\"IABTCF_gdprApplies\": \"1\",\"IABTCF_TCString\": \"BObdrPUOevsguAfDqFENCNAAAAAmeAAA.PVAfDObdrA.DqFENCAmeAENCDA.OevsguAfDq\"}}";

        List<String> processArgs = new ArrayList<>(Arrays.asList(
                IABConsent_Settings,
                IABLaunchOptions
        ));

        final net.minidev.json.JSONObject argsValue = new net.minidev.json.JSONObject();
        argsValue.put("args", processArgs);

        return argsValue.toString();
    }

    private String LiveRampATS_iOS() {

        String LiveRampLaunchOptions = "[{\"source\": \"liveramp.com\",\"uids\": [{\"id\": \"XY1000bIVBVah9ium-sZ3ykhPiXQbEcUpn4GjCtxrrw2BRDGM\"}] }]";

        List<String> processArgs = new ArrayList<>(Arrays.asList(
                EXTRA_EIDS,
                LiveRampLaunchOptions
        ));

        final net.minidev.json.JSONObject argsValue = new net.minidev.json.JSONObject();
        argsValue.put("args", processArgs);

        return argsValue.toString();
    }

    private String getComandLineCustomOpenRTBParams_iOS() {

        List<String> processArgs = new ArrayList<>(Arrays.asList(
                EXTRA_OPEN_RTB,
                comandLineCustomOpenRTBParams_iOS
        ));

        final net.minidev.json.JSONObject argsValue = new net.minidev.json.JSONObject();
        argsValue.put("args", processArgs);

        return argsValue.toString();
    }

    final String comandLineCustomOpenRTBParams_iOS = "[{\"auid\":\"540848492\",\"openRtb\":" +
            "{\"age\":23,\"url\":\"https://url.com\",\"crr\":\"carrier\",\"dma\":\"ukraine\"," +
            "\"eth\":\"WHITE\",\"inc\":10000,\"ip\":\"127.0.0.1\",\"mar\":\"SINGLE\",\"xid\":\"007\"," +
            "\"gen\":\"FEMALE\",\"buyerid\":\"buyerid_test\",\"customdata\":\"customdata_test\", " +
            "\"keywords\":\"keyword1,keyword2\",\"geo\":{ \"lat\":1.5, \"lon\":2.5 }, \"ext\":{ \"key1\":\"string\", " +
            "\"key2\":1, \"object\":{ \"inner\":\"value\" } }}}]";

    final String EXTRA_OPEN_RTB = "EXTRA_OPEN_RTB";
    final String EXTRA_EIDS = "EXTRA_EIDS";
    final String IABConsent_Settings = "IABConsent_Settings";
}