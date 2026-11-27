#!/bin/sh
# The adb commands that caused each change of ADR-0011 §3 on the Android Emulator (API 37), as observed in spike #15.
# Usage: changes.sh <change> [args]; set ANDROID_SERIAL first. Taps, typing, scrolling and back go through
# uia_client.py (step tap|tapText|type|swipe|back) or the adb fallbacks below.
set -eu
PKG=dev.mycroblog.spike15
case "$1" in
  tap)        adb shell input tap "$2" "$3" ;;
  type)       adb shell input text "$2" ;;                       # spaces must be %s; uia_client.py type has no such limit
  scroll)     adb shell input swipe 540 2000 540 600 300 ;;
  back)       adb shell input keyevent BACK ;;
  deeplink)   adb shell am start -W -a android.intent.action.VIEW -d "$2" ;;          # spike15://item/42
  applink)    adb shell pm set-app-links --package $PKG 2 spike15.example.com          # approve the domain once
              adb shell am start -W -a android.intent.action.VIEW -c android.intent.category.BROWSABLE -d "$2" ;;
  share)      adb shell am start -W -a android.intent.action.SEND -t text/plain --es android.intent.extra.TEXT "$2" -n $PKG/.MainActivity ;;
  sharesheet) adb shell "am start -W -a android.intent.action.CHOOSER --eu android.intent.extra.INTENT 'intent:#Intent;action=android.intent.action.SEND;type=text/plain;S.android.intent.extra.TEXT=$2;end'" ;;
  shade)      adb shell cmd statusbar expand-notifications ;;                         # then tap the notification by text
  notify)     adb shell cmd notification post -t "$2" spike "$3" ;;                    # posted by com.android.shell
  rotate)     adb shell cmd window user-rotation lock "$2" ;;                          # 0..3; "free" to unlock
  fold)       adb emu fold; adb shell wm dismiss-keyguard ;;                           # folding locks the screen on this image
  unfold)     adb emu unfold ;;
  posture)    adb emu posture "$2" ;;                                                  # 2 = half-opened, 3 = opened
  resize)     adb emu resize-display "$2" ;;                                           # resizable AVD, windowed emulator only
  size)       adb shell wm size "$2" ;;                                                # e.g. 1600x1200, or reset
  freeform)   adb shell settings put global enable_freeform_support 1
              adb shell am start -W --windowingMode 5 -n $PKG/.MainActivity ;;
  taskresize) adb shell am task resize "$2" "$3" "$4" "$5" "$6" ;;                     # taskId l t r b (freeform)
  split)      adb shell dumpsys activity service SystemUIService WMShell splitscreen moveToSideStage "$2" 0 ;;
  dark)       adb shell cmd uimode night "$2" ;;                                       # yes | no
  fontscale)  adb shell settings put system font_scale "$2" ;;
  rtl)        adb shell cmd locale set-app-locales $PKG --locales "$2" ;;              # ar; "" to reset
  background) adb shell input keyevent HOME ;;
  return)     adb shell am start -W -n $PKG/.MainActivity ;;                           # brings the existing task back
  kill)       adb shell input keyevent HOME; adb shell am kill $PKG ;;                 # process death in background
  *) echo "unknown change $1" >&2; exit 2 ;;
esac
