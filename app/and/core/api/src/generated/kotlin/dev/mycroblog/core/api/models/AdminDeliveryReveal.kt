package dev.mycroblog.core.api.models

import java.util.UUID
import kotlin.String
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The fields of a delivery that the list masks.
 */
@Serializable
public data class AdminDeliveryReveal(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  /**
   * Why the sender, the activity URI, and a personal inbox of a delivery are masked. `private`,
   * `direct`, `local`: the activity is about a post of that visibility. `approves_followers`: a
   * follow, its undo, an accept, or a reject of a local account that approves its followers. `unknown`:
   * the post or the account is gone, so what it was is no longer known.
   */
  @SerialName("mask")
  public val mask: AdminDeliveryMask? = null,
  /**
   * The local account that sent the activity.
   */
  @SerialName("sender")
  public val sender: AdminDeliverySender,
  @SerialName("activity_uri")
  public val activityUri: String,
  @SerialName("inbox_url")
  public val inboxUrl: String,
)
