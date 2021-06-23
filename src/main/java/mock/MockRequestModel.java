package mock;

import java.util.Map;

public class MockRequestModel {

    private final String path;
    private final String host;
    private final String method;
    private final String body;
    private final Map<String, String> queryString;

    public MockRequestModel(String path, String host, String method, String body, Map<String, String> queryString) {
        this.path = path;
        this.host = host;
        this.method = method;
        this.body = body;
        this.queryString = queryString;
    }


    public String getPath() {
        return path;
    }

    public String getHost() {
        return host;
    }

    public String getMethod() {
        return method;
    }

    public String getBody() {
        return body;
    }

    public Map<String, String> getQueryString() {
        return queryString;
    }

    @Override
    public String toString() {
        return "MockRequestModel{" +
                "path='" + path + '\'' +
                ", host='" + host + '\'' +
                ", method='" + method + '\'' +
                ", body='" + body + '\'' +
                ", queryString=" + queryString +
                '}';
    }
}
