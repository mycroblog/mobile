package social.mycroblog.api.models

import kotlin.Int
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * What an import of a CSV file would do.
 */
@Serializable
public data class DataImportPreview(
  /**
   * The number of entries of the file, each of which the import adds.
   */
  @SerialName("entries")
  public val entries: Int,
  /**
   * How many of the entries the user has already.
   */
  @SerialName("present")
  public val present: Int,
  /**
   * How many things the user has of the kind that the file does not list, which an
   * `overwrite` removes; 0 for a `merge`.
   */
  @SerialName("removals")
  public val removals: Int,
  /**
   * The lines that are not entries, in line order.
   */
  @SerialName("skipped")
  public val skipped: List<DataImportSkipped>,
)
