
# KeywordFilterKeyword

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **keyword** | **kotlin.String** | The word or phrase, as the user typed it. It matches the text, the content warning, the poll&#39;s choices, and the alt text of a post, each on its own, regardless of case, of half-width and full-width forms, and of katakana and hiragana; runs of white space match one space. Up to 100 characters once normalized.  |  |
| **wholeWord** | **kotlin.Boolean** | Whether the word matches only where no letter, digit, or &#x60;_&#x60; touches it. Characters of scripts that do not separate words with spaces (Chinese, Japanese, Thai, and the like) never count as touching, so there the word matches anywhere.  |  |



