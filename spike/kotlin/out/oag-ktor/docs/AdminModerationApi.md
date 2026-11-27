# AdminModerationApi

All URIs are relative to */api/v1*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**acceptDomainListDrafts**](AdminModerationApi.md#acceptDomainListDrafts) | **POST** /admin/domain_list_subscriptions/{id}/drafts/accept | Accept drafts of a subscription |
| [**addReportNote**](AdminModerationApi.md#addReportNote) | **POST** /admin/reports/{id}/notes | Write a note on a report |
| [**approveRegistration**](AdminModerationApi.md#approveRegistration) | **POST** /admin/registrations/{id}/approve | Approve a registration |
| [**assignReportToSelf**](AdminModerationApi.md#assignReportToSelf) | **POST** /admin/reports/{id}/assign_to_self | Take a report |
| [**cancelEventAsModerator**](AdminModerationApi.md#cancelEventAsModerator) | **POST** /admin/events/{id}/cancel | Cancel an event as a moderator |
| [**createAccountAction**](AdminModerationApi.md#createAccountAction) | **POST** /admin/accounts/{id}/actions | Take a moderation action on an account |
| [**createDomainAction**](AdminModerationApi.md#createDomainAction) | **POST** /admin/domain_actions | Take a moderation action on a domain |
| [**createDomainAllow**](AdminModerationApi.md#createDomainAllow) | **POST** /admin/domain_allows | Allow a domain to federate |
| [**createDomainListSubscription**](AdminModerationApi.md#createDomainListSubscription) | **POST** /admin/domain_list_subscriptions | Subscribe to a domain block list |
| [**createInvite**](AdminModerationApi.md#createInvite) | **POST** /admin/invites | Create an invite |
| [**createSignupBlock**](AdminModerationApi.md#createSignupBlock) | **POST** /admin/signup_blocks | Block sign-ups |
| [**deleteDomainListSubscription**](AdminModerationApi.md#deleteDomainListSubscription) | **DELETE** /admin/domain_list_subscriptions/{id} | Delete a subscription to a domain block list |
| [**endVoiceRoomAsModerator**](AdminModerationApi.md#endVoiceRoomAsModerator) | **POST** /admin/voice_rooms/{id}/end | End a voice chat room as a moderator |
| [**exportDomainBlocks**](AdminModerationApi.md#exportDomainBlocks) | **GET** /admin/domain_actions/export | Export the domain actions as Mastodon&#39;s domain block CSV |
| [**fetchDomainListSubscription**](AdminModerationApi.md#fetchDomainListSubscription) | **POST** /admin/domain_list_subscriptions/{id}/fetch | Fetch a subscribed list now |
| [**forwardReport**](AdminModerationApi.md#forwardReport) | **POST** /admin/reports/{id}/forward | Forward a report to the target&#39;s server |
| [**getAccountAction**](AdminModerationApi.md#getAccountAction) | **GET** /admin/account_actions/{id} | Get a moderation action on an account |
| [**getAdminAccount**](AdminModerationApi.md#getAdminAccount) | **GET** /admin/accounts/{id} | Get the details of an account |
| [**getAdminCounts**](AdminModerationApi.md#getAdminCounts) | **GET** /admin/counts | Count what awaits the staff |
| [**getAppeal**](AdminModerationApi.md#getAppeal) | **GET** /admin/appeals/{id} | Get an appeal of a moderation action |
| [**getDomainAction**](AdminModerationApi.md#getDomainAction) | **GET** /admin/domain_actions/{id} | Get a moderation action on a domain |
| [**getDomainListSubscription**](AdminModerationApi.md#getDomainListSubscription) | **GET** /admin/domain_list_subscriptions/{id} | Get a subscription to a domain block list |
| [**getDomainSuspendImpact**](AdminModerationApi.md#getDomainSuspendImpact) | **GET** /admin/domain_actions/impact | Count what suspending a domain would end |
| [**getHookAppeal**](AdminModerationApi.md#getHookAppeal) | **GET** /admin/hook_appeals/{id} | Get an appeal of a refused post |
| [**getHookDecision**](AdminModerationApi.md#getHookDecision) | **GET** /admin/hook_decisions/{id} | Get a decision |
| [**getHookSummary**](AdminModerationApi.md#getHookSummary) | **GET** /admin/hook_decisions/summary | Summarize the decision hooks |
| [**getInstanceHealth**](AdminModerationApi.md#getInstanceHealth) | **GET** /admin/instances/{domain} | Get the health of a remote server |
| [**getReport**](AdminModerationApi.md#getReport) | **GET** /admin/reports/{id} | Get a report |
| [**getSignupBlock**](AdminModerationApi.md#getSignupBlock) | **GET** /admin/signup_blocks/{id} | Get a sign-up block |
| [**importDomainBlocks**](AdminModerationApi.md#importDomainBlocks) | **POST** /admin/domain_actions/import | Import the chosen rows of a Mastodon domain block CSV |
| [**importSignupBlocks**](AdminModerationApi.md#importSignupBlocks) | **POST** /admin/signup_blocks/import | Block sign-ups from a list |
| [**liftAccountAction**](AdminModerationApi.md#liftAccountAction) | **POST** /admin/account_actions/{id}/lift | Lift a moderation action on an account |
| [**liftAdminAccountEmailSuppression**](AdminModerationApi.md#liftAdminAccountEmailSuppression) | **POST** /admin/accounts/{id}/email_suppression/lift | Lift the suppression of the email address of a local account |
| [**liftDomainAction**](AdminModerationApi.md#liftDomainAction) | **POST** /admin/domain_actions/{id}/lift | Lift a moderation action on a domain |
| [**liftSignupBlock**](AdminModerationApi.md#liftSignupBlock) | **POST** /admin/signup_blocks/{id}/lift | Lift a sign-up block |
| [**listAccountActions**](AdminModerationApi.md#listAccountActions) | **GET** /admin/accounts/{id}/actions | List the moderation actions on an account |
| [**listAdminAccountSameIPAccounts**](AdminModerationApi.md#listAdminAccountSameIPAccounts) | **GET** /admin/accounts/{id}/same_ip_accounts | List the other accounts that used an IP address of a local account |
| [**listAdminAccountSignIns**](AdminModerationApi.md#listAdminAccountSignIns) | **GET** /admin/accounts/{id}/sign_ins | List where the sign-ins of a local account came from |
| [**listAdminAccounts**](AdminModerationApi.md#listAdminAccounts) | **GET** /admin/accounts | Search the accounts |
| [**listAppeals**](AdminModerationApi.md#listAppeals) | **GET** /admin/appeals | List the appeals of moderation actions |
| [**listAuditLog**](AdminModerationApi.md#listAuditLog) | **GET** /admin/audit_log | List the audit log |
| [**listDomainActions**](AdminModerationApi.md#listDomainActions) | **GET** /admin/domain_actions | List the moderation actions on domains |
| [**listDomainAllows**](AdminModerationApi.md#listDomainAllows) | **GET** /admin/domain_allows | List the allowlist of federation |
| [**listDomainListDrafts**](AdminModerationApi.md#listDomainListDrafts) | **GET** /admin/domain_list_subscriptions/{id}/drafts | List the drafts of a subscription |
| [**listDomainListFetches**](AdminModerationApi.md#listDomainListFetches) | **GET** /admin/domain_list_subscriptions/{id}/fetches | List the fetches of a subscription |
| [**listDomainListSubscriptions**](AdminModerationApi.md#listDomainListSubscriptions) | **GET** /admin/domain_list_subscriptions | List the subscriptions to domain block lists |
| [**listHookAppeals**](AdminModerationApi.md#listHookAppeals) | **GET** /admin/hook_appeals | List the appeals of refused posts |
| [**listHookDecisions**](AdminModerationApi.md#listHookDecisions) | **GET** /admin/hook_decisions | List the decisions of the decision hooks |
| [**listInstanceAvailabilityEvents**](AdminModerationApi.md#listInstanceAvailabilityEvents) | **GET** /admin/instances/{domain}/availability_events | List the times a remote server was marked unavailable or available |
| [**listInstanceDeliveries**](AdminModerationApi.md#listInstanceDeliveries) | **GET** /admin/instances/{domain}/deliveries | List the deliveries to a remote server and their attempts |
| [**listInstanceHealth**](AdminModerationApi.md#listInstanceHealth) | **GET** /admin/instances | List the known remote servers with their health |
| [**listInvites**](AdminModerationApi.md#listInvites) | **GET** /admin/invites | List the invites |
| [**listPendingRegistrations**](AdminModerationApi.md#listPendingRegistrations) | **GET** /admin/registrations | List the registrations that await approval |
| [**listReports**](AdminModerationApi.md#listReports) | **GET** /admin/reports | List the reports |
| [**listSignupBlocks**](AdminModerationApi.md#listSignupBlocks) | **GET** /admin/signup_blocks | List the sign-up blocks |
| [**markInstanceAvailable**](AdminModerationApi.md#markInstanceAvailable) | **POST** /admin/instances/{domain}/mark_available | Mark a remote server available |
| [**overrideHookDecision**](AdminModerationApi.md#overrideHookDecision) | **POST** /admin/hook_decisions/{id}/override | Cancel a decision |
| [**overturnAppeal**](AdminModerationApi.md#overturnAppeal) | **POST** /admin/appeals/{id}/overturn | Overturn an appeal, lifting its action |
| [**overturnHookAppeal**](AdminModerationApi.md#overturnHookAppeal) | **POST** /admin/hook_appeals/{id}/overturn | Overturn an appeal of a refused post |
| [**previewDomainBlockImport**](AdminModerationApi.md#previewDomainBlockImport) | **POST** /admin/domain_actions/import/preview | Preview the import of a Mastodon domain block CSV |
| [**refetchAccountStatuses**](AdminModerationApi.md#refetchAccountStatuses) | **POST** /admin/accounts/{id}/refetch_statuses | Refetch the posts of a remote account from its server |
| [**refetchInstanceStatuses**](AdminModerationApi.md#refetchInstanceStatuses) | **POST** /admin/instances/{domain}/refetch_statuses | Refetch the posts of the accounts of a remote server |
| [**refetchStatus**](AdminModerationApi.md#refetchStatus) | **POST** /admin/statuses/{id}/refetch | Refetch a remote post from its origin |
| [**rejectDomainListDrafts**](AdminModerationApi.md#rejectDomainListDrafts) | **POST** /admin/domain_list_subscriptions/{id}/drafts/reject | Reject drafts of a subscription |
| [**rejectRegistration**](AdminModerationApi.md#rejectRegistration) | **POST** /admin/registrations/{id}/reject | Reject a registration |
| [**removeDomainAllow**](AdminModerationApi.md#removeDomainAllow) | **DELETE** /admin/domain_allows/{domain} | Remove a domain from the allowlist of federation |
| [**reopenReport**](AdminModerationApi.md#reopenReport) | **POST** /admin/reports/{id}/reopen | Reopen a resolved report |
| [**resolveReport**](AdminModerationApi.md#resolveReport) | **POST** /admin/reports/{id}/resolve | Resolve a report |
| [**revealAdminAccount**](AdminModerationApi.md#revealAdminAccount) | **POST** /admin/accounts/{id}/reveal | Show the email address and the IP addresses of a local account |
| [**revealInstanceDelivery**](AdminModerationApi.md#revealInstanceDelivery) | **POST** /admin/instances/{domain}/deliveries/{id}/reveal | Show the sender, the activity URI, and the inbox of a delivery |
| [**revokeInvite**](AdminModerationApi.md#revokeInvite) | **POST** /admin/invites/{id}/revoke | Revoke an invite |
| [**translateReportComment**](AdminModerationApi.md#translateReportComment) | **POST** /admin/reports/{id}/comment/translation | Translate the comment of a report into a language |
| [**translateReportedStatus**](AdminModerationApi.md#translateReportedStatus) | **POST** /admin/reports/{id}/statuses/{status_id}/translation | Translate a reported post into a language |
| [**unassignReport**](AdminModerationApi.md#unassignReport) | **POST** /admin/reports/{id}/unassign | Leave a report to nobody |
| [**updateDomainListSubscription**](AdminModerationApi.md#updateDomainListSubscription) | **PATCH** /admin/domain_list_subscriptions/{id} | Change the settings of a subscription |
| [**upholdAppeal**](AdminModerationApi.md#upholdAppeal) | **POST** /admin/appeals/{id}/uphold | Uphold an appeal, keeping its action |
| [**upholdHookAppeal**](AdminModerationApi.md#upholdHookAppeal) | **POST** /admin/hook_appeals/{id}/uphold | Uphold an appeal of a refused post |


<a id="acceptDomainListDrafts"></a>
# **acceptDomainListDrafts**
> AdminDomainListDraftResult acceptDomainListDrafts(id, idempotencyKey, domainListDraftsRequest)

Accept drafts of a subscription

Issues the action of each &#x60;issue&#x60; draft for the subscription, until it is lifted, and lifts the action of each &#x60;lift&#x60; draft. Each acceptance is audited, besides the action. A draft that an action in effect covers by then issues nothing. An unknown or deleted ID is a 404. Needs the &#x60;manage_domains&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the subscription to a domain block list.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
val domainListDraftsRequest : DomainListDraftsRequest =  // DomainListDraftsRequest | 
try {
    val result : AdminDomainListDraftResult = apiInstance.acceptDomainListDrafts(id, idempotencyKey, domainListDraftsRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#acceptDomainListDrafts")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#acceptDomainListDrafts")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the subscription to a domain block list. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |
| **domainListDraftsRequest** | [**DomainListDraftsRequest**](DomainListDraftsRequest.md)|  | [optional] |

### Return type

[**AdminDomainListDraftResult**](AdminDomainListDraftResult.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="addReportNote"></a>
# **addReportNote**
> AdminReportDetail addReportNote(id, createReportNoteRequest, idempotencyKey)

Write a note on a report

Adds an internal note to the report. Notes are shown to the moderators only, never to the target or the reporter, and cannot be edited or removed. An unknown ID is a 404. Needs the &#x60;manage_reports&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the report.
val createReportNoteRequest : CreateReportNoteRequest =  // CreateReportNoteRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AdminReportDetail = apiInstance.addReportNote(id, createReportNoteRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#addReportNote")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#addReportNote")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the report. | |
| **createReportNoteRequest** | [**CreateReportNoteRequest**](CreateReportNoteRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AdminReportDetail**](AdminReportDetail.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="approveRegistration"></a>
# **approveRegistration**
> AdminRegistration approveRegistration(id, idempotencyKey, registrationDecisionRequest)

Approve a registration

Approves a pending registration, so that its user can sign in, with an optional note for the moderators; the decision is audited. Deciding a decided registration changes nothing and keeps its decision. The answer on a decided registration has no email address: only the list of pending registrations shows it, and &#x60;POST /admin/accounts/{id}/reveal&#x60; after. An unknown ID is a 404. From the announcement of the closure of the service on, a pending registration is not approved: a 409 &#x60;/api/v1/problems/service-closing&#x60; (the announcement rejects the pending ones). Needs the &#x60;manage_accounts&#x60; permission.  On a one-person server (&#x60;Instance.single_user&#x60;) it is a 404 of the type &#x60;/api/v1/problems/single-user-mode&#x60;. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the registration.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
val registrationDecisionRequest : RegistrationDecisionRequest =  // RegistrationDecisionRequest | 
try {
    val result : AdminRegistration = apiInstance.approveRegistration(id, idempotencyKey, registrationDecisionRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#approveRegistration")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#approveRegistration")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the registration. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |
| **registrationDecisionRequest** | [**RegistrationDecisionRequest**](RegistrationDecisionRequest.md)|  | [optional] |

### Return type

[**AdminRegistration**](AdminRegistration.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="assignReportToSelf"></a>
# **assignReportToSelf**
> AdminReportDetail assignReportToSelf(id, idempotencyKey)

Take a report

Makes the authenticated user the moderator who handles the report. An unknown ID is a 404. Needs the &#x60;manage_reports&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the report.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AdminReportDetail = apiInstance.assignReportToSelf(id, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#assignReportToSelf")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#assignReportToSelf")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the report. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AdminReportDetail**](AdminReportDetail.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="cancelEventAsModerator"></a>
# **cancelEventAsModerator**
> cancelEventAsModerator(id, idempotencyKey, adminEventCancel)

Cancel an event as a moderator

Cancels an event with an optional reason, which the event shows with its cancellation, and notifies the users who answered going or maybe and the invited users. Recorded in the audit log (&#x60;event_canceled&#x60;). An unknown event is a 404, an event canceled already a 409 &#x60;/api/v1/problems/event-closed&#x60;, and a reason longer than 500 characters a 422. Needs the &#x60;manage_reports&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the event.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
val adminEventCancel : AdminEventCancel =  // AdminEventCancel | 
try {
    apiInstance.cancelEventAsModerator(id, idempotencyKey, adminEventCancel)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#cancelEventAsModerator")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#cancelEventAsModerator")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the event. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |
| **adminEventCancel** | [**AdminEventCancel**](AdminEventCancel.md)|  | [optional] |

### Return type

null (empty response body)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/problem+json

<a id="createAccountAction"></a>
# **createAccountAction**
> AdminAccountAction createAccountAction(id, createAccountActionRequest, idempotencyKey)

Take a moderation action on an account

Records an action on an account, in effect at once and until it expires or is lifted; several actions on one account add up. On a local account: &#x60;warn&#x60; only tells the account; &#x60;force_sensitive&#x60; shows its media as sensitive; &#x60;limit&#x60; makes its posts, past ones included, followers-only and keeps them off the public timelines; &#x60;restrict_posting&#x60; stops it posting, replying, boosting, quoting, and editing; &#x60;restrict_interaction&#x60; stops it following, favouriting, boosting, and voting; &#x60;disable_login&#x60; ends its sessions and tokens and stops it logging in; &#x60;suspend&#x60; does all of these and hides its profile and posts from everyone else; &#x60;ban&#x60; is a &#x60;suspend&#x60; that is never lifted and deletes the account 30 days later. &#x60;delete_statuses&#x60; deletes the posts of the account that &#x60;status_ids&#x60; names, for everyone, the account included, and sends their deletion like the account&#39;s own; &#x60;mark_statuses_sensitive&#x60; shows their media as sensitive until it is lifted. A remote account takes &#x60;limit&#x60;, &#x60;suspend&#x60;, and &#x60;force_sensitive&#x60;, with the same effects here: a &#x60;suspend&#x60; also drops what the account sends, and its lift brings the profile and posts back. An action on a remote account is not sent to its server, and &#x60;notify_target&#x60; does not apply to it. Another kind, the instance actor, or a &#x60;report_id&#x60; that is not a report of the account is a 422 naming &#x60;kind&#x60;, &#x60;id&#x60;, or &#x60;report_id&#x60;. &#x60;status_ids&#x60; on another kind, missing, repeated, or too many on an action on posts, or naming a post that does not exist, is deleted, or is of another account is a 422 naming &#x60;status_ids&#x60;; &#x60;expires_in&#x60; on a &#x60;ban&#x60; or an action on posts is a 422 naming &#x60;expires_in&#x60;. A text that is too long or contains U+0000 is a 422 naming it. An unknown account is a 404. A local account whose roles do not rank below the user&#39;s, the user&#39;s own included, is a 403 &#x60;/api/v1/problems/target-outranks-actor&#x60;. A &#x60;disable_login&#x60;, &#x60;suspend&#x60;, or &#x60;ban&#x60; that would lock out the only administrator is a 409 &#x60;/api/v1/problems/last-administrator&#x60;. Needs the &#x60;manage_accounts&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the account, local or remote.
val createAccountActionRequest : CreateAccountActionRequest =  // CreateAccountActionRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AdminAccountAction = apiInstance.createAccountAction(id, createAccountActionRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#createAccountAction")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#createAccountAction")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the account, local or remote. | |
| **createAccountActionRequest** | [**CreateAccountActionRequest**](CreateAccountActionRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AdminAccountAction**](AdminAccountAction.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="createDomainAction"></a>
# **createDomainAction**
> AdminDomainAction createDomainAction(createDomainActionRequest, idempotencyKey)

Take a moderation action on a domain

Records an action on a remote domain and its subdomains, in effect at once and until it expires or is lifted. A &#x60;suspend&#x60; stops every delivery to the domain, every fetch from it, and every activity received from it, and hides its accounts and posts from the timelines; &#x60;limit&#x60; keeps its posts off the public timelines and its accounts from reaching users who do not follow them; &#x60;reject_media&#x60; stops fetching its media; &#x60;reject_reports&#x60; drops the reports it sends; &#x60;force_sensitive&#x60; marks its media sensitive. The domain is normalized (IDNA A-labels in lower case, without a trailing dot); one that is not a valid domain, or this server&#39;s own, is a 422 naming &#x60;domain&#x60;. A &#x60;public_comment&#x60; or &#x60;private_note&#x60; that is too long or contains U+0000 is a 422 naming it. Needs the &#x60;manage_domains&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val createDomainActionRequest : CreateDomainActionRequest =  // CreateDomainActionRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AdminDomainAction = apiInstance.createDomainAction(createDomainActionRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#createDomainAction")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#createDomainAction")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **createDomainActionRequest** | [**CreateDomainActionRequest**](CreateDomainActionRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AdminDomainAction**](AdminDomainAction.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="createDomainAllow"></a>
# **createDomainAllow**
> DomainAllow createDomainAllow(createDomainAllowRequest, idempotencyKey)

Allow a domain to federate

Adds a domain, and its subdomains, to the allowlist and records it in the audit log. While the instance setting &#x60;federation_scope&#x60; is &#x60;allowlist&#x60;, the domain federates at once; the follows that the allowlist ended do not come back. A domain already allowed keeps its allow and its comment, and answers 200 instead of 201. A domain that is not a host is a 400; this instance&#39;s own domain, a comment longer than 5,000 characters, or an allow beyond 5,000 is a 422 naming the property (&#x60;domain&#x60;, &#x60;comment&#x60;, &#x60;domains&#x60;). Needs the &#x60;manage_domains&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val createDomainAllowRequest : CreateDomainAllowRequest =  // CreateDomainAllowRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : DomainAllow = apiInstance.createDomainAllow(createDomainAllowRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#createDomainAllow")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#createDomainAllow")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **createDomainAllowRequest** | [**CreateDomainAllowRequest**](CreateDomainAllowRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**DomainAllow**](DomainAllow.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="createDomainListSubscription"></a>
# **createDomainListSubscription**
> AdminDomainListSubscription createDomainListSubscription(createDomainListSubscriptionRequest, idempotencyKey)

Subscribe to a domain block list

Subscribes to the list at an https URL, which the server fetches at once and then every day, through the same guard as the fetches of other servers: a URL or a redirect to a private address fails the fetch. A fetch reads at most 2 MiB and 5000 rows. In the &#x60;draft&#x60; mode, what the list adds, changes, and removes waits for a moderator; in the &#x60;automatic&#x60; mode, it is applied at each fetch. A subscription never lifts nor replaces an action that it did not issue. A URL that is not https, or an invalid setting, is a 422. The subscription is audited. Needs the &#x60;manage_domains&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val createDomainListSubscriptionRequest : CreateDomainListSubscriptionRequest =  // CreateDomainListSubscriptionRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AdminDomainListSubscription = apiInstance.createDomainListSubscription(createDomainListSubscriptionRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#createDomainListSubscription")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#createDomainListSubscription")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **createDomainListSubscriptionRequest** | [**CreateDomainListSubscriptionRequest**](CreateDomainListSubscriptionRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AdminDomainListSubscription**](AdminDomainListSubscription.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="createInvite"></a>
# **createInvite**
> AdminInvite createInvite(idempotencyKey, createInviteRequest)

Create an invite

Creates an invite of the staff with a new random code; the caps of the members&#39; invites do not apply to it. People sign up with it while the registration mode is &#x60;open&#x60;, &#x60;approval&#x60; (approved at once), or &#x60;invite&#x60;; while the mode is &#x60;closed&#x60;, creating one is a 409 &#x60;/api/v1/problems/registration-closed&#x60;. &#x60;max_uses&#x60; outside 1 to 1,000,000 or &#x60;expires_in&#x60; outside 1 second to 10 years is a 422 naming it. Needs the &#x60;manage_invites&#x60; permission.  On a one-person server (&#x60;Instance.single_user&#x60;) it is a 404 of the type &#x60;/api/v1/problems/single-user-mode&#x60;. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
val createInviteRequest : CreateInviteRequest =  // CreateInviteRequest | 
try {
    val result : AdminInvite = apiInstance.createInvite(idempotencyKey, createInviteRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#createInvite")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#createInvite")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |
| **createInviteRequest** | [**CreateInviteRequest**](CreateInviteRequest.md)|  | [optional] |

### Return type

[**AdminInvite**](AdminInvite.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="createSignupBlock"></a>
# **createSignupBlock**
> AdminSignupBlock createSignupBlock(createSignupBlockRequest, idempotencyKey)

Block sign-ups

Records a block on sign-ups from the registration page, in effect at once and until it expires or is lifted; the block is audited. A sign-up whose email address or client address it matches is refused (&#x60;reject&#x60;) or starts pending for a moderator whatever the registration mode (&#x60;require_approval&#x60;); the page does not tell the applicant why. The value is stored in its canonical form: an &#x60;email_domain&#x60; in IDNA A-labels (it covers the subdomains), an &#x60;email_hash&#x60; as the hash of the canonical address (an email address given is hashed and not kept), an &#x60;ip_range&#x60; as a masked CIDR prefix (a single address is a prefix of its full length). A value that is not valid for its kind, or a &#x60;comment&#x60; that is too long or contains U+0000, is a 422. Needs the &#x60;manage_accounts&#x60; permission.  On a one-person server (&#x60;Instance.single_user&#x60;) it is a 404 of the type &#x60;/api/v1/problems/single-user-mode&#x60;. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val createSignupBlockRequest : CreateSignupBlockRequest =  // CreateSignupBlockRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AdminSignupBlock = apiInstance.createSignupBlock(createSignupBlockRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#createSignupBlock")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#createSignupBlock")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **createSignupBlockRequest** | [**CreateSignupBlockRequest**](CreateSignupBlockRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AdminSignupBlock**](AdminSignupBlock.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="deleteDomainListSubscription"></a>
# **deleteDomainListSubscription**
> deleteDomainListSubscription(id, liftActions, idempotencyKey)

Delete a subscription to a domain block list

Deletes the subscription: the list is no longer fetched. The actions it issued stay, as if a moderator had issued them, unless &#x60;lift_actions&#x60; is set, which lifts them first. The deletion is audited. An unknown or deleted ID is a 404. Needs the &#x60;manage_domains&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the subscription to a domain block list.
val liftActions : kotlin.Boolean = true // kotlin.Boolean | Lift the actions the subscription issued.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    apiInstance.deleteDomainListSubscription(id, liftActions, idempotencyKey)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#deleteDomainListSubscription")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#deleteDomainListSubscription")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the subscription to a domain block list. | |
| **liftActions** | **kotlin.Boolean**| Lift the actions the subscription issued. | [optional] |
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

<a id="endVoiceRoomAsModerator"></a>
# **endVoiceRoomAsModerator**
> endVoiceRoomAsModerator(id, idempotencyKey, adminVoiceRoomEnd)

End a voice chat room as a moderator

Ends an open voice chat room for every participant, with an optional reason for the moderators. Recorded in the audit log (&#x60;voice_room_ended&#x60;). Works while the voice chat is off too. An unknown or ended room is a 404, and a reason longer than 500 characters a 422. Needs the &#x60;manage_reports&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the voice chat room.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
val adminVoiceRoomEnd : AdminVoiceRoomEnd =  // AdminVoiceRoomEnd | 
try {
    apiInstance.endVoiceRoomAsModerator(id, idempotencyKey, adminVoiceRoomEnd)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#endVoiceRoomAsModerator")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#endVoiceRoomAsModerator")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the voice chat room. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |
| **adminVoiceRoomEnd** | [**AdminVoiceRoomEnd**](AdminVoiceRoomEnd.md)|  | [optional] |

### Return type

null (empty response body)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/problem+json

<a id="exportDomainBlocks"></a>
# **exportDomainBlocks**
> java.io.File exportDomainBlocks()

Export the domain actions as Mastodon&#39;s domain block CSV

The domain actions in effect as the CSV that Mastodon&#39;s moderation screens import, with the header &#x60;#domain,#severity,#reject_media,#reject_reports,#public_comment,#obfuscate&#x60;: one row per domain in domain order. &#x60;#severity&#x60; is &#x60;suspend&#x60; when a &#x60;suspend&#x60; is in effect on the domain, &#x60;silence&#x60; for a &#x60;limit&#x60;, and &#x60;noop&#x60; otherwise; &#x60;#reject_media&#x60;, &#x60;#reject_reports&#x60;, and &#x60;#obfuscate&#x60; are &#x60;true&#x60; when one of the domain&#39;s actions has them. A domain whose only actions are &#x60;force_sensitive&#x60;, which Mastodon&#39;s domain blocks cannot hold, is left out. Private notes and expiry dates are not exported. Needs the &#x60;manage_domains&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
try {
    val result : java.io.File = apiInstance.exportDomainBlocks()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#exportDomainBlocks")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#exportDomainBlocks")
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
 - **Accept**: text/csv, application/problem+json

<a id="fetchDomainListSubscription"></a>
# **fetchDomainListSubscription**
> fetchDomainListSubscription(id, idempotencyKey)

Fetch a subscribed list now

Queues a fetch of the list, which then applies it as the daily fetch does. The request is audited. An unknown or deleted ID is a 404. Needs the &#x60;manage_domains&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the subscription to a domain block list.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    apiInstance.fetchDomainListSubscription(id, idempotencyKey)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#fetchDomainListSubscription")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#fetchDomainListSubscription")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the subscription to a domain block list. | |
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

<a id="forwardReport"></a>
# **forwardReport**
> AdminReportDetail forwardReport(id, idempotencyKey, forwardReportRequest)

Forward a report to the target&#39;s server

Sends the report to the server of its remote target as a &#x60;Flag&#x60; of the instance actor, with the account and the posts it points at, and &#x60;comment&#x60; as its text. Nothing of the reporter is sent: not their account, and not their comment, nor the note of an automated review that filed it. Works on an open or a resolved report that &#x60;forwardable&#x60; allows: one filed on this server (by a person or by an automated review) about a remote account, of no voice chat room. A report forwarded already, when it was filed or later, changes nothing, whatever &#x60;comment&#x60; says. A report that cannot be forwarded is a 409 &#x60;/api/v1/problems/report-not-forwardable&#x60;, an unknown ID a 404, and a &#x60;comment&#x60; that is too long or contains U+0000 a 422 naming it. Needs the &#x60;manage_reports&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the report.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
val forwardReportRequest : ForwardReportRequest =  // ForwardReportRequest | 
try {
    val result : AdminReportDetail = apiInstance.forwardReport(id, idempotencyKey, forwardReportRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#forwardReport")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#forwardReport")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the report. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |
| **forwardReportRequest** | [**ForwardReportRequest**](ForwardReportRequest.md)|  | [optional] |

### Return type

[**AdminReportDetail**](AdminReportDetail.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="getAccountAction"></a>
# **getAccountAction**
> AdminAccountAction getAccountAction(id)

Get a moderation action on an account

An unknown ID is a 404. Needs the &#x60;manage_accounts&#x60; permission.

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the account action.
try {
    val result : AdminAccountAction = apiInstance.getAccountAction(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#getAccountAction")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#getAccountAction")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the account action. | |

### Return type

[**AdminAccountAction**](AdminAccountAction.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="getAdminAccount"></a>
# **getAdminAccount**
> AdminAccountDetail getAdminAccount(id)

Get the details of an account

An account as the moderators see it: its state, the restrictions of its own actions, and, for a local account, its registration, whether its email address is verified, and its roles, with its newest 20 actions and, for a viewer who also has the &#x60;manage_reports&#x60; permission, its newest 20 reports (&#x60;GET /admin/reports?target_account_id&#x3D;&#x60; pages through them all). The email address and the IP addresses are not included; &#x60;POST .../reveal&#x60; shows them. An unknown account is a 404. Needs the &#x60;manage_accounts&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the account, local or remote.
try {
    val result : AdminAccountDetail = apiInstance.getAdminAccount(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#getAdminAccount")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#getAdminAccount")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the account, local or remote. | |

### Return type

[**AdminAccountDetail**](AdminAccountDetail.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="getAdminCounts"></a>
# **getAdminCounts**
> AdminCounts getAdminCounts()

Count what awaits the staff

The number of open reports (with the &#x60;manage_reports&#x60; permission), of registrations that await approval and appeals that await a decision (with the &#x60;manage_accounts&#x60; permission), of custom emoji requests that await a decision (with &#x60;manage_custom_emojis&#x60;), and of open error groups (with &#x60;manage_errors&#x60;), for the badges of the staff navigation. A count whose permission the user&#39;s roles lack is omitted, so a user without any gets an empty object. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
try {
    val result : AdminCounts = apiInstance.getAdminCounts()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#getAdminCounts")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#getAdminCounts")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**AdminCounts**](AdminCounts.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="getAppeal"></a>
# **getAppeal**
> AdminAppeal getAppeal(id)

Get an appeal of a moderation action

An unknown ID is a 404. Needs the &#x60;manage_accounts&#x60; permission.

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the appeal.
try {
    val result : AdminAppeal = apiInstance.getAppeal(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#getAppeal")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#getAppeal")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the appeal. | |

### Return type

[**AdminAppeal**](AdminAppeal.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="getDomainAction"></a>
# **getDomainAction**
> AdminDomainAction getDomainAction(id)

Get a moderation action on a domain

An unknown ID is a 404. Needs the &#x60;manage_domains&#x60; permission.

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the domain action.
try {
    val result : AdminDomainAction = apiInstance.getDomainAction(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#getDomainAction")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#getDomainAction")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the domain action. | |

### Return type

[**AdminDomainAction**](AdminDomainAction.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="getDomainListSubscription"></a>
# **getDomainListSubscription**
> AdminDomainListSubscription getDomainListSubscription(id)

Get a subscription to a domain block list

An unknown or deleted ID is a 404. Needs the &#x60;manage_domains&#x60; permission.

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the subscription to a domain block list.
try {
    val result : AdminDomainListSubscription = apiInstance.getDomainListSubscription(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#getDomainListSubscription")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#getDomainListSubscription")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the subscription to a domain block list. | |

### Return type

[**AdminDomainListSubscription**](AdminDomainListSubscription.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="getDomainSuspendImpact"></a>
# **getDomainSuspendImpact**
> DomainSuspendImpact getDomainSuspendImpact(domain)

Count what suspending a domain would end

How many follows a &#x60;suspend&#x60; of &#x60;domain&#x60; would end now, and how many local accounts would lose one. Moderators read it before they confirm a suspension, which ends those follows for good. The domain is normalized like the domain of a new action; one that is not a valid domain, or this server&#39;s own, is a 422 naming &#x60;domain&#x60;. A domain with no follows counts zeros. Needs the &#x60;manage_domains&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val domain : kotlin.String = domain_example // kotlin.String | The domain to count for; its subdomains are counted with it.
try {
    val result : DomainSuspendImpact = apiInstance.getDomainSuspendImpact(domain)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#getDomainSuspendImpact")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#getDomainSuspendImpact")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **domain** | **kotlin.String**| The domain to count for; its subdomains are counted with it. | |

### Return type

[**DomainSuspendImpact**](DomainSuspendImpact.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="getHookAppeal"></a>
# **getHookAppeal**
> AdminHookAppeal getHookAppeal(id)

Get an appeal of a refused post

One author&#39;s appeal of a &#x60;reject&#x60; of &#x60;local.status&#x60;, with its decision and the text the author tried to post. An unknown ID is a 404. Needs the &#x60;manage_reports&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the appeal.
try {
    val result : AdminHookAppeal = apiInstance.getHookAppeal(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#getHookAppeal")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#getHookAppeal")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the appeal. | |

### Return type

[**AdminHookAppeal**](AdminHookAppeal.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="getHookDecision"></a>
# **getHookDecision**
> AdminHookDecision getHookDecision(id)

Get a decision

One decision of a decision hook, with its cancellation. An unknown ID is a 404. Needs the &#x60;manage_reports&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the decision.
try {
    val result : AdminHookDecision = apiInstance.getHookDecision(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#getHookDecision")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#getHookDecision")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the decision. | |

### Return type

[**AdminHookDecision**](AdminHookDecision.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="getHookSummary"></a>
# **getHookSummary**
> AdminHookSummary getHookSummary()

Summarize the decision hooks

Every decision hook with its settings, and the count and latency of its decisions of the last 7 days per mode and outcome. Needs the &#x60;manage_reports&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
try {
    val result : AdminHookSummary = apiInstance.getHookSummary()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#getHookSummary")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#getHookSummary")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**AdminHookSummary**](AdminHookSummary.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="getInstanceHealth"></a>
# **getInstanceHealth**
> AdminInstanceHealth getInstanceHealth(domain)

Get the health of a remote server

The health of the server, when it was first seen, and the software its NodeInfo last reported; one without failures is &#x60;available&#x60;, without &#x60;state_changed_at&#x60;. Needs the &#x60;manage_domains&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val domain : kotlin.String = domain_example // kotlin.String | The domain of the remote server, such as `mastodon.example`, normalized to IDNA A-labels in lower case without a trailing dot. A value that is not a host name is a 400. 
try {
    val result : AdminInstanceHealth = apiInstance.getInstanceHealth(domain)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#getInstanceHealth")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#getInstanceHealth")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **domain** | **kotlin.String**| The domain of the remote server, such as &#x60;mastodon.example&#x60;, normalized to IDNA A-labels in lower case without a trailing dot. A value that is not a host name is a 400.  | |

### Return type

[**AdminInstanceHealth**](AdminInstanceHealth.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="getReport"></a>
# **getReport**
> AdminReportDetail getReport(id)

Get a report

The report as a moderator opens it, with the posts it points at whatever their visibility. An unknown ID is a 404. Needs the &#x60;manage_reports&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the report.
try {
    val result : AdminReportDetail = apiInstance.getReport(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#getReport")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#getReport")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the report. | |

### Return type

[**AdminReportDetail**](AdminReportDetail.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="getSignupBlock"></a>
# **getSignupBlock**
> AdminSignupBlock getSignupBlock(id)

Get a sign-up block

An unknown ID is a 404. Needs the &#x60;manage_accounts&#x60; permission.  On a one-person server (&#x60;Instance.single_user&#x60;) it is a 404 of the type &#x60;/api/v1/problems/single-user-mode&#x60;. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the sign-up block.
try {
    val result : AdminSignupBlock = apiInstance.getSignupBlock(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#getSignupBlock")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#getSignupBlock")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the sign-up block. | |

### Return type

[**AdminSignupBlock**](AdminSignupBlock.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="importDomainBlocks"></a>
# **importDomainBlocks**
> DomainBlockImportResult importDomainBlocks(importDomainBlocksRequest, idempotencyKey)

Import the chosen rows of a Mastodon domain block CSV

Issues the domain actions of each block, as the preview names them: the kinds of the block that no action in effect on the domain or a parent domain has already. The actions last until they are lifted, carry the block&#39;s public comment and obfuscation and the request&#39;s private note, and are audited like actions issued one at a time. A block that would issue nothing is skipped with the reason. The actions are written a few dozen at a time, so an error part way keeps the ones before it; sending the blocks again skips them as &#x60;already_applied&#x60;. More than 5,000 blocks or a &#x60;private_note&#x60; that is too long or contains U+0000 is a 422 that issues nothing. Needs the &#x60;manage_domains&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val importDomainBlocksRequest : ImportDomainBlocksRequest =  // ImportDomainBlocksRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : DomainBlockImportResult = apiInstance.importDomainBlocks(importDomainBlocksRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#importDomainBlocks")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#importDomainBlocks")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **importDomainBlocksRequest** | [**ImportDomainBlocksRequest**](ImportDomainBlocksRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**DomainBlockImportResult**](DomainBlockImportResult.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="importSignupBlocks"></a>
# **importSignupBlocks**
> SignupBlockImportResult importSignupBlocks(importSignupBlocksRequest, idempotencyKey)

Block sign-ups from a list

Adds a block on sign-ups for each value of a list, such as a public list of disposable email domains, all with the same kind, severity, comment, and duration. The list has one value per line in any form that &#x60;createSignupBlock&#x60; takes, or a CSV row whose first field is the value; blank lines and lines starting with &#x60;#&#x60; are ignored. It adds what it can and reports the lines it skipped: a value that is not valid for the kind (&#x60;invalid&#x60;), that repeats an earlier line once in canonical form (&#x60;duplicate&#x60;), or that a block in effect of the same kind and value with a severity at least as strong already covers (&#x60;already_blocked&#x60;). Each block added is audited like one added on its own. The blocks are written a few hundred at a time, so an error part way keeps the ones before it; sending the list again skips them as &#x60;already_blocked&#x60;. More than 10,000 values, an invalid kind or severity, or a &#x60;comment&#x60; that is too long or contains U+0000 is a 422 that adds nothing. Needs the &#x60;manage_accounts&#x60; permission.  On a one-person server (&#x60;Instance.single_user&#x60;) it is a 404 of the type &#x60;/api/v1/problems/single-user-mode&#x60;. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val importSignupBlocksRequest : ImportSignupBlocksRequest =  // ImportSignupBlocksRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : SignupBlockImportResult = apiInstance.importSignupBlocks(importSignupBlocksRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#importSignupBlocks")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#importSignupBlocks")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **importSignupBlocksRequest** | [**ImportSignupBlocksRequest**](ImportSignupBlocksRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**SignupBlockImportResult**](SignupBlockImportResult.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="liftAccountAction"></a>
# **liftAccountAction**
> AdminAccountAction liftAccountAction(id, idempotencyKey, liftAccountActionRequest)

Lift a moderation action on an account

Ends the action&#39;s restrictions at once; those of other actions on the account stay. Sessions and tokens that the action ended stay ended, and so do the posts that &#x60;delete_statuses&#x60; deleted; the posts that &#x60;mark_statuses_sensitive&#x60; marked are shown as they were. Lifting a lifted action changes nothing and keeps its first reason. A &#x60;ban&#x60; is never lifted: a 422 naming &#x60;id&#x60;. A &#x60;reason&#x60; that is too long or contains U+0000 is a 422 naming &#x60;reason&#x60;. An unknown ID is a 404. A local account whose roles do not rank below the user&#39;s, the user&#39;s own included, is a 403 &#x60;/api/v1/problems/target-outranks-actor&#x60;. Needs the &#x60;manage_accounts&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the account action.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
val liftAccountActionRequest : LiftAccountActionRequest =  // LiftAccountActionRequest | 
try {
    val result : AdminAccountAction = apiInstance.liftAccountAction(id, idempotencyKey, liftAccountActionRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#liftAccountAction")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#liftAccountAction")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the account action. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |
| **liftAccountActionRequest** | [**LiftAccountActionRequest**](LiftAccountActionRequest.md)|  | [optional] |

### Return type

[**AdminAccountAction**](AdminAccountAction.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="liftAdminAccountEmailSuppression"></a>
# **liftAdminAccountEmailSuppression**
> liftAdminAccountEmailSuppression(id, idempotencyKey)

Lift the suppression of the email address of a local account

Lets the server send email again to the primary address of a local account&#39;s user, which a hard bounce or a complaint suppressed, such as after the user fixed their mailbox. It is recorded in the audit log as &#x60;email_suppression_lifted&#x60;. A remote account or the instance actor is a 422 naming &#x60;id&#x60;, an account whose address is not suppressed a 409 &#x60;/api/v1/problems/email-not-suppressed&#x60;, and an unknown account a 404. A local account whose roles do not rank below the user&#39;s, the user&#39;s own included, is a 403 &#x60;/api/v1/problems/target-outranks-actor&#x60;. Needs the &#x60;manage_accounts&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the account, local or remote.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    apiInstance.liftAdminAccountEmailSuppression(id, idempotencyKey)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#liftAdminAccountEmailSuppression")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#liftAdminAccountEmailSuppression")
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

<a id="liftDomainAction"></a>
# **liftDomainAction**
> AdminDomainAction liftDomainAction(id, idempotencyKey)

Lift a moderation action on a domain

Ends the action&#39;s restrictions at once; those of other actions on the domain stay. Lifting a lifted action changes nothing. An unknown ID is a 404. Needs the &#x60;manage_domains&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the domain action.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AdminDomainAction = apiInstance.liftDomainAction(id, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#liftDomainAction")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#liftDomainAction")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the domain action. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AdminDomainAction**](AdminDomainAction.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="liftSignupBlock"></a>
# **liftSignupBlock**
> AdminSignupBlock liftSignupBlock(id, idempotencyKey)

Lift a sign-up block

The block stops matching sign-ups at once; the lift is audited. Lifting a lifted block changes nothing. An unknown ID is a 404. Needs the &#x60;manage_accounts&#x60; permission.  On a one-person server (&#x60;Instance.single_user&#x60;) it is a 404 of the type &#x60;/api/v1/problems/single-user-mode&#x60;. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the sign-up block.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AdminSignupBlock = apiInstance.liftSignupBlock(id, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#liftSignupBlock")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#liftSignupBlock")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the sign-up block. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AdminSignupBlock**](AdminSignupBlock.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listAccountActions"></a>
# **listAccountActions**
> AdminAccountActionPage listAccountActions(id, active, cursor, limit)

List the moderation actions on an account

The actions that moderators took on an account, newest first, lifted and expired ones included unless &#x60;active&#x60; is set. An unknown account is a 404. Needs the &#x60;manage_accounts&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the account, local or remote.
val active : kotlin.Boolean = true // kotlin.Boolean | Keep only the actions in effect now, neither lifted nor expired.
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : AdminAccountActionPage = apiInstance.listAccountActions(id, active, cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#listAccountActions")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#listAccountActions")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the account, local or remote. | |
| **active** | **kotlin.Boolean**| Keep only the actions in effect now, neither lifted nor expired. | [optional] |
| **cursor** | **kotlin.String**| The &#x60;next_cursor&#x60; of the previous page; omitted for the first page. | [optional] |
| **limit** | **kotlin.Int**| The maximum number of items in the page. | [optional] [default to 20] |

### Return type

[**AdminAccountActionPage**](AdminAccountActionPage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listAdminAccountSameIPAccounts"></a>
# **listAdminAccountSameIPAccounts**
> AdminAccountPage listAdminAccountSameIPAccounts(id, ip, cursor, limit)

List the other accounts that used an IP address of a local account

The other local accounts that registered or signed in from &#x60;ip&#x60;, newest first: the whole list of which &#x60;POST .../reveal&#x60; shows 20 per IP address. The account itself is left out. A page may be shorter than &#x60;limit&#x60; and still have a &#x60;next_cursor&#x60;. Each call is recorded in the audit log as &#x60;revealed_private_data&#x60; before anything is returned, as the reveal is. An &#x60;ip&#x60; that is not an IP address is a 400 naming &#x60;ip&#x60;. A remote account or the instance actor is a 422 naming &#x60;id&#x60;. An unknown account is a 404. A local account whose roles do not rank below the user&#39;s, the user&#39;s own included, is a 403 &#x60;/api/v1/problems/target-outranks-actor&#x60;. Needs the &#x60;manage_accounts&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the account, local or remote.
val ip : kotlin.String = ip_example // kotlin.String | The IP address, as `sign_ins` or `registration_ip` of the reveal give it.
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : AdminAccountPage = apiInstance.listAdminAccountSameIPAccounts(id, ip, cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#listAdminAccountSameIPAccounts")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#listAdminAccountSameIPAccounts")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the account, local or remote. | |
| **ip** | **kotlin.String**| The IP address, as &#x60;sign_ins&#x60; or &#x60;registration_ip&#x60; of the reveal give it. | |
| **cursor** | **kotlin.String**| The &#x60;next_cursor&#x60; of the previous page; omitted for the first page. | [optional] |
| **limit** | **kotlin.Int**| The maximum number of items in the page. | [optional] [default to 20] |

### Return type

[**AdminAccountPage**](AdminAccountPage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listAdminAccountSignIns"></a>
# **listAdminAccountSignIns**
> AdminAccountSignInPage listAdminAccountSignIns(id, cursor, limit)

List where the sign-ins of a local account came from

The sign-ins of a local account within the retention of the sign-in origins, newest first, with their IP addresses: the whole list that &#x60;POST .../reveal&#x60; starts with its newest 20. Each call is recorded in the audit log as &#x60;revealed_private_data&#x60; before anything is returned, as the reveal is. A remote account or the instance actor is a 422 naming &#x60;id&#x60;. An unknown account is a 404. A local account whose roles do not rank below the user&#39;s, the user&#39;s own included, is a 403 &#x60;/api/v1/problems/target-outranks-actor&#x60;. Needs the &#x60;manage_accounts&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the account, local or remote.
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : AdminAccountSignInPage = apiInstance.listAdminAccountSignIns(id, cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#listAdminAccountSignIns")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#listAdminAccountSignIns")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the account, local or remote. | |
| **cursor** | **kotlin.String**| The &#x60;next_cursor&#x60; of the previous page; omitted for the first page. | [optional] |
| **limit** | **kotlin.Int**| The maximum number of items in the page. | [optional] [default to 20] |

### Return type

[**AdminAccountSignInPage**](AdminAccountSignInPage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listAdminAccounts"></a>
# **listAdminAccounts**
> AdminAccountPage listAdminAccounts(username, domain, origin, state, email, emailDomain, ip, cursor, limit)

Search the accounts

The local and remote accounts, newest first, without the instance actor: those whose handle&#39;s username starts with &#x60;username&#x60; (case-insensitively; a remote account whose handle is not verified has none), on a &#x60;domain&#x60;, local or remote (&#x60;origin&#x60;), in a &#x60;state&#x60;, and, for local accounts, of a primary email address (&#x60;email&#x60;, case-insensitively), an email domain (&#x60;email_domain&#x60;), or an IP address that the account registered or signed in from (&#x60;ip&#x60;). At most one of &#x60;email&#x60;, &#x60;email_domain&#x60;, and &#x60;ip&#x60; is given. The results never include an email or IP address. A search reads at most 400 accounts per page, so a page can be short, or empty, and still have a &#x60;next_cursor&#x60;. An unknown &#x60;origin&#x60; or &#x60;state&#x60;, an &#x60;ip&#x60; that is not an IP address, an &#x60;email_domain&#x60; with an &#x60;@&#x60; inside, more than one of &#x60;email&#x60;, &#x60;email_domain&#x60;, and &#x60;ip&#x60;, or a parameter longer than 320 characters is a 400 naming it. Needs the &#x60;manage_accounts&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val username : kotlin.String = username_example // kotlin.String | Keep the accounts whose username starts with this.
val domain : kotlin.String = domain_example // kotlin.String | Keep the remote accounts of this domain (its punycode form).
val origin : kotlin.String = origin_example // kotlin.String | Keep the local or the remote accounts.
val state : AdminAccountState =  // AdminAccountState | Keep the accounts in this state.
val email : kotlin.String = email_example // kotlin.String | Keep the local account whose primary email address is this.
val emailDomain : kotlin.String = emailDomain_example // kotlin.String | Keep the local accounts whose primary email address is at this domain (`example.com` or `@example.com`). 
val ip : kotlin.String = ip_example // kotlin.String | Keep the local accounts that registered or signed in from this IP address, within the retention of the sign-in and registration origins. 
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : AdminAccountPage = apiInstance.listAdminAccounts(username, domain, origin, state, email, emailDomain, ip, cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#listAdminAccounts")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#listAdminAccounts")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **username** | **kotlin.String**| Keep the accounts whose username starts with this. | [optional] |
| **domain** | **kotlin.String**| Keep the remote accounts of this domain (its punycode form). | [optional] |
| **origin** | **kotlin.String**| Keep the local or the remote accounts. | [optional] [enum: local, remote] |
| **state** | [**AdminAccountState**](.md)| Keep the accounts in this state. | [optional] [enum: active, pending, rejected, limited, disabled, deactivated, suspended, deleted] |
| **email** | **kotlin.String**| Keep the local account whose primary email address is this. | [optional] |
| **emailDomain** | **kotlin.String**| Keep the local accounts whose primary email address is at this domain (&#x60;example.com&#x60; or &#x60;@example.com&#x60;).  | [optional] |
| **ip** | **kotlin.String**| Keep the local accounts that registered or signed in from this IP address, within the retention of the sign-in and registration origins.  | [optional] |
| **cursor** | **kotlin.String**| The &#x60;next_cursor&#x60; of the previous page; omitted for the first page. | [optional] |
| **limit** | **kotlin.Int**| The maximum number of items in the page. | [optional] [default to 20] |

### Return type

[**AdminAccountPage**](AdminAccountPage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listAppeals"></a>
# **listAppeals**
> AdminAppealPage listAppeals(state, cursor, limit)

List the appeals of moderation actions

The appeals that local users made of the actions on their accounts, newest first, each with its action. Needs the &#x60;manage_accounts&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val state : ModerationAppealState =  // ModerationAppealState | Keep only the appeals in this state; omitted keeps every state.
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : AdminAppealPage = apiInstance.listAppeals(state, cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#listAppeals")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#listAppeals")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **state** | [**ModerationAppealState**](.md)| Keep only the appeals in this state; omitted keeps every state. | [optional] [enum: pending, upheld, overturned] |
| **cursor** | **kotlin.String**| The &#x60;next_cursor&#x60; of the previous page; omitted for the first page. | [optional] |
| **limit** | **kotlin.Int**| The maximum number of items in the page. | [optional] [default to 20] |

### Return type

[**AdminAppealPage**](AdminAppealPage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listAuditLog"></a>
# **listAuditLog**
> AdminAuditEntryPage listAuditLog(actorAccountId, action, targetKind, targetId, cursor, limit)

List the audit log

The operations of the moderators and administrators, newest first: who did what to which target and when. Each entry keeps the target&#39;s display name as it was (&#x60;target_label&#x60;), so it stays readable after the target is gone; the reasons and notes stay with the operation itself (&#x60;source_event_id&#x60;). The audit log cannot be changed or deleted through the API. An unknown &#x60;action&#x60; or &#x60;target_kind&#x60;, or a &#x60;target_id&#x60; without its &#x60;target_kind&#x60;, is a 400. Needs the &#x60;view_audit&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val actorAccountId : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | Keep only the operations of the user of this local account. An account that is not local, or not known, has no operations. 
val action : AdminAuditAction =  // AdminAuditAction | Keep only this kind of operation.
val targetKind : AdminAuditTargetKind =  // AdminAuditTargetKind | Keep only the operations on this kind of target.
val targetId : kotlin.String = targetId_example // kotlin.String | Keep only the operations on this target, of the kind `target_kind`.
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : AdminAuditEntryPage = apiInstance.listAuditLog(actorAccountId, action, targetKind, targetId, cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#listAuditLog")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#listAuditLog")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **actorAccountId** | **java.util.UUID**| Keep only the operations of the user of this local account. An account that is not local, or not known, has no operations.  | [optional] |
| **action** | [**AdminAuditAction**](.md)| Keep only this kind of operation. | [optional] [enum: account_action_issued, account_action_lifted, domain_action_issued, domain_action_lifted, report_assigned, report_unassigned, report_resolved, report_reopened, report_note_added, report_forwarded, registration_approved, registration_rejected, setting_changed, text_changed, rule_added, rule_revised, rule_moved, rule_retired, role_granted, role_revoked, role_created, role_updated, role_deleted, appeal_upheld, appeal_overturned, account_deletion_canceled, revealed_private_data, signup_block_added, signup_block_lifted, hook_decision_applied, hook_decision_overridden, hook_appeal_upheld, two_factor_disabled, password_reset_link_issued, email_suppression_lifted, account_moved, custom_emoji_created, custom_emoji_image_replaced, custom_emoji_copied, custom_emoji_updated, custom_emoji_enabled, custom_emoji_disabled, custom_emoji_deleted, webhook_created, webhook_updated, webhook_deleted, webhook_enabled, webhook_disabled, webhook_secret_rotated, announcement_created, announcement_updated, announcement_published, announcement_withdrawn, announcement_deleted, invite_created, invite_revoked, relay_added, relay_followed, relay_unfollowed, relay_removed, relay_accepted, relay_rejected, status_refetch_queued, account_statuses_refetch_queued, domain_statuses_refetch_queued, billing_import_queued, cost_item_created, cost_item_relabeled, cost_item_price_revised, cost_item_end_changed, cost_item_deleted, supporter_program_enabled, supporter_program_disabled, supporter_tips_enabled, supporter_tips_revised, supporter_tips_disabled, supporter_plan_created, supporter_plan_revised, supporter_plan_retired, supporter_subscription_ended, custom_emoji_request_submitted, custom_emoji_request_withdrawn, custom_emoji_request_approved, custom_emoji_request_rejected, payment_reconcile_queued, oauth_client_disabled, oauth_client_enabled, oauth_client_host_denied, oauth_client_host_allowed, service_closure_announced, service_closure_withdrawn, service_closure_closed, service_closure_delivery_completed, voice_room_ended, login_provider_created, login_provider_updated, login_provider_deleted, login_provider_enabled, login_provider_disabled, login_provider_secret_rotated, link_card_domain_fetch_stopped, link_card_domain_images_stopped, link_card_domain_rule_removed, instance_marked_available, delivery_revealed, account_deliveries_viewed, domain_allow_added, domain_allow_removed, domain_list_subscription_created, domain_list_subscription_updated, domain_list_subscription_deleted, domain_list_draft_accepted, domain_list_draft_rejected, domain_list_fetch_queued, event_canceled] |
| **targetKind** | [**AdminAuditTargetKind**](.md)| Keep only the operations on this kind of target. | [optional] [enum: account, status, domain, report, appeal, user, role, setting, rule, signup_block, custom_emoji, webhook, announcement, invite, relay, billing_source, cost_item, supporter_program, supporter_plan, supporter_subscription, custom_emoji_request, payment_provider, oauth_client, oauth_client_host, instance, voice_room, login_provider, link_card_domain, hook_decision, domain_allow, domain_list_subscription, event] |
| **targetId** | **kotlin.String**| Keep only the operations on this target, of the kind &#x60;target_kind&#x60;. | [optional] |
| **cursor** | **kotlin.String**| The &#x60;next_cursor&#x60; of the previous page; omitted for the first page. | [optional] |
| **limit** | **kotlin.Int**| The maximum number of items in the page. | [optional] [default to 20] |

### Return type

[**AdminAuditEntryPage**](AdminAuditEntryPage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listDomainActions"></a>
# **listDomainActions**
> AdminDomainActionPage listDomainActions(domain, active, cursor, limit)

List the moderation actions on domains

The actions that moderators took on remote domains, newest first, lifted and expired ones included unless &#x60;active&#x60; is set. Needs the &#x60;manage_domains&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val domain : kotlin.String = domain_example // kotlin.String | Keep only the actions on this domain (not those on its parent domains). It is normalized like the domain of a new action. 
val active : kotlin.Boolean = true // kotlin.Boolean | Keep only the actions in effect now, neither lifted nor expired.
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : AdminDomainActionPage = apiInstance.listDomainActions(domain, active, cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#listDomainActions")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#listDomainActions")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **domain** | **kotlin.String**| Keep only the actions on this domain (not those on its parent domains). It is normalized like the domain of a new action.  | [optional] |
| **active** | **kotlin.Boolean**| Keep only the actions in effect now, neither lifted nor expired. | [optional] |
| **cursor** | **kotlin.String**| The &#x60;next_cursor&#x60; of the previous page; omitted for the first page. | [optional] |
| **limit** | **kotlin.Int**| The maximum number of items in the page. | [optional] [default to 20] |

### Return type

[**AdminDomainActionPage**](AdminDomainActionPage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listDomainAllows"></a>
# **listDomainAllows**
> DomainAllowList listDomainAllows()

List the allowlist of federation

Every allowed domain, by domain. The list is not paginated; the allowlist holds at most 5,000 domains. Needs the &#x60;manage_domains&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
try {
    val result : DomainAllowList = apiInstance.listDomainAllows()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#listDomainAllows")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#listDomainAllows")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**DomainAllowList**](DomainAllowList.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listDomainListDrafts"></a>
# **listDomainListDrafts**
> AdminDomainListDraftList listDomainListDrafts(id)

List the drafts of a subscription

The differences between the list, as last read, and the actions the subscription issued that wait for a moderator: every difference in the &#x60;draft&#x60; mode, and in the &#x60;automatic&#x60; mode those of an unknown severity and the lifts of a fetch that would lift too many. A domain that an action in effect covers already, on it or a parent domain, whoever issued it, is no draft. Rejected drafts are not listed. An unknown or deleted ID is a 404. Needs the &#x60;manage_domains&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the subscription to a domain block list.
try {
    val result : AdminDomainListDraftList = apiInstance.listDomainListDrafts(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#listDomainListDrafts")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#listDomainListDrafts")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the subscription to a domain block list. | |

### Return type

[**AdminDomainListDraftList**](AdminDomainListDraftList.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listDomainListFetches"></a>
# **listDomainListFetches**
> AdminDomainListFetchPage listDomainListFetches(id, cursor, limit)

List the fetches of a subscription

The fetches of the list, newest first, failed ones included with why they failed. An unknown or deleted ID is a 404. Needs the &#x60;manage_domains&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the subscription to a domain block list.
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : AdminDomainListFetchPage = apiInstance.listDomainListFetches(id, cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#listDomainListFetches")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#listDomainListFetches")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the subscription to a domain block list. | |
| **cursor** | **kotlin.String**| The &#x60;next_cursor&#x60; of the previous page; omitted for the first page. | [optional] |
| **limit** | **kotlin.Int**| The maximum number of items in the page. | [optional] [default to 20] |

### Return type

[**AdminDomainListFetchPage**](AdminDomainListFetchPage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listDomainListSubscriptions"></a>
# **listDomainListSubscriptions**
> AdminDomainListSubscriptionPage listDomainListSubscriptions(cursor, limit)

List the subscriptions to domain block lists

The subscriptions that are not deleted, newest first, each with its last fetch. Needs the &#x60;manage_domains&#x60; permission.

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : AdminDomainListSubscriptionPage = apiInstance.listDomainListSubscriptions(cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#listDomainListSubscriptions")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#listDomainListSubscriptions")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **cursor** | **kotlin.String**| The &#x60;next_cursor&#x60; of the previous page; omitted for the first page. | [optional] |
| **limit** | **kotlin.Int**| The maximum number of items in the page. | [optional] [default to 20] |

### Return type

[**AdminDomainListSubscriptionPage**](AdminDomainListSubscriptionPage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listHookAppeals"></a>
# **listHookAppeals**
> AdminHookAppealPage listHookAppeals(state, cursor, limit)

List the appeals of refused posts

The authors&#39; appeals of a &#x60;reject&#x60; of &#x60;local.status&#x60;, the newest first, with the text each author tried to post. Needs the &#x60;manage_reports&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val state : HookAppealState =  // HookAppealState | Keep only the appeals in this state; omitted keeps every state.
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : AdminHookAppealPage = apiInstance.listHookAppeals(state, cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#listHookAppeals")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#listHookAppeals")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **state** | [**HookAppealState**](.md)| Keep only the appeals in this state; omitted keeps every state. | [optional] [enum: pending, upheld, overturned] |
| **cursor** | **kotlin.String**| The &#x60;next_cursor&#x60; of the previous page; omitted for the first page. | [optional] |
| **limit** | **kotlin.Int**| The maximum number of items in the page. | [optional] [default to 20] |

### Return type

[**AdminHookAppealPage**](AdminHookAppealPage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listHookDecisions"></a>
# **listHookDecisions**
> AdminHookDecisionPage listHookDecisions(hook, mode, outcome, cursor, limit)

List the decisions of the decision hooks

What the external reviews said, the newest first. Without &#x60;outcome&#x60; the list leaves out &#x60;allow&#x60;: it shows what a hook stopped, would have stopped in &#x60;observe&#x60;, or could not check. Needs the &#x60;manage_reports&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val hook : HookName =  // HookName | Keep the decisions of this hook.
val mode : HookDecisionMode =  // HookDecisionMode | Keep the decisions made in this mode.
val outcome : HookOutcome =  // HookOutcome | Keep the decisions with this outcome.
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : AdminHookDecisionPage = apiInstance.listHookDecisions(hook, mode, outcome, cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#listHookDecisions")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#listHookDecisions")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **hook** | [**HookName**](.md)| Keep the decisions of this hook. | [optional] [enum: inbound.status, inbound.follow, signup.check, media.check, local.status, inbound.actor, local.profile] |
| **mode** | [**HookDecisionMode**](.md)| Keep the decisions made in this mode. | [optional] [enum: observe, enforce] |
| **outcome** | [**HookOutcome**](.md)| Keep the decisions with this outcome. | [optional] [enum: allow, drop, flag, mark_sensitive, require_approval, reject, timeout, error, invalid, breaker_open] |
| **cursor** | **kotlin.String**| The &#x60;next_cursor&#x60; of the previous page; omitted for the first page. | [optional] |
| **limit** | **kotlin.Int**| The maximum number of items in the page. | [optional] [default to 20] |

### Return type

[**AdminHookDecisionPage**](AdminHookDecisionPage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listInstanceAvailabilityEvents"></a>
# **listInstanceAvailabilityEvents**
> AdminAvailabilityEventPage listInstanceAvailabilityEvents(domain, cursor, limit)

List the times a remote server was marked unavailable or available

Why and when the server was marked unavailable or available again, newest first. Needs the &#x60;manage_domains&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val domain : kotlin.String = domain_example // kotlin.String | The domain of the remote server, such as `mastodon.example`, normalized to IDNA A-labels in lower case without a trailing dot. A value that is not a host name is a 400. 
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : AdminAvailabilityEventPage = apiInstance.listInstanceAvailabilityEvents(domain, cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#listInstanceAvailabilityEvents")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#listInstanceAvailabilityEvents")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **domain** | **kotlin.String**| The domain of the remote server, such as &#x60;mastodon.example&#x60;, normalized to IDNA A-labels in lower case without a trailing dot. A value that is not a host name is a 400.  | |
| **cursor** | **kotlin.String**| The &#x60;next_cursor&#x60; of the previous page; omitted for the first page. | [optional] |
| **limit** | **kotlin.Int**| The maximum number of items in the page. | [optional] [default to 20] |

### Return type

[**AdminAvailabilityEventPage**](AdminAvailabilityEventPage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listInstanceDeliveries"></a>
# **listInstanceDeliveries**
> AdminDeliveryPage listInstanceDeliveries(domain, accountId, activityUri, cursor, limit)

List the deliveries to a remote server and their attempts

What this server sent to the remote server and what it answered, newest first: each delivery of an activity to an inbox of the server, with every attempt (its outcome, the signature scheme, the status code, and the error), the ones that succeeded included. Attempts are kept for 30 days. The body of the activity is never returned.  The type and the times of every delivery, and every attempt, are always shown. When the activity is about a post whose visibility is &#x60;private&#x60;, &#x60;direct&#x60;, or &#x60;local&#x60;, or is a follow, its undo, an accept, or a reject of a local account that approves its followers, the sender, the activity URI, and a personal inbox are masked: &#x60;mask&#x60; says why, and &#x60;POST /admin/instances/{domain}/deliveries/{id}/reveal&#x60; shows them, audited. A shared inbox is never masked, and a masked inbox in an error reads &#x60;[masked]&#x60;.  A query with &#x60;account_id&#x60; is recorded in the audit log as &#x60;account_deliveries_viewed&#x60; before anything is returned; an account that is not local is a 400 naming &#x60;account_id&#x60;, an unknown one a 404. Needs the &#x60;manage_domains&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val domain : kotlin.String = domain_example // kotlin.String | The domain of the remote server, such as `mastodon.example`, normalized to IDNA A-labels in lower case without a trailing dot. A value that is not a host name is a 400. 
val accountId : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | Keep only the deliveries of the activities of this local account; audited.
val activityUri : kotlin.String = activityUri_example // kotlin.String | Keep only the deliveries of the activity with this ID.
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : AdminDeliveryPage = apiInstance.listInstanceDeliveries(domain, accountId, activityUri, cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#listInstanceDeliveries")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#listInstanceDeliveries")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **domain** | **kotlin.String**| The domain of the remote server, such as &#x60;mastodon.example&#x60;, normalized to IDNA A-labels in lower case without a trailing dot. A value that is not a host name is a 400.  | |
| **accountId** | **java.util.UUID**| Keep only the deliveries of the activities of this local account; audited. | [optional] |
| **activityUri** | **kotlin.String**| Keep only the deliveries of the activity with this ID. | [optional] |
| **cursor** | **kotlin.String**| The &#x60;next_cursor&#x60; of the previous page; omitted for the first page. | [optional] |
| **limit** | **kotlin.Int**| The maximum number of items in the page. | [optional] [default to 20] |

### Return type

[**AdminDeliveryPage**](AdminDeliveryPage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listInstanceHealth"></a>
# **listInstanceHealth**
> AdminInstanceHealthPage listInstanceHealth(troubled, state, cursor, limit)

List the known remote servers with their health

The remote servers this instance knows, in domain order: those it stored a document of, and those deliveries went to. Each one carries its health, when it was first seen, and the software its NodeInfo last reported. &#x60;troubled&#x60; keeps the servers that deliveries failed to reach on some days or that are marked unavailable; &#x60;state&#x60; keeps those in one state, a server without failures being &#x60;available&#x60;. Unavailable servers are sent only &#x60;Follow&#x60;, &#x60;Accept&#x60;, and &#x60;Reject&#x60; until a delivery succeeds, they send a signed request, the daily probe reaches them, or an operator marks them available. Needs the &#x60;manage_domains&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val troubled : kotlin.Boolean = true // kotlin.Boolean | Keep only the servers with delivery failures or marked unavailable (`true`); `false` or omitted keeps every known server. 
val state : AdminInstanceState =  // AdminInstanceState | Keep only the servers in this state; omitted keeps both.
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : AdminInstanceHealthPage = apiInstance.listInstanceHealth(troubled, state, cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#listInstanceHealth")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#listInstanceHealth")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **troubled** | **kotlin.Boolean**| Keep only the servers with delivery failures or marked unavailable (&#x60;true&#x60;); &#x60;false&#x60; or omitted keeps every known server.  | [optional] |
| **state** | [**AdminInstanceState**](.md)| Keep only the servers in this state; omitted keeps both. | [optional] [enum: available, unavailable] |
| **cursor** | **kotlin.String**| The &#x60;next_cursor&#x60; of the previous page; omitted for the first page. | [optional] |
| **limit** | **kotlin.Int**| The maximum number of items in the page. | [optional] [default to 20] |

### Return type

[**AdminInstanceHealthPage**](AdminInstanceHealthPage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listInvites"></a>
# **listInvites**
> AdminInvitePage listInvites(cursor, limit)

List the invites

The invites that let people sign up while the registration mode is &#x60;open&#x60;, &#x60;approval&#x60;, or &#x60;invite&#x60;, newest first, with how they have been used and who created them: the staff&#39;s and the members&#39; own invites. Needs the &#x60;manage_invites&#x60; permission.  On a one-person server (&#x60;Instance.single_user&#x60;) it is a 404 of the type &#x60;/api/v1/problems/single-user-mode&#x60;. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : AdminInvitePage = apiInstance.listInvites(cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#listInvites")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#listInvites")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **cursor** | **kotlin.String**| The &#x60;next_cursor&#x60; of the previous page; omitted for the first page. | [optional] |
| **limit** | **kotlin.Int**| The maximum number of items in the page. | [optional] [default to 20] |

### Return type

[**AdminInvitePage**](AdminInvitePage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listPendingRegistrations"></a>
# **listPendingRegistrations**
> AdminRegistrationPage listPendingRegistrations(cursor, limit)

List the registrations that await approval

The sign-ups that wait for a moderator&#39;s decision, with the reasons their applicants gave, longest waiting first. Needs the &#x60;manage_accounts&#x60; permission.  On a one-person server (&#x60;Instance.single_user&#x60;) it is a 404 of the type &#x60;/api/v1/problems/single-user-mode&#x60;. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : AdminRegistrationPage = apiInstance.listPendingRegistrations(cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#listPendingRegistrations")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#listPendingRegistrations")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **cursor** | **kotlin.String**| The &#x60;next_cursor&#x60; of the previous page; omitted for the first page. | [optional] |
| **limit** | **kotlin.Int**| The maximum number of items in the page. | [optional] [default to 20] |

### Return type

[**AdminRegistrationPage**](AdminRegistrationPage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listReports"></a>
# **listReports**
> AdminReportPage listReports(state, category, assignee, targetAccountId, order, cursor, limit)

List the reports

The reports in the moderators&#39; queue, newest first unless &#x60;order&#x60; is &#x60;oldest&#x60;. Needs the &#x60;manage_reports&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val state : AdminReportState =  // AdminReportState | Keep only the reports in this state; omitted keeps both.
val category : ReportCategory =  // ReportCategory | Keep only the reports of this category; omitted keeps every category.
val assignee : kotlin.String = assignee_example // kotlin.String | Keep only the reports assigned to someone: `me` for the viewer, `none` for the reports that nobody handles, or the ID of a local account. Omitted keeps every report; any other value is a `400`. 
val targetAccountId : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | Keep only the reports about this account, local or remote. An account that no report is about gives an empty page. 
val order : kotlin.String = order_example // kotlin.String | `newest` (the default) lists the most recently filed report first; `oldest` lists the report that has waited longest first. The cursor continues the page in the same order. 
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : AdminReportPage = apiInstance.listReports(state, category, assignee, targetAccountId, order, cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#listReports")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#listReports")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **state** | [**AdminReportState**](.md)| Keep only the reports in this state; omitted keeps both. | [optional] [enum: open, resolved] |
| **category** | [**ReportCategory**](.md)| Keep only the reports of this category; omitted keeps every category. | [optional] [enum: spam, legal, violation, other] |
| **assignee** | **kotlin.String**| Keep only the reports assigned to someone: &#x60;me&#x60; for the viewer, &#x60;none&#x60; for the reports that nobody handles, or the ID of a local account. Omitted keeps every report; any other value is a &#x60;400&#x60;.  | [optional] |
| **targetAccountId** | **java.util.UUID**| Keep only the reports about this account, local or remote. An account that no report is about gives an empty page.  | [optional] |
| **order** | **kotlin.String**| &#x60;newest&#x60; (the default) lists the most recently filed report first; &#x60;oldest&#x60; lists the report that has waited longest first. The cursor continues the page in the same order.  | [optional] [default to Order.NEWEST] [enum: newest, oldest] |
| **cursor** | **kotlin.String**| The &#x60;next_cursor&#x60; of the previous page; omitted for the first page. | [optional] |
| **limit** | **kotlin.Int**| The maximum number of items in the page. | [optional] [default to 20] |

### Return type

[**AdminReportPage**](AdminReportPage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listSignupBlocks"></a>
# **listSignupBlocks**
> AdminSignupBlockPage listSignupBlocks(kind, active, cursor, limit)

List the sign-up blocks

The blocks on sign-ups from the registration page, newest first, lifted and expired ones included unless &#x60;active&#x60; is set. Needs the &#x60;manage_accounts&#x60; permission.  On a one-person server (&#x60;Instance.single_user&#x60;) it is a 404 of the type &#x60;/api/v1/problems/single-user-mode&#x60;. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val kind : AdminSignupBlockKind =  // AdminSignupBlockKind | Keep only the blocks of this kind.
val active : kotlin.Boolean = true // kotlin.Boolean | Keep only the blocks in effect now, neither lifted nor expired.
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : AdminSignupBlockPage = apiInstance.listSignupBlocks(kind, active, cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#listSignupBlocks")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#listSignupBlocks")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **kind** | [**AdminSignupBlockKind**](.md)| Keep only the blocks of this kind. | [optional] [enum: email_domain, email_hash, ip_range] |
| **active** | **kotlin.Boolean**| Keep only the blocks in effect now, neither lifted nor expired. | [optional] |
| **cursor** | **kotlin.String**| The &#x60;next_cursor&#x60; of the previous page; omitted for the first page. | [optional] |
| **limit** | **kotlin.Int**| The maximum number of items in the page. | [optional] [default to 20] |

### Return type

[**AdminSignupBlockPage**](AdminSignupBlockPage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="markInstanceAvailable"></a>
# **markInstanceAvailable**
> AdminInstanceHealth markInstanceAvailable(domain, idempotencyKey)

Mark a remote server available

Clears the failure days of the server and, when it is unavailable, marks it available again with the reason &#x60;operator&#x60;, so that deliveries to it resume; the operation is audited as &#x60;instance_marked_available&#x60;. A server that is already available without failures stays as it is, and nothing is audited. Needs the &#x60;manage_domains&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val domain : kotlin.String = domain_example // kotlin.String | The domain of the remote server, such as `mastodon.example`, normalized to IDNA A-labels in lower case without a trailing dot. A value that is not a host name is a 400. 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AdminInstanceHealth = apiInstance.markInstanceAvailable(domain, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#markInstanceAvailable")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#markInstanceAvailable")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **domain** | **kotlin.String**| The domain of the remote server, such as &#x60;mastodon.example&#x60;, normalized to IDNA A-labels in lower case without a trailing dot. A value that is not a host name is a 400.  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AdminInstanceHealth**](AdminInstanceHealth.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="overrideHookDecision"></a>
# **overrideHookDecision**
> AdminHookDecision overrideHookDecision(id, idempotencyKey)

Cancel a decision

Records the cancellation as its own event and in the audit log, and undoes what the hook can: what happens depends on the verdict. Cancelling a canceled decision again changes nothing and answers the decision. An unknown ID is a 404. A decision that had no effect, or a &#x60;reject&#x60; of &#x60;local.status&#x60;, which goes through its appeal, is a 409 &#x60;/api/v1/problems/hook-decision-not-overridable&#x60;. Needs the &#x60;manage_reports&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the decision.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AdminHookDecision = apiInstance.overrideHookDecision(id, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#overrideHookDecision")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#overrideHookDecision")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the decision. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AdminHookDecision**](AdminHookDecision.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="overturnAppeal"></a>
# **overturnAppeal**
> AdminAppeal overturnAppeal(id, idempotencyKey, decideAppealRequest)

Overturn an appeal, lifting its action

Grants the appeal and lifts its action at once, as &#x60;POST /admin/account_actions/{id}/lift&#x60; does: the posts that &#x60;delete_statuses&#x60; deleted stay deleted. An action lifted meanwhile stays as it is. Overturning the appeal of a &#x60;ban&#x60;, which nothing else lifts, also cancels the scheduled deletion of the account; a &#x60;ban&#x60; whose account is deleted is a 422 &#x60;/api/v1/problems/not-appealable&#x60;. The user who appealed gets an &#x60;appeal_decided&#x60; notification with &#x60;decision_reason&#x60;. Deciding a decided appeal the same way again changes nothing and keeps the first reason; deciding it the other way is a 409 &#x60;/api/v1/problems/appeal-decided&#x60;. A &#x60;public_reason&#x60; that is too long or contains U+0000 is a 422 naming &#x60;public_reason&#x60;. An unknown ID is a 404. A local account whose roles do not rank below the user&#39;s, the user&#39;s own included, is a 403 &#x60;/api/v1/problems/target-outranks-actor&#x60;. Needs the &#x60;manage_accounts&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the appeal.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
val decideAppealRequest : DecideAppealRequest =  // DecideAppealRequest | 
try {
    val result : AdminAppeal = apiInstance.overturnAppeal(id, idempotencyKey, decideAppealRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#overturnAppeal")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#overturnAppeal")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the appeal. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |
| **decideAppealRequest** | [**DecideAppealRequest**](DecideAppealRequest.md)|  | [optional] |

### Return type

[**AdminAppeal**](AdminAppeal.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="overturnHookAppeal"></a>
# **overturnHookAppeal**
> AdminHookAppeal overturnHookAppeal(id, idempotencyKey)

Overturn an appeal of a refused post

Cancels the decision, which goes in the audit log as &#x60;hook_decision_overridden&#x60;, and stops reviewing the author&#39;s posts with &#x60;local.status&#x60;, in one transaction. The refused post is not restored: the author posts again. Deciding a decided appeal the same way again changes nothing; deciding it the other way is a 409 &#x60;/api/v1/problems/appeal-decided&#x60;. An unknown ID is a 404. Needs the &#x60;manage_reports&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the appeal.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AdminHookAppeal = apiInstance.overturnHookAppeal(id, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#overturnHookAppeal")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#overturnHookAppeal")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the appeal. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AdminHookAppeal**](AdminHookAppeal.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="previewDomainBlockImport"></a>
# **previewDomainBlockImport**
> DomainBlockImportPreview previewDomainBlockImport(previewDomainBlockImportRequest, idempotencyKey)

Preview the import of a Mastodon domain block CSV

Reads a domain block CSV that Mastodon exported and says what importing each row would do, without changing anything. A file whose first field is &#x60;#domain&#x60; has a header naming the columns; any other file is a list of domains, one per row, each suspended. An empty &#x60;#severity&#x60; is &#x60;suspend&#x60;, and a boolean is false when empty, &#x60;0&#x60;, &#x60;f&#x60;, &#x60;false&#x60;, or &#x60;off&#x60;. A row of &#x60;suspend&#x60; issues a &#x60;suspend&#x60;, &#x60;silence&#x60; a &#x60;limit&#x60;, and &#x60;noop&#x60; nothing, plus &#x60;reject_media&#x60; and &#x60;reject_reports&#x60; when set. A file that is not CSV or has more than 5,000 rows is a 422 naming &#x60;csv&#x60;. Needs the &#x60;manage_domains&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val previewDomainBlockImportRequest : PreviewDomainBlockImportRequest =  // PreviewDomainBlockImportRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : DomainBlockImportPreview = apiInstance.previewDomainBlockImport(previewDomainBlockImportRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#previewDomainBlockImport")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#previewDomainBlockImport")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **previewDomainBlockImportRequest** | [**PreviewDomainBlockImportRequest**](PreviewDomainBlockImportRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**DomainBlockImportPreview**](DomainBlockImportPreview.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="refetchAccountStatuses"></a>
# **refetchAccountStatuses**
> refetchAccountStatuses(id, idempotencyKey)

Refetch the posts of a remote account from its server

Queues the refetch, as &#x60;POST /admin/statuses/{id}/refetch&#x60; does, of the newest 1000 posts of the remote account that are stored and not deleted. The posts are fetched in batches of 100, a minute apart. An unknown account is a 404; a local account is a 422. Needs the &#x60;manage_domains&#x60; permission. The request is recorded in the audit log; nothing schedules a refetch on its own. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the remote account.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    apiInstance.refetchAccountStatuses(id, idempotencyKey)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#refetchAccountStatuses")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#refetchAccountStatuses")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the remote account. | |
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

<a id="refetchInstanceStatuses"></a>
# **refetchInstanceStatuses**
> refetchInstanceStatuses(domain, idempotencyKey)

Refetch the posts of the accounts of a remote server

Queues the refetch, as &#x60;POST /admin/statuses/{id}/refetch&#x60; does, of the stored posts of the server&#39;s accounts that are not deleted: the newest 20 of each account, newest accounts first, 1000 in all, in batches of 5 accounts a minute apart. A domain that is not a host name is a 400; this server&#39;s domain is a 422. Needs the &#x60;manage_domains&#x60; permission. The request is recorded in the audit log; nothing schedules a refetch on its own. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val domain : kotlin.String = domain_example // kotlin.String | The domain of the remote server, such as `mastodon.example`, normalized to IDNA A-labels in lower case without a trailing dot. A value that is not a host name is a 400. 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    apiInstance.refetchInstanceStatuses(domain, idempotencyKey)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#refetchInstanceStatuses")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#refetchInstanceStatuses")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **domain** | **kotlin.String**| The domain of the remote server, such as &#x60;mastodon.example&#x60;, normalized to IDNA A-labels in lower case without a trailing dot. A value that is not a host name is a 400.  | |
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

<a id="refetchStatus"></a>
# **refetchStatus**
> refetchStatus(id, idempotencyKey)

Refetch a remote post from its origin

Queues a fetch of the remote post from its server. The job records what the server serves now, as a received &#x60;Update&#x60; would: a newer revision, the counts of its poll, or, when the server answers 410 Gone or serves a &#x60;Tombstone&#x60;, the post&#39;s deletion. A post that cannot be fetched is left as it is. An unknown or deleted post is a 404; a local post is a 422. Needs the &#x60;manage_domains&#x60; permission. The request is recorded in the audit log; nothing schedules a refetch on its own. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the remote post.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    apiInstance.refetchStatus(id, idempotencyKey)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#refetchStatus")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#refetchStatus")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the remote post. | |
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

<a id="rejectDomainListDrafts"></a>
# **rejectDomainListDrafts**
> AdminDomainListDraftResult rejectDomainListDrafts(id, idempotencyKey, domainListDraftsRequest)

Reject drafts of a subscription

Rejects the drafts: a rejected &#x60;issue&#x60; does not come back until the list changes the row of its domain, and a rejected &#x60;lift&#x60; while its action is in effect. Each rejection is audited. An unknown or deleted ID is a 404. Needs the &#x60;manage_domains&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the subscription to a domain block list.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
val domainListDraftsRequest : DomainListDraftsRequest =  // DomainListDraftsRequest | 
try {
    val result : AdminDomainListDraftResult = apiInstance.rejectDomainListDrafts(id, idempotencyKey, domainListDraftsRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#rejectDomainListDrafts")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#rejectDomainListDrafts")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the subscription to a domain block list. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |
| **domainListDraftsRequest** | [**DomainListDraftsRequest**](DomainListDraftsRequest.md)|  | [optional] |

### Return type

[**AdminDomainListDraftResult**](AdminDomainListDraftResult.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="rejectRegistration"></a>
# **rejectRegistration**
> AdminRegistration rejectRegistration(id, idempotencyKey, registrationDecisionRequest)

Reject a registration

Rejects a pending registration, so that its user cannot sign in, with an optional note for the moderators; the decision is audited. Deciding a decided registration changes nothing and keeps its decision. The answer on a decided registration has no email address: only the list of pending registrations shows it, and &#x60;POST /admin/accounts/{id}/reveal&#x60; after. An unknown ID is a 404. Needs the &#x60;manage_accounts&#x60; permission.  On a one-person server (&#x60;Instance.single_user&#x60;) it is a 404 of the type &#x60;/api/v1/problems/single-user-mode&#x60;. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the registration.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
val registrationDecisionRequest : RegistrationDecisionRequest =  // RegistrationDecisionRequest | 
try {
    val result : AdminRegistration = apiInstance.rejectRegistration(id, idempotencyKey, registrationDecisionRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#rejectRegistration")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#rejectRegistration")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the registration. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |
| **registrationDecisionRequest** | [**RegistrationDecisionRequest**](RegistrationDecisionRequest.md)|  | [optional] |

### Return type

[**AdminRegistration**](AdminRegistration.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="removeDomainAllow"></a>
# **removeDomainAllow**
> removeDomainAllow(domain, idempotencyKey)

Remove a domain from the allowlist of federation

Removes the allow of a domain and records it in the audit log. While the instance setting &#x60;federation_scope&#x60; is &#x60;allowlist&#x60; and no parent domain is allowed, the domain and its subdomains that no other allow covers are cut off at once, as a suspension of the domain cuts them off: nothing is received from, delivered to, or fetched from them, and the follows between the local accounts and their accounts end for good, each local user who loses one being notified. A domain without an allow changes nothing. Needs the &#x60;manage_domains&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val domain : kotlin.String = domain_example // kotlin.String | The canonical domain of the allow.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    apiInstance.removeDomainAllow(domain, idempotencyKey)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#removeDomainAllow")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#removeDomainAllow")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **domain** | **kotlin.String**| The canonical domain of the allow. | |
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

<a id="reopenReport"></a>
# **reopenReport**
> AdminReportDetail reopenReport(id, idempotencyKey)

Reopen a resolved report

Puts a resolved report back in the queue as &#x60;open&#x60;, keeping its assignee. Resolving it again does not notify the reporter again. An open report is a 409, an unknown ID a 404. Needs the &#x60;manage_reports&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the report.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AdminReportDetail = apiInstance.reopenReport(id, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#reopenReport")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#reopenReport")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the report. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AdminReportDetail**](AdminReportDetail.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="resolveReport"></a>
# **resolveReport**
> AdminReportDetail resolveReport(id, resolveReportRequest, idempotencyKey)

Resolve a report

Resolves an open report with an outcome. Resolving a resolved report changes nothing and keeps its outcome. An unknown ID is a 404. Needs the &#x60;manage_reports&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the report.
val resolveReportRequest : ResolveReportRequest =  // ResolveReportRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AdminReportDetail = apiInstance.resolveReport(id, resolveReportRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#resolveReport")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#resolveReport")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the report. | |
| **resolveReportRequest** | [**ResolveReportRequest**](ResolveReportRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AdminReportDetail**](AdminReportDetail.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="revealAdminAccount"></a>
# **revealAdminAccount**
> AdminAccountPrivateData revealAdminAccount(id)

Show the email address and the IP addresses of a local account

The primary email address of a local account, where it registered and its newest 20 sign-ins came from, and up to 20 other accounts per IP address that used the same one; &#x60;GET .../sign_ins&#x60; and &#x60;GET .../same_ip_accounts&#x60; page through the rest. Each call is recorded in the audit log as &#x60;revealed_private_data&#x60; before anything is returned; it changes nothing else. IP addresses are kept for the retention of the sign-in and registration origins only. A remote account or the instance actor is a 422 naming &#x60;id&#x60;. An unknown account is a 404. A local account whose roles do not rank below the user&#39;s, the user&#39;s own included, is a 403 &#x60;/api/v1/problems/target-outranks-actor&#x60;. Needs the &#x60;manage_accounts&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the account, local or remote.
try {
    val result : AdminAccountPrivateData = apiInstance.revealAdminAccount(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#revealAdminAccount")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#revealAdminAccount")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the account, local or remote. | |

### Return type

[**AdminAccountPrivateData**](AdminAccountPrivateData.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="revealInstanceDelivery"></a>
# **revealInstanceDelivery**
> AdminDeliveryReveal revealInstanceDelivery(domain, id)

Show the sender, the activity URI, and the inbox of a delivery

The fields that the list of deliveries masks, of one delivery to the server: its local sender, the URI of its activity, and its inbox. Each call is recorded in the audit log as &#x60;delivery_revealed&#x60; before anything is returned, also for a delivery that is not masked; it changes nothing else. The body of the activity is never returned. A delivery that is unknown or went to another server is a 404. Needs the &#x60;manage_domains&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val domain : kotlin.String = domain_example // kotlin.String | The domain of the remote server, such as `mastodon.example`, normalized to IDNA A-labels in lower case without a trailing dot. A value that is not a host name is a 400. 
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the delivery.
try {
    val result : AdminDeliveryReveal = apiInstance.revealInstanceDelivery(domain, id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#revealInstanceDelivery")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#revealInstanceDelivery")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **domain** | **kotlin.String**| The domain of the remote server, such as &#x60;mastodon.example&#x60;, normalized to IDNA A-labels in lower case without a trailing dot. A value that is not a host name is a 400.  | |
| **id** | **java.util.UUID**| The ID of the delivery. | |

### Return type

[**AdminDeliveryReveal**](AdminDeliveryReveal.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="revokeInvite"></a>
# **revokeInvite**
> AdminInvite revokeInvite(id, idempotencyKey)

Revoke an invite

The invite stops working at once; the people who signed up with it keep their accounts. Revoking a revoked invite changes nothing. An unknown ID is a 404. Needs the &#x60;manage_invites&#x60; permission.  On a one-person server (&#x60;Instance.single_user&#x60;) it is a 404 of the type &#x60;/api/v1/problems/single-user-mode&#x60;. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the invite.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AdminInvite = apiInstance.revokeInvite(id, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#revokeInvite")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#revokeInvite")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the invite. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AdminInvite**](AdminInvite.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="translateReportComment"></a>
# **translateReportComment**
> ReportCommentTranslation translateReportComment(id, statusTranslationRequest, idempotencyKey)

Translate the comment of a report into a language

Returns the reporter&#39;s comment in &#x60;target_language&#x60;, for a moderator who reads the report. An unknown report is a 404. Needs the &#x60;manage_reports&#x60; permission.  Only the reporter and the moderators read a comment, so it counts as a private post: the server sends it only while the instance setting &#x60;translation_reports_to_server&#x60; is on, and only to the providers allowed private posts. The translation is never kept, and the characters sent count against the server&#39;s monthly limit only. &#x60;provider&#x60; is omitted when the comment has no words to translate, and the answer is the comment as it is.  The problems are those of &#x60;POST /statuses/{id}/translation&#x60;: a 422 &#x60;/api/v1/problems/translation-unavailable&#x60; when the setting is off or no provider may receive the comment, a 422 &#x60;/api/v1/problems/translation-unsupported-language&#x60;, a 429 &#x60;/api/v1/problems/translation-limit&#x60; over the server&#39;s monthly limit, and a 503 &#x60;/api/v1/problems/translation-failed&#x60;. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the report.
val statusTranslationRequest : StatusTranslationRequest =  // StatusTranslationRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : ReportCommentTranslation = apiInstance.translateReportComment(id, statusTranslationRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#translateReportComment")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#translateReportComment")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the report. | |
| **statusTranslationRequest** | [**StatusTranslationRequest**](StatusTranslationRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**ReportCommentTranslation**](ReportCommentTranslation.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="translateReportedStatus"></a>
# **translateReportedStatus**
> StatusTranslation translateReportedStatus(id, statusId, statusTranslationRequest, idempotencyKey)

Translate a reported post into a language

Returns a post that the report points at in &#x60;target_language&#x60;, as &#x60;POST /statuses/{id}/translation&#x60; does, for a moderator who reads the report: whatever the post&#39;s visibility, and also when its author deleted it. A report that does not point at the post, an unknown report, and a post that is gone are a 404. Needs the &#x60;manage_reports&#x60; permission.  The moderator&#39;s access lifts nothing of what a provider may receive. The server sends the post to a provider only while the instance setting &#x60;translation_reports_to_server&#x60; is on, and only to the providers allowed the post&#39;s visibility, so a direct post goes only to a provider allowed direct posts. The characters sent count against the server&#39;s monthly limit only. The translation is kept for the readers of the post who may see it, except for a deleted post, whose translation is not kept.  The problems are those of &#x60;POST /statuses/{id}/translation&#x60;: a 422 &#x60;/api/v1/problems/translation-unavailable&#x60; when the setting is off or no provider may receive the post, a 422 &#x60;/api/v1/problems/translation-unsupported-language&#x60;, a 429 &#x60;/api/v1/problems/translation-limit&#x60; over the server&#39;s monthly limit, and a 503 &#x60;/api/v1/problems/translation-failed&#x60;. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the report.
val statusId : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of a post that the report points at.
val statusTranslationRequest : StatusTranslationRequest =  // StatusTranslationRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : StatusTranslation = apiInstance.translateReportedStatus(id, statusId, statusTranslationRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#translateReportedStatus")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#translateReportedStatus")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the report. | |
| **statusId** | **java.util.UUID**| The ID of a post that the report points at. | |
| **statusTranslationRequest** | [**StatusTranslationRequest**](StatusTranslationRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**StatusTranslation**](StatusTranslation.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="unassignReport"></a>
# **unassignReport**
> AdminReportDetail unassignReport(id, idempotencyKey)

Leave a report to nobody

Removes the moderator who handles the report, whoever it is. An unknown ID is a 404. Needs the &#x60;manage_reports&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the report.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AdminReportDetail = apiInstance.unassignReport(id, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#unassignReport")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#unassignReport")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the report. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AdminReportDetail**](AdminReportDetail.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="updateDomainListSubscription"></a>
# **updateDomainListSubscription**
> AdminDomainListSubscription updateDomainListSubscription(id, updateDomainListSubscriptionRequest, idempotencyKey)

Change the settings of a subscription

Changes the settings the request names and keeps the rest, and queues a fetch that applies them; the URL and the format do not change. An invalid setting is a 422. A change is audited. An unknown or deleted ID is a 404. Needs the &#x60;manage_domains&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the subscription to a domain block list.
val updateDomainListSubscriptionRequest : UpdateDomainListSubscriptionRequest =  // UpdateDomainListSubscriptionRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AdminDomainListSubscription = apiInstance.updateDomainListSubscription(id, updateDomainListSubscriptionRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#updateDomainListSubscription")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#updateDomainListSubscription")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the subscription to a domain block list. | |
| **updateDomainListSubscriptionRequest** | [**UpdateDomainListSubscriptionRequest**](UpdateDomainListSubscriptionRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AdminDomainListSubscription**](AdminDomainListSubscription.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="upholdAppeal"></a>
# **upholdAppeal**
> AdminAppeal upholdAppeal(id, idempotencyKey, decideAppealRequest)

Uphold an appeal, keeping its action

Rejects the appeal; the action stays as it is. The user who appealed gets an &#x60;appeal_decided&#x60; notification with &#x60;decision_reason&#x60;. Deciding a decided appeal the same way again changes nothing and keeps the first reason; deciding it the other way is a 409 &#x60;/api/v1/problems/appeal-decided&#x60;. A &#x60;public_reason&#x60; that is too long or contains U+0000 is a 422 naming &#x60;public_reason&#x60;. An unknown ID is a 404. A local account whose roles do not rank below the user&#39;s, the user&#39;s own included, is a 403 &#x60;/api/v1/problems/target-outranks-actor&#x60;. Needs the &#x60;manage_accounts&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the appeal.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
val decideAppealRequest : DecideAppealRequest =  // DecideAppealRequest | 
try {
    val result : AdminAppeal = apiInstance.upholdAppeal(id, idempotencyKey, decideAppealRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#upholdAppeal")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#upholdAppeal")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the appeal. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |
| **decideAppealRequest** | [**DecideAppealRequest**](DecideAppealRequest.md)|  | [optional] |

### Return type

[**AdminAppeal**](AdminAppeal.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="upholdHookAppeal"></a>
# **upholdHookAppeal**
> AdminHookAppeal upholdHookAppeal(id, idempotencyKey)

Uphold an appeal of a refused post

Rejects the appeal; the decision stays as it is and the author&#39;s posts are reviewed as before. Deciding a decided appeal the same way again changes nothing; deciding it the other way is a 409 &#x60;/api/v1/problems/appeal-decided&#x60;. An unknown ID is a 404. Needs the &#x60;manage_reports&#x60; permission. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AdminModerationApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the appeal.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AdminHookAppeal = apiInstance.upholdHookAppeal(id, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminModerationApi#upholdHookAppeal")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminModerationApi#upholdHookAppeal")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the appeal. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AdminHookAppeal**](AdminHookAppeal.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

