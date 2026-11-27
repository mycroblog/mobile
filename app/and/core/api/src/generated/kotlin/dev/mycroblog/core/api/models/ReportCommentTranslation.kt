package dev.mycroblog.core.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The comment of a report in another language. The server does not keep it.
 */
@Serializable
public data class ReportCommentTranslation(
  /**
   * The language of this answer, as a BCP 47 tag.
   */
  @SerialName("language")
  public val language: String,
  /**
   * The language translated from, which a provider may have detected; `und` when it is not known.
   */
  @SerialName("source_language")
  public val sourceLanguage: String,
  /**
   * The comment as plain text.
   */
  @SerialName("comment")
  public val comment: String,
  /**
   * The service that translated a post, for the attribution next to the translation.
   */
  @SerialName("provider")
  public val provider: TranslationProvider? = null,
)
