package social.mycroblog.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * How people sign up on the instance.
 */
@Serializable
public data class InstanceRegistrations(
  /**
   * Who may sign up: `open` (anyone), `approval` (sign-ups wait for a moderator's
   * approval), `invite` (only with an invite), or `closed` (nobody). It is the mode that
   * sign-ups follow: `closed` while the closure of the service is announced or done, whatever
   * the instance setting says, on a one-person server (`single_user`), and while registration
   * is limited to the login providers and none of them lets new users sign up.
   */
  @SerialName("mode")
  public val mode: InstanceRegistrationsMode,
)
