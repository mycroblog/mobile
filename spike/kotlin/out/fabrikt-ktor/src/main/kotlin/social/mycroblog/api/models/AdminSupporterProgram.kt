package social.mycroblog.api.models

import kotlin.Boolean
import kotlin.Int
import kotlin.String
import kotlin.collections.List
import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The supporter program. It is disabled by default; enabling it needs a
 * payment provider in the deployment's configuration (`configured_provider`) and a server
 * that sends email (`sends_email`). `warnings` lists what an administrator should look at.
 */
@Serializable
public data class AdminSupporterProgram(
  @SerialName("enabled")
  public val enabled: Boolean,
  /**
   * The payment provider the program was enabled with; omitted while disabled.
   */
  @SerialName("provider")
  public val provider: String? = null,
  /**
   * The payment provider of the deployment's configuration; omitted for none.
   */
  @SerialName("configured_provider")
  public val configuredProvider: String? = null,
  @SerialName("sends_email")
  public val sendsEmail: Boolean,
  /**
   * Whether the instance publishes its commerce disclosures (`/about/commerce`).
   */
  @SerialName("commerce_disclosure")
  public val commerceDisclosure: Boolean,
  /**
   * The subscriptions that go on, which keep the program from being disabled.
   */
  @SerialName("live_subscriptions")
  public val liveSubscriptions: Int,
  /**
   * Whether the program can be enabled now.
   */
  @SerialName("can_enable")
  public val canEnable: Boolean,
  /**
   * When the program was last enabled or disabled; omitted for never.
   */
  @SerialName("changed_at")
  public val changedAt: Instant? = null,
  @SerialName("warnings")
  public val warnings: List<SupporterProgramWarning>,
)
