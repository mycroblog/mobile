package dev.mycroblog.core.api.client

import dev.mycroblog.core.api.models.CostBasis
import dev.mycroblog.core.api.models.CostGranularity
import dev.mycroblog.core.api.models.CostTotals
import dev.mycroblog.core.api.models.IncomeTotals
import dev.mycroblog.core.api.models.Instance
import dev.mycroblog.core.api.models.InstanceDocument
import dev.mycroblog.core.api.models.InstanceDocumentKind
import dev.mycroblog.core.api.models.InstanceDomainActionPage
import dev.mycroblog.core.api.models.InstanceOverview
import dev.mycroblog.core.api.models.PickerCustomEmojiList
import dev.mycroblog.core.api.models.ServerHealth
import dev.mycroblog.core.api.models.SupporterListPage
import dev.mycroblog.core.api.models.SupporterPlanList
import dev.mycroblog.core.api.models.TipAmounts
import io.ktor.client.HttpClient
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.`get`
import io.ktor.client.request.`header`
import io.ktor.client.request.headers
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import io.ktor.serialization.ContentConvertException
import java.util.UUID
import kotlin.Int
import kotlin.String
import kotlinx.coroutines.CancellationException
import kotlinx.io.IOException

public class InstanceClient(
  private val httpClient: HttpClient,
) {
  /**
   * Get the public information of the instance
   *
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.Instance] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun getInstance(apiConfiguration: ApiConfiguration = ApiConfiguration()): NetworkResult<Instance> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/instance"""

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
   * List the custom emoji of the picker
   * The custom emoji of the instance that the picker offers: every
   * enabled local emoji that is not hidden from the picker, by category (those without one
   * first) and shortcode. A text can use every enabled local emoji, hidden or not. The list
   * is not paginated.
   *
   *
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.PickerCustomEmojiList] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun listCustomEmojis(apiConfiguration: ApiConfiguration = ApiConfiguration()): NetworkResult<PickerCustomEmojiList> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/custom_emojis"""

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
   * Get the counts of the instance
   * What the landing page shows about the instance besides `GET /instance`: how many members it
   * has and how active they are. Only local, public data: the members are counted, never listed,
   * and only public posts count. The instance setting `public_counts` decides whether it shows
   * them; while it is off, they are left out. The counts are read again every few minutes, so
   * they may lag behind.
   *
   *
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.InstanceOverview] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun getInstanceOverview(apiConfiguration: ApiConfiguration = ApiConfiguration()): NetworkResult<InstanceOverview> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/instance/overview"""

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
   * Get the health of the server
   * Whether the server can serve requests, for uptime monitors, status pages, and clients that
   * tell a server outage from their own network. The check is the readiness
   * check's: the database is reachable and its schema is applied. Each process caches the
   * result for 60 seconds and runs one check for concurrent requests, so the database is
   * checked at most once a minute however often the endpoint is called. The endpoint takes no
   * access token and counts against no rate limit.
   *
   * The body holds only the fixed words of `status`, never the reason of a failure. During the
   * maintenance mode the answer is a 503 with `Retry-After` and
   * `{"status":"maintenance"}`, without touching the database. Uptime monitors tell the
   * maintenance from an outage by the body.
   *
   *
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.ServerHealth] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun getServerHealth(apiConfiguration: ApiConfiguration = ApiConfiguration()): NetworkResult<ServerHealth> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/instance/health"""

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
   * Get a document of the instance
   * The body of the terms of service (`terms`), the privacy policy (`privacy_policy`) or the
   * commerce disclosures of the supporter plans (`commerce_disclosure`) that the instance
   * has published, as sanitized HTML with the time of its last revision. A 404 when the
   * instance has not published the document (`Instance.terms_url`,
   * `Instance.privacy_policy_url` and `Instance.commerce_disclosure_url` are omitted then),
   * and for an unknown kind. The web client shows it at `/about/terms`, `/about/privacy`
   * and `/about/commerce`.
   *
   *
   * Parameters:
   * 	 @param kind The kind of the document.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.InstanceDocument] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun getInstanceDocument(kind: InstanceDocumentKind, apiConfiguration: ApiConfiguration = ApiConfiguration()): NetworkResult<InstanceDocument> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/instance/documents/${kind}"""

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
   * List the domains that the instance moderates
   * The moderation actions in effect on remote domains that the instance lists publicly: `limit` and `suspend`, one item per action, newest first. The instance
   * setting `domain_actions_visibility`, which `GET /instance` gives, decides who may read it:
   * `off` makes it a 404, `signed_in` a 401 `/api/v1/problems/unauthenticated` for a request
   * without an access token, and `everyone` serves everyone. The setting `domain_actions_rationale_visibility`
   * decides, in the same way, who gets each action's public comment. The moderators'
   * private notes are never listed.
   *
   *
   * Parameters:
   * 	 @param limit The maximum number of items in the page.
   * 	 @param cursor The `next_cursor` of the previous page; omitted for the first page.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.InstanceDomainActionPage] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun listInstanceDomainActions(
    limit: Int? = null,
    cursor: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<InstanceDomainActionPage> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = buildString {
      append(basePath)
      append("""/instance/domain_actions""")
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
   * Total the instance's public costs by period
   * The totals of the instance's costs that the instance setting `finance_visibility`
   * publishes. It takes the query of `GET /admin/cost_totals` and
   * answers the same shape: `private` makes it a 404; `totals` leaves every `breakdown` empty
   * and the `rules` without `sources`, which would name the providers; `breakdown` gives the
   * breakdown by provider, category, and currency, and the providers' rules. No level returns
   * the names, memos, or individual payments of the cost items. The periods stop at the one
   * that holds today (UTC): a `to` after today reads as today, and a `from` after today gives
   * no period. More than 104 weeks, 60 months, or 20 years, and `to` before `from`, are a 422
   * naming `to`.
   *
   *
   * Parameters:
   * 	 @param granularity 
   * 	 @param from A date in the first period.
   * 	 @param to A date in the last period; a date after today reads as today.
   * 	 @param basis How the totals place a payment; `amortized` by default.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.CostTotals] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun getInstanceCostTotals(
    granularity: CostGranularity,
    from: String,
    to: String,
    basis: CostBasis? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<CostTotals> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = buildString {
      append(basePath)
      append("""/instance/cost_totals""")
      val params = buildList {
        add("granularity=${granularity}")
        add("from=${from}")
        add("to=${to}")
        basis?.let { add("basis=${it}") }
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
   * Total the instance's public income by period
   * The net income that the instance setting `income_visibility` publishes. It takes the query of `GET /admin/cost_totals`: `private` makes it a
   * 404; `totals` gives the net of each currency by period; `plans` adds `plans`, the number
   * of members of each supporter plan now. Each period carries `balance`, the net less the
   * costs, only while `finance_visibility` is not `private` either, since the balance would
   * otherwise tell the hidden side. No level returns individual payments, the subscribers,
   * the amounts of each plan, or the gross and the fees. The periods stop at the one that
   * holds today (UTC): a `to` after today reads as today, and a `from` after today gives no
   * period. More than 104 weeks, 60 months, or 20 years, and `to` before `from`, are a 422
   * naming `to`.
   *
   *
   * Parameters:
   * 	 @param granularity 
   * 	 @param from A date in the first period.
   * 	 @param to A date in the last period; a date after today reads as today.
   * 	 @param basis How the totals place a payment; `amortized` by default.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.IncomeTotals] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun getInstanceIncomeTotals(
    granularity: CostGranularity,
    from: String,
    to: String,
    basis: CostBasis? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<IncomeTotals> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = buildString {
      append(basePath)
      append("""/instance/income_totals""")
      val params = buildList {
        add("granularity=${granularity}")
        add("from=${from}")
        add("to=${to}")
        basis?.let { add("basis=${it}") }
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
   * List the supporter plans
   * The supporter plans that take subscriptions, by rank, with their monthly and yearly
   * prices. A 404 while the supporter program is disabled
   * (`Instance.supporters_enabled`).
   *
   *
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.SupporterPlanList] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun listSupporterPlans(apiConfiguration: ApiConfiguration = ApiConfiguration()): NetworkResult<SupporterPlanList> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/instance/supporter_plans"""

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
   * List the supporters who chose to be listed
   * The members of the supporter plans who chose to be listed (`PUT
   * /preferences/supporter_listing`), by plan in the plans' order and newest first within a
   * plan, with the plans that are not retired for the headings. Only each
   * member's account is listed: never an amount, an interval, a date, or a count. Accounts
   * that the viewer may not see are left out, so a page may be shorter than `limit`. The
   * instance setting `supporter_list_visibility` decides who may read it: `off` makes it a
   * 404, `signed_in` a 401 `/api/v1/problems/unauthenticated` for a request without an access
   * token, and `public` serves everyone. The responses always carry `X-Robots-Tag: noindex,
   * noarchive`.
   *
   *
   * Parameters:
   * 	 @param limit The maximum number of items in the page.
   * 	 @param planId Only the members of this plan.
   * 	 @param cursor The `next_cursor` of the previous page; omitted for the first page.
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.SupporterListPage] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun listInstanceSupporters(
    limit: Int? = null,
    planId: UUID? = null,
    cursor: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<SupporterListPage> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = buildString {
      append(basePath)
      append("""/instance/supporters""")
      val params = buildList {
        limit?.let { add("limit=${it}") }
        planId?.let { add("plan_id=${it}") }
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
   * Get the amounts of the tips
   * The amounts that a tip, a one-time payment to the server's operation with nothing in
   * return, may be: the operator's choices, smallest first, in one currency. A 404 while the
   * server takes no tips (`Instance.tips_enabled`). To a request whose access token grants
   * `read:supporters`, it also says why the user may not tip when they may not
   * (`ineligible_reason`, the check that `POST /supporter/tips` makes), and that answer is
   * `no-store`; a request without a token, or with one that does not grant the scope, gets the
   * amounts alone.
   *
   *
   *
   * Returns:
   * 	[NetworkResult.Success] with [dev.mycroblog.core.api.models.TipAmounts] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun getInstanceTipAmounts(apiConfiguration: ApiConfiguration = ApiConfiguration()): NetworkResult<TipAmounts> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/instance/tip_amounts"""

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
}
