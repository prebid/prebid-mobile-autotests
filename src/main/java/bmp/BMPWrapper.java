package bmp;

import appium.common.LogValidator;
import com.google.gson.JsonArray;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.core.har.Har;
import org.testng.Assert;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.testng.Assert.assertTrue;

/**
 * Wrapper around BMP Server class.
 */
public class BMPWrapper extends BrowserMobProxyServer implements LogValidator {


    public static class AD_DOMAIN {
        public static final String HTTPS = "https://";
        public static final String DOMAIN = "mobile-d.openx.net";
    }

    public static class EVENT {

        public static final String ADCHAIN_REQUEST =  "/ma/1.0/acj";

        public static final String VIDEO_REQUEST = "/v/1.0/av";

        public static final String OMSDK_SESSION_JSON_VV = "/sendMessage?msg";
        public static final String OMSDK_SESSION_OMID_VV = "/sendmessage?";

    }

    public BMPWrapper() {
        super();
    }

    /**
     * Replaces all responses with given string
     *
     * @param customResponse String custom response
     */
    public void setCustomResponse(String customResponse) {
        addResponseFilter((response, contents, messageInfo) ->
                              contents.setTextContents(customResponse));
    }

    /**
     * Replaces all responses with the one provided by filepath
     *
     * @param filePath String filepath to custom response
     * @return String response read from file
     * @throws FileNotFoundException when file can't be accessed
     */
    public String setResponseFromFile(String filePath) throws FileNotFoundException {
        Scanner in = new Scanner(new FileReader(filePath));
        StringBuilder sb = new StringBuilder();
        while (in.hasNext()) {
            sb.append(in.next());
        }
        in.close();
        String customResponse = sb.toString();
        setCustomResponse(customResponse);
        return customResponse;
    }

    /**
     * Counts occurrences of given event in har log.
     *
     * @param event String event, use EVENT class
     * @return int, number of occurrences
     */
    @Override
    public int getCountOfEvent(String event) {
        return HarParser.countEventEntries(getHar(), event);
    }

    @Override
    public int getCountOfEvent(String event, int code) {
        return HarParser.countEventEntries(getHar(), event, code);
    }

    /**
     * Count occurrences of a given arg in the bodyArgs of a POST request in the har log
     *
     * @param postReqEvent a POST request, ex, an acj call
     * @param argName      the key part of a key-value pair in a body-arg
     * @param argValue     the corresponding 'value' part of a key-value pair in a body-arg
     * @return int, number of occurrences
     */
    @Override
    public int getCountOfPostDataArg(String postReqEvent, String argName, String argValue) {
        return HarParser.countArgInPostdata(getHar(), postReqEvent, argName, argValue);
    }

    /**
     * Checks if Har log is empty
     *
     * @return String har log
     * @throws AssertionError when har log is empty
     */
    @Override
    public void assertHarIsNotEmpty() throws AssertionError {
        Assert.assertFalse(HarParser.isHarEmpty(getHar()), "Har Log is empty");
    }

    /**
     * Gets request header value for given event
     *
     * @param event      String event, use EVENT class
     * @param headerName String header name
     * @return String header value
     * @throws NoSuchFieldException when there is not such header name
     */
    @Override
    public String getHeaderValueOfEventRequest(String event, String headerName)
    throws NoSuchFieldException {
        return HarParser.getHeaderValueOfEventRequest(getHar(), event, headerName);
    }

    /**
     * Gets request postData for a given event
     *
     * @param postRequest String a post request identifier, use EVENT class
     * @param postDataArg String body arg name of a POST req
     * @return String postData value
     * @throws NoSuchFieldException when there is not such header name
     */
    @Override
    public String getValueOfPostDataArg(String postRequest, String postDataArg)
    throws NoSuchFieldException {
        return HarParser.getValueOfPostDataArg(getHar(), postRequest, postDataArg);
    }

    /**
     * Gets response error value for given event
     *
     * @param event String event, use EVENT class
     * @return String error value
     */
    @Override
    public String getResponseErrorOfEventRequest(String event) {
        return HarParser.getResponseErrorOfEventRequest(getHar(), event);
    }

    /**
     * Gets response code value for given event
     *
     * @param event String event, use EVENT class
     * @return int code
     */
    @Override
    public int getResponseCodeOfEventRequest(String event) {
        return HarParser.getResponseCodeOfEventRequest(getHar(), event);
    }

    /**
     * Waits for event to occur for given number of times.
     *
     * @param eventName           String event, use EVENT class
     * @param expectedOccurrences int, number of occurrences
     * @param timeout             int, timeout in seconds
     * @param delay               int, seconds, delay after which checking har will start,
     * @throws InterruptedException because of thread.sleep
     * @throws TimeoutException     thrown when couldn't find event given number of times before timeout
     */
    @Override
    public void waitForEvent(String eventName, int expectedOccurrences, int timeout, int delay)
    throws InterruptedException, TimeoutException {
        Thread.sleep(delay * 1000);
        int actualOccurrences = 0;
        for (int i = 0; i <= timeout; i++) {
            actualOccurrences = getCountOfEvent(eventName);
            if (actualOccurrences == expectedOccurrences) {
                return;
            }
            Thread.sleep(1000);
        }
        throw new TimeoutException(String.format("Expected %d occurrences of <%s> but found %d",
                                                 expectedOccurrences,
                                                 eventName,
                                                 actualOccurrences));
    }

    /**
     * Waits for event to occur for given number of times.
     *
     * @param eventName           String event, use EVENT class
     * @param expectedOccurrences int, number of occurrences
     * @param timeout             int, timeout in seconds
     * @throws InterruptedException because of thread.sleep
     * @throws TimeoutException     thrown when couldn't find event given number of times before timeout
     */
    @Override
    public void waitForEvent(String eventName, int expectedOccurrences, int timeout)
    throws TimeoutException, InterruptedException {
        waitForEvent(eventName, expectedOccurrences, timeout, 0);
    }

    /**
     * Waits for event to occur for given number of times.
     *
     * @param eventName           String event, use EVENT class
     * @throws InterruptedException because of thread.sleep
     * @throws TimeoutException     thrown when couldn't find event given number of times before timeout
     */
    @Override
    public void waitForEvent(String eventName)
            throws TimeoutException, InterruptedException {
        waitForEvent(eventName, 1, 0, 0);
    }

    /**
     * Waits for event to occur for given number of times.
     *
     * @param eventName           String event, use EVENT class
     * @param expectedOccurrences int, number of occurrences
     * @throws InterruptedException because of thread.sleep
     * @throws TimeoutException     thrown when couldn't find event given number of times before timeout
     */
    @Override
    public void waitForEvent(String eventName, int expectedOccurrences)
    throws TimeoutException, InterruptedException {
        waitForEvent(eventName, expectedOccurrences, 0, 0);
    }


    @Override
    public void waitForHttpsEvent(String eventName, int expectedOccurrences)
            throws TimeoutException, InterruptedException {

        String Request_URL = AD_DOMAIN.HTTPS + AD_DOMAIN.DOMAIN + eventName;
        waitForEvent(Request_URL, expectedOccurrences);
    }

    @Override
    public void waitForHttpsEvent(String eventName, int expectedOccurrences, int timeout)
            throws TimeoutException, InterruptedException {

        String Request_URL = AD_DOMAIN.HTTPS + AD_DOMAIN.DOMAIN + eventName;
        waitForEvent(Request_URL, expectedOccurrences, timeout);
    }

    @Override
    public void getValueOfPostDataArgInEvent(String postRequest, String postDataArg, String containsData){
        Har har = getHar();
        JsonArray json = HarParser.getEntriesWithEventInUrl(har, postRequest);

        String expectedData = String.format("\"name\":\"%s\",\"value\":\"%s", postDataArg, containsData);
        assertTrue (json.toString().contains(expectedData), String.format("Expected {%s} but not found in %s",
                expectedData, json));
    }

    @Override
    public void waitForEventsInExpectedInterval(String DOMAIN, String eventName, int expectedOccurrences, int timeout, int expectedTimeExecution)
            throws TimeoutException, InterruptedException {

        long requirementTime = expectedTimeExecution * 1000;

        Date datestart= new Date();
        long startTime = datestart.getTime();

        String Request_URL = AD_DOMAIN.HTTPS + DOMAIN + eventName;
        waitForEvent(Request_URL, expectedOccurrences, timeout);

        Date datefinish= new Date();
        long finishTime = datefinish.getTime();
        long currentTime = finishTime - startTime;

        if (currentTime < requirementTime) {
            System.out.print("events have wrong interval for updating");
            throw new TimeoutException(String.format("Expected %d ms execution of updating for <%s> but found %d ms",
                    requirementTime,
                    eventName,
                    currentTime));
        }
    }

    @Override
    public void clearLogs() {
        newHar();
    }

    @Override
    public JsonArray getLogs(String requestPrefix) {
        return HarParser.getEntriesWithEventInUrl(getHar(), requestPrefix);
    }

    @Override
    public JsonArray getLogs() {
        return HarParser.getEntriesWithEventInUrl(getHar(), "");
    }

    @Override
    public void setLatency(long milliseconds) {
        setLatency(milliseconds, TimeUnit.MILLISECONDS);
        setReadBandwidthLimit(500000);
        setWriteBandwidthLimit(100000);
    }

    @Override
    public void cancelLatency() {
        newHar();
    }

    @Override
    public void setResponseError() {
        setCustomResponse("wrong response");
    }

    @Override
    public void cancelResponseError() {
        newHar();
    }
}
