package dev.mycroblog.core.api.client

import dev.mycroblog.core.api.models.AdminAccountAction
import dev.mycroblog.core.api.models.AdminAccountActionPage
import dev.mycroblog.core.api.models.AdminAccountDetail
import dev.mycroblog.core.api.models.AdminAccountPage
import dev.mycroblog.core.api.models.AdminAccountPrivateData
import dev.mycroblog.core.api.models.AdminAccountSignInPage
import dev.mycroblog.core.api.models.AdminAccountState
import dev.mycroblog.core.api.models.AdminAppeal
import dev.mycroblog.core.api.models.AdminAppealPage
import dev.mycroblog.core.api.models.AdminAuditAction
import dev.mycroblog.core.api.models.AdminAuditEntryPage
import dev.mycroblog.core.api.models.AdminAuditTargetKind
import dev.mycroblog.core.api.models.AdminAvailabilityEventPage
import dev.mycroblog.core.api.models.AdminCounts
import dev.mycroblog.core.api.models.AdminDeliveryPage
import dev.mycroblog.core.api.models.AdminDeliveryReveal
import dev.mycroblog.core.api.models.AdminDomainAction
import dev.mycroblog.core.api.models.AdminDomainActionPage
import dev.mycroblog.core.api.models.AdminDomainListDraftList
import dev.mycroblog.core.api.models.AdminDomainListDraftResult
import dev.mycroblog.core.api.models.AdminDomainListFetchPage
import dev.mycroblog.core.api.models.AdminDomainListSubscription
import dev.mycroblog.core.api.models.AdminDomainListSubscriptionPage
import dev.mycroblog.core.api.models.AdminEventCancel
import dev.mycroblog.core.api.models.AdminHookAppeal
import dev.mycroblog.core.api.models.AdminHookAppealPage
import dev.mycroblog.core.api.models.AdminHookDecision
import dev.mycroblog.core.api.models.AdminHookDecisionPage
import dev.mycroblog.core.api.models.AdminHookSummary
import dev.mycroblog.core.api.models.AdminInstanceHealth
import dev.mycroblog.core.api.models.AdminInstanceHealthPage
import dev.mycroblog.core.api.models.AdminInstanceState
import dev.mycroblog.core.api.models.AdminInvite
import dev.mycroblog.core.api.models.AdminInvitePage
import dev.mycroblog.core.api.models.AdminRegistration
import dev.mycroblog.core.api.models.AdminRegistrationPage
import dev.mycroblog.core.api.models.AdminReportDetail
import dev.mycroblog.core.api.models.AdminReportPage
import dev.mycroblog.core.api.models.AdminReportState
import dev.mycroblog.core.api.models.AdminSignupBlock
import dev.mycroblog.core.api.models.AdminSignupBlockKind
import dev.mycroblog.core.api.models.AdminSignupBlockPage
import dev.mycroblog.core.api.models.AdminVoiceRoomEnd
import dev.mycroblog.core.api.models.CreateAccountActionRequest
import dev.mycroblog.core.api.models.CreateDomainActionRequest
import dev.mycroblog.core.api.models.CreateDomainAllowRequest
import dev.mycroblog.core.api.models.CreateDomainListSubscriptionRequest
import dev.mycroblog.core.api.models.CreateInviteRequest
import dev.mycroblog.core.api.models.CreateReportNoteRequest
import dev.mycroblog.core.api.models.CreateSignupBlockRequest
import dev.mycroblog.core.api.models.DecideAppealRequest
import dev.mycroblog.core.api.models.DomainAllow
import dev.mycroblog.core.api.models.DomainAllowList
import dev.mycroblog.core.api.models.DomainBlockImportPreview
import dev.mycroblog.core.api.models.DomainBlockImportResult
import dev.mycroblog.core.api.models.DomainListDraftsRequest
import dev.mycroblog.core.api.models.DomainSuspendImpact
import dev.mycroblog.core.api.models.ForwardReportRequest
import dev.mycroblog.core.api.models.HookAppealState
import dev.mycroblog.core.api.models.HookDecisionMode
import dev.mycroblog.core.api.models.HookName
import dev.mycroblog.core.api.models.HookOutcome
import dev.mycroblog.core.api.models.ImportDomainBlocksRequest
import dev.mycroblog.core.api.models.ImportSignupBlocksRequest
import dev.mycroblog.core.api.models.LiftAccountActionRequest
import dev.mycroblog.core.api.models.ModerationAppealState
import dev.mycroblog.core.api.models.Order
import dev.mycroblog.core.api.models.Origin
import dev.mycroblog.core.api.models.PreviewDomainBlockImportRequest
import dev.mycroblog.core.api.models.RegistrationDecisionRequest
import dev.mycroblog.core.api.models.ReportCategory
import dev.mycroblog.core.api.models.ReportCommentTranslation
import dev.mycroblog.core.api.models.ResolveReportRequest
import dev.mycroblog.core.api.models.SignupBlockImportResult
import dev.mycroblog.core.api.models.StatusTranslation
import dev.mycroblog.core.api.models.StatusTranslationRequest
import dev.mycroblog.core.api.models.UpdateDomainListSubscriptionRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.`get`
import io.ktor.client.request.`header`
import io.ktor.client.request.delete
import io.ktor.client.request.headers
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import io.ktor.serialization.ContentConvertException
import java.util.UUID
import kotlin.Boolean
import kotlin.ByteArray
import kotlin.Int
import kotlin.String
import kotlin.Unit
import kotlinx.coroutines.CancellationException
import kotlinx.io.IOException

public class AdminModerationClient(
  private val httpClient: HttpClient,
) {
  /**
   * List the known remote servers with their health
   * The remote servers this instance knows, in domain order: those it stored a document of,
   * and those deliveries went to. Each one carries its health, when it was first seen, and
   * the software its NodeInfo last reported. `troubled` keeps the servers that deliveries
   * failed to reach on some days or that are marked unavailable; `state` keeps those in one
   * state, a server without failures being `available`. Unavailable servers are sent only
   * `Follow`, `Accept`, and `Reject` until a delivery succeeds, they send a signed request,
   * the daily probe reaches them, or an operator marks them available. Needs the
   * `manage_domains` permission.
   *
   *
   * Parameters:
   * 	 @param limit The maximum number of items in the page.
   * 	 @param troubled Keep only the servers with delivery failures or marked unavailable (`true`); `false`
   * or omitted keeps every known server.
   * 	 @param state Keep only the servers in this state; omitted keeps both.
   * 	 @param cursor The `next_cursor` of the previous page; omitted for the first page.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.AdminInstanceHealthPage] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun listInstanceHealth(
    limit: Int? = null,
    troubled: Boolean? = null,
    state: AdminInstanceState? = null,
    cursor: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<AdminInstanceHealthPage> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = buildString {
      append(basePath)
      append("""/admin/instances""")
      val params = buildList {
        limit?.let { add("limit=${it}") }
        troubled?.let { add("troubled=${it}") }
        state?.let { add("state=${it}") }
        cursor?.let { add("cursor=${it}") }
      }
      if (params.isNotEmpty()) append("?").append(params.joinToString("&"))
    }

    return try {
      val response = httpClient.`get`(url) {
        `header`("Accept", "application/json")
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * Get the health of a remote server
   * The health of the server, when it was first seen, and the software its NodeInfo last
   * reported; one without failures is `available`, without `state_changed_at`. Needs the
   * `manage_domains` permission.
   *
   *
   * Parameters:
   * 	 @param domain The domain of the remote server, such as `mastodon.example`, normalized to IDNA A-labels in
   * lower case without a trailing dot. A value that is not a host name is a 400.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.AdminInstanceHealth] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun getInstanceHealth(domain: String, apiConfiguration: ApiConfiguration = ApiConfiguration()): NetworkResult<AdminInstanceHealth> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/admin/instances/${domain}"""

    return try {
      val response = httpClient.`get`(url) {
        `header`("Accept", "application/json")
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * List the times a remote server was marked unavailable or available
   * Why and when the server was marked unavailable or available again, newest first. Needs
   * the `manage_domains` permission.
   *
   *
   * Parameters:
   * 	 @param domain The domain of the remote server, such as `mastodon.example`, normalized to IDNA A-labels in
   * lower case without a trailing dot. A value that is not a host name is a 400.
   * 	 @param limit The maximum number of items in the page.
   * 	 @param cursor The `next_cursor` of the previous page; omitted for the first page.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.AdminAvailabilityEventPage] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun listInstanceAvailabilityEvents(
    domain: String,
    limit: Int? = null,
    cursor: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<AdminAvailabilityEventPage> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = buildString {
      append(basePath)
      append("""/admin/instances/${domain}/availability_events""")
      val params = buildList {
        limit?.let { add("limit=${it}") }
        cursor?.let { add("cursor=${it}") }
      }
      if (params.isNotEmpty()) append("?").append(params.joinToString("&"))
    }

    return try {
      val response = httpClient.`get`(url) {
        `header`("Accept", "application/json")
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * Mark a remote server available
   * Clears the failure days of the server and, when it is unavailable, marks it available
   * again with the reason `operator`, so that deliveries to it resume; the operation is
   * audited as `instance_marked_available`. A server that is already available without
   * failures stays as it is, and nothing is audited. Needs the `manage_domains`
   * permission.
   *
   *
   * Parameters:
   * 	 @param domain The domain of the remote server, such as `mastodon.example`, normalized to IDNA A-labels in
   * lower case without a trailing dot. A value that is not a host name is a 400.
   * 	 @param idempotencyKey A key the client chooses per request, which makes the request safe to send again. Within
   * 24 hours, a request with a key that the same sign-in of the user in the same client (an
   * anonymous client: the same address) already sent to the operation gets the first answer
   * again instead of being carried out again. The same key with another request (another
   * path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat
   * while the first request is still being carried out is a 409
   * `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx
   * or 5xx the key is free, so a request sent again with it is carried out. A key longer than
   * 256 characters, or one that is not UTF-8 without U+0000, is a 422.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.AdminInstanceHealth] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun markInstanceAvailable(
    domain: String,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<AdminInstanceHealth> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/admin/instances/${domain}/mark_available"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Idempotency-Key", idempotencyKey)
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * List the deliveries to a remote server and their attempts
   * What this server sent to the remote server and what it answered, newest first: each
   * delivery of an activity to an inbox of the server, with every attempt (its outcome, the
   * signature scheme, the status code, and the error), the ones that succeeded included.
   * Attempts are kept for 30 days. The body of the activity is never returned.
   *
   * The type and the times of every delivery, and every attempt, are always shown. When the
   * activity is about a post whose visibility is `private`, `direct`, or `local`, or is a
   * follow, its undo, an accept, or a reject of a local account that approves its followers, the sender,
   * the activity URI, and a personal inbox are masked: `mask` says why, and `POST
   * /admin/instances/{domain}/deliveries/{id}/reveal` shows them, audited. A shared inbox is
   * never masked, and a masked inbox in an error reads `[masked]`.
   *
   * A query with `account_id` is recorded in the audit log as `account_deliveries_viewed`
   * before anything is returned; an account that is not local is a 400 naming `account_id`,
   * an unknown one a 404. Needs the `manage_domains` permission.
   *
   *
   * Parameters:
   * 	 @param domain The domain of the remote server, such as `mastodon.example`, normalized to IDNA A-labels in
   * lower case without a trailing dot. A value that is not a host name is a 400.
   * 	 @param limit The maximum number of items in the page.
   * 	 @param accountId Keep only the deliveries of the activities of this local account; audited.
   * 	 @param activityUri Keep only the deliveries of the activity with this ID.
   * 	 @param cursor The `next_cursor` of the previous page; omitted for the first page.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.AdminDeliveryPage] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun listInstanceDeliveries(
    domain: String,
    limit: Int? = null,
    accountId: UUID? = null,
    activityUri: String? = null,
    cursor: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<AdminDeliveryPage> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = buildString {
      append(basePath)
      append("""/admin/instances/${domain}/deliveries""")
      val params = buildList {
        limit?.let { add("limit=${it}") }
        accountId?.let { add("account_id=${it}") }
        activityUri?.let { add("activity_uri=${it}") }
        cursor?.let { add("cursor=${it}") }
      }
      if (params.isNotEmpty()) append("?").append(params.joinToString("&"))
    }

    return try {
      val response = httpClient.`get`(url) {
        `header`("Accept", "application/json")
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * Show the sender, the activity URI, and the inbox of a delivery
   * The fields that the list of deliveries masks, of one delivery to the server: its local
   * sender, the URI of its activity, and its inbox. Each call is recorded in the audit log as
   * `delivery_revealed` before anything is returned, also for a delivery that is not masked;
   * it changes nothing else. The body of the activity is never returned. A delivery that is
   * unknown or went to another server is a 404. Needs the `manage_domains` permission.
   *
   *
   * Parameters:
   * 	 @param domain The domain of the remote server, such as `mastodon.example`, normalized to IDNA A-labels in
   * lower case without a trailing dot. A value that is not a host name is a 400.
   * 	 @param id The ID of the delivery.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.AdminDeliveryReveal] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun revealInstanceDelivery(
    domain: String,
    id: UUID,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<AdminDeliveryReveal> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/admin/instances/${domain}/deliveries/${id}/reveal"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * List the audit log
   * The operations of the moderators and administrators, newest first: who did
   * what to which target and when. Each entry keeps the target's display name as it was
   * (`target_label`), so it stays readable after the target is gone; the reasons and notes
   * stay with the operation itself (`source_event_id`). The audit log cannot be changed or
   * deleted through the API. An unknown `action` or `target_kind`, or a `target_id`
   * without its `target_kind`, is a 400. Needs the `view_audit` permission.
   *
   *
   * Parameters:
   * 	 @param limit The maximum number of items in the page.
   * 	 @param actorAccountId Keep only the operations of the user of this local account. An account that is not
   * local, or not known, has no operations.
   * 	 @param action Keep only this kind of operation.
   * 	 @param targetKind Keep only the operations on this kind of target.
   * 	 @param targetId Keep only the operations on this target, of the kind `target_kind`.
   * 	 @param cursor The `next_cursor` of the previous page; omitted for the first page.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.AdminAuditEntryPage] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun listAuditLog(
    limit: Int? = null,
    actorAccountId: UUID? = null,
    action: AdminAuditAction? = null,
    targetKind: AdminAuditTargetKind? = null,
    targetId: String? = null,
    cursor: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<AdminAuditEntryPage> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = buildString {
      append(basePath)
      append("""/admin/audit_log""")
      val params = buildList {
        limit?.let { add("limit=${it}") }
        actorAccountId?.let { add("actor_account_id=${it}") }
        action?.let { add("action=${it}") }
        targetKind?.let { add("target_kind=${it}") }
        targetId?.let { add("target_id=${it}") }
        cursor?.let { add("cursor=${it}") }
      }
      if (params.isNotEmpty()) append("?").append(params.joinToString("&"))
    }

    return try {
      val response = httpClient.`get`(url) {
        `header`("Accept", "application/json")
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * List the moderation actions on domains
   * The actions that moderators took on remote domains, newest first, lifted and
   * expired ones included unless `active` is set. Needs the `manage_domains` permission.
   *
   *
   * Parameters:
   * 	 @param limit The maximum number of items in the page.
   * 	 @param domain Keep only the actions on this domain (not those on its parent domains). It is
   * normalized like the domain of a new action.
   * 	 @param active Keep only the actions in effect now, neither lifted nor expired.
   * 	 @param cursor The `next_cursor` of the previous page; omitted for the first page.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.AdminDomainActionPage] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun listDomainActions(
    limit: Int? = null,
    domain: String? = null,
    active: Boolean? = null,
    cursor: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<AdminDomainActionPage> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = buildString {
      append(basePath)
      append("""/admin/domain_actions""")
      val params = buildList {
        limit?.let { add("limit=${it}") }
        domain?.let { add("domain=${it}") }
        active?.let { add("active=${it}") }
        cursor?.let { add("cursor=${it}") }
      }
      if (params.isNotEmpty()) append("?").append(params.joinToString("&"))
    }

    return try {
      val response = httpClient.`get`(url) {
        `header`("Accept", "application/json")
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * Take a moderation action on a domain
   * Records an action on a remote domain and its subdomains, in effect at once and
   * until it expires or is lifted. A `suspend` stops every delivery to the domain, every
   * fetch from it, and every activity received from it, and hides its accounts and posts
   * from the timelines; `limit` keeps its posts off the public timelines and its accounts
   * from reaching users who do not follow them; `reject_media` stops fetching its media;
   * `reject_reports` drops the reports it sends; `force_sensitive` marks its media
   * sensitive. The domain is normalized (IDNA A-labels in lower case, without a trailing
   * dot); one that is not a valid domain, or this server's own, is a 422 naming `domain`. A `public_comment` or
   * `private_note` that is too long or contains U+0000 is a 422 naming it. Needs the
   * `manage_domains` permission.
   *
   *
   * Parameters:
   * 	 @param createDomainActionRequest 
   * 	 @param idempotencyKey A key the client chooses per request, which makes the request safe to send again. Within
   * 24 hours, a request with a key that the same sign-in of the user in the same client (an
   * anonymous client: the same address) already sent to the operation gets the first answer
   * again instead of being carried out again. The same key with another request (another
   * path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat
   * while the first request is still being carried out is a 409
   * `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx
   * or 5xx the key is free, so a request sent again with it is carried out. A key longer than
   * 256 characters, or one that is not UTF-8 without U+0000, is a 422.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.AdminDomainAction] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun createDomainAction(
    createDomainActionRequest: CreateDomainActionRequest,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<AdminDomainAction> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/admin/domain_actions"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Content-Type", "application/json")
        setBody(createDomainActionRequest)
        `header`("Idempotency-Key", idempotencyKey)
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * Get a moderation action on a domain
   * An unknown ID is a 404. Needs the `manage_domains` permission.
   *
   * Parameters:
   * 	 @param id The ID of the domain action.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.AdminDomainAction] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun getDomainAction(id: UUID, apiConfiguration: ApiConfiguration = ApiConfiguration()): NetworkResult<AdminDomainAction> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/admin/domain_actions/${id}"""

    return try {
      val response = httpClient.`get`(url) {
        `header`("Accept", "application/json")
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * List the decisions of the decision hooks
   * What the external reviews said, the newest first. Without `outcome` the list leaves out
   * `allow`: it shows what a hook stopped, would have stopped in `observe`, or could not check.
   * Needs the `manage_reports` permission.
   *
   *
   * Parameters:
   * 	 @param limit The maximum number of items in the page.
   * 	 @param hook Keep the decisions of this hook.
   * 	 @param mode Keep the decisions made in this mode.
   * 	 @param outcome Keep the decisions with this outcome.
   * 	 @param cursor The `next_cursor` of the previous page; omitted for the first page.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.AdminHookDecisionPage] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun listHookDecisions(
    limit: Int? = null,
    hook: HookName? = null,
    mode: HookDecisionMode? = null,
    outcome: HookOutcome? = null,
    cursor: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<AdminHookDecisionPage> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = buildString {
      append(basePath)
      append("""/admin/hook_decisions""")
      val params = buildList {
        limit?.let { add("limit=${it}") }
        hook?.let { add("hook=${it}") }
        mode?.let { add("mode=${it}") }
        outcome?.let { add("outcome=${it}") }
        cursor?.let { add("cursor=${it}") }
      }
      if (params.isNotEmpty()) append("?").append(params.joinToString("&"))
    }

    return try {
      val response = httpClient.`get`(url) {
        `header`("Accept", "application/json")
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * Summarize the decision hooks
   * Every decision hook with its settings, and the count and latency of its decisions of the
   * last 7 days per mode and outcome. Needs the `manage_reports` permission.
   *
   *
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.AdminHookSummary] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun getHookSummary(apiConfiguration: ApiConfiguration = ApiConfiguration()): NetworkResult<AdminHookSummary> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/admin/hook_decisions/summary"""

    return try {
      val response = httpClient.`get`(url) {
        `header`("Accept", "application/json")
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * Get a decision
   * One decision of a decision hook, with its cancellation. An unknown ID is a 404. Needs the
   * `manage_reports` permission.
   *
   *
   * Parameters:
   * 	 @param id The ID of the decision.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.AdminHookDecision] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun getHookDecision(id: UUID, apiConfiguration: ApiConfiguration = ApiConfiguration()): NetworkResult<AdminHookDecision> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/admin/hook_decisions/${id}"""

    return try {
      val response = httpClient.`get`(url) {
        `header`("Accept", "application/json")
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * Cancel a decision
   * Records the cancellation as its own event and in the audit log, and undoes what the hook
   * can: what happens depends on the verdict. Cancelling a canceled decision again changes
   * nothing and answers the decision. An unknown ID is a 404. A decision that had no effect, or
   * a `reject` of `local.status`, which goes through its appeal, is a 409
   * `/api/v1/problems/hook-decision-not-overridable`. Needs the `manage_reports` permission.
   *
   *
   * Parameters:
   * 	 @param id The ID of the decision.
   * 	 @param idempotencyKey A key the client chooses per request, which makes the request safe to send again. Within
   * 24 hours, a request with a key that the same sign-in of the user in the same client (an
   * anonymous client: the same address) already sent to the operation gets the first answer
   * again instead of being carried out again. The same key with another request (another
   * path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat
   * while the first request is still being carried out is a 409
   * `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx
   * or 5xx the key is free, so a request sent again with it is carried out. A key longer than
   * 256 characters, or one that is not UTF-8 without U+0000, is a 422.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.AdminHookDecision] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun overrideHookDecision(
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<AdminHookDecision> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/admin/hook_decisions/${id}/override"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Idempotency-Key", idempotencyKey)
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * List the appeals of refused posts
   * The authors' appeals of a `reject` of `local.status`, the newest first, with the text each
   * author tried to post. Needs the `manage_reports` permission.
   *
   *
   * Parameters:
   * 	 @param limit The maximum number of items in the page.
   * 	 @param state Keep only the appeals in this state; omitted keeps every state.
   * 	 @param cursor The `next_cursor` of the previous page; omitted for the first page.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.AdminHookAppealPage] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun listHookAppeals(
    limit: Int? = null,
    state: HookAppealState? = null,
    cursor: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<AdminHookAppealPage> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = buildString {
      append(basePath)
      append("""/admin/hook_appeals""")
      val params = buildList {
        limit?.let { add("limit=${it}") }
        state?.let { add("state=${it}") }
        cursor?.let { add("cursor=${it}") }
      }
      if (params.isNotEmpty()) append("?").append(params.joinToString("&"))
    }

    return try {
      val response = httpClient.`get`(url) {
        `header`("Accept", "application/json")
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * Get an appeal of a refused post
   * One author's appeal of a `reject` of `local.status`, with its decision and the text the author
   * tried to post. An unknown ID is a 404. Needs the `manage_reports` permission.
   *
   *
   * Parameters:
   * 	 @param id The ID of the appeal.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.AdminHookAppeal] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun getHookAppeal(id: UUID, apiConfiguration: ApiConfiguration = ApiConfiguration()): NetworkResult<AdminHookAppeal> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/admin/hook_appeals/${id}"""

    return try {
      val response = httpClient.`get`(url) {
        `header`("Accept", "application/json")
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * Uphold an appeal of a refused post
   * Rejects the appeal; the decision stays as it is and the author's posts are reviewed as
   * before. Deciding a decided appeal the same way again changes nothing; deciding it the other
   * way is a 409 `/api/v1/problems/appeal-decided`. An unknown ID is a 404. Needs the
   * `manage_reports` permission.
   *
   *
   * Parameters:
   * 	 @param id The ID of the appeal.
   * 	 @param idempotencyKey A key the client chooses per request, which makes the request safe to send again. Within
   * 24 hours, a request with a key that the same sign-in of the user in the same client (an
   * anonymous client: the same address) already sent to the operation gets the first answer
   * again instead of being carried out again. The same key with another request (another
   * path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat
   * while the first request is still being carried out is a 409
   * `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx
   * or 5xx the key is free, so a request sent again with it is carried out. A key longer than
   * 256 characters, or one that is not UTF-8 without U+0000, is a 422.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.AdminHookAppeal] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun upholdHookAppeal(
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<AdminHookAppeal> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/admin/hook_appeals/${id}/uphold"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Idempotency-Key", idempotencyKey)
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * Overturn an appeal of a refused post
   * Cancels the decision, which goes in the audit log as `hook_decision_overridden`, and stops
   * reviewing the author's posts with `local.status`, in one transaction. The refused post is not
   * restored: the author posts again. Deciding a decided appeal the same way again changes
   * nothing; deciding it the other way is a 409 `/api/v1/problems/appeal-decided`. An unknown ID
   * is a 404. Needs the `manage_reports` permission.
   *
   *
   * Parameters:
   * 	 @param id The ID of the appeal.
   * 	 @param idempotencyKey A key the client chooses per request, which makes the request safe to send again. Within
   * 24 hours, a request with a key that the same sign-in of the user in the same client (an
   * anonymous client: the same address) already sent to the operation gets the first answer
   * again instead of being carried out again. The same key with another request (another
   * path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat
   * while the first request is still being carried out is a 409
   * `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx
   * or 5xx the key is free, so a request sent again with it is carried out. A key longer than
   * 256 characters, or one that is not UTF-8 without U+0000, is a 422.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.AdminHookAppeal] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun overturnHookAppeal(
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<AdminHookAppeal> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/admin/hook_appeals/${id}/overturn"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Idempotency-Key", idempotencyKey)
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * Lift a moderation action on a domain
   * Ends the action's restrictions at once; those of other actions on the domain stay.
   * Lifting a lifted action changes nothing. An unknown ID is a 404. Needs the
   * `manage_domains` permission.
   *
   *
   * Parameters:
   * 	 @param id The ID of the domain action.
   * 	 @param idempotencyKey A key the client chooses per request, which makes the request safe to send again. Within
   * 24 hours, a request with a key that the same sign-in of the user in the same client (an
   * anonymous client: the same address) already sent to the operation gets the first answer
   * again instead of being carried out again. The same key with another request (another
   * path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat
   * while the first request is still being carried out is a 409
   * `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx
   * or 5xx the key is free, so a request sent again with it is carried out. A key longer than
   * 256 characters, or one that is not UTF-8 without U+0000, is a 422.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.AdminDomainAction] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun liftDomainAction(
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<AdminDomainAction> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/admin/domain_actions/${id}/lift"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Idempotency-Key", idempotencyKey)
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * List the reports
   * The reports in the moderators' queue, newest first unless `order` is `oldest`.
   * Needs the `manage_reports` permission.
   *
   *
   * Parameters:
   * 	 @param order `newest` (the default) lists the most recently filed report first; `oldest` lists the
   * report that has waited longest first. The cursor continues the page in the same order.
   * 	 @param limit The maximum number of items in the page.
   * 	 @param state Keep only the reports in this state; omitted keeps both.
   * 	 @param category Keep only the reports of this category; omitted keeps every category.
   * 	 @param assignee Keep only the reports assigned to someone: `me` for the viewer, `none` for the reports
   * that nobody handles, or the ID of a local account. Omitted keeps every report; any
   * other value is a `400`.
   * 	 @param targetAccountId Keep only the reports about this account, local or remote. An account that no report is
   * about gives an empty page.
   * 	 @param cursor The `next_cursor` of the previous page; omitted for the first page.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.AdminReportPage] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun listReports(
    order: Order? = null,
    limit: Int? = null,
    state: AdminReportState? = null,
    category: ReportCategory? = null,
    assignee: String? = null,
    targetAccountId: UUID? = null,
    cursor: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<AdminReportPage> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = buildString {
      append(basePath)
      append("""/admin/reports""")
      val params = buildList {
        order?.let { add("order=${it}") }
        limit?.let { add("limit=${it}") }
        state?.let { add("state=${it}") }
        category?.let { add("category=${it}") }
        assignee?.let { add("assignee=${it}") }
        targetAccountId?.let { add("target_account_id=${it}") }
        cursor?.let { add("cursor=${it}") }
      }
      if (params.isNotEmpty()) append("?").append(params.joinToString("&"))
    }

    return try {
      val response = httpClient.`get`(url) {
        `header`("Accept", "application/json")
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * Get a report
   * The report as a moderator opens it, with the posts it points at whatever their
   * visibility. An unknown ID is a 404. Needs the `manage_reports` permission.
   *
   *
   * Parameters:
   * 	 @param id The ID of the report.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.AdminReportDetail] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun getReport(id: UUID, apiConfiguration: ApiConfiguration = ApiConfiguration()): NetworkResult<AdminReportDetail> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/admin/reports/${id}"""

    return try {
      val response = httpClient.`get`(url) {
        `header`("Accept", "application/json")
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * Take a report
   * Makes the authenticated user the moderator who handles the report. An unknown ID is a
   * 404. Needs the `manage_reports` permission.
   *
   *
   * Parameters:
   * 	 @param id The ID of the report.
   * 	 @param idempotencyKey A key the client chooses per request, which makes the request safe to send again. Within
   * 24 hours, a request with a key that the same sign-in of the user in the same client (an
   * anonymous client: the same address) already sent to the operation gets the first answer
   * again instead of being carried out again. The same key with another request (another
   * path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat
   * while the first request is still being carried out is a 409
   * `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx
   * or 5xx the key is free, so a request sent again with it is carried out. A key longer than
   * 256 characters, or one that is not UTF-8 without U+0000, is a 422.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.AdminReportDetail] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun assignReportToSelf(
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<AdminReportDetail> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/admin/reports/${id}/assign_to_self"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Idempotency-Key", idempotencyKey)
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * Leave a report to nobody
   * Removes the moderator who handles the report, whoever it is. An unknown ID is a 404.
   * Needs the `manage_reports` permission.
   *
   *
   * Parameters:
   * 	 @param id The ID of the report.
   * 	 @param idempotencyKey A key the client chooses per request, which makes the request safe to send again. Within
   * 24 hours, a request with a key that the same sign-in of the user in the same client (an
   * anonymous client: the same address) already sent to the operation gets the first answer
   * again instead of being carried out again. The same key with another request (another
   * path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat
   * while the first request is still being carried out is a 409
   * `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx
   * or 5xx the key is free, so a request sent again with it is carried out. A key longer than
   * 256 characters, or one that is not UTF-8 without U+0000, is a 422.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.AdminReportDetail] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun unassignReport(
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<AdminReportDetail> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/admin/reports/${id}/unassign"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Idempotency-Key", idempotencyKey)
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * Resolve a report
   * Resolves an open report with an outcome. Resolving a resolved report changes nothing
   * and keeps its outcome. An unknown ID is a 404. Needs the `manage_reports` permission.
   *
   *
   * Parameters:
   * 	 @param resolveReportRequest 
   * 	 @param id The ID of the report.
   * 	 @param idempotencyKey A key the client chooses per request, which makes the request safe to send again. Within
   * 24 hours, a request with a key that the same sign-in of the user in the same client (an
   * anonymous client: the same address) already sent to the operation gets the first answer
   * again instead of being carried out again. The same key with another request (another
   * path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat
   * while the first request is still being carried out is a 409
   * `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx
   * or 5xx the key is free, so a request sent again with it is carried out. A key longer than
   * 256 characters, or one that is not UTF-8 without U+0000, is a 422.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.AdminReportDetail] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun resolveReport(
    resolveReportRequest: ResolveReportRequest,
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<AdminReportDetail> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/admin/reports/${id}/resolve"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Content-Type", "application/json")
        setBody(resolveReportRequest)
        `header`("Idempotency-Key", idempotencyKey)
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * Write a note on a report
   * Adds an internal note to the report. Notes are shown to the moderators only,
   * never to the target or the reporter, and cannot be edited or removed. An unknown ID is
   * a 404. Needs the `manage_reports` permission.
   *
   *
   * Parameters:
   * 	 @param createReportNoteRequest 
   * 	 @param id The ID of the report.
   * 	 @param idempotencyKey A key the client chooses per request, which makes the request safe to send again. Within
   * 24 hours, a request with a key that the same sign-in of the user in the same client (an
   * anonymous client: the same address) already sent to the operation gets the first answer
   * again instead of being carried out again. The same key with another request (another
   * path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat
   * while the first request is still being carried out is a 409
   * `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx
   * or 5xx the key is free, so a request sent again with it is carried out. A key longer than
   * 256 characters, or one that is not UTF-8 without U+0000, is a 422.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.AdminReportDetail] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun addReportNote(
    createReportNoteRequest: CreateReportNoteRequest,
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<AdminReportDetail> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/admin/reports/${id}/notes"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Content-Type", "application/json")
        setBody(createReportNoteRequest)
        `header`("Idempotency-Key", idempotencyKey)
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * Reopen a resolved report
   * Puts a resolved report back in the queue as `open`, keeping its assignee. Resolving it
   * again does not notify the reporter again. An open report is a 409, an unknown ID a
   * 404. Needs the `manage_reports` permission.
   *
   *
   * Parameters:
   * 	 @param id The ID of the report.
   * 	 @param idempotencyKey A key the client chooses per request, which makes the request safe to send again. Within
   * 24 hours, a request with a key that the same sign-in of the user in the same client (an
   * anonymous client: the same address) already sent to the operation gets the first answer
   * again instead of being carried out again. The same key with another request (another
   * path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat
   * while the first request is still being carried out is a 409
   * `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx
   * or 5xx the key is free, so a request sent again with it is carried out. A key longer than
   * 256 characters, or one that is not UTF-8 without U+0000, is a 422.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.AdminReportDetail] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun reopenReport(
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<AdminReportDetail> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/admin/reports/${id}/reopen"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Idempotency-Key", idempotencyKey)
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * Forward a report to the target's server
   * Sends the report to the server of its remote target as a `Flag` of the instance actor,
   * with the account and the posts it points at, and `comment` as its text. Nothing of the
   * reporter is sent: not their account, and not their comment, nor the note of an automated
   * review that filed it. Works on an open or a resolved report that `forwardable` allows:
   * one filed on this server (by a person or by an automated review) about a remote account,
   * of no voice chat room. A report forwarded already, when it was filed or later, changes
   * nothing, whatever `comment` says. A report that cannot be forwarded is a 409
   * `/api/v1/problems/report-not-forwardable`, an unknown ID a 404, and a `comment` that is too
   * long or contains U+0000 a 422 naming it. Needs the `manage_reports` permission.
   *
   *
   * Parameters:
   * 	 @param forwardReportRequest 
   * 	 @param id The ID of the report.
   * 	 @param idempotencyKey A key the client chooses per request, which makes the request safe to send again. Within
   * 24 hours, a request with a key that the same sign-in of the user in the same client (an
   * anonymous client: the same address) already sent to the operation gets the first answer
   * again instead of being carried out again. The same key with another request (another
   * path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat
   * while the first request is still being carried out is a 409
   * `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx
   * or 5xx the key is free, so a request sent again with it is carried out. A key longer than
   * 256 characters, or one that is not UTF-8 without U+0000, is a 422.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.AdminReportDetail] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun forwardReport(
    forwardReportRequest: ForwardReportRequest,
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<AdminReportDetail> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/admin/reports/${id}/forward"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Content-Type", "application/json")
        setBody(forwardReportRequest)
        `header`("Idempotency-Key", idempotencyKey)
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * Translate the comment of a report into a language
   * Returns the reporter's comment in `target_language`, for a moderator who reads the report. An
   * unknown report is a 404. Needs the `manage_reports` permission.
   *
   * Only the reporter and the moderators read a comment, so it counts as a private post: the server
   * sends it only while the instance setting `translation_reports_to_server` is on, and only to the
   * providers allowed private posts. The translation is never kept, and the characters sent count
   * against the server's monthly limit only. `provider` is omitted when the comment has no words to
   * translate, and the answer is the comment as it is.
   *
   * The problems are those of `POST /statuses/{id}/translation`: a 422
   * `/api/v1/problems/translation-unavailable` when the setting is off or no provider may receive the
   * comment, a 422 `/api/v1/problems/translation-unsupported-language`, a 429
   * `/api/v1/problems/translation-limit` over the server's monthly limit, and a 503
   * `/api/v1/problems/translation-failed`.
   *
   *
   * Parameters:
   * 	 @param statusTranslationRequest 
   * 	 @param id The ID of the report.
   * 	 @param idempotencyKey A key the client chooses per request, which makes the request safe to send again. Within
   * 24 hours, a request with a key that the same sign-in of the user in the same client (an
   * anonymous client: the same address) already sent to the operation gets the first answer
   * again instead of being carried out again. The same key with another request (another
   * path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat
   * while the first request is still being carried out is a 409
   * `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx
   * or 5xx the key is free, so a request sent again with it is carried out. A key longer than
   * 256 characters, or one that is not UTF-8 without U+0000, is a 422.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.ReportCommentTranslation] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun translateReportComment(
    statusTranslationRequest: StatusTranslationRequest,
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<ReportCommentTranslation> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/admin/reports/${id}/comment/translation"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Content-Type", "application/json")
        setBody(statusTranslationRequest)
        `header`("Idempotency-Key", idempotencyKey)
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * Translate a reported post into a language
   * Returns a post that the report points at in `target_language`, as `POST /statuses/{id}/translation`
   * does, for a moderator who reads the report: whatever the post's visibility, and also when its
   * author deleted it. A report that does not point at the post, an unknown report, and a post that is
   * gone are a 404. Needs the `manage_reports` permission.
   *
   * The moderator's access lifts nothing of what a provider may receive. The server sends the post to a
   * provider only while the instance setting `translation_reports_to_server` is on, and only to the
   * providers allowed the post's visibility, so a direct post goes only to a provider allowed direct
   * posts. The characters sent count against the server's monthly limit only. The translation is kept
   * for the readers of the post who may see it, except for a deleted post, whose translation is not
   * kept.
   *
   * The problems are those of `POST /statuses/{id}/translation`: a 422
   * `/api/v1/problems/translation-unavailable` when the setting is off or no provider may receive the
   * post, a 422 `/api/v1/problems/translation-unsupported-language`, a 429
   * `/api/v1/problems/translation-limit` over the server's monthly limit, and a 503
   * `/api/v1/problems/translation-failed`.
   *
   *
   * Parameters:
   * 	 @param statusTranslationRequest 
   * 	 @param id The ID of the report.
   * 	 @param statusId The ID of a post that the report points at.
   * 	 @param idempotencyKey A key the client chooses per request, which makes the request safe to send again. Within
   * 24 hours, a request with a key that the same sign-in of the user in the same client (an
   * anonymous client: the same address) already sent to the operation gets the first answer
   * again instead of being carried out again. The same key with another request (another
   * path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat
   * while the first request is still being carried out is a 409
   * `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx
   * or 5xx the key is free, so a request sent again with it is carried out. A key longer than
   * 256 characters, or one that is not UTF-8 without U+0000, is a 422.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.StatusTranslation] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun translateReportedStatus(
    statusTranslationRequest: StatusTranslationRequest,
    id: UUID,
    statusId: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<StatusTranslation> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/admin/reports/${id}/statuses/${statusId}/translation"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Content-Type", "application/json")
        setBody(statusTranslationRequest)
        `header`("Idempotency-Key", idempotencyKey)
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * End a voice chat room as a moderator
   * Ends an open voice chat room for every participant, with an optional reason for the
   * moderators. Recorded in the audit log (`voice_room_ended`). Works while the voice chat
   * is off too. An unknown or ended room is a 404, and a reason longer than 500 characters
   * a 422. Needs the `manage_reports` permission.
   *
   *
   * Parameters:
   * 	 @param adminVoiceRoomEnd 
   * 	 @param id The ID of the voice chat room.
   * 	 @param idempotencyKey A key the client chooses per request, which makes the request safe to send again. Within
   * 24 hours, a request with a key that the same sign-in of the user in the same client (an
   * anonymous client: the same address) already sent to the operation gets the first answer
   * again instead of being carried out again. The same key with another request (another
   * path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat
   * while the first request is still being carried out is a 409
   * `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx
   * or 5xx the key is free, so a request sent again with it is carried out. A key longer than
   * 256 characters, or one that is not UTF-8 without U+0000, is a 422.
   *
   * Returns:
   * 	[NetworkResult.Success] with [kotlin.Unit] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun endVoiceRoomAsModerator(
    adminVoiceRoomEnd: AdminVoiceRoomEnd,
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<Unit> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/admin/voice_rooms/${id}/end"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Content-Type", "application/json")
        setBody(adminVoiceRoomEnd)
        `header`("Idempotency-Key", idempotencyKey)
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * Cancel an event as a moderator
   * Cancels an event with an optional reason, which the event shows with its cancellation, and
   * notifies the users who answered going or maybe and the invited users. Recorded in the audit
   * log (`event_canceled`). An unknown event is a 404, an event canceled already a 409
   * `/api/v1/problems/event-closed`, and a reason longer than 500 characters a 422. Needs the
   * `manage_reports` permission.
   *
   *
   * Parameters:
   * 	 @param adminEventCancel 
   * 	 @param id The ID of the event.
   * 	 @param idempotencyKey A key the client chooses per request, which makes the request safe to send again. Within
   * 24 hours, a request with a key that the same sign-in of the user in the same client (an
   * anonymous client: the same address) already sent to the operation gets the first answer
   * again instead of being carried out again. The same key with another request (another
   * path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat
   * while the first request is still being carried out is a 409
   * `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx
   * or 5xx the key is free, so a request sent again with it is carried out. A key longer than
   * 256 characters, or one that is not UTF-8 without U+0000, is a 422.
   *
   * Returns:
   * 	[NetworkResult.Success] with [kotlin.Unit] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun cancelEventAsModerator(
    adminEventCancel: AdminEventCancel,
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<Unit> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/admin/events/${id}/cancel"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Content-Type", "application/json")
        setBody(adminEventCancel)
        `header`("Idempotency-Key", idempotencyKey)
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * List the moderation actions on an account
   * The actions that moderators took on an account, newest first, lifted and
   * expired ones included unless `active` is set. An unknown account is a 404. Needs the
   * `manage_accounts` permission.
   *
   *
   * Parameters:
   * 	 @param id The ID of the account, local or remote.
   * 	 @param limit The maximum number of items in the page.
   * 	 @param active Keep only the actions in effect now, neither lifted nor expired.
   * 	 @param cursor The `next_cursor` of the previous page; omitted for the first page.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.AdminAccountActionPage] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun listAccountActions(
    id: UUID,
    limit: Int? = null,
    active: Boolean? = null,
    cursor: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<AdminAccountActionPage> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = buildString {
      append(basePath)
      append("""/admin/accounts/${id}/actions""")
      val params = buildList {
        limit?.let { add("limit=${it}") }
        active?.let { add("active=${it}") }
        cursor?.let { add("cursor=${it}") }
      }
      if (params.isNotEmpty()) append("?").append(params.joinToString("&"))
    }

    return try {
      val response = httpClient.`get`(url) {
        `header`("Accept", "application/json")
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * Take a moderation action on an account
   * Records an action on an account, in effect at once and until it expires or is
   * lifted; several actions on one account add up. On a local account: `warn` only tells
   * the account; `force_sensitive` shows its media as sensitive; `limit` makes its posts,
   * past ones included, followers-only and keeps them off the public timelines;
   * `restrict_posting` stops it posting, replying, boosting, quoting, and editing;
   * `restrict_interaction` stops it following, favouriting, boosting, and voting;
   * `disable_login` ends its sessions and tokens and stops it logging in; `suspend` does all
   * of these and hides its profile and posts from everyone else; `ban` is a `suspend` that
   * is never lifted and deletes the account 30 days later. `delete_statuses` deletes the
   * posts of the account that `status_ids` names, for everyone, the account included, and
   * sends their deletion like the account's own; `mark_statuses_sensitive` shows their
   * media as sensitive until it is lifted. A remote account takes `limit`, `suspend`, and
   * `force_sensitive`, with the same effects here: a `suspend` also drops what the account
   * sends, and its lift brings the profile and posts back. An action on a remote account is
   * not sent to its server, and `notify_target` does not apply to it. Another kind, the instance actor, or a `report_id` that is not a
   * report of the account is a 422 naming `kind`, `id`, or `report_id`. `status_ids` on
   * another kind, missing, repeated, or too many on an action on posts, or naming a post
   * that does not exist, is deleted, or is of another account is a 422 naming `status_ids`;
   * `expires_in` on a `ban` or an action on posts is a 422 naming `expires_in`. A text that
   * is too long or contains U+0000 is a 422 naming it. An unknown account is a 404.
   * A local account whose roles do not rank below the user's, the user's own included, is a
   * 403 `/api/v1/problems/target-outranks-actor`. A `disable_login`, `suspend`, or `ban`
   * that would lock out the only administrator is a 409 `/api/v1/problems/last-administrator`.
   * Needs the `manage_accounts` permission.
   *
   *
   * Parameters:
   * 	 @param createAccountActionRequest 
   * 	 @param id The ID of the account, local or remote.
   * 	 @param idempotencyKey A key the client chooses per request, which makes the request safe to send again. Within
   * 24 hours, a request with a key that the same sign-in of the user in the same client (an
   * anonymous client: the same address) already sent to the operation gets the first answer
   * again instead of being carried out again. The same key with another request (another
   * path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat
   * while the first request is still being carried out is a 409
   * `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx
   * or 5xx the key is free, so a request sent again with it is carried out. A key longer than
   * 256 characters, or one that is not UTF-8 without U+0000, is a 422.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.AdminAccountAction] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun createAccountAction(
    createAccountActionRequest: CreateAccountActionRequest,
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<AdminAccountAction> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/admin/accounts/${id}/actions"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Content-Type", "application/json")
        setBody(createAccountActionRequest)
        `header`("Idempotency-Key", idempotencyKey)
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * Get a moderation action on an account
   * An unknown ID is a 404. Needs the `manage_accounts` permission.
   *
   * Parameters:
   * 	 @param id The ID of the account action.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.AdminAccountAction] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun getAccountAction(id: UUID, apiConfiguration: ApiConfiguration = ApiConfiguration()): NetworkResult<AdminAccountAction> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/admin/account_actions/${id}"""

    return try {
      val response = httpClient.`get`(url) {
        `header`("Accept", "application/json")
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * Lift a moderation action on an account
   * Ends the action's restrictions at once; those of other actions on the account stay.
   * Sessions and tokens that the action ended stay ended, and so do the posts that
   * `delete_statuses` deleted; the posts that `mark_statuses_sensitive` marked are shown as
   * they were. Lifting a lifted action changes nothing and keeps its first reason. A `ban`
   * is never lifted: a 422 naming `id`. A `reason` that is too long or contains U+0000 is a
   * 422 naming `reason`. An unknown ID is a 404. A local account whose roles do not rank below the user's, the user's own included, is a
   * 403 `/api/v1/problems/target-outranks-actor`. Needs the `manage_accounts` permission.
   *
   *
   * Parameters:
   * 	 @param liftAccountActionRequest 
   * 	 @param id The ID of the account action.
   * 	 @param idempotencyKey A key the client chooses per request, which makes the request safe to send again. Within
   * 24 hours, a request with a key that the same sign-in of the user in the same client (an
   * anonymous client: the same address) already sent to the operation gets the first answer
   * again instead of being carried out again. The same key with another request (another
   * path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat
   * while the first request is still being carried out is a 409
   * `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx
   * or 5xx the key is free, so a request sent again with it is carried out. A key longer than
   * 256 characters, or one that is not UTF-8 without U+0000, is a 422.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.AdminAccountAction] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun liftAccountAction(
    liftAccountActionRequest: LiftAccountActionRequest,
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<AdminAccountAction> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/admin/account_actions/${id}/lift"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Content-Type", "application/json")
        setBody(liftAccountActionRequest)
        `header`("Idempotency-Key", idempotencyKey)
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * Count what awaits the staff
   * The number of open reports (with the `manage_reports` permission), of registrations
   * that await approval and appeals that await a decision (with the `manage_accounts`
   * permission), of custom emoji requests that await a decision (with `manage_custom_emojis`), and of
   * open error groups (with `manage_errors`), for the badges of the staff navigation. A count whose
   * permission the user's roles lack is omitted, so a user without any gets an empty object.
   *
   *
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.AdminCounts] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun getAdminCounts(apiConfiguration: ApiConfiguration = ApiConfiguration()): NetworkResult<AdminCounts> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/admin/counts"""

    return try {
      val response = httpClient.`get`(url) {
        `header`("Accept", "application/json")
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * Search the accounts
   * The local and remote accounts, newest first, without the instance actor:
   * those whose handle's username starts with `username` (case-insensitively; a remote
   * account whose handle is not verified has none), on a `domain`, local or remote
   * (`origin`), in a `state`, and, for local accounts, of a primary email address (`email`,
   * case-insensitively), an email domain (`email_domain`), or an IP address that the
   * account registered or signed in from (`ip`). At most one of `email`, `email_domain`,
   * and `ip` is given. The results never include an email or IP address. A search reads at
   * most 400 accounts per page, so a page can be short, or empty, and still have a
   * `next_cursor`. An unknown `origin` or `state`, an `ip` that is not an IP address, an
   * `email_domain` with an `@` inside, more than one of `email`, `email_domain`, and `ip`,
   * or a parameter longer than 320 characters is a 400 naming it. Needs the
   * `manage_accounts` permission.
   *
   *
   * Parameters:
   * 	 @param limit The maximum number of items in the page.
   * 	 @param username Keep the accounts whose username starts with this.
   * 	 @param domain Keep the remote accounts of this domain (its punycode form).
   * 	 @param origin Keep the local or the remote accounts.
   * 	 @param state Keep the accounts in this state.
   * 	 @param email Keep the local account whose primary email address is this.
   * 	 @param emailDomain Keep the local accounts whose primary email address is at this domain
   * (`example.com` or `@example.com`).
   * 	 @param ip Keep the local accounts that registered or signed in from this IP address, within
   * the retention of the sign-in and registration origins.
   * 	 @param cursor The `next_cursor` of the previous page; omitted for the first page.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.AdminAccountPage] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun listAdminAccounts(
    limit: Int? = null,
    username: String? = null,
    domain: String? = null,
    origin: Origin? = null,
    state: AdminAccountState? = null,
    email: String? = null,
    emailDomain: String? = null,
    ip: String? = null,
    cursor: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<AdminAccountPage> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = buildString {
      append(basePath)
      append("""/admin/accounts""")
      val params = buildList {
        limit?.let { add("limit=${it}") }
        username?.let { add("username=${it}") }
        domain?.let { add("domain=${it}") }
        origin?.let { add("origin=${it}") }
        state?.let { add("state=${it}") }
        email?.let { add("email=${it}") }
        emailDomain?.let { add("email_domain=${it}") }
        ip?.let { add("ip=${it}") }
        cursor?.let { add("cursor=${it}") }
      }
      if (params.isNotEmpty()) append("?").append(params.joinToString("&"))
    }

    return try {
      val response = httpClient.`get`(url) {
        `header`("Accept", "application/json")
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * Get the details of an account
   * An account as the moderators see it: its state, the restrictions of its own
   * actions, and, for a local account, its registration, whether its email address is
   * verified, and its roles, with its newest 20 actions and, for a viewer who also has the
   * `manage_reports` permission, its newest 20 reports (`GET /admin/reports?target_account_id=`
   * pages through them all). The email address and the IP
   * addresses are not included; `POST .../reveal` shows them. An unknown account is a 404.
   * Needs the `manage_accounts` permission.
   *
   *
   * Parameters:
   * 	 @param id The ID of the account, local or remote.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.AdminAccountDetail] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun getAdminAccount(id: UUID, apiConfiguration: ApiConfiguration = ApiConfiguration()): NetworkResult<AdminAccountDetail> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/admin/accounts/${id}"""

    return try {
      val response = httpClient.`get`(url) {
        `header`("Accept", "application/json")
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * Show the email address and the IP addresses of a local account
   * The primary email address of a local account, where it registered and its newest 20
   * sign-ins came from, and up to 20 other accounts per IP address that used the same one;
   * `GET .../sign_ins` and `GET .../same_ip_accounts` page through the rest. Each call is recorded in the audit log as `revealed_private_data` before
   * anything is returned; it changes nothing else. IP addresses are kept for the retention
   * of the sign-in and registration origins only. A remote account or the
   * instance actor is a 422 naming `id`. An unknown account is a 404. A local account whose roles do not rank below the
   * user's, the user's own included, is a 403 `/api/v1/problems/target-outranks-actor`. Needs the
   * `manage_accounts` permission.
   *
   *
   * Parameters:
   * 	 @param id The ID of the account, local or remote.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.AdminAccountPrivateData] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun revealAdminAccount(id: UUID, apiConfiguration: ApiConfiguration = ApiConfiguration()): NetworkResult<AdminAccountPrivateData> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/admin/accounts/${id}/reveal"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * List where the sign-ins of a local account came from
   * The sign-ins of a local account within the retention of the sign-in origins, newest first,
   * with their IP addresses: the whole list that `POST .../reveal` starts with its newest 20.
   * Each call is recorded in the audit log as `revealed_private_data` before anything is
   * returned, as the reveal is. A remote account or the instance actor is a 422 naming `id`. An
   * unknown account is a 404. A local account whose roles do not rank below the user's, the
   * user's own included, is a 403 `/api/v1/problems/target-outranks-actor`. Needs the
   * `manage_accounts` permission.
   *
   *
   * Parameters:
   * 	 @param id The ID of the account, local or remote.
   * 	 @param limit The maximum number of items in the page.
   * 	 @param cursor The `next_cursor` of the previous page; omitted for the first page.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.AdminAccountSignInPage] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun listAdminAccountSignIns(
    id: UUID,
    limit: Int? = null,
    cursor: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<AdminAccountSignInPage> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = buildString {
      append(basePath)
      append("""/admin/accounts/${id}/sign_ins""")
      val params = buildList {
        limit?.let { add("limit=${it}") }
        cursor?.let { add("cursor=${it}") }
      }
      if (params.isNotEmpty()) append("?").append(params.joinToString("&"))
    }

    return try {
      val response = httpClient.`get`(url) {
        `header`("Accept", "application/json")
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * List the other accounts that used an IP address of a local account
   * The other local accounts that registered or signed in from `ip`, newest first: the whole
   * list of which `POST .../reveal` shows 20 per IP address. The account itself is left out. A
   * page may be shorter than `limit` and still have a `next_cursor`. Each call is recorded in
   * the audit log as `revealed_private_data` before anything is returned, as the reveal is. An
   * `ip` that is not an IP address is a 400 naming `ip`. A remote account or the instance actor
   * is a 422 naming `id`. An unknown account is a 404. A local account whose roles do not rank
   * below the user's, the user's own included, is a 403
   * `/api/v1/problems/target-outranks-actor`. Needs the `manage_accounts` permission.
   *
   *
   * Parameters:
   * 	 @param id The ID of the account, local or remote.
   * 	 @param ip The IP address, as `sign_ins` or `registration_ip` of the reveal give it.
   * 	 @param limit The maximum number of items in the page.
   * 	 @param cursor The `next_cursor` of the previous page; omitted for the first page.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.AdminAccountPage] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun listAdminAccountSameIPAccounts(
    id: UUID,
    ip: String,
    limit: Int? = null,
    cursor: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<AdminAccountPage> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = buildString {
      append(basePath)
      append("""/admin/accounts/${id}/same_ip_accounts""")
      val params = buildList {
        add("ip=${ip}")
        limit?.let { add("limit=${it}") }
        cursor?.let { add("cursor=${it}") }
      }
      if (params.isNotEmpty()) append("?").append(params.joinToString("&"))
    }

    return try {
      val response = httpClient.`get`(url) {
        `header`("Accept", "application/json")
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * Lift the suppression of the email address of a local account
   * Lets the server send email again to the primary address of a local account's user, which
   * a hard bounce or a complaint suppressed, such as after the user fixed their
   * mailbox. It is recorded in the audit log as `email_suppression_lifted`. A remote account or
   * the instance actor is a 422 naming `id`, an account whose address is not suppressed a 409
   * `/api/v1/problems/email-not-suppressed`, and an unknown account a 404.
   * A local account whose roles do not rank below the user's, the user's own included, is a
   * 403 `/api/v1/problems/target-outranks-actor`. Needs the
   * `manage_accounts` permission.
   *
   *
   * Parameters:
   * 	 @param id The ID of the account, local or remote.
   * 	 @param idempotencyKey A key the client chooses per request, which makes the request safe to send again. Within
   * 24 hours, a request with a key that the same sign-in of the user in the same client (an
   * anonymous client: the same address) already sent to the operation gets the first answer
   * again instead of being carried out again. The same key with another request (another
   * path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat
   * while the first request is still being carried out is a 409
   * `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx
   * or 5xx the key is free, so a request sent again with it is carried out. A key longer than
   * 256 characters, or one that is not UTF-8 without U+0000, is a 422.
   *
   * Returns:
   * 	[NetworkResult.Success] with [kotlin.Unit] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun liftAdminAccountEmailSuppression(
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<Unit> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/admin/accounts/${id}/email_suppression/lift"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Idempotency-Key", idempotencyKey)
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * List the appeals of moderation actions
   * The appeals that local users made of the actions on their accounts, newest
   * first, each with its action. Needs the `manage_accounts` permission.
   *
   *
   * Parameters:
   * 	 @param limit The maximum number of items in the page.
   * 	 @param state Keep only the appeals in this state; omitted keeps every state.
   * 	 @param cursor The `next_cursor` of the previous page; omitted for the first page.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.AdminAppealPage] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun listAppeals(
    limit: Int? = null,
    state: ModerationAppealState? = null,
    cursor: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<AdminAppealPage> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = buildString {
      append(basePath)
      append("""/admin/appeals""")
      val params = buildList {
        limit?.let { add("limit=${it}") }
        state?.let { add("state=${it}") }
        cursor?.let { add("cursor=${it}") }
      }
      if (params.isNotEmpty()) append("?").append(params.joinToString("&"))
    }

    return try {
      val response = httpClient.`get`(url) {
        `header`("Accept", "application/json")
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * Get an appeal of a moderation action
   * An unknown ID is a 404. Needs the `manage_accounts` permission.
   *
   * Parameters:
   * 	 @param id The ID of the appeal.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.AdminAppeal] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun getAppeal(id: UUID, apiConfiguration: ApiConfiguration = ApiConfiguration()): NetworkResult<AdminAppeal> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/admin/appeals/${id}"""

    return try {
      val response = httpClient.`get`(url) {
        `header`("Accept", "application/json")
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * Overturn an appeal, lifting its action
   * Grants the appeal and lifts its action at once, as `POST /admin/account_actions/{id}/lift`
   * does: the posts that `delete_statuses` deleted stay deleted. An action lifted meanwhile
   * stays as it is. Overturning the appeal of a `ban`, which nothing else lifts, also
   * cancels the scheduled deletion of the account; a `ban` whose account is deleted is a
   * 422 `/api/v1/problems/not-appealable`. The user who appealed gets an `appeal_decided` notification with
   * `decision_reason`. Deciding a decided appeal the same way again changes nothing and
   * keeps the first reason; deciding it the other way is a 409
   * `/api/v1/problems/appeal-decided`. A `public_reason` that is too long or contains
   * U+0000 is a 422 naming `public_reason`. An unknown ID is a 404.
   * A local account whose roles do not rank below the user's, the user's own included, is a
   * 403 `/api/v1/problems/target-outranks-actor`. Needs the
   * `manage_accounts` permission.
   *
   *
   * Parameters:
   * 	 @param decideAppealRequest 
   * 	 @param id The ID of the appeal.
   * 	 @param idempotencyKey A key the client chooses per request, which makes the request safe to send again. Within
   * 24 hours, a request with a key that the same sign-in of the user in the same client (an
   * anonymous client: the same address) already sent to the operation gets the first answer
   * again instead of being carried out again. The same key with another request (another
   * path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat
   * while the first request is still being carried out is a 409
   * `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx
   * or 5xx the key is free, so a request sent again with it is carried out. A key longer than
   * 256 characters, or one that is not UTF-8 without U+0000, is a 422.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.AdminAppeal] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun overturnAppeal(
    decideAppealRequest: DecideAppealRequest,
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<AdminAppeal> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/admin/appeals/${id}/overturn"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Content-Type", "application/json")
        setBody(decideAppealRequest)
        `header`("Idempotency-Key", idempotencyKey)
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * Uphold an appeal, keeping its action
   * Rejects the appeal; the action stays as it is. The user who appealed gets an `appeal_decided` notification with
   * `decision_reason`. Deciding a decided appeal the same way again changes nothing and
   * keeps the first reason; deciding it the other way is a 409
   * `/api/v1/problems/appeal-decided`. A `public_reason` that is too long or contains
   * U+0000 is a 422 naming `public_reason`. An unknown ID is a 404.
   * A local account whose roles do not rank below the user's, the user's own included, is a
   * 403 `/api/v1/problems/target-outranks-actor`. Needs the
   * `manage_accounts` permission.
   *
   *
   * Parameters:
   * 	 @param decideAppealRequest 
   * 	 @param id The ID of the appeal.
   * 	 @param idempotencyKey A key the client chooses per request, which makes the request safe to send again. Within
   * 24 hours, a request with a key that the same sign-in of the user in the same client (an
   * anonymous client: the same address) already sent to the operation gets the first answer
   * again instead of being carried out again. The same key with another request (another
   * path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat
   * while the first request is still being carried out is a 409
   * `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx
   * or 5xx the key is free, so a request sent again with it is carried out. A key longer than
   * 256 characters, or one that is not UTF-8 without U+0000, is a 422.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.AdminAppeal] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun upholdAppeal(
    decideAppealRequest: DecideAppealRequest,
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<AdminAppeal> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/admin/appeals/${id}/uphold"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Content-Type", "application/json")
        setBody(decideAppealRequest)
        `header`("Idempotency-Key", idempotencyKey)
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * List the registrations that await approval
   * The sign-ups that wait for a moderator's decision, with the reasons their
   * applicants gave, longest waiting first. Needs the `manage_accounts` permission.
   *
   * On a one-person server (`Instance.single_user`) it is a 404 of the type
   * `/api/v1/problems/single-user-mode`.
   *
   *
   * Parameters:
   * 	 @param limit The maximum number of items in the page.
   * 	 @param cursor The `next_cursor` of the previous page; omitted for the first page.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.AdminRegistrationPage] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun listPendingRegistrations(
    limit: Int? = null,
    cursor: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<AdminRegistrationPage> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = buildString {
      append(basePath)
      append("""/admin/registrations""")
      val params = buildList {
        limit?.let { add("limit=${it}") }
        cursor?.let { add("cursor=${it}") }
      }
      if (params.isNotEmpty()) append("?").append(params.joinToString("&"))
    }

    return try {
      val response = httpClient.`get`(url) {
        `header`("Accept", "application/json")
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * Approve a registration
   * Approves a pending registration, so that its user can sign in, with an optional note
   * for the moderators; the decision is audited. Deciding a decided registration changes
   * nothing and keeps its decision. The answer on a decided registration has no email address:
   * only the list of pending registrations shows it, and `POST /admin/accounts/{id}/reveal` after. An unknown ID is a 404. From the
   * announcement of the closure of the service on, a pending registration is not approved: a
   * 409 `/api/v1/problems/service-closing` (the announcement rejects the pending ones). Needs
   * the `manage_accounts` permission.
   *
   * On a one-person server (`Instance.single_user`) it is a 404 of the type
   * `/api/v1/problems/single-user-mode`.
   *
   *
   * Parameters:
   * 	 @param registrationDecisionRequest 
   * 	 @param id The ID of the registration.
   * 	 @param idempotencyKey A key the client chooses per request, which makes the request safe to send again. Within
   * 24 hours, a request with a key that the same sign-in of the user in the same client (an
   * anonymous client: the same address) already sent to the operation gets the first answer
   * again instead of being carried out again. The same key with another request (another
   * path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat
   * while the first request is still being carried out is a 409
   * `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx
   * or 5xx the key is free, so a request sent again with it is carried out. A key longer than
   * 256 characters, or one that is not UTF-8 without U+0000, is a 422.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.AdminRegistration] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun approveRegistration(
    registrationDecisionRequest: RegistrationDecisionRequest,
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<AdminRegistration> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/admin/registrations/${id}/approve"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Content-Type", "application/json")
        setBody(registrationDecisionRequest)
        `header`("Idempotency-Key", idempotencyKey)
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * Reject a registration
   * Rejects a pending registration, so that its user cannot sign in, with an optional note
   * for the moderators; the decision is audited. Deciding a decided registration changes
   * nothing and keeps its decision. The answer on a decided registration has no email address:
   * only the list of pending registrations shows it, and `POST /admin/accounts/{id}/reveal` after.
   * An unknown ID is a 404. Needs the
   * `manage_accounts` permission.
   *
   * On a one-person server (`Instance.single_user`) it is a 404 of the type
   * `/api/v1/problems/single-user-mode`.
   *
   *
   * Parameters:
   * 	 @param registrationDecisionRequest 
   * 	 @param id The ID of the registration.
   * 	 @param idempotencyKey A key the client chooses per request, which makes the request safe to send again. Within
   * 24 hours, a request with a key that the same sign-in of the user in the same client (an
   * anonymous client: the same address) already sent to the operation gets the first answer
   * again instead of being carried out again. The same key with another request (another
   * path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat
   * while the first request is still being carried out is a 409
   * `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx
   * or 5xx the key is free, so a request sent again with it is carried out. A key longer than
   * 256 characters, or one that is not UTF-8 without U+0000, is a 422.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.AdminRegistration] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun rejectRegistration(
    registrationDecisionRequest: RegistrationDecisionRequest,
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<AdminRegistration> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/admin/registrations/${id}/reject"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Content-Type", "application/json")
        setBody(registrationDecisionRequest)
        `header`("Idempotency-Key", idempotencyKey)
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * List the sign-up blocks
   * The blocks on sign-ups from the registration page, newest first, lifted and
   * expired ones included unless `active` is set. Needs the `manage_accounts` permission.
   *
   * On a one-person server (`Instance.single_user`) it is a 404 of the type
   * `/api/v1/problems/single-user-mode`.
   *
   *
   * Parameters:
   * 	 @param limit The maximum number of items in the page.
   * 	 @param kind Keep only the blocks of this kind.
   * 	 @param active Keep only the blocks in effect now, neither lifted nor expired.
   * 	 @param cursor The `next_cursor` of the previous page; omitted for the first page.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.AdminSignupBlockPage] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun listSignupBlocks(
    limit: Int? = null,
    kind: AdminSignupBlockKind? = null,
    active: Boolean? = null,
    cursor: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<AdminSignupBlockPage> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = buildString {
      append(basePath)
      append("""/admin/signup_blocks""")
      val params = buildList {
        limit?.let { add("limit=${it}") }
        kind?.let { add("kind=${it}") }
        active?.let { add("active=${it}") }
        cursor?.let { add("cursor=${it}") }
      }
      if (params.isNotEmpty()) append("?").append(params.joinToString("&"))
    }

    return try {
      val response = httpClient.`get`(url) {
        `header`("Accept", "application/json")
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * Block sign-ups
   * Records a block on sign-ups from the registration page, in effect at once and
   * until it expires or is lifted; the block is audited. A sign-up whose email address or
   * client address it matches is refused (`reject`) or starts pending for a moderator
   * whatever the registration mode (`require_approval`); the page does not tell the
   * applicant why. The value is stored in its canonical form: an `email_domain` in IDNA
   * A-labels (it covers the subdomains), an `email_hash` as the hash of the canonical
   * address (an email address given is hashed and not kept), an `ip_range` as a masked
   * CIDR prefix (a single address is a prefix of its full length). A value that is not
   * valid for its kind, or a `comment` that is too long or contains U+0000, is a 422.
   * Needs the `manage_accounts` permission.
   *
   * On a one-person server (`Instance.single_user`) it is a 404 of the type
   * `/api/v1/problems/single-user-mode`.
   *
   *
   * Parameters:
   * 	 @param createSignupBlockRequest 
   * 	 @param idempotencyKey A key the client chooses per request, which makes the request safe to send again. Within
   * 24 hours, a request with a key that the same sign-in of the user in the same client (an
   * anonymous client: the same address) already sent to the operation gets the first answer
   * again instead of being carried out again. The same key with another request (another
   * path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat
   * while the first request is still being carried out is a 409
   * `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx
   * or 5xx the key is free, so a request sent again with it is carried out. A key longer than
   * 256 characters, or one that is not UTF-8 without U+0000, is a 422.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.AdminSignupBlock] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun createSignupBlock(
    createSignupBlockRequest: CreateSignupBlockRequest,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<AdminSignupBlock> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/admin/signup_blocks"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Content-Type", "application/json")
        setBody(createSignupBlockRequest)
        `header`("Idempotency-Key", idempotencyKey)
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * Get a sign-up block
   * An unknown ID is a 404. Needs the `manage_accounts` permission.
   *
   * On a one-person server (`Instance.single_user`) it is a 404 of the type
   * `/api/v1/problems/single-user-mode`.
   *
   *
   * Parameters:
   * 	 @param id The ID of the sign-up block.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.AdminSignupBlock] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun getSignupBlock(id: UUID, apiConfiguration: ApiConfiguration = ApiConfiguration()): NetworkResult<AdminSignupBlock> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/admin/signup_blocks/${id}"""

    return try {
      val response = httpClient.`get`(url) {
        `header`("Accept", "application/json")
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * Lift a sign-up block
   * The block stops matching sign-ups at once; the lift is audited. Lifting a lifted block
   * changes nothing. An unknown ID is a 404. Needs the `manage_accounts` permission.
   *
   * On a one-person server (`Instance.single_user`) it is a 404 of the type
   * `/api/v1/problems/single-user-mode`.
   *
   *
   * Parameters:
   * 	 @param id The ID of the sign-up block.
   * 	 @param idempotencyKey A key the client chooses per request, which makes the request safe to send again. Within
   * 24 hours, a request with a key that the same sign-in of the user in the same client (an
   * anonymous client: the same address) already sent to the operation gets the first answer
   * again instead of being carried out again. The same key with another request (another
   * path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat
   * while the first request is still being carried out is a 409
   * `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx
   * or 5xx the key is free, so a request sent again with it is carried out. A key longer than
   * 256 characters, or one that is not UTF-8 without U+0000, is a 422.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.AdminSignupBlock] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun liftSignupBlock(
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<AdminSignupBlock> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/admin/signup_blocks/${id}/lift"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Idempotency-Key", idempotencyKey)
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * Block sign-ups from a list
   * Adds a block on sign-ups for each value of a list, such as a public list of
   * disposable email domains, all with the same kind, severity, comment, and duration. The
   * list has one value per line in any form that `createSignupBlock` takes, or a CSV row
   * whose first field is the value; blank lines and lines starting with `#` are ignored.
   * It adds what it can and reports the lines it skipped: a value that is not valid for the
   * kind (`invalid`), that repeats an earlier line once in canonical form (`duplicate`), or
   * that a block in effect of the same kind and value with a severity at least as strong
   * already covers (`already_blocked`). Each block added is audited like one added on its
   * own. The blocks are written a few hundred at a time, so an error part way keeps the
   * ones before it; sending the list again skips them as `already_blocked`. More than
   * 10,000 values, an invalid kind or severity, or a `comment` that is too long or contains
   * U+0000 is a 422 that adds nothing. Needs the `manage_accounts` permission.
   *
   * On a one-person server (`Instance.single_user`) it is a 404 of the type
   * `/api/v1/problems/single-user-mode`.
   *
   *
   * Parameters:
   * 	 @param importSignupBlocksRequest 
   * 	 @param idempotencyKey A key the client chooses per request, which makes the request safe to send again. Within
   * 24 hours, a request with a key that the same sign-in of the user in the same client (an
   * anonymous client: the same address) already sent to the operation gets the first answer
   * again instead of being carried out again. The same key with another request (another
   * path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat
   * while the first request is still being carried out is a 409
   * `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx
   * or 5xx the key is free, so a request sent again with it is carried out. A key longer than
   * 256 characters, or one that is not UTF-8 without U+0000, is a 422.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.SignupBlockImportResult] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun importSignupBlocks(
    importSignupBlocksRequest: ImportSignupBlocksRequest,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<SignupBlockImportResult> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/admin/signup_blocks/import"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Content-Type", "application/json")
        setBody(importSignupBlocksRequest)
        `header`("Idempotency-Key", idempotencyKey)
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * Count what suspending a domain would end
   * How many follows a `suspend` of `domain` would end now, and how many local accounts would
   * lose one. Moderators read it before they confirm a suspension, which ends those follows for
   * good. The domain is normalized like the domain of a new action; one that is not a valid
   * domain, or this server's own, is a 422 naming `domain`. A domain with no follows counts
   * zeros. Needs the `manage_domains` permission.
   *
   *
   * Parameters:
   * 	 @param domain The domain to count for; its subdomains are counted with it.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.DomainSuspendImpact] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun getDomainSuspendImpact(domain: String, apiConfiguration: ApiConfiguration = ApiConfiguration()): NetworkResult<DomainSuspendImpact> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = buildString {
      append(basePath)
      append("""/admin/domain_actions/impact""")
      val params = buildList {
        add("domain=${domain}")
      }
      if (params.isNotEmpty()) append("?").append(params.joinToString("&"))
    }

    return try {
      val response = httpClient.`get`(url) {
        `header`("Accept", "application/json")
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * Export the domain actions as Mastodon's domain block CSV
   * The domain actions in effect as the CSV that Mastodon's moderation screens
   * import, with the header
   * `#domain,#severity,#reject_media,#reject_reports,#public_comment,#obfuscate`: one row
   * per domain in domain order. `#severity` is `suspend` when a `suspend` is in effect on
   * the domain, `silence` for a `limit`, and `noop` otherwise; `#reject_media`,
   * `#reject_reports`, and `#obfuscate` are `true` when one of the domain's actions has
   * them. A domain whose only actions are `force_sensitive`, which Mastodon's domain blocks
   * cannot hold, is left out. Private notes and expiry dates are not exported. Needs the
   * `manage_domains` permission.
   *
   *
   *
   * Returns:
   * 	[NetworkResult.Success] with [kotlin.ByteArray] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun exportDomainBlocks(apiConfiguration: ApiConfiguration = ApiConfiguration()): NetworkResult<ByteArray> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/admin/domain_actions/export"""

    return try {
      val response = httpClient.`get`(url) {
        `header`("Accept", "application/json")
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * Preview the import of a Mastodon domain block CSV
   * Reads a domain block CSV that Mastodon exported and says what importing each
   * row would do, without changing anything. A file whose first field is `#domain` has a
   * header naming the columns; any other file is a list of domains, one per row, each
   * suspended. An empty `#severity` is `suspend`, and a boolean is false when empty, `0`,
   * `f`, `false`, or `off`. A row of `suspend` issues a `suspend`, `silence` a `limit`, and
   * `noop` nothing, plus `reject_media` and `reject_reports` when set. A file that is not
   * CSV or has more than 5,000 rows is a 422 naming `csv`. Needs the `manage_domains`
   * permission.
   *
   *
   * Parameters:
   * 	 @param previewDomainBlockImportRequest 
   * 	 @param idempotencyKey A key the client chooses per request, which makes the request safe to send again. Within
   * 24 hours, a request with a key that the same sign-in of the user in the same client (an
   * anonymous client: the same address) already sent to the operation gets the first answer
   * again instead of being carried out again. The same key with another request (another
   * path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat
   * while the first request is still being carried out is a 409
   * `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx
   * or 5xx the key is free, so a request sent again with it is carried out. A key longer than
   * 256 characters, or one that is not UTF-8 without U+0000, is a 422.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.DomainBlockImportPreview] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun previewDomainBlockImport(
    previewDomainBlockImportRequest: PreviewDomainBlockImportRequest,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<DomainBlockImportPreview> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/admin/domain_actions/import/preview"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Content-Type", "application/json")
        setBody(previewDomainBlockImportRequest)
        `header`("Idempotency-Key", idempotencyKey)
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * Import the chosen rows of a Mastodon domain block CSV
   * Issues the domain actions of each block, as the preview names them: the kinds
   * of the block that no action in effect on the domain or a parent domain has already. The
   * actions last until they are lifted, carry the block's public comment and obfuscation
   * and the request's private note, and are audited like actions issued one at a time. A
   * block that would issue nothing is skipped with the reason. The actions are written a
   * few dozen at a time, so an error part way keeps the ones before it; sending the blocks
   * again skips them as `already_applied`. More than 5,000 blocks or a `private_note` that
   * is too long or contains U+0000 is a 422 that issues nothing. Needs the `manage_domains`
   * permission.
   *
   *
   * Parameters:
   * 	 @param importDomainBlocksRequest 
   * 	 @param idempotencyKey A key the client chooses per request, which makes the request safe to send again. Within
   * 24 hours, a request with a key that the same sign-in of the user in the same client (an
   * anonymous client: the same address) already sent to the operation gets the first answer
   * again instead of being carried out again. The same key with another request (another
   * path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat
   * while the first request is still being carried out is a 409
   * `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx
   * or 5xx the key is free, so a request sent again with it is carried out. A key longer than
   * 256 characters, or one that is not UTF-8 without U+0000, is a 422.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.DomainBlockImportResult] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun importDomainBlocks(
    importDomainBlocksRequest: ImportDomainBlocksRequest,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<DomainBlockImportResult> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/admin/domain_actions/import"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Content-Type", "application/json")
        setBody(importDomainBlocksRequest)
        `header`("Idempotency-Key", idempotencyKey)
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * List the subscriptions to domain block lists
   * The subscriptions that are not deleted, newest first, each with its last fetch. Needs the `manage_domains` permission.
   *
   * Parameters:
   * 	 @param limit The maximum number of items in the page.
   * 	 @param cursor The `next_cursor` of the previous page; omitted for the first page.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.AdminDomainListSubscriptionPage] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun listDomainListSubscriptions(
    limit: Int? = null,
    cursor: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<AdminDomainListSubscriptionPage> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = buildString {
      append(basePath)
      append("""/admin/domain_list_subscriptions""")
      val params = buildList {
        limit?.let { add("limit=${it}") }
        cursor?.let { add("cursor=${it}") }
      }
      if (params.isNotEmpty()) append("?").append(params.joinToString("&"))
    }

    return try {
      val response = httpClient.`get`(url) {
        `header`("Accept", "application/json")
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * Subscribe to a domain block list
   * Subscribes to the list at an https URL, which the server fetches at once and then every
   * day, through the same guard as the fetches of other servers: a URL or a redirect to a
   * private address fails the fetch. A fetch reads at most 2 MiB and 5000 rows. In the
   * `draft` mode, what the list adds, changes, and removes waits for a moderator; in the
   * `automatic` mode, it is applied at each fetch. A subscription never lifts nor replaces an
   * action that it did not issue. A URL that is not https, or an invalid setting, is a 422.
   * The subscription is audited. Needs the `manage_domains` permission.
   *
   *
   * Parameters:
   * 	 @param createDomainListSubscriptionRequest 
   * 	 @param idempotencyKey A key the client chooses per request, which makes the request safe to send again. Within
   * 24 hours, a request with a key that the same sign-in of the user in the same client (an
   * anonymous client: the same address) already sent to the operation gets the first answer
   * again instead of being carried out again. The same key with another request (another
   * path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat
   * while the first request is still being carried out is a 409
   * `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx
   * or 5xx the key is free, so a request sent again with it is carried out. A key longer than
   * 256 characters, or one that is not UTF-8 without U+0000, is a 422.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.AdminDomainListSubscription] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun createDomainListSubscription(
    createDomainListSubscriptionRequest: CreateDomainListSubscriptionRequest,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<AdminDomainListSubscription> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/admin/domain_list_subscriptions"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Content-Type", "application/json")
        setBody(createDomainListSubscriptionRequest)
        `header`("Idempotency-Key", idempotencyKey)
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * Get a subscription to a domain block list
   * An unknown or deleted ID is a 404. Needs the `manage_domains` permission.
   *
   * Parameters:
   * 	 @param id The ID of the subscription to a domain block list.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.AdminDomainListSubscription] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun getDomainListSubscription(id: UUID, apiConfiguration: ApiConfiguration = ApiConfiguration()): NetworkResult<AdminDomainListSubscription> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/admin/domain_list_subscriptions/${id}"""

    return try {
      val response = httpClient.`get`(url) {
        `header`("Accept", "application/json")
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * Change the settings of a subscription
   * Changes the settings the request names and keeps the rest, and queues a fetch that applies
   * them; the URL and the format do not change. An invalid setting is a 422. A change is
   * audited. An unknown or deleted ID is a 404. Needs the `manage_domains` permission.
   *
   *
   * Parameters:
   * 	 @param updateDomainListSubscriptionRequest 
   * 	 @param id The ID of the subscription to a domain block list.
   * 	 @param idempotencyKey A key the client chooses per request, which makes the request safe to send again. Within
   * 24 hours, a request with a key that the same sign-in of the user in the same client (an
   * anonymous client: the same address) already sent to the operation gets the first answer
   * again instead of being carried out again. The same key with another request (another
   * path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat
   * while the first request is still being carried out is a 409
   * `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx
   * or 5xx the key is free, so a request sent again with it is carried out. A key longer than
   * 256 characters, or one that is not UTF-8 without U+0000, is a 422.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.AdminDomainListSubscription] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun updateDomainListSubscription(
    updateDomainListSubscriptionRequest: UpdateDomainListSubscriptionRequest,
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<AdminDomainListSubscription> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/admin/domain_list_subscriptions/${id}"""

    return try {
      val response = httpClient.patch(url) {
        `header`("Accept", "application/json")
        `header`("Content-Type", "application/json")
        setBody(updateDomainListSubscriptionRequest)
        `header`("Idempotency-Key", idempotencyKey)
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * Delete a subscription to a domain block list
   * Deletes the subscription: the list is no longer fetched. The actions it issued stay, as
   * if a moderator had issued them, unless `lift_actions` is set, which lifts them first. The
   * deletion is audited. An unknown or deleted ID is a 404. Needs the `manage_domains`
   * permission.
   *
   *
   * Parameters:
   * 	 @param id The ID of the subscription to a domain block list.
   * 	 @param liftActions Lift the actions the subscription issued.
   * 	 @param idempotencyKey A key the client chooses per request, which makes the request safe to send again. Within
   * 24 hours, a request with a key that the same sign-in of the user in the same client (an
   * anonymous client: the same address) already sent to the operation gets the first answer
   * again instead of being carried out again. The same key with another request (another
   * path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat
   * while the first request is still being carried out is a 409
   * `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx
   * or 5xx the key is free, so a request sent again with it is carried out. A key longer than
   * 256 characters, or one that is not UTF-8 without U+0000, is a 422.
   *
   * Returns:
   * 	[NetworkResult.Success] with [kotlin.Unit] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun deleteDomainListSubscription(
    id: UUID,
    liftActions: Boolean? = null,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<Unit> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = buildString {
      append(basePath)
      append("""/admin/domain_list_subscriptions/${id}""")
      val params = buildList {
        liftActions?.let { add("lift_actions=${it}") }
      }
      if (params.isNotEmpty()) append("?").append(params.joinToString("&"))
    }

    return try {
      val response = httpClient.delete(url) {
        `header`("Accept", "application/json")
        `header`("Idempotency-Key", idempotencyKey)
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * Fetch a subscribed list now
   * Queues a fetch of the list, which then applies it as the daily fetch does. The request is
   * audited. An unknown or deleted ID is a 404. Needs the `manage_domains` permission.
   *
   *
   * Parameters:
   * 	 @param id The ID of the subscription to a domain block list.
   * 	 @param idempotencyKey A key the client chooses per request, which makes the request safe to send again. Within
   * 24 hours, a request with a key that the same sign-in of the user in the same client (an
   * anonymous client: the same address) already sent to the operation gets the first answer
   * again instead of being carried out again. The same key with another request (another
   * path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat
   * while the first request is still being carried out is a 409
   * `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx
   * or 5xx the key is free, so a request sent again with it is carried out. A key longer than
   * 256 characters, or one that is not UTF-8 without U+0000, is a 422.
   *
   * Returns:
   * 	[NetworkResult.Success] with [kotlin.Unit] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun fetchDomainListSubscription(
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<Unit> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/admin/domain_list_subscriptions/${id}/fetch"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Idempotency-Key", idempotencyKey)
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * List the fetches of a subscription
   * The fetches of the list, newest first, failed ones included with why they failed. An
   * unknown or deleted ID is a 404. Needs the `manage_domains` permission.
   *
   *
   * Parameters:
   * 	 @param id The ID of the subscription to a domain block list.
   * 	 @param limit The maximum number of items in the page.
   * 	 @param cursor The `next_cursor` of the previous page; omitted for the first page.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.AdminDomainListFetchPage] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun listDomainListFetches(
    id: UUID,
    limit: Int? = null,
    cursor: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<AdminDomainListFetchPage> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = buildString {
      append(basePath)
      append("""/admin/domain_list_subscriptions/${id}/fetches""")
      val params = buildList {
        limit?.let { add("limit=${it}") }
        cursor?.let { add("cursor=${it}") }
      }
      if (params.isNotEmpty()) append("?").append(params.joinToString("&"))
    }

    return try {
      val response = httpClient.`get`(url) {
        `header`("Accept", "application/json")
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * List the drafts of a subscription
   * The differences between the list, as last read, and the actions the subscription issued
   * that wait for a moderator: every difference in the `draft` mode, and in the `automatic`
   * mode those of an unknown severity and the lifts of a fetch that would lift too many. A
   * domain that an action in effect covers already, on it or a parent domain, whoever issued
   * it, is no draft. Rejected drafts are not listed. An unknown or deleted ID is a 404. Needs
   * the `manage_domains` permission.
   *
   *
   * Parameters:
   * 	 @param id The ID of the subscription to a domain block list.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.AdminDomainListDraftList] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun listDomainListDrafts(id: UUID, apiConfiguration: ApiConfiguration = ApiConfiguration()): NetworkResult<AdminDomainListDraftList> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/admin/domain_list_subscriptions/${id}/drafts"""

    return try {
      val response = httpClient.`get`(url) {
        `header`("Accept", "application/json")
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * Accept drafts of a subscription
   * Issues the action of each `issue` draft for the subscription, until it is lifted, and
   * lifts the action of each `lift` draft. Each acceptance is audited, besides the action. A
   * draft that an action in effect covers by then issues nothing. An unknown or deleted ID is
   * a 404. Needs the `manage_domains` permission.
   *
   *
   * Parameters:
   * 	 @param domainListDraftsRequest 
   * 	 @param id The ID of the subscription to a domain block list.
   * 	 @param idempotencyKey A key the client chooses per request, which makes the request safe to send again. Within
   * 24 hours, a request with a key that the same sign-in of the user in the same client (an
   * anonymous client: the same address) already sent to the operation gets the first answer
   * again instead of being carried out again. The same key with another request (another
   * path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat
   * while the first request is still being carried out is a 409
   * `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx
   * or 5xx the key is free, so a request sent again with it is carried out. A key longer than
   * 256 characters, or one that is not UTF-8 without U+0000, is a 422.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.AdminDomainListDraftResult] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun acceptDomainListDrafts(
    domainListDraftsRequest: DomainListDraftsRequest,
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<AdminDomainListDraftResult> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/admin/domain_list_subscriptions/${id}/drafts/accept"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Content-Type", "application/json")
        setBody(domainListDraftsRequest)
        `header`("Idempotency-Key", idempotencyKey)
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * Reject drafts of a subscription
   * Rejects the drafts: a rejected `issue` does not come back until the list changes the row
   * of its domain, and a rejected `lift` while its action is in effect. Each rejection is
   * audited. An unknown or deleted ID is a 404. Needs the `manage_domains` permission.
   *
   *
   * Parameters:
   * 	 @param domainListDraftsRequest 
   * 	 @param id The ID of the subscription to a domain block list.
   * 	 @param idempotencyKey A key the client chooses per request, which makes the request safe to send again. Within
   * 24 hours, a request with a key that the same sign-in of the user in the same client (an
   * anonymous client: the same address) already sent to the operation gets the first answer
   * again instead of being carried out again. The same key with another request (another
   * path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat
   * while the first request is still being carried out is a 409
   * `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx
   * or 5xx the key is free, so a request sent again with it is carried out. A key longer than
   * 256 characters, or one that is not UTF-8 without U+0000, is a 422.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.AdminDomainListDraftResult] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun rejectDomainListDrafts(
    domainListDraftsRequest: DomainListDraftsRequest,
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<AdminDomainListDraftResult> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/admin/domain_list_subscriptions/${id}/drafts/reject"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Content-Type", "application/json")
        setBody(domainListDraftsRequest)
        `header`("Idempotency-Key", idempotencyKey)
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * List the allowlist of federation
   * Every allowed domain, by domain. The list is not paginated; the allowlist holds at most
   * 5,000 domains. Needs the `manage_domains` permission.
   *
   *
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.DomainAllowList] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun listDomainAllows(apiConfiguration: ApiConfiguration = ApiConfiguration()): NetworkResult<DomainAllowList> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/admin/domain_allows"""

    return try {
      val response = httpClient.`get`(url) {
        `header`("Accept", "application/json")
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * Allow a domain to federate
   * Adds a domain, and its subdomains, to the allowlist and records it in the audit log. While
   * the instance setting `federation_scope` is `allowlist`, the domain federates at once; the
   * follows that the allowlist ended do not come back. A domain already allowed keeps its allow
   * and its comment, and answers 200 instead of 201. A domain that is not a host is a 400; this
   * instance's own domain, a comment longer than 5,000 characters, or an allow beyond 5,000
   * is a 422 naming the property (`domain`, `comment`, `domains`). Needs the `manage_domains`
   * permission.
   *
   *
   * Parameters:
   * 	 @param createDomainAllowRequest 
   * 	 @param idempotencyKey A key the client chooses per request, which makes the request safe to send again. Within
   * 24 hours, a request with a key that the same sign-in of the user in the same client (an
   * anonymous client: the same address) already sent to the operation gets the first answer
   * again instead of being carried out again. The same key with another request (another
   * path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat
   * while the first request is still being carried out is a 409
   * `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx
   * or 5xx the key is free, so a request sent again with it is carried out. A key longer than
   * 256 characters, or one that is not UTF-8 without U+0000, is a 422.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.DomainAllow] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun createDomainAllow(
    createDomainAllowRequest: CreateDomainAllowRequest,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<DomainAllow> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/admin/domain_allows"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Content-Type", "application/json")
        setBody(createDomainAllowRequest)
        `header`("Idempotency-Key", idempotencyKey)
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * Remove a domain from the allowlist of federation
   * Removes the allow of a domain and records it in the audit log. While the instance setting
   * `federation_scope` is `allowlist` and no parent domain is allowed, the domain and its
   * subdomains that no other allow covers are cut off at once, as a suspension of the domain
   * cuts them off: nothing is received from, delivered to, or fetched from them, and the follows
   * between the local accounts and their accounts end for good, each local user who loses one
   * being notified. A domain without an allow changes nothing. Needs the `manage_domains`
   * permission.
   *
   *
   * Parameters:
   * 	 @param domain The canonical domain of the allow.
   * 	 @param idempotencyKey A key the client chooses per request, which makes the request safe to send again. Within
   * 24 hours, a request with a key that the same sign-in of the user in the same client (an
   * anonymous client: the same address) already sent to the operation gets the first answer
   * again instead of being carried out again. The same key with another request (another
   * path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat
   * while the first request is still being carried out is a 409
   * `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx
   * or 5xx the key is free, so a request sent again with it is carried out. A key longer than
   * 256 characters, or one that is not UTF-8 without U+0000, is a 422.
   *
   * Returns:
   * 	[NetworkResult.Success] with [kotlin.Unit] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun removeDomainAllow(
    domain: String,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<Unit> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/admin/domain_allows/${domain}"""

    return try {
      val response = httpClient.delete(url) {
        `header`("Accept", "application/json")
        `header`("Idempotency-Key", idempotencyKey)
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * List the invites
   * The invites that let people sign up while the registration mode is `open`, `approval`,
   * or `invite`, newest first, with how they have been used and who
   * created them: the staff's and the members' own invites. Needs the `manage_invites`
   * permission.
   *
   * On a one-person server (`Instance.single_user`) it is a 404 of the type
   * `/api/v1/problems/single-user-mode`.
   *
   *
   * Parameters:
   * 	 @param limit The maximum number of items in the page.
   * 	 @param cursor The `next_cursor` of the previous page; omitted for the first page.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.AdminInvitePage] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun listInvites(
    limit: Int? = null,
    cursor: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<AdminInvitePage> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = buildString {
      append(basePath)
      append("""/admin/invites""")
      val params = buildList {
        limit?.let { add("limit=${it}") }
        cursor?.let { add("cursor=${it}") }
      }
      if (params.isNotEmpty()) append("?").append(params.joinToString("&"))
    }

    return try {
      val response = httpClient.`get`(url) {
        `header`("Accept", "application/json")
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * Create an invite
   * Creates an invite of the staff with a new random code; the caps of the members' invites
   * do not apply to it. People sign up with it while the registration mode is `open`,
   * `approval` (approved at once), or `invite`; while the mode is `closed`, creating one is a
   * 409 `/api/v1/problems/registration-closed`. `max_uses` outside 1 to 1,000,000 or
   * `expires_in` outside 1 second to 10 years is a 422 naming it. Needs the `manage_invites`
   * permission.
   *
   * On a one-person server (`Instance.single_user`) it is a 404 of the type
   * `/api/v1/problems/single-user-mode`.
   *
   *
   * Parameters:
   * 	 @param createInviteRequest 
   * 	 @param idempotencyKey A key the client chooses per request, which makes the request safe to send again. Within
   * 24 hours, a request with a key that the same sign-in of the user in the same client (an
   * anonymous client: the same address) already sent to the operation gets the first answer
   * again instead of being carried out again. The same key with another request (another
   * path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat
   * while the first request is still being carried out is a 409
   * `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx
   * or 5xx the key is free, so a request sent again with it is carried out. A key longer than
   * 256 characters, or one that is not UTF-8 without U+0000, is a 422.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.AdminInvite] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun createInvite(
    createInviteRequest: CreateInviteRequest,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<AdminInvite> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/admin/invites"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Content-Type", "application/json")
        setBody(createInviteRequest)
        `header`("Idempotency-Key", idempotencyKey)
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * Revoke an invite
   * The invite stops working at once; the people who signed up with it keep their
   * accounts. Revoking a revoked invite changes nothing. An unknown ID is a 404. Needs the
   * `manage_invites` permission.
   *
   * On a one-person server (`Instance.single_user`) it is a 404 of the type
   * `/api/v1/problems/single-user-mode`.
   *
   *
   * Parameters:
   * 	 @param id The ID of the invite.
   * 	 @param idempotencyKey A key the client chooses per request, which makes the request safe to send again. Within
   * 24 hours, a request with a key that the same sign-in of the user in the same client (an
   * anonymous client: the same address) already sent to the operation gets the first answer
   * again instead of being carried out again. The same key with another request (another
   * path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat
   * while the first request is still being carried out is a 409
   * `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx
   * or 5xx the key is free, so a request sent again with it is carried out. A key longer than
   * 256 characters, or one that is not UTF-8 without U+0000, is a 422.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.AdminInvite] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun revokeInvite(
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<AdminInvite> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/admin/invites/${id}/revoke"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Idempotency-Key", idempotencyKey)
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * Refetch a remote post from its origin
   * Queues a fetch of the remote post from its server. The job records what the
   * server serves now, as a received `Update` would: a newer revision, the counts of its
   * poll, or, when the server answers 410 Gone or serves a `Tombstone`, the post's
   * deletion. A post that cannot be fetched is left as it is. An unknown or deleted post is
   * a 404; a local post is a 422. Needs the `manage_domains` permission. The request is recorded in the audit log;
   * nothing schedules a refetch on its own.
   *
   *
   * Parameters:
   * 	 @param id The ID of the remote post.
   * 	 @param idempotencyKey A key the client chooses per request, which makes the request safe to send again. Within
   * 24 hours, a request with a key that the same sign-in of the user in the same client (an
   * anonymous client: the same address) already sent to the operation gets the first answer
   * again instead of being carried out again. The same key with another request (another
   * path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat
   * while the first request is still being carried out is a 409
   * `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx
   * or 5xx the key is free, so a request sent again with it is carried out. A key longer than
   * 256 characters, or one that is not UTF-8 without U+0000, is a 422.
   *
   * Returns:
   * 	[NetworkResult.Success] with [kotlin.Unit] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun refetchStatus(
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<Unit> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/admin/statuses/${id}/refetch"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Idempotency-Key", idempotencyKey)
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * Refetch the posts of a remote account from its server
   * Queues the refetch, as `POST /admin/statuses/{id}/refetch` does, of the newest 1000
   * posts of the remote account that are stored and not deleted. The posts are fetched in
   * batches of 100, a minute apart. An unknown account is a 404; a local account is a 422.
   * Needs the `manage_domains` permission. The request is recorded in the audit log;
   * nothing schedules a refetch on its own.
   *
   *
   * Parameters:
   * 	 @param id The ID of the remote account.
   * 	 @param idempotencyKey A key the client chooses per request, which makes the request safe to send again. Within
   * 24 hours, a request with a key that the same sign-in of the user in the same client (an
   * anonymous client: the same address) already sent to the operation gets the first answer
   * again instead of being carried out again. The same key with another request (another
   * path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat
   * while the first request is still being carried out is a 409
   * `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx
   * or 5xx the key is free, so a request sent again with it is carried out. A key longer than
   * 256 characters, or one that is not UTF-8 without U+0000, is a 422.
   *
   * Returns:
   * 	[NetworkResult.Success] with [kotlin.Unit] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun refetchAccountStatuses(
    id: UUID,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<Unit> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/admin/accounts/${id}/refetch_statuses"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Idempotency-Key", idempotencyKey)
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }

  /**
   * Refetch the posts of the accounts of a remote server
   * Queues the refetch, as `POST /admin/statuses/{id}/refetch` does, of the stored posts of
   * the server's accounts that are not deleted: the newest 20 of each account, newest
   * accounts first, 1000 in all, in batches of 5 accounts a minute apart. A domain that is
   * not a host name is a 400; this server's domain is a 422. Needs the `manage_domains` permission. The request is recorded in the audit log;
   * nothing schedules a refetch on its own.
   *
   *
   * Parameters:
   * 	 @param domain The domain of the remote server, such as `mastodon.example`, normalized to IDNA A-labels in
   * lower case without a trailing dot. A value that is not a host name is a 400.
   * 	 @param idempotencyKey A key the client chooses per request, which makes the request safe to send again. Within
   * 24 hours, a request with a key that the same sign-in of the user in the same client (an
   * anonymous client: the same address) already sent to the operation gets the first answer
   * again instead of being carried out again. The same key with another request (another
   * path, query, or body) is a 422 `/api/v1/problems/idempotency-key-reused`, and a repeat
   * while the first request is still being carried out is a 409
   * `/api/v1/problems/idempotency-key-in-use` with a `Retry-After` of a second. Only an answer below 400 is kept: after a 4xx
   * or 5xx the key is free, so a request sent again with it is carried out. A key longer than
   * 256 characters, or one that is not UTF-8 without U+0000, is a 422.
   *
   * Returns:
   * 	[NetworkResult.Success] with [kotlin.Unit] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun refetchInstanceStatuses(
    domain: String,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<Unit> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/admin/instances/${domain}/refetch_statuses"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Idempotency-Key", idempotencyKey)
        headers {
          apiConfiguration.customHeaders.forEach { (name, value) ->
            remove(name)
            append(name, value)
          }
        }
      }

      if (response.status.isSuccess()) {
        NetworkResult.Success(response.body())
      } else {
        val errorBody = response.bodyAsText().ifBlank { null }
        NetworkResult.Failure(NetworkError.Http(statusCode = response.status.value, statusDescription = response.status.description, body = errorBody))
      }
    } catch (e: ResponseException) {
      val status = e.response.status
      val body = runCatching { e.response.bodyAsText() }.getOrNull()?.ifBlank { null }
      NetworkResult.Failure(NetworkError.Http(status.value, status.description, body))
    } catch (e: IOException) {
      NetworkResult.Failure(NetworkError.Network(e))
    } catch (e: ContentConvertException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: NoTransformationFoundException) {
      NetworkResult.Failure(NetworkError.Serialization(e))
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      NetworkResult.Failure(NetworkError.Unknown(e))
    }
  }
}
