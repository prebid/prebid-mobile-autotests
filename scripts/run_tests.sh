#!/bin/bash
set -o errexit

TEST_TASK=$1

bash ./scripts/unlock_android_device.sh
$TEST_TASK

