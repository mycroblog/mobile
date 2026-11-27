package dev.mycroblog.core.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The software a remote server last reported in its NodeInfo; omitted for a server whose
 * NodeInfo was never read.
 */
@Serializable
public data class AdminInstanceSoftware(
  @SerialName("name")
  public val name: String,
  /**
   * The version as reported; empty when the server reports none.
   */
  @SerialName("version")
  public val version: String,
)
