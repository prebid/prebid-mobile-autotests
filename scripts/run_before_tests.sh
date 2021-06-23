#!/bin/bash

echo "Make the node to not sleep"
caffeinate -u -t 1

echo "Shutdown Auxiliary Applications"
sh ./scripts/shutdown_auxiliary_applications.sh

echo "Start the Appium"
appium --address 0.0.0.0 --port 4723 --session-override --log-level error:error &
echo "Waiting 30 seconds while Appium starts"
sleep 30
echo lsof -t -i :4723
echo "Appium started"

bundle install
