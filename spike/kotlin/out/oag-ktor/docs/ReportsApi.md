# ReportsApi

All URIs are relative to */api/v1*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**createReport**](ReportsApi.md#createReport) | **POST** /reports | Report an account |
| [**listOwnReports**](ReportsApi.md#listOwnReports) | **GET** /reports | List the reports that the authenticated user filed |


<a id="createReport"></a>
# **createReport**
> Report createReport(createReportRequest, idempotencyKey)

Report an account

Reports an account, and optionally some of its posts, to the moderators. The report waits, open, in the moderators&#39; queue; nothing tells the account of it, and nothing that the account can read names the reporter. 422 when the account does not exist or is the user&#39;s own (&#x60;account_id&#x60;), when a post is not the account&#39;s or the user may not see it (&#x60;status_ids&#x60;), when &#x60;rule_ids&#x60; cite a rule that is not in effect or the category is not &#x60;violation&#x60;, or when the comment is too long. Reports are limited to 30 per hour per account (429 beyond). 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = ReportsApi()
val createReportRequest : CreateReportRequest =  // CreateReportRequest | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : Report = apiInstance.createReport(createReportRequest, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ReportsApi#createReport")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ReportsApi#createReport")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **createReportRequest** | [**CreateReportRequest**](CreateReportRequest.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**Report**](Report.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="listOwnReports"></a>
# **listOwnReports**
> ReportPage listOwnReports(cursor, limit)

List the reports that the authenticated user filed

The reports that the user filed, newest first, each with whether it is still open or resolved. A report reopened by the moderators is open again. It never tells how a report was resolved, which actions it led to, the reasons given to the account, or who handles it. A report stays listed when the user may no longer see its target (the account is suspended, deactivated, deleted, or blocks the user), without &#x60;target_account&#x60;. A report whose target no longer exists at all is left out, so a page may hold fewer reports than &#x60;limit&#x60; and still have a &#x60;next_cursor&#x60;. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = ReportsApi()
val cursor : kotlin.String = cursor_example // kotlin.String | The `next_cursor` of the previous page; omitted for the first page.
val limit : kotlin.Int = 56 // kotlin.Int | The maximum number of items in the page.
try {
    val result : ReportPage = apiInstance.listOwnReports(cursor, limit)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ReportsApi#listOwnReports")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ReportsApi#listOwnReports")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **cursor** | **kotlin.String**| The &#x60;next_cursor&#x60; of the previous page; omitted for the first page. | [optional] |
| **limit** | **kotlin.Int**| The maximum number of items in the page. | [optional] [default to 20] |

### Return type

[**ReportPage**](ReportPage.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

