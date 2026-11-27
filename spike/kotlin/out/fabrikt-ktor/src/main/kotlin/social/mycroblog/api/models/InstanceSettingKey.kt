package social.mycroblog.api.models

import kotlin.String
import kotlin.collections.Map
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder

/**
 * The key of an instance setting. New keys may be
 * added; clients show the ones they know.
 */
@Serializable(with = InstanceSettingKey.Serializer::class)
public enum class InstanceSettingKey(
  public val `value`: String,
) {
  @SerialName("registration_mode")
  REGISTRATION_MODE("registration_mode"),
  @SerialName("max_status_length")
  MAX_STATUS_LENGTH("max_status_length"),
  @SerialName("default_locale")
  DEFAULT_LOCALE("default_locale"),
  @SerialName("local_timeline_visibility")
  LOCAL_TIMELINE_VISIBILITY("local_timeline_visibility"),
  @SerialName("federated_timeline_visibility")
  FEDERATED_TIMELINE_VISIBILITY("federated_timeline_visibility"),
  @SerialName("preview_image")
  PREVIEW_IMAGE("preview_image"),
  @SerialName("contact_account")
  CONTACT_ACCOUNT("contact_account"),
  @SerialName("contact_email")
  CONTACT_EMAIL("contact_email"),
  @SerialName("thumbnail")
  THUMBNAIL("thumbnail"),
  @SerialName("remote_media_policy")
  REMOTE_MEDIA_POLICY("remote_media_policy"),
  @SerialName("remote_media_cache_ttl_seconds")
  REMOTE_MEDIA_CACHE_TTL_SECONDS("remote_media_cache_ttl_seconds"),
  @SerialName("authorized_fetch")
  AUTHORIZED_FETCH("authorized_fetch"),
  @SerialName("domain_actions_visibility")
  DOMAIN_ACTIONS_VISIBILITY("domain_actions_visibility"),
  @SerialName("domain_actions_rationale_visibility")
  DOMAIN_ACTIONS_RATIONALE_VISIBILITY("domain_actions_rationale_visibility"),
  @SerialName("appeal_window_days")
  APPEAL_WINDOW_DAYS("appeal_window_days"),
  @SerialName("session_origins_retention_days")
  SESSION_ORIGINS_RETENTION_DAYS("session_origins_retention_days"),
  @SerialName("registration_origins_retention_days")
  REGISTRATION_ORIGINS_RETENTION_DAYS("registration_origins_retention_days"),
  @SerialName("reports_retention_days")
  REPORTS_RETENTION_DAYS("reports_retention_days"),
  @SerialName("moderation_actions_retention_days")
  MODERATION_ACTIONS_RETENTION_DAYS("moderation_actions_retention_days"),
  @SerialName("audit_log_retention_days")
  AUDIT_LOG_RETENTION_DAYS("audit_log_retention_days"),
  @SerialName("search_indexing")
  SEARCH_INDEXING("search_indexing"),
  @SerialName("ai_usage")
  AI_USAGE("ai_usage"),
  @SerialName("indexable_default")
  INDEXABLE_DEFAULT("indexable_default"),
  @SerialName("search_visibilities")
  SEARCH_VISIBILITIES("search_visibilities"),
  @SerialName("security_contact")
  SECURITY_CONTACT("security_contact"),
  @SerialName("require_media_description")
  REQUIRE_MEDIA_DESCRIPTION("require_media_description"),
  @SerialName("link_cards")
  LINK_CARDS("link_cards"),
  @SerialName("link_cards_ignore_robots_txt")
  LINK_CARDS_IGNORE_ROBOTS_TXT("link_cards_ignore_robots_txt"),
  @SerialName("finance_visibility")
  FINANCE_VISIBILITY("finance_visibility"),
  @SerialName("finance_viewer_item_names")
  FINANCE_VIEWER_ITEM_NAMES("finance_viewer_item_names"),
  @SerialName("finance_viewer_item_memos")
  FINANCE_VIEWER_ITEM_MEMOS("finance_viewer_item_memos"),
  @SerialName("finance_viewer_income")
  FINANCE_VIEWER_INCOME("finance_viewer_income"),
  @SerialName("supporter_list_visibility")
  SUPPORTER_LIST_VISIBILITY("supporter_list_visibility"),
  @SerialName("income_visibility")
  INCOME_VISIBILITY("income_visibility"),
  @SerialName("federation_mode")
  FEDERATION_MODE("federation_mode"),
  @SerialName("federation_scope")
  FEDERATION_SCOPE("federation_scope"),
  @SerialName("user_app_registration")
  USER_APP_REGISTRATION("user_app_registration"),
  @SerialName("dynamic_client_registration")
  DYNAMIC_CLIENT_REGISTRATION("dynamic_client_registration"),
  @SerialName("oauth_metadata_document_clients")
  OAUTH_METADATA_DOCUMENT_CLIENTS("oauth_metadata_document_clients"),
  @SerialName("voice_room_starters")
  VOICE_ROOM_STARTERS("voice_room_starters"),
  @SerialName("voice_public_summary")
  VOICE_PUBLIC_SUMMARY("voice_public_summary"),
  @SerialName("voice_participants_retention_days")
  VOICE_PARTICIPANTS_RETENTION_DAYS("voice_participants_retention_days"),
  @SerialName("hook_inbound_status_mode")
  HOOK_INBOUND_STATUS_MODE("hook_inbound_status_mode"),
  @SerialName("hook_inbound_follow_mode")
  HOOK_INBOUND_FOLLOW_MODE("hook_inbound_follow_mode"),
  @SerialName("hook_signup_check_mode")
  HOOK_SIGNUP_CHECK_MODE("hook_signup_check_mode"),
  @SerialName("hook_media_check_mode")
  HOOK_MEDIA_CHECK_MODE("hook_media_check_mode"),
  @SerialName("hook_local_status_mode")
  HOOK_LOCAL_STATUS_MODE("hook_local_status_mode"),
  @SerialName("hook_inbound_actor_mode")
  HOOK_INBOUND_ACTOR_MODE("hook_inbound_actor_mode"),
  @SerialName("hook_local_profile_mode")
  HOOK_LOCAL_PROFILE_MODE("hook_local_profile_mode"),
  @SerialName("hook_inbound_status_on_failure")
  HOOK_INBOUND_STATUS_ON_FAILURE("hook_inbound_status_on_failure"),
  @SerialName("hook_inbound_status_unfollowed_direct")
  HOOK_INBOUND_STATUS_UNFOLLOWED_DIRECT("hook_inbound_status_unfollowed_direct"),
  @SerialName("hook_inbound_follow_on_failure")
  HOOK_INBOUND_FOLLOW_ON_FAILURE("hook_inbound_follow_on_failure"),
  @SerialName("hook_signup_check_on_failure")
  HOOK_SIGNUP_CHECK_ON_FAILURE("hook_signup_check_on_failure"),
  @SerialName("hook_signup_check_reject")
  HOOK_SIGNUP_CHECK_REJECT("hook_signup_check_reject"),
  @SerialName("hook_local_status_account_age_days")
  HOOK_LOCAL_STATUS_ACCOUNT_AGE_DAYS("hook_local_status_account_age_days"),
  @SerialName("hook_local_profile_account_age_days")
  HOOK_LOCAL_PROFILE_ACCOUNT_AGE_DAYS("hook_local_profile_account_age_days"),
  @SerialName("hook_media_check_on_failure")
  HOOK_MEDIA_CHECK_ON_FAILURE("hook_media_check_on_failure"),
  @SerialName("hook_inbound_actor_on_failure")
  HOOK_INBOUND_ACTOR_ON_FAILURE("hook_inbound_actor_on_failure"),
  @SerialName("hook_decisions_retention_days")
  HOOK_DECISIONS_RETENTION_DAYS("hook_decisions_retention_days"),
  @SerialName("invite_creators")
  INVITE_CREATORS("invite_creators"),
  @SerialName("member_invite_max_active")
  MEMBER_INVITE_MAX_ACTIVE("member_invite_max_active"),
  @SerialName("member_invite_max_uses")
  MEMBER_INVITE_MAX_USES("member_invite_max_uses"),
  @SerialName("member_invite_max_lifetime_seconds")
  MEMBER_INVITE_MAX_LIFETIME_SECONDS("member_invite_max_lifetime_seconds"),
  @SerialName("public_counts")
  PUBLIC_COUNTS("public_counts"),
  @SerialName("landing_fediverse_explanation")
  LANDING_FEDIVERSE_EXPLANATION("landing_fediverse_explanation"),
  @SerialName("landing_features")
  LANDING_FEATURES("landing_features"),
  @SerialName("featured_accounts")
  FEATURED_ACCOUNTS("featured_accounts"),
  @SerialName("login_providers_only")
  LOGIN_PROVIDERS_ONLY("login_providers_only"),
  @SerialName("remote_media_cache_max_bytes")
  REMOTE_MEDIA_CACHE_MAX_BYTES("remote_media_cache_max_bytes"),
  @SerialName("remote_status_retention_days")
  REMOTE_STATUS_RETENTION_DAYS("remote_status_retention_days"),
  @SerialName("remote_status_max_retention_days")
  REMOTE_STATUS_MAX_RETENTION_DAYS("remote_status_max_retention_days"),
  @SerialName("relay_status_retention_days")
  RELAY_STATUS_RETENTION_DAYS("relay_status_retention_days"),
  @SerialName("oauth_client_rate_limits")
  OAUTH_CLIENT_RATE_LIMITS("oauth_client_rate_limits"),
  @SerialName("translation_providers")
  TRANSLATION_PROVIDERS("translation_providers"),
  @SerialName("translation_provider_visibilities")
  TRANSLATION_PROVIDER_VISIBILITIES("translation_provider_visibilities"),
  @SerialName("translation_browser")
  TRANSLATION_BROWSER("translation_browser"),
  @SerialName("translation_user_monthly_characters")
  TRANSLATION_USER_MONTHLY_CHARACTERS("translation_user_monthly_characters"),
  @SerialName("translation_server_monthly_characters")
  TRANSLATION_SERVER_MONTHLY_CHARACTERS("translation_server_monthly_characters"),
  @SerialName("translation_retention_days")
  TRANSLATION_RETENTION_DAYS("translation_retention_days"),
  @SerialName("translation_reports_to_server")
  TRANSLATION_REPORTS_TO_SERVER("translation_reports_to_server"),
  @SerialName("auto_delete_default_enabled")
  AUTO_DELETE_DEFAULT_ENABLED("auto_delete_default_enabled"),
  @SerialName("auto_delete_default_min_age_days")
  AUTO_DELETE_DEFAULT_MIN_AGE_DAYS("auto_delete_default_min_age_days"),
  @SerialName("auto_delete_default_keep_pinned")
  AUTO_DELETE_DEFAULT_KEEP_PINNED("auto_delete_default_keep_pinned"),
  @SerialName("auto_delete_default_keep_bookmarked")
  AUTO_DELETE_DEFAULT_KEEP_BOOKMARKED("auto_delete_default_keep_bookmarked"),
  @SerialName("auto_delete_default_keep_favourited")
  AUTO_DELETE_DEFAULT_KEEP_FAVOURITED("auto_delete_default_keep_favourited"),
  @SerialName("auto_delete_default_keep_media")
  AUTO_DELETE_DEFAULT_KEEP_MEDIA("auto_delete_default_keep_media"),
  @SerialName("auto_delete_default_keep_polls")
  AUTO_DELETE_DEFAULT_KEEP_POLLS("auto_delete_default_keep_polls"),
  @SerialName("auto_delete_default_keep_public")
  AUTO_DELETE_DEFAULT_KEEP_PUBLIC("auto_delete_default_keep_public"),
  @SerialName("auto_delete_default_keep_local")
  AUTO_DELETE_DEFAULT_KEEP_LOCAL("auto_delete_default_keep_local"),
  @SerialName("auto_delete_default_keep_unlisted")
  AUTO_DELETE_DEFAULT_KEEP_UNLISTED("auto_delete_default_keep_unlisted"),
  @SerialName("auto_delete_default_keep_private")
  AUTO_DELETE_DEFAULT_KEEP_PRIVATE("auto_delete_default_keep_private"),
  @SerialName("auto_delete_default_keep_direct")
  AUTO_DELETE_DEFAULT_KEEP_DIRECT("auto_delete_default_keep_direct"),
  @SerialName("auto_delete_default_keep_min_boosts")
  AUTO_DELETE_DEFAULT_KEEP_MIN_BOOSTS("auto_delete_default_keep_min_boosts"),
  @SerialName("auto_delete_default_keep_min_favourites")
  AUTO_DELETE_DEFAULT_KEEP_MIN_FAVOURITES("auto_delete_default_keep_min_favourites"),
  @SerialName("auto_delete_max_age_days")
  AUTO_DELETE_MAX_AGE_DAYS("auto_delete_max_age_days"),
  @SerialName("auto_delete_max_allow_pinned")
  AUTO_DELETE_MAX_ALLOW_PINNED("auto_delete_max_allow_pinned"),
  @SerialName("auto_delete_max_kept_pins")
  AUTO_DELETE_MAX_KEPT_PINS("auto_delete_max_kept_pins"),
  @SerialName("auto_delete_max_allow_bookmarked")
  AUTO_DELETE_MAX_ALLOW_BOOKMARKED("auto_delete_max_allow_bookmarked"),
  @SerialName("auto_delete_max_allow_favourited")
  AUTO_DELETE_MAX_ALLOW_FAVOURITED("auto_delete_max_allow_favourited"),
  @SerialName("auto_delete_max_allow_media")
  AUTO_DELETE_MAX_ALLOW_MEDIA("auto_delete_max_allow_media"),
  @SerialName("auto_delete_max_allow_polls")
  AUTO_DELETE_MAX_ALLOW_POLLS("auto_delete_max_allow_polls"),
  @SerialName("auto_delete_max_allow_visibilities")
  AUTO_DELETE_MAX_ALLOW_VISIBILITIES("auto_delete_max_allow_visibilities"),
  @SerialName("auto_delete_max_allow_thresholds")
  AUTO_DELETE_MAX_ALLOW_THRESHOLDS("auto_delete_max_allow_thresholds"),
  @SerialName("auto_delete_grace_days")
  AUTO_DELETE_GRACE_DAYS("auto_delete_grace_days"),
  @SerialName("single_user")
  SINGLE_USER("single_user"),
  @SerialName("max_drafts")
  MAX_DRAFTS("max_drafts"),
  @SerialName("max_scheduled_posts")
  MAX_SCHEDULED_POSTS("max_scheduled_posts"),
  @SerialName("max_scheduled_posts_per_day")
  MAX_SCHEDULED_POSTS_PER_DAY("max_scheduled_posts_per_day"),
  @SerialName("scheduled_post_min_lead_minutes")
  SCHEDULED_POST_MIN_LEAD_MINUTES("scheduled_post_min_lead_minutes"),
  @SerialName("scheduled_post_max_lead_days")
  SCHEDULED_POST_MAX_LEAD_DAYS("scheduled_post_max_lead_days"),
  @SerialName("article_authoring")
  ARTICLE_AUTHORING("article_authoring"),
  @SerialName("max_article_bytes")
  MAX_ARTICLE_BYTES("max_article_bytes"),
  @SerialName("max_article_images")
  MAX_ARTICLE_IMAGES("max_article_images"),
  @SerialName("auto_delete_default_keep_articles")
  AUTO_DELETE_DEFAULT_KEEP_ARTICLES("auto_delete_default_keep_articles"),
  @SerialName("auto_delete_max_allow_articles")
  AUTO_DELETE_MAX_ALLOW_ARTICLES("auto_delete_max_allow_articles"),
  @SerialName("event_creators")
  EVENT_CREATORS("event_creators"),
  @SerialName("event_records_retention_days")
  EVENT_RECORDS_RETENTION_DAYS("event_records_retention_days"),
  @SerialName("funding_meter_visibility")
  FUNDING_METER_VISIBILITY("funding_meter_visibility"),
  @SerialName("funding_meter_target_amount")
  FUNDING_METER_TARGET_AMOUNT("funding_meter_target_amount"),
  @SerialName("funding_meter_target_currency")
  FUNDING_METER_TARGET_CURRENCY("funding_meter_target_currency"),
  @SerialName("funding_meter_rail")
  FUNDING_METER_RAIL("funding_meter_rail"),
  @SerialName("ux_measurement")
  UX_MEASUREMENT("ux_measurement"),
  @SerialName("ux_measurement_share")
  UX_MEASUREMENT_SHARE("ux_measurement_share"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<InstanceSettingKey> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.InstanceSettingKey", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: InstanceSettingKey) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): InstanceSettingKey {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, InstanceSettingKey> =
        entries.associateBy(InstanceSettingKey::value)

    public fun fromValue(`value`: String): InstanceSettingKey = mapping[value] ?: UNRECOGNIZED
  }
}
