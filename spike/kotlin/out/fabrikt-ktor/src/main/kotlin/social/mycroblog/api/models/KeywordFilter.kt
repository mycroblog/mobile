package social.mycroblog.api.models

import java.util.UUID
import kotlin.Boolean
import kotlin.String
import kotlin.collections.List
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A keyword filter of the user, which only the user sees. The server matches the posts it
 * returns against the filters of their contexts, so every client gets the same result; the
 * user's own posts and boosts are never matched.
 */
@Serializable
public data class KeywordFilter(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  /**
   * The name of the filter, 1 to 50 characters, which a folded post shows.
   */
  @SerialName("title")
  public val title: String,
  /**
   * What a keyword filter does to a post that matches it: `warn` returns the post with the filter
   * in its `filtered`, and clients fold it behind the filter's title; `hide` leaves the post out,
   * as if the user could not see it.
   */
  @SerialName("action")
  public val action: KeywordFilterAction,
  /**
   * Where the filter applies, at least one.
   */
  @SerialName("contexts")
  public val contexts: List<KeywordFilterContext>,
  /**
   * The words of the filter, in the order given; a post matches the filter when it matches any of them.
   */
  @SerialName("keywords")
  public val keywords: List<KeywordFilterKeyword>,
  /**
   * When the filter stops applying; omitted for a filter that never does. An expired filter
   * stays, applying nowhere, and applies again when its expiry moves later.
   */
  @SerialName("expires_at")
  public val expiresAt: Instant? = null,
  /**
   * Whether the filter has expired and applies nowhere.
   */
  @SerialName("expired")
  public val expired: Boolean,
  /**
   * When the user created the filter.
   */
  @SerialName("created_at")
  public val createdAt: Instant,
)
