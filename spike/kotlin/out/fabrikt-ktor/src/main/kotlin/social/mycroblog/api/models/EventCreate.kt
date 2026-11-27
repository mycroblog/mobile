package social.mycroblog.api.models

import kotlin.Int
import kotlin.String
import kotlin.collections.List
import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A new event, either with a fixed date (`starts_at` and `ends_at`) or with the candidate dates
 * of a date poll (`candidates`), not both.
 */
@Serializable
public data class EventCreate(
  /**
   * 1 to 100 characters on one line.
   */
  @SerialName("title")
  public val title: String,
  /**
   * Up to 3,000 characters of plain text; line breaks are kept.
   */
  @SerialName("description")
  public val description: String? = null,
  /**
   * Who sees the event; `public` when omitted.
   */
  @SerialName("visibility")
  public val visibility: EventVisibility? = null,
  /**
   * The IANA name of the event's time zone; the user's time zone when omitted.
   */
  @SerialName("time_zone")
  public val timeZone: String? = null,
  /**
   * Who sees the `physical` and `url` places; `approved` when omitted.
   */
  @SerialName("place_disclosure")
  public val placeDisclosure: EventPlaceDisclosure? = null,
  /**
   * How many attendees may answer `going`, up to 10,000; 0 (the default) for no limit.
   */
  @SerialName("capacity")
  public val capacity: Int? = null,
  /**
   * Who posts on the event's board; `coorganizers` when omitted.
   */
  @SerialName("board_writers")
  public val boardWriters: EventBoardWriters? = null,
  /**
   * Up to 3 places, one of each kind.
   */
  @SerialName("places")
  public val places: List<EventPlace>? = null,
  /**
   * After now and at most a year ahead.
   */
  @SerialName("starts_at")
  public val startsAt: Instant? = null,
  /**
   * After `starts_at` and at most 7 days later.
   */
  @SerialName("ends_at")
  public val endsAt: Instant? = null,
  /**
   * 1 to 20 different candidate dates, each checked as a fixed date: the event is
   * `unscheduled` until the organizers confirm one of them.
   */
  @SerialName("candidates")
  public val candidates: List<EventCandidateCreate>? = null,
)
