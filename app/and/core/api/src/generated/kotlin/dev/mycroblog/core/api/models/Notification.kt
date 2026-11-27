package dev.mycroblog.core.api.models

import java.util.UUID
import kotlin.Boolean
import kotlin.String
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A notification of something that happened to the authenticated user.
 */
@Serializable
public data class Notification(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  /**
   * The kind of a notification. `account` is the account that acted: the follower
   * (`follow`, `follow_request`), the followed account (`follow_accepted`), the author
   * (`mention`), the booster (`boost`), the account that favourited (`favourite`), or the
   * account that reacted with an emoji (`reaction`, whose `reaction` is the emoji; a reaction
   * with another emoji is another notification), or the account that quoted a post of the
   * user (`quote`, once the quote is approved or, from software without approvals, received).
   * `status` is the post that `mention`, `boost`, `favourite`, `reaction`, and `poll_closed`
   * are about, and for `quote` the post that quotes. `reply_request`, `boost_request`, and
   * `quote_request` tell that `account` replied to, boosted, or quoted the user's `status` and
   * that it waits for the user's approval (`listInteractionRequests`); `interaction_accepted`
   * and `interaction_rejected` tell that `account`, the author of `status`, approved or refused
   * the user's reply to it, boost of it, or quote of it. `moderation_notice` comes from the instance and has no `account`; its
   * `moderation_action` is the action on the user's account, with its kind, reason, and end.
   * `report_resolved` also comes from the instance: its `report` is the user's report that
   * the moderators resolved, without how they resolved it. It is sent once per report, even
   * when the report is reopened and resolved again. `appeal_decided` also comes from the
   * instance: the moderators decided the user's appeal, and its `moderation_action` is the
   * action with the appeal and the decision. `hook_appeal_decided` also comes from the
   * instance: a moderator upheld or overturned the user's appeal of a post that the automated
   * review refused, and its `hook_appeal` is the appeal with its state; first-party clients only,
   * as the appeal is. It is sent once per appeal.
   * `relationships_severed` also comes from the instance: its `relationship_severance` tells
   * how many follows the user lost when the moderators suspended a server or a remote
   * account, whose `account` it carries for an account. It is sent once per suspension.
   * The moderator notifications come from the instance too: `admin_report` (an account
   * that had no open report was reported; `report_id` is the report) goes to the users
   * with the `manage_reports` permission, `admin_sign_up` (a registration awaits
   * approval; `account` is the new user's account) to those with `manage_accounts`, and
   * `admin_appeal` (a user appealed an action; `appeal_id` is the appeal) to those with
   * `manage_accounts`, and `admin_webhook_disabled` (the server disabled a webhook that
   * answered 410 Gone or kept failing; it names none) to those with `manage_webhooks`, and
   * `admin_hook_failing` (calls of an external review keep failing; it names none) and
   * `admin_hook_appeal` (a user appealed a post that the automated review refused;
   * `hook_appeal_id` is the appeal) to those with `manage_reports`, and
   * `admin_custom_emoji_request` (a user requested a custom emoji or a new image of one;
   * `custom_emoji_request` is the request) to those with `manage_custom_emojis`, and
   * `admin_domain_list_failing` (three fetches in a row of a subscribed domain block list
   * failed; it names none) to those with `manage_domains`.
   * `custom_emoji_request_decided` also comes from the instance: an administrator approved
   * or rejected the user's custom emoji request, which `custom_emoji_request` carries with
   * its state and the reason of a rejection.
   * The supporter notifications also come from the instance and tell of the user's own
   * supporter subscription, which `supporter_subscription` carries as it is now:
   * `supporter_payment_failed` (a payment failed; the plan's role stays until
   * `grace_ends_at` unless a payment succeeds), `supporter_subscription_ended` (the
   * subscription lost the plan's role; `end_reason` says why), and `supporter_plan_retired`
   * (the administrators retired the plan; the subscription ends with its period). Each is
   * sent once per change of the subscription. A client must not lead from them to
   * subscribing or paying where it offers no way to subscribe.
   * `account_moved` tells that an account that the user followed (`account`) moved to another
   * one; its `account_move` carries the target and what became of the user's follow. It is
   * sent once per move. `admin_account_moved` goes to the users with `manage_accounts`: an
   * account that the moderators limit moved, and the target does not get the limit; its
   * `account_move` carries the target, and it has no `account`. `service_closure` goes to every
   * local user when the closure of the service is announced, when its dates change, and when the
   * announcement is withdrawn; `admin_service_closure_due` goes to the users with
   * `manage_service_closure` once the closing date of the announcement in effect has passed, to
   * close the server; `supporter_service_closure` tells a subscriber that the announcement cancels
   * their supporter subscription at the end of its period, with its `supporter_subscription`.
   * Their `service_closure` says what they tell of, and they have no `account`.
   * `voice_room_started` tells that an account that the user follows (`account`, the host)
   * opened a voice chat room that the user sees, once per room; `voice_room_invited` that the
   * host or a co-host of a room (`account`) invited the user to it, once per invitation. Both
   * carry `voice_room_id`, and `voice_room` while the room is open and the user sees it.
   * `admin_error_group` tells the holders of `manage_errors` of a new error group or of a
   * resolved one that happened again, at most 10 times an hour for the whole server, with the
   * rest of the hour folded into one; its `error_group` says which, and it has no `account`.
   * The type `status` tells of a new post (`status`) of an account that the user subscribes to
   * (`account`, the author; `subscribeAccount`), once per post. It is not sent for a reply to
   * someone else's post, a boost, an edit, or a post that the server fetched rather than received,
   * nor when the user gets a `mention` of the same post.
   * `announcement` goes to every local user once a server-wide announcement starts to show (its
   * publication, or the start of its window when that comes later), once per announcement; its
   * `announcement` carries it, and it has no `account`. Editing the announcement afterwards sends
   * nothing again, and the notification leaves the list and the unread count once the
   * announcement is withdrawn or its window ends.
   * `auto_delete_limit` comes from the instance: the server maximum of the automatic deletion of
   * old posts now binds the user (it was turned on or lowered, or the user lost the permission
   * `keep_posts_longer`), and after a grace period it deletes the user's posts that their own
   * policy keeps. Its `auto_delete_limit` carries the maximum and when it starts to apply, and it
   * has no `account`. It is sent once per notice.
   * `scheduled_status_failed` comes from the instance: a post that the user scheduled could not be
   * published at its time, and its draft stays to fix and schedule again. Its
   * `scheduled_status_failure` carries the draft, why, and when it was to be published, while the
   * draft exists, and it has no `account`. It is sent once per schedule; a published scheduled post
   * sends nothing.
   * The `event_*` types tell of an event of the server (`event_id`, and `event` while the user
   * sees it). `event_invited` tells that `account` invited the user, once per invitation;
   * `event_schedule_confirmed` that the organizers fixed the date of an event whose date poll the
   * user answered or to which the user was invited, once per date; `event_changed` that the date,
   * the places, or the voice chat room of an event that the user answered going or maybe to, or was
   * invited to, changed, once per change (a change of the title or the description sends
   * nothing); `event_canceled` that such an event was canceled; `event_reminder` that an event
   * that the user answered going or maybe to starts soon, at each time before the start that the
   * user chose (`getEventReminders`), once per date and time, and never for a date that changed
   * since; `event_started` that it starts now, once per date; `event_room_unavailable` tells
   * the organizer that the voice chat room of the event cannot open; and `event_board_posted`
   * tells a user who answered going or maybe that `account` wrote a new post (`status`) on the
   * board of the event, once per post, but not when the post mentions the user, who gets the
   * `mention` instead. Their `account` is the organizer, except for `event_invited` and
   * `event_board_posted`. Each is sent only while the user sees the event, and an
   * `event_board_posted` only while the user reads its board.
   */
  @SerialName("type")
  public val type: NotificationType,
  /**
   * When it happened.
   */
  @SerialName("created_at")
  public val createdAt: Instant,
  /**
   * Whether the user has read it: it is older than or at the marker's `last_read_id`, or
   * it was marked read on its own (`POST /notifications/{id}/read`).
   */
  @SerialName("read")
  public val read: Boolean,
  /**
   * An account, as clients show it.
   */
  @SerialName("account")
  public val account: Account? = null,
  /**
   * A status (post), as the viewer sees it. `favourited`, `boosted`, `bookmarked`, and
   * `muted` describe the authenticated user's relation to it and are omitted for anonymous
   * requests; `pinned` is only given to the author.
   *
   * In a timeline, a boost is a status of the booster with `boost` set to the boosted
   * status: its `id` and `uri` are those of the boost, `created_at` is when it was boosted,
   * `visibility` and `language` are those of the boosted status, its `content`,
   * `spoiler_text`, `media_attachments`, `mentions`, `tags`, `emojis`, and `reactions` are
   * empty, its `card` and `quote` are omitted, and its counts are zero.
   *
   * An article (`article` is given) is read as its title, summary, and cover everywhere, and
   * its `content` is empty except where one article is asked for: `getStatus`, `createArticle`,
   * and `editArticle`. Timelines, notifications, streaming, search, threads, and every other list
   * carry no body. An article has no content warning, media attachments, or poll.
   */
  @SerialName("status")
  public val status: Status? = null,
  /**
   * A report that the user filed. It never names the reporter, and tells only whether the
   * moderators resolved it, never how.
   */
  @SerialName("report")
  public val report: Report? = null,
  /**
   * A moderation action on the authenticated user's own account, as the
   * user sees it. It never carries the moderators' private note, the report the action
   * follows from, or who filed the report or issued the action.
   */
  @SerialName("moderation_action")
  public val moderationAction: ModerationAction? = null,
  /**
   * The report that an `admin_report` is about.
   */
  @SerialName("report_id")
  public val reportId: String? = null,
  /**
   * The appeal that an `admin_appeal` is about.
   */
  @SerialName("appeal_id")
  public val appealId: String? = null,
  /**
   * The appeal of a refused post that an `admin_hook_appeal` is about.
   */
  @SerialName("hook_appeal_id")
  public val hookAppealId: String? = null,
  /**
   * The user's appeal of a post that the automated review refused.
   */
  @SerialName("hook_appeal")
  public val hookAppeal: OwnHookAppeal? = null,
  /**
   * A request of a holder of `create_custom_emojis` for a new local custom emoji, or for a new
   * image of one they made, which a holder of `manage_custom_emojis` approves or rejects. A request is not an emoji: no picker, post, reaction, or other server shows
   * its image until it is approved.
   */
  @SerialName("custom_emoji_request")
  public val customEmojiRequest: CustomEmojiRequest? = null,
  /**
   * The authenticated user's subscription to a supporter plan. `price` is what it renews at,
   * which may be lower than the plan's current price (a price change does not apply to the
   * subscriptions that go on). The plan's name is written by the administrators in any
   * language; render it with `dir="auto"`.
   */
  @SerialName("supporter_subscription")
  public val supporterSubscription: SupporterSubscription? = null,
  /**
   * The follows that a `relationships_severed` tells of: the user lost them when the
   * moderators suspended a server (`domain_suspension`, `domain` is the server) or a remote
   * account (`account_suspension`, the notification's `account` is the account), or when the
   * server began to federate only with the servers it allows (`allowlist_cutoff`: `domain` is the
   * server whose allow was removed, and is omitted when the server switched to its allowlist).
   * Both directions end, follow requests too, and lifting the suspension or allowing the server
   * again does not restore them; the counts are of the accepted follows.
   */
  @SerialName("relationship_severance")
  public val relationshipSeverance: RelationshipSeverance? = null,
  /**
   * The move that an `account_moved` or an `admin_account_moved` tells of: the notification's
   * `account` moved to `target`. Only the user's follows of the moved account move;
   * its posts stay where they are.
   */
  @SerialName("account_move")
  public val accountMove: AccountMove? = null,
  /**
   * What a `service_closure`, an `admin_service_closure_due`, or a `supporter_service_closure`
   * tells of the closure of the service. `change` is what the event of a
   * `service_closure` did: `announced` (the closure was announced), `rescheduled` (its dates
   * changed), or `withdrawn` (the announcement was withdrawn; the server goes on); it is omitted
   * for the other types. `closes_at` and `export_until` are the dates of the announcement that the
   * notification tells of, omitted for a withdrawal. The current state is in `GET /instance`.
   */
  @SerialName("service_closure")
  public val serviceClosure: ServiceClosureNotice? = null,
  /**
   * The emoji of a reaction: a Unicode emoji, or a custom emoji with its image. A custom
   * emoji of another server is shown as it is and cannot be used to react from here.
   */
  @SerialName("reaction")
  public val reaction: ReactionEmoji? = null,
  /**
   * The voice chat room that a `voice_room_started` or a `voice_room_invited` is about.
   */
  @SerialName("voice_room_id")
  public val voiceRoomId: String? = null,
  /**
   * An open voice chat room: its settings, its host, and its participants in the order they
   * joined. Everyone who sees the room sees its participants.
   */
  @SerialName("voice_room")
  public val voiceRoom: VoiceRoom? = null,
  /**
   * The interaction request that a `reply_request`, a `boost_request`, a `quote_request`, an
   * `interaction_accepted`, or an `interaction_rejected` is about, which the interaction
   * requests answer by.
   */
  @SerialName("interaction_request_id")
  public val interactionRequestId: String? = null,
  /**
   * The event that an `event_*` notification is about.
   */
  @SerialName("event_id")
  public val eventId: String? = null,
  /**
   * An event of the local users of this server, as the user sees it. Times are in UTC;
   * `time_zone` is the event's own time zone, to show next to the user's. `places` holds only the
   * places that the user sees: `hidden_places` lists the kinds of the others, whose details never
   * reach the client.
   */
  @SerialName("event")
  public val event: Event? = null,
  /**
   * What an `admin_error_group` tells of: a new error group (`created`, with `group_id`), a
   * resolved group that happened again on another version (`regressed`, with `group_id`), or the
   * new and regressed groups of an hour beyond the 10 that the server notifies of one by one
   * (`others`, with how many so far in `count`).
   */
  @SerialName("error_group")
  public val errorGroup: NotificationErrorGroup? = null,
  /**
   * An announcement of the server to every local user, as the user sees it.
   */
  @SerialName("announcement")
  public val announcement: Announcement? = null,
  /**
   * The notice that the server maximum of the automatic deletion of old posts binds the user: the
   * maximum it told of and when the user's posts start to go by it. The settings of the automatic
   * deletion (`getAutoDeletePolicy`) show the maximum in effect now, and their preview what goes.
   */
  @SerialName("auto_delete_limit")
  public val autoDeleteLimit: NotificationAutoDeleteLimit? = null,
  /**
   * The scheduled post that a `scheduled_status_failed` tells of: its draft, why it was not
   * published, and when it was to be published.
   */
  @SerialName("scheduled_status_failure")
  public val scheduledStatusFailure: NotificationScheduledStatusFailure? = null,
)
