package dev.mycroblog.core.api.models

import kotlin.String
import kotlin.collections.Map
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder

/**
 * Why a supporter plan is refused. `name`, `description`, `monthly_price`, `currency`,
 * `annual_discount_percent`, `emoji_limit`, `rank`, `visibility`: the value breaks its rule
 * (a price below the provider's minimum, of its yearly price too, or a currency the
 * provider does not take, included). `role_builtin`: `admin` and `moderator` cannot be a
 * plan's role. `role_taken`: another plan has the role. `role_held`: users hold the role.
 * `role_permissions`: the role has a permission that is not a member permission.
 * `role_position`: the role is not below every staff role, or its position is outside 0 to
 * 999. `order_rank`: another plan has the rank. `order_position`, `order_permissions`,
 * `order_emoji_limit`: a higher plan's role must be above the lower plan's, have all its
 * permissions, and let its members create at least as many custom emoji.
 */
@Serializable(with = SupporterPlanProblemReason.Serializer::class)
public enum class SupporterPlanProblemReason(
  public val `value`: String,
) {
  @SerialName("name")
  NAME("name"),
  @SerialName("description")
  DESCRIPTION("description"),
  @SerialName("monthly_price")
  MONTHLY_PRICE("monthly_price"),
  @SerialName("currency")
  CURRENCY("currency"),
  @SerialName("annual_discount_percent")
  ANNUAL_DISCOUNT_PERCENT("annual_discount_percent"),
  @SerialName("emoji_limit")
  EMOJI_LIMIT("emoji_limit"),
  @SerialName("rank")
  RANK("rank"),
  @SerialName("visibility")
  VISIBILITY("visibility"),
  @SerialName("role_builtin")
  ROLE_BUILTIN("role_builtin"),
  @SerialName("role_taken")
  ROLE_TAKEN("role_taken"),
  @SerialName("role_held")
  ROLE_HELD("role_held"),
  @SerialName("role_permissions")
  ROLE_PERMISSIONS("role_permissions"),
  @SerialName("role_position")
  ROLE_POSITION("role_position"),
  @SerialName("order_rank")
  ORDER_RANK("order_rank"),
  @SerialName("order_position")
  ORDER_POSITION("order_position"),
  @SerialName("order_permissions")
  ORDER_PERMISSIONS("order_permissions"),
  @SerialName("order_emoji_limit")
  ORDER_EMOJI_LIMIT("order_emoji_limit"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<SupporterPlanProblemReason> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.mycroblog.core.api.models.SupporterPlanProblemReason", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: SupporterPlanProblemReason) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): SupporterPlanProblemReason {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, SupporterPlanProblemReason> =
        entries.associateBy(SupporterPlanProblemReason::value)

    public fun fromValue(`value`: String): SupporterPlanProblemReason = mapping[value] ?: UNRECOGNIZED
  }
}
