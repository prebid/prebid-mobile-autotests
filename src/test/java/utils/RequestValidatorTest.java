package utils;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RequestValidatorTest {
    private JSONObject mModelAcjRequest;
    private JSONObject mModelVastRequest;

    @Before
    public void setup() {
        mModelAcjRequest = getJsonObjectFromFile("appium/custom_requests/Android_acj.json");
        mModelVastRequest = getJsonObjectFromFile("appium/custom_requests/av_Android.json");
    }

    @Test
    public void acjRequestValid_ReturnTrue() {
        JSONObject validAcjRequest = getJsonObjectFromFile("appium/custom_requests/acj_request_valid.json");
        assertTrue(validAcjRequest, mModelAcjRequest);
    }

    @Test
    public void acjUpperLevelKeyMissing_ReturnFalse() {
        // missing "device" key
        JSONObject acjRequestKeyMissing = getJsonObjectFromFile("appium/custom_requests/acj_request_missing_key.json");
        assertFalse(acjRequestKeyMissing, mModelAcjRequest, "Root JSON: key sets are not equal.\n" +
                "Expected: [app, tmax, imp, device, user]\n" +
                "Actual: [app, tmax, imp, user]");
    }

    @Test
    public void acjNestedObjectKeyMissing_ReturnFalse() {
        // missing "connectiontype" key
        JSONObject acjRequestNestedKeyMissing = getJsonObjectFromFile("appium/custom_requests/acj_request_missing_nested_key.json");
        assertFalse(acjRequestNestedKeyMissing, mModelAcjRequest, "device: key sets are not equal.\n" +
                "Expected: [os, ifa, h, language, geo, lmt, carrier, osv, mccmnc, w, model, connectiontype, make]\n" +
                "Actual: [geo, lmt, carrier, osv, os, mccmnc, ifa, w, h, language, model, make]");
    }

    @Test
    public void acjInvalidUpperLevelValue_ReturnFalse() {
        // invalid tmax value
        JSONObject acjRequestInvalidValue = getJsonObjectFromFile("appium/custom_requests/acj_request_invalid_value.json");
        assertFalse(acjRequestInvalidValue, mModelAcjRequest, "Content for \"tmax\" key is not matching. Expected: \"3000\", Actual: \"3001\"");
    }

    @Test
    public void acjInvalidNestedObjectValue_ReturnFalse() {
        // invalid connectiontype value
        JSONObject acjRequestInvalidNestedValue = getJsonObjectFromFile("appium/custom_requests/acj_request_invalid_nested_value.json");
        assertFalse(acjRequestInvalidNestedValue, mModelAcjRequest, "Content for \"connectiontype\" key is not matching. Expected: \"2\", Actual: \"3\"");
    }

    @Test
    public void acjRedundantKeyPresent_ReturnFalse() {
        //redundant "key" key
        JSONObject acjRequestRedundantKey = getJsonObjectFromFile("appium/custom_requests/acj_request_redundant_key.json");
        assertFalse(acjRequestRedundantKey, mModelAcjRequest, "Root JSON: key sets are not equal.\n" +
                "Expected: [app, tmax, imp, device, user]\n" +
                "Actual: [app, tmax, imp, device, user, key]");
    }

    @Test
    public void vastRequestValid_ReturnTrue() {
        JSONObject validVastRequest = getJsonObjectFromFile("appium/custom_requests/vast_request_valid.json");
        assertTrue(validVastRequest, mModelVastRequest);
    }

    @Test
    public void vastUpperLevelKeyMissing_ReturnFalse() {
        // missing "device" key
        JSONObject vastRequestKeyMissing = getJsonObjectFromFile("appium/custom_requests/vast_request_missing_key.json");
        assertFalse(vastRequestKeyMissing, mModelVastRequest, "Root JSON: key sets are not equal.\n" +
                "Expected: [app, tmax, imp, device, user]\n" +
                "Actual: [app, tmax, imp, user]");
    }

    @Test
    public void vastNestedObjectKeyMissing_ReturnFalse() {
        // missing "connectiontype" key
        JSONObject vastRequestNestedKeyMissing = getJsonObjectFromFile("appium/custom_requests/vast_request_missing_nested_key.json");
        assertFalse(vastRequestNestedKeyMissing, mModelVastRequest, "device: key sets are not equal.\n" +
                "Expected: [os, ifa, h, language, geo, lmt, carrier, osv, mccmnc, w, model, connectiontype, make]\n" +
                "Actual: [geo, lmt, carrier, osv, os, mccmnc, ifa, w, h, language, model, make]");
    }

    @Test
    public void vastInvalidUpperLevelValue_ReturnFalse() {
        // invalid tmax value
        JSONObject vastRequestInvalidValue = getJsonObjectFromFile("appium/custom_requests/vast_request_invalid_value.json");
        assertFalse(vastRequestInvalidValue, mModelVastRequest, "Content for \"tmax\" key is not matching. Expected: \"3000\", Actual: \"3001\"");
    }

    @Test
    public void vastInvalidNestedObjectValue_ReturnFalse() {
        // invalid connectiontype value
        JSONObject vastRequestInvalidNestedValue = getJsonObjectFromFile("appium/custom_requests/vast_request_invalid_nested_value.json");
        assertFalse(vastRequestInvalidNestedValue, mModelVastRequest, "Content for \"connectiontype\" key is not matching. Expected: \"2\", Actual: \"3\"");
    }

    @Test
    public void vastRedundantKeyPresent_ReturnFalse() {
        //redundant "key" key
        JSONObject vastRequestRedundantKey = getJsonObjectFromFile("appium/custom_requests/vast_request_redundant_key.json");
        assertFalse(vastRequestRedundantKey, mModelVastRequest, "Root JSON: key sets are not equal.\n" +
                "Expected: [app, tmax, imp, device, user]\n" +
                "Actual: [app, tmax, imp, device, user, key]");
    }

    private JSONObject getJsonObjectFromFile(String path) {
        return new JSONObject(FileUtils.getJsonStringFromResourceFile(path));
    }

    private void assertTrue(JSONObject testRequest, JSONObject modelRequest) {
        boolean result;
        try {
            result = RequestValidator.isJsonValid(testRequest, modelRequest, RequestValidator.ROOT_JSON_KEY);
        } catch (Exception e) {
            result = false;
        }
        Assert.assertTrue(result);
    }

    private void assertFalse(JSONObject testRequest, JSONObject modelRequest, String expectedError) {
        String actualError = null;
        try {
            RequestValidator.isJsonValid(testRequest, modelRequest, RequestValidator.ROOT_JSON_KEY);
        } catch (Exception e) {
            actualError = e.getMessage();
        }
        assertEquals(expectedError, actualError);
    }
}