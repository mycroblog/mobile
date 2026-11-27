package dev.mycroblog.core.api.models

import kotlin.Int
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A row of a domain block CSV and what importing it would do.
 */
@Serializable
public data class DomainBlockImportRow(
  /**
   * The line of the file the row starts on, from 1.
   */
  @SerialName("line")
  public val line: Int,
  /**
   * The domain as written in the row.
   */
  @SerialName("written_domain")
  public val writtenDomain: String,
  /**
   * A row of Mastodon's domain block CSV. The server checks the domain (a U-label spelling
   * such as `例え.jp` is accepted) and the public comment (at most 5,000 characters).
   */
  @SerialName("block")
  public val block: DomainBlock? = null,
  /**
   * What importing a row would do. `importable`: it issues the actions of `issue`.
   * `already_applied`: an action in effect of each of the row's kinds covers the domain
   * already, on it or on a parent domain. `no_action`: the row stands for no action (a
   * `noop` without rejections). `duplicate`: an earlier row has the same domain once
   * normalized. `own_domain`: the domain is this server's. `invalid`: the domain, the severity, or the public comment is not valid.
   */
  @SerialName("status")
  public val status: DomainBlockImportStatus,
  /**
   * The kinds of the actions the import would issue; empty unless `importable`.
   */
  @SerialName("issue")
  public val issue: List<AdminDomainActionKind>,
)
