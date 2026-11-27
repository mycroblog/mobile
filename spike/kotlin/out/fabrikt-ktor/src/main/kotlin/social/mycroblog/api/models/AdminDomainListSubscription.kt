package social.mycroblog.api.models

import java.util.UUID
import kotlin.Boolean
import kotlin.Int
import kotlin.String
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A subscription to a domain block list that another server or person publishes.
 */
@Serializable
public data class AdminDomainListSubscription(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  /**
   * The https URL of the list.
   */
  @SerialName("url")
  public val url: String,
  /**
   * The format of a subscribed list. `mastodon_csv`: Mastodon's domain block CSV, read as the
   * import reads it. `mastodon_json`: the answer of Mastodon's
   * `GET /api/v1/instance/domain_blocks`; obfuscated domains are skipped. `plain_text`: one
   * domain per line, each a suspend.
   */
  @SerialName("format")
  public val format: AdminDomainListFormat,
  @SerialName("title")
  public val title: String,
  /**
   * `draft`: every difference waits for a moderator. `automatic`: the differences are applied
   * at each fetch, except those that `AdminDomainListHold` names.
   */
  @SerialName("mode")
  public val mode: AdminDomainListMode,
  /**
   * What a `suspend` of the list issues on this server; `ignore` issues nothing.
   */
  @SerialName("suspend_as")
  public val suspendAs: AdminDomainListSuspendAs,
  /**
   * What a `silence` of the list issues on this server; `ignore` issues nothing.
   */
  @SerialName("silence_as")
  public val silenceAs: AdminDomainListSilenceAs,
  /**
   * Whether the `reject_media` of a row issues a `reject_media` action.
   */
  @SerialName("apply_reject_media")
  public val applyRejectMedia: Boolean,
  /**
   * Whether the `reject_reports` of a row issues a `reject_reports` action.
   */
  @SerialName("apply_reject_reports")
  public val applyRejectReports: Boolean,
  /**
   * Whether the actions copy the public comment of their row.
   */
  @SerialName("public_comment_from_list")
  public val publicCommentFromList: Boolean,
  /**
   * The failed fetches since the last one that did not fail.
   */
  @SerialName("consecutive_failures")
  public val consecutiveFailures: Int,
  /**
   * One fetch of a subscribed domain block list.
   */
  @SerialName("last_fetch")
  public val lastFetch: AdminDomainListFetch? = null,
  @SerialName("created_at")
  public val createdAt: Instant,
  /**
   * When the settings last changed; the creation for settings never changed.
   */
  @SerialName("updated_at")
  public val updatedAt: Instant,
)
