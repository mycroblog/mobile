package dev.mycroblog.core.api.models

import java.util.UUID
import kotlin.Boolean
import kotlin.Int
import kotlin.String
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A relay that the instance joins or joined.
 */
@Serializable
public data class AdminRelay(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  /**
   * How a relay is joined. `mastodon`: at its inbox, by a `Follow` of the Public
   * collection. `litepub`: at its actor, by a `Follow` of the actor.
   */
  @SerialName("protocol")
  public val protocol: RelayProtocol,
  /**
   * The URL it was registered with, its inbox or its actor.
   */
  @SerialName("url")
  public val url: String,
  /**
   * Where the public posts are delivered.
   */
  @SerialName("inbox_url")
  public val inboxUrl: String,
  /**
   * Whether the public posts it relays are taken in; `false` only sends.
   */
  @SerialName("receive")
  public val receive: Boolean,
  /**
   * Where the membership of a relay stands. `pending`: the `Follow` is sent and not
   * answered. `accepted`: public posts go to the relay and, with `receive`, come from it.
   * `rejected`: the relay rejected the `Follow` or ended the membership. `left`: the
   * instance sent the `Undo` of its `Follow`.
   */
  @SerialName("state")
  public val state: RelayState,
  @SerialName("state_changed_at")
  public val stateChangedAt: Instant,
  /**
   * The relay's actor, which accepted the `Follow`; omitted unless `accepted`.
   */
  @SerialName("actor_uri")
  public val actorUri: String? = null,
  /**
   * The activities it brought in during the last 24 hours that were taken in.
   */
  @SerialName("taken_last_day")
  public val takenLastDay: Int,
  /**
   * The activities it brought in during the last 24 hours that were dropped over the
   * hourly limit (`relay_max_activities_per_hour`).
   */
  @SerialName("dropped_last_day")
  public val droppedLastDay: Int,
  /**
   * Whether a domain action suspends its server: nothing goes to it or comes from it
   * while it does, whatever `state` says.
   */
  @SerialName("domain_suspended")
  public val domainSuspended: Boolean,
  @SerialName("created_at")
  public val createdAt: Instant,
)
