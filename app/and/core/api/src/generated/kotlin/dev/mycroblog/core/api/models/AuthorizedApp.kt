package dev.mycroblog.core.api.models

import java.util.UUID
import kotlin.Boolean
import kotlin.String
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * An app the authenticated user authorized: the user's grants of one OAuth client that still
 * work. The name and the website are what the app says about itself; `registration`,
 * `client_id` and `registered_by` are what the server knows.
 */
@Serializable
public data class AuthorizedApp(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  /**
   * The client's public identifier. For an app registered by its Client ID Metadata Document it
   * is the document's `https` URL, whose host shows where the app comes from.
   */
  @SerialName("client_id")
  public val clientId: String,
  /**
   * The app's name, as the app gives it.
   */
  @SerialName("name")
  public val name: String,
  /**
   * The app's home page, as the app gives it. Absent when it gave none.
   */
  @SerialName("website")
  public val website: String? = null,
  /**
   * How the app was registered: `first_party` for the server's own client, `user` for an app a
   * user registered, `metadata_document` for a Client ID Metadata Document, and `dynamic` for
   * dynamic client registration (self-declared).
   */
  @SerialName("registration")
  public val registration: AuthorizedAppRegistration,
  /**
   * The username of the user who registered the app (`user` only). Absent otherwise.
   */
  @SerialName("registered_by")
  public val registeredBy: String? = null,
  /**
   * Whether the app is one of the server's own clients.
   */
  @SerialName("first_party")
  public val firstParty: Boolean,
  /**
   * The scopes the user granted, space separated, as the union of the app's working grants.
   */
  @SerialName("scope")
  public val scope: String,
  /**
   * When the user first authorized the app (the oldest of its working grants).
   */
  @SerialName("authorized_at")
  public val authorizedAt: Instant,
  /**
   * When the app last made a request with a grant of the user, exact to the hour. Absent when
   * no use was recorded.
   */
  @SerialName("last_used_at")
  public val lastUsedAt: Instant? = null,
)
