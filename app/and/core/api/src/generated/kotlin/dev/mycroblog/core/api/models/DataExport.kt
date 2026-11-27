package dev.mycroblog.core.api.models

import java.util.UUID
import kotlin.Long
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A request for an archive of the user's data.
 */
@Serializable
public data class DataExport(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  /**
   * `pending` while the archive is being built, `ready` while it can be downloaded,
   * `failed` when it could not be built (request another one).
   */
  @SerialName("state")
  public val state: DataExportState,
  /**
   * When the user asked for the export.
   */
  @SerialName("requested_at")
  public val requestedAt: Instant,
  /**
   * When the build ended; omitted while `pending`.
   */
  @SerialName("completed_at")
  public val completedAt: Instant? = null,
  /**
   * When the export and its archive are removed; omitted while `pending`.
   */
  @SerialName("expires_at")
  public val expiresAt: Instant? = null,
  /**
   * The size of the archive in bytes; only for `ready`.
   */
  @SerialName("file_size")
  public val fileSize: Long? = null,
)
