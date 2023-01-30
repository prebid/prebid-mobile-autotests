package utils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TemplatesValidator {

    private static final Map<String, Object> templatesMap = new HashMap<>();

    public static final String KEY_VARIABLE = "$VARIABLE";
    public static final String KEY_CONFIG_APP_NAME = "$CONFIG_APP_NAME";

    public static void addTemplate(String key, Object value) {
        templatesMap.put(key, value);
    }

    public static void checkTemplate(JSONObject sentObject, String key, String template) throws RequestValidator.ValidationException {
        Object sentValue = sentObject.get(key);
        switch (template) {
            case KEY_VARIABLE:
                checkVariable(sentValue, key);
                break;
            case KEY_CONFIG_APP_NAME:
                checkConfigAppName(sentValue, key);
                break;
        }
    }

    private static void checkVariable(Object sentValue, String key) throws RequestValidator.ValidationException {
        if (sentValue == null) {
            throw new RequestValidator.ValidationException("Key \"" + key + "\" doesn't present in JSON");
        }
    }

    private static void checkConfigAppName(Object sentValue, String key) throws RequestValidator.ValidationException {
        Object appName = templatesMap.get(KEY_CONFIG_APP_NAME);
        if (appName instanceof String && sentValue != null) {
            assert sentValue.equals(appName) : "Value \"" + sentValue + "\" is not equal to \"" + appName + "\"";
        } else {
            throw new RequestValidator.ValidationException("Key \"" + key + "\" doesn't present in JSON");
        }
    }

}
