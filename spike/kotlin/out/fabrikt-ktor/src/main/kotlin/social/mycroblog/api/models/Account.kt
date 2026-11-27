package social.mycroblog.api.models

import java.net.URI
import java.util.UUID
import kotlin.Boolean
import kotlin.Int
import kotlin.String
import kotlin.collections.List
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * An account, as clients show it.
 */
@Serializable
public data class Account(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  /**
   * The username, without the domain; empty for a remote account without a confirmed handle.
   */
  @SerialName("username")
  public val username: String,
  /**
   * The username for local accounts, `username@domain` for remote ones; empty for a remote
   * account without a confirmed handle.
   */
  @SerialName("acct")
  public val acct: String,
  /**
   * The domain of the account: the domain of its handle, which for local accounts is the
   * instance's account domain, or the host of its `uri` when a remote account has no
   * confirmed handle.
   */
  @SerialName("domain")
  public val domain: String,
  /**
   * The ActivityPub id of the account's actor, on the server it belongs to.
   */
  @Contextual
  @SerialName("uri")
  public val uri: URI,
  /**
   * The display name; empty when the account has none.
   */
  @SerialName("display_name")
  public val displayName: String,
  /**
   * The profile text as sanitized HTML; empty when there is none.
   */
  @SerialName("note")
  public val note: String,
  /**
   * The account's profile page: on this instance for a local account, and for a remote
   * one the page its actor declares (`url`), or its `uri` when it declares none.
   */
  @Contextual
  @SerialName("url")
  public val url: URI,
  /**
   * Whether follow requests need the account's approval.
   */
  @SerialName("locked")
  public val locked: Boolean,
  /**
   * Whether the account is automated.
   */
  @SerialName("bot")
  public val bot: Boolean,
  /**
   * When the account was created.
   */
  @SerialName("created_at")
  public val createdAt: Instant,
  /**
   * The profile's metadata fields, in their order; empty when there are none.
   */
  @SerialName("fields")
  public val fields: List<ProfileField>,
  /**
   * The custom emoji of the display name, the note, and the fields' names and values. Clients show each `:shortcode:` of those texts that is in this list as its
   * image, and the rest as text.
   */
  @SerialName("emojis")
  public val emojis: List<CustomEmoji>,
  /**
   * The role badges that the account shows on its profile, highest role first.
   * Filled on the account's own endpoints (`getAccount`, `lookupAccount`,
   * `verifyAccountCredentials`) only, and empty where accounts are listed or embedded (posts,
   * notifications, lists of accounts) and for remote accounts. Badges are never federated.
   */
  @SerialName("badges")
  public val badges: List<AccountBadge>,
  /**
   * The renditions of a ready media file within the sizes of its purpose (see
   * `MediaUpload.purpose`), never larger than the file. `small`, `medium`, and `large` are
   * still: for an animation, its first frame, and for a video or audio file, its poster
   * (absent without one). Clients pick one by its width (`srcset`), and
   * show `animated` instead only when the user lets animations play and does not prefer
   * reduced motion.
   */
  @SerialName("avatar")
  public val avatar: MediaVariants? = null,
  /**
   * The renditions of a ready media file within the sizes of its purpose (see
   * `MediaUpload.purpose`), never larger than the file. `small`, `medium`, and `large` are
   * still: for an animation, its first frame, and for a video or audio file, its poster
   * (absent without one). Clients pick one by its width (`srcset`), and
   * show `animated` instead only when the user lets animations play and does not prefer
   * reduced motion.
   */
  @SerialName("header")
  public val `header`: MediaVariants? = null,
  /**
   * The number of accounts that actively follow the account, as far as the instance
   * knows. Present on the account's own endpoints (`getAccount`, `lookupAccount`,
   * `verifyAccountCredentials`) and left out where accounts are listed or embedded.
   */
  @SerialName("followers_count")
  public val followersCount: Int? = null,
  /**
   * The number of accounts that the account actively follows; present where `followers_count` is.
   */
  @SerialName("following_count")
  public val followingCount: Int? = null,
  /**
   * An account, as clients show it.
   */
  @SerialName("moved")
  public val moved: Account? = null,
  /**
   * True for an account that the user may not see but that a field must name (an event's
   * `organizer`): its `id`, `uri`, `domain`, and `created_at` are its own, `url` is its `uri`,
   * `locked` and `bot` are false, and its texts and lists are empty. Omitted otherwise.
   */
  @SerialName("unavailable")
  public val unavailable: Boolean? = null,
  /**
   * The open voice chat room where an account is the host or a speaker, on its profile. Only a
   * user who sees the room gets it; a listener's profile shows no room.
   */
  @SerialName("voice_room")
  public val voiceRoom: VoiceRoomEntry? = null,
)
