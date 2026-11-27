# PasskeysApi

All URIs are relative to */api/v1*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**beginPasskeyConfirmation**](PasskeysApi.md#beginPasskeyConfirmation) | **POST** /passkeys/confirmation | Start a confirmation with a passkey |
| [**beginPasskeyRegistration**](PasskeysApi.md#beginPasskeyRegistration) | **POST** /passkeys/registration | Start the registration of a passkey |
| [**createPasskey**](PasskeysApi.md#createPasskey) | **POST** /passkeys | Register a passkey with the browser&#39;s response to a registration |
| [**deletePasskey**](PasskeysApi.md#deletePasskey) | **DELETE** /passkeys/{id} | Remove a passkey |
| [**listPasskeys**](PasskeysApi.md#listPasskeys) | **GET** /passkeys | List the authenticated user&#39;s passkeys |


<a id="beginPasskeyConfirmation"></a>
# **beginPasskeyConfirmation**
> PasskeyConfirmation beginPasskeyConfirmation()

Start a confirmation with a passkey

Returns the options of a WebAuthn authentication (with user verification) for the browser&#39;s &#x60;navigator.credentials.get()&#x60;, with a &#x60;confirmation_token&#x60; that carries them for 5 minutes, once, in the &#x60;passkey&#x60; of an operation that a passkey of the user confirms in place of the current password: deleting the account, deactivating it, moving it, changing or removing the password, registering a passkey, and linking an identity. Nothing is stored. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = PasskeysApi()
try {
    val result : PasskeyConfirmation = apiInstance.beginPasskeyConfirmation()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PasskeysApi#beginPasskeyConfirmation")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PasskeysApi#beginPasskeyConfirmation")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**PasskeyConfirmation**](PasskeyConfirmation.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="beginPasskeyRegistration"></a>
# **beginPasskeyRegistration**
> PasskeyRegistration beginPasskeyRegistration(passkeyRegistrationRequest)

Start the registration of a passkey

While registration and sign-in are limited to the login providers (&#x60;login_providers_only&#x60; of &#x60;GET /instance&#x60;), it is a 403 &#x60;/api/v1/problems/login-providers-only&#x60;: a new passkey could not sign in. Checks the user&#39;s current &#x60;password&#x60; or a &#x60;passkey&#x60; of the user (a user without a password confirms with a passkey) and returns the options of a WebAuthn registration (a discoverable credential with user verification, no attestation) for the browser&#39;s &#x60;navigator.credentials.create()&#x60;, with a &#x60;registration_token&#x60; that carries them and &#x60;name&#x60; to &#x60;POST /passkeys&#x60; for 5 minutes. Nothing is stored until then. A wrong password is a 422 &#x60;/api/v1/problems/wrong-password&#x60;, a passkey that does not verify a 422 &#x60;/api/v1/problems/invalid-passkey&#x60;, and an expired confirmation a 422 &#x60;/api/v1/problems/passkey-confirmation-expired&#x60;; at most 5 wrong ones per user are accepted every hour. A user with 20 passkeys gets a 409 &#x60;/api/v1/problems/too-many-passkeys&#x60;. An &#x60;external_confirmation&#x60; from a linked login provider (&#x60;POST /linked_identities/confirmation&#x60;) takes the place of the password; an unknown, expired, or used one, or one for another operation, is a 422 &#x60;/api/v1/problems/external-confirmation-expired&#x60;. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = PasskeysApi()
val passkeyRegistrationRequest : PasskeyRegistrationRequest =  // PasskeyRegistrationRequest | 
try {
    val result : PasskeyRegistration = apiInstance.beginPasskeyRegistration(passkeyRegistrationRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PasskeysApi#beginPasskeyRegistration")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PasskeysApi#beginPasskeyRegistration")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **passkeyRegistrationRequest** | [**PasskeyRegistrationRequest**](PasskeyRegistrationRequest.md)|  | |

### Return type

[**PasskeyRegistration**](PasskeyRegistration.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="createPasskey"></a>
# **createPasskey**
> Passkey createPasskey(createPasskeyRequest, idempotencyKey)

Register a passkey with the browser&#39;s response to a registration

Verifies &#x60;credential&#x60;, the browser&#39;s answer to the &#x60;options&#x60; of &#x60;POST /passkeys/registration&#x60;, and stores the passkey under the name given there. From then on the passkey signs in on the server&#39;s sign-in page instead of the password; a user with two-factor authentication still enters a code after it. A response that does not verify (another origin, no user verification, a credential that is registered already) is a 422 &#x60;/api/v1/problems/invalid-passkey&#x60;, an expired or unknown &#x60;registration_token&#x60; a 422 &#x60;/api/v1/problems/passkey-registration-expired&#x60;, and a user with 20 passkeys a 409 &#x60;/api/v1/problems/too-many-passkeys&#x60;. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = PasskeysApi()
val createPasskeyRequest : CreatePasskeyRequest =  // CreatePasskeyRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : Passkey = apiInstance.createPasskey(createPasskeyRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PasskeysApi#createPasskey")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PasskeysApi#createPasskey")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **createPasskeyRequest** | [**CreatePasskeyRequest**](CreatePasskeyRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**Passkey**](Passkey.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="deletePasskey"></a>
# **deletePasskey**
> deletePasskey(id, idempotencyKey)

Remove a passkey

The passkey no longer signs in. An unknown or removed passkey is a 404, and the last passkey of a user without a password and without a linked account a 409 &#x60;/api/v1/problems/last-sign-in-method&#x60;. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = PasskeysApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the passkey.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    apiInstance.deletePasskey(id, idempotencyKey)
} catch (e: ClientException) {
    println("4xx response calling PasskeysApi#deletePasskey")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PasskeysApi#deletePasskey")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the passkey. | |
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

<a id="listPasskeys"></a>
# **listPasskeys**
> PasskeyList listPasskeys()

List the authenticated user&#39;s passkeys

The passkeys that sign the user in, oldest first; there are at most 20.

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = PasskeysApi()
try {
    val result : PasskeyList = apiInstance.listPasskeys()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PasskeysApi#listPasskeys")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PasskeysApi#listPasskeys")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**PasskeyList**](PasskeyList.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

