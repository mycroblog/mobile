package dev.mycroblog.core.api.models

import kotlin.Boolean
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A new subscription to a domain block list. The settings that are omitted take their
 * defaults: `draft`, `suspend` as `suspend`, `silence` as `limit`, the rejections applied, and
 * no public comment copied.
 */
@Serializable
public data class CreateDomainListSubscriptionRequest(
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
  public val mode: AdminDomainListMode? = null,
  /**
   * What a `suspend` of the list issues on this server; `ignore` issues nothing.
   */
  @SerialName("suspend_as")
  public val suspendAs: AdminDomainListSuspendAs? = null,
  /**
   * What a `silence` of the list issues on this server; `ignore` issues nothing.
   */
  @SerialName("silence_as")
  public val silenceAs: AdminDomainListSilenceAs? = null,
  @SerialName("apply_reject_media")
  public val applyRejectMedia: Boolean? = null,
  @SerialName("apply_reject_reports")
  public val applyRejectReports: Boolean? = null,
  @SerialName("public_comment_from_list")
  public val publicCommentFromList: Boolean? = null,
)
