package social.mycroblog.api.models

import java.util.UUID
import kotlin.String
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A badge that the account shows on its profile: the badge of a role that the
 * account's user holds and chose to show. Clients show `label` as user content (`dir="auto"`),
 * never translated.
 */
@Serializable
public data class AccountBadge(
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
)
