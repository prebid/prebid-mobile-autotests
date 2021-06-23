package OMSDK;

import appium.common.LogValidator;
import bmp.HarParser;
import com.google.gson.JsonArray;
import com.sun.tools.javac.util.Pair;
import net.lightbody.bmp.core.har.Har;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that contains methods for getting specific events in the har responses recorded for the OMSDK test cases.
 */
public class OMSDKEventHandler {

    private final JsonArray harResponse;
    private final OMSDKEventParser omsdkParser = new OMSDKEventParserOMIDURL();
    private final ArrayList<OMSDKSessionDescriptor> sessions = new ArrayList<OMSDKSessionDescriptor>();

    /**
     * The events that are not associated with any session.
     */
    private final ArrayList<String> distinctEvents = new ArrayList<String>();


    public OMSDKEventHandler(Har har) {
        this.harResponse = HarParser.getEntriesWithEventInUrl(har, omsdkParser.requestPrefixForHar());
        parseResponse();
    }

    public OMSDKEventHandler(LogValidator logValidator) {
        this.harResponse = logValidator.getLogs(omsdkParser.requestPrefixForHar());
        parseResponse();
    }

    // ----------------------------------------------------------------------
    // Checks for all sessions during teh test
    // ----------------------------------------------------------------------

    /**
     * Checks the obligatory OM events for test and checks the number of expected sessions
     * @return the OMSDKCheckResult object with result of check
     */
    public OMSDKCheckResult checkSessionsCount(int expectedCount) {
        final int count = sessions.size();

        return checkIsOmidSupported()
                .and(new OMSDKCheckResult(count == expectedCount, String.format("Wrong sessions count. Expected - %d. Recieved - %d", expectedCount, count)));
    }

    /**
     * Returns the number of registered OM tracking sessions
     * @return the OMSDKCheckResult object with result of check
     */
    public OMSDKCheckResult checkSessions() {
        final int count = sessions.size();

        return checkIsOmidSupported()
                .and(new OMSDKCheckResult(count > 0, "Should be at least one session"));
    }

    /**
     * Checks if events contain omidSupported
     * @return the OMSDKCheckResult object with result of check
     */
    public OMSDKCheckResult checkIsOmidSupported() {
        boolean res = false;
        for (String event : distinctEvents) {
            if (omsdkParser.isOmidSupportedEvent(event)) {
                res = omsdkParser.isOmidSupported(event);
                if (!res){
                    break;
                }
            }
        }

        // The OmidSupported event doesn't belong to particular session (it doesn't have a sessionID param).
        // So we can check it only on EventHandler layer (the scope of test case).
        return new OMSDKCheckResult(res, "The add does not receive the OM VV script.");
    }

    // ----------------------------------------------------------------------
    // Extract sessions data
    // ----------------------------------------------------------------------

    /**
     * Returns the number of registered impressions
     * @return the number of registered impressions
     */
    public int getImpressionsCount() {
        return eventCount();
    }

    /**
     * Returns the first registered session
     * @return the first registered session
     */
    public OMSDKSessionDescriptor getFirstSession() {
        return sessions.get(0);
    }

    /**
     * Returns the registered session by given index
     * @return the registered session by given index
     */
    public OMSDKSessionDescriptor[] getSessions() {
        return this.sessions.toArray(new  OMSDKSessionDescriptor[0]);
    }

    // ----------------------------------------------------------------------
    // Internal Methods
    // ----------------------------------------------------------------------

    /**
     * The top level method that converts raw data to the list of OMSDKSessionDescriptor objects
     */
    private void parseResponse() {
        // Sort events by sessionID
        ArrayList<Pair<String, ArrayList<String>>> sessions = splitEventsBySessionID();

        // Prepare session Descriptors
        for (int i = 0; i < sessions.size(); i++) {
            Pair<String, ArrayList<String>> sessionRecord = sessions.get(i);
            this.sessions.add( new OMSDKSessionDescriptor(i, sessionRecord.fst, sessionRecord.snd, omsdkParser));
        }
    }

    /**
     * Returns the list of structurized raw data corresponded to OM sessions
     * @return ArrayList<Pair<String, ArrayList<String>>>
     *     Pair:
     *     - key: the SessionID
     *     - value: the list of events
     *
     *  We have to use list to save the order of sessions
     */
    private ArrayList<Pair<String, ArrayList<String>>> splitEventsBySessionID() {

        Map<String, ArrayList<String>> sessionsRepository = new HashMap<String, ArrayList<String>>();
        ArrayList<String> sessionsOrder = new ArrayList<String>(); // Need to restore original sequence of sessions

        int size = harResponse.size();
        for (int i = 0; i < size; i++) {

            final String event;
            final String sessionId;

            event = omsdkParser.extractEventFromHarRecord(harResponse.get(i).toString());

            sessionId = omsdkParser.getSessionID(event);
            if (sessionId.isEmpty()) {
                distinctEvents.add(event);
                continue;
            }

            // Appearance of the new session
            if (!sessionsRepository.containsKey(sessionId)) {
                sessionsRepository.put(sessionId, new ArrayList<String>());
                sessionsOrder.add(sessionId);
            }

            // Add event to the session
            sessionsRepository.get(sessionId).add(event);
        }

        ArrayList<Pair<String, ArrayList<String>>> res = new ArrayList<Pair<String, ArrayList<String>>>();
        for (String sessionId : sessionsOrder) {
            res.add(new Pair<>(sessionId, sessionsRepository.get(sessionId)));
        }

        return res;
    }

    /**
     * Returns the total number of events with given name in all sessions
     * @return the total number of events with given name in all sessions
     */
    private int eventCount() {
        return sessions.stream()
                .map( session -> session.eventCount(OMSDKSessionDescriptor.EVENT_TYPE.IMPRESSION) )
                .reduce(0, (Integer a, Integer b) -> a + b);
    }
}
