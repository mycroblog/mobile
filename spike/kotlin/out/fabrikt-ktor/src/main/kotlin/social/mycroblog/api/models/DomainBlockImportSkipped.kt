package social.mycroblog.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A block of an import that issued nothing.
 */
@Serializable
public data class DomainBlockImportSkipped(
  /**
   * The domain as sent.
   */
  @SerialName("domain")
  public val domain: String,
  /**
   * What importing a row would do. `importable`: it issues the actions of `issue`.
   * `already_applied`: an action in effect of each of the row's kinds covers the domain
   * already, on it or on a parent domain. `no_action`: the row stands for no action (a
   * `noop` without rejections). `duplicate`: an earlier row has the same domain once
   * normalized. `own_domain`: the domain is this server's. `invalid`: the domain, the severity, or the public comment is not valid.
   */
  @SerialName("reason")
  public val reason: DomainBlockImportStatus,
)
