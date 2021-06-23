package appium.common;

import com.google.gson.JsonArray;

import java.util.concurrent.TimeoutException;

public interface LogValidator {


    void waitForHttpsEvent(String eventName, int expectedOccurrences)
            throws TimeoutException, InterruptedException;

    void waitForHttpsEvent(String eventName, int expectedOccurrences, int timeout)
            throws TimeoutException, InterruptedException;

    void getValueOfPostDataArgInEvent(String postRequest, String postDataArg, String containsData);

    void waitForEventsInExpectedInterval(String DOMAIN, String eventName, int expectedOccurrences, int timeout, int expectedTimeExecution)
            throws TimeoutException, InterruptedException;

    void clearLogs();

    int getCountOfEvent(String event);

    int getCountOfEvent(String event, int code);

    int getCountOfPostDataArg(String postReqEvent, String argName, String argValue);

    void assertHarIsNotEmpty() throws AssertionError;

    String getHeaderValueOfEventRequest(String event, String headerName)
            throws NoSuchFieldException;

    String getValueOfPostDataArg(String postRequest, String postDataArg)
            throws NoSuchFieldException;

    String getResponseErrorOfEventRequest(String event);

    int getResponseCodeOfEventRequest(String event);

    void waitForEvent(String eventName, int expectedOccurrences, int timeout, int delay)
            throws InterruptedException, TimeoutException;

    void waitForEvent(String eventName, int expectedOccurrences, int timeout)
            throws TimeoutException, InterruptedException;

    void waitForEvent(String eventName, int expectedOccurrences)
            throws TimeoutException, InterruptedException;

    void waitForEvent(String eventName)
            throws TimeoutException, InterruptedException;

    JsonArray getLogs(String requestPrefix);

    JsonArray getLogs();

    void setLatency(long milliseconds);

    void cancelLatency();

    void setResponseError();

    void cancelResponseError();
}
