
# CreateArticleRequest

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **title** | **kotlin.String** | The title of an article, as plain text; surrounding spaces are dropped, and a blank title is a 422. |  |
| **text** | **kotlin.String** | The body of an article in Markdown, as &#x60;createArticle&#x60; describes it. Its HTML may be at most &#x60;Instance.limits.articles.max_bytes&#x60; bytes.  |  |
| **visibility** | [**Visibility**](Visibility.md) | The visibility; an article is never &#x60;direct&#x60; (422). |  |
| **summary** | **kotlin.String** | The summary of an article, as plain text. Omitted or blank, the server makes one from the start of the body (about 200 characters, cut at the end of a sentence or word). It is a summary, never a content warning.  |  [optional] |
| **cover** | [**ArticleCoverInput**](ArticleCoverInput.md) |  |  [optional] |
| **tags** | **kotlin.collections.List&lt;kotlin.String&gt;** | The hashtags of an article, with or without &#x60;#&#x60;; the hashtag timelines and search show the article under them. A name that is no hashtag is a 422. The &#x60;#name&#x60; in the body are text.  |  [optional] |
| **language** | **kotlin.String** | A BCP 47 language tag; omitted means the user&#39;s default post language. |  [optional] |
| **sensitive** | **kotlin.Boolean** | Whether the body and the cover are folded behind the title and the summary. |  [optional] |
| **interactionPolicy** | [**InteractionPolicyChange**](InteractionPolicyChange.md) | Who may reply to, boost, and quote the new article; the kinds left out take the user&#39;s &#x60;default_interaction_policy&#x60;.  |  [optional] |



