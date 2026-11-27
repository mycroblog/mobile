package social.mycroblog.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * An uncaught error of the web client.
 */
@Serializable
public data class ClientErrorReport(
  /**
   * The error's `name`, such as `TypeError`.
   */
  @SerialName("name")
  public val name: String,
  /**
   * The error's `message`.
   */
  @SerialName("message")
  public val message: String,
  /**
   * The error's `stack` as the browser formats it.
   */
  @SerialName("stack")
  public val stack: String,
  /**
   * The version of the server, from the page that started the web client.
   */
  @SerialName("release")
  public val release: String? = null,
  /**
   * The ID of the route that was shown (a pattern such as `/_app/statuses/$id`, never the
   * URL).
   */
  @SerialName("route")
  public val route: String? = null,
  /**
   * How the error was caught: `route_error` by the router, `boundary_error` by an error
   * boundary outside the routes, `uncaught` outside every boundary, and
   * `unhandledrejection` as a promise rejection that nothing handled.
   */
  @SerialName("kind")
  public val kind: ClientErrorReportKind,
)
