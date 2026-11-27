package dev.mycroblog.core.api.models

import kotlin.Boolean
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A change of the preferences; properties left out keep their current values.
 */
@Serializable
public data class UpdatePreferencesRequest(
  /**
   * The UI locale, a BCP 47 language tag. Without `locale_follows_browser`, it stops following the browser.
   */
  @SerialName("locale")
  public val locale: String? = null,
  /**
   * Whether clients with a browser show its language instead of `locale`.
   */
  @SerialName("locale_follows_browser")
  public val localeFollowsBrowser: Boolean? = null,
  /**
   * The time zone, an IANA name. Without `time_zone_follows_browser`, it stops following the browser.
   */
  @SerialName("time_zone")
  public val timeZone: String? = null,
  /**
   * Whether clients with a browser show dates in its time zone instead of `time_zone`.
   */
  @SerialName("time_zone_follows_browser")
  public val timeZoneFollowsBrowser: Boolean? = null,
  /**
   * Who may see a status: `public` everyone, listed on public timelines;
   * `local` logged-in users of this instance and followers; `unlisted` everyone, kept off
   * public timelines; `private` followers and mentioned accounts; `direct` mentioned
   * accounts.
   */
  @SerialName("default_visibility")
  public val defaultVisibility: Visibility? = null,
  /**
   * The defaults of the kinds it has; the others stay.
   */
  @SerialName("default_interaction_policy")
  public val defaultInteractionPolicy: InteractionPolicyChange? = null,
  /**
   * The language of new posts that name none; `und` for undetermined.
   */
  @SerialName("default_post_language")
  public val defaultPostLanguage: String? = null,
  /**
   * Whether clients ask for a description before posting media without one.
   */
  @SerialName("require_media_description")
  public val requireMediaDescription: Boolean? = null,
  /**
   * Whether the web client inserts new timeline posts without asking.
   */
  @SerialName("timeline_auto_insert")
  public val timelineAutoInsert: Boolean? = null,
  /**
   * How a client shows link cards.
   */
  @SerialName("link_card_size")
  public val linkCardSize: LinkCardSize? = null,
  /**
   * The languages that timelines show, at most 64; empty shows every language.
   */
  @SerialName("timeline_languages")
  public val timelineLanguages: List<String>? = null,
)
