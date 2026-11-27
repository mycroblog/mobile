# Entry points

- Receive SEND/SEND_MULTIPLE with intent filters; check incoming data; read off the main thread; let the user confirm [https://developer.android.com/training/sharing/receive, checked 2026-11-27]
- Direct Share only through Sharing Shortcuts since Android 11; EXTRA_SHORTCUT_ID present only from the share sheet [https://developer.android.com/training/sharing/direct-share-targets, checked 2026-11-27]
- Android 12+ (by target SDK): web links open in an app only for verified domains or ones the user approves; one app per domain; Android 15 re-verifies periodically [https://developer.android.com/about/versions/12/behavior-changes-all#web-intent-resolution, https://developer.android.com/training/app-links/verify-applinks, checked 2026-11-27]
- Notification trampolines blocked and PendingIntent mutability required for apps targeting Android 12 [https://developer.android.com/about/versions/12/behavior-changes-12, checked 2026-11-27]
- Notification tap to a regular destination: new task with a synthesized back stack; direct reply needs a mutable PendingIntent with a unique request code, and the notification posted again after [https://developer.android.com/develop/ui/views/notifications/navigation, https://developer.android.com/develop/ui/views/notifications/build-notification, checked 2026-11-27]
