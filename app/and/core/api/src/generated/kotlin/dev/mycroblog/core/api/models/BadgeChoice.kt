package dev.mycroblog.core.api.models

import java.util.UUID
import kotlin.Boolean
import kotlin.String
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A role that the authenticated user holds and that has a badge, with whether the user shows
 * the badge on their profile. A badge is hidden until the user shows it; losing the
 * role or its badge hides it again.
 */
@Serializable
public data class BadgeChoice(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("role_id")
  public val roleId: UUID,
  /**
   * The name on the badge.
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
  /**
   * Whether the user shows the badge on their profile.
   */
  @SerialName("displayed")
  public val displayed: Boolean,
)
