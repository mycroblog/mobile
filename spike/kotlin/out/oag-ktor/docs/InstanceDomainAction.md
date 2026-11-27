
# InstanceDomainAction

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **domain** | **kotlin.String** | The domain, in IDNA A-labels. When the moderators chose to obfuscate it, the characters of its middle half other than dots are replaced with &#x60;*&#x60;.  |  |
| **digest** | **kotlin.String** | The SHA-256 of the domain (not obfuscated) in lower-case hexadecimal, to check a known domain against an obfuscated item. |  |
| **kind** | [**inline**](#Kind) | &#x60;suspend&#x60;: no federation with the domain at all. &#x60;limit&#x60;: its posts stay off the public timelines and its accounts reach only their followers.  |  |
| **comment** | **kotlin.String** | The public reason for the action; omitted when there is none or the viewer may not read it. |  [optional] |


<a id="Kind"></a>
## Enum: kind
| Name | Value |
| ---- | ----- |
| kind | limit, suspend |



