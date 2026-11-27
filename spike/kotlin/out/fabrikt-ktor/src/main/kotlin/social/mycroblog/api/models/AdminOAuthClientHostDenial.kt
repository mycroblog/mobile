package social.mycroblog.api.models

import java.util.UUID
import kotlin.Boolean
import kotlin.String
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A host whose Client ID Metadata Documents an administrator denied.
 */
@Serializable
public data class AdminOAuthClientHostDenial(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  /**
   * The host name in lowercase, without a port.
   */
  @SerialName("host")
  public val host: String,
  /**
   * The account of the administrator; omitted for a denial from the command line and when
   * that account is deleted (`denied_by_deleted`).
   */
  @SerialName("denied_by")
  public val deniedBy: Account? = null,
  /**
   * Whether an administrator denied the host whose account no longer exists.
   */
  @SerialName("denied_by_deleted")
  public val deniedByDeleted: Boolean,
  @SerialName("denied_at")
  public val deniedAt: Instant,
)
