package social.mycroblog.api.models

import kotlin.Boolean
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A new post on the board of an event. It has what a new status has but a visibility, which the
 * board decides, and a quote, which a board post never has.
 */
@Serializable
public data class EventBoardPostCreate(
  /**
   * The plain text; the server links mentions, hashtags, and URLs.
   */
  @SerialName("text")
  public val text: String,
  /**
   * The content warning; omitted or empty for none. It counts toward `max_post_characters`.
   */
  @SerialName("spoiler_text")
  public val spoilerText: String? = null,
  /**
   * Whether the media are sensitive.
   */
  @SerialName("sensitive")
  public val sensitive: Boolean? = null,
  /**
   * A BCP 47 language tag; omitted means the user's default post language.
   */
  @SerialName("language")
  public val language: String? = null,
  /**
   * A post of the same board to reply to; any other status is a 422.
   */
  @SerialName("in_reply_to_id")
  public val inReplyToId: String? = null,
  /**
   * The media to attach, in order.
   */
  @SerialName("media")
  public val media: List<StatusMediaItem>? = null,
  /**
   * A poll of a new status. It ends `expires_in` seconds after the status is
   * posted; the author and the users who voted are notified then.
   */
  @SerialName("poll")
  public val poll: PollInput? = null,
)
