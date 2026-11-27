package dev.mycroblog.core.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * What to translate a post into.
 */
@Serializable
public data class StatusTranslationRequest(
  /**
   * The language to translate into, as a BCP 47 tag.
   */
  @SerialName("target_language")
  public val targetLanguage: String,
)
