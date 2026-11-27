package dev.mycroblog.core.api.models

import kotlin.String
import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A document that the instance has published, rendered for reading. The administrators write
 * it once, in any language, so every locale gets the same text.
 */
@Serializable
public data class InstanceDocument(
  /**
   * The kind of an instance document: the terms of service, the privacy policy, and the
   * commerce disclosures of the supporter plans. Each is the instance text of the same name
   * (`terms`, `privacy_policy`, `commerce_disclosure`).
   */
  @SerialName("kind")
  public val kind: InstanceDocumentKind,
  /**
   * The document as HTML, which the server renders from the administrators' Markdown and
   * sanitizes (headings from `h2`, paragraphs, lists, quotes, code, rules, tables, and links
   * to `http`, `https` and `mailto`; no raw HTML, scripts or images). Clients put it in the
   * page as it is. The language of the text is unknown: mark it `lang=""`.
   */
  @SerialName("html")
  public val html: String,
  /**
   * When the current revision of the document was recorded.
   */
  @SerialName("revised_at")
  public val revisedAt: Instant,
)
