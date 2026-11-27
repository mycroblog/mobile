package dev.mycroblog.core.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The account to add as an alias.
 */
@Serializable
public data class AddAccountAliasRequest(
  /**
   * The handle, `username` or `username@domain` (a leading `@` is ignored), or the `https`
   * URL of the account's actor or profile page.
   */
  @SerialName("acct")
  public val acct: String,
)
