
# AutoDeletePolicy

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **enabled** | **kotlin.Boolean** | Whether old posts are deleted. |  |
| **minAgeDays** | **kotlin.Int** | How many days after its publication a post is deleted. |  |
| **keepPinned** | **kotlin.Boolean** | Keep the posts pinned to the profile. |  |
| **keepBookmarked** | **kotlin.Boolean** | Keep the posts that the user bookmarked. |  |
| **keepFavourited** | **kotlin.Boolean** | Keep the posts that the user favourited. |  |
| **keepMedia** | **kotlin.Boolean** | Keep the posts with media attachments. |  |
| **keepPolls** | **kotlin.Boolean** | Keep the posts with a poll. |  |
| **keepVisibilities** | [**kotlin.collections.List&lt;Visibility&gt;**](Visibility.md) | Keep the posts with these visibilities, from the widest audience to the narrowest; empty keeps none for their visibility. |  |
| **keepMinBoosts** | **kotlin.Int** | Keep the posts boosted at least this many times; 0 keeps none for their boosts. |  |
| **keepMinFavourites** | **kotlin.Int** | Keep the posts favourited at least this many times; 0 keeps none for their favourites. |  |
| **keepArticles** | **kotlin.Boolean** | Keep the articles. Always given in an answer; omitted in a request, the saved value stays (on for a user who never saved a policy).  |  [optional] |



