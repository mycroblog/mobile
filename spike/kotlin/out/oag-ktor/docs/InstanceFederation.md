
# InstanceFederation

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **mode** | [**inline**](#Mode) | The direction the instance federates in (the instance setting &#x60;federation_mode&#x60;):  - &#x60;off&#x60;: neither way. Following, looking up with &#x60;resolve&#x60;, and quoting remote   accounts and posts are refused with the 403 &#x60;/api/v1/problems/federation-restricted&#x60;. - &#x60;inbound&#x60;: the instance follows and reads remote accounts but sends none of the local   posts, profiles, and reactions out. Favourites, boosts, reactions, and replies to   remote posts are recorded here but do not reach their servers; quoting a remote post   is refused with the 403 &#x60;/api/v1/problems/federation-restricted&#x60;, since the quote   request cannot be sent. - &#x60;outbound&#x60;: the instance delivers the local posts to remote followers but takes none   of the remote posts and reactions in. Following and resolving remote accounts are   refused with the 403 &#x60;/api/v1/problems/federation-restricted&#x60;. - &#x60;both&#x60;: both ways.  |  |


<a id="Mode"></a>
## Enum: mode
| Name | Value |
| ---- | ----- |
| mode | off, inbound, outbound, both |



