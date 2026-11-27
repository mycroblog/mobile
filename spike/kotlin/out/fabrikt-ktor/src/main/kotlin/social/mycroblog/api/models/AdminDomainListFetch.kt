package social.mycroblog.api.models

import java.util.UUID
import kotlin.Int
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * One fetch of a subscribed domain block list.
 */
@Serializable
public data class AdminDomainListFetch(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
  /**
   * `applied`: the list was read and replaced the domains the subscription holds.
   * `not_modified`: the list did not change. `failed`: nothing was read and nothing changed.
   */
  @SerialName("outcome")
  public val outcome: AdminDomainListFetchOutcome,
  /**
   * The status code of the answer; omitted when no answer came.
   */
  @SerialName("http_status")
  public val httpStatus: Int? = null,
  /**
   * Why a fetch failed. `http_status`: the server answered another status than 2xx or 304,
   * such as 401, 403, or 404 of a server that does not publish its domain blocks. `network`:
   * the server could not be reached or did not answer in time. `forbidden_destination`: the
   * URL or a redirect leads to an address the server does not fetch from. `too_large`: the
   * list is larger than 2 MiB. `too_many_entries`: it has more than 5000 rows. `malformed`: it
   * cannot be read in the subscription's format.
   */
  @SerialName("error_code")
  public val errorCode: AdminDomainListFetchError? = null,
  /**
   * The domains the subscription holds after the fetch.
   */
  @SerialName("entry_count")
  public val entryCount: Int,
  @SerialName("fetched_at")
  public val fetchedAt: Instant,
)
