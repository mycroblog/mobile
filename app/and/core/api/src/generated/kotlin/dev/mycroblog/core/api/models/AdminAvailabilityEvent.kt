package dev.mycroblog.core.api.models

import java.util.UUID
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The time a remote server was marked unavailable or available again, and why.
 */
@Serializable
public data class AdminAvailabilityEvent(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  @SerialName("kind")
  public val kind: AdminAvailabilityEventKind,
  /**
   * `failure_days`: deliveries failed on 7 distinct days. `delivery_succeeded`: a delivery
   * succeeded. `inbound_request`: the server sent a signed request. `probe_succeeded`: the
   * daily probe fetched its NodeInfo. `operator`: an operator marked it.
   */
  @SerialName("reason")
  public val reason: AdminAvailabilityEventReason,
  @SerialName("occurred_at")
  public val occurredAt: Instant,
)
