package dev.mycroblog.core.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The move that an `account_moved` or an `admin_account_moved` tells of: the notification's
 * `account` moved to `target`. Only the user's follows of the moved account move;
 * its posts stay where they are.
 */
@Serializable
public data class AccountMove(
  /**
   * An account, as clients show it.
   */
  @SerialName("target")
  public val target: Account? = null,
  /**
   * What became of the user's follow of the moved account, for an `account_moved` only:
   * `followed`, the user follows `target` instead; `requested`, the user's follow of
   * `target` awaits its approval instead; and the user still follows the moved account
   * when the user blocks `target` (`blocking`), `target` blocks the user (`blocked_by`), or
   * the user mutes the server of `target` (`domain_muted`).
   */
  @SerialName("outcome")
  public val outcome: AccountMoveOutcome? = null,
)
