package social.mycroblog.api.models

import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * What accepting or rejecting drafts did.
 */
@Serializable
public data class AdminDomainListDraftResult(
  /**
   * The drafts that were accepted or rejected.
   */
  @SerialName("done")
  public val done: List<AdminDomainListDraft>,
  /**
   * The keys of the request that name no draft, such as one handled since.
   */
  @SerialName("missing")
  public val missing: List<String>,
)
