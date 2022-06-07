package utils;

import java.io.IOException;

public class AppiumUtils {
    private AppiumUtils(){}

    public static void startAppiumServer()  {
        try {
            Runtime.getRuntime().exec("bash scripts/start_appium.sh");
        } catch (IOException exception){
            System.err.println(exception.getMessage());
        }
    }
    public static void stopAppiumServer(){
        try {
            Runtime.getRuntime().exec("bash scripts/stop_appium.sh");
        } catch (IOException exception){
            System.err.println(exception.getMessage());
        }
    }

    public static void main(String[] args) {
//        AppiumUtils.startAppiumServer();
        AppiumUtils.stopAppiumServer();
    }
}
