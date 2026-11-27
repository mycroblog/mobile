package dev.mycroblog.core.api.models

import java.util.UUID
import kotlin.Int
import kotlin.String
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A list of accounts that the user follows, which only the user sees. Its timeline shows the
 * posts and boosts of its members.
 */
@Serializable
public data class List(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  /**
   * The name of the list, 1 to 50 characters.
   */
  @SerialName("name")
  public val name: String,
  /**
   * How many accounts are in the list.
   */
  @SerialName("members_count")
  public val membersCount: Int,
  /**
   * When the user created the list.
   */
  @SerialName("created_at")
  public val createdAt: Instant,
)
