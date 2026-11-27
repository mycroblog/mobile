package dev.mycroblog.core.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A domain block CSV to preview.
 */
@Serializable
public data class PreviewDomainBlockImportRequest(
  /**
   * The file's text, with a header or as a list of domains; at most 5,000 rows, and the request body at most 1 MiB, past which the answer is 413.
   */
  @SerialName("csv")
  public val csv: String,
)
