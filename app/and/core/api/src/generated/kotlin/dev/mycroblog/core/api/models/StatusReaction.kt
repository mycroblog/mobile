package dev.mycroblog.core.api.models

import kotlin.Boolean
import kotlin.Int
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The reactions to a status with one emoji.
 */
@Serializable
public data class StatusReaction(
  /**
   * The emoji as `listStatusReactions` takes it: the Unicode emoji, the shortcode of a
   * custom emoji of this server, or `shortcode@domain` for a custom emoji of another
   * server.
   */
  @SerialName("name")
  public val name: String,
  /**
   * The shortcode of a custom emoji, without colons; omitted for a Unicode emoji.
   */
  @SerialName("shortcode")
  public val shortcode: String? = null,
  /**
   * The server of a custom emoji of another server; omitted otherwise.
   */
  @SerialName("domain")
  public val domain: String? = null,
  /**
   * A custom emoji that a text uses. The text has it as `:shortcode:`.
   * An emoji that is disabled or deleted, or whose images come from a domain whose media the
   * instance rejects, is not listed, and its `:shortcode:` stays text. Clients show
   * `static_url` when animations should not play (`prefers-reduced-motion`), and `url`
   * otherwise.
   */
  @SerialName("emoji")
  public val emoji: CustomEmoji? = null,
  /**
   * The number of the reactions with the emoji.
   */
  @SerialName("count")
  public val count: Int,
  /**
   * Whether the authenticated user reacted with the emoji; omitted for anonymous
   * requests.
   */
  @SerialName("me")
  public val me: Boolean? = null,
)
