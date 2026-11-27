
# Instance

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **domain** | **kotlin.String** | The domain that serves the instance. |  |
| **name** | **kotlin.String** | The display name of the instance; the domain when the instance has none. |  |
| **registrations** | [**InstanceRegistrations**](InstanceRegistrations.md) |  |  |
| **loginProvidersOnly** | **kotlin.Boolean** | Whether registration and sign-in are limited to the login providers now: the instance setting is on and at least one provider is enabled. While it is true, a password or a passkey signs nobody in, and neither can be set or added; both still confirm operations of a signed-in user.  |  |
| **version** | **kotlin.String** | The server version. |  |
| **features** | **kotlin.collections.List&lt;kotlin.String&gt;** | The identifiers of the features that this server offers now, in no particular order. Detect a feature by its identifier rather than by &#x60;version&#x60;. An identifier is listed only while its feature is on, so the list can change with the instance&#39;s settings; it is never removed from the API or reused for something else. Ignore the identifiers you do not know. The identifiers:  - &#x60;voice_rooms&#x60;: the voice chat rooms (&#x60;/voice/...&#x60;). - &#x60;full_text_search&#x60;: the full-text search of statuses (&#x60;searchStatuses&#x60;). - &#x60;supporters&#x60;: the supporter program and its plans (&#x60;GET /instance/supporter_plans&#x60;). - &#x60;tips&#x60;: one-time tips to the server&#39;s operation (&#x60;GET /instance/tip_amounts&#x60;). - &#x60;web_push&#x60;: Web Push subscriptions (&#x60;push_server_key&#x60;). - &#x60;translation&#x60;: the translation of statuses through this server (&#x60;translateStatus&#x60;).  |  |
| **sourceUrl** | [**java.net.URI**](java.net.URI.md) | Where users get the source code of the running server (AGPL-3.0 section 13), as an https URL to read it at or a mailto URI to request it by email. |  |
| **limits** | [**InstanceLimits**](InstanceLimits.md) |  |  |
| **timelines** | [**InstanceTimelines**](InstanceTimelines.md) |  |  |
| **federation** | [**InstanceFederation**](InstanceFederation.md) |  |  |
| **sendsEmail** | **kotlin.Boolean** | Whether the instance sends email (the server setting &#x60;mail&#x60; is not &#x60;none&#x60;). Without email, notifications come only in the app, so clients do not offer the email column of the notification settings (&#x60;GET /preferences/notifications&#x60;).  |  |
| **rules** | [**kotlin.collections.List&lt;InstanceRule&gt;**](InstanceRule.md) | The rules in effect, in order. |  |
| **supportersEnabled** | **kotlin.Boolean** | Whether the supporter program is enabled: its plans (&#x60;GET /instance/supporter_plans&#x60;) and the subscriptions show only then.  |  |
| **tipsEnabled** | **kotlin.Boolean** | Whether the server takes tips, one-time payments to its operation with nothing in return: their amounts (&#x60;GET /instance/tip_amounts&#x60;) and the tips show only then.  |  |
| **supporterListVisibility** | [**InstanceAudience**](InstanceAudience.md) |  |  |
| **domainActionsVisibility** | [**InstanceAudience**](InstanceAudience.md) | Who may read the list of moderated domains (&#x60;GET /instance/domain_actions&#x60;), the instance setting &#x60;domain_actions_visibility&#x60;: &#x60;off&#x60;, nobody (the list is a 404); &#x60;signed_in&#x60;, the users signed in to this server; &#x60;everyone&#x60;, anonymous visitors included. The about page shows the list only when it lets the viewer read it.  |  |
| **voiceEnabled** | **kotlin.Boolean** | Whether the voice chat rooms are on: the voice operations (&#x60;/voice/...&#x60;) answer 404 while they are off, and clients then show no entry to them.  |  |
| **voiceRoomStarters** | [**inline**](#VoiceRoomStarters) | Who may open voice chat rooms: &#x60;everyone&#x60; (every local user who is not limited) or &#x60;permitted&#x60; (only the users whose roles have &#x60;start_voice_rooms&#x60;). &#x60;permitted&#x60; while the voice chat is off.  |  |
| **eventCreators** | [**inline**](#EventCreators) | Who may create events: &#x60;everyone&#x60; (every local user who is not limited) or &#x60;permitted&#x60; (only the users whose roles have &#x60;create_events&#x60;).  |  |
| **fullTextSearch** | [**InstanceFullTextSearch**](InstanceFullTextSearch.md) |  |  |
| **searchIndexing** | [**SearchIndexing**](SearchIndexing.md) |  |  |
| **aiUsage** | [**AIUsage**](AIUsage.md) |  |  |
| **linkCards** | **kotlin.Boolean** | Whether the server fetches the pages that posts link to, for their link cards. While it is false, the server fetches nothing for link cards.  |  |
| **linkCardsIgnoreRobotsTxt** | **kotlin.Boolean** | Whether the fetches of link cards skip &#x60;robots.txt&#x60;. While it is true, the sites that posts link to cannot refuse those fetches in &#x60;robots.txt&#x60;; the directives of their pages still apply.  |  |
| **landingFediverseExplanation** | **kotlin.Boolean** | Whether the landing page (&#x60;/&#x60;, signed out) explains how the fediverse works: that an account here follows and is followed by people on other servers (the instance setting &#x60;landing_fediverse_explanation&#x60;).  |  |
| **landingFeatures** | **kotlin.Boolean** | Whether the landing page shows what the software does on this instance, a sample of each feature that is on (the instance setting &#x60;landing_features&#x60;).  |  |
| **translation** | [**InstanceTranslation**](InstanceTranslation.md) |  |  |
| **financeVisibility** | [**FinanceVisibility**](FinanceVisibility.md) |  |  |
| **incomeVisibility** | [**IncomeVisibility**](IncomeVisibility.md) |  |  |
| **singleUser** | **kotlin.Boolean** | Whether the instance is a one-person server: the home of its operator alone, with nobody else to join. While it is true, &#x60;registrations.mode&#x60; is &#x60;closed&#x60; and both &#x60;timelines&#x60; are &#x60;off&#x60;, whatever the instance settings store. Clients also hide the suggestions of accounts, invites, the review of registrations, the sign-up blocks, and the visibility &#x60;local&#x60;: those operations answer a 404 of the type &#x60;/api/v1/problems/single-user-mode&#x60;, and a new &#x60;local&#x60; post is a 422.  |  |
| **shortDescription** | **kotlin.String** | A one-paragraph summary of the instance, as plain text; omitted when there is none. |  [optional] |
| **description** | **kotlin.String** | The full description of the instance, as plain text; omitted when there is none. |  [optional] |
| **statusPageUrl** | [**java.net.URI**](java.net.URI.md) | The https address of the instance&#39;s status page, on a service apart from the server. Absent when the operator has not set one. |  [optional] |
| **closure** | [**InstanceClosure**](InstanceClosure.md) |  |  [optional] |
| **pushServerKey** | **kotlin.String** | The instance&#39;s VAPID public key (RFC 8292), an uncompressed P-256 point in unpadded base64url: the &#x60;applicationServerKey&#x60; that browsers subscribe with before &#x60;putPushSubscription&#x60;. Omitted when the instance sends no Web Push.  |  [optional] |
| **termsUrl** | [**java.net.URI**](java.net.URI.md) | The web client&#39;s page of the instance&#39;s terms of service (&#x60;/about/terms&#x60;), which needs no sign-in and shows &#x60;GET /instance/documents/terms&#x60;; omitted when the instance has none, so that clients do not link to it.  |  [optional] |
| **privacyPolicyUrl** | [**java.net.URI**](java.net.URI.md) | The web client&#39;s page of the instance&#39;s privacy policy (&#x60;/about/privacy&#x60;), which needs no sign-in and shows &#x60;GET /instance/documents/privacy_policy&#x60;; omitted when the instance has none.  |  [optional] |
| **commerceDisclosureUrl** | [**java.net.URI**](java.net.URI.md) | The web client&#39;s page of the commerce disclosures of the supporter plans (&#x60;/about/commerce&#x60;, the URL that the operators give the payment provider), which needs no sign-in and shows &#x60;GET /instance/documents/commerce_disclosure&#x60;; omitted when the instance has none.  |  [optional] |
| **externalReviews** | [**kotlin.collections.List&lt;HookName&gt;**](HookName.md) | The decision hooks in use: what the server sends to an external review before it accepts it. Absent when there is none. The information page states this.  |  [optional] |
| **externalReviewScope** | [**InstanceExternalReviewScope**](InstanceExternalReviewScope.md) |  |  [optional] |
| **voiceMaxParticipants** | **kotlin.Int** | The most people a voice chat room may hold, the bound of a room&#39;s &#x60;max_participants&#x60;. Given while the voice chat rooms are on.  |  [optional] |
| **articleAuthoring** | [**inline**](#ArticleAuthoring) | Who may write new articles (&#x60;createArticle&#x60;): &#x60;everyone&#x60; (every local user who is not limited) or &#x60;permission&#x60; (only the users whose roles have &#x60;create_articles&#x60;). Always given.  |  [optional] |
| **theme** | [**InstanceTheme**](InstanceTheme.md) |  |  [optional] |
| **icon** | [**InstanceBrandImage**](InstanceBrandImage.md) |  |  [optional] |
| **logo** | [**InstanceBrandImage**](InstanceBrandImage.md) |  |  [optional] |
| **logoDark** | [**InstanceBrandImage**](InstanceBrandImage.md) |  |  [optional] |
| **contact** | [**InstanceContact**](InstanceContact.md) |  |  [optional] |
| **thumbnail** | [**InstanceThumbnail**](InstanceThumbnail.md) |  |  [optional] |
| **uxMeasurement** | **kotlin.Boolean** | Whether the server takes samples of how fast the web client responds (&#x60;POST /client_measurements&#x60;). While it is false, the server takes none and the client sends none.  |  [optional] |
| **uxMeasurementShare** | **kotlin.Int** | The percent of browser sessions whose web client sends its samples while &#x60;ux_measurement&#x60; is true: each session is drawn once, at this share.  |  [optional] |
| **autoDeleteLimit** | [**AutoDeleteLimit**](AutoDeleteLimit.md) | The server maximum of the automatic deletion of old posts, which the about page shows so that people know it before they sign up; absent when the server sets none.  |  [optional] |
| **fundingMeter** | [**FundingMeter**](FundingMeter.md) | The funding meter of this month, which the about page shows; absent while the instance setting &#x60;funding_meter_visibility&#x60; is &#x60;off&#x60; or the month has no target.  |  [optional] |
| **singleUserAccount** | [**Account**](Account.md) | The operator&#39;s account of a one-person server, whose profile the web client shows people who are not signed in at the root address. Present only while &#x60;single_user&#x60; is true, and omitted when the account cannot be shown (it is suspended or deleted).  |  [optional] |


<a id="VoiceRoomStarters"></a>
## Enum: voice_room_starters
| Name | Value |
| ---- | ----- |
| voiceRoomStarters | everyone, permitted |


<a id="EventCreators"></a>
## Enum: event_creators
| Name | Value |
| ---- | ----- |
| eventCreators | everyone, permitted |


<a id="ArticleAuthoring"></a>
## Enum: article_authoring
| Name | Value |
| ---- | ----- |
| articleAuthoring | everyone, permission |



