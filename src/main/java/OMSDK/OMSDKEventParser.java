package OMSDK;

import java.awt.geom.Rectangle2D;

/**
 *
 */
public interface OMSDKEventParser {

    /**
     * Provides the string that could serve as a filter for Har responses.
     * @return the obligatory part of http request that could be parsed by particular implementation
     */
     String requestPrefixForHar();

    /**
     * Checks is the given event is 'isOmidSuported' event
     * @param event - the raw string for OM event received from Har
     * @return true if given event is 'isOmidSuported'
     */
     boolean isOmidSupportedEvent(String event);

    /**
     * Checks if omid is supported
     * @param event - the raw string for OM event received from Har
     * @return true if event is 'isOmidSuported' and value is true
     */
     boolean isOmidSupported(String event);

    /**
     * Extracts the value of 'skippable' from the given event.
     *
     * @param event - event with type {@link OMSDKSessionDescriptor.EVENT_TYPE#LOADED}
     * @return 'skippable' value
     */
     boolean isSkippable(String event);

    /**
     * Extracts the value of 'autoPlay' from the given event.
     *
     * @param event - event with type {@link OMSDKSessionDescriptor.EVENT_TYPE#LOADED}
     * @return 'autoPlay' value
     */
     boolean isAutoPlay(String event);

    /**
     * Extracts the value of 'position' from the given event.
     *
     * @param event - event with type {@link OMSDKSessionDescriptor.EVENT_TYPE#LOADED}
     * @return 'position' value.
     */
     String getPosition(String event);

    /**
     * Finds and returns the valuable data form the whole response that could be treated as OM event
     * @param rawHarRecord data from Har for particular request
     * @return the substring of rawHarRecord that could be treated as OM event
     */
     String extractEventFromHarRecord(String rawHarRecord);

    /**
     * Extracts the value of session ID from the given event
     * @param event - the raw string for OM event received from Har
     * @return the session ID of the given event
     */
     String getSessionID(String event);

    /**
     * Extracts the value of event type from the given event
     * @param event - the raw string for OM event received from Har
     * @return the string name of OM event type
     */
     String getEventType(String event);

    /**
     * Extracts the value of percentageInView from the given event
     * @param event - the raw string for OM event received from Har
     * @return the value of percentageInView [0...100]
     */
     int getPercentageInView(String event);

    /**
     * Extracts the value of serviceVersion from the given event
     * @param event - the raw string for OM event received from Har
     * @return the value of serviceVersion
     */
     String getServiceVersion(String event);

    /**
     * Extracts the value of partnerVersion from the given event
     * @param event - the raw string for OM event received from Har
     * @return the value of PartnerVersion
     */
     String getPartnerVersion(String event);

    /**
     * Extracts the value of environment from the given event
     * @param event - the raw string for OM event received from Har
     * @return the value of environment
     */
     String getEnvironment(String event);

     /**
     * Extracts the value of adSessionType from the given event
     * @param event - the raw string for OM event received from Har
     * @return the value of adSessionType - native or html
     */
     String getAdSessionType(String event);

    /**
     * Extracts the value of supports from the given event
     * @param event - the raw string for OM event received from Har
     * @return the value of supports - clid - for all ad types
     */
    String getClid(String event);

    /**
     * Extracts the value of supports from the given event
     * @param event - the raw string for OM event received from Har
     * @return the value of supports - vlid for video ad types
     */
    String getVlid(String event);

    /**
     * Extracts the value of appId from the given event
     * @param event - the raw string for OM event received from Har
     * @return the value of appId - org.prebid.mobile.renderingtestapp
     */
    String getAppId(String event);

    /**
     * Extracts the value of libraryVersion from the given event
     * @param event - the raw string for OM event received from Har
     * @return the value of libraryVersion
     */
    String getLibraryVersion(String event);


    /**
     * Extracts the value of partnerVersion from the given event
     * @param event - the raw string for OM event received from Har
     * @return the value of PartnerVersion
     */
     String getVerificationParameter(String event);


    /**
     * Checks if given event contains reasons for non full viewability
     * @param event - the raw string for OM event received from Har
     * @param reasons - the array of expected reasons
     * @return true if at least one of the given reasons is present in the 'reasons' property of given event
     */
     boolean eventContainsReasons(String event, String[] reasons);

    /**
     * Extracts reasons for broken viewability in the given event
     * @param event - the raw string for OM event received from Har
     * @return  string representation of 'reasons' from given event.
     *          Empty row if no reasons found.
     */
     String getViewabilityErrorReasons(String event);


    /**
     * Extracts the kind of user interaction from the given event.
     * @param event - the raw string for OM event received from Har
     * @return  the string value of user interaction kind ('click').
     *          Or empty string if event does not contain such property.
     */
     String getUserInteraction(String event);


    /**
     * Extracts the Frame of the view from the onScreenGeometry of the given event.
     * @param event - the raw string for OM event received from Har
     * @return  the description of the ad geometry (x, y, w, h).
     *          null if onScreenGeometry is absent in the event
     */
     Rectangle2D getOnScreenGeometry(String event);

    /**
     * Extracts the Frame of the view from the obstruction[0] of the given event.
     * @param event - the raw string for OM event received from Har
     * @return  the description of the ad geometry (x, y, w, h).
     *          null if obstruction[0] is absent in the event
     */
    Rectangle2D getObstructionGeometry(String event);

    /**
     * Extracts the value of ad media type.
     * @param event - the raw string for OM event received from Har
     * @return the value of mediaType property of the given event.
     */
     String getMediaType(String event);

    /**
     * Searches for device volume in given event.
     *
     * @param event event with type "start"
     * @return device volume value
     */
     String getDeviceVolume(String event);

    /**
     * Searches for video player volume in given event.
     *
     * @param event event with type "start"
     * @return video volume value
     */
     String getVideoPlayerVolume(String event);

    /**
     * Searches for viewport data in given event.
     *
     * @param event event with type "impression"
     * @return viewport width, height, x, y
     */
    String getViewportData(String event);

    /**
     * Searches for player state in given event.
     *
     * @param event event with type "playerStateChange"
     * @return player state value
     */
    String getPlayerState(String event);
}
