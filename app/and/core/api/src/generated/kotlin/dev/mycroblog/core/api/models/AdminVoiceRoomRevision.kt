package dev.mycroblog.core.api.models

import java.util.UUID
import kotlin.String
import kotlin.collections.List
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A revision of the settings of a voice chat room. The name and the description are empty
 * once the account that wrote them is erased.
 */
@Serializable
public data class AdminVoiceRoomRevision(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  @SerialName("name")
  public val name: String,
  @SerialName("description")
  public val description: String,
  /**
   * The hashtags as the host typed them.
   */
  @SerialName("hashtags")
  public val hashtags: List<String>,
  /**
   * Who sees a voice chat room and may join it: `local` every local user, `followers` the
   * followers of the current host, `following` those whom the host follows, `mutuals` those who
   * follow the host and whom the host follows, and `invited` only the invited users. The host, the co-hosts, the participants, and the
   * invited users always see it, and a block with the host or a co-host always hides it.
   */
  @SerialName("visibility")
  public val visibility: VoiceRoomVisibility,
  /**
   * Who may become a speaker without asking: `everyone`, `followers` of the current host,
   * `following` (those whom the host follows), `mutuals` with the host, or `manual` (nobody: the host and the co-hosts let them speak).
   */
  @SerialName("speakers")
  public val speakers: VoiceRoomSpeakers,
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("revised_by_account_id")
  public val revisedByAccountId: UUID,
  @SerialName("revised_at")
  public val revisedAt: Instant,
)
