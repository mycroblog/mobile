package social.mycroblog.api.models

import kotlin.Int
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * What an import of a list of sign-up blocks did.
 */
@Serializable
public data class SignupBlockImportResult(
  /**
   * The number of blocks added.
   */
  @SerialName("added")
  public val added: Int,
  /**
   * The lines whose block was not added, in line order.
   */
  @SerialName("skipped")
  public val skipped: List<SignupBlockImportSkipped>,
)
