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
import social.mycroblog.api.models.Timeline

public class StreamingClient(
  private val httpClient: HttpClient,
) {
  /**
   * Stream real-time updates
   * A Server-Sent Events stream (`text/event-stream`) of the authenticated user's new home
   * and list timeline entries, notifications, conversations of the direct messages,
   * announcements, and the edits and deletions of the statuses it may see, from the moment it
   * opens. Read it with `fetch` and
   * send the access token in `Authorization`, like every other operation (`EventSource`
   * cannot send it).
   *
   * Events:
   *
   * - `update`: a new post or boost in the home timeline, as it would appear there (the
   *   viewer's follows, timeline languages, blocks, and mutes apply). `data` is a `Status`.
   * - `list_update`: the same post or boost, once for each of the viewer's lists whose
   *   timeline shows it (its author is a member), right after its `update`. `data` is
   *   `{"list_id": "<list id>", "status": <Status>}`.
   * - `notification`: a new notification. `data` is a `Notification`. A notification that the
   *   user's notification policy filters into a notification request sends none.
   * - `notification_requests`: the counts of the notification requests changed (a notification
   *   was filtered into one, or one was accepted or dismissed). `data` is a
   *   `NotificationRequestSummary`.
   * - `notifications_read`: the user read notifications on a device: the marker moved, or one
   *   notification was read on its own. `data` is a `NotificationsRead`.
   * - `conversation`: a conversation of the direct messages changed: a `direct` post that the
   *   user sent or received arrived in it, or the user read it on a device. `data` is a
   *   `Conversation`, as `listConversations` shows it; nothing is sent for a conversation that it
   *   leaves out, nor without the `read:direct` scope.
   * - `announcement`: an announcement that the users see was published or changed. `data`
   *   is an `Announcement`.
   * - `announcement_removed`: an announcement no longer shows (it was withdrawn, or its
   *   window changed). `data` is `{"id": "<announcement id>"}`.
   * - `status_update`: a status that the user may see was edited, by a local author or on its
   *   remote server. `data` is the edited `Status` as the user sees it; replace the status, and
   *   the status inside the boosts of it, wherever they show. It is sent to the users who may see
   *   the status now (its visibility, their follows, the accounts it mentions, and blocks
   *   apply), not without the `read:direct` scope for a `direct` post. The keyword filters of
   *   the home timeline apply as for `update`: a status that a warning filter matches arrives
   *   with `filtered`, and one that a hiding filter matches sends a `delete` instead.
   * - `delete`: remove a status that the user may have seen: it was deleted by its author, a
   *   moderator, or its remote server, or it was edited and a hiding keyword filter of the
   *   home timeline now matches it. `data` is `{"id": "<status id>"}`; remove the status, and
   *   the boosts of it, wherever they show. A deletion is sent to the users who may see the
   *   status now (its visibility, their follows, and blocks apply), not to the recipients of a
   *   `direct` post other than its author, nor without the `read:direct` scope for a `direct`
   *   post. Purges of many statuses at once (an account's deletion, the automatic deletion of
   *   old posts) send none.
   *
   * A comment line (`: heartbeat`) arrives every 30 seconds. The server ends the stream when
   * the access token stops being valid, when the client does not keep up, and when it shuts
   * down; nothing is replayed, so after reconnecting read the home timeline, the open list
   * timelines, the notifications, and the conversations again for what was missed. Announcements whose window starts later
   * arrive with no event: list the announcements again from time to time. A user may hold a limited number of streams
   * open at once; past it the answer is 429 with type
   * `/api/v1/problems/too-many-streams`. While the server cannot receive updates (its
   * broker is not ready, or it is shutting down) the answer is 503 with `Retry-After`; try
   * again after that many seconds. The server may also end an open stream when it may have
   * missed updates; reconnect and read again, as after any other end.
   *
   * With the `stream = hub` setting, each server process delivers only the updates that it
   * produced itself, so a deployment that runs several web processes, or separate web and
   * worker processes, misses updates on some streams. A deployment with a shared stream broker
   * does not.
   *
   *
   *
   * Returns:
   * 	[NetworkResult.Success] with [kotlin.String] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun streamEvents(apiConfiguration: ApiConfiguration = ApiConfiguration()): NetworkResult<String> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/streaming"""

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
   * Stream the new posts of the local or the federated timeline
   * A Server-Sent Events stream (`text/event-stream`) of the new posts of the local timeline
   * (`local`) or the federated timeline (`federated`), from the moment it opens. Read it with
   * `fetch` and send the access token in `Authorization`, like every other operation
   * (`EventSource` cannot send it). Only logged-in users open it, whatever the instance setting
   * `local_timeline_visibility` or `federated_timeline_visibility` allows anonymous visitors; while the
   * setting of the timeline is `off` it is a 404.
   *
   * Events:
   *
   * - `update`: a new post of the timeline, as `GET /api/v1/timelines/local` or
   *   `GET /api/v1/timelines/federated` would list it for the user at that moment (the visibility,
   *   the user's blocks, mutes, and muted domains, and the user's timeline languages apply).
   *   `data` is a `Status`. The local timeline has the public posts of the local accounts and
   *   their `local` posts; the federated timeline has the public posts of local and remote
   *   accounts, never `local` posts, and neither has boosts. A remote post arrives when the
   *   server stores it.
   * - `status_update`: a status that the timeline may list for the user was edited, as
   *   `GET /api/v1/streaming` sends it, with the keyword filters of the public timelines.
   *   `data` is a `Status`.
   * - `delete`: a status that the timeline may have listed for the user was deleted, or was
   *   edited and a hiding keyword filter of the public timelines now matches it, as
   *   `GET /api/v1/streaming` sends it. `data` is `{"id": "<status id>"}`.
   *
   * A comment line (`: heartbeat`) arrives every 30 seconds. The server ends the stream when
   * the access token stops being valid, when the setting of the timeline becomes `off`, when
   * the client does not keep up, and when it shuts down; nothing is replayed, so after
   * reconnecting read the first page of the timeline again for what was missed. The stream
   * counts toward the streams a user may hold open at once, with `GET /api/v1/streaming`; past
   * the limit the answer is 429 with type `/api/v1/problems/too-many-streams`. While the server
   * cannot receive updates (its broker is not ready, or it is shutting down) the answer is 503
   * with `Retry-After`; try again after that many seconds. The server may also end an open
   * stream when it may have missed updates; reconnect and read again, as after any other end.
   *
   * With the `stream = hub` setting, each server process delivers only the updates that it
   * produced itself, so a deployment that runs several web processes, or separate web and
   * worker processes, misses updates on some streams. A deployment with a shared stream broker
   * does not.
   *
   * On a one-person server (`Instance.single_user`) it is a 404 of the type
   * `/api/v1/problems/single-user-mode`, and an open stream ends at the next heartbeat after the
   * server becomes one.
   *
   *
   * Parameters:
   * 	 @param timeline The timeline to stream, `local` or `federated`; any other is a 404.
   *
   * Returns:
   * 	[NetworkResult.Success] with [kotlin.String] if the request was successful.
   * 	[NetworkResult.Failure] with a [NetworkError] if the request failed.
   */
  public suspend fun streamTimeline(timeline: Timeline, apiConfiguration: ApiConfiguration = ApiConfiguration()): NetworkResult<String> {
    val basePath = apiConfiguration.basePath.trimEnd('/')
    val url = basePath + """/streaming/timelines/${timeline}"""

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
