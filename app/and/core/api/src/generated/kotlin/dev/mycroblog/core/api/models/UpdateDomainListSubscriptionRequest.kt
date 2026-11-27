package dev.mycroblog.core.api.models

import kotlin.Boolean
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The changes to the settings of a subscription; what is omitted stays.
 */
@Serializable
public data class UpdateDomainListSubscriptionRequest(
  @SerialName("title")
  public val title: String? = null,
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
