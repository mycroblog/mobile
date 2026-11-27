
# AdminServiceClosureCheck

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **localAccounts** | **kotlin.Int** | The local accounts of users that are not deleted, each of which the closure sends a &#x60;Delete&#x60; of. |  |
| **deliveryInboxes** | **kotlin.Int** | The shared inboxes known to the server, which each &#x60;Delete&#x60; goes to. |  |
| **estimatedDeletes** | **kotlin.Int** | The estimate of the deliveries of the closure, &#x60;local_accounts&#x60; times &#x60;delivery_inboxes&#x60;. |  |
| **liveSupporterSubscriptions** | **kotlin.Int** | The supporter subscriptions that go on, which the closure cancels at once without a refund, except a payment for one still pending. |  |
| **pendingMigrations** | **kotlin.Int** | The account migrations (the sending of a &#x60;Move&#x60; and the jobs that move the followers) that have not ended. They should end before the closure delivers its &#x60;Delete&#x60;; the admin screen and the CLI warn while it is not 0.  |  |
| **retentionHolds** | **kotlin.Int** | The accounts under a retention hold. While it is not 0, the operator exports their records with the CLI before discarding the data of the closed server.  |  |



