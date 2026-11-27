# AdminServerApi

All URIs are relative to */api/v1*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**allowOAuthClientHost**](AdminServerApi.md#allowOAuthClientHost) | **DELETE** /admin/oauth_client_host_denials/{host} | Lift the denial of a host |
| [**announceServiceClosure**](AdminServerApi.md#announceServiceClosure) | **PUT** /admin/closure/announcement | Announce the closure of the service |
| [**approveCustomEmojiRequest**](AdminServerApi.md#approveCustomEmojiRequest) | **POST** /admin/custom_emoji_requests/{id}/approve | Approve a custom emoji request |
| [**clearAdminBrand**](AdminServerApi.md#clearAdminBrand) | **DELETE** /admin/instance/brand/{kind} | Clear a brand image |
| [**closeService**](AdminServerApi.md#closeService) | **POST** /admin/closure/close | Close the server |
| [**copyCustomEmoji**](AdminServerApi.md#copyCustomEmoji) | **POST** /admin/custom_emojis/{id}/copy | Copy a remote custom emoji into a local one |
| [**createAdminAnnouncement**](AdminServerApi.md#createAdminAnnouncement) | **POST** /admin/announcements | Write an announcement |
| [**createCostItem**](AdminServerApi.md#createCostItem) | **POST** /admin/cost_items | Record a cost item |
| [**createCustomEmoji**](AdminServerApi.md#createCustomEmoji) | **POST** /admin/custom_emojis | Register a local custom emoji |
| [**createInstanceRule**](AdminServerApi.md#createInstanceRule) | **POST** /admin/rules | Add an instance rule |
| [**createLoginProvider**](AdminServerApi.md#createLoginProvider) | **POST** /admin/login_providers | Set up a login provider |
| [**createRelay**](AdminServerApi.md#createRelay) | **POST** /admin/relays | Register a relay and join it |
| [**createRole**](AdminServerApi.md#createRole) | **POST** /admin/roles | Create a role |
| [**createSupporterPlan**](AdminServerApi.md#createSupporterPlan) | **POST** /admin/supporter_plans | Create a supporter plan |
| [**createWebhook**](AdminServerApi.md#createWebhook) | **POST** /admin/webhooks | Set up a webhook |
| [**deleteAdminAnnouncement**](AdminServerApi.md#deleteAdminAnnouncement) | **DELETE** /admin/announcements/{id} | Delete a draft announcement |
| [**deleteCostItem**](AdminServerApi.md#deleteCostItem) | **DELETE** /admin/cost_items/{id} | Delete a cost item |
| [**deleteCustomEmoji**](AdminServerApi.md#deleteCustomEmoji) | **DELETE** /admin/custom_emojis/{id} | Delete a local custom emoji |
| [**deleteLoginProvider**](AdminServerApi.md#deleteLoginProvider) | **DELETE** /admin/login_providers/{id} | Delete a login provider |
| [**deleteRelay**](AdminServerApi.md#deleteRelay) | **DELETE** /admin/relays/{id} | Delete a relay |
| [**deleteRole**](AdminServerApi.md#deleteRole) | **DELETE** /admin/roles/{role} | Delete a role |
| [**deleteWebhook**](AdminServerApi.md#deleteWebhook) | **DELETE** /admin/webhooks/{id} | Delete a webhook |
| [**denyOAuthClientHost**](AdminServerApi.md#denyOAuthClientHost) | **POST** /admin/oauth_client_host_denials | Deny a host of client metadata documents |
| [**disableAdminAccountTwoFactor**](AdminServerApi.md#disableAdminAccountTwoFactor) | **POST** /admin/accounts/{id}/two_factor/disable | Remove the two-factor authentication of a local account |
| [**disableCustomEmoji**](AdminServerApi.md#disableCustomEmoji) | **POST** /admin/custom_emojis/{id}/disable | Disable a custom emoji |
| [**disableLoginProvider**](AdminServerApi.md#disableLoginProvider) | **POST** /admin/login_providers/{id}/disable | Disable a login provider |
| [**disableOAuthClient**](AdminServerApi.md#disableOAuthClient) | **POST** /admin/oauth_clients/{id}/disable | Disable an OAuth client |
| [**disableWebhook**](AdminServerApi.md#disableWebhook) | **POST** /admin/webhooks/{id}/disable | Disable a webhook |
| [**enableCustomEmoji**](AdminServerApi.md#enableCustomEmoji) | **POST** /admin/custom_emojis/{id}/enable | Enable a custom emoji |
| [**enableLoginProvider**](AdminServerApi.md#enableLoginProvider) | **POST** /admin/login_providers/{id}/enable | Enable a login provider |
| [**enableOAuthClient**](AdminServerApi.md#enableOAuthClient) | **POST** /admin/oauth_clients/{id}/enable | Enable an OAuth client |
| [**enableWebhook**](AdminServerApi.md#enableWebhook) | **POST** /admin/webhooks/{id}/enable | Enable a webhook |
| [**endSupporterSubscription**](AdminServerApi.md#endSupporterSubscription) | **POST** /admin/supporter_subscriptions/{id}/end | End a subscription at once |
| [**followRelay**](AdminServerApi.md#followRelay) | **POST** /admin/relays/{id}/follow | Join a relay again |
| [**getAdminAnnouncement**](AdminServerApi.md#getAdminAnnouncement) | **GET** /admin/announcements/{id} | Get an announcement |
| [**getAdminBrands**](AdminServerApi.md#getAdminBrands) | **GET** /admin/instance/brand | Read the brand images |
| [**getAdminCustomEmoji**](AdminServerApi.md#getAdminCustomEmoji) | **GET** /admin/custom_emojis/{id} | Get a custom emoji |
| [**getAdminJob**](AdminServerApi.md#getAdminJob) | **GET** /admin/jobs/{id} | Get a job of the job queue |
| [**getAdminLoginProvider**](AdminServerApi.md#getAdminLoginProvider) | **GET** /admin/login_providers/{id} | Get a login provider |
| [**getAdminQueue**](AdminServerApi.md#getAdminQueue) | **GET** /admin/queue | Get the state of the job queue |
| [**getAdminServiceClosure**](AdminServerApi.md#getAdminServiceClosure) | **GET** /admin/closure | Read the service closure |
| [**getAdminServiceClosureCheck**](AdminServerApi.md#getAdminServiceClosureCheck) | **GET** /admin/closure/check | Check the server before its closure |
| [**getAdminTheme**](AdminServerApi.md#getAdminTheme) | **GET** /admin/theme | Read the instance theme |
| [**getAdminThemeDraftStylesheet**](AdminServerApi.md#getAdminThemeDraftStylesheet) | **GET** /admin/theme/draft/stylesheet | Preview the draft of the instance theme |
| [**getAdminTranslationOverview**](AdminServerApi.md#getAdminTranslationOverview) | **GET** /admin/translation | Show how translation is used |
| [**getAdminWebhook**](AdminServerApi.md#getAdminWebhook) | **GET** /admin/webhooks/{id} | Get a webhook |
| [**getCostItem**](AdminServerApi.md#getCostItem) | **GET** /admin/cost_items/{id} | Get a cost item |
| [**getCostTotals**](AdminServerApi.md#getCostTotals) | **GET** /admin/cost_totals | Total the costs by period |
| [**getErrorGroup**](AdminServerApi.md#getErrorGroup) | **GET** /admin/error_groups/{id} | Get an error group |
| [**getFederationScopeImpact**](AdminServerApi.md#getFederationScopeImpact) | **GET** /admin/settings/federation_scope/impact | Count what switching federation to the allowlist would end |
| [**getIncomeTotals**](AdminServerApi.md#getIncomeTotals) | **GET** /admin/income_totals | Total the income by period |
| [**getInstanceRule**](AdminServerApi.md#getInstanceRule) | **GET** /admin/rules/{id} | Get an instance rule |
| [**getLoginProvidersOnlyImpact**](AdminServerApi.md#getLoginProvidersOnlyImpact) | **GET** /admin/settings/login_providers_only/impact | Count who limiting sign-in to login providers would affect |
| [**getPaymentStatus**](AdminServerApi.md#getPaymentStatus) | **GET** /admin/payment_status | Get the state of the payment provider |
| [**getSupporterProgram**](AdminServerApi.md#getSupporterProgram) | **GET** /admin/supporter_program | Get the supporter program |
| [**getSupporterSubscription**](AdminServerApi.md#getSupporterSubscription) | **GET** /admin/supporter_subscriptions/{id} | Get a subscription |
| [**getSupporterTips**](AdminServerApi.md#getSupporterTips) | **GET** /admin/supporter_tips | Get the tips |
| [**grantAccountRole**](AdminServerApi.md#grantAccountRole) | **PUT** /admin/accounts/{id}/roles/{role} | Grant a role to a local account |
| [**issueAdminAccountPasswordResetLink**](AdminServerApi.md#issueAdminAccountPasswordResetLink) | **POST** /admin/accounts/{id}/password_reset_link | Issue a password reset link for a local account |
| [**listAccountRoles**](AdminServerApi.md#listAccountRoles) | **GET** /admin/accounts/{id}/roles | List the roles of a local account |
| [**listAdminAnnouncements**](AdminServerApi.md#listAdminAnnouncements) | **GET** /admin/announcements | List the announcements |
| [**listAdminCustomEmojiRequests**](AdminServerApi.md#listAdminCustomEmojiRequests) | **GET** /admin/custom_emoji_requests | List the custom emoji requests |
| [**listAdminCustomEmojis**](AdminServerApi.md#listAdminCustomEmojis) | **GET** /admin/custom_emojis | List the custom emoji |
| [**listAdminJobAttempts**](AdminServerApi.md#listAdminJobAttempts) | **GET** /admin/jobs/{id}/attempts | List the attempts of a job that did not succeed |
| [**listAdminJobs**](AdminServerApi.md#listAdminJobs) | **GET** /admin/jobs | List the jobs of the job queue |
| [**listAdminLoginProviders**](AdminServerApi.md#listAdminLoginProviders) | **GET** /admin/login_providers | List the login providers |
| [**listAdminOAuthClients**](AdminServerApi.md#listAdminOAuthClients) | **GET** /admin/oauth_clients | List the OAuth clients |
| [**listAdminRelays**](AdminServerApi.md#listAdminRelays) | **GET** /admin/relays | List the relays |
| [**listAdminSupporterPlans**](AdminServerApi.md#listAdminSupporterPlans) | **GET** /admin/supporter_plans | List the supporter plans |
| [**listAdminWebhooks**](AdminServerApi.md#listAdminWebhooks) | **GET** /admin/webhooks | List the webhooks |
| [**listBillingImports**](AdminServerApi.md#listBillingImports) | **GET** /admin/billing_imports | List the state of the billing imports |
| [**listCostItems**](AdminServerApi.md#listCostItems) | **GET** /admin/cost_items | List the cost items |
| [**listErrorGroups**](AdminServerApi.md#listErrorGroups) | **GET** /admin/error_groups | List the error groups |
| [**listIncomeMovements**](AdminServerApi.md#listIncomeMovements) | **GET** /admin/income_movements | List the money movements of the income |
| [**listInstanceRules**](AdminServerApi.md#listInstanceRules) | **GET** /admin/rules | List the instance rules |
| [**listInstanceSettings**](AdminServerApi.md#listInstanceSettings) | **GET** /admin/settings | List the instance settings |
| [**listInstanceTexts**](AdminServerApi.md#listInstanceTexts) | **GET** /admin/instance_texts | List the instance texts |
| [**listLinkCardDomainRules**](AdminServerApi.md#listLinkCardDomainRules) | **GET** /admin/link_card_domains | List the rules of the link card crawler |
| [**listOAuthClientHostDenials**](AdminServerApi.md#listOAuthClientHostDenials) | **GET** /admin/oauth_client_host_denials | List the denied hosts of client metadata documents |
| [**listRoles**](AdminServerApi.md#listRoles) | **GET** /admin/roles | List the roles |
| [**listSupporterSubscriptions**](AdminServerApi.md#listSupporterSubscriptions) | **GET** /admin/supporter_subscriptions | List the subscriptions |
| [**listUXMeasurements**](AdminServerApi.md#listUXMeasurements) | **GET** /admin/ux_measurements | List the UX measurements |
| [**listWebhookDeliveries**](AdminServerApi.md#listWebhookDeliveries) | **GET** /admin/webhooks/{id}/deliveries | List the deliveries of a webhook |
| [**moveInstanceRule**](AdminServerApi.md#moveInstanceRule) | **POST** /admin/rules/{id}/move | Move an instance rule |
| [**muteErrorGroup**](AdminServerApi.md#muteErrorGroup) | **POST** /admin/error_groups/{id}/mute | Mute an error group |
| [**publishAdminAnnouncement**](AdminServerApi.md#publishAdminAnnouncement) | **POST** /admin/announcements/{id}/publish | Publish an announcement |
| [**publishAdminTheme**](AdminServerApi.md#publishAdminTheme) | **POST** /admin/theme/publish | Publish the draft of the instance theme |
| [**redeliverWebhookDelivery**](AdminServerApi.md#redeliverWebhookDelivery) | **POST** /admin/webhooks/{id}/deliveries/{delivery_id}/redeliver | Send a delivery of a webhook again |
| [**rejectCustomEmojiRequest**](AdminServerApi.md#rejectCustomEmojiRequest) | **POST** /admin/custom_emoji_requests/{id}/reject | Reject a custom emoji request |
| [**removeLinkCardDomainRule**](AdminServerApi.md#removeLinkCardDomainRule) | **DELETE** /admin/link_card_domains/{domain} | Remove the rule of a domain for the link card crawler |
| [**replaceCustomEmojiImage**](AdminServerApi.md#replaceCustomEmojiImage) | **PUT** /admin/custom_emojis/{id}/image | Replace the image of a local custom emoji |
| [**resolveErrorGroup**](AdminServerApi.md#resolveErrorGroup) | **POST** /admin/error_groups/{id}/resolve | Resolve an error group |
| [**restoreAdminTheme**](AdminServerApi.md#restoreAdminTheme) | **POST** /admin/theme/restore | Restore the previously published instance theme |
| [**retireInstanceRule**](AdminServerApi.md#retireInstanceRule) | **POST** /admin/rules/{id}/retire | Retire an instance rule |
| [**retireSupporterPlan**](AdminServerApi.md#retireSupporterPlan) | **POST** /admin/supporter_plans/{id}/retire | Retire a supporter plan |
| [**retryAdminJob**](AdminServerApi.md#retryAdminJob) | **POST** /admin/jobs/{id}/retry | Run a dead job again |
| [**reviseCostItemPrice**](AdminServerApi.md#reviseCostItemPrice) | **POST** /admin/cost_items/{id}/price_revisions | Revise a cost item&#39;s price |
| [**revokeAccountRole**](AdminServerApi.md#revokeAccountRole) | **DELETE** /admin/accounts/{id}/roles/{role} | Revoke a role from a local account |
| [**rotateLoginProviderSecret**](AdminServerApi.md#rotateLoginProviderSecret) | **POST** /admin/login_providers/{id}/rotate_secret | Give a login provider a new client secret |
| [**rotateWebhookSecret**](AdminServerApi.md#rotateWebhookSecret) | **POST** /admin/webhooks/{id}/rotate_secret | Give a webhook a new secret |
| [**runBillingImport**](AdminServerApi.md#runBillingImport) | **POST** /admin/billing_imports/{source}/runs | Import a provider&#39;s usage charges now |
| [**runPaymentReconciliation**](AdminServerApi.md#runPaymentReconciliation) | **POST** /admin/payment_reconciliation/runs | Reconcile with the payment provider now |
| [**sanitizeAdminBrand**](AdminServerApi.md#sanitizeAdminBrand) | **POST** /admin/instance/brand/sanitize | Check an SVG for a brand image |
| [**setAdminBrand**](AdminServerApi.md#setAdminBrand) | **PUT** /admin/instance/brand/{kind} | Set a brand image |
| [**setAdminThemeDraft**](AdminServerApi.md#setAdminThemeDraft) | **PUT** /admin/theme/draft | Save the draft of the instance theme |
| [**setCostItemEnd**](AdminServerApi.md#setCostItemEnd) | **PUT** /admin/cost_items/{id}/end | Set a cost item&#39;s end |
| [**setInstanceText**](AdminServerApi.md#setInstanceText) | **PUT** /admin/instance_texts/{key} | Set an instance text |
| [**setLinkCardDomainRule**](AdminServerApi.md#setLinkCardDomainRule) | **PUT** /admin/link_card_domains/{domain} | Set the rule of a domain for the link card crawler |
| [**setSupporterProgram**](AdminServerApi.md#setSupporterProgram) | **PUT** /admin/supporter_program | Enable or disable the supporter program |
| [**setSupporterTips**](AdminServerApi.md#setSupporterTips) | **PUT** /admin/supporter_tips | Enable or disable the tips |
| [**testWebhook**](AdminServerApi.md#testWebhook) | **POST** /admin/webhooks/{id}/test | Send a test message to a webhook |
| [**unfollowRelay**](AdminServerApi.md#unfollowRelay) | **DELETE** /admin/relays/{id}/follow | Leave a relay |
| [**unmuteErrorGroup**](AdminServerApi.md#unmuteErrorGroup) | **POST** /admin/error_groups/{id}/unmute | Unmute an error group |
| [**updateAdminAnnouncement**](AdminServerApi.md#updateAdminAnnouncement) | **PUT** /admin/announcements/{id} | Revise an announcement |
| [**updateCostItem**](AdminServerApi.md#updateCostItem) | **PATCH** /admin/cost_items/{id} | Change a cost item&#39;s label |
| [**updateCustomEmoji**](AdminServerApi.md#updateCustomEmoji) | **PATCH** /admin/custom_emojis/{id} | Change the settings of a custom emoji |
| [**updateInstanceRule**](AdminServerApi.md#updateInstanceRule) | **PUT** /admin/rules/{id} | Revise the text of an instance rule |
| [**updateInstanceSettings**](AdminServerApi.md#updateInstanceSettings) | **PATCH** /admin/settings | Change instance settings |
| [**updateLoginProvider**](AdminServerApi.md#updateLoginProvider) | **PATCH** /admin/login_providers/{id} | Change a login provider |
| [**updateRole**](AdminServerApi.md#updateRole) | **PATCH** /admin/roles/{role} | Change a role |
| [**updateSupporterPlan**](AdminServerApi.md#updateSupporterPlan) | **PATCH** /admin/supporter_plans/{id} | Change a supporter plan |
| [**updateWebhook**](AdminServerApi.md#updateWebhook) | **PATCH** /admin/webhooks/{id} | Change a webhook |
| [**withdrawAdminAnnouncement**](AdminServerApi.md#withdrawAdminAnnouncement) | **POST** /admin/announcements/{id}/withdraw | Withdraw an announcement |
| [**withdrawServiceClosure**](AdminServerApi.md#withdrawServiceClosure) | **DELETE** /admin/closure/announcement | Withdraw the announcement of the closure |


<a id="allowOAuthClientHost"></a>
# **allowOAuthClientHost**
> allowOAuthClientHost(host, idempotencyKey)

Lift the denial of a host

The Client ID Metadata Documents on the host are accepted again. The tokens that the denial revoked stay revoked. Lifting a host that is not denied, or a malformed host that cannot be, changes nothing and answers 204 all the same. The lift is recorded in the audit log. Needs the &#x60;manage_oauth_apps&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val host : kotlin.String = host_example // kotlin.String | The denied host, in lowercase and without a port.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    apiInstance.allowOAuthClientHost(host, idempotencyKey)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#allowOAuthClientHost")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#allowOAuthClientHost")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **host** | **kotlin.String**| The denied host, in lowercase and without a port. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

null (empty response body)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/problem+json

<a id="announceServiceClosure"></a>
# **announceServiceClosure**
> AdminServiceClosure announceServiceClosure(announceServiceClosureRequest, idempotencyKey)

Announce the closure of the service

Announces that the server closes at &#x60;closes_at&#x60;, or changes the dates of the announcement in effect. Each call records a new announcement, kept with the earlier ones, and the audit log&#39;s &#x60;service_closure_announced&#x60;. The announcement shows in &#x60;Instance.closure&#x60; and NodeInfo at once; the server keeps running until an administrator closes it. A schedule whose &#x60;export_until&#x60; is before &#x60;closes_at&#x60;, or a message too long, is a 422 that names the parameter; a server that is closed already answers the 409 &#x60;/api/v1/problems/service-closure-state&#x60;. A notice of less than 30 days is allowed and answered with &#x60;short_notice&#x60;. Needs the &#x60;manage_service_closure&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val announceServiceClosureRequest : AnnounceServiceClosureRequest =  // AnnounceServiceClosureRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AdminServiceClosure = apiInstance.announceServiceClosure(announceServiceClosureRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#announceServiceClosure")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#announceServiceClosure")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **announceServiceClosureRequest** | [**AnnounceServiceClosureRequest**](AnnounceServiceClosureRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AdminServiceClosure**](AdminServiceClosure.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="approveCustomEmojiRequest"></a>
# **approveCustomEmojiRequest**
> AdminCustomEmojiRequest approveCustomEmojiRequest(id, idempotencyKey)

Approve a custom emoji request

Approves a pending request: a &#x60;create&#x60; request makes the local emoji, without a category, in the picker, and enabled, whose creator is the requester; an &#x60;image&#x60; request gives the emoji the requested image. The image is copied as an upload of the instance. The approval and the emoji&#39;s registration or new image are recorded in the audit log, and the requester is notified. A request that is not pending, whose requester no longer may create custom emoji, or whose shortcode a local emoji has is a 409 &#x60;/api/v1/problems/custom-emoji-request-conflict&#x60; whose &#x60;reason&#x60; says which (reject it instead); an image that no longer qualifies is a 422. An unknown request is a 404. Needs the &#x60;manage_custom_emojis&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the custom emoji request.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AdminCustomEmojiRequest = apiInstance.approveCustomEmojiRequest(id, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#approveCustomEmojiRequest")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#approveCustomEmojiRequest")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the custom emoji request. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AdminCustomEmojiRequest**](AdminCustomEmojiRequest.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="clearAdminBrand"></a>
# **clearAdminBrand**
> clearAdminBrand(kind, idempotencyKey)

Clear a brand image

Brings the icon, the logo, or the dark logo back to the built-in images. The change is kept like that of &#x60;setAdminBrand&#x60;. Needs the &#x60;manage_settings&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val kind : BrandKind =  // BrandKind | Which brand image.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    apiInstance.clearAdminBrand(kind, idempotencyKey)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#clearAdminBrand")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#clearAdminBrand")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **kind** | [**BrandKind**](.md)| Which brand image. | [enum: icon, logo, logo_dark] |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

null (empty response body)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/problem+json

<a id="closeService"></a>
# **closeService**
> AdminServiceClosure closeService(closeServiceRequest, idempotencyKey)

Close the server

Closes the server for good: it cannot be undone. Only an announced closure whose &#x60;closes_at&#x60; has passed can close; before it, without an announcement, or once closed, it is the 409 &#x60;/api/v1/problems/service-closure-state&#x60;. &#x60;confirm_domain&#x60; must be the server&#39;s domain, or it is a 422 that names it. The closure records &#x60;service_closure_closed&#x60; in the audit log and cancels every supporter subscription that goes on, in the same transaction. From then on, the client API serves only the users&#39; own data and exports (&#x60;/api/v1/problems/service-closed&#x60;). Needs the &#x60;manage_service_closure&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val closeServiceRequest : CloseServiceRequest =  // CloseServiceRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AdminServiceClosure = apiInstance.closeService(closeServiceRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#closeService")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#closeService")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **closeServiceRequest** | [**CloseServiceRequest**](CloseServiceRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AdminServiceClosure**](AdminServiceClosure.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="copyCustomEmoji"></a>
# **copyCustomEmoji**
> AdminCustomEmoji copyCustomEmoji(id, idempotencyKey, copyCustomEmojiRequest)

Copy a remote custom emoji into a local one

Registers a new local emoji with the image of a remote emoji, under the shortcode of the request or the remote one&#39;s. The image is fetched through the media proxy, so the instance does not fetch the media of a domain whose media it rejects, and is checked as the image of a new emoji (&#x60;createCustomEmoji&#x60;); it shrinks into the boxes of an emoji. The new emoji is in the picker, without a category or aliases. A local emoji is a 422 with the reason &#x60;local_emoji&#x60;; an image that cannot be fetched is a 422 with the reason &#x60;fetch_failed&#x60;; a shortcode or an image that does not qualify is a 422 whose &#x60;reason&#x60; says which, and keeps nothing of the copy. The copy is recorded in the audit log (&#x60;custom_emoji_copied&#x60; on the new emoji). An unknown ID is a 404. Needs the &#x60;manage_custom_emojis&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the custom emoji.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
val copyCustomEmojiRequest : CopyCustomEmojiRequest =  // CopyCustomEmojiRequest | 
try {
    val result : AdminCustomEmoji = apiInstance.copyCustomEmoji(id, idempotencyKey, copyCustomEmojiRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#copyCustomEmoji")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#copyCustomEmoji")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the custom emoji. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |
| **copyCustomEmojiRequest** | [**CopyCustomEmojiRequest**](CopyCustomEmojiRequest.md)|  | [optional] |

### Return type

[**AdminCustomEmoji**](AdminCustomEmoji.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="createAdminAnnouncement"></a>
# **createAdminAnnouncement**
> AdminAnnouncement createAdminAnnouncement(adminAnnouncementCreateRequest, idempotencyKey)

Write an announcement

Writes an announcement to every local user, a draft unless &#x60;publish&#x60; is set. The title is plain text and the text Markdown; the users get the text as sanitized HTML. Once it starts to show (its publication, or the start of its window when that comes later), every local user gets an &#x60;announcement&#x60; notification, unless they turned it off. A blank title, one of more than 100 grapheme clusters or of more than one line, is a 422 naming &#x60;title&#x60;; a blank text or one longer than 5,000 characters is a 422 naming &#x60;text&#x60;, and a window that ends when or before it starts a 422 naming &#x60;ends_at&#x60;. Recorded in the audit log. Needs the &#x60;manage_announcements&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val adminAnnouncementCreateRequest : AdminAnnouncementCreateRequest =  // AdminAnnouncementCreateRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AdminAnnouncement = apiInstance.createAdminAnnouncement(adminAnnouncementCreateRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#createAdminAnnouncement")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#createAdminAnnouncement")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **adminAnnouncementCreateRequest** | [**AdminAnnouncementCreateRequest**](AdminAnnouncementCreateRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AdminAnnouncement**](AdminAnnouncement.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="createCostItem"></a>
# **createCostItem**
> CostItem createCostItem(costItemCreateRequest, idempotencyKey)

Record a cost item

Records a cost of the instance: once, or every N weeks, months, or years from the start date. A value the server rejects is a 422 naming it: &#x60;interval&#x60; outside 1 to 99 (1 for &#x60;once&#x60;), an invalid date, &#x60;ends_on&#x60; before &#x60;starts_on&#x60;, an empty or too long &#x60;name&#x60; or &#x60;provider&#x60;, a too long &#x60;memo&#x60;, an unknown currency, a negative amount, or an amount with more digits than its currency (&#x60;price&#x60;). Recorded in the audit log. Needs the &#x60;manage_finance&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val costItemCreateRequest : CostItemCreateRequest =  // CostItemCreateRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : CostItem = apiInstance.createCostItem(costItemCreateRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#createCostItem")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#createCostItem")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **costItemCreateRequest** | [**CostItemCreateRequest**](CostItemCreateRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**CostItem**](CostItem.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="createCustomEmoji"></a>
# **createCustomEmoji**
> AdminCustomEmoji createCustomEmoji(createCustomEmojiRequest, idempotencyKey)

Register a local custom emoji

Registers a local emoji with the image of a media file that the user uploaded and that finished processing (upload it with the &#x60;emoji&#x60; purpose). The image must be a PNG, a GIF, or a WebP, at most &#x60;custom_emoji_max_bytes&#x60; (256 KiB by default) and 1,024 pixels on its longer side; an APNG shows only its first frame. A shortcode that breaks the rules or that a local emoji has, an alias or a category that breaks the rules, or an image that does not qualify is a 422 &#x60;/api/v1/problems/invalid-custom-emoji&#x60; whose &#x60;reason&#x60; says which. The registration is recorded in the audit log. Needs the &#x60;manage_custom_emojis&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val createCustomEmojiRequest : CreateCustomEmojiRequest =  // CreateCustomEmojiRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AdminCustomEmoji = apiInstance.createCustomEmoji(createCustomEmojiRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#createCustomEmoji")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#createCustomEmoji")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **createCustomEmojiRequest** | [**CreateCustomEmojiRequest**](CreateCustomEmojiRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AdminCustomEmoji**](AdminCustomEmoji.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="createInstanceRule"></a>
# **createInstanceRule**
> AdminRule createInstanceRule(instanceRuleInput, idempotencyKey)

Add an instance rule

Adds a rule after the rules in effect. A text that is blank, longer than 300 characters, or on more than one line, or a hint longer than 1,000 characters, is a 422 naming the property (&#x60;text&#x60;, &#x60;hint&#x60;). With 100 rules in effect it is a 422 naming &#x60;rules&#x60;. Needs the &#x60;manage_rules&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val instanceRuleInput : InstanceRuleInput =  // InstanceRuleInput | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AdminRule = apiInstance.createInstanceRule(instanceRuleInput, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#createInstanceRule")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#createInstanceRule")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **instanceRuleInput** | [**InstanceRuleInput**](InstanceRuleInput.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AdminRule**](AdminRule.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="createLoginProvider"></a>
# **createLoginProvider**
> AdminLoginProvider createLoginProvider(createLoginProviderRequest, idempotencyKey)

Set up a login provider

Sets up an external login provider, enabled, with the client ID and secret that the provider gave for the answer&#39;s &#x60;callback_url&#x60;. The secret is never shown again. For &#x60;mastodon&#x60;, the server reads the Mastodon server&#39;s metadata and, without a client ID and secret, registers a client there with the &#x60;profile&#x60; scope. Settings that break a rule are a 422 &#x60;/api/v1/problems/invalid-login-provider&#x60; whose &#x60;reason&#x60; says which. The setup is recorded in the audit log under the display name. Needs the &#x60;manage_login_providers&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val createLoginProviderRequest : CreateLoginProviderRequest =  // CreateLoginProviderRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AdminLoginProvider = apiInstance.createLoginProvider(createLoginProviderRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#createLoginProvider")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#createLoginProvider")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **createLoginProviderRequest** | [**CreateLoginProviderRequest**](CreateLoginProviderRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AdminLoginProvider**](AdminLoginProvider.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="createRelay"></a>
# **createRelay**
> AdminRelay createRelay(createRelayRequest, idempotencyKey)

Register a relay and join it

Registers a relay and sends the instance actor&#39;s &#x60;Follow&#x60;; the relay is &#x60;pending&#x60; until it answers. &#x60;url&#x60; is the relay&#39;s inbox for a &#x60;mastodon&#x60; relay and its actor for a &#x60;litepub&#x60; one, whose inbox the server fetches. Without &#x60;protocol&#x60;, a URL that serves an actor is a &#x60;litepub&#x60; relay and any other a &#x60;mastodon&#x60; one. Only public posts go to the relay; with &#x60;receive&#x60;, the public posts it relays come in too. A URL that is not https, is registered already, is on a suspended server, or (for &#x60;litepub&#x60;) serves no actor is a 422 &#x60;/api/v1/problems/invalid-relay&#x60; whose &#x60;reason&#x60; says which. The registration and the &#x60;Follow&#x60; are recorded in the audit log. Needs the &#x60;manage_settings&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val createRelayRequest : CreateRelayRequest =  // CreateRelayRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AdminRelay = apiInstance.createRelay(createRelayRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#createRelay")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#createRelay")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **createRelayRequest** | [**CreateRelayRequest**](CreateRelayRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AdminRelay**](AdminRelay.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="createRole"></a>
# **createRole**
> AdminRole createRole(createRoleRequest, idempotencyKey)

Create a role

Creates a role with a key, a position below &#x60;admin&#x60; (0 to 999), and permissions; the audit log records &#x60;role_created&#x60;. A position above every role of the viewer is a 403. A key, position, or permissions that break a rule, a key that another role has (&#x60;admin&#x60; and &#x60;moderator&#x60; included), or a permission that the viewer&#39;s roles do not grant is a 422 &#x60;/api/v1/problems/invalid-role&#x60; whose &#x60;reason&#x60; says which. Needs the &#x60;manage_roles&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val createRoleRequest : CreateRoleRequest =  // CreateRoleRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AdminRole = apiInstance.createRole(createRoleRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#createRole")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#createRole")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **createRoleRequest** | [**CreateRoleRequest**](CreateRoleRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AdminRole**](AdminRole.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="createSupporterPlan"></a>
# **createSupporterPlan**
> AdminSupporterPlan createSupporterPlan(adminSupporterPlanCreateRequest, idempotencyKey)

Create a supporter plan

Creates a supporter plan on a role, which then only the plan&#39;s subscriptions grant. The prices are made at the payment provider after the answer; the plan takes subscriptions once &#x60;prices_ready&#x60;. A plan that breaks a rule is a 422 &#x60;/api/v1/problems/invalid-supporter-plan&#x60;, an unknown role a 404, and a server without a payment provider a 409 &#x60;/api/v1/problems/supporters-unavailable&#x60;. Recorded in the audit log. Needs the &#x60;manage_supporters&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val adminSupporterPlanCreateRequest : AdminSupporterPlanCreateRequest =  // AdminSupporterPlanCreateRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AdminSupporterPlan = apiInstance.createSupporterPlan(adminSupporterPlanCreateRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#createSupporterPlan")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#createSupporterPlan")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **adminSupporterPlanCreateRequest** | [**AdminSupporterPlanCreateRequest**](AdminSupporterPlanCreateRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AdminSupporterPlan**](AdminSupporterPlan.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="createWebhook"></a>
# **createWebhook**
> AdminWebhookCreated createWebhook(createWebhookRequest)

Set up a webhook

Sets up an endpoint, enabled, that receives the messages of the &#x60;events&#x60; it subscribes to, signed with Standard Webhooks v1. The answer carries its secret, which is shown only now. The user needs the permission of every event besides &#x60;manage_webhooks&#x60; (&#x60;WebhookEventType&#x60;). A URL, a description, or events that break a rule, or an event whose permission the user lacks, is a 422 &#x60;/api/v1/problems/invalid-webhook&#x60; whose &#x60;reason&#x60; says which. The URL may name any destination: whether the server may reach it is decided on every delivery, which a refused destination fails. The setup is recorded in the audit log under the description and the host name of the URL. Needs the &#x60;manage_webhooks&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val createWebhookRequest : CreateWebhookRequest =  // CreateWebhookRequest | 
try {
    val result : AdminWebhookCreated = apiInstance.createWebhook(createWebhookRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#createWebhook")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#createWebhook")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **createWebhookRequest** | [**CreateWebhookRequest**](CreateWebhookRequest.md)|  | |

### Return type

[**AdminWebhookCreated**](AdminWebhookCreated.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="deleteAdminAnnouncement"></a>
# **deleteAdminAnnouncement**
> deleteAdminAnnouncement(id, idempotencyKey)

Delete a draft announcement

Deletes a draft, which no user ever saw: it is found nowhere after, a 404 like an unknown ID. The audit log records &#x60;announcement_deleted&#x60; with its title. An announcement that was published is a 409: withdraw it instead. Needs the &#x60;manage_announcements&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the announcement.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    apiInstance.deleteAdminAnnouncement(id, idempotencyKey)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#deleteAdminAnnouncement")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#deleteAdminAnnouncement")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the announcement. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

null (empty response body)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/problem+json

<a id="deleteCostItem"></a>
# **deleteCostItem**
> deleteCostItem(id, idempotencyKey)

Delete a cost item

Removes a cost item recorded by mistake from every period, past ones included. There is no undo: record the item again. An unknown or already deleted item is a 404. Recorded in the audit log. Needs the &#x60;manage_finance&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the cost item.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    apiInstance.deleteCostItem(id, idempotencyKey)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#deleteCostItem")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#deleteCostItem")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the cost item. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

null (empty response body)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/problem+json

<a id="deleteCustomEmoji"></a>
# **deleteCustomEmoji**
> deleteCustomEmoji(id, idempotencyKey)

Delete a local custom emoji

Deletes a local emoji for good, with its images; the posts and profiles that used it show its &#x60;:shortcode:&#x60; as text, and the reactions with it are no longer shown. A new emoji with the same shortcode is another emoji. A remote emoji is a 422 with the reason &#x60;remote_emoji&#x60; (disable it instead). The deletion is recorded in the audit log. An unknown ID is a 404. Needs the &#x60;manage_custom_emojis&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the custom emoji.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    apiInstance.deleteCustomEmoji(id, idempotencyKey)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#deleteCustomEmoji")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#deleteCustomEmoji")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the custom emoji. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

null (empty response body)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/problem+json

<a id="deleteLoginProvider"></a>
# **deleteLoginProvider**
> deleteLoginProvider(id, idempotencyKey)

Delete a login provider

Deletes the provider for good: the users linked to it no longer sign in with it. The deletion is recorded in the audit log. An unknown or deleted ID is a 404. Needs the &#x60;manage_login_providers&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the login provider.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    apiInstance.deleteLoginProvider(id, idempotencyKey)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#deleteLoginProvider")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#deleteLoginProvider")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the login provider. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

null (empty response body)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/problem+json

<a id="deleteRelay"></a>
# **deleteRelay**
> deleteRelay(id, idempotencyKey)

Delete a relay

Deletes the registration of a relay that the instance left or that rejected it, with the history of its membership. The edits and deletions of the posts that went to it still go there. A relay that is &#x60;pending&#x60; or &#x60;accepted&#x60; is a 422 &#x60;/api/v1/problems/invalid-relay&#x60; (&#x60;state&#x60;): leave it first. The deletion is recorded in the audit log. An unknown ID is a 404. Needs the &#x60;manage_settings&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the relay.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    apiInstance.deleteRelay(id, idempotencyKey)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#deleteRelay")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#deleteRelay")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the relay. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

null (empty response body)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/problem+json

<a id="deleteRole"></a>
# **deleteRole**
> deleteRole(role, idempotencyKey)

Delete a role

Deletes a role that nobody holds; the audit log records &#x60;role_deleted&#x60;. &#x60;admin&#x60; and &#x60;moderator&#x60; are never deleted (a 422 &#x60;builtin&#x60;), and a role that users hold is a 422 &#x60;held&#x60;: revoke it from them first. A role above every role of the viewer is a 403. An unknown role is a 404. Needs the &#x60;manage_roles&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val role : kotlin.String = role_example // kotlin.String | The key of the role, such as `moderator`.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    apiInstance.deleteRole(role, idempotencyKey)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#deleteRole")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#deleteRole")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **role** | **kotlin.String**| The key of the role, such as &#x60;moderator&#x60;. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

null (empty response body)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/problem+json

<a id="deleteWebhook"></a>
# **deleteWebhook**
> deleteWebhook(id, idempotencyKey)

Delete a webhook

Deletes the endpoint for good: it receives nothing more, and its deliveries are no longer listed. The deletion is recorded in the audit log. An unknown or deleted ID is a 404. Needs the &#x60;manage_webhooks&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the webhook.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    apiInstance.deleteWebhook(id, idempotencyKey)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#deleteWebhook")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#deleteWebhook")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the webhook. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

null (empty response body)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/problem+json

<a id="denyOAuthClientHost"></a>
# **denyOAuthClientHost**
> AdminOAuthClientHostDenial denyOAuthClientHost(denyOAuthClientHostRequest, idempotencyKey)

Deny a host of client metadata documents

From now on no &#x60;client_id&#x60; URL on the host is fetched or accepted, and the authorization requests, token exchanges and refreshes of its clients fail. The tokens that the clients of the host hold are revoked at once (with the reason &#x60;client_disabled&#x60;). Denying a denied host revokes again, records nothing new, and answers 200 with the denial it has. A host that is empty or malformed, or has a port, is a 422. The denial is recorded in the audit log. Needs the &#x60;manage_oauth_apps&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val denyOAuthClientHostRequest : DenyOAuthClientHostRequest =  // DenyOAuthClientHostRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AdminOAuthClientHostDenial = apiInstance.denyOAuthClientHost(denyOAuthClientHostRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#denyOAuthClientHost")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#denyOAuthClientHost")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **denyOAuthClientHostRequest** | [**DenyOAuthClientHostRequest**](DenyOAuthClientHostRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AdminOAuthClientHostDenial**](AdminOAuthClientHostDenial.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="disableAdminAccountTwoFactor"></a>
# **disableAdminAccountTwoFactor**
> disableAdminAccountTwoFactor(id, idempotencyKey)

Remove the two-factor authentication of a local account

Ends the TOTP enrollment of a local account&#39;s user, who lost their authenticator app and recovery codes, so that they sign in with the password alone. It is recorded in the audit log as &#x60;two_factor_disabled&#x60;. A remote account or the instance actor is a 422 naming &#x60;id&#x60;, an account without two-factor authentication a 409 &#x60;/api/v1/problems/two-factor-not-enabled&#x60;, and an unknown account a 404. A local account whose roles do not rank below the user&#39;s, the user&#39;s own included, is a 403 &#x60;/api/v1/problems/target-outranks-actor&#x60;. Needs the &#x60;manage_login_recovery&#x60; permission, which only the built-in admin role has by default. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the account, local or remote.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    apiInstance.disableAdminAccountTwoFactor(id, idempotencyKey)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#disableAdminAccountTwoFactor")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#disableAdminAccountTwoFactor")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the account, local or remote. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

null (empty response body)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/problem+json

<a id="disableCustomEmoji"></a>
# **disableCustomEmoji**
> AdminCustomEmoji disableCustomEmoji(id, idempotencyKey)

Disable a custom emoji

Stops showing the emoji anywhere: its &#x60;:shortcode:&#x60; stays text. Disabling a disabled emoji changes nothing. Works on a local and a remote emoji. Each change is recorded in the audit log. An unknown ID is a 404. Needs the &#x60;manage_custom_emojis&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the custom emoji.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AdminCustomEmoji = apiInstance.disableCustomEmoji(id, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#disableCustomEmoji")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#disableCustomEmoji")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the custom emoji. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AdminCustomEmoji**](AdminCustomEmoji.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="disableLoginProvider"></a>
# **disableLoginProvider**
> AdminLoginProvider disableLoginProvider(id, idempotencyKey)

Disable a login provider

Nobody signs in, signs up, or links an account with the provider until it is enabled again, and the sign-in screen no longer shows it; the links stay. &#x60;linked_users&#x60; of the answer is the number of users that can no longer sign in with it. Disabling a disabled provider changes nothing. The change is recorded in the audit log. An unknown or deleted ID is a 404. Needs the &#x60;manage_login_providers&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the login provider.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AdminLoginProvider = apiInstance.disableLoginProvider(id, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#disableLoginProvider")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#disableLoginProvider")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the login provider. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AdminLoginProvider**](AdminLoginProvider.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="disableOAuthClient"></a>
# **disableOAuthClient**
> AdminOAuthClient disableOAuthClient(id, idempotencyKey)

Disable an OAuth client

Revokes every token of the client at once (with the reason &#x60;client_disabled&#x60;) and refuses its authorization requests, token exchanges and refreshes until it is enabled again. Disabling a disabled client revokes its tokens again and records nothing new. One of the server&#39;s own clients (&#x60;registration&#x60; &#x60;first_party&#x60;) cannot be disabled: it is a 422. The disablement is recorded in the audit log. An unknown ID is a 404. Needs the &#x60;manage_oauth_apps&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the OAuth client (not its `client_id`).
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AdminOAuthClient = apiInstance.disableOAuthClient(id, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#disableOAuthClient")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#disableOAuthClient")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the OAuth client (not its &#x60;client_id&#x60;). | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AdminOAuthClient**](AdminOAuthClient.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="disableWebhook"></a>
# **disableWebhook**
> AdminWebhook disableWebhook(id, idempotencyKey)

Disable a webhook

The endpoint receives nothing until it is enabled again, also of the deliveries waiting to be retried. Disabling a disabled webhook changes nothing. The change is recorded in the audit log. An unknown or deleted ID is a 404. Needs the &#x60;manage_webhooks&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the webhook.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AdminWebhook = apiInstance.disableWebhook(id, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#disableWebhook")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#disableWebhook")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the webhook. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AdminWebhook**](AdminWebhook.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="enableCustomEmoji"></a>
# **enableCustomEmoji**
> AdminCustomEmoji enableCustomEmoji(id, idempotencyKey)

Enable a custom emoji

Shows the emoji again; enabling an enabled emoji changes nothing. Works on a local and a remote emoji. Each change is recorded in the audit log. An unknown ID is a 404. Needs the &#x60;manage_custom_emojis&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the custom emoji.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AdminCustomEmoji = apiInstance.enableCustomEmoji(id, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#enableCustomEmoji")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#enableCustomEmoji")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the custom emoji. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AdminCustomEmoji**](AdminCustomEmoji.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="enableLoginProvider"></a>
# **enableLoginProvider**
> AdminLoginProvider enableLoginProvider(id, idempotencyKey)

Enable a login provider

Users sign in, sign up, and link their accounts with the provider again. Enabling an enabled provider changes nothing. The change is recorded in the audit log. An unknown or deleted ID is a 404. Needs the &#x60;manage_login_providers&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the login provider.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AdminLoginProvider = apiInstance.enableLoginProvider(id, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#enableLoginProvider")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#enableLoginProvider")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the login provider. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AdminLoginProvider**](AdminLoginProvider.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="enableOAuthClient"></a>
# **enableOAuthClient**
> AdminOAuthClient enableOAuthClient(id, idempotencyKey)

Enable an OAuth client

The client can be authorized again. The tokens that disabling it revoked stay revoked: the users authorize the client again. Enabling an enabled client changes nothing. The change is recorded in the audit log. An unknown ID is a 404, and one of the server&#39;s own clients a 422 naming &#x60;id&#x60;. Needs the &#x60;manage_oauth_apps&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the OAuth client (not its `client_id`).
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AdminOAuthClient = apiInstance.enableOAuthClient(id, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#enableOAuthClient")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#enableOAuthClient")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the OAuth client (not its &#x60;client_id&#x60;). | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AdminOAuthClient**](AdminOAuthClient.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="enableWebhook"></a>
# **enableWebhook**
> AdminWebhook enableWebhook(id, idempotencyKey)

Enable a webhook

The endpoint receives the messages of its events again; the messages of the time it was disabled are not sent. Enabling an enabled webhook changes nothing. The change is recorded in the audit log. An unknown or deleted ID is a 404. Needs the &#x60;manage_webhooks&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the webhook.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AdminWebhook = apiInstance.enableWebhook(id, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#enableWebhook")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#enableWebhook")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the webhook. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AdminWebhook**](AdminWebhook.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="endSupporterSubscription"></a>
# **endSupporterSubscription**
> AdminSupporterSubscription endSupporterSubscription(id, idempotencyKey)

End a subscription at once

Ends a subscription at once, without a refund: the role goes now, and the payment provider stops billing. A refund is the operator&#39;s to make at the provider, except that a payment for a subscription still pending, which gave nothing, is refunded. A subscription that has ended is a 409 &#x60;/api/v1/problems/subscription-ended&#x60;, and an unknown one a 404. Recorded in the audit log. Needs the &#x60;manage_supporters&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the subscription.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AdminSupporterSubscription = apiInstance.endSupporterSubscription(id, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#endSupporterSubscription")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#endSupporterSubscription")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the subscription. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AdminSupporterSubscription**](AdminSupporterSubscription.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="followRelay"></a>
# **followRelay**
> AdminRelay followRelay(id, idempotencyKey)

Join a relay again

Sends a new &#x60;Follow&#x60; to a relay that rejected the instance or that the instance left; the relay is &#x60;pending&#x60; again. A relay that is &#x60;pending&#x60; or &#x60;accepted&#x60;, or on a suspended server, is a 422 &#x60;/api/v1/problems/invalid-relay&#x60;. The &#x60;Follow&#x60; is recorded in the audit log. An unknown ID is a 404. Needs the &#x60;manage_settings&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the relay.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AdminRelay = apiInstance.followRelay(id, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#followRelay")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#followRelay")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the relay. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AdminRelay**](AdminRelay.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="getAdminAnnouncement"></a>
# **getAdminAnnouncement**
> AdminAnnouncement getAdminAnnouncement(id)

Get an announcement

An unknown ID is a 404. Needs the &#x60;manage_announcements&#x60; permission.

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the announcement.
try {
    val result : AdminAnnouncement = apiInstance.getAdminAnnouncement(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#getAdminAnnouncement")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#getAdminAnnouncement")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the announcement. | |

### Return type

[**AdminAnnouncement**](AdminAnnouncement.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="getAdminBrands"></a>
# **getAdminBrands**
> AdminBrands getAdminBrands()

Read the brand images

The instance&#39;s own icon, logo, and dark logo, each with the background color of the icon. A kind that uses the built-in images is omitted. Needs the &#x60;manage_settings&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
try {
    val result : AdminBrands = apiInstance.getAdminBrands()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#getAdminBrands")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#getAdminBrands")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**AdminBrands**](AdminBrands.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="getAdminCustomEmoji"></a>
# **getAdminCustomEmoji**
> AdminCustomEmoji getAdminCustomEmoji(id)

Get a custom emoji

An unknown ID is a 404. Needs the &#x60;manage_custom_emojis&#x60; permission.

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the custom emoji.
try {
    val result : AdminCustomEmoji = apiInstance.getAdminCustomEmoji(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#getAdminCustomEmoji")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#getAdminCustomEmoji")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the custom emoji. | |

### Return type

[**AdminCustomEmoji**](AdminCustomEmoji.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="getAdminJob"></a>
# **getAdminJob**
> AdminJob getAdminJob(id)

Get a job of the job queue

One job, pending or dead, with its state at the time of the request. A completed job, an unknown ID, or a pending job on a queue that cannot list them (&#x60;pending_listing&#x60; of &#x60;GET /admin/queue&#x60; is &#x60;false&#x60;) is a 404. Needs the &#x60;manage_jobs&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the job.
try {
    val result : AdminJob = apiInstance.getAdminJob(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#getAdminJob")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#getAdminJob")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the job. | |

### Return type

[**AdminJob**](AdminJob.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="getAdminLoginProvider"></a>
# **getAdminLoginProvider**
> AdminLoginProvider getAdminLoginProvider(id)

Get a login provider

An unknown or deleted ID is a 404. Needs the &#x60;manage_login_providers&#x60; permission.

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the login provider.
try {
    val result : AdminLoginProvider = apiInstance.getAdminLoginProvider(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#getAdminLoginProvider")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#getAdminLoginProvider")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the login provider. | |

### Return type

[**AdminLoginProvider**](AdminLoginProvider.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="getAdminQueue"></a>
# **getAdminQueue**
> AdminQueue getAdminQueue()

Get the state of the job queue

The jobs of the job queue by state and type, when the oldest due job has been waiting since, and what the queue can list. Every queue lists its pending jobs (&#x60;queued&#x60;, &#x60;running&#x60;, &#x60;retrying&#x60;): with Cloud Tasks the jobs are in the database too, and that service only signals them. &#x60;pending_listing&#x60; is &#x60;false&#x60; for a queue that cannot, and then &#x60;counts&#x60; is empty, while &#x60;GET /admin/jobs?state&#x3D;dead&#x60; and the attempts still list the dead jobs and the failed runs. Needs the &#x60;manage_jobs&#x60; permission; a user without it gets a 403 &#x60;/api/v1/problems/missing-permission&#x60;. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
try {
    val result : AdminQueue = apiInstance.getAdminQueue()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#getAdminQueue")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#getAdminQueue")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**AdminQueue**](AdminQueue.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="getAdminServiceClosure"></a>
# **getAdminServiceClosure**
> AdminServiceClosure getAdminServiceClosure()

Read the service closure

The state of the closure of the service with the schedule in effect, and whether its notice is short; once the server is closed, the progress of the closure&#39;s &#x60;Delete&#x60;s. Needs the &#x60;manage_service_closure&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
try {
    val result : AdminServiceClosure = apiInstance.getAdminServiceClosure()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#getAdminServiceClosure")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#getAdminServiceClosure")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**AdminServiceClosure**](AdminServiceClosure.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="getAdminServiceClosureCheck"></a>
# **getAdminServiceClosureCheck**
> AdminServiceClosureCheck getAdminServiceClosureCheck()

Check the server before its closure

What the closure of the server would end: the estimate of the &#x60;Delete&#x60; deliveries, the supporter subscriptions that it cancels, the account migrations that have not ended, and the accounts under a retention hold. Needs the &#x60;manage_service_closure&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
try {
    val result : AdminServiceClosureCheck = apiInstance.getAdminServiceClosureCheck()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#getAdminServiceClosureCheck")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#getAdminServiceClosureCheck")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**AdminServiceClosureCheck**](AdminServiceClosureCheck.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="getAdminTheme"></a>
# **getAdminTheme**
> AdminTheme getAdminTheme()

Read the instance theme

The theme of the web client as its administrators edit it: the draft token set and the set in effect. Each omits what was never saved or published. A set lists the overrides that apply in &#x60;tokens&#x60; and, in &#x60;stale_tokens&#x60;, those that no longer apply since an upgrade renamed or retired their token or changed its bounds; the server leaves those out of the stylesheet and the next save of the draft drops them. Needs the &#x60;manage_settings&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
try {
    val result : AdminTheme = apiInstance.getAdminTheme()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#getAdminTheme")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#getAdminTheme")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**AdminTheme**](AdminTheme.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="getAdminThemeDraftStylesheet"></a>
# **getAdminThemeDraftStylesheet**
> java.io.File getAdminThemeDraftStylesheet()

Preview the draft of the instance theme

The stylesheet that the draft would serve once published, for the administrator to apply over the page and preview the draft. Only administrators see it; it is never cached. 404 when no draft was saved. Needs the &#x60;manage_settings&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
try {
    val result : java.io.File = apiInstance.getAdminThemeDraftStylesheet()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#getAdminThemeDraftStylesheet")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#getAdminThemeDraftStylesheet")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**java.io.File**](java.io.File.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: text/css, application/problem+json

<a id="getAdminTranslationOverview"></a>
# **getAdminTranslationOverview**
> AdminTranslationOverview getAdminTranslationOverview()

Show how translation is used

The characters of posts sent to providers in the current month, the cache hit rate, and the providers that this server has. Months are in UTC. The settings of translation are instance settings (&#x60;GET /admin/settings&#x60;). Needs the &#x60;manage_settings&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
try {
    val result : AdminTranslationOverview = apiInstance.getAdminTranslationOverview()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#getAdminTranslationOverview")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#getAdminTranslationOverview")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**AdminTranslationOverview**](AdminTranslationOverview.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="getAdminWebhook"></a>
# **getAdminWebhook**
> AdminWebhook getAdminWebhook(id)

Get a webhook

An unknown or deleted ID is a 404. Needs the &#x60;manage_webhooks&#x60; permission.

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the webhook.
try {
    val result : AdminWebhook = apiInstance.getAdminWebhook(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#getAdminWebhook")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#getAdminWebhook")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the webhook. | |

### Return type

[**AdminWebhook**](AdminWebhook.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="getCostItem"></a>
# **getCostItem**
> CostItem getCostItem(id)

Get a cost item

An unknown or deleted item is a 404. Needs the &#x60;manage_finance&#x60; permission.

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the cost item.
try {
    val result : CostItem = apiInstance.getCostItem(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#getCostItem")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#getCostItem")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the cost item. | |

### Return type

[**CostItem**](CostItem.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="getCostTotals"></a>
# **getCostTotals**
> CostTotals getCostTotals(granularity, from, to, basis)

Total the costs by period

The totals of the cost items and the imported usage charges for each week, month, or year from the one that holds &#x60;from&#x60; to the one that holds &#x60;to&#x60;, by currency and broken down by provider and category, with the rules that cut the periods. The totals are computed on every read. More than 104 weeks, 60 months, or 20 years, and &#x60;to&#x60; before &#x60;from&#x60;, are a 422 naming &#x60;to&#x60;. Needs the &#x60;manage_finance&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val granularity : CostGranularity =  // CostGranularity | 
val from : kotlin.String = from_example // kotlin.String | A date in the first period.
val to : kotlin.String = to_example // kotlin.String | A date in the last period.
val basis : CostBasis =  // CostBasis | How the totals place a payment; `amortized` by default.
try {
    val result : CostTotals = apiInstance.getCostTotals(granularity, from, to, basis)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#getCostTotals")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#getCostTotals")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **granularity** | [**CostGranularity**](.md)|  | [enum: week, month, year] |
| **from** | **kotlin.String**| A date in the first period. | |
| **to** | **kotlin.String**| A date in the last period. | |
| **basis** | [**CostBasis**](.md)| How the totals place a payment; &#x60;amortized&#x60; by default. | [optional] [enum: amortized, cash] |

### Return type

[**CostTotals**](CostTotals.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="getErrorGroup"></a>
# **getErrorGroup**
> ErrorGroupDetail getErrorGroup(id)

Get an error group

An error group with its latest occurrence (the scrubbed stack, &#x60;request_id&#x60;, and &#x60;trace_id&#x60;), its hourly counts, and the changes of its state. An unknown group is a 404. Needs the &#x60;manage_errors&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the error group.
try {
    val result : ErrorGroupDetail = apiInstance.getErrorGroup(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#getErrorGroup")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#getErrorGroup")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the error group. | |

### Return type

[**ErrorGroupDetail**](ErrorGroupDetail.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="getFederationScopeImpact"></a>
# **getFederationScopeImpact**
> FederationScopeImpact getFederationScopeImpact()

Count what switching federation to the allowlist would end

How many follows switching the instance setting &#x60;federation_scope&#x60; to &#x60;allowlist&#x60; with the current allowlist would end, how many local accounts would lose one, and the servers with the most of them. Administrators read it before they switch, which ends those follows for good. Needs the &#x60;manage_settings&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
try {
    val result : FederationScopeImpact = apiInstance.getFederationScopeImpact()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#getFederationScopeImpact")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#getFederationScopeImpact")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**FederationScopeImpact**](FederationScopeImpact.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="getIncomeTotals"></a>
# **getIncomeTotals**
> AdminIncomeTotals getIncomeTotals(granularity, from, to, basis)

Total the income by period

The totals of the payment provider&#39;s money movements for each week, month, or year from the one that holds &#x60;from&#x60; to the one that holds &#x60;to&#x60;: the gross, the provider&#39;s fees, and the net of each currency, the balance against the costs of &#x60;GET /admin/cost_totals&#x60; under the same basis, the number of members of each supporter plan now, and the rules that cut the periods. The totals are computed on every read. More than 104 weeks, 60 months, or 20 years, and &#x60;to&#x60; before &#x60;from&#x60;, are a 422 naming &#x60;to&#x60;. Needs the &#x60;manage_finance&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val granularity : CostGranularity =  // CostGranularity | 
val from : kotlin.String = from_example // kotlin.String | A date in the first period.
val to : kotlin.String = to_example // kotlin.String | A date in the last period.
val basis : CostBasis =  // CostBasis | How the totals place a payment; `amortized` by default.
try {
    val result : AdminIncomeTotals = apiInstance.getIncomeTotals(granularity, from, to, basis)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#getIncomeTotals")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#getIncomeTotals")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **granularity** | [**CostGranularity**](.md)|  | [enum: week, month, year] |
| **from** | **kotlin.String**| A date in the first period. | |
| **to** | **kotlin.String**| A date in the last period. | |
| **basis** | [**CostBasis**](.md)| How the totals place a payment; &#x60;amortized&#x60; by default. | [optional] [enum: amortized, cash] |

### Return type

[**AdminIncomeTotals**](AdminIncomeTotals.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="getInstanceRule"></a>
# **getInstanceRule**
> AdminRule getInstanceRule(id)

Get an instance rule

A rule, retired or not; reports cite rules by ID. An unknown ID is a 404. Needs the &#x60;manage_rules&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the rule.
try {
    val result : AdminRule = apiInstance.getInstanceRule(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#getInstanceRule")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#getInstanceRule")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the rule. | |

### Return type

[**AdminRule**](AdminRule.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="getLoginProvidersOnlyImpact"></a>
# **getLoginProvidersOnlyImpact**
> LoginProvidersOnlyImpact getLoginProvidersOnlyImpact(providerId)

Count who limiting sign-in to login providers would affect

How many active local users have no link to a login provider that users can sign in with now, and so could not sign in while the instance setting &#x60;login_providers_only&#x60; applies, and whether the user who asks is one of them. Administrators read it before they turn the setting on, and before they add or enable a provider, which starts the limit at once when no other can be signed in with (&#x60;starts_limit&#x60;). Needs the &#x60;manage_settings&#x60; or the &#x60;manage_login_providers&#x60; permission. An unknown or deleted &#x60;provider_id&#x60; is a 404. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val providerId : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | A provider to count as if it were enabled, for the question of what enabling it would do. A new provider needs none: nobody links to it yet. 
try {
    val result : LoginProvidersOnlyImpact = apiInstance.getLoginProvidersOnlyImpact(providerId)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#getLoginProvidersOnlyImpact")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#getLoginProvidersOnlyImpact")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **providerId** | **java.util.UUID**| A provider to count as if it were enabled, for the question of what enabling it would do. A new provider needs none: nobody links to it yet.  | [optional] |

### Return type

[**LoginProvidersOnlyImpact**](LoginProvidersOnlyImpact.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="getPaymentStatus"></a>
# **getPaymentStatus**
> PaymentStatus getPaymentStatus()

Get the state of the payment provider

The payment provider of the deployment&#39;s configuration: its name and mode (test or live), when it last delivered a webhook, and the last attempt of the reconciliation with its failure. &#x60;warnings&#x60; names the signs of a webhook that is not set up right: no delivery is recorded, or the last reconciliation corrected subscriptions that the webhooks had not. A deployment without a payment provider is a 404. Needs the &#x60;manage_supporters&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
try {
    val result : PaymentStatus = apiInstance.getPaymentStatus()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#getPaymentStatus")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#getPaymentStatus")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**PaymentStatus**](PaymentStatus.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="getSupporterProgram"></a>
# **getSupporterProgram**
> AdminSupporterProgram getSupporterProgram()

Get the supporter program

Whether the supporter program is enabled, and what it needs. Needs the &#x60;manage_supporters&#x60; permission.

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
try {
    val result : AdminSupporterProgram = apiInstance.getSupporterProgram()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#getSupporterProgram")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#getSupporterProgram")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**AdminSupporterProgram**](AdminSupporterProgram.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="getSupporterSubscription"></a>
# **getSupporterSubscription**
> AdminSupporterSubscription getSupporterSubscription(id)

Get a subscription

One subscription to a supporter plan, as the list shows it. An unknown subscription is a 404. Needs the &#x60;manage_supporters&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the subscription.
try {
    val result : AdminSupporterSubscription = apiInstance.getSupporterSubscription(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#getSupporterSubscription")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#getSupporterSubscription")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the subscription. | |

### Return type

[**AdminSupporterSubscription**](AdminSupporterSubscription.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="getSupporterTips"></a>
# **getSupporterTips**
> AdminSupporterTips getSupporterTips()

Get the tips

Whether the server takes tips, one-time payments to its operation, and their amounts, with what enabling them needs. Needs the &#x60;manage_supporters&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
try {
    val result : AdminSupporterTips = apiInstance.getSupporterTips()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#getSupporterTips")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#getSupporterTips")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**AdminSupporterTips**](AdminSupporterTips.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="grantAccountRole"></a>
# **grantAccountRole**
> AdminRoleList grantAccountRole(id, role, idempotencyKey)

Grant a role to a local account

Grants the role to the user of a local account; its permissions apply from the user&#39;s next request, and the audit log records &#x60;role_granted&#x60;. Granting a role that the user holds changes nothing and records nothing. A role whose position is above every role of the viewer, or that has a permission the viewer does not hold, is a 403. An unknown account, one that is not local, or an unknown role is a 404, and a role that a supporter plan manages a 409 &#x60;/api/v1/problems/role-managed-by-plan&#x60;. Answers the roles that the user holds afterwards. Needs the &#x60;manage_roles&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the account, local or remote.
val role : kotlin.String = role_example // kotlin.String | The key of the role, such as `moderator`.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AdminRoleList = apiInstance.grantAccountRole(id, role, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#grantAccountRole")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#grantAccountRole")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the account, local or remote. | |
| **role** | **kotlin.String**| The key of the role, such as &#x60;moderator&#x60;. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AdminRoleList**](AdminRoleList.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="issueAdminAccountPasswordResetLink"></a>
# **issueAdminAccountPasswordResetLink**
> AdminPasswordResetLink issueAdminAccountPasswordResetLink(id)

Issue a password reset link for a local account

Issues a link that sets a new password for a local account&#39;s user, for an administrator to hand to the user, such as when the server sends no email or the email does not arrive. The link works once, for an hour, and stops working when the password changes otherwise; only its hash is stored, so this response is the only place it is shown. It is recorded in the audit log as &#x60;password_reset_link_issued&#x60;. Whoever holds the link can set the password: confirm who the user is through another channel first. A remote account or the instance actor is a 422 naming &#x60;id&#x60;, and an unknown account a 404. A local account whose roles do not rank below the user&#39;s, the user&#39;s own included, is a 403 &#x60;/api/v1/problems/target-outranks-actor&#x60;. Needs the &#x60;manage_login_recovery&#x60; permission, which only the built-in admin role has by default. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the account, local or remote.
try {
    val result : AdminPasswordResetLink = apiInstance.issueAdminAccountPasswordResetLink(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#issueAdminAccountPasswordResetLink")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#issueAdminAccountPasswordResetLink")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the account, local or remote. | |

### Return type

[**AdminPasswordResetLink**](AdminPasswordResetLink.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listAccountRoles"></a>
# **listAccountRoles**
> AdminRoleList listAccountRoles(id)

List the roles of a local account

The roles that the user of a local account holds, highest position first. An unknown account, or one that is not local, is a 404. Needs the &#x60;manage_roles&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the account, local or remote.
try {
    val result : AdminRoleList = apiInstance.listAccountRoles(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#listAccountRoles")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#listAccountRoles")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the account, local or remote. | |

### Return type

[**AdminRoleList**](AdminRoleList.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listAdminAnnouncements"></a>
# **listAdminAnnouncements**
> AdminAnnouncementPage listAdminAnnouncements(state, cursor, limit)

List the announcements

The announcements, drafts and withdrawn ones included, newest first, a page at a time. &#x60;state&#x60; keeps only some of them; an unknown one is a 400 naming &#x60;state&#x60;. Needs the &#x60;manage_announcements&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val state : AdminAnnouncementFilter =  // AdminAnnouncementFilter | Keep only these announcements; omitted keeps every one.
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : AdminAnnouncementPage = apiInstance.listAdminAnnouncements(state, cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#listAdminAnnouncements")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#listAdminAnnouncements")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **state** | [**AdminAnnouncementFilter**](.md)| Keep only these announcements; omitted keeps every one. | [optional] [enum: draft, scheduled, showing, ended, withdrawn] |
| **cursor** | **kotlin.String**| The &#x60;next_cursor&#x60; of the previous page; omitted for the first page. | [optional] |
| **limit** | **kotlin.Int**| The maximum number of items in the page. | [optional] [default to 20] |

### Return type

[**AdminAnnouncementPage**](AdminAnnouncementPage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listAdminCustomEmojiRequests"></a>
# **listAdminCustomEmojiRequests**
> AdminCustomEmojiRequestPage listAdminCustomEmojiRequests(state, cursor, limit)

List the custom emoji requests

The custom emoji requests of the holders of &#x60;create_custom_emojis&#x60;, newest first, with their requesters: the pending ones unless &#x60;state&#x60; names another state. Needs the &#x60;manage_custom_emojis&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val state : CustomEmojiRequestState =  // CustomEmojiRequestState | Keep the requests in this state; `pending` by default.
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : AdminCustomEmojiRequestPage = apiInstance.listAdminCustomEmojiRequests(state, cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#listAdminCustomEmojiRequests")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#listAdminCustomEmojiRequests")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **state** | [**CustomEmojiRequestState**](.md)| Keep the requests in this state; &#x60;pending&#x60; by default. | [optional] [enum: pending, approved, rejected, withdrawn, lapsed] |
| **cursor** | **kotlin.String**| The &#x60;next_cursor&#x60; of the previous page; omitted for the first page. | [optional] |
| **limit** | **kotlin.Int**| The maximum number of items in the page. | [optional] [default to 20] |

### Return type

[**AdminCustomEmojiRequestPage**](AdminCustomEmojiRequestPage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listAdminCustomEmojis"></a>
# **listAdminCustomEmojis**
> AdminCustomEmojiPage listAdminCustomEmojis(local, domain, shortcode, cursor, limit)

List the custom emoji

The custom emoji, local and remote, newest first. &#x60;local&#x60; keeps the emoji of this instance (&#x60;true&#x60;) or of other servers (&#x60;false&#x60;); &#x60;domain&#x60; keeps those of one remote domain; &#x60;shortcode&#x60; keeps those whose shortcode contains it, ignoring case. Needs the &#x60;manage_custom_emojis&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val local : kotlin.Boolean = true // kotlin.Boolean | Keep the local emoji (`true`) or the remote ones (`false`).
val domain : kotlin.String = domain_example // kotlin.String | Keep the emoji of this remote domain.
val shortcode : kotlin.String = shortcode_example // kotlin.String | Keep the emoji whose shortcode contains this text, ignoring case.
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : AdminCustomEmojiPage = apiInstance.listAdminCustomEmojis(local, domain, shortcode, cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#listAdminCustomEmojis")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#listAdminCustomEmojis")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **local** | **kotlin.Boolean**| Keep the local emoji (&#x60;true&#x60;) or the remote ones (&#x60;false&#x60;). | [optional] |
| **domain** | **kotlin.String**| Keep the emoji of this remote domain. | [optional] |
| **shortcode** | **kotlin.String**| Keep the emoji whose shortcode contains this text, ignoring case. | [optional] |
| **cursor** | **kotlin.String**| The &#x60;next_cursor&#x60; of the previous page; omitted for the first page. | [optional] |
| **limit** | **kotlin.Int**| The maximum number of items in the page. | [optional] [default to 20] |

### Return type

[**AdminCustomEmojiPage**](AdminCustomEmojiPage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listAdminJobAttempts"></a>
# **listAdminJobAttempts**
> AdminJobAttemptPage listAdminJobAttempts(id, cursor, limit)

List the attempts of a job that did not succeed

The records of the runs of the job that did not succeed (a retry, or the run that made it a dead job), the most recently finished first. Successful runs are not recorded, and records are deleted after the server&#39;s &#x60;job_attempt_retention&#x60;, so a job without failures, or an unknown ID, has an empty page. A requeued dead job counts its attempts again from 1. Needs the &#x60;manage_jobs&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the job.
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : AdminJobAttemptPage = apiInstance.listAdminJobAttempts(id, cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#listAdminJobAttempts")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#listAdminJobAttempts")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the job. | |
| **cursor** | **kotlin.String**| The &#x60;next_cursor&#x60; of the previous page; omitted for the first page. | [optional] |
| **limit** | **kotlin.Int**| The maximum number of items in the page. | [optional] [default to 20] |

### Return type

[**AdminJobAttemptPage**](AdminJobAttemptPage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listAdminJobs"></a>
# **listAdminJobs**
> AdminJobPage listAdminJobs(state, type, cursor, limit)

List the jobs of the job queue

The jobs in one state, or the pending jobs of every state when &#x60;state&#x60; is omitted. Pending jobs (&#x60;queued&#x60;, &#x60;running&#x60;, &#x60;retrying&#x60;) come the earliest &#x60;run_at&#x60; first; on a queue that cannot list them (&#x60;pending_listing&#x60; of &#x60;GET /admin/queue&#x60; is &#x60;false&#x60;) the page is empty. Dead jobs (&#x60;state&#x3D;dead&#x60;) come the most recently failed first. Needs the &#x60;manage_jobs&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val state : AdminJobState =  // AdminJobState | Keep only the jobs in this state; omitted keeps the pending ones.
val type : kotlin.String = type_example // kotlin.String | Keep only the jobs of this type, such as `federation.deliver`.
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : AdminJobPage = apiInstance.listAdminJobs(state, type, cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#listAdminJobs")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#listAdminJobs")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **state** | [**AdminJobState**](.md)| Keep only the jobs in this state; omitted keeps the pending ones. | [optional] [enum: queued, running, retrying, dead] |
| **type** | **kotlin.String**| Keep only the jobs of this type, such as &#x60;federation.deliver&#x60;. | [optional] |
| **cursor** | **kotlin.String**| The &#x60;next_cursor&#x60; of the previous page; omitted for the first page. | [optional] |
| **limit** | **kotlin.Int**| The maximum number of items in the page. | [optional] [default to 20] |

### Return type

[**AdminJobPage**](AdminJobPage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listAdminLoginProviders"></a>
# **listAdminLoginProviders**
> AdminLoginProviderList listAdminLoginProviders()

List the login providers

The external login providers that are not deleted, enabled or not, newest first. Needs the &#x60;manage_login_providers&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
try {
    val result : AdminLoginProviderList = apiInstance.listAdminLoginProviders()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#listAdminLoginProviders")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#listAdminLoginProviders")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**AdminLoginProviderList**](AdminLoginProviderList.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listAdminOAuthClients"></a>
# **listAdminOAuthClients**
> AdminOAuthClientPage listAdminOAuthClients(registration, cursor, limit)

List the OAuth clients

Every OAuth client that is not deleted, newest first, the server&#39;s own and the clients of every other route. &#x60;registration&#x60; keeps the clients of one route. Needs the &#x60;manage_oauth_apps&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val registration : OAuthClientRegistration =  // OAuthClientRegistration | Keep the clients registered by this route.
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : AdminOAuthClientPage = apiInstance.listAdminOAuthClients(registration, cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#listAdminOAuthClients")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#listAdminOAuthClients")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **registration** | [**OAuthClientRegistration**](.md)| Keep the clients registered by this route. | [optional] [enum: first_party, user, metadata_document, dynamic] |
| **cursor** | **kotlin.String**| The &#x60;next_cursor&#x60; of the previous page; omitted for the first page. | [optional] |
| **limit** | **kotlin.Int**| The maximum number of items in the page. | [optional] [default to 20] |

### Return type

[**AdminOAuthClientPage**](AdminOAuthClientPage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listAdminRelays"></a>
# **listAdminRelays**
> AdminRelayList listAdminRelays()

List the relays

The relays that the instance joins or joined, in registration order, with the state of the membership and what each brought in during the last 24 hours. Needs the &#x60;manage_settings&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
try {
    val result : AdminRelayList = apiInstance.listAdminRelays()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#listAdminRelays")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#listAdminRelays")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**AdminRelayList**](AdminRelayList.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listAdminSupporterPlans"></a>
# **listAdminSupporterPlans**
> AdminSupporterPlanList listAdminSupporterPlans()

List the supporter plans

Every supporter plan, retired ones too, by rank. Needs the &#x60;manage_supporters&#x60; permission.

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
try {
    val result : AdminSupporterPlanList = apiInstance.listAdminSupporterPlans()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#listAdminSupporterPlans")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#listAdminSupporterPlans")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**AdminSupporterPlanList**](AdminSupporterPlanList.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listAdminWebhooks"></a>
# **listAdminWebhooks**
> AdminWebhookList listAdminWebhooks()

List the webhooks

The webhook endpoints of the instance that are not deleted, newest first. Needs the &#x60;manage_webhooks&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
try {
    val result : AdminWebhookList = apiInstance.listAdminWebhooks()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#listAdminWebhooks")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#listAdminWebhooks")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**AdminWebhookList**](AdminWebhookList.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listBillingImports"></a>
# **listBillingImports**
> BillingImportList listBillingImports()

List the state of the billing imports

The state of the imports of every cloud provider of &#x60;billing_sources&#x60;, in its order: the last attempt and its failure, the last success, the billed requests of the UTC day and month with their caps, the bytes billed this month, and the billing months of the last year with imported costs, each an estimate, final, or corrected, with its totals. The list is empty when nothing is imported. Needs the &#x60;manage_finance&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
try {
    val result : BillingImportList = apiInstance.listBillingImports()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#listBillingImports")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#listBillingImports")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**BillingImportList**](BillingImportList.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listCostItems"></a>
# **listCostItems**
> CostItemPage listCostItems(cursor, limit)

List the cost items

The instance&#39;s cost items that are not deleted, newest first. Needs the &#x60;manage_finance&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : CostItemPage = apiInstance.listCostItems(cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#listCostItems")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#listCostItems")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **cursor** | **kotlin.String**| The &#x60;next_cursor&#x60; of the previous page; omitted for the first page. | [optional] |
| **limit** | **kotlin.Int**| The maximum number of items in the page. | [optional] [default to 20] |

### Return type

[**CostItemPage**](CostItemPage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listErrorGroups"></a>
# **listErrorGroups**
> ErrorGroupPage listErrorGroups(state, source, cursor, limit)

List the error groups

The groups of the errors that the server and the web client reported, the most recently seen first, with their occurrences of the last 24 hours and 7 days. &#x60;state&#x60; and &#x60;source&#x60; keep the groups of that state and source. Needs the &#x60;manage_errors&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val state : ErrorGroupState =  // ErrorGroupState | Keep the groups in this state; every state by default.
val source : ErrorGroupSource =  // ErrorGroupSource | Keep the groups of this source; both by default.
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : ErrorGroupPage = apiInstance.listErrorGroups(state, source, cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#listErrorGroups")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#listErrorGroups")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **state** | [**ErrorGroupState**](.md)| Keep the groups in this state; every state by default. | [optional] [enum: open, resolved, muted] |
| **source** | [**ErrorGroupSource**](.md)| Keep the groups of this source; both by default. | [optional] [enum: server, web] |
| **cursor** | **kotlin.String**| The &#x60;next_cursor&#x60; of the previous page; omitted for the first page. | [optional] |
| **limit** | **kotlin.Int**| The maximum number of items in the page. | [optional] [default to 20] |

### Return type

[**ErrorGroupPage**](ErrorGroupPage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listIncomeMovements"></a>
# **listIncomeMovements**
> IncomeMovementPage listIncomeMovements(from, to, kind, cursor, limit)

List the money movements of the income

The payment provider&#39;s money movements that the income records keep, newest first by the time the provider booked them, narrowed to the dates they fall on and a kind, with the subscriber&#39;s account of each. Only this operation and the admin screen show individual movements and their subscribers. Needs the &#x60;manage_finance&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val from : kotlin.String = from_example // kotlin.String | Only the movements on or after this date.
val to : kotlin.String = to_example // kotlin.String | Only the movements on or before this date.
val kind : IncomeKind =  // IncomeKind | Only the movements of this kind.
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : IncomeMovementPage = apiInstance.listIncomeMovements(from, to, kind, cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#listIncomeMovements")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#listIncomeMovements")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **from** | **kotlin.String**| Only the movements on or after this date. | [optional] |
| **to** | **kotlin.String**| Only the movements on or before this date. | [optional] |
| **kind** | [**IncomeKind**](.md)| Only the movements of this kind. | [optional] [enum: payment, refund, chargeback, chargeback_reversal, fee] |
| **cursor** | **kotlin.String**| The &#x60;next_cursor&#x60; of the previous page; omitted for the first page. | [optional] |
| **limit** | **kotlin.Int**| The maximum number of items in the page. | [optional] [default to 20] |

### Return type

[**IncomeMovementPage**](IncomeMovementPage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listInstanceRules"></a>
# **listInstanceRules**
> AdminRuleList listInstanceRules(includeRetired)

List the instance rules

Every rule: the rules in effect by position, followed, when &#x60;include_retired&#x60; is set, by the retired ones. The list is not paginated; at most 100 rules are in effect. Needs the &#x60;manage_rules&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val includeRetired : kotlin.Boolean = true // kotlin.Boolean | Also list the retired rules, after the rules in effect.
try {
    val result : AdminRuleList = apiInstance.listInstanceRules(includeRetired)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#listInstanceRules")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#listInstanceRules")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **includeRetired** | **kotlin.Boolean**| Also list the retired rules, after the rules in effect. | [optional] [default to false] |

### Return type

[**AdminRuleList**](AdminRuleList.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listInstanceSettings"></a>
# **listInstanceSettings**
> AdminSettingList listInstanceSettings()

List the instance settings

Every instance setting: its current value, or its default when it has never been set, with what it accepts. The policies that administrators change at run time live here; the deployment configuration does not. Needs the &#x60;manage_settings&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
try {
    val result : AdminSettingList = apiInstance.listInstanceSettings()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#listInstanceSettings")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#listInstanceSettings")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**AdminSettingList**](AdminSettingList.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listInstanceTexts"></a>
# **listInstanceTexts**
> AdminInstanceTextList listInstanceTexts()

List the instance texts

The current text of every key that has one: the name, the descriptions, the terms of service, the privacy policy, the commerce disclosure, and the alternative texts of the preview image and the thumbnail, by key. Each holds one value, shown as it is in every locale. Cleared texts are not listed. The list is not paginated. Needs the &#x60;manage_settings&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
try {
    val result : AdminInstanceTextList = apiInstance.listInstanceTexts()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#listInstanceTexts")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#listInstanceTexts")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**AdminInstanceTextList**](AdminInstanceTextList.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listLinkCardDomainRules"></a>
# **listLinkCardDomainRules**
> LinkCardDomainRuleList listLinkCardDomainRules()

List the rules of the link card crawler

Every rule by which the crawler of link cards fetches nothing, or no images, from a domain and its subdomains, by domain. The list is not paginated; a server keeps at most 1,000 rules. Needs the &#x60;manage_settings&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
try {
    val result : LinkCardDomainRuleList = apiInstance.listLinkCardDomainRules()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#listLinkCardDomainRules")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#listLinkCardDomainRules")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**LinkCardDomainRuleList**](LinkCardDomainRuleList.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listOAuthClientHostDenials"></a>
# **listOAuthClientHostDenials**
> AdminOAuthClientHostDenialPage listOAuthClientHostDenials(cursor, limit)

List the denied hosts of client metadata documents

The hosts whose Client ID Metadata Documents are denied, newest denial first. Needs the &#x60;manage_oauth_apps&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : AdminOAuthClientHostDenialPage = apiInstance.listOAuthClientHostDenials(cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#listOAuthClientHostDenials")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#listOAuthClientHostDenials")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **cursor** | **kotlin.String**| The &#x60;next_cursor&#x60; of the previous page; omitted for the first page. | [optional] |
| **limit** | **kotlin.Int**| The maximum number of items in the page. | [optional] [default to 20] |

### Return type

[**AdminOAuthClientHostDenialPage**](AdminOAuthClientHostDenialPage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listRoles"></a>
# **listRoles**
> AdminRoleList listRoles()

List the roles

The roles of the instance, highest position first, with the permissions they grant. Every instance has &#x60;admin&#x60; (position 1000, every permission, never changed) and &#x60;moderator&#x60; (position 100 with &#x60;manage_reports&#x60;, &#x60;manage_accounts&#x60;, and &#x60;view_audit&#x60; until an administrator changes them). Needs the &#x60;manage_roles&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
try {
    val result : AdminRoleList = apiInstance.listRoles()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#listRoles")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#listRoles")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**AdminRoleList**](AdminRoleList.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listSupporterSubscriptions"></a>
# **listSupporterSubscriptions**
> AdminSupporterSubscriptionPage listSupporterSubscriptions(planId, state, cursor, limit)

List the subscriptions

The subscriptions to the supporter plans, newest first, narrowed to a plan and a state. Needs the &#x60;manage_supporters&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val planId : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | Only the subscriptions of this plan.
val state : SupporterSubscriptionState =  // SupporterSubscriptionState | Only the subscriptions in this state.
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : AdminSupporterSubscriptionPage = apiInstance.listSupporterSubscriptions(planId, state, cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#listSupporterSubscriptions")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#listSupporterSubscriptions")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **planId** | **java.util.UUID**| Only the subscriptions of this plan. | [optional] |
| **state** | [**SupporterSubscriptionState**](.md)| Only the subscriptions in this state. | [optional] [enum: pending, active, canceling, past_due, lapsed, expired, revoked, abandoned] |
| **cursor** | **kotlin.String**| The &#x60;next_cursor&#x60; of the previous page; omitted for the first page. | [optional] |
| **limit** | **kotlin.Int**| The maximum number of items in the page. | [optional] [default to 20] |

### Return type

[**AdminSupporterSubscriptionPage**](AdminSupporterSubscriptionPage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listUXMeasurements"></a>
# **listUXMeasurements**
> UXMeasurementList listUXMeasurements(hours)

List the UX measurements

The samples of how fast the web client responded (&#x60;POST /client_measurements&#x60;), summed over the last &#x60;hours&#x60; hours, the current one included, by metric and name, with their buckets and 75th percentile. The server keeps the hourly counts for 30 days. Needs the &#x60;manage_errors&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val hours : kotlin.Int = 56 // kotlin.Int | The hours of the window, 24 by default.
try {
    val result : UXMeasurementList = apiInstance.listUXMeasurements(hours)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#listUXMeasurements")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#listUXMeasurements")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **hours** | **kotlin.Int**| The hours of the window, 24 by default. | [optional] [default to 24] |

### Return type

[**UXMeasurementList**](UXMeasurementList.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listWebhookDeliveries"></a>
# **listWebhookDeliveries**
> AdminWebhookDeliveryPage listWebhookDeliveries(id, cursor, limit)

List the deliveries of a webhook

The messages sent to the endpoint in the last 14 days, newest first, with their attempts. An unknown or deleted ID is a 404. Needs the &#x60;manage_webhooks&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the webhook.
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : AdminWebhookDeliveryPage = apiInstance.listWebhookDeliveries(id, cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#listWebhookDeliveries")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#listWebhookDeliveries")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the webhook. | |
| **cursor** | **kotlin.String**| The &#x60;next_cursor&#x60; of the previous page; omitted for the first page. | [optional] |
| **limit** | **kotlin.Int**| The maximum number of items in the page. | [optional] [default to 20] |

### Return type

[**AdminWebhookDeliveryPage**](AdminWebhookDeliveryPage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="moveInstanceRule"></a>
# **moveInstanceRule**
> AdminRule moveInstanceRule(id, moveInstanceRuleRequest, idempotencyKey)

Move an instance rule

Moves the rule to a place among the rules in effect and numbers them 1, 2, ... in their new order; a place after the last rule moves it last. A &#x60;position&#x60; below 1 is a 422, a retired rule a 409, and an unknown ID a 404. Needs the &#x60;manage_rules&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the rule.
val moveInstanceRuleRequest : MoveInstanceRuleRequest =  // MoveInstanceRuleRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AdminRule = apiInstance.moveInstanceRule(id, moveInstanceRuleRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#moveInstanceRule")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#moveInstanceRule")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the rule. | |
| **moveInstanceRuleRequest** | [**MoveInstanceRuleRequest**](MoveInstanceRuleRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AdminRule**](AdminRule.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="muteErrorGroup"></a>
# **muteErrorGroup**
> ErrorGroup muteErrorGroup(id, idempotencyKey)

Mute an error group

Mutes a group: it notifies nobody and sends no webhook event until it is unmuted, and it is still counted. Muting a muted group changes nothing. An unknown group is a 404. Needs the &#x60;manage_errors&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the error group.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : ErrorGroup = apiInstance.muteErrorGroup(id, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#muteErrorGroup")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#muteErrorGroup")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the error group. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**ErrorGroup**](ErrorGroup.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="publishAdminAnnouncement"></a>
# **publishAdminAnnouncement**
> AdminAnnouncement publishAdminAnnouncement(id, idempotencyKey)

Publish an announcement

Publishes a draft to every local user, within its window; once it starts to show, every local user gets an &#x60;announcement&#x60; notification, unless they turned it off. Publishing a published announcement changes nothing. A withdrawn announcement is a 409 and an unknown ID a 404. Recorded in the audit log. Needs the &#x60;manage_announcements&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the announcement.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AdminAnnouncement = apiInstance.publishAdminAnnouncement(id, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#publishAdminAnnouncement")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#publishAdminAnnouncement")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the announcement. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AdminAnnouncement**](AdminAnnouncement.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="publishAdminTheme"></a>
# **publishAdminTheme**
> AdminTheme publishAdminTheme(idempotencyKey)

Publish the draft of the instance theme

Publishes the draft under a new revision, which every visitor&#39;s web client and the server-rendered pages load as the stylesheet &#x60;stylesheet_url&#x60; (after the shell&#39;s shared cache of up to a few minutes). The draft is checked again as a whole, since an upgrade may have changed the defaults its contrast pairs are checked with; its stale overrides are left out. The publication is kept (the audit log&#39;s &#x60;setting_changed&#x60; of &#x60;theme&#x60;). 404 when no draft was saved; a 422 &#x60;/api/v1/problems/invalid-theme&#x60; when the draft no longer meets a rule. Needs the &#x60;manage_settings&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AdminTheme = apiInstance.publishAdminTheme(idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#publishAdminTheme")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#publishAdminTheme")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AdminTheme**](AdminTheme.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="redeliverWebhookDelivery"></a>
# **redeliverWebhookDelivery**
> AdminWebhookDelivery redeliverWebhookDelivery(id, deliveryId, idempotencyKey)

Send a delivery of a webhook again

Sends the message again with the same &#x60;webhook-id&#x60;, also after it succeeded or used up its attempts; the job queue sends it soon. A disabled webhook receives nothing. It is not recorded in the audit log. An unknown webhook or delivery is a 404. Needs the &#x60;manage_webhooks&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the webhook.
val deliveryId : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the delivery.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AdminWebhookDelivery = apiInstance.redeliverWebhookDelivery(id, deliveryId, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#redeliverWebhookDelivery")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#redeliverWebhookDelivery")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the webhook. | |
| **deliveryId** | **java.util.UUID**| The ID of the delivery. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AdminWebhookDelivery**](AdminWebhookDelivery.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="rejectCustomEmojiRequest"></a>
# **rejectCustomEmojiRequest**
> AdminCustomEmojiRequest rejectCustomEmojiRequest(id, idempotencyKey, rejectCustomEmojiRequestRequest)

Reject a custom emoji request

Rejects a pending request, with an optional reason that the requester sees, which frees its shortcode. The rejection is recorded in the audit log, and the requester is notified. A reason longer than 500 characters is a 422 with the reason &#x60;invalid_reason&#x60;; a request that is not pending is a 409 &#x60;/api/v1/problems/custom-emoji-request-conflict&#x60; (&#x60;not_pending&#x60;). An unknown request is a 404. Needs the &#x60;manage_custom_emojis&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the custom emoji request.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
val rejectCustomEmojiRequestRequest : RejectCustomEmojiRequestRequest =  // RejectCustomEmojiRequestRequest | 
try {
    val result : AdminCustomEmojiRequest = apiInstance.rejectCustomEmojiRequest(id, idempotencyKey, rejectCustomEmojiRequestRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#rejectCustomEmojiRequest")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#rejectCustomEmojiRequest")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the custom emoji request. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |
| **rejectCustomEmojiRequestRequest** | [**RejectCustomEmojiRequestRequest**](RejectCustomEmojiRequestRequest.md)|  | [optional] |

### Return type

[**AdminCustomEmojiRequest**](AdminCustomEmojiRequest.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="removeLinkCardDomainRule"></a>
# **removeLinkCardDomainRule**
> removeLinkCardDomainRule(domain, idempotencyKey)

Remove the rule of a domain for the link card crawler

Removes the rule of a domain and records it in the audit log. The cards already kept show as they did at once; a URL that was not fetched because of the rule gets its card the next time a post needs it, and nothing is fetched again in bulk. A domain without a rule changes nothing. Needs the &#x60;manage_settings&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val domain : kotlin.String = domain_example // kotlin.String | The canonical domain of the rule.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    apiInstance.removeLinkCardDomainRule(domain, idempotencyKey)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#removeLinkCardDomainRule")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#removeLinkCardDomainRule")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **domain** | **kotlin.String**| The canonical domain of the rule. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

null (empty response body)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/problem+json

<a id="replaceCustomEmojiImage"></a>
# **replaceCustomEmojiImage**
> AdminCustomEmoji replaceCustomEmojiImage(id, replaceCustomEmojiImageRequest, idempotencyKey)

Replace the image of a local custom emoji

Makes the image of a media file the new image of a local emoji, checked as for a new emoji; the servers that have the emoji fetch it again (its &#x60;updated_at&#x60; changes). A remote emoji is a 422 with the reason &#x60;remote_emoji&#x60;. The change is recorded in the audit log. An unknown ID is a 404. Needs the &#x60;manage_custom_emojis&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the custom emoji.
val replaceCustomEmojiImageRequest : ReplaceCustomEmojiImageRequest =  // ReplaceCustomEmojiImageRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AdminCustomEmoji = apiInstance.replaceCustomEmojiImage(id, replaceCustomEmojiImageRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#replaceCustomEmojiImage")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#replaceCustomEmojiImage")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the custom emoji. | |
| **replaceCustomEmojiImageRequest** | [**ReplaceCustomEmojiImageRequest**](ReplaceCustomEmojiImageRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AdminCustomEmoji**](AdminCustomEmoji.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="resolveErrorGroup"></a>
# **resolveErrorGroup**
> ErrorGroup resolveErrorGroup(id, idempotencyKey)

Resolve an error group

Marks a group resolved with the version of its last occurrence: an occurrence on another version regresses it to &#x60;open&#x60; and notifies again; an occurrence on the same version is only counted. Resolving a resolved group changes nothing. An unknown group is a 404. Needs the &#x60;manage_errors&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the error group.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : ErrorGroup = apiInstance.resolveErrorGroup(id, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#resolveErrorGroup")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#resolveErrorGroup")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the error group. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**ErrorGroup**](ErrorGroup.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="restoreAdminTheme"></a>
# **restoreAdminTheme**
> AdminTheme restoreAdminTheme(idempotencyKey)

Restore the previously published instance theme

Publishes again, under a new revision, the token set published before the one in effect; the draft stays as it is. Restoring twice in a row goes back to the set in effect before the first restore. The publication is kept (the audit log&#39;s &#x60;setting_changed&#x60; of &#x60;theme&#x60;). 404 when no other set was ever published; a 422 &#x60;/api/v1/problems/invalid-theme&#x60; when that set no longer meets a rule. Needs the &#x60;manage_settings&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AdminTheme = apiInstance.restoreAdminTheme(idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#restoreAdminTheme")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#restoreAdminTheme")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AdminTheme**](AdminTheme.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="retireInstanceRule"></a>
# **retireInstanceRule**
> AdminRule retireInstanceRule(id, idempotencyKey)

Retire an instance rule

Removes the rule from the rules in effect, at once and for good; reports that cite it still refer to it. Retiring a retired rule changes nothing. An unknown ID is a 404. Needs the &#x60;manage_rules&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the rule.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AdminRule = apiInstance.retireInstanceRule(id, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#retireInstanceRule")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#retireInstanceRule")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the rule. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AdminRule**](AdminRule.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="retireSupporterPlan"></a>
# **retireSupporterPlan**
> AdminSupporterPlan retireSupporterPlan(id, idempotencyKey)

Retire a supporter plan

Retires a supporter plan for good: it takes no subscriptions any more, and every subscription of it ends at the end of its period (a pending one at once). A retired plan is a 409 &#x60;/api/v1/problems/supporter-plan-retired&#x60;, and an unknown one a 404. Recorded in the audit log. Needs the &#x60;manage_supporters&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the supporter plan.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AdminSupporterPlan = apiInstance.retireSupporterPlan(id, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#retireSupporterPlan")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#retireSupporterPlan")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the supporter plan. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AdminSupporterPlan**](AdminSupporterPlan.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="retryAdminJob"></a>
# **retryAdminJob**
> retryAdminJob(id, idempotencyKey)

Run a dead job again

Enqueues the dead job again with a fresh attempt count, to run now, and removes it from the dead jobs. When a pending job has the same idempotency key, that job stands in for it. An unknown ID, one that is not a dead job, or one already retried, is a 404. Needs the &#x60;manage_jobs&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the dead job.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    apiInstance.retryAdminJob(id, idempotencyKey)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#retryAdminJob")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#retryAdminJob")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the dead job. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

null (empty response body)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/problem+json

<a id="reviseCostItemPrice"></a>
# **reviseCostItemPrice**
> CostItem reviseCostItemPrice(id, costPriceRevisionRequest, idempotencyKey)

Revise a cost item&#39;s price

Records the price of the payments on or after &#x60;effective_on&#x60;. A date before the start date (&#x60;effective_on&#x60;) and an invalid price (&#x60;price&#x60;) are a 422. An unknown or deleted item is a 404. Recorded in the audit log. Needs the &#x60;manage_finance&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the cost item.
val costPriceRevisionRequest : CostPriceRevisionRequest =  // CostPriceRevisionRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : CostItem = apiInstance.reviseCostItemPrice(id, costPriceRevisionRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#reviseCostItemPrice")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#reviseCostItemPrice")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the cost item. | |
| **costPriceRevisionRequest** | [**CostPriceRevisionRequest**](CostPriceRevisionRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**CostItem**](CostItem.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="revokeAccountRole"></a>
# **revokeAccountRole**
> AdminRoleList revokeAccountRole(id, role, idempotencyKey)

Revoke a role from a local account

Revokes the role from the user of a local account; its permissions stop applying from the user&#39;s next request, and the audit log records &#x60;role_revoked&#x60;. Revoking a role that the user does not hold changes nothing and records nothing. A role whose position is above every role of the viewer is a 403, and revoking &#x60;admin&#x60; from its only holder is a 409. An unknown account, one that is not local, or an unknown role is a 404. Answers the roles that the user holds afterwards. Needs the &#x60;manage_roles&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the account, local or remote.
val role : kotlin.String = role_example // kotlin.String | The key of the role, such as `moderator`.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AdminRoleList = apiInstance.revokeAccountRole(id, role, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#revokeAccountRole")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#revokeAccountRole")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the account, local or remote. | |
| **role** | **kotlin.String**| The key of the role, such as &#x60;moderator&#x60;. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AdminRoleList**](AdminRoleList.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="rotateLoginProviderSecret"></a>
# **rotateLoginProviderSecret**
> AdminLoginProvider rotateLoginProviderSecret(id, rotateLoginProviderSecretRequest, idempotencyKey)

Give a login provider a new client secret

Replaces the client secret with the one the request gives, which the provider issued; the previous one is no longer used. The secret is never shown again. A secret that breaks a rule is a 422 &#x60;/api/v1/problems/invalid-login-provider&#x60;. The rotation is recorded in the audit log. An unknown or deleted ID is a 404. Needs the &#x60;manage_login_providers&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the login provider.
val rotateLoginProviderSecretRequest : RotateLoginProviderSecretRequest =  // RotateLoginProviderSecretRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AdminLoginProvider = apiInstance.rotateLoginProviderSecret(id, rotateLoginProviderSecretRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#rotateLoginProviderSecret")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#rotateLoginProviderSecret")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the login provider. | |
| **rotateLoginProviderSecretRequest** | [**RotateLoginProviderSecretRequest**](RotateLoginProviderSecretRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AdminLoginProvider**](AdminLoginProvider.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="rotateWebhookSecret"></a>
# **rotateWebhookSecret**
> AdminWebhookSecret rotateWebhookSecret(id)

Give a webhook a new secret

Makes a new secret, shown only now, that signs every message from now on. For 24 hours the previous secret signs them too (&#x60;webhook-signature&#x60; carries both), so that the receiver can switch without losing a message. The rotation is recorded in the audit log. An unknown or deleted ID is a 404. Needs the &#x60;manage_webhooks&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the webhook.
try {
    val result : AdminWebhookSecret = apiInstance.rotateWebhookSecret(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#rotateWebhookSecret")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#rotateWebhookSecret")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the webhook. | |

### Return type

[**AdminWebhookSecret**](AdminWebhookSecret.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="runBillingImport"></a>
# **runBillingImport**
> runBillingImport(source, idempotencyKey)

Import a provider&#39;s usage charges now

Queues an import of the billing source now, besides the daily one. Its billed requests count toward the caps like the others. One import per source and UTC hour: another request in the hour after its import ran is a 429. A source that &#x60;billing_sources&#x60; does not enable is a 404. The request is recorded in the audit log. Needs the &#x60;manage_finance&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val source : BillingSourceName =  // BillingSourceName | The billing source.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    apiInstance.runBillingImport(source, idempotencyKey)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#runBillingImport")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#runBillingImport")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **source** | [**BillingSourceName**](.md)| The billing source. | [enum: aws, gcp, cloudflare, plugin] |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

null (empty response body)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/problem+json

<a id="runPaymentReconciliation"></a>
# **runPaymentReconciliation**
> runPaymentReconciliation(idempotencyKey)

Reconcile with the payment provider now

Queues a reconciliation with the payment provider now, besides the periodic one. Its requests count toward &#x60;payments_reconcile_max_requests&#x60; like the others. One per UTC hour: another request in the hour after its reconciliation ran is a 429. A deployment without a payment provider is a 404. The request is recorded in the audit log. Needs the &#x60;manage_supporters&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    apiInstance.runPaymentReconciliation(idempotencyKey)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#runPaymentReconciliation")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#runPaymentReconciliation")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

null (empty response body)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/problem+json

<a id="sanitizeAdminBrand"></a>
# **sanitizeAdminBrand**
> BrandSanitized sanitizeAdminBrand(kind, svg, idempotencyKey)

Check an SVG for a brand image

Checks and sanitizes an SVG as &#x60;setAdminBrand&#x60; would, without saving it, and answers the sanitized SVG with the renditions to draw from it. The admin screen draws them from this SVG and sends them with it to &#x60;setAdminBrand&#x60;.  The server reads the SVG as XML and writes a new document of the allowed elements (shapes, groups, &#x60;defs&#x60;, &#x60;symbol&#x60;, &#x60;use&#x60;, gradients, &#x60;clipPath&#x60;, &#x60;mask&#x60;, &#x60;pattern&#x60;, &#x60;style&#x60;, &#x60;title&#x60;, &#x60;desc&#x60;) and attributes (geometry, transforms, presentation attributes, &#x60;id&#x60;, &#x60;class&#x60;, &#x60;style&#x60;) only. Other elements are removed with their content (scripts, &#x60;foreignObject&#x60;, embedded images, links, animations, filters), and so are other attributes (event handlers among them), references other than to an element of the same document (&#x60;#id&#x60;), and &#x60;data:&#x60; URLs; &#x60;dropped&#x60; lists what was removed. What removing cannot mend is a 422 &#x60;/api/v1/problems/invalid-brand&#x60; (&#x60;brand_error&#x60;): see &#x60;BrandError&#x60;. Needs the &#x60;manage_settings&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val kind : BrandKind =  // BrandKind | 
val svg : io.ktor.client.request.forms.FormPart<io.ktor.client.request.forms.InputProvider> = BINARY_DATA_HERE // io.ktor.client.request.forms.FormPart<io.ktor.client.request.forms.InputProvider> | The SVG file, at most 262,144 bytes.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : BrandSanitized = apiInstance.sanitizeAdminBrand(kind, svg, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#sanitizeAdminBrand")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#sanitizeAdminBrand")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **kind** | [**BrandKind**](BrandKind.md)|  | [enum: icon, logo, logo_dark] |
| **svg** | **io.ktor.client.request.forms.FormPart&lt;io.ktor.client.request.forms.InputProvider&gt;**| The SVG file, at most 262,144 bytes. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**BrandSanitized**](BrandSanitized.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: multipart/form-data
 - **Accept**: application/json, application/problem+json

<a id="setAdminBrand"></a>
# **setAdminBrand**
> AdminBrandImage setAdminBrand(kind, svg, idempotencyKey, background, favicon32, favicon48, appleTouch180, pwa192, pwa512, maskable512, logoMail, logoDarkMail, preview1200x630)

Set a brand image

Replaces the icon, the logo, or the dark logo with an SVG and its PNG renditions (see &#x60;SetBrandRequest&#x60; and &#x60;BrandRendition&#x60;), which the web client, the Web App Manifest, link previews, and emails then show instead of the built-in images. The server sanitizes the SVG again (&#x60;sanitizeAdminBrand&#x60;), checks that each PNG is a PNG of its rendition&#39;s size (at most 1,048,576 bytes, opaque where the rendition must be), and writes the PNGs again without their metadata. A problem with any file saves nothing: a 422 &#x60;/api/v1/problems/invalid-brand&#x60; names it in &#x60;brand_error&#x60;. The server does not check that the PNGs show the same picture as the SVG.  The revision and the change are kept (the audit log&#39;s &#x60;setting_changed&#x60; of &#x60;brand_icon&#x60;, &#x60;brand_logo&#x60;, or &#x60;brand_logo_dark&#x60;); the files of every revision stay at their URLs. Needs the &#x60;manage_settings&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val kind : BrandKind =  // BrandKind | Which brand image.
val svg : io.ktor.client.request.forms.FormPart<io.ktor.client.request.forms.InputProvider> = BINARY_DATA_HERE // io.ktor.client.request.forms.FormPart<io.ktor.client.request.forms.InputProvider> | The SVG file, at most 262,144 bytes.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
val background : kotlin.String = background_example // kotlin.String | The `#rrggbb` color behind the opaque renditions of the icon; the theme's light surface color when it is left out. A logo takes none. 
val favicon32 : io.ktor.client.request.forms.FormPart<io.ktor.client.request.forms.InputProvider> = BINARY_DATA_HERE // io.ktor.client.request.forms.FormPart<io.ktor.client.request.forms.InputProvider> | 
val favicon48 : io.ktor.client.request.forms.FormPart<io.ktor.client.request.forms.InputProvider> = BINARY_DATA_HERE // io.ktor.client.request.forms.FormPart<io.ktor.client.request.forms.InputProvider> | 
val appleTouch180 : io.ktor.client.request.forms.FormPart<io.ktor.client.request.forms.InputProvider> = BINARY_DATA_HERE // io.ktor.client.request.forms.FormPart<io.ktor.client.request.forms.InputProvider> | 
val pwa192 : io.ktor.client.request.forms.FormPart<io.ktor.client.request.forms.InputProvider> = BINARY_DATA_HERE // io.ktor.client.request.forms.FormPart<io.ktor.client.request.forms.InputProvider> | 
val pwa512 : io.ktor.client.request.forms.FormPart<io.ktor.client.request.forms.InputProvider> = BINARY_DATA_HERE // io.ktor.client.request.forms.FormPart<io.ktor.client.request.forms.InputProvider> | 
val maskable512 : io.ktor.client.request.forms.FormPart<io.ktor.client.request.forms.InputProvider> = BINARY_DATA_HERE // io.ktor.client.request.forms.FormPart<io.ktor.client.request.forms.InputProvider> | 
val logoMail : io.ktor.client.request.forms.FormPart<io.ktor.client.request.forms.InputProvider> = BINARY_DATA_HERE // io.ktor.client.request.forms.FormPart<io.ktor.client.request.forms.InputProvider> | 
val logoDarkMail : io.ktor.client.request.forms.FormPart<io.ktor.client.request.forms.InputProvider> = BINARY_DATA_HERE // io.ktor.client.request.forms.FormPart<io.ktor.client.request.forms.InputProvider> | 
val preview1200x630 : io.ktor.client.request.forms.FormPart<io.ktor.client.request.forms.InputProvider> = BINARY_DATA_HERE // io.ktor.client.request.forms.FormPart<io.ktor.client.request.forms.InputProvider> | 
try {
    val result : AdminBrandImage = apiInstance.setAdminBrand(kind, svg, idempotencyKey, background, favicon32, favicon48, appleTouch180, pwa192, pwa512, maskable512, logoMail, logoDarkMail, preview1200x630)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#setAdminBrand")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#setAdminBrand")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **kind** | [**BrandKind**](.md)| Which brand image. | [enum: icon, logo, logo_dark] |
| **svg** | **io.ktor.client.request.forms.FormPart&lt;io.ktor.client.request.forms.InputProvider&gt;**| The SVG file, at most 262,144 bytes. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |
| **background** | **kotlin.String**| The &#x60;#rrggbb&#x60; color behind the opaque renditions of the icon; the theme&#39;s light surface color when it is left out. A logo takes none.  | [optional] |
| **favicon32** | **io.ktor.client.request.forms.FormPart&lt;io.ktor.client.request.forms.InputProvider&gt;**|  | [optional] |
| **favicon48** | **io.ktor.client.request.forms.FormPart&lt;io.ktor.client.request.forms.InputProvider&gt;**|  | [optional] |
| **appleTouch180** | **io.ktor.client.request.forms.FormPart&lt;io.ktor.client.request.forms.InputProvider&gt;**|  | [optional] |
| **pwa192** | **io.ktor.client.request.forms.FormPart&lt;io.ktor.client.request.forms.InputProvider&gt;**|  | [optional] |
| **pwa512** | **io.ktor.client.request.forms.FormPart&lt;io.ktor.client.request.forms.InputProvider&gt;**|  | [optional] |
| **maskable512** | **io.ktor.client.request.forms.FormPart&lt;io.ktor.client.request.forms.InputProvider&gt;**|  | [optional] |
| **logoMail** | **io.ktor.client.request.forms.FormPart&lt;io.ktor.client.request.forms.InputProvider&gt;**|  | [optional] |
| **logoDarkMail** | **io.ktor.client.request.forms.FormPart&lt;io.ktor.client.request.forms.InputProvider&gt;**|  | [optional] |
| **preview1200x630** | **io.ktor.client.request.forms.FormPart&lt;io.ktor.client.request.forms.InputProvider&gt;**|  | [optional] |

### Return type

[**AdminBrandImage**](AdminBrandImage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: multipart/form-data
 - **Accept**: application/json, application/problem+json

<a id="setAdminThemeDraft"></a>
# **setAdminThemeDraft**
> AdminTheme setAdminThemeDraft(setThemeRequest, idempotencyKey)

Save the draft of the instance theme

Saves a token set as the draft, which replaces the previous draft as a whole. The theme in effect does not change until the draft is published (&#x60;POST /admin/theme/publish&#x60;); the draft&#39;s stylesheet can be previewed first (&#x60;GET /admin/theme/draft/stylesheet&#x60;). &#x60;tokens&#x60; lists every overridden token; the tokens it leaves out keep their defaults, and no tokens make a draft that resets the theme.  The overridable tokens are the colors, the font, the type sizes and line heights, the radii, and the spin duration of the web client&#39;s design tokens (the list generated from &#x60;@my/design-tokens&#x60;); the shadows, the spacing, the component sizes, and the colors of the data charts stay fixed. A color token takes &#x60;light&#x60; and &#x60;dark&#x60;, each &#x60;#rrggbb&#x60;; the other tokens take &#x60;value&#x60;: a length is &#x60;0&#x60; or a &#x60;rem&#x60; / &#x60;px&#x60; value within the token&#39;s bounds, a line height a number, a duration &#x60;ms&#x60; / &#x60;s&#x60;, and a font family a list of family names. Every contrast pair of the color tokens, with the defaults of the tokens not overridden, must meet WCAG 2.2 AA (4.5:1 for text, 3:1 for marks) in both color schemes. A set that breaks any rule is a 422 &#x60;/api/v1/problems/invalid-theme&#x60; listing every problem in &#x60;theme_errors&#x60;. Needs the &#x60;manage_settings&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val setThemeRequest : SetThemeRequest =  // SetThemeRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AdminTheme = apiInstance.setAdminThemeDraft(setThemeRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#setAdminThemeDraft")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#setAdminThemeDraft")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **setThemeRequest** | [**SetThemeRequest**](SetThemeRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AdminTheme**](AdminTheme.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="setCostItemEnd"></a>
# **setCostItemEnd**
> CostItem setCostItemEnd(id, costItemEndRequest, idempotencyKey)

Set a cost item&#39;s end

Ends a cost item on &#x60;ends_on&#x60; (a cancellation of the subscription: the payments after it go away, the past ones stay), or makes it endless again without &#x60;ends_on&#x60;. A date before the start date is a 422 naming &#x60;ends_on&#x60;. An unknown or deleted item is a 404. Recorded in the audit log. Needs the &#x60;manage_finance&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the cost item.
val costItemEndRequest : CostItemEndRequest =  // CostItemEndRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : CostItem = apiInstance.setCostItemEnd(id, costItemEndRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#setCostItemEnd")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#setCostItemEnd")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the cost item. | |
| **costItemEndRequest** | [**CostItemEndRequest**](CostItemEndRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**CostItem**](CostItem.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="setInstanceText"></a>
# **setInstanceText**
> AdminInstanceText setInstanceText(key, setInstanceTextRequest, idempotencyKey)

Set an instance text

Records a revision of the text of &#x60;key&#x60;, which becomes its current text in every locale: the administrator writes it once, in any language, and readers of every locale see the same value. An empty &#x60;body&#x60; clears the text. The revision and the change are kept (the audit log&#39;s &#x60;text_changed&#x60;). The terms of service, the commerce disclosure, and the privacy policy are Markdown, which the server renders and sanitizes for the web client&#39;s pages of them (&#x60;GET /instance/documents/{kind}&#x60;, linked from &#x60;Instance.terms_url&#x60;, &#x60;Instance.privacy_policy_url&#x60; and &#x60;Instance.commerce_disclosure_url&#x60;); the other texts are plain text. The display name (&#x60;name&#x60;) names the instance to people in place of its domain: the web client&#39;s navigation and tab title, the server-rendered pages, emails, link previews, the Web App Manifest, and NodeInfo&#39;s &#x60;metadata.nodeName&#x60;; without a name, the domain names it, and handles and URLs always use the domain. An unknown key is a 404. A body longer than the key allows (100 characters for &#x60;name&#x60;, 500 for &#x60;short_description&#x60;, 1,500 for &#x60;preview_image_alt&#x60; and &#x60;thumbnail_alt&#x60;, 20,000 for &#x60;description&#x60;, 100,000 for &#x60;terms&#x60;, &#x60;privacy_policy&#x60; and &#x60;commerce_disclosure&#x60;), or with a line break in &#x60;name&#x60; or of only spaces in &#x60;name&#x60;, is a 422 naming &#x60;body&#x60;. Needs the &#x60;manage_settings&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val key : InstanceTextKey =  // InstanceTextKey | The key of the text.
val setInstanceTextRequest : SetInstanceTextRequest =  // SetInstanceTextRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AdminInstanceText = apiInstance.setInstanceText(key, setInstanceTextRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#setInstanceText")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#setInstanceText")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **key** | [**InstanceTextKey**](.md)| The key of the text. | [enum: name, short_description, description, terms, privacy_policy, preview_image_alt, thumbnail_alt, commerce_disclosure] |
| **setInstanceTextRequest** | [**SetInstanceTextRequest**](SetInstanceTextRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AdminInstanceText**](AdminInstanceText.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="setLinkCardDomainRule"></a>
# **setLinkCardDomainRule**
> LinkCardDomainRule setLinkCardDomainRule(domain, setLinkCardDomainRuleRequest, idempotencyKey)

Set the rule of a domain for the link card crawler

Records the rule of a domain, replacing the rule it has, and records the change in the audit log. The rule takes effect at once, also on the cards already kept, and bears only on link cards: federation and other remote media follow the domain actions. A domain that is not a host is a 400; a note longer than 5,000 characters or a new rule beyond 1,000 is a 422 naming the property (&#x60;note&#x60;, &#x60;rules&#x60;). Needs the &#x60;manage_settings&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val domain : kotlin.String = domain_example // kotlin.String | The domain, a host without a scheme, path, or port; it also covers its subdomains.
val setLinkCardDomainRuleRequest : SetLinkCardDomainRuleRequest =  // SetLinkCardDomainRuleRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : LinkCardDomainRule = apiInstance.setLinkCardDomainRule(domain, setLinkCardDomainRuleRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#setLinkCardDomainRule")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#setLinkCardDomainRule")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **domain** | **kotlin.String**| The domain, a host without a scheme, path, or port; it also covers its subdomains. | |
| **setLinkCardDomainRuleRequest** | [**SetLinkCardDomainRuleRequest**](SetLinkCardDomainRuleRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**LinkCardDomainRule**](LinkCardDomainRule.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="setSupporterProgram"></a>
# **setSupporterProgram**
> AdminSupporterProgram setSupporterProgram(adminSupporterProgramRequest, idempotencyKey)

Enable or disable the supporter program

Enables or disables the supporter program. Enabling it without a payment provider in the deployment&#39;s configuration or without email is a 409 &#x60;/api/v1/problems/supporters-unavailable&#x60;; disabling it while subscriptions go on is a 409 &#x60;/api/v1/problems/live-subscriptions&#x60; (retire the plans first). Asking for the state it is in changes nothing. Recorded in the audit log. Needs the &#x60;manage_supporters&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val adminSupporterProgramRequest : AdminSupporterProgramRequest =  // AdminSupporterProgramRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AdminSupporterProgram = apiInstance.setSupporterProgram(adminSupporterProgramRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#setSupporterProgram")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#setSupporterProgram")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **adminSupporterProgramRequest** | [**AdminSupporterProgramRequest**](AdminSupporterProgramRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AdminSupporterProgram**](AdminSupporterProgram.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="setSupporterTips"></a>
# **setSupporterTips**
> AdminSupporterTips setSupporterTips(adminSupporterTipsRequest, idempotencyKey)

Enable or disable the tips

Enables the tips with their amounts, changes the amounts, or disables the tips. Enabling them needs the supporter program enabled on the configured payment provider, which takes one-time payments: a 409 &#x60;/api/v1/problems/tips-unavailable&#x60; otherwise. The amounts are 1 to 6 different amounts in one currency, each from ¥100 to ¥100,000 or from US$1 to US$1,000 and at least the provider&#39;s smallest one-time charge: a 422 &#x60;/api/v1/problems/invalid-tip-amounts&#x60; whose &#x60;reason&#x60; says which rule they break. Asking for the state the tips are in changes nothing. Recorded in the audit log. Needs the &#x60;manage_supporters&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val adminSupporterTipsRequest : AdminSupporterTipsRequest =  // AdminSupporterTipsRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AdminSupporterTips = apiInstance.setSupporterTips(adminSupporterTipsRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#setSupporterTips")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#setSupporterTips")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **adminSupporterTipsRequest** | [**AdminSupporterTipsRequest**](AdminSupporterTipsRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AdminSupporterTips**](AdminSupporterTips.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="testWebhook"></a>
# **testWebhook**
> AdminWebhookDelivery testWebhook(id, idempotencyKey)

Send a test message to a webhook

Sends a &#x60;webhook.test&#x60; message to the endpoint, whatever it subscribes to, and returns its delivery, which the job queue sends soon. A disabled webhook receives nothing. It is not recorded in the audit log. An unknown or deleted ID is a 404. Needs the &#x60;manage_webhooks&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the webhook.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AdminWebhookDelivery = apiInstance.testWebhook(id, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#testWebhook")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#testWebhook")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the webhook. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AdminWebhookDelivery**](AdminWebhookDelivery.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="unfollowRelay"></a>
# **unfollowRelay**
> AdminRelay unfollowRelay(id, idempotencyKey)

Leave a relay

Sends the &#x60;Undo&#x60; of the &#x60;Follow&#x60; of a &#x60;pending&#x60; or &#x60;accepted&#x60; relay; the relay is &#x60;left&#x60;, and nothing goes to it or comes from it any more. The posts it brought in stay until their retention ends. A relay in another state is a 422 &#x60;/api/v1/problems/invalid-relay&#x60; (&#x60;state&#x60;). The &#x60;Undo&#x60; is recorded in the audit log. An unknown ID is a 404. Needs the &#x60;manage_settings&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the relay.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AdminRelay = apiInstance.unfollowRelay(id, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#unfollowRelay")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#unfollowRelay")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the relay. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AdminRelay**](AdminRelay.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="unmuteErrorGroup"></a>
# **unmuteErrorGroup**
> ErrorGroup unmuteErrorGroup(id, idempotencyKey)

Unmute an error group

Opens a muted group again (also one that was resolved before it was muted). Unmuting a group that is not muted changes nothing. An unknown group is a 404. Needs the &#x60;manage_errors&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the error group.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : ErrorGroup = apiInstance.unmuteErrorGroup(id, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#unmuteErrorGroup")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#unmuteErrorGroup")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the error group. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**ErrorGroup**](ErrorGroup.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="updateAdminAnnouncement"></a>
# **updateAdminAnnouncement**
> AdminAnnouncement updateAdminAnnouncement(id, adminAnnouncementInput, idempotencyKey)

Revise an announcement

Replaces the title, the text, and the window of a draft or a published announcement, which keeps its state; an omitted &#x60;starts_at&#x60; or &#x60;ends_at&#x60; leaves that side of the window open. The input is checked as for a new announcement (422). A change after the announcement has started to show notifies nobody again; moving the start of the window of one that has not shown yet moves its notifications too. A withdrawn announcement is a 409 and an unknown ID a 404. Recorded in the audit log. Needs the &#x60;manage_announcements&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the announcement.
val adminAnnouncementInput : AdminAnnouncementInput =  // AdminAnnouncementInput | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AdminAnnouncement = apiInstance.updateAdminAnnouncement(id, adminAnnouncementInput, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#updateAdminAnnouncement")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#updateAdminAnnouncement")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the announcement. | |
| **adminAnnouncementInput** | [**AdminAnnouncementInput**](AdminAnnouncementInput.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AdminAnnouncement**](AdminAnnouncement.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="updateCostItem"></a>
# **updateCostItem**
> CostItem updateCostItem(id, costItemLabelRequest, idempotencyKey)

Change a cost item&#39;s label

Records a new name, provider, category, or memo of a cost item; an omitted property keeps its value. The values are checked as for a new item (422). An unknown or deleted item is a 404. Recorded in the audit log. Needs the &#x60;manage_finance&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the cost item.
val costItemLabelRequest : CostItemLabelRequest =  // CostItemLabelRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : CostItem = apiInstance.updateCostItem(id, costItemLabelRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#updateCostItem")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#updateCostItem")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the cost item. | |
| **costItemLabelRequest** | [**CostItemLabelRequest**](CostItemLabelRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**CostItem**](CostItem.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="updateCustomEmoji"></a>
# **updateCustomEmoji**
> AdminCustomEmoji updateCustomEmoji(id, updateCustomEmojiRequest, idempotencyKey)

Change the settings of a custom emoji

Changes what the request names and keeps the rest. A remote emoji has none of these settings: a change of one is a 422 with the reason &#x60;remote_emoji&#x60;. &#x60;category&#x60; is trimmed, and an empty one removes the category; &#x60;aliases&#x60; replace the aliases. Each change is recorded in the audit log. An unknown ID is a 404. Needs the &#x60;manage_custom_emojis&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the custom emoji.
val updateCustomEmojiRequest : UpdateCustomEmojiRequest =  // UpdateCustomEmojiRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AdminCustomEmoji = apiInstance.updateCustomEmoji(id, updateCustomEmojiRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#updateCustomEmoji")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#updateCustomEmoji")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the custom emoji. | |
| **updateCustomEmojiRequest** | [**UpdateCustomEmojiRequest**](UpdateCustomEmojiRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AdminCustomEmoji**](AdminCustomEmoji.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="updateInstanceRule"></a>
# **updateInstanceRule**
> AdminRule updateInstanceRule(id, instanceRuleInput, idempotencyKey)

Revise the text of an instance rule

Records a revision whose text and hint replace those of the rule, so that an omitted hint removes the hint; the rule keeps its ID and position. They are checked as for a new rule (422). A retired rule is a 409 and an unknown ID a 404. Needs the &#x60;manage_rules&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the rule.
val instanceRuleInput : InstanceRuleInput =  // InstanceRuleInput | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AdminRule = apiInstance.updateInstanceRule(id, instanceRuleInput, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#updateInstanceRule")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#updateInstanceRule")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the rule. | |
| **instanceRuleInput** | [**InstanceRuleInput**](InstanceRuleInput.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AdminRule**](AdminRule.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="updateInstanceSettings"></a>
# **updateInstanceSettings**
> AdminSettingList updateInstanceSettings(updateInstanceSettingsRequest, idempotencyKey)

Change instance settings

Sets the settings named in &#x60;values&#x60;, all of them or none: each value is checked against its key and stored in its canonical form (&#x60;TRUE&#x60; is &#x60;true&#x60;, &#x60;ja_jp&#x60; is &#x60;ja-JP&#x60;), and each change is kept as a revision and audited (&#x60;setting_changed&#x60;). Setting the current value again still records a revision, so clients send only the settings they change. The changes take effect on the next request. An unknown key, or a value its key rejects, is a 422 naming the key, and nothing is changed. A value can also depend on the current one: the mode of a decision hook (&#x60;hook_*_mode&#x60;) goes from &#x60;off&#x60; to &#x60;enforce&#x60; only through &#x60;observe&#x60;, so &#x60;enforce&#x60; while it is &#x60;off&#x60; is a 422. The funding meter follows the publication of the costs and the income: &#x60;funding_meter_visibility&#x60; may be &#x60;percent&#x60; only while &#x60;finance_visibility&#x60; and &#x60;income_visibility&#x60; are both &#x60;private&#x60;, and &#x60;amounts&#x60; only while neither is, judged after the whole change, so another value is a 422; a change of either visibility that leaves the stored value invalid turns it &#x60;off&#x60; with a revision of its own. &#x60;funding_meter_target_amount&#x60; is above zero, with no more digits than &#x60;funding_meter_target_currency&#x60; has, which is an ISO 4217 code. Needs the &#x60;manage_settings&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val updateInstanceSettingsRequest : UpdateInstanceSettingsRequest =  // UpdateInstanceSettingsRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AdminSettingList = apiInstance.updateInstanceSettings(updateInstanceSettingsRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#updateInstanceSettings")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#updateInstanceSettings")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **updateInstanceSettingsRequest** | [**UpdateInstanceSettingsRequest**](UpdateInstanceSettingsRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AdminSettingList**](AdminSettingList.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="updateLoginProvider"></a>
# **updateLoginProvider**
> AdminLoginProvider updateLoginProvider(id, updateLoginProviderRequest, idempotencyKey)

Change a login provider

Changes what the request names and keeps the rest; empty &#x60;scopes&#x60;, &#x60;claims&#x60;, or &#x60;organizations&#x60;, or &#x60;guilds&#x60; remove them. A refused change is a 422 &#x60;/api/v1/problems/invalid-login-provider&#x60;. A change is recorded in the audit log. An unknown or deleted ID is a 404. Needs the &#x60;manage_login_providers&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the login provider.
val updateLoginProviderRequest : UpdateLoginProviderRequest =  // UpdateLoginProviderRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AdminLoginProvider = apiInstance.updateLoginProvider(id, updateLoginProviderRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#updateLoginProvider")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#updateLoginProvider")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the login provider. | |
| **updateLoginProviderRequest** | [**UpdateLoginProviderRequest**](UpdateLoginProviderRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AdminLoginProvider**](AdminLoginProvider.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="updateRole"></a>
# **updateRole**
> AdminRole updateRole(role, updateRoleRequest, idempotencyKey)

Change a role

Changes the position, the permissions, the badge, or several of them of a role; the change applies to its holders from their next request, and the audit log records &#x60;role_updated&#x60;. A request that changes nothing records nothing. The key never changes. &#x60;admin&#x60; changes only its badge (a new position or permissions is a 422 &#x60;builtin&#x60;); &#x60;moderator&#x60; takes a new position and permissions like any other role. A change that would break a role that a supporter plan grants is a 409 &#x60;/api/v1/problems/breaks-supporter-roles&#x60;. Removing the badge hides it from every holder&#39;s profile. A role, or a new position, above every role of the viewer is a 403. A position, permissions, or badge that break a rule, or adding or removing a permission that the viewer&#39;s roles do not grant, is a 422 &#x60;/api/v1/problems/invalid-role&#x60; whose &#x60;reason&#x60; says which. An unknown role is a 404. Needs the &#x60;manage_roles&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val role : kotlin.String = role_example // kotlin.String | The key of the role, such as `moderator`.
val updateRoleRequest : UpdateRoleRequest =  // UpdateRoleRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AdminRole = apiInstance.updateRole(role, updateRoleRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#updateRole")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#updateRole")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **role** | **kotlin.String**| The key of the role, such as &#x60;moderator&#x60;. | |
| **updateRoleRequest** | [**UpdateRoleRequest**](UpdateRoleRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AdminRole**](AdminRole.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="updateSupporterPlan"></a>
# **updateSupporterPlan**
> AdminSupporterPlan updateSupporterPlan(id, adminSupporterPlanUpdateRequest, idempotencyKey)

Change a supporter plan

Changes a supporter plan&#39;s terms and its role&#39;s permissions and position. New prices apply to new subscriptions and changes of plan only. A change that breaks a rule is a 422 &#x60;/api/v1/problems/invalid-supporter-plan&#x60;, a retired plan a 409 &#x60;/api/v1/problems/supporter-plan-retired&#x60;, and an unknown plan a 404. Recorded in the audit log. Needs the &#x60;manage_supporters&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the supporter plan.
val adminSupporterPlanUpdateRequest : AdminSupporterPlanUpdateRequest =  // AdminSupporterPlanUpdateRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AdminSupporterPlan = apiInstance.updateSupporterPlan(id, adminSupporterPlanUpdateRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#updateSupporterPlan")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#updateSupporterPlan")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the supporter plan. | |
| **adminSupporterPlanUpdateRequest** | [**AdminSupporterPlanUpdateRequest**](AdminSupporterPlanUpdateRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AdminSupporterPlan**](AdminSupporterPlan.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="updateWebhook"></a>
# **updateWebhook**
> AdminWebhook updateWebhook(id, updateWebhookRequest, idempotencyKey)

Change a webhook

Changes what the request names and keeps the rest; an empty &#x60;description&#x60; removes it. The events need the permissions as for a new webhook, and a refused change is a 422 &#x60;/api/v1/problems/invalid-webhook&#x60;. A change is recorded in the audit log. An unknown or deleted ID is a 404. Needs the &#x60;manage_webhooks&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the webhook.
val updateWebhookRequest : UpdateWebhookRequest =  // UpdateWebhookRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AdminWebhook = apiInstance.updateWebhook(id, updateWebhookRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#updateWebhook")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#updateWebhook")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the webhook. | |
| **updateWebhookRequest** | [**UpdateWebhookRequest**](UpdateWebhookRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AdminWebhook**](AdminWebhook.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="withdrawAdminAnnouncement"></a>
# **withdrawAdminAnnouncement**
> AdminAnnouncement withdrawAdminAnnouncement(id, idempotencyKey)

Withdraw an announcement

Withdraws an announcement for good: no user sees it again, and it can no longer change or be published. Withdrawing a withdrawn announcement changes nothing. An unknown ID is a 404. Recorded in the audit log. Needs the &#x60;manage_announcements&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the announcement.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AdminAnnouncement = apiInstance.withdrawAdminAnnouncement(id, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#withdrawAdminAnnouncement")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#withdrawAdminAnnouncement")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the announcement. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AdminAnnouncement**](AdminAnnouncement.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="withdrawServiceClosure"></a>
# **withdrawServiceClosure**
> AdminServiceClosure withdrawServiceClosure(idempotencyKey)

Withdraw the announcement of the closure

Withdraws the announcement in effect: the server runs on as before, and the audit log records &#x60;service_closure_withdrawn&#x60;. Without an announcement in effect, or once the server is closed, it is the 409 &#x60;/api/v1/problems/service-closure-state&#x60;. Needs the &#x60;manage_service_closure&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminServerApi()
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AdminServiceClosure = apiInstance.withdrawServiceClosure(idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminServerApi#withdrawServiceClosure")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminServerApi#withdrawServiceClosure")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AdminServiceClosure**](AdminServiceClosure.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

