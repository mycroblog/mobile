package dev.mycroblog.core.api.models

import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The authenticated user's passkeys, oldest first.
 */
@Serializable
public data class PasskeyList(
  @SerialName("items")
  public val items: List<Passkey>,
)
