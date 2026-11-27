package social.mycroblog.api.models

import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The drafts to accept or reject, by key; omitted or empty for every draft.
 */
@Serializable
public data class DomainListDraftsRequest(
  @SerialName("keys")
  public val keys: List<String>? = null,
)
