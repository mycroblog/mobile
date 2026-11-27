
# PasskeyRegistration

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **options** | [**kotlin.collections.Map&lt;kotlin.String, kotlinx.serialization.json.JsonElement&gt;**](kotlinx.serialization.json.JsonElement.md) | The &#x60;PublicKeyCredentialCreationOptionsJSON&#x60; of WebAuthn Level 3 (§5.1.8): binary values in base64url, for &#x60;PublicKeyCredential.parseCreationOptionsFromJSON()&#x60;.  |  |
| **registrationToken** | **kotlin.String** | An opaque token that carries the registration to &#x60;POST /passkeys&#x60;. |  |
| **expiresAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When &#x60;registration_token&#x60; stops working. |  |



