package OMSDK;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class OMSDKSessionDescriptor {

    private final OMSDKEventParser omsdkParser;
    private final ArrayList<String> events;

    public final String sessionID;
    public final int index;

    public OMSDKSessionDescriptor(int index, String sessionID, ArrayList<String> events, OMSDKEventParser eventParser) {
        this.omsdkParser = eventParser;
        this.index = index;
        this.sessionID = sessionID;
        this.events = events;
    }

    public static class EVENT_TYPE {
        public static final String SESSION_START = "sessionStart";
        public static final String SESSION_FINISH = "sessionFinish";
        public static final String IMPRESSION = "impression";
        public static final String GEOMETRY_CHANGE = "geometryChange";
        public static final String AD_USER_INTERACTION = "adUserInteraction";
        public static final String PLAYER_STATE_CHANGE = "playerStateChange";

        public static final String LOADED = "loaded";
        public static final String START = "start";
        public static final String FIRST_QUARTILE = "firstQuartile";
        public static final String MID_POINT = "midpoint";
        public static final String THIRD_QUARTILE = "thirdQuartile";
        public static final String COMPLETE = "complete";

        public static final String PAUSE = "pause";
        public static final String RESUME = "resume";

    }

    public static class EVENT_VALUE {
        public static final String APP = "app";
        public static final String NATIVE = "native";
        public static final String HTML = "html";
        public static final String CLID = "clid";
        public static final String VLID = "vlid";
        public static final String APP_ID = "org.prebid.mobile.renderingtestapp";
        public static final String APP_ID_ANDROID = "org.prebid.mobile.renderingtestapp";
        public static final String LIBRARY_VERSION = "1.3.17-Prebidorg";
        public static final String LIBRARY_VERSION_ANDROID = "1.3.17-Prebidorg";
        public static final String OBSTRUCTED = "obstructed";
        public static final String BACKGROUNDED = "backgrounded";
        public static final String NOT_FOUND = "notFound";
        public static final String CLIPPED = "clipped";
        public static final String MEDIA_TYPE_VIDEO = "video";
        public static final String MEDIA_TYPE_DISPLAY = "display";
        public static final String PLAYER_STATE_NORMAL = "normal";
        public static final String PLAYER_STATE_EXPANDED = "expanded";
        public static final String CLICK = "click";
        public static final String POSITION_STANDALONE = "standalone";
        public static final String IABTECHNIC = "iabtechlab-openx";
        public static final String UNDEFINED = "undefined";
    }

    /**
     * Returns the number of events with given type
     */
    public int eventCount(String eventType) {
        return getAllEventsWithType(eventType).size();
    }

    /**
     * Returns the impression event string
     */
    public String getEventImpression() {
        return getEventByType(EVENT_TYPE.IMPRESSION);
    }

    /**
     * Checks all obligatory events for ad.
     */
    public void checkOMBaseEvents(String platformName) {
        OMSDKAssert.assertTrue(checkSessionStart()
                .and(checkImpression())
                .and(checkVerificationParameter())
                .and(checkSessionContextEnvironmentIsApp())
                .and(checkAdSessionTypeIsNativeOrHtml())
                .and(checkContextSupportsClidAndVlid())
                .and(checkAppIdAndLibraryVersion(platformName))
                .and(checkSessionFinish())
                .and(checkViewable())
                .and(checkValidGeometry()));
    }

    public void checkNativeOMBaseEvents(String platformName) {
        OMSDKAssert.assertTrue(checkSessionStart()
                .and(checkImpression())
                .and(checkVerificationParameterNativeAds())
                .and(checkSessionContextEnvironmentIsApp())
                .and(checkAdSessionTypeNativeAds())
                .and(checkContextSupportsClidAndVlid())
                .and(checkAppIdAndLibraryVersion(platformName))
                .and(checkSessionFinish())
                .and(checkViewable())
                .and(checkValidGeometry()));
    }

    public void checkNoObstructions() {
        OMSDKAssert.assertTrue(checkNoObstructionsPresent());
    }

    /**
     * Checks all obligatory events for backgrounded ad.
     */
    public void checkHideAndRestoreViewabilityWithReasons(String[] reasons) {
        OMSDKAssert.assertTrue(checkHideAndRestoreViewability(reasons));
    }

    /**
     * Checks all obligatory events for ad that should not have any viewability == 100 due to obstructions.
     */
    public void checkOMWithObstructions() {
        OMSDKAssert.assertTrue(checkHasObstructions());
    }

    /**
     * Checks events for clicking on ad's End Card
     */
    public void checkOMEndCardUserInteractionsAndClick() {
        final String event = getEventByType(EVENT_TYPE.AD_USER_INTERACTION);
        final String interaction = omsdkParser.getUserInteraction(event);
        final boolean containsClick = interaction.equals(EVENT_VALUE.CLICK);
        OMSDKAssert.assertTrue(new OMSDKCheckResult(containsClick, errorMessage("unexpected AdUserInteractionType - '%s', must be '%s'", interaction, EVENT_VALUE.CLICK)));
    }

    /**
     * Checks playback events in particular order
     */
    public void checkVideoPlaybackEvents() {
        ArrayList<String> expectedEvents = new ArrayList<String>();
        expectedEvents.add(EVENT_TYPE.LOADED);
        expectedEvents.add(EVENT_TYPE.START);
        expectedEvents.add(EVENT_TYPE.FIRST_QUARTILE);
        expectedEvents.add(EVENT_TYPE.MID_POINT);
        expectedEvents.add(EVENT_TYPE.THIRD_QUARTILE);
        expectedEvents.add(EVENT_TYPE.COMPLETE);
        checkPlaybackEvents(expectedEvents);
    }

    public void checkOutstreamPlaybackEvents(String platformName) {
        ArrayList<String> expectedEvents = new ArrayList<String>();
        expectedEvents.add(EVENT_TYPE.LOADED);
        expectedEvents.add(EVENT_TYPE.START);
        expectedEvents.add(EVENT_TYPE.FIRST_QUARTILE);
        expectedEvents.add(EVENT_TYPE.MID_POINT);
        expectedEvents.add(EVENT_TYPE.THIRD_QUARTILE);
        expectedEvents.add(EVENT_TYPE.COMPLETE);
        if (platformName.equalsIgnoreCase("iOS")) {
            checkPlaybackEvents(expectedEvents);
        } else {
            checkPlaybackEventsAndroid(expectedEvents);
        }
    }

    private void checkPlaybackEvents(ArrayList<String> expectedEvents) {
        System.out.println("Events: "+expectedEvents);

        for (String event : events) {
            System.out.println("Get event: "+omsdkParser.getEventType(event));
            if (omsdkParser.getEventType(event).equals(expectedEvents.get(0))) {
                expectedEvents.remove(0);
                if (expectedEvents.isEmpty()) {
                    break;
                }
            }
        }

        OMSDKCheckResult res = new OMSDKCheckResult(expectedEvents.isEmpty(), errorMessage("Session does not contain events: %s", String.join(",", expectedEvents)));

        OMSDKAssert.assertTrue(res);
        checkVideoStartEventPlayerVolume1();
    }

    /**
     * Checks playback events in particular order
     * used for Video300x250Ads
     */
    private void checkPlaybackEventsAndroid(ArrayList<String> expectedEvents) {
        for (String event : events) {
            if (omsdkParser.getEventType(event).equals(expectedEvents.get(0))) {
                expectedEvents.remove(0);
                if (expectedEvents.isEmpty()) {
                    break;
                }
            }
        }

        OMSDKCheckResult res = new OMSDKCheckResult(expectedEvents.isEmpty(), errorMessage("Session does not contain events: %s", String.join(",", expectedEvents)));

        OMSDKAssert.assertTrue(res.and(checkVideoStartEventPlayerVolume0()));
    }

    /**
     * Checks properties of "start" event
     * - player volume must be 1.0
     * - device volume should be bigger than 0
     */
    public void checkVideoStartEventPlayerVolume1() {
        final String event = getEventByType(EVENT_TYPE.START);

        final double playerVolume = Double.parseDouble(omsdkParser.getVideoPlayerVolume(event));
        final double deviceVolume = Double.parseDouble(omsdkParser.getDeviceVolume(event));

        final boolean isValidPlayerVolume = playerVolume == 1.0;
        final boolean isValidDeviceVolume = deviceVolume > 0;

        OMSDKAssert.assertTrue(new OMSDKCheckResult(isValidPlayerVolume, errorMessage("Player volume [%f] must be 1", playerVolume))
                .and(new OMSDKCheckResult(isValidDeviceVolume, errorMessage("Device volume [%f] must be bigger than 0", deviceVolume))));
    }

    public void checkVideoStartEvent(String platformName) {
        if (platformName.equalsIgnoreCase("iOS")) {
            checkVideoStartEventPlayerVolume1();
        } else {
            checkVideoStartEventPlayerVolume0();
        }
    }

    /**
     * Checks properties of "start" event
     * - player volume must be 0
     * - device volume should be bigger than 0
     * used for Video300x250Ads
     */
    public OMSDKCheckResult checkVideoStartEventPlayerVolume0() {
        final String event = getEventByType(EVENT_TYPE.START);

        final double playerVolume = Double.parseDouble(omsdkParser.getVideoPlayerVolume(event));
        final double deviceVolume = Double.parseDouble(omsdkParser.getDeviceVolume(event));

        final boolean isValidPlayerVolume = playerVolume == 0;
        final boolean isValidDeviceVolume = deviceVolume > 0;

        return new OMSDKCheckResult(isValidPlayerVolume, errorMessage("Player volume [%f] must be 0", playerVolume))
                .and(new OMSDKCheckResult(isValidDeviceVolume, errorMessage("Device volume [%f] must be bigger than 0", deviceVolume)));
    }

    /**
     * Checks if event's media type is 'video'
     */
    public void checkMediaTypeIsVideo() {
        final String impressionEvent = getEventImpression();
        final String mediaType = omsdkParser.getMediaType(impressionEvent);
        final boolean isValidMediaType = mediaType.equals(EVENT_VALUE.MEDIA_TYPE_VIDEO);

        OMSDKAssert.assertTrue(new OMSDKCheckResult(isValidMediaType, errorMessage("Unexpected media type: '%s'. Must be '%s'.", mediaType, EVENT_VALUE.MEDIA_TYPE_VIDEO)));
    }

    /**
     * Checks if session contains 'pause' and 'resume' events
     */
    public void checkPauseAndResumeAreEqual() {
        final int eventIndexPause = getAllEventsWithType(EVENT_TYPE.PAUSE).size();
        final int eventIndexResume = getAllEventsWithType(EVENT_TYPE.RESUME).size();

        OMSDKAssert.assertTrue(new OMSDKCheckResult(eventIndexPause != -1, errorMessage("The 'pause' event is not found."))
                .and(new OMSDKCheckResult(eventIndexResume != -1, errorMessage("The 'resume' event is not found.")))
                .and(new OMSDKCheckResult(eventIndexPause == eventIndexResume, errorMessage("Pause and Resume are not equal"))));
    }

    /**
     * Checks properties of {@link EVENT_TYPE#LOADED} event
     * - skippable must be false
     * - autoPlay must be false
     * - position must be 'standalone'
     */
    public void checkNonAutoPlaySkippableAndStandalonePosition() {
        String loadedEvent = getEventByType(EVENT_TYPE.LOADED);

        boolean skippable = omsdkParser.isSkippable(loadedEvent);
        boolean autoPlay = omsdkParser.isAutoPlay(loadedEvent);
        String position = omsdkParser.getPosition(loadedEvent);

        OMSDKAssert.assertTrue(new OMSDKCheckResult(!skippable, errorMessage("The video is skippable"))
                .and(new OMSDKCheckResult(!autoPlay, errorMessage("The video is autoPlayed")))
                .and(new OMSDKCheckResult(position.equals(EVENT_VALUE.POSITION_STANDALONE), errorMessage("Position is not standalone"))));
    }

    /**
     * Goes through all geometry change events to find a pattern:
     * - viewability = 100     (totally visible)
     * - viewability = 0       (hidden) with given reasons
     * - viewability = 100     (totally visible again)
     */
    private OMSDKCheckResult checkHideAndRestoreViewability(String[] hiddenReasons) {

        boolean firstViewable = false;
        boolean hidden = false;
        boolean restoredViewable = false;

        ArrayList<String> geometryChangeEvents = getAllEventsWithType(EVENT_TYPE.GEOMETRY_CHANGE);
        for (String event : geometryChangeEvents) {

            if (!firstViewable) {
                firstViewable = omsdkParser.getPercentageInView(event) == 100;
                continue;
            }

            if (!hidden) {
                hidden = firstViewable && omsdkParser.getPercentageInView(event) == 0;
                if (hiddenReasons.length > 0) {
                    hidden = hidden && omsdkParser.eventContainsReasons(event, hiddenReasons);
                }

                continue;
            }

            if (!restoredViewable) {
                restoredViewable = hidden && omsdkParser.getPercentageInView(event) == 100;
            }

            if (restoredViewable) {
                break;
            }
        }

        // check viewability after
        return new OMSDKCheckResult(firstViewable, errorMessage("Session does not contain 'viewability' event"))
                .and(new OMSDKCheckResult(hidden, errorMessage("Session does not contain 'hidden' event")))
                .and(new OMSDKCheckResult(restoredViewable, errorMessage("Session does not contain 'viewability' event after 'hidden'")));
    }

    /**
     * Goes through all geometry change events to find at least one with given reasons.
     */
    public void checkGeometryChangeReasons(String[] hiddenReasons) {

        boolean reasonsFound = false;

        ArrayList<String> geometryChangeEvents = getAllEventsWithType(EVENT_TYPE.GEOMETRY_CHANGE);
        for (String event : geometryChangeEvents) {
            if (omsdkParser.getPercentageInView(event) == 0 && omsdkParser.eventContainsReasons(event, hiddenReasons)) {
                reasonsFound = true;
                break;
            }
        }

        OMSDKAssert.assertTrue(new OMSDKCheckResult(reasonsFound, errorMessage("Session does not contain events with reasons: %s", String.join(",", hiddenReasons))));
    }

    /**
     * @param eventType
     * @return the index of the first occurrence of event of given type in the list
     */
    private int getEventIndex(String eventType) {
        return events.stream()
                .map(event -> omsdkParser.getEventType(event))
                .collect(Collectors.toList())
                .indexOf(eventType);
    }

    public OMSDKCheckResult checkVerificationParameter() {
        final String impressionEvent = getEventImpression();
        final String mediaType = omsdkParser.getMediaType(impressionEvent);
        final String event = getEventByType(EVENT_TYPE.SESSION_START);
        final String verificationParameter = omsdkParser.getVerificationParameter(event);

        if (mediaType.equals(EVENT_VALUE.MEDIA_TYPE_VIDEO)) {
            return new OMSDKCheckResult(verificationParameter.contains(EVENT_VALUE.IABTECHNIC), errorMessage("The verification parameter is not 'IABTECHLAB-PREBID' "));
        } else {
            return new OMSDKCheckResult(verificationParameter.equalsIgnoreCase(EVENT_VALUE.UNDEFINED.trim()), errorMessage("The verification parameter is not 'UNDEFINED' "));
        }
    }

    public OMSDKCheckResult checkVerificationParameterNativeAds() {
        final String event = getEventByType(EVENT_TYPE.SESSION_START);
        final String verificationParameter = omsdkParser.getVerificationParameter(event);

        return new OMSDKCheckResult(verificationParameter.contains(EVENT_VALUE.IABTECHNIC), errorMessage("The verification parameter is not 'IABTECHLAB-PREBID' "));
    }

    /**
     * Checks if event with type SessionStart contain obligatory parameter
     * 'environment' and equal to event_value = 'app'.
     */
    public OMSDKCheckResult checkSessionContextEnvironmentIsApp() {
        final String event = getEventByType(EVENT_TYPE.SESSION_START);
        final String environment = omsdkParser.getEnvironment(event);
        return new OMSDKCheckResult(environment.equalsIgnoreCase(EVENT_VALUE.APP.trim()), errorMessage("Data context environment not equal to APP"));
    }

    /**
     * Checks if event with type SessionStart contain obligatory parameter
     * 'adSessionType' and equal to event_value = 'native' or 'html'.
     */
    public OMSDKCheckResult checkAdSessionTypeIsNativeOrHtml() {
        final String impressionEvent = getEventImpression();
        final String mediaType = omsdkParser.getMediaType(impressionEvent);
        final String event = getEventByType(EVENT_TYPE.SESSION_START);
        final String adSessionType = omsdkParser.getAdSessionType(event);

        if (mediaType.equals(EVENT_VALUE.MEDIA_TYPE_VIDEO)) {
            return new OMSDKCheckResult(adSessionType.equalsIgnoreCase(EVENT_VALUE.NATIVE.trim()), errorMessage("The adSessionType parameter is not 'native' "));
        } else {
            return new OMSDKCheckResult(adSessionType.equalsIgnoreCase(EVENT_VALUE.HTML.trim()), errorMessage("The adSessionType parameter is not 'html' "));
        }
    }

    public OMSDKCheckResult checkAdSessionTypeNativeAds() {
        final String event = getEventByType(EVENT_TYPE.SESSION_START);
        final String adSessionType = omsdkParser.getAdSessionType(event);

        return new OMSDKCheckResult(adSessionType.equalsIgnoreCase(EVENT_VALUE.NATIVE.trim()), errorMessage("The adSessionType parameter is not 'native' "));
    }

    /**
     * Checks if event with type SessionStart contain obligatory parameter
     * 'supports' and equal to event_value = 'vlid' and 'clid'.
     */
    public OMSDKCheckResult checkContextSupportsClidAndVlid() {
        final String event = getEventByType(EVENT_TYPE.SESSION_START);
        final String clid = omsdkParser.getClid(event);
        final String vlid = omsdkParser.getVlid(event);
        return new OMSDKCheckResult(clid.equalsIgnoreCase(EVENT_VALUE.CLID) && vlid.equalsIgnoreCase(EVENT_VALUE.VLID), errorMessage("Data context environment not equal to CLID or VLID"));
    }

    /**
     * Checks if event with type SessionStart contain obligatory parameter
     */
    public OMSDKCheckResult checkAppIdAndLibraryVersion(String platformName) {
        final String event = getEventByType(EVENT_TYPE.SESSION_START);

        final String appId = omsdkParser.getAppId(event);
        final String libraryVersion = omsdkParser.getLibraryVersion(event);

        final String validAppId = getAppId(platformName);
        final String validLibraryVersion = getLibraryVersion(platformName);

        final Boolean isValidAppID = appId.equalsIgnoreCase(validAppId);
        final Boolean isValidAppVersion = libraryVersion.equalsIgnoreCase(validLibraryVersion);

        return new OMSDKCheckResult(isValidAppID && isValidAppVersion, errorMessage(" OM data not valid " + "APP_ID " + validAppId + " != " + appId
                + " OR " + " LIBRARY_VERSION " + validLibraryVersion + " != " + libraryVersion));
    }

    /**
     * Return APP_ID according to platformName
     */
    private String getAppId(String platformName) {
        if (platformName.equalsIgnoreCase("Android")) {
            return EVENT_VALUE.APP_ID_ANDROID;
        } else {
            return EVENT_VALUE.APP_ID;
        }
    }

    /**
     * Return LIBRARY_VERSION according to platformName
     */
    private String getLibraryVersion(String platformName) {
        if (platformName.equalsIgnoreCase("Android")) {
            return EVENT_VALUE.LIBRARY_VERSION_ANDROID;
        } else {
            return EVENT_VALUE.LIBRARY_VERSION;
        }
    }

    /**
     * Checks if event with type Impression contain parameter
     * 'viewport' and not null.
     */
    public OMSDKCheckResult checkViewportDataNotEmpty() {
        final String event = getEventByType(EVENT_TYPE.IMPRESSION);
        final String viewportData = omsdkParser.getViewportData(event);
        return new OMSDKCheckResult(!viewportData.isEmpty(), errorMessage("Viewport width or height, or x, or y are empty"));
    }

    /**
     * Checks if event with type playerStateChange contain obligatory parameter
     * 'state'.
     */
    public void checkPlayerStateIsNormal() {
        final String event = getEventByType(EVENT_TYPE.PLAYER_STATE_CHANGE);
        final String playerState = omsdkParser.getPlayerState(event);
        OMSDKAssert.assertTrue(new OMSDKCheckResult(playerState.equalsIgnoreCase(EVENT_VALUE.PLAYER_STATE_NORMAL), errorMessage("playerState is not NORMAL")));
    }

    /**
     * Checks if event with type playerStateChange contain obligatory parameter
     * 'state'.
     */
    public void checkPlayerStateIsExpanded() {
        OMSDKAssert.assertTrue(new OMSDKCheckResult(isPlayerStateExpanded(), "playerState is not EXPANDED"));
    }

    public boolean isPlayerStateExpanded() {
        ArrayList<String> playerStateChanges = getAllEventsWithType(EVENT_TYPE.PLAYER_STATE_CHANGE);

        for (String event : playerStateChanges) {
            if (omsdkParser.getPlayerState(event).equalsIgnoreCase(EVENT_VALUE.PLAYER_STATE_EXPANDED)) {
                return true;
            }
        }
        return false;
    }

    // ----------------------------------------------------------------------
    // Shortcuts for checking common events.
    // ----------------------------------------------------------------------

    /**
     * Checks all obligatory properties for 'impression' event
     */
    private OMSDKCheckResult checkSessionStart() {

        final String event = getEventByType(EVENT_TYPE.SESSION_START);
        final String serviceVersion = omsdkParser.getServiceVersion(event);
        final String partnerVersion = omsdkParser.getPartnerVersion(event);
        final String environment = omsdkParser.getEnvironment(event);
        final String adSessionType = omsdkParser.getAdSessionType(event);

        return checkEventAppearance(EVENT_TYPE.SESSION_START)
                .and(new OMSDKCheckResult(!serviceVersion.isEmpty(), errorMessage("The service version is empty")))
                .and(new OMSDKCheckResult(!partnerVersion.isEmpty(), errorMessage("The partner version is empty")))
                .and(new OMSDKCheckResult(!adSessionType.isEmpty(), errorMessage("The adSessionType is empty")))
                .and(new OMSDKCheckResult(!environment.isEmpty(), errorMessage("The environment is empty")));
    }

    /**
     * Checks that all geometryChange events have valid coordinates for ad's frame
     */
    private OMSDKCheckResult checkValidGeometry() {
        OMSDKCheckResult res = new OMSDKCheckResult(true, "");

        final String eventType = EVENT_TYPE.GEOMETRY_CHANGE;

        ArrayList<String> geometryChangeEvents = getAllEventsWithType(eventType);
        for (String event : geometryChangeEvents) {

            Rectangle2D frame = omsdkParser.getOnScreenGeometry(event);
            if (frame != null) {
                res = res.and(checkGeometry(frame, eventType));
            }
        }

        return res;
    }

    private OMSDKCheckResult checkGeometry(Rectangle2D frame, String eventType) {
        final double w = frame.getWidth();
        final double h = frame.getHeight();
        final double x = frame.getX();
        final double y = frame.getY();

        return new OMSDKCheckResult(w >= 0, errorMessage("Event %s contains invalid width value: %f", eventType, w))
                .and(new OMSDKCheckResult(h >= 0, errorMessage("Event %s contains invalid height value: %f", eventType, h)))
                .and(new OMSDKCheckResult(x >= 0, errorMessage("Event %s contains invalid x value: %f", eventType, x)))
                .and(new OMSDKCheckResult(y >= 0, errorMessage("Event %s contains invalid y value: %f", eventType, y)));
    }

    /**
     * Checks that session contains only one sessionFinish event.
     */
    private OMSDKCheckResult checkSessionFinish() {
        return checkEventAppearance(EVENT_TYPE.SESSION_FINISH);
    }

    /**
     * Checks that session contains only one impression event.
     */
    private OMSDKCheckResult checkImpression() {
        return checkEventAppearance(EVENT_TYPE.IMPRESSION);
    }

    /**
     * Checks that session contains at least one geometryChange event with percentageInView == 100.
     */
    private OMSDKCheckResult checkViewable() {
        return new OMSDKCheckResult(wasTotallyViewable(), errorMessage("Session does't contain any event with viewability = 100"));
    }

    /**
     * Checks that session does not contain any geometryChange event with obstructions.
     */
    private OMSDKCheckResult checkNoObstructionsPresent() {
        return checkObstructions(false);
    }

    /**
     * Checks that session contains any geometryChange event with obstructions.
     */
    private OMSDKCheckResult checkHasObstructions() {
        return checkObstructions(true);
    }

    /**
     * Returns all events with given type
     */
    private ArrayList<String> getAllEventsWithType(String eventType) {
        return new ArrayList<String>(events.stream().filter(event -> {
            final String type = omsdkParser.getEventType(event);

            return type.equals(eventType);
        }).collect(Collectors.toList()));
    }

    /**
     * Checks that session contains at least one geometryChange event with percentageInView == 100.
     */
    private boolean wasTotallyViewable() {
        ArrayList<String> events = getAllEventsWithType(EVENT_TYPE.GEOMETRY_CHANGE);
        for (String event : events) {
            if (omsdkParser.getPercentageInView(event) == 100) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks that session contains at least one event geometryChange with obstructions.
     */
    private OMSDKCheckResult checkObstructions(boolean isExpeced) {
        ArrayList<String> events = getAllEventsWithType(EVENT_TYPE.GEOMETRY_CHANGE);
        String[] obstructionReasons = {EVENT_VALUE.OBSTRUCTED};

        for (String event : events) {
            if (omsdkParser.eventContainsReasons(event, obstructionReasons)) {
                Rectangle2D frame = omsdkParser.getObstructionGeometry(event);

                return new OMSDKCheckResult(isExpeced, errorMessage("Session contains event(s) with obstructions"))
                        .and(checkGeometry(frame, EVENT_TYPE.GEOMETRY_CHANGE));
            }
        }

        return new OMSDKCheckResult(!isExpeced, "Session does not contain event(s) with obstructions");
    }

    /**
     * Checks the number of events of given type
     */
    private OMSDKCheckResult checkEventAppearance(String eventType) {
        final int count = eventCount(eventType);
        final boolean isPassed = count == 1;

        return new OMSDKCheckResult(isPassed, errorMessage("Expected %d event %s", 1, eventType));
    }

    /**
     * Searches event with given type
     */
    private String getEventByType(String eventType) {
        for (String event : events) {
            final String type = omsdkParser.getEventType(event);

            if (type.equals(eventType)) {
                return event;
            }
        }

        return "";
    }

    /**
     * Cretaes error message with unified format
     */
    private String errorMessage(String format, Object... args) {
        String msg = String.format(format, args);
        String sessionIDString = String.format("Session [%d]: ", index);

        return sessionIDString.concat(msg);
    }

}
