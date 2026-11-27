
# PasskeyConfirmation

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **options** | [**kotlin.collections.Map&lt;kotlin.String, kotlinx.serialization.json.JsonElement&gt;**](kotlinx.serialization.json.JsonElement.md) | The &#x60;PublicKeyCredentialRequestOptionsJSON&#x60; of WebAuthn Level 3 (§5.1.9): binary values in base64url, for &#x60;PublicKeyCredential.parseRequestOptionsFromJSON()&#x60;.  |  |
| **confirmationToken** | **kotlin.String** | An opaque token that carries the confirmation to the operation it confirms. |  |
| **expiresAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When &#x60;confirmation_token&#x60; stops working. |  |



