package social.mycroblog.api.models

import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A condition of an `oidc` provider: a user signs in only when the claim of the ID token
 * has one of the values, such as `hd` for the domain of a Google Workspace organization.
 */
@Serializable
public data class LoginProviderClaimCondition(
  /**
   * The name of the claim, up to 100 bytes.
   */
  @SerialName("claim")
  public val claim: String,
  /**
   * The allowed values, 1 to 50 of up to 256 characters each, sorted.
   */
  @SerialName("values")
  public val values: List<String>,
)
