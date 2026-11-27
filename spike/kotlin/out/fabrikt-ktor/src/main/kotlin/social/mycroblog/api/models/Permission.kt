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
 * A capability that a role grants. `manage_accounts`: the accounts and their moderation
 * actions.
 * `manage_domains`: the health of remote servers and the moderation actions on domains.
 * `manage_reports`: the reports. `manage_rules`: the instance rules. `manage_roles`: the
 * roles of users. `manage_jobs`: the dead jobs of the job queue. `view_audit`: the audit
 * log. `manage_login_recovery`: recovering a local user's sign-in, by removing their two-factor
 * authentication or issuing a password reset link (only the built-in admin role by
 * default). `manage_custom_emojis`: the custom emoji, local and
 * remote. `manage_webhooks`: the webhooks of the instance. `manage_announcements`: the
 * server's announcements to every local user. `manage_finance`: the instance's costs and
 * the state of the billing imports (only the built-in admin role by default). `view_finance`:
 * reading the totals of the instance's costs on `GET /finance/cost_totals`, a member
 * permission outside the staff's areas. `manage_supporters`: the supporter program, its plans, and their
 * subscriptions (only the built-in admin role by default). `create_custom_emojis`: requesting
 * new local custom emoji, which a holder of `manage_custom_emojis` approves, and changing and
 * deleting the ones the user made, on `/custom_emoji_requests` and `/custom_emojis/mine`, a
 * member permission. `manage_oauth_apps`: all the OAuth clients, disabling them,
 * and denying the hosts of Client ID Metadata Documents on `/admin/oauth_clients` and
 * `/admin/oauth_client_host_denials`. `manage_service_closure`: announcing the closure
 * of the service, changing its dates, and withdrawing it on `/admin/closure` (only the built-in
 * admin role by default). `start_voice_rooms`: opening voice chat rooms while the
 * instance setting `voice_room_starters` is `permitted`, a member permission (only the
 * built-in admin role by default). `manage_errors`: the error groups of the server
 * and the web client on `/admin/error_groups`, their `admin_error_group` notifications, and
 * the webhook events `error_group.*` (only the built-in admin role by default).
 * `manage_login_providers`: the external identity providers that users sign in with (only the
 * built-in admin role by default). `create_invites`: creating, listing, and revoking
 * invites of one's own on `/invites` while the instance setting `invite_creators` is
 * `permitted`, a member permission (only the built-in admin role by default).
 * `keep_posts_longer`: keeping one's own posts past the server maximum of the automatic
 * deletion and turning it off on `/preferences/auto_delete` while the maximum is on, a member
 * permission (only the built-in admin role by default). `create_articles`: writing new
 * articles with `createArticle` while the instance setting `article_authoring` is
 * `permission`, a member permission (only the built-in admin role by default).
 * `create_events`: creating events on `POST /events` while the instance setting
 * `event_creators` is `permitted`, a member permission (only the built-in admin role by
 * default). The others guard no client API operation yet.
 */
@Serializable(with = Permission.Serializer::class)
public enum class Permission(
  public val `value`: String,
) {
  @SerialName("manage_reports")
  MANAGE_REPORTS("manage_reports"),
  @SerialName("manage_accounts")
  MANAGE_ACCOUNTS("manage_accounts"),
  @SerialName("manage_domains")
  MANAGE_DOMAINS("manage_domains"),
  @SerialName("manage_rules")
  MANAGE_RULES("manage_rules"),
  @SerialName("manage_settings")
  MANAGE_SETTINGS("manage_settings"),
  @SerialName("manage_roles")
  MANAGE_ROLES("manage_roles"),
  @SerialName("manage_invites")
  MANAGE_INVITES("manage_invites"),
  @SerialName("view_audit")
  VIEW_AUDIT("view_audit"),
  @SerialName("manage_jobs")
  MANAGE_JOBS("manage_jobs"),
  @SerialName("manage_login_recovery")
  MANAGE_LOGIN_RECOVERY("manage_login_recovery"),
  @SerialName("manage_custom_emojis")
  MANAGE_CUSTOM_EMOJIS("manage_custom_emojis"),
  @SerialName("manage_webhooks")
  MANAGE_WEBHOOKS("manage_webhooks"),
  @SerialName("manage_announcements")
  MANAGE_ANNOUNCEMENTS("manage_announcements"),
  @SerialName("manage_finance")
  MANAGE_FINANCE("manage_finance"),
  @SerialName("view_finance")
  VIEW_FINANCE("view_finance"),
  @SerialName("manage_supporters")
  MANAGE_SUPPORTERS("manage_supporters"),
  @SerialName("create_custom_emojis")
  CREATE_CUSTOM_EMOJIS("create_custom_emojis"),
  @SerialName("manage_oauth_apps")
  MANAGE_OAUTH_APPS("manage_oauth_apps"),
  @SerialName("manage_service_closure")
  MANAGE_SERVICE_CLOSURE("manage_service_closure"),
  @SerialName("start_voice_rooms")
  START_VOICE_ROOMS("start_voice_rooms"),
  @SerialName("manage_errors")
  MANAGE_ERRORS("manage_errors"),
  @SerialName("manage_login_providers")
  MANAGE_LOGIN_PROVIDERS("manage_login_providers"),
  @SerialName("create_invites")
  CREATE_INVITES("create_invites"),
  @SerialName("keep_posts_longer")
  KEEP_POSTS_LONGER("keep_posts_longer"),
  @SerialName("create_articles")
  CREATE_ARTICLES("create_articles"),
  @SerialName("create_events")
  CREATE_EVENTS("create_events"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<Permission> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.Permission", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: Permission) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): Permission {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, Permission> = entries.associateBy(Permission::value)

    public fun fromValue(`value`: String): Permission = mapping[value] ?: UNRECOGNIZED
  }
}
