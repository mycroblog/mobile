
# DomainBlockImportRow

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **line** | **kotlin.Int** | The line of the file the row starts on, from 1. |  |
| **writtenDomain** | **kotlin.String** | The domain as written in the row. |  |
| **status** | [**DomainBlockImportStatus**](DomainBlockImportStatus.md) |  |  |
| **issue** | [**kotlin.collections.List&lt;AdminDomainActionKind&gt;**](AdminDomainActionKind.md) | The kinds of the actions the import would issue; empty unless &#x60;importable&#x60;. |  |
| **block** | [**DomainBlock**](DomainBlock.md) | The row, with its domain normalized (IDNA A-labels in lower case); absent when the row is &#x60;invalid&#x60;. The import takes it as it is.  |  [optional] |



