package dev.mycroblog.core.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The confirmation of the closure of the server.
 */
@Serializable
public data class CloseServiceRequest(
  /**
   * The domain of the server (`Instance.domain`), typed by the administrator to confirm; compared without regard to case.
   */
  @SerialName("confirm_domain")
  public val confirmDomain: String,
)
