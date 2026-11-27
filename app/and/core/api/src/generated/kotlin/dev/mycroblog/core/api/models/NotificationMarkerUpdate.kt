package dev.mycroblog.core.api.models

import java.util.UUID
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The new position of the read marker.
 */
@Serializable
public data class NotificationMarkerUpdate(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("last_read_id")
  public val lastReadId: UUID,
)
