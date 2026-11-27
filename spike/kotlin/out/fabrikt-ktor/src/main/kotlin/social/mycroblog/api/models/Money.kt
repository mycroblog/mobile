package social.mycroblog.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * An amount of money: a decimal string in the major unit with the digits of
 * its currency (`12.34` USD, `1650` JPY), never a JSON number, and its ISO 4217 code.
 */
@Serializable
public data class Money(
  @SerialName("amount")
  public val amount: String,
  @SerialName("currency")
  public val currency: String,
)
