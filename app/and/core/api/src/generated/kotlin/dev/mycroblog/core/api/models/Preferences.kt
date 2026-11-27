package dev.mycroblog.core.api.models

import kotlin.Boolean
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The authenticated user's preferences.
 */
@Serializable
public data class Preferences(
  /**
   * The UI locale, a BCP 47 language tag.
   */
  @SerialName("locale")
  public val locale: String,
  /**
   * Whether clients with a browser show its language instead of `locale`, or the server's default
   * language when the browser names none they have. They keep `locale` at what they last detected,
   * for what has no browser (email, push). True for a new user.
   */
  @SerialName("locale_follows_browser")
  public val localeFollowsBrowser: Boolean,
  /**
   * The time zone, an IANA name such as `Asia/Tokyo`; `UTC` until one is chosen.
   */
  @SerialName("time_zone")
  public val timeZone: String,
  /**
   * Whether a time zone has been chosen: false for a new user until a request sets
   * `time_zone`. Clients may then set it to the device's time zone.
   */
  @SerialName("time_zone_chosen")
  public val timeZoneChosen: Boolean,
  /**
   * Whether clients with a browser show dates in its time zone instead of `time_zone`. They keep
   * `time_zone` at what they last detected, for what has no browser. True for a new user.
   */
  @SerialName("time_zone_follows_browser")
  public val timeZoneFollowsBrowser: Boolean,
  /**
   * Who may see a status: `public` everyone, listed on public timelines;
   * `local` logged-in users of this instance and followers; `unlisted` everyone, kept off
   * public timelines; `private` followers and mentioned accounts; `direct` mentioned
   * accounts.
   */
  @SerialName("default_visibility")
  public val defaultVisibility: Visibility,
  /**
   * Who may reply to, boost, and quote the user's new statuses, for the kinds their
   * `interaction_policy` leaves out. Everyone at once for a new user.
   */
  @SerialName("default_interaction_policy")
  public val defaultInteractionPolicy: InteractionPolicy,
  /**
   * The language of new posts that name none, a BCP 47 language tag; `und` when undetermined.
   */
  @SerialName("default_post_language")
  public val defaultPostLanguage: String,
  /**
   * Whether clients ask for a description before posting media without one.
   */
  @SerialName("require_media_description")
  public val requireMediaDescription: Boolean,
  /**
   * Whether emails no longer reach the primary address, read-only; omitted when they do.
   * After a `hard_bounce`, the user asks for the verification email again
   * (`POST /accounts/email/verification`) to show that the address works.
   */
  @SerialName("email_suppression")
  public val emailSuppression: EmailSuppression? = null,
  /**
   * Whether the web client inserts new timeline posts without asking, instead of offering them
   * behind a "show new posts" button. On for a new user; the same on every device.
   */
  @SerialName("timeline_auto_insert")
  public val timelineAutoInsert: Boolean,
  /**
   * How the web client shows link cards: `large`, with the image above the text, or `small`, with
   * a square image beside it. `large` for a new user; the same on every device.
   */
  @SerialName("link_card_size")
  public val linkCardSize: LinkCardSize,
  /**
   * The languages that timelines show, sorted; empty shows every language.
   */
  @SerialName("timeline_languages")
  public val timelineLanguages: List<String>,
)
