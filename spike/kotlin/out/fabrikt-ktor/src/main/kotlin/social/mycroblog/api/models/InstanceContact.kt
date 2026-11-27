package social.mycroblog.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * How people reach the instance's operators (the instance settings `contact_account` and
 * `contact_email`). Omitted when the instance has neither.
 */
@Serializable
public data class InstanceContact(
  /**
   * The local account to contact; omitted when there is none, or when the account
   * cannot be shown (it is suspended or deleted).
   */
  @SerialName("account")
  public val account: Account? = null,
  /**
   * The email address to contact; omitted when there is none.
   */
  @SerialName("email")
  public val email: String? = null,
)
