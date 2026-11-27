
# InstanceDocument

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **kind** | [**InstanceDocumentKind**](InstanceDocumentKind.md) |  |  |
| **html** | **kotlin.String** | The document as HTML, which the server renders from the administrators&#39; Markdown and sanitizes (headings from &#x60;h2&#x60;, paragraphs, lists, quotes, code, rules, tables, and links to &#x60;http&#x60;, &#x60;https&#x60; and &#x60;mailto&#x60;; no raw HTML, scripts or images). Clients put it in the page as it is. The language of the text is unknown: mark it &#x60;lang&#x3D;\&quot;\&quot;&#x60;.  |  |
| **revisedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the current revision of the document was recorded. |  |



