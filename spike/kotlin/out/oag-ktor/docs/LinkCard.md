
# LinkCard

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **url** | [**java.net.URI**](java.net.URI.md) | The page that the card links to, after redirects. |  |
| **title** | **kotlin.String** | The title of the page, as plain text. |  |
| **description** | **kotlin.String** | The description of the page, as plain text; empty when it has none. |  |
| **siteName** | **kotlin.String** | The name of the site, as plain text; the host of &#x60;url&#x60; when the page names none. |  |
| **authorName** | **kotlin.String** | The author that the page names, as plain text; omitted when it names none. |  [optional] |
| **publishedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the page says it was published; omitted when it does not say. |  [optional] |
| **language** | **kotlin.String** | The BCP 47 language of the page; omitted when it does not say. |  [optional] |
| **image** | [**LinkCardImage**](LinkCardImage.md) |  |  [optional] |
| **author** | [**Account**](Account.md) | The account that the page names as its creator (&#x60;fediverse:creator&#x60;), given only when the account&#39;s server lists the page&#39;s domain among its &#x60;attributionDomains&#x60;.  |  [optional] |



