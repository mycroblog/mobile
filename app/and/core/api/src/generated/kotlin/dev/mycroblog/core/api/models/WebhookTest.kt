package dev.mycroblog.core.api.models

import java.util.UUID
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The data of a `webhook.test` message.
 */
@Serializable
public data class WebhookTest(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("endpoint_id")
  public val endpointId: UUID,
)
