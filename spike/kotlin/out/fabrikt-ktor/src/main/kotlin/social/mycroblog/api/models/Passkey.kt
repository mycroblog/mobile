package social.mycroblog.api.models

import java.util.UUID
import kotlin.String
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A passkey that signs the authenticated user in instead of the password.
 */
@Serializable
public data class Passkey(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  /**
   * What the user calls the passkey, such as the device it is on.
   */
  @SerialName("name")
  public val name: String,
  /**
   * When the passkey was registered.
   */
  @SerialName("created_at")
  public val createdAt: Instant,
)
