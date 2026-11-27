
# AdminAccountPrivateData

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **signIns** | [**kotlin.collections.List&lt;AdminAccountSignIn&gt;**](AdminAccountSignIn.md) | The newest 20 sign-ins within the retention, newest first; &#x60;GET .../sign_ins&#x60; pages through them all.  |  |
| **sameIpAccounts** | [**kotlin.collections.List&lt;AdminSameIpAccounts&gt;**](AdminSameIpAccounts.md) | For each IP address of &#x60;sign_ins&#x60; and &#x60;registration_ip&#x60;, newest first, up to 20 other accounts that registered or signed in from it, newest first; &#x60;GET .../same_ip_accounts&#x60; pages through them all.  |  |
| **email** | **kotlin.String** | The primary email address; omitted when there is none. |  [optional] |
| **emailVerified** | **kotlin.Boolean** | Whether the primary email address is verified; omitted with &#x60;email&#x60;. |  [optional] |
| **registrationIp** | **kotlin.String** | The IP address the account registered from; omitted for a registration from an unknown address, by the command line, or past the retention.  |  [optional] |
| **registeredAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the account registered from &#x60;registration_ip&#x60;; omitted with it. |  [optional] |



