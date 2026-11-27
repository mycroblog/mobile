package social.mycroblog.api.models

import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Recovery codes, each of which signs in once in place of a code of the app. They are
 * shown only once.
 */
@Serializable
public data class RecoveryCodes(
  @SerialName("recovery_codes")
  public val recoveryCodes: List<String>,
)
