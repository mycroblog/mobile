package dev.mycroblog.core.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The instance theme as its administrators edit it.
 */
@Serializable
public data class AdminTheme(
  /**
   * A saved token set of the instance theme.
   */
  @SerialName("draft")
  public val draft: ThemeSet? = null,
  /**
   * A publication of a token set, which made it the theme in effect.
   */
  @SerialName("published")
  public val published: AdminThemeRevision? = null,
)
