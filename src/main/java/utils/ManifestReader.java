package utils;

import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ManifestReader {

    /**
     * return value of commitShort key from application manifest
     */
    public String initCommitShortFromManifest(String pathToManifest) {
        return getManifestInfo(pathToManifest).getString("commitShort");
    }

    /**
     * return value of appVersion key from application manifest
     */
    public String initAppVersionFromManifest(String pathToManifest) {
        return getManifestInfo(pathToManifest).getString("appVersion");
    }

    /**
     * Return application manifest as JSONObject
     */
    private JSONObject getManifestInfo(String pathToManifest) {

        String contents = "";
        String path;
        if (Files.exists(Paths.get(pathToManifest))) {
            path = pathToManifest;
        } else {
            path = default_path(pathToManifest);
        }
        try {
            contents = new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new JSONObject(contents);
    }

    private String default_path(String pathToManifest) {
        String default_path;
        String iOSpathToManifest = "/Users/jenkins-mobile/Downloads/AppiumBuilds/PrebidMobileDemoRendering.info";

        if (pathToManifest.equalsIgnoreCase(iOSpathToManifest)) {
            default_path = "src/test/resources/appium/config/default_manifest.info";
        } else {
            default_path = "src/test/resources/appium/config/default_manifest_android.info";
        }
        return default_path;
    }
}
