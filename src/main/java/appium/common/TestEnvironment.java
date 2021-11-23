package appium.common;

import bmp.BMPWrapper;
import mock.MockServerManager;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Test Environment class for handling default & system properties and preparing appium capabilities
 * Override Default properties Key-Values with System properties  Key-Values.
 * e.g. -DdeviceName="iPhone 8 Plus" in command line will override deviceName defined in .properties file
 */
public class TestEnvironment {

    /**
     * Allowed environment's types ('env' property values)
     */
    public static class EnvType {
        static final String LOCAL = "local";
    }

    public String urlAdress;
    public final DesiredCapabilities capabilities = new DesiredCapabilities();
    public BMPWrapper bmp;
    public LogValidator logValidator;

    public enum TrafficInspectorKind {
        MOB_PROXY,
        MOCK_SERVER
    }

    public static final Set<TrafficInspectorKind> INSPECTORS_MOB_PROXY = new HashSet<>(Arrays.asList(TrafficInspectorKind.MOB_PROXY));
    public static final Set<TrafficInspectorKind> INSPECTORS_MOCK_SERVER = new HashSet<>(Arrays.asList(TrafficInspectorKind.MOCK_SERVER));

    private static class RemoteServer {
        public String host;
        public Integer port;
        public final String user;
        public final String key;

        RemoteServer(String host, Integer port, String user, String key) {
            this.host = host;
            this.port = port;
            this.user = user;
            this.key = key;
        }

        RemoteServer(String host, Integer port) {
            this(host, port, null, null);
        }
    }

    private static final RemoteServer appiumServer = new RemoteServer("localhost", 4723);
    private static final RemoteServer bmpServer = new RemoteServer("127.0.0.1", 9091);
    private static final RemoteServer mockServer = new RemoteServer("localhost", 8000);

    private static final String APPIUM_LOCAL_URL_TEMPLATE = "http://%s:%s/wd/hub";

    private static final Logger logger = Logger.getLogger(TestEnvironment.class.getName());

    private String envType;
    private final Properties config = new Properties();

    /**
     * Constructor with possibility to use BMP
     *
     * @param name
     * @param propertiesFilePath path to .properties file with default configuration
     * @param useBrowserMobProxy if set to true BrowserMobProxy will be started, by default false
     * @throws IOException when propertiesFilePath can't be loaded
     */

    /**
     * @param name               test name which is later used as name in SauceLabs, e.g. iOS_SDK_UI
     * @param propertiesFilePath path to .properties file with default configuration
     * @param trafficInspectors  the set of tools for sniffing the traffic
     * @throws IOException when propertiesFilePath can't be loaded
     */
    public TestEnvironment(String name, String propertiesFilePath, Set<TrafficInspectorKind> trafficInspectors)
            throws IOException {

        logger.setLevel(Level.WARNING);

        setProperties(propertiesFilePath);
        setCapabilities(trafficInspectors);

        if (capabilities.getCapability("name") == null) {
            capabilities.setCapability("name", name);
        }

        if (trafficInspectors.contains(TrafficInspectorKind.MOB_PROXY)) {
            initMobProxy();
        }

        if (trafficInspectors.contains(TrafficInspectorKind.MOCK_SERVER)) {
            initMockServer();
        }

        setup();
    }

    /**
     * Sets environment type based on 'env' property
     *
     * @throws RuntimeException when wrong 'env' value
     */
    private void setup() throws RuntimeException {
        envType = config.getProperty("env");
        switch (envType) {
            case EnvType.LOCAL:
                urlAdress = String.format(APPIUM_LOCAL_URL_TEMPLATE, appiumServer.host, appiumServer.port);
                break;

            default:
                throw new RuntimeException(
                        String.format("Wrong environment type: env=%s, env should be one of: {%s, %s, %s, %s}", envType,
                                EnvType.LOCAL));
        }
    }

    /**
     * Close BrowserMob Proxy if it was started.
     */
    public void teardown() throws IOException {
        if (bmp != null) {
            teardownBrowserMobProxy();
        }
    }

    /**
     * Starts BrowserMob Proxy
     *
     * @throws IOException BMP error
     */
    private void initMobProxy() throws IOException {
        bmp = new BMPWrapper();
        bmp.setTrustAllServers(true);

        bmp.start(bmpServer.port);

        if (config.getProperty("env").equals(EnvType.LOCAL)
                && capabilities.getPlatform().equals(Platform.IOS)) {
            TestEnvironment.turnOnHttpProxyOnMac();
            TestEnvironment.turnOnHttpsProxyOnMac();
        }
    }

    private void initMockServer() throws IOException {
        logValidator = new MockServerManager(mockServer.host + ":" + mockServer.port);
    }

    /**
     * Stops BrowserMob Proxy. For localMAC also turns off proxies.
     *
     * @throws IOException error with turning off proxies
     */
    private void teardownBrowserMobProxy() throws IOException {
        bmp.stop();
        if (config.getProperty("env").equals(EnvType.LOCAL)
                && capabilities.getPlatform().equals(Platform.IOS)) {
            TestEnvironment.turnOffHttpProxyOnMac();
            TestEnvironment.turnOffHttpsProxyOnMac();
        }
    }

    /**
     * Gets default properties from specified file and update them with command line system properties.
     *
     * @param propertiesFilePath path to .properties file with default configuration
     * @throws IOException when propertiesFilePath can't be loaded
     */
    private void setProperties(String propertiesFilePath) throws IOException {
        Properties defaultConfig = new Properties();
        defaultConfig.load(new FileInputStream(propertiesFilePath));
        config.putAll(defaultConfig);

        Properties systemConfig = System.getProperties();
        config.putAll(systemConfig);

        if (config.getProperty("appium_port") != null) {
            appiumServer.port = Integer.valueOf(config.getProperty("appium_port"));
        }
        if (config.getProperty("bmp_port") != null) {
            bmpServer.port = Integer.valueOf(config.getProperty("bmp_port"));
        }

        if (config.getProperty("mockServerIP") != null) {
            mockServer.host = config.getProperty("mockServerIP");
        }
        if (config.getProperty("mockServerPort") != null) {
            mockServer.port = Integer.valueOf(config.getProperty("mockServerPort"));
        }
    }

    /**
     * Sets appium capabilities based on extracted default & system properties.
     */
    private void setCapabilities(Set<TrafficInspectorKind> trafficInspecors) throws IOException {
        capabilities.setCapability("platformName", config.getProperty("platformName"));
        if (!config.getProperty("deviceName").equals("")) {
            capabilities.setCapability("deviceName", config.getProperty("deviceName"));
        }

        capabilities.setCapability("authToken", config.getProperty("authToken"));
        capabilities.setCapability("prebidServerKind", config.getProperty("prebidServerKind"));
        capabilities.setCapability("displaySdk", config.getProperty("displaySdk"));

        capabilities.setCapability("avd", config.getProperty("avd"));
        capabilities.setCapability("automationName", config.getProperty("automationName"));
        capabilities.setCapability("platformVersion", config.getProperty("platformVersion"));
        capabilities.setCapability("browserName", config.getProperty("browserName"));
        capabilities.setCapability("deviceOrientation", config.getProperty("deviceOrientation"));
        capabilities.setCapability("autoAcceptAlerts", config.getProperty("autoAcceptAlerts"));
        capabilities.setCapability("appiumVersion", config.getProperty("appiumVersion"));
        capabilities.setCapability("androidInstallTimeout", config.getProperty("androidInstallTimeout"));
        capabilities.setCapability("autoGrantPermissions", config.getProperty("autoGrantPermissions"));
        capabilities.setCapability("name", config.getProperty("name"));
        capabilities.setCapability("app", new File(config.getProperty("app")).getAbsolutePath());
        capabilities.setCapability("tags", config.getProperty("tags"));
        capabilities.setCapability("newCommandTimeout", config.getProperty("newCommandTimeout"));
        capabilities.setCapability("acceptSslCerts", true);
        capabilities.setCapability("fullReset", config.getProperty("fullReset"));
        capabilities.setCapability("noReset", config.getProperty("noReset"));
        if (capabilities.getPlatform().equals(Platform.IOS)) {
            capabilities.setCapability("resetOnSessionStartOnly", true);
        }

        if (trafficInspecors.contains(TrafficInspectorKind.MOB_PROXY)
                && capabilities.getPlatform().equals(Platform.ANDROID)
                && config.getProperty("env").equals(EnvType.LOCAL)) {
            capabilities.setCapability("avdArgs", String.format("-http-proxy %s:%s", bmpServer.host, bmpServer.port));
        }
    }

    /**
     * ONLY when used with localMAC and BrowserMob Proxy: Sets proxy on MAC
     */
    static private void turnOnHttpProxyOnMac() throws IOException {
        String[] cmdHttp = {
                "networksetup", "-setwebproxy",
                TestEnvironment.getInternetServiceName(), bmpServer.host, bmpServer.port.toString()};
        Runtime.getRuntime().exec(cmdHttp);
        waitUntil(TestEnvironment::isHttpProxyEnabled, true);
        logger.info("[PROXY] MAC HTTP proxy turned ON");
    }

    /**
     * ONLY when used with localMAC and BrowserMob Proxy: Sets proxy on MAC
     */
    static private void turnOnHttpsProxyOnMac() throws IOException {
        String[] cmdHttps = {
                "networksetup", "-setsecurewebproxy",
                TestEnvironment.getInternetServiceName(), bmpServer.host, bmpServer.port.toString()};
        Runtime.getRuntime().exec(cmdHttps);
        waitUntil(TestEnvironment::isHttpsProxyEnabled, true);
        logger.info("[PROXY] MAC HTTPS proxy turned ON");
    }

    /**
     * ONLY when used with localMAC and BrowserMob Proxy: Turn off proxy on MAC
     *
     * @throws IOException execution error
     */
    static private void turnOffHttpProxyOnMac() throws IOException {
        String[] cmdHttp = {"networksetup", "-setwebproxystate", TestEnvironment.getInternetServiceName(), "off"};
        Runtime.getRuntime().exec(cmdHttp);
        waitUntil(TestEnvironment::isHttpProxyEnabled, false);
        logger.info("[PROXY] MAC HTTP proxy turned OFF");
    }

    /**
     * ONLY when used with localMAC and BrowserMob Proxy: Turn off proxy on MAC
     *
     * @throws IOException execution error
     */
    static private void turnOffHttpsProxyOnMac() throws IOException {
        String[] cmdHttps = {
                "networksetup",
                "-setsecurewebproxystate",
                TestEnvironment.getInternetServiceName(),
                "off"};
        Runtime.getRuntime().exec(cmdHttps);
        waitUntil(TestEnvironment::isHttpsProxyEnabled, false);
        logger.info("[PROXY] MAC HTTPS proxy turned OFF");
    }

    /**
     * Waits until method returns expected boolean.
     *
     * @param func          method reference or lambda
     * @param expectedValue boolean expecten return value of method
     */
    static private void waitUntil(Supplier<Boolean> func, Boolean expectedValue) {
        for (int i = 0; i < 10; i++) {
            if (func.get() == expectedValue) {
                return;
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Returns Mac http proxy state.
     *
     * @return boolean proxy state (true if enabled)
     */
    static private boolean isHttpProxyEnabled() {
        String[] cmd = {"networksetup", "-getwebproxy", TestEnvironment.getInternetServiceName()};
        try {
            return extractMacProxyState(cmd);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Returns Mac https proxy state.
     *
     * @return boolean proxy state (true if enabled)
     */
    static private boolean isHttpsProxyEnabled() {
        String[] cmd = {"networksetup", "-getsecurewebproxy", TestEnvironment.getInternetServiceName()};
        try {
            return extractMacProxyState(cmd);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Extracts boolean proxy state from cmd output
     *
     * @param cmd String[] command
     * @return boolean proxy state
     * @throws IOException
     */
    static private boolean extractMacProxyState(String[] cmd) throws IOException {
        Process proc = Runtime.getRuntime().exec(cmd);
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        return stdInput.readLine().equals("Enabled: Yes");
    }

    /**
     * ONLY when used with localMAC and BrowserMob Proxy:
     * get Internet service name for which Web proxy should be turn on/off.
     * Can be provided as cli arg, e.g.: "-DserviceName=Ethernet"
     *
     * @return String, service name, By default: Wi-fi.
     */
    static private String getInternetServiceName() {
        String systemProperty = System.getProperty("serviceName");
        if (systemProperty != null) {
            return systemProperty;
        } else {
            return "Wi-fi";
        }
    }

    /**
     * Sets system proxy properties
     *
     * @param hostname proxy hostname
     * @param port     proxy port
     */
    /*private void setProxySystemProperties(String hostname, Integer port) {
        System.setProperty("http.proxyHost", hostname);
        System.setProperty("https.proxyHost", hostname);
        System.setProperty("http.proxyPort", port.toString());
        System.setProperty("https.proxyPort", port.toString());
    }*/

    /**
     * Returns a the value in the properties file that corresponds to the key that was passed in.
     *
     * @param key
     * @return String, value associated with the key
     */
    public String getProperty(String key) {
        return config.getProperty(key);
    }
}