package bmp;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.lightbody.bmp.core.har.*;
import net.minidev.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.InvalidArgumentException;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class HarParser {
    private static final Logger logger = Logger.getLogger(HarParser.class.getName());

    /**
     * Checks is har log empty
     *
     * @param harLog Har log
     * @return boolean
     */
    public static boolean isHarEmpty(Har harLog) {
        return harToJson(harLog).isJsonNull();
    }

    /**
     * Counts event entries in har log (based on request url value)
     *
     * @param harLog Har log
     * @param event  String event to be looked for in request url value
     * @return int number of entries with given event
     */
    public static int countEventEntries(Har harLog, String event) {
        return getEntriesWithEventInUrl(harLog, event).size();
    }

    /**
     * Counts event entries in har log (based on request url value) with response
     * code
     *
     * @param harLog Har log
     * @param event  String event to be looked for in request url value
     * @param code   int response code
     * @return int number of entries with given event
     */
    public static int countEventEntries(Har harLog, String event, int code) {
        return getEntriesWithEventInUrlAndCode(harLog, event, code).size();
    }

    public static int countArgInPostdata(Har harLog, String event, String argName, String argValue) {
        JSONArray count = new JSONArray();
        // get 'request' part of a particular url for a given event, ex: url for an
        // acjEvent
        JsonArray allUrls = getEntriesWithEventInUrl(harLog, event);
        for (JsonElement eachURL : allUrls) {
            // for each acj url, get the postData args
            JsonArray allParams = eachURL.getAsJsonObject().getAsJsonObject("request").getAsJsonObject("postData")
                    .getAsJsonArray("params");

            for (JsonElement eachParam : allParams) {
                // check if the arg name is matching with what we are looking for
                if (eachParam.getAsJsonObject().get("name").getAsString().equals(argName)
                        && argValue.equals(eachParam.getAsJsonObject().get("value").getAsString())) {

                    // check if it's arg value is matching with what we are looking for
                    count.add(eachURL);

                }
            }
        }
        // get the count of all matching arg(ex: auid:12345)
        return count.size();
    }

    /**
     * Gets header value by header name for specific event request
     *
     * @param harLog     Har log
     * @param event      String event to be looked for in request url value
     * @param headerName String header name
     * @return String header value
     * @throws NoSuchFieldException when there is not such header name
     */
    public static String getHeaderValueOfEventRequest(Har harLog, String event, String headerName)
            throws NoSuchFieldException {
        for (JsonElement header : getHeadersOfEventRequest(harLog, event)) {
            if (header.getAsJsonObject().get("name").getAsString().equals(headerName)) {
                return header.getAsJsonObject().get("value").toString();
            }
        }
        throw new NoSuchFieldException(String.format("No header: %s in event: %s request.", headerName, event));
    }

    /**
     * Extract entries from har log
     *
     * @param harLog Har log
     * @return JsonArray with entries
     */
    public static JsonArray getEntries(Har harLog) {
        return HarParser.harToJson(harLog).getAsJsonObject("log").getAsJsonArray("entries");
    }

    /**
     * Extract entries with given string event in request url
     *
     * @param harLog Har log
     * @param event  String event to be looked for in request url value
     * @return JsonArray entries with specific event
     */
    public static JsonArray getEntriesWithEventInUrl(Har harLog, String event) {
        JsonArray results = new JsonArray();

        for (JsonElement entry : getEntries(harLog)) {
            System.out.println("Event: "+entry.getAsJsonObject().getAsJsonObject("request").get("url").getAsString());
            if (entry.getAsJsonObject().getAsJsonObject("request").get("url").getAsString().contains(event)) {
                results.add(entry);
            }
        }
        return results;
    }

    /**
     * Extract entries with given string event in request url and response code
     *
     * @param harLog Har log
     * @param event  String event to be looked for in request url value
     * @param code   int response code
     * @return JsonArray entries with specific event
     */
    private static JsonArray getEntriesWithEventInUrlAndCode(Har harLog, String event, int code) {
        JsonArray results = new JsonArray();
        for (JsonElement entry : getEntries(harLog)) {
            if (entry.getAsJsonObject().getAsJsonObject("request").get("url").getAsString().contains(event)
                    && entry.getAsJsonObject().getAsJsonObject("response").get("status").getAsInt() == code) {
                results.add(entry);
            }
        }
        return results;
    }

    /**
     * Gets entries from 'request' part of the of entries in har log, for a
     * particular postRequest
     *
     * @param harLog           Har log
     * @param postRequestEvent String event to be looked for, in request url value
     * @return JsonObject with request body
     */
    public static JsonObject getRequestPostData(Har harLog, String postRequestEvent) {
        return getEntriesWithEventInUrl(harLog, postRequestEvent).get(0).getAsJsonObject().getAsJsonObject("request");
    }

    public static JsonObject getResponsePostData(Har harLog, String postRequestEvent) {
        return getEntriesWithEventInUrl(harLog, postRequestEvent).get(0).getAsJsonObject().getAsJsonObject("response");
    }

    /**
     * Gets headers of first request in har log with specific event
     *
     * @param harLog Har log
     * @param event  String event to be looked for in request url value
     * @return JsonArray with headers
     */
    private static JsonArray getHeadersOfEventRequest(Har harLog, String event) {
        return getRequestPostData(harLog, event).getAsJsonArray("headers");
    }

    /**
     * Gets postData params of first post request in har log
     *
     * @param harLog           Har log
     * @param postRequestEvent String event to be looked for in request url value
     * @return JsonArray with postData params
     */
    public static JsonArray getRequestPostDataParams(Har harLog, String postRequestEvent) {
        return getRequestPostData(harLog, postRequestEvent).getAsJsonObject("postData").getAsJsonArray("params");
    }

    public static String getRequestPostDataParamsNameString(Har harLog, String postRequestEvent) {
        List<String> listOfNamesValues = new ArrayList<String>();
        String finalNameString = "";
        int requestSize = getRequestPostDataParams(harLog, postRequestEvent).size();
        for(int i = 0; i < requestSize; i++){
            listOfNamesValues.add(getRequestPostDataParamsName(harLog, postRequestEvent, i));
        }
        for (String nameString : listOfNamesValues){
            finalNameString = finalNameString + nameString;
        }
        return finalNameString;
    }

    private static String getRequestPostDataParamsName(Har harLog, String postRequestEvent, int objectNumber){
        return getRequestPostDataParams(harLog, postRequestEvent).get(objectNumber).getAsJsonObject().get("name").getAsString();
    }

    public static JSONObject getRequestPostDataParamsNameJSON(Har harLog, String postRequestEvent) {
        return new JSONObject(getRequestPostDataParamsNameString(harLog, postRequestEvent));
    }

    public static JSONObject getRequestPostDataJSON(Har harLog, String postRequestEvent) {
        String postDataParamsString = null;
        for (JsonElement postDataParams : getRequestPostDataParams(harLog, postRequestEvent)) {
            postDataParamsString = postDataParams.getAsJsonObject().get("name").getAsString();
        }
        return new JSONObject(postDataParamsString);
    }

    public static JsonElement getRequestPostDataText(Har harLog, String postRequestEvent) {
        return getRequestPostData(harLog, postRequestEvent).getAsJsonObject("postData").get("text");
    }

    public static JSONObject getRequestPostDataTextJson(Har harLog, String postRequestEvent) {
        return new JSONObject(getRequestPostDataText(harLog, postRequestEvent).getAsString());
    }

    /**
     * Gets the value of a particular postData argument
     *
     * @param harLog           Har log
     * @param postRequestEvent String event to be looked for in request url value
     * @param postDataArgName  postData Argument name
     * @return String value of an argument in postData of a request
     * @throws NoSuchFieldException when there is no such arg in the postData params
     */
    public static String getValueOfPostDataArg(Har harLog, String postRequestEvent, String postDataArgName)
            throws NoSuchFieldException {
        for (JsonElement postDataParams : getRequestPostDataParams(harLog, postRequestEvent)) {
            if (postDataParams.getAsJsonObject().get("name").getAsString().equals(postDataArgName)) {
                return postDataParams.getAsJsonObject().get("value").toString();
            }
        }
        throw new NoSuchFieldException(
                String.format("No postData arg: %s in event: %s request.", postDataArgName, postRequestEvent));
    }

    /**
     * Gets response body for first entry with given event
     *
     * @param harLog Har log
     * @param event  String event to be looked for in request url value
     * @return JsonObject with response body
     */
    private static JsonObject getResponseBodyOfEventRequest(Har harLog, String event) {
        return getEntriesWithEventInUrl(harLog, event).get(0).getAsJsonObject().getAsJsonObject("response");
    }

    private static JsonObject getResponseContent(Har harLog, String event) {
        return getResponsePostData(harLog, event).getAsJsonObject("content");
    }

    private static JsonElement getResponseText(Har harLog, String event) {
        return getResponseContent(harLog, event).get("text");
    }

    public static JSONObject getResponseTextJson(Har harLog, String event) {
        return new JSONObject(getResponseText(harLog, event).getAsString());
    }

    /**
     * Gets response code of entry with given event
     *
     * @param harLog Har log
     * @param event  String event to be looked for in request url value
     * @return int status code
     */
    public static int getResponseCodeOfEventRequest(Har harLog, String event) {
        return getResponseBodyOfEventRequest(harLog, event).get("status").getAsInt();
    }

    /**
     * Gets _error field from response for first request in har log with specific
     * event
     *
     * @param harLog Har log
     * @param event  String event to be looked for in request url value
     * @return String error message
     */
    public static String getResponseErrorOfEventRequest(Har harLog, String event) {
        try {
            return getResponseBodyOfEventRequest(harLog, event).get("_error").getAsString();
        } catch (NullPointerException e) {
            return String.format("There was no error message in response for %s", event);
        }
    }

    /**
     * Decodes har log to string
     *
     * @param har har log
     * @return String representation of har log
     */
    private static String harToString(Har har) {
        StringWriter writer = new StringWriter();
        try {
            har.writeTo(writer);
        } catch (IOException | NullPointerException e) {
            return "";
        }

        return writer.toString();
    }

    /**
     * Decodes har log to json
     *
     * @param har har log
     * @return JsonElement representation of har log
     */
    private static JsonObject harToJson(Har har) {
        return new JsonParser().parse(harToString(har)).getAsJsonObject();
    }


    public static String getResponseTextName(Har harLog, String event) {
        return getEntriesWithEventInUrl(harLog, event).get(0).getAsJsonObject().getAsJsonObject("response")
                .getAsJsonObject("content")
                .get("text")
                .getAsString();
    }

    public static boolean checkExpectedId(Har harLog, String event, String parameterName) {
        return getResponseTextName(harLog, event).contains(parameterName);
    }

    /**
     * @param log   HarLog with all requests
     * @param event to search the request by
     * @return HarRequest which request url ends with {@param event}
     */
    public static HarRequest getLastValidRequest(HarLog log, String event) {
        if (log == null) {
            throw new InvalidArgumentException("HarLog is null");
        }

        List<HarEntry> entries = log.getEntries();

        if (entries == null || entries.isEmpty()) {
            throw new InvalidArgumentException("HarEntry list is empty or null");
        }

        for (int i = entries.size() - 1; i >= 0; i--) {
            HarEntry entry = entries.get(i);
            HarRequest request = entry.getRequest();

            if (request == null) {
                continue;
            }

            String url = request.getUrl();
            if (url != null && url.endsWith(event)) {
                return request;
            }
        }
        return null;
    }

    /**
     * @param harRequest    value which contains request with desired parameters
     * @param parameterName name of required parameter
     * @return found parameter value as {@link JSONObject} or null
     */
    public static JSONObject getRequestParamJson(HarRequest harRequest, String parameterName) {
        if (harRequest == null || harRequest.getPostData() == null) {
            throw new InvalidArgumentException("HarRequest or HarPostData is null.");
        }

        HarPostData postData = harRequest.getPostData();
        List<HarPostDataParam> params = postData.getParams();

        if (params == null || params.isEmpty()) {
            throw new InvalidArgumentException("HarPostDataParam list is empty or null.");
        }

        for (HarPostDataParam param : params) {
            String name = param.getName();
            if (name != null && name.equalsIgnoreCase(parameterName)) {
                return new JSONObject(param.getValue());
            }
        }
        return null;
    }
}
