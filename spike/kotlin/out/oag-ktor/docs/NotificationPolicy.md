
# NotificationPolicy

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **notFollowing** | [**NotificationPolicyAction**](NotificationPolicyAction.md) | Sources that the user does not follow (a follow request waiting for approval does not count). |  |
| **notFollower** | [**NotificationPolicyAction**](NotificationPolicyAction.md) | Sources that do not follow the user. |  |
| **newAccount** | [**NotificationPolicyAction**](NotificationPolicyAction.md) | Sources whose account was created less than 30 days ago. |  |
| **unsolicitedPrivateMention** | [**NotificationPolicyAction**](NotificationPolicyAction.md) | Mentions in a direct post that does not answer a direct post of the user that mentioned the source in the same conversation.  |  |
| **limitedAccount** | [**NotificationPolicyAction**](NotificationPolicyAction.md) | Sources that a moderator limited, by themselves or by their domain. |  |



