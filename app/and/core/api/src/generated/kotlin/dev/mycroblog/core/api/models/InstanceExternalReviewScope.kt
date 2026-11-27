package dev.mycroblog.core.api.models

import kotlin.Boolean
import kotlin.Long
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The settings that narrow what the decision hooks in use send to the external review, which the
 * information page states. Each field is present only while its hook is in use.
 */
@Serializable
public data class InstanceExternalReviewScope(
  /**
   * For `local.status`: the posts of local accounts created at most this many days ago are sent
   * (the instance setting `hook_local_status_account_age_days`); `0` sends those of every account.
   */
  @SerialName("local_status_account_age_days")
  public val localStatusAccountAgeDays: Long? = null,
  /**
   * For `local.profile`: the profile updates of local accounts created at most this many days ago
   * are sent (the instance setting `hook_local_profile_account_age_days`); `0` sends those of every
   * account.
   */
  @SerialName("local_profile_account_age_days")
  public val localProfileAccountAgeDays: Long? = null,
  /**
   * For `inbound.status`: whether the direct posts of remote authors that no local account
   * follows are sent too (the instance setting `hook_inbound_status_unfollowed_direct`). The direct
   * posts of followed authors are always sent.
   */
  @SerialName("inbound_status_unfollowed_direct")
  public val inboundStatusUnfollowedDirect: Boolean? = null,
)
