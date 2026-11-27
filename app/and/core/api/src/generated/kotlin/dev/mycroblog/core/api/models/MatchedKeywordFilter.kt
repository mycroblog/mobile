package dev.mycroblog.core.api.models

import java.util.UUID
import kotlin.String
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The keyword filter that a post matched, as far as a folded post shows it.
 */
@Serializable
public data class MatchedKeywordFilter(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  /**
   * The title of the filter, which the folded post shows.
   */
  @SerialName("title")
  public val title: String,
)
