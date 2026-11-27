package dev.mycroblog.core.api.models

import kotlin.Boolean
import kotlin.Int
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A moderation action to take on a domain.
 */
@Serializable
public data class CreateDomainActionRequest(
  /**
   * The remote domain, such as `spam.example`; a U-label spelling such as `例え.jp` is
   * accepted. The action covers its subdomains.
   */
  @SerialName("domain")
  public val domain: String,
  /**
   * `suspend`: no federation with the domain at all. `limit`: its posts stay off the public
   * timelines and its accounts reach only their followers. `reject_media`: its media are
   * not fetched. `reject_reports`: its reports are dropped. `force_sensitive`: its media
   * are marked sensitive.
   */
  @SerialName("kind")
  public val kind: AdminDomainActionKind,
  @SerialName("public_comment")
  public val publicComment: String? = null,
  @SerialName("private_note")
  public val privateNote: String? = null,
  @SerialName("obfuscate")
  public val obfuscate: Boolean = false,
  /**
   * The seconds the action lasts, at most ten years (315360000); omitted, it lasts until it
   * is lifted.
   */
  @SerialName("expires_in")
  public val expiresIn: Int? = null,
)
