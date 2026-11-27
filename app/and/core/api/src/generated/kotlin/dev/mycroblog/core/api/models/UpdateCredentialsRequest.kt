package dev.mycroblog.core.api.models

import kotlin.Boolean
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A change of the profile; properties left out keep their current values.
 */
@Serializable
public data class UpdateCredentialsRequest(
  /**
   * The display name, at most 30 characters; empty removes it.
   */
  @SerialName("display_name")
  public val displayName: String? = null,
  /**
   * The profile text as plain text, at most 500 characters; empty removes it.
   */
  @SerialName("note")
  public val note: String? = null,
  /**
   * The metadata fields, replacing the current ones; at most 4.
   */
  @SerialName("fields")
  public val fields: List<AccountFieldSource>? = null,
  /**
   * The ID of a processed image that the user uploaded, to become the avatar; empty
   * removes the avatar.
   */
  @SerialName("avatar_media_id")
  public val avatarMediaId: String? = null,
  /**
   * The ID of a processed image that the user uploaded, to become the header image;
   * empty removes the header image.
   */
  @SerialName("header_media_id")
  public val headerMediaId: String? = null,
  /**
   * Whether follow requests need the user's approval.
   */
  @SerialName("locked")
  public val locked: Boolean? = null,
  /**
   * Whether the account is automated.
   */
  @SerialName("bot")
  public val bot: Boolean? = null,
  /**
   * Whether the account is suggested to others and found by the middle of its names in the
   * account search; when `false`, the search finds it only by its whole
   * username. New accounts are discoverable.
   */
  @SerialName("discoverable")
  public val discoverable: Boolean? = null,
  /**
   * Whether the account's profile and public posts may be indexed for search.
   */
  @SerialName("indexable")
  public val indexable: Boolean? = null,
  /**
   * Whether the account's pages state that AI may neither train on nor use them.
   */
  @SerialName("ai_opt_out")
  public val aiOptOut: Boolean? = null,
  /**
   * Whether the Atom feed of the account's public posts may be served. Setting it makes the
   * owner's choice, which a later change of `indexable` leaves as it is.
   */
  @SerialName("feed_enabled")
  public val feedEnabled: Boolean? = null,
  /**
   * Who may read an account's follows and followers lists (`GET /accounts/{id}/following` and
   * `GET /accounts/{id}/followers`): `public`, everyone; `followers`, the accounts whose follow
   * of it is approved; `private`, nobody. The owner always reads both, and the counts
   * (`following_count`, `followers_count`) stay visible to everyone. Other servers see the
   * number of follows and followers alone unless the lists are `public`.
   */
  @SerialName("follows_visibility")
  public val followsVisibility: FollowsVisibility? = null,
)
