# FiltersApi

All URIs are relative to */api/v1*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**createKeywordFilter**](FiltersApi.md#createKeywordFilter) | **POST** /filters | Create a keyword filter |
| [**deleteKeywordFilter**](FiltersApi.md#deleteKeywordFilter) | **DELETE** /filters/{id} | Delete a keyword filter |
| [**getKeywordFilter**](FiltersApi.md#getKeywordFilter) | **GET** /filters/{id} | Get a keyword filter |
| [**listKeywordFilters**](FiltersApi.md#listKeywordFilters) | **GET** /filters | List the user&#39;s keyword filters |
| [**updateKeywordFilter**](FiltersApi.md#updateKeywordFilter) | **PUT** /filters/{id} | Change a keyword filter |


<a id="createKeywordFilter"></a>
# **createKeywordFilter**
> KeywordFilter createKeywordFilter(keywordFilterRequest, idempotencyKey)

Create a keyword filter

Creates a keyword filter, which applies at once to every post the contexts show, old ones too. A field that is not one is a 422 naming it. A user has at most 100 filters; one more is a 422 &#x60;/api/v1/problems/too-many-keyword-filters&#x60;. Words beyond 100 in the filter or 1,000 in all the user&#39;s filters are a 422 &#x60;/api/v1/problems/too-many-filter-keywords&#x60;. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = FiltersApi()
val keywordFilterRequest : KeywordFilterRequest =  // KeywordFilterRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : KeywordFilter = apiInstance.createKeywordFilter(keywordFilterRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling FiltersApi#createKeywordFilter")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling FiltersApi#createKeywordFilter")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **keywordFilterRequest** | [**KeywordFilterRequest**](KeywordFilterRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**KeywordFilter**](KeywordFilter.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="deleteKeywordFilter"></a>
# **deleteKeywordFilter**
> deleteKeywordFilter(id, idempotencyKey)

Delete a keyword filter

Deletes the filter with its words. Another user&#39;s filter is a 404.

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = FiltersApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the keyword filter.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    apiInstance.deleteKeywordFilter(id, idempotencyKey)
} catch (e: ClientException) {
    println("4xx response calling FiltersApi#deleteKeywordFilter")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling FiltersApi#deleteKeywordFilter")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the keyword filter. | |
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

<a id="getKeywordFilter"></a>
# **getKeywordFilter**
> KeywordFilter getKeywordFilter(id)

Get a keyword filter

A keyword filter of the user. Another user&#39;s filter is a 404, as if it did not exist.

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = FiltersApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the keyword filter.
try {
    val result : KeywordFilter = apiInstance.getKeywordFilter(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling FiltersApi#getKeywordFilter")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling FiltersApi#getKeywordFilter")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the keyword filter. | |

### Return type

[**KeywordFilter**](KeywordFilter.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="listKeywordFilters"></a>
# **listKeywordFilters**
> KeywordFilterCollection listKeywordFilters()

List the user&#39;s keyword filters

Every keyword filter of the user (at most 100), oldest first, the expired ones too. Nobody else sees them.

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = FiltersApi()
try {
    val result : KeywordFilterCollection = apiInstance.listKeywordFilters()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling FiltersApi#listKeywordFilters")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling FiltersApi#listKeywordFilters")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**KeywordFilterCollection**](KeywordFilterCollection.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="updateKeywordFilter"></a>
# **updateKeywordFilter**
> KeywordFilter updateKeywordFilter(id, keywordFilterRequest, idempotencyKey)

Change a keyword filter

Replaces the title, action, contexts, words, and expiry of the filter, checked as in &#x60;createKeywordFilter&#x60;; an &#x60;expires_at&#x60; left out makes the filter apply always. Another user&#39;s filter is a 404. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = FiltersApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the keyword filter.
val keywordFilterRequest : KeywordFilterRequest =  // KeywordFilterRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : KeywordFilter = apiInstance.updateKeywordFilter(id, keywordFilterRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling FiltersApi#updateKeywordFilter")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling FiltersApi#updateKeywordFilter")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the keyword filter. | |
| **keywordFilterRequest** | [**KeywordFilterRequest**](KeywordFilterRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**KeywordFilter**](KeywordFilter.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

