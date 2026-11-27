package dev.mycroblog.core.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The override of one design token, named without `--` (`color-accent`, `radius-control`): `light`
 * and `dark` for a color token, `value` for the others.
 */
@Serializable
public data class ThemeTokenValue(
  @SerialName("token")
  public val token: String,
  @SerialName("light")
  public val light: String? = null,
  @SerialName("dark")
  public val dark: String? = null,
  @SerialName("value")
  public val `value`: String? = null,
)
