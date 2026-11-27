
# ImportSignupBlocksRequest

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **kind** | [**AdminSignupBlockKind**](AdminSignupBlockKind.md) |  |  |
| **text** | **kotlin.String** | The list: one value per line, or a CSV row whose first field is the value; blank lines and lines starting with &#x60;#&#x60; are ignored. At most 10,000 values; the request body is at most 1 MiB, past which the answer is 413.  |  |
| **severity** | [**AdminSignupBlockSeverity**](AdminSignupBlockSeverity.md) |  |  |
| **comment** | **kotlin.String** | The comment of every block added. |  [optional] |
| **expiresIn** | **kotlin.Int** | The seconds each block lasts, at most ten years (315360000); omitted, they last until they are lifted.  |  [optional] |



