package adapters.events;

import java.util.concurrent.TimeoutException;

public interface EventsInspector {
    void checkEvents() throws InterruptedException, TimeoutException;
}
