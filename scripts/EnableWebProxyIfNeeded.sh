#!/bin/bash

# Configurate Web Proxy (HTTP) and Secure Web Proxy (HTTPS) for Appium.
#   10.0.0.2:9091

SERVER="127.0.0.1"
PORT="9091"

networks=("Wi-Fi" "Ethernet" "USB 10/100/1000 LAN")
for network in "${networks[@]}"; do

    webProxyState=$(networksetup -getwebproxy "$network" | awk {'print $2'} | awk 'FNR == 1 {print}' | cut -d' ' -f2)
    secureWebProxyState=$(networksetup -getsecurewebproxy "$network" | awk {'print $2'} | awk 'FNR == 1 {print}' | cut -d' ' -f2)

    # Web Proxy (HTTP)
    if [[ "$webProxyState" != "(null)" && "$webProxyState" == "No" ]]; then
        networksetup -setwebproxy "$network" "$SERVER" "$PORT"
        echo "$network"
        echo "Web Proxy (HTTP) Enabled"
    fi

    # Secure Web Proxy (HTTPS)
    if [[ "$secureWebProxyState" != "(null)" && "$secureWebProxyState" == "No" ]]; then
        networksetup -setsecurewebproxy "$network" "$SERVER" "$PORT"
        echo "$network"
        echo "Secure Web Proxy (HTTPS) Enabled"
    fi

done
