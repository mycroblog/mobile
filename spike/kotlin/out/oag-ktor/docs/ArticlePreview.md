
# ArticlePreview

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **content** | **kotlin.String** | The body as sanitized HTML, the same as the published article&#39;s &#x60;content&#x60;. |  |
| **summary** | **kotlin.String** | The summary the article would get. |  |
| **summaryFromBody** | **kotlin.Boolean** | Whether &#x60;summary&#x60; is made from the body, because the one sent was blank. |  |
| **bytes** | **kotlin.Int** | The UTF-8 bytes of &#x60;content&#x60;; over &#x60;max_bytes&#x60;, publishing is a 422. |  |
| **maxBytes** | **kotlin.Int** | The most bytes of &#x60;content&#x60; (&#x60;Instance.limits.articles.max_bytes&#x60;). |  |
| **images** | **kotlin.Int** | The number of the user&#39;s own uploads that the body shows as images; over &#x60;max_images&#x60;, publishing is a 422. |  |
| **maxImages** | **kotlin.Int** | The most images of the body (&#x60;Instance.limits.articles.max_images&#x60;). |  |



