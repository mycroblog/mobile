
# TotpSetup

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **secret** | **kotlin.String** | The secret in base32, for typing it into the app. |  |
| **uri** | **kotlin.String** | The &#x60;otpauth://totp/...&#x60; URI of the secret. |  |
| **qrCode** | [**QrCode**](QrCode.md) |  |  |
| **setupToken** | **kotlin.String** | An opaque token that carries the secret to &#x60;POST /two_factor/totp&#x60;. |  |
| **expiresAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When &#x60;setup_token&#x60; stops working. |  |



