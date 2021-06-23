# Prebid Mobile Autotests

## Introduction
The tests located in this project bring some confidence that possible changes in the Rendering Modules didn't break the OpenRTB structure in request or OpenMeasurement event tracking.
Development is carried out using Appium framework and the Java programing language. 
Most tests are validating Prebid auction network requests and OpenMeasurement events with the help of BrowserMobProxy and MockServer, but there are also smoke tests to verify ad behaviour.


## Required tools
- [IntelliJ Community Edition IDE](https://www.jetbrains.com/idea/download/#section=mac)
- [Android Studio IDE](https://developer.android.com/studio/)
- [Xcode IDE](https://developer.apple.com/xcode/)
- [Appium](https://github.com/appium/appium-desktop): is an open-source, cross-platform test automation tool;
- [Mock server](https://github.com/openx/mobile-mock-server): is a Django (Python Web framework) web server application. Follow the installation guide in README;
- [Nginx](https://www.nginx.com/resources/wiki/start/topics/tutorials/install/): for proxying video events. 


## Running tests
### Quick steps
- Run appium server;
- Run mock server or proxy (depends on the test suite. More details can be found in test suites XML.);
- Run nginx config located in: `scripts/conf/nginx_openx_ios_mock_server.conf`;
- Configure emulators and [install certificates](#ssl);
- Build .apk/.app files using appropriate IDE;
- Go to configuration files (e.g. `src/test/resources/appium/config/iOSConfig.properties`) and modify the path to .apk/.app files and emulator info (appropriate OS, name, etc.);
- Run selected xml test suite as shown [here](#running-directly-from-intellij).

**IMPORTANT**: You have to always start your appium server before running automation tests. You can either use Desktop app or cli app with default port: 4723. 
Pay attention that some tests require running mock server and others require running with mac proxy.

### Test Properties
Supported test properties:

- **env**: environment type. We support:
  - localMAC
  - runSauceFromMACNoProxy
  - runSauceFromGoCDWithProxy
- **serviceName**: internet service name on which proxy should be set. By default it’s ‘Wi-fi’
- **platformName** : appium capability, ‘Android’ or ‘iOS’
- **deviceName** : appium capability, e.g. ‘iPhone 8’
- **avd** : appium capability, avd device name
- **automationName** : appium capability, automation engine, ‘XCUITest’ for iOS and ‘UiAutomator2’ for Android
- **platformVersion** : appium capability, OS version
- **browserName** : appium capability, Should be an empty string if automating an app
- **deviceOrientation** : appium capability, ‘portrait’ or ‘landscape’
- **autoAcceptAlerts** : appium capability, true or false
- **appiumVersion** : appium capability, appium version
- **autoGrantPermissions** : appium capability, true or false
- **app** : appium capability, path to the app (can be url link)
- **newCommandTimeout** : appium capability, max time when no command is send to appium server before it will be shut down
- **customSSLCert** : appium capability, SSL certificate, works only for iOS
- **acceptSslCerts** : appium capability, true or false
- **name** : saucelabs capability, saucelabs name
- **tags** : saucelabs capability, saucelabs tag
- **retry** : number of retries that should be performed when test fails


Properties can be provided in two ways, by .properties files or by command line args. **Command line args are overwriting values in .properties files!**

### .properties files
There are two .properties files with default values inside

**iOS**: /prebid-mobile-autotests/src/test/resources/appium/config/iOSConfig.properties

**Android**: /prebid-mobile-autotests/src/test/resources/appium/config/AndroidConfig.properties

### Command line args
Properties can be also set with command line args by preceding them with a ‘-D’ e.g.:

```
java -cp
/Users/oxautomation/Documents/mobileArtifacts/lego-1.0-SNAPSHOT-jar-with-dependencies.jar 
-Denv=localMAC 
-DdeviceName="iPhone 7" 
-DserviceName=Ethernet -Dapp=/Users/oxautomation/Documents/mobileArtifacts/OpenXInternalTestAppObjC.app 
-DplatformVersion=11.4 
-Dretry=1 
org.testng.TestNG 
test_config/tests/Mobile/appium/sdk/iOS/iOS_SDK_ALL.xml
Running directly from IntelliJ
You can run tests directly from IntelliJ by clicking on file textng.xml:
```

### IntelliJ run config
You can also configure Intellij to run methods/classes or testng xml files by defining Run Configuration (top menu bar -> Run -> Edit Configurations…).
This way you can provide command line args to overwrite properties file by putting them in VM options field.

### Running directly from IntelliJ
<img src="/docs-res/intellij-manual-test-run.png">

***Test will use test properties from .properties file!***

You can then use it by choosing created configuration from drop down menu and clicking Play.

### Starting simulator
iOS emulator will automatically start (based on `deviceName` property).
If you want your android emulator to also be started automatically when running tests, you have to provide ‘avd’ property with you emulator name - which can be accessed by `emulator -list-avds` command.


### Automation with CLI networksetup
In our automation we are using command line ‘networksetup’ to control local MAC proxy settings. Example commands:

```
networksetup -setwebproxy Wi-fi 127.0.0.1 9091
networksetup -setsecurewebproxy Wi-fi 127.0.0.1 9091
networksetup -setwebproxystate Wi-fi on
networksetup -setsecurewebproxystate Wi-fi on
networksetup -setwebproxystate Wi-fi off
networksetup -setsecurewebproxystate Wi-fi off
```

### Browsermob proxy
BMP is an HTTP proxy similar to Charles but with possibility to use it from java code.

BMP site: https://bmp.lightbody.net/

### SSL
In order to accept SSL traffic by the emulator when BMP is used, you have to install [BMP certificate](https://github.com/lightbody/browsermob-proxy/blob/master/browsermob-core/src/main/resources/sslSupport/ca-certificate-rsa.cer) on your emulator.



## References
- [Appium docs](http://appium.io/docs/en/about-appium/intro/);
- [Appium server GitHub](https://github.com/appium/appium);
- [Appium java client GitHub](https://github.com/appium/java-client);
- [BrowserMobProxy GitHub](https://github.com/lightbody/browsermob-proxy).
