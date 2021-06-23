#!/bin/bash

DIR=$1

echo 'Clone mock server'
git clone git@github.com:openx/mobile-mock-server.git

echo 'Installing mock server'
bash mobile-mock-server/install.sh
python3 mobile-mock-server/manage.py makemigrations
python3 mobile-mock-server/manage.py migrate

echo 'Starting mock server'
python3 mobile-mock-server/manage.py runserver_plus 0.0.0.0:8000 --cert-file mobile-mock-server/emulator.crt &

python3 mobile-mock-server/manage.py runserver_plus 10.0.2.2:8000 --cert-file mobile-mock-server/emulator.crt &

echo 'Start NGINX'
sudo nginx -c "$DIR/scripts/conf/nginx_openx_ios_mock_server.conf"
