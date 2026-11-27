package social.mycroblog.api.models

import java.net.URI
import java.util.UUID
import kotlin.Boolean
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.collections.List
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The authenticated user's own account, with what only its owner sees.
 */
@Serializable
public data class CredentialAccount(
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
  /**
   * Whether the account is suggested to others and found by the middle of its names in
   * the account search; when `false`, the search finds it only by its
   * whole username. New accounts are discoverable.
   */
  @SerialName("discoverable")
  public val discoverable: Boolean,
  /**
   * Whether the account's profile and public posts may be indexed for search: by this
   * instance's search and, when the instance's `search_indexing` is `allow`, by search
   * engines. New accounts take the instance's default.
   */
  @SerialName("indexable")
  public val indexable: Boolean,
  /**
   * Whether the account's pages state that AI may neither train on nor use them, whatever the instance's `ai_usage`. The statement is made by the
   * pages of this instance only; other servers and the media files do not carry it.
   */
  @SerialName("ai_opt_out")
  public val aiOptOut: Boolean,
  /**
   * Whether the owner lets the Atom feed of the account's public posts be served at the
   * profile's `url` followed by `.atom`, and its public posts appear in the Atom feeds of
   * hashtags. It is the owner's own choice once made, and `indexable` until then
   * (`feed_follows_indexable`). A restricted or moved account has no feed whatever it says.
   */
  @SerialName("feed_enabled")
  public val feedEnabled: Boolean,
  /**
   * Whether the owner has not chosen about the feed yet, so that `feed_enabled` follows
   * `indexable`. Setting `feed_enabled` makes the choice, and a later change of `indexable`
   * leaves it as it is.
   */
  @SerialName("feed_follows_indexable")
  public val feedFollowsIndexable: Boolean,
  /**
   * Who may read an account's follows and followers lists (`GET /accounts/{id}/following` and
   * `GET /accounts/{id}/followers`): `public`, everyone; `followers`, the accounts whose follow
   * of it is approved; `private`, nobody. The owner always reads both, and the counts
   * (`following_count`, `followers_count`) stay visible to everyone. Other servers see the
   * number of follows and followers alone unless the lists are `public`.
   */
  @SerialName("follows_visibility")
  public val followsVisibility: FollowsVisibility,
  /**
   * The profile as the user typed it, for editing.
   */
  @SerialName("source")
  public val source: AccountSource,
  /**
   * What the account's current moderation restrictions let it post. The
   * server applies the same policy when it posts: a status asked for with a visibility that is
   * not in `visibilities` gets `visibility_ceiling` instead.
   */
  @SerialName("posting")
  public val posting: PostingLimits,
  /**
   * The number of the account's own posts that are not deleted, of every visibility.
   * Only the owner sees it; other accounts do not carry it. A client that creates a post
   * adds one to it, so a count of 1 after the create is the account's first post.
   */
  @SerialName("statuses_count")
  public val statusesCount: Long,
  /**
   * The number of follow requests that await the account's approval. Only the owner sees
   * it. Accepting or rejecting a request takes it out of the count. Like `followers_count`,
   * it also counts the requests of deactivated accounts, which `GET /follow_requests`
   * leaves out.
   */
  @SerialName("follow_requests_count")
  public val followRequestsCount: Long,
  /**
   * When the account last moved to another account, also when that move was canceled;
   * omitted when it never moved.
   */
  @SerialName("last_moved_at")
  public val lastMovedAt: Instant? = null,
  /**
   * From when the account can move again, 30 days after its last move; omitted when it
   * can move now.
   */
  @SerialName("can_move_at")
  public val canMoveAt: Instant? = null,
  /**
   * The permissions that the user's roles grant, which the admin operations need; empty
   * for a user without roles. Clients show the admin screens by them; the server checks
   * the permission of each admin operation regardless.
   */
  @SerialName("permissions")
  public val permissions: List<Permission>,
)
