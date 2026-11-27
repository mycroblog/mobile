# TwoFactorApi

All URIs are relative to */api/v1*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**disableTotp**](TwoFactorApi.md#disableTotp) | **POST** /two_factor/totp/disable | Disable two-factor authentication |
| [**enableTotp**](TwoFactorApi.md#enableTotp) | **POST** /two_factor/totp | Enable two-factor authentication with the secret of a setup |
| [**getTwoFactor**](TwoFactorApi.md#getTwoFactor) | **GET** /two_factor | Get where the authenticated user&#39;s two-factor authentication stands |
| [**regenerateRecoveryCodes**](TwoFactorApi.md#regenerateRecoveryCodes) | **POST** /two_factor/recovery_codes | Replace the recovery codes |
| [**setUpTotp**](TwoFactorApi.md#setUpTotp) | **POST** /two_factor/totp/setup | Generate a TOTP secret for the authenticated user to add to an authenticator app |


<a id="disableTotp"></a>
# **disableTotp**
> TwoFactor disableTotp(twoFactorCodeRequest, idempotencyKey)

Disable two-factor authentication

Ends the enrollment once &#x60;code&#x60;, a code of the app or an unused recovery code, confirms it. A wrong code is a 422 &#x60;/api/v1/problems/invalid-two-factor-code&#x60;, too many wrong codes a 429, and a user without two-factor authentication a 409 &#x60;/api/v1/problems/two-factor-not-enabled&#x60;. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = TwoFactorApi()
val twoFactorCodeRequest : TwoFactorCodeRequest =  // TwoFactorCodeRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : TwoFactor = apiInstance.disableTotp(twoFactorCodeRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling TwoFactorApi#disableTotp")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling TwoFactorApi#disableTotp")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **twoFactorCodeRequest** | [**TwoFactorCodeRequest**](TwoFactorCodeRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**TwoFactor**](TwoFactor.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="enableTotp"></a>
# **enableTotp**
> RecoveryCodes enableTotp(enableTotpRequest)

Enable two-factor authentication with the secret of a setup

Enrolls the secret of &#x60;setup_token&#x60; once &#x60;code&#x60; shows that the app has it, and returns the recovery codes, which are shown only this once. From then on, signing in asks for a code after the password. A wrong code is a 422 &#x60;/api/v1/problems/invalid-two-factor-code&#x60;, an expired or unknown setup a 422 &#x60;/api/v1/problems/totp-setup-expired&#x60;, and too many wrong codes a 429. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = TwoFactorApi()
val enableTotpRequest : EnableTotpRequest =  // EnableTotpRequest | 
try {
    val result : RecoveryCodes = apiInstance.enableTotp(enableTotpRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling TwoFactorApi#enableTotp")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling TwoFactorApi#enableTotp")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **enableTotpRequest** | [**EnableTotpRequest**](EnableTotpRequest.md)|  | |

### Return type

[**RecoveryCodes**](RecoveryCodes.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="getTwoFactor"></a>
# **getTwoFactor**
> TwoFactor getTwoFactor()

Get where the authenticated user&#39;s two-factor authentication stands

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = TwoFactorApi()
try {
    val result : TwoFactor = apiInstance.getTwoFactor()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling TwoFactorApi#getTwoFactor")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling TwoFactorApi#getTwoFactor")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**TwoFactor**](TwoFactor.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="regenerateRecoveryCodes"></a>
# **regenerateRecoveryCodes**
> RecoveryCodes regenerateRecoveryCodes(twoFactorCodeRequest)

Replace the recovery codes

Returns new recovery codes, shown only this once, once &#x60;code&#x60; confirms it; the previous ones stop working and the app keeps working. The problems are those of &#x60;POST /two_factor/totp/disable&#x60;. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = TwoFactorApi()
val twoFactorCodeRequest : TwoFactorCodeRequest =  // TwoFactorCodeRequest | 
try {
    val result : RecoveryCodes = apiInstance.regenerateRecoveryCodes(twoFactorCodeRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling TwoFactorApi#regenerateRecoveryCodes")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling TwoFactorApi#regenerateRecoveryCodes")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **twoFactorCodeRequest** | [**TwoFactorCodeRequest**](TwoFactorCodeRequest.md)|  | |

### Return type

[**RecoveryCodes**](RecoveryCodes.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="setUpTotp"></a>
# **setUpTotp**
> TotpSetup setUpTotp()

Generate a TOTP secret for the authenticated user to add to an authenticator app

A new secret (RFC 6238: HMAC-SHA-1, six digits, 30-second steps) as text, as an &#x60;otpauth://&#x60; URI, and as a QR code of the URI, with a &#x60;setup_token&#x60; that carries the secret to &#x60;POST /two_factor/totp&#x60; for 15 minutes. Nothing is stored until then. A user who has two-factor authentication enabled gets a 409 &#x60;/api/v1/problems/two-factor-enabled&#x60;. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = TwoFactorApi()
try {
    val result : TotpSetup = apiInstance.setUpTotp()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling TwoFactorApi#setUpTotp")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling TwoFactorApi#setUpTotp")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**TotpSetup**](TotpSetup.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

