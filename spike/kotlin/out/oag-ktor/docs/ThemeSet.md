
# ThemeSet

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | **kotlin.String** |  |  |
| **tokens** | [**kotlin.collections.List&lt;ThemeTokenValue&gt;**](ThemeTokenValue.md) | The overrides that apply, in the order of the token list. |  |
| **staleTokens** | [**kotlin.collections.List&lt;StaleThemeToken&gt;**](StaleThemeToken.md) | The overrides that no longer apply and are left out of the stylesheet: &#x60;reason&#x60; is &#x60;unknown_token&#x60; (the token was renamed or retired), &#x60;duplicate_token&#x60;, &#x60;invalid_value&#x60;, or &#x60;out_of_range&#x60; (the token&#39;s kind or bounds changed).  |  |
| **savedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) |  |  |



