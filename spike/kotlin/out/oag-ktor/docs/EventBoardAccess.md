
# EventBoardAccess

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **readable** | **kotlin.Boolean** | Whether the user reads the board: the user organizes or co-organizes the event, or answers &#x60;going&#x60; or &#x60;maybe&#x60;.  |  |
| **writable** | **kotlin.Boolean** | Whether the user may post on the board and edit their posts on it now: the event&#39;s &#x60;board_writers&#x60; admit the user, and the board is not read-only. The board is read-only once the event is canceled, and from 7 days after its end (for an &#x60;unscheduled&#x60; event, the end of its last candidate date, its &#x60;ends_at&#x60;).  |  |



