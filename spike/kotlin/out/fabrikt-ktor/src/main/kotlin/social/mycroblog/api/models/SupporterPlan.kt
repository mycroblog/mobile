package social.mycroblog.api.models

import java.util.UUID
import kotlin.Int
import kotlin.String
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A supporter plan that takes subscriptions. The name and the description are written by
 * the administrators in any language; render them with `dir="auto"`.
 */
@Serializable
public data class SupporterPlan(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  @SerialName("name")
  public val name: String,
  @SerialName("description")
  public val description: String,
  /**
   * A higher rank is a higher plan, which includes everything of the lower ones.
   */
  @SerialName("rank")
  public val rank: Int,
  /**
   * An amount of money: a decimal string in the major unit with the digits of
   * its currency (`12.34` USD, `1650` JPY), never a JSON number, and its ISO 4217 code.
   */
  @SerialName("monthly_price")
  public val monthlyPrice: Money,
  /**
   * An amount of money: a decimal string in the major unit with the digits of
   * its currency (`12.34` USD, `1650` JPY), never a JSON number, and its ISO 4217 code.
   */
  @SerialName("yearly_price")
  public val yearlyPrice: Money,
)
