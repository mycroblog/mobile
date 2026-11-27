package social.mycroblog.api.models

import java.net.URI
import kotlin.Boolean
import kotlin.Int
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Public information about the instance.
 */
@Serializable
public data class Instance(
  /**
   * The domain that serves the instance.
   */
  @SerialName("domain")
  public val domain: String,
  /**
   * The display name of the instance; the domain when the instance has none.
   */
  @SerialName("name")
  public val name: String,
  /**
   * A one-paragraph summary of the instance, as plain text; omitted when there is none.
   */
  @SerialName("short_description")
  public val shortDescription: String? = null,
  /**
   * The full description of the instance, as plain text; omitted when there is none.
   */
  @SerialName("description")
  public val description: String? = null,
  /**
   * How people sign up on the instance.
   */
  @SerialName("registrations")
  public val registrations: InstanceRegistrations,
  /**
   * Whether registration and sign-in are limited to the login providers now: the instance setting
   * is on and at least one provider is enabled. While it is true, a password or a passkey signs
   * nobody in, and neither can be set or added; both still confirm operations of a signed-in user.
   */
  @SerialName("login_providers_only")
  public val loginProvidersOnly: Boolean,
  /**
   * The server version.
   */
  @SerialName("version")
  public val version: String,
  /**
   * The identifiers of the features that this server offers now, in no particular order. Detect a
   * feature by its identifier rather than by `version`. An identifier is listed only while its
   * feature is on, so the list can change with the instance's settings; it is never removed from
   * the API or reused for something else. Ignore the identifiers you do not know. The identifiers:
   *
   * - `voice_rooms`: the voice chat rooms (`/voice/...`).
   * - `full_text_search`: the full-text search of statuses (`searchStatuses`).
   * - `supporters`: the supporter program and its plans (`GET /instance/supporter_plans`).
   * - `tips`: one-time tips to the server's operation (`GET /instance/tip_amounts`).
   * - `web_push`: Web Push subscriptions (`push_server_key`).
   * - `translation`: the translation of statuses through this server (`translateStatus`).
   */
  @SerialName("features")
  public val features: List<String>,
  /**
   * Where users get the source code of the running server (AGPL-3.0 section 13), as an https URL to read it at or a mailto URI to request it by email.
   */
  @Contextual
  @SerialName("source_url")
  public val sourceUrl: URI,
  /**
   * The https address of the instance's status page, on a service apart from the server. Absent when the operator has not set one.
   */
  @Contextual
  @SerialName("status_page_url")
  public val statusPageUrl: URI? = null,
  /**
   * Limits that clients check before sending a request.
   */
  @SerialName("limits")
  public val limits: InstanceLimits,
  /**
   * Who may read the public timelines the instance serves: `local` is the instance
   * setting `local_timeline_visibility` of `GET /timelines/local`, and `federated` the instance
   * setting `federated_timeline_visibility` of `GET /timelines/federated`. Clients do not offer a
   * timeline that is `off`, nor one that is `signed_in` to a visitor who is not logged in. On a
   * one-person server (`single_user`) both are `off`, whatever the settings store.
   */
  @SerialName("timelines")
  public val timelines: InstanceTimelines,
  /**
   * How the instance federates with other servers, so that clients offer only the
   * operations on remote accounts and posts that can work.
   */
  @SerialName("federation")
  public val federation: InstanceFederation,
  /**
   * The closure of the service, present while a closure is announced and after
   * the server closes; absent when none is announced. Clients show it on every screen with the
   * way to export the data and to move the account.
   */
  @SerialName("closure")
  public val closure: InstanceClosure? = null,
  /**
   * Whether the instance sends email (the server setting `mail` is not `none`). Without
   * email, notifications come only in the app, so clients do not offer the email column of
   * the notification settings (`GET /preferences/notifications`).
   */
  @SerialName("sends_email")
  public val sendsEmail: Boolean,
  /**
   * The instance's VAPID public key (RFC 8292), an uncompressed P-256 point in unpadded
   * base64url: the `applicationServerKey` that browsers subscribe with before
   * `putPushSubscription`. Omitted when the instance sends no Web Push.
   */
  @SerialName("push_server_key")
  public val pushServerKey: String? = null,
  /**
   * The rules in effect, in order.
   */
  @SerialName("rules")
  public val rules: List<InstanceRule>,
  /**
   * The web client's page of the instance's terms of service (`/about/terms`), which needs no
   * sign-in and shows `GET /instance/documents/terms`; omitted when the instance has none,
   * so that clients do not link to it.
   */
  @Contextual
  @SerialName("terms_url")
  public val termsUrl: URI? = null,
  /**
   * The web client's page of the instance's privacy policy (`/about/privacy`), which needs no
   * sign-in and shows `GET /instance/documents/privacy_policy`; omitted when the instance has
   * none.
   */
  @Contextual
  @SerialName("privacy_policy_url")
  public val privacyPolicyUrl: URI? = null,
  /**
   * The web client's page of the commerce disclosures of the supporter plans (`/about/commerce`,
   * the URL that the operators give the payment provider), which needs no sign-in and shows
   * `GET /instance/documents/commerce_disclosure`; omitted when the instance has none.
   */
  @Contextual
  @SerialName("commerce_disclosure_url")
  public val commerceDisclosureUrl: URI? = null,
  /**
   * Whether the supporter program is enabled: its plans (`GET /instance/supporter_plans`)
   * and the subscriptions show only then.
   */
  @SerialName("supporters_enabled")
  public val supportersEnabled: Boolean,
  /**
   * Whether the server takes tips, one-time payments to its operation with nothing in return:
   * their amounts (`GET /instance/tip_amounts`) and the tips show only then.
   */
  @SerialName("tips_enabled")
  public val tipsEnabled: Boolean,
  /**
   * Who may read something public about the instance, the value of the instance settings
   * `local_timeline_visibility`, `federated_timeline_visibility`, `domain_actions_visibility`,
   * `domain_actions_rationale_visibility`, and `supporter_list_visibility`: `off`, nobody, and the
   * resource is a 404; `signed_in`, the users signed in to this server, and an anonymous request is
   * a 401; `everyone`, everyone, anonymous visitors included.
   */
  @SerialName("supporter_list_visibility")
  public val supporterListVisibility: InstanceAudience,
  /**
   * Who may read something public about the instance, the value of the instance settings
   * `local_timeline_visibility`, `federated_timeline_visibility`, `domain_actions_visibility`,
   * `domain_actions_rationale_visibility`, and `supporter_list_visibility`: `off`, nobody, and the
   * resource is a 404; `signed_in`, the users signed in to this server, and an anonymous request is
   * a 401; `everyone`, everyone, anonymous visitors included.
   */
  @SerialName("domain_actions_visibility")
  public val domainActionsVisibility: InstanceAudience,
  /**
   * The decision hooks in use: what the server sends to an external review before it accepts it.
   * Absent when there is none. The information page states this.
   */
  @SerialName("external_reviews")
  public val externalReviews: List<HookName>? = null,
  /**
   * The settings that narrow what the decision hooks in use send to the external review, which the
   * information page states. Each field is present only while its hook is in use.
   */
  @SerialName("external_review_scope")
  public val externalReviewScope: InstanceExternalReviewScope? = null,
  /**
   * Whether the voice chat rooms are on: the voice operations (`/voice/...`) answer 404 while
   * they are off, and clients then show no entry to them.
   */
  @SerialName("voice_enabled")
  public val voiceEnabled: Boolean,
  /**
   * The most people a voice chat room may hold, the bound of a room's `max_participants`.
   * Given while the voice chat rooms are on.
   */
  @SerialName("voice_max_participants")
  public val voiceMaxParticipants: Int? = null,
  /**
   * Who may write new articles (`createArticle`): `everyone` (every local user who is not
   * limited) or `permission` (only the users whose roles have `create_articles`). Always given.
   */
  @SerialName("article_authoring")
  public val articleAuthoring: InstanceArticleAuthoring? = null,
  /**
   * Who may open voice chat rooms: `everyone` (every local user who is not limited) or
   * `permitted` (only the users whose roles have `start_voice_rooms`). `permitted` while the
   * voice chat is off.
   */
  @SerialName("voice_room_starters")
  public val voiceRoomStarters: InstanceVoiceRoomStarters,
  /**
   * Who may create events: `everyone` (every local user who is not limited) or `permitted`
   * (only the users whose roles have `create_events`).
   */
  @SerialName("event_creators")
  public val eventCreators: InstanceEventCreators,
  /**
   * The full-text search of statuses on this server. It is separate from the search of accounts and
   * hashtags, which every server has. Clients read this to know whether to show an entry to it,
   * without a search that fails.
   */
  @SerialName("full_text_search")
  public val fullTextSearch: InstanceFullTextSearch,
  /**
   * The instance's theme, for clients that do not load the server's HTML shell: the
   * stylesheet to apply and the overridden tokens. `Instance.theme` omits it when the
   * instance has no theme.
   */
  @SerialName("theme")
  public val theme: InstanceTheme? = null,
  /**
   * A brand image that the administrators set: the sanitized SVG and its PNG renditions, at URLs
   * under `/brand/` on the server that name the revision, so their content never changes and
   * they may be cached for good. Show the SVG only as an image (`<img>` or a CSS background),
   * never inline. `Instance` has the `icon`, the `logo` (a wordmark that includes the instance's
   * name, which clients show instead of the name), and the `logo_dark` for dark backgrounds
   * (`logo` is used there without it); each is omitted while the instance uses the built-in
   * image.
   */
  @SerialName("icon")
  public val icon: InstanceBrandImage? = null,
  /**
   * A brand image that the administrators set: the sanitized SVG and its PNG renditions, at URLs
   * under `/brand/` on the server that name the revision, so their content never changes and
   * they may be cached for good. Show the SVG only as an image (`<img>` or a CSS background),
   * never inline. `Instance` has the `icon`, the `logo` (a wordmark that includes the instance's
   * name, which clients show instead of the name), and the `logo_dark` for dark backgrounds
   * (`logo` is used there without it); each is omitted while the instance uses the built-in
   * image.
   */
  @SerialName("logo")
  public val logo: InstanceBrandImage? = null,
  /**
   * A brand image that the administrators set: the sanitized SVG and its PNG renditions, at URLs
   * under `/brand/` on the server that name the revision, so their content never changes and
   * they may be cached for good. Show the SVG only as an image (`<img>` or a CSS background),
   * never inline. `Instance` has the `icon`, the `logo` (a wordmark that includes the instance's
   * name, which clients show instead of the name), and the `logo_dark` for dark backgrounds
   * (`logo` is used there without it); each is omitted while the instance uses the built-in
   * image.
   */
  @SerialName("logo_dark")
  public val logoDark: InstanceBrandImage? = null,
  /**
   * How people reach the instance's operators (the instance settings `contact_account` and
   * `contact_email`). Omitted when the instance has neither.
   */
  @SerialName("contact")
  public val contact: InstanceContact? = null,
  /**
   * The image that represents the instance (the instance setting `thumbnail`): the large still
   * variant of the media file. Omitted when there is none, or while the file is processing.
   */
  @SerialName("thumbnail")
  public val thumbnail: InstanceThumbnail? = null,
  /**
   * Whether the instance lets search engines index its pages (the instance setting
   * `search_indexing`). `allow`: the profiles and public posts of the accounts
   * that are `indexable`; `deny`: no page. An account can deny what the instance allows, never
   * the reverse.
   */
  @SerialName("search_indexing")
  public val searchIndexing: SearchIndexing,
  /**
   * What the instance states to AI crawlers (the instance setting `ai_usage`).
   * `allow`: no reservation; `deny_training`: no training of AI models; `deny`: no training
   * and no use by AI search, assistants, and agents. An account can opt out of what the
   * instance allows (`ai_opt_out`), never the reverse.
   */
  @SerialName("ai_usage")
  public val aiUsage: AIUsage,
  /**
   * Whether the server fetches the pages that posts link to, for their link cards. While it is
   * false, the server fetches nothing for link cards.
   */
  @SerialName("link_cards")
  public val linkCards: Boolean,
  /**
   * Whether the fetches of link cards skip `robots.txt`. While it is true, the sites that posts
   * link to cannot refuse those fetches in `robots.txt`; the directives of their pages still apply.
   */
  @SerialName("link_cards_ignore_robots_txt")
  public val linkCardsIgnoreRobotsTxt: Boolean,
  /**
   * Whether the landing page (`/`, signed out) explains how the fediverse works: that an account
   * here follows and is followed by people on other servers (the instance setting
   * `landing_fediverse_explanation`).
   */
  @SerialName("landing_fediverse_explanation")
  public val landingFediverseExplanation: Boolean,
  /**
   * Whether the landing page shows what the software does on this instance, a sample of each
   * feature that is on (the instance setting `landing_features`).
   */
  @SerialName("landing_features")
  public val landingFeatures: Boolean,
  /**
   * Whether the server takes samples of how fast the web client responds
   * (`POST /client_measurements`). While it is false, the server takes none and the client sends
   * none.
   */
  @SerialName("ux_measurement")
  public val uxMeasurement: Boolean? = null,
  /**
   * The percent of browser sessions whose web client sends its samples while `ux_measurement`
   * is true: each session is drawn once, at this share.
   */
  @SerialName("ux_measurement_share")
  public val uxMeasurementShare: Int? = null,
  /**
   * What the instance offers for translating posts, and what it sends where. With `browser` false and
   * no `providers`, nothing can be translated and clients show no action for it.
   */
  @SerialName("translation")
  public val translation: InstanceTranslation,
  /**
   * The server maximum of the automatic deletion of old posts: while it is on, the automatic
   * deletion of every local user without the permission `keep_posts_longer` is on, deletes a post
   * at most `max_age_days` days after its publication, and keeps posts only by the exceptions that
   * the operator allows.
   */
  @SerialName("auto_delete_limit")
  public val autoDeleteLimit: AutoDeleteLimit? = null,
  /**
   * How much of the instance's costs everyone may read (the instance setting
   * `finance_visibility`). `private`: nothing, and
   * `GET /instance/cost_totals` is a 404; `totals`: the total of each currency by period;
   * `breakdown`: the totals and their breakdown by provider and category. No level publishes
   * the names, memos, or individual payments of the cost items.
   */
  @SerialName("finance_visibility")
  public val financeVisibility: FinanceVisibility,
  /**
   * How much of the instance's income everyone may read (the instance setting
   * `income_visibility`), apart from `finance_visibility`. `private`:
   * nothing, and `GET /instance/income_totals` is a 404; `totals`: the net income of each
   * currency by period; `plans`: also the number of members of each supporter plan now. No
   * level publishes individual payments, the subscribers, the amounts of each plan, or the
   * gross and the fees.
   */
  @SerialName("income_visibility")
  public val incomeVisibility: IncomeVisibility,
  /**
   * How far this month's net income goes toward the instance's monthly target (the instance
   * setting `funding_meter_visibility`): the income and the target are prorated as the totals
   * under the basis `amortized` are, and the month is the calendar month that holds today in UTC.
   * Only the income in the target's currency counts; no amount is converted. The target is the
   * one the operators set, or this month's costs while they are in one currency. `percent` alone
   * is published while the costs and the income are both private, so that no public amount turns
   * it into a private one; `income` and `target` are added only while both are public.
   */
  @SerialName("funding_meter")
  public val fundingMeter: FundingMeter? = null,
  /**
   * Whether the instance is a one-person server: the home of its operator alone, with nobody else
   * to join. While it is true, `registrations.mode` is `closed` and both `timelines` are `off`,
   * whatever the instance settings store. Clients also hide the suggestions of accounts, invites,
   * the review of registrations, the sign-up blocks, and the visibility `local`: those operations
   * answer a 404 of the type `/api/v1/problems/single-user-mode`, and a new `local` post is a 422.
   */
  @SerialName("single_user")
  public val singleUser: Boolean,
  /**
   * The operator's account of a one-person server, whose profile the web client shows people who
   * are not signed in at the root address. Present only while `single_user` is true, and omitted
   * when the account cannot be shown (it is suspended or deleted).
   */
  @SerialName("single_user_account")
  public val singleUserAccount: Account? = null,
)
