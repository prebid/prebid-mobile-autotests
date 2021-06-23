#!/bin/bash

echo 'Unlocking the emulator'
adb shell input swipe 800 400 400 400
sleep 2
adb shell input text 0000
sleep 2
adb shell input keyevent KEYCODE_ENTER
