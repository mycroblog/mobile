
# KeywordFilterRequest

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **title** | **kotlin.String** | 1 to 50 characters (grapheme clusters) on one line, without control characters. White space around it is removed.  |  |
| **action** | [**KeywordFilterAction**](KeywordFilterAction.md) |  |  |
| **contexts** | [**kotlin.collections.List&lt;KeywordFilterContext&gt;**](KeywordFilterContext.md) | Where the filter applies, at least one. |  |
| **keywords** | [**kotlin.collections.List&lt;KeywordFilterKeyword&gt;**](KeywordFilterKeyword.md) | 1 to 100 words. White space around each is removed. The user&#39;s filters have at most 1,000 words in all.  |  |
| **expiresAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the filter stops applying; omit it for a filter that never does. |  [optional] |



