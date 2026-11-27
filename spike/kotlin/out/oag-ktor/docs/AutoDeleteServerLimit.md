
# AutoDeleteServerLimit

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **maxAgeDays** | **kotlin.Int** | The most days after its publication that a post is kept. |  |
| **allowPinned** | **kotlin.Boolean** | Whether pinned posts may be kept (&#x60;keep_pinned&#x60;). |  |
| **keptPins** | **kotlin.Int** | How many pins keep posts, the most recently pinned first; 5, the most pins of an account, keeps every pinned post.  |  |
| **allowBookmarked** | **kotlin.Boolean** | Whether the posts that the user bookmarked may be kept (&#x60;keep_bookmarked&#x60;). |  |
| **allowFavourited** | **kotlin.Boolean** | Whether the posts that the user favourited may be kept (&#x60;keep_favourited&#x60;). |  |
| **allowMedia** | **kotlin.Boolean** | Whether the posts with media attachments may be kept (&#x60;keep_media&#x60;). |  |
| **allowPolls** | **kotlin.Boolean** | Whether the posts with a poll may be kept (&#x60;keep_polls&#x60;). |  |
| **allowVisibilities** | **kotlin.Boolean** | Whether posts may be kept by their visibility (&#x60;keep_visibilities&#x60;). |  |
| **allowThresholds** | **kotlin.Boolean** | Whether posts may be kept by their boosts and favourites (&#x60;keep_min_boosts&#x60;, &#x60;keep_min_favourites&#x60;). |  |
| **effective** | [**AutoDeletePolicy**](AutoDeletePolicy.md) | The policy that deletes the user&#39;s posts under the maximum: it is on, its days are the saved ones or the maximum, whichever is fewer (the maximum when the saved policy is off), and it keeps only the saved exceptions that the maximum allows, with at most &#x60;kept_pins&#x60; pins.  |  |
| **bound** | **kotlin.Boolean** | Whether &#x60;effective&#x60; deletes posts that the saved policy keeps. A user whom the maximum newly binds is told in advance and keeps the saved policy until &#x60;enforce_from&#x60;.  |  |
| **allowArticles** | **kotlin.Boolean** | Whether articles may be kept (&#x60;keep_articles&#x60;); always given. |  [optional] |
| **enforceFrom** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When &#x60;effective&#x60; starts to delete the posts of a bound user, after the grace period; absent while the user is not bound, and until the hourly job has told them.  |  [optional] |



