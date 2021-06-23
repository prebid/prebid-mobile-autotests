package AdResponseUtils;

import appium.common.TestEnvironment;
import bmp.BMPWrapper;
import com.google.gson.JsonArray;
import net.lightbody.bmp.core.har.Har;

import java.io.IOException;

public class AdResponseDescriptor {

    public static AdResponseParser AdResponseEventParser;
    private JsonArray harResponse;
    private Har har;

    public AdResponseDescriptor(TestEnvironment env) {
        if (env.logValidator != null) {
            harResponse = env.logValidator.getLogs();
        }

        if (env.bmp != null && env.logValidator != null) {
            this.har = env.bmp.getHar();
        }
    }


    public boolean checkDisplayAdIdValue(String expectedAdId) throws IOException {
        AdResponseEventParser = new AdResponseParserURL();
        return AdResponseEventParser.getDisplayAdIdValue(har, BMPWrapper.EVENT.ADCHAIN_REQUEST).equalsIgnoreCase(expectedAdId);
    }

    public boolean checkVideoAdIdValue(String expectedAdId) throws IOException, NoSuchFieldException {
        AdResponseEventParser = new AdResponseParserURL();
        return AdResponseEventParser.getVideoAdIdValue(har, BMPWrapper.EVENT.VIDEO_REQUEST).contains(expectedAdId);
    }

}
