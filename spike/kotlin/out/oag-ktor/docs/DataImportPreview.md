
# DataImportPreview

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **propertyEntries** | **kotlin.Int** | The number of entries of the file, each of which the import adds. |  |
| **present** | **kotlin.Int** | How many of the entries the user has already. |  |
| **removals** | **kotlin.Int** | How many things the user has of the kind that the file does not list, which an &#x60;overwrite&#x60; removes; 0 for a &#x60;merge&#x60;.  |  |
| **skipped** | [**kotlin.collections.List&lt;DataImportSkipped&gt;**](DataImportSkipped.md) | The lines that are not entries, in line order. |  |



