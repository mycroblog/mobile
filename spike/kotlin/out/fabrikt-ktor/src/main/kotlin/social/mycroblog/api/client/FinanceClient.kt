package social.mycroblog.api.client

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
import kotlin.String
import kotlinx.coroutines.CancellationException
import kotlinx.io.IOException
import social.mycroblog.api.models.CostBasis
import social.mycroblog.api.models.CostGranularity
import social.mycroblog.api.models.FinanceCostTotals
import social.mycroblog.api.models.IncomeTotals

public class FinanceClient(
  private val httpClient: HttpClient,
) {
  /**
   * Total the costs by period for the finance viewers
   * The totals of the instance's costs for the holders of `view_finance`. It
   * takes the query of `GET /admin/cost_totals` and always answers its totals, their breakdown
   * by provider, category, and currency, and the rules that cut the periods. Each period adds
   * `items`, the amount of each cost item and each service of the imported costs, only while
   * the instance setting `finance_viewer_item_names` or `finance_viewer_item_memos` is on: an
   * item line carries `name` while names are shown and `memo` while memos are shown, so with
   * memos only it has a memo and no name. A holder of `manage_finance` reads every item line
   * with its name and memo whatever the settings. `viewer` reports the three settings. No
   * answer carries who paid what, the revisions of the prices, or who recorded the items. More
   * than 104 weeks, 60 months, or 20 years, and `to` before `from`, are a 422 naming `to`.
   * Needs the `view_finance` or the `manage_finance` permission (a 403
   * `/api/v1/problems/missing-permission` otherwise).
   *
   *
   * Parameters:
   * 	 @param granularity 
   * 	 @param from A date in the first period.
   * 	 @param to A date in the last period.
   * 	 @param basis How the totals place a payment; `amortized` by default.
   *
   * Returns:
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.FinanceCostTotals] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun getFinanceCostTotals(
    granularity: CostGranularity,
    from: String,
    to: String,
    basis: CostBasis? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<FinanceCostTotals> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = buildString {
      append(basePath)
      append("""/finance/cost_totals""")
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
   * Total the income by period for the finance viewers
   * The income totals for the holders of `view_finance`. It takes
   * the query of `GET /admin/cost_totals` and always answers the net of each currency by
   * period with its `balance` against the costs, whatever `income_visibility` and
   * `finance_visibility` publish. `plans`, the number of members of each supporter plan now,
   * is present while the instance setting `finance_viewer_income` is `plans` or
   * `income_visibility` publishes the plans; a holder of `manage_finance` always reads it. No
   * answer carries individual payments, the subscribers, the amounts of each plan, or the
   * gross and the fees. More than 104 weeks, 60 months, or 20 years, and `to` before `from`,
   * are a 422 naming `to`. Needs the `view_finance` or the `manage_finance` permission (a 403
   * `/api/v1/problems/missing-permission` otherwise).
   *
   *
   * Parameters:
   * 	 @param granularity 
   * 	 @param from A date in the first period.
   * 	 @param to A date in the last period.
   * 	 @param basis How the totals place a payment; `amortized` by default.
   *
   * Returns:
   * 	[NetworkResult.Success] with [social.mycroblog.api.models.IncomeTotals] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun getFinanceIncomeTotals(
    granularity: CostGranularity,
    from: String,
    to: String,
    basis: CostBasis? = null,
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
  ): NetworkResult<IncomeTotals> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = buildString {
      append(basePath)
      append("""/finance/income_totals""")
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
}
