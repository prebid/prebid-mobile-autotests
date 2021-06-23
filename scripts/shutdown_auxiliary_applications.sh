#!/bin/bash

echo "Shutdown an Appium"
kill -kill $(lsof -t -i :4723) &
sleep 15

echo "Shutdown the remained emulators"
sh ./scripts/kill_emulators.sh

echo "Shutdown a Mock Server"
pkill -f runserver

echo "Shutdown a NGINX"
sudo nginx -s stop

echo "================================="
echo "The tests are over. See you soon!"
echo "================================="


