package utils;

import bmp.HarParser;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import net.lightbody.bmp.core.har.Har;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.InvalidArgumentException;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class RequestValidator {

    public static final String KEY_VARIABLE = "$VARIABLE";
    public static final String ROOT_JSON_KEY = "Root JSON";

    public static void validatePrebidRequest(Har data, String prebidEvent, JSONObject validJson) {
        // uncomment for debugging
        validateRequestPrebid(data, validJson, prebidEvent);
    }

    public static void validateInAppBiddingRequest(Har data, String inAppBiddingEvent, JSONObject validJson) {
        // uncomment for debugging
        validateRequestInAppBidding(data, validJson, inAppBiddingEvent);
    }

    public static void validateInAppBiddingResponse(Har data, String inAppBiddingEvent, JSONObject validJson) {
        validateResponseInAppBidding(data, validJson, inAppBiddingEvent);
    }

    public static void validatePrebidResponse(Har data, String prebidEvent, JSONObject validJson) {
        System.out.println(HarParser.getResponseTextJson(data, prebidEvent));
        validateResponsePrebid(data, validJson, prebidEvent);
    }

    public static void validateAuctionRequest(JsonArray data, JSONObject validJson) {
        validateRequest(data, validJson, "/openrtb2/auction");
    }

    public static void validateAuctionResponse(JsonArray data, JSONObject validJson) {
        validateResponse(data, validJson, "/openrtb2/auction");
    }

    public static void checkVersionParametersFromRequest(Har harLog, String validAppVer, String validAppExtPrebidVersion, String validOmidpv, String validImpDisplaymanagerver) {
        String appVer = getAppVer(harLog);
        String appExtPrebidVersion = getAppExtPrebidVersion(harLog);
        String omidpv = getOmidpv(harLog);
        String impDisplaymanagerver = getImpDisplaymanagerver(harLog);
        assertEquals(appVer, validAppVer, "app.VER " + appVer + " not equal to " + validAppVer);
        assertEquals(appExtPrebidVersion, validAppExtPrebidVersion, "app.ext.prebid.VERSION " + appExtPrebidVersion + " not equal to " + validAppExtPrebidVersion);
        assertEquals(omidpv, validOmidpv, "source.ext.OMIDPV " + omidpv + " not equal to " + validOmidpv);
        assertEquals(impDisplaymanagerver, validImpDisplaymanagerver, "imp.DISPLAYMANAGERVER " + impDisplaymanagerver + " not equal to " + validImpDisplaymanagerver);
    }

    private static String getAppVer(Har harLog) {
        JSONObject appObject = (JSONObject) HarParser.getRequestPostDataTextJson(harLog, "auction").get("app");
        return appObject.get("ver").toString();
    }

    private static String getAppExtPrebidVersion(Har harLog) {
        JSONObject appObject = (JSONObject) HarParser.getRequestPostDataTextJson(harLog, "auction").get("app");
        return appObject.getJSONObject("ext").getJSONObject("prebid").get("version").toString();
    }

    private static String getOmidpv(Har harLog) {
        JSONObject sourceObject = (JSONObject) HarParser.getRequestPostDataTextJson(harLog, "auction").get("source");
        return sourceObject.getJSONObject("ext").get("omidpv").toString();
    }

    private static String getImpDisplaymanagerver(Har harLog) {
        JSONArray impArray = (JSONArray) HarParser.getRequestPostDataTextJson(harLog, "auction").get("imp");
        return impArray.getJSONObject(0).get("displaymanagerver").toString();
    }

    private static List<String> getListStringSplittedByDelimeter(String stringToSplit, String delimiters) {
        String[] byPair = stringToSplit.split(delimiters);
        List<String> nl;
        nl = Arrays.asList(byPair);
        return nl;
    }

    //PRIVATE METHODS


    private static void validateRequest(JsonArray data, JSONObject validJson, String event) {
        if (data == null) {
            throw new InvalidArgumentException("Passed data is null");
        }

        String lastValidData = "";
        for (JsonElement element : data) {
            if (element.getAsJsonObject().get("request").getAsJsonObject().get("url").getAsString().endsWith(event)) {
                lastValidData = element.getAsJsonObject().get("request").getAsJsonObject().get("body").getAsString();
            }
        }

        JSONObject sentJson = new JSONObject(lastValidData);
        System.out.println("Sent json: " + sentJson);
        System.out.println("Valid Json: " + validJson);
        boolean checkResult;
        String errorMessage = null;
        try {
            checkResult = isJsonValid(sentJson, validJson, ROOT_JSON_KEY);
        } catch (ValidationException e) {
            checkResult = false;
            errorMessage = e.getMessage();
        }

        assertTrue(checkResult, "Invalid OpenRTB parameters: " + errorMessage);
    }

    private static void validateResponse(JsonArray data, JSONObject validJson, String event) {
        if (data == null) {
            throw new InvalidArgumentException("Passed data is null");
        }

        //TODO add validation for response, currently MOCK-server logValidator couldn't return response

        System.out.println("THIS IS JSON ARRAY OF REQUEST-RESPONSE ==>" + data + "\n");
    }

    private static void validateRequestInAppBidding(Har harLog, JSONObject validJson, String event) {
        if (harLog == null) {
            throw new InvalidArgumentException("Passed Har is null");
        }
        JSONObject sentJson = HarParser.getRequestPostDataTextJson(harLog, event);
        boolean checkResult;
        System.out.println("Sent json: " + sentJson);
        System.out.println("Valid json: " + validJson);
        String errorMessage = null;
        try {
            checkResult = isJsonValid(sentJson, validJson, ROOT_JSON_KEY);
        } catch (ValidationException e) {
            checkResult = false;
            errorMessage = e.getMessage();
        }

        assertTrue(checkResult, "Invalid InAppBidding Request parameters: " + errorMessage);
    }

    private static void validateResponseInAppBidding(Har harLog, JSONObject validJson, String event) {
        if (harLog == null) {
            throw new InvalidArgumentException("Passed Har is null");
        }
        JSONObject sentJson = HarParser.getResponseTextJson(harLog, event);
        boolean checkResult;
        String errorMessage = null;
        try {
            checkResult = isJsonValid(sentJson, validJson, ROOT_JSON_KEY);
        } catch (ValidationException e) {
            checkResult = false;
            errorMessage = e.getMessage();
        }

        assertTrue(checkResult, "Invalid InAppBidding Response parameters: " + errorMessage);
    }

    private static void validateRequestPrebid(Har harLog, JSONObject validJson, String event) {
        if (harLog == null) {
            throw new InvalidArgumentException("Passed Har is null");
        }
        JSONObject sentJson = HarParser.getRequestPostDataTextJson(harLog, event);
        boolean checkResult;
        String errorMessage = null;
        try {
            checkResult = isJsonValid(sentJson, validJson, ROOT_JSON_KEY);
        } catch (ValidationException e) {
            checkResult = false;
            errorMessage = e.getMessage();
        }

        assertTrue(checkResult, "Invalid Prebid parameters: " + errorMessage);
    }

    private static void validateResponsePrebid(Har harLog, JSONObject validJson, String event) {
        if (harLog == null) {
            throw new InvalidArgumentException("Passed Har is null");
        }
        JSONObject sentJson = HarParser.getResponseTextJson(harLog, event);
        boolean checkResult;
        String errorMessage = null;
        try {
            checkResult = isJsonValid(sentJson, validJson, ROOT_JSON_KEY);
        } catch (ValidationException e) {
            checkResult = false;
            errorMessage = e.getMessage();
        }

        assertTrue(checkResult, "Invalid Prebid parameters: " + errorMessage);
    }

    protected static boolean isJsonValid(JSONObject sentJson, JSONObject validJson, String parentKey) throws ValidationException {
        compareKeySets(parentKey, sentJson, validJson);
        Set<String> validJsonKeys = validJson.keySet();
        for (String key : validJsonKeys) {
            Object validEntry = validJson.get(key);
            if (isVariable(validEntry)) {
                checkVariable(sentJson, key);
            } else if (isArray(validEntry)) {
                checkArray(sentJson.getJSONArray(key), (JSONArray) validEntry, key);
            } else if (isObject(validEntry)) {
                checkObject(sentJson, (JSONObject) validEntry, key);
            } else {
                checkValue(sentJson.get(key).toString(), validEntry.toString(), key);
            }
        }
        return true;
    }



    private static void compareKeySets(String parentKey, JSONObject sentJson, JSONObject validJson) throws ValidationException {
        if (sentJson == null) {
            throw new ValidationException("Sent data is null");
        }
        Set<String> sample = sentJson.keySet();
        Set<String> exampleKeySet = validJson.keySet();
        if (!sample.equals(exampleKeySet)) {
            throw new ValidationException(parentKey + ": " + "key sets are not equal.\nExpected: " + exampleKeySet.toString()
                    + "\nActual: " + sample.toString());
        }
    }

    private static boolean isArray(Object entry) {
        return entry instanceof JSONArray;
    }

    private static boolean isObject(Object entry) {
        return entry instanceof JSONObject;
    }

    private static boolean isVariable(Object entry) {
        return entry instanceof String && entry.equals(KEY_VARIABLE);
    }

    private static void checkArray(JSONArray sentArray, JSONArray validArray, String key) throws ValidationException {
        if (validArray.length() != sentArray.length()) {
            throw new ValidationException(key + ": array has different number of elements");
        }
        for (int i = 0; i < validArray.length(); i++) {
            Object value = validArray.get(i);
            if (value instanceof JSONObject) {
                isJsonValid(extractObjectFromArray(sentArray, i), (JSONObject) value, key);
            } else {
                checkValue(sentArray.toString(), validArray.toString(), key);
            }
        }
    }

    private static void checkVariable(JSONObject sentObject, String key) throws ValidationException {
        if (sentObject.get(key) == null) {
            throw new ValidationException("Key \"" + key + "\" doesn't present in JSON");
        }
    }

    private static void checkObject(JSONObject sentObject, JSONObject validObject, String key) throws ValidationException {
        isJsonValid(sentObject.getJSONObject(key), validObject, key);
    }

    private static void checkValue(String sentValue, String validValue, String key) throws ValidationException {
        if (!validValue.equals(sentValue)) {
            throw new ValidationException("Content for \"" + key + "\" key is not matching. Expected: \""
                    + validValue + "\", Actual: \"" + sentValue + "\"");
        }
    }

    private static JSONObject extractObjectFromArray(JSONArray jsonArray, int index) {
        return jsonArray.getJSONObject(index);
    }

    private static class ValidationException extends Exception {
        ValidationException(String message) {
            super(message);
        }
    }
}