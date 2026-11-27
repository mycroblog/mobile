
# UpdatePreferencesRequest

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **locale** | **kotlin.String** | The UI locale, a BCP 47 language tag. Without &#x60;locale_follows_browser&#x60;, it stops following the browser. |  [optional] |
| **localeFollowsBrowser** | **kotlin.Boolean** | Whether clients with a browser show its language instead of &#x60;locale&#x60;. |  [optional] |
| **timeZone** | **kotlin.String** | The time zone, an IANA name. Without &#x60;time_zone_follows_browser&#x60;, it stops following the browser. |  [optional] |
| **timeZoneFollowsBrowser** | **kotlin.Boolean** | Whether clients with a browser show dates in its time zone instead of &#x60;time_zone&#x60;. |  [optional] |
| **defaultVisibility** | [**Visibility**](Visibility.md) |  |  [optional] |
| **defaultInteractionPolicy** | [**InteractionPolicyChange**](InteractionPolicyChange.md) | The defaults of the kinds it has; the others stay. |  [optional] |
| **defaultPostLanguage** | **kotlin.String** | The language of new posts that name none; &#x60;und&#x60; for undetermined. |  [optional] |
| **requireMediaDescription** | **kotlin.Boolean** | Whether clients ask for a description before posting media without one. |  [optional] |
| **timelineAutoInsert** | **kotlin.Boolean** | Whether the web client inserts new timeline posts without asking. |  [optional] |
| **linkCardSize** | [**LinkCardSize**](LinkCardSize.md) |  |  [optional] |
| **timelineLanguages** | **kotlin.collections.List&lt;kotlin.String&gt;** | The languages that timelines show, at most 64; empty shows every language. |  [optional] |



