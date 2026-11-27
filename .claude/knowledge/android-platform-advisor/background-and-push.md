# Background and push

- WorkManager for work under 10 minutes or started in the background; user-initiated data transfer jobs for user-started long transfers [https://developer.android.com/develop/background-work/background-tasks/data-transfer-options, checked 2026-11-27]
- User-initiated data transfer jobs: JobScheduler, Android 14+ (API 34), not supported by WorkManager, must be scheduled while the app is visible [https://developer.android.com/develop/background-work/background-tasks/uidt, checked 2026-11-27]
- Expedited work has quotas and can be deferred [https://developer.android.com/develop/background-work/background-tasks/persistent/getting-started/define-work#expedited, checked 2026-11-27]
- Android 15: dataSync and mediaProcessing foreground services get 6 hours per 24 hours [https://developer.android.com/develop/background-work/services/fgs/timeout, checked 2026-11-27]
- FCM onMessageReceived has a short window ("several seconds"); show the notification at once; longer work to WorkManager [https://firebase.google.com/docs/cloud-messaging/android/receive-messages, https://firebase.blog/posts/2025/04/fcm-on-android, checked 2026-11-27]
- High-priority messages that show no notification are deprioritized, also when the user disabled notifications [https://firebase.google.com/docs/cloud-messaging/android/message-priority, checked 2026-11-27]
