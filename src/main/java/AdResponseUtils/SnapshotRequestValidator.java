package AdResponseUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.fail;

public class SnapshotRequestValidator {

    private final static List<String> EXPECTED_PARAM_NAMES = initExpectedParamNames();
    private final static List<String> CONSTANT_PARAM_NAMES = initConstantParamNames();
    private final static List<String> REQUIRED_PARAM_NAMES = initRequiredParamNames();

    private static ArrayList<String> initExpectedParamNames() {
        ArrayList<String> expectedParamNames = new ArrayList<>();
        expectedParamNames.add("aus");
        expectedParamNames.add("sp");
        expectedParamNames.add("dr");
        expectedParamNames.add("vrw");
        expectedParamNames.add("rd");
        expectedParamNames.add("rm");
        expectedParamNames.add("rc");
        expectedParamNames.add("url");
        expectedParamNames.add("lt");
        expectedParamNames.add("dims");
        expectedParamNames.add("adxy");
        expectedParamNames.add("res");
        expectedParamNames.add("plg");
        expectedParamNames.add("ch");
        expectedParamNames.add("tz");
        expectedParamNames.add("ws");
        expectedParamNames.add("ifr");
        expectedParamNames.add("tws");
        expectedParamNames.add("vmt");
        expectedParamNames.add("sd");
        expectedParamNames.add("mt");
        expectedParamNames.add("nl");
        expectedParamNames.add("ul");
        return expectedParamNames;
    }

    private static ArrayList<String> initConstantParamNames() {
        ArrayList<String> constantParamNames = new ArrayList<>();
        constantParamNames.add("lmt");
        constantParamNames.add("sv");
        return constantParamNames;
    }

    private static ArrayList<String> initRequiredParamNames() {
        ArrayList<String> requiredParamNames = new ArrayList<>();
        requiredParamNames.add("auid");
        requiredParamNames.add("openrtb");
        requiredParamNames.add("ifa");
        requiredParamNames.add("lmt");
        return requiredParamNames;
    }

    /**
     * get all parameters from postData section of request.
     * check that there are present all required parameters.
     * check that there are no additional and unexpected parameters.
     * check that all parameters have not empty value.
     * check constant values of parameters.
     */
    public static void checkParametersNamesAndValues(JsonArray logs, String event, String pathToManifest) {
        JsonObject validData = getLastValidDataFromBodySectionOfRequest(logs, event);

            checkRequestJson(validData, pathToManifest);

    }

    /**
     * get all parameters from postData section of request.
     * return JsonObject
     */
    private static JsonObject getLastValidDataFromBodySectionOfRequest(JsonArray logs, String event) {
        String requestBody = "";
        for (JsonElement element : logs) {
            if (element.getAsJsonObject().get("request").getAsJsonObject().get("url").getAsString().contains(event)) {
                requestBody = element.getAsJsonObject().get("request").getAsJsonObject().get("body").getAsString();
            }
        }
        Assert.assertFalse(requestBody.isEmpty(), "No data found for event: " + event);
        JsonObject lastValidData = new JsonObject();
        String[] rawData = requestBody.split("&");
        for (String keyValue : rawData) {
            String[] data = keyValue.split("=");
            lastValidData.addProperty(data[0], data[1]);
        }
        assertTrue(lastValidData.keySet().size() != 0, "No data to check");
        return lastValidData;
    }

    /**
     * check that there are present all required parameters.
     * check that there are no additional and unexpected parameters.
     * check that all parameters have not empty value.
     * check constant values of parameters.
     *
     * @param data
     */
    private static void checkRequestJson(JsonObject data, String pathToManifest) {
        ArrayList<String> unexpectedParamNames = new ArrayList<>();
        isRequiredParamsPresent(data);
        for (String receivedParamName : data.keySet()) {
            if (REQUIRED_PARAM_NAMES.contains(receivedParamName) || EXPECTED_PARAM_NAMES.contains(receivedParamName) || CONSTANT_PARAM_NAMES.contains(receivedParamName)) {
                if (CONSTANT_PARAM_NAMES.contains(receivedParamName)) {
                    isConstantValueValid(data, receivedParamName, pathToManifest);
                }
                Assert.assertFalse(isValueOfParamEmpty(data, receivedParamName), "Values should not be empty.");
            } else {
                unexpectedParamNames.add(receivedParamName);
            }
        }
        if (unexpectedParamNames.size() > 0) {
            fail(String.format("%s is unexpected or extra param", unexpectedParamNames));
        }
    }

    private static boolean isValueOfParamEmpty(JsonObject data, String key) {
        return !data.has(key) || data.get(key).getAsString().isEmpty();
    }

    private static String getConstantParamValue(String argName, String pathToManifest) {
        String app_version = new utils.ManifestReader().initAppVersionFromManifest(pathToManifest);
        String result = "";
        switch (argName) {
            case "lmt":
                result = "1";
                break;
            case "sv":
                result = app_version;
                break;
        }
        return result;
    }

    private static void isRequiredParamsPresent(JsonObject data) {
        ArrayList<String> required = initRequiredParamNames();
        for (String name : required) {
            assertTrue(data.keySet().contains(name), "There is NO required parameters : " + name + " among received" + data.keySet());
        }
    }

    private static void isConstantValueValid(JsonObject data, String paramName, String pathToManifest) {
        String receivedValue = data.get(paramName).getAsString();
        String constantValue = getConstantParamValue(paramName, pathToManifest);
        assertTrue(receivedValue.equalsIgnoreCase(constantValue), "Values should be equal. Constant value is:" + constantValue + ", received value is: " + receivedValue);
    }

    public static void isParamValueValid(JsonArray logs, String event, String paramName, String paramValue) {
        JsonObject data = getLastValidDataFromBodySectionOfRequest(logs, event);
        String receivedValue = data.get(paramName).getAsString();
        assertTrue(receivedValue.equalsIgnoreCase(paramValue), "Values should be equal. Expected paramValue is:" + paramValue + " received value is: " + receivedValue);
    }
}
