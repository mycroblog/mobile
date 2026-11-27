package dev.mycroblog.core.api.models

import java.util.UUID
import kotlin.Boolean
import kotlin.String
import kotlin.collections.List
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * An OAuth client, as the administrators see it.
 */
@Serializable
public data class AdminOAuthClient(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  /**
   * The identifier that the client sends, or the URL of its metadata document.
   */
  @SerialName("client_id")
  public val clientId: String,
  /**
   * The client's name. For a client registered by its metadata document, the name that the
   * document declares; every other name is a claim of whoever registered the client.
   */
  @SerialName("name")
  public val name: String,
  /**
   * The client's home page; omitted when it has none.
   */
  @SerialName("website")
  public val website: String? = null,
  /**
   * The route by which an OAuth client was registered: `first_party` is one of the
   * server's own clients, `user` a client that a signed-in user registered, `metadata_document` a
   * client whose `client_id` is the URL of a Client ID Metadata Document, and `dynamic` a client
   * that registered itself (RFC 7591).
   */
  @SerialName("registration")
  public val registration: OAuthClientRegistration,
  /**
   * Whether the client has a secret (a confidential client) or not (a public client).
   */
  @SerialName("confidential")
  public val confidential: Boolean,
  /**
   * The most that the client may be granted.
   */
  @SerialName("scopes")
  public val scopes: List<String>,
  /**
   * The account of the user who registered the client; omitted for the other routes and
   * when that account is deleted (`owner_deleted`).
   */
  @SerialName("owner")
  public val owner: Account? = null,
  /**
   * Whether a user registered the client whose account no longer exists.
   */
  @SerialName("owner_deleted")
  public val ownerDeleted: Boolean,
  /**
   * Whether the client is authorized and gets tokens. An administrator disables and enables it.
   */
  @SerialName("state")
  public val state: AdminOAuthClientState,
  @SerialName("created_at")
  public val createdAt: Instant,
)
