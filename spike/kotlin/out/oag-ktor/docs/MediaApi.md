# MediaApi

All URIs are relative to */api/v1*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**getMedia**](MediaApi.md#getMedia) | **GET** /media/{id} | Get a media file that the user uploaded |
| [**updateMedia**](MediaApi.md#updateMedia) | **PATCH** /media/{id} | Change the description or focal point of a media file |
| [**uploadMedia**](MediaApi.md#uploadMedia) | **POST** /media | Upload a media file |


<a id="getMedia"></a>
# **getMedia**
> MediaAttachment getMedia(id)

Get a media file that the user uploaded

Returns a media file of the authenticated user with its processing state. A file of another account is a 404: other accounts see a file only as an attachment of a post. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = MediaApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the media file.
try {
    val result : MediaAttachment = apiInstance.getMedia(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling MediaApi#getMedia")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling MediaApi#getMedia")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the media file. | |

### Return type

[**MediaAttachment**](MediaAttachment.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a id="updateMedia"></a>
# **updateMedia**
> MediaAttachment updateMedia(id, mediaUpdate, idempotencyKey)

Change the description or focal point of a media file

Changes the description (alternative text) or the focal point of a media file of the authenticated user that no post attaches yet; a property left out keeps its value. A post copies the description when it attaches the file, so a file that a post attaches is a 409 &#x60;media-attached&#x60;: edit the post instead. A file of another account is a 404. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = MediaApi()
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | The ID of the media file.
val mediaUpdate : MediaUpdate =  // MediaUpdate | 
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
try {
    val result : MediaAttachment = apiInstance.updateMedia(id, mediaUpdate, idempotencyKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling MediaApi#updateMedia")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling MediaApi#updateMedia")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**| The ID of the media file. | |
| **mediaUpdate** | [**MediaUpdate**](MediaUpdate.md)|  | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |

### Return type

[**MediaAttachment**](MediaAttachment.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a id="uploadMedia"></a>
# **uploadMedia**
> MediaAttachment uploadMedia(file, idempotencyKey, description, purpose, thumbnail)

Upload a media file

Uploads an image to attach to a post or to use as an avatar, a header, or a custom emoji (&#x60;purpose&#x60;), or a video or audio file to attach to a post. The server checks the format from the content (images: JPEG, PNG, GIF, or WebP, the last two animated or not; video: MP4, QuickTime, or WebM of H.264, VP8, VP9, or AV1; audio: MP3, Ogg, M4A, or WebM of AAC, Opus, Vorbis, or MP3; the declared type must match it) and the size, dimension, and duration limits before it accepts the file, then processes it in the background: it removes the metadata (such as the location) by re-encoding the image into small, medium, and large variants for its purpose, still and, for an animation, moving ones, and makes a blurhash. A video or audio file is not re-encoded: the server writes its streams again without metadata, and makes the variants and the blurhash of its &#x60;thumbnail&#x60;, or of the cover image in the file. The answer is 202 with the file in its current &#x60;state&#x60;, usually &#x60;processing&#x60;; poll &#x60;getMedia&#x60; until it is &#x60;ready&#x60; or &#x60;failed&#x60;. A file used for another purpose later is processed again for it.  The size limits of images and of video and audio files differ, and the limits of video and audio files are set by the operator (&#x60;media_max_av_bytes&#x60;, &#x60;media_max_video_duration&#x60;, &#x60;media_max_audio_duration&#x60; in the server configuration).  A body larger than the size limit is refused with a 413 before it is read. Uploads count against a limit per account besides the API-wide one. Only the uploader can read and change the file. 

### Example
```kotlin
// Import classes:
//import social.mycroblog.api.infrastructure.*
//import social.mycroblog.api.models.*

val apiInstance = MediaApi()
val file : io.ktor.client.request.forms.FormPart<io.ktor.client.request.forms.InputProvider> = BINARY_DATA_HERE // io.ktor.client.request.forms.FormPart<io.ktor.client.request.forms.InputProvider> | The file. Its declared type, if any, must match its content.
val idempotencyKey : kotlin.String = idempotencyKey_example // kotlin.String | A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat while the first request is still being carried out is a 409 `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422. 
val description : kotlin.String = description_example // kotlin.String | The alternative text. Text that is not UTF-8 or has U+0000 is a 422.
val purpose : kotlin.String = purpose_example // kotlin.String | What the file is for, which picks the sizes of its variants: an `attachment` of a post (within 640 × 360, 1920 × 1080, and 3840 × 2160 pixels of area), an `avatar` (cropped to squares of 96, 192, and 400 pixels around the focal point), a `header` (within 750 × 250, 1500 × 500, and 3000 × 1000), or a custom `emoji` (within 64, 128, and 256 pixels). 
val thumbnail : io.ktor.client.request.forms.FormPart<io.ktor.client.request.forms.InputProvider> = BINARY_DATA_HERE // io.ktor.client.request.forms.FormPart<io.ktor.client.request.forms.InputProvider> | The poster of a video or audio file: an image that it shows before it plays, which the client takes from the file. Its declared type is ignored; it must be an image within the image limits. It is ignored for an image. 
try {
    val result : MediaAttachment = apiInstance.uploadMedia(file, idempotencyKey, description, purpose, thumbnail)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling MediaApi#uploadMedia")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling MediaApi#uploadMedia")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **file** | **io.ktor.client.request.forms.FormPart&lt;io.ktor.client.request.forms.InputProvider&gt;**| The file. Its declared type, if any, must match its content. | |
| **idempotencyKey** | **kotlin.String**| A key the client chooses per request, which makes the request safe to send again. Within 24 hours, a request with a key that the same sign-in of the user in the same client (an anonymous client: the same address) already sent to the operation gets the first answer again instead of being carried out again. The same key with another request (another path, query, or body) is a 422 &#x60;/api/v1/problems/idempotency-key-reused&#x60;, and a repeat while the first request is still being carried out is a 409 &#x60;/api/v1/problems/idempotency-key-in-use&#x60; with a &#x60;Retry-After&#x60; of a second. Only an answer below 400 is kept: after a 4xx or 5xx the key is free, so a request sent again with it is carried out. A key longer than 256 characters, or one that is not UTF-8 without U+0000, is a 422.  | [optional] |
| **description** | **kotlin.String**| The alternative text. Text that is not UTF-8 or has U+0000 is a 422. | [optional] |
| **purpose** | **kotlin.String**| What the file is for, which picks the sizes of its variants: an &#x60;attachment&#x60; of a post (within 640 × 360, 1920 × 1080, and 3840 × 2160 pixels of area), an &#x60;avatar&#x60; (cropped to squares of 96, 192, and 400 pixels around the focal point), a &#x60;header&#x60; (within 750 × 250, 1500 × 500, and 3000 × 1000), or a custom &#x60;emoji&#x60; (within 64, 128, and 256 pixels).  | [optional] [default to Purpose.ATTACHMENT] [enum: attachment, avatar, header, emoji] |
| **thumbnail** | **io.ktor.client.request.forms.FormPart&lt;io.ktor.client.request.forms.InputProvider&gt;**| The poster of a video or audio file: an image that it shows before it plays, which the client takes from the file. Its declared type is ignored; it must be an image within the image limits. It is ignored for an image.  | [optional] |

### Return type

[**MediaAttachment**](MediaAttachment.md)

### Authorization


Configure oauth2 statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: multipart/form-data
 - **Accept**: application/json, application/problem+json

