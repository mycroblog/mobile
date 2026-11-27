
# AdminQueue

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **adapter** | **kotlin.String** | The queue the server runs, the &#x60;queue&#x60; setting (&#x60;db&#x60;, &#x60;inline&#x60;, &#x60;cloudtasks&#x60;). |  |
| **pendingListing** | **kotlin.Boolean** | Whether the queue lists its pending jobs (&#x60;queued&#x60;, &#x60;running&#x60;, &#x60;retrying&#x60;). |  |
| **counts** | [**kotlin.collections.List&lt;AdminJobCount&gt;**](AdminJobCount.md) | The jobs by state (&#x60;queued&#x60;, &#x60;running&#x60;, &#x60;retrying&#x60;, &#x60;dead&#x60;) then type; states and types without jobs are left out. Empty without &#x60;pending_listing&#x60;.  |  |
| **waits** | [**kotlin.collections.List&lt;AdminJobWait&gt;**](AdminJobWait.md) | How long each type with due jobs that no worker holds has kept a job waiting, by type. Empty without &#x60;pending_listing&#x60;.  |  |
| **oldestReadyAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | The earliest &#x60;run_at&#x60; among the due jobs that no worker holds: how long the queue has kept a job waiting. Omitted when no job waits, or without &#x60;pending_listing&#x60;.  |  [optional] |



