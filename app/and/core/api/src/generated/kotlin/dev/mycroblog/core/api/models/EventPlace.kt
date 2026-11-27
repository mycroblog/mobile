package dev.mycroblog.core.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A place of an event. A `physical` place has a `name`, an `address`, and an optional map link in
 * `url`; a `url` place has a `name` and a `url`; a `voice` place has `speakers`, who may speak
 * without asking in the voice chat room that opens when the event starts. A URL is an `http` or
 * `https` URL of up to 2,000 characters, which the server never fetches. The fields that a kind
 * does not have are omitted.
 */
@Serializable
public data class EventPlace(
  /**
   * The kind of a place: `voice` a voice chat room of this server, `physical` a place to meet in
   * person, and `url` a link such as a stream or a meeting of another service.
   */
  @SerialName("kind")
  public val kind: EventPlaceKind,
  /**
   * Up to 100 characters on one line.
   */
  @SerialName("name")
  public val name: String? = null,
  /**
   * Up to 500 characters; line breaks are kept.
   */
  @SerialName("address")
  public val address: String? = null,
  @SerialName("url")
  public val url: String? = null,
  /**
   * Who may speak without asking in the room; `manual` when omitted.
   */
  @SerialName("speakers")
  public val speakers: VoiceRoomSpeakers? = null,
  /**
   * For a `voice` place, the voice chat room that the start of the event's current date
   * opened (`GET /voice/rooms/{id}`), which may have ended since; omitted before the room opens.
   * Ignored in a request.
   */
  @SerialName("room_id")
  public val roomId: String? = null,
  /**
   * For a `voice` place, why the room of the event's current date could not open at the
   * start; omitted otherwise. Ignored in a request.
   */
  @SerialName("room_unavailable")
  public val roomUnavailable: EventRoomUnavailableReason? = null,
)
