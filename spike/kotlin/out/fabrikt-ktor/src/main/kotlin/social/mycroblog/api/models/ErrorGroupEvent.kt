package social.mycroblog.api.models

import kotlin.String
import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A change of the state of an error group: `resolved`, `muted`, and `unmuted` by an
 * administrator (`actor` `user`) or the CLI (`cli`), and `regressed` by the server (`system`)
 * when a resolved group happened again on another version, which `version` names. A
 * `resolved` names the version of the last occurrence then.
 */
@Serializable
public data class ErrorGroupEvent(
  @SerialName("kind")
  public val kind: ErrorGroupEventKind,
  @SerialName("actor")
  public val actor: ErrorGroupEventActor,
  /**
   * The version that a `resolved` or a `regressed` names; omitted for the others.
   */
  @SerialName("version")
  public val version: String? = null,
  @SerialName("occurred_at")
  public val occurredAt: Instant,
)
