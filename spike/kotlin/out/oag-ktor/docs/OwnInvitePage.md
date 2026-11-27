
# OwnInvitePage

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **items** | [**kotlin.collections.List&lt;OwnInvite&gt;**](OwnInvite.md) |  |  |
| **canCreate** | **kotlin.Boolean** | Whether the user may create invites now: the instance setting &#x60;invite_creators&#x60; is &#x60;everyone&#x60; or the user&#39;s roles have &#x60;create_invites&#x60;, the account is neither suspended nor limited, its registration is approved, and the registration mode is not &#x60;closed&#x60;. It does not count the cap of usable invites (&#x60;usable&#x60; against &#x60;limits.max_active&#x60;). When it is false, &#x60;cannot_create_reason&#x60; says why; when it is true, &#x60;cannot_create_reason&#x60; is omitted.  |  |
| **usable** | **kotlin.Int** | How many usable invites the user holds now, which &#x60;limits.max_active&#x60; bounds. |  |
| **limits** | [**MemberInviteLimits**](MemberInviteLimits.md) |  |  |
| **nextCursor** | **kotlin.String** | An opaque pagination cursor. Clients pass it back unchanged and never parse it. |  [optional] |
| **cannotCreateReason** | [**InvitesNotAllowedReason**](InvitesNotAllowedReason.md) |  |  [optional] |



