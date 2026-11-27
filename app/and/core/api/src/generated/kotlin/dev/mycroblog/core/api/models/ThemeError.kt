package dev.mycroblog.core.api.models

import java.math.BigDecimal
import kotlin.String
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * One reason a token set cannot be saved or published. `code` is `unknown_token`,
 * `duplicate_token`, `invalid_value` (the value does not have the token's shape; `scheme` names the
 * color scheme of a color), `out_of_range` (`min` and `max` are the bounds, in rem for a length and
 * ms for a duration), `low_contrast` (`token` on `background` is `ratio`:1 in `scheme` and needs
 * `minimum`:1), or `too_many_overrides` (more than `max` tokens).
 */
@Serializable
public data class ThemeError(
  @SerialName("code")
  public val code: ThemeErrorCode,
  @SerialName("token")
  public val token: String? = null,
  @SerialName("scheme")
  public val scheme: ThemeErrorScheme? = null,
  @SerialName("background")
  public val background: String? = null,
  @Contextual
  @SerialName("ratio")
  public val ratio: BigDecimal? = null,
  @Contextual
  @SerialName("minimum")
  public val minimum: BigDecimal? = null,
  @Contextual
  @SerialName("min")
  public val min: BigDecimal? = null,
  @Contextual
  @SerialName("max")
  public val max: BigDecimal? = null,
)
