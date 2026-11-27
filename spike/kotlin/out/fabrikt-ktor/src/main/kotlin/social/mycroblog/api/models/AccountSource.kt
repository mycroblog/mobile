package social.mycroblog.api.models

import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The profile as the user typed it, for editing.
 */
@Serializable
public data class AccountSource(
  /**
   * The profile text as plain text; empty when there is none.
   */
  @SerialName("note")
  public val note: String,
  /**
   * The metadata fields as typed, in the order of the account's `fields`.
   */
  @SerialName("fields")
  public val fields: List<AccountFieldSource>,
  /**
   * The media file of the avatar; omitted when there is none.
   */
  @SerialName("avatar_media_id")
  public val avatarMediaId: String? = null,
  /**
   * The media file of the header image; omitted when there is none.
   */
  @SerialName("header_media_id")
  public val headerMediaId: String? = null,
)
