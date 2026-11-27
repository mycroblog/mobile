package dev.mycroblog.core.api.models

import java.util.UUID
import kotlin.String
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A plan of the supporter list. The name is written by the administrators in any language;
 * render it with `dir="auto"`.
 */
@Serializable
public data class SupporterListPlan(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  @SerialName("name")
  public val name: String,
)
