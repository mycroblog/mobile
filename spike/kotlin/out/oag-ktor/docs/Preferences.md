
# Preferences

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **locale** | **kotlin.String** | The UI locale, a BCP 47 language tag. |  |
| **localeFollowsBrowser** | **kotlin.Boolean** | Whether clients with a browser show its language instead of &#x60;locale&#x60;, or the server&#39;s default language when the browser names none they have. They keep &#x60;locale&#x60; at what they last detected, for what has no browser (email, push). True for a new user.  |  |
| **timeZone** | **kotlin.String** | The time zone, an IANA name such as &#x60;Asia/Tokyo&#x60;; &#x60;UTC&#x60; until one is chosen. |  |
| **timeZoneChosen** | **kotlin.Boolean** | Whether a time zone has been chosen: false for a new user until a request sets &#x60;time_zone&#x60;. Clients may then set it to the device&#39;s time zone.  |  |
| **timeZoneFollowsBrowser** | **kotlin.Boolean** | Whether clients with a browser show dates in its time zone instead of &#x60;time_zone&#x60;. They keep &#x60;time_zone&#x60; at what they last detected, for what has no browser. True for a new user.  |  |
| **defaultVisibility** | [**Visibility**](Visibility.md) |  |  |
| **defaultInteractionPolicy** | [**InteractionPolicy**](InteractionPolicy.md) | Who may reply to, boost, and quote the user&#39;s new statuses, for the kinds their &#x60;interaction_policy&#x60; leaves out. Everyone at once for a new user.  |  |
| **defaultPostLanguage** | **kotlin.String** | The language of new posts that name none, a BCP 47 language tag; &#x60;und&#x60; when undetermined. |  |
| **requireMediaDescription** | **kotlin.Boolean** | Whether clients ask for a description before posting media without one. |  |
| **timelineAutoInsert** | **kotlin.Boolean** | Whether the web client inserts new timeline posts without asking, instead of offering them behind a \&quot;show new posts\&quot; button. On for a new user; the same on every device.  |  |
| **linkCardSize** | [**LinkCardSize**](LinkCardSize.md) | How the web client shows link cards: &#x60;large&#x60;, with the image above the text, or &#x60;small&#x60;, with a square image beside it. &#x60;large&#x60; for a new user; the same on every device.  |  |
| **timelineLanguages** | **kotlin.collections.List&lt;kotlin.String&gt;** | The languages that timelines show, sorted; empty shows every language. |  |
| **emailSuppression** | [**EmailSuppression**](EmailSuppression.md) | Whether emails no longer reach the primary address, read-only; omitted when they do. After a &#x60;hard_bounce&#x60;, the user asks for the verification email again (&#x60;POST /accounts/email/verification&#x60;) to show that the address works.  |  [optional] [readonly] |



