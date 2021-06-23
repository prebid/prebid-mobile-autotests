package utils;

import java.io.InputStream;
import java.util.Scanner;

public class FileUtils {

    /**
     * @param filePath which is located in test resources. Example of valid path appium/custom_responses/av_Android.json
     * @return JSON or empty string
     */
    public static String getJsonStringFromResourceFile(String filePath) {
        InputStream resourceAsStream = RequestValidator.class.getClassLoader().getResourceAsStream(filePath);
        Scanner scanner = new Scanner(resourceAsStream).useDelimiter("\\A");
        return scanner.hasNext() ? scanner.next() : "";
    }
}
