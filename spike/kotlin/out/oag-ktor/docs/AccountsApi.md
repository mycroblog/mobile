# AccountsApi

All URIs are relative to */api/v1*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**acceptFollowRequest**](AccountsApi.md#acceptFollowRequest) | **POST** /follow_requests/{id}/accept | Accept a follow request |
| [**addAccountAlias**](AccountsApi.md#addAccountAlias) | **POST** /accounts/aliases | Add an alias to the authenticated user |
| [**blockAccount**](AccountsApi.md#blockAccount) | **POST** /accounts/{id}/block | Block an account |
| [**cancelOwnAccountMove**](AccountsApi.md#cancelOwnAccountMove) | **DELETE** /accounts/move | Cancel the move of the authenticated user&#39;s account |
| [**changePassword**](AccountsApi.md#changePassword) | **POST** /accounts/password | Change or set the authenticated user&#39;s password |
| [**deactivateOwnAccount**](AccountsApi.md#deactivateOwnAccount) | **POST** /accounts/deactivation | Deactivate the authenticated user&#39;s account |
| [**deleteAccountNote**](AccountsApi.md#deleteAccountNote) | **DELETE** /accounts/{id}/note | Remove the private note on an account |
| [**deleteOwnAccount**](AccountsApi.md#deleteOwnAccount) | **POST** /accounts/delete | Delete the authenticated user&#39;s account |
| [**downloadDataExport**](AccountsApi.md#downloadDataExport) | **GET** /exports/{id}/archive | Download the archive of a data export |
| [**exportCsv**](AccountsApi.md#exportCsv) | **GET** /csv_exports/{kind} | Export the authenticated user&#39;s follows, followed hashtags, blocks, mutes, muted domains, or bookmarks as CSV |
| [**followAccount**](AccountsApi.md#followAccount) | **POST** /accounts/{id}/follow | Follow an account |
| [**getAccount**](AccountsApi.md#getAccount) | **GET** /accounts/{id} | Get an account |
| [**getDeactivationPreview**](AccountsApi.md#getDeactivationPreview) | **GET** /accounts/deactivation | Get what deactivating the authenticated user&#39;s account would do now |
| [**getPasswordStatus**](AccountsApi.md#getPasswordStatus) | **GET** /accounts/password | Get whether the authenticated user has a password |
| [**getRelationships**](AccountsApi.md#getRelationships) | **GET** /accounts/relationships | Get the viewer&#39;s relationships with accounts |
| [**listAccountAliases**](AccountsApi.md#listAccountAliases) | **GET** /accounts/aliases | List the authenticated user&#39;s aliases |
| [**listAccountFeaturedTags**](AccountsApi.md#listAccountFeaturedTags) | **GET** /accounts/{id}/featured_tags | List the hashtags an account features |
| [**listAccountFollowers**](AccountsApi.md#listAccountFollowers) | **GET** /accounts/{id}/followers | List the followers of an account |
| [**listAccountFollowing**](AccountsApi.md#listAccountFollowing) | **GET** /accounts/{id}/following | List the accounts that an account follows |
| [**listAccountSuggestions**](AccountsApi.md#listAccountSuggestions) | **GET** /accounts/suggestions | Suggest accounts to follow |
| [**listBlocks**](AccountsApi.md#listBlocks) | **GET** /blocks | List the accounts that the viewer blocks |
| [**listDataExports**](AccountsApi.md#listDataExports) | **GET** /exports | List the authenticated user&#39;s data exports |
| [**listDataImports**](AccountsApi.md#listDataImports) | **GET** /imports | List the authenticated user&#39;s imports |
| [**listDomainMutes**](AccountsApi.md#listDomainMutes) | **GET** /domain_mutes | List the domains that the viewer mutes |
| [**listFeaturedTags**](AccountsApi.md#listFeaturedTags) | **GET** /featured_tags | List the hashtags the user features |
| [**listFollowRequests**](AccountsApi.md#listFollowRequests) | **GET** /follow_requests | List the follow requests to the viewer |
| [**listMutes**](AccountsApi.md#listMutes) | **GET** /mutes | List the accounts that the viewer mutes |
| [**listSubscriptions**](AccountsApi.md#listSubscriptions) | **GET** /subscriptions | List the accounts whose new posts notify the viewer |
| [**lookupAccount**](AccountsApi.md#lookupAccount) | **GET** /accounts/lookup | Look up or resolve an account by its handle or URL |
| [**moveOwnAccount**](AccountsApi.md#moveOwnAccount) | **POST** /accounts/move | Move the authenticated user&#39;s account to another account |
| [**muteAccount**](AccountsApi.md#muteAccount) | **POST** /accounts/{id}/mute | Mute an account |
| [**muteDomain**](AccountsApi.md#muteDomain) | **POST** /domain_mutes | Mute a domain |
| [**previewDataImport**](AccountsApi.md#previewDataImport) | **POST** /imports/preview | Preview an import of a CSV file |
| [**rejectFollowRequest**](AccountsApi.md#rejectFollowRequest) | **POST** /follow_requests/{id}/reject | Reject a follow request |
| [**removeAccountAlias**](AccountsApi.md#removeAccountAlias) | **DELETE** /accounts/aliases | Remove an alias of the authenticated user |
| [**removeFollower**](AccountsApi.md#removeFollower) | **DELETE** /accounts/{id}/follower | Remove an account from the viewer&#39;s followers |
| [**removePassword**](AccountsApi.md#removePassword) | **POST** /accounts/password/remove | Remove the authenticated user&#39;s password |
| [**requestDataExport**](AccountsApi.md#requestDataExport) | **POST** /exports | Request an archive of the authenticated user&#39;s data |
| [**resendVerificationEmail**](AccountsApi.md#resendVerificationEmail) | **POST** /accounts/email/verification | Send the authenticated user a new verification email |
| [**searchAccounts**](AccountsApi.md#searchAccounts) | **GET** /accounts/search | Search accounts by name |
| [**setAccountNote**](AccountsApi.md#setAccountNote) | **PUT** /accounts/{id}/note | Write a private note on an account |
| [**setFeaturedTags**](AccountsApi.md#setFeaturedTags) | **PUT** /featured_tags | Choose the hashtags the user features |
| [**startDataImport**](AccountsApi.md#startDataImport) | **POST** /imports | Import a CSV file of follows, followed hashtags, blocks, mutes, muted domains, or bookmarks |
| [**subscribeAccount**](AccountsApi.md#subscribeAccount) | **POST** /accounts/{id}/subscribe | Be notified of an account&#39;s new posts |
| [**unblockAccount**](AccountsApi.md#unblockAccount) | **DELETE** /accounts/{id}/block | Unblock an account |
| [**unfollowAccount**](AccountsApi.md#unfollowAccount) | **DELETE** /accounts/{id}/follow | Unfollow an account |
| [**unmuteAccount**](AccountsApi.md#unmuteAccount) | **DELETE** /accounts/{id}/mute | Unmute an account |
| [**unmuteDomain**](AccountsApi.md#unmuteDomain) | **DELETE** /domain_mutes/{domain} | Unmute a domain |
| [**unsubscribeAccount**](AccountsApi.md#unsubscribeAccount) | **DELETE** /accounts/{id}/subscribe | Stop being notified of an account&#39;s new posts |
| [**updateAccountCredentials**](AccountsApi.md#updateAccountCredentials) | **PATCH** /accounts/update_credentials | Change the authenticated user&#39;s profile |
| [**verifyAccountCredentials**](AccountsApi.md#verifyAccountCredentials) | **GET** /accounts/verify_credentials | Get the authenticated user&#39;s own account |


<a id="acceptFollowRequest"></a>
# **acceptFollowRequest**
> Relationship acceptFollowRequest(id, idempotencyKey)

Accept a follow request

Makes the account&#39;s pending follow of the viewer active. Accepting a follow that is already active changes nothing; without a follow it is a 404. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AccountsApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The account ID.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : Relationship = apiInstance.acceptFollowRequest(id, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AccountsApi#acceptFollowRequest")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AccountsApi#acceptFollowRequest")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The account ID. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**Relationship**](Relationship.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="addAccountAlias"></a>
# **addAccountAlias**
> AccountAliasList addAccountAlias(addAccountAliasRequest, idempotencyKey)

Add an alias to the authenticated user

Adds the account that the user moves from as an alias: the user&#39;s actor names it in &#x60;alsoKnownAs&#x60;, which lets that account move its followers to the user, and the actor&#39;s &#x60;Update&#x60; goes to the user&#39;s followers as a profile change does. An alias alone changes nothing else. &#x60;acct&#x60; is a handle or a URL, resolved to the account&#39;s actor as &#x60;GET /accounts/lookup?resolve&#x3D;true&#x60; resolves it: WebFinger and the fetch of the actor, limited to 30 fetches per account every 5 minutes (429 with &#x60;Retry-After&#x60;).  A handle or URL that leads to no account, the URL of something that is not an actor, the instance actor, and the user itself are a 422 &#x60;/api/v1/problems/invalid-alias&#x60;; so is an account that the instance does not know while it takes nothing in from other servers (&#x60;Instance.federation.mode&#x60; &#x60;off&#x60; or &#x60;outbound&#x60;). A user has at most 5 aliases; one more is a 422 &#x60;/api/v1/problems/too-many-aliases&#x60;. Adding an alias that the user has already changes nothing. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AccountsApi()
val addAccountAliasRequest : AddAccountAliasRequest =  // AddAccountAliasRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AccountAliasList = apiInstance.addAccountAlias(addAccountAliasRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AccountsApi#addAccountAlias")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AccountsApi#addAccountAlias")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **addAccountAliasRequest** | [**AddAccountAliasRequest**](AddAccountAliasRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AccountAliasList**](AccountAliasList.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="blockAccount"></a>
# **blockAccount**
> Relationship blockAccount(id, idempotencyKey)

Block an account

Ends the follows between the viewer and the account in both directions and keeps either from following the other until the block is lifted. Blocking an account that the viewer already blocks changes nothing, and an account that blocks the viewer can be blocked back. Blocking a remote account also sends a Block to its server. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AccountsApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The account ID.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : Relationship = apiInstance.blockAccount(id, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AccountsApi#blockAccount")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AccountsApi#blockAccount")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The account ID. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**Relationship**](Relationship.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="cancelOwnAccountMove"></a>
# **cancelOwnAccountMove**
> cancelOwnAccountMove(idempotencyKey)

Cancel the move of the authenticated user&#39;s account

Cancels the move of the user&#39;s account: the account no longer names the target, can post, boost, react, follow, and vote again, and the other servers get an &#x60;Update&#x60; of its actor without &#x60;movedTo&#x60;. The followers that moved stay with the target, and the next move still waits 30 days from the canceled one. An account that has not moved gets a 409 &#x60;/api/v1/problems/account-not-moved&#x60;. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AccountsApi()
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    apiInstance.cancelOwnAccountMove(idempotencyKey)
} catch (e: ClientException) {
    println("4xx response calling AccountsApi#cancelOwnAccountMove")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AccountsApi#cancelOwnAccountMove")
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

<a id="changePassword"></a>
# **changePassword**
> changePassword(changePasswordRequest, idempotencyKey)

Change or set the authenticated user&#39;s password

While registration and sign-in are limited to the login providers (&#x60;login_providers_only&#x60; of &#x60;GET /instance&#x60;), it is a 403 &#x60;/api/v1/problems/login-providers-only&#x60;: a new password could not sign in. Replaces the user&#39;s password with &#x60;new_password&#x60; after checking &#x60;current_password&#x60; or a &#x60;passkey&#x60; of the user; a user without a password sets one with a passkey. Every other session and grant of the user ends: the user is signed out of the server&#39;s sign-in pages and of every app but the one that makes the request, whose tokens keep working. Passkeys and two-factor authentication stay as they are, and the user is told by email when the server sends email. A wrong current password is a 422 &#x60;/api/v1/problems/wrong-password&#x60;, a passkey that does not verify a 422 &#x60;/api/v1/problems/invalid-passkey&#x60;, and an expired confirmation a 422 &#x60;/api/v1/problems/passkey-confirmation-expired&#x60;; at most 5 wrong ones per user are accepted every hour. A new password that is not 8 to 128 characters is a 422 that names &#x60;new_password&#x60;. An &#x60;external_confirmation&#x60; from a linked login provider (&#x60;POST /linked_identities/confirmation&#x60;) takes the place of the password; an unknown, expired, or used one, or one for another operation, is a 422 &#x60;/api/v1/problems/external-confirmation-expired&#x60;. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AccountsApi()
val changePasswordRequest : ChangePasswordRequest =  // ChangePasswordRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    apiInstance.changePassword(changePasswordRequest, idempotencyKey)
} catch (e: ClientException) {
    println("4xx response calling AccountsApi#changePassword")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AccountsApi#changePassword")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **changePasswordRequest** | [**ChangePasswordRequest**](ChangePasswordRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

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

<a id="deactivateOwnAccount"></a>
# **deactivateOwnAccount**
> Deactivation deactivateOwnAccount(deactivateOwnAccountRequest, idempotencyKey)

Deactivate the authenticated user&#39;s account

Deactivates the user&#39;s own account, after checking the user&#39;s current &#x60;password&#x60;, a &#x60;two_factor_code&#x60;, a &#x60;passkey&#x60; of the user, or an &#x60;external_confirmation&#x60; from a linked login provider. The account and its posts are hidden from everyone, every session and token of the user ends, and the other servers get an &#x60;Update&#x60; of the actor marked &#x60;suspended&#x60;. Where the server sends mail, one email states the date of the deletion.  The user signs in again to reactivate it, on a page of the server, within 30 days; after that the account is deleted, and the other servers get a &#x60;Delete&#x60; of the actor. While it is deactivated, nothing reaches it: mentions, direct messages, notifications, emails, Web Push, and new followers. A deactivated account can be deactivated again 24 hours after its reactivation.  A wrong password is a 422 &#x60;/api/v1/problems/wrong-password&#x60;, a wrong code a 422 &#x60;/api/v1/problems/invalid-two-factor-code&#x60;, a passkey that does not verify a 422 &#x60;/api/v1/problems/invalid-passkey&#x60;, and an expired confirmation a 422 &#x60;/api/v1/problems/passkey-confirmation-expired&#x60; or &#x60;/api/v1/problems/external-confirmation-expired&#x60;. An account that cannot be deactivated now is a 409 &#x60;/api/v1/problems/deactivation-refused&#x60;, whose detail says why: moderation keeps it from logging in, its deletion is scheduled, it moved, its registration waits for review, a paid subscription goes on, no other administrator can sign in, or it was reactivated less than 24 hours ago. At most 5 attempts per account are accepted every hour. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AccountsApi()
val deactivateOwnAccountRequest : DeactivateOwnAccountRequest =  // DeactivateOwnAccountRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : Deactivation = apiInstance.deactivateOwnAccount(deactivateOwnAccountRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AccountsApi#deactivateOwnAccount")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AccountsApi#deactivateOwnAccount")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **deactivateOwnAccountRequest** | [**DeactivateOwnAccountRequest**](DeactivateOwnAccountRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**Deactivation**](Deactivation.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="deleteAccountNote"></a>
# **deleteAccountNote**
> Relationship deleteAccountNote(id, idempotencyKey)

Remove the private note on an account

Without a note it changes nothing. It also works on an account that blocks the user.

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AccountsApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The account ID.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : Relationship = apiInstance.deleteAccountNote(id, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AccountsApi#deleteAccountNote")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AccountsApi#deleteAccountNote")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The account ID. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**Relationship**](Relationship.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="deleteOwnAccount"></a>
# **deleteOwnAccount**
> deleteOwnAccount(deleteOwnAccountRequest, idempotencyKey)

Delete the authenticated user&#39;s account

Deletes the user&#39;s own account at once, after checking the user&#39;s current &#x60;password&#x60; or a &#x60;passkey&#x60; of the user; a user without a password confirms with a passkey. The account and its posts disappear, every session and token of the user ends, and the other servers get a &#x60;Delete&#x60; of the actor; the data is erased 7 days later. It cannot be undone, and the username is never reused. The only administrator of the instance gets a 409 &#x60;/api/v1/problems/last-administrator&#x60; once the confirmation passes. A wrong password is a 422 &#x60;/api/v1/problems/wrong-password&#x60;, a passkey that does not verify a 422 &#x60;/api/v1/problems/invalid-passkey&#x60;, and an expired confirmation a 422 &#x60;/api/v1/problems/passkey-confirmation-expired&#x60;; at most 5 attempts per account are accepted every hour. An &#x60;external_confirmation&#x60; from a linked login provider (&#x60;POST /linked_identities/confirmation&#x60;) takes the place of the password; an unknown, expired, or used one, or one for another operation, is a 422 &#x60;/api/v1/problems/external-confirmation-expired&#x60;. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AccountsApi()
val deleteOwnAccountRequest : DeleteOwnAccountRequest =  // DeleteOwnAccountRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    apiInstance.deleteOwnAccount(deleteOwnAccountRequest, idempotencyKey)
} catch (e: ClientException) {
    println("4xx response calling AccountsApi#deleteOwnAccount")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AccountsApi#deleteOwnAccount")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **deleteOwnAccountRequest** | [**DeleteOwnAccountRequest**](DeleteOwnAccountRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

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

<a id="downloadDataExport"></a>
# **downloadDataExport**
> java.io.File downloadDataExport(id)

Download the archive of a data export

Returns the ZIP archive of one of the user&#39;s exports. An export that is not &#x60;ready&#x60; is a 409 &#x60;/api/v1/problems/export-not-ready&#x60;; another user&#39;s export is a 404. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AccountsApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the export.
try {
    val result : java.io.File = apiInstance.downloadDataExport(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AccountsApi#downloadDataExport")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AccountsApi#downloadDataExport")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the export. | |

### Return type

[**java.io.File**](java.io.File.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/zip, application/problem+json

<a id="exportCsv"></a>
# **exportCsv**
> kotlin.String exportCsv(kind)

Export the authenticated user&#39;s follows, followed hashtags, blocks, mutes, muted domains, or bookmarks as CSV

Returns what the user has of &#x60;kind&#x60; now as the CSV file that Mastodon exports, which Mastodon and this server import: &#x60;following&#x60; is &#x60;following_accounts.csv&#x60; (&#x60;Account address,Show boosts,Notify on new posts,Languages&#x60;, the active follows), &#x60;blocks&#x60; is &#x60;blocked_accounts.csv&#x60; (one address per line), &#x60;mutes&#x60; is &#x60;muted_accounts.csv&#x60; (&#x60;Account address,Hide notifications&#x60;, the mutes in effect), &#x60;domain_mutes&#x60; is &#x60;blocked_domains.csv&#x60; (one domain per line, the domains the user mutes, which Mastodon imports as blocked domains), and &#x60;bookmarks&#x60; is &#x60;bookmarks.csv&#x60; (one post URI per line, the posts the user may still see). &#x60;followed_tags&#x60; is &#x60;followed_hashtags.csv&#x60; (one hashtag per line without its &#x60;#&#x60;, the latest followed first), a file of this server that Mastodon neither exports nor imports. Addresses are &#x60;username@domain&#x60;, with the account domain for this server&#39;s accounts; remote accounts whose handle is unconfirmed are left out. At most 30 exports per account are accepted every hour. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AccountsApi()
val kind : DataImportKind =  // DataImportKind | What to export.
try {
    val result : kotlin.String = apiInstance.exportCsv(kind)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AccountsApi#exportCsv")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AccountsApi#exportCsv")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **kind** | [**DataImportKind**](.md)| What to export. | [enum: following, followed_tags, blocks, mutes, domain_mutes, bookmarks] |

### Return type

**kotlin.String**

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: text/csv, application/problem+json

<a id="followAccount"></a>
# **followAccount**
> Relationship followAccount(id, idempotencyKey, followAccountRequest)

Follow an account

Follows the account at once, or sends a follow request when the account is locked: then &#x60;requested&#x60; is true until the account accepts it. Following an account that the viewer already follows or has requested to follow changes only the preferences in the body. Following an account that the viewer blocks is a 409 &#x60;/api/v1/problems/blocking&#x60;, following oneself is a 422, and following an account while moderation keeps the viewer from interacting with it is a 403. A user whose account moved gets a 403 &#x60;/api/v1/problems/account-moved&#x60;. A follow of an account on another server is a request until that server accepts it. While the instance does not follow remote accounts (&#x60;Instance.federation.mode&#x60; &#x60;off&#x60; or &#x60;outbound&#x60;), following an account on another server that the viewer does not follow or request yet is a 403 &#x60;/api/v1/problems/federation-restricted&#x60;. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AccountsApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The account ID.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
val followAccountRequest : FollowAccountRequest =  // FollowAccountRequest | 
try {
    val result : Relationship = apiInstance.followAccount(id, idempotencyKey, followAccountRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AccountsApi#followAccount")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AccountsApi#followAccount")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The account ID. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |
| **followAccountRequest** | [**FollowAccountRequest**](FollowAccountRequest.md)|  | [optional] |

### Return type

[**Relationship**](Relationship.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="getAccount"></a>
# **getAccount**
> Account getAccount(id)

Get an account

An account that blocks the viewer, is deactivated, or is deleted is a 404, the same as an unknown one. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AccountsApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The account ID.
try {
    val result : Account = apiInstance.getAccount(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AccountsApi#getAccount")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AccountsApi#getAccount")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The account ID. | |

### Return type

[**Account**](Account.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="getDeactivationPreview"></a>
# **getDeactivationPreview**
> DeactivationPreview getDeactivationPreview()

Get what deactivating the authenticated user&#39;s account would do now

The date the account would be deleted if the user deactivated it now, and why it cannot be deactivated now, if so. It changes nothing. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AccountsApi()
try {
    val result : DeactivationPreview = apiInstance.getDeactivationPreview()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AccountsApi#getDeactivationPreview")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AccountsApi#getDeactivationPreview")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**DeactivationPreview**](DeactivationPreview.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="getPasswordStatus"></a>
# **getPasswordStatus**
> PasswordStatus getPasswordStatus()

Get whether the authenticated user has a password

Whether the user signs in with a password, or with passkeys only after removing it. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AccountsApi()
try {
    val result : PasswordStatus = apiInstance.getPasswordStatus()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AccountsApi#getPasswordStatus")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AccountsApi#getPasswordStatus")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**PasswordStatus**](PasswordStatus.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="getRelationships"></a>
# **getRelationships**
> RelationshipList getRelationships(id)

Get the viewer&#39;s relationships with accounts

Returns one relationship per requested account, in the order of the request. Accounts that do not exist, are not active, or block the viewer are left out. &#x60;blocking&#x60; needs &#x60;read:blocks&#x60; and &#x60;muting&#x60; and &#x60;muting_notifications&#x60; need &#x60;read:mutes&#x60; as well: without them the fields are absent. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AccountsApi()
val id : kotlin.collections.List<java.util.UUID> =  // kotlin.collections.List<java.util.UUID> | The account IDs, repeated (`?id=a&id=b`).
try {
    val result : RelationshipList = apiInstance.getRelationships(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AccountsApi#getRelationships")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AccountsApi#getRelationships")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | [**kotlin.collections.List&lt;java.util.UUID&gt;**](java.util.UUID.md)| The account IDs, repeated (&#x60;?id&#x3D;a&amp;id&#x3D;b&#x60;). | |

### Return type

[**RelationshipList**](RelationshipList.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listAccountAliases"></a>
# **listAccountAliases**
> AccountAliasList listAccountAliases()

List the authenticated user&#39;s aliases

The accounts that the user moves from, which the user&#39;s actor names in &#x60;alsoKnownAs&#x60;, oldest first. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AccountsApi()
try {
    val result : AccountAliasList = apiInstance.listAccountAliases()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AccountsApi#listAccountAliases")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AccountsApi#listAccountAliases")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**AccountAliasList**](AccountAliasList.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listAccountFeaturedTags"></a>
# **listAccountFeaturedTags**
> FeaturedHashtagCollection listAccountFeaturedTags(id)

List the hashtags an account features

The hashtags that the account features on its profile (at most 10), in the order it chose. For a remote account they are those its server lists, as this server last fetched them with the account. The counts read only the account&#39;s &#x60;public&#x60; and &#x60;unlisted&#x60; posts that this server has, so a remote account&#39;s may be lower than on its server.  An account that is not found by &#x60;getAccount&#x60; is a 404 here too: an unknown, suspended, deactivated, or deleted account, the instance actor, and an account that blocks the viewer. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AccountsApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The account.
try {
    val result : FeaturedHashtagCollection = apiInstance.listAccountFeaturedTags(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AccountsApi#listAccountFeaturedTags")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AccountsApi#listAccountFeaturedTags")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The account. | |

### Return type

[**FeaturedHashtagCollection**](FeaturedHashtagCollection.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listAccountFollowers"></a>
# **listAccountFollowers**
> AccountPage listAccountFollowers(id, cursor, limit)

List the followers of an account

The accounts that actively follow the account, newest follow first. Followers that block the viewer, are deactivated, or are deleted are left out, so a page may hold fewer than &#x60;limit&#x60; accounts before the last.  When the account&#39;s &#x60;follows_visibility&#x60; keeps its followers from the viewer, the answer is &#x60;/api/v1/problems/follows-hidden&#x60; (403); the owner always reads them. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AccountsApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The account ID.
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : AccountPage = apiInstance.listAccountFollowers(id, cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AccountsApi#listAccountFollowers")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AccountsApi#listAccountFollowers")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The account ID. | |
| **cursor** | **kotlin.String**| The &#x60;next_cursor&#x60; of the previous page; omitted for the first page. | [optional] |
| **limit** | **kotlin.Int**| The maximum number of items in the page. | [optional] [default to 20] |

### Return type

[**AccountPage**](AccountPage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listAccountFollowing"></a>
# **listAccountFollowing**
> AccountPage listAccountFollowing(id, cursor, limit)

List the accounts that an account follows

The accounts that the account actively follows, newest follow first, leaving out the same accounts as &#x60;listAccountFollowers&#x60;.  When the account&#39;s &#x60;follows_visibility&#x60; keeps its follows from the viewer, the answer is &#x60;/api/v1/problems/follows-hidden&#x60; (403); the owner always reads them. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AccountsApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The account ID.
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : AccountPage = apiInstance.listAccountFollowing(id, cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AccountsApi#listAccountFollowing")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AccountsApi#listAccountFollowing")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The account ID. | |
| **cursor** | **kotlin.String**| The &#x60;next_cursor&#x60; of the previous page; omitted for the first page. | [optional] |
| **limit** | **kotlin.Int**| The maximum number of items in the page. | [optional] [default to 20] |

### Return type

[**AccountPage**](AccountPage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listAccountSuggestions"></a>
# **listAccountSuggestions**
> AccountSuggestionList listAccountSuggestions(limit)

Suggest accounts to follow

Accounts of this server for the viewer to follow: first the accounts that the administrators feature (the instance setting &#x60;featured_accounts&#x60;), in the order they gave, then the accounts that published a public or local post in the last 30 days, the one that posted last first. Each account comes once.  Only accounts that are &#x60;discoverable&#x60; are suggested. The viewer itself, the accounts that it follows, the accounts that it blocks, mutes, or is blocked by, and accounts that are deactivated, deleted, or suspended are left out. There is no paging.  On a one-person server (&#x60;Instance.single_user&#x60;) it is a 404 of the type &#x60;/api/v1/problems/single-user-mode&#x60;. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AccountsApi()
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of suggestions.
try {
    val result : AccountSuggestionList = apiInstance.listAccountSuggestions(limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AccountsApi#listAccountSuggestions")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AccountsApi#listAccountSuggestions")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **limit** | **kotlin.Int**| The maximum number of suggestions. | [optional] [default to 10] |

### Return type

[**AccountSuggestionList**](AccountSuggestionList.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listBlocks"></a>
# **listBlocks**
> AccountPage listBlocks(cursor, limit)

List the accounts that the viewer blocks

Newest block first. Deactivated and deleted accounts are left out.

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AccountsApi()
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : AccountPage = apiInstance.listBlocks(cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AccountsApi#listBlocks")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AccountsApi#listBlocks")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **cursor** | **kotlin.String**| The &#x60;next_cursor&#x60; of the previous page; omitted for the first page. | [optional] |
| **limit** | **kotlin.Int**| The maximum number of items in the page. | [optional] [default to 20] |

### Return type

[**AccountPage**](AccountPage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listDataExports"></a>
# **listDataExports**
> DataExportList listDataExports()

List the authenticated user&#39;s data exports

Lists the user&#39;s latest 10 data exports, newest first. An archive can be downloaded while its export is &#x60;ready&#x60;; exports are removed 7 days after they end. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AccountsApi()
try {
    val result : DataExportList = apiInstance.listDataExports()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AccountsApi#listDataExports")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AccountsApi#listDataExports")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**DataExportList**](DataExportList.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listDataImports"></a>
# **listDataImports**
> DataImportList listDataImports()

List the authenticated user&#39;s imports

Lists the user&#39;s latest 10 imports of CSV files, newest first, with their progress. Imports are removed 7 days after they start. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AccountsApi()
try {
    val result : DataImportList = apiInstance.listDataImports()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AccountsApi#listDataImports")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AccountsApi#listDataImports")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**DataImportList**](DataImportList.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listDomainMutes"></a>
# **listDomainMutes**
> DomainMutePage listDomainMutes(cursor, limit)

List the domains that the viewer mutes

In the order of the domains.

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AccountsApi()
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : DomainMutePage = apiInstance.listDomainMutes(cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AccountsApi#listDomainMutes")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AccountsApi#listDomainMutes")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **cursor** | **kotlin.String**| The &#x60;next_cursor&#x60; of the previous page; omitted for the first page. | [optional] |
| **limit** | **kotlin.Int**| The maximum number of items in the page. | [optional] [default to 20] |

### Return type

[**DomainMutePage**](DomainMutePage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listFeaturedTags"></a>
# **listFeaturedTags**
> FeaturedHashtagCollection listFeaturedTags()

List the hashtags the user features

The hashtags that the user features on their profile (at most 10), in the order the user chose, with how many of the user&#39;s &#x60;public&#x60; and &#x60;unlisted&#x60; posts carry each. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AccountsApi()
try {
    val result : FeaturedHashtagCollection = apiInstance.listFeaturedTags()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AccountsApi#listFeaturedTags")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AccountsApi#listFeaturedTags")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**FeaturedHashtagCollection**](FeaturedHashtagCollection.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listFollowRequests"></a>
# **listFollowRequests**
> AccountPage listFollowRequests(cursor, limit)

List the follow requests to the viewer

The accounts whose follow requests await the viewer&#39;s approval, newest first. Deactivated and deleted accounts are left out. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AccountsApi()
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : AccountPage = apiInstance.listFollowRequests(cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AccountsApi#listFollowRequests")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AccountsApi#listFollowRequests")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **cursor** | **kotlin.String**| The &#x60;next_cursor&#x60; of the previous page; omitted for the first page. | [optional] |
| **limit** | **kotlin.Int**| The maximum number of items in the page. | [optional] [default to 20] |

### Return type

[**AccountPage**](AccountPage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listMutes"></a>
# **listMutes**
> MutePage listMutes(cursor, limit)

List the accounts that the viewer mutes

Mutes that have not expired, newest first. Deactivated and deleted accounts are left out. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AccountsApi()
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : MutePage = apiInstance.listMutes(cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AccountsApi#listMutes")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AccountsApi#listMutes")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **cursor** | **kotlin.String**| The &#x60;next_cursor&#x60; of the previous page; omitted for the first page. | [optional] |
| **limit** | **kotlin.Int**| The maximum number of items in the page. | [optional] [default to 20] |

### Return type

[**MutePage**](MutePage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listSubscriptions"></a>
# **listSubscriptions**
> AccountPage listSubscriptions(cursor, limit)

List the accounts whose new posts notify the viewer

The viewer&#39;s subscriptions to new posts (&#x60;subscribeAccount&#x60;), newest first. Accounts that the viewer may not see, deactivated, and deleted accounts are left out. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AccountsApi()
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : AccountPage = apiInstance.listSubscriptions(cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AccountsApi#listSubscriptions")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AccountsApi#listSubscriptions")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **cursor** | **kotlin.String**| The &#x60;next_cursor&#x60; of the previous page; omitted for the first page. | [optional] |
| **limit** | **kotlin.Int**| The maximum number of items in the page. | [optional] [default to 20] |

### Return type

[**AccountPage**](AccountPage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="lookupAccount"></a>
# **lookupAccount**
> Account lookupAccount(acct, resolve)

Look up or resolve an account by its handle or URL

Finds &#x60;username&#x60; (a local account), &#x60;username@domain&#x60;, or the &#x60;https&#x60; or &#x60;http&#x60; URL of an actor among the accounts that the instance knows: its &#x60;uri&#x60; always, and a profile page that serves the actor only with &#x60;resolve&#x60;, which fetches it. A leading &#x60;@&#x60; of a handle is ignored. An account that blocks the viewer, is deactivated, or is deleted is a 404, the same as an unknown one.  With &#x60;resolve&#x3D;true&#x60;, a remote account that the instance does not know yet is fetched from its server: a handle through WebFinger, a URL directly. Only accounts whose handle WebFinger confirms are returned; a handle or URL that does not lead to one is a 404. Resolving needs an access token (anonymous requests get the 401 of &#x60;/api/v1/problems/unauthenticated&#x60;) and is limited to 30 fetches per account every 5 minutes (429 with &#x60;Retry-After&#x60;); finding a known account is not limited. While the instance takes nothing in from other servers (&#x60;Instance.federation.mode&#x60; &#x60;off&#x60; or &#x60;outbound&#x60;), only the known accounts are found: resolving one that the instance does not know, or one that is a 404 as above, is a 403 &#x60;/api/v1/problems/federation-restricted&#x60;, and nothing is fetched. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AccountsApi()
val acct : kotlin.String = acct_example // kotlin.String | The handle, `username` or `username@domain`, or the `https` or `http` URL of an actor.
val resolve : kotlin.Boolean = true // kotlin.Boolean | Fetch a remote account that the instance does not know yet.
try {
    val result : Account = apiInstance.lookupAccount(acct, resolve)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AccountsApi#lookupAccount")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AccountsApi#lookupAccount")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **acct** | **kotlin.String**| The handle, &#x60;username&#x60; or &#x60;username@domain&#x60;, or the &#x60;https&#x60; or &#x60;http&#x60; URL of an actor. | |
| **resolve** | **kotlin.Boolean**| Fetch a remote account that the instance does not know yet. | [optional] [default to false] |

### Return type

[**Account**](Account.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="moveOwnAccount"></a>
# **moveOwnAccount**
> moveOwnAccount(moveOwnAccountRequest, idempotencyKey)

Move the authenticated user&#39;s account to another account

Moves the user&#39;s account to the account &#x60;target_account_id&#x60;, after checking the user&#39;s current &#x60;password&#x60; or a &#x60;passkey&#x60; of the user; a user without a password confirms with a passkey. Find the target&#39;s ID with &#x60;GET /accounts/lookup?resolve&#x3D;true&#x60;; it may be an account of this server.  The target is fetched again from its server, and it must list this account among its aliases (&#x60;alsoKnownAs&#x60;), which its owner sets there first. The move is refused with a 422: &#x60;/api/v1/problems/move-alias-missing&#x60; when the target does not list this account, &#x60;/api/v1/problems/move-target-moved&#x60; when the target moved itself, &#x60;/api/v1/problems/move-target-unavailable&#x60; when the target is this account, is not found, cannot be fetched, or is suspended here (it or its server), and &#x60;/api/v1/problems/move-too-soon&#x60; within 30 days of the account&#39;s last move, also when that move was canceled, with &#x60;can_move_at&#x60;, and &#x60;/api/v1/problems/move-repeated&#x60; for a target the account moved to before. &#x60;CredentialAccount.can_move_at&#x60; says beforehand from when the account can move again. An account that may not move (under a moderation action that stops it, say) is a 403 &#x60;/api/v1/problems/account-unavailable&#x60;. A wrong confirmation counts towards a limit of 5 an hour for the user, past which the answer is 429.  Once moved, the account&#39;s followers on other servers get a &#x60;Move&#x60; and follow the target, the followers on this server follow it too, and the account&#39;s profile names the target (&#x60;Account.moved&#x60;). The account can still sign in, read, export, delete itself, set its aliases, and cancel the move (&#x60;DELETE /accounts/move&#x60;); posting, boosting, reacting, following, and voting are refused with a 403 &#x60;/api/v1/problems/account-moved&#x60;. A wrong password is a 422 &#x60;/api/v1/problems/wrong-password&#x60;, a passkey that does not verify a 422 &#x60;/api/v1/problems/invalid-passkey&#x60;, and an expired confirmation a 422 &#x60;/api/v1/problems/passkey-confirmation-expired&#x60;; at most 10 attempts per account are accepted every hour. An &#x60;external_confirmation&#x60; from a linked login provider (&#x60;POST /linked_identities/confirmation&#x60;) takes the place of the password; an unknown, expired, or used one, or one for another operation, is a 422 &#x60;/api/v1/problems/external-confirmation-expired&#x60;. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AccountsApi()
val moveOwnAccountRequest : MoveOwnAccountRequest =  // MoveOwnAccountRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    apiInstance.moveOwnAccount(moveOwnAccountRequest, idempotencyKey)
} catch (e: ClientException) {
    println("4xx response calling AccountsApi#moveOwnAccount")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AccountsApi#moveOwnAccount")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **moveOwnAccountRequest** | [**MoveOwnAccountRequest**](MoveOwnAccountRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

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

<a id="muteAccount"></a>
# **muteAccount**
> Relationship muteAccount(id, idempotencyKey, muteAccountRequest)

Mute an account

Hides the account&#39;s posts, and unless &#x60;notifications&#x60; is false its notifications, from the viewer. Muting an account that the viewer already mutes replaces the settings and the expiry. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AccountsApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The account ID.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
val muteAccountRequest : MuteAccountRequest =  // MuteAccountRequest | 
try {
    val result : Relationship = apiInstance.muteAccount(id, idempotencyKey, muteAccountRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AccountsApi#muteAccount")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AccountsApi#muteAccount")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The account ID. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |
| **muteAccountRequest** | [**MuteAccountRequest**](MuteAccountRequest.md)|  | [optional] |

### Return type

[**Relationship**](Relationship.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="muteDomain"></a>
# **muteDomain**
> DomainMute muteDomain(muteDomainRequest, idempotencyKey)

Mute a domain

Hides the posts of a remote domain and of its subdomains from the viewer&#39;s timelines other than home, their boosts from home, and their notifications. The accounts of the domain that the viewer follows stay on the home timeline. Nothing is sent to the domain. Muting a domain that the viewer already mutes changes nothing. The domain is normalized (IDNA A-labels in lower case, without a trailing dot); one that is not a valid remote domain, or this server&#39;s own, is a 422 naming &#x60;domain&#x60;. A viewer mutes at most 1000 domains; one more is a 422 &#x60;/api/v1/problems/too-many-domain-mutes&#x60;. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AccountsApi()
val muteDomainRequest : MuteDomainRequest =  // MuteDomainRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : DomainMute = apiInstance.muteDomain(muteDomainRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AccountsApi#muteDomain")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AccountsApi#muteDomain")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **muteDomainRequest** | [**MuteDomainRequest**](MuteDomainRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**DomainMute**](DomainMute.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="previewDataImport"></a>
# **previewDataImport**
> DataImportPreview previewDataImport(dataImportRequest, idempotencyKey)

Preview an import of a CSV file

Reads a CSV file as &#x60;startDataImport&#x60; would and tells what importing it would do, changing nothing. The header row is optional, a leading &#x60;@&#x60; of an address is dropped, and blank lines are ignored; a line that names no address (or for &#x60;bookmarks&#x60;, no &#x60;http(s)&#x60; URL; for &#x60;followed_tags&#x60;, nothing that can be a hashtag, with or without its &#x60;#&#x60;) is skipped as &#x60;invalid&#x60;, and one that repeats an earlier entry as &#x60;duplicate&#x60;. Settings left out default as in Mastodon: boosts shown, no notifications of new posts, notifications of a muted account hidden. The &#x60;Languages&#x60; column of a follow is ignored. A file that is not CSV or has more than 10,000 entries is a 422. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AccountsApi()
val dataImportRequest : DataImportRequest =  // DataImportRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : DataImportPreview = apiInstance.previewDataImport(dataImportRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AccountsApi#previewDataImport")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AccountsApi#previewDataImport")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **dataImportRequest** | [**DataImportRequest**](DataImportRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**DataImportPreview**](DataImportPreview.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="rejectFollowRequest"></a>
# **rejectFollowRequest**
> Relationship rejectFollowRequest(id, idempotencyKey)

Reject a follow request

Declines the account&#39;s pending follow request; without one it is a 404.

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AccountsApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The account ID.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : Relationship = apiInstance.rejectFollowRequest(id, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AccountsApi#rejectFollowRequest")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AccountsApi#rejectFollowRequest")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The account ID. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**Relationship**](Relationship.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="removeAccountAlias"></a>
# **removeAccountAlias**
> AccountAliasList removeAccountAlias(uri, idempotencyKey)

Remove an alias of the authenticated user

Removes an alias; the actor&#39;s &#x60;Update&#x60; goes to the user&#39;s followers as a profile change does. Removing an alias that the user does not have changes nothing. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AccountsApi()
val uri : kotlin.String = uri_example // kotlin.String | The `uri` of the alias, as `GET /accounts/aliases` lists it.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : AccountAliasList = apiInstance.removeAccountAlias(uri, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AccountsApi#removeAccountAlias")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AccountsApi#removeAccountAlias")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **uri** | **kotlin.String**| The &#x60;uri&#x60; of the alias, as &#x60;GET /accounts/aliases&#x60; lists it. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**AccountAliasList**](AccountAliasList.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="removeFollower"></a>
# **removeFollower**
> Relationship removeFollower(id, idempotencyKey)

Remove an account from the viewer&#39;s followers

Ends the account&#39;s active follow of the viewer. Without one it changes nothing; a pending request is declined with &#x60;rejectFollowRequest&#x60; instead. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AccountsApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The account ID.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : Relationship = apiInstance.removeFollower(id, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AccountsApi#removeFollower")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AccountsApi#removeFollower")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The account ID. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**Relationship**](Relationship.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="removePassword"></a>
# **removePassword**
> removePassword(removePasswordRequest, idempotencyKey)

Remove the authenticated user&#39;s password

Removes the password of a user who has a passkey or a linked account, after checking a &#x60;passkey&#x60; of the user or an &#x60;external_confirmation&#x60; from a linked login provider: from then on only the passkeys and the linked accounts sign in, and a user with two-factor authentication still enters a code after one. A password reset link or &#x60;POST /accounts/password&#x60; with a passkey sets a password again. Every other session and grant of the user ends, as a change of the password does, and the user is told by email when the server sends email. A user without a password gets a 204 and nothing changes. A user without a passkey and without a linked account gets a 409 &#x60;/api/v1/problems/last-sign-in-method&#x60;; a passkey that does not verify is a 422 &#x60;/api/v1/problems/invalid-passkey&#x60;, an expired confirmation a 422 &#x60;/api/v1/problems/passkey-confirmation-expired&#x60;, and at most 5 wrong ones per user are accepted every hour. An unknown, expired, or used &#x60;external_confirmation&#x60; is a 422 &#x60;/api/v1/problems/external-confirmation-expired&#x60;. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AccountsApi()
val removePasswordRequest : RemovePasswordRequest =  // RemovePasswordRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    apiInstance.removePassword(removePasswordRequest, idempotencyKey)
} catch (e: ClientException) {
    println("4xx response calling AccountsApi#removePassword")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AccountsApi#removePassword")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **removePasswordRequest** | [**RemovePasswordRequest**](RemovePasswordRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

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

<a id="requestDataExport"></a>
# **requestDataExport**
> DataExport requestDataExport(idempotencyKey)

Request an archive of the authenticated user&#39;s data

Starts building a ZIP archive of the user&#39;s data: &#x60;actor.json&#x60; (the actor), &#x60;outbox.json&#x60; (every post as an ActivityStreams &#x60;OrderedCollection&#x60; of &#x60;Create&#x60; activities), &#x60;moderation.json&#x60; (the moderation actions on the account, its appeals, and the reports it filed), &#x60;keyword_filters.json&#x60; (the user&#39;s keyword filters), &#x60;account_notes.json&#x60; (the user&#39;s private notes on accounts, with each account&#39;s ID and actor URI), and the CSV files of &#x60;exportCsv&#x60; (&#x60;following_accounts.csv&#x60;, &#x60;blocked_accounts.csv&#x60;, &#x60;muted_accounts.csv&#x60;, &#x60;blocked_domains.csv&#x60;, &#x60;bookmarks.csv&#x60;). The export is &#x60;pending&#x60; until the archive is built. While another export is &#x60;pending&#x60; the request is a 409 &#x60;/api/v1/problems/export-in-progress&#x60;; at most 3 requests per account are accepted every day. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AccountsApi()
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : DataExport = apiInstance.requestDataExport(idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AccountsApi#requestDataExport")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AccountsApi#requestDataExport")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**DataExport**](DataExport.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="resendVerificationEmail"></a>
# **resendVerificationEmail**
> resendVerificationEmail(idempotencyKey)

Send the authenticated user a new verification email

Sends a new verification email to the user&#39;s primary email address, such as after a &#x60;hard_bounce&#x60; suppressed the address (&#x60;Preferences.email_suppression&#x60;): following its link shows that the address works and lifts the suppression. Earlier links keep working until they expire. The email is queued and sent in the background, so a 202 does not tell whether it arrived. A user whose address is verified and not suppressed by a hard bounce gets a 409 &#x60;/api/v1/problems/email-already-verified&#x60;; a user without an email address, or an instance that sends no email (&#x60;Instance.sends_email&#x60;), a 404. At most 3 emails per user are sent every hour; more get a 429 with &#x60;Retry-After&#x60;. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AccountsApi()
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    apiInstance.resendVerificationEmail(idempotencyKey)
} catch (e: ClientException) {
    println("4xx response calling AccountsApi#resendVerificationEmail")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AccountsApi#resendVerificationEmail")
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

<a id="searchAccounts"></a>
# **searchAccounts**
> AccountList searchAccounts(q, limit)

Search accounts by name

The accounts whose username, &#x60;username@domain&#x60;, or a word of the display name starts with &#x60;q&#x60;, and the accounts of this server whose username or display name contains &#x60;q&#x60;, for the autocomplete of mentions and the account search. The search covers the local accounts and the remote accounts that the instance knows with a handle WebFinger confirmed; it fetches nothing from other servers (look an unknown &#x60;username@domain&#x60; up with &#x60;GET /api/v1/accounts/lookup&#x60; and &#x60;resolve&#x3D;true&#x60;).  &#x60;q&#x60; is compared under NFKC and lowercase. A display name is matched whole and word by word, split at white space and without its &#x60;:shortcode:&#x60;s, so a display name in a script without spaces (Chinese, Japanese) matches from its start.  An account of this server that is not &#x60;discoverable&#x60; is found only by its whole username, unless the viewer follows it; a remote account&#39;s &#x60;discoverable&#x60; does not matter. Accounts that the viewer blocks, mutes, or is blocked by, accounts that are deactivated, deleted, or suspended, and accounts of a suspended domain are left out. An account&#39;s &#x60;indexable&#x60; does not matter. The results come in this order: an exact match of the username or &#x60;username@domain&#x60;, then the matches from the start of a name, then the accounts that the viewer follows, then the local accounts, then those with more followers. There is no paging. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AccountsApi()
val q : kotlin.String = q_example // kotlin.String | The start of a username, a `username@domain`, or a word of a display name: 1 to 100 characters after a leading `@`, which is ignored. Anything else is a 400. 
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of results of a prefix search for the autocomplete.
try {
    val result : AccountList = apiInstance.searchAccounts(q, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AccountsApi#searchAccounts")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AccountsApi#searchAccounts")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **q** | **kotlin.String**| The start of a username, a &#x60;username@domain&#x60;, or a word of a display name: 1 to 100 characters after a leading &#x60;@&#x60;, which is ignored. Anything else is a 400.  | |
| **limit** | **kotlin.Int**| The maximum number of results of a prefix search for the autocomplete. | [optional] [default to 8] |

### Return type

[**AccountList**](AccountList.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="setAccountNote"></a>
# **setAccountNote**
> Relationship setAccountNote(id, accountNoteUpdate, idempotencyKey)

Write a private note on an account

Replaces the viewer&#39;s private note on the account, a local or a remote one. Only the viewer reads the note (&#x60;Relationship.note&#x60;); the account is not told, and the note does not leave this server. An empty &#x60;note&#x60; removes the note. A note on the viewer&#39;s own account is a 422. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AccountsApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The account ID.
val accountNoteUpdate : AccountNoteUpdate =  // AccountNoteUpdate | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : Relationship = apiInstance.setAccountNote(id, accountNoteUpdate, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AccountsApi#setAccountNote")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AccountsApi#setAccountNote")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The account ID. | |
| **accountNoteUpdate** | [**AccountNoteUpdate**](AccountNoteUpdate.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**Relationship**](Relationship.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="setFeaturedTags"></a>
# **setFeaturedTags**
> FeaturedHashtagCollection setFeaturedTags(setFeaturedTagsRequest, idempotencyKey)

Choose the hashtags the user features

Replaces the hashtags that the user features on their profile with &#x60;names&#x60;, in their order; a hashtag that no post has used yet can be featured. The other servers that follow the user learn of the change as of a change of the profile. A name that is no hashtag is a 422, and more than 10 hashtags a 422 &#x60;/api/v1/problems/too-many-featured-tags&#x60;; either changes nothing. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AccountsApi()
val setFeaturedTagsRequest : SetFeaturedTagsRequest =  // SetFeaturedTagsRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : FeaturedHashtagCollection = apiInstance.setFeaturedTags(setFeaturedTagsRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AccountsApi#setFeaturedTags")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AccountsApi#setFeaturedTags")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **setFeaturedTagsRequest** | [**SetFeaturedTagsRequest**](SetFeaturedTagsRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**FeaturedHashtagCollection**](FeaturedHashtagCollection.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="startDataImport"></a>
# **startDataImport**
> DataImport startDataImport(dataImportRequest, idempotencyKey)

Import a CSV file of follows, followed hashtags, blocks, mutes, muted domains, or bookmarks

Imports a CSV file in the format that Mastodon exports, or for &#x60;followed_tags&#x60; that of this server (&#x60;exportCsv&#x60;). The file&#39;s entries are what &#x60;previewDataImport&#x60; reports; &#x60;merge&#x60; adds them and keeps the rest, and &#x60;overwrite&#x60; also removes what the user has of &#x60;kind&#x60; that the file does not list. The server carries the import out in the background: it resolves remote accounts and posts on their servers, sends the follows, and counts the entries it could not carry out (an account or post that cannot be found or acted on). The import is &#x60;in_progress&#x60; until every entry is done. While another import of the user is &#x60;in_progress&#x60; the request is a 409 &#x60;/api/v1/problems/import-in-progress&#x60;. At most 10 imports per account are accepted every day. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AccountsApi()
val dataImportRequest : DataImportRequest =  // DataImportRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : DataImport = apiInstance.startDataImport(dataImportRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AccountsApi#startDataImport")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AccountsApi#startDataImport")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **dataImportRequest** | [**DataImportRequest**](DataImportRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**DataImport**](DataImport.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="subscribeAccount"></a>
# **subscribeAccount**
> Relationship subscribeAccount(id, idempotencyKey)

Be notified of an account&#39;s new posts

Subscribes the viewer to the account&#39;s new posts: each new post that the viewer may see becomes a &#x60;status&#x60; notification, in the app and by Web Push, never by email. The account is not told. An account on this server can be subscribed to with or without a follow; an account on another server only while the viewer follows it (otherwise a 422). Subscribing to the viewer&#39;s own account, to an account that the viewer blocks or that blocks the viewer, and to more than 1,000 accounts is a 422. Subscribing again changes nothing. Unfollowing an account on another server ends the subscription; a block ends it either way. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AccountsApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The account ID.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : Relationship = apiInstance.subscribeAccount(id, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AccountsApi#subscribeAccount")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AccountsApi#subscribeAccount")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The account ID. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**Relationship**](Relationship.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="unblockAccount"></a>
# **unblockAccount**
> Relationship unblockAccount(id, idempotencyKey)

Unblock an account

The follows that the block ended stay ended. Without a block it changes nothing. It also works on an account that blocks the user. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AccountsApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The account ID.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : Relationship = apiInstance.unblockAccount(id, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AccountsApi#unblockAccount")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AccountsApi#unblockAccount")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The account ID. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**Relationship**](Relationship.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="unfollowAccount"></a>
# **unfollowAccount**
> Relationship unfollowAccount(id, idempotencyKey)

Unfollow an account

Ends the follow or withdraws the follow request. Without one it changes nothing.

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AccountsApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The account ID.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : Relationship = apiInstance.unfollowAccount(id, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AccountsApi#unfollowAccount")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AccountsApi#unfollowAccount")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The account ID. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**Relationship**](Relationship.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="unmuteAccount"></a>
# **unmuteAccount**
> Relationship unmuteAccount(id, idempotencyKey)

Unmute an account

Without a mute it changes nothing. It also works on an account that blocks the user.

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AccountsApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The account ID.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : Relationship = apiInstance.unmuteAccount(id, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AccountsApi#unmuteAccount")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AccountsApi#unmuteAccount")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The account ID. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**Relationship**](Relationship.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="unmuteDomain"></a>
# **unmuteDomain**
> unmuteDomain(domain, idempotencyKey)

Unmute a domain

The domain is normalized as in &#x60;muteDomain&#x60;. Without a mute it changes nothing.

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AccountsApi()
val domain : kotlin.String = domain_example // kotlin.String | The muted domain, such as `mastodon.example`.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    apiInstance.unmuteDomain(domain, idempotencyKey)
} catch (e: ClientException) {
    println("4xx response calling AccountsApi#unmuteDomain")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AccountsApi#unmuteDomain")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **domain** | **kotlin.String**| The muted domain, such as &#x60;mastodon.example&#x60;. | |
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

<a id="unsubscribeAccount"></a>
# **unsubscribeAccount**
> Relationship unsubscribeAccount(id, idempotencyKey)

Stop being notified of an account&#39;s new posts

Ends the viewer&#39;s subscription to the account&#39;s new posts, and one asked for with a follow request that awaits the account&#39;s server. Without a subscription it changes nothing. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AccountsApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The account ID.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : Relationship = apiInstance.unsubscribeAccount(id, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AccountsApi#unsubscribeAccount")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AccountsApi#unsubscribeAccount")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The account ID. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**Relationship**](Relationship.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="updateAccountCredentials"></a>
# **updateAccountCredentials**
> CredentialAccount updateAccountCredentials(updateCredentialsRequest, idempotencyKey)

Change the authenticated user&#39;s profile

Records a new revision of the profile. Properties left out of the request keep their current values. The note and the field values are plain text that the server renders to HTML like the text of a post (mentions, hashtags, and URLs become links). A 422 problem names the property that is invalid in &#x60;detail&#x60;. When the server&#39;s automated review refuses the change, the answer is a 422 &#x60;/api/v1/problems/profile-rejected&#x60; with fixed text and no decision to appeal, and the profile stays as it was. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AccountsApi()
val updateCredentialsRequest : UpdateCredentialsRequest =  // UpdateCredentialsRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : CredentialAccount = apiInstance.updateAccountCredentials(updateCredentialsRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AccountsApi#updateAccountCredentials")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AccountsApi#updateAccountCredentials")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **updateCredentialsRequest** | [**UpdateCredentialsRequest**](UpdateCredentialsRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**CredentialAccount**](CredentialAccount.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="verifyAccountCredentials"></a>
# **verifyAccountCredentials**
> CredentialAccount verifyAccountCredentials()

Get the authenticated user&#39;s own account

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = AccountsApi()
try {
    val result : CredentialAccount = apiInstance.verifyAccountCredentials()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AccountsApi#verifyAccountCredentials")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AccountsApi#verifyAccountCredentials")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**CredentialAccount**](CredentialAccount.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

