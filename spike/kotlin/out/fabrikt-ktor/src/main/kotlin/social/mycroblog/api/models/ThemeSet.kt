package social.mycroblog.api.models

import kotlin.String
import kotlin.collections.List
import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A saved token set of the instance theme.
 */
@Serializable
public data class ThemeSet(
  @SerialName("id")
  public val id: String,
  /**
   * The overrides that apply, in the order of the token list.
   */
  @SerialName("tokens")
  public val tokens: List<ThemeTokenValue>,
  /**
   * The overrides that no longer apply and are left out of the stylesheet: `reason` is
   * `unknown_token` (the token was renamed or retired), `duplicate_token`, `invalid_value`, or
   * `out_of_range` (the token's kind or bounds changed).
   */
  @SerialName("stale_tokens")
  public val staleTokens: List<StaleThemeToken>,
  @SerialName("saved_at")
  public val savedAt: Instant,
)
