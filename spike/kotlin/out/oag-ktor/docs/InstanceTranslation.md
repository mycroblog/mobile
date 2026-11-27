
# InstanceTranslation

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **browser** | **kotlin.Boolean** | Whether clients may use the translation built into the browser, where the browser has one. It sends the text of a post to neither this server nor a provider, and works on every visibility.  |  |
| **providers** | [**kotlin.collections.List&lt;InstanceTranslationProvider&gt;**](InstanceTranslationProvider.md) | The enabled providers that run through this server, the first tried first. |  |
| **reportsToServer** | **kotlin.Boolean** | Whether moderators may send the text of a report, its posts and its comment, to the providers. Each provider still receives only the visibilities it lists, and a comment counts as &#x60;private&#x60;. Absent on a server without translation, where it is false.  |  [optional] |



