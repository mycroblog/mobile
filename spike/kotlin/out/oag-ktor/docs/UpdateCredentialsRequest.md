
# UpdateCredentialsRequest

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **displayName** | **kotlin.String** | The display name, at most 30 characters; empty removes it. |  [optional] |
| **note** | **kotlin.String** | The profile text as plain text, at most 500 characters; empty removes it. |  [optional] |
| **fields** | [**kotlin.collections.List&lt;AccountFieldSource&gt;**](AccountFieldSource.md) | The metadata fields, replacing the current ones; at most 4. |  [optional] |
| **avatarMediaId** | **kotlin.String** | The ID of a processed image that the user uploaded, to become the avatar; empty removes the avatar.  |  [optional] |
| **headerMediaId** | **kotlin.String** | The ID of a processed image that the user uploaded, to become the header image; empty removes the header image.  |  [optional] |
| **locked** | **kotlin.Boolean** | Whether follow requests need the user&#39;s approval. |  [optional] |
| **bot** | **kotlin.Boolean** | Whether the account is automated. |  [optional] |
| **discoverable** | **kotlin.Boolean** | Whether the account is suggested to others and found by the middle of its names in the account search; when &#x60;false&#x60;, the search finds it only by its whole username. New accounts are discoverable.  |  [optional] |
| **indexable** | **kotlin.Boolean** | Whether the account&#39;s profile and public posts may be indexed for search. |  [optional] |
| **aiOptOut** | **kotlin.Boolean** | Whether the account&#39;s pages state that AI may neither train on nor use them. |  [optional] |
| **feedEnabled** | **kotlin.Boolean** | Whether the Atom feed of the account&#39;s public posts may be served. Setting it makes the owner&#39;s choice, which a later change of &#x60;indexable&#x60; leaves as it is.  |  [optional] |
| **followsVisibility** | [**FollowsVisibility**](FollowsVisibility.md) |  |  [optional] |



