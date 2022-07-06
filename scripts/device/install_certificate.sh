#!/bin/bash

adb root
certificate=`adb shell ls /data/misc/user/0/cacerts-added | grep 5bc1dc75.0`
if [ -z "$certificate"  ]; then
  subjectHash=`openssl x509 -inform PEM -subject_hash_old -in androidBMPCertificate.pem | head -n 1`
  openssl x509 -in androidBMPCertificate.pem -inform PEM -outform DER -out $subjectHash.0
  adb push ./$subjectHash.0 /data/misc/user/0/cacerts-added/$subjectHash.0
  adb shell "su 0 chmod 644 /data/misc/user/0/cacerts-added/$subjectHash.0"
  adb reboot
fi