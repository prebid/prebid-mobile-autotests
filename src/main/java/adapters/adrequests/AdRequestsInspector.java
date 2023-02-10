package adapters.adrequests;

import java.util.concurrent.TimeoutException;

public interface AdRequestsInspector {
    void checkAdRequests() throws InterruptedException, TimeoutException;
}
