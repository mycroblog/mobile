package social.mycroblog.api.models

import kotlin.Int
import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The notice that the server maximum of the automatic deletion of old posts binds the user: the
 * maximum it told of and when the user's posts start to go by it. The settings of the automatic
 * deletion (`getAutoDeletePolicy`) show the maximum in effect now, and their preview what goes.
 */
@Serializable
public data class NotificationAutoDeleteLimit(
  /**
   * The most days after its publication that a post is kept, as of the notice.
   */
  @SerialName("max_age_days")
  public val maxAgeDays: Int,
  /**
   * When the maximum starts to delete the user's posts, at the end of the grace period.
   */
  @SerialName("enforce_from")
  public val enforceFrom: Instant,
)
