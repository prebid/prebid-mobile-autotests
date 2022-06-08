package utils;

import com.google.common.hash.Hashing;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;
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
        if (!isAndroidBmpCertificateInstalled()) {
            CommandUtils.executeCommand("bash scripts/device/install_certificate.sh");
        }
    }

    private static boolean isAndroidBmpCertificateInstalled() {
        return getCertificates().contains("5bc1dc75.0");
    }

    private static boolean isAndroidProxyEnabled() {
        String proxy = CommandUtils.executeCommand("adb shell settings get global http_proxy");
        return !proxy.equals("null") && !proxy.contains(":0");
    }
    private static List<String> getCertificates(){
        String certificates = CommandUtils.executeCommand("adb shell ls /data/misc/user/0/cacerts-added");
        return Arrays.stream(certificates.split(System.lineSeparator())).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        System.out.println(getCertificates());
        System.out.println(isAndroidBmpCertificateInstalled());
    }
}
