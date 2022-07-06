package utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AndroidEmulatorUtils {
    private AndroidEmulatorUtils() {
    }

    public static void enableAndroidProxy() {
        if (!isAndroidProxyEnabled()) {
            CommandUtils.executeCommand("bash scripts/device/set_proxy.sh");
        }
    }

    public static void disableAndroidProxy() {
        if (isAndroidProxyEnabled()) {
            CommandUtils.executeCommand("bash scripts/device/disable_proxy.sh");
        }
    }

    public static void installAndroidBmpCertificate() {
        CommandUtils.executeCommand("bash scripts/device/install_certificate.sh");
    }

    private static boolean isAndroidProxyEnabled() {
        String proxy = CommandUtils.executeCommand("adb shell settings get global http_proxy");
        return !proxy.equals("null") && !proxy.contains(":0");
    }


    public static void main(String[] args) {

    }
}
