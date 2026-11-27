
# MediaAttachment

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **type** | [**inline**](#Type) | The kind of media. An accepted upload is an &#x60;image&#x60;, a &#x60;video&#x60;, or an &#x60;audio&#x60; file; a file that failed processing is &#x60;unknown&#x60;. &#x60;gifv&#x60; is a remote video that plays like an animated image: muted, looped, without controls.  |  |
| **state** | [**inline**](#State) | The processing state: &#x60;processing&#x60; until the server has re-encoded the file, then &#x60;ready&#x60;, or &#x60;failed&#x60; when the content could not be processed.  |  |
| **meta** | [**MediaMeta**](MediaMeta.md) |  |  |
| **description** | **kotlin.String** | The alternative text; absent when there is none. |  [optional] |
| **blurhash** | **kotlin.String** | The blurhash (https://blurha.sh) of the image, to show while it loads. |  [optional] |
| **variants** | [**MediaVariants**](MediaVariants.md) |  |  [optional] |
| **url** | [**java.net.URI**](java.net.URI.md) | Where the file is served on this instance; for an upload, &#x60;variants.large.url&#x60;, or &#x60;variants.animated.large.url&#x60; for an animation, and the file itself for a video or audio file.  |  [optional] |
| **previewUrl** | [**java.net.URI**](java.net.URI.md) | Where its preview is served; for an upload, &#x60;variants.small.url&#x60; (still). A video or audio file without a poster has none.  |  [optional] |
| **remoteUrl** | [**java.net.URI**](java.net.URI.md) | The file&#39;s URL on the remote server, for a file of a remote status; clients load &#x60;url&#x60; instead. |  [optional] |


<a id="Type"></a>
## Enum: type
| Name | Value |
| ---- | ----- |
| type | image, gifv, video, audio, unknown |


<a id="State"></a>
## Enum: state
| Name | Value |
| ---- | ----- |
| state | processing, ready, failed |



