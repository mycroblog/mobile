package social.mycroblog.api.models

import kotlin.String
import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * An account that the user moves from: the user's actor names it in
 * `alsoKnownAs`, which lets it move its followers to the user. An alias alone changes nothing.
 */
@Serializable
public data class AccountAlias(
  /**
   * The ActivityPub id of the alias's actor.
   */
  @SerialName("uri")
  public val uri: String,
  /**
   * The handle of the alias, `username` for a local account and `username@domain` for a
   * remote one; omitted when the instance does not know the account or its handle.
   */
  @SerialName("acct")
  public val acct: String? = null,
  /**
   * When the user added the alias.
   */
  @SerialName("created_at")
  public val createdAt: Instant,
)
