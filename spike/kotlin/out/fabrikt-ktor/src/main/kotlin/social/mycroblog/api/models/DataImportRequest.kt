package social.mycroblog.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A CSV file to import, and how.
 */
@Serializable
public data class DataImportRequest(
  /**
   * What a CSV file lists: `following` (the accounts the user follows), `followed_tags` (the
   * hashtags the user follows, `followed_hashtags.csv`: one hashtag per line without its `#`,
   * a file of this server that Mastodon does not have), `blocks` (the accounts the user
   * blocks), `mutes` (the accounts the user mutes), `domain_mutes` (the domains the user mutes;
   * Mastodon's `blocked_domains.csv` with the header `#domain`, which a Mastodon user's blocked
   * domains become here), or `bookmarks` (the posts the user bookmarked).
   */
  @SerialName("kind")
  public val kind: DataImportKind,
  /**
   * `merge` adds the file's entries and keeps what the user has; `overwrite` also removes
   * what the user has that the file does not list.
   */
  @SerialName("mode")
  public val mode: DataImportMode,
  /**
   * The content of the CSV file, in UTF-8.
   */
  @SerialName("text")
  public val text: String,
)
