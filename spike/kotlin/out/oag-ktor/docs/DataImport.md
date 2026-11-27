
# DataImport

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **kind** | [**DataImportKind**](DataImportKind.md) |  |  |
| **mode** | [**DataImportMode**](DataImportMode.md) |  |  |
| **state** | [**inline**](#State) | &#x60;in_progress&#x60; until every entry and removal is done, then &#x60;finished&#x60;. |  |
| **createdAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the import started. |  |
| **total** | **kotlin.Int** | The number of entries to add and things to remove. |  |
| **processed** | **kotlin.Int** | How many of them are done, including the failed ones. |  |
| **failed** | **kotlin.Int** | How many of them could not be carried out: an account or post that cannot be found or acted on, a follow that a block stops, or a domain that cannot be muted (this server&#39;s own, or one beyond the most muted domains allowed).  |  |


<a id="State"></a>
## Enum: state
| Name | Value |
| ---- | ----- |
| state | in_progress, finished |



