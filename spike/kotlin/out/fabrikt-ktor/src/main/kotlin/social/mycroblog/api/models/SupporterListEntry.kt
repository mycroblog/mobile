package social.mycroblog.api.models

import java.util.UUID
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A member on the supporter list.
 */
@Serializable
public data class SupporterListEntry(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("plan_id")
  public val planId: UUID,
  /**
   * An account, as clients show it.
   */
  @SerialName("account")
  public val account: Account,
)
