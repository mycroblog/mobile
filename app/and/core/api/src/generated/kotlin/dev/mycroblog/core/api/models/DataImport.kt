package dev.mycroblog.core.api.models

import java.util.UUID
import kotlin.Int
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * An import of a CSV file, with its progress.
 */
@Serializable
public data class DataImport(
  /**
   * The ID of a resource, a UUIDv7 in its canonical string form.
   */
  @Contextual
  @SerialName("id")
  public val id: UUID,
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
   * `in_progress` until every entry and removal is done, then `finished`.
   */
  @SerialName("state")
  public val state: DataImportState,
  /**
   * When the import started.
   */
  @SerialName("created_at")
  public val createdAt: Instant,
  /**
   * The number of entries to add and things to remove.
   */
  @SerialName("total")
  public val total: Int,
  /**
   * How many of them are done, including the failed ones.
   */
  @SerialName("processed")
  public val processed: Int,
  /**
   * How many of them could not be carried out: an account or post that cannot be found
   * or acted on, a follow that a block stops, or a domain that cannot be muted (this
   * server's own, or one beyond the most muted domains allowed).
   */
  @SerialName("failed")
  public val failed: Int,
)
