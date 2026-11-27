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
 * The kind of an audited operation, each on one kind of target. On an `account`:
 * `account_action_issued`, `account_action_lifted`, `account_deletion_canceled`,
 * `revealed_private_data` (a moderator was shown the account's email address and IP
 * addresses, a page of its sign-ins, or a page of the accounts that shared an IP address), `two_factor_disabled` (an administrator removed the account's two-factor
 * authentication), `password_reset_link_issued` (an administrator issued a link that sets
 * a new password for the account's user), `email_suppression_lifted` (a moderator lifted the
 * suppression of the primary email address of the account's user), `account_moved` (a limited
 * local account moved to another local account, which does not inherit the limit),
 * `account_deliveries_viewed` (a holder of `manage_domains` listed the deliveries of the
 * account to a remote server; the target name is the handle and the server). On a `domain`:
 * `domain_action_issued`, `domain_action_lifted`, `instance_marked_available` (an operator
 * cleared the failures of the server), `delivery_revealed` (a holder of `manage_domains` was
 * shown the masked sender, activity URI, and inbox of a delivery to the server, whose ID is
 * `source_event_id`). On a `report`: `report_assigned`,
 * `report_unassigned`, `report_resolved`, `report_reopened`, `report_note_added`,
 * `report_forwarded` (a moderator forwarded the report to the target's server). On a
 * `user` (a registration):
 * `registration_approved`, `registration_rejected`. On a `setting`: `setting_changed`
 * (an instance setting), `text_changed` (an instance text). On a `rule`:
 * `rule_added`, `rule_revised`, `rule_moved`, `rule_retired`. On a `link_card_domain`:
 * `link_card_domain_fetch_stopped`, `link_card_domain_images_stopped` (the rule of the link
 * card crawler set on the domain), `link_card_domain_rule_removed`. On a `domain_allow`:
 * `domain_allow_added`, `domain_allow_removed` (a domain added to and removed from the allowlist
 * of federation). On a `role`: `role_granted`,
 * `role_revoked` (a role of a user; the label is the key and the handle), `role_created`,
 * `role_updated` (its position or permissions), `role_deleted` (the label is the key). On
 * an `appeal`:
 * `appeal_upheld`, `appeal_overturned` (which also records the action's
 * `account_action_lifted`, and for a `ban` the `account_deletion_canceled` on the
 * `account`). On a `signup_block`: `signup_block_added`, `signup_block_lifted`. On a
 * `hook_decision`: `hook_decision_applied` (the server acted on a decision hook's verdict
 * and left state; the actor is the system), `hook_decision_overridden` (a moderator
 * canceled the decision, also when overturning its appeal), `hook_appeal_upheld` (a
 * moderator upheld the appeal of a refused post, and the decision stays). On a
 * `custom_emoji`: `custom_emoji_created`, `custom_emoji_image_replaced`,
 * `custom_emoji_copied` (a new local emoji copied from a remote one),
 * `custom_emoji_updated` (its category, aliases, or place in the picker),
 * `custom_emoji_enabled`, `custom_emoji_disabled`, `custom_emoji_deleted`. On a `webhook`:
 * `webhook_created`, `webhook_updated` (its URL, description, or events),
 * `webhook_deleted`, `webhook_enabled`, `webhook_disabled` (by the `system` when the
 * endpoint answered 410 Gone or kept failing), `webhook_secret_rotated`; the label is its
 * description and the host name of its URL, never the URL. On an `announcement`:
 * `announcement_created`, `announcement_updated` (its text or window),
 * `announcement_published`, `announcement_withdrawn`, `announcement_deleted` (a draft); the
 * label is the first line of its text. On an `invite`: `invite_created`, `invite_revoked`;
 * the label is the first characters of its code, never the whole code. On a `relay`: `relay_added`,
 * `relay_followed`, `relay_unfollowed`, `relay_removed`, and `relay_accepted`,
 * `relay_rejected` (by the `remote` relay); the label is its URL. The refetch of remote
 * posts from their servers: `status_refetch_queued` on a `status` (the label is its URI),
 * `account_statuses_refetch_queued` on an `account`, and `domain_statuses_refetch_queued`
 * on a `domain`. On a `billing_source`: `billing_import_queued`, an import started by hand;
 * the ID is the source (`aws`, `gcp`, `cloudflare`, `plugin`) and the label the provider's name. On a `cost_item`:
 * `cost_item_created`, `cost_item_relabeled` (its name, provider, category, or memo),
 * `cost_item_price_revised`, `cost_item_end_changed`, and `cost_item_deleted`; the label is
 * the item's name. On the `supporter_program`: `supporter_program_enabled`,
 * `supporter_program_disabled`, where the label is the payment provider's name, and
 * `supporter_tips_enabled`, `supporter_tips_revised` (their amounts), and
 * `supporter_tips_disabled`, where the label is the currency and the amounts in minor units. On a
 * `supporter_plan`: `supporter_plan_created`, `supporter_plan_revised`, and
 * `supporter_plan_retired`; the label is the plan's name. On a `supporter_subscription`:
 * `supporter_subscription_ended`, an administrator ended it at once, without a refund; the
 * label is the plan's name and the subscriber's handle. On a `custom_emoji_request`:
 * `custom_emoji_request_submitted` and `custom_emoji_request_withdrawn` by the requester, and
 * `custom_emoji_request_approved` (which also records the emoji's `custom_emoji_created` or
 * `custom_emoji_image_replaced`) and `custom_emoji_request_rejected`; the label is the
 * `:shortcode:` of the request. On a `payment_provider`: `payment_reconcile_queued`, a
 * reconciliation with the payment provider started by hand; the ID and the label are the
 * provider's name (`stripe`, `payjp`). On an `oauth_client`: `oauth_client_disabled`, which also revokes
 * every token of the client, and `oauth_client_enabled`; the label is the client's name and its
 * `client_id`. On an `oauth_client_host`: `oauth_client_host_denied`, which also revokes the
 * tokens of the clients on the host, and `oauth_client_host_allowed`; the ID and the label are
 * the host. On the `instance`: `service_closure_announced`, the announcement of the closure of
 * the service or a change of its dates, `service_closure_withdrawn`, its withdrawal,
 * `service_closure_closed`, the closure of the server, and `service_closure_delivery_completed`,
 * the end of the delivery of the closure's `Delete` by the server; the ID and the label are
 * `service_closure`. On a `voice_room`: `voice_room_ended`; the label is the room's name. On a `login_provider`: `login_provider_created`, `login_provider_updated`
 * (its settings), `login_provider_deleted`, `login_provider_enabled`, `login_provider_disabled`,
 * and `login_provider_secret_rotated` (a new client secret); the label is the provider's display
 * name. On a `domain_list_subscription`: `domain_list_subscription_created`,
 * `domain_list_subscription_updated` (its settings), `domain_list_subscription_deleted`,
 * `domain_list_draft_accepted` (which also records the `domain_action_issued` or
 * `domain_action_lifted` on the `domain`), `domain_list_draft_rejected`, and
 * `domain_list_fetch_queued` (a fetch asked for by hand); the label is its title. The actions
 * that a subscription in the automatic mode issues and lifts are recorded on the `domain` with
 * the `system` as the actor. On an `event`: `event_canceled`, a moderator's cancellation of
 * the event; the ID and the label are the event's ID.
 */
@Serializable(with = AdminAuditAction.Serializer::class)
public enum class AdminAuditAction(
  public val `value`: String,
) {
  @SerialName("account_action_issued")
  ACCOUNT_ACTION_ISSUED("account_action_issued"),
  @SerialName("account_action_lifted")
  ACCOUNT_ACTION_LIFTED("account_action_lifted"),
  @SerialName("domain_action_issued")
  DOMAIN_ACTION_ISSUED("domain_action_issued"),
  @SerialName("domain_action_lifted")
  DOMAIN_ACTION_LIFTED("domain_action_lifted"),
  @SerialName("report_assigned")
  REPORT_ASSIGNED("report_assigned"),
  @SerialName("report_unassigned")
  REPORT_UNASSIGNED("report_unassigned"),
  @SerialName("report_resolved")
  REPORT_RESOLVED("report_resolved"),
  @SerialName("report_reopened")
  REPORT_REOPENED("report_reopened"),
  @SerialName("report_note_added")
  REPORT_NOTE_ADDED("report_note_added"),
  @SerialName("report_forwarded")
  REPORT_FORWARDED("report_forwarded"),
  @SerialName("registration_approved")
  REGISTRATION_APPROVED("registration_approved"),
  @SerialName("registration_rejected")
  REGISTRATION_REJECTED("registration_rejected"),
  @SerialName("setting_changed")
  SETTING_CHANGED("setting_changed"),
  @SerialName("text_changed")
  TEXT_CHANGED("text_changed"),
  @SerialName("rule_added")
  RULE_ADDED("rule_added"),
  @SerialName("rule_revised")
  RULE_REVISED("rule_revised"),
  @SerialName("rule_moved")
  RULE_MOVED("rule_moved"),
  @SerialName("rule_retired")
  RULE_RETIRED("rule_retired"),
  @SerialName("role_granted")
  ROLE_GRANTED("role_granted"),
  @SerialName("role_revoked")
  ROLE_REVOKED("role_revoked"),
  @SerialName("role_created")
  ROLE_CREATED("role_created"),
  @SerialName("role_updated")
  ROLE_UPDATED("role_updated"),
  @SerialName("role_deleted")
  ROLE_DELETED("role_deleted"),
  @SerialName("appeal_upheld")
  APPEAL_UPHELD("appeal_upheld"),
  @SerialName("appeal_overturned")
  APPEAL_OVERTURNED("appeal_overturned"),
  @SerialName("account_deletion_canceled")
  ACCOUNT_DELETION_CANCELED("account_deletion_canceled"),
  @SerialName("revealed_private_data")
  REVEALED_PRIVATE_DATA("revealed_private_data"),
  @SerialName("signup_block_added")
  SIGNUP_BLOCK_ADDED("signup_block_added"),
  @SerialName("signup_block_lifted")
  SIGNUP_BLOCK_LIFTED("signup_block_lifted"),
  @SerialName("hook_decision_applied")
  HOOK_DECISION_APPLIED("hook_decision_applied"),
  @SerialName("hook_decision_overridden")
  HOOK_DECISION_OVERRIDDEN("hook_decision_overridden"),
  @SerialName("hook_appeal_upheld")
  HOOK_APPEAL_UPHELD("hook_appeal_upheld"),
  @SerialName("two_factor_disabled")
  TWO_FACTOR_DISABLED("two_factor_disabled"),
  @SerialName("password_reset_link_issued")
  PASSWORD_RESET_LINK_ISSUED("password_reset_link_issued"),
  @SerialName("email_suppression_lifted")
  EMAIL_SUPPRESSION_LIFTED("email_suppression_lifted"),
  @SerialName("account_moved")
  ACCOUNT_MOVED("account_moved"),
  @SerialName("custom_emoji_created")
  CUSTOM_EMOJI_CREATED("custom_emoji_created"),
  @SerialName("custom_emoji_image_replaced")
  CUSTOM_EMOJI_IMAGE_REPLACED("custom_emoji_image_replaced"),
  @SerialName("custom_emoji_copied")
  CUSTOM_EMOJI_COPIED("custom_emoji_copied"),
  @SerialName("custom_emoji_updated")
  CUSTOM_EMOJI_UPDATED("custom_emoji_updated"),
  @SerialName("custom_emoji_enabled")
  CUSTOM_EMOJI_ENABLED("custom_emoji_enabled"),
  @SerialName("custom_emoji_disabled")
  CUSTOM_EMOJI_DISABLED("custom_emoji_disabled"),
  @SerialName("custom_emoji_deleted")
  CUSTOM_EMOJI_DELETED("custom_emoji_deleted"),
  @SerialName("webhook_created")
  WEBHOOK_CREATED("webhook_created"),
  @SerialName("webhook_updated")
  WEBHOOK_UPDATED("webhook_updated"),
  @SerialName("webhook_deleted")
  WEBHOOK_DELETED("webhook_deleted"),
  @SerialName("webhook_enabled")
  WEBHOOK_ENABLED("webhook_enabled"),
  @SerialName("webhook_disabled")
  WEBHOOK_DISABLED("webhook_disabled"),
  @SerialName("webhook_secret_rotated")
  WEBHOOK_SECRET_ROTATED("webhook_secret_rotated"),
  @SerialName("announcement_created")
  ANNOUNCEMENT_CREATED("announcement_created"),
  @SerialName("announcement_updated")
  ANNOUNCEMENT_UPDATED("announcement_updated"),
  @SerialName("announcement_published")
  ANNOUNCEMENT_PUBLISHED("announcement_published"),
  @SerialName("announcement_withdrawn")
  ANNOUNCEMENT_WITHDRAWN("announcement_withdrawn"),
  @SerialName("announcement_deleted")
  ANNOUNCEMENT_DELETED("announcement_deleted"),
  @SerialName("invite_created")
  INVITE_CREATED("invite_created"),
  @SerialName("invite_revoked")
  INVITE_REVOKED("invite_revoked"),
  @SerialName("relay_added")
  RELAY_ADDED("relay_added"),
  @SerialName("relay_followed")
  RELAY_FOLLOWED("relay_followed"),
  @SerialName("relay_unfollowed")
  RELAY_UNFOLLOWED("relay_unfollowed"),
  @SerialName("relay_removed")
  RELAY_REMOVED("relay_removed"),
  @SerialName("relay_accepted")
  RELAY_ACCEPTED("relay_accepted"),
  @SerialName("relay_rejected")
  RELAY_REJECTED("relay_rejected"),
  @SerialName("status_refetch_queued")
  STATUS_REFETCH_QUEUED("status_refetch_queued"),
  @SerialName("account_statuses_refetch_queued")
  ACCOUNT_STATUSES_REFETCH_QUEUED("account_statuses_refetch_queued"),
  @SerialName("domain_statuses_refetch_queued")
  DOMAIN_STATUSES_REFETCH_QUEUED("domain_statuses_refetch_queued"),
  @SerialName("billing_import_queued")
  BILLING_IMPORT_QUEUED("billing_import_queued"),
  @SerialName("cost_item_created")
  COST_ITEM_CREATED("cost_item_created"),
  @SerialName("cost_item_relabeled")
  COST_ITEM_RELABELED("cost_item_relabeled"),
  @SerialName("cost_item_price_revised")
  COST_ITEM_PRICE_REVISED("cost_item_price_revised"),
  @SerialName("cost_item_end_changed")
  COST_ITEM_END_CHANGED("cost_item_end_changed"),
  @SerialName("cost_item_deleted")
  COST_ITEM_DELETED("cost_item_deleted"),
  @SerialName("supporter_program_enabled")
  SUPPORTER_PROGRAM_ENABLED("supporter_program_enabled"),
  @SerialName("supporter_program_disabled")
  SUPPORTER_PROGRAM_DISABLED("supporter_program_disabled"),
  @SerialName("supporter_tips_enabled")
  SUPPORTER_TIPS_ENABLED("supporter_tips_enabled"),
  @SerialName("supporter_tips_revised")
  SUPPORTER_TIPS_REVISED("supporter_tips_revised"),
  @SerialName("supporter_tips_disabled")
  SUPPORTER_TIPS_DISABLED("supporter_tips_disabled"),
  @SerialName("supporter_plan_created")
  SUPPORTER_PLAN_CREATED("supporter_plan_created"),
  @SerialName("supporter_plan_revised")
  SUPPORTER_PLAN_REVISED("supporter_plan_revised"),
  @SerialName("supporter_plan_retired")
  SUPPORTER_PLAN_RETIRED("supporter_plan_retired"),
  @SerialName("supporter_subscription_ended")
  SUPPORTER_SUBSCRIPTION_ENDED("supporter_subscription_ended"),
  @SerialName("custom_emoji_request_submitted")
  CUSTOM_EMOJI_REQUEST_SUBMITTED("custom_emoji_request_submitted"),
  @SerialName("custom_emoji_request_withdrawn")
  CUSTOM_EMOJI_REQUEST_WITHDRAWN("custom_emoji_request_withdrawn"),
  @SerialName("custom_emoji_request_approved")
  CUSTOM_EMOJI_REQUEST_APPROVED("custom_emoji_request_approved"),
  @SerialName("custom_emoji_request_rejected")
  CUSTOM_EMOJI_REQUEST_REJECTED("custom_emoji_request_rejected"),
  @SerialName("payment_reconcile_queued")
  PAYMENT_RECONCILE_QUEUED("payment_reconcile_queued"),
  @SerialName("oauth_client_disabled")
  OAUTH_CLIENT_DISABLED("oauth_client_disabled"),
  @SerialName("oauth_client_enabled")
  OAUTH_CLIENT_ENABLED("oauth_client_enabled"),
  @SerialName("oauth_client_host_denied")
  OAUTH_CLIENT_HOST_DENIED("oauth_client_host_denied"),
  @SerialName("oauth_client_host_allowed")
  OAUTH_CLIENT_HOST_ALLOWED("oauth_client_host_allowed"),
  @SerialName("service_closure_announced")
  SERVICE_CLOSURE_ANNOUNCED("service_closure_announced"),
  @SerialName("service_closure_withdrawn")
  SERVICE_CLOSURE_WITHDRAWN("service_closure_withdrawn"),
  @SerialName("service_closure_closed")
  SERVICE_CLOSURE_CLOSED("service_closure_closed"),
  @SerialName("service_closure_delivery_completed")
  SERVICE_CLOSURE_DELIVERY_COMPLETED("service_closure_delivery_completed"),
  @SerialName("voice_room_ended")
  VOICE_ROOM_ENDED("voice_room_ended"),
  @SerialName("login_provider_created")
  LOGIN_PROVIDER_CREATED("login_provider_created"),
  @SerialName("login_provider_updated")
  LOGIN_PROVIDER_UPDATED("login_provider_updated"),
  @SerialName("login_provider_deleted")
  LOGIN_PROVIDER_DELETED("login_provider_deleted"),
  @SerialName("login_provider_enabled")
  LOGIN_PROVIDER_ENABLED("login_provider_enabled"),
  @SerialName("login_provider_disabled")
  LOGIN_PROVIDER_DISABLED("login_provider_disabled"),
  @SerialName("login_provider_secret_rotated")
  LOGIN_PROVIDER_SECRET_ROTATED("login_provider_secret_rotated"),
  @SerialName("link_card_domain_fetch_stopped")
  LINK_CARD_DOMAIN_FETCH_STOPPED("link_card_domain_fetch_stopped"),
  @SerialName("link_card_domain_images_stopped")
  LINK_CARD_DOMAIN_IMAGES_STOPPED("link_card_domain_images_stopped"),
  @SerialName("link_card_domain_rule_removed")
  LINK_CARD_DOMAIN_RULE_REMOVED("link_card_domain_rule_removed"),
  @SerialName("instance_marked_available")
  INSTANCE_MARKED_AVAILABLE("instance_marked_available"),
  @SerialName("delivery_revealed")
  DELIVERY_REVEALED("delivery_revealed"),
  @SerialName("account_deliveries_viewed")
  ACCOUNT_DELIVERIES_VIEWED("account_deliveries_viewed"),
  @SerialName("domain_allow_added")
  DOMAIN_ALLOW_ADDED("domain_allow_added"),
  @SerialName("domain_allow_removed")
  DOMAIN_ALLOW_REMOVED("domain_allow_removed"),
  @SerialName("domain_list_subscription_created")
  DOMAIN_LIST_SUBSCRIPTION_CREATED("domain_list_subscription_created"),
  @SerialName("domain_list_subscription_updated")
  DOMAIN_LIST_SUBSCRIPTION_UPDATED("domain_list_subscription_updated"),
  @SerialName("domain_list_subscription_deleted")
  DOMAIN_LIST_SUBSCRIPTION_DELETED("domain_list_subscription_deleted"),
  @SerialName("domain_list_draft_accepted")
  DOMAIN_LIST_DRAFT_ACCEPTED("domain_list_draft_accepted"),
  @SerialName("domain_list_draft_rejected")
  DOMAIN_LIST_DRAFT_REJECTED("domain_list_draft_rejected"),
  @SerialName("domain_list_fetch_queued")
  DOMAIN_LIST_FETCH_QUEUED("domain_list_fetch_queued"),
  @SerialName("event_canceled")
  EVENT_CANCELED("event_canceled"),
  @SerialName("UNRECOGNIZED")
  UNRECOGNIZED("UNRECOGNIZED"),
  ;

  override fun toString(): String = value

  public object Serializer : KSerializer<AdminAuditAction> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("social.mycroblog.api.models.AdminAuditAction", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, `value`: AdminAuditAction) {
      encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): AdminAuditAction {
      val value = decoder.decodeString()
      val ignoreCase = (decoder as? JsonDecoder)?.json?.configuration?.decodeEnumsCaseInsensitive == true
      return entries.firstOrNull { it.value.equals(value, ignoreCase) } ?: UNRECOGNIZED
    }
  }

  public companion object {
    private val mapping: Map<String, AdminAuditAction> =
        entries.associateBy(AdminAuditAction::value)

    public fun fromValue(`value`: String): AdminAuditAction = mapping[value] ?: UNRECOGNIZED
  }
}
