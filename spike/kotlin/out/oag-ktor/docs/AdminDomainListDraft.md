
# AdminDomainListDraft

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **key** | **kotlin.String** | Names the draft to accept or reject. |  |
| **change** | [**AdminDomainListChange**](AdminDomainListChange.md) |  |  |
| **domain** | **kotlin.String** | The domain, in IDNA A-labels. |  |
| **kind** | [**AdminDomainActionKind**](AdminDomainActionKind.md) |  |  |
| **held** | [**AdminDomainListHold**](AdminDomainListHold.md) |  |  |
| **actionId** | [**java.util.UUID**](java.util.UUID.md) | The action that a &#x60;lift&#x60; lifts; omitted for an &#x60;issue&#x60;. |  [optional] |
| **severity** | **kotlin.String** | The severity as the list gives it, for an &#x60;issue&#x60;. |  [optional] |
| **publicComment** | **kotlin.String** | The public comment of the row, for an &#x60;issue&#x60;; omitted when there is none. |  [optional] |
| **obfuscate** | **kotlin.Boolean** | Whether the row asks to hide part of the domain, for an &#x60;issue&#x60;. |  [optional] |



