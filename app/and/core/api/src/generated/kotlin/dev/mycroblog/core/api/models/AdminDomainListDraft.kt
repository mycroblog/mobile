package dev.mycroblog.core.api.models

import kotlin.Boolean
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A difference between a subscribed list and the actions the subscription issued, waiting for a moderator.
 */
@Serializable
public data class AdminDomainListDraft(
  /**
   * Names the draft to accept or reject.
   */
  @SerialName("key")
  public val key: String,
  /**
   * `issue`: the list names the domain, and no action of the kind is in effect on it or a
   * parent domain. `lift`: an action that the subscription issued is no longer on the list.
   */
  @SerialName("change")
  public val change: AdminDomainListChange,
  /**
   * The domain, in IDNA A-labels.
   */
  @SerialName("domain")
  public val domain: String,
  /**
   * `suspend`: no federation with the domain at all. `limit`: its posts stay off the public
   * timelines and its accounts reach only their followers. `reject_media`: its media are
   * not fetched. `reject_reports`: its reports are dropped. `force_sensitive`: its media
   * are marked sensitive.
   */
  @SerialName("kind")
  public val kind: AdminDomainActionKind,
  /**
   * Why a difference waits for a moderator. `draft_mode`: the subscription is in the draft
   * mode. `unknown_severity`: the list gives the domain a severity this server does not know.
   * `mass_lift`: one fetch would lift more than 10 actions and more than half of the
   * subscription's actions, which a broken or empty list would do.
   */
  @SerialName("held")
  public val held: AdminDomainListHold,
  /**
   * The action that a `lift` lifts; omitted for an `issue`.
   */
  @SerialName("action_id")
  public val actionId: String? = null,
  /**
   * The severity as the list gives it, for an `issue`.
   */
  @SerialName("severity")
  public val severity: String? = null,
  /**
   * The public comment of the row, for an `issue`; omitted when there is none.
   */
  @SerialName("public_comment")
  public val publicComment: String? = null,
  /**
   * Whether the row asks to hide part of the domain, for an `issue`.
   */
  @SerialName("obfuscate")
  public val obfuscate: Boolean? = null,
)
