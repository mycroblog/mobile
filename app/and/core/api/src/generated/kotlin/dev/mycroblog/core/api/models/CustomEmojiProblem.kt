package dev.mycroblog.core.api.models

import kotlin.Int
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The problem of a custom emoji that breaks a rule (422,
 * `/api/v1/problems/invalid-custom-emoji`); `reason` says which. Another 422 (a malformed
 * request) has none.
 */
@Serializable
public data class CustomEmojiProblem(
  /**
   * A URI that identifies the problem type, stable for clients to branch on.
   * It is `about:blank` when the problem means no more than its HTTP status.
   */
  @SerialName("type")
  public val type: String,
  /**
   * A short summary of the problem type, localized from `Accept-Language`.
   */
  @SerialName("title")
  public val title: String,
  /**
   * The HTTP status code.
   */
  @SerialName("status")
  public val status: Int,
  /**
   * An explanation of this occurrence, localized from `Accept-Language`.
   */
  @SerialName("detail")
  public val detail: String? = null,
  /**
   * The request ID (the `X-Request-Id` response header), for reporting the problem.
   */
  @SerialName("request_id")
  public val requestId: String? = null,
  /**
   * The member of the request that a 422 is about, by its name in the request: a property of
   * the JSON body by its path (`display_name`, `fields[].name`, `poll.options[1]`), a form
   * field, a query parameter, or a header (`Idempotency-Key`). `detail` then says what is
   * wrong with its value without naming it, for the client to show beside that field. Absent
   * when the problem is not about one member.
   */
  @SerialName("field")
  public val `field`: String? = null,
  /**
   * Why a custom emoji is refused. `invalid_shortcode`: not 2 to 30 letters, digits, and
   * underscores. `shortcode_taken`: a local emoji has the shortcode, or a pending request reserves it. `invalid_alias`: an
   * alias breaks the rules of a shortcode, or there are more than 10. `invalid_category`:
   * the category is longer than 64 characters or not text. `media_not_found`: the media
   * file is not an upload of the user. `media_not_ready`: it has not finished processing.
   * `unsupported_format`: it is not a PNG, a GIF, or a WebP. `too_large`: it is larger
   * than `custom_emoji_max_bytes`. `too_big`: it is larger than 1,024 pixels on a side.
   * `remote_emoji`: the operation is for local emoji only. `local_emoji`: the operation is
   * for remote emoji only. `fetch_failed`: the image of the remote emoji could not be
   * fetched (its server did not return it, or the instance rejects the media of its domain).
   * `limit_reached`: the approved and pending requests to create an emoji that the user submitted
   * this month (UTC) are as many as their monthly limit. `request_pending`: the emoji already has a pending request for a new image.
   * `invalid_reason`: the reason of a rejection is longer than 500 characters or not text.
   */
  @SerialName("reason")
  public val reason: CustomEmojiProblemReason? = null,
)
