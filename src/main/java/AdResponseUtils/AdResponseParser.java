package AdResponseUtils;

import net.lightbody.bmp.core.har.Har;

import java.io.IOException;

/**
 *
 */
public interface AdResponseParser {

    /**
     * Provides the string that could serve as a filter for Har responses.
     * @return the obligatory part of http request that could be parsed by particular implementation
     */

    String getDisplayAdIdValue(Har har, String event) throws IOException;

    String getVideoAdIdValue(Har har, String event) throws NoSuchFieldException;

}
