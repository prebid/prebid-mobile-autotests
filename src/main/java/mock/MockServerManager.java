package mock;

import appium.common.LogValidator;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

import javax.net.ssl.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class MockServerManager implements LogValidator {

    private final String DEFAULT_URL;

    // The creative requires some time to fire OM event.
    private final Integer DEFAULT_REQUEST_TIMEOUT = 3;
    private final Integer DEFAULT_REQUEST_DEALY = 0;

    private final String PATH_CLEAR_LOGS    = "/api/clear_logs";
    private final String PATH_GET_LOGS      = "/api/logs";
    private final String PATH_SET_LATENCY   = "/api/set_latency";

    private final String PATH_CANCEL_LATECY = "/api/cancel_latency";
    private final String PATH_SET_ERROR     = "/api/set_error";
    private final String PATH_CANCEL_ERROR  = "/api/cancel_error";


    public MockServerManager( String url) {
        DEFAULT_URL = "https://" + url;

        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }

                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    }
                }};

        // Ignore differences between given hostname and certificate hostname
        HostnameVerifier hv = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };

        // Install the all-trusting trust manager
        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance("SSL");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        try {
            sc.init(null, trustAllCerts, new SecureRandom());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        HttpsURLConnection.setDefaultHostnameVerifier(hv);
    }

    private URLConnection openConnectionForPath(String path) throws IOException {
        return new URL(DEFAULT_URL + path).openConnection();
    }

    @Override
    public JsonArray getLogs(String requestPrefix) {
        JsonArray results = new JsonArray();
        for (MockRequestModel model : getLoggedRequests()) {
            if (model.getPath().contains(requestPrefix)) {
                JsonObject object = new JsonObject();
                JsonObject requestObject = new JsonObject();
                JsonArray queryStringArray = new JsonArray();
                requestObject.addProperty("method", model.getMethod());
                requestObject.addProperty("url", "https://" + model.getHost() + model.getPath());
                requestObject.addProperty("bodySize", model.getBody().length());
                requestObject.addProperty("body", model.getBody());
                for (Map.Entry<String, String> entry : model.getQueryString().entrySet()) {
                    JsonObject entryObject = new JsonObject();
                    entryObject.addProperty("name", entry.getKey());
                    entryObject.addProperty("value", entry.getValue());
                    queryStringArray.add(entryObject);
                }
                requestObject.add("queryString", queryStringArray);
                object.add("request", requestObject);
                results.add(object);
            }
        }


        return results;
    }

    @Override
    public JsonArray getLogs() {
        return getLogs("");
    }

    @Override
    public void waitForEvent(String eventName, int expectedOccurrences, int timeout) throws TimeoutException, InterruptedException {
        waitForEvent(eventName, expectedOccurrences, timeout, DEFAULT_REQUEST_DEALY);
    }

    /**
     * Waits for event to occur for given number of times.
     *
     * @param eventName           String event, use EVENT class
     * @throws InterruptedException because of thread.sleep
     * @throws TimeoutException     thrown when couldn't find event given number of times before timeout
     */
    @Override
    public void waitForEvent(String eventName) throws TimeoutException, InterruptedException {
        waitForEvent(eventName, 1);
    }

    @Override
    public int getCountOfEvent(String eventName) {
        int result = 0;
        List<MockRequestModel> loggedRequests = getLoggedRequests();
        for (MockRequestModel request : loggedRequests) {

            if (request.getPath().contains(eventName)) {
                System.out.println("Request: "+request.getPath());
                result++;
            }
        }

        return result;
    }

    @Override
    public void waitForEvent(String eventName, int expectedOccurrences, int timeout, int delay) throws InterruptedException, TimeoutException {

        final int pullDelayMS = 200;
        double waitingTime = 0;
        int actualOccurrences = 0;

        if (delay > 0) {
            Thread.sleep(delay * 1000);
        }

        do {
            Thread.sleep(pullDelayMS);
            waitingTime += ((double) pullDelayMS) / 1000;

            actualOccurrences = getCountOfEvent(eventName);

            if (actualOccurrences == expectedOccurrences) {
                return;
            } else if (actualOccurrences > expectedOccurrences) {
                break;
            }
        }
        while (waitingTime < timeout);

        throw new TimeoutException(String.format("Expected %d occurrences of <%s> but found %d",
                expectedOccurrences,
                eventName,
                actualOccurrences));


    }

    @Override
    public void waitForHttpsEvent(String eventName, int expectedOccurrences) throws TimeoutException, InterruptedException {
        waitForEvent(eventName, expectedOccurrences);
    }

    @Override
    public void waitForHttpsEvent(String eventName, int expectedOccurrences, int timeout) throws TimeoutException, InterruptedException {
        waitForEvent(eventName, expectedOccurrences, timeout);
    }

    @Override
    public void getValueOfPostDataArgInEvent(String postRequest, String postDataArg, String containsData) {

    }

    @Override
    public void waitForEventsInExpectedInterval(String DOMAIN, String eventName, int expectedOccurrences, int timeout, int expectedTimeExecution) {

    }

    @Override
    public int getCountOfEvent(String event, int code) {
        return 0;
    }

    @Override
    public int getCountOfPostDataArg(String postReqEvent, String argName, String argValue) {
        return 0;
    }

    @Override
    public void assertHarIsNotEmpty() throws AssertionError {
        Assert.assertFalse(getLoggedRequests().isEmpty(), "Har Log is empty");
    }

    @Override
    public String getHeaderValueOfEventRequest(String event, String headerName) {
        return null;
    }

    @Override
    public String getValueOfPostDataArg(String postRequest, String postDataArg) {
        return null;
    }

    @Override
    public String getResponseErrorOfEventRequest(String event) {
        return null;
    }

    @Override
    public int getResponseCodeOfEventRequest(String event) {
        return 0;
    }

    @Override
    public void waitForEvent(String eventName, int expectedOccurrences) throws TimeoutException, InterruptedException {
        waitForEvent(eventName, expectedOccurrences, DEFAULT_REQUEST_TIMEOUT, DEFAULT_REQUEST_DEALY);
    }

    @Override
    public void setLatency(long milliseconds) {
        try {
            URLConnection connection = openConnectionForPath(PATH_SET_LATENCY);
            connection.setDoOutput(true);
            String data = URLEncoder.encode("latency", "UTF-8") + "=" + milliseconds;
            DataOutputStream wr = null;
            try {
                wr = new DataOutputStream(connection.getOutputStream());
                wr.writeBytes(data);
            } finally {
                if (wr != null) {
                    wr.flush();
                    wr.close();
                }
            }
            connection.getInputStream();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void cancelLatency() {
        try {
            URLConnection connection = openConnectionForPath(PATH_CANCEL_LATECY);
            connection.getInputStream();
        } catch (IOException e) {
        }
    }

    @Override
    public void setResponseError() {
        try {
            URLConnection connection = openConnectionForPath(PATH_SET_ERROR);
            connection.getInputStream();
        } catch (IOException e) {
        }
    }

    @Override
    public void cancelResponseError() {
        try {
            URLConnection connection = openConnectionForPath(PATH_CANCEL_ERROR);
            connection.getInputStream();
        } catch (IOException e) {
        }
    }

    @Override
    public void clearLogs() {
        try {
            URLConnection connection = openConnectionForPath(PATH_CLEAR_LOGS);
            connection.getInputStream();
        } catch (IOException e) {
        }
    }

    private static Map<String, String> jsonToMap(JSONObject jsonObject) {
        HashMap<String, String> result = new HashMap<>();
        for (String key : jsonObject.keySet()) {
            String value = jsonObject.get(key).toString();
            if (value.contains("\"")) {
                value = value.substring(value.indexOf('"') + 1, value.lastIndexOf('"'));
            }
            result.put(key, value);
        }
        return result;
    }

    private List<MockRequestModel> getLoggedRequests() {
        ArrayList<MockRequestModel> result = new ArrayList<>();
        try {
            URLConnection connection = openConnectionForPath(PATH_GET_LOGS);
            String data = InputStreamUtils.convert(connection.getInputStream());
            JSONObject jsonObject = new JSONObject(data);
            JSONArray requests = (JSONArray) jsonObject.get("requests");

            for (int i = 0; i < requests.length(); i++) {
                JSONObject request = (JSONObject) requests.get(i);
                MockRequestModel model = new MockRequestModel(request.getString("path"),
                        request.getString("host"),
                        request.getString("method"),
                        URLDecoder.decode(request.getString("body"), "UTF-8"),
                        jsonToMap(((JSONObject) request.get("queryString"))));
                result.add(model);
            }
            return result;
        } catch (IOException e) {
            throw new RuntimeException("Failed request to mock server");
        }
    }
}
