
# OAuthAppList

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **items** | [**kotlin.collections.List&lt;OAuthApp&gt;**](OAuthApp.md) |  |  |
| **canCreate** | **kotlin.Boolean** | Whether the user may register an app now: the instance setting &#x60;user_app_registration&#x60; is on. When it is false, &#x60;POST /oauth_apps&#x60; answers 403 &#x60;/api/v1/problems/oauth-app-registration-disabled&#x60;. It does not count the cap of 25 apps (the length of &#x60;items&#x60;) or the rate limit of registrations.  |  |



