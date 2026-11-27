package social.mycroblog.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The badge of a role, which its holders may show on their profile. The label is
 * the staff's own text, the same in every locale: clients show it as user content
 * (`dir="auto"`), never translated.
 */
@Serializable
public data class RoleBadge(
  /**
   * The name on the badge, 1 to 32 characters on one line; white space around it is removed.
   */
  @SerialName("label")
  public val label: String,
  /**
   * The color of a role badge: the name of a pair of the web client's design tokens
   * for its background and its text, which keep their contrast in the light and the dark theme.
   * Server themes do not change them. The color is decoration; the badge's `label` carries its
   * meaning.
   */
  @SerialName("color")
  public val color: BadgeColor,
)
