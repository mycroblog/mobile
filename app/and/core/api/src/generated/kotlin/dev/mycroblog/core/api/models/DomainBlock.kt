package dev.mycroblog.core.api.models

import kotlin.Boolean
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A row of Mastodon's domain block CSV. The server checks the domain (a U-label spelling
 * such as `例え.jp` is accepted) and the public comment (at most 5,000 characters).
 */
@Serializable
public data class DomainBlock(
  @SerialName("domain")
  public val domain: String,
  /**
   * The `#severity` of Mastodon's domain block CSV. `suspend`: the action `suspend`.
   * `silence`: Mastodon's name of the action `limit`. `noop`: no action but the rejections.
   */
  @SerialName("severity")
  public val severity: DomainBlockSeverity,
  @SerialName("reject_media")
  public val rejectMedia: Boolean,
  @SerialName("reject_reports")
  public val rejectReports: Boolean,
  @SerialName("public_comment")
  public val publicComment: String? = null,
  @SerialName("obfuscate")
  public val obfuscate: Boolean,
)
