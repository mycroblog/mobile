package social.mycroblog.api.models

import kotlin.String
import kotlin.collections.Map
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * New values of some instance settings, saved together.
 */
@Serializable
public data class UpdateInstanceSettingsRequest(
  /**
   * The new value of each setting to change, by key, as a string.
   */
  @SerialName("values")
  public val values: Map<String, String?>,
)
