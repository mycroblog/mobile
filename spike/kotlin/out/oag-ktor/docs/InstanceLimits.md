
# InstanceLimits

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **maxPostCharacters** | **kotlin.Int** | The maximum number of characters in the text and content warning of a post. |  |
| **requireMediaDescription** | **kotlin.Boolean** | Whether every media file attached to a post needs an alternative text (the instance setting &#x60;require_media_description&#x60;). While it is on, a post or an edit with a media file without one is a 422, so clients ask for it before sending.  |  |
| **drafts** | [**InstanceDraftLimits**](InstanceDraftLimits.md) |  |  [optional] |
| **articles** | [**InstanceArticleLimits**](InstanceArticleLimits.md) |  |  [optional] |



