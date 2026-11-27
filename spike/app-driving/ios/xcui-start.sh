#!/bin/sh
# Start the XCUITest driver server on a booted simulator and wait until it answers.
# usage: xcui-start.sh <udid> <xctestrun> [port]   (build first: xcodebuild build-for-testing)
U=$1; RUN=$2; PORT=${3:-22087}
SPIKE_DRIVER_PORT=$PORT TEST_RUNNER_SPIKE_DRIVER_PORT=$PORT \
  xcodebuild test-without-building -xctestrun "$RUN" -destination "id=$U" \
  -only-testing:Spike3UITests/DriverServer/testServe > "${TMPDIR:-/tmp}/xcui-$U.log" 2>&1 &
until SPIKE_DRIVER_PORT=$PORT "$(dirname "$0")/xcui.py" '{"op":"ping"}' >/dev/null 2>&1; do sleep 0.2; done
echo "ready (pid $!)"
