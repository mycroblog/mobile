package dev.mycroblog.core.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * One metadata field of a profile.
 */
@Serializable
public data class ProfileField(
  /**
   * The field's name, as plain text.
   */
  @SerialName("name")
  public val name: String,
  /**
   * The field's value as sanitized HTML.
   */
  @SerialName("value")
  public val `value`: String,
)
