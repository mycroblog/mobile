package dev.mycroblog.core.api.models

import java.util.UUID
import kotlin.String
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A money movement of the payment provider as the income records keep it:
 * recorded once its amounts are final, and never edited. `occurred_on` is `occurred_at` as a
 * date in the provider's reporting time zone. A payment carries the billing period it pays
 * for (`covers_starts_on` to `covers_ends_on`) when it has one; a refund, a chargeback, or a
 * reversal carries the provider ID of its payment (`parent_provider_id`). `subscription_id`
 * names the subscription while it is known, and `tip_id` the tip of a `one_time` movement while
 * its record is kept; `account_id` and `username` are the local account of the subscriber or of
 * the user who paid the tip while the account exists. A fee of no subscription has none of them.
 */
@Serializable
public data class IncomeMovement(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  @SerialName("provider")
  public val provider: String,
  @SerialName("provider_id")
  public val providerId: String,
  /**
   * The kind of a money movement of the payment provider: `payment` of a
   * subscription, `refund` and `chargeback` of a payment (a negative gross), the
   * `chargeback_reversal` that gives a chargeback back, and a `fee` of the provider that
   * belongs to no payment (a zero gross and a negative net).
   */
  @SerialName("kind")
  public val kind: IncomeKind,
  /**
   * Where a money movement comes from: a subscription to a supporter plan (`subscription`), a tip
   * (`one_time`), or neither (`unknown`: a payment taken outside this server, or a fee of no
   * subscription). A refund, a chargeback, or a reversal has the source of its payment.
   */
  @SerialName("source")
  public val source: IncomeSource,
  @SerialName("parent_provider_id")
  public val parentProviderId: String? = null,
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("subscription_id")
  public val subscriptionId: UUID? = null,
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("tip_id")
  public val tipId: UUID? = null,
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("account_id")
  public val accountId: UUID? = null,
  /**
   * The username of the subscriber or of the user who paid the tip; omitted with `account_id` for an erased account.
   */
  @SerialName("username")
  public val username: String? = null,
  @SerialName("occurred_at")
  public val occurredAt: Instant,
  /**
   * A calendar date `YYYY-MM-DD` without a time or a time zone, such as the day
   * of a payment on a card statement or an invoice.
   */
  @SerialName("occurred_on")
  public val occurredOn: String,
  /**
   * A calendar date `YYYY-MM-DD` without a time or a time zone, such as the day
   * of a payment on a card statement or an invoice.
   */
  @SerialName("covers_starts_on")
  public val coversStartsOn: String? = null,
  /**
   * A calendar date `YYYY-MM-DD` without a time or a time zone, such as the day
   * of a payment on a card statement or an invoice.
   */
  @SerialName("covers_ends_on")
  public val coversEndsOn: String? = null,
  /**
   * An amount of money: a decimal string in the major unit with the digits of
   * its currency (`12.34` USD, `1650` JPY), never a JSON number, and its ISO 4217 code.
   */
  @SerialName("gross")
  public val gross: Money,
  /**
   * An amount of money: a decimal string in the major unit with the digits of
   * its currency (`12.34` USD, `1650` JPY), never a JSON number, and its ISO 4217 code.
   */
  @SerialName("fee")
  public val fee: Money,
  /**
   * An amount of money: a decimal string in the major unit with the digits of
   * its currency (`12.34` USD, `1650` JPY), never a JSON number, and its ISO 4217 code.
   */
  @SerialName("net")
  public val net: Money,
  @SerialName("recorded_at")
  public val recordedAt: Instant,
)
