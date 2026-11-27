package social.mycroblog.api.models

import java.net.URI
import java.util.UUID
import kotlin.String
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * An account that a status mentions.
 */
@Serializable
public data class StatusMention(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  /**
   * The username, without the domain.
   */
  @SerialName("username")
  public val username: String,
  /**
   * The username for local accounts, `username@domain` for remote ones.
   */
  @SerialName("acct")
  public val acct: String,
  /**
   * The account's profile page.
   */
  @Contextual
  @SerialName("url")
  public val url: URI,
)
