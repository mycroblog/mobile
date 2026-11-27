package social.mycroblog.api.client

import io.ktor.client.HttpClient
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.`get`
import io.ktor.client.request.`header`
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import io.ktor.serialization.ContentConvertException
import java.util.UUID
import kotlin.String
import kotlinx.coroutines.CancellationException
import kotlinx.io.IOException
import social.mycroblog.api.models.OwnSupporterSubscription
import social.mycroblog.api.models.SupporterCheckout
import social.mycroblog.api.models.SupporterPlanChoice
import social.mycroblog.api.models.SupporterPortal
import social.mycroblog.api.models.SupporterSubscription
import social.mycroblog.api.models.SupporterTip
import social.mycroblog.api.models.SupporterTipCheckout
import social.mycroblog.api.models.SupporterTipRequest

public class SupportersClient(
  private val httpClient: HttpClient,
) {
  /**
   * Get the authenticated user's supporter subscription
   * The user's subscription that goes on (any state but `expired`, `revoked`, and
   * `abandoned`), and why the user cannot subscribe when they cannot. A 404
   * while the supporter program is disabled (`Instance.supporters_enabled`).
   *
   *
   *
   * Returns:
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.OwnSupporterSubscription] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun getOwnSupporterSubscription(apiConfiguration: ApiConfiguration = ApiConfiguration()): NetworkResult<OwnSupporterSubscription> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/supporter/subscription"""

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
   * Start a subscription to a supporter plan
   * Starts a subscription to a plan at an interval and returns the payment provider's hosted
   * checkout, which the client opens in the same tab; no card details reach this server. The checkout returns to `/settings/supporter` of the web client.
   * Starting again while the subscription is `pending` continues the open checkout for the same
   * plan and interval, or expires it and makes a new one of the same subscription, so that one
   * checkout at most can take a payment. When one was paid already, `redirect_url` is the return
   * to `/settings/supporter` instead of a checkout. Only a local user with a verified primary address and without another
   * subscription may subscribe: a 422 `/api/v1/problems/supporter-ineligible` otherwise, a 409
   * `/api/v1/problems/supporter-subscribed` with a subscription that is not pending, and a 422
   * `/api/v1/problems/supporter-plan-unavailable` for a plan that takes no subscriptions. A 404
   * for an unknown plan and while the program is disabled. From the announcement of the closure
   * of the service on, nobody subscribes: a 409 `/api/v1/problems/service-closing`.
   *
   *
   * Parameters:
   * 	 @param supporterPlanChoice 
   *
   * Returns:
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.SupporterCheckout] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun startSupporterCheckout(supporterPlanChoice: SupporterPlanChoice, apiConfiguration: ApiConfiguration = ApiConfiguration()): NetworkResult<SupporterCheckout> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/supporter/checkout"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Content-Type", "application/json")
        setBody(supporterPlanChoice)
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
   * Re-read the authenticated user's subscription from the payment provider
   * What the web client sends when the payment provider's checkout or portal returns to it:
   * it queues a re-read of the user's subscription from the provider, without waiting for its
   * webhook, and answers the subscription as it is now. Read it again to see the
   * result. A 404 while the program is disabled.
   *
   *
   * Parameters:
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
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.OwnSupporterSubscription] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun syncOwnSupporterSubscription(idempotencyKey: String? = null, apiConfiguration: ApiConfiguration = ApiConfiguration()): NetworkResult<OwnSupporterSubscription> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/supporter/subscription/sync"""

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
   * Change the plan or the interval of the authenticated user's subscription
   * Changes an `active` subscription to another plan or interval: to a higher
   * plan or to the yearly price once the difference, charged pro rata at once, is paid; to a
   * lower plan or to the monthly price at the end of the period, as `scheduled_change`. The new
   * plan's current price applies. A 402 `/api/v1/problems/supporter-payment-failed` when the
   * payment fails, and a 409 `/api/v1/problems/supporter-payment-processing` while it has not
   * settled: in both the plan stays. A 409 `/api/v1/problems/supporter-subscription-state` unless the
   * subscription is `active`, a 422 `/api/v1/problems/supporter-plan-unavailable` for a plan
   * that takes no subscriptions, and a 422 `/api/v1/problems/supporter-same-plan` for the
   * current plan and interval. An unknown plan is a 404, no subscription a 409
   * `/api/v1/problems/supporter-subscription-state`, and an interval that is neither `month`
   * nor `year` a 422. While the supporter program is off, the operation answers the 404 or the
   * 503 `/api/v1/problems/supporters-unavailable` that says what is missing.
   *
   *
   * Parameters:
   * 	 @param supporterPlanChoice 
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
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.SupporterSubscription] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun changeOwnSupporterSubscription(
    supporterPlanChoice: SupporterPlanChoice,
    idempotencyKey: String? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<SupporterSubscription> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/supporter/subscription/change"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Content-Type", "application/json")
        setBody(supporterPlanChoice)
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
   * Cancel the authenticated user's subscription at the end of its period
   * Cancels an `active` or `past_due` subscription at the end of its period: the plan's role
   * stays until then (`canceling`). A 409
   * `/api/v1/problems/supporter-subscription-state` in another state.
   *
   *
   * Parameters:
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
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.SupporterSubscription] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun cancelOwnSupporterSubscription(idempotencyKey: String? = null, apiConfiguration: ApiConfiguration = ApiConfiguration()): NetworkResult<SupporterSubscription> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/supporter/subscription/cancel"""

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
   * Resume the authenticated user's canceling subscription
   * Takes back the cancellation of a `canceling` subscription, which then renews as before. A
   * 409 `/api/v1/problems/supporter-subscription-state` in another state.
   *
   *
   * Parameters:
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
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.SupporterSubscription] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun resumeOwnSupporterSubscription(idempotencyKey: String? = null, apiConfiguration: ApiConfiguration = ApiConfiguration()): NetworkResult<SupporterSubscription> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/supporter/subscription/resume"""

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
   * Cancel the scheduled change of the authenticated user's subscription
   * Cancels the change to a lower plan or to the monthly price that waits for the end of
   * the period; the subscription renews at its current plan and interval. A 409
   * `/api/v1/problems/supporter-subscription-state` without a scheduled change.
   *
   *
   * Parameters:
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
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.SupporterSubscription] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun cancelOwnSupporterScheduledChange(idempotencyKey: String? = null, apiConfiguration: ApiConfiguration = ApiConfiguration()): NetworkResult<SupporterSubscription> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/supporter/subscription/cancel_scheduled_change"""

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
   * Open the payment provider's portal of the authenticated user
   * The payment provider's portal of the user's payment methods and invoices, which returns
   * to `/settings/supporter` of the web client. Plans change on this server, not in
   * the portal. A 409 `/api/v1/problems/subscription-ended` for a user whom the provider does not
   * know.
   *
   *
   *
   * Returns:
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.SupporterPortal] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun openSupporterPortal(apiConfiguration: ApiConfiguration = ApiConfiguration()): NetworkResult<SupporterPortal> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/supporter/portal"""

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
   * Start a tip
   * Starts a tip of one of the amounts of `GET /instance/tip_amounts` and returns the payment
   * provider's hosted checkout of that one payment, which the client opens in the same tab; no
   * card details reach this server. The checkout returns to `/settings/supporter/tip` of the web
   * client with `tip` (the tip's ID) and `checkout` (`success` or `canceled`) in its query.
   * A user has one open tip at most: starting again makes the open tip's checkout unpayable
   * first, and when it was paid already, `redirect_url` is the return of that tip instead of a
   * checkout. Only a local user with a verified primary address may tip: a 422
   * `/api/v1/problems/supporter-ineligible` otherwise, and a 422
   * `/api/v1/problems/supporter-tip-amount` for an amount that is not one of the choices. The
   * starts are limited per user (5 an hour, 20 a day) and for the whole server (100 an hour):
   * a 429 beyond them, with `Retry-After`. A 409 `/api/v1/problems/supporter-tip-pending` when
   * another start of the user's runs at the same time. A 404 while the server takes no tips.
   * From the announcement of the closure of the service on, nobody tips: a 409
   * `/api/v1/problems/service-closing`.
   *
   *
   * Parameters:
   * 	 @param supporterTipRequest 
   *
   * Returns:
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.SupporterTipCheckout] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun startSupporterTip(supporterTipRequest: SupporterTipRequest, apiConfiguration: ApiConfiguration = ApiConfiguration()): NetworkResult<SupporterTipCheckout> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/supporter/tips"""

    return try {
      val response = httpClient.post(url) {
        `header`("Accept", "application/json")
        `header`("Content-Type", "application/json")
        setBody(supporterTipRequest)
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
   * Get a tip of the authenticated user
   * The state of a tip of the authenticated user: `open` until its payment or the end of its
   * checkout, then `paid` or `expired`. While it is `open`, reading it makes the server read the
   * checkout again from the payment provider, so the return from the checkout reads it again
   * shortly until it is `paid`. Another user's tip and an unknown one are a 404.
   *
   *
   * Parameters:
   * 	 @param id The ID of the tip.
   *
   * Returns:
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.SupporterTip] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun getOwnSupporterTip(id: UUID, apiConfiguration: ApiConfiguration = ApiConfiguration()): NetworkResult<SupporterTip> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/supporter/tips/${id}"""

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
