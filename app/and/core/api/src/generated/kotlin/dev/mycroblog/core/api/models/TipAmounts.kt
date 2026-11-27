package dev.mycroblog.core.api.models

import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The amounts that a tip may be, smallest first, all in one currency.
 */
@Serializable
public data class TipAmounts(
  @SerialName("amounts")
  public val amounts: List<Money>,
  /**
   * Why the user may not subscribe to a supporter plan or tip: `not_local`, not a user
   * of this server; `email_unverified`, the primary email address is not verified;
   * `restricted`, the account is suspended or its deletion is done or scheduled. Omitted when
   * the user may.
   */
  @SerialName("ineligible_reason")
  public val ineligibleReason: SupporterIneligibleReason? = null,
)
