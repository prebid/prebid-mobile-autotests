#!/bin/bash

# shellcheck disable=SC2006
subjectHash=`openssl x509 -inform PEM -subject_hash_old -in androidBMPCertificate.pem | head -n 1`
openssl x509 -in androidBMPCertificate.pem -inform PEM -outform DER -out $subjectHash.0
adb root
adb push ./$subjectHash.0 /data/misc/user/0/cacerts-added/$subjectHash.0
adb shell "su 0 chmod 644 /data/misc/user/0/cacerts-added/$subjectHash.0"
adb reboot