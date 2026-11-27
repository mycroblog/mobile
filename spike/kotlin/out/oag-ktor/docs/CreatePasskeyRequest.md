
# CreatePasskeyRequest

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **registrationToken** | **kotlin.String** | The &#x60;registration_token&#x60; of &#x60;POST /passkeys/registration&#x60;. |  |
| **credential** | [**kotlin.collections.Map&lt;kotlin.String, kotlinx.serialization.json.JsonElement&gt;**](kotlinx.serialization.json.JsonElement.md) | The &#x60;RegistrationResponseJSON&#x60; of WebAuthn Level 3 (§5.1): what &#x60;PublicKeyCredential.toJSON()&#x60; returns for the new credential.  |  |



