
# InstanceExternalReviewScope

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **localStatusAccountAgeDays** | **kotlin.Long** | For &#x60;local.status&#x60;: the posts of local accounts created at most this many days ago are sent (the instance setting &#x60;hook_local_status_account_age_days&#x60;); &#x60;0&#x60; sends those of every account.  |  [optional] |
| **localProfileAccountAgeDays** | **kotlin.Long** | For &#x60;local.profile&#x60;: the profile updates of local accounts created at most this many days ago are sent (the instance setting &#x60;hook_local_profile_account_age_days&#x60;); &#x60;0&#x60; sends those of every account.  |  [optional] |
| **inboundStatusUnfollowedDirect** | **kotlin.Boolean** | For &#x60;inbound.status&#x60;: whether the direct posts of remote authors that no local account follows are sent too (the instance setting &#x60;hook_inbound_status_unfollowed_direct&#x60;). The direct posts of followed authors are always sent.  |  [optional] |



