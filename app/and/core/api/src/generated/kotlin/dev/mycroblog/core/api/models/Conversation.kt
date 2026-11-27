package dev.mycroblog.core.api.models

import java.util.UUID
import kotlin.Boolean
import kotlin.collections.List
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A conversation of the user's direct messages: the `direct` posts that the user sent or
 * received in one thread, whether or not the user follows their authors.
 */
@Serializable
public data class Conversation(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  /**
   * The other accounts of the direct posts that the user sent or received in the
   * conversation: their authors and the accounts they mention.
   */
  @SerialName("accounts")
  public val accounts: List<Account>,
  /**
   * The newest direct post of the conversation that the user may see. A post of an
   * account that the user blocks, is blocked by, or mutes, or of a suspended account, is
   * skipped for the one before it.
   */
  @SerialName("last_status")
  public val lastStatus: Status,
  /**
   * Whether a direct post by another account is newer than the point up to which the user
   * read the conversation (`markConversationRead`), on any device.
   */
  @SerialName("unread")
  public val unread: Boolean,
)
